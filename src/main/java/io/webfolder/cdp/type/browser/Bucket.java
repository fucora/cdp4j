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
package io.webfolder.cdp.type.browser;

import io.webfolder.cdp.annotation.Experimental;

/**
 * Chrome histogram bucket
 */
@Experimental
public class Bucket {
    private Integer low;

    private Integer high;

    private Integer count;

    /**
     * Minimum value (inclusive).
     */
    public Integer getLow() {
        return low;
    }

    /**
     * Minimum value (inclusive).
     */
    public void setLow(Integer low) {
        this.low = low;
    }

    /**
     * Maximum value (exclusive).
     */
    public Integer getHigh() {
        return high;
    }

    /**
     * Maximum value (exclusive).
     */
    public void setHigh(Integer high) {
        this.high = high;
    }

    /**
     * Number of samples.
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Number of samples.
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}
