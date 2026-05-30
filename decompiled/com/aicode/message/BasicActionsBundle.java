package com.aicode.message;

import com.aicode.diff.FileInfo;
import com.aicode.util.AICodeUtils;
import com.intellij.DynamicBundle;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/* compiled from: fc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/message/BasicActionsBundle.class */
public class BasicActionsBundle extends DynamicBundle {

    /* renamed from: byte, reason: not valid java name */
    @NonNls
    private static final String f524byte = AICodeUtils.H("oKS\u007fc|pu&Borf\u007fSNWs{o|Ipmifa");

    /* renamed from: enum, reason: not valid java name */
    private static final BasicActionsBundle f525enum = new BasicActionsBundle();

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m256enum(int a) {
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
                H = FileInfo.H("9c\u0013h\u0012s\u0010c\u0015:\u0019m^W&n\u001cO\u000f\u007f\u00105\u000evG=4U\u000ev\u001a!U>\u000b6z\\\u001e1Ju\\>\u0007=\u0018i\u001buy^\u0001sU~\u001a6\u0019k\u0012{");
                i = a;
                break;
            case 2:
            case 5:
                do {
                } while (0 != 0);
                H = AICodeUtils.H("HHep\u000f:AO.myfngh\"\u000bS\"'h5k}sz!asf\rQ\u007f`t}e%mxfh");
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
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 3:
            default:
                objArr[0] = FileInfo.H("u\u001bn");
                i3 = a;
                break;
            case 1:
            case 4:
                do {
                } while (0 != 0);
                objArr[0] = AICodeUtils.H("{dqlgw");
                i3 = a;
                break;
            case 2:
            case 5:
                objArr[0] = FileInfo.H("?6]Ur\u0001b\u001d\u007f\u001d>7V\u000bb\u000ea\u001746|\u0006u\u000b@:D\u0007h\u001bo=c\u0019z\u0012r");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 3:
            case 4:
            default:
                objArr[1] = AICodeUtils.H(",BN!auqili-CE\u007fqzrc'Borf\u007fSNWs{o|Ipmifa");
                i4 = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = FileInfo.H("q\u001ae\u0004\u007f\u0019r");
                i4 = a;
                break;
            case 5:
                objArr[1] = AICodeUtils.H("qw^P{sd_dlmyov");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            default:
                objArr[2] = FileInfo.H("q\u001ae\u0004\u007f\u0019r");
                break;
            case 2:
            case 5:
                break;
            case 3:
            case 4:
                do {
                } while (0 != 0);
                objArr[2] = AICodeUtils.H("qw^P{sd_dlmyov");
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
                throw new IllegalStateException(format);
        }
    }

    private BasicActionsBundle() {
        super(FileInfo.H("|?@\u000bp\bc\u000156|\u0006u\u000b@:D\u0007h\u001bo=c\u0019z\u0012r"));
    }

    @NotNull
    public static Supplier<String> messagePointer(@PropertyKey(resourceBundle = "messages.BasicActionsBundle") @NotNull String key, Object... params) {
        if (key == null) {
            m256enum(3);
        }
        if (params == null) {
            m256enum(4);
        }
        Supplier<String> lazyMessage = f525enum.getLazyMessage(key, params);
        if (lazyMessage == null) {
            m256enum(5);
        }
        return lazyMessage;
    }

    @Nls
    @NotNull
    public static String message(@PropertyKey(resourceBundle = "messages.BasicActionsBundle") @NotNull String key, Object... params) {
        if (key == null) {
            m256enum(0);
        }
        if (params == null) {
            m256enum(1);
        }
        String message = f525enum.getMessage(key, params);
        if (message == null) {
            m256enum(2);
        }
        return message;
    }
}
