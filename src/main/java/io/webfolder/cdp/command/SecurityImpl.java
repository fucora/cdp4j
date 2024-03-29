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
package io.webfolder.cdp.command;

import io.webfolder.cdp.session.SessionInvocationHandler;
import io.webfolder.cdp.command.Security;
import io.webfolder.cdp.type.security.CertificateErrorAction;

public class SecurityImpl implements Security {

    private static final String[] EMPTY_ARGS = new String[] {};
    private static final Object[] EMPTY_VALUES = new Object[] {};
    private final SessionInvocationHandler handler;

    public SecurityImpl(SessionInvocationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void disable() {
        handler.invoke("Security", "disable", "Security.disable", null, void.class, null, true, false, true, EMPTY_ARGS,
                EMPTY_VALUES);
    }

    @Override
    public void enable() {
        handler.invoke("Security", "enable", "Security.enable", null, void.class, null, true, true, false, EMPTY_ARGS,
                EMPTY_VALUES);
    }

    @Override
    public void handleCertificateError(Integer eventId, CertificateErrorAction action) {
        handler.invoke("Security", "handleCertificateError", "Security.handleCertificateError", null, void.class, null,
                true, false, false, new String[] { "eventId", "action" }, new Object[] { eventId, action });
    }

    @Override
    public void setIgnoreCertificateErrors(Boolean ignore) {
        handler.invoke("Security", "setIgnoreCertificateErrors", "Security.setIgnoreCertificateErrors", null,
                void.class, null, true, false, false, new String[] { "ignore" }, new Object[] { ignore });
    }

    @Override
    public void setOverrideCertificateErrors(Boolean override) {
        handler.invoke("Security", "setOverrideCertificateErrors", "Security.setOverrideCertificateErrors", null,
                void.class, null, true, false, false, new String[] { "override" }, new Object[] { override });
    }
}