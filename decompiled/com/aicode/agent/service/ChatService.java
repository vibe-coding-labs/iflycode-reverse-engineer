/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.IdUtil
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.intellij.openapi.application.Application
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.command.WriteCommandAction
 *  com.intellij.openapi.editor.Document
 *  com.intellij.openapi.editor.Editor
 *  com.intellij.openapi.editor.ScrollType
 *  com.intellij.openapi.editor.SelectionModel
 *  com.intellij.openapi.editor.impl.EditorImpl
 *  com.intellij.openapi.fileChooser.FileChooserDescriptor
 *  com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
 *  com.intellij.openapi.fileEditor.FileEditorManager
 *  com.intellij.openapi.fileEditor.OpenFileDescriptor
 *  com.intellij.openapi.fileTypes.FileTypeManager
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.ui.MessageType
 *  com.intellij.openapi.ui.TextBrowseFolderListener
 *  com.intellij.openapi.ui.TextFieldWithBrowseButton
 *  com.intellij.openapi.util.TextRange
 *  com.intellij.openapi.vfs.LocalFileSystem
 *  com.intellij.openapi.vfs.VirtualFile
 *  com.intellij.openapi.wm.ToolWindowManager
 *  com.intellij.psi.PsiFile
 *  com.intellij.psi.PsiManager
 *  com.intellij.ui.components.JBTextField
 *  com.intellij.ui.content.ContentManager
 *  git4idea.GitRemoteBranch
 *  git4idea.repo.GitRepository
 *  git4idea.repo.GitRepositoryManager
 *  org.apache.commons.collections.CollectionUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.jetbrains.annotations.NotNull
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.agent.service;

