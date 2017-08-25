/**
 * cdp4j - cdp4j - Chrome DevTools Protocol for Java
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
package io.webfolder.cdp.type.cachestorage;

/**
 * Data entry
 */
public class DataEntry {
    private String request;

    private String response;

    private Double responseTime;

    /**
     * Request url spec.
     */
    public String getRequest() {
        return request;
    }

    /**
     * Request url spec.
     */
    public void setRequest(String request) {
        this.request = request;
    }

    /**
     * Response status text.
     */
    public String getResponse() {
        return response;
    }

    /**
     * Response status text.
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * Number of seconds since epoch.
     */
    public Double getResponseTime() {
        return responseTime;
    }

    /**
     * Number of seconds since epoch.
     */
    public void setResponseTime(Double responseTime) {
        this.responseTime = responseTime;
    }
}
