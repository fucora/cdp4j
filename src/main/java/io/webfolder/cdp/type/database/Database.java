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
package io.webfolder.cdp.type.database;

import com.vimeo.stag.UseStag;

/**
 * Database object
 */
@UseStag
public class Database {
    private String id;

    private String domain;

    private String name;

    private String version;

    /**
     * Database ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Database ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Database domain.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Database domain.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Database name.
     */
    public String getName() {
        return name;
    }

    /**
     * Database name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Database version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Database version.
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
