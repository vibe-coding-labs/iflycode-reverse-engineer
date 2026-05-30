package com.aicode.service;

import com.aicode.agent.dto.ResponseStreamDto;
import com.intellij.openapi.util.TextRange;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: p */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/CodeInlayList.class */
public interface CodeInlayList extends Iterable<CodeEditorInlay> {
    boolean isEmpty();

    void setData(ResponseStreamDto.ResponseData responseData);

    void setRemoveBlank(boolean z);

    @NotNull
    List<CodeEditorInlay> getInlays();

    @NotNull
    TextRange getReplacementRange();

    @NotNull
    int getOffset();

    boolean isRemoveBlank();

    void setReplacementText(String str);

    @NotNull
    CodeTip getAICodeTip();

    ResponseStreamDto.ResponseData getData();

    @NotNull
    String getReplacementText();

    void setReplacementRange(TextRange textRange);
}
