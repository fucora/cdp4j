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
import java.io.InputStream;
import java.io.OutputStream;

import io.webfolder.cdp.exception.CdpException;

class PipeChannel implements Channel, Runnable {

	private static final char MESSAGE_SEPERATOR = '\0';

    private final InputStream is;

    private final OutputStream os;

	private byte[] remaining;

	private MessageHandler handler;

	public PipeChannel(PipeConnection connection) {
	    this.is = connection.getInput();
	    this.os = connection.getOutput();
	    Thread thread = new Thread(this);
	    thread.setDaemon(true);
	    thread.start();
    }

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
      byte[] b = new byte[bs.available()];
        try {
            bs.read(b);
        } catch (IOException e) {
            // ignore
        }
        try {
            this.handler.process(b);
        } catch (Exception e) {
            throw new CdpException(e);
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
        try {
            this.os.write((message).getBytes());
            this.os.write(0);
        } catch (IOException e) {
            throw new CdpException(e);
        }
    }

    @Override
    public void connect() {
        // ignore
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    int available = is.available();
                    if (available > 0) {
                        byte[] content = new byte[available];
                        is.read(content);
                        onResponse(content);
                    }
                }
            } catch (IOException e) {
                throw new CdpException(e);
            }
        }
    }
}
