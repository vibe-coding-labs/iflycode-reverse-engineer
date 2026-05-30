package com.aicode.ui;

import com.aicode.service.editor.RequestResultList;
import com.aicode.util.Application;
import com.intellij.util.ui.JBFont;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: fb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/ui/Font.class */
public final class Font {

    @NotNull
    public static final Font INSTANCE = new Font();

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m368enum(int a) {
        String H = RequestResultList.H("ZfnGW^{I;D|_2\u0007q\u00078\\9��h\t}WbWz\u0006nG=]?\u001cGru\tt]l^");
        Object[] objArr = new Object[2];
        objArr[0] = Application.H("fi#bt\u007fje*(\tPf#Hbzc");
        switch (a) {
            case 0:
            default:
                objArr[1] = RequestResultList.H("=\rFXce{ZgW");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = Application.H("*CQW@o\u007fsr");
                break;
            case 2:
                objArr[1] = RequestResultList.H("Ueoe{ZgW");
                break;
            case 3:
                objArr[1] = Application.H("*CQBijdaz");
                break;
            case 4:
                objArr[1] = RequestResultList.H("UeozwIl^");
                break;
            case 5:
                objArr[1] = Application.H("*CQW_clx{");
                break;
        }
        throw new IllegalStateException(String.format(H, objArr));
    }

    @NotNull
    public final JBFont getXxLarge() {
        JBFont biggerOn = getMedium().biggerOn(5.0f);
        Intrinsics.checkNotNullExpressionValue(biggerOn, RequestResultList.H("DuFxV7FcZzH?\u001a}n3\u001c4\u0018f\u001b"));
        if (biggerOn == null) {
            m368enum(0);
        }
        return biggerOn;
    }

    @NotNull
    public final JBFont getSmall() {
        JBFont lessOn = getMedium().lessOn(1.0f);
        Intrinsics.checkNotNullExpressionValue(lessOn, RequestResultList.H("OtG3\u001dl\u001dqJ)\u001b}n3\u00184\u0018f\u001b"));
        if (lessOn == null) {
            m368enum(4);
        }
        return lessOn;
    }

    @NotNull
    public final JBFont getMedium() {
        JBFont label = JBFont.label();
        Intrinsics.checkNotNullExpressionValue(label, Application.H("Innka<>"));
        JBFont plain = FontKt.plain(label);
        if (plain == null) {
            m368enum(3);
        }
        return plain;
    }

    @NotNull
    public final JBFont getXSmall() {
        JBFont lessOn = getMedium().lessOn(2.0f);
        Intrinsics.checkNotNullExpressionValue(lessOn, Application.H("j`b'8x8eo=>iK'> =r>"));
        if (lessOn == null) {
            m368enum(5);
        }
        return lessOn;
    }

    @NotNull
    public final JBFont getXLarge() {
        JBFont biggerOn = getMedium().biggerOn(3.0f);
        Intrinsics.checkNotNullExpressionValue(biggerOn, Application.H("aacls#cw\u007fnm+?iK'? =r>"));
        if (biggerOn == null) {
            m368enum(1);
        }
        return biggerOn;
    }

    @NotNull
    public final JBFont getLarge() {
        JBFont biggerOn = getMedium().biggerOn(1.0f);
        Intrinsics.checkNotNullExpressionValue(biggerOn, RequestResultList.H("DuFxV7FcZzH?\u001a}n3\u00184\u0018f\u001b"));
        if (biggerOn == null) {
            m368enum(2);
        }
        return biggerOn;
    }

    private Font() {
    }
}
