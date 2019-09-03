package io.webfolder.cdp.channel;

import static java.lang.Integer.MAX_VALUE;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import io.webfolder.cdp.session.MessageHandler;
import io.webfolder.cdp.session.SessionFactory;

@WebSocket(maxTextMessageSize = MAX_VALUE)
public class JettyWebSocketMessageAdapter {

    private final SessionFactory factory;

    private final MessageHandler handler;

    public JettyWebSocketMessageAdapter(SessionFactory factory, MessageHandler handler) {
        this.factory = factory;
        this.handler = handler;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
    	factory.close();
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
    	handler.process(data);
    }
}
