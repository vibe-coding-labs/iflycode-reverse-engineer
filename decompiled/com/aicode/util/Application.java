package com.aicode.util;

import com.aicode.agent.service.CodeCompleteService;
import com.aicode.diff.GenericUtils;
import com.intellij.openapi.application.ApplicationManager;
import java.util.concurrent.Future;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

/* compiled from: tb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/Application.class */
public final class Application {
    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m393enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 2:
            case 3:
            default:
                H = CodeCompleteService.H("nxDsLa\f3\rnJr\u000fJmi\\C\\`\u000egI}J|HeT`\u0010g*\rO>\u001cvF%��s\u0001/^(UhVt\u0003hMs\tnG'WiMh");
                i = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                H = GenericUtils.H("X^,?\f?3;z?}d\u001f\u0010\"nc=tw,w8($+b$0#y#=$&)6p->7?");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 2:
            case 3:
            default:
                i2 = 3;
                break;
            case 1:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 3:
            default:
                objArr[0] = CodeCompleteService.H("[yLiX~Ma");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = GenericUtils.H("-)#u364:92p7>6;v\u0010( ?2;17\"4=");
                i3 = a;
                break;
            case 2:
                objArr[0] = CodeCompleteService.H("a\\bAsPsO4");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 2:
            case 3:
            default:
                objArr[1] = GenericUtils.H("-)#u364:92p7>6;v\u0010( ?2;17\"4=");
                i4 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = CodeCompleteService.H("zMsjnsiMkLhvoKy@`");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = GenericUtils.H("-7$\u00109\t>7<6?\f81.:7");
                break;
            case 1:
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = CodeCompleteService.H("[yLHWYEp");
                break;
            case 3:
                objArr[2] = GenericUtils.H("+$6\u001f=\u001e<$\t*-2");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 2:
            case 3:
            default:
                throw new IllegalArgumentException(format);
            case 1:
                throw new IllegalStateException(format);
        }
    }

    public static /* synthetic */ String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = ((3 ^ 5) << 4) ^ 3;
        int i2 = 5 << 4;
        int i3 = (3 ^ 5) << 4;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i4 = length2 - 1;
        int i5 = i4;
        int i6 = length;
        while (i4 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (i ^ (str.charAt(i7) ^ stringBuffer.charAt(i6)));
            if (i8 < 0) {
                break;
            }
            char charAt = (char) (i3 ^ (str.charAt(i8) ^ stringBuffer.charAt(i6)));
            i5 = i8 - 1;
            i6--;
            cArr[i8] = charAt;
            if (i6 < 0) {
                i6 = length;
            }
        }
        return new String(cArr);
    }

    public static /* synthetic */ void runOnEdtJava(@NotNull Runnable runnable) {
        if (runnable == null) {
            m393enum(3);
        }
        ApplicationManager.getApplication().invokeLater(runnable);
    }

    public static /* synthetic */ void runOnEdt(@NotNull Function0<Unit> function0) {
        if (function0 == null) {
            m393enum(2);
        }
        ApplicationManager.getApplication().invokeLater(() -> {
            function0.invoke();
        });
    }

    @NotNull
    public static /* synthetic */ Future<?> runOnPooledThread(@NotNull Runnable runnable) {
        if (runnable == null) {
            m393enum(0);
        }
        Future<?> executeOnPooledThread = ApplicationManager.getApplication().executeOnPooledThread(runnable);
        if (executeOnPooledThread == null) {
            m393enum(1);
        }
        return executeOnPooledThread;
    }
}
