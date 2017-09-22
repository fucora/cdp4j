package io.webfolder.cdp;

import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isReadable;
import static java.nio.file.Files.isRegularFile;
import static java.nio.file.Files.list;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.size;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class LinuxProcessManager extends ProcessManager {

    private int pid;

    private String cdp4jId;

    @Override
    void setProcess(Process process) {
        try {
            Field pidField = process.getClass().getDeclaredField("pid");
            pidField.setAccessible(true);
            this.pid = (int) pidField.get(process);
        } catch (Throwable e) {
            // ignored
        }
        try {
            Path envFile = get("/proc").resolve(valueOf(pid));
            Map<String, String> environment = readEnvironmentVariables(envFile);
            cdp4jId = environment.get("CDP4J_ID");
        } catch (IOException e) {
            // ignored
        }
    }

    @Override
    public void kill() {
        if (pid == 0 ||
                cdp4jId == null ||
                cdp4jId.trim().isEmpty()) {
            return;
        }
        try {
            Path envFile = get("/proc").resolve(valueOf(pid));
            Map<String, String> environment = readEnvironmentVariables(envFile);
            if ( ! cdp4jId.equals(environment.get("CDP4J_ID")) ) {
                return;
            }
        } catch (IOException e) {
            // ignored
        }
        for (Integer next : children(cdp4jId)) {
            kill(next.intValue(), this.cdp4jId);
        }
        kill(pid, this.cdp4jId);
    }

    protected void kill(int pid, String cdp4jId) {
        Path envFile = get("/proc").resolve(valueOf(pid));
        try {
            Map<String, String> environment = readEnvironmentVariables(envFile);
            if ( ! cdp4jId.equals(environment.get("CDP4J_ID")) ) {
                return;
            }
        } catch (IOException e) {
            return;
        }
        try {
            Class<?> clazz = Class.forName("java.lang.UNIXProcess");
            Method destroyProcess = clazz.getDeclaredMethod("destroyProcess", int.class, boolean.class);
            destroyProcess.setAccessible(true);
            boolean force = false;
            destroyProcess.invoke(null, pid, force);
        } catch (Throwable e) {
            // ignored
        }
    }

    protected List<Integer> children(String cdp4jId) {
        List<Integer> children = new ArrayList<>();
        try (Stream<Path> stream = list(get("/proc"))) {
            List<Path> directories = stream
                                        .filter(p -> isDirectory(p))
                                        .collect(toList());
            for (Path next : directories) {
                String path = next.getFileName().toString();
                // process only pid directories
                if ( ! isDigit(path.charAt(0)) ) {
                    continue;
                }
                Path environ = next.resolve("environ");
                Map<String, String> environment = readEnvironmentVariables(environ);
                String childCdp4jId = environment.get("CDP4J_ID");
                if (cdp4jId.equals(childCdp4jId)) {
                    children.add(new Integer(parseInt(next.getFileName().toString())));
                }
            }
        } catch (Throwable e) {
            // ignored
        }
        return children;
    }
    
    protected Map<String, String> readEnvironmentVariables(final Path environ) throws IOException {
        Map<String, String> environment = new LinkedHashMap<>();
        if (isReadable(environ) &&
                isRegularFile(environ) &&
                size(environ) <= 16 * 4096) {
            List<String> lines = readAllLines(environ);
            for (String line : lines) {
                String content = line.replace('\0', '\n');
                try (Scanner scanner = new Scanner(content)) {
                    while (scanner.hasNext()) {
                        String next = scanner.next();
                        String[] arr = next.split("=");
                        if (arr.length == 2) {
                            environment.put(arr[0], arr[1]);
                        }
                    }
                }
            }
        }
        return environment;
    }
}
