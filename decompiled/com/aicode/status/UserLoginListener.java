package com.aicode.status;

import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.message.BasicActionsBundle;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.Topic;

@FunctionalInterface
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/status/UserLoginListener.class */
public interface UserLoginListener {
    public static final Topic<UserLoginListener> USER_LOGIN = Topic.create(BasicActionsBundle.message(FileExtensionLanguageDetails.H("zg\u007fCZl5cmaaly hlc~e"), new Object[0]) + " login", UserLoginListener.class);

    void login(Project project, boolean z);
}
