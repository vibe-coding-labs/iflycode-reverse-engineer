/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.project.Project
 *  com.intellij.util.messages.Topic
 */
package com.aicode.agent;

import com.aicode.service.editor.CancelRequestTip;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.Topic;

public interface SocketMessageListener {
    public static final Topic<SocketMessageListener> TOPIC = Topic.create((String)CancelRequestTip.H("\u0002\u000e\u000e\u000fO\t\u0017\u0018\\T\u00024\u0007\u000b\u0006\b\u001e'\b\u001e\u0003\u0011]_"), SocketMessageListener.class);

    public void handleSocketMessage(String var1, Project var2);
}
