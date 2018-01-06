/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017 WebFolder OÜ (support@webfolder.io)
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
package com.neovisionaries.ws.client;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;


class WebSocketOutputStream extends BufferedOutputStream
{
    public WebSocketOutputStream(OutputStream out)
    {
        super(out);
    }


    public void write(String string) throws IOException
    {
        // Convert the string into a byte array.
        byte[] bytes = Misc.getBytesUTF8(string);

        write(bytes);
    }


    public void write(WebSocketFrame frame) throws IOException
    {
        writeFrame0(frame);
        writeFrame1(frame);
        writeFrameExtendedPayloadLength(frame);

        // Generate a random masking key.
        byte[] maskingKey = Misc.nextBytes(4);

        // Write the masking key.
        write(maskingKey, 0, maskingKey.length);

        // Write the payload.
        writeFramePayload(frame, maskingKey);
    }


    private void writeFrame0(WebSocketFrame frame) throws IOException
    {
        int b = (frame.getFin()  ? 0x80 : 0x00)
              | (frame.getRsv1() ? 0x40 : 0x00)
              | (frame.getRsv2() ? 0x20 : 0x00)
              | (frame.getRsv3() ? 0x10 : 0x00)
              | (frame.getOpcode() & 0x0F);

        write(b);
    }


    private void writeFrame1(WebSocketFrame frame) throws IOException
    {
        // Frames sent from a client are always masked.
        int b = 0x80;

        int len = frame.getPayloadLength();

        if (len <= 125)
        {
            b |= len;
        }
        else if (len <= 65535)
        {
            b |= 126;
        }
        else
        {
            b |= 127;
        }

        write(b);
    }


    private void writeFrameExtendedPayloadLength(WebSocketFrame frame) throws IOException
    {
        int len = frame.getPayloadLength();

        if (len <= 125)
        {
            return;
        }

        if (len <= 65535)
        {
            // 2-byte in network byte order.
            write((len >> 8) & 0xFF);
            write((len     ) & 0xFF);
            return;
        }

        // In this implementation, the maximum payload length is (2^31 - 1).
        // So, the first 4 bytes are 0.
        write(0);
        write(0);
        write(0);
        write(0);
        write((len >> 24) & 0xFF);
        write((len >> 16) & 0xFF);
        write((len >>  8) & 0xFF);
        write((len      ) & 0xFF);
    }


    private void writeFramePayload(WebSocketFrame frame, byte[] maskingKey) throws IOException
    {
        byte[] payload = frame.getPayload();

        if (payload == null)
        {
            return;
        }

        for (int i = 0; i < payload.length; ++i)
        {
            // Mask
            int b = (payload[i] ^ maskingKey[i % 4]) & 0xFF;

            // Write
            write(b);
        }
    }
}
