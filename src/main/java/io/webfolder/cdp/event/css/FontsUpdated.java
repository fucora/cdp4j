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
package io.webfolder.cdp.event.css;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.css.FontFace;

/**
 * Fires whenever a web font is updated
 * A non-empty font parameter indicates a successfully loaded
 * web font
 */
@Domain("CSS")
@EventName("fontsUpdated")
public class FontsUpdated {
    private FontFace font;

    /**
     * The web font that has loaded.
     */
    public FontFace getFont() {
        return font;
    }

    /**
     * The web font that has loaded.
     */
    public void setFont(FontFace font) {
        this.font = font;
    }
}
