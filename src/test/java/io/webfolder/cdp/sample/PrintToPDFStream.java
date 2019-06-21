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

import static io.webfolder.cdp.type.constant.PdfTransferMode.ReturnAsStream;
import static java.awt.Desktop.getDesktop;
import static java.awt.Desktop.isDesktopSupported;
import static java.nio.file.Files.createTempFile;
import static java.util.Arrays.asList;
import static java.util.Base64.getDecoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import io.webfolder.cdp.AdaptiveProcessManager;
import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.command.IO;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.type.io.ReadResult;
import io.webfolder.cdp.type.page.PrintToPDFResult;

public class PrintToPDFStream {

    // Requires Headless Chrome
    // https://chromium.googlesource.com/chromium/src/+/lkgr/headless/README.md
    public static void main(String[] args) throws IOException {
        Launcher launcher = new Launcher();
        launcher.setProcessManager(new AdaptiveProcessManager());

        Path file = createTempFile("cdp4j", ".pdf");

        try (SessionFactory factory = launcher.launch(asList("--disable-gpu", "--headless"))) {

            String context = factory.createBrowserContext();
            try (Session session = factory.create(context)) {

                session.navigate("https://docs.jboss.org/resteasy/docs/4.0.0.Final/userguide/html_single/index.html");
                session.waitDocumentReady();

                PrintToPDFResult pdfResult = session
		                                    .getCommand()
		                                    .getPage()
		                                    .printToPDF(null, null,
		                                    			null, null,
		                                    			null, null,
		                                    			null, null,
		                                    			null, null,
		                                    			null, null,
		                                    			null, null,
		                                    			null, ReturnAsStream);

                IO io = session.getCommand().getIO();
                String stream = pdfResult.getStream();

                boolean eof = false;
                try {
                	while ( ! eof ) {
                		ReadResult streamResult = io.read(stream);
                		eof = streamResult.getEof();
                		if (streamResult.getBase64Encoded()) {
                			if ( streamResult.getData() != null &&
                					! streamResult.getData().isEmpty() ) {
                				byte[] content = getDecoder().decode(streamResult.getData());
                				Files.write(file, content, StandardOpenOption.APPEND);
                			}
                		}
                	}
                } finally {
                    io.close(stream);
                }
            }

            factory.disposeBrowserContext(context);
        }

        if (isDesktopSupported()) {
            getDesktop().open(file.toFile());
        }

        launcher.getProcessManager().kill();
    }
}
