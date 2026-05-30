package com.aicode.service;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: n */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/CodeTip.class */
public interface CodeTip {
    @NotNull
    List<String> getTip();

    @NotNull
    CodeTip withCompletion(@NotNull List<String> list);

    boolean isCached();

    @NotNull
    CodeTip asCached();
}
