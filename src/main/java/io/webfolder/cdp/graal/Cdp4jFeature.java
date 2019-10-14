package io.webfolder.cdp.graal;

import static java.io.File.pathSeparator;
import static java.lang.System.getProperty;
import static java.util.Locale.ENGLISH;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeReflection;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

import io.webfolder.cdp.LinuxProcessManager;
import io.webfolder.cdp.MacOsProcessManager;
import io.webfolder.cdp.ProcessManager;
import io.webfolder.cdp.TaskKillProcessManager;
import io.webfolder.cdp.channel.ChannelFactory;
import io.webfolder.cdp.exception.CdpException;

final class Constants {
    static final String  OS      = getProperty("os.name").toLowerCase(ENGLISH);
    static final boolean WINDOWS = ";".equals(pathSeparator);
    static final boolean LINUX   = "linux".contains(OS);
    static final boolean MAC     = OS.contains("mac");
    static final boolean JAVA_8  = getProperty("java.version").startsWith("1.8.");
}

@TargetClass(className = "io.webfolder.cdp.session.CdpTypeAdapterFactory")
final class Target_io_webfolder_cdp_session_CdpTypeAdapterFactory {

    @Substitute
    @SuppressWarnings("rawtypes")
    public TypeAdapter create(Gson gson, TypeToken type) {
        return null;
    }
}

@TargetClass(io.webfolder.cdp.Launcher.class)
final class Target_io_webfolder_cdp_Launcher {

    @Substitute
    protected static ChannelFactory createChannelFactory() {
        return null;
    }
}

@TargetClass(io.webfolder.cdp.AdaptiveProcessManager.class)
final class Target_io_webfolder_cdp_AdaptiveProcessManager {

    @Substitute
    private ProcessManager init() {
        if (Constants.WINDOWS) {
            return new TaskKillProcessManager();
        } else if (Constants.LINUX) {
            return new LinuxProcessManager();
        } else if (Constants.MAC) {
            return new MacOsProcessManager();            
        } else {
            throw new CdpException(Constants.OS + " is not supported by AdaptiveProcessManager");
        }
    }
}

public final class Cdp4jFeature implements Feature {

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        if ( Constants.JAVA_8 && ! Constants.WINDOWS ) { // required by LinuxProcessManager and MacOsProcessManager
            try {
                Class<?> unixProcess = Cdp4jFeature.class.getClassLoader().loadClass("java.lang.UNIXProcess");
                Field pid = unixProcess.getDeclaredField("pid");
                RuntimeReflection.register(pid);
                Method destroyProcess = unixProcess.getDeclaredMethod("destroyProcess",
                                        int.class,
                                        boolean.class);
                RuntimeReflection.register(destroyProcess);
            } catch (ClassNotFoundException | NoSuchFieldException |
                    SecurityException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
