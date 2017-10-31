package io.webfolder.cdp.sample;

import static io.webfolder.cdp.session.SessionFactory.DEFAULT_PORT;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.util.Random;

import static java.lang.System.getProperty;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class MultiProcess {


    // port number and user-data-dir must be different for each chrome process
    // As an alternative @see IncognitoBrowsing.java for incognito mode (private browsing).
    public static void main(String[] args) {
        new Thread() {

            public void run() {
                Launcher launcher1 = new Launcher(getFreePort(DEFAULT_PORT));

                Path remoteProfileData1 = get(getProperty("java.io.tmpdir")).resolve("remote-profile-" + new Random().nextInt());
                SessionFactory factory1 = launcher1.launch(asList("--user-data-dir=" + remoteProfileData1.toString()));

                try (SessionFactory sf = factory1) {
                    try (Session session = sf.create()) {
                        session.navigate("https://webfolder.io");
                        session.waitDocumentReady();
                        System.out.println("Content Length: " + session.getContent().length());
                    }
                }                
            }
        }.start();
        
        new Thread() {

            public void run() {
                Launcher launcher2 = new Launcher(getFreePort(DEFAULT_PORT));
                Path remoteProfileData2 = get(getProperty("java.io.tmpdir")).resolve("remote-profile-" + new Random().nextInt());
                SessionFactory factory2 = launcher2.launch(asList("--user-data-dir=" + remoteProfileData2.toString()));

                try (SessionFactory sf = factory2) {
                    try (Session session = sf.create()) {
                        session.navigate("https://webfolder.io");
                        session.waitDocumentReady();
                        System.out.println("Content Length: " + session.getContent().length());
                    }
                }                
            }
        }.start();
    }

    protected static int getFreePort(int portNumber) {
        try (ServerSocket socket = new ServerSocket(portNumber)) {
            int freePort = socket.getLocalPort();
            return freePort;
        } catch (IOException e) {
            return getFreePort(portNumber + 1);
        }
    }
}
