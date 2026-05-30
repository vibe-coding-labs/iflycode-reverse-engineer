package com.aicode.complete;

import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.enums.OperateActionEnum;
import com.aicode.service.EditorRequestService;
import com.aicode.service.TipRenderer;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Inlay;
import com.intellij.util.messages.Topic;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: sa */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/complete/InlayListener.class */
public interface InlayListener {
    public static final Topic<InlayListener> TOPIC = Topic.create(FileExtensionLanguageDetails.H("hCZl5zoxg|d[lavfe"), InlayListener.class);

    void inlaysUpdated(@NotNull EditorRequestService editorRequestService, @NotNull OperateActionEnum operateActionEnum, @NotNull Editor editor, @NotNull List<Inlay<TipRenderer>> list);
}
