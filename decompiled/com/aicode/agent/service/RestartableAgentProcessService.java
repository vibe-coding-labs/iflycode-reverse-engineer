/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.IdUtil
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.intellij.execution.configurations.GeneralCommandLine
 *  com.intellij.execution.process.ProcessInfo
 *  com.intellij.openapi.Disposable
 *  com.intellij.openapi.diagnostic.Attachment
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.util.Pair
 *  io.opentelemetry.api.trace.Span
 *  org.apache.commons.lang3.StringUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.aicode.agent.service;

import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.action.RefreshAction;
import com.aicode.action.batch.GeneratorConfig;
import com.aicode.agent.PluginAgentCommandLine;
import com.aicode.agent.PluginAgentProcessHandler;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.PluginWebsocketListener;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.service.PluginAgentProcessService;
import com.aicode.agent.service.PluginAgentProcessServiceEx;
import com.aicode.agent.service.PluginAgentProcessServiceImpl;
import com.aicode.apm.OpenTelemetryService;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.apm.enums.SpanAttrEnum;
import com.aicode.apm.enums.TracerEnum;
import com.aicode.enums.RestartEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.ApplicationUtil;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessInfo;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.diagnostic.Attachment;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import io.opentelemetry.api.trace.Span;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class RestartableAgentProcessService
implements PluginAgentProcessService,
Disposable {
    private final Object float;
    @NotNull
    public static final AtomicBoolean pushAgentRefresh;
    @NotNull
    public final AtomicInteger connectAttempts;
    @NotNull
    public static final AtomicInteger refreshTimes;
    private PluginAgentProcessServiceImpl byte;
    @NotNull
    public static final AtomicInteger restartAttempts;
    private static final Logger enum;
    public static final int RESTART_TIME = 3;

    static {
        enum = Logger.getInstance(RestartableAgentProcessService.class);
        restartAttempts = new AtomicInteger();
        refreshTimes = new AtomicInteger(1);
        pushAgentRefresh = new AtomicBoolean(false);
    }

    public static void pushAgentRefreshToWebView() {
        Project project = ApplicationUtil.findCurrentProject();
        WebViewWindowPanel webViewWindowPanel = (WebViewWindowPanel)project.getUserData(WebViewWindowPanel.WEB_VIEW_PANEL);
        if (Objects.isNull(webViewWindowPanel) || !webViewWindowPanel.isLoaded.get()) {
            pushAgentRefresh.set(true);
            return;
        }
        webViewWindowPanel = new JsonObject();
        webViewWindowPanel.addProperty(AICodeStringUtil.H("_Y]C"), WebViewDataTypeEnum.LOGIN_SHOW_FRESH.getType());
        Object object = new JsonObject();
        int n = refreshTimes.getAndIncrement();
        object.addProperty(GeneratorConfig.H("\u0019\t= \u001a=\u000b\u001d\u0012\u0019,\u0001\u0005\u001c\u000b\n"), (Number)n);
        webViewWindowPanel.add(AICodeStringUtil.H("\\JLXC"), (JsonElement)object);
        if (n == 1) {
            object = new HashMap<String, String>();
            object.put(GeneratorConfig.H("\u001c\u0007\u001e\u001d"), WebViewDataTypeEnum.COMMON_OPEN_PAGE.getType());
            object.put(AICodeStringUtil.H("\\JLXC"), PageEnum.CHAT_VIEW.getType());
            SocketMessageHandleListener.send2Web(project, object);
        }
        SocketMessageHandleListener.send2Web(project, webViewWindowPanel);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void forceRestart() {
        RestartableAgentProcessService restartableAgentProcessService = this;
        Object a = restartableAgentProcessService.float;
        synchronized (a) {
            try {
                restartableAgentProcessService.byte.shutdown();
            }
            finally {
                restartableAgentProcessService.pE();
            }
            return;
        }
    }

    private void pE() {
        RestartableAgentProcessService restartableAgentProcessService;
        block3: {
            restartableAgentProcessService = this;
            int a22 = restartAttempts.get();
            try {
                if (a22 < 3) break block3;
                enum.info(AICodeStringUtil.H("DT_;}LHC\u0011LPR^JRYU"));
                return;
            }
            catch (Throwable a22) {
                enum.error(GeneratorConfig.H("\u0001\u0000\u001a\u0011\u001cX\u0011\u0000\u0011\u001a1/\u0002\u0011\u0003\u0006\u0013\fB\u0015\u000f\u001b\u0000\f"), a22, new Attachment[0]);
                return;
            }
        }
        PluginAgentProcessServiceImpl a22 = restartableAgentProcessService.byte = restartableAgentProcessService.createInitializedDelegate();
        a22.startNotify();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public void onRestartException(@NotNull String string, Integer n) {
        void a;
        void recentOutput;
        RestartableAgentProcessService restartableAgentProcessService = this;
        if (recentOutput == null) {
            RestartableAgentProcessService.enum(0);
        }
        if (PluginStartupActivity.UNINSTALL.get()) {
            enum.info("[RestartableAgentProcessService] onRestartException: " + PluginStartupActivity.UNINSTALL.get());
            return;
        }
        boolean bl = false;
        try {
            if (!restartableAgentProcessService.byte.isShutdown() && a.intValue() == RestartEnum.CONNECT_REFUSED.getCode()) {
                restartableAgentProcessService.connectAttempts.incrementAndGet();
                int n2 = restartableAgentProcessService.connectAttempts.get();
                if (n2 < 3) {
                    return;
                }
                restartableAgentProcessService.connectAttempts.set(0);
            }
            bl = true;
            restartableAgentProcessService.forceRestart();
            bl = false;
        }
        catch (Throwable throwable) {
            enum.warn(" Agent terminated Exception. Exit code: " + (Integer)a + (String)recentOutput);
        }
        finally {
            if (bl) {
                enum.warn(" Agent terminated false. Exit code: " + (Integer)a + (String)recentOutput);
            }
        }
        enum.warn(" Agent terminated unsafely. Exit code: " + (Integer)a + (String)recentOutput);
    }

    public RestartableAgentProcessService() {
        RestartableAgentProcessService a;
        RestartableAgentProcessService restartableAgentProcessService = a;
        RestartableAgentProcessService restartableAgentProcessService2 = a;
        restartableAgentProcessService.float = new Object();
        RestartableAgentProcessService restartableAgentProcessService3 = a;
        restartableAgentProcessService2.connectAttempts = new AtomicInteger();
        restartableAgentProcessService.init();
    }

    @Override
    public boolean isRunning() {
        PluginAgentProcessService pluginAgentProcessService = this;
        PluginAgentProcessService a = (PluginAgentProcessServiceImpl)pluginAgentProcessService.getDelegate();
        if (a == null) {
            return false;
        }
        return ((PluginAgentProcessServiceImpl)a).isRunning();
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray = new Object[3];
        objectArray[0] = AICodeStringUtil.H("_CYTPAn__PXR");
        objectArray[1] = GeneratorConfig.H("\r\u0010\u0004E\u001d\u0010\f\u0010\r\u000fS\b\u0018\u0002\u001f\u001aW99\u001e\f\u0007\u001b\u0000\\-\f(9\u0018\u001d\u0010\u0013\n\u0012\u000b9\u001f\u000b\u0016\u001a\b<\u0001\u001b\u001c\u001c\u000e8\u0007\u0006\u001e\u0017\r\u001d");
        objectArray[2] = AICodeStringUtil.H("T^iUhdLTNtFVDZ_IBH");
        throw new IllegalArgumentException(String.format(GeneratorConfig.H("dA>:\u0003\u001d\u0016\u001a_\u000f\u0005\u000eY/1\u0006\u001e2\u001c\u0013\u000bQ\u001e\u00198=\u0001\u001f\u001a\u001d\u0017SXL(jY\u0000\u0002RM\r@]\u000bN\u0015\u001b+:N\u0016\u0016\u001b]\t\u0007T\u0006\u000b\u0002\u0014"), objectArray));
    }

    /*
     * Exception decompiling
     */
    public void onReconnectException(String var1_3, @Nullable Integer var2_4, Project var3_5) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private /* synthetic */ void de(String string, Project project) {
        RestartableAgentProcessService a = project;
        RestartableAgentProcessService a2 = this;
        try {
            void a3;
            Thread.sleep(3000L);
            if (RefreshAction.REFRESH_MAP.containsKey(a3) && !((Boolean)RefreshAction.REFRESH_MAP.get(a3)).booleanValue()) {
                a2.onReconnectException(RestartEnum.REFRESH_RECONNECT.getText(), RestartEnum.REFRESH_RECONNECT.getCode(), (Project)a);
            } else {
                PluginWebsocketClient.sendWsMessage(CommandEnum.USER_LOGIN, (Project)a);
            }
            RefreshAction.REFRESH_MAP.clear();
            return;
        }
        catch (Throwable throwable) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PluginAgentProcessServiceEx getDelegate() {
        RestartableAgentProcessService restartableAgentProcessService = this;
        Object a = restartableAgentProcessService.float;
        synchronized (a) {
            return restartableAgentProcessService.byte;
        }
    }

    /*
     * WARNING - void declaration
     */
    public void checkAgent(Project project) {
        Span span;
        void a;
        RestartableAgentProcessService restartableAgentProcessService = this;
        Span span2 = OpenTelemetryService.getInstance().parentSpan;
        Span span3 = OpenTelemetryUtil.buildWithParent(span2, TracerEnum.AGENT_RUN, PluginWebsocketClient.class.getName());
        PluginWebsocketClient pluginWebsocketClient = new PluginWebsocketClient();
        PluginWebsocketListener pluginWebsocketListener = new PluginWebsocketListener((Project)a);
        try {
            String a2 = restartableAgentProcessService.JD();
            pluginWebsocketClient.createWebSocketConnect(pluginWebsocketListener, a2, span3);
            span = span3;
        }
        catch (Exception a2) {
            enum.info(GeneratorConfig.H("\u0000\u001d\u0015\u001a\u0003\u001b\u0006?\t\u001d\u0016\u001a%N+:\u000f\n\rO\u0018\u0019\u0010\u001b\u001a^TX"), (Throwable)a2);
            restartableAgentProcessService.onRestartException(a2.getMessage(), RestartEnum.START_AGENT.getCode(), span3);
            if (restartAttempts.get() <= 3) {
                restartableAgentProcessService.checkAgent((Project)a);
            }
            Span span4 = span3;
            span = span4;
            span4.recordException((Throwable)a2);
        }
        span.end();
        if (span2 != null) {
            span2.end();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dispose() {
        RestartableAgentProcessService restartableAgentProcessService = this;
        Object a = restartableAgentProcessService.float;
        synchronized (a) {
            Object object;
            try {
                PluginWebsocketClient.closeWebsocket(AICodeStringUtil.H("XZP\u0001IGO^C"));
                restartableAgentProcessService.byte.shutdown();
                object = a;
            }
            catch (Throwable throwable) {
                enum.error(GeneratorConfig.H("\u000f\u000b\u0007\u0018\u0011\u001d\u001d"), throwable);
                object = a;
            }
            return;
        }
    }

    @NotNull
    public PluginAgentProcessServiceImpl createInitializedDelegate() throws Exception {
        return new PluginAgentProcessServiceImpl();
    }

    @Nullable
    private String JD() throws InterruptedException {
        Object a;
        RestartableAgentProcessService restartableAgentProcessService;
        RestartableAgentProcessService restartableAgentProcessService2 = restartableAgentProcessService = this;
        Pair pair = restartableAgentProcessService2.byte.getAgentPort(restartableAgentProcessService2.byte.getAgentPid(), 0);
        int n = 1;
        Object object = a = (String)pair.first;
        while (StringUtils.isBlank((CharSequence)object)) {
            if (n >= 5) break;
            RestartableAgentProcessService restartableAgentProcessService3 = restartableAgentProcessService;
            pair = restartableAgentProcessService3.byte.getAgentPort(restartableAgentProcessService3.byte.getAgentPid(), n);
            ++n;
            object = a = (String)pair.first;
        }
        return a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public void onRestartException(String string, Integer n, Span span) {
        void a;
        void a2;
        Object a3 = string;
        RestartableAgentProcessService a4 = this;
        if (a3 == null) {
            a3 = AICodeStringUtil.H("\u5f23\u5e12\u4fca\u604f\u4e17\u7a5c");
        }
        restartAttempts.incrementAndGet();
        int n2 = restartAttempts.get();
        if (n2 > 3) {
            try {
                Thread.sleep(3000L);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }
        try {
            Thread.sleep(3000L);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (PluginStartupActivity.UNINSTALL.get()) {
            enum.info("[RestartableAgentProcessService] onRestartException: " + PluginStartupActivity.UNINSTALL.get());
            return;
        }
        Span span2 = OpenTelemetryUtil.buildWithParent((Span)a2, TracerEnum.AGENT_RESTART, a4.getClass().getName());
        span2.setAttribute(SpanAttrEnum.AGENT_START_REASON.getText(), (String)a3);
        span2.setAttribute(SpanAttrEnum.AGENT_START_CODE.getText(), (long)a.intValue());
        if (PluginStartupActivity.UNINSTALL.get()) {
            span2.end();
            return;
        }
        boolean bl = false;
        try {
            enum.warn("agent\u91cd\u8bd5\u6b21\u6570\uff1a" + n2);
            if (n2 == 3) {
                RestartableAgentProcessService.pushAgentRefreshToWebView();
            }
            if (!a4.byte.isShutdown() && a.intValue() == RestartEnum.CONNECT_REFUSED.getCode()) {
                a4.connectAttempts.incrementAndGet();
                n2 = a4.connectAttempts.get();
                if (n2 < 3) {
                    return;
                }
                a4.connectAttempts.set(0);
            }
            bl = true;
            a4.forceRestart();
            bl = false;
        }
        catch (Throwable throwable) {
            enum.warn(" Agent terminated Exception. Exit code: " + (Integer)a + (String)a3);
            span2.recordException(throwable);
        }
        finally {
            if (bl) {
                enum.warn(" Agent terminated false. Exit code: " + (Integer)a + (String)a3);
            }
        }
        enum.warn(" Agent terminated unsafely. Exit code: " + (Integer)a + (String)a3);
        span2.end();
        if (a2 != null) {
            a2.end();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void killAgent() {
        List<ProcessInfo> list = PluginAgentProcessHandler.getAgents();
        try {
            Iterator<ProcessInfo> iterator = list.iterator();
            while (iterator.hasNext()) {
                Object object = PluginAgentCommandLine.getKillCommandLine(iterator.next());
                if (!((object = new PluginAgentProcessHandler((GeneralCommandLine)object, null)).isProcessTerminating() || object.isProcessTerminated() || object.canKillProcess())) {
                    object.killProcess();
                    continue;
                }
                enum.info(AICodeStringUtil.H("\u007fOEMAC)v@\u0004KITNYQm\u007fVH\u0007XNR@OUQOU;qJCTE\u001eESEHE^U"));
            }
            return;
        }
        catch (Throwable throwable) {
            enum.info(GeneratorConfig.H("\u0013\u00074\"N\u0019\u001e\n\u0013\u001fB\u0011\u001a\f\u0001\n"), throwable);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void refreshAgent(Project project) {
        void a;
        RestartableAgentProcessService restartableAgentProcessService = this;
        String string = IdUtil.fastSimpleUUID();
        Object a2 = CommandEnum.USER_VERSION.getType();
        a2 = new MessageDto(string, (String)a2);
        RefreshAction.REFRESH_MAP.put(string, false);
        PluginWebsocketClient.sendWsMessage((MessageDto)a2, (Project)a);
        PluginStartupActivity.handleExecutorService.execute(() -> restartableAgentProcessService.de(string, (Project)a));
    }

    public void init() {
        RestartableAgentProcessService restartableAgentProcessService = this;
        try {
            RestartableAgentProcessService restartableAgentProcessService2 = restartableAgentProcessService;
            restartableAgentProcessService2.byte = restartableAgentProcessService2.createInitializedDelegate();
            restartableAgentProcessService2.byte.startNotify();
            return;
        }
        catch (Throwable a) {
            enum.info(GeneratorConfig.H("\u000b\n\n\u0001\nN1 N\u001b\u0016\u0001\u000e\u001f\u0010\u0001\u000b\n\u0001\n"), a);
            return;
        }
    }
}
