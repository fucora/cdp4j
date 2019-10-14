package io.webfolder.cdp;

import static java.lang.System.getProperty;
import static java.util.Locale.ENGLISH;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeReflection;

import com.google.gson.TypeAdapterFactory;
import com.oracle.svm.core.annotate.AutomaticFeature;
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;
import com.vimeo.stag.generated.Stag;

import io.webfolder.cdp.channel.ChannelFactory;
import io.webfolder.cdp.channel.NvWebSocketFactory;
import io.webfolder.cdp.logger.CdpConsoleLogger;
import io.webfolder.cdp.logger.CdpConsoleLogggerLevel;
import io.webfolder.cdp.logger.CdpLogger;

//----------------------------------------------------------------------------
//
// STEP 1
//
// configure cdp4j Logger
//
// ----------------------------------------------------------------------------
@TargetClass(io.webfolder.cdp.logger.CdpLoggerFactory.class)
final class Target_io_webfolder_cdp_logger_CdpLoggerFactory {

    @Substitute
    public CdpLogger getLogger(String name, CdpConsoleLogggerLevel loggerLevel) {
        return new CdpConsoleLogger(loggerLevel);
    }
}

//----------------------------------------------------------------------------
//
// STEP 2
//
// configure ChannelFactory
//
//----------------------------------------------------------------------------
@TargetClass(io.webfolder.cdp.Launcher.class)
final class Target_io_webfolder_cdp_Launcher {

    @Substitute
    protected static ChannelFactory createChannelFactory() {
        return new NvWebSocketFactory();
    }
}

//----------------------------------------------------------------------------
//
// STEP 3
//
// configure ProcessManager
//
//----------------------------------------------------------------------------
@TargetClass(io.webfolder.cdp.AdaptiveProcessManager.class)
final class Target_io_webfolder_cdp_AdaptiveProcessManager {

    @Substitute
    private ProcessManager init() {
        return new LinuxProcessManager();
    }
}

//----------------------------------------------------------------------------
//
// Use gson TypeAdapterFactory
//
//----------------------------------------------------------------------------
@TargetClass(io.webfolder.cdp.session.SessionFactory.class)
final class Target_io_webfolder_cdp_session_SessionFactory {

    @Substitute
    private TypeAdapterFactory createTypeAdapterFactory() {
        return new Stag.Factory();
    }
}

//----------------------------------------------------------------------------
//
// Reflection support
//
//----------------------------------------------------------------------------
@AutomaticFeature
class JNIReflectionClasses implements Feature {

    private static final String  OS_NAME = getProperty("os.name").toLowerCase(ENGLISH);

    private static final boolean WINDOWS = OS_NAME.startsWith("windows");

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        if ( ! WINDOWS ) { // required by LinuxProcessManager and MacOsProcessManager
            try {
                Class<?> unixProcess = JNIReflectionClasses.class.getClassLoader().loadClass("java.lang.UNIXProcess");
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

public class Cdp4jSubstitutions {

}
