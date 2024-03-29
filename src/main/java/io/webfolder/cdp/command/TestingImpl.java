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
import io.webfolder.cdp.command.Testing;

public class TestingImpl implements Testing {

    private final SessionInvocationHandler handler;

    public TestingImpl(SessionInvocationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void generateTestReport(String message) {
        handler.invoke("Testing", "generateTestReport", "Testing.generateTestReport", null, void.class, null, true,
                false, false, new String[] { "message" }, new Object[] { message });
    }

    @Override
    public void generateTestReport(String message, String group) {
        handler.invoke("Testing", "generateTestReport", "Testing.generateTestReport", null, void.class, null, true,
                false, false, new String[] { "message", "group" }, new Object[] { message, group });
    }
}