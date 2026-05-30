package com.aicode.updater;

import com.aicode.agent.service.CodeCompleteService;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.inline.ide.IdeAction;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.PluginInfoUtils;
import com.aicode.util.PropertyUtils;
import com.aicode.util.ReflectUtil;
import com.aicode.util.StringUtils;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.updateSettings.impl.PluginDownloader;
import com.intellij.util.concurrency.AppExecutorUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: hb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/updater/PluginUpdaterCheckService.class */
public class PluginUpdaterCheckService {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f661enum = Logger.getInstance(PluginUpdaterCheckService.class);

    /* renamed from: float, reason: not valid java name */
    private static final Object f659float = new Object();

    /* renamed from: final, reason: not valid java name */
    private static volatile boolean f657final = false;

    /* renamed from: try, reason: not valid java name */
    private static volatile boolean f658try = false;

    /* renamed from: byte, reason: not valid java name */
    private static volatile String f660byte = "";

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m384enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 4:
            default:
                H = ConditionalActionConfiguration.H(".\u0018TC -\u001f��H\u000b\u0015\rT1=\u0019<\u0003\u0018\u0004\u0015\\\f\u0018\u001b\r\u0015\u0018\u001b\u000f,{RU\u000bZ^\u0014\u0010Sk8VX\u001cJ\u0006\u001b\u0007\u0005^\u0015\u0017\tX\u001f\u0016V\u0006\u0018\u001c\u0019");
                i = a;
                break;
            case 2:
            case 3:
                do {
                } while (0 != 0);
                H = IdeAction.H("#f\u000b[\u0011a\u0016]NH\u000eT\u0016Z\u000b\u0004]@g'\u0011\t\u0002Q\u001aVAD6fOV\u001dG\tE\r\b\u0007W\u0003H");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 4:
            default:
                i2 = 3;
                break;
            case 2:
            case 3:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = ConditionalActionConfiguration.H("\r\u0001\u0019\u0002\b\u0013\u0001");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = IdeAction.H("Z\u0012S\nK\bV��V");
                i3 = a;
                break;
            case 2:
            case 3:
                objArr[0] = ConditionalActionConfiguration.H("\u000e\u0007\u0014S\u001d\u0010\n\u0003\u001c\u0018@\u001f.?\u0014\u0004\u001d\u000fQ+\u001a\u0006)\"\u0016(\u001f\u000e\n\u001a\u0011\u0003=\u0013\u001d\u001e\u0013.\u0016\u0004\u001e\u0004\u0013\u0010");
                i3 = a;
                break;
            case 4:
                objArr[0] = IdeAction.H("��\\8{\u0003E\u001a_\u0019b\u0013L\bV\nW");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 4:
            default:
                objArr[1] = ConditionalActionConfiguration.H("\u000e\u0007\u0014S\u001d\u0010\n\u0003\u001c\u0018@\u001f.?\u0014\u0004\u001d\u000fQ+\u001a\u0006)\"\u0016(\u001f\u000e\n\u001a\u0011\u0003=\u0013\u001d\u001e\u0013.\u0016\u0004\u001e\u0004\u0013\u0010");
                i4 = a;
                break;
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[1] = IdeAction.H("\tM\u0007F \\8{\u0003E\u001a_\u0019b\u0013L\bV\nW");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = ConditionalActionConfiguration.H("\u001a\u001b\u0011\u0004\u001b.\b\u0019\u0019\t\u00165��\b\u0013\u001e");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = IdeAction.H("\tM\u0007F \\8{\u0003E\u001a_\u0019b\u0013L\bV\nW");
                break;
            case 2:
            case 3:
                break;
            case 4:
                objArr[2] = ConditionalActionConfiguration.H("\u001e\u0014\u0016\u0019&\u0006\f\f\u0004\u0010");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 4:
            default:
                throw new IllegalArgumentException(format);
            case 2:
            case 3:
                throw new IllegalStateException(format);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: hb */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/updater/PluginUpdaterCheckService$k.class */
    public class k extends Task.Backgroundable {

