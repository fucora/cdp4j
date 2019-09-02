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

import static io.webfolder.cdp.logger.CdpLoggerType.Null;
import static io.webfolder.cdp.session.ConnectionType.NvWebSocket;
import static java.lang.Integer.valueOf;
import static java.util.Collections.emptyList;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;

import io.webfolder.cdp.logger.CdpLoggerType;
import io.webfolder.cdp.session.ConnectionType;

public class Options {

    private static final int DEFAULT_CONNECTION_TIMEOUT = 10 * 1000; // 10 seconds

    private static final int DEFAULT_READ_TIMEOUT       = 10 * 1000; // 10 seconds

    private static final int DEFAULT_SCREEN_WIDTH       = 1366     ; // WXGA width

    private static final int DEFAULT_SCREEN_HEIGHT      = 768      ; // WXGA height

    private CdpLoggerType loggerType;

    private ExecutorService workerThreadPool;

    private ExecutorService eventHandlerThreadPool;

    private Integer connectionTimeout;

    private Integer readTimeout;

    private List<String> arguments;

    private Path userDataDir;

    private ProcessManager processManager;

    private boolean headless;

    private Integer screenWidth;

    private Integer screenHeight;

    private ConnectionType connectionType;

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

        public Builder loggerType(CdpLoggerType loggerType) {
            options.loggerType = loggerType;
            return this;
        }

        public Builder workerThreadPool(ExecutorService workerThreadPool) {
            options.workerThreadPool = workerThreadPool;
            return this;
        }

        public Builder eventHandlerThreadPool(ExecutorService eventHandlerThreadPool) {
            options.eventHandlerThreadPool = eventHandlerThreadPool;
            return this;
        }

        public Builder connectionTimeout(int connectionTimeout) {
            options.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder arguments(List<String> arguments) {
            options.arguments = arguments;
            return this;
        }

        public Builder userDataDir(Path userDataDir) {
            options.userDataDir = userDataDir;
            return this;
        }

        public Builder headless(boolean headless) {
            options.headless = headless;
            return this;
        }

        public Builder connectionType(ConnectionType connectionType) {
            options.connectionType = connectionType;
            return this;
        }

        public Options build() {
            if (options.loggerType == null) {
                options.loggerType = Null;
            }
            if (options.workerThreadPool == null) {
                options.workerThreadPool = newSingleThreadExecutor(new CdpThreadFactory("cdp4j-WorkerThread"));
            }
            if (options.eventHandlerThreadPool == null) {
                options.eventHandlerThreadPool = newSingleThreadExecutor(new CdpThreadFactory("cdp4j-EventHandlerThread"));
            }
            if (options.connectionTimeout == null) {
                options.connectionTimeout = valueOf(DEFAULT_CONNECTION_TIMEOUT);
            }
            if (options.arguments == null) {
                options.arguments = emptyList();
            }
            if (options.readTimeout == null) {
                options.readTimeout = DEFAULT_READ_TIMEOUT;
            }
            if (options.processManager == null) {
                options.processManager = new AdaptiveProcessManager();
            }
            if (options.screenHeight == null) {
                options.screenHeight = DEFAULT_SCREEN_HEIGHT;
            }
            if (options.screenWidth == null) {
                options.screenWidth = DEFAULT_SCREEN_WIDTH;
            }
            if (options.connectionType == null) {
                options.connectionType = NvWebSocket;
            }
            return options;
        }
    }

    public CdpLoggerType getLoggerType() {
        return loggerType;
    }

    public ExecutorService getWorkerThreadPool() {
        return workerThreadPool;
    }

    public ExecutorService getEventHandlerThreadPool() {
        return eventHandlerThreadPool;
    }

    public int getConnectionTimeout() {
        return connectionTimeout.intValue();
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

    public ProcessManager getProcessManager() {
        return processManager;
    }

    public boolean isHeadless() {
        return headless;
    }

    public Integer getScreenWidth() {
        return screenWidth;
    }

    public Integer getScreenHeight() {
        return screenHeight;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }
}
