package com.aicode.language;

import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.inline.controller.ChatInputController;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/* compiled from: xi */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/language/CommonLanguageSupport.class */
public final class CommonLanguageSupport {

    /* renamed from: enum, reason: not valid java name */
    private static final Pattern f486enum = Pattern.compile(ChatInputController.H("<2\u0014A<B\u0005(8KD\u000fv\r(\u000bQ,D\tBY#M?\u001c\u0003\u0001"));

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m227enum(int a) {
        throw new IllegalArgumentException(String.format(OpenTelemetryUtil.H("\u000eyj<m!a?&${\" \u0004H-p\u000e:gNF~+~)a-z/}k\nLsc/$`b1#.aubi5A\u00024>~!17kj}\"y="), ChatInputController.H("\u000f\u0006\u001c\u001b*��\u0004\b\u0013\u000e"), OpenTelemetryUtil.H("\u0005a'#)e+a.jdA\bn#z*a';\u0013o)k-j\fS\u0018s%p2t\u0006{:c8g%"), ChatInputController.H("\u000b\u001d \u001b\u001c\u0015\u00163-,\u0006\u0002\u0002$\u0001?\u0010\u0011)��\r\n\"\u0011\n\u001c\u0016\u0007\u0015\u0018")));
    }

    public static boolean isValidMiddleOfTheLinePosition(@NotNull String lineSuffix) {
        if (lineSuffix == null) {
            m227enum(0);
        }
        return f486enum.matcher(lineSuffix.trim()).matches();
    }
}