        /* renamed from: byte, reason: not valid java name */
        public final /* synthetic */ Project f662byte;

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ PluginDownloader f663enum;

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m386enum(int a) {
            String H = CodeCompleteService.H("]KGpTyEz\u0005fMu9|rv\\CXdT=YmLzbOPd[,\b/T%?UO,\u001bh\u0014:V B\u007fZxH#Hv\tnG'WiMh");
            Object[] objArr = new Object[3];
            switch (a) {
                case 0:
                default:
                    objArr[0] = OpenTelemetryUtil.H("%l\"m#$ue<");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = CodeCompleteService.H("bKnNv");
                    break;
            }
            objArr[1] = OpenTelemetryUtil.H("k#bdU\u0019r:a$/1e5e4v%\r6e8c)l\u0013z*S\u0002a2P?r0c\u001fg4r)&d.\u007f");
            switch (a) {
                case 0:
                default:
                    objArr[2] = CodeCompleteService.H("nTj");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[2] = OpenTelemetryUtil.H("<f\u0018j4k7$cf+");
                    break;
            }
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public k(Project a, String a2, boolean z, Project project, PluginDownloader pluginDownloader) {
            super(a, a2, z);
            this.f662byte = project;
            this.f663enum = pluginDownloader;
        }

        public void onThrowable(@NotNull Throwable a) {
            if (a == null) {
                m386enum(1);
            }
            PluginUpdaterCheckService.f658try = false;
            super.onThrowable(a);
        }

        public void onCancel() {
            PluginUpdaterCheckService.f658try = false;
            super.onCancel();
        }

        /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
        public void run(@NotNull ProgressIndicator a) {
            if (a == null) {
                m386enum(0);
            }
            PluginUpdaterCheckService.f658try = true;
            try {
                try {
                    PluginUpdaterCheckService.t(this.f662byte, this.f663enum, a);
                    PluginUpdaterCheckService.f658try = false;
                } catch (Throwable th) {
                    PluginUpdaterCheckService.f661enum.warn(OpenTelemetryUtil.H("S0m,v3\u0002\u0012fmg(g%anT\u0019v`c;b4a\"\"3t$$uo="), th);
                    PluginUpdaterCheckService.f658try = false;
                }
            } catch (Throwable th2) {
                PluginUpdaterCheckService.f658try = false;
                throw th2;
            }
        }
    }

    private static void X(Project a, PluginDownloader a2) {
        new k(a, BasicActionsBundle.message(ConditionalActionConfiguration.H("\u0019\u0014\f\u0005:>[\u0005\b\u0019\u001f\u000f\u0013]'%\u000b\t\u000e\u0006\u0007\u0007\u001a\u0016P\u000f\u0011\t)%"), BasicActionsBundle.message(IdeAction.H("V\u001cJ\u001ePPT\u0006G\u0017W,,'M\u0006P\u0006P I-{��J?A\u0013B\u0013\u0006\u001dG\u0017P"), new Object[0])), true, a, a2).queue();
    }

    public static void scheduleRepeatedUpdateCheck(Project a) {
        AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(() -> {
            if (a != null && !a.isDisposed() && AICodeSettingsState.getInstance().openAutoUpdate) {
                queueUpdateCheck(a);
            }
        }, 0L, 24L, TimeUnit.HOURS);
    }

