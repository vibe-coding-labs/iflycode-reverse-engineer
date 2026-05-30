package com.aicode.inline;

import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: q */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/KeyStrokeExecutorProvider.class */
public interface KeyStrokeExecutorProvider {
    @Nullable
    KeyStrokeHandler keyStrokeExecutor(@NotNull Editor editor);
}
