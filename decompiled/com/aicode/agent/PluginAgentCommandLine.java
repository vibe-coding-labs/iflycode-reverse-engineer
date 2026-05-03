/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.intellij.execution.configurations.GeneralCommandLine
 *  com.intellij.execution.process.ProcessInfo
 *  com.intellij.openapi.application.ApplicationInfo
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.util.SystemInfoRt
 *  com.intellij.openapi.util.io.FileUtil
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.aicode.agent;

import com.aicode.agent.service.GitReviewService;
import com.aicode.service.editor.RequestResultList;
import com.aicode.util.AICodeUtils;
import com.aicode.util.FileUtils;
import com.aicode.util.LogUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessInfo;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.SystemInfoRt;
import com.intellij.openapi.util.io.FileUtil;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PluginAgentCommandLine {
    private static final Logger enum = Logger.getInstance(PluginAgentCommandLine.class);

    @NotNull
    public static GeneralCommandLine getKillCommandLine(ProcessInfo processInfo) {
        ProcessInfo processInfo2 = processInfo;
        int n = processInfo2.getPid();
        enum.info("current agent pid is " + n);
        ArrayList a = new ArrayList();
        if (SystemInfoRt.isWindows) {
            Object object = a;
            a.add(GitReviewService.H("0 \u0012d\u00149\u001f"));
            object.add(RequestResultList.H(">@"));
            object.add("taskkill /F /PID " + n);
        } else {
            a.add(GitReviewService.H("|/\u001f$^2\u0012"));
            a.add(RequestResultList.H("<@"));
            a.add("kill -9 " + n);
        }
        return new GeneralCommandLine((List)a);
    }

    private static Path DD() {
        File file;
        Path path = FileUtils.getLocalPath();
        if (path == null) {
            path = Paths.get(AICodeUtils.getAgentDirectoryPath(), new String[0]);
            file = new File(path + File.separator + FileUtils.AGENT_DIR + File.separator + FileUtils.BIN_DIR);
        } else {
            file = new File(path + File.separator + FileUtils.BIN_DIR);
        }
        enum.info("the agent base path is: " + path);
        enum.info("the agent bin path is: " + file);
        if (file.exists()) {
            return file.toPath();
        }
        enum.warn("Unable to locate the agent bin path in base path: " + path);
        return null;
    }

    public static boolean isIntel64() {
        try {
            Method method;
            Class<?> clazz = Class.forName(RequestResultList.H("Vh~\u000fxMnMl^^o5\\mBu\u0005f^i\\uO\u0016ImZ[ZrK"));
            Method method2 = method = clazz.getDeclaredMethod(GitReviewService.H("\u000223&\u0007wE&\u001f"), new Class[0]);
            method2.setAccessible(true);
            return (Boolean)method2.invoke(null, new Object[0]);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @NotNull
    public static GeneralCommandLine createAgentBinaryCommandline() throws IOException {
        String string;
        Path path;
        Path path2 = PluginAgentCommandLine.findAgentBinary();
        if (path2 == null) {
            throw new IllegalStateException(RequestResultList.H("t\u007fBxDe\u0012Cj;EvHx_p\u0007{OuLL*\u007fFtIcZ"));
        }
        if (SystemInfoRt.isUnix && !Files.isExecutable(path2)) {
            try {
                FileUtil.setExecutable((File)path2.toFile());
            }
            catch (IOException iOException) {
                enum.warn(GitReviewService.H("\f\u00109\u0007\u00039m\u0002%Q(\u001f=\b}\u0007\u00009%\u0004i\u0010 \u001c$\f3Q%\u0003-\u00108\u0002+\u0013-\u001f"), (Throwable)iOException);
            }
        }
        if ((path = PluginAgentCommandLine.DD()) == null) {
            if (null == null) {
                PluginAgentCommandLine.enum(1);
            }
            return null;
        }
        path = path.resolve(FileUtils.NODE_EXE_FILENAME);
        if (!Files.exists(path2, new LinkOption[0])) {
            throw new IllegalStateException(RequestResultList.H("HA{JYb3U~\u0003vGcSC`;H~Nw_5NtLuZ\u0016`n\u000f|A}F"));
        }
        try {
            string = (String)Class.forName(GitReviewService.H("\u0015%\u001ck\u00178\u00198\n\u000b5!^<\u0006 \u001ek.&\u00104\u001d'\u0001 #>\u0018-\t")).getMethod(RequestResultList.H("L|_EK{\\vMJgM]\u007fNx["), new Class[0]).invoke(null, new Object[0]);
        }
        catch (Exception exception) {
            string = GitReviewService.H("#\u0015$\u001b");
        }
        String[] stringArray = new String[4];
        stringArray[0] = path2.toString();
        stringArray[1] = RequestResultList.H("\u0005=G@zr\\\u007f\u0005v@");
        stringArray[2] = path.toString();
        stringArray[3] = string.toLowerCase(Locale.ROOT);
        GeneralCommandLine generalCommandLine = new GeneralCommandLine(stringArray);
        generalCommandLine.withEnvironment(GitReviewService.H("\u0004>\u0004>\u0017<\u001d\"\u0003>\u000f)"), "");
        if (generalCommandLine == null) {
            PluginAgentCommandLine.enum(2);
        }
        return generalCommandLine;
    }

    @NotNull
    public static GeneralCommandLine createAgentCommandLine() throws IOException {
        GeneralCommandLine generalCommandLine = PluginAgentCommandLine.createAgentBinaryCommandline();
        generalCommandLine.setCharset(StandardCharsets.UTF_8);
        if (generalCommandLine == null) {
            PluginAgentCommandLine.enum(0);
        }
        return generalCommandLine;
    }

    public static boolean is64Bit() {
        try {
            Field field;
            Class<?> clazz = Class.forName(RequestResultList.H("pNp\u0001sFAb\u007fMxI4GpWYdk@7^mBy\tIQcV]gTA|GCW"));
            Field field2 = field = clazz.getDeclaredField(GitReviewService.H("B\u0015ky49\u001f"));
            field2.setAccessible(true);
            return (Boolean)field2.get(null);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static boolean isArm64() {
        try {
            Method method;
            Class<?> clazz = Class.forName(RequestResultList.H("Vh~\u000fxMnMl^^o5\\mBu\u0005f^i\\uO\u0016ImZ[ZrK"));
            Method method2 = method = clazz.getDeclaredMethod(GitReviewService.H("\u0004:3`D&\u001f"), new Class[0]);
            method2.setAccessible(true);
            return (Boolean)method2.invoke(null, new Object[0]);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public PluginAgentCommandLine() {
        PluginAgentCommandLine a;
    }

    private static void RE(@NotNull Path path) {
        Path path2 = path;
        if (path2 == null) {
            PluginAgentCommandLine.enum(3);
        }
        try {
            Path path3;
            Path path4 = path2.resolve(FileUtils.CONFIG_JSON);
            if (Files.exists(path4, new LinkOption[0]) && (path3 = JsonParser.parseString((String)Files.readString(path4, StandardCharsets.UTF_8))) instanceof JsonObject) {
                path3 = path3.getAsJsonObject();
                LogUtil.PRINT_STACK_TRACE.set(path3.has(GitReviewService.H("2*\u0013$\u0005o\u001e\u0013/\u0014=")));
                return;
            }
        }
        catch (IOException iOException) {
            enum.info(RequestResultList.H("E`zM9HvEsN}\u0006zQWd=JhZ~Q"), (Throwable)iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static /* synthetic */ void enum(int a) {
        RuntimeException runtimeException;
        int n;
        Object[] objectArray;
        int n2;
        Object[] objectArray2;
        int n3;
        int n4;
        String string;
        switch (a) {
            default: {
                string = GitReviewService.H("\n?\n*\r\r-\u0016j\u001c5\u001f\u000f3/Pl\u0001gW6^'\u00043\u000fH=\"\u0002j\u0003$\u000e4\b$Q?\u001f%\u001e");
                n4 = a;
                break;
            }
            case 3: {
                string = RequestResultList.H("QPvV7\r{S3G~Q:hN].&Qzt\nqRa@pJnMG'4\u0004b\u0004:Gf\u0012\u0012v5\fj\u000bt^fS:F\u007fV\u0018hx\u000ft]}O");
                n4 = a;
                break;
            }
        }
        switch (n4) {
            default: {
                n3 = 2;
                break;
            }
            case 3: {
                n3 = 3;
                break;
            }
        }
        Object[] objectArray3 = new Object[n3];
        switch (a) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = GitReviewService.H("=,\u0015n\u001b#\u0012?\u000f\u0002s*\u0017,\u001c=]\u0015\u0012?\u0016)\u0015)4(\u0018>2.\u0017,\u001b$\u0015\u001d\u0003'\u0017");
                n2 = a;
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = RequestResultList.H("jIeK");
                n2 = a;
                break;
            }
        }
        switch (n2) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = GitReviewService.H("&\f/\u00104\u001e)4(\u0018>2.\u0017,\u001b$\u0015\u001d\u0003'\u0017");
                n = a;
                break;
            }
            case 1: 
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = RequestResultList.H("rQ\u007fItWvb~GmipEtUck\u007fOUksKvA\u007fF");
                n = a;
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = GitReviewService.H("=,\u0015n\u001b#\u0012?\u000f\u0002s*\u0017,\u001c=]\u0015\u0012?\u0016)\u0015)4(\u0018>2.\u0017,\u001b$\u0015\u001d\u0003'\u0017");
                n = a;
                break;
            }
        }
        switch (n) {
            default: {
                break;
            }
            case 3: {
                objectArray = objectArray;
                objectArray[2] = RequestResultList.H("rI~FhxtAnd~D");
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (a) {
            default: {
                runtimeException = new IllegalStateException(string2);
                throw runtimeException;
            }
            case 3: 
        }
        runtimeException = new IllegalArgumentException(string2);
        throw runtimeException;
    }

    @Nullable
    public static Path findAgentBinary() throws IOException {
        Path path;
        boolean bl = ApplicationInfo.getInstance().getBuild().getBaselineVersion() > 222 ? PluginAgentCommandLine.isIntel64() : PluginAgentCommandLine.is64Bit();
        if (!SystemInfoRt.isMac && !bl) {
            String string = System.getProperty(RequestResultList.H("en\u0001{ZrK"));
            enum.debug("Agent binary is unsupported, os.arch: " + string);
            return null;
        }
        Path path2 = PluginAgentCommandLine.DD();
        if (path2 == null) {
            return null;
        }
        PluginAgentCommandLine.RE(path2);
        Path path3 = null;
        String string = System.getProperty(GitReviewService.H("\"\u0005d\u0007 \f%\u00042\b"));
        if (SystemInfoRt.isLinux) {
            path3 = path2.resolve(FileUtils.X86_64_LINUX_NODE);
            String string2 = "chmod a+x " + path3;
            String[] stringArray = new String[3];
            stringArray[0] = RequestResultList.H("%\u007fFt\u0007bK");
            stringArray[1] = GitReviewService.H("p\u0005");
            stringArray[2] = string2;
            ProcessBuilder processBuilder = new ProcessBuilder(stringArray);
            path = path3;
            processBuilder.start();
        } else if (SystemInfoRt.isWindows) {
            Path path4 = path2;
            path = string.startsWith(RequestResultList.H("\u001e?\u0012")) ? (path3 = path4.resolve(FileUtils.X86_64_WINDOWS7_NODE + FileUtils.AGENT_EXE_SUFFIX)) : (path3 = path4.resolve(FileUtils.X86_64_WINDOWS_NODE + FileUtils.AGENT_EXE_SUFFIX));
        } else {
            if (SystemInfoRt.isMac) {
                Path path5 = path2;
                path3 = PluginAgentCommandLine.isArm64() ? path5.resolve(FileUtils.X86_64_DARWIN_ARM_NODE) : path5.resolve(FileUtils.X86_64_DARWIN_NODE);
                String string3 = "chmod a+x " + path3;
                String[] stringArray = new String[3];
                stringArray[0] = GitReviewService.H("^'\u00178B.\u000e");
                stringArray[1] = RequestResultList.H("<@");
                stringArray[2] = string3;
                ProcessBuilder processBuilder = new ProcessBuilder(stringArray);
                processBuilder.start();
            }
            path = path3;
        }
        if (path != null) {
            if (Files.exists(path3, new LinkOption[0])) {
                return path3;
            }
        }
        return null;
    }
}
