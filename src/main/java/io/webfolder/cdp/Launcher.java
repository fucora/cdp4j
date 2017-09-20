/**
 * cpd4j - Chrome DevTools Protocol for Java
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
package io.webfolder.cdp;

import static io.webfolder.cdp.session.SessionFactory.DEFAULT_HOST;
import static java.lang.Runtime.getRuntime;
import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.lang.Thread.sleep;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Locale.ENGLISH;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.session.SessionInfo;

public class Launcher {

    private final SessionFactory factory;

    private static final String  OS       = getProperty("os.name").toLowerCase(ENGLISH);

    private static final boolean WINDOWS  = OS.startsWith("windows");

    private static final boolean OSX      = OS.startsWith("mac");

    private ProcessManager processManager = new ProcessManager() { };

    public Launcher() {
        this(new SessionFactory());
    }

    public Launcher(int port) {
      this(new SessionFactory(port));
    }

    public Launcher(final SessionFactory factory) {
        this.factory = factory;
    }

    private String getCustomChromeBinary() {
        String chromeBinary = getProperty("chrome_binary");
        if (chromeBinary != null) {
            File chromeExecutable = new File(chromeBinary);
            if (chromeExecutable.exists() && chromeExecutable.canExecute()) {
                return chromeExecutable.getAbsolutePath();
            }
        }
        return null;
    }

    public String findChrome() {
        String chromePath = null;
        chromePath = getCustomChromeBinary();
        if (chromePath == null && WINDOWS) {
          chromePath = findChromeWinPath();
        }
        if (chromePath == null && OSX) {
          chromePath = findChromeOsxPath();
        }
        if ( chromePath == null && ! WINDOWS ) {
          chromePath = "google-chrome";
        }
        return chromePath;
      }

    public String findChromeWinPath() {
      try {
          for (String path : getChromeWinPaths()) {
              final Process process = getRuntime().exec(new String[] {
                      "cmd", "/c", "echo", path
              });
              final int exitCode = process.waitFor();
              if (exitCode == 0) {
                  final String location = toString(process.getInputStream()).trim().replace("\"", "");
                  final File chrome = new File(location);
                  if (chrome.exists() && chrome.canExecute()) {
                      return chrome.toString();
                  }
              }
          }
          throw new CdpException("Unable to find chrome.exe");
      } catch (Throwable e) {
          // ignore
      }
      return null;
    }

    protected List<String> getChromeWinPaths() {
        return asList(
                "%localappdata%\\Google\\Chrome SxS\\Application\\chrome.exe", // Chrome Canary
                "%programfiles%\\Google\\Chrome\\Application\\chrome.exe",     // Chrome Stable 64-bit
                "%programfiles(x86)%\\Google\\Chrome\\Application\\chrome.exe" // Chrome Stable 32-bit
        );
    }

    public String findChromeOsxPath() {
        for (String path : getChromeOsxPaths()) {
            final File chrome = new File(path);
            if (chrome.exists() && chrome.canExecute()) {
                return chrome.toString();
            }
        }
        return null;
    }

    protected List<String> getChromeOsxPaths() {
        return asList(
                "/Applications/Google Chrome Canary.app/Contents/MacOS/Google Chrome Canary", // Chrome Canary
                "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"                // Chrome Stable
        );
    }

    public SessionFactory launch() {
        return launch(findChrome(), new ArrayList<String>());
    }

    public SessionFactory launch(List<String> arguments) {
        return launch(findChrome(), arguments);
    }

    /**
     *
     * @deprecated As of release 1.1.0, replaced by {@link #launch(String, List)}
     */
    @Deprecated
    public SessionFactory launch(String chromePath, String... arguments) {
        return launch(findChrome(), asList(arguments));
    }

    public SessionFactory launch(String chromePath, List<String> arguments) {
        if (launched()) {
            return factory;
        }

        if (chromePath == null || chromePath.trim().isEmpty()) {
            throw new CdpException("chrome not found");
        }
        Path remoteProfileData = get(getProperty("java.io.tmpdir"))
                                        .resolve("remote-profile");
        List<String> args = new ArrayList<>();
        args.add(chromePath);
        args.add(format("--remote-debugging-port=%d", factory.getPort()));
        args.add(format("--user-data-dir=%s", remoteProfileData.toString()));
        if ( ! DEFAULT_HOST.equals(factory.getHost()) ) {
            args.add(format("--remote-debugging-address=%s", factory.getHost()));
        }
        // Disable built-in Google Translate service
        args.add("--disable-translate");
        // Disable all chrome extensions entirely
        args.add("--disable-extensions");
        // Disable various background network services, including extension updating,
        // safe browsing service, upgrade detector, translate, UMA
        args.add("--disable-background-networking");
        // Disable fetching safebrowsing lists, likely redundant due to disable-background-networking
        args.add("--safebrowsing-disable-auto-update");
        // Disable syncing to a Google account
        args.add("--disable-sync");
        // Disable reporting to UMA, but allows for collection
        args.add("--metrics-recording-only");
        // Disable installation of default apps on first run
        args.add("--disable-default-apps");
        // Mute any audio
        args.add("--mute-audio");
        // Skip first run wizards
        args.add("--no-first-run");
        args.add("--no-default-browser-check");
        args.add("--disable-plugin-power-saver");
        args.add("--disable-popup-blocking");

        if ( ! arguments.isEmpty() ) {
            args.addAll(arguments);
        }

        try {
            Process process = getRuntime().exec(args.toArray(new String[0]));

            process.getOutputStream().close();
            process.getInputStream().close();

            if ( ! process.isAlive() ) {
                throw new CdpException("No process: the chrome process is not alive.");
            }

            processManager.onStart(process, args);
        } catch (IOException e) {
            throw new CdpException(e);
        }

        if ( ! launched() ) {
            int       counter  =  0;
            final int maxCount = 20;
            while ( ! launched() && counter < maxCount ) {
                try {
                    sleep(500);
                    counter += 1;
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        if ( ! launched() ) {
            throw new CdpException("Unable to connect to the chrome remote debugging server [" +
                            factory.getHost() + ":" + factory.getPort() + "]");
        }

        return factory;
    }

    protected String toString(InputStream is) {
        try (Scanner scanner = new Scanner(is)) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    public boolean launched() {
        List<SessionInfo> list = emptyList();
        try {
            int timeout = 1000; // milliseconds
            list = factory.list(timeout);
        } catch (Throwable t) {
            // ignore
        }
        return ! list.isEmpty() ? true : false;
    }

    public void setProcessManager(ProcessManager processManager) {
        this.processManager = processManager;
    }

    public ProcessManager getProcessManager() {
        return processManager;
    }
}
