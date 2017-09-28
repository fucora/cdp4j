package io.webfolder.cdp;

class CdpProcess {

    private final Process process;

    private final String cdp4jProcessId;

    CdpProcess(Process process, String cdp4jProcessId) {
        this.process = process;
        this.cdp4jProcessId = cdp4jProcessId;
    }

    Process getProcess() {
        return process;
    }

    String getCdp4jProcessId() {
        return cdp4jProcessId;
    }
}
