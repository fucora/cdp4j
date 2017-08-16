/**
 * The MIT License
 * Copyright © 2017 WebFolder OÜ
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.webfolder.cdp.sample;

import static java.util.Base64.getEncoder;

import java.util.Collections;
import java.util.Map;

import com.google.gson.Gson;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.command.Network;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import static java.util.Arrays.asList;

public class BasicAuthentication {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        Launcher launcher = new Launcher();

        try (SessionFactory factory = launcher.launch(asList("--headless", "--disable-gpu"));
                            Session session = factory.create()) {

            Network network = session.getCommand().getNetwork();
            network.enable();

            Map<String, Object> headers = Collections.<String, Object>singletonMap("Authorization",
                                            "Basic " + new String(getEncoder().encode("user:password".getBytes())));

            network.setExtraHTTPHeaders(headers);

            session.navigate("https://httpbin.org/basic-auth/user/password");
            session.wait(1000);

            String content = (String) session.evaluate("window.document.body.textContent");
            Map<String, Object> map = new Gson().fromJson(content, Map.class);
            Object authenticated = map.get("authenticated");
            Object user = map.get("user");

            System.out.println("Authenticated: " + authenticated);
            System.out.println("User:" + user);
        }

    }
}
