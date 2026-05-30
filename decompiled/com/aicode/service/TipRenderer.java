package com.aicode.service;

import com.aicode.enums.CodeTipType;
import com.intellij.openapi.editor.EditorCustomElementRenderer;
import com.intellij.openapi.editor.Inlay;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: m */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/TipRenderer.class */
public interface TipRenderer extends EditorCustomElementRenderer {
    @NotNull
    CodeTipType getType();

    @NotNull
    List<String> getContentLines();

    @Nullable
    Inlay<TipRenderer> getInlay();
}
