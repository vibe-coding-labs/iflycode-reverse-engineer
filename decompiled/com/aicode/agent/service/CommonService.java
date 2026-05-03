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
 *  com.google.gson.reflect.TypeToken
 *  com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
 *  com.intellij.ide.BrowserUtil
 *  com.intellij.notification.NotificationGroupManager
 *  com.intellij.openapi.actionSystem.AnAction
 *  com.intellij.openapi.application.Application
 *  com.intellij.openapi.application.ApplicationInfo
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.command.WriteCommandAction
 *  com.intellij.openapi.editor.Document
 *  com.intellij.openapi.editor.Editor
 *  com.intellij.openapi.editor.ScrollType
 *  com.intellij.openapi.editor.SelectionModel
 *  com.intellij.openapi.editor.VisualPosition
 *  com.intellij.openapi.editor.ex.EditorGutterComponentEx
 *  com.intellij.openapi.editor.impl.EditorImpl
 *  com.intellij.openapi.editor.markup.GutterIconRenderer
 *  com.intellij.openapi.editor.markup.MarkupModel
 *  com.intellij.openapi.editor.markup.RangeHighlighter
 *  com.intellij.openapi.fileEditor.FileDocumentManager
 *  com.intellij.openapi.fileEditor.FileEditorManager
 *  com.intellij.openapi.fileEditor.OpenFileDescriptor
 *  com.intellij.openapi.fileEditor.TextEditor
 *  com.intellij.openapi.options.ShowSettingsUtil
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.project.ProjectManager
 *  com.intellij.openapi.ui.MessageType
 *  com.intellij.openapi.util.TextRange
 *  com.intellij.openapi.vfs.LocalFileSystem
 *  com.intellij.openapi.vfs.VirtualFile
 *  com.intellij.openapi.wm.ToolWindowManager
 *  com.intellij.psi.PsiDocumentManager
 *  com.intellij.psi.PsiFile
 *  com.intellij.psi.PsiManager
 *  org.apache.commons.collections.CollectionUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.agent.service;

