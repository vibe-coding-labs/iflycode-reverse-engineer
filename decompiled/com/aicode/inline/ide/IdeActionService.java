package com.aicode.inline.ide;

import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.util.Maps;
import java.util.List;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: si */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/ide/IdeActionService.class */
public final class IdeActionService {

    @NotNull
    public static final IdeActionService INSTANCE = new IdeActionService();

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m205enum(int a) {
        throw new IllegalStateException(String.format(OpenTelemetryUtil.H("\u000fEg8K4h,ofM\u0018m.``\u0005\u0017,cfqe9}>/%]\u0002$2v#b!fll3h,"), Maps.H("\u00066\tw!\u0015\u00011\u0011,G=��>\u0006=7A\r<\u0016`>/\r\u0015\u0001*\r7\n\u000b\n!\u0002!\u000f5"), OpenTelemetryUtil.H("\u0011a4Z3r\u0012k8k)j3")));
    }

    private IdeActionService() {
    }

    @NotNull
    public List<IdeAction> getIdeActions() {
        List<IdeAction> wa = wa();
        if (wa == null) {
            m205enum(0);
        }
        return wa;
    }

    private List<IdeAction> wa() {
        return CollectionsKt.listOf(new IdeAction[]{new IdeAction(Maps.H("\u001d\u0017&\u0003$\u001a\u0016\u0003=\u000f\u000b\u0006+\u00039"), null, 2), new IdeAction(OpenTelemetryUtil.H("A$z#x!L)n#p%"), null, 2), new IdeAction(Maps.H("\u0011\u00067\u00107\u0004\t\u0015("), ActionScope.INLINE_CHAT_FOCUSED), new IdeAction(OpenTelemetryUtil.H("V3~'g>A)t9"), ActionScope.INLINE_CHAT_FOCUSED), new IdeAction(Maps.H("\u000e\f=\u00161\u0016\b\u00179\u00149"), ActionScope.INLINE_CHAT_FOCUSED), new IdeAction(OpenTelemetryUtil.H("A$z#x!M?a't%"), ActionScope.INLINE_CHAT_OPENED), new IdeAction(Maps.H("\u000e\f=\u00161\u0016\u001d\u0018>\u0005."), null, 2), new IdeAction(OpenTelemetryUtil.H("\u0012s:|#p\u0012e\""), null, 2), new IdeAction(Maps.H("!<\u001a;\u00189,1\u000e;\u0010=:#\u000e9"), null, 2), new IdeAction(OpenTelemetryUtil.H("\u0003q8|#|\u000ej'W\u0002a\u0014|��x!l\u001fv'v4"), null, 2), new IdeAction(Maps.H("\u0017\u000b:&\u0001\u0016\u001c\u0016#\u0012?\r��\r\t\u000b*\u0012\u000f\u000e8"), null, 2), new IdeAction(OpenTelemetryUtil.H("V3~'g>N#b4"), null, 2), new IdeAction(Maps.H("\u000e\f=\u00161\u0016\n\u001f-\b("), null, 2), new IdeAction(OpenTelemetryUtil.H("R7a8m4Q0"), null, 2), new IdeAction(Maps.H("-0\u000b*\u000b*2%\u00172"), null, 2), new IdeAction(OpenTelemetryUtil.H("K.f?]\u0004W4r%c\u001dm;N/j%"), null, 2), new IdeAction(Maps.H("\u0017\u000b:&\u0001\u0016\u0011\u001d+\u0012%\u001c\u0007\u00072\u0001;\u0002#\u000f2"), null, 2), new IdeAction(OpenTelemetryUtil.H("\u0003q8|#|\u001fa\"\\\u0012a.g\u0004r?m/v/k."), null, 2), new IdeAction(Maps.H("\u0011\n;\u001b< *\u000b/\u001d\u0018\u001e?��\u0007\u00072\u0001;\u0002#\u000f2"), null, 2), new IdeAction(OpenTelemetryUtil.H("\u0014l%z%}\u001eB!m4{\u0004r?m/v/k."), null, 2), new IdeAction(Maps.H("\u0011\n;\u001b< \"\u0001>\u0007\u0018\u001e?��\u0007\u00072\u0001;\u0002#\u000f2"), null, 2), new IdeAction(OpenTelemetryUtil.H("G\"|%g>\\#h#F!m4{\u0004r?m/v/k."), null, 2), new IdeAction(Maps.H("\r\u0010\f-\u000b*\u0002\u001d\u00015\u0002(\u001a0>3\u001d25\u001c\u0005(\u001b\u0018\u001e?��\u0007\u00072\u0001;\u0002#\u000f2"), null, 2), new IdeAction(OpenTelemetryUtil.H("\u0005+b\\\u0003w\u0007k2W\u0005p\"E0z-i8n;Z!m4{\u0004r?m/v/k."), null, 2), new IdeAction(Maps.H("\u001b\u0011 \u001c;\u001c\u0002\u000e47*\u000b/\u001d\u0018\u001e?��\u0007\u00072\u0001;\u0002#\u000f2"), null, 2), new IdeAction(OpenTelemetryUtil.H("!f/a>z\u001co-j\u001eB!m4{\u0004r?m/v/k."), null, 2), new IdeAction(Maps.H("':\u001c=\u0007&\";\u00016\u0001\u001a\u0005*\u0007\u0018\u001e?��\u0007\u00072\u0001;\u0002#\u000f2"), null, 2), new IdeAction(OpenTelemetryUtil.H("e��k2z#D%`/J%V!m4{\u0004r?m/v/k."), null, 2), new IdeAction(Maps.H("6+\u001e?\u0007&2?\u0003=2%\u00172"), null, 2), new IdeAction(OpenTelemetryUtil.H("A$z#x!X-e#Q0"), null, 2), new IdeAction(Maps.H("\u001d\u0017&\u0003$\u001a\u0018\u000b0\u0001\u000b\u0002+\u0012("), null, 2), new IdeAction(OpenTelemetryUtil.H("3`)g8e\u001fa\"g\u0003j$"), null, 2), new IdeAction(Maps.H("!<\u001a;\u00189;1\u000e;\u0007,!%\u00128"), null, 2), new IdeAction(OpenTelemetryUtil.H("P5a8a8I$@\u0001e2w\u0007v!i+p't("), null, 2), new IdeAction(Maps.H("\u0011\n;\u001b< ,\u0005;\u00188\u00169\f\u0004\u0003,\u0005?\u0004+\u00104"), null, 2), new IdeAction(OpenTelemetryUtil.H("7\u0004r?m/v\u0007h,"), ActionScope.INLINE_CHAT_FOCUSED), new IdeAction(Maps.H("\u001b\u0011 \u001c;\u001c\u001c\n+&9\u000b*\u0017\u0018\u001e?��\u0007\u00072\u0001;\u0002#\u000f2"), null, 2), new IdeAction(OpenTelemetryUtil.H(")a(p/R4p#c8g9}\u001d`9V!m4{\u0004r?m/v/k."), null, 2), new IdeAction(Maps.H("6+\u001e?\u0007&,;\u001c,!%\u00128"), null, 2), new IdeAction(OpenTelemetryUtil.H("K.f?]\u0004T2v!~<}?U)v$"), null, 2), new IdeAction(Maps.H(":$\u001e1.7\n=2%\u00172"), null, 2), new IdeAction(OpenTelemetryUtil.H("^8a6D%l#Q0"), null, 2), new IdeAction(Maps.H("\u0015\u001c9\u0012\u0018\u001c5\u0016;\t=\u0018>5,"), null, 2), new IdeAction(OpenTelemetryUtil.H("\u0007`=W%p!g2z6f8F)s."), null, 2), new IdeAction(Maps.H("\u0017\n\r,\u001c='9\r\"\u000b1\u0011+!%\u00128"), null, 2), new IdeAction(OpenTelemetryUtil.H("w\u0012m4|%Y6p8U)v$"), null, 2), new IdeAction(Maps.H("!<\u001a;\u00189,1\u000e;\u0010=:#\u000e9"), null, 2)});
    }
}
