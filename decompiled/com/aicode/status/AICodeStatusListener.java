package com.aicode.status;

import com.aicode.enums.AICodeStatus;
import com.aicode.exception.RequestCancelException;
import com.intellij.util.messages.Topic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: j */
@FunctionalInterface
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/status/AICodeStatusListener.class */
public interface AICodeStatusListener {
    public static final Topic<AICodeStatusListener> TOPIC = Topic.create(RequestCancelException.H("\u0014t\u0019W;ZvK+D6\u001a{"), AICodeStatusListener.class);

    void onAICodeStatus(@NotNull AICodeStatus aICodeStatus, @Nullable String str);
}
