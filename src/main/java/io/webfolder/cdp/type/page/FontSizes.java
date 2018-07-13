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
package io.webfolder.cdp.type.page;

import io.webfolder.cdp.annotation.Experimental;

/**
 * Default font sizes
 */
@Experimental
public class FontSizes {
    private Integer standard;

    private Integer fixed;

    /**
     * Default standard font size.
     */
    public Integer getStandard() {
        return standard;
    }

    /**
     * Default standard font size.
     */
    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    /**
     * Default fixed font size.
     */
    public Integer getFixed() {
        return fixed;
    }

    /**
     * Default fixed font size.
     */
    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }
}
