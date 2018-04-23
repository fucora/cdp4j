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
package io.webfolder.cdp.event.page;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.page.DialogType;

/**
 * Fired when a JavaScript initiated dialog (alert, confirm, prompt, or onbeforeunload) is about to
 * open
 */
@Domain("Page")
@EventName("javascriptDialogOpening")
public class JavascriptDialogOpening {
    private String url;

    private String message;

    private DialogType type;

    private Boolean hasBrowserHandler;

    private String defaultPrompt;

    /**
     * Frame url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Frame url.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Message that will be displayed by the dialog.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Message that will be displayed by the dialog.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Dialog type.
     */
    public DialogType getType() {
        return type;
    }

    /**
     * Dialog type.
     */
    public void setType(DialogType type) {
        this.type = type;
    }

    /**
     * True iff browser is capable showing or acting on the given dialog. When browser has no
     * dialog handler for given target, calling alert while Page domain is engaged will stall
     * the page execution. Execution can be resumed via calling Page.handleJavaScriptDialog.
     */
    public Boolean isHasBrowserHandler() {
        return hasBrowserHandler;
    }

    /**
     * True iff browser is capable showing or acting on the given dialog. When browser has no
     * dialog handler for given target, calling alert while Page domain is engaged will stall
     * the page execution. Execution can be resumed via calling Page.handleJavaScriptDialog.
     */
    public void setHasBrowserHandler(Boolean hasBrowserHandler) {
        this.hasBrowserHandler = hasBrowserHandler;
    }

    /**
     * Default dialog prompt.
     */
    public String getDefaultPrompt() {
        return defaultPrompt;
    }

    /**
     * Default dialog prompt.
     */
    public void setDefaultPrompt(String defaultPrompt) {
        this.defaultPrompt = defaultPrompt;
    }
}
