package com.aicode.inline.status;

import com.aicode.util.PositionUtil;
import com.aicode.util.PropertyUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: hk */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/status/InlineChatStatusServiceKt.class */
public final class InlineChatStatusServiceKt {
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m218enum(int a) {
        throw new IllegalStateException(String.format(PropertyUtils.H("\fU(d/C&qcy3u\u0019I2!fgx$$ <s\u0002Rg~?stq.h4d)0\u0007K:m"), PositionUtil.H("Q-Tf~\u0006W+Y(\u00071a\u0013A6Xb[,H-Z, 6W%G0O\u0019]$K\u001cM(c\u0012[\u000b\\;D+J<z5"), PropertyUtils.H("H9l8h\u0014e/q$T b?i2E\"b\u001fW5d")));
    }

    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = ((3 ^ 5) << 4) ^ ((2 << 2) ^ 3);
        int i2 = (1 << 3) ^ 5;
        int i3 = 5 << 3;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i4 = length2 - 1;
        int i5 = i4;
        int i6 = length;
        while (i4 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (i2 ^ (str.charAt(i7) ^ stringBuffer.charAt(i6)));
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

    @NotNull
    public static InlineChatStatusService InlineChatStatusService() {
        InlineChatStatusService inlineChatStatusService = InlineChatStatusServiceProvider.INSTANCE.get();
        if (inlineChatStatusService == null) {
            m218enum(0);
        }
        return inlineChatStatusService;
    }
}
