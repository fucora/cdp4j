package io.webfolder.cdp.channel;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.session.MessageHandler;
import io.webfolder.cdp.session.SessionFactory;

public class JettyWebSocketChannelFactory implements ChannelFactory, AutoCloseable {

	private final WebSocketClient client;

	public JettyWebSocketChannelFactory() {
		client = new WebSocketClient();
		try {
			client.start();
		} catch (Exception e) {
			throw new CdpException(e);
		}
	}

	public JettyWebSocketChannelFactory(WebSocketClient client) {
		this.client = client;
	}

	@Override
	public Channel createChannel(Connection connection, SessionFactory factory, MessageHandler handler) {
        String url = ((WebSocketConnection) connection).getUrl();
        Future<Session> future = null;
        try {
			future = client.connect(new JettyWebSocketMessageAdapter(factory, handler), new URI(url));
		} catch (IOException | URISyntaxException e) {
			throw new CdpException(e);
		}
		return new JettyWebSocketChannel(future);
	}

	@Override
	public void close() {
		if ( client != null && ! client.isStopped() ) {
			try {
				client.stop();
			} catch (Exception e) {
				// ignore
			}
		}
	}
}
