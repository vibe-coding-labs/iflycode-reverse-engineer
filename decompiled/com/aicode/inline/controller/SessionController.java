/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.IdUtil
 *  com.intellij.openapi.Disposable
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.command.WriteCommandAction
 *  com.intellij.openapi.editor.Document
 *  com.intellij.openapi.editor.Editor
 *  com.intellij.openapi.editor.Inlay
 *  com.intellij.openapi.editor.SelectionModel
 *  com.intellij.openapi.editor.markup.MarkupModel
 *  com.intellij.openapi.editor.markup.RangeHighlighter
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.util.TextRange
 *  com.intellij.openapi.util.UserDataHolder
 *  com.intellij.openapi.vfs.VirtualFile
 *  com.intellij.psi.codeStyle.CodeStyleManager
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  org.apache.commons.lang3.StringUtils
 *  org.jetbrains.annotations.NotNull
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.inline.controller;

import cn.hutool.core.util.IdUtil;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.InlineChatCommandService;
import com.aicode.diff.DiffService;
import com.aicode.enums.AICodeStatus;
import com.aicode.enums.OperateActionEnum;
import com.aicode.inline.InlineChatHandleService;
import com.aicode.inline.InlineChatInlay;
import com.aicode.inline.InlineChatService;
import com.aicode.inline.InlineChatStreamHandleService;
import com.aicode.inline.content.ChatMessage;
import com.aicode.inline.dto.InlineChatInfo;
import com.aicode.inline.dto.LastSelectionTextCache;
import com.aicode.inline.enums.InlineChatCategoryEnum;
import com.aicode.inline.enums.InlineChatOperateEnum;
import com.aicode.inline.enums.InlineChatStepEnum;
import com.aicode.inline.render.InlineChatBtnPanelRenderer;
import com.aicode.inline.render.InlineChatCategoryPanelRenderer;
import com.aicode.inline.render.InlineChatErrorPanelRenderer;
import com.aicode.inline.render.InlineChatStopPanelRenderer;
import com.aicode.listener.AutoCodeGenerateListener;
import com.aicode.service.EditorManagerService;
import com.aicode.status.AICodeStatusService;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.AICodeUtils;
import com.aicode.util.Application;
import com.aicode.util.EditorCacheUtil;
import com.aicode.util.EditorKt;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public abstract class SessionController
implements Disposable {
    private int catch;
    private boolean const;
    private volatile InlineChatStepEnum false;
    private Editor do;
    private int break;
    private String class;
    private String true;
    private int this;
    private static final Logger else = LoggerFactory.getLogger(SessionController.class);
    private int char;
    private RangeHighlighter int;
    private Inlay<?> new;
    private InlineChatOperateEnum long;
    private int super;
    private int for;
    public volatile boolean stop;
    private String if;
    private int case;
    private Map<Integer, RangeHighlighter> final;
    private InlineChatCategoryEnum try;
    private static boolean float = true;
    private int byte;
    private int enum;

    public void doCancelCategory(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        if (a2.false == InlineChatStepEnum.CATEGORY) {
            ApplicationManager.getApplication().invokeLater(() -> a2.gA((Editor)a));
        }
    }

    public int getLineBreBlock() {
        SessionController a;
        return a.break;
    }

    public InlineChatStepEnum getInlineChatStepEnum() {
        SessionController a;
        return a.false;
    }

    public void doStop(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        if (a2.false == InlineChatStepEnum.LOADING) {
            ApplicationManager.getApplication().invokeLater(() -> a2.nA((Editor)a));
        }
    }

    /*
     * WARNING - void declaration
     */
    private List<CodeInfoDto.RangeDTO> kB(Editor editor, Integer n, Integer n2) {
        void a;
        void a2;
        Integer a3 = n2;
        SessionController a4 = this;
        return (List)ApplicationManager.getApplication().runReadAction(() -> SessionController.JC((Editor)a2, (Integer)a, a3));
    }

    private void YB(@NotNull Editor editor) {
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(7);
        }
        ApplicationManager.getApplication().invokeLater(() -> a.Pc((Editor)sessionController2));
    }

    private /* synthetic */ Unit cC(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        a2.MB((Editor)a);
        return Unit.INSTANCE;
    }

    public void setTipText(String string) {
        String a = string;
        SessionController a2 = this;
        a2.true = a;
    }

    private /* synthetic */ void ua(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        a2.vB((Editor)a);
    }

    private void rA(@NotNull Editor editor) {
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(12);
        }
        if (EditorKt.inlineChatBtnCache.containsKey(sessionController2)) {
            EditorKt.inlineChatBtnCache.get(sessionController2).dispose();
            EditorKt.inlineChatBtnCache.remove(sessionController2);
        }
    }

    /*
     * WARNING - void declaration
     */
    public final void executeRequest(@NotNull ChatMessage chatMessage, @NotNull Editor editor) {
        void chatMessage2;
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (chatMessage2 == null) {
            SessionController.enum(0);
        }
        if (sessionController2 == null) {
            SessionController.enum(1);
        }
        ApplicationManager.getApplication().runReadAction(() -> a.Zc((Editor)sessionController2, (ChatMessage)chatMessage2));
    }

    public int getEndLineNumber() {
        SessionController a;
        return a.catch;
    }

    public abstract void unlockSession();

    public void doErrorRetry(@NotNull Editor editor) {
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(8);
        }
        SessionController sessionController3 = a;
        SessionController sessionController4 = sessionController2;
        sessionController3.vB((Editor)sessionController4);
        sessionController3.YB((Editor)sessionController4);
    }

    public int getCareOffset() {
        SessionController a;
        return a.this;
    }

    public void setChangeLength(int n) {
        int a = n;
        SessionController a2 = this;
        a2.for = a;
    }

    public void renderCategoryPanel() {
        Object a;
        SessionController sessionController = this;
        if (sessionController.do.getSelectionModel().hasSelection()) {
            a = sessionController.do.getSelectionModel();
            a.removeSelection();
        }
        sessionController.false = InlineChatStepEnum.CATEGORY;
        a = new InlineChatCategoryPanelRenderer(sessionController.getLineBreBlock(), (Disposable)sessionController.do.getProject(), sessionController.do, (Function0<Unit>)((Function0)() -> {
            SessionController a;
            SessionController sessionController = a;
            sessionController.doCancelCategory(sessionController.do);
            return Unit.INSTANCE;
        }));
        sessionController.do.getContentComponent().add((Component)a);
        SessionController sessionController2 = sessionController;
        ((InlineChatCategoryPanelRenderer)a).createInlay(sessionController2.byte);
        String string = InlineChatService.getVirtualFile(sessionController2.do).getUrl();
        EditorKt.categoryRendererCollection.put(string, (InlineChatCategoryPanelRenderer)a);
    }

    /*
     * WARNING - void declaration
     */
    private /* synthetic */ void nB(Editor editor, CommandEnum commandEnum) {
        void a;
        Object a2 = commandEnum;
        SessionController a3 = this;
        a3.handleOperation((Editor)a, (CommandEnum)((Object)a2));
    }

    /*
     * WARNING - void declaration
     */
    private /* synthetic */ void Zc(Editor editor, ChatMessage chatMessage) {
        void a;
        ChatMessage a2 = chatMessage;
        SessionController a3 = this;
        a3.BA((Editor)a, a2);
    }

    /*
     * WARNING - void declaration
     */
    private /* synthetic */ void Ea(Editor editor, ChatMessage chatMessage) {
        SessionController a = editor;
        SessionController a2 = this;
        if (a.getSelectionModel().hasSelection()) {
            void a222;
            a222.setSelected(true);
            SelectionModel selectionModel = a.getSelectionModel();
            int a222 = selectionModel.getSelectionStart();
            int n = selectionModel.getSelectionEnd();
            if (!Boolean.TRUE.equals(EditorCacheUtil.getEditCache((Editor)a))) {
                Object object = a.getDocument();
                int n2 = object.getLineNumber(a222);
                int n3 = object.getLineNumber(n);
                n2 = object.getLineStartOffset(n2);
                n3 = object.getLineEndOffset(n3);
                Document document = object;
                object = object.getText(new TextRange(n2, n3));
                EditorCacheUtil.LAST_SELECTION_TEXT_CACHE_KEY.set((UserDataHolder)a, (Object)new LastSelectionTextCache(a222, n, (String)object, a2.kB((Editor)a, n2, n3)));
                EditorCacheUtil.ORIGINAL_SELECTION_TEXT_CACHE_KEY.set((UserDataHolder)a, (Object)new LastSelectionTextCache(a222, n, (String)object, a2.kB((Editor)a, n2, n3)));
            }
            a.getContentComponent().requestFocus();
        }
    }

    public void errorStop(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        SessionController sessionController = a;
        a2.stop = true;
        AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
        EditorKt.closeStopPanel((Editor)sessionController);
        sessionController.getMarkupModel().removeAllHighlighters();
    }

    /*
     * WARNING - void declaration
     */
    public final void sendMessage(ChatMessage chatMessage, @NotNull Editor editor) {
        void message;
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(3);
        }
        a.do = sessionController2;
        InlineChatInlay.disposeInlay();
        VirtualFile virtualFile = InlineChatService.getVirtualFile((Editor)sessionController2);
        if (virtualFile == null) {
            return;
        }
        SessionController sessionController3 = sessionController2;
        int n = sessionController3.getCaretModel().getOffset();
        SessionController sessionController4 = a;
        sessionController4.byte = a.this = n;
        sessionController4.char = n;
        if (sessionController3.getSelectionModel().hasSelection()) {
            n = sessionController2.getSelectionModel().getSelectionStart();
            SessionController sessionController5 = a;
            SessionController sessionController6 = a;
            sessionController6.const = true;
            sessionController6.byte = n;
            sessionController5.char = sessionController2.getSelectionModel().getSelectionEnd();
            sessionController5.if = sessionController2.getSelectionModel().getSelectedText();
        }
        a.class = CodeStyleManager.getInstance((Project)sessionController2.getProject()).getLineIndent(sessionController2.getDocument(), a.byte);
        if (StringUtils.isBlank((CharSequence)a.class)) {
            a.class = "";
        }
        SessionController sessionController7 = a;
        SessionController sessionController8 = sessionController2;
        SessionController sessionController9 = a;
        sessionController9.catch = sessionController2.getDocument().getLineNumber(a.char);
        int n2 = sessionController2.getDocument().getLineNumber(a.byte);
        int n3 = sessionController8.getSettings().getTabSize(sessionController2.getProject());
        sessionController9.break = AICodeStringUtil.leadingWhitespaceLengthWithTab(sessionController8.getDocument().getText(new TextRange(sessionController2.getDocument().getLineStartOffset(n2), sessionController2.getDocument().getLineEndOffset(n2))), n3);
        sessionController7.Qa((ChatMessage)message, (Editor)sessionController2);
        SessionController sessionController10 = sessionController2;
        EditorCacheUtil.setCache((Editor)sessionController10, n, message.getQuestion(), message.isSelected());
        InlineChatService.Companion.closeInlineChat((Editor)sessionController10);
        sessionController7.nc((ChatMessage)message, (Editor)sessionController10, virtualFile);
    }

    public abstract void lockSession();

    public void doRetry(@NotNull Editor editor) {
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(6);
        }
        SessionController sessionController3 = a;
        SessionController sessionController4 = sessionController2;
        sessionController3.FC((Editor)sessionController4, CommandEnum.DIALOG_REJECT);
        sessionController3.YB((Editor)sessionController4);
    }

    public void renderStopPanel() {
        Object a;
        SessionController sessionController = this;
        if (sessionController.do.getSelectionModel().hasSelection()) {
            a = sessionController.do.getSelectionModel();
            a.removeSelection();
        }
        sessionController.false = InlineChatStepEnum.LOADING;
        a = new InlineChatStopPanelRenderer(sessionController.getLineBreBlock(), (Disposable)sessionController.do.getProject(), sessionController.do, (Function0<Unit>)((Function0)() -> {
            SessionController a;
            SessionController sessionController = a;
            sessionController.doStop(sessionController.do);
            return Unit.INSTANCE;
        }));
        sessionController.do.getContentComponent().add((Component)a);
        SessionController sessionController2 = sessionController;
        ((InlineChatStopPanelRenderer)a).createInlay(sessionController2.byte);
        String string = InlineChatService.getVirtualFile(sessionController2.do).getUrl();
        EditorKt.stopRendererCollection.put(string, (InlineChatStopPanelRenderer)a);
    }

    public int getEndOffset() {
        SessionController a;
        return a.char;
    }

    public void setLineBreBlock(int n) {
        int a = n;
        SessionController a2 = this;
        a2.break = a;
    }

    private void VA(@NotNull Editor editor) {
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(18);
        }
        ApplicationManager.getApplication().invokeLater(() -> SessionController.CA((Editor)sessionController2));
    }

    public void doAccept(@NotNull Editor editor) {
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(13);
        }
        a.FC((Editor)sessionController2, CommandEnum.DIALOG_ACCEPT);
    }

    /*
     * WARNING - void declaration
     */
    private void BA(@NotNull Editor editor, ChatMessage chatMessage) {
        void a;
        InlineChatInfo inlineChatInfo;
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController editor2 = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(2);
        }
        if ((inlineChatInfo = EditorKt.getInfoByEditor((Editor)sessionController2)) == null) {
            return;
        }
        inlineChatInfo.setEditor((Editor)sessionController2);
        inlineChatInfo.setSessionController(editor2);
        EditorKt.addInfoByEditor((Editor)sessionController2, inlineChatInfo);
        editor2.sendMessage((ChatMessage)a, (Editor)sessionController2);
    }

    public SessionController() {
        SessionController a;
        SessionController sessionController = a;
        SessionController sessionController2 = a;
        sessionController2.stop = false;
        sessionController2.const = false;
        sessionController.class = "";
        sessionController.true = "";
        SessionController sessionController3 = a;
        sessionController.final = new HashMap<Integer, RangeHighlighter>();
    }

    public Editor getEditor() {
        SessionController a;
        return a.do;
    }

    /*
     * WARNING - void declaration
     */
    private void fB(Editor editor, boolean bl, InlineChatOperateEnum inlineChatOperateEnum) {
        void a;
        void a2;
        void a3;
        SessionController sessionController = this;
        void v0 = a3;
        MarkupModel markupModel = v0.getMarkupModel();
        SessionController a4 = v0.getDocument();
        WriteCommandAction.runWriteCommandAction((Project)v0.getProject(), () -> sessionController.ba(markupModel, (InlineChatOperateEnum)a2, (boolean)a, (Document)a4, (Editor)a3));
    }

    public void setOriginalSelectText(String string) {
        String a = string;
        SessionController a2 = this;
        a2.if = a;
    }

    public int getInsertStartOffset() {
        SessionController a;
        return a.super;
    }

    public int getChangeLength() {
        SessionController a;
        return a.for;
    }

    public String getLineIndent() {
        SessionController a;
        return a.class;
    }

    public void setHasSelect(boolean bl) {
        boolean a = bl;
        SessionController a2 = this;
        a2.const = a;
    }

    public void clear(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        if (a2.false != null) {
            switch (a2.false) {
                case CATEGORY: {
                    SessionController sessionController = a2;
                    while (false) {
                    }
                    SessionController sessionController2 = sessionController;
                    sessionController.doCancelCategory((Editor)a);
                    break;
                }
                case LOADING: {
                    SessionController sessionController = a2;
                    SessionController sessionController2 = sessionController;
                    sessionController.doStop((Editor)a);
                    break;
                }
                case ERROR: {
                    SessionController sessionController = a2;
                    SessionController sessionController2 = sessionController;
                    sessionController.doCancel((Editor)a);
                    break;
                }
                case SUCCESS: {
                    a2.doReject((Editor)a);
                }
                default: {
                    SessionController sessionController2 = a2;
                }
            }
            sessionController2.false = null;
        }
    }

    public InlineChatCategoryEnum getInlineChatCategoryEnum() {
        SessionController a;
        return a.try;
    }

    public Map<Integer, RangeHighlighter> getToHandleRangeHighlighterMap() {
        SessionController a;
        return a.final;
    }

    private /* synthetic */ void JB(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        EditorKt.closeCategoryPanel((Editor)a);
        a2.renderCategoryPanel();
    }

    public void handleUndo(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        if (a2.false != null) {
            switch (a2.false) {
                case LOADING: {
                    SessionController sessionController = a2;
                    while (false) {
                    }
                    SessionController sessionController2 = sessionController;
                    sessionController.doStop((Editor)a);
                    break;
                }
                case ERROR: {
                    SessionController sessionController = a2;
                    SessionController sessionController2 = sessionController;
                    sessionController.doCancel((Editor)a);
                    break;
                }
                case SUCCESS: {
                    a2.VA((Editor)a);
                }
                default: {
                    SessionController sessionController2 = a2;
                }
            }
            sessionController2.false = null;
        }
    }

    public void setCareOffset(int n) {
        int a = n;
        SessionController a2 = this;
        a2.this = a;
    }

    private /* synthetic */ void nA(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        a2.stop = true;
        SessionController sessionController = a;
        EditorKt.removeEditor((Editor)sessionController);
        EditorKt.closeStopPanel((Editor)a);
        SessionController.lB((Editor)sessionController);
    }

    private void MB(@NotNull Editor editor) {
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(10);
        }
        a.Jc((Editor)sessionController2);
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray;
        Object[] objectArray2;
        String string = AICodeUtils.H("@}mq,*bv hn};US|t@vao-zeor,*HWx$;7o5&gf.$|\u0002\u0007s.lzha=}oz#of-dqq\u007f");
        Object[] objectArray3 = new Object[3];
        switch (a) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = Application.H("rgmxBi|vgut");
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: {
                objectArray2 = objectArray3;
                while (false) {
                }
                objectArray3[0] = AICodeUtils.H("ficpra");
                break;
            }
        }
        objectArray2[1] = Application.H("lca mffivta$]\\lhv?p\u007fg~}cbaFR _k~g~}\u007fLcb{~`ijwc");
        switch (a) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = AICodeUtils.H("~mxpuzf_f|\u007fang");
                break;
            }
            case 3: {
                objectArray = objectArray2;
                while (false) {
                }
                objectArray2[2] = Application.H("bjbhBi|vgut");
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[2] = AICodeUtils.H("tez@lwhmkoj");
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[2] = Application.H("mjiGr~tlxe`bLdezt");
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[2] = AICodeUtils.H("il_opoj");
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[2] = Application.H("Rjx|tFx~}Mmod_jicqe");
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[2] = AICodeUtils.H("y|E|qbq_opoj");
                break;
            }
            case 9: {
                objectArray = objectArray2;
                objectArray2[2] = Application.H("ynbhciLicsc");
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[2] = AICodeUtils.H("gbNm{u");
                break;
            }
            case 11: {
                objectArray = objectArray2;
                objectArray2[2] = Application.H("h`^jocqe");
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[2] = AICodeUtils.H("jh|\\MskHaw|svCfbyExdFi}");
                break;
            }
            case 13: {
                objectArray = objectArray2;
                objectArray2[2] = Application.H("h`Mlfcbe");
                break;
            }
            case 14: {
                objectArray = objectArray2;
                objectArray2[2] = AICodeUtils.H("kld`qv");
                break;
            }
            case 15: {
                objectArray = objectArray2;
                objectArray2[2] = Application.H("euyv}jC|j~nqo}\u007f");
                break;
            }
            case 16: {
                objectArray = objectArray2;
                objectArray2[2] = AICodeUtils.H("homiohNm{u");
                break;
            }
            case 17: {
                objectArray = objectArray2;
                objectArray2[2] = Application.H("bd|dM\u007faYdjgt");
                break;
            }
            case 18: {
                objectArray = objectArray2;
                objectArray2[2] = AICodeUtils.H("gb_jy|");
                break;
            }
        }
        throw new IllegalArgumentException(String.format(string, objectArray));
    }

    public void setRangeHighlighter(RangeHighlighter rangeHighlighter) {
        SessionController a = rangeHighlighter;
        SessionController a2 = this;
        a2.int = a;
    }

    private /* synthetic */ Unit sC(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        a2.doErrorRetry((Editor)a);
        return Unit.INSTANCE;
    }

    private /* synthetic */ void Pc(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        SessionController sessionController = a;
        InlineChatService.Companion.openInlineChat((Editor)sessionController, EditorCacheUtil.getCache((Editor)sessionController));
        if (a2.const) {
            SessionController sessionController2 = a2;
            a.getSelectionModel().setSelection(sessionController2.byte, sessionController2.char);
        }
        a.getCaretModel().moveToOffset(a2.this);
    }

    public InlineChatOperateEnum getInlineChatOperateEnum() {
        SessionController a;
        return a.long;
    }

    public void setDefaultOffset(int n) {
        int a = n;
        SessionController a2 = this;
        a2.case = a;
    }

    public boolean isHasSelect() {
        SessionController a;
        return a.const;
    }

    public int getDefaultOffset() {
        SessionController a;
        return a.case;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private /* synthetic */ void ba(MarkupModel markupModel, InlineChatOperateEnum inlineChatOperateEnum, boolean bl, Document document, Editor editor) {
        int n;
        void a;
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = document;
        SessionController a2 = sessionController;
        RangeHighlighter[] rangeHighlighterArray = a.getAllHighlighters();
        int n2 = rangeHighlighterArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            RangeHighlighter rangeHighlighter = rangeHighlighterArray[n];
            a.removeHighlighter(rangeHighlighter);
            n3 = ++n;
        }
        try {
            SessionController a3;
            void v2;
            void a4;
            void a5;
            void a6;
            if (InlineChatOperateEnum.EDIT == a6) {
                if (a5 != false) {
                    v2 = a4;
                    SessionController sessionController3 = a2;
                    a3.replaceString(sessionController3.byte, sessionController3.char + a2.for, a2.true);
                } else {
                    SessionController sessionController4 = a2;
                    a3.replaceString(sessionController4.byte, sessionController4.char + a2.for, a2.if);
                    v2 = a4;
                }
            } else {
                if (InlineChatOperateEnum.INSERT == a6 && a5 == false) {
                    SessionController sessionController5 = a2;
                    a3.deleteString(sessionController5.super, sessionController5.super + a2.true.length());
                }
                v2 = a4;
            }
            InlineChatHandleService.saveDocument(v2.getProject(), (Document)a3);
            return;
        }
        catch (Throwable throwable) {
            return;
        }
    }

    public static void setAccept(boolean a) {
        float = a;
    }

    private /* synthetic */ Unit zb(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        a2.doAccept((Editor)a);
        return Unit.INSTANCE;
    }

    public String getOriginalSelectText() {
        SessionController a;
        return a.if;
    }

    public void doReject(@NotNull Editor editor) {
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(11);
        }
        a.FC((Editor)sessionController2, CommandEnum.DIALOG_REJECT);
    }

    private static void lB(Editor editor) {
        Editor editor2 = editor;
        AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
        Editor editor3 = editor2;
        editor3.getMarkupModel().removeAllHighlighters();
        Editor a = DiffService.getDocument(editor3);
        if (a == null) {
            return;
        }
        WriteCommandAction.runWriteCommandAction((Project)editor2.getProject(), () -> SessionController.gC(editor2, (Document)a));
    }

    private /* synthetic */ void gA(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        SessionController sessionController = a;
        a2.stop = true;
        EditorKt.removeEditor((Editor)sessionController);
        EditorKt.closeCategoryPanel((Editor)sessionController);
    }

    public void setEndOffset(int n) {
        int a = n;
        SessionController a2 = this;
        a2.char = a;
    }

    public void setEditor(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        a2.do = a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static /* synthetic */ void CA(Editor editor) {
        Editor editor2 = editor;
        Object a = SessionController.class;
        synchronized (SessionController.class) {
            block4: {
                if (EditorKt.containEditor(editor2)) break block4;
                // ** MonitorExit[a /* !! */ ] (shouldn't be in output)
                return;
            }
            Editor editor3 = editor2;
            EditorKt.removeEditor(editor3);
            AutoCodeGenerateListener.inlineChatOperate.set(true);
            EditorKt.closeButtonPanel(editor3);
            SessionController.lB(editor3);
            // ** MonitorExit[a /* !! */ ] (shouldn't be in output)
            return;
        }
    }

    private /* synthetic */ Unit Xa(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        a2.doRetry((Editor)a);
        return Unit.INSTANCE;
    }

    private static /* synthetic */ void gC(Editor editor, Document document) {
        Editor a = document;
        Editor a2 = editor;
        InlineChatStreamHandleService.HANDING_DATA = true;
        Editor editor2 = a2;
        editor2.getDocument().setText((CharSequence)a.getText());
        InlineChatStreamHandleService.HANDING_DATA = false;
        AutoCodeGenerateListener.inlineChatOperate.set(true);
        InlineChatHandleService.saveDocument(editor2.getProject(), (Document)a);
    }

    /*
     * WARNING - void declaration
     */
    public void renderFunButtons(int n, Editor editor) {
        void a22;
        SessionController a = editor;
        SessionController a3 = this;
        a3.false = InlineChatStepEnum.SUCCESS;
        SessionController sessionController = a3;
        SessionController sessionController2 = a;
        InlineChatBtnPanelRenderer inlineChatBtnPanelRenderer = new InlineChatBtnPanelRenderer(sessionController.long, sessionController.break, (Disposable)a.getProject(), (Editor)sessionController2, (Function0<Unit>)((Function0)() -> a3.zb((Editor)sessionController2)), (Function0<Unit>)((Function0)() -> a3.Bc((Editor)a)), (Function0<Unit>)((Function0)() -> a3.Xa((Editor)a)), (Function0<Unit>)((Function0)() -> a3.cC((Editor)a)));
        a.getContentComponent().add(inlineChatBtnPanelRenderer);
        inlineChatBtnPanelRenderer.createInlay((int)a22);
        String a22 = InlineChatService.getVirtualFile((Editor)a).getUrl();
        EditorKt.rendererCollection.put(a22, inlineChatBtnPanelRenderer);
    }

    public void setStartOffset(int n) {
        int a = n;
        SessionController a2 = this;
        a2.byte = a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleOperation(@NotNull Editor editor, CommandEnum commandEnum) {
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController editor2 = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(15);
        }
        Class<SessionController> clazz = SessionController.class;
        synchronized (SessionController.class) {
            void a;
            boolean bl = a == CommandEnum.DIALOG_ACCEPT;
            if (!EditorKt.containEditor((Editor)sessionController2)) {
                // ** MonitorExit[var3_3] (shouldn't be in output)
                return;
            }
            SessionController sessionController3 = sessionController2;
            EditorKt.removeEditor((Editor)sessionController3);
            AutoCodeGenerateListener.inlineChatOperate.set(true);
            EditorKt.closeButtonPanel((Editor)sessionController3);
            if (editor2.getInlineChatOperateEnum() != null) {
                SessionController sessionController4 = editor2;
                InlineChatOperateEnum inlineChatOperateEnum = sessionController4.long;
                sessionController4.setInlineChatOperateEnum(null);
                if (a != null) {
                    editor2.fB((Editor)sessionController2, bl, inlineChatOperateEnum);
                }
            }
            EditorManagerService.getInstance().disposeTips((Editor)sessionController2, OperateActionEnum.Typing);
            if (a != null && bl) {
                editor2.NA((Editor)sessionController2, CommandEnum.DIALOG_ACCEPT);
            }
            // ** MonitorExit[var3_3] (shouldn't be in output)
            return;
        }
    }

    public void setLineIndent(String string) {
        String a = string;
        SessionController a2 = this;
        a2.class = a;
    }

    /*
     * WARNING - void declaration
     */
    private void Qa(ChatMessage chatMessage, @NotNull Editor editor) {
        void message;
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(5);
        }
        ApplicationManager.getApplication().runReadAction(() -> a.Ea((Editor)sessionController2, (ChatMessage)message));
    }

    public void setToHandleRangeHighlighterMap(Map<Integer, RangeHighlighter> map) {
        Map<Integer, RangeHighlighter> a = map;
        SessionController a2 = this;
        a2.final = a;
    }

    public void setEndLineNumber(int n) {
        int a = n;
        SessionController a2 = this;
        a2.catch = a;
    }

    /*
     * WARNING - void declaration
     */
    public void renderErrorFunButtons(int n2, Editor editor, String string) {
        void a22;
        void a;
        Object a3 = string;
        SessionController a4 = this;
        a4.false = InlineChatStepEnum.ERROR;
        void v0 = a;
        a3 = new InlineChatErrorPanelRenderer(a4.break, (Disposable)a.getProject(), (Editor)v0, (String)a3, (Function0<Unit>)((Function0)() -> a4.tB((Editor)v0)), (Function0<Unit>)((Function0)() -> a4.sC((Editor)a)));
        a.getContentComponent().add((Component)a3);
        ((InlineChatErrorPanelRenderer)a3).createInlay((int)a22);
        String a22 = InlineChatService.getVirtualFile((Editor)a).getUrl();
        EditorKt.rendererCollection.put(a22, a3);
    }

    public void setDefaultInlay(Inlay<?> inlay) {
        Inlay<?> a = inlay;
        Inlay<?> a2 = this;
        a2.new = a;
    }

    public void setInlineChatCategoryEnum(InlineChatCategoryEnum inlineChatCategoryEnum) {
        Object a = inlineChatCategoryEnum;
        SessionController a2 = this;
        a2.try = a;
    }

    /*
     * WARNING - void declaration
     */
    private void FC(@NotNull Editor editor, CommandEnum commandEnum) {
        void a;
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController editor2 = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(14);
        }
        ApplicationManager.getApplication().invokeLater(() -> editor2.nB((Editor)sessionController2, (CommandEnum)a));
    }

    private /* synthetic */ Unit tB(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        a2.doCancel((Editor)a);
        return Unit.INSTANCE;
    }

    public void setHandleOffset(int n2) {
        int a = n2;
        SessionController a2 = this;
        a2.enum = a;
    }

    public Inlay<?> getDefaultInlay() {
        SessionController a;
        return a.new;
    }

    public static boolean isAccept() {
        return float;
    }

    private synchronized void vB(@NotNull Editor editor) {
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController a = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(9);
        }
        SessionController sessionController3 = sessionController2;
        EditorKt.removeEditor((Editor)sessionController3);
        EditorKt.closeButtonPanel((Editor)sessionController3);
        EditorManagerService.getInstance().disposeTips((Editor)sessionController2, OperateActionEnum.Applied);
    }

    public void doCancel(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        ApplicationManager.getApplication().invokeLater(() -> a2.ua((Editor)a));
    }

    public void setInlineChatStepEnum(InlineChatStepEnum inlineChatStepEnum) {
        Object a = inlineChatStepEnum;
        SessionController a2 = this;
        a2.false = a;
    }

    public int getHandleOffset() {
        SessionController a;
        return a.enum;
    }

    public RangeHighlighter getRangeHighlighter() {
        SessionController a;
        return a.int;
    }

    public void setInsertStartOffset(int n2) {
        int a = n2;
        SessionController a2 = this;
        a2.super = a;
    }

    private void NA(@NotNull Editor editor, CommandEnum commandEnum) {
        SessionController sessionController = sessionController2;
        SessionController sessionController2 = editor;
        SessionController editor2 = sessionController;
        if (sessionController2 == null) {
            SessionController.enum(17);
        }
    }

    /*
     * WARNING - void declaration
     */
    private void nc(ChatMessage chatMessage, @NotNull Editor editor, VirtualFile virtualFile) {
        void message;
        CodeInfoDto.RangeDTO rangeDTO;
        CodeInfoDto.RangeDTO a;
        void editor2;
        SessionController sessionController = sessionController2;
        if (editor2 == null) {
            SessionController.enum(4);
        }
        SessionController sessionController2 = editor2.getDocument();
        SessionController sessionController3 = sessionController;
        int n2 = sessionController2.getLineNumber(sessionController3.byte);
        SessionController sessionController4 = sessionController;
        int n3 = sessionController4.byte - sessionController2.getLineStartOffset(n2);
        int n4 = sessionController2.getLineNumber(sessionController3.char);
        int this32 = sessionController4.char - sessionController2.getLineStartOffset(n4);
        MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.INLINECHAT_CATEGORY.getType());
        messageDto.setPath(a.getPath());
        CodeInfoDto.RangeDTO rangeDTO2 = a = new CodeInfoDto.RangeDTO();
        rangeDTO2.setLine(n2);
        rangeDTO2.setCharacter(n3);
        CodeInfoDto.RangeDTO rangeDTO3 = rangeDTO = new CodeInfoDto.RangeDTO();
        rangeDTO3.setLine(n4);
        rangeDTO3.setCharacter(this32);
        ArrayList<CodeInfoDto.RangeDTO> this32 = new ArrayList<CodeInfoDto.RangeDTO>();
        this32.add(a);
        this32.add(rangeDTO);
        MessageDto messageDto2 = messageDto;
        messageDto.setRange(this32);
        messageDto2.setData(message.getQuestion());
        messageDto2.setOtherObject(sessionController);
        messageDto.setInlineChatVersion((Integer)editor2.getUserData(InlineChatCommandService.VERSION_KEY));
        PluginWebsocketClient.sendWsMessage(messageDto, editor2.getProject());
        ApplicationManager.getApplication().invokeLater(() -> sessionController.JB((Editor)editor2));
    }

    private static /* synthetic */ List JC(Editor editor, Integer n, Integer n2) {
        CodeInfoDto.RangeDTO rangeDTO;
        Document a32;
        Integer a22 = n2;
        Editor a = editor;
        ArrayList<CodeInfoDto.RangeDTO> arrayList = new ArrayList<CodeInfoDto.RangeDTO>();
        Editor editor2 = a;
        int n3 = editor2.getSelectionModel().getSelectionStart();
        int n4 = editor2.getSelectionModel().getSelectionEnd();
        if (a32 != null && a22 != null) {
            n3 = a32.intValue();
            n4 = a22;
        }
        a32 = a.getDocument();
        int a22 = a32.getLineNumber(n3);
        Document document = a32;
        int n5 = document.getLineNumber(n4);
        int a32 = n4 - a32.getLineStartOffset(n5);
        CodeInfoDto.RangeDTO rangeDTO2 = rangeDTO = new CodeInfoDto.RangeDTO();
        rangeDTO2.setLine(a22);
        rangeDTO2.setCharacter(n3 -= document.getLineStartOffset(a22));
        CodeInfoDto.RangeDTO rangeDTO3 = a22 = new CodeInfoDto.RangeDTO();
        rangeDTO3.setLine(n5);
        rangeDTO3.setCharacter(a32);
        ArrayList<CodeInfoDto.RangeDTO> arrayList2 = arrayList;
        arrayList.add(rangeDTO);
        arrayList2.add(a22);
        return arrayList2;
    }

    public String getTipText() {
        SessionController a;
        return a.true;
    }

    private /* synthetic */ Unit Bc(Editor editor) {
        SessionController a = editor;
        SessionController a2 = this;
        a2.doReject((Editor)a);
        return Unit.INSTANCE;
    }

    public void setInlineChatOperateEnum(InlineChatOperateEnum inlineChatOperateEnum) {
        Object a = inlineChatOperateEnum;
        SessionController a2 = this;
        a2.long = a;
    }

    /*
     * WARNING - void declaration
     */
    private void Jc(@NotNull Editor editor) {
        int n2;
        int n3;
        void a;
        SessionController sessionController = this;
        if (a == null) {
            SessionController.enum(16);
        }
        if (sessionController.long == InlineChatOperateEnum.INSERT) {
            SessionController sessionController2 = sessionController;
            n3 = sessionController2.super;
            n2 = sessionController2.super;
        } else {
            SessionController sessionController3 = sessionController;
            n2 = sessionController3.char;
            n3 = sessionController3.byte;
        }
        void v2 = a;
        new DiffService().openInlineChatDiff((Editor)v2, sessionController.true, v2.getDocument(), n3, n2);
    }

    public int getStartOffset() {
        SessionController a;
        return a.byte;
    }
}
