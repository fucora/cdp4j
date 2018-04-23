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
import io.webfolder.cdp.annotation.Experimental;

/**
 * Fired when same-document navigation happens, e
 * g
 * due to history API usage or anchor navigation
 */
@Experimental
@Domain("Page")
@EventName("navigatedWithinDocument")
public class NavigatedWithinDocument {
    private String frameId;

    private String url;

    /**
     * Id of the frame.
     */
    public String getFrameId() {
        return frameId;
    }

    /**
     * Id of the frame.
     */
    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    /**
     * Frame's new url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Frame's new url.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
