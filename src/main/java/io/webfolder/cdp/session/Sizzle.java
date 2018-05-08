/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017, 2018 WebFolder OÜ (support@webfolder.io)
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
package io.webfolder.cdp.session;

import java.util.Scanner;

import io.webfolder.cdp.command.Page;

public interface Sizzle {

    /**
     * @return <code>true</code> if sizzle is installed
     */
    public default boolean useSizzle() {
        return getThis().getSizzleScriptId() != null;
    }

    /**
     * Remove sizzle CSS selector engine
     */
    public default void removeSizzle() {
        if (getThis().useSizzle()) {
            getThis().logEntry("removeSizzle");
            getThis()
                .getCommand()
                .getPage()
                .removeScriptToEvaluateOnNewDocument(getThis().getSizzleScriptId());
            getThis().setSizzleScriptId(null);
        }
    }

    /**
     * Install and use sizzle CSS selector engine instead of browser's native selector engine.
     * 
     * This method must be called before {@link Session#navigate(String)}
     * 
     * @return this
     */
    default Session installSizzle() {
        if ( getThis().getSizzleScriptId() == null ) {
            Page page = getThis().getCommand().getPage();
            page.enable();
            String sizzle = null;
            try (Scanner scanner = new Scanner(getClass().getResourceAsStream("/cdp4j-sizzle-2.3.3.min.js"))) {
                scanner.useDelimiter("\\A");
                sizzle = scanner.hasNext() ? scanner.next() : "";
            }
            String source  = "window.cdp4j = {}; ";
                   source += "window.cdp4j.queryAll = function(selector) { " +
                           sizzle +
                           " var result = Sizzle(selector); if (result.length > 0) { return result; } else { return null; } };" +
                           "window.cdp4j.query = function(selector) { var result = window.cdp4j.queryAll(selector); if (result.length > 0) { return result[0] } else { return null; } };";
            String scriptId = page.addScriptToEvaluateOnNewDocument(source);
            getThis().setSizzleScriptId(scriptId);
        }
        return getThis();
    }

    Session getThis();
}
