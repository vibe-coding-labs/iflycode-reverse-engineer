package com.aicode.service.editor;

import com.aicode.agent.service.GitReviewService;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.intellij.openapi.Disposable;
import com.intellij.util.Alarm;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

/* compiled from: yc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/CancelRequestTip.class */
public class CancelRequestTip {

    /* renamed from: byte, reason: not valid java name */
    private final Object f561byte;

    /* renamed from: enum, reason: not valid java name */
    private final Alarm f562enum;

    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).insert(0, stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (3 << 3) ^ 3;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i2 = length2 - 1;
        int i3 = i2;
        int i4 = length;
        while (i2 >= 0) {
            int i5 = i3;
            int i6 = i3 - 1;
            cArr[i5] = (char) (4 ^ (str.charAt(i5) ^ stringBuffer.charAt(i4)));
            if (i6 < 0) {
                break;
            }
            char charAt = (char) (4 ^ (str.charAt(i6) ^ stringBuffer.charAt(i4)));
            i3 = i6 - 1;
            i4--;
            cArr[i6] = charAt;
            if (i4 < 0) {
                i4 = length;
            }
        }
        return new String(cArr);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m277enum(int a) {
        String H = GitReviewService.H("\f\u0004 \t,\u001fdEa\u001c/\tm6\u001e\u0004?>#\u0001f\u0011\u0017=7\u001f'\u00143\u00193ZoV\u0005ja\u00153Nt\u0019o_$L=\u001e\u0003?m\u0018;\u001ba\u0018/Q?\u001f%\u001e");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = FileExtensionLanguageDetails.H("abgbkcWhgv[Uhy|g");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = GitReviewService.H("\b/��$\u000f:\u0006");
                break;
        }
        objArr[1] = FileExtensionLanguageDetails.H("jty)ntqo*9\fBdaxu`t*rhwFOw8Rb{d`{AdesQU}Oyr");
        switch (a) {
            case 0:
            default:
                objArr[2] = GitReviewService.H("v\u0018?\u0003=L");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = FileExtensionLanguageDetails.H("o\u007f\\C`{PoyFksRepTQW|~cv");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public CancelRequestTip(@NotNull Disposable a) {
        if (a == null) {
            m277enum(0);
        }
        this.f561byte = new Object();
        this.f562enum = new Alarm(Alarm.ThreadToUse.POOLED_THREAD, a);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @TestOnly
    public void ab(int a, TimeUnit a2) throws ExecutionException, InterruptedException, TimeoutException {
        synchronized (this.f561byte) {
            this.f562enum.waitForAllExecuted(a, a2);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void lA() {
        synchronized (this.f561byte) {
            this.f562enum.cancelAllRequests();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void cancelAllAndAddRequest(@NotNull Runnable request, int a) {
        if (request == null) {
            m277enum(1);
        }
        synchronized (this.f561byte) {
            this.f562enum.cancelAllRequests();
            this.f562enum.addRequest(request, a);
        }
    }
}
