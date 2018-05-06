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
package io.webfolder.cdp.type.accessibility;

import com.google.gson.annotations.SerializedName;

/**
 * Values of AXProperty name: from 'busy' to 'roledescription' - states which apply to every AX
 * node, from 'live' to 'root' - attributes which apply to nodes in live regions, from
 * 'autocomplete' to 'valuetext' - attributes which apply to widgets, from 'checked' to 'selected'
 * - states which apply to widgets, from 'activedescendant' to 'owns' - relationships between
 * elements other than parent/child/sibling
 */
public enum AXPropertyName {
    @SerializedName("busy")
    Busy("busy"),

    @SerializedName("disabled")
    Disabled("disabled"),

    @SerializedName("hidden")
    Hidden("hidden"),

    @SerializedName("hiddenRoot")
    HiddenRoot("hiddenRoot"),

    @SerializedName("invalid")
    Invalid("invalid"),

    @SerializedName("keyshortcuts")
    Keyshortcuts("keyshortcuts"),

    @SerializedName("roledescription")
    Roledescription("roledescription"),

    @SerializedName("live")
    Live("live"),

    @SerializedName("atomic")
    Atomic("atomic"),

    @SerializedName("relevant")
    Relevant("relevant"),

    @SerializedName("root")
    Root("root"),

    @SerializedName("autocomplete")
    Autocomplete("autocomplete"),

    @SerializedName("hasPopup")
    HasPopup("hasPopup"),

    @SerializedName("level")
    Level("level"),

    @SerializedName("multiselectable")
    Multiselectable("multiselectable"),

    @SerializedName("orientation")
    Orientation("orientation"),

    @SerializedName("multiline")
    Multiline("multiline"),

    @SerializedName("readonly")
    Readonly("readonly"),

    @SerializedName("required")
    Required("required"),

    @SerializedName("valuemin")
    Valuemin("valuemin"),

    @SerializedName("valuemax")
    Valuemax("valuemax"),

    @SerializedName("valuetext")
    Valuetext("valuetext"),

    @SerializedName("checked")
    Checked("checked"),

    @SerializedName("expanded")
    Expanded("expanded"),

    @SerializedName("modal")
    Modal("modal"),

    @SerializedName("pressed")
    Pressed("pressed"),

    @SerializedName("selected")
    Selected("selected"),

    @SerializedName("activedescendant")
    Activedescendant("activedescendant"),

    @SerializedName("controls")
    Controls("controls"),

    @SerializedName("describedby")
    Describedby("describedby"),

    @SerializedName("details")
    Details("details"),

    @SerializedName("errormessage")
    Errormessage("errormessage"),

    @SerializedName("flowto")
    Flowto("flowto"),

    @SerializedName("labelledby")
    Labelledby("labelledby"),

    @SerializedName("owns")
    Owns("owns");

    public final String value;

    AXPropertyName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
