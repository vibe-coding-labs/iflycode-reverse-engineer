/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.difflib.text.DiffRow
 *  com.github.difflib.text.DiffRow$Tag
 *  com.github.difflib.text.DiffRowGenerator
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.command.WriteCommandAction
 *  com.intellij.openapi.editor.CaretModel
 *  com.intellij.openapi.editor.Document
 *  com.intellij.openapi.editor.Editor
 *  com.intellij.openapi.editor.markup.HighlighterTargetArea
 *  com.intellij.openapi.editor.markup.MarkupModel
 *  com.intellij.openapi.editor.markup.RangeHighlighter
 *  com.intellij.openapi.editor.markup.TextAttributes
 *  com.intellij.openapi.fileEditor.FileDocumentManager
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.util.text.StringUtil
 *  com.intellij.psi.PsiDocumentManager
 *  com.intellij.psi.PsiFile
 *  com.intellij.ui.JBColor
 *  org.jetbrains.annotations.NotNull
 */
package com.aicode.inline;

import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.ResponseStreamDto;
import com.aicode.agent.service.CommonService;
import com.aicode.content.util.EditorUtils;
import com.aicode.inline.controller.SessionController;
import com.aicode.inline.dto.InlineChatInfo;
import com.aicode.inline.enums.InlineChatCategoryEnum;
import com.aicode.inline.enums.InlineChatOperateEnum;
import com.aicode.listener.AutoCodeGenerateListener;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.util.EditorKt;
import com.aicode.util.StringUtils;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.ui.JBColor;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class InlineChatStreamHandleService {
    private static TextAttributes byte;
    public static volatile boolean HANDING_DATA;
    private static TextAttributes enum;
    public static TextAttributes toHandleAttributes;
    public static TextAttributes highLightAttributes;

    /*
     * WARNING - void declaration
     */
    private static void sb(SessionController sessionController, Editor editor, int n) {
        int a = n;
        SessionController a2 = sessionController;
        if (a2 == null) {
            return;
        }
        if (a2.getToHandleRangeHighlighterMap().containsKey(a)) {
            void a3;
            a3.getMarkupModel().removeHighlighter(a2.getToHandleRangeHighlighterMap().get(a));
            a2.getToHandleRangeHighlighterMap().remove(a);
        }
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray = new Object[2];
        objectArray[0] = CancelRequestTip.H("\u00138:_\u0011\u001f\u0015\u000e\u0005\u0000J\u0000\u0007 %\u000b\u0000E#\u000e\f\u0001\u0006\u0004\"?6\u00152\u0002\u0004\u0017\u0013\u0000%\u0006\t\u0005\r\u00042\u000f\u0018\u0007\u0018\n\f");
        objectArray[1] = EditorUtils.H("=`*I5b\"x*");
        throw new IllegalStateException(String.format(EditorUtils.H("R\u0007h(n\u000ek0!7s9z&ze\u001e\u0013<lnfz9l06#O\u000f6?d.p,ta~>z!"), objectArray));
    }

    public static void handleErrorData(SessionController sessionController, String string) {
        String a = string;
        SessionController a2 = sessionController;
        ApplicationManager.getApplication().invokeLater(() -> {
            SessionController a;
            String a2 = a;
            SessionController sessionController2 = a = a2;
            sessionController2.renderErrorFunButtons(a.getStartOffset(), sessionController2.getEditor(), a2);
        });
    }

    public static void saveDocument(Project project, Document document) {
        Project a = document;
        Project a2 = project;
        FileDocumentManager.getInstance().saveDocument((Document)a);
        PsiDocumentManager.getInstance((Project)a2).commitDocument((Document)a);
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void rc(ResponseStreamDto responseStreamDto, SessionController sessionController, String string, Editor editor) {
        SessionController a = sessionController;
        ResponseStreamDto a2 = responseStreamDto;
        if (a2.getData().isEnded()) {
            void a3;
            void a222;
            Object object = a;
            InlineChatStreamHandleService.Tc((SessionController)object);
            PluginWebsocketClient.AGENT_REQUEST.remove(a222);
            int a222 = ((SessionController)object).getInlineChatOperateEnum() == InlineChatOperateEnum.INSERT ? a.getInsertStartOffset() : a.getStartOffset();
            a.renderFunButtons(a222, (Editor)a3);
        }
    }

    /*
     * WARNING - void declaration
     */
    private static void eA(SessionController sessionController, InlineChatInfo inlineChatInfo, Editor editor, Document document) {
        void a;
        String string;
        void a2;
        SessionController sessionController2 = sessionController;
        a2.getMarkupModel().removeAllHighlighters();
        int a3 = sessionController2.getHandleOffset();
        Object a4 = a3.getContent();
        if (InlineChatCategoryEnum.DOC == sessionController2.getInlineChatCategoryEnum() && (string = sessionController2.getLineIndent()).length() > 0) {
            a4 = CommonService.addLineIndent((String)a4, string);
        }
        a4 = ((String)a4).endsWith(EditorUtils.H("G")) ? a4 : (String)a4 + "\n";
        sessionController2.setTipText((String)a4);
        HANDING_DATA = true;
        WriteCommandAction.runWriteCommandAction((Project)a2.getProject(), () -> InlineChatStreamHandleService.sc((Document)a, sessionController2, a3));
        WriteCommandAction.runWriteCommandAction((Project)a2.getProject(), () -> InlineChatStreamHandleService.hC((Document)a, sessionController2, (Editor)a2));
        HANDING_DATA = false;
    }

    @NotNull
    private static String gB(String a) {
        if ((a = a.replaceAll(EditorUtils.H("v-@SJ:*s:\u0002t~l+v-"), "").replaceAll(CancelRequestTip.H("1\u001f;\t\u001d=\u0011\u001f\u001a("), EditorUtils.H("G"))) == null) {
            InlineChatStreamHandleService.enum(0);
        }
        return a;
    }

    /*
     * WARNING - void declaration
     */
    private static void ka(SessionController sessionController, InlineChatInfo inlineChatInfo, Editor editor, Document document, CaretModel caretModel, int n, boolean bl) {
        void a;
        void a2;
        void a3;
        void a4;
        void a5;
        SessionController a6 = editor;
        SessionController a7 = sessionController;
        WriteCommandAction.runWriteCommandAction((Project)a6.getProject(), () -> InlineChatStreamHandleService.Ua(a7, (Document)a5, (int)a4, (CaretModel)a3, (Editor)a6, (InlineChatInfo)a2, (boolean)a));
    }

    public InlineChatStreamHandleService() {
        InlineChatStreamHandleService a;
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void Ua(SessionController sessionController, Document document, int n, CaretModel caretModel, Editor editor, InlineChatInfo inlineChatInfo, boolean bl) {
        void a;
        void a2;
        void a3;
        int n2;
        void a4;
        SessionController a5 = document;
        SessionController a6 = sessionController;
        AutoCodeGenerateListener.ignoreApply.set(true);
        int n3 = a6.getHandleOffset();
        int n4 = a5.getLineNumber(n3);
        if (a4 != false) {
            void a7;
            int n5 = a5.getLineCount();
            boolean bl2 = n5 <= n4 + 1;
            if (bl2) {
                SessionController sessionController2 = a5;
                sessionController2.insertString(sessionController2.getLineEndOffset(n4), CancelRequestTip.H("o"));
            }
            n2 = a5.getLineStartOffset(n4 + 1);
            a7.moveToOffset(n2);
            a6.setHandleOffset(n2);
            n3 = a6.getHandleOffset();
            n4 = a5.getLineNumber(n3);
        }
        InlineChatStreamHandleService.xB(a6, (Editor)a3);
        Object object = a2.getLineList().get((int)a4);
        if (InlineChatCategoryEnum.DOC == a6.getInlineChatCategoryEnum() && StringUtils.isNotBlank((CharSequence)object)) {
            object = a6.getLineIndent() + (String)object;
        }
        if (a == false) {
            a2.getHandleLineIndex().incrementAndGet();
        }
        HANDING_DATA = true;
        a5.insertString(n3, (String)object + "\n");
        RangeHighlighter rangeHighlighter = a3.getMarkupModel().addRangeHighlighter(a5.getLineStartOffset(n4), a5.getLineEndOffset(n4), 6000, highLightAttributes, HighlighterTargetArea.LINES_IN_RANGE);
        a6.setRangeHighlighter(rangeHighlighter);
        if (a != false) {
            n2 = a5.getLineEndOffset(n4);
            a6.setHandleOffset(n2);
        }
        a6.setInlineChatOperateEnum(InlineChatOperateEnum.INSERT);
        InlineChatStreamHandleService.saveDocument(a3.getProject(), (Document)a5);
        HANDING_DATA = false;
    }

    /*
     * WARNING - void declaration
     */
    private static void OA(Editor editor, List<DiffRow> list, Document document, int n, SessionController sessionController) {
        void a;
        void a2;
        void a3;
        void a4;
        Editor editor2;
        Editor editor3 = editor2 = editor;
        Editor a5 = editor3.getMarkupModel();
        WriteCommandAction.runWriteCommandAction((Project)editor3.getProject(), (String)CancelRequestTip.H("\u0001\u0006\r\b92A\u0002\u001e\u0017\u0006R>\u0018\u0000\u0000\u0004\u0012?\"*+"), (String)EditorUtils.H("\u001a\u007f.n>`pt.d\"u("), () -> InlineChatStreamHandleService.La((int)a4, (List)a3, (Document)a2, (MarkupModel)a5, (SessionController)a, editor2), (PsiFile[])new PsiFile[0]);
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void hC(Document document, SessionController sessionController, Editor editor) {
        SessionController a22 = sessionController;
        Document a = document;
        AutoCodeGenerateListener.ignoreApply.set(true);
        SessionController sessionController2 = a22;
        a.insertString(a22.getInsertStartOffset(), (CharSequence)a22.getTipText());
        int a22 = a.getLineNumber(sessionController2.getInsertStartOffset());
        List<String> list = Arrays.asList(StringUtil.splitByLinesKeepSeparators((String)sessionController2.getTipText()));
        int n = a22 + list.size();
        int n2 = a22 = a22;
        while (n2 < n) {
            void a3;
            int n3 = a22++;
            a3.getMarkupModel().addLineHighlighter(n3, 6000, enum);
            n2 = a22;
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void handleData(String string, ResponseStreamDto responseStreamDto, MessageDto messageDto) {
        void a;
        InlineChatCategoryEnum a22;
        String string2 = string;
        Object object = ((MessageDto)((Object)a22)).getOtherObject();
        SessionController sessionController = (SessionController)object;
        if (object == null || !(object instanceof SessionController)) {
            return;
        }
        object = sessionController.getEditor();
        if (object == null) {
            return;
        }
        InlineChatInfo a3 = EditorKt.getInfoByEditor((Editor)object);
        if (a3 == null || !StringUtils.equals((CharSequence)a3.getRequestId(), (CharSequence)string2)) {
            PluginWebsocketClient.AGENT_REQUEST.remove(string2);
            return;
        }
        if (a.getData() == null) {
            return;
        }
        String string3 = InlineChatStreamHandleService.zA(a.getData(), a3);
        a22 = InlineChatCategoryEnum.getCategoryEnumByName(((MessageDto)((Object)a22)).getDirectName());
        Object object2 = object;
        Document document = object2.getDocument();
        CaretModel caretModel = object2.getCaretModel();
        if (!a3.isTrimPrefix()) {
            return;
        }
        if (StringUtils.isBlank((CharSequence)string3) && !a.getData().isEnded()) {
            return;
        }
        int n = a3.getHandleLineIndex().get();
        switch (a22) {
            case DOC: 
            case GENERATE: {
                while (false) {
                }
                if (!a.getData().isEnded()) {
                    if (a3.getLineList().size() <= n + 1) break;
                    InlineChatStreamHandleService.ka(sessionController, a3, (Editor)object, document, caretModel, n, false);
                    break;
                }
                int a22 = a3.getLineList().size();
                if (n < a22) {
                    int n2;
                    int n3 = n2 = n;
                    while (n3 < a22) {
                        int n4 = n2;
                        InlineChatStreamHandleService.ka(sessionController, a3, (Editor)object, document, caretModel, n4, n4 == a22 - 1);
                        n3 = ++n2;
                    }
                }
                InlineChatStreamHandleService.eA(sessionController, a3, (Editor)object, document);
                break;
            }
            case EDIT: 
            case LINEDOC: {
                if (!a.getData().isEnded()) {
                    if (a3.getLineList().size() <= n + 1) break;
                    InlineChatStreamHandleService.pB(sessionController, a3, (Editor)object, document, caretModel, n, false);
                    break;
                }
                int a22 = a3.getLineList().size();
                if (n < a22) {
                    int n5;
                    int n6 = n5 = n;
                    while (n6 < a22) {
                        int n7 = n5;
                        InlineChatStreamHandleService.pB(sessionController, a3, (Editor)object, document, caretModel, n7, n7 == a22 - 1);
                        n6 = ++n5;
                    }
                }
                InlineChatStreamHandleService.pc(sessionController, a3, (Editor)object, document, sessionController.getHandleOffset());
            }
        }
        ApplicationManager.getApplication().invokeAndWait(() -> InlineChatStreamHandleService.rc((ResponseStreamDto)a, sessionController, string2, (Editor)object));
    }

    private static void xB(SessionController sessionController, Editor editor) {
        SessionController a = editor;
        SessionController a2 = sessionController;
        if (a2 != null && a2.getRangeHighlighter() != null) {
            a.getMarkupModel().removeHighlighter(a2.getRangeHighlighter());
        }
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void Gc(SessionController sessionController, Document document, int n, Editor editor, InlineChatInfo inlineChatInfo, boolean bl, CaretModel caretModel) {
        void v4;
        void a;
        void a2;
        void a3;
        int n2;
        int n3;
        int a222;
        void a4;
        SessionController sessionController2 = sessionController;
        AutoCodeGenerateListener.ignoreApply.set(true);
        int n4 = sessionController2.getHandleOffset();
        int a5 = a4.getLineNumber(n4);
        if (a222 != 0) {
            n3 = a4.getLineCount();
            n2 = n3 <= a5 + 1 ? 1 : 0;
            if (n2 != 0) {
                void v0 = a4;
                v0.insertString(v0.getLineEndOffset(a5), (CharSequence)EditorUtils.H("G"));
            }
            int n5 = a4.getLineStartOffset(a5 + 1);
            sessionController2.setHandleOffset(n5);
            n4 = sessionController2.getHandleOffset();
            a5 = a4.getLineNumber(n4);
        }
        SessionController sessionController3 = sessionController2;
        void v2 = a3;
        InlineChatStreamHandleService.sb(sessionController3, (Editor)v2, a5);
        InlineChatStreamHandleService.xB(sessionController3, (Editor)v2);
        void v3 = a4;
        n3 = v3.getLineStartOffset(a5);
        n2 = v3.getLineEndOffset(a5);
        Object object = a2.getLineList().get(a222);
        object = sessionController2.getLineIndent() + (String)object;
        if (a == false) {
            a2.getHandleLineIndex().incrementAndGet();
        }
        HANDING_DATA = true;
        if (a5 > sessionController2.getEndLineNumber()) {
            a4.insertString(n4, (CharSequence)((String)object + "\n"));
            v4 = a;
        } else {
            void v5 = a4;
            int n6 = n3;
            v5.deleteString(n6, n2);
            v5.insertString(n6, (CharSequence)object);
            v4 = a;
        }
        if (v4 != false) {
            void a6;
            a222 = a4.getLineEndOffset(a5);
            a6.moveToOffset(a222);
            sessionController2.setHandleOffset(a222);
        }
        void v7 = a3;
        RangeHighlighter a222 = v7.getMarkupModel().addRangeHighlighter(a4.getLineStartOffset(a5), a4.getLineEndOffset(a5), 6000, highLightAttributes, HighlighterTargetArea.LINES_IN_RANGE);
        sessionController2.setRangeHighlighter(a222);
        InlineChatStreamHandleService.saveDocument(v7.getProject(), (Document)a4);
        HANDING_DATA = false;
    }

    /*
     * WARNING - void declaration
     */
    private static void pB(SessionController sessionController, InlineChatInfo inlineChatInfo, Editor editor, Document document, CaretModel caretModel, int n, boolean bl) {
        void a;
        void a2;
        void a3;
        void a4;
        void a5;
        SessionController a6 = editor;
        SessionController a7 = sessionController;
        WriteCommandAction.runWriteCommandAction((Project)a6.getProject(), () -> InlineChatStreamHandleService.Gc(a7, (Document)a5, (int)a4, (Editor)a6, (InlineChatInfo)a3, (boolean)a2, (CaretModel)a));
    }

    /*
     * WARNING - void declaration
     */
    private static void pc(SessionController sessionController, InlineChatInfo inlineChatInfo, Editor editor, Document document, int n) {
        boolean bl;
        void a2;
        void a3;
        SessionController a4;
        Object a5;
        block4: {
            int a3222;
            a5 = inlineChatInfo;
            a4 = sessionController;
            a3.getMarkupModel().removeAllHighlighters();
            a3222 = a3222 > a4.getStartOffset() ? a3222 : a4.getEndOffset();
            HANDING_DATA = true;
            WriteCommandAction.runWriteCommandAction((Project)a3.getProject(), () -> InlineChatStreamHandleService.Hc((Document)a2, a4, a3222));
            HANDING_DATA = false;
            SessionController sessionController2 = a4;
            Object a3222 = sessionController2.getOriginalSelectText();
            a5 = ((InlineChatInfo)a5).getContent();
            Object object = sessionController2.getLineIndent();
            if (((String)object).length() > 0) {
                a5 = CommonService.addLineIndent((String)a5, (String)object);
            }
            Object object2 = a5 = ((String)a5).endsWith(CancelRequestTip.H("m")) ? a5 : (String)a5 + "\n";
            if (StringUtils.isNotBlank((CharSequence)a3222) && !((String)a3222).endsWith(EditorUtils.H("G"))) {
                List<String> list = a5;
                a5 = ((String)((Object)list)).substring(0, ((String)((Object)list)).length() - 1);
            }
            a4.setTipText((String)a5);
            a5 = Arrays.asList(StringUtil.splitByLinesKeepSeparators((String)a5));
            a3222 = Arrays.asList(StringUtil.splitByLinesKeepSeparators((String)a3222));
            a5 = DiffRowGenerator.create().showInlineDiffs(false).inlineDiffByWord(true).oldTag(a -> CancelRequestTip.H(";")).newTag(a -> EditorUtils.H("<g")).lineNormalizer(a -> a).build().generateDiffRows((List)a3222, (List)a5);
            boolean a3222 = false;
            object = a5.iterator();
            while (object.hasNext()) {
                DiffRow diffRow = (DiffRow)object.next();
                if (DiffRow.Tag.EQUAL == diffRow.getTag()) continue;
                bl = a3222 = true;
                break block4;
            }
            bl = a3222;
        }
        if (bl) {
            SessionController sessionController3 = a4;
            sessionController3.setInlineChatOperateEnum(InlineChatOperateEnum.EDIT);
            int n2 = sessionController3.getStartOffset();
            int n3 = a2.getLineNumber(n2);
            InlineChatStreamHandleService.OA((Editor)a3, (List<DiffRow>)a5, (Document)a2, n3, a4);
        }
    }

    static {
        HANDING_DATA = false;
        byte = InlineChatStreamHandleService.tb(new Color(240, 20, 20, 20));
        enum = InlineChatStreamHandleService.tb(new Color(120, 254, 200, 50));
        highLightAttributes = InlineChatStreamHandleService.tb(new Color(120, 120, 120, 100));
        toHandleAttributes = InlineChatStreamHandleService.tb(new Color(120, 120, 120, 30));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void sc(Document document, SessionController sessionController, int n) {
        void a;
        int a2 = n;
        Document a3 = document;
        AutoCodeGenerateListener.ignoreApply.set(true);
        a3.replaceString(a.getInsertStartOffset(), a2 + 1, (CharSequence)"");
    }

    private static TextAttributes tb(Color color) {
        Color a;
        Color color2 = color;
        Color color3 = a = new TextAttributes();
        Color color4 = color2;
        color3.setBackgroundColor((Color)new JBColor(color4, color4));
        return color3;
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void Hc(Document document, SessionController sessionController, int n) {
        void a;
        SessionController a2 = sessionController;
        Document a3 = document;
        AutoCodeGenerateListener.ignoreApply.set(true);
        a3.replaceString(a2.getStartOffset(), (int)a, (CharSequence)a2.getOriginalSelectText());
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static /* synthetic */ void La(int n, List list, Document document, MarkupModel markupModel, SessionController sessionController, Editor editor) {
        int n2 = n;
        AutoCodeGenerateListener.ignoreApply.set(true);
        try {
            void a;
            void a2;
            void a3;
            void a4;
            int n3;
            HANDING_DATA = true;
            int n4 = n2;
            int n5 = 0;
            int n6 = n3 = 0;
            while (n6 < a4.size()) {
                DiffRow diffRow = (DiffRow)a4.get(n3);
                Object a5 = diffRow.getNewLine();
                if (n3 == a4.size() - 1 && !((String)a5).endsWith(CancelRequestTip.H("o"))) {
                    a5 = (String)a5 + "\n";
                }
                int n7 = a3.getLineStartOffset(n4);
                switch (diffRow.getTag()) {
                    case EQUAL: {
                        break;
                    }
                    case DELETE: {
                        void a6;
                        a6.addLineHighlighter(n4, 6000, byte);
                        break;
                    }
                    case INSERT: {
                        void a6;
                        a3.insertString(n7, (CharSequence)a5);
                        n5 += ((String)a5).length();
                        a6.addLineHighlighter(n4, 6000, enum);
                        break;
                    }
                    case CHANGE: {
                        void a6;
                        int n8 = n4++;
                        a6.addLineHighlighter(n8, 6000, byte);
                        void v2 = a3;
                        n7 = v2.getLineStartOffset(n4);
                        v2.insertString(n7, (CharSequence)a5);
                        n5 += ((String)a5).length();
                        a6.addLineHighlighter(n4, 6000, enum);
                        break;
                    }
                }
                ++n4;
                n6 = ++n3;
            }
            a2.setChangeLength(n5);
            HANDING_DATA = false;
            InlineChatStreamHandleService.saveDocument(a.getProject(), (Document)a3);
            return;
        }
        catch (Throwable throwable) {
            HANDING_DATA = false;
            return;
        }
    }

    private static void Tc(SessionController a) {
        ApplicationManager.getApplication().invokeLater(() -> {
            try {
                EditorKt.closeStopPanel(a.getEditor());
                return;
            }
            catch (Throwable throwable) {
                return;
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    private static String zA(ResponseStreamDto.ResponseData responseData, InlineChatInfo inlineChatInfo) {
        void a;
        ResponseStreamDto.ResponseData responseData2 = responseData;
        if (Objects.isNull(responseData2)) {
            return null;
        }
        if (responseData2.isEnded()) {
            void v0 = a;
            v0.setContent(InlineChatStreamHandleService.gB(v0.getContent()));
            return null;
        }
        Object a2 = responseData2.getText();
        if (StringUtils.isBlank((CharSequence)a2)) {
            return null;
        }
        String string = a.getContent() + (String)a2;
        void v1 = a;
        v1.setContent(string);
        if (!v1.isTrimPrefix()) {
            Matcher matcher = Pattern.compile(CancelRequestTip.H("\u0001\u0001\u0016^.\u0005FDX;\u000f^\u0002\u001e%%")).matcher(string);
            if (string.contains(EditorUtils.H("G")) && matcher.find()) {
                a.setTrimPrefix(true);
                a2 = InlineChatStreamHandleService.gB(string);
                if (((String)a2).startsWith(CancelRequestTip.H("OO"))) {
                    a2 = ((String)a2).stripLeading();
                }
                void v2 = a;
                v2.setContent((String)a2);
                v2.setLineList();
                return a2;
            }
        } else {
            void v3 = a;
            v3.setContent(InlineChatStreamHandleService.gB(v3.getContent()));
            v3.setLineList();
            a2 = InlineChatStreamHandleService.gB((String)a2);
            return a2;
        }
        return null;
    }
}
