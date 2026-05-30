package com.aicode.service;

import com.aicode.util.Maps;
import com.intellij.util.messages.Topic;
import javax.annotation.concurrent.Immutable;

@Immutable
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/RequestsCancelledService.class */
public interface RequestsCancelledService {
    public static final Topic<RequestsCancelledService> TOPIC = Topic.create(Maps.H("6\u0002+;\u0006;J*Xp\u0017;\u001e%\u001b\u0017\u000e=\u000b1\u0019%Zg"), RequestsCancelledService.class);

    void requestsCancelled(int i);
}
