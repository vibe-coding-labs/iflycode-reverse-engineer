package com.aicode.inline;

import com.aicode.action.batch.GeneratorConfig;
import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.service.InlineChatCommandService;
import com.aicode.enums.FileExtensionEnum;
import com.aicode.inline.controller.SessionController;
import com.aicode.inline.enums.InlineChatCategoryEnum;
import com.aicode.inline.enums.InlineChatOperateEnum;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.EditorKt;
import com.aicode.util.StringUtils;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import com.google.gson.JsonObject;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.ui.JBColor;
import java.awt.Color;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/* compiled from: oj */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/InlineChatHandleService.class */
public class InlineChatHandleService {
    public static volatile boolean HANDING_DATA = false;

    /* renamed from: byte, reason: not valid java name */
    private static TextAttributes f313byte = tb(new Color(240, 20, 20, 20));

    /* renamed from: enum, reason: not valid java name */
    private static TextAttributes f314enum = tb(new Color(120, 254, 200, 50));
    public static TextAttributes selectOriginalAttributes = tb(new Color(34, 66, 131, 50));

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean Hd(Editor a, String a2, List<CodeInfoDto.RangeDTO> list, Document a3, List<String> list2, SessionController a4) {
        String str;
        if (list != null) {
            Integer line = list.get(0).getLine();
            int lineStartOffset = a3.getLineStartOffset(line.intValue());
            String str2 = "";
            String str3 = "";
            int leadingWhitespaceLengthWithTab = AICodeStringUtil.leadingWhitespaceLengthWithTab(a.getDocument().getText(new TextRange(a.getDocument().getLineStartOffset(line.intValue()), a.getDocument().getLineEndOffset(line.intValue()))), a.getSettings().getTabSize(a.getProject()));
            if (leadingWhitespaceLengthWithTab > 0) {
                int i = 0;
                int i2 = 0;
                while (i < leadingWhitespaceLengthWithTab) {
                    i2++;
                    str3 = str3 + " ";
                    i = i2;
                }
                String[] split = a2.split(MethodGeneratorConfig.H("T"));
                int i3 = 0;
                int i4 = 0;
                while (i3 < split.length) {
                    String str4 = str2;
                    if (i4 == 0) {
                        str = str4 + str3 + split[i4];
                    } else {
                        str = str4 + "\n" + str3 + split[i4];
                    }
                    str2 = str;
                    i4++;
                    i3 = i4;
                }
            }
            String str5 = str2 + "\n";
            a4.setTipText(str5);
            a4.setInsertStartOffset(lineStartOffset);
            a4.setInlineChatOperateEnum(InlineChatOperateEnum.INSERT);
            Tf(a, str5, lineStartOffset, a3, line.intValue(), list2, true);
            return false;
        }
        return true;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void handleData(JsonObject a, MessageDto a2) {
        String replaceAll = ((String) Optional.ofNullable(a).filter(jsonObject -> {
            return a.has(MethodGeneratorConfig.H("\u0015\u001e%>"));
        }).map(jsonObject2 -> {
            return a.get(GeneratorConfig.H("\f\u001f\u001a\u0019")).getAsString();
        }).orElseGet(String::new)).replaceAll(MethodGeneratorConfig.H(":40v\u0004!zvO\";d<.5;"), "").replaceAll(GeneratorConfig.H("%\u001d!\u0005\u001e(\u001a\u00022\u0016"), MethodGeneratorConfig.H("Q"));
        Object otherObject = a2.getOtherObject();
        if (otherObject != null && (otherObject instanceof SessionController)) {
            SessionController sessionController = (SessionController) otherObject;
            Tc(sessionController);
            if (!StringUtils.isBlank(replaceAll) && !sessionController.stop) {
                handleData(sessionController, replaceAll.endsWith(GeneratorConfig.H("r")) ? replaceAll : replaceAll + "\n", a2);
            } else if (((Integer) sessionController.getEditor().getUserData(InlineChatCommandService.VERSION_KEY)).intValue() == a2.getInlineChatVersion()) {
                EditorKt.removeEditor(sessionController.getEditor());
            }
        }
    }

    public static void handleErrorData(SessionController a, String a2) {
        ApplicationManager.getApplication().invokeLater(() -> {
            a.renderErrorFunButtons(a.getStartOffset(), a.getEditor(), a2);
        });
    }

    private static void Tc(SessionController a) {
        ApplicationManager.getApplication().invokeLater(() -> {
            try {
                Editor editor = a.getEditor();
                EditorKt.closeStopPanel(editor);
                editor.getMarkupModel().removeAllHighlighters();
            } catch (Throwable unused) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: oj */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/InlineChatHandleService$z.class */
    public static /* synthetic */ class z {

        /* renamed from: byte, reason: not valid java name */
        public static final /* synthetic */ int[] f315byte;

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        static {
            try {
                f316enum[InlineChatCategoryEnum.DOC.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f316enum[InlineChatCategoryEnum.GENERATE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f316enum[InlineChatCategoryEnum.EDIT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f316enum[InlineChatCategoryEnum.LINEDOC.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            f315byte = new int[DiffRow.Tag.values().length];
            try {
                f315byte[DiffRow.Tag.EQUAL.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f315byte[DiffRow.Tag.DELETE.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f315byte[DiffRow.Tag.INSERT.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f315byte[DiffRow.Tag.CHANGE.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    private static void dF(SessionController a, String a2, Editor a3, int a4, Document a5, int a6, List<String> list) {
        int lineNumber = a5.getLineNumber(a4);
        int i = a4;
        int i2 = a6;
        if (StringUtils.isNotBlank(a5.getText(new TextRange(a5.getLineStartOffset(lineNumber), a5.getLineEndOffset(lineNumber))))) {
            i = a4 + 1;
            i2 = a6 + 1;
        }
        a.setInsertStartOffset(i);
        a.setInlineChatOperateEnum(InlineChatOperateEnum.INSERT);
        Tf(a3, a2, i, a5, i2, list, false);
    }

    public static void handleData(SessionController a, String a2, MessageDto a3) {
        InlineChatCategoryEnum categoryEnumByName = InlineChatCategoryEnum.getCategoryEnumByName(a3.getDirectName());
        ApplicationManager.getApplication().invokeLater(() -> {
            SessionController sessionController;
            boolean z2;
            List list;
            Editor editor;
            try {
                Editor editor2 = a.getEditor();
                a.setTipText(a2);
                a.setInlineChatCategoryEnum(categoryEnumByName);
                int startOffset = a.getStartOffset();
                int endOffset = a.getEndOffset();
                Document document = editor2.getDocument();
                int lineNumber = document.getLineNumber(startOffset);
                int lineNumber2 = document.getLineNumber(endOffset);
                List asList = Arrays.asList(StringUtil.splitByLinesKeepSeparators(a2));
                switch (categoryEnumByName) {
                    case DOC:
                        do {
                        } while (0 != 0);
                        if (FileExtensionEnum.PYTHON_LANGUAGE_01.getSuffix().equals(((EditorImpl) editor2).getVirtualFile().getExtension())) {
                            list = (List) editor2.getUserData(InlineChatCommandService.BODY_RANGE_KEY);
                            editor = editor2;
                        } else {
                            list = (List) editor2.getUserData(InlineChatCommandService.RANGE_KEY);
                            editor = editor2;
                        }
                        if (Hd(editor, a2, list, document, asList, a)) {
                            return;
                        }
                        sessionController = a;
                        int insertStartOffset = sessionController.getInlineChatOperateEnum() == InlineChatOperateEnum.INSERT ? a.getInsertStartOffset() : startOffset;
                        a.renderFunButtons(insertStartOffset, editor2);
                        CommandProcessor.getInstance().runUndoTransparentAction(() -> {
                            editor2.getCaretModel().moveToOffset(insertStartOffset);
                        });
                        return;
                    case GENERATE:
                        sessionController = a;
                        dF(sessionController, a2, editor2, endOffset, document, lineNumber2, asList);
                        int insertStartOffset2 = sessionController.getInlineChatOperateEnum() == InlineChatOperateEnum.INSERT ? a.getInsertStartOffset() : startOffset;
                        a.renderFunButtons(insertStartOffset2, editor2);
                        CommandProcessor.getInstance().runUndoTransparentAction(() -> {
                            editor2.getCaretModel().moveToOffset(insertStartOffset2);
                        });
                        return;
                    case EDIT:
                    case LINEDOC:
                        if (!a.isHasSelect()) {
                            sessionController = a;
                            dF(sessionController, a2, editor2, endOffset, document, lineNumber2, asList);
                            int insertStartOffset22 = sessionController.getInlineChatOperateEnum() == InlineChatOperateEnum.INSERT ? a.getInsertStartOffset() : startOffset;
                            a.renderFunButtons(insertStartOffset22, editor2);
                            CommandProcessor.getInstance().runUndoTransparentAction(() -> {
                                editor2.getCaretModel().moveToOffset(insertStartOffset22);
                            });
                            return;
                        }
                        String originalSelectText = a.getOriginalSelectText();
                        if (StringUtils.isNotBlank(originalSelectText) && !originalSelectText.endsWith(MethodGeneratorConfig.H("u"))) {
                            String substring = a2.substring(0, a2.length() - 1);
                            a.setTipText(substring);
                            asList = Arrays.asList(StringUtil.splitByLinesKeepSeparators(substring));
                        }
                        List generateDiffRows = DiffRowGenerator.create().showInlineDiffs(false).inlineDiffByWord(true).oldTag(a4 -> {
                            return MethodGeneratorConfig.H("��");
                        }).newTag(a5 -> {
                            return GeneratorConfig.H("DR");
                        }).lineNormalizer(a6 -> {
                            return a6;
                        }).build().generateDiffRows(Arrays.asList(StringUtil.splitByLinesKeepSeparators(originalSelectText)), asList);
                        Iterator it = generateDiffRows.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z2 = false;
                            } else {
                                if (DiffRow.Tag.EQUAL != ((DiffRow) it.next()).getTag()) {
                                    z2 = true;
                                }
                            }
                        }
                        if (z2) {
                            a.setInlineChatOperateEnum(InlineChatOperateEnum.EDIT);
                            OA(editor2, generateDiffRows, document, lineNumber, a);
                        }
                        sessionController = a;
                        int insertStartOffset222 = sessionController.getInlineChatOperateEnum() == InlineChatOperateEnum.INSERT ? a.getInsertStartOffset() : startOffset;
                        a.renderFunButtons(insertStartOffset222, editor2);
                        CommandProcessor.getInstance().runUndoTransparentAction(() -> {
                            editor2.getCaretModel().moveToOffset(insertStartOffset222);
                        });
                        return;
                    default:
                        sessionController = a;
                        int insertStartOffset2222 = sessionController.getInlineChatOperateEnum() == InlineChatOperateEnum.INSERT ? a.getInsertStartOffset() : startOffset;
                        a.renderFunButtons(insertStartOffset2222, editor2);
                        CommandProcessor.getInstance().runUndoTransparentAction(() -> {
                            editor2.getCaretModel().moveToOffset(insertStartOffset2222);
                        });
                        return;
                }
            } catch (Throwable th) {
                EditorKt.removeEditor(a.getEditor());
            }
        });
    }

    private static TextAttributes tb(Color a) {
        TextAttributes textAttributes = new TextAttributes();
        textAttributes.setBackgroundColor(new JBColor(a, a));
        return textAttributes;
    }

    private static void OA(Editor a, List<DiffRow> list, Document a2, int a3, SessionController a4) {
        MarkupModel markupModel = a.getMarkupModel();
        WriteCommandAction.runWriteCommandAction(a.getProject(), GeneratorConfig.H("\f\u001d\u0003\u0010\t\u0014N\u001b0/\u001aX*\u001a\u001a\f\u0007\u0007\u001c\u0017\u0001\u0016"), MethodGeneratorConfig.H(")+/272w?0\u000f\u001c\u0016\u001e"), () -> {
            try {
                HANDING_DATA = true;
                int i = a3;
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                while (i3 < list.size()) {
                    DiffRow diffRow = (DiffRow) list.get(i4);
                    String newLine = diffRow.getNewLine();
                    if (i4 == list.size() - 1 && !newLine.endsWith(GeneratorConfig.H("r"))) {
                        newLine = newLine + "\n";
                    }
                    int lineStartOffset = a2.getLineStartOffset(i);
                    switch (z.f315byte[diffRow.getTag().ordinal()]) {
                        case 2:
                            do {
                            } while (0 != 0);
                            markupModel.addLineHighlighter(i, 6000, f313byte);
                            break;
                        case 3:
                            a2.insertString(lineStartOffset, newLine);
                            i2 += newLine.length();
                            markupModel.addLineHighlighter(i, 6000, f314enum);
                            break;
                        case 4:
                            int i5 = i;
                            i++;
                            markupModel.addLineHighlighter(i5, 6000, f313byte);
                            a2.insertString(a2.getLineStartOffset(i), newLine);
                            i2 += newLine.length();
                            markupModel.addLineHighlighter(i, 6000, f314enum);
                            break;
                    }
                    i++;
                    i4++;
                    i3 = i4;
                }
                a4.setChangeLength(i2);
                HANDING_DATA = false;
                saveDocument(a.getProject(), a2);
            } catch (Throwable th) {
                HANDING_DATA = false;
            }
        }, new PsiFile[0]);
    }

    public static void saveDocument(Project a, Document a2) {
        FileDocumentManager.getInstance().saveDocument(a2);
        PsiDocumentManager.getInstance(a).commitDocument(a2);
    }

    private static void Tf(Editor a, String a2, int a3, Document a4, int a5, List<String> list, boolean z2) {
        MarkupModel markupModel = a.getMarkupModel();
        WriteCommandAction.runWriteCommandAction(a.getProject(), GeneratorConfig.H("\\\f\u001d\u0003\u0010\t\u0014N\u001b0/\u001aX*\u001a\u001a\f\u0007\u0007\u001c\u0017\u0001\u0016"), MethodGeneratorConfig.H(")+/272w?0\u0014\u000719"), () -> {
            HANDING_DATA = true;
            a4.insertString(a3, a2);
            int size = a5 + list.size();
            int i = a5;
            int i2 = i;
            while (i < size) {
                int i3 = i2;
                i2++;
                markupModel.addLineHighlighter(i3, 6000, f314enum);
                i = i2;
            }
            HANDING_DATA = false;
            saveDocument(a.getProject(), a4);
            InlineChatService.scrollToLines(a, a3, z2);
        }, new PsiFile[0]);
    }
}