    /* compiled from: hb */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/updater/PluginUpdaterCheckService$CheckUpdatesTask.class */
    public static final class CheckUpdatesTask extends Task.Backgroundable {
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m385enum(int a) {
            String H = PropertyUtils.H("\u0006b7r\fS$igv.di^?I3^%k8#;}3w*ur4\u0013\u0016m847ay/>RS| 51.a%ug~>rVC&4?s%r");
            Object[] objArr = new Object[3];
            switch (a) {
                case 0:
                default:
                    objArr[0] = PropertyUtils.H("Q1{;c*j");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = PropertyUtils.H("o\u0018E*w0r&l");
                    break;
                case 2:
                    objArr[0] = PropertyUtils.H("q#t&l");
                    break;
            }
            objArr[1] = PropertyUtils.H("s?jNW#~(t$9<n\u0015G3u\"(\u0004o>{(x\u0012`b0\u0015S8^/u\"}\u001a{\u0005V;f#5��|3b,E!b\u0017U&g\u0005g:u");
            switch (a) {
                case 0:
                default:
                    objArr[2] = PropertyUtils.H("\u007f}?o= ");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[2] = PropertyUtils.H("t<p");
                    break;
                case 2:
                    objArr[2] = PropertyUtils.H("\u007f?R\u001eS,c0d%{");
                    break;
            }
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        public void onThrowable(@NotNull Throwable a) {
            if (a == null) {
                m385enum(2);
            }
            PluginUpdaterCheckService.f657final = false;
            super.onThrowable(a);
        }

        public void onCancel() {
            PluginUpdaterCheckService.f657final = false;
            super.onCancel();
        }

        /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
        public void run(@NotNull ProgressIndicator a) {
            if (a == null) {
                m385enum(1);
            }
            try {
                if (this.myProject.isDisposed()) {
                    return;
                }
                try {
                    PluginDownloader N = PluginUpdaterCheckService.N(PluginUpdaterCheckService.O(a));
                    if ((N != null) && !StringUtils.equals(PluginUpdaterCheckService.f660byte, N.getPluginVersion())) {
                        StartupManager.getInstance(this.myProject).runWhenProjectIsInitialized(() -> {
                            PluginUpdaterCheckService.X(this.myProject, N);
                        });
                    }
                    PluginUpdaterCheckService.f657final = false;
                } catch (Exception e) {
                    PluginUpdaterCheckService.f661enum.warn(PropertyUtils.H("@0\bZ/ygd.6*v\u0012C9% ~14&m2w8hVT3p0r,m"), e);
                    PluginUpdaterCheckService.f657final = false;
                }
            } catch (Throwable th) {
                PluginUpdaterCheckService.f657final = false;
                throw th;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CheckUpdatesTask(@NotNull Project a) {
            super(a, "正在检查 " + BasicActionsBundle.message(PropertyUtils.H("wt>\u0014Fd|.s.r,02D;q)c\u0002w\"h(~\u0016t\u0019T3:%c1j"), new Object[0]) + " 最新版本...", true);
            if (a == null) {
                m385enum(0);
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void queueUpdateCheck(@NotNull Project project) {
        if (project == null) {
            m384enum(0);
        }
        synchronized (f659float) {
            if (!f657final && !f658try) {
                f657final = true;
                new CheckUpdatesTask(project).queue();
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static void t(Project a, PluginDownloader a2, ProgressIndicator a3) {
        try {
            f661enum.info("start installing:" + a2.getPluginVersion());
            Class<?> classForName = ReflectUtil.classForName(ConditionalActionConfiguration.H("\f\u0005^\u0018$&\u0005\u0011\u0004\u0001\u0013\u0015Z\u001e\u0003\u0013&,\u001d\u0001W\t\f\u001d\b\u0018\u001d.\n\u001e*2\u001b\u0017\u000bS\u0017\u0016\u0006\u001f`\u001e\b\u0019\u000e\u001e\u000e'\u001a\u0002\n\u001a\u0014\u0011\f\u001e"));
            if (classForName == null) {
                return;
            }
            Boolean bool = (Boolean) ReflectUtil.getMethod(classForName, IdeAction.H("\u0006J\u001aV��F5B\u0003Q\u001fZ\u0012b\u0013L\bV\nW"), Collection.class, ProgressIndicator.class).invoke(null, Collections.singletonList(a2), a3);
            f661enum.info("finished installing:" + bool);
            if (bool == null || !bool.booleanValue()) {
                return;
            }
            PluginUpdater.notification(a, a2.getPluginVersion());
            f660byte = a2.getPluginVersion();
        } catch (Exception e) {
            f661enum.warn("install update failed:" + a2.getPluginVersion(), e);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    private static Collection<PluginDownloader> O(@NotNull ProgressIndicator indicator) {
        if (indicator == null) {
            m384enum(1);
        }
        if (ApplicationInfo.getInstance().getBuild().getBaselineVersion() <= 211) {
            Collection<PluginDownloader> findAvailableUpdates = UpdaterChecker2021_1.findAvailableUpdates(indicator);
            if (findAvailableUpdates == null) {
                m384enum(2);
            }
            return findAvailableUpdates;
        }
        Collection<PluginDownloader> findAvailableUpdates2 = UpdaterCheckerFrom2021_2.findAvailableUpdates(indicator);
        if (findAvailableUpdates2 == null) {
            m384enum(3);
        }
        return findAvailableUpdates2;
    }

    @Nullable
    private static PluginDownloader N(@NotNull Collection<PluginDownloader> collection) {
        if (collection == null) {
            m384enum(4);
        }
        return collection.stream().filter(a -> {
            return PluginInfoUtils.AICODE_ID.equals(a.getId());
        }).findFirst().orElse(null);
    }
}
