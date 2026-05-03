/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.thread.ThreadFactoryBuilder
 *  com.google.gson.JsonObject
 *  com.intellij.ide.plugins.IdeaPluginDescriptor
 *  com.intellij.ide.plugins.PluginInstaller
 *  com.intellij.ide.plugins.PluginStateListener
 *  com.intellij.openapi.application.PathManager
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.startup.StartupActivity$Background
 *  com.intellij.openapi.startup.StartupActivity$DumbAware
 *  com.intellij.openapi.util.Key
 *  io.opentelemetry.api.GlobalOpenTelemetry
 *  org.jetbrains.annotations.NotNull
 */
package com.aicode;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.aicode.action.ActionsUtil;
import com.aicode.agent.HeartBeatCheckRunner;
import com.aicode.agent.service.InitService;
import com.aicode.agent.service.PluginAgentProcessService;
import com.aicode.agent.service.RestartableAgentProcessService;
import com.aicode.apm.OpenTelemetryService;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.content.util.file.LanguageFileExtensionDetails;
import com.aicode.enums.PluginSceneEnum;
import com.aicode.enums.RestartEnum;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.listener.GitBranchChangeListener;
import com.aicode.message.BasicActionsBundle;
import com.aicode.ui.FontKt;
import com.aicode.updater.PluginUpdaterCheckService;
import com.aicode.util.ApplicationUtil;
import com.google.gson.JsonObject;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginInstaller;
import com.intellij.ide.plugins.PluginStateListener;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.util.Key;
import io.opentelemetry.api.GlobalOpenTelemetry;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PluginStartupActivity
implements StartupActivity.Background,
StartupActivity.DumbAware {
    public static ExecutorService handleExecutorService;
    public static final Key API_KEY;
    public static final AtomicBoolean THREAD_START;
    public static final AtomicBoolean UNINSTALL;
    private static final Logger enum;
    public static AtomicBoolean ACTIVITY_STARTED;
    public static ThreadFactory namedThreadFactory;

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray = new Object[3];
        objectArray[0] = FontKt.H(">+*(;92");
        objectArray[1] = InlineChatStatusServiceKt.H("%\f\u0016q=\u0010*\u0003>\u001as)1\r?\u0014\u0007\u001f?\u000f.\r4\u0014\u001f\u00185\r*\u0010%\r");
        objectArray[2] = FontKt.H("-;<\u0013--,47.?");
        throw new IllegalArgumentException(String.format(InlineChatStatusServiceKt.H("\u0006\u0010\"\u0015kF'\u0018a\u0002$\u001cg\"\u0002\u00069&sO\u0014}4\u0000/\u0019\"\u000f5\u00014C\\{/^i\u0003<_y\ns]+]\u000498\u001a|\u0017.\u0010~\u0019$D2\f=\u0018"), objectArray));
    }

    public static void clearAgent() {
        PluginInstaller.addStateListener((PluginStateListener)new PluginStateListener(){

            private static /* synthetic */ void enum(int a) {
                Object[] objectArray;
                String string = OpenTelemetryUtil.H("A6o9o#`>%'k2oK\u007f\u001ay\u0007a<jbx-}*_\u0013a4rd4rfv4?wu\u0005\u0017,cfqe9d'(\"z%8> !?`<x");
                Object[] objectArray2 = new Object[3];
                objectArray2[0] = LanguageFileExtensionDetails.H("e\u0019b\u001cL}J\nq\u0018Y\bc\u001c`\nP\u001cj\u00176@ I>N");
                objectArray2[1] = OpenTelemetryUtil.H("v>mkr>v>p5>\u0005L\u0011e/{\u0002|-e'}<T2l53h%lt%");
                switch (a) {
                    default: {
                        objectArray = objectArray2;
                        objectArray2[2] = LanguageFileExtensionDetails.H("\u001d*Z$\\=P");
                        break;
                    }
                    case 1: {
                        objectArray = objectArray2;
                        while (false) {
                        }
                        objectArray2[2] = OpenTelemetryUtil.H("$v5+r%t<x");
                        break;
                    }
                }
                throw new IllegalArgumentException(String.format(string, objectArray));
            }

            public void install(@NotNull IdeaPluginDescriptor ideaPluginDescriptor) {
                01 v0 = this_;
                01 this_ = ideaPluginDescriptor;
                01 a = v0;
                if (this_ == null) {
                    01.enum(0);
                }
            }
            {
                01 a;
            }

            public void uninstall(@NotNull IdeaPluginDescriptor ideaPluginDescriptor) {
                01 v0 = this_;
                01 this_ = ideaPluginDescriptor;
                01 a = v0;
                if (this_ == null) {
                    01.enum(1);
                }
                enum.info("[" + BasicActionsBundle.message(OpenTelemetryUtil.H("\u0011g>u4=6|2{4t{e\u0000k2z#I/c:g\"R#w)5/%p(`"), new Object[0]) + "] uninstall ...");
                enum.info("[" + BasicActionsBundle.message(LanguageFileExtensionDetails.H("9f\u0016t\u001c<\u001e}\u001az\u001cuSd(j\u001a{\u000bH\u0007b\u0012f\nS\u000bv\u00014\u0007$X)H"), new Object[0]) + "] uninstall,path=" + PathManager.getPluginsPath());
                PluginStartupActivity.clear();
            }
        });
    }

    public static void setApiKey(String string) {
        String string2 = string;
        Iterator<Project> a = ApplicationUtil.findValidProjects().iterator();
        block0: while (true) {
            Iterator<Project> iterator = a;
            while (iterator.hasNext()) {
                Project project = (Project)a.next();
                if (project == null) continue block0;
                if (project.isDisposed()) {
                    iterator = a;
                    continue;
                }
                project.putUserData(API_KEY, (Object)string2);
                iterator = a;
            }
            break;
        }
    }

    public static void clear() {
        UNINSTALL.set(true);
        try {
            GlobalOpenTelemetry.resetForTest();
            RestartableAgentProcessService.killAgent();
            enum.info(InlineChatStatusServiceKt.H("1\u0011\b3\u0004\u00120\u000f\r(\u001e.\n,8>\f1\u000b\u0000823|\u0012(\b2[ \u00039\u0017%U"));
            return;
        }
        catch (Throwable throwable) {
            enum.info(FontKt.H("\u0002\u0015\b\r$68\u0019181+7.\u00068\u0002\u0003\"!7&\u0003b*42.c>)7:<{!3':/!x"));
            return;
        }
    }

    public void runActivity(@NotNull Project project) {
        PluginStartupActivity pluginStartupActivity = pluginStartupActivity2;
        PluginStartupActivity pluginStartupActivity2 = project;
        PluginStartupActivity a = pluginStartupActivity;
        if (pluginStartupActivity2 == null) {
            PluginStartupActivity.enum(0);
        }
        pluginStartupActivity2.putUserData(API_KEY, "");
        RestartableAgentProcessService restartableAgentProcessService = (RestartableAgentProcessService)PluginAgentProcessService.getInstance();
        if (!restartableAgentProcessService.isRunning()) {
            restartableAgentProcessService.onRestartException(RestartEnum.START_AGENT.getText(), RestartEnum.START_AGENT.getCode());
        }
        OpenTelemetryService.getInstance().handApmConfig(new JsonObject());
        ACTIVITY_STARTED.set(true);
        PluginStartupActivity pluginStartupActivity3 = pluginStartupActivity2;
        restartableAgentProcessService.checkAgent((Project)pluginStartupActivity3);
        ((InitService)pluginStartupActivity3.getService(InitService.class)).initProject((Project)pluginStartupActivity2);
        a.checkAgentState();
        PluginStartupActivity.clearAgent();
        ActionsUtil.refreshActions();
        new GitBranchChangeListener((Project)pluginStartupActivity2);
        if (PluginSceneEnum.saasScene()) {
            PluginUpdaterCheckService.scheduleRepeatedUpdateCheck((Project)pluginStartupActivity2);
        }
    }

    public synchronized void checkAgentState() {
        if (THREAD_START.get()) {
            return;
        }
        THREAD_START.set(true);
        HeartBeatCheckRunner.run();
    }

    public PluginStartupActivity() {
        PluginStartupActivity a;
    }

    public static String getApiKey() {
        Project project;
        block3: {
            project = ApplicationUtil.findCurrentProject();
            try {
                if (project != null) break block3;
                return "";
            }
            catch (Exception exception) {
                return "";
            }
        }
        return project.getUserData(API_KEY).toString();
    }

    static {
        namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix(BasicActionsBundle.message(InlineChatStatusServiceKt.H("\r3\u000b3\u0013U?5\u001a&\b?Q\u0019\u001d4\f7\u000f(/?\u00073\u0017\u0006\u00161\u000e1J(\u001c)\u0000"), new Object[0]) + "-").build();
        handleExecutorService = new ThreadPoolExecutor(10, 200, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        ACTIVITY_STARTED = new AtomicBoolean(false);
        THREAD_START = new AtomicBoolean(false);
        UNINSTALL = new AtomicBoolean(false);
        API_KEY = Key.create((String)FontKt.H("?2*\u0014ll"));
        enum = Logger.getInstance(PluginStartupActivity.class);
    }
}
