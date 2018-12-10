package io.webfolder.cdp.foo;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.net.telnet.TelnetClient;

public class Main {

    public static class Foo implements Runnable {

        private TelnetClient tc;

        public Foo(TelnetClient tc) {
            this.tc = tc;
        }

        @Override
        public void run() {
            InputStream instr = tc.getInputStream();

            try {
                byte[] buff = new byte[1024];
                int ret_read = 0;
                StringBuilder builder = new StringBuilder();
                do {
                    ret_read = instr.read(buff);
                    if (ret_read > 0) {
                        builder.append(new String(buff, 0, ret_read));
                    }
                    System.out.println(builder.toString());
                } while (ret_read >= 0);
            } catch (IOException e) {
                System.err.println("Exception while reading socket:" + e.getMessage());
            }

            try {
                tc.disconnect();
            } catch (IOException e) {
                System.err.println("Exception while closing telnet:" + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws SocketException, IOException, InterruptedException {
        TelnetClient client = new TelnetClient();
        client.setCharset(StandardCharsets.UTF_8);
        client.connect("localhost", 9222);

        Thread t = new Thread(new Foo(client));
        t.start();
        System.out.println("foo");

        Thread.sleep(2000);
        

        System.in.read();
    }
}
