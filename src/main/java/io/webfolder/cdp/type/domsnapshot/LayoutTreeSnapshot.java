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
 * Details of an element in the DOM tree with a LayoutObject
 */
public class LayoutTreeSnapshot {
    private List<Integer> nodeIndex = new ArrayList<>();

    private List<List<Double>> bounds = new ArrayList<>();

    private List<Integer> text = new ArrayList<>();

    /**
     * The index of the related DOM node in the <code>domNodes</code>array returned by<code>getSnapshot</code>.
     */
    public List<Integer> getNodeIndex() {
        return nodeIndex;
    }

    /**
     * The index of the related DOM node in the <code>domNodes</code>array returned by<code>getSnapshot</code>.
     */
    public void setNodeIndex(List<Integer> nodeIndex) {
        this.nodeIndex = nodeIndex;
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
     * Contents of the LayoutText, if any.
     */
    public List<Integer> getText() {
        return text;
    }

    /**
     * Contents of the LayoutText, if any.
     */
    public void setText(List<Integer> text) {
        this.text = text;
    }
}
