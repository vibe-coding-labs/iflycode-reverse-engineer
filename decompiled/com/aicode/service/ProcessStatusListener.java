package com.aicode.service;

import com.intellij.util.messages.Topic;

/* compiled from: b */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/ProcessStatusListener.class */
public interface ProcessStatusListener {
    public static final Topic<ProcessStatusListener> TOPIC = new Topic<>(ProcessStatusListener.class);

    void onAgentProcessRestart();
}
