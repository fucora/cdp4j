package io.webfolder.cdp.session;

public class WebSocketConnection implements Connection {

    private final String webSocketDebuggerUrl;

    public WebSocketConnection(String webSocketDebuggerUrl) {
        this.webSocketDebuggerUrl = webSocketDebuggerUrl;
    }

    String getWebSocketDebuggerUrl() {
        return webSocketDebuggerUrl;
    }
}
