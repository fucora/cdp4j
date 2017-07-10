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
