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

import static java.lang.System.arraycopy;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import io.webfolder.cdp.exception.CdpException;

public class PipeChannel implements Channel {

	private static final char MESSAGE_SEPERATOR = '\0';

	private byte[] remaining;

	private MessageHandler handler;

    private SessionFactory factory;

	protected void onResponse(byte[] data) {
		int start = 0;
		int end   = 0;
		while (end < data.length) {
			if (data[end] == MESSAGE_SEPERATOR) {
				int len = end - start;
				if ( remaining != null ) {
					byte[] msg = new byte[remaining.length + len];
					arraycopy(remaining, 0, msg, 0, remaining.length);
					arraycopy(data, start, msg, remaining.length, len);
					onMessage(new ByteArrayInputStream(msg));
					remaining = null;
				} else {
					onMessage(new ByteArrayInputStream(data, start, len));
				}
				start = end + 1;
			}
			end += 1;
		}
		int remainingLength = end - start;
		if (remainingLength > 0) {
			int srcPos = remainingLength == data.length ? 0 : start;
			if (remaining == null) {
				if (srcPos == 0) {
					remaining = data;
				} else {
					byte[] buffer = new byte[remainingLength];
					arraycopy(data, srcPos, buffer, 0, remainingLength);
					remaining = buffer;
				}
			} else {
				byte[] buffer = new byte[remaining.length + remainingLength];
				arraycopy(remaining, 0, buffer, 0, remaining.length);
				arraycopy(data, srcPos, buffer, remaining.length, remainingLength);
				remaining = buffer;
			}
		}
	}

	protected void onMessage(ByteArrayInputStream bs) {
		if ( ! factory.closed() ) {
		      byte[] b = new byte[bs.available()];
		        try {
		            bs.read(b);
		        } catch (IOException e) {
		            // ignore
		        }
		    try {
		        this.handler.processAsync(b);
		    } catch (Exception e) {
		        throw new CdpException(e);
		    }
		}
	}

	void setHandler(MessageHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public void disconnect() {
        // ignore
    }

    @Override
    public void sendText(String message) {
        // ignore
    }

    @Override
    public void connect() {
        // ignore
    }

    @Override
    public void addListener(MessageAdapter<?> adapter) {
        // ignore
    }
}
