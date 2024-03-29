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
package io.webfolder.cdp.type.network;

import com.google.gson.annotations.SerializedName;
import com.vimeo.stag.UseStag;

/**
 * Resource type as it was perceived by the rendering engine
 */
@UseStag
public enum ResourceType {
    @SerializedName("Document")
    Document("Document"),

    @SerializedName("Stylesheet")
    Stylesheet("Stylesheet"),

    @SerializedName("Image")
    Image("Image"),

    @SerializedName("Media")
    Media("Media"),

    @SerializedName("Font")
    Font("Font"),

    @SerializedName("Script")
    Script("Script"),

    @SerializedName("TextTrack")
    TextTrack("TextTrack"),

    @SerializedName("XHR")
    XHR("XHR"),

    @SerializedName("Fetch")
    Fetch("Fetch"),

    @SerializedName("EventSource")
    EventSource("EventSource"),

    @SerializedName("WebSocket")
    WebSocket("WebSocket"),

    @SerializedName("Manifest")
    Manifest("Manifest"),

    @SerializedName("SignedExchange")
    SignedExchange("SignedExchange"),

    @SerializedName("Ping")
    Ping("Ping"),

    @SerializedName("CSPViolationReport")
    CSPViolationReport("CSPViolationReport"),

    @SerializedName("Other")
    Other("Other");

    public final String value;

    ResourceType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
