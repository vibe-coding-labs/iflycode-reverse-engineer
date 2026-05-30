package com.aicode.inline.ide;

import com.aicode.util.Application;
import com.aicode.util.HandleCacheUtil;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: rk */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/ide/IdeAction.class */
public final class IdeAction {

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private final String f413byte;

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final ActionScope f414enum;

    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (4 << 4) ^ 1;
        int i2 = (4 << 4) ^ ((3 << 2) ^ 1);
        int i3 = (1 << 3) ^ 2;
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

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m203enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 3:
            case 4:
            default:
                H = Application.H("Brhy`k 9!dfx#@AcpIpj\"myk|laj}o|-\"#=j)eb' u`hZ\nirvr\u0001Llt4ul*ayba");
                i = a;
                break;
            case 2:
            case 5:
            case 6:
                do {
                } while (0 != 0);
                H = HandleCacheUtil.H("\u000bZc'E!e:,>n h0/4) /{s\u007f&a_\u0007!0o+\u0004\tc-d<bsd g8");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 3:
            case 4:
            default:
                i2 = 3;
                break;
            case 2:
            case 5:
            case 6:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 3:
            default:
                objArr[0] = Application.H("ut}c`bGi");
                i3 = a;
                break;
            case 1:
            case 4:
                do {
                } while (0 != 0);
                objArr[0] = HandleCacheUtil.H(" i:{1");
                i3 = a;
                break;
            case 2:
            case 5:
            case 6:
                objArr[0] = Application.H("njka,`ikc`)'#ECjb*oEG,IprHi{eac");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 3:
            case 4:
            default:
                objArr[1] = HandleCacheUtil.H("7o2due0n:ep\"z@\u001ao;/6@\u001e)\u0010u+M0~<d:");
                i4 = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = Application.H("`xZ~}e`j");
                i4 = a;
                break;
            case 5:
                objArr[1] = HandleCacheUtil.H("\u001cc-P-x:e;B0");
                i4 = a;
                break;
            case 6:
                objArr[1] = Application.H("sr}Ylc~h");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            default:
                objArr[2] = HandleCacheUtil.H("i:{-");
                break;
            case 2:
            case 5:
            case 6:
                break;
            case 3:
            case 4:
                do {
                } while (0 != 0);
                objArr[2] = Application.H("5caez3");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 3:
            case 4:
            default:
                throw new IllegalArgumentException(format);
            case 2:
            case 5:
            case 6:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public IdeAction(String a, ActionScope a2, int a3) {
        this(a, (a3 & 2) != 0 ? ActionScope.INPUT_FOCUSED : a2);
    }

    @NotNull
    public ActionScope getScope() {
        ActionScope actionScope = this.f414enum;
        if (actionScope == null) {
            m203enum(6);
        }
        return actionScope;
    }

    @NotNull
    public IdeAction copy(@NotNull String actionId, @NotNull ActionScope a) {
        if (actionId == null) {
            m203enum(0);
        }
        if (a == null) {
            m203enum(1);
        }
        return new IdeAction(actionId, a);
    }

    public int hashCode() {
        return (this.f413byte.hashCode() * 31) + this.f414enum.hashCode();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public boolean equals(@Nullable Object a) {
        if (this != a) {
            if (a instanceof IdeAction) {
                return Intrinsics.areEqual(this.f413byte, ((IdeAction) a).f413byte) && this.f414enum == ((IdeAction) a).f414enum;
            }
            return false;
        }
        return true;
    }

    public static IdeAction copy(IdeAction a, String a2, ActionScope a3, int a4) {
        if ((a4 & 1) != 0) {
            a2 = a.f413byte;
        }
        if ((a4 & 2) != 0) {
            a3 = a.f414enum;
        }
        return a.copy(a2, a3);
    }

    @NotNull
    public String getActionId() {
        String str = this.f413byte;
        if (str == null) {
            m203enum(5);
        }
        return str;
    }

    public IdeAction(@NotNull String actionId, @NotNull ActionScope a) {
        if (actionId == null) {
            m203enum(3);
        }
        if (a == null) {
            m203enum(4);
        }
        this.f413byte = actionId;
        this.f414enum = a;
    }

    @NotNull
    public String toString() {
        String str = "IdeAction(actionId=" + this.f413byte + ", scope=" + this.f414enum + ")";
        if (str == null) {
            m203enum(2);
        }
        return str;
    }
}
