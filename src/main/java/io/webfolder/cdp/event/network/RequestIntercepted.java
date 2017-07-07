/**
 * The MIT License
 * Copyright © 2017 WebFolder OÜ
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.webfolder.cdp.event.network;

import java.util.HashMap;
import java.util.Map;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.type.network.Request;

/**
 * Details of an intercepted HTTP request, which must be either allowed, blocked, modified or mocked
 */
@Experimental
@Domain("Network")
@EventName("requestIntercepted")
public class RequestIntercepted {
    private String interceptionId;

    private Request request;

    private Map<String, Object> redirectHeaders = new HashMap<>();

    private Integer redirectStatusCode;

    private String redirectUrl;

    /**
     * Each request the page makes will have a unique id, however if any redirects are encountered while processing that fetch, they will be reported with the same id as the original fetch.
     */
    public String getInterceptionId() {
        return interceptionId;
    }

    /**
     * Each request the page makes will have a unique id, however if any redirects are encountered while processing that fetch, they will be reported with the same id as the original fetch.
     */
    public void setInterceptionId(String interceptionId) {
        this.interceptionId = interceptionId;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
     * Allowed values: Document, Stylesheet, Image, Media, Font, Script, TextTrack, XHR, Fetch, EventSource, WebSocket, Manifest, Other.
     */
    public String getResourceType() {
      return resourceType;
    }

    /**
     * How the requested resource will be used.
     * Allowed values: Document, Stylesheet, Image, Media, Font, Script, TextTrack, XHR, Fetch, EventSource, WebSocket, Manifest, Other.
     */
    public void setResourceType(String resourceType) {
      this.resourceType = resourceType;
    }    

    /**
     * HTTP response headers, only sent if a redirect was intercepted.
     */
    public Map<String, Object> getRedirectHeaders() {
        return redirectHeaders;
    }

    /**
     * HTTP response headers, only sent if a redirect was intercepted.
     */
    public void setRedirectHeaders(Map<String, Object> redirectHeaders) {
        this.redirectHeaders = redirectHeaders;
    }

    /**
     * HTTP response code, only sent if a redirect was intercepted.
     */
    public Integer getRedirectStatusCode() {
        return redirectStatusCode;
    }

    /**
     * HTTP response code, only sent if a redirect was intercepted.
     */
    public void setRedirectStatusCode(Integer redirectStatusCode) {
        this.redirectStatusCode = redirectStatusCode;
    }

    /**
     * Redirect location, only sent if a redirect was intercepted.
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Redirect location, only sent if a redirect was intercepted.
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
