/**
 * cpd4j - Chrome DevTools Protocol for Java
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
package io.webfolder.cdp.sample;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.type.network.GetResponseBodyResult;
import io.webfolder.cdp.type.network.Response;

public class NetworkResponse {

    public static void main(String[] args) {
        Launcher launcher = new Launcher();

        try (SessionFactory factory = launcher.launch();
                            Session session = factory.create()) {
            session.getCommand().getNetwork().enable();
            session.navigate("http://cnn.com");
            session
                    .getListenerManager()
                    .addNetworkResponseReceivedListener(rr -> {
                Response response = rr.getResponse();
                GetResponseBodyResult rb = session.getCommand().getNetwork().getResponseBody(rr.getRequestId());
                String body = rb.getBody();
                System.out.println("----------------------------------------");
                System.out.println("URL       : " + response.getUrl());
                System.out.println("Status    : HTTP " + response.getStatus().intValue() + " " + response.getStatusText());
                System.out.println("Mime Type : " + response.getMimeType());
                System.out.println("Content   : " + body.substring(0, body.length() > 1024 ? 1024 : body.length()));
            });
            session.waitDocumentReady();
        }
    }
}
