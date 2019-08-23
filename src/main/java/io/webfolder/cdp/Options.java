/**
 * cdp4j Commercial License
 *
 * Copyright 2017, 2019 WebFolder OÜ
 *
 * Permission  is hereby  granted,  to "____" obtaining  a  copy of  this software  and
 * associated  documentation files  (the "Software"), to deal in  the Software  without
 * restriction, including without limitation  the rights  to use, copy, modify,  merge,
 * publish, distribute  and sublicense  of the Software,  and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  IMPLIED,
 * INCLUDING  BUT NOT  LIMITED  TO THE  WARRANTIES  OF  MERCHANTABILITY, FITNESS  FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL  THE AUTHORS  OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.webfolder.cdp;

import static java.util.Collections.emptyList;
import static io.webfolder.cdp.ConnectionType.WebSocket;
import static io.webfolder.cdp.logger.CdpLoggerType.Null;
import static java.lang.Integer.valueOf;
import static java.util.concurrent.Executors.newCachedThreadPool;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;

import io.webfolder.cdp.logger.CdpLoggerType;

public class Options {
    
    private static final int DEFAULT_CONNECTION_TIMEOUT = 60 * 1000; // 60 seconds

    private static final int DEFAULT_WS_READ_TIMEOUT = 60 * 1000; // 60 seconds

    private CdpLoggerType loggerType;

    private ExecutorService threadPool;

    private Integer connectionTimeout;

    private Integer readTimeout;

    private String webSocketDebuggerUrl;

    private ConnectionType connectionType;

    private List<String> arguments;

    private Path userDataDir;

    private ProcessManager processManager;

    private Options() {
        // no op
    }

    public static Builder builder() {
        return new Options.Builder();
    }

    public static class Builder {
        
        private Options options = new Options();

        private Builder() {
            // no op
        }

        public Builder setWebSocketDebuggerUrl(String webSocketDebuggerUrl) {
            options.webSocketDebuggerUrl = webSocketDebuggerUrl;
            return this;
        }

        public Builder loggerType(CdpLoggerType loggerType) {
            options.loggerType = loggerType;
            return this;
        }

        public Builder threadPool(ExecutorService threadPool) {
            options.threadPool = threadPool;
            return this;
        }

        public Builder connectionTimeout(int connectionTimeout) {
            options.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder connectionType(ConnectionType connectionType) {
            options.connectionType = connectionType;
            return this;
        }

        public Builder arguments(List<String> arguments) {
            options.arguments = arguments;
            return this;
        }

        public Builder setUserDataDir(Path userDataDir) {
            options.userDataDir = userDataDir;
            return this;
        }

        public Options build() {
            if (options.loggerType == null) {
                options.loggerType = Null;
            }
            if (options.threadPool == null) {
                options.threadPool = newCachedThreadPool(new CdpThreadFactory());
            }
            if (options.connectionTimeout == null) {
                options.connectionTimeout = valueOf(DEFAULT_CONNECTION_TIMEOUT);
            }
            if (options.connectionType == null) {
                options.connectionType = WebSocket;
            }
            if (options.arguments == null) {
                options.arguments = emptyList();
            }
            if (options.readTimeout == null) {
                options.readTimeout = DEFAULT_WS_READ_TIMEOUT;
            }
            if (options.processManager == null) {
                options.processManager = new AdaptiveProcessManager();
            }
            return options;
        }
    }

    public String getWebSocketDebuggerUrl() {
        return webSocketDebuggerUrl;
    }

    public CdpLoggerType getLoggerType() {
        return loggerType;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public int getConnectionTimeout() {
        return connectionTimeout.intValue();
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public Path getUserDataDir() {
        return userDataDir;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    void setWebSocketDebuggerUrl(String webSocketDebuggerUrl) {
        this.webSocketDebuggerUrl = webSocketDebuggerUrl;
    }

    public ProcessManager getProcessManager() {
        return processManager;
    }

    @Override
    public String toString() {
        return "Options [loggerType=" + loggerType + ", threadPool=" + threadPool + ", connectionTimeout="
                + connectionTimeout + ", readTimeout=" + readTimeout + ", webSocketDebuggerUrl=" + webSocketDebuggerUrl
                + ", connectionType=" + connectionType + ", arguments=" + arguments + ", userDataDir=" + userDataDir
                + ", processManager=" + processManager + "]";
    }
}
