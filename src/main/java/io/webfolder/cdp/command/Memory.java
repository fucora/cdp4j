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
import io.webfolder.cdp.annotation.Returns;
import io.webfolder.cdp.type.memory.GetDOMCountersResult;
import io.webfolder.cdp.type.memory.PressureLevel;
import io.webfolder.cdp.type.memory.SamplingProfile;

@Experimental
@Domain("Memory")
public interface Memory {
    /**
     * 
     * @return GetDOMCountersResult
     */
    GetDOMCountersResult getDOMCounters();

    void prepareForLeakDetection();

    /**
     * Enable/disable suppressing memory pressure notifications in all processes.
     * 
     * @param suppressed If true, memory pressure notifications will be suppressed.
     */
    void setPressureNotificationsSuppressed(Boolean suppressed);

    /**
     * Simulate a memory pressure notification in all processes.
     * 
     * @param level Memory pressure level of the notification.
     */
    void simulatePressureNotification(PressureLevel level);

    /**
     * Start collecting native memory profile.
     * 
     * @param samplingInterval Average number of bytes between samples.
     * @param suppressRandomness Do not randomize intervals between samples.
     */
    void startSampling(@Optional Integer samplingInterval, @Optional Boolean suppressRandomness);

    /**
     * Stop collecting native memory profile.
     */
    void stopSampling();

    /**
     * Retrieve native memory allocations profile
     * collected since renderer process startup.
     */
    @Returns("profile")
    SamplingProfile getAllTimeSamplingProfile();

    /**
     * Retrieve native memory allocations profile
     * collected since browser process startup.
     */
    @Returns("profile")
    SamplingProfile getBrowserSamplingProfile();

    /**
     * Retrieve native memory allocations profile collected since last
     * <code>startSampling</code> call.
     */
    @Returns("profile")
    SamplingProfile getSamplingProfile();

    /**
     * Start collecting native memory profile.
     */
    void startSampling();
}
