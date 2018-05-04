/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017, 2018 WebFolder OÜ (support@webfolder.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.webfolder.cdp.session;

import static io.webfolder.cdp.logger.CdpLoggerType.Slf4j;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static java.util.Locale.ENGLISH;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import io.webfolder.cdp.command.Target;
import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.listener.EventListener;
import io.webfolder.cdp.logger.CdpLoggerFactory;
import io.webfolder.cdp.logger.CdpLoggerType;
import io.webfolder.cdp.logger.LoggerFactory;

public class SessionFactory implements AutoCloseable {

    public final static String DEFAULT_HOST = "localhost";

    public final static int DEFAULT_PORT = 9222;

    private final String host;

    private final int port;

    private final int connectionTimeout;

    private final WebSocketFactory factory;

    private final Gson gson;

    private final LoggerFactory loggerFactory;

    private static final int DEFAULT_CONNECTION_TIMEOUT = 60 * 1000; // 60 seconds

    private static final Integer DEFAULT_SCREEN_WIDTH = 1366; // WXGA width

    private static final Integer DEFAULT_SCREEN_HEIGHT = 768; // WXGA height

    private static final int DEFAULT_WS_READ_TIMEOUT = 10 * 1000; // 10 seconds

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private final Map<String, WSAdapter> wsAdapters = new ConcurrentHashMap<>();

    private final List<String> contexts = new CopyOnWriteArrayList<>();

    private final BlockingQueue<TabInfo> tabs = new ArrayBlockingQueue<>(256, true);

    private final ExecutorService threadPool;

	private volatile Session browserSession;

	private volatile boolean closed;

    private String browserVersion;

	private WebSocket webSocket;

    private volatile int webSocketReadTimeout = DEFAULT_WS_READ_TIMEOUT;

    public SessionFactory() {
        this(DEFAULT_HOST,
                DEFAULT_PORT,
                DEFAULT_CONNECTION_TIMEOUT,
                Slf4j,
                newCachedThreadPool(new CdpThreadFactory()));
    }

    public SessionFactory(CdpLoggerType loggerType) {
        this(DEFAULT_HOST,
                DEFAULT_PORT,
                DEFAULT_CONNECTION_TIMEOUT,
                loggerType,
                newCachedThreadPool(new CdpThreadFactory()));
    }

    public SessionFactory(final int port) {
        this(DEFAULT_HOST,
                port,
                DEFAULT_CONNECTION_TIMEOUT,
                Slf4j,
                newCachedThreadPool(new CdpThreadFactory()));
    }

    public SessionFactory(final int port, CdpLoggerType loggerType) {
        this(DEFAULT_HOST,
                port,
                DEFAULT_CONNECTION_TIMEOUT,
                loggerType,
                newCachedThreadPool(new CdpThreadFactory()));
    }

    public SessionFactory(final String host, final int port) {
        this(host,
                port,
                DEFAULT_CONNECTION_TIMEOUT,
                Slf4j,
                newCachedThreadPool(new CdpThreadFactory()));
    }

    public SessionFactory(
                final String host,
                final int port,
                final CdpLoggerType loggerType,
                final ExecutorService threadPool) {
        this(host,
                port,
                DEFAULT_CONNECTION_TIMEOUT,
                loggerType,
                threadPool);
    }

    public SessionFactory(
                    final String host,
                    final int port,
                    final int connectionTimeout,
                    final CdpLoggerType loggerType,
                    final ExecutorService threadPool) {
        this.host              = host;
        this.port              = port;
        this.connectionTimeout = connectionTimeout;
        this.factory           = new WebSocketFactory();
        this.loggerFactory     = createLoggerFactory(loggerType);
        this.threadPool        = threadPool;
        this.gson              = new GsonBuilder()
                                    .disableHtmlEscaping()
                                    .create();
        this.factory.setConnectionTimeout(this.connectionTimeout);
        if (ThreadPoolExecutor.class.isAssignableFrom(threadPool.getClass())) {
        	((ThreadPoolExecutor) threadPool).setKeepAliveTime(DEFAULT_WS_READ_TIMEOUT, SECONDS);
        }
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public Session create() {
        return create(null);
    }

    public Session create(String browserContextId) {
    	boolean initialized = browserSession == null ? false : true;
    	Session ms = getBrowserSession();
    	Target target = ms.getCommand().getTarget();
    	TabInfo tab = null;
    	if ( ! initialized && (tab = tabs.poll()) == null ) {
    		for (int i = 0; i < 1000; i++) {
    			try {
					tab = tabs.poll(5, MILLISECONDS);
					if ( tab != null ) {
						break;
					}
				} catch (InterruptedException e) {
					throw new CdpException(e);
				}
    		}
    	} else if (tab == null) {
			target.createTarget("about:blank",
		    					DEFAULT_SCREEN_WIDTH,
		    					DEFAULT_SCREEN_HEIGHT,
		    					browserContextId, false);
    		for (int i = 0; i < 1000; i++) {
    			try {
					tab = tabs.poll(5, MILLISECONDS);
					if ( tab != null ) {
						break;
					}
				} catch (InterruptedException e) {
					throw new CdpException(e);
				}
    		}
    	}

		if (tab == null) {
			throw new CdpException("Unable to create target");
		}

        String sessionId = target.attachToTarget(tab.getTargetId());
        tab.setSessionId(sessionId);

        Map<Integer, WSContext> contexts = new ConcurrentHashMap<>();
		List<EventListener> listeners = new CopyOnWriteArrayList<>();

		Session session = new Session(gson, tab.getSessionId(),
        								tab.getTargetId(), browserContextId,
        								webSocket, contexts,
        								this, listeners,
        								loggerFactory, false,
        								browserSession);
        WSAdapter wsAdapter = new WSAdapter(gson, contexts,
	        									listeners, threadPool,
	        									loggerFactory.getLogger("cdp4j.ws.response"));
        wsAdapter.setSession(session);
        wsAdapters.put(sessionId, wsAdapter);
        sessions.put(sessionId, session);

        return session;
    }

    Session getBrowserSession() {
    	if (browserSession == null) {
    		Map<String, Object> version = getVersion();
    		String webSocketDebuggerUrl = (String) version.get("webSocketDebuggerUrl");
    		webSocket = null;
    		try {
    			webSocket = factory.createSocket(webSocketDebuggerUrl);
    		} catch (IOException e) {
    			throw new CdpException(e);
    		}
    		Map<Integer, WSContext> contexts = new ConcurrentHashMap<>();
    		List<EventListener> listeners = new CopyOnWriteArrayList<>();
    		WSAdapter adapter = new WSAdapter(gson, contexts,
					    				listeners, threadPool,
					    				loggerFactory.getLogger("cdp4j.ws.response"));
    		webSocket.addListener(adapter);
    		try {
    			webSocket.connect();
    		} catch (WebSocketException e) {
    			throw new CdpException(e);
    		}
    		webSocket.setAutoFlush(true);

    		browserSession = new Session(gson, webSocketDebuggerUrl,
    									webSocketDebuggerUrl, null,
					    				webSocket, contexts,
					    				this, listeners,
					    				loggerFactory, true,
					    				null);
    		adapter.setSession(browserSession);
    		browserSession.addEventListener(new TargetListener(sessions, wsAdapters, tabs));
    		Target target = browserSession.getCommand().getTarget();
            target.setDiscoverTargets(TRUE);
            browserSession.onTerminate(event -> close());
    	}
    	return browserSession;
    }

    public void close(Session session) {
    	if (browserSession.isConnected()) {
    		browserSession
	    		.getCommand()
	    		.getTarget()
	    		.closeTarget(session.getTargetId());
    	}
		session.dispose();
		wsAdapters.remove(session.getId());
		sessions.remove(session.getId());
    }

    @Override
    public void close() {
    	if (closed) {
    		return;
    	}
    	closed = true;
    	browserSession.dispose();
        sessions.clear();
        wsAdapters.clear();
        contexts.clear();
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
        return getBrowserSession()
        			.getCommand()
					.getBrowser()
					.getVersion()
					.getUserAgent()
					.toLowerCase(ENGLISH)
					.contains("headless");
    }

    protected Map<String, Object> getVersion() {
        String sessions = format("http://%s:%d/json/version", host, port);
        URL    url      = null;
        Reader reader   = null;
        try {
            url = new URL(sessions);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(connectionTimeout);
            reader = new InputStreamReader(conn.getInputStream());
            @SuppressWarnings("unchecked")
            Map<String, Object> map = gson.fromJson(reader, Map.class);
            return map;
        } catch (ConnectException e) {
            throw new CdpException(format("Unable to connect [%s:%d]", host, port));
        } catch (IOException e) {
            throw new CdpException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    public String createBrowserContext() {
        String browserContextId = getBrowserSession()
					                .getCommand()
					                .getTarget()
					                .createBrowserContext();
        contexts.add(browserContextId);
        return browserContextId;
    }

    public void disposeBrowserContext(final String browserContextId) {
    	if (contexts.contains(browserContextId)) {
    		getBrowserSession()
		    		.getCommand()
		    		.getTarget()
		    		.disposeBrowserContext(browserContextId);
    		contexts.remove(browserContextId);
    	}
    }

    public List<String> listBrowserContextIds() {
        return unmodifiableList(contexts);
    }

    ExecutorService getThreadPool() {
        return threadPool;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    protected LoggerFactory createLoggerFactory(CdpLoggerType loggerType) {
        ServiceLoader<LoggerFactory> loader = ServiceLoader.<LoggerFactory>load(LoggerFactory.class);
        Iterator<LoggerFactory> iter = loader.iterator();
        if (iter.hasNext()) {
            return iter.next();
        } else {
            return new CdpLoggerFactory(loggerType);
        }
    }

    public int getWebSocketReadTimeout() {
		return webSocketReadTimeout;
	}

	public void setWebSocketReadTimeout(int webSocketReadTimeout) {
		this.webSocketReadTimeout = webSocketReadTimeout;
	}

    @Override
    public String toString() {
        return "SessionFactory [host=" + host + ", port=" + port + ", sessions=" + sessions + "]";
    }
}
