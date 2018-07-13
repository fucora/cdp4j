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
package io.webfolder.cdp.type.domsnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Details of post layout rendered text positions
 * The exact layout should not be regarded as
 * stable and may change between versions
 */
public class TextBoxSnapshot {
    private List<Integer> layoutIndex = new ArrayList<>();

    private List<List<Double>> bounds = new ArrayList<>();

    private List<Integer> start = new ArrayList<>();

    private List<Integer> length = new ArrayList<>();

    /**
     * Intex of th elayout tree node that owns this box collection.
     */
    public List<Integer> getLayoutIndex() {
        return layoutIndex;
    }

    /**
     * Intex of th elayout tree node that owns this box collection.
     */
    public void setLayoutIndex(List<Integer> layoutIndex) {
        this.layoutIndex = layoutIndex;
    }

    /**
     * The absolute position bounding box.
     */
    public List<List<Double>> getBounds() {
        return bounds;
    }

    /**
     * The absolute position bounding box.
     */
    public void setBounds(List<List<Double>> bounds) {
        this.bounds = bounds;
    }

    /**
     * The starting index in characters, for this post layout textbox substring. Characters that
     * would be represented as a surrogate pair in UTF-16 have length 2.
     */
    public List<Integer> getStart() {
        return start;
    }

    /**
     * The starting index in characters, for this post layout textbox substring. Characters that
     * would be represented as a surrogate pair in UTF-16 have length 2.
     */
    public void setStart(List<Integer> start) {
        this.start = start;
    }

    /**
     * The number of characters in this post layout textbox substring. Characters that would be
     * represented as a surrogate pair in UTF-16 have length 2.
     */
    public List<Integer> getLength() {
        return length;
    }

    /**
     * The number of characters in this post layout textbox substring. Characters that would be
     * represented as a surrogate pair in UTF-16 have length 2.
     */
    public void setLength(List<Integer> length) {
        this.length = length;
    }
}
