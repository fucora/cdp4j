/**
 * cdp4j Commercial License
 *
 * Copyright 2017, 2019 WebFolder OÜ
 *
 * Permission  is hereby  granted,  to "____" obtaining  a  copy of  this software  and
 * associated  documentation files  (the "Software"), to deal in  the Software  without
 * restriction, including without limitation  the rights  to use, copy, modify,  merge,
 * publish, distribute  and sublicense  of the Software,  and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  IMPLIED,
 * INCLUDING  BUT NOT  LIMITED  TO THE  WARRANTIES  OF  MERCHANTABILITY, FITNESS  FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL  THE AUTHORS  OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.webfolder.cdp.event.debugger;

import com.vimeo.stag.UseStag;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.debugger.Location;

/**
 * Fired when breakpoint is resolved to an actual script and location
 */
@Domain("Debugger")
@EventName("breakpointResolved")
@UseStag
public class BreakpointResolved {
    private String breakpointId;

    private Location location;

    /**
     * Breakpoint unique identifier.
     */
    public String getBreakpointId() {
        return breakpointId;
    }

    /**
     * Breakpoint unique identifier.
     */
    public void setBreakpointId(String breakpointId) {
        this.breakpointId = breakpointId;
    }

    /**
     * Actual breakpoint location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Actual breakpoint location.
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
