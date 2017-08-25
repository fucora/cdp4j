/**
 * cdp4j - cdp4j - Chrome DevTools Protocol for Java
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
package io.webfolder.cdp.type.input;

import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.type.constant.TouchPointState;

@Experimental
public class TouchPoint {
    private TouchPointState state;

    private Integer x;

    private Integer y;

    private Integer radiusX;

    private Integer radiusY;

    private Double rotationAngle;

    private Double force;

    private Double id;

    /**
     * State of the touch point.
     */
    public TouchPointState getState() {
        return state;
    }

    /**
     * State of the touch point.
     */
    public void setState(TouchPointState state) {
        this.state = state;
    }

    /**
     * X coordinate of the event relative to the main frame's viewport.
     */
    public Integer getX() {
        return x;
    }

    /**
     * X coordinate of the event relative to the main frame's viewport.
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * Y coordinate of the event relative to the main frame's viewport. 0 refers to the top of the viewport and Y increases as it proceeds towards the bottom of the viewport.
     */
    public Integer getY() {
        return y;
    }

    /**
     * Y coordinate of the event relative to the main frame's viewport. 0 refers to the top of the viewport and Y increases as it proceeds towards the bottom of the viewport.
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * X radius of the touch area (default: 1).
     */
    public Integer getRadiusX() {
        return radiusX;
    }

    /**
     * X radius of the touch area (default: 1).
     */
    public void setRadiusX(Integer radiusX) {
        this.radiusX = radiusX;
    }

    /**
     * Y radius of the touch area (default: 1).
     */
    public Integer getRadiusY() {
        return radiusY;
    }

    /**
     * Y radius of the touch area (default: 1).
     */
    public void setRadiusY(Integer radiusY) {
        this.radiusY = radiusY;
    }

    /**
     * Rotation angle (default: 0.0).
     */
    public Double getRotationAngle() {
        return rotationAngle;
    }

    /**
     * Rotation angle (default: 0.0).
     */
    public void setRotationAngle(Double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    /**
     * Force (default: 1.0).
     */
    public Double getForce() {
        return force;
    }

    /**
     * Force (default: 1.0).
     */
    public void setForce(Double force) {
        this.force = force;
    }

    /**
     * Identifier used to track touch sources between events, must be unique within an event.
     */
    public Double getId() {
        return id;
    }

    /**
     * Identifier used to track touch sources between events, must be unique within an event.
     */
    public void setId(Double id) {
        this.id = id;
    }
}
