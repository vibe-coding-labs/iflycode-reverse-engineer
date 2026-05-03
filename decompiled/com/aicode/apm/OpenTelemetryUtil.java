/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.opentelemetry.api.GlobalOpenTelemetry
 *  io.opentelemetry.api.trace.Span
 *  io.opentelemetry.api.trace.SpanBuilder
 *  io.opentelemetry.api.trace.SpanKind
 *  io.opentelemetry.api.trace.Tracer
 *  io.opentelemetry.context.Context
 *  io.opentelemetry.context.ImplicitContextKeyed
 *  org.apache.commons.lang3.StringUtils
 */
package com.aicode.apm;

import com.aicode.apm.enums.TracerEnum;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.ImplicitContextKeyed;
import org.apache.commons.lang3.StringUtils;

public class OpenTelemetryUtil {
    /*
     * WARNING - void declaration
     */
    public static Span buildWithParent(Span span, TracerEnum tracerEnum, String string) {
        void a;
        Object a2 = string;
        Span a3 = span;
        return OpenTelemetryUtil.eF(a3, "", (TracerEnum)a, SpanKind.INTERNAL, (String)a2);
    }

    public OpenTelemetryUtil() {
        OpenTelemetryUtil a;
    }

    public static Span buildWithCommand(String string, String string2) {
        String a = string2;
        String a2 = string;
        return OpenTelemetryUtil.eF(null, a2, null, SpanKind.CLIENT, a);
    }

    public static Span buildWithTracer(TracerEnum tracerEnum, String string) {
        Object a = string;
        TracerEnum a2 = tracerEnum;
        return OpenTelemetryUtil.eF(null, "", a2, SpanKind.CLIENT, (String)a);
    }

    /*
     * WARNING - void declaration
     */
    private static Span eF(Span span, String string, TracerEnum tracerEnum, SpanKind spanKind, String string2) {
        void a;
        SpanBuilder spanBuilder;
        Span a2;
        void a3;
        Tracer a4;
        Span span2 = span;
        a4 = GlobalOpenTelemetry.getTracer((String)a4);
        if (StringUtils.isNotBlank((CharSequence)a3)) {
            a2 = a4.spanBuilder((String)a3);
            spanBuilder = a2;
        } else {
            void a5;
            a2 = a4.spanBuilder(a5.getText());
            spanBuilder = a2;
        }
        spanBuilder.setSpanKind((SpanKind)a);
        if (span2 != null) {
            a2.setParent(Context.current().with((ImplicitContextKeyed)span2));
        }
        return a2.startSpan();
    }

    public static String H(Object object) {
        int a;
        Object object2 = object;
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String string = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        object2 = (String)object2;
        int n = ((String)object2).length();
        int n2 = n - 1;
        char[] cArray = new char[n];
        int n3 = (3 ^ 5) << 4 ^ 1;
        int cfr_ignored_0 = 2 << 3 ^ 4;
        int n4 = 4 << 3 ^ 5;
        int n5 = a = string.length() - 1;
        int n6 = n2;
        String string2 = string;
        while (n6 >= 0) {
            int n7 = n2--;
            cArray[n7] = (char)(n4 ^ (((String)object2).charAt(n7) ^ string2.charAt(a)));
            if (n2 < 0) break;
            int n8 = n2--;
            char c = cArray[n8] = (char)(n3 ^ (((String)object2).charAt(n8) ^ string2.charAt(a)));
            if (--a < 0) {
                a = n5;
            }
            n6 = n2;
        }
        return new String(cArray);
    }
}
