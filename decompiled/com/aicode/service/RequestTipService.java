package com.aicode.service;

import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.ResponseStreamDto;
import com.aicode.enums.CodeTipRequestType;
import com.aicode.enums.TipType;
import com.google.gson.JsonObject;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.util.concurrency.annotations.RequiresBackgroundThread;
import com.intellij.util.concurrency.annotations.RequiresEdt;
import java.util.List;
import java.util.concurrent.Flow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/RequestTipService.class */
public interface RequestTipService {
    @Nullable
    EditorRequestService createRequest(@NotNull Editor editor, int i, @NotNull TipType tipType);

    boolean isAvailable(@NotNull Editor editor);

    @RequiresEdt
    @Nullable
    List<CodeInlayList> fetchCachedTips(@NotNull EditorRequestService editorRequestService);

    void dealStreamAgentTips(String str, ResponseStreamDto responseStreamDto, Project project, MessageDto messageDto);

    @RequiresBackgroundThread
    void fetchTips(@NotNull EditorRequestService editorRequestService, @NotNull Flow.Subscriber<List<CodeInlayList>> subscriber, Editor editor, String str, CodeTipRequestType codeTipRequestType);

    @RequiresBackgroundThread
    void fetchInlineChatContent(EditorRequestService editorRequestService, Flow.Subscriber<List<CodeInlayList>> subscriber, Editor editor, String str, CodeTipRequestType codeTipRequestType);

    EditorRequestService createInlineChatRequest(Editor editor, int i, TipType tipType);

    void dealAgentTips(String str, JsonObject jsonObject, Project project);

    static RequestTipService getInstance() {
        return (RequestTipService) ApplicationManager.getApplication().getService(RequestTipService.class);
    }
}
