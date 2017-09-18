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

import io.webfolder.cdp.event.target.DetachedFromTarget;

/**
 * Issued when detached from target for any reason (including
 * <code>detachFromTarget</code> command) Can be issued multiple times per
 * target if multiple sessions have been attached to it
 */
@FunctionalInterface
public interface TargetDetachedFromTargetListener extends AbstractListener<DetachedFromTarget> {

    void onEvent(DetachedFromTarget value);
}