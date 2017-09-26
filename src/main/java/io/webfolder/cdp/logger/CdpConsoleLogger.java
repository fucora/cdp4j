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
package io.webfolder.cdp.logger;

import static io.webfolder.cdp.logger.MessageFormatter.arrayFormat;
import static io.webfolder.cdp.logger.MessageFormatter.format;
import static java.lang.System.getProperty;

public class CdpConsoleLogger implements CdpLogger {

    private final String LEVEL = getProperty("cdp.console.defaultLogLevel", "info");

    @Override
    public void info(String message, Object... args) {
        if ("info".equals(LEVEL) || "debug".equals(LEVEL)) {
            FormattingTuple tuple = arrayFormat(message, args);
            System.out.println("[INFO] " + tuple.getMessage());
        }
    }

    @Override
    public void debug(String message, Object... args) {
        if ("debug".equals(LEVEL)) {
            FormattingTuple tuple = arrayFormat(message, args);
            System.out.println("[INFO] " + tuple.getMessage());
        }
    }

    @Override
    public void warning(String message, Object... args) {
        if ("info".equals(LEVEL) || "debug".equals(LEVEL)) {
            FormattingTuple tuple = arrayFormat(message, args);
            System.out.println("[WARN] " + tuple.getMessage());
        }
    }

    @Override
    public void error(String message, Object... args) {
        if ("info".equals(LEVEL) || "debug".equals(LEVEL) || "error".equals(LEVEL)) {
            FormattingTuple tuple = arrayFormat(message, args);
            System.out.println("[ERROR] " + tuple.getMessage());
        }
    }

    @Override
    public void error(String message, Throwable t) {
        if ("info".equals(LEVEL) || "debug".equals(LEVEL) || "error".equals(LEVEL)) {
            FormattingTuple tuple = format(message, t.getMessage());
            System.err.println("[ERROR] " + tuple.getMessage());
        }
    }
}
