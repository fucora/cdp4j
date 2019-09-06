package io.webfolder.cdp.command;

import io.webfolder.cdp.session.SessionInvocationHandler;
import io.webfolder.cdp.command.WebAudio;
import io.webfolder.cdp.type.webaudio.ContextRealtimeData;

public class WebAudioImpl implements WebAudio {

    private static final String[] EMPTY_ARGS = new String[] {};
    private static final Object[] EMPTY_VALUES = new Object[] {};
    private final SessionInvocationHandler handler;

    public WebAudioImpl(SessionInvocationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void disable() {
        handler.invoke("WebAudio", "disable", "WebAudio.disable", null, void.class, null, true, false, true, EMPTY_ARGS,
                EMPTY_VALUES);
    }

    @Override
    public void enable() {
        handler.invoke("WebAudio", "enable", "WebAudio.enable", null, void.class, null, true, true, false, EMPTY_ARGS,
                EMPTY_VALUES);
    }

    @Override
    public ContextRealtimeData getRealtimeData(String contextId) {
        return (ContextRealtimeData) handler.invoke("WebAudio", "getRealtimeData", "WebAudio.getRealtimeData",
                "realtimeData", ContextRealtimeData.class, null, false, false, false, new String[] { "contextId" },
                new Object[] { contextId });
    }
}