import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.action.CommitMessageSuggestionAction;
import com.aicode.action.PrepushReviewAction;
import com.aicode.action.batch.doc.BatchFunctionCommentAction;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.ResponseDto;
import com.aicode.agent.dto.WebRequestDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.dto.chat.CommentInfo;
import com.aicode.agent.dto.chat.FirstChatMessage;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.agent.service.CommonService;
import com.aicode.agent.service.GitReviewService;
import com.aicode.agent.service.PluginAgentProcessService;
import com.aicode.agent.service.RecentFilesManager;
import com.aicode.agent.service.RestartableAgentProcessService;
import com.aicode.agent.service.SqlService;
import com.aicode.agent.service.UserService;
import com.aicode.content.util.EditorUtils;
import com.aicode.content.util.file.FileUtils;
import com.aicode.enums.AICodeStatus;
import com.aicode.enums.AssistantTypeEnum;
import com.aicode.enums.CodeCollectEnum;
import com.aicode.enums.RestartEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.exception.RequestCancelException;
import com.aicode.listener.GitBranchChangeListener;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.EditorManagerServiceImpl;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.status.AICodeStatusService;
import com.aicode.test.dto.RequestCaseCodeDto;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.AICodeUtils;
import com.aicode.util.ApplicationUtil;
import com.aicode.util.FileUtil;
import com.aicode.util.NewFileUtils;
import com.aicode.util.StringUtils;
import com.aicode.view.PluginToolWindowPanel;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.content.ContentManager;
import git4idea.GitRemoteBranch;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class ChatService {
    public static List<String> NEED_CODE_LIST;
    private static final Logger enum;
    public static ConcurrentNavigableMap<String, String> SESSION_ID;

    public static JsonObject getTalkList(JsonObject jsonObject) {
        JsonObject jsonObject2;
        JsonObject jsonObject3;
        JsonObject jsonObject4 = jsonObject;
        JsonObject a = null;
        try {
            jsonObject3 = a = jsonObject4.get(RequestCancelException.H("F$Q#")).getAsJsonArray();
        }
        catch (Exception exception) {
            enum.error(CommandEnum.TALK_HISTORY.getType() + exception.getMessage(), (Throwable)exception);
            jsonObject3 = a;
        }
        a = jsonObject3 == null ? new JsonArray() : a;
        JsonObject jsonObject5 = jsonObject2 = new JsonObject();
        jsonObject5.addProperty(NewFileUtils.H("I\u0016J\r"), WebViewDataTypeEnum.CHAT_RECEIVER_HISTORY_LIST.getType());
        jsonObject5.add(RequestCancelException.H(")C)P'"), (JsonElement)a);
        return jsonObject5;
    }

    /*
     * WARNING - void declaration
     */
    private static void Ye(Project project, MessageDto messageDto, RequestCaseCodeDto requestCaseCodeDto) {
        Object object;
        Object a22 = requestCaseCodeDto;
        Project a = project;
        if (a22 == null) {
            return;
        }
        if ((a22 = ((RequestCaseCodeDto)a22).getValue()) == null) {
            return;
        }
        Object object2 = object = null != ((RequestCaseCodeDto.ValueDTO)a22).getContext() ? ((RequestCaseCodeDto.ValueDTO)a22).getContext().getMethods() : null;
        if (CollectionUtils.isEmpty(object)) {
            return;
        }
        try {
            object.sort((commentInfo, commentInfo2) -> {
                CommentInfo a = commentInfo2;
                CommentInfo a2 = commentInfo;
                return a.getIndex() - a2.getIndex();
            });
            a22 = object.iterator();
            while (a22.hasNext()) {
                void a3;
                object = (CommentInfo)a22.next();
                ChatService.handleCodeComment(a, (CommentInfo)object, (MessageDto)a3);
            }
        }
        catch (Exception a22) {
            enum.error(NewFileUtils.H("\u6213\u91e8\u91b2\u7e9e\u5182\u655b\u6c91\u91dd\uff5fP\u0004"), (Object)a22.getMessage());
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void sendError2Web(JsonObject jsonObject, Project project, MessageDto messageDto) {
        void a;
        FirstChatMessage.ValueDTO valueDTO;
        Object a2 = messageDto;
        JsonObject a3 = jsonObject;
        String string = BasicActionsBundle.message(NewFileUtils.H("8U\rS\nD]_\u0006\u001dZ7.Y\u000bC\f"), new Object[0]);
        String string2 = "";
        if (a3.has(RequestCancelException.H(">@3"))) {
            string = a3.get(NewFileUtils.H("\u0014_\u0019")).getAsString();
        }
        if (a3.has(RequestCancelException.H("W<W1"))) {
            string2 = a3.get(NewFileUtils.H("H\u0016H\u001b")).getAsString();
        }
        FirstChatMessage.ValueDTO valueDTO2 = valueDTO = new FirstChatMessage.ValueDTO();
        FirstChatMessage.ValueDTO valueDTO3 = valueDTO;
        valueDTO3.setId(((MessageDto)a2).getId());
        valueDTO3.setSessionId(((MessageDto)a2).getSessionId());
        valueDTO2.setErrorMessage(string);
        valueDTO2.setCode(string2);
        a2 = ChatService.getErrorChatResponse(valueDTO2);
        SocketMessageHandleListener.send2Web((Project)a, a2);
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void ZC(Project object, String string, int n, AtomicInteger atomicInteger, int n2, String string2) {
        Project a;
        void a2;
        void a3;
        void a4;
        void a5;
        Project a6;
        Project project = object;
        object = string2;
        Project project2 = a6 = project;
        WriteCommandAction.runWriteCommandAction((Project)project2, () -> ChatService.of((String)a5, project2, (int)a4, (AtomicInteger)a3, (int)a2, (String)a));
    }

    /*
     * WARNING - void declaration
     */
    public static void handleParseWebUrlErr(JsonObject jsonObject, Project project, String string) {
        void a;
        JsonObject jsonObject3;
        void a2;
        JsonObject jsonObject4 = jsonObject;
        JsonObject jsonObject5 = Optional.ofNullable(jsonObject4).filter(jsonObject2 -> {
            JsonObject a = jsonObject2;
            JsonObject a2 = jsonObject4;
            return a2.has(RequestCancelException.H("~\u001cC1"));
        }).map(jsonObject2 -> {
            JsonObject a = jsonObject2;
            JsonObject a2 = jsonObject4;
            return a2.getAsJsonObject(NewFileUtils.H("n9\u007f8"));
        }).orElseGet(JsonObject::new);
        JsonObject a3 = new JsonObject();
        a3.addProperty(RequestCancelException.H("W=S!"), WebViewDataTypeEnum.CHAT_SEND_VALID_WEBSITE_RESULT.getType());
        if (StringUtils.equals((CharSequence)NewFileUtils.H("\t^\f\\"), (CharSequence)a2)) {
            jsonObject5.addProperty(RequestCancelException.H("7q\u0000P%D!"), BasicActionsBundle.message(NewFileUtils.H("\u0011B\u001aq(JSL\u000fO\u001cNWn.IWY\fwgY\u001cP\u001fywH\u000bD\u001a"), new Object[0]));
            jsonObject3 = a3;
        } else {
            jsonObject5.addProperty(RequestCancelException.H("7q\u0000P%D!"), BasicActionsBundle.message(NewFileUtils.H("\u0007D\u0011V^_\u001cm8\u0001\u0010Y\u001aU\u0000OWk.Z\fI\rogY\u001cP\u001fywH\u000bD\u001a"), new Object[0]));
            jsonObject3 = a3;
        }
        jsonObject3.add(RequestCancelException.H("\u0005B(V!"), (JsonElement)jsonObject5);
        SocketMessageHandleListener.send2Web((Project)a, a3);
    }

    public static JsonObject getTalkPredictResult(JsonObject jsonObject, MessageDto messageDto) {
        JsonObject jsonObject2;
        Object a22;
        block4: {
            a22 = messageDto;
            JsonObject a = jsonObject;
            if (!a.has(NewFileUtils.H("F\u0011N\t"))) {
                return null;
            }
            try {
                a22 = a.get(RequestCancelException.H("Y;Q#")).getAsString();
                if (!StringUtils.isBlank((CharSequence)a22)) break block4;
                return null;
            }
            catch (Exception a22) {
                enum.error(a22.getMessage(), (Throwable)a22);
                return null;
            }
        }
        JsonObject jsonObject3 = jsonObject2 = new JsonObject();
        jsonObject3.addProperty(NewFileUtils.H("V\tJ\r"), WebViewDataTypeEnum.CHAT_PREDICT.getType());
        jsonObject3.addProperty(RequestCancelException.H("5\\6P'"), (String)a22);
        return jsonObject2;
    }

    /*
     * Unable to fully structure code
     */
    public static JsonObject getAgentChatResponse(JsonObject var0, MessageDto var1_2) {
        var2_3 = var0;
        if (!var2_3.has(NewFileUtils.H("Y\u000e_\u0018"))) {
            return null;
        }
        var4_4 = new JsonObject();
        var4_4.addProperty(RequestCancelException.H("V<D6"), WebViewDataTypeEnum.CHAT_UPDATE_CONVERSATION_LIST.getType());
        var3_5 = new JsonObject();
        var4_4.add(NewFileUtils.H("\u0004\\\u0003^\u001c"), (JsonElement)var3_5);
        v0 = var3_5;
        v0.addProperty(RequestCancelException.H("]7"), a.getId());
        v0.addProperty(NewFileUtils.H("\u001fD\u0000S\u001bR\u0001b\u001d"), a.getSessionId());
        a = var2_3.get(RequestCancelException.H("F$@2")).getAsJsonObject();
        var3_5.addProperty(NewFileUtils.H("O\nM\u001cN\u001dS\u0017i\u0016[\u001c"), RequestCancelException.H("V L'"));
        if (a.has(NewFileUtils.H("I\nS\r"))) {
            var3_5.addProperty(RequestCancelException.H("L<L(M+G6"), a.get(NewFileUtils.H("I\nS\r")).getAsString());
        }
        if (a.has(RequestCancelException.H("S#_*P6v L'"))) {
            var3_5.addProperty(NewFileUtils.H("\u000bX\u000eM\u0003O0O\u001cI\nE\r"), a.get(RequestCancelException.H("S#_*P6v L'")).getAsString());
        }
        if (a.has(NewFileUtils.H("I\u0016[\u001c")) && StringUtils.equals((CharSequence)a.get(RequestCancelException.H("V<D6")).getAsString(), (CharSequence)NewFileUtils.H("M\u0017I\u0007D\u001d")) && null != (var5_6 = a.getAsJsonObject(RequestCancelException.H("F$@2"))) && null != var5_6.get(NewFileUtils.H("P\u000eL\u0007E\u001cW\u001cs\u000eF\u001c"))) {
            v1 = var3_5;
            v1.addProperty(RequestCancelException.H("L<L(M+G6"), var5_6.get(NewFileUtils.H("P\u000eL\u0007E\u001cW\u001cs\u000eF\u001c")).getAsString());
            v1.add(RequestCancelException.H("F$@2"), (JsonElement)a);
        }
        if (!a.has(NewFileUtils.H("\u0017O\u001dD\u000b"))) ** GOTO lbl34
        try {
            var5_6 = a.get(RequestCancelException.H("=P7[!")).getAsJsonObject();
            var3_5.addProperty(NewFileUtils.H("S\u0016S\u0002R\u0001X\u001c"), var5_6.get(RequestCancelException.H("4Z+Q$S6")).getAsString());
            v2 = var3_5;
            ** GOTO lbl31
        }
        catch (Exception var5_7) {
            try {
                v2 = var3_5;
lbl31:
                // 2 sources

                v2.addProperty(NewFileUtils.H("O\nM\u001cN\u001dS\u0017i\u0016[\u001c"), RequestCancelException.H("=P7[!"));
                PluginWebsocketClient.AGENT_REQUEST.remove(a.getId());
lbl34:
                // 2 sources

                v3 = var3_5;
                if (a.has(NewFileUtils.H("\u0017S\u000bN\u001d"))) {
                    v3.addProperty(RequestCancelException.H("K6h(l<L(M+G6"), Boolean.valueOf(false));
                    PluginWebsocketClient.AGENT_REQUEST.remove(a.getId());
                    return var4_4;
                }
                v3.addProperty(NewFileUtils.H("T\u001cw\u0002s\u0016S\u0002R\u0001X\u001c"), Boolean.valueOf(true));
                return var4_4;
            }
            catch (Exception a) {
                return var4_4;
            }
        }
    }

    private static void bF(JsonObject jsonObject) {
        JsonObject jsonObject2 = jsonObject;
        try {
            Object a = jsonObject2.get(NewFileUtils.H("H\u001f|;")).getAsJsonObject().get(RequestCancelException.H("C/O1^:U7c5c\u0018")).getAsString();
            File file = new File((String)a);
            if (file.exists()) {
                ApplicationManager.getApplication().invokeLater(() -> {
                    try {
                        Desktop.getDesktop().open(file);
                        return;
                    }
                    catch (Exception exception) {
                        return;
                    }
                });
                return;
            }
        }
        catch (Throwable a) {
            // empty catch block
        }
    }

    private static void Ad(Project project) {
        Project project2 = project;
        JsonObject jsonObject = new JsonObject();
        Project a = new JsonObject();
        JsonObject jsonObject2 = jsonObject;
        Project project3 = a;
        a.addProperty(RequestCancelException.H("0V6U%V+}%Q=V\u001eY7"), FileUtil.currentOpenFile(project2));
        project3.addProperty(NewFileUtils.H("T\u0005D\u0014A-N\t`\u0019C\n"), project2.getBasePath());
        project3.add(RequestCancelException.H("H0]1t<X6\\\u001eF&"), (JsonElement)FileUtil.openFileList(project2));
        jsonObject2.add(NewFileUtils.H("\u0013D\u0013N*{<^\u001d"), (JsonElement)a);
        jsonObject2.addProperty(RequestCancelException.H("c\u001eO7"), (Number)3);
        PluginWebsocketClient.sendWsMessage(CommandEnum.TALK_RECOMMEND_GAMEPLAY, jsonObject, project2);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAction(WebViewDataTypeEnum webViewDataTypeEnum, JsonObject jsonObject, String string, Project project) {
        void var3_7;
        WebViewDataTypeEnum a = var3_7;
        WebViewDataTypeEnum a2 = webViewDataTypeEnum;
        switch (a2) {
            case CHAT_CHOOSE_HISTORY_ITEM: {
                void a322;
                ChatService.getRequestForTalkHistory((JsonObject)a322, (Project)a);
                return;
            }
            case CHAT_GET_HISTORY_LIST: {
                ChatService.getHistoryList((Project)a);
                return;
            }
            case CHAT_DELETE_HISTORY_ITEM_ALL: 
            case CHAT_DELETE_HISTORY_ITEM: {
                void a322;
                ChatService.deleteHistoryItem((JsonObject)a322, (Project)a);
                return;
            }
            case CHAT_SEND_MSG: {
                JsonObject a4;
                ChatService.handleChatMessage((Project)a, (String)a4);
                return;
            }
            case CHAT_REFRESH_MODEL: 
            case CHAT_GET_MODEL_LIST: {
                PluginWebsocketClient.sendWsMessage(CommandEnum.USER_MODEL_LIST, (Project)a);
                return;
            }
            case CHAT_SET_MODEL: {
                void a322;
                UserService.SetModel((JsonObject)a322);
                return;
            }
            case CHAT_DELETE_MSG: {
                JsonObject a4;
                ChatService.handleChatDeleteMsg((String)a4, (Project)a);
                return;
            }
            case CHAT_STOP_RESPONSE: {
                void a322;
                ChatService.handleChatStop((Project)a, (JsonObject)a322);
                return;
            }
            case CHAT_NEW_CHAT: {
                ChatService.handleNewChat((Project)a);
                return;
            }
            case CHAT_GET_IDE_FILE_STATE: {
                void a322;
                ChatService.Cd((Project)a, (JsonObject)a322);
                return;
            }
            case CHAT_RECOMMEND_GAMEPLAY: {
                ChatService.Ad((Project)a);
                return;
            }
            case CHAT_GET_DOC_KNOWLEDGE_LIST: {
                ChatService.df((Project)a);
                return;
            }
            case CHAT_GET_CODE_KNOWLEDGE_LIST: {
                ChatService.VE((Project)a);
                return;
            }
            case CHAT_RESEND: {
                void a322;
                ChatService.dE((Project)a, (JsonObject)a322);
                return;
            }
            case CHAT_FEEDBACK_CATEGORY: {
                PluginWebsocketClient.sendWsMessage(CommandEnum.USER_FEEDBACK_CATEGORY, (Project)a);
                return;
            }
            case CHAT_CHOOSE_FILE: {
                ApplicationManager.getApplication().invokeLater(() -> ChatService.Me((Project)a));
                return;
            }
            case COMMON_DOWNLOAD_TABLE: {
                void a322;
                ChatService.jF((Project)a, (JsonObject)a322);
                return;
            }
            case CHAT_AGENT_REFRESH: {
                ChatService.refreshAgent((Project)a, false);
                return;
            }
            case CHAT_VALID_WEBSITE: {
                void a322;
                ChatService.Ed((Project)a, (JsonObject)a322);
                return;
            }
            case GIT_CODE_KNOWLEDGE_REPO_STATUS: 
            case GIT_AUTHORIZE: 
            case GIT_RE_INDEX: 
            case GIT_SAVE_TOKEN: 
            case GIT_GET_STATUS: {
                void a322;
                ChatService.ed((Project)a, (JsonObject)a322, a2);
                return;
            }
            case CHAT_GET_OPEN_DIR_LIST: {
                void a322;
                JsonArray a322 = RecentFilesManager.getRecentFileDirs((Project)a);
                JsonObject a4 = new JsonObject();
                a4.addProperty(RequestCancelException.H("J O="), WebViewDataTypeEnum.CHAT_SEND_OPEN_DIR_LIST.getType());
                JsonObject jsonObject2 = new JsonObject();
                JsonArray jsonArray = a322;
                jsonObject2.add(NewFileUtils.H("\fN\u0007m\u001aS\u0006"), (JsonElement)jsonArray);
                boolean bl = jsonArray == null || a322.size() == 0;
                String string2 = a322 = bl ? RequestCancelException.H("\u65a2\u6738\u8f8e\u626d\u5f59\u76d1\u5f0d") : "";
                if (bl) {
                    String a322 = ChatService.hasAnyDirectory(a.getBasePath()) ? NewFileUtils.H("\u6588\u6727\u8fa4\u6272\u5f73\u76ce\u5f27") : RequestCancelException.H("\u4ea1\u7839\u5ecc\u4e13\u65b9\u76d1\u5f0d");
                }
                jsonObject2.addProperty(NewFileUtils.H("\u001aW\u0018S\fu\nP\u0017"), (String)a322);
                JsonObject jsonObject3 = a4;
                jsonObject3.add(RequestCancelException.H(")_5J="), (JsonElement)jsonObject2);
                SocketMessageHandleListener.send2Web((Project)a, jsonObject3);
                return;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private static void vD(Project project, JsonObject jsonObject, MessageDto messageDto) {
        void a;
        MessageDto a2 = messageDto;
        Project a3 = project;
        RequestCaseCodeDto requestCaseCodeDto = (RequestCaseCodeDto)PluginWebsocketClient.WEB_REQUEST_DATA.get(a2.getId());
        if (null != requestCaseCodeDto) {
            ChatService.Ye(a3, a2, requestCaseCodeDto);
            PluginWebsocketClient.WEB_REQUEST_DATA.remove(a2.getId());
            return;
        }
        ChatService.handleCodeComment(a3, (JsonObject)a, a2);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static boolean isChat(CommandEnum commandEnum, JsonObject jsonObject, Project project, MessageDto messageDto, ResponseDto responseDto) {
        CommandEnum a = project;
        CommandEnum a2 = commandEnum;
        switch (a2) {
            case TALK_ASK: 
            case CODE_EXPLAIN: 
            case CODE_OPTIMIZE: 
            case CODE_COMMENT: 
            case CODE_INLINE_COMMENT: 
            case CODE_DEBUG: 
            case CODE_HELP: 
            case TALK_KNOWLEDGE: 
            case TALK_RESEND: 
            case TALK_INTELLIGENT: {
                void a3;
                void a4;
                ChatService.sendError2Web((JsonObject)a4, (Project)a, (MessageDto)a3);
                return true;
            }
            case GIT_DIFF: {
                void a4;
                JsonObject jsonObject2 = GitReviewService.getGiffDiff((JsonObject)a4);
                SocketMessageHandleListener.send2Web((Project)a, jsonObject2);
                PrepushReviewAction.PREPUSH_REVIEW_BUTTON.set(false);
                return true;
            }
            case GIT_REVIEW: {
                void a5;
                void a4;
                JsonObject jsonObject3 = GitReviewService.getGiffReview(a5.getId(), (JsonObject)a4);
                SocketMessageHandleListener.send2Web((Project)a, jsonObject3);
                PrepushReviewAction.PREPUSH_REVIEW_BUTTON.set(false);
                return true;
            }
            case CODE_TEST: {
                void a3;
                void a4;
                if (!a3.isChatTest()) return false;
                ChatService.sendError2Web((JsonObject)a4, (Project)a, (MessageDto)a3);
                return true;
            }
            case SQL_SOURCE_EDIT: {
                void a4;
                JsonObject jsonObject4 = SqlService.saveSource((JsonObject)a4);
                SocketMessageHandleListener.send2Web((Project)a, jsonObject4);
                return true;
            }
            case ERROR: {
                void a5;
                enum.info(RequestCancelException.H("\u0004S6M0\u00070M,ZuQ!J0Ph{\u001b_1\nw^?"), (Object)a5.getMsg());
                return true;
            }
            case GIT_REPOSITORY_STATUS: {
                void a3;
                void a4;
                ChatService.Oe((JsonObject)a4, (Project)a, (MessageDto)a3);
                return false;
            }
            case USER_KNOWLEDGE_LIST: {
                void a3;
                ChatService.ae((Project)a, (MessageDto)a3, new JsonObject());
                return false;
            }
            case USER_FEEDBACK_CATEGORY: {
                ChatService.BD((Project)a, new JsonArray());
                return false;
            }
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private static void TE(Project project, PluginToolWindowPanel pluginToolWindowPanel) {
        void a;
        JsonObject jsonObject;
        Object a2;
        String string;
        Project project2;
        block5: {
            block4: {
                project2 = project;
                try {
                    if (AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.CODE_KNOWLEDGE_BASE.getPermission())) break block4;
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }
            string = GitBranchChangeListener.CURRENT_REPO.get(NewFileUtils.H("I\u000b{&h1"));
            a2 = GitBranchChangeListener.CURRENT_REPO.get(RequestCancelException.H("'Q#j7f\u001f"));
            if (!StringUtils.isBlank((CharSequence)string) && !StringUtils.isBlank((CharSequence)a2)) break block5;
            return;
        }
        JsonObject jsonObject2 = jsonObject = new JsonObject();
        jsonObject2.addProperty(NewFileUtils.H("I\u000b{&h1"), string);
        jsonObject2.addProperty(RequestCancelException.H("'Q#j7f\u001f"), (String)a2);
        a2 = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.GIT_CODE_KNOWLEDGE_REPO_STATUS.getType());
        a2.setData(jsonObject);
        ApplicationManager.getApplication().invokeLater(() -> ChatService.ZE((PluginToolWindowPanel)a, (MessageDto)a2, project2));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void Mf(Project object, String string, int n, AtomicInteger atomicInteger, int n2, String string2) {
        Project a;
        void a2;
        void a3;
        void a4;
        void a5;
        Project a6;
        Project project = object;
        object = string2;
        Project project2 = a6 = project;
        WriteCommandAction.runWriteCommandAction((Project)project2, () -> ChatService.rE((String)a5, project2, (int)a4, (AtomicInteger)a3, (int)a2, (String)a));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void of(String string, Project project, int n, AtomicInteger atomicInteger, int n2, String string2) {
        String a;
        Object a2;
        block11: {
            block10: {
                String string3 = string;
                LocalFileSystem localFileSystem = LocalFileSystem.getInstance();
                a2 = new File(string3);
                a2 = localFileSystem.findFileByIoFile((File)a2);
                if (a2 != null) break block10;
                return;
            }
            a2 = new OpenFileDescriptor((Project)a, (VirtualFile)a2);
            a2 = FileEditorManager.getInstance((Project)a).openTextEditor((OpenFileDescriptor)a2, true);
            if (a2 != null) break block11;
            return;
        }
        try {
            void a3;
            int a4;
            String[] a5;
            void a222;
            Object object = a2;
            int n3 = object.getDocument().getLineStartOffset((int)a222);
            if (object.getSettings().isUseTabCharacter((Project)a)) {
                a = a2.getDocument().getText(new TextRange(a2.getDocument().getLineStartOffset((int)a222), a2.getDocument().getLineEndOffset((int)a222)));
                int n4 = AICodeStringUtil.leadingWhitespaceLength(a);
                a5.set(n4);
            }
            Object object2 = a2;
            object2.getCaretModel().moveToOffset(n3 + a5.get());
            object2.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
            a = object2.getSelectionModel();
            a.removeSelection();
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder a222 = new StringBuilder();
            if (a4 > 0) {
                a222.append(NewFileUtils.H("Z").repeat(a4));
                a5 = a3.split(RequestCancelException.H("Z"));
                a4 = 0;
                int n5 = a4;
                while (n5 < a5.length) {
                    StringBuilder stringBuilder2 = stringBuilder;
                    if (a4 == 0) {
                        stringBuilder2.append(a5[a4]);
                    } else {
                        stringBuilder2.append(NewFileUtils.H("p")).append((CharSequence)a222).append(a5[a4]);
                    }
                    n5 = ++a4;
                }
            }
            if (StringUtils.isBlank((CharSequence)stringBuilder.toString())) {
                stringBuilder = new StringBuilder((String)a3);
            }
            a2.getDocument().replaceString(a.getSelectionStart(), a.getSelectionEnd(), (CharSequence)(stringBuilder + "\n" + a222));
            a2.getContentComponent().requestFocus();
            return;
        }
        catch (Throwable throwable) {
            enum.error(RequestCancelException.H("\u91f3\u7ee0\u65b9\u683a\u6cdf\u919a"), throwable);
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private static void Oe(JsonObject jsonObject, Project project, MessageDto messageDto) {
        JsonArray a;
        JsonObject a2;
        void a3;
        JsonObject jsonObject3 = jsonObject;
        JsonArray jsonArray = Optional.ofNullable(jsonObject3).filter(jsonObject2 -> {
            JsonObject a = jsonObject2;
            JsonObject a2 = jsonObject3;
            return a2.has(RequestCancelException.H("d\u0006a\u0013"));
        }).map(jsonObject2 -> {
            JsonObject a = jsonObject2;
            JsonObject a2 = jsonObject3;
            return a2.getAsJsonArray(NewFileUtils.H("i>~9"));
        }).orElse(null);
        VirtualFile virtualFile = AICodeUtils.getVirtualFile((Project)a3);
        String string = null;
        if (virtualFile != null && !(a2 = FileTypeManager.getInstance().getFileTypeByFile(virtualFile)).isBinary()) {
            string = virtualFile.getPath();
            RecentFilesManager.fileOpened((Project)a3, virtualFile.getPath());
        }
        a2 = new JsonObject();
        if (a != null) {
            a2 = JsonParser.parseString((String)new Gson().toJson(a.getData())).getAsJsonObject();
        }
        JsonObject jsonObject4 = a2;
        jsonObject4.addProperty(RequestCancelException.H("0V6U%V+}%Q=X\u0010X6"), string);
        jsonObject4.add(NewFileUtils.H("\u0002W\tF\u0019\u007f\u001a[\u0016M:X\r"), (JsonElement)jsonArray);
        a = FileUtil.openFileList((Project)a3);
        a2.add(RequestCancelException.H("H0]1t<X6R\u0010G'"), (JsonElement)a);
        JsonArray jsonArray2 = a = new JsonObject();
        jsonArray2.addProperty(NewFileUtils.H("u*[\u001c"), WebViewDataTypeEnum.CHAT_RECEIVER_IDE_FILE_STATE.getType());
        jsonArray2.add(RequestCancelException.H("%\u007f\u0015A6"), (JsonElement)a2);
        SocketMessageHandleListener.send2Web((Project)a3, jsonArray2);
    }

    /*
     * WARNING - void declaration
     */
    public static FirstChatMessage getRightChatMessage2Web(Project project, String string) {
        Project project2 = project;
        try {
            void a;
            void v0 = a;
            Object a2 = ChatService.getSelectedCode((String)v0);
            JsonArray jsonArray = ChatService.UD((String)v0);
            return ChatService.getFirstChatMessage(project2, (String)a, (CodeInfoDto)a2, jsonArray);
        }
        catch (Exception a2) {
            return null;
        }
    }

    public static void send2Agent(Project project, FirstChatMessage firstChatMessage) {
        MessageDto messageDto;
        Object object;
        FirstChatMessage.ValueDTO a;
        Project project2 = project;
        MessageDto a2 = new MessageDto();
        a = ((FirstChatMessage)((Object)a)).getValue();
        JsonArray jsonArray = a.getIntelligent();
        String string = null;
        if (jsonArray != null) {
            int n;
            int n2 = n = 0;
            while (n2 < jsonArray.size()) {
                object = jsonArray.get(n).getAsJsonObject();
                if (object.has(RequestCancelException.H("K!U'")) && NewFileUtils.H("\u0018D\u0014M\u0013T\f").equals(object.get(RequestCancelException.H("K!U'")).getAsString())) {
                    string = object.get(NewFileUtils.H("\u000fA\u001eO\r")).getAsString();
                }
                n2 = ++n;
            }
        }
        MessageDto messageDto2 = a2;
        FirstChatMessage.ValueDTO valueDTO = a;
        a2.setId(a.getId());
        messageDto2.setSessionId(valueDTO.getSessionId());
        messageDto2.setCommand(valueDTO.getType());
        if (CommandEnum.CODE_HELP.getType().equals(string)) {
            Object object2 = a2;
            ((MessageDto)object2).setCommand(string);
            PluginWebsocketClient.sendWsMessage((MessageDto)object2, project2);
            return;
        }
        a2.setData(a.getInputText());
        CodeInfoDto codeInfoDto = a.getCodeInfo();
        if (codeInfoDto != null) {
            Object object3 = a2;
            CodeInfoDto codeInfoDto2 = codeInfoDto;
            a2.setPath(codeInfoDto2.getPath());
            ((MessageDto)object3).setRange(codeInfoDto2.getRange());
            ((MessageDto)object3).setContent(codeInfoDto.getAllContent());
        }
        if (StringUtils.isBlank((CharSequence)a2.getPath())) {
            a2.setPath(ChatService.getPath(project2));
        }
        if (StringUtils.equals((CharSequence)string, (CharSequence)CommandEnum.CODE_TEST.getType())) {
            a2.setChatTest(true);
        }
        MessageDto messageDto3 = a2;
        FirstChatMessage.ValueDTO valueDTO2 = a;
        a2.setIntelligent(a.getIntelligent());
        messageDto3.setRelatedFiles(valueDTO2.getRelatedFiles());
        messageDto3.setData(valueDTO2.getData());
        try {
            object = AICodeSettingsState.getInstance();
            a2.setLanguage(object.defaultLanguage);
            messageDto = a2;
        }
        catch (Exception exception) {
            enum.error(RequestCancelException.H("\u8b9a\u8a36\u4fb0\u605b\u83e4\u53e9\u5f5a\u5e1dx"), (Throwable)exception);
            messageDto = a2;
        }
        PluginWebsocketClient.sendWsMessage(messageDto, project2);
    }

    private static void dE(Project project, JsonObject jsonObject) {
        Project a22 = jsonObject;
        Project a = project;
        try {
            MessageDto messageDto;
            a22 = a22.getAsJsonObject(NewFileUtils.H("\u000fK\u0014~<"));
            MessageDto messageDto2 = messageDto = new MessageDto();
            MessageDto messageDto3 = messageDto;
            Project project2 = a22;
            messageDto.setId(project2.get(RequestCancelException.H("}\u0017")).getAsString());
            messageDto3.setSessionId(project2.get(NewFileUtils.H("\u0006H\fX\u0010E\u0016B=")).getAsString());
            messageDto3.setData(a22.get(RequestCancelException.H("-W$A6F&]\u0017")).getAsString());
            messageDto2.setCommand(CommandEnum.TALK_RESEND.getType());
            messageDto2.setModelCode(AICodeSettingsState.getInstance().modelCode);
            PluginWebsocketClient.sendWsMessage(messageDto2, a);
            return;
        }
        catch (Exception a22) {
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private static FirstChatMessage oE(String string, Project project, boolean bl, String string2) {
        String string3;
        void a;
        FirstChatMessage.ValueDTO valueDTO;
        FirstChatMessage a2;
        String string4 = string;
        String string5 = IdUtil.fastSimpleUUID();
        String a3 = StringUtils.isBlank((CharSequence)((CharSequence)SESSION_ID.get(a2.getBasePath()))) ? IdUtil.fastSimpleUUID() : (String)SESSION_ID.get(a2.getBasePath());
        SESSION_ID.put(a2.getBasePath(), a3);
        a2 = new FirstChatMessage();
        a2.setType(WebViewDataTypeEnum.CHAT_UPDATE_CONVERSATION_LIST.getType());
        FirstChatMessage.ValueDTO valueDTO2 = valueDTO = new FirstChatMessage.ValueDTO();
        valueDTO2.setId(string5);
        valueDTO2.setSessionId(a3);
        valueDTO2.setType(CommandEnum.TALK_INTELLIGENT.getType());
        string5 = new JsonArray();
        a3 = new JsonObject();
        String string6 = string5;
        String string7 = a3;
        string7.addProperty(NewFileUtils.H("U\n{<"), RequestCancelException.H(">A&] J8z\u0007"));
        string7.addProperty(NewFileUtils.H("\u000f@\u001f~<"), AssistantTypeEnum.IFLY_MATE.getType());
        string6.add((JsonElement)a3);
        String string8 = a3 = new JsonObject();
        string8.addProperty(RequestCancelException.H("J d\u0016"), NewFileUtils.H("\u001cD\u0014L\u0012e="));
        string8.addProperty(RequestCancelException.H("%_5a\u0016"), CommandEnum.CODE_DEBUG.getType());
        string6.add((JsonElement)string8);
        a3 = new JsonObject();
        a3.addProperty(NewFileUtils.H("U\n{<"), RequestCancelException.H("Z2W1U!T\u0000^4Z4K8s\u0016"));
        CodeInfoDto codeInfoDto = new CodeInfoDto();
        if (a != false) {
            string3 = string5;
            String string9 = string4;
            a3.addProperty(NewFileUtils.H("\u000f@\u001f~<"), RequestCancelException.H("\u8ba4\u5e51\u6209\u5214\u67e5\u7ef1\u7ab1\u76b4\u62f2\u953c\u65a7\u5fd5\uff69\u5e42\u6383\u4fb8\u89a7\u5194\u65f9\u6870\u305d\u53d8\u89b6\u5187\u7b7f\u4e3e\u4e73\u62b1\u956a"));
            codeInfoDto.setContent(string9);
            valueDTO.setInputText(string9);
        } else {
            void a4;
            a3.addProperty(NewFileUtils.H("\u000f@\u001f~<"), "\u8bf7\u5206\u6790\u4ee5\u4e0b\u4ee3\u7801\u95ee\u9898\u5e76\u8fdb\u884c\u4fee\u590d\uff1a \n " + string4);
            string3 = string5;
            void v6 = a4;
            valueDTO.setInputText((String)v6);
            codeInfoDto.setContent((String)v6);
        }
        string3.add((JsonElement)a3);
        FirstChatMessage firstChatMessage = a2;
        FirstChatMessage.ValueDTO valueDTO3 = valueDTO;
        valueDTO3.setIntelligent((JsonArray)string5);
        valueDTO3.setCodeInfo(codeInfoDto);
        firstChatMessage.setValue(valueDTO);
        return firstChatMessage;
    }

    private static void jF(Project project, JsonObject jsonObject) {
        Project a = jsonObject;
        Project a2 = project;
        try {
            Application application = ApplicationManager.getApplication();
            application.invokeLater(() -> ChatService.zd(application, a2, (JsonObject)a));
            return;
        }
        catch (Throwable throwable) {
            return;
        }
    }

    private static /* synthetic */ void hE(Project project, String string) {
        Object a = string;
        Project a2 = project;
        if ((a = ChatService.getFirstChatMessage2Web(a2, (String)a)) == null) {
            return;
        }
        PluginStartupActivity.handleExecutorService.execute(() -> ChatService.Dd(a2, (FirstChatMessage)a));
    }

    public static CodeInfoDto getSelectedCode(String a) {
        if (a == null) {
            return null;
        }
        return (CodeInfoDto)ApplicationManager.getApplication().runReadAction(() -> {
            int n;
            Editor editor;
            int n2;
            String string2 = a;
            Project project = ApplicationUtil.findCurrentProject();
            if (project == null) {
                return null;
            }
            Editor editor2 = EditorUtils.getSelectedEditor(project);
            if (editor2 == null) {
                return null;
            }
            String a = editor2.getSelectionModel();
            if (StringUtils.isBlank((CharSequence)a.getSelectedText()) && CommandEnum.CODE_COMMENT.getType().equals(string2)) {
                VirtualFile virtualFile = ((EditorImpl)editor2).getVirtualFile();
                PsiFile psiFile = PsiManager.getInstance((Project)project).findFile(virtualFile);
                if (null == psiFile) {
                    return null;
                }
                return BatchFunctionCommentAction.buildCodeInfo(virtualFile, psiFile, project);
            }
            if (!(CommandEnum.CODE_EXPLAIN.getType().equals(string2) || CommandEnum.CODE_OPTIMIZE.getType().equals(string2) || a != null && !StringUtils.isBlank((CharSequence)a.getSelectedText()))) {
                return null;
            }
            if ((CommandEnum.CODE_EXPLAIN.getType().equals(string2) || CommandEnum.CODE_OPTIMIZE.getType().equals(string2)) && (a == null || StringUtils.isBlank((CharSequence)a.getSelectedText()))) {
                n2 = 0;
                Editor editor3 = editor2;
                editor = editor3;
                n = editor3.getDocument().getTextLength() - 1;
            } else {
                String string3 = a;
                n2 = string3.getSelectionStart();
                n = string3.getSelectionEnd();
                editor = editor2;
            }
            return ChatService.getCodeInfoDto(editor, (SelectionModel)a, n2, n);
        });
    }

    /*
     * WARNING - void declaration
     */
    private static void cd(JsonObject jsonObject, MessageDto messageDto, Project project) {
        void a;
        void a2;
        JsonObject jsonObject3 = jsonObject;
        JsonObject a3 = Optional.ofNullable(jsonObject3).filter(jsonObject2 -> {
            JsonObject a = jsonObject2;
            JsonObject a2 = jsonObject3;
            return a2.has(RequestCancelException.H("L.@2"));
        }).map(jsonObject2 -> {
            JsonObject a = jsonObject2;
            JsonObject a2 = jsonObject3;
            return a2.getAsJsonObject(NewFileUtils.H("f1\\\u001b"));
        }).orElseGet(JsonObject::new);
        ChatService.ae((Project)a2, (MessageDto)a, a3);
    }

    public static void handleChatStop(Project project, JsonObject jsonObject) {
        MessageDto messageDto;
        Project a;
        Object a22;
        block4: {
            a22 = jsonObject;
            a = project;
            if (!a22.has(RequestCancelException.H("4_5T#"))) {
                return;
            }
            try {
                a22 = a22.get(NewFileUtils.H("\u001e@\u001fK\t")).getAsString();
                if (!StringUtils.isBlank((CharSequence)a22)) break block4;
                return;
            }
            catch (Throwable a22) {
                enum.error(a22.getMessage(), a22);
                return;
            }
        }
        MessageDto messageDto2 = messageDto = new MessageDto((String)a22, CommandEnum.ACTION_ABORT.getType());
        messageDto2.setData(a22);
        PluginWebsocketClient.sendWsMessage(messageDto2, a);
        messageDto = new JsonObject();
        messageDto.addProperty(RequestCancelException.H("J Q#"), WebViewDataTypeEnum.CHAT_UPDATE_CONVERSATION_LIST.getType());
        JsonObject jsonObject2 = new JsonObject();
        messageDto.add(NewFileUtils.H("\u001e@\u001fK\t"), (JsonElement)jsonObject2);
        JsonObject jsonObject3 = jsonObject2;
        jsonObject3.addProperty(RequestCancelException.H("H\""), (String)a22);
        jsonObject3.addProperty(NewFileUtils.H("\u001bx<I\u0001N\u001dw\b"), (String)SESSION_ID.get(a.getBasePath()));
        JsonObject jsonObject4 = jsonObject2;
        jsonObject2.addProperty(RequestCancelException.H("p\u0000V2Q7R#"), "");
        jsonObject4.addProperty(NewFileUtils.H("]\u0018I\u0018r!I\ru\nN\t"), RequestCancelException.H("M-N6"));
        jsonObject4.addProperty(NewFileUtils.H("F\u000es\u0006O*I\u0018N\u001dM\t"), Boolean.valueOf(false));
        SocketMessageHandleListener.send2Web(a, messageDto);
        PluginWebsocketClient.AGENT_REQUEST.remove(a22);
    }

    /*
     * WARNING - void declaration
     */
    public static void handleFeedbackCategory(JsonObject jsonObject, Project project) {
        void a;
        JsonObject jsonObject2;
        JsonObject jsonObject3 = jsonObject;
        JsonObject a2 = null;
        try {
            jsonObject2 = a2 = jsonObject3.get(RequestCancelException.H("G%\\.")).getAsJsonArray();
        }
        catch (Exception exception) {
            enum.error(CommandEnum.USER_FEEDBACK_CATEGORY.getType() + exception.getMessage());
            jsonObject2 = a2;
        }
        a2 = jsonObject2 == null ? new JsonArray() : a2;
        ChatService.BD((Project)a, (JsonArray)a2);
    }

    public static JsonObject getTalkHistory(Project project, JsonObject jsonObject) {
        JsonObject a3;
        Project project2 = project;
        Project a2 = null;
        try {
            a2 = a3.get(NewFileUtils.H("X\u000fC\u0004")).getAsJsonArray();
            if (a2 != null && a2.size() > 0) {
                a3 = a2.get(0).getAsJsonObject();
                SESSION_ID.put(project2.getBasePath(), a3.get(RequestCancelException.H("6@1M0L*a+")).getAsString());
            }
        }
        catch (Exception a3) {
            enum.error(CommandEnum.TALK_HISTORY.getType() + a3.getMessage(), (Throwable)a3);
        }
        a2 = a2 == null ? new JsonArray() : a2;
        a3 = new JsonObject();
        a3.add(NewFileUtils.H("F\u0018h\"B\u0007N\u000eN\u0001N\u001dp\u0007D\u0011"), (JsonElement)a2);
        a2 = new JsonObject();
        a2.add(RequestCancelException.H("[0L3@0M8W-G!"), (JsonElement)a3);
        JsonObject jsonObject2 = a3 = new JsonObject();
        jsonObject2.addProperty(NewFileUtils.H("H\u0017G\u0000"), WebViewDataTypeEnum.CHAT_GET_CONVERSATION.getType());
        jsonObject2.add(RequestCancelException.H("/B(]*"), (JsonElement)a2);
        return jsonObject2;
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void rE(String string, Project project, int n, AtomicInteger atomicInteger, int n2, String string2) {
        Object a;
        Object a2;
        block10: {
            block9: {
                String string3 = string;
                LocalFileSystem localFileSystem = LocalFileSystem.getInstance();
                a2 = new File(string3);
                a2 = localFileSystem.findFileByIoFile((File)a2);
                if (a2 != null) break block9;
                return;
            }
            a2 = new OpenFileDescriptor((Project)a, (VirtualFile)a2);
            a2 = FileEditorManager.getInstance((Project)a).openTextEditor((OpenFileDescriptor)a2, true);
            if (a2 != null) break block10;
            return;
        }
        try {
            void a3;
            int a4;
            void a52;
            void a222;
            Object object = a2;
            int n3 = object.getDocument().getLineStartOffset((int)a222);
            if (object.getSettings().isUseTabCharacter((Project)a)) {
                a = a2.getDocument().getText(new TextRange(a2.getDocument().getLineStartOffset((int)a222), a2.getDocument().getLineEndOffset((int)a222)));
                int n4 = AICodeStringUtil.leadingWhitespaceLength((String)a);
                a52.set(n4);
            }
            Object object2 = a2;
            object2.getCaretModel().moveToOffset(n3 + a52.get());
            object2.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
            a = object2.getSelectionModel();
            a.removeSelection();
            Object object3 = "";
            Object a222 = "";
            if (a4 > 0) {
                int n5 = a52 = 0;
                while (n5 < a4) {
                    a222 = (String)a222 + " ";
                    n5 = ++a52;
                }
                String[] a52 = a3.split(NewFileUtils.H("S"));
                int n6 = a4 = 0;
                while (n6 < a52.length) {
                    String string4 = object3;
                    object3 = a4 == 0 ? string4 + a52[a4] : string4 + "\n" + (String)a222 + a52[a4];
                    n6 = ++a4;
                }
            }
            if (StringUtils.isBlank((CharSequence)object3)) {
                object3 = a3;
            }
            a2.getDocument().replaceString(a.getSelectionStart(), a.getSelectionEnd(), (CharSequence)((String)object3 + "\n" + (String)a222));
            Object object4 = a2;
            object4.getContentComponent().requestFocus();
            EditorManagerServiceImpl.acceptCount((Editor)object4, a.getSelectionStart(), a.getSelectionStart() + ((String)object3 + "\n" + (String)a222).length(), CodeCollectEnum.INSERT);
            return;
        }
        catch (Throwable throwable) {
            enum.error(throwable.getMessage(), throwable);
            return;
        }
    }

    private static void VE(Project project) {
        Project project2 = project;
        Project a = new JsonObject();
        a.addProperty(RequestCancelException.H("'H$]\u0014\\:C?b\u0004s\u0016"), Boolean.valueOf(true));
        PluginWebsocketClient.sendWsMessage(CommandEnum.USER_KNOWLEDGE_LIST, a, project2);
    }

    static {
        SESSION_ID = new ConcurrentSkipListMap<String, String>();
        String[] stringArray = new String[5];
        stringArray[0] = CommandEnum.CODE_INLINE_COMMENT.getType();
        stringArray[1] = CommandEnum.CODE_SPLIT.getType();
        stringArray[2] = CommandEnum.CODE_EXPLAIN.getType();
        stringArray[3] = CommandEnum.CODE_OPTIMIZE.getType();
        stringArray[4] = CommandEnum.CODE_TEST.getType();
        NEED_CODE_LIST = Arrays.asList(stringArray);
        enum = LoggerFactory.getLogger(ChatService.class);
    }

    /*
     * WARNING - void declaration
     */
    public static void handleCodeDebug(Project project, String string, String string2, boolean bl) {
        void a;
        void a2;
        boolean a3 = bl;
        Project a4 = project;
        ChatService.handleCodeDebug(a4, null, null, (String)a2, (String)a, a3);
    }

    public static void getTalkPredict(Project project) {
        Project project2 = project;
        Object a = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.TALK_PREDICT.getType());
        a.setSessionId((String)SESSION_ID.get(project2.getBasePath()));
        PluginWebsocketClient.sendWsMessage((MessageDto)a, project2);
    }

    private static void df(Project project) {
        Project project2 = project;
        Project a = new JsonObject();
        a.addProperty(NewFileUtils.H("+D\u001aI\u0003]\u0004S>C\u0010\\\u0015O\u001cO\u001f"), Boolean.valueOf(true));
        PluginWebsocketClient.sendWsMessage(CommandEnum.USER_KNOWLEDGE_LIST, a, project2);
    }

    /*
     * WARNING - void declaration
     */
    public static void getRequestForTalkHistory(JsonObject jsonObject, Project project) {
        void a;
        JsonObject jsonObject2 = jsonObject;
        Object a2 = IdUtil.fastSimpleUUID();
        a2 = new MessageDto((String)a2, CommandEnum.TALK_HISTORY.getType());
        if (jsonObject2.has(RequestCancelException.H("/B(]*"))) {
            try {
                String string = jsonObject2.get(NewFileUtils.H("\u0005]\u0002B\u0000")).getAsString();
                if (StringUtils.isNotBlank((CharSequence)string)) {
                    SESSION_ID.put(a.getBasePath(), string);
                    ((MessageDto)a2).setSessionId(string);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        PluginWebsocketClient.sendWsMessage((MessageDto)a2, (Project)a);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private static void ed(Project project, JsonObject jsonObject, WebViewDataTypeEnum webViewDataTypeEnum) {
        void a;
        JsonObject a2;
        Project project2 = project;
        Project a3 = new JsonObject();
        if ((a2 = a2.get(NewFileUtils.H("\u000fJ\u0015_\u001d")).getAsJsonObject()).get(RequestCancelException.H("\\6")) != null) {
            a3.addProperty(NewFileUtils.H("C\u001c"), a2.get(RequestCancelException.H("\\6")).getAsString());
        }
        if (a2.get(NewFileUtils.H("\rN\tD,X\u0014")) != null) {
            a3.addProperty(RequestCancelException.H("'Q#[\u0006G>"), a2.get(NewFileUtils.H("\rN\tD,X\u0014")).getAsString());
        }
        if (a2.get(RequestCancelException.H("V!U=V:")) != null) {
            a3.addProperty(NewFileUtils.H("I\u000bJ\u0017I\u0010"), a2.get(RequestCancelException.H("V!U=V:")).getAsString());
        }
        if (a2.get(NewFileUtils.H("\rD\u0012O\u0016")) != null) {
            a3.addProperty(RequestCancelException.H("'[8P<"), a2.get(NewFileUtils.H("\rD\u0012O\u0016")).getAsString());
        }
        if (a2.get(RequestCancelException.H("@0D<`*E7")) != null) {
            a3.addProperty(NewFileUtils.H("_\u001a[\u0016\u007f\u0000Z\u001d"), (Number)a2.get(RequestCancelException.H("@0D<`*E7")).getAsInt());
        }
        switch (Ia.byte[a.ordinal()]) {
            case 22: 
            case 26: {
                PluginWebsocketClient.sendWsMessageForGitKnowledge(CommandEnum.GIT_CODE_KNOWLEDGE_REPO_STATUS, a3, project2, (WebViewDataTypeEnum)a);
                return;
            }
            case 23: {
                PluginWebsocketClient.sendWsMessageForGitKnowledge(CommandEnum.GIT_REPO_AUTHORIZE, a3, project2, (WebViewDataTypeEnum)a);
                return;
            }
            case 24: {
                a3.addProperty(NewFileUtils.H("B\nd\tO\u0016"), (Number)2);
                a3.addProperty(RequestCancelException.H("[&d&V?\\1"), (Number)0);
                PluginWebsocketClient.sendWsMessageForGitKnowledge(CommandEnum.GIT_CODE_KNOWLEDGE_RE_INDEX, a3, project2, (WebViewDataTypeEnum)a);
                return;
            }
            case 25: {
                PluginWebsocketClient.sendWsMessageForGitKnowledge(CommandEnum.GIT_SAVE_TOKEN, a3, project2, (WebViewDataTypeEnum)a);
                return;
            }
        }
    }

    private static /* synthetic */ String hF(Project project) {
        Project project2 = project;
        Project a = FileEditorManager.getInstance((Project)project2);
        if (a == null) {
            return null;
        }
        if ((a = a.getSelectedTextEditor()) == null) {
            return null;
        }
        return ((EditorImpl)a).getVirtualFile().getPath();
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray;
        String string = RequestCancelException.H("c\nH4v*^9\u0014>\u001alz\u001a]~\u0015$\u000bgqEY&P0\u0007.W+\u0012'Q'A!QxJ6P7");
        Object[] objectArray2 = new Object[2];
        objectArray2[0] = NewFileUtils.H("N\u0010FV\u0001[n0B\u0011\u0000\u001c]\rs;\u0004\nY\u001cN\u0003D\u0010\u0002<C\u0018_*E\u0000M\u0000@\u0014");
        switch (a) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = RequestCancelException.H("4F0n.L:^9]4Q=K\u0019V1]\"");
                break;
            }
            case 1: {
                objectArray = objectArray2;
                while (false) {
                }
                objectArray2[1] = NewFileUtils.H("\u001eY\u001a~\u0003U\u0006Y<C\u0018_4E\u0001H\bD\u0014");
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = RequestCancelException.H("2Q's2R=t/]\"");
                break;
            }
        }
        throw new IllegalStateException(String.format(string, objectArray));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void zd(Application application, Project project, JsonObject jsonObject) {
        void a;
        Application a2;
        Application a3 = jsonObject;
        Application application2 = a2 = application;
        application2.runReadAction(() -> ChatService.QE((Project)a, application2, (JsonObject)a3));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void Id(Application object, Project project, String string, int n, AtomicInteger atomicInteger, int n2, String string2) {
        Application a;
        void a2;
        void a3;
        void a4;
        void a5;
        void a6;
        Application application = object;
        object = string2;
        Application a7 = application;
        a7.runWriteAction(() -> ChatService.Mf((Project)a6, (String)a5, (int)a4, (AtomicInteger)a3, (int)a2, (String)a));
    }

    public static void handleNewChat(Project a) {
        SESSION_ID.put(a.getBasePath(), IdUtil.fastSimpleUUID());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static FirstChatMessage getFirstChatMessage2Web(Project project, String string) {
        Project project2 = project;
        try {
            Project a;
            JsonObject a2;
            a2 = JsonParser.parseString((String)a2).getAsJsonObject();
            if (!a2.has(NewFileUtils.H("=J\u0015Y\u001b"))) {
                return null;
            }
            JsonObject jsonObject = a2.getAsJsonObject(RequestCancelException.H("\u0017U?F1"));
            Object object = jsonObject.getAsJsonArray(NewFileUtils.H("\u0012E\r\u0019Bu\"L\u001cB\n"));
            String string2 = null;
            String string3 = null;
            if (object != null) {
                int n;
                int n2 = n = 0;
                while (n2 < object.size()) {
                    a = object.get(n).getAsJsonObject();
                    if (a.has(RequestCancelException.H("@*C1")) && NewFileUtils.H("Mv&F\u0018B\u001a").equals(a.get(RequestCancelException.H("@*C1")).getAsString())) {
                        string2 = a.get(NewFileUtils.H("=J\u0015Y\u001b")).getAsString();
                    }
                    if (a.has(RequestCancelException.H("@*C1")) && NewFileUtils.H("\u0018\u000f]p8_\u0018B\n").equals(a.get(RequestCancelException.H("@*C1")).getAsString())) {
                        string3 = a.get(NewFileUtils.H("=J\u0015Y\u001b")).getAsString();
                    }
                    n2 = ++n;
                }
                if (StringUtils.isBlank(string3)) {
                    JsonObject jsonObject2;
                    JsonObject jsonObject3 = jsonObject2 = new JsonObject();
                    jsonObject3.addProperty(RequestCancelException.H("@*C1"), NewFileUtils.H("\u0018\u000f]p8_\u0018B\n"));
                    jsonObject3.addProperty(RequestCancelException.H("\u0017U?F1"), AssistantTypeEnum.IFLY_MATE.getType());
                    Project project3 = a = new JsonArray();
                    project3.add((JsonElement)jsonObject2);
                    project3.addAll((JsonArray)object);
                    object = project3;
                }
            }
            JsonArray jsonArray = null;
            if ((AssistantTypeEnum.IFLY_TEST.getType().equals(string3) || AssistantTypeEnum.IFLY_PM.getType().equals(string3)) && jsonObject.has(NewFileUtils.H("i*Y\u0018A\r")) && (a = jsonObject.get(RequestCancelException.H("v\u0000F2^'")).getAsJsonObject()) != null && a.has(NewFileUtils.H("[\u001eG\u0018\bK}\rB\u0015I\r"))) {
                jsonArray = a.get(RequestCancelException.H("D4X2\u0017ab']?V'")).getAsJsonArray();
            }
            a = null;
            if (jsonObject.has(NewFileUtils.H("O\u0018X\u001f"))) {
                a = jsonObject.getAsJsonObject(RequestCancelException.H("P2G5"));
            }
            string2 = StringUtils.isBlank(string2) ? CommandEnum.TALK_INTELLIGENT.getType() : string2;
            CodeInfoDto codeInfoDto = ChatService.getSelectedCode(string2);
            FirstChatMessage firstChatMessage = ChatService.getFirstChatMessage(project2, string2, codeInfoDto, (JsonArray)object);
            Object object2 = object = firstChatMessage.getValue();
            ((FirstChatMessage.ValueDTO)object2).setRelatedFiles(jsonArray);
            ((FirstChatMessage.ValueDTO)object2).setData((JsonObject)a);
            return firstChatMessage;
        }
        catch (Exception a2) {
            return null;
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void handleCodeComment(Project project, JsonObject jsonObject, MessageDto messageDto) {
        Application application;
        int n;
        int n2;
        Object object;
        Object object2;
        String string;
        Project a;
        block9: {
            void a2;
            Project a222 = jsonObject;
            a = project;
            void v0 = a2;
            string = v0.getPath();
            object2 = v0.getLang();
            object = (WebRequestDto)PluginWebsocketClient.WEB_REQUEST.get(a2.getId());
            PluginWebsocketClient.WEB_REQUEST.remove(a2.getId());
            if (object == null) {
                return;
            }
            if (StringUtils.isBlank((CharSequence)(object = (String)((WebRequestDto)object).getValue()))) {
                return;
            }
            n2 = -1;
            n = -1;
            try {
                Project project2 = a222 = a222.get(RequestCancelException.H("[9Q#")).getAsJsonObject();
                if (NewFileUtils.H("[\u0000T\u001aU\u0006").equalsIgnoreCase((String)object2)) {
                    object2 = project2.get(RequestCancelException.H("9S?M\u0001^6B'")).getAsJsonArray();
                    application = object2.get(0).getAsJsonObject();
                    n2 = application.get(NewFileUtils.H("L\u001bT\r")).getAsInt();
                    n = application.get(RequestCancelException.H("8T:F2\\,@0")).getAsInt();
                } else {
                    object2 = project2.get(NewFileUtils.H("\u000bA\u001c]\r")).getAsJsonArray();
                    application = object2.get(0).getAsJsonObject();
                    n2 = application.get(RequestCancelException.H("S1K'")).getAsInt();
                    n = application.get(NewFileUtils.H("\u0012K\u0010Y\u0018C\u0006_\u001a")).getAsInt();
                }
                PluginWebsocketClient.AGENT_REQUEST.remove(a2.getId());
                if (!StringUtils.isBlank((CharSequence)string)) {
                    if (n2 != -1) {
                        if (n != -1) break block9;
                    }
                }
                return;
            }
            catch (Exception a222) {
                enum.error(a222.getMessage(), (Throwable)a222);
            }
        }
        int a222 = n2;
        object2 = new AtomicInteger(n);
        application = ApplicationManager.getApplication();
        int a2 = n;
        Application application2 = application;
        application2.invokeLater(() -> ChatService.Id(application2, a, string, a222, (AtomicInteger)object2, a2, (String)object));
    }

    /*
     * WARNING - void declaration
     */
    public static void handleChatDeleteMsg(String string, Project project) {
        String string2 = string;
        String a22 = JsonParser.parseString((String)string2).getAsJsonObject();
        if (!a22.has(NewFileUtils.H("'\\\u0003\\\u001e"))) {
            return;
        }
        try {
            a22 = a22.get(RequestCancelException.H("\rC)C4")).getAsJsonObject();
            String string3 = a22.get(NewFileUtils.H("@\u001f")).getAsString();
            String string4 = a22.get(RequestCancelException.H("1Q o\u0012M+\u007f5")).getAsString();
            if (StringUtils.isNotBlank((CharSequence)string3) && StringUtils.isNotBlank((CharSequence)string4)) {
                void a;
                MessageDto messageDto;
                MessageDto messageDto2 = messageDto = new MessageDto(string3, CommandEnum.TALK_DELETE.getType());
                messageDto2.setSessionId(string4);
                String[] stringArray = new String[1];
                stringArray[0] = string3;
                messageDto2.setData(Arrays.asList(stringArray));
                PluginWebsocketClient.sendWsMessage(messageDto, (Project)a);
            }
            PluginWebsocketClient.AGENT_REQUEST.remove(string3);
            return;
        }
        catch (Exception a22) {
            enum.error(a22.getMessage(), (Throwable)a22);
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void handleCodeDebug(Project project, String string, Integer n, String string2, String string3, boolean bl) {
        MessageDto a;
        void a2;
        void a222;
        void a3;
        Object a4 = n;
        Project a5 = project;
        FirstChatMessage a222 = ChatService.oE((String)a3, a5, (boolean)a222, (String)a2);
        if (a222 == null) {
            return;
        }
        FirstChatMessage.ValueDTO valueDTO = a222.getValue();
        a = ChatService.Uf(valueDTO.getId(), valueDTO.getSessionId(), (String)((Object)a), (Integer)a4, (String)a2, (String)a3);
        FirstChatMessage.ValueDTO valueDTO2 = valueDTO;
        a.setIntelligent(valueDTO2.getIntelligent());
        a4 = valueDTO2.getCodeInfo();
        if (a4 != null) {
            Object object = a4;
            MessageDto messageDto = a;
            ((CodeInfoDto)a4).setRange(messageDto.getRange());
            ((CodeInfoDto)object).setPath(messageDto.getPath());
            ((CodeInfoDto)object).setLanguage(a.getLanguage());
        }
        if (StringUtils.isBlank((CharSequence)((CodeInfoDto)a4).getPath())) {
            ((CodeInfoDto)a4).setPath(RequestCancelException.H("'L&Z="));
        }
        if (StringUtils.isBlank((CharSequence)((CodeInfoDto)a4).getLanguage())) {
            ((CodeInfoDto)a4).setLanguage(NewFileUtils.H("\rS\fE\u0017"));
        }
        PluginStartupActivity.handleExecutorService.execute(() -> {
            void a;
            Project a2;
            Object a3 = a;
            Project project2 = a2 = a5;
            CommonService.openPage(project2, PageEnum.CHAT_VIEW);
            if (!SocketMessageHandleListener.send2Web(project2, a).booleanValue()) {
                Project project3 = a2;
                project3.putUserData(WebViewWindowPanel.CODE_DEBUG_MESSAGE_DATA, (Object)a);
                project3.putUserData(WebViewWindowPanel.CODE_DEBUG_AGENT_DATA, a3);
                return;
            }
            PluginStartupActivity.handleExecutorService.execute(() -> ChatService.gF((MessageDto)a3, a2));
        });
    }

    public static boolean hasAnyDirectory(String string) {
        String string2 = string;
        if (string2 == null) {
            return false;
        }
        Object a = new File(string2);
        if (a.isDirectory() && (a = a.listFiles()) != null) {
            int n;
            int n2 = ((File[])a).length;
            int n3 = n = 0;
            while (n3 < n2) {
                if (a[n].isDirectory()) {
                    return true;
                }
                n3 = ++n;
            }
        }
        return false;
    }

    public static String getPath(Project project) {
        Project project2 = project;
        AtomicReference a = new AtomicReference();
        ApplicationManager.getApplication().invokeAndWait(() -> {
            AtomicReference a = project2;
            AtomicReference a2 = a;
            a2.set((String)ApplicationManager.getApplication().runReadAction(() -> ChatService.hF((Project)a)));
        });
        return (String)a.get();
    }

    /*
     * WARNING - void declaration
     */
    public static void deleteHistoryItem(JsonObject jsonObject, Project project) {
        void a;
        Object a2;
        JsonObject jsonObject2 = jsonObject;
        Object object = IdUtil.fastSimpleUUID();
        object = new MessageDto((String)object, CommandEnum.TALK_CLEAR.getType());
        if (jsonObject2.has(RequestCancelException.H("4U?I>")) && Objects.nonNull(a2 = jsonObject2.getAsJsonObject(NewFileUtils.H("\u001eJ\u0015V\u0014"))) && a2.has(RequestCancelException.H("'q\fQ-F*u?"))) {
            a2 = a2.get(NewFileUtils.H("\rn&N\u0007Y\u0000j\u0015")).getAsString();
            ((MessageDto)object).setSessionId((String)a2);
        }
        PluginWebsocketClient.sendWsMessage((MessageDto)object, (Project)a);
        ChatService.getHistoryList((Project)a);
    }

    private ChatService() {
        ChatService a;
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void Dd(Project project, FirstChatMessage firstChatMessage) {
        void a;
        Project project2 = project;
        void v0 = a;
        SocketMessageHandleListener.send2Web(project2, v0);
        if (v0.getValue() == null) {
            return;
        }
        Object a2 = a.getValue();
        if (a2.isErrorType()) {
            a2 = ChatService.getErrorChatResponse((FirstChatMessage.ValueDTO)a2);
            SocketMessageHandleListener.send2Web(project2, a2);
            return;
        }
        ChatService.send2Agent(project2, (FirstChatMessage)a);
    }

    public static JsonObject getErrorChatResponse(FirstChatMessage.ValueDTO valueDTO) {
        FirstChatMessage.ValueDTO valueDTO2 = valueDTO;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(RequestCancelException.H("V<D6"), WebViewDataTypeEnum.CHAT_UPDATE_CONVERSATION_LIST.getType());
        FirstChatMessage.ValueDTO a = new JsonObject();
        jsonObject.add(NewFileUtils.H("\u0004\\\u0003^\u001c"), (JsonElement)a);
        FirstChatMessage.ValueDTO valueDTO3 = a;
        valueDTO3.addProperty(RequestCancelException.H("]7"), valueDTO2.getId());
        valueDTO3.addProperty(NewFileUtils.H("\u001fD\u0000S\u001bR\u0001b\u001d"), valueDTO2.getSessionId());
        try {
            FirstChatMessage.ValueDTO valueDTO4 = a;
            FirstChatMessage.ValueDTO valueDTO5 = a;
            valueDTO5.addProperty(RequestCancelException.H("L<L(M+G6"), valueDTO2.getErrorMessage());
            valueDTO5.addProperty(NewFileUtils.H("O\nM\u001cN\u001dS\u0017i\u0016[\u001c"), RequestCancelException.H("=P7[!"));
            valueDTO4.addProperty(NewFileUtils.H("\tS\u0001O\u0000~\u0000O\u001c"), valueDTO2.getCode());
            valueDTO4.addProperty(RequestCancelException.H("K6h(l<L(M+G6"), false);
            a.addProperty(NewFileUtils.H("^\u0000O\u001c"), valueDTO2.getCode());
            return jsonObject;
        }
        catch (Exception exception) {
            return jsonObject;
        }
    }

    private static void ae(Project project, MessageDto messageDto, JsonObject jsonObject) {
        Project project2;
        JsonObject a;
        Object a2 = messageDto;
        Project a3 = project;
        JsonObject jsonObject2 = new JsonObject();
        if (((JsonObject)((MessageDto)a2).getData()).has(NewFileUtils.H("+D\u001aI\u0003]\u0004S>C\u0010\\\u0015J\u0019L\u001c"))) {
            JsonObject jsonObject3 = jsonObject2;
            jsonObject3.addProperty(RequestCancelException.H("D.D6"), WebViewDataTypeEnum.CHAT_RECEIVER_DOC_KNOWLEDGE_LIST.getType());
            a2 = a.get(NewFileUtils.H("+D\u001aI\u0003]\u0004S>C\u0010\\\u0015J\u0019L\u001c")).getAsJsonObject();
            jsonObject3.add(RequestCancelException.H("%Q;A6"), (JsonElement)a2);
            project2 = a3;
        } else {
            jsonObject2.addProperty(NewFileUtils.H("[\u0004[\u001c"), WebViewDataTypeEnum.CHAT_RECEIVER_CODE_KNOWLEDGE_LIST.getType());
            a2 = a.get(RequestCancelException.H("'H$]\u0014\\:C?U3S6")).getAsJsonObject();
            a = new JsonObject();
            try {
                Object object = a2;
                JsonArray jsonArray = object.getAsJsonArray(NewFileUtils.H("\u001cD\u0017[\u0018E\r"));
                String string = "";
                if (!object.get(RequestCancelException.H("Y;V\u0014P7B2G ")).isJsonNull()) {
                    string = a2.get(NewFileUtils.H("F\u0011I>O\u001d]\u0018X\n")).getAsString();
                }
                Boolean bl = false;
                if (!a2.get(RequestCancelException.H("P>A\u0014P7q\"@;")).isJsonNull()) {
                    bl = a2.get(NewFileUtils.H("O\u0014^>O\u001dn\b_\u0011")).getAsBoolean();
                }
                JsonObject jsonObject4 = a;
                jsonObject4.add(RequestCancelException.H("\u000eZ<T(B$_:p4G6|>G'"), (JsonElement)jsonArray);
                jsonObject4.addProperty(NewFileUtils.H("F\u0011I>O\u001d]\u0018X\n"), string);
                a.addProperty(RequestCancelException.H("P>A\u0014P7q\"@;"), bl);
                jsonObject2.add(NewFileUtils.H("\u000fN\u0011^\u001c"), (JsonElement)a);
                project2 = a3;
            }
            catch (Exception exception) {
                project2 = a3;
            }
        }
        SocketMessageHandleListener.send2Web(project2, jsonObject2);
    }

    public static boolean isCurrentBranchRemote(Project project) {
        Project project2 = project;
        Object a = (GitRepository)GitRepositoryManager.getInstance((Project)project2).getRepositoryForFile(project2.getBaseDir());
        if (a == null) {
            return false;
        }
        Project project3 = a;
        a = project3.getCurrentBranchName();
        for (GitRemoteBranch gitRemoteBranch : project3.getBranches().getRemoteBranches()) {
            if (!org.apache.commons.lang3.StringUtils.equals((CharSequence)("refs/remotes/" + (String)a), (CharSequence)gitRemoteBranch.getFullName()) && !org.apache.commons.lang3.StringUtils.equals((CharSequence)("refs/remotes/origin/" + (String)a), (CharSequence)gitRemoteBranch.getFullName())) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    public static JsonObject getKnowledgeChatResponse(JsonObject jsonObject, MessageDto messageDto) {
        void a;
        JsonObject jsonObject2;
        block6: {
            jsonObject2 = jsonObject;
            if (!jsonObject2.has(RequestCancelException.H("F$@2"))) {
                return null;
            }
            JsonObject jsonObject3 = new JsonObject();
            try {
                JsonObject a2 = jsonObject2.get(NewFileUtils.H("Y\u000e_\u0018")).getAsJsonObject();
                if (!a2.has(RequestCancelException.H("K7L,P U>")) || !a2.get(NewFileUtils.H("T\u001dS\u0006O\nJ\u0014")).getAsBoolean()) break block6;
                JsonObject jsonObject4 = jsonObject3;
                jsonObject4.addProperty(RequestCancelException.H("V<D6"), WebViewDataTypeEnum.CHAT_UPDATE_CONVERSATION_LIST.getType());
                JsonObject jsonObject5 = new JsonObject();
                jsonObject4.add(NewFileUtils.H("\u0004\\\u0003^\u001c"), (JsonElement)jsonObject5);
                JsonObject jsonObject6 = jsonObject5;
                jsonObject5.addProperty(RequestCancelException.H("]7"), a.getId());
                jsonObject6.addProperty(NewFileUtils.H("\u001fD\u0000S\u001bR\u0001b\u001d"), a.getSessionId());
                jsonObject6.addProperty(RequestCancelException.H("P R6Q7L=v<D6"), NewFileUtils.H("T\u001dS\u0006O\nJ\u0014"));
                if (a2.has(RequestCancelException.H("w\f]=I,O!n+P?P G "))) {
                    jsonObject5.add(NewFileUtils.H("h&B\u0017V\u0006P\u000bq\u0001O\u0015O\nX\n"), (JsonElement)a2.get(RequestCancelException.H("w\f]=I,O!n+P?P G ")).getAsJsonObject());
                }
                if (a2.has(NewFileUtils.H("_\u0011T\u0001U\u0005O\u0014r\u0017N\u001aG\r"))) {
                    jsonObject5.add(RequestCancelException.H("@;K+J/P>m=Q0X'"), (JsonElement)a2.get(NewFileUtils.H("_\u0011T\u0001U\u0005O\u0014r\u0017N\u001aG\r")).getAsJsonObject());
                }
                jsonObject5.addProperty(RequestCancelException.H("K6h(l<L(M+G6"), Boolean.valueOf(true));
                return jsonObject3;
            }
            catch (Exception a2) {
                return jsonObject3;
            }
        }
        return ChatService.getAgentChatResponse(jsonObject2, (MessageDto)a);
    }

    /*
     * WARNING - void declaration
     */
    private static MessageDto Uf(String string, String string2, String string3, Integer n, String string4, String string5) {
        void a;
        Object a2;
        CodeInfoDto.RangeDTO a3;
        void a4;
        ArrayList<CodeInfoDto.RangeDTO> a5;
        MessageDto a6;
        String string6 = string;
        MessageDto messageDto = a6 = new MessageDto();
        messageDto.setId(string6);
        messageDto.setSessionId((String)((Object)a5));
        messageDto.setCommand(CommandEnum.TALK_INTELLIGENT.getType());
        a5 = null;
        if (StringUtils.isNotBlank((CharSequence)a4) && a3 != null) {
            a2 = Math.max((Integer)((Object)a3) - 1, 0);
            a3 = new CodeInfoDto.RangeDTO();
            a3.setLine((Integer)a2);
            CodeInfoDto.RangeDTO rangeDTO = new CodeInfoDto.RangeDTO();
            rangeDTO.setLine((Integer)a2);
            a5 = new ArrayList<CodeInfoDto.RangeDTO>();
            a5.add(a3);
            a5.add(rangeDTO);
        }
        a6.setRange(a5);
        a6.setPath((String)a4);
        if (StringUtils.isNotBlank((CharSequence)a4)) {
            void v1 = a4;
            a2 = v1.substring(v1.lastIndexOf(RequestCancelException.H("~")) + 1);
            a6.setLanguage((String)a2);
        }
        a2 = new JsonObject();
        MessageDto messageDto2 = a6;
        a2.addProperty(NewFileUtils.H("\u0012N\nh(O\u001f"), (String)a);
        messageDto2.setData(a2);
        return messageDto2;
    }

    /*
     * WARNING - void declaration
     */
    public static FirstChatMessage getEditorChatMessage2Web(Project project, String string, CodeInfoDto codeInfoDto) {
        Project project2 = project;
        try {
            void a;
            void a2;
            Project a3 = ChatService.UD((String)a2);
            return ChatService.getFirstChatMessage(project2, (String)a2, (CodeInfoDto)a, (JsonArray)a3);
        }
        catch (Exception a3) {
            return null;
        }
    }

    private static /* synthetic */ void Me(Project project) {
        Project a;
        Project project2;
        Project project3 = project2 = project;
        String string = NewFileUtils.getChooseFile(project3);
        Project project4 = a = new JsonObject();
        project4.addProperty(NewFileUtils.H("w([\u001c"), WebViewDataTypeEnum.CHAT_CHOOSE_FILE.getType());
        project4.addProperty(RequestCancelException.H("%}\u0017A6"), string);
        SocketMessageHandleListener.send2Web(project3, project4);
    }

    private static void Cd(Project project, JsonObject jsonObject) {
        Project a = jsonObject;
        Project a2 = project;
        JsonObject jsonObject2 = new JsonObject();
        if (a.get(NewFileUtils.H("\u000fl3_\u001d")).isJsonObject()) {
            jsonObject2 = a.get(RequestCancelException.H("%s\u0019@7")).getAsJsonObject();
        }
        a = new JsonArray();
        a.add(a2.getBasePath());
        jsonObject2.add(NewFileUtils.H("\u001aU\u001aG\u001aH\rI6X\u000b"), (JsonElement)a);
        PluginWebsocketClient.sendWsMessage(CommandEnum.GIT_REPOSITORY_STATUS, jsonObject2, a2);
    }

    /*
     * WARNING - void declaration
     */
    private static void qd(JsonObject jsonObject, Project project) {
        void a;
        JsonObject a2;
        JsonObject jsonObject3 = jsonObject;
        JsonObject jsonObject4 = Optional.ofNullable(jsonObject3).filter(jsonObject2 -> {
            JsonObject a = jsonObject2;
            JsonObject a2 = jsonObject3;
            return a2.has(RequestCancelException.H("X:a\u0013"));
        }).map(jsonObject2 -> {
            JsonObject a = jsonObject2;
            JsonObject a2 = jsonObject3;
            return a2.getAsJsonObject(NewFileUtils.H("[\f~9"));
        }).orElseGet(JsonObject::new);
        JsonObject jsonObject5 = a2 = new JsonObject();
        jsonObject5.addProperty(RequestCancelException.H("T>E7"), WebViewDataTypeEnum.CHAT_SEND_VALID_WEBSITE_RESULT.getType());
        jsonObject5.add(NewFileUtils.H("\u000f^\u0001_\u001d"), (JsonElement)jsonObject4);
        SocketMessageHandleListener.send2Web((Project)a, jsonObject5);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static FirstChatMessage getFirstChatMessage(Project project, String string, CodeInfoDto codeInfoDto, JsonArray jsonArray) {
        void a;
        void a2;
        void a3;
        Object a4;
        Project project2 = project;
        String string2 = IdUtil.fastSimpleUUID();
        String string3 = StringUtils.isBlank((CharSequence)((CharSequence)SESSION_ID.get(project2.getBasePath()))) ? IdUtil.fastSimpleUUID() : (String)SESSION_ID.get(project2.getBasePath());
        SESSION_ID.put(project2.getBasePath(), string3);
        FirstChatMessage firstChatMessage = new FirstChatMessage();
        firstChatMessage.setType(WebViewDataTypeEnum.CHAT_UPDATE_CONVERSATION_LIST.getType());
        Object object = a4 = new FirstChatMessage.ValueDTO();
        ((FirstChatMessage.ValueDTO)a4).setId(string2);
        ((FirstChatMessage.ValueDTO)object).setSessionId(string3);
        ((FirstChatMessage.ValueDTO)object).setIntelligent((JsonArray)a3);
        ((FirstChatMessage.ValueDTO)object).setType(CommandEnum.TALK_INTELLIGENT.getType());
        if (a2 != null) {
            ((FirstChatMessage.ValueDTO)a4).setCodeInfo((CodeInfoDto)a2);
        }
        if (NEED_CODE_LIST.contains(a) && a2 == null) {
            Object object2 = a4;
            ((FirstChatMessage.ValueDTO)object2).setErrorType(true);
            ((FirstChatMessage.ValueDTO)object2).setErrorMessage(BasicActionsBundle.message(NewFileUtils.H("l6E\u001bK\u0018\u0014\u0006l\u007fX\u001cQ\n^\u001b\u0001\u0018[\tD\u000b"), new Object[0]));
        }
        FirstChatMessage firstChatMessage2 = firstChatMessage;
        firstChatMessage2.setValue((FirstChatMessage.ValueDTO)a4);
        if (firstChatMessage2 == null) {
            ChatService.enum(1);
        }
        return firstChatMessage2;
    }

    private static /* synthetic */ void gF(MessageDto messageDto, Project project) {
        MessageDto a = project;
        MessageDto a2 = messageDto;
        PluginWebsocketClient.sendWsMessage(a2, (Project)a);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void handleCodeComment(Project project, CommentInfo commentInfo, MessageDto messageDto) {
        AtomicInteger atomicInteger;
        int n;
        int n2;
        String string;
        Application a;
        String string2;
        Project project2;
        block9: {
            void a2;
            project2 = project;
            void v0 = a2;
            string2 = v0.getPath();
            String a32 = v0.getLang();
            string = a.getTextContext();
            if (StringUtils.isBlank((CharSequence)string)) {
                return;
            }
            n2 = -1;
            n = -1;
            try {
                if (RequestCancelException.H("D*K0J,").equalsIgnoreCase(a32)) {
                    JsonArray a32 = a.getBodyRange();
                    atomicInteger = a32.get(0).getAsJsonObject();
                    n2 = atomicInteger.get(NewFileUtils.H("L\u001bT\r")).getAsInt();
                    n = atomicInteger.get(RequestCancelException.H("8T:F2\\,@0")).getAsInt();
                } else {
                    JsonArray a32 = a.getRange();
                    atomicInteger = a32.get(0).getAsJsonObject();
                    n2 = atomicInteger.get(NewFileUtils.H("L\u001bT\r")).getAsInt();
                    n = atomicInteger.get(RequestCancelException.H("8T:F2\\,@0")).getAsInt();
                }
                PluginWebsocketClient.AGENT_REQUEST.remove(a2.getId());
                if (!StringUtils.isBlank((CharSequence)string2)) {
                    if (n2 != -1) {
                        if (n != -1) break block9;
                    }
                }
                return;
            }
            catch (Exception a32) {
                enum.error(NewFileUtils.H("\u91ec\u7eca\u65a7\u6811\u6cd2\u91a2"), (Throwable)a32);
            }
        }
        int a32 = n2;
        atomicInteger = new AtomicInteger(n);
        a = ApplicationManager.getApplication();
        int a2 = n;
        Application application = a;
        application.invokeLater(() -> {
            Application a;
            void a2;
            void a3;
            void a4;
            void a5;
            void a6;
            Application application = application;
            application = string;
            Application a7 = application;
            a7.runWriteAction(() -> ChatService.ZC((Project)a6, (String)a5, (int)a4, (AtomicInteger)a3, (int)a2, (String)a));
        });
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void QE(Project project, Application application, JsonObject jsonObject) {
        TextFieldWithBrowseButton textFieldWithBrowseButton;
        Project project2 = project;
        Object a = FileChooserDescriptorFactory.createSingleFolderDescriptor();
        a.setForcedToUseIdeaFileChooser(true);
        Object object = new TextFieldWithBrowseButton();
        String string = System.getProperty(NewFileUtils.H("\u0000^\u001aYWw\"f<"));
        String string2 = string + File.separator + "Downloads";
        if (new File(string2).exists()) {
            TextFieldWithBrowseButton textFieldWithBrowseButton2 = object;
            textFieldWithBrowseButton = textFieldWithBrowseButton2;
            textFieldWithBrowseButton2.setText(string2);
        } else {
            TextFieldWithBrowseButton textFieldWithBrowseButton3 = object;
            textFieldWithBrowseButton = textFieldWithBrowseButton3;
            textFieldWithBrowseButton3.setText(string);
        }
        textFieldWithBrowseButton.addBrowseFolderListener(new TextBrowseFolderListener((FileChooserDescriptor)a, project2));
        a = new JBTextField(RequestCancelException.H("r.L6F9Q7.\u0004g\u0005"));
        a.setPreferredSize(new Dimension(400, a.getPreferredSize().height));
        if (NewFileUtils.showDialog(project2, object, (JBTextField)a, null, BasicActionsBundle.message(NewFileUtils.H("[\u0001~ O\u001c\u0012\bQ\u0006B[I\u0010\\\u0017s\"j="), new Object[0]), BasicActionsBundle.message(RequestCancelException.H("?Y4J&gKW!F%S%\u00169[9Q}d\u0002g\u0010"), new Object[0])).show() == 0) {
            try {
                void a2;
                JsonObject a3;
                object = object.getText();
                a = a.getText();
                if (StringUtils.isBlank((CharSequence)object) || StringUtils.isBlank((CharSequence)a)) {
                    a3.invokeLater(() -> CommonService.messageBus(project2, RequestCancelException.H("\u65a0\u4eb6\u8dd7\u5fdb\u4e3c\u5458\u79c4\u4e5e\u80e9\u4e49\u7a6e\uff72"), MessageType.INFO));
                }
                JsonObject jsonObject2 = a3 = a2.get(NewFileUtils.H("\u000f~!~<")).getAsJsonObject();
                jsonObject2.addProperty(RequestCancelException.H("C/O1^:U7P\u0006`\u001b"), (String)object);
                jsonObject2.addProperty(NewFileUtils.H("K\u0016G\u001cQ,f<"), (String)a);
                PluginWebsocketClient.sendWsMessage(CommandEnum.TALK_DOWNLOAD_MARKDOWN_TABLE, a3, project2);
                return;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private static void Ed(Project project, JsonObject jsonObject) {
        JsonObject jsonObject2;
        Object a = jsonObject;
        Project a2 = project;
        if (!a.has(NewFileUtils.H("\u000fj5_\u001d"))) {
            return;
        }
        a = a.get(RequestCancelException.H("%u\u001f@7")).getAsString();
        JsonObject jsonObject3 = jsonObject2 = new JsonObject();
        jsonObject3.addProperty(NewFileUtils.H("d)O\n"), RequestCancelException.H("a"));
        jsonObject3.addProperty(NewFileUtils.H(",X\u0014"), (String)a);
        PluginWebsocketClient.sendWsMessage(CommandEnum.USER_PARSE_WEB_URL, jsonObject2, a2);
    }

    /*
     * WARNING - void declaration
     */
    private static void BD(Project project, JsonArray jsonArray) {
        void a;
        Project a2;
        Project project2 = project;
        Project project3 = a2 = new JsonObject();
        project3.addProperty(NewFileUtils.H("x'z="), WebViewDataTypeEnum.CHAT_GET_FEEDBACK_LIST.getType());
        project3.add(RequestCancelException.H("%r\u0018`\u0017"), (JsonElement)a);
        SocketMessageHandleListener.send2Web(project2, project3);
    }

    public static void getHistoryList(Project a) {
        PluginWebsocketClient.sendWsMessage(CommandEnum.TALK_LIST, a);
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void ZE(PluginToolWindowPanel pluginToolWindowPanel, MessageDto messageDto, Project project) {
        void a;
        void a2;
        block3: {
            PluginToolWindowPanel pluginToolWindowPanel2 = pluginToolWindowPanel;
            try {
                Object a3 = (WebViewWindowPanel)pluginToolWindowPanel2.getContent();
                if (Objects.isNull(a3) || !((WebViewWindowPanel)a3).isLoaded.get()) break block3;
                PluginWebsocketClient.sendWsMessage((MessageDto)a2, (Project)a);
                return;
            }
            catch (Throwable a3) {
                a.putUserData(GitBranchChangeListener.GIT_CODE_KNOWLEDGE_REPO_STATUS, (Object)a2);
                return;
            }
        }
        a.putUserData(GitBranchChangeListener.GIT_CODE_KNOWLEDGE_REPO_STATUS, (Object)a2);
    }

    @NotNull
    private static JsonArray UD(String string) {
        JsonObject jsonObject;
        String string2 = string;
        String a = new JsonArray();
        JsonObject jsonObject2 = jsonObject = new JsonObject();
        jsonObject2.addProperty(NewFileUtils.H("o0z="), RequestCancelException.H(">A&] p\u0002{\u0006"));
        jsonObject2.addProperty(NewFileUtils.H("\u000fz%\u007f="), AssistantTypeEnum.IFLY_MATE.getType());
        String string3 = a;
        string3.add((JsonElement)jsonObject);
        String string4 = a = new JsonObject();
        string4.addProperty(RequestCancelException.H("p\u001ae\u0017"), NewFileUtils.H("\u001cD\u0014v(d<"));
        string4.addProperty(RequestCancelException.H("%e\u000f`\u0017"), string2);
        a.add((JsonElement)string4);
        if (string3 == null) {
            ChatService.enum(0);
        }
        return string3;
    }

    public static void handleChatMessage(Project project, String string) {
        Object a = string;
        Project a2 = project;
        ApplicationManager.getApplication().invokeLater(() -> ChatService.hE(a2, (String)a));
    }

    @NotNull
    public static JsonObject getGamePlay(JsonObject jsonObject) {
        JsonObject jsonObject2 = jsonObject;
        JsonObject jsonObject3 = new JsonObject();
        jsonObject3.addProperty(NewFileUtils.H("[\u0004G\u0000"), WebViewDataTypeEnum.CHAT_RECEIVER_RECOMMEND_GAMEPLAY.getType());
        JsonArray jsonArray = new JsonArray();
        try {
            JsonObject a = jsonObject2.get(RequestCancelException.H("T6\\."));
            if (a instanceof JsonArray) {
                jsonArray = a.getAsJsonArray();
            }
        }
        catch (Exception a) {
            enum.error(CommandEnum.TALK_RECOMMEND_GAMEPLAY.getType() + a.getMessage(), (Throwable)a);
        }
        JsonObject jsonObject4 = jsonObject3;
        jsonObject4.add(NewFileUtils.H("\u0006N\u0011B\u0000"), (JsonElement)jsonArray);
        if (jsonObject4 == null) {
            ChatService.enum(2);
        }
        return jsonObject4;
    }

    public static void refreshAgent(Project project, boolean bl) {
        boolean bl2;
        boolean a3 = bl;
        Project a2 = project;
        if (a2 == null) {
            return;
        }
        CommitMessageSuggestionAction.COMMIT_MESSAGE_BUTTON.set(false);
        PrepushReviewAction.PREPUSH_REVIEW_BUTTON.set(false);
        AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
        PluginWebsocketClient.WEB_REQUEST.clear();
        PluginWebsocketClient.AGENT_REQUEST.clear();
        if (a3) {
            RestartableAgentProcessService.restartAttempts.set(0);
            bl2 = a3;
        } else {
            RestartableAgentProcessService.restartAttempts.set(1);
            bl2 = a3;
        }
        if (bl2) {
            Object a3 = BasicActionsBundle.message(RequestCancelException.H("4Q+R0\u0016>[6[7Fjq7^$L6u0V,V0W\u0005Y$D}K=]6"), new Object[0]);
            a3 = ToolWindowManager.getInstance((Project)a2).getToolWindow(a3);
            if (a3 != null) {
                ContentManager contentManager = a3.getContentManager();
                contentManager.removeAllContents(true);
                a3 = new PluginToolWindowPanel(a2, a3.getDisposable());
                contentManager.addContent(contentManager.getFactory().createContent(a3.getContent(), "", false));
                ChatService.TE(a2, (PluginToolWindowPanel)((Object)a3));
            }
        }
        CompletableFuture.runAsync(() -> {
            Project project2 = a2;
            RestartableAgentProcessService a = (RestartableAgentProcessService)PluginAgentProcessService.getInstance();
            if (!a.isRunning()) {
                PluginWebsocketClient.AGENT_WEBSOCKETS.clear();
                Object object = a;
                ((RestartableAgentProcessService)object).onRestartException(RestartEnum.REFRESH.getText(), RestartEnum.REFRESH.getCode());
                ((RestartableAgentProcessService)object).checkAgent(project2);
                return;
            }
            a.refreshAgent(project2);
        });
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAgentAction(CommandEnum commandEnum, JsonObject jsonObject, String string, MessageDto messageDto, Project project) {
        CommandEnum a = jsonObject;
        CommandEnum a2 = commandEnum;
        switch (a2) {
            case TALK_HISTORY: {
                void a3;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                void v0 = a3;
                SocketMessageHandleListener.send2Web((Project)v0, ChatService.getTalkHistory((Project)v0, (JsonObject)a));
                return;
            }
            case TALK_LIST: {
                void a3;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                JsonObject jsonObject2 = ChatService.getTalkList((JsonObject)a);
                SocketMessageHandleListener.send2Web((Project)a3, jsonObject2);
                return;
            }
            case TALK_RECOMMEND_GAMEPLAY: {
                void a3;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                JsonObject jsonObject3 = ChatService.getGamePlay((JsonObject)a);
                SocketMessageHandleListener.send2Web((Project)a3, jsonObject3);
                return;
            }
            case TALK_ASK: 
            case CODE_EXPLAIN: 
            case CODE_OPTIMIZE: 
            case CODE_COMMENT: 
            case CODE_INLINE_COMMENT: 
            case CODE_DEBUG: 
            case CODE_HELP: 
            case TALK_RESEND: 
            case TALK_INTELLIGENT: 
            case CODE_TEST: {
                void a5;
                void a3;
                JsonObject jsonObject4 = ChatService.getKnowledgeChatResponse((JsonObject)a, (MessageDto)a5);
                SocketMessageHandleListener.send2Web((Project)a3, jsonObject4);
                return;
            }
            case TALK_PREDICT: {
                void a5;
                void a3;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                JsonObject jsonObject5 = ChatService.getTalkPredictResult((JsonObject)a, (MessageDto)a5);
                if (jsonObject5 == null) break;
                SocketMessageHandleListener.send2Web((Project)a3, jsonObject5);
                return;
            }
            case CODE_COMMENT_RANGE: {
                void a5;
                void a3;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                ChatService.vD((Project)a3, (JsonObject)a, (MessageDto)a5);
                return;
            }
            case ACTION_OPEN_DOCUMENT: {
                void a3;
                void a4;
                MessageDto messageDto2 = (MessageDto)PluginWebsocketClient.AGENT_REQUEST.get(a4);
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                CommonService.refreshFunctionAction((Project)a3, messageDto2, (JsonObject)a);
                return;
            }
            case GIT_REPOSITORY_STATUS: {
                void a5;
                void a3;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                ChatService.Oe((JsonObject)a, (Project)a3, (MessageDto)a5);
                return;
            }
            case USER_KNOWLEDGE_LIST: {
                void a5;
                void a3;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                ChatService.cd((JsonObject)a, (MessageDto)a5, (Project)a3);
                return;
            }
            case TALK_DOWNLOAD_MARKDOWN_TABLE: {
                ChatService.bF((JsonObject)a);
                return;
            }
            case USER_FEEDBACK_CATEGORY: 
            case FEEDBACK_CATEGORY_INFO: {
                void a3;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                ChatService.handleFeedbackCategory((JsonObject)a, (Project)a3);
                return;
            }
            case USER_PARSE_WEB_URL: {
                void a3;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                ChatService.qd((JsonObject)a, (Project)a3);
                return;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public static CodeInfoDto getCodeInfoDto(Editor editor, SelectionModel selectionModel, int n, int n2) {
        CodeInfoDto.RangeDTO a;
        void a22;
        void a3;
        Editor editor2 = editor;
        CodeInfoDto a4 = new CodeInfoDto();
        Editor editor3 = editor2;
        Document document = editor3.getDocument();
        int n3 = document.getLineNumber((int)a3);
        Document document2 = document;
        a3 -= document2.getLineStartOffset(n3);
        int n4 = document2.getLineNumber((int)a22);
        a22 -= document.getLineStartOffset(n4);
        String string = ((EditorImpl)editor3).getVirtualFile().getName();
        String string2 = ((EditorImpl)editor2).getVirtualFile().getPath();
        String string3 = FileUtils.getFileExtension(string);
        a4.setContent(document.getText());
        if (a != null && StringUtils.isNotBlank((CharSequence)a.getSelectedText())) {
            a4.setContent(a.getSelectedText());
        }
        Object object = a4;
        Object object2 = a4;
        ((CodeInfoDto)object2).setAllContent(document.getText());
        ((CodeInfoDto)object2).setLanguage(string3);
        ((CodeInfoDto)object).setFileName(string);
        ((CodeInfoDto)object).setPath(string2);
        CodeInfoDto.RangeDTO rangeDTO = a = new CodeInfoDto.RangeDTO();
        rangeDTO.setLine(n3);
        rangeDTO.setCharacter((int)a3);
        CodeInfoDto.RangeDTO rangeDTO2 = a3 = new CodeInfoDto.RangeDTO();
        rangeDTO2.setLine(n4);
        rangeDTO2.setCharacter((int)a22);
        ArrayList<CodeInfoDto.RangeDTO> a22 = new ArrayList<CodeInfoDto.RangeDTO>();
        a22.add(a);
        a22.add(a3);
        a4.setRange(a22);
        return a4;
    }
}
