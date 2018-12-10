package io.webfolder.cdp.foo;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class EchoClient {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 9222);
        java.util.concurrent.Future<Void> future = client.connect(hostAddress);
        future.get(); // returns null

        System.out.println("Client is started: " + client.isOpen());
        System.out.println("Sending messages to server: ");

        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Future<Integer> task = client.read(buffer);
            Integer len = task.get();

            if (len > 0) {
                byte[] content = buffer.array();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < content.length; i++) {
                    char c = (char) content[i];
                    if (':' == c) {
                        break;
                    }
                    builder.append(c);
                }
                String strMessageLength = builder.toString();
                int messageLength = Integer.parseInt(strMessageLength);
                String message = new String(content, strMessageLength.length() + 1, messageLength, UTF_8);
                
            }
        }
    }
}
