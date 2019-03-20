package io.webfolder.cdp;

import static java.lang.String.valueOf;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.IOException;

import org.jvnet.winp.WinProcess;

import io.webfolder.cdp.exception.CdpException;

/**
 * Alternative implmentation of {@link WindowsProcessManager}.
 */
public class TaskKillProcessManager extends ProcessManager {

    private CdpProcess cdpProcess;

    @Override
    void setProcess(CdpProcess process) {
        this.cdpProcess = process;
    }

    @Override
    public boolean kill() {
        WinProcess winProcess = new WinProcess(cdpProcess.getProcess());
        try {
            Process process = Runtime
                                .getRuntime()
                                .exec(new String[] {
                                        "cmd", "/c", "/pid",
                                        valueOf(winProcess.getPid()), "/T", "/F"
                                    });
            return process.waitFor(10, SECONDS);
        } catch (IOException | InterruptedException e) {
            throw new CdpException(e);
        }
    }
}
