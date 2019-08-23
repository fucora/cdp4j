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

import java.io.IOException;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import io.webfolder.cdp.exception.CdpException;

public class WebSocketChannelFactory implements ChannelFactory {

    private final WebSocketFactory factory = new WebSocketFactory();

    @Override
    public void setConnectionTimeout(int timeout) {
        factory.setConnectionTimeout(timeout);
    }

    @Override
    public Channel createChannel(String webSocketDebuggerUrl, MessageHandler handler) {
        try {
            return new WebSocketChannel(factory.createSocket(webSocketDebuggerUrl));
        } catch (IOException e) {
            throw new CdpException(e);
        }
    }

    @Override
    public MessageAdapter<WebSocket> createAdapter(MessageHandler handler) {
        return new WebSocketMessageAdapter<WebSocket>(handler);
    }

    public WebSocketFactory getWebSocketFactory() {
        return factory;
    }
}
