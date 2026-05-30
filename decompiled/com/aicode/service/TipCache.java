package com.aicode.service;

import java.util.List;
import javax.annotation.concurrent.ThreadSafe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: t */
@ThreadSafe
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/TipCache.class */
public interface TipCache {
    @Nullable
    List<CodeTip> getLatest(@NotNull String str);

    @Nullable
    List<CodeTip> get(@NotNull String str, boolean z);

    void clear();

    void updateLatest(@NotNull String str, @NotNull String str2, boolean z);

    void add(@NotNull String str, @NotNull String str2, boolean z, @NotNull CodeTip codeTip);

    boolean isLatestPrefix(@NotNull String str);
}
