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
package io.webfolder.cdp.command;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.annotation.Optional;
import io.webfolder.cdp.type.headlessexperimental.BeginFrameResult;
import io.webfolder.cdp.type.headlessexperimental.ScreenshotParams;

/**
 * This domain provides experimental commands only supported in headless mode
 */
@Experimental
@Domain("HeadlessExperimental")
public interface HeadlessExperimental {
    /**
     * Sends a BeginFrame to the target and returns when the frame was completed. Optionally captures a
     * screenshot from the resulting frame. Requires that the target was created with enabled
     * BeginFrameControl. Designed for use with --run-all-compositor-stages-before-draw, see also
     * https://goo.gl/3zHXhB for more background.
     * 
     * @param frameTime Timestamp of this BeginFrame (milliseconds since epoch). If not set, the current time will
     * be used unless frameTicks is specified.
     * @param frameTimeTicks Timestamp of this BeginFrame in Renderer TimeTicks (milliseconds of uptime). If not set,
     * the current time will be used unless frameTime is specified.
     * @param deadline Deadline of this BeginFrame (milliseconds since epoch). If not set, the deadline will be
     * calculated from the frameTime and interval unless deadlineTicks is specified.
     * @param deadlineTicks Deadline of this BeginFrame in Renderer TimeTicks  (milliseconds of uptime). If not set,
     * the deadline will be calculated from the frameTime and interval unless deadline is specified.
     * @param interval The interval between BeginFrames that is reported to the compositor, in milliseconds.
     * Defaults to a 60 frames/second interval, i.e. about 16.666 milliseconds.
     * @param noDisplayUpdates Whether updates should not be committed and drawn onto the display. False by default. If
     * true, only side effects of the BeginFrame will be run, such as layout and animations, but
     * any visual updates may not be visible on the display or in screenshots.
     * @param screenshot If set, a screenshot of the frame will be captured and returned in the response. Otherwise,
     * no screenshot will be captured. Note that capturing a screenshot can fail, for example,
     * during renderer initialization. In such a case, no screenshot data will be returned.
     * 
     * @return BeginFrameResult
     */
    BeginFrameResult beginFrame(@Optional Double frameTime, @Optional Double frameTimeTicks,
            @Optional Double deadline, @Optional Double deadlineTicks, @Optional Double interval,
            @Optional Boolean noDisplayUpdates, @Optional ScreenshotParams screenshot);

    /**
     * Puts the browser into deterministic mode.  Only effective for subsequently created web contents.
     * Only supported in headless mode.  Once set there's no way of leaving deterministic mode.
     * 
     * @param initialDate Number of seconds since the Epoch
     */
    void enterDeterministicMode(@Optional Double initialDate);

    /**
     * Disables headless events for the target.
     */
    void disable();

    /**
     * Enables headless events for the target.
     */
    void enable();

    /**
     * Sends a BeginFrame to the target and returns when the frame was completed. Optionally captures a
     * screenshot from the resulting frame. Requires that the target was created with enabled
     * BeginFrameControl. Designed for use with --run-all-compositor-stages-before-draw, see also
     * https://goo.gl/3zHXhB for more background.
     * 
     * @return BeginFrameResult
     */
    BeginFrameResult beginFrame();

    /**
     * Puts the browser into deterministic mode.  Only effective for subsequently created web contents.
     * Only supported in headless mode.  Once set there's no way of leaving deterministic mode.
     */
    void enterDeterministicMode();
}
