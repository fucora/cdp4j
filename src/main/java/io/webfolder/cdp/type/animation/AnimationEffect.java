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
package io.webfolder.cdp.type.animation;

/**
 * AnimationEffect instance
 */
public class AnimationEffect {
    private Double delay;

    private Double endDelay;

    private Double iterationStart;

    private Double iterations;

    private Double duration;

    private String direction;

    private String fill;

    private Integer backendNodeId;

    private KeyframesRule keyframesRule;

    private String easing;

    /**
     * `AnimationEffect`'s delay.
     */
    public Double getDelay() {
        return delay;
    }

    /**
     * `AnimationEffect`'s delay.
     */
    public void setDelay(Double delay) {
        this.delay = delay;
    }

    /**
     * `AnimationEffect`'s end delay.
     */
    public Double getEndDelay() {
        return endDelay;
    }

    /**
     * `AnimationEffect`'s end delay.
     */
    public void setEndDelay(Double endDelay) {
        this.endDelay = endDelay;
    }

    /**
     * `AnimationEffect`'s iteration start.
     */
    public Double getIterationStart() {
        return iterationStart;
    }

    /**
     * `AnimationEffect`'s iteration start.
     */
    public void setIterationStart(Double iterationStart) {
        this.iterationStart = iterationStart;
    }

    /**
     * `AnimationEffect`'s iterations.
     */
    public Double getIterations() {
        return iterations;
    }

    /**
     * `AnimationEffect`'s iterations.
     */
    public void setIterations(Double iterations) {
        this.iterations = iterations;
    }

    /**
     * `AnimationEffect`'s iteration duration.
     */
    public Double getDuration() {
        return duration;
    }

    /**
     * `AnimationEffect`'s iteration duration.
     */
    public void setDuration(Double duration) {
        this.duration = duration;
    }

    /**
     * `AnimationEffect`'s playback direction.
     */
    public String getDirection() {
        return direction;
    }

    /**
     * `AnimationEffect`'s playback direction.
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * `AnimationEffect`'s fill mode.
     */
    public String getFill() {
        return fill;
    }

    /**
     * `AnimationEffect`'s fill mode.
     */
    public void setFill(String fill) {
        this.fill = fill;
    }

    /**
     * `AnimationEffect`'s target node.
     */
    public Integer getBackendNodeId() {
        return backendNodeId;
    }

    /**
     * `AnimationEffect`'s target node.
     */
    public void setBackendNodeId(Integer backendNodeId) {
        this.backendNodeId = backendNodeId;
    }

    /**
     * `AnimationEffect`'s keyframes.
     */
    public KeyframesRule getKeyframesRule() {
        return keyframesRule;
    }

    /**
     * `AnimationEffect`'s keyframes.
     */
    public void setKeyframesRule(KeyframesRule keyframesRule) {
        this.keyframesRule = keyframesRule;
    }

    /**
     * `AnimationEffect`'s timing function.
     */
    public String getEasing() {
        return easing;
    }

    /**
     * `AnimationEffect`'s timing function.
     */
    public void setEasing(String easing) {
        this.easing = easing;
    }
}
