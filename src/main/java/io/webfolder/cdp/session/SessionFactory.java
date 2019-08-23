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
import static java.lang.Boolean.TRUE;
import static java.lang.Thread.sleep;
import static java.util.Locale.ENGLISH;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

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

    private final ChannelFactory channelFactory;

    private final Gson gson;

    private final LoggerFactory loggerFactory;

    private final Options options;

    private static final Integer DEFAULT_SCREEN_WIDTH = 1366; // WXGA width

    private static final Integer DEFAULT_SCREEN_HEIGHT = 768; // WXGA height

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private final Map<String, MessageAdapter<?>> adapters = new ConcurrentHashMap<>();

    private final List<String> browserContexts = new CopyOnWriteArrayList<>();

    private final List<TabInfo> tabs = new CopyOnWriteArrayList<>();

    private final ExecutorService threadPool;

    private Channel channel;

    private volatile Session browserSession;

    private volatile boolean closed;

    private volatile Boolean headless;

    private volatile int majorVersion;

    public SessionFactory(Options options) {
        this.options           = options;
        this.channelFactory    = new WebSocketChannelFactory();
        this.loggerFactory     = createLoggerFactory(options.getLoggerType());
        this.threadPool        = options.getThreadPool();
        this.gson              = new GsonBuilder()
                                    .disableHtmlEscaping()
                                    .create();
        this.channelFactory.setConnectionTimeout(options.getConnectionTimeout());
        if (ThreadPoolExecutor.class.isAssignableFrom(threadPool.getClass())) {
            ((ThreadPoolExecutor) threadPool).setKeepAliveTime(5, SECONDS);
        }
    }

    public Session create() {
        return create(null);
    }

    public Session create(String browserContextId) {
        boolean initialized = browserSession == null ? false : true;

        Session browserSession = getBrowserSession();
        Target target = browserSession.getCommand().getTarget();

        TabInfo tab = null;

        if ( ! initialized ) {
            for (int i = 0; i < 500 && tabs.isEmpty(); i++) {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new CdpException(e);
                }
            }
            if ( ! tabs.isEmpty() ) {
                tab = tabs.remove(0);
            }
        }

        if (tab == null) {
            String targetId = target.createTarget("about:blank",
                                                    DEFAULT_SCREEN_WIDTH,
                                                    DEFAULT_SCREEN_HEIGHT,
                                                    browserContextId, false, null, null);
            boolean found = false;
            for (int i = 0; i < 500 && ! found; i++) {
                for (TabInfo info : tabs) {
                    if (info.getTargetId().equals(targetId)) {
                        found = true;
                        tabs.remove(info);
                        break;
                    }
                }
                if ( ! found ) {
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        throw new CdpException(e);
                    }
                }
            }

            tab = new TabInfo(targetId, browserContextId);
        }

        return connect(tab.getTargetId(), tab.getBrowserContextId());
    }

    public Session connect(String targetId) {
        return connect(targetId, null);
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
        String sessionId = target.attachToTarget(targetId);

        Map<Integer, AdapterContext> adapterContexts = new ConcurrentHashMap<>();
        List<EventListener> eventListeners = new CopyOnWriteArrayList<>();

        Session session = new Session(options, gson, sessionId,
                                      targetId, browserContextId,
                                      channel, adapterContexts,
                                      this, eventListeners,
                                      loggerFactory, false,
                                      browserSession, getMajorVersion());
        MessageHandler handler = new MessageHandler(gson, adapterContexts,
                                                    eventListeners, threadPool,
                                                    loggerFactory.getLogger("cdp4j.ws.response"));
        handler.setSession(session);
        MessageAdapter<?> adapter = channelFactory.createAdapter(handler);
        adapters.put(sessionId, adapter);
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

        command.getInspector().enable();
        command.getPage().enable();
        command.getPage().setLifecycleEventsEnabled(true);
 
        return session;
    }

    private synchronized Session getBrowserSession() {
        if (browserSession == null) {
            Map<Integer, AdapterContext> adapterContexts = new ConcurrentHashMap<>();
            List<EventListener> eventlisteners = new CopyOnWriteArrayList<>();
            MessageHandler handler = new MessageHandler(gson, adapterContexts,
                                                        eventlisteners, threadPool,
                                                        loggerFactory.getLogger("cdp4j.ws.response"));
            channel = channelFactory.createChannel(options.getWebSocketDebuggerUrl(), handler);
            MessageAdapter<?> adapter = channelFactory.createAdapter(handler);
            channel.addListener(adapter);
            channel.connect();
            browserSession = new Session(options, gson, options.getWebSocketDebuggerUrl(),
                                         options.getWebSocketDebuggerUrl(), null,
                                         channel, adapterContexts,
                                         this, eventlisteners,
                                         loggerFactory, true,
                                         null, 0);
            handler.setSession(browserSession);
            browserSession.addEventListener(new TargetListener(sessions, adapters, tabs));
            Target target = browserSession.getCommand().getTarget();
            target.setDiscoverTargets(TRUE);
            browserSession.onTerminate(event -> close());
        }
        return browserSession;
    }

    void close(Session session) {
        if (browserSession.isConnected()) {
            session.getCommand()
                   .getPage()
                   .close();
        }
        session.dispose();
        adapters.remove(session.getId());
        sessions.remove(session.getId());
    }

    private int getMajorVersion() {
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
        if (closed) {
            return;
        }
        closed = true;
        if ( browserSession != null ) {
            browserSession.dispose();
        }
        sessions.clear();
        adapters.clear();
        browserContexts.clear();
        tabs.clear();
        threadPool.shutdownNow();
        browserSession = null;
    }

    public void activate(String sessionId) {
        Session session = null;
        for (Session next : sessions.values()) {
            if (next.getId().equals(sessionId)) {
                session = next;
                break;
            }
        }
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

    ExecutorService getThreadPool() {
        return threadPool;
    }

    protected LoggerFactory createLoggerFactory(CdpLoggerType loggerType) {
        return new CdpLoggerFactory(loggerType);
    }

    public boolean closed() {
        return closed;
    }

    @Override
    public String toString() {
        return "SessionFactory [sessions=" + sessions + "]";
    }
}
