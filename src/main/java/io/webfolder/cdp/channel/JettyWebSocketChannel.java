package io.webfolder.cdp.channel;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.Session;

import io.webfolder.cdp.exception.CdpException;

public class JettyWebSocketChannel implements Channel {

	private final Future<Session> future;

	private Session webSocket;

	public JettyWebSocketChannel(Future<Session> future) {
		this.future = future;
	}

	@Override
	public boolean isOpen() {
		return webSocket.isOpen();
	}

	@Override
	public void disconnect() {
		if (webSocket.isOpen()) {
			webSocket.close();
			try {
				webSocket.disconnect();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	@Override
	public void sendText(String message) {
		webSocket.getRemote().sendStringByFuture(message);
	}

	@Override
	public void connect() {
		try {
			this.webSocket = future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new CdpException(e);
		}
	}
}
