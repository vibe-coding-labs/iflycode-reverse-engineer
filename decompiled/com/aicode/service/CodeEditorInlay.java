package com.aicode.service;

import com.aicode.enums.CodeTipType;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: z */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/CodeEditorInlay.class */
public interface CodeEditorInlay {
    @NotNull
    List<String> getLines();

    int getEditorOffset();

    void setEditorOffset(int i);

    @NotNull
    void setType(CodeTipType codeTipType);

    @NotNull
    void setLines(List<String> list);

    @NotNull
    CodeTipType getType();

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    default boolean isEmptyTip() {
        List<String> lines = getLines();
        return lines.isEmpty() || (lines.size() == 1 && lines.get(0).isEmpty());
    }
}
