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
package io.webfolder.cdp;

import org.jvnet.winp.WinProcess;

public class WindowsProcessManager extends ProcessManager {

    private int pid;

    private String cdp4jId;


    @Override
    void setProcess(Process process) {
        WinProcess winProcess = new WinProcess(process);
        pid = winProcess.getPid();
        cdp4jId = winProcess.getEnvironmentVariables().get("CDP4J_ID");
    }

    @Override
    void setCdp4jId(String cdp4jId) {
        this.cdp4jId = cdp4jId;
    }

    @Override
    public void kill() {
        if (pid == 0 ||
                cdp4jId == null ||
                cdp4jId.trim().isEmpty()) {
            return;
        }
        try {
            WinProcess process = new WinProcess(pid);
            String cdp4jId = process.getEnvironmentVariables().get("CDP4J_ID");
            if (pid == process.getPid() &&
                        this.cdp4jId.equals(cdp4jId)) {
                process.killRecursively();
            }
        } catch (Throwable t) {
            // ignored
        }
    }
}
