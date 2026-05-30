package com.aicode.ui;

import com.aicode.exception.RequestCancelException;
import com.aicode.util.Application;
import com.intellij.util.ui.JBFont;
import javax.swing.JLabel;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ob */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/ui/FontKt.class */
public final class FontKt {
    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m369enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
                H = Application.H("\u000f?APbi`y4qDZ4WKizC`za.sa}m`k:(s\"./p'/cb' u`hf6d\u007f=9\u0006K`x.oq7E]x{");
                i = a;
                break;
            case 1:
            case 3:
            case 5:
                do {
                } while (0 != 0);
                H = RequestCancelException.H("?V_#v*^9\u001e4P&\\<\u001b8\u00010\u0016z\f8z\u0005M-\u001f6J6:\u000f@6A!QxJ6P7");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
                i2 = 3;
                break;
            case 1:
            case 3:
            case 5:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = Application.H("\u0001{dg~0gGI}y");
                i3 = a;
                break;
            case 1:
            case 3:
            case 5:
                do {
                } while (0 != 0);
                objArr[0] = RequestCancelException.H("t\u001fSv^1F-~\u0018\n7]|y7J7w/");
                i3 = a;
                break;
            case 2:
                objArr[0] = Application.H("\u0002Qge})}cJD}t");
                i3 = a;
                break;
            case 4:
                objArr[0] = RequestCancelException.H("\u00016\\:L|F,P?");
                i3 = a;
                break;
            case 6:
                objArr[0] = Application.H("_Mlc");
                i3 = a;
                break;
            case 7:
            case 9:
                objArr[0] = RequestCancelException.H("B,R/");
                i3 = a;
                break;
            case 8:
                objArr[0] = Application.H("1bac=iQLkxfK{emGzc");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
                objArr[1] = RequestCancelException.H("t\u001fSv^1F-~\u0018\n7]|y7J7w/");
                i4 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = Application.H("gGI}y");
                i4 = a;
                break;
            case 3:
                objArr[1] = RequestCancelException.H("V,E/U8");
                i4 = a;
                break;
            case 5:
                objArr[1] = Application.H("IGxs");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = RequestCancelException.H("(H\"U5");
                break;
            case 1:
            case 3:
            case 5:
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = Application.H("}cJD}t");
                break;
            case 4:
                objArr[2] = RequestCancelException.H("F,P?");
                break;
            case 6:
            case 7:
                objArr[2] = Application.H("}o69qLkxfK{emGzc");
                break;
            case 8:
            case 9:
                objArr[2] = RequestCancelException.H("m\u0014A6\\\u0015P*b,R/");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
                throw new IllegalArgumentException(format);
            case 1:
            case 3:
            case 5:
                throw new IllegalStateException(format);
        }
    }

    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).insert(0, stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (4 << 4) ^ 3;
        int i2 = (5 << 3) ^ 3;
        int i3 = ((3 ^ 5) << 3) ^ (2 ^ 5);
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
    public static final JBFont italic(@NotNull JBFont $this$italic) {
        if ($this$italic == null) {
            m369enum(2);
        }
        Intrinsics.checkNotNullParameter($this$italic, Application.H("(cCAg)"));
        JBFont asItalic = $this$italic.asItalic();
        Intrinsics.checkNotNullExpressionValue(asItalic, RequestCancelException.H("D1y#\\6Q<\u001a|"));
        if (asItalic == null) {
            m369enum(3);
        }
        return asItalic;
    }

    public static final int widthForFont(@NotNull String $this$widthForFont, @NotNull java.awt.Font font) {
        if ($this$widthForFont == null) {
            m369enum(8);
        }
        if (font == null) {
            m369enum(9);
        }
        Intrinsics.checkNotNullParameter($this$widthForFont, Application.H("(cCAg)"));
        Intrinsics.checkNotNullParameter(font, RequestCancelException.H("Y7K6"));
        return textWidthForFont($this$widthForFont, font);
    }

    @NotNull
    public static final JBFont plain(@NotNull JBFont $this$plain) {
        if ($this$plain == null) {
            m369enum(0);
        }
        Intrinsics.checkNotNullParameter($this$plain, Application.H("(cCAg)"));
        JBFont asPlain = $this$plain.asPlain();
        Intrinsics.checkNotNullExpressionValue(asPlain, RequestCancelException.H("'N\n\\6Q1\u0017q"));
        if (asPlain == null) {
            m369enum(1);
        }
        return asPlain;
    }

    public static final int textWidthForFont(@NotNull String text, @NotNull java.awt.Font font) {
        if (text == null) {
            m369enum(6);
        }
        if (font == null) {
            m369enum(7);
        }
        Intrinsics.checkNotNullParameter(text, Application.H("_Mlc"));
        Intrinsics.checkNotNullParameter(font, RequestCancelException.H("Y7K6"));
        return new JLabel().getFontMetrics(font).stringWidth(text);
    }

    @NotNull
    public static final JBFont bold(@NotNull JBFont $this$bold) {
        if ($this$bold == null) {
            m369enum(4);
        }
        Intrinsics.checkNotNullParameter($this$bold, Application.H("(cCAg)"));
        JBFont asBold = $this$bold.asBold();
        Intrinsics.checkNotNullExpressionValue(asBold, RequestCancelException.H("R'|6Q>\u001d{"));
        if (asBold == null) {
            m369enum(5);
        }
        return asBold;
    }
}
