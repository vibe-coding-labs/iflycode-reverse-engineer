/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.execution.ExecutionException
 *  com.intellij.execution.configurations.GeneralCommandLine
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.util.Pair
 *  com.intellij.util.ConcurrencyUtil
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.agent.service;

import com.aicode.agent.PluginAgentCommandLine;
import com.aicode.agent.PluginAgentProcessHandler;
import com.aicode.agent.service.PluginAgentProcessServiceEx;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.service.editor.RequestResultList;
import com.aicode.util.AICodeUtils;
import com.aicode.util.FileUtils;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Pair;
import com.intellij.util.ConcurrencyUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PluginAgentProcessServiceImpl
implements PluginAgentProcessServiceEx {
    private String final;
    private final PluginAgentProcessHandler try;
    private final AtomicBoolean float;
    private static final Logger byte = LoggerFactory.getLogger(PluginAgentProcessServiceImpl.class);
    private final ExecutorService enum;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void shutdown() {
        PluginAgentProcessServiceImpl pluginAgentProcessServiceImpl;
        block11: {
            pluginAgentProcessServiceImpl = this;
            if (!pluginAgentProcessServiceImpl.float.compareAndSet(false, true)) {
                throw new IllegalStateException(RequestResultList.H("fRb[S5fBu\u0014c\\oJvAh\u0003Ngl_`YoD"));
            }
            if (!Objects.nonNull((Object)pluginAgentProcessServiceImpl.try)) return;
            boolean a = !ApplicationManager.getApplication().isDisposed();
            try {
                if (a) {
                    pluginAgentProcessServiceImpl.try.destroyProcess();
                }
                pluginAgentProcessServiceImpl.enum.shutdown();
                if (a) {
                    pluginAgentProcessServiceImpl.enum.awaitTermination(1L, TimeUnit.SECONDS);
                }
                if (!a) break block11;
            }
            catch (Exception exception) {
                block12: {
                    try {
                        byte.error(RequestResultList.H("PUg~Q&@gBpFyDeJSh9JcSv^"), (Throwable)exception);
                        if (!a) break block12;
                    }
                    catch (Throwable throwable) {
                        Throwable throwable2;
                        if (!a || !pluginAgentProcessServiceImpl.try.isProcessTerminating() && !pluginAgentProcessServiceImpl.try.isProcessTerminated()) {
                            if (pluginAgentProcessServiceImpl.try.canKillProcess()) {
                                throwable2 = throwable;
                                pluginAgentProcessServiceImpl.try.killProcess();
                                throw throwable2;
                            }
                            byte.error(RequestResultList.H("FOpAvM FK6`[iJrCdOkL'ABg|JhUvU=Np@\u007fW\u001d\u007fkDgSkY"));
                        }
                        throwable2 = throwable;
                        throw throwable2;
                    }
                    if (pluginAgentProcessServiceImpl.try.isProcessTerminating()) return;
                    if (pluginAgentProcessServiceImpl.try.isProcessTerminated()) return;
                }
                if (pluginAgentProcessServiceImpl.try.canKillProcess()) {
                    pluginAgentProcessServiceImpl.try.killProcess();
                    return;
                }
                byte.error(FileExtensionLanguageDetails.H("R{duby4r_\u0002to}~fwp{\u007fx3uVSh~|aba)zdtkc\tK\u007fpsg\u007fm"));
                return;
            }
            if (pluginAgentProcessServiceImpl.try.isProcessTerminating()) return;
            if (pluginAgentProcessServiceImpl.try.isProcessTerminated()) return;
        }
        if (pluginAgentProcessServiceImpl.try.canKillProcess()) {
            pluginAgentProcessServiceImpl.try.killProcess();
            return;
        }
        byte.error(FileExtensionLanguageDetails.H("R{duby4r_\u0002to}~fwp{\u007fx3uVSh~|aba)zdtkc\tK\u007fpsg\u007fm"));
        return;
    }

    public String getPort() {
        PluginAgentProcessServiceImpl a;
        return a.final;
    }

    @Override
    public boolean isRunning() {
        PluginAgentProcessServiceImpl a;
        if (!a.try.isProcessTerminated() && !a.try.isProcessTerminating()) {
            return true;
        }
        return false;
    }

    public PluginAgentProcessServiceImpl() throws IOException, ExecutionException {
        PluginAgentProcessServiceImpl a;
        PluginAgentProcessServiceImpl pluginAgentProcessServiceImpl = a;
        PluginAgentProcessServiceImpl pluginAgentProcessServiceImpl2 = a;
        pluginAgentProcessServiceImpl2.float = new AtomicBoolean(false);
        a.enum = ConcurrencyUtil.newSingleThreadExecutor((String)RequestResultList.H("gIdDTa9JcSv^"));
        pluginAgentProcessServiceImpl.final = null;
        a.unZipAgent();
        pluginAgentProcessServiceImpl.copySource();
        pluginAgentProcessServiceImpl.try = pluginAgentProcessServiceImpl.launchAgent();
    }

    @Override
    public void copySource() {
        PluginAgentProcessServiceImpl pluginAgentProcessServiceImpl = this;
        Path a = FileUtils.getLocalPath();
        FileUtils.copyDir(a != null ? (Path)a + File.separator + FileUtils.WASM_DIR : Paths.get(AICodeUtils.getAgentDirectoryPath(), new String[0]) + File.separator + FileUtils.AGENT_DIR + File.separator + FileUtils.WASM_DIR, AICodeUtils.getWasmsDirectoryPath());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Pair getAgentPort(Long l, int n) throws InterruptedException {
        void a;
        int a2 = n;
        PluginAgentProcessServiceImpl a3 = this;
        a3.enum.awaitTermination(a2, TimeUnit.SECONDS);
        return new Pair((Object)a3.final, (Object)a);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void unZipAgent() {
        PluginAgentProcessServiceImpl pluginAgentProcessServiceImpl = this;
        Object a222323232222 = FileUtils.getLocalPath();
        if (a222323232222 != null) {
            return;
        }
        if (a222323232222 == null) {
            a222323232222 = Paths.get(AICodeUtils.getAgentDirectoryPath(), new String[0]);
        }
        if (new File(a222323232222.toFile(), FileExtensionLanguageDetails.H("~wgbj")).exists()) {
            byte.info(RequestResultList.H("LP\u007fYT\u007fD\u000bw]qZ"));
            return;
        }
        File file = FileUtils.getFileOfPluginPath(FileExtensionLanguageDetails.H("vN^ck>xen"));
        FileUtils.copyFile(file, RequestResultList.H(">BZjw_*LqZ"));
        if (file.exists()) {
            PluginAgentProcessServiceImpl.Of(file, a222323232222.toFile());
            try {
                file.delete();
                return;
            }
            catch (Exception a222323232222) {
                byte.warn("fail to delete directory " + file.getName(), (Throwable)a222323232222);
                return;
            }
            catch (IOException a222323232222) {
                try {
                    byte.info("[unzip] error: " + a222323232222);
                }
                catch (Throwable throwable) {
                    Throwable throwable2;
                    try {
                        file.delete();
                        throwable2 = throwable;
                    }
                    catch (Exception a222323232222) {
                        byte.warn("fail to delete directory " + file.getName(), (Throwable)a222323232222);
                        throwable2 = throwable;
                    }
                    throw throwable2;
                }
                try {
                    file.delete();
                    return;
                }
                catch (Exception a222323232222) {
                    byte.warn("fail to delete directory " + file.getName(), (Throwable)a222323232222);
                    return;
                }
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void Of(File file, File file2) throws IOException {
        File file3 = file;
        ZipInputStream a = new ZipInputStream(new FileInputStream(file3));
        try {
            ZipEntry zipEntry;
            while ((zipEntry = a.getNextEntry()) != null) {
                ZipInputStream zipInputStream;
                void a2;
                File file4 = new File((File)a2, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    zipInputStream = a;
                    file4.mkdirs();
                } else {
                    file4.getParentFile().mkdirs();
                    CopyOption[] copyOptionArray = new CopyOption[1];
                    copyOptionArray[0] = StandardCopyOption.REPLACE_EXISTING;
                    Files.copy(a, file4.toPath(), copyOptionArray);
                    zipInputStream = a;
                }
                zipInputStream.closeEntry();
            }
        }
        catch (Throwable throwable) {
            Throwable throwable2;
            try {
                a.close();
                throwable2 = throwable;
                throw throwable2;
            }
            catch (Throwable throwable3) {
                Throwable throwable4 = throwable;
                throwable2 = throwable4;
                throwable4.addSuppressed(throwable3);
            }
            throw throwable2;
        }
        a.close();
    }

    @Override
    public boolean isShutdown() {
        PluginAgentProcessServiceImpl a;
        return a.float.get();
    }

    @Override
    public void startNotify() {
        PluginAgentProcessServiceImpl pluginAgentProcessServiceImpl = this;
        try {
            pluginAgentProcessServiceImpl.try.startNotify();
            return;
        }
        catch (Throwable a) {
            byte.error(FileExtensionLanguageDetails.H("bv`ygQanPDvd2sbe{oM~q~OB-zbpcl"), a);
            return;
        }
    }

    public PluginAgentProcessHandler launchAgent() throws IOException, ExecutionException {
        PluginAgentProcessServiceImpl pluginAgentProcessServiceImpl = this;
        PluginAgentProcessServiceImpl a = PluginAgentCommandLine.createAgentCommandLine();
        return new PluginAgentProcessHandler((GeneralCommandLine)a, pluginAgentProcessServiceImpl);
    }

    public void setPort(String string) {
        String a = string;
        PluginAgentProcessServiceImpl a2 = this;
        a2.final = a;
    }

    public Long getAgentPid() {
        PluginAgentProcessServiceImpl a;
        return a.try.getProcess().pid();
    }
}
