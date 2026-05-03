/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.IdUtil
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.ui.MessageType
 *  com.intellij.ui.EditorTextField
 */
package com.aicode.agent.service;

import cn.hutool.core.util.IdUtil;
import com.aicode.action.CommitMessageSuggestionAction;
import com.aicode.action.PrepushReviewAction;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.CommonService;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.enums.WebViewResponseTypeEnum;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.util.NewFileUtils;
import com.aicode.util.StringUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.ui.EditorTextField;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class GitReviewService {
    public static String removeMarkdownCodeBlocks(String a) {
        return a.replaceAll(CancelRequestTip.H("nW\u0005B5\u0007\u0007\u000fA]S"), "").replaceAll(NewFileUtils.H("$a\u001eQAZMW\u001aV]F"), "");
    }

    public static String H(Object object) {
        int a;
        Object object2 = object;
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String string = new StringBuffer(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName()).toString();
        object2 = (String)object2;
        int n = ((String)object2).length();
        int n2 = n - 1;
        char[] cArray = new char[n];
        int n3 = 4 << 3 ^ 4;
        int cfr_ignored_0 = (2 ^ 5) << 3 ^ 2;
        int n4 = 3 << 3 ^ (2 ^ 5);
        int n5 = a = string.length() - 1;
        int n6 = n2;
        String string2 = string;
        while (n6 >= 0) {
            int n7 = n2--;
            cArray[n7] = (char)(n4 ^ (((String)object2).charAt(n7) ^ string2.charAt(a)));
            if (n2 < 0) break;
            int n8 = n2--;
            char c = cArray[n8] = (char)(n3 ^ (((String)object2).charAt(n8) ^ string2.charAt(a)));
            if (--a < 0) {
                a = n5;
            }
            n6 = n2;
        }
        return new String(cArray);
    }

    /*
     * WARNING - void declaration
     */
    public static void sendGitDiffRequest(String string, Project project) {
        void a;
        Object a2;
        String string2 = string;
        PrepushReviewAction.PREPUSH_REVIEW_BUTTON.set(true);
        Object object = a2 = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.GIT_DIFF.getType());
        ((MessageDto)object).setPath(string2);
        PluginWebsocketClient.sendWsMessage((MessageDto)object, (Project)a);
    }

    public static void getCommitMessage(Project project, String string, JsonObject jsonObject) {
        boolean bl;
        JsonObject a;
        Object a2 = string;
        Project a3 = project;
        EditorTextField editorTextField = CommitMessageSuggestionAction.COMMIT_MESSAGE_MAP.get(a2);
        if ((a = a.get(CancelRequestTip.H("\u0007\u0002\u0015\u0000")).getAsJsonObject()).has(NewFileUtils.H("]\u001eS\r"))) {
            String string2 = a.get(CancelRequestTip.H("\u0017\u0006\u0019\u0015")).getAsString();
            ApplicationManager.getApplication().invokeLater(() -> {
                Object a = string2;
                EditorTextField a2 = editorTextField;
                a = a2.getText() + ((String)a).replace(NewFileUtils.H("_\u0002P"), "");
                if (((String)(a = GitReviewService.removeMarkdownCodeBlocks((String)a))).startsWith(CancelRequestTip.H("\n\u0006\u0013\n\t\u0002\u0015\f"))) {
                    a = ((String)a).replaceFirst(NewFileUtils.H("@\u001eY\u0012C\u001a_\u0014"), "");
                }
                a2.setText((String)a);
            });
        }
        if (a.has(NewFileUtils.H("\u0018G\u001fN\u001d")) && (bl = a.get(CancelRequestTip.H("\u0000\r\u0007\u0004\u0005")).getAsBoolean())) {
            CommitMessageSuggestionAction.COMMIT_MESSAGE_BUTTON.set(false);
            PluginWebsocketClient.AGENT_REQUEST.remove(a2);
            CommitMessageSuggestionAction.COMMIT_MESSAGE_MAP.remove(a2);
            a2 = editorTextField.getText();
            a2 = a2.trim();
            if (StringUtils.isBlank((CharSequence)a2)) {
                CommonService.messageBus(a3, NewFileUtils.H("\u66bf\u658f\u63ff\u4ed9\u4fc8\u6014\u7534\u6269"), MessageType.INFO);
                return;
            }
            ApplicationManager.getApplication().invokeLater(() -> GitReviewService.ME(editorTextField, (String)a2));
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAgentAction(CommandEnum commandEnum, JsonObject jsonObject, Project project) {
        void a;
        CommandEnum commandEnum2 = commandEnum;
        String string = a.get(CancelRequestTip.H("\u0003\u000e")).getAsString();
        switch (commandEnum2) {
            case GIT_DIFF: {
                void a2;
                PluginWebsocketClient.AGENT_REQUEST.remove(string);
                CommandEnum a3 = GitReviewService.getGiffDiff((JsonObject)a);
                SocketMessageHandleListener.send2Web((Project)a2, (Object)a3);
                return;
            }
            case GIT_REVIEW: {
                void a2;
                CommandEnum a3 = GitReviewService.getGiffReview(string, (JsonObject)a);
                SocketMessageHandleListener.send2Web((Project)a2, (Object)a3);
                return;
            }
            case GIT_COMMIT_MESSAGE: {
                void a2;
                GitReviewService.getCommitMessage((Project)a2, string, (JsonObject)a);
                return;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public static JsonObject getGiffReview(String string, JsonObject jsonObject) {
        String string2 = string;
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty(CancelRequestTip.H("\u0015\u0018\u0003\u0016"), WebViewResponseTypeEnum.CODE_REVIEW_RECEIVER_CHANGE_RESULT.getType());
        String a = null;
        try {
            void a2;
            a = a2.get(NewFileUtils.H("O\u0018M\n")).getAsJsonObject();
            if (a.has(CancelRequestTip.H("\b\u000f\u0005\u0016\u0017"))) {
                PluginWebsocketClient.AGENT_REQUEST.remove(string2);
            }
        }
        catch (NullPointerException nullPointerException) {}
        JsonObject jsonObject3 = jsonObject2;
        jsonObject3.add(NewFileUtils.H("\u0003J\u0015L\u000e"), (JsonElement)a);
        return jsonObject3;
    }

    public static JsonObject getGiffDiff(JsonObject jsonObject) {
        JsonObject a;
        JsonObject jsonObject2 = jsonObject;
        JsonObject jsonObject3 = a = new JsonObject();
        jsonObject3.addProperty(CancelRequestTip.H("\u0016\u001b\u0012\u0007"), WebViewResponseTypeEnum.CODE_REVIEW_RECEIVER_CODE_REVIEW.getType());
        jsonObject3.add(NewFileUtils.H("\u0003I\u0016]\u001f"), (JsonElement)jsonObject2);
        return jsonObject3;
    }

    public GitReviewService() {
        GitReviewService a;
    }

    private static /* synthetic */ void ME(EditorTextField editorTextField, String string) {
        Object a = string;
        EditorTextField a2 = editorTextField;
        a2.setText((String)a);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAction(WebViewDataTypeEnum webViewDataTypeEnum, JsonObject jsonObject, Project project) {
        WebViewDataTypeEnum a = project;
        WebViewDataTypeEnum a2 = webViewDataTypeEnum;
        switch (a2) {
            case CODE_REVIEW_PAGE_READY: {
                if (!StringUtils.isNotBlank((CharSequence)PrepushReviewAction.path)) break;
                GitReviewService.sendGitDiffRequest(PrepushReviewAction.path, (Project)a);
                PrepushReviewAction.PAGE_READY.set(true);
                return;
            }
            case CODE_REVIEW_GET_CHANGE_RESULT: {
                void a3;
                GitReviewService.sendCodeReviewRequest((JsonObject)a3, (Project)a);
                return;
            }
            case CODE_REVIEW_GET_CODEREVIEW_LIST: {
                void a3;
                GitReviewService.sendGitDiffRequest(((JsonObject)a3.get(CancelRequestTip.H("\u001b\n\u0007\u001f\u000f"))).get(NewFileUtils.H("Q\u0012T\u001a")).getAsString(), (Project)a);
                return;
            }
            case CODE_REVIEW_GET_CHANGE_RESULT_END: {
                PrepushReviewAction.PREPUSH_REVIEW_BUTTON.set(false);
                return;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void sendCodeReviewRequest(JsonObject jsonObject, Project project) {
        void a;
        Object a2;
        JsonObject jsonObject2 = jsonObject;
        JsonObject jsonObject3 = (JsonObject)jsonObject2.get(NewFileUtils.H("\u000f\\\u0003O\r"));
        String string = jsonObject3.get(CancelRequestTip.H("\u0007\u0016\u0004\u0018")).getAsString();
        int n = jsonObject3.get(NewFileUtils.H("Y\u000eN\t")).getAsInt();
        Object object = a2 = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.GIT_REVIEW.getType());
        ((MessageDto)a2).setPath(string);
        ((MessageDto)object).setData(n);
        PluginWebsocketClient.sendWsMessage((MessageDto)object, (Project)a);
    }
}
