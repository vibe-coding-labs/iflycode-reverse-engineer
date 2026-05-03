/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.IdUtil
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.reflect.TypeToken
 *  com.intellij.openapi.application.Application
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.application.ModalityState
 *  com.intellij.openapi.editor.Document
 *  com.intellij.openapi.editor.Editor
 *  com.intellij.openapi.editor.impl.EditorImpl
 *  com.intellij.openapi.editor.markup.HighlighterTargetArea
 *  com.intellij.openapi.editor.markup.RangeHighlighter
 *  com.intellij.openapi.editor.markup.TextAttributes
 *  com.intellij.openapi.fileEditor.FileDocumentManager
 *  com.intellij.openapi.fileEditor.FileEditorManager
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.util.Key
 *  com.intellij.openapi.util.TextRange
 *  com.intellij.openapi.vfs.VirtualFile
 *  org.jetbrains.annotations.NotNull
 */
package com.aicode.agent.service;

import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.ResponseStreamDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.dto.chat.FirstChatMessage;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.service.ChatService;
import com.aicode.agent.service.CommonService;
import com.aicode.diff.DiffService;
import com.aicode.enums.AssistantTypeEnum;
import com.aicode.enums.FileExtensionEnum;
import com.aicode.inline.InlineChatService;
import com.aicode.inline.InlineChatStreamHandleService;
import com.aicode.inline.controller.SessionController;
import com.aicode.inline.dto.InlineChatInfo;
import com.aicode.inline.enums.InlineChatCategoryEnum;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.util.EditorKt;
import com.aicode.util.HandleCacheUtil;
import com.aicode.util.StringUtils;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import java.lang.invoke.LambdaMetafactory;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class InlineChatCommandService {
    public static final Key<List<CodeInfoDto.RangeDTO>> RANGE_KEY;
    public static final Key<Integer> VERSION_KEY;
    public static final Key<List<CodeInfoDto.RangeDTO>> BODY_RANGE_KEY;

    /*
     * WARNING - void declaration
     */
    private static void OD(@NotNull Project project, MessageDto messageDto, InlineChatCategoryEnum inlineChatCategoryEnum, SessionController sessionController) {
        void v0;
        void messageDto2;
        Object project2;
        InlineChatInfo categoryEnumByValue;
        Editor editor;
        VirtualFile sessionController2;
        Project project3;
        block6: {
            block5: {
                block4: {
                    project3 = project;
                    if (project3 == null) {
                        InlineChatCommandService.enum(0);
                    }
                    editor = sessionController2.getEditor();
                    project2 = categoryEnumByValue;
                    if (InlineChatCategoryEnum.DOC == categoryEnumByValue && editor.getUserData(RANGE_KEY) == null) {
                        project2 = InlineChatCategoryEnum.LINEDOC;
                    }
                    if (!sessionController2.isHasSelect()) break block4;
                    if (!StringUtils.isBlank((CharSequence)sessionController2.getOriginalSelectText()) || InlineChatCategoryEnum.DOC == categoryEnumByValue) break block5;
                    project2 = InlineChatCategoryEnum.GENERATE;
                    v0 = messageDto2;
                    break block6;
                }
                project2 = InlineChatCategoryEnum.GENERATE;
            }
            v0 = messageDto2;
        }
        v0.setId(IdUtil.fastSimpleUUID());
        void v1 = messageDto2;
        v1.setDirectName(((Enum)project2).name());
        v1.setCommand(CommandEnum.INLINECHAT_DIRECT.getType());
        v1.setStream(true);
        categoryEnumByValue = EditorKt.getInfoByEditor(editor);
        if (categoryEnumByValue != null) {
            categoryEnumByValue.setRequestId(messageDto2.getId());
        }
        messageDto2.setInlineChatVersion(categoryEnumByValue.getInlineChatVersion());
        void v2 = sessionController2;
        InlineChatCommandService.Ze((SessionController)v2, editor, (InlineChatCategoryEnum)((Object)project2));
        v2.setInlineChatCategoryEnum((InlineChatCategoryEnum)((Object)project2));
        sessionController2 = FileDocumentManager.getInstance().getFile(editor.getDocument());
        if (sessionController2 != null) {
            DiffService.copyFile(sessionController2.getPath(), editor.getDocument().getText(), sessionController2.getName(), "" + categoryEnumByValue.getInlineChatVersion());
        }
        PluginWebsocketClient.sendWsMessage((MessageDto)messageDto2, project3);
    }

    private static /* synthetic */ String nD(JsonObject jsonObject, JsonObject jsonObject2) {
        JsonObject a = jsonObject2;
        JsonObject a2 = jsonObject;
        return a2.get(InlineChatStatusServiceKt.H("/\u000f9\t")).getAsString();
    }

    private static /* synthetic */ boolean LF(JsonObject jsonObject, JsonObject jsonObject2) {
        JsonObject a = jsonObject2;
        JsonObject a2 = jsonObject;
        return a2.has(HandleCacheUtil.H("b8t>"));
    }

    private static void Se(Project project, MessageDto messageDto) {
        Object a = messageDto;
        Project a2 = project;
        ApplicationManager.getApplication().runReadAction(() -> InlineChatCommandService.Ue((MessageDto)a, a2));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void dd(MessageDto messageDto, CommandEnum commandEnum, Project project, String string) {
        void a;
        MessageDto messageDto2 = messageDto;
        InlineChatInfo a2 = EditorKt.inlineChatCacheData.get(messageDto2.getPath());
        if (a2 == null) {
            return;
        }
        if (messageDto2.getInlineChatVersion() != a2.getInlineChatVersion()) {
            return;
        }
        switch (ka.byte[a.ordinal()]) {
            case 1: 
            case 2: {
                void a3;
                Application application = ApplicationManager.getApplication();
                while (false) {
                }
                application.invokeLater(() -> InlineChatCommandService.bf(a2, (Project)a3, messageDto2));
                return;
            }
            case 3: {
                void a4;
                ApplicationManager.getApplication().invokeLater(() -> InlineChatCommandService.me(a2, (String)a4));
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private static void mE(Editor editor, Document document, Type type, JsonObject jsonObject, List<CodeInfoDto.RangeDTO> list, boolean bl) {
        void a;
        boolean bl2 = bl;
        Editor a2 = editor;
        Integer n = ((CodeInfoDto.RangeDTO)a.get(0)).getLine();
        Integer n2 = ((CodeInfoDto.RangeDTO)a.get(1)).getLine();
        if (n != null && n2 != null) {
            void a3;
            int a222;
            if (a222 == 0) {
                void a4;
                a222 = a2.getCaretModel().getOffset();
                int n3 = a4.getLineNumber(a222);
                void v0 = a4;
                void v1 = a4;
                if (StringUtils.isNotBlank((CharSequence)v1.getText(new TextRange(v1.getLineStartOffset(n3), a4.getLineEndOffset(n3))))) {
                    a2.getSelectionModel().setSelection(a4.getLineStartOffset(n.intValue()), a4.getLineEndOffset(n2.intValue()));
                }
            }
            List a222 = null;
            if (a3.has(InlineChatStatusServiceKt.H("\u001d1\u001f86*\u0000*\r"))) {
                void a5;
                JsonArray jsonArray = a3.get(HandleCacheUtil.H("*|(u\u0001g7g:")).getAsJsonArray();
                a222 = (List)new Gson().fromJson((JsonElement)jsonArray, (Type)a5);
            }
            Editor editor2 = a2;
            editor2.putUserData(BODY_RANGE_KEY, a222);
            editor2.putUserData(RANGE_KEY, (Object)a);
        }
    }

    /*
     * Unable to fully structure code
     */
    public static void handleAgentAction(String var0, CommandEnum var1_1, Project var2_2, MessageDto var3_3, JsonObject var4_4) {
        var5_5 = var0;
        switch (ka.byte[a.ordinal()]) lbl-1000:
        // 2 sources

        {
            case 1: {
                if (false) ** GOTO lbl-1000
                InlineChatCommandService.Ve((Project)a, (MessageDto)a, (JsonObject)a);
                PluginWebsocketClient.AGENT_REQUEST.remove(var5_5);
                return;
            }
            case 2: {
                if (InlineChatCommandService.zf((MessageDto)a)) {
                    return;
                }
                a = InlineChatCategoryEnum.getCategoryEnumByValue(Optional.ofNullable(a).filter((Predicate<JsonObject>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, LF(com.google.gson.JsonObject com.google.gson.JsonObject ), (Lcom/google/gson/JsonObject;)Z)((JsonObject)a)).map((Function<JsonObject, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, nD(com.google.gson.JsonObject com.google.gson.JsonObject ), (Lcom/google/gson/JsonObject;)Ljava/lang/String;)((JsonObject)a)).orElseGet((Supplier<String>)LambdaMetafactory.metafactory(null, null, null, ()Ljava/lang/Object;, <init>(), ()Ljava/lang/String;)()).trim());
                a = a.getOtherObject();
                if (a == null || !(a instanceof SessionController)) {
                    return;
                }
                a = (SessionController)a;
                ApplicationManager.getApplication().invokeLater((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, HF(com.aicode.inline.controller.SessionController ), ()V)((SessionController)a));
                switch (ka.enum[a.ordinal()]) {
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: {
                        v0 = ApplicationManager.getApplication();
                        while (false) {
                        }
                        v0.invokeLater((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, oD(com.aicode.inline.controller.SessionController com.intellij.openapi.project.Project com.aicode.agent.dto.MessageDto com.aicode.inline.enums.InlineChatCategoryEnum ), ()V)((SessionController)a, (Project)a, (MessageDto)a, (InlineChatCategoryEnum)a));
                        break;
                    }
                    case 5: {
                        InlineChatCommandService.Se((Project)a, (MessageDto)a);
                        break;
                    }
                }
                PluginWebsocketClient.AGENT_REQUEST.remove(var5_5);
                return;
            }
            case 3: {
                ApplicationManager.getApplication().invokeAndWait((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lF(com.aicode.agent.dto.MessageDto com.google.gson.JsonObject java.lang.String ), ()V)((MessageDto)a, (JsonObject)a, (String)var5_5), ModalityState.defaultModalityState());
                return;
            }
        }
        PluginWebsocketClient.AGENT_REQUEST.remove(var5_5);
    }

    /*
     * WARNING - void declaration
     */
    private static void XD(JsonObject jsonObject, Editor editor, Document document, boolean bl) {
        void a;
        void a2;
        void a3;
        block7: {
            JsonObject jsonObject2 = jsonObject;
            Type type = new TypeToken<List<CodeInfoDto.RangeDTO>>(){
                {
                    fa a;
                }
            }.getType();
            JsonObject jsonObject3 = jsonObject2.get(HandleCacheUtil.H("b8t>")).getAsJsonObject();
            if (!jsonObject3.has(InlineChatStatusServiceKt.H("\u0016*\u0000*\r"))) break block7;
            JsonArray jsonArray = jsonObject3.get(HandleCacheUtil.H("!g7g:")).getAsJsonArray();
            List a4 = (List)new Gson().fromJson((JsonElement)jsonArray, type);
            if (a4 != null) {
                if (a4.size() == 2) {
                    InlineChatCommandService.mE((Editor)a3, (Document)a2, type, jsonObject3, a4, (boolean)a);
                    return;
                }
            }
        }
        try {
            if (a == false) {
                int n = a3.getCaretModel().getOffset();
                int a4 = a2.getLineNumber(n);
                void v0 = a2;
                void v1 = a2;
                if (StringUtils.isNotBlank((CharSequence)v1.getText(new TextRange(v1.getLineStartOffset(a4), a2.getLineEndOffset(a4))))) {
                    a3.getSelectionModel().setSelection(a2.getLineStartOffset(a4), a2.getLineEndOffset(a4));
                    return;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void me(InlineChatInfo inlineChatInfo, String string) {
        InlineChatInfo inlineChatInfo2;
        InlineChatInfo inlineChatInfo3 = inlineChatInfo2 = inlineChatInfo;
        inlineChatInfo3.getEditor().getMarkupModel().removeAllHighlighters();
        SessionController a = inlineChatInfo3.getSessionController();
        if (a != null && inlineChatInfo2.getEditor() != null) {
            void a2;
            inlineChatInfo2.getSessionController().errorStop(inlineChatInfo2.getEditor());
            InlineChatStreamHandleService.handleErrorData(a, (String)a2);
            return;
        }
        EditorKt.removeEditor(a.getEditor());
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void bf(InlineChatInfo inlineChatInfo, Project project, MessageDto messageDto) {
        void a;
        InlineChatInfo a2;
        MessageDto a3 = messageDto;
        InlineChatInfo inlineChatInfo2 = a2 = inlineChatInfo;
        EditorKt.removeEditor(inlineChatInfo2.getEditor());
        EditorKt.closeCategoryPanel(inlineChatInfo2.getEditor());
        inlineChatInfo2.getEditor().getMarkupModel().removeAllHighlighters();
        InlineChatCommandService.Se((Project)a, a3);
    }

    static {
        BODY_RANGE_KEY = Key.create((String)InlineChatStatusServiceKt.H("9\u0011\t1\u0005-\u001f5\u0006!\u0014%\b1"));
        RANGE_KEY = Key.create((String)HandleCacheUtil.H("\u001aR\u0002K\u0016Y\u0012E\u0006"));
        VERSION_KEY = Key.create((String)InlineChatStatusServiceKt.H(">\u001f-\r2\u000e*\u0014%\b1"));
    }

    private static boolean zf(MessageDto messageDto) {
        MessageDto messageDto2 = messageDto;
        int n = messageDto2.getInlineChatVersion();
        InlineChatInfo a = EditorKt.inlineChatCacheData.get(messageDto2.getPath());
        if (a == null || n != a.getInlineChatVersion()) {
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private static boolean Sd(List<CodeInfoDto.RangeDTO> list, Editor editor, Document document) {
        void a;
        Document a2 = document;
        List<CodeInfoDto.RangeDTO> a3 = list;
        if (a.getSelectionModel().hasSelection()) {
            CodeInfoDto.RangeDTO rangeDTO = a3.get(0);
            CodeInfoDto.RangeDTO rangeDTO2 = a3.get(1);
            void v0 = a;
            int n = v0.getSelectionModel().getSelectionStart();
            int n2 = v0.getSelectionModel().getSelectionEnd();
            int n3 = a2.getLineNumber(n);
            int n4 = a2.getLineNumber(n2);
            int n5 = n - a2.getLineStartOffset(n3);
            int n6 = n2 - a2.getLineStartOffset(n4);
            if (rangeDTO.getLine() != n3 || rangeDTO.getCharacter() != n5) {
                return true;
            }
            if (rangeDTO2.getLine() != n4 || rangeDTO2.getCharacter() != n6) {
                return true;
            }
            Document document2 = a2;
            n3 = document2.getLineStartOffset(n3);
            n4 = document2.getLineEndOffset(n4);
            a.getSelectionModel().setSelection(n3, n4);
        } else {
            int n = a.getCaretModel().getOffset();
            int n7 = a2.getLineNumber(n);
            int n8 = n - a2.getLineStartOffset(n7);
            CodeInfoDto.RangeDTO rangeDTO = a3.get(0);
            if (rangeDTO.getLine() != n7 || rangeDTO.getCharacter() != n8) {
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    public static void handleAgentAction(Project project, MessageDto messageDto, String string, CommandEnum commandEnum) {
        void a;
        void a2;
        Object a3 = commandEnum;
        Project a4 = project;
        ApplicationManager.getApplication().invokeLater(() -> InlineChatCommandService.dd((MessageDto)a2, (CommandEnum)((Object)a3), a4, (String)a));
    }

    /*
     * WARNING - void declaration
     */
    private static void Ve(Project project, MessageDto messageDto, JsonObject jsonObject) {
        void a;
        Project a2 = jsonObject;
        Project a3 = project;
        ApplicationManager.getApplication().invokeLater(() -> InlineChatCommandService.UC((MessageDto)a, a3, (JsonObject)a2));
    }

    public InlineChatCommandService() {
        InlineChatCommandService a;
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void lF(MessageDto messageDto, JsonObject jsonObject, String string) {
        void a;
        Object a2 = jsonObject;
        MessageDto a3 = messageDto;
        if (InlineChatCommandService.zf(a3)) {
            return;
        }
        a2 = (ResponseStreamDto)new Gson().fromJson((JsonElement)a2, ResponseStreamDto.class);
        InlineChatStreamHandleService.handleData((String)a, (ResponseStreamDto)a2, a3);
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void oD(SessionController sessionController, Project project, MessageDto messageDto, InlineChatCategoryEnum inlineChatCategoryEnum) {
        void a;
        void a2;
        Object a3 = inlineChatCategoryEnum;
        SessionController a4 = sessionController;
        a4.renderStopPanel();
        InlineChatCommandService.OD((Project)a2, (MessageDto)a, (InlineChatCategoryEnum)((Object)a3), a4);
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void UC(MessageDto messageDto, Project project, JsonObject jsonObject) {
        void a;
        List<CodeInfoDto.RangeDTO> list;
        MessageDto a2;
        MessageDto a3;
        block8: {
            block7: {
                a3 = project;
                a2 = messageDto;
                try {
                    if (!InlineChatCommandService.zf(a2)) break block7;
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }
            list = a2.getRange();
            a3 = FileEditorManager.getInstance((Project)a3).getSelectedTextEditor();
            if (a3 != null) break block8;
            return;
        }
        Object object = a2.getOtherObject();
        if (object == null || !(object instanceof InlineChatService)) {
            return;
        }
        object = (InlineChatService)object;
        if (!StringUtils.equals((CharSequence)((EditorImpl)a3).getVirtualFile().getPath(), (CharSequence)a2.getPath())) {
            return;
        }
        MessageDto messageDto2 = a3;
        Document document = messageDto2.getDocument();
        boolean bl = messageDto2.getSelectionModel().hasSelection();
        if (InlineChatCommandService.Sd(list, (Editor)a3, document)) {
            return;
        }
        InlineChatCommandService.XD((JsonObject)a, (Editor)a3, document, bl);
        MessageDto messageDto3 = a3;
        messageDto3.putUserData(VERSION_KEY, a2.getInlineChatVersion());
        ((InlineChatService)object).toggleInlineChat((Editor)messageDto3);
    }

    private static /* synthetic */ void HF(SessionController a) {
        EditorKt.closeCategoryPanel(a.getEditor());
    }

    private static /* synthetic */ void YE(Project project, FirstChatMessage firstChatMessage) {
        Project a;
        Object a2 = firstChatMessage;
        Project project2 = a = project;
        CommonService.openPage(project2, PageEnum.CHAT_VIEW);
        if (!SocketMessageHandleListener.send2Web(project2, a2).booleanValue()) {
            a.putUserData(WebViewWindowPanel.CODE_MESSAGE_DATA, a2);
            return;
        }
        CommonService.chatMessage2Web(a, (FirstChatMessage)a2, true);
    }

    public static FirstChatMessage handleChatScene(MessageDto messageDto) {
        JsonObject jsonObject;
        MessageDto messageDto2 = messageDto;
        Object a = messageDto2.getOtherObject();
        if (a == null || !(a instanceof SessionController)) {
            return null;
        }
        a = ((SessionController)a).getEditor();
        CodeInfoDto codeInfoDto = null;
        if (a.getSelectionModel().hasSelection()) {
            Object object = a;
            codeInfoDto = ChatService.getCodeInfoDto((Editor)object, a.getSelectionModel(), a.getSelectionModel().getSelectionStart(), object.getSelectionModel().getSelectionEnd());
        }
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject2 = jsonObject = new JsonObject();
        jsonObject2.addProperty(HandleCacheUtil.H("r p:"), InlineChatStatusServiceKt.H("\u001e-\b(\u0017?\u000f#\u001c"));
        jsonObject2.addProperty(HandleCacheUtil.H("%g5u:"), AssistantTypeEnum.IFLY_MATE.getType());
        jsonArray.add((JsonElement)jsonObject2);
        JsonObject jsonObject3 = jsonObject = new JsonObject();
        jsonObject3.addProperty(InlineChatStatusServiceKt.H("?\u0017=\r"), HandleCacheUtil.H("o?B\u001cr>{\u0017\u007f-b4s8g:"));
        jsonObject3.addProperty(InlineChatStatusServiceKt.H("\u0012*\u00028\r"), (String)messageDto2.getData());
        jsonArray.add((JsonElement)jsonObject);
        return ChatService.getFirstChatMessage(a.getProject(), null, codeInfoDto, jsonArray);
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void Ue(MessageDto messageDto, Project project) {
        void a;
        MessageDto messageDto2 = messageDto;
        FirstChatMessage a2 = InlineChatCommandService.handleChatScene(messageDto2);
        PluginStartupActivity.handleExecutorService.execute(() -> InlineChatCommandService.YE((Project)a, a2));
    }

    /*
     * WARNING - void declaration
     */
    private static void Ze(SessionController sessionController, Editor editor, InlineChatCategoryEnum inlineChatCategoryEnum) {
        VirtualFile a3;
        void a2;
        SessionController sessionController2 = sessionController;
        Document document = a2.getDocument();
        SessionController sessionController3 = sessionController2;
        sessionController3.setHandleOffset(sessionController3.getCareOffset());
        switch (ka.enum[a3.ordinal()]) {
            case 2: 
            case 3: {
                int n;
                if (!sessionController2.isHasSelect()) break;
                SessionController sessionController4 = sessionController2;
                a2.getCaretModel().moveToOffset(sessionController4.getStartOffset());
                SessionController sessionController5 = sessionController2;
                sessionController5.setHandleOffset(sessionController5.getStartOffset());
                int a3 = sessionController4.getStartOffset();
                int n2 = sessionController4.getEndOffset();
                int a4 = document.getLineNumber(a3);
                int n3 = document.getLineNumber(n2);
                int n4 = n = a4;
                while (n4 <= n3) {
                    TextAttributes textAttributes = n == a4 ? InlineChatStreamHandleService.highLightAttributes : InlineChatStreamHandleService.toHandleAttributes;
                    RangeHighlighter rangeHighlighter = a2.getMarkupModel().addRangeHighlighter(document.getLineStartOffset(n), document.getLineEndOffset(n), 6000, textAttributes, HighlighterTargetArea.LINES_IN_RANGE);
                    sessionController2.getToHandleRangeHighlighterMap().put(n++, rangeHighlighter);
                    n4 = n;
                }
                break;
            }
            case 1: {
                Object a4;
                while (false) {
                }
                a3 = ((EditorImpl)a2).getVirtualFile();
                String string = a3.getExtension();
                void v4 = a2;
                if ((FileExtensionEnum.PYTHON_LANGUAGE_01.getSuffix().equals(string) ? (a4 = (List)v4.getUserData(BODY_RANGE_KEY)) : (a4 = (List)v4.getUserData(RANGE_KEY))) == null) {
                    return;
                }
                CodeInfoDto.RangeDTO rangeDTO = (CodeInfoDto.RangeDTO)a4.get(0);
                Integer n = rangeDTO.getLine();
                void v5 = a2;
                int n5 = v5.getDocument().getLineStartOffset(n.intValue());
                v5.getCaretModel().moveToOffset(n5);
                SessionController sessionController6 = sessionController2;
                sessionController6.setInsertStartOffset(n5);
                sessionController6.setHandleOffset(n5);
                return;
            }
            case 4: {
                int n = sessionController2.getEndOffset();
                int a3 = document.getLineNumber(n);
                Document document2 = document;
                Document document3 = document;
                String string = document3.getText(new TextRange(document3.getLineStartOffset(a3), document.getLineEndOffset(a3)));
                int a4 = n;
                if (StringUtils.isNotBlank((CharSequence)string)) {
                    a4 = document.getLineStartOffset(a3 + 1);
                }
                a2.getCaretModel().moveToOffset(a4);
                SessionController sessionController7 = sessionController2;
                sessionController7.setInsertStartOffset(a4);
                sessionController7.setHandleOffset(a4);
            }
        }
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray = new Object[3];
        objectArray[0] = InlineChatStatusServiceKt.H("\u000b3\u000b!\u000b.\u001c");
        objectArray[1] = HandleCacheUtil.H("wC\u001e$5`5c7n{a8C\u0017y}w>c8O\u001aozA9d>j>H<`*u\u0006m2v&w\u001fi!p0c:");
        objectArray[2] = InlineChatStatusServiceKt.H("\u0010)\u0007\u001f\u001a$\u001a?\u001c*6$\u00178\u000f*\r");
        throw new IllegalArgumentException(String.format(HandleCacheUtil.H("A-p=~)b'&?o-kTb\u001c\u007f\u001a|:`s{5r>K\u001cy7v{6kU^*:nw-$*~xtl+E\u001d 1x<3.ish,l3"), objectArray));
    }
}
