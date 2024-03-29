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
package io.webfolder.cdp.sample;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.channel.ChannelFactory;
import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class JreWebSocketConnection {

    public static void main(String[] args) {
        ChannelFactory jreWebSocketFactory = null;
        try {
            Class<?> klass = Launcher.class.getClassLoader().loadClass("io.webfolder.cdp.channel.JreWebSocketFactory");
            Constructor<?> constructor = klass.getConstructor();
            jreWebSocketFactory = (ChannelFactory) constructor.newInstance();
        } catch (ClassNotFoundException |
                InstantiationException | IllegalAccessException |
                NoSuchMethodException  | SecurityException |
                IllegalArgumentException | InvocationTargetException e) {
           throw new CdpException(e);
       }

        Launcher launcher = new Launcher(jreWebSocketFactory);

        try (SessionFactory factory = launcher.launch();
                            Session session = factory.create()) {
            session.navigate("https://webfolder.io?cdp4j");
            session.waitDocumentReady();
            String content = session.getContent();
            System.out.println(content);
        } finally {
            launcher.kill();
        }
    }
}
