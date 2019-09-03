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

import static java.net.URI.create;
import static java.net.http.HttpClient.newBuilder;
import static java.time.Duration.ofMillis;

import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class JreWebSocketChannelFactory implements ChannelFactory {

    private static final int CONNECTION_TIMEOUT = 10_000; // 10 seconds

    private final HttpClient client;

    public JreWebSocketChannelFactory(Executor executor) {
        this(executor, CONNECTION_TIMEOUT);
    }

    public JreWebSocketChannelFactory(Executor executor, int connectionTimeout) {
        client = newBuilder()
                    .executor(executor)
                    .connectTimeout(ofMillis(connectionTimeout))
                    .build();
    }

    @Override
    public Channel createChannel(Connection connection, SessionFactory factory, MessageHandler handler) {
        CompletableFuture<WebSocket> future = client
                                                .newWebSocketBuilder()
                                                .buildAsync(create(((WebSocketConnection) connection).getUrl()),
                                                        new JreWebSocketMessageAdapter(factory, handler));
        JreWebSocketChannel channel = new JreWebSocketChannel(future);
        return channel;
    }
}
