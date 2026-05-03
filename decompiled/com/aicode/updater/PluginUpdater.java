/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.io.FileUtil
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.intellij.ide.plugins.IdeaPluginDescriptor
 *  com.intellij.ide.plugins.InstalledPluginsState
 *  com.intellij.ide.plugins.PluginInstaller
 *  com.intellij.ide.plugins.PluginManager
 *  com.intellij.ide.plugins.PluginManagerCore
 *  com.intellij.ide.startup.StartupActionScriptManager
 *  com.intellij.ide.startup.StartupActionScriptManager$CopyCommand
 *  com.intellij.ide.startup.StartupActionScriptManager$DeleteCommand
 *  com.intellij.ide.startup.StartupActionScriptManager$UnzipCommand
 *  com.intellij.notification.Notification
 *  com.intellij.notification.NotificationAction
 *  com.intellij.notification.NotificationGroupManager
 *  com.intellij.openapi.actionSystem.AnAction
 *  com.intellij.openapi.actionSystem.AnActionEvent
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.application.PathManager
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.extensions.PluginId
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.ui.MessageType
 *  org.apache.commons.lang3.StringUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.aicode.updater;

import cn.hutool.core.io.FileUtil;
import com.aicode.agent.dto.LoginInfo;
import com.aicode.agent.service.GitReviewService;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.content.util.OverlayUtils;
import com.aicode.enums.PluginSceneEnum;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.FileUtils;
import com.aicode.util.HandleCacheUtil;
import com.aicode.util.MessageBundle;
import com.aicode.util.PluginInfoUtils;
import com.aicode.util.PositionUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.InstalledPluginsState;
import com.intellij.ide.plugins.PluginInstaller;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.ide.startup.StartupActionScriptManager;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PluginUpdater {
    public static final Logger logger = Logger.getInstance(PluginUpdater.class);
    private static final AtomicReference<String> enum = new AtomicReference();

    public static synchronized void checkUpdate(Project project, JsonObject jsonObject) {
        Project a22 = jsonObject;
        Project a = project;
        if (PluginSceneEnum.saasScene()) {
            return;
        }
        if (!a22.has(GitReviewService.H("4\n5\u001b"))) {
            return;
        }
        try {
            a22 = a22.get(OverlayUtils.H(")\u0001.\u0016")).getAsJsonObject();
            LoginInfo loginInfo = (LoginInfo)new Gson().fromJson((JsonElement)a22, LoginInfo.class);
            String string = loginInfo.getCurrent();
            String string2 = loginInfo.getUpdate();
            String string3 = loginInfo.getFile();
            String string4 = loginInfo.getMd5();
            String string5 = loginInfo.getName();
            CharSequence[] charSequenceArray = new CharSequence[5];
            charSequenceArray[0] = string;
            charSequenceArray[1] = string2;
            charSequenceArray[2] = string3;
            charSequenceArray[3] = string4;
            charSequenceArray[4] = string5;
            if (StringUtils.isAnyBlank((CharSequence[])charSequenceArray)) {
                return;
            }
            if (!StringUtils.equals((CharSequence)string, (CharSequence)string2)) {
                logger.info("[PluginUpdater] ready update " + string2);
                SwingUtilities.invokeLater(() -> {
                    Project a;
                    void a2;
                    void a3;
                    void a4;
                    Project project = a;
                    a = string5;
                    Project a5 = project;
                    PluginUpdater.doUpdate(a5, (String)a4, (String)a3, (String)a2, (String)a);
                });
                return;
            }
        }
        catch (Exception a22) {
            logger.info("[PluginUpdater] update error : " + a22);
        }
    }

    public static void notification(Project project, String string) {
        Object a = string;
        Project a2 = project;
        Object[] objectArray = new Object[1];
        objectArray[0] = a;
        NotificationGroupManager.getInstance().getNotificationGroup(OverlayUtils.H("11\u0016#\u0005,J2\u001e9\t9\u0012")).createNotification(BasicActionsBundle.message(GitReviewService.H("\b2\u00140\u000e~\n(\u00199\t/_\u000e\u00149\u001f\"\u0004\u0003\u001a9\u001f(\u0012\u0002\f?\u001e=X?\u00152\u0005"), new Object[0]), MessageType.INFO).setTitle(BasicActionsBundle.message(OverlayUtils.H("\u0003.\u001e8\u0010t\u0016oH\u00171!G\u0018\u0014&\u0016.\u001e\u0007\b\t97\u001b\u000b\u0013&\u0011,_9\u0005\"\u0003"), new Object[0])).setContent(String.format(MessageBundle.get(GitReviewService.H("!\u0012&\u00114\u000eo\n:\u0018-\u0018%^4\u0004:\u0018.\u0016,\u0012i\u000f0\u001d3\u000e>\u0005e\u001d9\u0016")), objectArray)).addAction((AnAction)new NotificationAction(MessageBundle.get(OverlayUtils.H("-\b*\u000b8\u0014c\u00106\u0002aB\u0016{ \u0006*\u001e#\r \bh\u0018\b3;\u0010?\u0012g\u000b,\u0005$\u000f4F"))){

            private static /* synthetic */ void enum(int a) {
                Object[] objectArray;
                String string = InlineChatStatusServiceKt.H("GQ.\u0019,\u0001%\u001ag\u0004#\u001bm(HL)6-\u0011 I9\r.\u0018 \r.\u001at\u0003_x7F}\u0017)Jd\u0017hF\u000ex5\b?\u001di\u00023\rm\n?_b\\\u0001$");
                Object[] objectArray2 = new Object[3];
                switch (a) {
                    default: {
                        objectArray = objectArray2;
                        objectArray2[0] = PositionUtil.H("$");
                        break;
                    }
                    case 1: {
                        objectArray = objectArray2;
                        while (false) {
                        }
                        objectArray2[0] = InlineChatStatusServiceKt.H("'\u0003(\u0010+\u00019\u001ex@\u0002&");
                        break;
                    }
                }
                objectArray[1] = PositionUtil.H("J6VdT,Q-m\u001c\u0003)H,\\9M*\u0016\u0019B+\u001faw<I-S6L+\u0015s");
                objectArray[2] = InlineChatStatusServiceKt.H("\u001c/\u001d \u00032)(\u001a<\u0010~D\b,");
                throw new IllegalArgumentException(String.format(string, objectArray));
            }

            /*
             * WARNING - void declaration
             */
            public void actionPerformed(@NotNull AnActionEvent anActionEvent, @NotNull Notification notification) {
                void e;
                E e2 = e3;
                E e3 = notification;
                E a = e2;
                if (e == null) {
                    E.enum(0);
                }
                if (e3 == null) {
                    E.enum(1);
                }
                ApplicationManager.getApplication().invokeLater(() -> ApplicationManager.getApplication().restart());
            }
            {
                Object a = string;
                E a2 = this;
                super((String)a);
            }
        }).addAction((AnAction)new NotificationAction(MessageBundle.get(GitReviewService.H("\u0010#7\u0000$\u001ek\u000e<\u001e&\u00138C.\u001e<\u001e<\u0004,\u0012l\n8\u0015$\u00196\r~\u0004=\u0002\"\u001f$C"))){
            {
                Object a = string;
                m a2 = this;
                super((String)a);
            }

            private static /* synthetic */ void enum(int a) {
                Object[] objectArray;
                String string = OpenTelemetryUtil.H("\u000eyg1e)l2.,j3$\u0000\u0001d`\u001ed9iap%g0i%g2=+\u0016P~n4?`b-?!nGP| v5 *z%$\"vw+t`$");
                Object[] objectArray2 = new Object[3];
                switch (a) {
                    default: {
                        objectArray = objectArray2;
                        objectArray2[0] = HandleCacheUtil.H("2");
                        break;
                    }
                    case 1: {
                        objectArray = objectArray2;
                        while (false) {
                        }
                        objectArray2[0] = OpenTelemetryUtil.H("n+a8b)p61hc&");
                        break;
                    }
                }
                objectArray[1] = HandleCacheUtil.H("t'&;T\u0003j9t*-(|7j U\u001d:\u001am+c2\u007f\u001bp;v<$l,f");
                objectArray[2] = OpenTelemetryUtil.H("4f5i+{\u0001a2u87li,");
                throw new IllegalArgumentException(String.format(string, objectArray));
            }

            /*
             * WARNING - void declaration
             */
            public void actionPerformed(@NotNull AnActionEvent anActionEvent, @NotNull Notification notification) {
                void e;
                m m2 = m3;
                m m3 = notification;
                m a = m2;
                if (e == null) {
                    m.enum(0);
                }
                if (m3 == null) {
                    m.enum(1);
                }
                m3.hideBalloon();
            }
        }).notify(a2);
    }

    /*
     * WARNING - void declaration
     */
    public static void doUpdate(Project project, String string, String string2, String string3, String string4) {
        Object a;
        void a2;
        void a3;
        Object a4 = string2;
        Project a5 = project;
        if (StringUtils.equals((CharSequence)enum.get(), (CharSequence)a3)) {
            PluginUpdater.notification(a5, (String)a2);
            return;
        }
        if (!((File)(a4 = new File((String)a4))).exists()) {
            return;
        }
        a = PathManager.getPluginTempPath() + File.separator + (String)a;
        if (!((File)(a = new File((String)a))).exists()) {
            FileUtil.copy((File)a4, (File)a, (boolean)true);
        } else if (((File)a).length() != ((File)a4).length()) {
            FileUtil.copy((File)a4, (File)a, (boolean)true);
        }
        a4 = PluginInfoUtils.AICODE_ID;
        try {
            boolean bl = PluginUpdater.disableOrEnablePlugin(GitReviewService.H("\u0014\u001895\r,\u001e\u0015\u0012%\f(\u0014"), (PluginId)a4);
            if (bl) {
                IdeaPluginDescriptor ideaPluginDescriptor = PluginManager.getInstance().findEnabledPlugin((PluginId)a4);
                Path path = FileUtils.getFileOfPluginPath(OverlayUtils.H(" \"0\b\u000e\u000f>\u0012")).toPath();
                boolean bl2 = PluginUpdater.isOccurred();
                PluginUpdater.installAfterRestart(ideaPluginDescriptor, ((File)a).toPath(), path, !bl2);
                if (bl2) {
                    IdeaPluginDescriptor ideaPluginDescriptor2 = ideaPluginDescriptor;
                    InstalledPluginsState.getInstance().onPluginInstall(ideaPluginDescriptor2, PluginManagerCore.isPluginInstalled((PluginId)ideaPluginDescriptor2.getPluginId()), true);
                } else {
                    InstalledPluginsState.addPreInstalledPlugin((IdeaPluginDescriptor)ideaPluginDescriptor);
                }
                PluginUpdater.disableOrEnablePlugin(GitReviewService.H("\u0014$5\r,\u001e\u0015\u0012%\f(\u0014"), (PluginId)a4);
                AICodeSettingsState.getInstance().isUpdater = true;
                PluginUpdater.notification(a5, (String)a2);
            }
            enum.set((String)a3);
            return;
        }
        catch (Throwable throwable) {
            logger.info("[PluginUpdater] update error : " + throwable);
            return;
        }
    }

    public static void isUpdater(Project a) {
        if (!AICodeSettingsState.getInstance().isUpdater) {
            return;
        }
        NotificationGroupManager.getInstance().getNotificationGroup(OverlayUtils.H("11\u0016#\u0005,J2\u001e9\t9\u0012")).createNotification(BasicActionsBundle.message(GitReviewService.H("\u00118\u001e\u0004:z\u000e)\u0018*\u001a5E\u0004\u001e?\u0019\"\u0004\u0016\u000f\u0005#;\u0001\u0007\t*\u000b E5\u001f.\u0019"), new Object[0]), MessageType.INFO).setTitle(BasicActionsBundle.message(OverlayUtils.H("\u0003.\u001e8\u0010t\u0016oH\u00171!G\u0018\u0014&\u0016.\u001e\u0007\b\t97\u001b\u000b\u0013&\u0011,_9\u0005\"\u0003"), new Object[0])).setContent(BasicActionsBundle.message(GitReviewService.H("\u001b?\u000e\"\u00122B\u0001&!\b)\u0015k\b5\u00192\u00139\u0003"), new Object[0]) + MessageBundle.get(OverlayUtils.H("gB\u001b: \fs\u0000#\u0017&\u0005(E\b <\u00148\u0004g\u0017)\u0012.\u0005)\u0004"))).notify(a);
        AICodeSettingsState.getInstance().isUpdater = false;
    }

    public PluginUpdater() {
        PluginUpdater a;
    }

    /*
     * WARNING - void declaration
     */
    public static void installAfterRestart(@NotNull IdeaPluginDescriptor ideaPluginDescriptor, @NotNull Path path, @Nullable Path path2, boolean bl) throws IOException {
        void v0;
        void deleteSourceFile;
        Path existingPlugin;
        void sourceFile;
        IdeaPluginDescriptor ideaPluginDescriptor2 = ideaPluginDescriptor;
        if (ideaPluginDescriptor2 == null) {
            PluginUpdater.enum(0);
        }
        if (sourceFile == null) {
            PluginUpdater.enum(1);
        }
        ArrayList descriptor = new ArrayList();
        if (existingPlugin != null) {
            descriptor.add(new StartupActionScriptManager.DeleteCommand(existingPlugin));
        }
        if ((existingPlugin = FileUtils.getFileOfPluginPath(GitReviewService.H("(<;\u0015\u0013\u0004!\u001b{\u000f1\u0000")).toPath()) != null) {
            descriptor.add(new StartupActionScriptManager.DeleteCommand(existingPlugin));
        }
        existingPlugin = Paths.get(PathManager.getPluginsPath(), new String[0]);
        if (sourceFile.getFileName().toString().endsWith(OverlayUtils.H("c\n;\u0005"))) {
            v0 = deleteSourceFile;
            void v1 = sourceFile;
            descriptor.add(new StartupActionScriptManager.CopyCommand((Path)v1, existingPlugin.resolve(v1.getFileName())));
        } else {
            descriptor.add(new StartupActionScriptManager.DeleteCommand(existingPlugin.resolve(PluginInstaller.rootEntryName((Path)sourceFile))));
            descriptor.add(new StartupActionScriptManager.UnzipCommand((Path)sourceFile, existingPlugin));
            v0 = deleteSourceFile;
        }
        if (v0 != false) {
            descriptor.add(new StartupActionScriptManager.DeleteCommand((Path)sourceFile));
        }
        logger.info(GitReviewService.H("7\u0000\u00070\u0019!\u001d\u001d\u0003\u0001?6\u001c\"6a\u001b2\t7.5\u00138\u0002\u0013\u0004(\u00137\u00034\u0018"));
        StartupActionScriptManager.addActionCommands((List)descriptor);
    }

    public static boolean disableOrEnablePlugin(String string, PluginId pluginId) {
        block3: {
            Object a = pluginId;
            String a2 = string;
            try {
                Method method;
                Class<?> clazz = Class.forName(GitReviewService.H("#\u0014 X>\u00021\u001b*\u0011!\u0019o\u0013\u000f5x\u001d\r/-\u0018+\rh-$\u0006&\u0013\u001a\u0002)\u001d0\r&\n\u000e\u00198\u0014"));
                Class[] classArray = new Class[1];
                classArray[0] = PluginId.class;
                Method method2 = method = clazz.getMethod(a2, classArray);
                method2.setAccessible(true);
                Object[] objectArray = new Object[1];
                objectArray[0] = a;
                a = (Boolean)method2.invoke(null, objectArray);
                logger.info("[PluginUpdater] " + a2 + " disableOrEnablePlugin: " + (Boolean)a);
                if (a == null || !((Boolean)a).booleanValue()) break block3;
                return true;
            }
            catch (Exception exception) {
                logger.info("[PluginUpdater] " + a2 + " disableOrEnablePlugin error: " + exception);
                return false;
            }
        }
        boolean bl = false;
        return bl;
    }

    public static boolean isOccurred() {
        block3: {
            try {
                Method method;
                Class<?> clazz = Class.forName(OverlayUtils.H("*\u000b1_$\u000e.\u0012jG\u0011?j\r4\u0011(\f.\u001f2\u0002\u001e~\u0014\u001a-\u0005 \n;\"9\u0001.\u0012"));
                Object object = clazz.getDeclaredField(GitReviewService.H("/$\u001d\u00173\t9\u001f>\u00052\u001a\"\u0000>\u0005?"));
                Field field = object;
                field.setAccessible(true);
                object = field.get(null);
                Method method2 = method = object.getClass().getMethod(OverlayUtils.H("%\u0012\u0006\u0007?\u0004?\u0012?\u0013"), new Class[0]);
                method2.setAccessible(true);
                object = (Boolean)method2.invoke(object, new Object[0]);
                logger.info("[PluginUpdater] isOccurred: " + (Boolean)object);
                if (object == null || !((Boolean)object).booleanValue()) break block3;
                return true;
            }
            catch (Exception exception) {
                logger.info("[PluginUpdater] isOccurred error: " + exception);
                return true;
            }
        }
        boolean bl = false;
        return bl;
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray;
        String string = OverlayUtils.H("\u0006\u0018\"\u001dkN'\u0010a\n$\u0014g*\u0002\u000e9.sG1P(\u0014>\u0000$\u0001(\u0014?@}Ru\fX:\"Ix\u0003aG2L+\u001e\u000e$x\u001b#\u0015i\u00069Q#\u00156\u001b");
        Object[] objectArray2 = new Object[3];
        switch (a) {
            default: {
                objectArray = objectArray2;
                objectArray2[0] = GitReviewService.H("2\b2\u00198\u0018!\u001e&\u0000");
                break;
            }
            case 1: {
                objectArray = objectArray2;
                while (false) {
                }
                objectArray2[0] = OverlayUtils.H("?\u000e<\u0016?\u0014\u000b\t6\u0012");
                break;
            }
        }
        objectArray[1] = GitReviewService.H("+\u001c<E\"\u0011.\u0019.\u0014^?$\u000b!\u000f \f\u007f;-\u000f1\u0004//:\u00150\u001e,\u0000");
        objectArray[2] = OverlayUtils.H("\u000b/\u001f2\n\u0011<\u0019\u00138\u0004;69\u00029\u0001(\u0003");
        throw new IllegalArgumentException(String.format(string, objectArray));
    }
}
