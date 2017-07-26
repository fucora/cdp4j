package io.webfolder.cdp.test;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import io.webfolder.cdp.event.Events;
import io.webfolder.cdp.event.dom.SetChildNodes;
import io.webfolder.cdp.event.runtime.ExecutionContextCreated;
import io.webfolder.cdp.listener.EventListener;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.type.dom.Node;
import io.webfolder.cdp.type.runtime.ExecutionContextDescription;

public class TestFrame {

    private static SessionFactory factory;

    private static Session session;

    private static FrameEventListener eventListener;

    private static LoggerContext loggerContext;

    private static CdpAppender appender;

    public TestFrame(SessionFactory factory) {
        TestFrame.factory = factory;
    }

    @BeforeClass
    @SuppressWarnings("unchecked")
    public static void init() {
        loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        appender = new CdpAppender();
        appender.setContext(loggerContext);
        appender.start();
        appender.setName(CdpAppender.class.getName());

        Logger logger = loggerContext.getLogger("cdp4j.flow");
        logger.addAppender((Appender<ILoggingEvent>) appender);

        eventListener = new FrameEventListener();

        session = factory.create();
        session.addEventListener(eventListener);
        session.getCommand().getRuntime().enable();
        session.enableConsoleLog();

        URL url = TestAll.class.getResource("/frame-test.html");
        session.navigate(url.toString());
    }

    @AfterClass
    public static void dispose() {
        appender.stop();
        factory.close();
    }

    public void test() {
        session.wait(1000);

        // Needed so that xpaths work correctly.
        session.getCommand().getDOM().getDocument();

        String linkValue = session.getAttribute("//a[@id='link']", "href");
        Assert.assertEquals("Not expected value", "https://www.google.com", linkValue);

        String frameObjectId = session.getObjectId("//iframe");
        final Integer frameNodeId = session.getCommand().getDOM().requestNode(frameObjectId);
        final ExecutionContextDescription ecFrame = eventListener.getExecutionContextForFrame(frameNodeId);
        linkValue = session.getAttribute(ecFrame.getId(), "//a[@id='link']", "href");
        Assert.assertEquals("Not expected value", "https://www.yahoo.com", linkValue);
    }

    private static class FrameEventListener implements EventListener<Object> {

        CopyOnWriteArrayList<Node> nodes;

        CopyOnWriteArrayList<ExecutionContextDescription> executionContexts;

        FrameEventListener() {
            nodes = new CopyOnWriteArrayList<>();
            executionContexts = new CopyOnWriteArrayList<>();
        }

        public Node findNodeById(final Integer nodeId) {

            for (final Node node : nodes) {
                if (Objects.equals(node.getNodeId(), nodeId)) {
                    return node;
                }
            }
            return null;
        }

        public ExecutionContextDescription getExecutionContextForFrame(final Integer nodeId) {
            final Node node = findNodeById(nodeId);
            if (node != null) {
                return getExecutionContextForFrame(node.getFrameId());
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        public ExecutionContextDescription getExecutionContextForFrame(final String frameId) {

            Map<String, Object> map;
            for (final ExecutionContextDescription ecd : executionContexts) {
                map = (Map<String, Object>) ecd.getAuxData();
                if (Objects.equals(map.get("frameId"), frameId)) {
                    return ecd;
                }
            }

            return null;
        }

        @Override
        public void onEvent(final Events event, final Object value) {
            if (Events.DOMSetChildNodes == event) {
                nodes.clear();
                final SetChildNodes scn = (SetChildNodes) value;
                nodes.addAll(scn.getNodes());
            }
            if (Events.RuntimeExecutionContextCreated == event) {
                final ExecutionContextCreated exc = (ExecutionContextCreated) value;
                executionContexts.add(exc.getContext());
            }
        }

    }
}
