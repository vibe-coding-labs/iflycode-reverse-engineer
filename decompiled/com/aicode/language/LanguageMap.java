package com.aicode.language;

import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.util.JComponentKt;
import com.intellij.lang.Language;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/language/LanguageMap.class */
public final class LanguageMap {

    /* renamed from: enum, reason: not valid java name */
    private static final Map<String, String> f488enum = Collections.unmodifiableMap(new s());

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/language/LanguageMap$s.class */
    class s extends ConcurrentHashMap<String, String> {
    }

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m231enum(int a) {
        throw new IllegalArgumentException(String.format(JComponentKt.H("\f\u0019\"\u0016\"\f-\u0011h\b&\u001d\"d\u000e\t9%7\b'M)\u001e?\n&\b=\np\u0004Gc>Lb\u000b-M|\fcN8M$\u001a\u00123m\u00053\u000ei\r'D7\n-\u000b"), OpenTelemetryUtil.H("h!B\u000fu%v0"), JComponentKt.H("G\u000f+b\n+\u0007$\t<P!\n%\n<\u000e\u0006\"b'=\u0014.\u001a#\u0003<2 \u0017"), OpenTelemetryUtil.H("\u000fe0X1")));
    }

    @Nullable
    public static String getId(@NotNull Language language) {
        if (language == null) {
            m231enum(0);
        }
        return f488enum.get(language.getID());
    }
}
