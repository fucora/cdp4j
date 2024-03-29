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
package io.webfolder.cdp.session;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.locks.LockSupport.parkUntil;
import static java.util.concurrent.locks.LockSupport.unpark;

import com.google.gson.JsonElement;

import io.webfolder.cdp.exception.CommandException;

class ThreadContext implements Context {

    private final Thread thread;

    private JsonElement data;

    private CommandException error;

    public ThreadContext() {
        thread = currentThread();
    }

    @Override
    public void await(final int timeout) {
        parkUntil(currentTimeMillis() + timeout);
    }

    @Override
    public void setData(final JsonElement data) {
        this.data = data;
    }

    @Override
    public JsonElement getData() {
        return data;
    }

    @Override
    public void setError(final CommandException error) {
        this.error = error;
    }

    @Override
    public CommandException getError() {
        return error;
    }

    @Override
    public void release() {
        unpark(thread);
    }
}
