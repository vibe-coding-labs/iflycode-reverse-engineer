package com.aicode.updater;

import com.aicode.util.JComponentKt;
import com.aicode.util.Maps;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.updateSettings.impl.PluginDownloader;
import com.intellij.openapi.updateSettings.impl.UpdateChecker;
import com.intellij.openapi.util.BuildNumber;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: yb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/updater/UpdaterCheckerFrom2021_2.class */
public final class UpdaterCheckerFrom2021_2 {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f665enum = Logger.getInstance(UpdaterCheckerFrom2021_2.class);

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m389enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = Maps.H("%*\u0014:Bv:\u001cQ+\n+@\u001c;&\u0010\u0016\u0006#.^\u00194\u00169\u000f;\u001e3\u0016xTj4\\S \brI#\u001d*B-^zCx~\f\\{D:\ns\u001a=��<");
                i = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                H = JComponentKt.H("/\u0007+\u0016\u0007\u001a#\u0005g\f,\u001b6\u0017\u000el{\u000bm@2GsMoN>VrOS'{L<\u001d,D7\n-\u000b");
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
                objArr[0] = Maps.H("f\n<\u00060\u0015<\u0003\"");
                i3 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = JComponentKt.H("\u001b��$k\u0003 \f \r\"N<\u001f:\u0019\u001e),W\u0016\u0015%\u0006j]nyv]~P\u0016'XJ&\u0002pTkN\u001eU");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = Maps.H(",-\u0013F4\r;\r:\u000fy\u0011(\u0017.3\u001e\u0001`;\"\b1GjCN[jSg;\u0010u}\u000b5]cFy3b");
                i4 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = JComponentKt.H("xQr^_N|R\u001f4|T,:2��8\u000b$\u0014");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = Maps.H("Uf_iryQe2\u0003Qc\u0001\r\u001f7\u0015<\t#");
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
            m389enum(0);
        }
        try {
            Object invoke = UpdateChecker.class.getMethod(JComponentKt.H("'\u00039\" \u001c%\u0014'\u000e\u0015\u000f0\u000f/\u0007#>(\u001a(\u001b:\n"), BuildNumber.class, ProgressIndicator.class).invoke(null, null, indicator);
            Object invoke2 = invoke.getClass().getMethod(Maps.H("\u0003= 8\u001d8\u00020\u000e\t\u0005-\u0005,\u0017="), new Class[0]).invoke(invoke, new Object[0]);
            Object invoke3 = invoke2.getClass().getMethod(JComponentKt.H("89\u000e\t\u0002!.6\u001f+\u0003:\u001d"), new Class[0]).invoke(invoke2, new Object[0]);
            Collection<PluginDownloader> emptyList = invoke3 == null ? Collections.emptyList() : (Collection) invoke3;
            if (emptyList == null) {
                m389enum(1);
            }
            return emptyList;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            f665enum.error(e);
            List emptyList2 = Collections.emptyList();
            if (emptyList2 == null) {
                m389enum(2);
            }
            return emptyList2;
        }
    }
}
