package com.aicode.inline.status;

import com.aicode.inline.controller.ChatInputController;
import com.aicode.util.Maps;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

/* compiled from: ae */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/status/InlineStatusService.class */
public final class InlineStatusService implements InlineChatStatusService {

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final ConcurrentMap<String, Function0<Unit>> f480enum = new ConcurrentHashMap();

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private final ConcurrentMap<String, Function0<Unit>> f479byte = new ConcurrentHashMap();

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m221enum(int a) {
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
            default:
                H = ChatInputController.H("xG\u0003\u001d\u000e\n\u0018\u000eC\t\r\u001cD(wZ*\u001c\f\u0019\u0017W\u000e\u0013\u000b\u0014\u001f\u001b0-\u0011OQ_\u0010HB\u0001\u0002Ha;\\[\u0016I\f\u0018\r\u0006T\u0016\u001d\nR\u001c\u001cU\f\u001b\u0016\u001a");
                i = a;
                break;
            case 3:
            case 5:
                do {
                } while (0 != 0);
                H = Maps.H("-\u001f\u0007 !&\b4r\u0003\u0010=\b3\u0011iQ;\\k!N\t-��;W%\u0007 B,\u0001,\u0011*\u0001s\u001a=��<");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 4:
            default:
                i2 = 3;
                break;
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
                objArr[0] = ChatInputController.H("\u001d\u000e\u0004");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = Maps.H("\u0002-\u00010��!\u0003>");
                i3 = a;
                break;
            case 2:
            case 4:
                objArr[0] = ChatInputController.H("\u0018\u0007\u0010\u001a\u0001\u000b\u0001\u0014F");
                i3 = a;
                break;
            case 3:
            case 5:
                objArr[0] = Maps.H("\f<��~\t=\f<��=}\u0007\u001b%\t2\u0010f\u0007<\u0013:'\u001dK\u0011\u001d#\u001e%\r\u0007\u0016?\u0010-\u0017\u000b\n!\u0002!\u000f5");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 2:
            case 4:
            default:
                objArr[1] = ChatInputController.H("\u001a\u001a\u0016X\u001f\u001b\u001a\u001a\u0016\u001bk!\r\u0003\u001f\u0014\u0006@\u0011\u001a\u0005\u001c1;]7\u000b\u0005\b\u0003\u001b!��\u0019\u0006\u000b\u0001-\u001c\u0007\u0014\u0007\u0019\u0013");
                i4 = a;
                break;
            case 3:
                do {
                } while (0 != 0);
                objArr[1] = Maps.H(" \u0019\f\u0004;��?\b4\u001d\u001d\u00012\u0016$\t4");
                i4 = a;
                break;
            case 5:
                objArr[1] = ChatInputController.H("\n\u0007&\u0001\u0011\u0010\u0015\u0014\u001e\u00076\u0017\n\u0014��\u0002\u001f\u0012");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            default:
                objArr[2] = Maps.H("\u001e--:\u0003<\b=��\u001e��!2!��5");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = ChatInputController.H("\u0006\u000f*\u0012\u001d\u0016\u0019\u001e\u0012\u000b;\u0017\u0014��\u0002\u001f\u0012");
                break;
            case 3:
            case 5:
                break;
            case 4:
                objArr[2] = Maps.H("\u001c!0'\u00076\u00032\b! 1\u001c2\u0016$\t4");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 4:
            default:
                throw new IllegalArgumentException(format);
            case 3:
            case 5:
                throw new IllegalStateException(format);
        }
    }

    @Override // com.aicode.inline.status.InlineChatStatusService
    public void ifEnabledForFile(@NotNull String str, @NotNull Supplier<?> supplier) {
        if (str == null) {
            m221enum(0);
        }
        if (supplier == null) {
            m221enum(1);
        }
        supplier.get();
    }

    @Override // com.aicode.inline.status.InlineChatStatusService
    @NotNull
    public InlineChatStatusSubscription onGloballyDisabled(@NotNull Function0<Unit> function0) {
        if (function0 == null) {
            m221enum(4);
        }
        String rC = rC();
        this.f479byte.put(rC, function0);
        InlineChatStatusSubscription cB = cB(() -> {
            this.f479byte.remove(rC);
            return Unit.INSTANCE;
        });
        if (cB == null) {
            m221enum(5);
        }
        return cB;
    }

    private InlineChatStatusSubscription cB(Function0<Unit> function0) {
        Objects.requireNonNull(function0);
        return function0::invoke;
    }

    @Override // com.aicode.inline.status.InlineChatStatusService
    @NotNull
    public InlineChatStatusSubscription onGloballyEnabled(@NotNull Function0<Unit> function0) {
        if (function0 == null) {
            m221enum(2);
        }
        String rC = rC();
        this.f480enum.put(rC, function0);
        InlineChatStatusSubscription cB = cB(() -> {
            this.f480enum.remove(rC);
            return Unit.INSTANCE;
        });
        if (cB == null) {
            m221enum(3);
        }
        return cB;
    }

    private String rC() {
        return UUID.randomUUID().toString();
    }
}
