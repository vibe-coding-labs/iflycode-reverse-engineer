package com.aicode.inline.status;

import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

/* compiled from: g */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/status/InlineChatStatusService.class */
public interface InlineChatStatusService {
    @NotNull
    InlineChatStatusSubscription onGloballyEnabled(@NotNull Function0<Unit> function0);

    @NotNull
    InlineChatStatusSubscription onGloballyDisabled(@NotNull Function0<Unit> function0);

    void ifEnabledForFile(@NotNull String str, @NotNull Supplier<?> supplier);
}
