package com.aicode.language;

import com.aicode.exception.RequestCancelException;
import com.intellij.lang.Language;
import java.util.Locale;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: nk */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/language/AICodeLanguageInfo.class */
public final class AICodeLanguageInfo {

    /* renamed from: byte, reason: not valid java name */
    @Nullable
    private final String f484byte;

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final Language f485enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m224enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 2:
            default:
                H = H("#\u001c\u0006,\u000b\u0001\"\u0013o\u0013\u0003##\u0015 Uh\u000fqK8Z \t<\nC<+\u0001l\u000f ��:\f*U1\u001b+\u001a");
                i = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                H = RequestCancelException.H("|(W\"R=X%\u0004%_%\u0016\u0011z<\u000bVe\u001bt_b\u0014L8X7@6oZ\u0017rL\u007f\u0016>Bc\u0015$\u0018tGsu\nL,\u0017>Q-\u00141ZxJ6P7");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 2:
            default:
                i2 = 2;
                break;
            case 1:
                i2 = 3;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 2:
            default:
                objArr[0] = H("&\u001b#P.\u0017\u00058/\u001fk\u0019,\u00128\u001b*\u001d(S\u000e7 = \u0010��\u001c+\u0013:\u001f#\u0010\u0016��!\u0019");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = RequestCancelException.H("X2Q?Q\"[>");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = H("\u001a ��\u0003\u001f*\u0012*\u000f \u0013");
                i4 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = RequestCancelException.H("]6X}U:~\u0015T2\u00104W?C6Q0S~u\u001a[\u0010[={1P>A2X=m-Z4");
                i4 = a;
                break;
            case 2:
                objArr[1] = H("\t.\u000e\u001b/\f\u0011\u00077\r\u0011\u001b\u00141\u001c\t\u001f(\u0019=\u000f$\u001d");
                i4 = a;
                break;
        }
        switch (i4) {
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = RequestCancelException.H("\u00031J*He");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 2:
            default:
                throw new IllegalStateException(format);
            case 1:
                throw new IllegalArgumentException(format);
        }
    }

    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).insert(0, stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (3 << 3) ^ 3;
        int i2 = ((2 ^ 5) << 3) ^ 3;
        int i3 = (5 << 3) ^ 2;
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

    public String toString() {
        return "LanguageInfo(language=" + getLanguage() + ", vscodeId=" + getVscodeId() + ")";
    }

    @NotNull
    public Language getLanguage() {
        Language language = this.f485enum;
        if (language == null) {
            m224enum(0);
        }
        return language;
    }

    @NotNull
    public String getVSCodeIdWithFallback() {
        String str = (String) Objects.requireNonNullElseGet(this.f484byte, () -> {
            return this.f485enum.getID().toLowerCase(Locale.ENGLISH);
        });
        if (str == null) {
            m224enum(2);
        }
        return str;
    }

    @Nullable
    public String getVscodeId() {
        return this.f484byte;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public AICodeLanguageInfo(@NotNull Language language, @Nullable String a) {
        if (language == null) {
            m224enum(1);
        }
        this.f485enum = language;
        this.f484byte = a == null ? null : a.toLowerCase(Locale.ENGLISH);
    }
}
