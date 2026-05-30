package com.aicode.service;

import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.intellij.util.messages.Topic;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: x */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/TipReceivedMessage.class */
public interface TipReceivedMessage {
    public static final Topic<TipReceivedMessage> TOPIC = Topic.create(ConditionalActionConfiguration.H("83<\u0017\u0019D\n\u0017\u0015\u001d\u0015\r\u0002!\u0013\u0017\u0014��\u001aFB"), TipReceivedMessage.class);

    void inlaysReceived(@NotNull EditorRequestService editorRequestService, @NotNull List<CodeInlayList> list);
}
