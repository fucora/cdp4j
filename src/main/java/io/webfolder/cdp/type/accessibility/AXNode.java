/**
 * cdp4j - Chrome DevTools Protocol for Java
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
package io.webfolder.cdp.type.accessibility;

import java.util.ArrayList;
import java.util.List;

/**
 * A node in the accessibility tree
 */
public class AXNode {
    private String nodeId;

    private Boolean ignored;

    private List<AXProperty> ignoredReasons = new ArrayList<>();

    private AXValue role;

    private AXValue name;

    private AXValue description;

    private AXValue value;

    private List<AXProperty> properties = new ArrayList<>();

    private Integer backendDOMNodeId;

    /**
     * Unique identifier for this node.
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Unique identifier for this node.
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * Whether this node is ignored for accessibility
     */
    public Boolean isIgnored() {
        return ignored;
    }

    /**
     * Whether this node is ignored for accessibility
     */
    public void setIgnored(Boolean ignored) {
        this.ignored = ignored;
    }

    /**
     * Collection of reasons why this node is hidden.
     */
    public List<AXProperty> getIgnoredReasons() {
        return ignoredReasons;
    }

    /**
     * Collection of reasons why this node is hidden.
     */
    public void setIgnoredReasons(List<AXProperty> ignoredReasons) {
        this.ignoredReasons = ignoredReasons;
    }

    /**
     * This `Node`'s role, whether explicit or implicit.
     */
    public AXValue getRole() {
        return role;
    }

    /**
     * This `Node`'s role, whether explicit or implicit.
     */
    public void setRole(AXValue role) {
        this.role = role;
    }

    /**
     * The accessible name for this `Node`.
     */
    public AXValue getName() {
        return name;
    }

    /**
     * The accessible name for this `Node`.
     */
    public void setName(AXValue name) {
        this.name = name;
    }

    /**
     * The accessible description for this `Node`.
     */
    public AXValue getDescription() {
        return description;
    }

    /**
     * The accessible description for this `Node`.
     */
    public void setDescription(AXValue description) {
        this.description = description;
    }

    /**
     * The value for this `Node`.
     */
    public AXValue getValue() {
        return value;
    }

    /**
     * The value for this `Node`.
     */
    public void setValue(AXValue value) {
        this.value = value;
    }

    /**
     * All other properties
     */
    public List<AXProperty> getProperties() {
        return properties;
    }

    /**
     * All other properties
     */
    public void setProperties(List<AXProperty> properties) {
        this.properties = properties;
    }

    /**
     * The backend ID for the associated DOM node, if any.
     */
    public Integer getBackendDOMNodeId() {
        return backendDOMNodeId;
    }

    /**
     * The backend ID for the associated DOM node, if any.
     */
    public void setBackendDOMNodeId(Integer backendDOMNodeId) {
        this.backendDOMNodeId = backendDOMNodeId;
    }
}
