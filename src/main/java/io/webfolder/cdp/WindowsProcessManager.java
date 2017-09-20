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

import java.util.List;

import org.jvnet.winp.WinProcess;
import org.jvnet.winp.WinpException;

public class WindowsProcessManager implements ProcessManager {

    private int pid;

    private String commandLine;

    @Override
    public void onStart(Process process, List<String> args) {
        WinProcess winProcess = new WinProcess(process);
        this.pid = winProcess.getPid();
        this.commandLine = winProcess.getCommandLine();
    }

    @Override
    public synchronized void kill() {
        try {
            WinProcess process = new WinProcess(pid);
            if ( pid > 0 &&
                    commandLine != null &&
                    process.getCommandLine() != null &&
                    commandLine.equals(process.getCommandLine()) ) {
                process.killRecursively();
            }
        } catch (WinpException e) {
            // ignored
        }
    }
}
