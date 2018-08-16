/**
 * cdp4j Commercial License
 *
 * Copyright 2018 WebFolder OÜ
 *
 * Permission is hereby granted, to "____"
 * obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute and sublicense of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.webfolder.cdp;

import org.jvnet.winp.WinProcess;

public class WindowsProcessManager extends ProcessManager {

    private int pid;

    private String cdp4jId;

    @Override
    void setProcess(CdpProcess process) {
        WinProcess winProcess = new WinProcess(process.getProcess());
        pid = winProcess.getPid();
        cdp4jId = winProcess.getEnvironmentVariables().get("CDP4J_ID");
    }

    @Override
    public boolean kill() {
        if (pid == 0 ||
                cdp4jId == null ||
                cdp4jId.trim().isEmpty()) {
            return false;
        }
        try {
            WinProcess process = new WinProcess(pid);
            String cdp4jId = process.getEnvironmentVariables().get("CDP4J_ID");
            if (pid == process.getPid() &&
                        this.cdp4jId.equals(cdp4jId)) {
                process.killRecursively();
                return true;
            } else {
                return false;
            }
        } catch (Throwable t) {
            return false;
        }
    }
}
