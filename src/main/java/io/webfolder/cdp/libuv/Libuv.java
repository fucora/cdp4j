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
package io.webfolder.cdp.libuv;

import static java.io.File.pathSeparator;
import static java.io.File.separator;
import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.graalvm.nativeimage.UnmanagedMemory.malloc;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.UnmanagedMemory;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.function.CLibrary;
import org.graalvm.nativeimage.c.struct.CField;
import org.graalvm.nativeimage.c.struct.CFieldAddress;
import org.graalvm.nativeimage.c.struct.CStruct;
import org.graalvm.nativeimage.c.struct.SizeOf;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CCharPointerPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.word.PointerBase;
import org.graalvm.word.UnsignedWord;
import org.graalvm.word.WordFactory;

@CContext(Libuv.UDirectives.class)
@CLibrary("cdp4j")
class Libuv {

    static final boolean WINDOWS = ";".equals(pathSeparator);

    static class UDirectives implements CContext.Directives {

        private String getVcpkgRoot() {
            if (WINDOWS) {
                return getProperty("cdp4j.vcpkg.root",
                        format("C:\\tools\\vcpkg"));
            } else {
                return getProperty("cdp4j.vcpkg.root",
                        format("%s%s%s", getProperty("user.home"), separator, "vcpkg"));
            }
        }

        private Path getUvCdp4jPath() {
            String path = Libuv.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = WINDOWS ? path.substring(1) : path;
            return get(path)
                       .getParent()
                       .getParent()
                       .resolve("src")
                       .resolve("main")
                       .resolve("native");
        }
        public List<String> getOptions() {
            Path include = get(getVcpkgRoot())
                                .resolve("installed")
                                .resolve(WINDOWS ? "x64-windows-static" : "x64-linux")
                                .resolve("include");
            System.out.println("[cdp4j] vcpkg include path: " + include.toString());
            String uvCdp4jIncludePath = getUvCdp4jPath().toString();
            System.out.println("[cdp4j] uv-cdp4j include path: " + uvCdp4jIncludePath);
            String libCdp4jStaticPath = getUvCdp4jPath()
                                            .resolve("build")
                                            .resolve(WINDOWS ? "cdp4j.lib" : "libcdp4j.a")
                                            .toString();
            System.out.println("[cdp4j] libcdp4j path: " + libCdp4jStaticPath);
            List<String> options = new ArrayList<>(asList(libCdp4jStaticPath,
                                            format("-I%s",
                                                include.toAbsolutePath().toString()),
                                            format("-I%s",
                                                  uvCdp4jIncludePath)));
            return options;
        }

        public List<String> getLibraryPaths() {
            Path vcpkgLibraryPath = get(getVcpkgRoot())
                                    .resolve("installed")
                                    .resolve(WINDOWS ? "x64-windows-static" : "x64-linux")
                                    .resolve("lib");
            System.out.println("[cdp4j] vcpkg library path: " + vcpkgLibraryPath);
            String uvCdp4jLibraryPath = getUvCdp4jPath().resolve("build").toString();
            System.out.println("[cdp4j] uv-cdp4j library path: " + uvCdp4jLibraryPath);
            return asList(vcpkgLibraryPath.toString(), uvCdp4jLibraryPath);
        }

        @Override
        public List<String> getHeaderFiles() {
            return asList("<uv.h>", "<cdp4j-libuv.h>");
        }

        @Override
        public List<String> getLibraries() {
            if (WINDOWS) {
                return asList("libuv", "iphlpapi", "psapi", "userenv", "ws2_32", "wsock32", "user32");
            } else {
                return emptyList();
            }
        }
    }

    @CStruct(value = "uv_loop_s", addStructKeyword = true)
    interface loop extends PointerBase {

    }

    @CStruct(value = "uv_pipe_s", addStructKeyword = true)
    interface pipe extends PointerBase {

        @CField("data")
        PointerBase data();

        @CField("data")
        void data(PointerBase data);
    }

    @CStruct(value = "uv_data_t")
    interface data extends PointerBase {

        @CField("fd")
        int fd();

        @CField("fd")
        void fd(int fd);

        @CField("stream")
        pipe stream();

        @CField("stream")
        void stream(pipe pipe);
    }

    @CStruct(value = "uv_stdio_container_s", addStructKeyword = true)
    interface stdio_container extends PointerBase {

        stdio_container addressOf(int index);

        @CField("flags")
        int flags();

        @CField("flags")
        void flags(int flags);

        @CFieldAddress("data")
        data data();
    }

    @CStruct(value = "uv_process_options_s", addStructKeyword = true)
    interface process_options extends PointerBase {

        @CField("stdio_count")
        int stdio_count();

        @CField("stdio_count")
        void stdio_count(int stdio_count);

        @CField("stdio")
        stdio_container stdio();

        @CField("stdio")
        void stdio(stdio_container stdio_container);

        @CField("file")
        CCharPointer file();

        @CField("file")
        void file(CCharPointer file);

        @CField("args")
        CCharPointerPointer args();

        @CField("args")
        void args(CCharPointerPointer args);

        @CField("flags")
        void flags(int flags);

        @CField("flags")
        int flags();

        @CField("env")
        CCharPointerPointer env();

        @CField("env")
        void env(CCharPointerPointer env);

