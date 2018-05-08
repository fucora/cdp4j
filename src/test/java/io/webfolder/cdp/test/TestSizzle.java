package io.webfolder.cdp.test;

import java.net.URL;

import org.junit.Test;

import static org.junit.Assert.*;
import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class TestSizzle {

    @Test
    public void test() {
        Launcher launcher = new Launcher();

        try (SessionFactory factory = launcher.launch();
            Session session = factory.create()) {
            URL url = TestAll.class.getResource("/session-test.html");
            session.installSizzle();
            session.navigate(url.toString());
            session.waitDocumentReady();
    
            assertTrue(session.useSizzle());
    
            String innerHtml = (String) session.getProperty("p:contains('%s')", "innerHTML", "hello");
            assertEquals("hello, world!", innerHtml);
            session.setProperty("//p", "innerHTML", "hi");
    
            innerHtml = (String) session.getProperty("p:contains('hi')", "innerHTML");
            assertEquals("hi", innerHtml);
        }
    }
}
