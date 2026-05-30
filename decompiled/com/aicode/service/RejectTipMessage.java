package com.aicode.service;

import com.aicode.util.Maps;
import com.intellij.util.messages.Topic;
import javax.annotation.concurrent.Immutable;
import org.jetbrains.annotations.Nullable;

@Immutable
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/RejectTipMessage.class */
public interface RejectTipMessage {
    public static final Topic<RejectTipMessage> TOPIC = Topic.create(Maps.H("\u0013'\u0011!\u00049H9\u000b5Mm\u0007*\u0004>\u0006'=6\u00021\u0016=Zg"), RejectTipMessage.class);

    void automaticCodeTipsRejected(@Nullable EditorRequestService editorRequestService);
}
