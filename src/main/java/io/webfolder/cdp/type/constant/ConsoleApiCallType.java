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
package io.webfolder.cdp.type.constant;

import com.google.gson.annotations.SerializedName;
import com.vimeo.stag.UseStag;

@UseStag
public enum ConsoleApiCallType {
    @SerializedName("log")
    Log("log"),

    @SerializedName("debug")
    Debug("debug"),

    @SerializedName("info")
    Info("info"),

    @SerializedName("error")
    Error("error"),

    @SerializedName("warning")
    Warning("warning"),

    @SerializedName("dir")
    Dir("dir"),

    @SerializedName("dirxml")
    Dirxml("dirxml"),

    @SerializedName("table")
    Table("table"),

    @SerializedName("trace")
    Trace("trace"),

    @SerializedName("clear")
    Clear("clear"),

    @SerializedName("startGroup")
    StartGroup("startGroup"),

    @SerializedName("startGroupCollapsed")
    StartGroupCollapsed("startGroupCollapsed"),

    @SerializedName("endGroup")
    EndGroup("endGroup"),

    @SerializedName("assert")
    Assert("assert"),

    @SerializedName("profile")
    Profile("profile"),

    @SerializedName("profileEnd")
    ProfileEnd("profileEnd"),

    @SerializedName("count")
    Count("count"),

    @SerializedName("timeEnd")
    TimeEnd("timeEnd");

    public final String value;

    ConsoleApiCallType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
