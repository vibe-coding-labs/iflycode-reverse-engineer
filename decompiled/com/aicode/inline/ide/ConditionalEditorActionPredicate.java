package com.aicode.inline.ide;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.Nullable;

/* compiled from: y */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/ide/ConditionalEditorActionPredicate.class */
public interface ConditionalEditorActionPredicate {
    boolean evaluate(@Nullable Editor editor, @Nullable Caret caret, @Nullable DataContext dataContext);
}
