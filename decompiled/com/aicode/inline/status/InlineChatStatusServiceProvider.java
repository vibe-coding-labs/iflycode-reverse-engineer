package com.aicode.inline.status;

import com.aicode.exception.RequestCancelException;
import com.aicode.util.HandleCacheUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: kg */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/status/InlineChatStatusServiceProvider.class */
public final class InlineChatStatusServiceProvider {

    @NotNull
    public static final InlineChatStatusServiceProvider INSTANCE = new InlineChatStatusServiceProvider();

    /* renamed from: enum, reason: not valid java name */
    @Nullable
    private static InlineChatStatusService f478enum = null;

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m220enum(int a) {
        throw new IllegalStateException(String.format(RequestCancelException.H("d\rM1L\u0010X?\u0003)B4P0Vu\u0011 /CPdS,T4\u00181Z&\u0014!F0A!QxJ6P7"), HandleCacheUtil.H("o<f{a6E\u0016i7+2\u007f\"_\u0007taw/p:e<9��X\u0005i1r\u000b{-x��r8t*F9r:|<p)\\!n(i;r:"), RequestCancelException.H("$Y/")));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    public InlineChatStatusService get() {
        InlineStatusService inlineStatusService;
        synchronized (this) {
            InlineStatusService inlineStatusService2 = (InlineStatusService) f478enum;
            InlineStatusService inlineStatusService3 = inlineStatusService2;
            if (inlineStatusService2 == null) {
                InlineStatusService inlineStatusService4 = new InlineStatusService();
                f478enum = inlineStatusService4;
                inlineStatusService3 = inlineStatusService4;
            }
            inlineStatusService = inlineStatusService3;
        }
        if (inlineStatusService == null) {
            m220enum(0);
        }
        return inlineStatusService;
    }

    private InlineChatStatusServiceProvider() {
    }
}
