package io.webfolder.cdp.session;

public enum WaitUntil {
    Load("load"),
    DomContentLoad("DOMContentLoaded"),
    NetworkIdle("networkIdle"),
    NetworkAlmostIdle("networkAlmostIdle"),
    DomReady("DomReady");

    public String value;

    private WaitUntil(String value) {
        this.value = value;
    }
}
