package com.aicode.inline.ide;

import com.aicode.inline.KeyStrokeExecutorProvider;
import com.aicode.ui.ActionButton;
import com.aicode.util.AICodeStringUtil;
import java.util.Set;
import javax.swing.KeyStroke;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: gf */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/ide/ConditionalActionConfiguration.class */
public final class ConditionalActionConfiguration {

    /* renamed from: float, reason: not valid java name */
    @NotNull
    private final KeyStrokeExecutorProvider f404float;

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private final ActionScope f405byte;

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final Set<KeyStroke> f406enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m199enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            default:
                H = ActionButton.H("nG%-\u0003\u0011\u0001\u0001E\u0019\u0007��U/&\u001d\u001a:\u001a\u0019\fZ\u001d\u00162;\u000f\u001d\u0001\n\u001aRIQ\u001cRb7\bTJ\u0006IX\u001bR\u000b\t\u0007\u001aS\u0007\u000f\u000eU\r\rR��\u0001\u0003\u0019");
                i = a;
                break;
            case 3:
            case 7:
            case 8:
            case 9:
                do {
                } while (0 != 0);
                H = AICodeStringUtil.H("ilK[GwGL\u001cZD^OCB\r.s\t\tU\rCPR^\u000fJRB\u001aCLVIEO\nIYJA");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            default:
                i2 = 3;
                break;
            case 3:
            case 7:
            case 8:
            case 9:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 4:
            default:
                objArr[0] = ActionButton.H("\u0001\r\u001b\u001f\u0010");
                i3 = a;
                break;
            case 1:
            case 5:
                do {
                } while (0 != 0);
                objArr[0] = AICodeStringUtil.H("kBUuY\\JJOj\\XUOEFPlEN\\NHC_");
                i3 = a;
                break;
            case 2:
            case 6:
                objArr[0] = ActionButton.H("\u0007\n\u0001");
                i3 = a;
                break;
            case 3:
            case 7:
            case 8:
            case 9:
                objArr[0] = AICodeStringUtil.H("BEQ\u0018@CDCBH\u0006KJC`lN\u000fUSD\u0005dCHIbtNCHLBdB^FKSuU_OK[BSKSEIC");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            default:
                objArr[1] = ActionButton.H("\u000b\u001d\u0018@\t\u001b\r\u001b\u000b\u0010O\u0013\u0003\u001b)4\u0007W\u001c\u000b\r]-\u001b\u0001\u0011+,\u0007\u001b\u0001\u0014\u000b<\u000b\u0006\u000f\u0013\u001a-\u001c\u0007\u0006\u0013\u0012\u001a\u001a\u0013\u001a\u001d��\u001b");
                i4 = a;
                break;
            case 3:
                do {
                } while (0 != 0);
                objArr[1] = AICodeStringUtil.H("HXr^UEHJ");
                i4 = a;
                break;
            case 7:
                objArr[1] = ActionButton.H("\u0012\n\u001c!\r\u001b\u001f\u0010");
                i4 = a;
                break;
            case 8:
                objArr[1] = AICodeStringUtil.H("AH\u007fKBUuY\\JJOj\\XUOEFPlEN\\NHC_");
                i4 = a;
                break;
            case 9:
                objArr[1] = ActionButton.H("\u000f\u0017\u0012>\u001b\u001b\u001d\r+\u001f\f<\u001c��\u0001\u001f\n\u0006");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            case 2:
            default:
                objArr[2] = AICodeStringUtil.H("DCVT");
                break;
            case 3:
            case 7:
            case 8:
            case 9:
                break;
            case 4:
            case 5:
            case 6:
                do {
                } while (0 != 0);
                objArr[2] = ActionButton.H("T\u001b��\u001d\u001bK");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            default:
                throw new IllegalArgumentException(format);
            case 3:
            case 7:
            case 8:
            case 9:
                throw new IllegalStateException(format);
        }
    }

    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName()).toString();
        int length = stringBuffer.length() - 1;
        int i = 3 << 3;
        int i2 = (4 << 4) ^ ((2 << 2) ^ 1);
        int i3 = (3 << 3) ^ 5;
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

    @NotNull
    public Set<KeyStroke> getBoundKeyStrokes() {
        Set<KeyStroke> set = this.f406enum;
        if (set == null) {
            m199enum(9);
        }
        return set;
    }

    public ConditionalActionConfiguration(@NotNull ActionScope scope, @NotNull KeyStrokeExecutorProvider keyStrokeExecutorProvider, @NotNull Set<KeyStroke> set) {
        if (scope == null) {
            m199enum(4);
        }
        if (keyStrokeExecutorProvider == null) {
            m199enum(5);
        }
        if (set == null) {
            m199enum(6);
        }
        this.f405byte = scope;
        this.f404float = keyStrokeExecutorProvider;
        this.f406enum = set;
    }

    public int hashCode() {
        return (((this.f405byte.hashCode() * 31) + this.f404float.hashCode()) * 31) + this.f406enum.hashCode();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public boolean equals(@Nullable Object a) {
        if (this != a) {
            if (a instanceof ConditionalActionConfiguration) {
                ConditionalActionConfiguration conditionalActionConfiguration = (ConditionalActionConfiguration) a;
                return this.f405byte == conditionalActionConfiguration.f405byte && Intrinsics.areEqual(this.f404float, conditionalActionConfiguration.f404float) && Intrinsics.areEqual(this.f406enum, conditionalActionConfiguration.f406enum);
            }
            return false;
        }
        return true;
    }

    @NotNull
    public ConditionalActionConfiguration copy(@NotNull ActionScope scope, @NotNull KeyStrokeExecutorProvider keyStrokeExecutorProvider, @NotNull Set<KeyStroke> set) {
        if (scope == null) {
            m199enum(0);
        }
        if (keyStrokeExecutorProvider == null) {
            m199enum(1);
        }
        if (set == null) {
            m199enum(2);
        }
        return new ConditionalActionConfiguration(scope, keyStrokeExecutorProvider, set);
    }

    @NotNull
    public KeyStrokeExecutorProvider getKeyStrokeExecutorProvider() {
        KeyStrokeExecutorProvider keyStrokeExecutorProvider = this.f404float;
        if (keyStrokeExecutorProvider == null) {
            m199enum(8);
        }
        return keyStrokeExecutorProvider;
    }

    @NotNull
    public ActionScope getScope() {
        ActionScope actionScope = this.f405byte;
        if (actionScope == null) {
            m199enum(7);
        }
        return actionScope;
    }

    public static ConditionalActionConfiguration copy(ConditionalActionConfiguration a, ActionScope a2, KeyStrokeExecutorProvider a3, Set a4, int a5) {
        if ((a5 & 1) != 0) {
            a2 = a.f405byte;
        }
        if ((a5 & 2) != 0) {
            a3 = a.f404float;
        }
        if ((a5 & 4) != 0) {
            a4 = a.f406enum;
        }
        return a.copy(a2, a3, a4);
    }

    @NotNull
    public String toString() {
        String str = "ConditionalActionConfiguration(scope=" + this.f405byte + ", keyStrokeExecutorProvider=" + this.f404float + ", boundKeyStrokes=" + this.f406enum + ")";
        if (str == null) {
            m199enum(3);
        }
        return str;
    }
}
