/**
 * cdp4j Commercial License
 *
 * Copyright 2017, 2019 WebFolder OÜ
 *
 * Permission  is hereby  granted,  to "____" obtaining  a  copy of  this software  and
 * associated  documentation files  (the "Software"), to deal in  the Software  without
 * restriction, including without limitation  the rights  to use, copy, modify,  merge,
 * publish, distribute  and sublicense  of the Software,  and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  IMPLIED,
 * INCLUDING  BUT NOT  LIMITED  TO THE  WARRANTIES  OF  MERCHANTABILITY, FITNESS  FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL  THE AUTHORS  OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.webfolder.cdp.session;

import static io.webfolder.cdp.event.Events.RuntimeExecutionContextCreated;
import static io.webfolder.cdp.event.Events.RuntimeExecutionContextDestroyed;
import static io.webfolder.cdp.session.ConnectionType.JreWebSocket;
import static java.lang.Boolean.TRUE;
import static java.util.Locale.ENGLISH;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.webfolder.cdp.Options;
import io.webfolder.cdp.command.Target;
import io.webfolder.cdp.event.runtime.ExecutionContextCreated;
import io.webfolder.cdp.event.runtime.ExecutionContextDestroyed;
import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.listener.EventListener;
import io.webfolder.cdp.logger.CdpLoggerFactory;
import io.webfolder.cdp.logger.CdpLoggerType;
import io.webfolder.cdp.logger.LoggerFactory;
import io.webfolder.cdp.type.target.TargetInfo;

public class SessionFactory implements AutoCloseable {

    private final Gson gson;

    private final LoggerFactory loggerFactory;

    private final Options options;

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private final List<String> browserContexts = new CopyOnWriteArrayList<>();

    private final String browserTargetId;

    private final Channel channel;

    private volatile Session browserSession;

    private volatile Boolean headless;

    private volatile int majorVersion;

    private AtomicBoolean closed = new AtomicBoolean(false);

    public SessionFactory(Options options, Connection connection) {
        this.options           = options;
        this.loggerFactory     = createLoggerFactory(options.getLoggerType());
        this.gson              = new GsonBuilder()
                                    .disableHtmlEscaping()
                                    .create();
        ChannelFactory channelFactory = JreWebSocket.equals(options.getConnectionType()) ?
                                                new JreWebSocketChannelFactory(this) :
                                                new NvWebSocketChannelFactory(this)  ;
        MessageHandler handler = new MessageHandler(gson, this,
                                                    options.getWorkerThreadPool(), options.getEventHandlerThreadPool(),
                                                    loggerFactory.getLogger("cdp4j.ws.response", options.getLoggerLevel()));
        channel = channelFactory.createChannel(connection, options.getConnectionTimeout(), handler);
        channel.connect();
        this.browserTargetId = initBrowserSession();
    }

    public Session create() {
        return create(null);
    }

    public Session create(String browserContextId) {
        Session browserSession = getBrowserSession();
        Target target = browserSession.getCommand().getTarget();
        // Try to use blank page on first launch
        if (sessions.isEmpty() && browserContextId == null) {
            TargetInfo blankPage = null;
            List<TargetInfo> targets = target.getTargets();
            for (TargetInfo next : targets) {
                if (isEmptyTarget(next)) {
                    blankPage = next;
                    break;
                }
            }
            if ( blankPage != null ) {
                return connect(blankPage.getTargetId(), blankPage.getBrowserContextId());
            }
        }
        String targetId = target.createTarget("about:blank",
                                              options.getScreenWidth(),
                                              options.getScreenHeight(),
                                              browserContextId, false, null, null);
        return connect(targetId, browserContextId);
    }

    public Session connect(String targetId) {
        return connect(targetId, null);
    }

    private boolean isEmptyTarget(TargetInfo targetInfo) {
        String url = targetInfo.getUrl();
        String type = targetInfo.getType();
        if ("page".equals(type) &&
                (url.isEmpty()                      ||
                    "about:blank".equals(url)       ||
                    "chrome://welcome/".equals(url) ||
                    "chrome://newtab/".equals(url)  ||
                    url.startsWith("chrome://welcome-win10"))) {
            return true;
        }
        return false;
    }

    Session connect(String targetId, String browserContextId) {
        Session bs = getBrowserSession();

        if (browserContextId == null) {
            TargetInfo found = null;
            List<TargetInfo> targets = bs.getCommand().getTarget().getTargets();
            for (TargetInfo next : targets) {
                if (next.getTargetId().equals(targetId)) {
                    found = next;
                }
            }
            if (found == null) {
                throw new CdpException("Target not found: " + targetId);
            }
            browserContextId = found.getBrowserContextId();
        }

        Target target = bs.getCommand().getTarget();
        String sessionId = target.attachToTarget(targetId, TRUE);

        Map<Integer, Context> contexts = new ConcurrentHashMap<>();
        List<EventListener> eventListeners = new CopyOnWriteArrayList<>();

        Session session = new Session(options, gson, sessionId,
                                      targetId, browserContextId,
                                      channel, contexts,
                                      this, eventListeners,
                                      loggerFactory);
        sessions.put(sessionId, session);

        session.getCommand().getRuntime().enable();

        session.addEventListener((event, value) -> {
            if (RuntimeExecutionContextCreated.equals(event)) {
                ExecutionContextCreated ecc = (ExecutionContextCreated) value;
                if (targetId.equals(ecc.getContext().getAuxData().get("frameId"))) {
                    session.setExecutionContextId(ecc.getContext().getId());
                }
            } else if (RuntimeExecutionContextDestroyed.equals(event)) {
                ExecutionContextDestroyed ecd = (ExecutionContextDestroyed) value;
                if ( ecd.getExecutionContextId() != null &&
                        ecd.getExecutionContextId().equals(session.getExecutionContextId()) ) {
                    session.setExecutionContextId(null);
                }
            }
        });

        Command command = session.getCommand();

        command.getPage().enable();
        command.getPage().setLifecycleEventsEnabled(true);
 
        return session;
    }

    private String initBrowserSession() {
        Map<Integer, Context> contexts = new ConcurrentHashMap<>();
        List<EventListener> eventlisteners = new CopyOnWriteArrayList<>();
        browserSession = new Session(options, gson, null,
                                     null, null,
                                     channel, contexts,
                                     this, eventlisteners,
                                     loggerFactory);
        browserSession.addEventListener(new TargetListener(sessions));        
        Target target = browserSession.getCommand().getTarget();
        target.setDiscoverTargets(TRUE);
        TargetInfo info = target.getTargetInfo();
        String targetId = info.getTargetId();
        return targetId;
    }

    Session getBrowserSession() {
        return browserSession;
    }

    Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    void close(Session session) {
        session.getCommand()
               .getPage()
               .close();
        session.getCommand()
               .getTarget()
               .closeTarget(session.getTargetId());
        session.dispose();
        sessions.remove(session.getId());
    }

    public int getMajorVersion() {
        if (majorVersion == 0) {
            String[] product = browserSession
                                        .getCommand()
                                        .getBrowser()
                                        .getVersion()
                                        .getProduct()
                                        .split("/");
            if (product.length == 2) {
                String[] version = product[1].split("\\.");
                if (version.length > 2) {
                    majorVersion = Integer.parseInt(version[0]);
                }
            }
        }
        return majorVersion;
    }

    @Override
    public void close() {
        if (closed.compareAndSet(false, true)) {
            Target target = browserSession.getCommand().getTarget();
            if (channel.isOpen()) {
                for (String next : browserContexts) {
                    target.disposeBrowserContext(next);
                }
            }
            if ( browserSession != null ) {
                if (channel.isOpen()) {
                    target.closeTarget(browserTargetId);
                }
                browserSession.dispose();
            }
            channel.disconnect();
            sessions.clear();
            browserContexts.clear();
            if ( ! options.getWorkerThreadPool().isShutdown() ) {
                options.getWorkerThreadPool().shutdownNow();
            }
            if ( ! options.getEventHandlerThreadPool().isShutdown() ) {
                options.getEventHandlerThreadPool().shutdownNow();
            }
            browserSession = null;
        }
    }

    public void activate(String sessionId) {
        Session session = sessions.get(sessionId);
        if ( session != null ) {
                browserSession
                    .getCommand()
                    .getTarget()
                    .activateTarget(session.getTargetId());
        }
    }

    public boolean isHeadless() {
        if (headless == null) {
            headless = getBrowserSession()
                            .getCommand()
                            .getBrowser()
                            .getVersion()
                            .getProduct()
                            .toLowerCase(ENGLISH)
                            .contains("headless");
        }
        return headless.booleanValue();
    }

    public String createBrowserContext() {
        String browserContextId = getBrowserSession()
                                    .getCommand()
                                    .getTarget()
                                    .createBrowserContext();
        browserContexts.add(browserContextId);
        return browserContextId;
    }

    public void disposeBrowserContext(final String browserContextId) {
        if (browserContexts.contains(browserContextId)) {
            getBrowserSession()
                    .getCommand()
                    .getTarget()
                    .disposeBrowserContext(browserContextId);
            browserContexts.remove(browserContextId);
        }
    }

    public boolean closed() {
        return closed.get();
    }

    ExecutorService getWorkerThreadPool() {
        return options.getWorkerThreadPool();
    }

    protected LoggerFactory createLoggerFactory(CdpLoggerType loggerType) {
        return new CdpLoggerFactory(loggerType);
    }

    @Override
    public String toString() {
        return "SessionFactory [sessions=" + sessions + "]";
    }
}
