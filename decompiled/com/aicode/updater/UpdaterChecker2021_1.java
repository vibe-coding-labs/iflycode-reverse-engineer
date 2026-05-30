package com.aicode.updater;

import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.util.NewFileUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.updateSettings.impl.PluginDownloader;
import com.intellij.openapi.updateSettings.impl.UpdateChecker;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: db */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/updater/UpdaterChecker2021_1.class */
public final class UpdaterChecker2021_1 {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f664enum = Logger.getInstance(UpdaterChecker2021_1.class);

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m388enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = NewFileUtils.H("\u007f\u001eM\rB\u0018T\u001c\u000b\u001fS\u001c@rU&J\"_\u0014C]J\tY\u0018Q\u000by:TT\f\\^X\u0005\u0018MY\u0019\u001dR\u000b\r\f\u0011[\fY1-\u0010Y\u000b\u001bERU\u001cO\u001d");
                i = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                H = MethodGeneratorConfig.H("\u0015\u0015/:\u001f**$W\u00149&903yz\"\u007fz5hk}w~&fj\u007fK\u0017`\u007f$-4t/:5;");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            default:
                i2 = 3;
                break;
            case 1:
            case 2:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = NewFileUtils.H("DE\u001dI\u0011Z\u001dL\u0003");
                i3 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = MethodGeneratorConfig.H("->2i)\u001e\u001a364p\");0%:4gSx`krmwH\u0003��f`4-hds~\u0006f");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = NewFileUtils.H("\u000bD\u0014\u0013\u000fd<I\u0010NVX\u000fA\u0016_\u001cNA)^\u001aM\bK\rny&\u001cFN\u000b\u0012B\tX|@");
                i4 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = MethodGeneratorConfig.H("`ajnG~db\u0007\u0004gg4\n*0 ;<$");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = NewFileUtils.H("\u001aG\u0010H=X\u001eD}\"\u001dAN,P\u0016Z\u001dF\u0002");
                break;
            case 1:
            case 2:
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            default:
                throw new IllegalArgumentException(format);
            case 1:
            case 2:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    public static Collection<PluginDownloader> findAvailableUpdates(@NotNull ProgressIndicator indicator) {
        if (indicator == null) {
            m388enum(0);
        }
        try {
            Object invoke = UpdateChecker.class.getMethod(NewFileUtils.H("A\u0018N\u001ap\u0019R\u0019M\u0011A\u000eo\u0018O\u0018I\n"), ProgressIndicator.class).invoke(null, indicator);
            Object invoke2 = invoke.getClass().getDeclaredMethod(MethodGeneratorConfig.H("?=\"\u0010)��\u0006(+220\u000e0*0+\":"), new Class[0]).invoke(invoke, new Object[0]);
            Collection<PluginDownloader> emptyList = invoke2 == null ? Collections.emptyList() : (Collection) invoke2;
            if (emptyList == null) {
                m388enum(1);
            }
            return emptyList;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            f664enum.error(e);
            List emptyList2 = Collections.emptyList();
            if (emptyList2 == null) {
                m388enum(2);
            }
            return emptyList2;
        }
    }
}