        @CField("cwd")
        CCharPointer cwd();

        @CField("cwd")
        void cwd(CCharPointer cwd);
    }

    @CStruct(value = "uv_process_s", addStructKeyword = true)
    interface process extends PointerBase {

    }

    @CStruct(value = "uv_buf_t", addStructKeyword = true)
    interface buf extends PointerBase {
        @CField("base")
        void base(CCharPointer base);

        @CField("base")
        CCharPointer base();

        @CField("len")
        void len(int len);

        @CField("len")
        int len();
    }

    @CStruct(value = "uv_async_s", addStructKeyword = true)
    interface async extends PointerBase {

        @CField("data")
        PointerBase data();

        @CField("data")
        void data(PointerBase data);
    }

    @CStruct(value = "uv_write_s", addStructKeyword = true)
    interface write_request extends PointerBase {

        @CField("data")
        PointerBase data();

        @CField("data")
        void data(PointerBase data);
    }

    @CStruct(value = "context_write", addStructKeyword = true)
    interface context_write extends PointerBase {
        @CField("pipe")
        void pipe(pipe pie);

        @CField("pipe")
        pipe pipe();

        @CField("buf")
        void buf(buf buf);

        @CField("buf")
        buf buf();
    }

    @CFunction
    static native int uv_loop_init(loop loop);

    @CFunction
    static native int uv_pipe_init(loop loop, pipe pipe, int ipc);

    @CFunction
    static native int uv_run(loop loop, int mode);

    @CFunction
    static native int uv_stop(loop loop);

    @CFunction
    static native int uv_process_kill(process process, int signum);

    @CFunction
    static native int uv_write(write_request req, pipe handle, buf buf, UnsignedWord nbufs, PointerBase cb);

    @CFunction
    static native CCharPointer uv_err_name(int err);

    // ------------------------------------------------------------------------
    // cdp4j native methods
    // ------------------------------------------------------------------------

    @CFunction
    static native int cdp4j_spawn_process(loop loop, process process, process_options options);
    
    @CFunction
    static native int cdp4j_write_pipe(loop loop, async handle, context_write context);

    @CFunction
    static native int cdp4j_start_read(pipe out_pipe);

    @CEntryPoint(name = "cdp4j_on_read_callback_java")
    static void cdp4j_on_read_callback_java(IsolateThread thread, CCharPointer data, int len) {
        String str = CTypeConversion.toJavaString(data);
        System.out.println(str);
    }

    @CEntryPoint(name = "cdp4j_on_write_callback_java")
    static void cdp4j_on_write_callback_java(IsolateThread thread, write_request request, int status) {
        context_write context = (context_write) request.data();
        UnmanagedMemory.free(context.buf().base());
        UnmanagedMemory.free(context.buf());
        UnmanagedMemory.free(context);
        UnmanagedMemory.free(request);
    }

    @CEntryPoint(name = "cdp4j_on_async_callback")
    static void cdp4j_on_async_callback(IsolateThread thread, async handle, PointerBase callback) {
        System.out.println("on_async_callback");
        
        context_write write = (context_write) handle.data();

        write_request request = malloc(SizeOf.get(write_request.class));
        request.data(write);

        uv_write(request, write.pipe(), write.buf(), WordFactory.unsigned(1), callback);
        UnmanagedMemory.free(handle);
    }

    @CConstant
    static final native int UV_PROCESS_SETUID();

    @CConstant
    static final native int UV_PROCESS_SETGID();

    @CConstant
    static final native int UV_PROCESS_WINDOWS_VERBATIM_ARGUMENTS();

    @CConstant
    static final native int UV_IGNORE();

    @CConstant
    static final native int UV_CREATE_PIPE();

    @CConstant
    static final native int UV_INHERIT_FD();

    @CConstant
    static final native int UV_INHERIT_STREAM();
  
    @CConstant
    static final native int UV_READABLE_PIPE();

    @CConstant
    static final native int UV_WRITABLE_PIPE();

    @CConstant
    static final native int UV_OVERLAPPED_PIPE();

    @CConstant
    static final native int UV_RUN_DEFAULT();

    @CConstant
    static final native int SIGKILL();

    @CConstant
    static final native int CDP4J_UV_SUCCESS();

    public static void main(String[] args) throws Exception {
        UvLoop loop = new UvLoop();
        if (loop.init()) {

            UvProcess process = loop.createProcess();
            
            CountDownLatch latch = new CountDownLatch(1);
            
            loop.start(new Runnable() {

                @Override
                public void run() {
                    process.spawn("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe", new String[] {
                                  "chrome.exe",
                                  "--remote-debugging-pipe",
                                  "--user-data-dir=C:\\test"
                                  }, true, true);
                    latch.countDown();
                }
            });

            latch.await();
            
            Thread.sleep(1000);

            String MESSAGE_SEPERATOR = Character.toString('\0');

            if ( process != null ) {
                for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                    String message = "{\"id\":" + String.valueOf(i) + ",\"method\":\"Target.getTargets\"}" + MESSAGE_SEPERATOR;
                    process.writeAsync(message.getBytes());
                    Thread.sleep(Integer.parseInt(args[0]));
                }
            }

            System.in.read();
            
            process.kill();
            loop.stop();
        }
    }
}