import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.SettingsDto;
import com.aicode.agent.dto.TipInfoDto;
import com.aicode.agent.dto.WebRequestDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.dto.chat.FirstChatMessage;
import com.aicode.agent.dto.chat.PresentationDataDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.agent.service.ChatService;
import com.aicode.content.util.EditorUtils;
import com.aicode.diff.DiffService;
import com.aicode.enums.ChatOperationEnum;
import com.aicode.enums.CodeCollectEnum;
import com.aicode.enums.ElementTypeEnum;
import com.aicode.enums.FileExtensionEnum;
import com.aicode.enums.LanguageEnum;
import com.aicode.enums.LineToolsTypeEnum;
import com.aicode.enums.PluginSceneEnum;
import com.aicode.enums.SendKeyEnum;
import com.aicode.enums.TipTypeEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.listener.CodeFileEditorManagerListener;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.EditorManagerServiceImpl;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.statusBar.StatusBarPopup;
import com.aicode.test.dto.RequestCaseCodeDto;
import com.aicode.toolwindow.CheckGutterIconRenderer;
import com.aicode.updater.PluginUpdaterCheckService;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.AICodeUtils;
import com.aicode.util.FileUtils;
import com.aicode.util.NewFileUtils;
import com.aicode.util.PsiUtils;
import com.aicode.util.StringUtils;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.ide.BrowserUtil;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.editor.ex.EditorGutterComponentEx;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class CommonService {
    private static final Logger byte = LoggerFactory.getLogger(CommonService.class);

    private static boolean cD(Project project, String string) {
        Object a = string;
        Project a2 = project;
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)a)) {
            return true;
        }
        if (a2.isDisposed()) {
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void ce(Application object, String string, String string2, Project project, String string3) {
        Application a;
        void a2;
        void a3;
        void a4;
        Application application = object;
        object = string3;
        Application a5 = application;
        a5.runReadAction(() -> CommonService.tC((String)a4, (String)a3, (Project)a2, (String)a));
    }

    /*
     * WARNING - void declaration
     */
    public static void logOperate(String string, String string2, Project project) {
        void a;
        void a2;
        String a3;
        String string3 = string;
        String string4 = a3 = new JsonObject();
        string4.addProperty(NewFileUtils.H("\u000bY\u001fZ\u0018I\u001cb\u001d"), string3);
        string4.addProperty(MethodGeneratorConfig.H("484'>1"), (String)a2);
        PluginWebsocketClient.sendWsMessage(CommandEnum.LOG_OPERATE, a3, (Project)a);
    }

    private static /* synthetic */ void xD(Project project, String string) {
        Project a;
        Object a2 = string;
        Project project2 = a = project;
        WriteCommandAction.runWriteCommandAction((Project)project2, () -> CommonService.Qd(project2, (String)a2));
    }

    @NotNull
    private static SettingsDto ED(AICodeSettingsState aICodeSettingsState) {
        Object a;
        AICodeSettingsState aICodeSettingsState2 = aICodeSettingsState;
        Object object = a = new SettingsDto();
        AICodeSettingsState aICodeSettingsState3 = aICodeSettingsState2;
        Object object2 = a;
        AICodeSettingsState aICodeSettingsState4 = aICodeSettingsState2;
        Object object3 = a;
        AICodeSettingsState aICodeSettingsState5 = aICodeSettingsState2;
        ((SettingsDto)a).setAutoTriggerOnPause(aICodeSettingsState2.autoTrigger);
        ((SettingsDto)a).setAutoTriggerTimeDelay(aICodeSettingsState5.triggerTime);
        ((SettingsDto)object3).setGenerateCodeMode(TipTypeEnum.getByName(aICodeSettingsState5.tipType).name());
        ((SettingsDto)object3).setCodeCompleteDisableLang(aICodeSettingsState2.codeCompleteDisableLang);
        ((SettingsDto)a).setSendMessageType(aICodeSettingsState4.sendKey);
        ((SettingsDto)object2).setLineToolsType(aICodeSettingsState4.lineToolsType);
        ((SettingsDto)object2).setJavaTestFramework(aICodeSettingsState2.testFramework);
        ((SettingsDto)a).setJavaMockFramework(aICodeSettingsState3.mockFramework);
        ((SettingsDto)object).setDefaultLanguage(aICodeSettingsState3.defaultLanguage);
        ((SettingsDto)object).setOpenCodeEnhance(aICodeSettingsState2.openCodeEnhance);
        if (PluginSceneEnum.saasScene()) {
            ((SettingsDto)a).setOpenAutoUpdate(aICodeSettingsState2.openAutoUpdate);
        }
        Object object4 = a;
        Object object5 = a;
        AICodeSettingsState aICodeSettingsState6 = aICodeSettingsState2;
        Object object6 = a;
        AICodeSettingsState aICodeSettingsState7 = aICodeSettingsState2;
        Object object7 = a;
        AICodeSettingsState aICodeSettingsState8 = aICodeSettingsState2;
        Object object8 = a;
        AICodeSettingsState aICodeSettingsState9 = aICodeSettingsState2;
        Object object9 = a;
        ((SettingsDto)object9).setLineToolsPermissionDocComments(aICodeSettingsState2.lineToolsPermissionDocComments);
        ((SettingsDto)object9).setLineToolsPermissionLineComments(aICodeSettingsState2.lineToolsPermissionLineComments);
        ((SettingsDto)a).setLineToolsPermissionComments(aICodeSettingsState9.lineToolsPermissionComments);
        ((SettingsDto)object8).setLineToolsPermissionFunctionSplit(aICodeSettingsState9.lineToolsPermissionFunctionSplit);
        ((SettingsDto)object8).setLineToolsPermissionCodeOptimization(aICodeSettingsState2.lineToolsPermissionCodeOptimization);
        ((SettingsDto)a).setLineToolsPermissionUnitTesting(aICodeSettingsState8.lineToolsPermissionUnitTesting);
        ((SettingsDto)object7).setInlineCompletionInputStyle(aICodeSettingsState8.inlineCompletionInputStyle);
        ((SettingsDto)object7).setOpenFunctionSplit(aICodeSettingsState2.openFunctionSplit);
        ((SettingsDto)a).setOpenCodeOptimization(aICodeSettingsState7.openCodeOptimization);
        ((SettingsDto)object6).setOpenIFlyTest(aICodeSettingsState7.openIFlyTest);
        ((SettingsDto)object6).setOpenInlineChat(aICodeSettingsState2.openInlineChat);
        ((SettingsDto)a).setOpenIFlyDBA(aICodeSettingsState6.openIFlyDBA);
        ((SettingsDto)object5).setOpenIFlyOps(aICodeSettingsState6.openIFlyOps);
        ((SettingsDto)object5).setOpenIFlyPm(aICodeSettingsState2.openIFlyPm);
        if (object4 == null) {
            CommonService.enum(1);
        }
        return object4;
    }

    public static void refreshDocumentStruct(Project a) {
        if (a.isDisposed()) {
            return;
        }
        ApplicationManager.getApplication().invokeLater(() -> {
            FileEditorManager fileEditorManager;
            Project project2 = a;
            try {
                fileEditorManager = FileEditorManager.getInstance((Project)project2);
            }
            catch (Exception a) {
                byte.error(NewFileUtils.H("\u000fL\u001dY\u001cS\u001a~\u0007\u0003GP\nE\ro\u001aJ\u001fD\u0001\r9B\u0015\u0005wi6U\u001cQ<B\u001f@\u0014E\u0000=(N\ru\u0000K\u001eF\u001bN\u001a\u000b\u001c{)E\n"), (Throwable)a);
                return;
            }
            Project a = fileEditorManager.getSelectedEditor();
            if (a != null && a instanceof TextEditor) {
                fileEditorManager = ((TextEditor)a).getEditor();
                VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(fileEditorManager.getDocument());
                CodeFileEditorManagerListener.syncDocumentList(virtualFile);
                virtualFile = PsiManager.getInstance((Project)project2).findFile(virtualFile);
                if (virtualFile != null) {
                    DaemonCodeAnalyzer.getInstance((Project)project2).restart((PsiFile)virtualFile);
                }
                CommonService.IE((Editor)fileEditorManager);
                CodeFileEditorManagerListener.sendOpenDocument(a.getFile(), a.getFile().getPath(), (Editor)fileEditorManager);
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    private static void uE(Editor editor, EditorGutterComponentEx editorGutterComponentEx, int n) {
        void a;
        Editor a2 = editorGutterComponentEx;
        Editor a3 = editor;
        a2.addMouseMotionListener((MouseMotionListener)new MouseMotionAdapter(a3, (int)a, (EditorGutterComponentEx)a2){
            public final /* synthetic */ EditorGutterComponentEx float;
            public final /* synthetic */ Editor byte;
            public final /* synthetic */ int enum;

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                MouseEvent a22 = mouseEvent;
                Fa a = this;
                int a22 = a22.getY();
                if (CommonService.Le(a.byte, a22, a.enum)) {
                    a.float.setCursor(Cursor.getPredefinedCursor(12));
                }
            }
            {
                Fa a = object;
                object = this;
                object.byte = editor;
                object.enum = n;
                object.float = a;
            }
        });
    }

    public static void handleChatFocusFileLine(Project project, JsonObject jsonObject) {
        Project project2;
        JsonObject a22;
        Project project3 = project;
        if (project3.isDisposed()) {
            return;
        }
        if (!a22.has(NewFileUtils.H("\u0003A\u001e^\u001c"))) {
            return;
        }
        Object a = null;
        Integer n = null;
        try {
            a22 = a22.get(MethodGeneratorConfig.H("%;8$:")).getAsJsonObject();
            a = a22.get(NewFileUtils.H("d9K\u0010p\u0013_\u0011")).getAsString();
            n = a22.get(MethodGeneratorConfig.H(",\f\u0017/'\u0016=?:")).getAsInt();
            project2 = a;
        }
        catch (Exception a22) {
            byte.error(a22.getMessage(), (Throwable)a22);
            project2 = a;
        }
        if (StringUtils.isBlank((CharSequence)project2) || n == null) {
            return;
        }
        if (!new File((String)a).exists()) {
            return;
        }
        a22 = a;
        a = n;
        ApplicationManager.getApplication().invokeLater(() -> CommonService.cf((String)a22, project3, (Integer)a));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void kD(String string, Project project, Integer n, Integer n2, boolean bl) {
        Editor a;
        String string2 = string;
        String a23 = LocalFileSystem.getInstance();
        File file = new File(string2);
        if ((a23 = a23.findFileByIoFile(file)) == null) {
            return;
        }
        a23 = new OpenFileDescriptor((Project)a, (VirtualFile)a23);
        if ((a = FileEditorManager.getInstance((Project)a).openTextEditor((OpenFileDescriptor)a23, true)) == null) {
            return;
        }
        try {
            void a3;
            void a322;
            void a222;
            int a23 = a.getDocument().getLineCount();
            int n3 = a222.intValue() >= a23 ? a23 - 1 : a222.intValue();
            Editor editor = a;
            int a222 = editor.getDocument().getLineStartOffset(a322.intValue());
            int a322 = editor.getDocument().getLineEndOffset(n3);
            editor.getSelectionModel().setSelection(a222, a322);
            editor.getCaretModel().moveToOffset(a322);
            if (a3 != false) {
                a.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
                return;
            }
        }
        catch (Throwable a23) {
            byte.error(a23.getMessage(), a23);
        }
    }

    private static String sd(AICodeSettingsState aICodeSettingsState) {
        Object object;
        AICodeSettingsState aICodeSettingsState2 = aICodeSettingsState;
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)aICodeSettingsState2.feedbackUrl)) {
            return null;
        }
        StringBuilder a = new StringBuilder(aICodeSettingsState2.feedbackUrl);
        if (!aICodeSettingsState2.feedbackUrl.contains(NewFileUtils.H("G"))) {
            Object object2 = a;
            object = object2;
            ((StringBuilder)object2).append(MethodGeneratorConfig.H("a"));
        } else {
            Object object3 = a;
            object = object3;
            ((StringBuilder)object3).append(NewFileUtils.H("^"));
        }
        ((StringBuilder)object).append(MethodGeneratorConfig.H("6$7+4=\u0001<#,.&>c")).append(BasicActionsBundle.message(NewFileUtils.H("\u0012I\u0011r+NWL\u0002M\rN\u001b\u0003\tN\u000bN\u0006E\u0016"), new Object[0])).append(MethodGeneratorConfig.H("w<*!'\")\u001d644b\u000e\r\u0015\u001f"));
        if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)aICodeSettingsState2.userName)) {
            AICodeSettingsState aICodeSettingsState3 = aICodeSettingsState2;
            a.append(NewFileUtils.H("\u0001\u0000^\u001aY7\\\u0002OE")).append(aICodeSettingsState3.userName);
            if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)aICodeSettingsState3.enterpriseId)) {
                a.append(MethodGeneratorConfig.H("`-,88!'+8,\"\u00004c")).append(aICodeSettingsState2.enterpriseId);
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)aICodeSettingsState2.userId)) {
                a.append(NewFileUtils.H("\u000b\nX\u001cO&NE")).append(aICodeSettingsState2.userId);
            }
        }
        return a.toString();
    }

    /*
     * WARNING - void declaration
     */
    public static String addLineIndent(String string, String string2) {
        void a;
        String string3 = string;
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)string3) || string3.startsWith(MethodGeneratorConfig.H("n")) || string3.startsWith(NewFileUtils.H("a"))) {
            return string3;
        }
        Object object = "";
        if (a.length() > 0) {
            int n;
            String[] a2 = string3.split(MethodGeneratorConfig.H("D"));
            int n2 = n = 0;
            while (n2 < a2.length) {
                if (n == 0) {
                    String[] stringArray;
                    String string4;
                    if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)a2[n])) {
                        string4 = "";
                        stringArray = a2;
                    } else {
                        string4 = a;
                        stringArray = a2;
                    }
                    object = (String)object + string4 + stringArray[n];
                } else {
                    String[] stringArray;
                    String string5;
                    if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)a2[n])) {
                        string5 = "";
                        stringArray = a2;
                    } else {
                        string5 = a;
                        stringArray = a2;
                    }
                    object = (String)object + "\n" + string5 + stringArray[n];
                }
                n2 = ++n;
            }
        }
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)object)) {
            object = string3;
        }
        return object;
    }

    /*
     * WARNING - void declaration
     */
    private static void SE(PresentationDataDto presentationDataDto, List<CommandEnum> list) {
        PresentationDataDto presentationDataDto2 = presentationDataDto;
        try {
            void a;
            List<CodeInfoDto.RangeDTO> a22 = presentationDataDto2.getCodeInfoDto().getRange();
            CodeInfoDto.RangeDTO rangeDTO = a22.get(0);
            int a22 = rangeDTO.getLine();
            if (((CodeInfoDto.RangeDTO)a22.get(1)).getLine() - a22 + 1 < 20 && a.contains((Object)CommandEnum.CODE_SPLIT)) {
                a.remove((Object)CommandEnum.CODE_SPLIT);
                return;
            }
        }
        catch (Exception exception) {}
    }

    /*
     * WARNING - void declaration
     */
    public static void copyCode(Project project, String string) {
        void a;
        Project project2 = project;
        Object a2 = new StringSelection((String)a);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable)a2, null);
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> CommonService.lD(application, project2, (String)a));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAction(WebViewDataTypeEnum webViewDataTypeEnum, JsonObject jsonObject, String string, Project project) {
        WebViewDataTypeEnum a = project;
        WebViewDataTypeEnum a2 = webViewDataTypeEnum;
        switch (a2) {
            case COMMON_PAGE_READY: {
                byte.info(NewFileUtils.H(";m=f6A\u0002}>}-x'd2d+"));
                return;
            }
            case COMMON_OPEN_URL: {
                void a3;
                CommonService.openUrl((String)a3, (Project)a);
                return;
            }
            case COMMON_CODE_CLICK_ACTION: {
                void a3;
                CommonService.handleClick((Project)a, (String)a3);
                return;
            }
            case COMMON_FOCUS_FILE: {
                void a4;
                CommonService.handleChatFocusFile((Project)a, (JsonObject)a4);
                return;
            }
            case COMMON_FOCUS_FILE_LINE: {
                void a4;
                CommonService.handleChatFocusFileLine((Project)a, (JsonObject)a4);
                return;
            }
            case COMMON_FEEDBACK: {
                void a4;
                CommonService.handleEval((JsonObject)a4, (Project)a);
                return;
            }
            case COMMON_EVALUATION: {
                void a3;
                CommonService.handleChatFeedback((String)a3, (Project)a);
                return;
            }
            case SETTING_GET_CONFIG: {
                CommonService.getConfig();
                return;
            }
            case SETTING_UPDATE_CONFIG: {
                void a4;
                CommonService.updateConfig((JsonObject)a4, (Project)a);
                return;
            }
            case SETTING_GET_CAN_OPEN_CODE_ENHANCE: {
                PluginWebsocketClient.sendWsMessage(CommandEnum.USER_CAN_CODE_ENHANCE, (Project)a);
                return;
            }
            case COMMON_OPEN_FILE_DIALOG: {
                void a4;
                CommonService.openFileDialog((Project)a, (JsonObject)a4);
                return;
            }
            case SETTING_POPUP_KEYMAP_SETTINGS: {
                CommonService.popupKeymapSettings((Project)a);
                return;
            }
            case SAVE_SHOW_OPERATE_GUIDANCE: {
                CommonService.saveShowOperateGuidance((Project)a);
                return;
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static /* synthetic */ void re(Project project, RequestCaseCodeDto.ValueDTO valueDTO) {
        Project project2 = project;
        try {
            void a;
            Editor editor = FileEditorManager.getInstance((Project)project2).getSelectedTextEditor();
            DiffService diffService = new DiffService();
            if (editor != null && org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)editor.getSelectionModel().getSelectedText())) {
                diffService.openDiffViewForAICode(project2, a.getContent(), editor);
                return;
            }
            CodeInfoDto a3222 = a.getCodeInfo();
            boolean bl = a3222 != null && org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)a3222.getPath());
            if (bl && new File(a3222.getPath()).exists()) {
                LocalFileSystem localFileSystem = LocalFileSystem.getInstance();
                File file = new File(a3222.getPath());
                if ((localFileSystem = localFileSystem.findFileByIoFile(file)) != null) {
                    localFileSystem = new OpenFileDescriptor(project2, (VirtualFile)localFileSystem);
                    localFileSystem = FileEditorManager.getInstance((Project)project2).openTextEditor((OpenFileDescriptor)localFileSystem, true);
                    if (localFileSystem != null && a3222.getRange() != null) {
                        file = localFileSystem.getDocument();
                        List<CodeInfoDto.RangeDTO> a3222 = a3222.getRange();
                        CodeInfoDto.RangeDTO rangeDTO = a3222.get(0);
                        CodeInfoDto.RangeDTO a3222 = a3222.get(1);
                        int[] a3222 = CommonService.getOffsets((Document)file, rangeDTO.getLine(), rangeDTO.getCharacter(), a3222.getLine(), a3222.getCharacter());
                        localFileSystem.getSelectionModel().setSelection(a3222[0], a3222[1]);
                        diffService.openDiffViewForAICode(project2, a.getContent(), (Editor)localFileSystem);
                        return;
                    }
                }
            }
            CommonService.messageBus(project2, BasicActionsBundle.message(NewFileUtils.H("\u0015J\u0017G]S\u0017q*H\r\u0012\u000bU\u001aS\f\u0003\u001cD\u0017H\u000bE\r"), new Object[0]), MessageType.INFO);
            return;
        }
        catch (Throwable throwable) {
            CommonService.messageBus(project2, BasicActionsBundle.message(MethodGeneratorConfig.H("301={)1\u000b\f2+h-/<)*y:>12-?+"), new Object[0]), MessageType.INFO);
            return;
        }
    }

    public static boolean isSupportJava(Editor editor) {
        block3: {
            boolean bl;
            Editor editor2 = editor;
            try {
                Editor a = ((EditorImpl)editor2).getVirtualFile();
                if (a == null) break block3;
                String[] stringArray = new String[1];
                stringArray[0] = MethodGeneratorConfig.H("200}.'\u0013\f-#- j:(<h\u00183'4\u0011#:\u0004%9>");
                if (!PsiUtils.instanceOf((Object)PsiManager.getInstance((Project)editor2.getProject()).findFile((VirtualFile)a), stringArray)) break block3;
                bl = true;
            }
            catch (Throwable a) {
                return false;
            }
            return bl;
        }
        boolean bl = false;
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    private static boolean Le(Editor editor, int n, int n2) {
        void a;
        int a2 = n;
        Editor a3 = editor;
        a2 = a3.yToVisualLine(a2);
        if (a3.visualToLogicalPosition((VisualPosition)new VisualPosition((int)a2, (int)0)).line == a) {
            return true;
        }
        return false;
    }

    public static void updateConfig(JsonObject jsonObject, Project project) {
        JsonElement jsonElement;
        Project[] a;
        AICodeSettingsState aICodeSettingsState;
        JsonObject jsonObject2 = jsonObject;
        JsonElement jsonElement2 = jsonObject2.get(MethodGeneratorConfig.H("*<?&8"));
        SettingsDto a2 = (SettingsDto)new Gson().fromJson(jsonElement2, SettingsDto.class);
        if (Objects.isNull(a2)) {
            return;
        }
        AICodeSettingsState aICodeSettingsState2 = aICodeSettingsState = AICodeSettingsState.getInstance();
        SettingsDto settingsDto = a2;
        AICodeSettingsState aICodeSettingsState3 = aICodeSettingsState;
        SettingsDto settingsDto2 = a2;
        AICodeSettingsState aICodeSettingsState4 = aICodeSettingsState;
        SettingsDto settingsDto3 = a2;
        aICodeSettingsState.autoTrigger = settingsDto3.isAutoTriggerOnPause();
        aICodeSettingsState4.triggerTime = settingsDto3.getAutoTriggerTimeDelay();
        aICodeSettingsState4.tipType = TipTypeEnum.getByName(a2.getGenerateCodeMode()).name();
        aICodeSettingsState.codeCompleteDisableLang = settingsDto2.getCodeCompleteDisableLang();
        aICodeSettingsState3.sendKey = SendKeyEnum.getByText(settingsDto2.getSendMessageType()).getText();
        aICodeSettingsState3.lineToolsType = a2.getLineToolsType();
        aICodeSettingsState.testFramework = settingsDto.getJavaTestFramework();
        aICodeSettingsState2.mockFramework = settingsDto.getJavaMockFramework();
        aICodeSettingsState2.openCodeEnhance = a2.isOpenCodeEnhance();
        if (PluginSceneEnum.saasScene()) {
            boolean bl = aICodeSettingsState.openAutoUpdate;
            aICodeSettingsState.openAutoUpdate = a2.isOpenAutoUpdate();
            if (!bl && a2.isOpenAutoUpdate()) {
                PluginUpdaterCheckService.queueUpdateCheck((Project)a);
            }
        }
        a = (jsonElement = ((JsonObject)jsonElement2).get(NewFileUtils.H("\fN\u001fl*M\u0007l\u0013F\u001dR\u0014N\u001e"))) == null ? MethodGeneratorConfig.H("<&'2") : jsonElement.getAsString();
        aICodeSettingsState.defaultLanguage = (String)org.apache.commons.lang3.StringUtils.defaultIfBlank((CharSequence)a, (CharSequence)NewFileUtils.H("F\u0000]\u0014"));
        AICodeSettingsState aICodeSettingsState5 = aICodeSettingsState;
        SettingsDto settingsDto4 = a2;
        AICodeSettingsState aICodeSettingsState6 = aICodeSettingsState;
        SettingsDto settingsDto5 = a2;
        aICodeSettingsState.lineToolsPermissionDocComments = settingsDto5.isLineToolsPermissionDocComments();
        aICodeSettingsState6.lineToolsPermissionLineComments = settingsDto5.isLineToolsPermissionLineComments();
        aICodeSettingsState6.lineToolsPermissionComments = a2.isLineToolsPermissionComments();
        aICodeSettingsState.lineToolsPermissionFunctionSplit = settingsDto4.isLineToolsPermissionFunctionSplit();
        aICodeSettingsState5.lineToolsPermissionCodeOptimization = settingsDto4.isLineToolsPermissionCodeOptimization();
        aICodeSettingsState5.lineToolsPermissionUnitTesting = a2.isLineToolsPermissionUnitTesting();
        SettingsDto settingsDto6 = a2;
        aICodeSettingsState5.streamOutputConfig = MethodGeneratorConfig.H("\u0001\b\u000f\u0016\u0012\u0010").equals(settingsDto6.getInlineCompletionInputStyle());
        aICodeSettingsState.inlineCompletionInputStyle = settingsDto6.getInlineCompletionInputStyle();
        if (aICodeSettingsState.openFunctionSplit != a2.isOpenFunctionSplit() || aICodeSettingsState.openCodeOptimization != a2.isOpenCodeOptimization() || aICodeSettingsState.openInlineChat != a2.isOpenInlineChat()) {
        }
        AICodeSettingsState aICodeSettingsState7 = aICodeSettingsState;
        SettingsDto settingsDto7 = a2;
        AICodeSettingsState aICodeSettingsState8 = aICodeSettingsState;
        Object object = a2;
        AICodeSettingsState aICodeSettingsState9 = aICodeSettingsState;
        aICodeSettingsState9.openFunctionSplit = a2.isOpenFunctionSplit();
        aICodeSettingsState9.openCodeOptimization = a2.isOpenCodeOptimization();
        aICodeSettingsState.openIFlyTest = ((SettingsDto)object).isOpenIFlyTest();
        aICodeSettingsState8.openInlineChat = ((SettingsDto)object).isOpenInlineChat();
        aICodeSettingsState8.openIFlyDBA = a2.isOpenIFlyDBA();
        aICodeSettingsState7.openIFlyOps = settingsDto7.isOpenIFlyOps();
        aICodeSettingsState7.openIFlyPm = settingsDto7.isOpenIFlyPm();
        if (!a2.isOpenInlineChat()) {
            // empty if block
        }
        a = ProjectManager.getInstance().getOpenProjects();
        int n = a.length;
        int n2 = a2 = 0;
        while (n2 < n) {
            aICodeSettingsState = a[a2];
            if (!aICodeSettingsState.isDisposed()) {
                AICodeSettingsState aICodeSettingsState10 = aICodeSettingsState;
                StatusBarPopup.update((Project)aICodeSettingsState10);
                CommonService.refreshDocumentStruct((Project)aICodeSettingsState10);
            }
            n2 = ++a2;
        }
    }

    public static void openFileDialog(Project project, JsonObject jsonObject) {
        Project a22 = jsonObject;
        Project a = project;
        String string = null;
        String string2 = null;
        try {
            a22 = a22.get(MethodGeneratorConfig.H(" :9&8")).getAsJsonObject();
            string = a22.get(NewFileUtils.H("I\u0014N\u0015q\u0012]\u0013")).getAsString();
            string2 = a22.get(MethodGeneratorConfig.H("/,#8")).getAsString();
        }
        catch (Exception a22) {
            byte.error(a22.getMessage(), (Throwable)a22);
        }
        if (NewFileUtils.H("G\u001aE\u001e").equals(string2)) {
            CommonService.openFile(a, string);
        }
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void cf(String string, Project project, Integer n) {
        String a4 = project;
        String a2 = string;
        LocalFileSystem localFileSystem = LocalFileSystem.getInstance();
        File file = new File(a2);
        if ((localFileSystem = localFileSystem.findFileByIoFile(file)) == null) {
            return;
        }
        localFileSystem = new OpenFileDescriptor((Project)a4, (VirtualFile)localFileSystem);
        if ((a4 = FileEditorManager.getInstance((Project)a4).openTextEditor((OpenFileDescriptor)localFileSystem, true)) == null) {
            return;
        }
        try {
            void a3;
            String string2 = a4;
            int a4 = string2.getDocument().getLineStartOffset(a3.intValue());
            string2.getCaretModel().moveToOffset(a4);
            string2.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
            return;
        }
        catch (Throwable a4) {
            byte.error(a4.getMessage(), a4);
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    public static synchronized void refreshFunctionAction(Project project, MessageDto messageDto, JsonObject jsonObject) {
        void a;
        Object a2 = messageDto;
        Project a3 = project;
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)(a2 = ((MessageDto)a2).getPath())) || !a.has(MethodGeneratorConfig.H("?4.5"))) {
            return;
        }
        try {
            ApplicationManager.getApplication().invokeLater(() -> CommonService.te(a3, (String)a2, (JsonObject)a));
            return;
        }
        catch (Throwable throwable) {
            byte.warn(NewFileUtils.H("*Z\u001bS\u0017\r\u001bU\u000bR\u0018D\u001dTR|)_\nY\u001c\u0007\u0010S\u0001O\u0000"));
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void insertLineComment(Project project, String string, String string2, List<CodeInfoDto.RangeDTO> list) {
        void a;
        void a2;
        Object a3 = list;
        Project a4 = project;
        if (a4.isDisposed()) {
            return;
        }
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> CommonService.xd(application, a4, (List)a3, (String)a2, (String)a));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    private static List<PresentationDataDto> ID(JsonObject jsonObject, String string, Editor editor) {
        Iterator iterator;
        void a;
        void a2;
        JsonObject jsonObject2 = jsonObject;
        Object a3 = PsiDocumentManager.getInstance((Project)Objects.requireNonNull(a2.getProject())).getPsiFile(a2.getDocument());
        String string2 = null;
        if (a3 != null) {
            string2 = LanguageEnum.getLanguage(FileUtils.getFileExtension(a3.getVirtualFile().getName()));
        }
        a3 = Paths.get((String)a, new String[0]).getFileName().toString();
        ArrayList<PresentationDataDto> arrayList = new ArrayList<PresentationDataDto>();
        Type type = new TypeToken<List<CodeInfoDto.RangeDTO>>(){
            {
                Ma a;
            }
        }.getType();
        Iterator iterator2 = iterator = jsonObject2.get(NewFileUtils.H("c4~9")).getAsJsonArray().iterator();
        while (iterator2.hasNext()) {
            JsonElement jsonElement = (JsonElement)iterator.next();
            iterator2 = iterator;
            CommonService.cF(a2.getDocument(), (String)a, arrayList, type, jsonElement, string2, (String)a3);
        }
        ArrayList<PresentationDataDto> arrayList2 = arrayList;
        if (arrayList2 == null) {
            CommonService.enum(0);
        }
        return arrayList2;
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void je(Project project, List list, String string, String string2) {
        void a;
        void a2;
        Project a3;
        Object a4 = string2;
        Project project2 = a3 = project;
        WriteCommandAction.runWriteCommandAction((Project)project2, () -> CommonService.Qe((List)a2, (String)a, project2, (String)a4));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void RD(Application application, Project project, String string) {
        void a;
        Object a2 = string;
        Application a3 = application;
        a3.runWriteAction(() -> CommonService.xD((Project)a, (String)a2));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void xd(Application object, Project project, List list, String string, String string2) {
        Application a;
        void a2;
        void a3;
        void a4;
        Application application = object;
        object = string2;
        Application a5 = application;
        a5.runWriteAction(() -> CommonService.je((Project)a4, (List)a3, (String)a2, (String)a));
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray;
        String string = NewFileUtils.H("g;B\u000be\f\f^-2D\u0007K\u001eGQ\u0004\u0000\u000eWnoF\fO\u001a\u0018\u0004H\u0001\r\rN\r^\u000bNRU\u001cO\u001d");
        Object[] objectArray2 = new Object[2];
        objectArray2[0] = MethodGeneratorConfig.H("20w;\u0016\u00108:=2v6<04 H\u001a4-0!!)r\u001084<0?\f?&7&:2");
        switch (a) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = NewFileUtils.H("\u0015x;{\u000bY\u001d]\u0004S\u0014Y\u0016D\u0017o\u0018T\u0013\u007f\u001dL\u0002");
                break;
            }
            case 1: {
                objectArray = objectArray2;
                while (false) {
                }
                objectArray2[1] = MethodGeneratorConfig.H(":6#\n4+%6432\u000b-8");
                break;
            }
        }
        throw new IllegalStateException(String.format(string, objectArray));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void XC(Project project, String string) {
        Project project2 = project;
        Project a = EditorUtils.getSelectedEditor(project2);
        if (a != null) {
            void a2;
            a = (EditorImpl)a;
            EditorManagerServiceImpl.acceptCount(project2, a.getVirtualFile().getPath(), (String)a2, CodeCollectEnum.COPY);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void IE(Editor var0) {
        block6: {
            var2_3 = var0;
            var1_4 = null;
            try {
                var1_4 = Class.forName(NewFileUtils.H("C\u001dWF\t\\I\nG\u0015U\u0004\u0016\tH\u0011H6E\n\tUe+\u000f\u001bJ\u001fW\u0002\u000f:N\u001e|6c\u0010R\u001aK:F\u0006^9J\u001as:y "));
                v0 = var1_4;
                ** GOTO lbl16
            }
            catch (Exception a) {
                try {
                    var1_4 = Class.forName(MethodGeneratorConfig.H("8:=p81nq97:7\u007f<50%\u0007tg..9+h,#)0<9w82jxY0598.\u0011>5!)\u0004\u0006\u001a\"\u0019'+6#/*\u001e7%:\u000f\u001d\u0010\u0013"));
                    v0 = var1_4;
                    ** GOTO lbl16
                }
                catch (Exception var3_5) {
                    CommonService.byte.error(a.getMessage(), (Throwable)a);
                    try {
                        v0 = var1_4;
lbl16:
                        // 3 sources

                        v1 = a = v0.getDeclaredField(NewFileUtils.H("6B\u0012[\u0018i<d7"));
                        v1.setAccessible(true);
                        var3_6 = v1.get(null);
                        v2 = var1_4 = var3_6.getClass().getDeclaredMethod(MethodGeneratorConfig.H("?8+4>\u001d3:\u0013\u001a\u0004/\")6)\u0012=\u0019<)+-\u0012\u0002\f"), new Class[0]);
                        v2.setAccessible(true);
                        v2.invoke(var3_6, new Object[0]);
                    }
                    catch (Exception a) {
                        CommonService.byte.error(a.getMessage(), (Throwable)a);
                        v3 = var2_3;
                        break block6;
                    }
                }
            }
            v3 = var2_3;
        }
        v3.getContentComponent().repaint();
    }

    public static void insertCode(Project project, String string) {
        Object a = string;
        Project a2 = project;
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> CommonService.RD(application, a2, (String)a));
    }

    public static void openPage(Project project, PageEnum pageEnum) {
        Project project2 = project;
        try {
            Boolean a;
            assert (project2 != null);
            HashMap<String, String> a2 = new HashMap<String, String>();
            a2.put(NewFileUtils.H("]\u0002[\u001c"), WebViewDataTypeEnum.COMMON_OPEN_PAGE.getType());
            a2.put(MethodGeneratorConfig.H("-21$:"), ((PageEnum)((Object)a)).getType());
            a = SocketMessageHandleListener.send2Web(project2, a2);
            ApplicationManager.getApplication().invokeLater(() -> {
                void a;
                Project project2 = project2;
                Project a2 = ToolWindowManager.getInstance((Project)project2).getToolWindow(BasicActionsBundle.message(MethodGeneratorConfig.H(">#0odY\u00182663<y\u001e13 \b\u001b\u0010<2!-\"\u001a!8,!q\u0002\u001d\t\u000b"), new Object[0]));
                if (a2 != null && !a2.isVisible()) {
                    a2.show();
                }
                if (!a.booleanValue()) {
                    void a3;
                    project2.putUserData(WebViewWindowPanel.OPEN_PAGE_DATA, (Object)a3);
                }
            });
            return;
        }
        catch (Throwable a2) {
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void messageBus(Project project, String string, MessageType messageType) {
        Project a = messageType;
        Project a2 = project;
        try {
            void a3;
            if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)a3) && a != null) {
                NotificationGroupManager.getInstance().getNotificationGroup(NewFileUtils.H("\u000eF\u001eF\u001fNWb1O\u0000^\n")).createNotification((String)a3, (MessageType)a).setTitle(BasicActionsBundle.message(MethodGeneratorConfig.H("3\u0015\u0006$/h)+/272w\u0014;0#>-\u0006*3 :5\u0014/>*\u0006V5*?="), new Object[0])).notify(a2);
                return;
            }
        }
        catch (Throwable throwable) {}
    }

    public static void handleChatFocusFile(Project project, JsonObject jsonObject) {
        Project project2;
        JsonObject a22;
        Project project3 = project;
        if (!a22.has(NewFileUtils.H("\u0003C\u001c^\u001c"))) {
            return;
        }
        Object a = null;
        Integer n = null;
        Integer n2 = null;
        try {
            a22 = a22.get(MethodGeneratorConfig.H("%9:$:")).getAsJsonObject();
            String string = a22.get(NewFileUtils.H("B\u001d")).getAsString();
            JsonObject jsonObject2 = a22.get(MethodGeneratorConfig.H("\u0011\u001396\u0011870")).getAsJsonObject();
            a = jsonObject2.get(NewFileUtils.H("R\u0011_\u0011")).getAsString();
            JsonArray jsonArray = jsonObject2.get(MethodGeneratorConfig.H("!986:")).getAsJsonArray();
            n = jsonArray.get(0).getAsJsonObject().get(NewFileUtils.H("N\u0019E\u001c")).getAsInt();
            n2 = jsonArray.get(1).getAsJsonObject().get(MethodGeneratorConfig.H("4??:")).getAsInt();
            PluginWebsocketClient.AGENT_REQUEST.remove(string);
            project2 = a;
        }
        catch (Exception a22) {
            byte.error(a22.getMessage(), (Throwable)a22);
            project2 = a;
        }
        if (StringUtils.isBlank((CharSequence)project2) || n == null || n2 == null) {
            return;
        }
        if (!new File((String)a).exists()) {
            return;
        }
        CommonService.jumpToFileByIndex(project3, (String)a, n, n2, true);
    }

    public static void diffCode(Project project, RequestCaseCodeDto.ValueDTO valueDTO) {
        Object a = valueDTO;
        Project a2 = project;
        if (a2.isDisposed()) {
            return;
        }
        ApplicationManager.getApplication().invokeLater(() -> CommonService.re(a2, (RequestCaseCodeDto.ValueDTO)a));
    }

    /*
     * WARNING - void declaration
     */
    public static void jumpToFileByIndex(Project project, String string, Integer n, Integer n2, boolean bl) {
        void a;
        void a2;
        void a3;
        void a4;
        boolean bl2 = bl;
        Project a5 = project;
        if (a5.isDisposed()) {
            return;
        }
        ApplicationManager.getApplication().invokeLater(() -> CommonService.kD((String)a4, a5, (Integer)a3, (Integer)a2, (boolean)a));
    }

    public static void openFile(Project project, String string) {
        Project a;
        block5: {
            Object a22;
            block4: {
                a22 = string;
                a = project;
                try {
                    a22 = new File((String)a22);
                    if (a22.exists()) break block4;
                    CommonService.messageBus(a, MethodGeneratorConfig.H("\u65d4\u4eae\u4e5b\u5b09\u5777"), MessageType.INFO);
                    return;
                }
                catch (IOException a22) {
                    byte.error(MethodGeneratorConfig.H("\u622f\u5f5d\u65d4\u4eae\u5967\u8d74\uff5e"), (Throwable)a22);
                    CommonService.messageBus(a, NewFileUtils.H("\u6209\u5f27\u65f2\u4ed4\u5941\u8d0e\uff78"), MessageType.INFO);
                    return;
                }
            }
            if (!Desktop.isDesktopSupported()) break block5;
            Desktop.getDesktop().open((File)a22);
            return;
        }
        CommonService.messageBus(a, NewFileUtils.H("\u5f2a\u526d\u5e01\u53f8\u4e57\u6508\u6374\u6271\u5f70\u65ac\u4e8f"), MessageType.INFO);
    }

    /*
     * WARNING - void declaration
     */
    public static void chatMessage2Web(Project project, FirstChatMessage firstChatMessage, Boolean bl) {
        void a;
        Object a2 = bl;
        Project a3 = project;
        FirstChatMessage.ValueDTO valueDTO = a.getValue();
        if (!((Boolean)a2).booleanValue()) {
            SocketMessageHandleListener.send2Web(a3, a);
            if (CommandEnum.CODE_DEBUG.getType().equals(valueDTO.getType())) {
                return;
            }
        }
        if (a.getValue() != null) {
            a2 = a.getValue();
            if (((FirstChatMessage.ValueDTO)a2).isErrorType()) {
                a2 = ChatService.getErrorChatResponse((FirstChatMessage.ValueDTO)a2);
                PluginStartupActivity.handleExecutorService.execute(() -> CommonService.aF(a3, (JsonObject)a2));
                return;
            }
            PluginStartupActivity.handleExecutorService.execute(() -> CommonService.KE(a3, (FirstChatMessage)a));
        }
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void lD(Application application, Project project, String string) {
        void a;
        Object a2 = string;
        Application a3 = application;
        a3.runWriteAction(() -> CommonService.GD((Project)a, (String)a2));
    }

    /*
     * WARNING - void declaration
     */
    public static void handleChatFeedback(String string, Project project) {
        String string2 = string;
        String a22 = JsonParser.parseString((String)string2).getAsJsonObject();
        if (!a22.has(NewFileUtils.H("\u000bL\u0013P\u0012"))) {
            return;
        }
        try {
            void a;
            a22 = a22.get(MethodGeneratorConfig.H("-65*4")).getAsJsonObject();
            String string3 = a22.get(NewFileUtils.H("L\u0013")).getAsString();
            String string4 = a22.get(MethodGeneratorConfig.H("5(74 :#00?")).getAsString();
            MessageDto messageDto = new MessageDto(string3, CommandEnum.LOG_EVALUATION.getType());
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(NewFileUtils.H("\nI\u000fZ\u0018^\u000bl\u0013"), string3);
            hashMap.put(MethodGeneratorConfig.H("5(74 :#00?"), string4);
            MessageDto messageDto2 = messageDto;
            messageDto2.setData(hashMap);
            PluginWebsocketClient.sendWsMessage(messageDto2, (Project)a);
            PluginWebsocketClient.AGENT_REQUEST.remove(messageDto.getId());
            return;
        }
        catch (Exception a22) {
            byte.error(a22.getMessage(), (Throwable)a22);
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    public static int[] getOffsets(Document document, int n, int n2, int n3, int n4) {
        void a;
        void a2;
        int a3;
        Document a4;
        int a5 = n2;
        Document document2 = a4 = document;
        a3 = document2.getLineStartOffset(a3) + a5;
        a5 = document2.getLineStartOffset((int)a2) + a;
        if (a3 > a4.getTextLength()) {
            a3 = a4.getTextLength();
        }
        if (a5 > a4.getTextLength()) {
            a5 = a4.getTextLength();
        }
        int[] nArray = new int[2];
        nArray[0] = a3;
        nArray[1] = a5;
        return nArray;
    }

    /*
     * WARNING - void declaration
     */
    private static void cF(Document document, String string, List<PresentationDataDto> list, Type type, JsonElement jsonElement, String string2, String string3) {
        void a;
        void a2;
        void a3;
        void a4;
        List list2;
        Object a5;
        Object object;
        String string4;
        JsonObject a62;
        Document document2;
        block10: {
            block9: {
                document2 = document;
                try {
                    a62 = a62.getAsJsonObject();
                    string4 = a62.get(MethodGeneratorConfig.H("# \u0002\u0019")).getAsString();
                    if (ElementTypeEnum.METHOD.getType().equals(string4)) break block9;
                    return;
                }
                catch (Throwable a62) {
                    byte.warn(NewFileUtils.H("\u0003B\u0003P\u0014\u0001\u0017O\u0011h\"N\u0017HNK\u001eU\u0000N\u000b\u000b\u001c_\rg("));
                    return;
                }
            }
            object = a62.get(NewFileUtils.H("\u000bL\u0011o?")).getAsJsonArray();
            list2 = (List)new Gson().fromJson((JsonElement)object, (Type)a5);
            if (!CollectionUtils.isEmpty((Collection)list2)) {
                if (list2.size() >= 2) break block10;
            }
            return;
        }
        JsonObject jsonObject = object.get(0).getAsJsonObject();
        object = jsonObject.get(MethodGeneratorConfig.H(";0\u001c\u0019")).getAsInt();
        Integer n = jsonObject.get(NewFileUtils.H("\u0016E\u001eY\u0018N\u000bm(")).getAsInt();
        try {
            Object a7 = a62.get(MethodGeneratorConfig.H("#\"+4\r67\u0015\u0019")).getAsJsonArray();
            a5 = (List)new Gson().fromJson((JsonElement)a7, (Type)a5);
            if (CollectionUtils.isNotEmpty((Collection)a5)) {
                JsonObject jsonObject2 = a7.get(0).getAsJsonObject();
                object = jsonObject2.get(NewFileUtils.H("A\u0016f?")).getAsInt();
                n = jsonObject2.get(MethodGeneratorConfig.H("0?8#>4-\u0017\u000e")).getAsInt();
            }
        }
        catch (Exception a7) {
            // empty catch block
        }
        if (object == null) {
            return;
        }
        Object object2 = a7 = new CodeInfoDto();
        Object object3 = a7;
        ((CodeInfoDto)a7).setRange(list2);
        ((CodeInfoDto)object3).setPath((String)a4);
        ((CodeInfoDto)object3).setLanguage((String)a3);
        ((CodeInfoDto)object2).setFileName((String)a2);
        ((CodeInfoDto)object2).setContent(document2.getText());
        Object object4 = a5 = new PresentationDataDto();
        Object object5 = a5;
        ((PresentationDataDto)object5).setCodeInfoDto((CodeInfoDto)a7);
        ((PresentationDataDto)object5).setType(string4);
        ((PresentationDataDto)object4).setLine((Integer)object);
        ((PresentationDataDto)object4).setCharacter(n);
        a.add(object4);
    }

    public static void popupKeymapSettings(Project a) {
        ApplicationManager.getApplication().invokeLater(() -> ShowSettingsUtil.getInstance().showSettingsDialog(a, MethodGeneratorConfig.H("\u001a:\u0006\u001c\u0013\f")));
    }

    private static /* synthetic */ void qf(String a) {
        BrowserUtil.browse((String)a);
    }

    /*
     * WARNING - void declaration
     */
    public static void handleEval(JsonObject jsonObject, Project project) {
        void a;
        String string;
        String string2;
        String string3;
        block3: {
            JsonObject jsonObject2 = jsonObject;
            try {
                JsonObject jsonObject3 = jsonObject2.get(NewFileUtils.H("\u001cN\u0011W\u0015")).getAsJsonObject();
                string3 = jsonObject3.get(MethodGeneratorConfig.H("12")).getAsString();
                string2 = jsonObject3.get(NewFileUtils.H("[\u0004R\u0015")).getAsString();
                string = jsonObject3.get(MethodGeneratorConfig.H("0)8:*=")).getAsString();
                if (!StringUtils.isBlank((CharSequence)string2) || !StringUtils.isBlank((CharSequence)string)) break block3;
                return;
            }
            catch (Exception exception) {
                byte.error(exception.getMessage(), (Throwable)exception);
                return;
            }
        }
        PluginWebsocketClient.AGENT_REQUEST.remove(string3);
        String string4 = IdUtil.fastSimpleUUID();
        MessageDto messageDto = new MessageDto(string4, CommandEnum.LOG_FEEDBACK.getType());
        HashMap<String, String> a2 = new HashMap<String, String>();
        a2.put(NewFileUtils.H("\u000bn(M\u000f\\\tk\u0014"), string3);
        a2.put(MethodGeneratorConfig.H("!\"(3"), string2);
        a2.put(NewFileUtils.H("J\u000fB\u001cP\u001b"), string);
        MessageDto messageDto2 = messageDto;
        messageDto2.setData(a2);
        PluginWebsocketClient.sendWsMessage(messageDto2, (Project)a);
        PluginWebsocketClient.AGENT_REQUEST.remove(string4);
    }

    private static String Ge(String string, int n) {
        int a;
        String string2 = string;
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)string2) || string2.startsWith(NewFileUtils.H("Y")) || string2.startsWith(MethodGeneratorConfig.H("V"))) {
            return string2;
        }
        Object object = "";
        Object a2 = "";
        if (a > 0) {
            int n2;
            int n3 = n2 = 0;
            while (n3 < a) {
                object = (String)object + " ";
                n3 = ++n2;
            }
            String[] stringArray = string2.split(NewFileUtils.H("s"));
            int n4 = a = 0;
            while (n4 < stringArray.length) {
                String string3 = a2;
                a2 = a == 0 ? string3 + (String)object + stringArray[a] : string3 + "\n" + (String)object + stringArray[a];
                n4 = ++a;
            }
        }
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)a2)) {
            a2 = string2;
        }
        return a2;
    }

    /*
     * WARNING - void declaration
     */
    public static void genCodeFile(Project project, String string, String string2) {
        void a;
        void a2;
        Project project2 = project;
        Object a3 = FileExtensionEnum.getLanguage((String)a2);
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> CommonService.ce(application, (String)a3, (String)a2, project2, (String)a));
    }

    private static /* synthetic */ void GD(Project project, String string) {
        Project a;
        Object a2 = string;
        Project project2 = a = project;
        WriteCommandAction.runWriteCommandAction((Project)project2, () -> CommonService.XC(project2, (String)a2));
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    private static boolean WD(List<RangeHighlighter> list, PresentationDataDto presentationDataDto, Set<String> set) {
        List<RangeHighlighter> list2 = list;
        for (RangeHighlighter rangeHighlighter : list2) {
            void a;
            void a2;
            Object object;
            int n;
            CheckGutterIconRenderer checkGutterIconRenderer = (CheckGutterIconRenderer)rangeHighlighter.getGutterIconRenderer();
            Object object2 = checkGutterIconRenderer.getPresentationDataDto().getCodeInfoDto().getRange();
            Object a222 = checkGutterIconRenderer.getAnActions();
            if (a222 == null || ((AnAction[])a222).length == 0) {
                return false;
            }
            HashSet<String> hashSet = new HashSet<String>();
            int n2 = ((AnAction[])a222).length;
            int n3 = n = 0;
            while (n3 < n2) {
                AnAction anAction = a222[n];
                object = anAction.getTemplatePresentation().getDescription();
                if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)object)) {
                    hashSet.add((String)object);
                }
                n3 = ++n;
            }
            a222 = object2.get(0);
            CodeInfoDto.RangeDTO rangeDTO = object2.get(1);
            n = ((CodeInfoDto.RangeDTO)a222).getLine();
            int n4 = rangeDTO.getLine();
            object = a2.getCodeInfoDto().getRange();
            object2 = object.get(0);
            int n5 = ((CodeInfoDto.RangeDTO)object2).getLine();
            int a222 = ((CodeInfoDto.RangeDTO)object.get(1)).getLine();
            if (n != n5 || n4 != a222 || !a.equals(hashSet)) continue;
            list2.remove(rangeHighlighter);
            return true;
        }
        return false;
    }

    public static void saveShowOperateGuidance(Project project) {
        Project project2 = project;
        try {
            TipInfoDto tipInfoDto;
            String string = IdUtil.fastSimpleUUID();
            Object a = AICodeSettingsState.getInstance().userName;
            String string2 = ApplicationInfo.getInstance().getVersionName();
            TipInfoDto tipInfoDto2 = tipInfoDto = new TipInfoDto();
            tipInfoDto.setUser((String)a);
            tipInfoDto2.setPlatform(string2);
            tipInfoDto2.setShowOperateGuide(false);
            Object object = a = new MessageDto();
            Object object2 = a;
            ((MessageDto)object2).setId(string);
            ((MessageDto)object2).setCommand(CommandEnum.LOG_TIP_SETTING.getType());
            ((MessageDto)object).setTipinfo(tipInfoDto);
            PluginWebsocketClient.sendWsMessage((MessageDto)object, project2);
            return;
        }
        catch (Exception exception) {
            byte.error(MethodGeneratorConfig.H("90)#\u001b=47\u0001!:\u0001\u001c5*\u001a&9:4591\u5386\u9058\u5f53\u5e67"), (Object)exception.getMessage());
            return;
        }
    }

    public static JsonObject getConfig() {
        SettingsDto settingsDto = CommonService.ED(AICodeSettingsState.getInstance());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(MethodGeneratorConfig.H(")*#8"), WebViewDataTypeEnum.SETTING_GET_CONFIG.getType());
        settingsDto = new Gson().toJsonTree((Object)settingsDto);
        jsonObject.add(NewFileUtils.H("\fF\u0019\\\u001e"), (JsonElement)settingsDto);
        return jsonObject;
    }

    public CommonService() {
        CommonService a;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void openUrl(String string, Project project) {
        Object object;
        String string2 = string;
        Object object2 = (RequestCaseCodeDto)new Gson().fromJson(string2, RequestCaseCodeDto.class);
        if (Objects.isNull(object2)) {
            return;
        }
        if (Objects.isNull(((RequestCaseCodeDto)object2).getValue())) {
            return;
        }
        object2 = ((RequestCaseCodeDto)object2).getValue();
        Object a = ((RequestCaseCodeDto.ValueDTO)object2).getUrl();
        String string3 = ((RequestCaseCodeDto.ValueDTO)object2).getType();
        String string4 = ((RequestCaseCodeDto.ValueDTO)object2).getAppend();
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)a) && org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)string3)) {
            object = AICodeSettingsState.getInstance();
            if (org.apache.commons.lang3.StringUtils.equals((CharSequence)NewFileUtils.H("x)n=b3X\u0002c;p<"), (CharSequence)string3)) {
                a = CommonService.sd((AICodeSettingsState)object);
            } else if (org.apache.commons.lang3.StringUtils.equals((CharSequence)MethodGeneratorConfig.H("\u0001\u0003\u0019\u0016\b\n\u0014\u001e\t\u0016\f\u0015\u0003\u001a\n\u001b2&\u0012\u0007\n\u000f"), (CharSequence)string3)) {
                a = ((AICodeSettingsState)object).maintainRepoUrl;
            } else if (org.apache.commons.lang3.StringUtils.equals((CharSequence)NewFileUtils.H("h3k6h0`?a;n;s;O\fc;p<"), (CharSequence)string3)) {
                a = ((AICodeSettingsState)object).officialWebsiteUrl;
            } else if (org.apache.commons.lang3.StringUtils.equals((CharSequence)MethodGeneratorConfig.H("\u0014\b\u0007\u0015\u0000\u0018\u0017\u0010\u001c\u000e\u001d\u001a\u0006\u0001\u0015\u0012\u0010\u0014\u0012((\u0019\u001d\n\u001a"), (CharSequence)string3)) {
                a = CommonService.WC((AICodeSettingsState)object, string4);
            } else {
                if (!org.apache.commons.lang3.StringUtils.equals((CharSequence)NewFileUtils.H(",r6l3h<n&^\u001bc;p<"), (CharSequence)string3)) return;
                a = ((AICodeSettingsState)object).userCenterWebUrl;
            }
        } else if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)a)) {
            void a2;
            if (!org.apache.commons.lang3.StringUtils.isBlank((CharSequence)string3)) return;
            PluginWebsocketClient.sendWsMessage(CommandEnum.USER_LOGIN, (Project)a2);
            return;
        }
        if (((RequestCaseCodeDto.ValueDTO)object2).isNeedToken() && ((String)a).endsWith(MethodGeneratorConfig.H("\u0015\u0000--6k"))) {
            a = (String)a + PluginStartupActivity.getApiKey();
        }
        object = a;
        ApplicationManager.getApplication().invokeLater(() -> CommonService.qf((String)object));
    }

    private static /* synthetic */ void KE(Project project, FirstChatMessage firstChatMessage) {
        Object a = firstChatMessage;
        Project a2 = project;
        ChatService.send2Agent(a2, (FirstChatMessage)a);
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void Qe(List list, String string, Project project, String string2) {
        void a;
        void a5222;
        List a2;
        Object a422;
        block7: {
            block6: {
                a422 = string;
                a2 = list;
                try {
                    if (!CollectionUtils.isEmpty((Collection)a2)) {
                        if (a2.size() >= 2) break block6;
                    }
                    return;
                }
                catch (Throwable throwable) {
                    byte.error("inset inline comment" + throwable.getMessage(), throwable);
                    return;
                }
            }
            LocalFileSystem localFileSystem = LocalFileSystem.getInstance();
            a422 = new File((String)a422);
            a422 = localFileSystem.findFileByIoFile((File)a422);
            if (a422 != null) break block7;
            return;
        }
        a422 = new OpenFileDescriptor((Project)a5222, (VirtualFile)a422);
        a422 = FileEditorManager.getInstance((Project)a5222).openTextEditor((OpenFileDescriptor)a422, true);
        if (a422 == null) {
            return;
        }
        Object object = (CodeInfoDto.RangeDTO)a2.get(0);
        CodeInfoDto.RangeDTO rangeDTO = (CodeInfoDto.RangeDTO)a2.get(1);
        object = ((CodeInfoDto.RangeDTO)object).getLine();
        Object object2 = a422;
        Object object3 = a422;
        int a5222 = a422.getSettings().getTabSize((Project)a5222);
        int a422 = AICodeStringUtil.leadingWhitespaceLengthWithTab(object3.getDocument().getText(new TextRange(a422.getDocument().getLineStartOffset(((Integer)object).intValue()), a422.getDocument().getLineEndOffset(((Integer)object).intValue()))), a5222);
        Integer a5222 = rangeDTO.getLine();
        int n = object2.getDocument().getLineStartOffset(((Integer)object).intValue());
        int a5222 = object3.getDocument().getLineEndOffset(a5222.intValue());
        String a422 = CommonService.Ge((String)a, a422);
        object2.getDocument().replaceString(n, a5222, (CharSequence)a422);
        object2.getContentComponent().requestFocus();
        int n2 = n;
        EditorManagerServiceImpl.acceptCount((Editor)object2, n2, n2 + a422.length(), CodeCollectEnum.INSERT);
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void tC(String string, String string2, Project project, String string3) {
        void a;
        void v0;
        void a2;
        Object a3;
        String string4 = string;
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)string4)) {
            void a4;
            a3 = org.apache.commons.lang3.StringUtils.isBlank((CharSequence)a4) ? NewFileUtils.H("x\u0011_\u0010N\u0004h;") : "Untitled." + (String)a4;
            v0 = a2;
        } else {
            a3 = "Untitled." + string4;
            v0 = a2;
        }
        NewFileUtils.handleCreateFile((Project)v0, (String)a, (String)a3, "", MethodGeneratorConfig.H("\u0003!\u0013\u001c"), CodeCollectEnum.NEW);
    }

    private static /* synthetic */ void aF(Project project, JsonObject jsonObject) {
        Project a = jsonObject;
        Project a2 = project;
        SocketMessageHandleListener.send2Web(a2, a);
    }

    private static /* synthetic */ void te(Project project, String string, JsonObject jsonObject) {
        RangeHighlighter rangeHighlighter;
        List<PresentationDataDto> list;
        Object a;
        Object object;
        EditorGutterComponentEx editorGutterComponentEx;
        Object a2 = string;
        Project a3 = project;
        if (a3.isDisposed()) {
            return;
        }
        Editor editor = AICodeUtils.getEditorFromAbsolutePath(a3, (String)a2);
        if (editor == null) {
            return;
        }
        if (CommonService.isSupportJava(editor)) {
            return;
        }
        MarkupModel markupModel = editor.getMarkupModel();
        if (markupModel == null) {
            return;
        }
        if (!LineToolsTypeEnum.ICON.getCode().equals(AICodeSettingsState.getInstance().lineToolsType)) {
            MarkupModel markupModel2 = markupModel;
            CommonService.clearHighLight(markupModel2, markupModel2.getAllHighlighters());
            return;
        }
        ArrayList<RangeHighlighter> arrayList = new ArrayList<RangeHighlighter>();
        if (markupModel.getAllHighlighters() != null) {
            int n;
            editorGutterComponentEx = markupModel.getAllHighlighters();
            int n2 = ((RangeHighlighter[])editorGutterComponentEx).length;
            int n3 = n = 0;
            while (n3 < n2) {
                RangeHighlighter rangeHighlighter2 = editorGutterComponentEx[n];
                if (rangeHighlighter2.getGutterIconRenderer() instanceof CheckGutterIconRenderer) {
                    arrayList.add(rangeHighlighter2);
                } else {
                    RangeHighlighter[] rangeHighlighterArray = new RangeHighlighter[1];
                    rangeHighlighterArray[0] = rangeHighlighter2;
                    CommonService.clearHighLight(markupModel, rangeHighlighterArray);
                }
                n3 = ++n;
            }
        }
        editorGutterComponentEx = new LinkedHashMap();
        try {
            object = PermissionEnum.getEditorAction();
            if (CollectionUtils.isEmpty(object)) {
                MarkupModel markupModel3 = markupModel;
                CommonService.clearHighLight(markupModel3, markupModel3.getAllHighlighters());
                return;
            }
            list = CommonService.ID((JsonObject)a, (String)a2, editor);
            if (CollectionUtils.isEmpty(list)) {
                MarkupModel markupModel4 = markupModel;
                CommonService.clearHighLight(markupModel4, markupModel4.getAllHighlighters());
                return;
            }
            int n = editor.getDocument().getLineCount();
            a2 = list.iterator();
            block3: while (true) {
                Object object2 = a2;
                while (object2.hasNext()) {
                    a = (PresentationDataDto)a2.next();
                    rangeHighlighter = PermissionEnum.getEditorAction();
                    if (CollectionUtils.isEmpty(rangeHighlighter)) {
                        object2 = a2;
                        continue;
                    }
                    if (n <= ((PresentationDataDto)a).getLine()) {
                        object2 = a2;
                        continue;
                    }
                    CommonService.SE((PresentationDataDto)a, rangeHighlighter);
                    if (!CollectionUtils.isNotEmpty(rangeHighlighter)) continue block3;
                    editorGutterComponentEx.put(a, rangeHighlighter);
                    continue block3;
                }
                break;
            }
        }
        catch (Exception exception) {
            MarkupModel markupModel5 = markupModel;
            CommonService.clearHighLight(markupModel5, markupModel5.getAllHighlighters());
            return;
        }
        Object object3 = object = editorGutterComponentEx.entrySet().iterator();
        while (object3.hasNext()) {
            list = (Map.Entry)object.next();
            List list2 = (List)list.getValue();
            a2 = (PresentationDataDto)list.getKey();
            if (CommonService.WD(arrayList, (PresentationDataDto)a2, (Set<String>)(a = list2.stream().map(CommandEnum::getType).collect(Collectors.toSet())))) {
                object3 = object;
                continue;
            }
            rangeHighlighter = markupModel.addLineHighlighter(((PresentationDataDto)a2).getLine(), 0, null);
            Object object4 = a2;
            editorGutterComponentEx = new CheckGutterIconRenderer((PresentationDataDto)object4, ((PresentationDataDto)a2).getLine(), ((PresentationDataDto)object4).getType(), rangeHighlighter, editor, list2);
            rangeHighlighter.setGutterIconRenderer((GutterIconRenderer)editorGutterComponentEx);
            editorGutterComponentEx = (EditorGutterComponentEx)editor.getGutter();
            object3 = object;
            CommonService.uE(editor, editorGutterComponentEx, ((PresentationDataDto)a2).getLine());
        }
        CommonService.clearHighLight(markupModel, arrayList.toArray(new RangeHighlighter[0]));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void Qd(Project project, String string) {
        Project project2 = project;
        Editor editor = EditorUtils.getSelectedEditor(project2);
        if (editor != null) {
            void a;
            Editor editor2 = editor;
            SelectionModel selectionModel = editor2.getSelectionModel();
            int a2 = selectionModel.getSelectionStart();
            editor2.getDocument().replaceString(a2, selectionModel.getSelectionEnd(), (CharSequence)a);
            editor2.getContentComponent().requestFocus();
            selectionModel.removeSelection();
            EditorManagerServiceImpl.acceptCount(project2, a2, a2 + a.length(), ((EditorImpl)editor).getVirtualFile().getPath(), editor.getDocument(), CodeCollectEnum.INSERT);
        }
    }

    /*
     * WARNING - void declaration
     */
    private static String WC(AICodeSettingsState aICodeSettingsState, String string) {
        void v0;
        void a;
        Object a2;
        AICodeSettingsState aICodeSettingsState2 = aICodeSettingsState;
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)aICodeSettingsState2.codeKnowledgeWebUrl)) {
            return null;
        }
        if (aICodeSettingsState2.codeKnowledgeWebUrl.endsWith(MethodGeneratorConfig.H("%0\b\b\u0019D"))) {
            a2 = aICodeSettingsState2.codeKnowledgeWebUrl + PluginStartupActivity.getApiKey();
            v0 = a;
        } else {
            AICodeSettingsState aICodeSettingsState3 = aICodeSettingsState2;
            if (!aICodeSettingsState2.codeKnowledgeWebUrl.endsWith(NewFileUtils.H("`"))) {
                a2 = aICodeSettingsState3.codeKnowledgeWebUrl + "?token=" + PluginStartupActivity.getApiKey();
                v0 = a;
            } else {
                a2 = aICodeSettingsState3.codeKnowledgeWebUrl;
                v0 = a;
            }
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)v0)) {
            Object object = a2;
            a2 = ((String)a2).contains(MethodGeneratorConfig.H("F")) ? (String)object + "&" + (String)a : (String)object + "?" + (String)a;
        }
        return a2;
    }

    /*
     * WARNING - void declaration
     */
    public static void clearHighLight(MarkupModel markupModel, RangeHighlighter[] rangeHighlighterArray) {
        int n;
        void a;
        MarkupModel markupModel2 = markupModel;
        if (a == null) {
            return;
        }
        int n2 = ((void)a).length;
        int n3 = n = 0;
        while (n3 < n2) {
            block7: {
                void var4_8;
                block5: {
                    int n4;
                    int n5;
                    int a2;
                    block8: {
                        block6: {
                            var4_8 = a[n];
                            if (var4_8.getTextAttributes() == null || var4_8.getTextAttributes().getBackgroundColor() == null) break block5;
                            Color color = var4_8.getTextAttributes().getBackgroundColor();
                            a2 = color.getRed();
                            n5 = color.getGreen();
                            n4 = color.getBlue();
                            if (a2 != 240) break block6;
                            if (n5 != 20) break block6;
                            if (n4 == 20) break block7;
                        }
                        if (a2 != 120) break block8;
                        if (n5 != 254) break block8;
                        if (n4 == 200) break block7;
                    }
                    if (a2 != 34) break block5;
                    if (n5 != 66) break block5;
                    if (n4 == 131) break block7;
                }
                markupModel2.removeHighlighter((RangeHighlighter)var4_8);
            }
            n3 = ++n;
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void handleComment(Project project, RequestCaseCodeDto.ValueDTO valueDTO, ChatOperationEnum chatOperationEnum, String string, String string2, RequestCaseCodeDto requestCaseCodeDto) {
        void a;
        void a2;
        void a3;
        Project project2 = project;
        Object a4 = a3.getCodeInfo();
        if (a4 == null) {
            return;
        }
        String string3 = ((CodeInfoDto)a4).getPath();
        if (StringUtils.isBlank((CharSequence)string3)) {
            return;
        }
        if ((a4 = ((CodeInfoDto)a4).getRange()) == null || a4.size() == 0) {
            return;
        }
        if (ChatOperationEnum.ACTION_ACCEPT == a2) {
            WebRequestDto<void> a5;
            MessageDto messageDto;
            MessageDto messageDto2 = messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.CODE_COMMENT_RANGE.getType());
            MessageDto messageDto3 = messageDto;
            messageDto3.setPath(string3);
            messageDto3.setLang((String)((Object)a5));
            messageDto2.setRange((List<CodeInfoDto.RangeDTO>)a4);
            messageDto2.setMd5(null != a3.getContext() ? a3.getContext().getMd5() : null);
            PluginWebsocketClient.sendWsMessage(messageDto, project2);
            a5 = new WebRequestDto<void>();
            a5.setValue(a);
            if (null != a3.getContext() && CollectionUtils.isNotEmpty(a3.getContext().getMethods())) {
                void a6;
                PluginWebsocketClient.WEB_REQUEST_DATA.put(messageDto.getId(), (RequestCaseCodeDto)a6);
                return;
            }
            PluginWebsocketClient.WEB_REQUEST.put(messageDto.getId(), a5);
            return;
        }
        if (ChatOperationEnum.ACTION_ACCEPT_INLINE_COMMENT == a2) {
            CommonService.insertLineComment(project2, (String)a, string3, (List<CodeInfoDto.RangeDTO>)a4);
        }
    }

    public static void getPluginInfo(Project project) {
        Project project2 = project;
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(NewFileUtils.H("\\\u0003Q\u0016"), WebViewDataTypeEnum.COMMON_PLUGIN_BASE_INFO.getType());
            Project a = new JsonObject();
            a.addProperty(MethodGeneratorConfig.H("-1&==\u0013%?&!54;"), "V" + BasicActionsBundle.message(NewFileUtils.H("\tw/M\u0014^GY\u0017R\u0012I\u001c)#E\u0000[\u0013N\u001d"), new Object[0]));
            a.addProperty(MethodGeneratorConfig.H("!?8\u0018\u0012)1\u0016=/0"), BasicActionsBundle.message(NewFileUtils.H("\u001aB\u001aU\f{bR\u001cN\u000e@\u0015\t\u0005U\u0010k<C\\L\u001bU\u0016"), new Object[0]));
            JsonObject jsonObject2 = jsonObject;
            Project project3 = a;
            project3.addProperty(MethodGeneratorConfig.H("30\u0018%?&!54;"), ApplicationInfo.getInstance().getFullApplicationName());
            project3.addProperty(NewFileUtils.H("T\fS\u0006b8o\u0001f\u001bL\u0016"), System.getProperty(MethodGeneratorConfig.H("\u001c)z<=60")));
            jsonObject2.add(NewFileUtils.H("\u0004I\u0016T\u0016"), (JsonElement)a);
            SocketMessageHandleListener.send2Web(project2, jsonObject2);
            return;
        }
        catch (Exception exception) {
            byte.error(MethodGeneratorConfig.H("(6)\r?/3\u0014\u001d\u0013:43\u5f59\u5e6d"), (Object)exception.getMessage());
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void handleClick(Project project, String string) {
        ChatOperationEnum chatOperationEnum;
        String string2;
        String string3;
        String a;
        String string4;
        void a2;
        Project project2 = project;
        RequestCaseCodeDto requestCaseCodeDto = (RequestCaseCodeDto)new Gson().fromJson((String)a2, RequestCaseCodeDto.class);
        RequestCaseCodeDto.ValueDTO valueDTO = requestCaseCodeDto.getValue();
        if (Objects.isNull(requestCaseCodeDto) || Objects.isNull(valueDTO)) {
            return;
        }
        try {
            RequestCaseCodeDto.ValueDTO valueDTO2 = valueDTO;
            string4 = valueDTO2.getType();
            a = valueDTO2.getId();
            string3 = valueDTO2.getContent();
            string2 = valueDTO2.getLanguage();
            chatOperationEnum = ChatOperationEnum.getByName(string4);
            if (Objects.isNull((Object)chatOperationEnum)) {
                return;
            }
        }
        catch (Exception exception) {
            byte.error(exception.getMessage(), (Throwable)exception);
            return;
        }
        {
            String string5;
            if (CommonService.cD(project2, (String)a2)) {
                return;
            }
            switch (chatOperationEnum) {
                case ACTION_COPY: {
                    CommonService.copyCode(project2, string3);
                    string5 = a;
                    break;
                }
                case ACTION_INSERT: {
                    CommonService.insertCode(project2, string3);
                    string5 = a;
                    break;
                }
                case ACTION_NEW: {
                    CommonService.genCodeFile(project2, string3, string2);
                    string5 = a;
                    break;
                }
                case ACTION_DIFF: {
                    CommonService.diffCode(project2, valueDTO);
                    string5 = a;
                    break;
                }
                case ACTION_ACCEPT: 
                case ACTION_ACCEPT_INLINE_COMMENT: {
                    CommonService.handleComment(project2, valueDTO, chatOperationEnum, string2, string3, requestCaseCodeDto);
                    string5 = a;
                    break;
                }
                default: {
                    string5 = a;
                }
            }
            CommonService.logOperate(string5, string4, project2);
            return;
        }
    }
}
