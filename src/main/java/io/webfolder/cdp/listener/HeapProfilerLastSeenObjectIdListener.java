/**
 * cpd4j - Chrome DevTools Protocol for Java
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
package io.webfolder.cdp.listener;

import io.webfolder.cdp.event.heapprofiler.LastSeenObjectId;

/**
 * If heap objects tracking has been started then backend regularly sends a
 * current value for last seen object id and corresponding timestamp If the
 * were changes in the heap since last event then one or more
 * heapStatsUpdate events will be sent before a new lastSeenObjectId event
 */
@FunctionalInterface
public interface HeapProfilerLastSeenObjectIdListener extends AbstractListener<LastSeenObjectId> {

    void onEvent(LastSeenObjectId value);
}