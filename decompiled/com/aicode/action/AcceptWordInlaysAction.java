package com.aicode.action;

import com.aicode.action.batch.GeneratorConfig;
import com.aicode.content.util.file.LanguageFileExtensionDetails;
import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.service.EditorManagerService;
import com.aicode.settings.AICodeRequestSettings;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.IndentLineUtil;
import com.aicode.util.PropertyUtils;
import com.intellij.application.options.CodeStyle;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import java.awt.event.KeyEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: li */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/AcceptWordInlaysAction.class */
public class AcceptWordInlaysAction extends EditorAction implements DumbAware, CodeAction {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m11enum(int a) {
        String H = ConditionalActionConfiguration.H("(\u001e\u0013\u0004\u001f\u0012\u001d\u0002\u0013P3+^;0\u0014\f3\u0018\u0004\u0005L:.��\u0016\u0002\u000f\r\u0019&qTS\u0002S\\\u0016\u0002AK\u0018r|\r[\u0004\u0019\u0007\u0005R\u0019\u001c\u0002X\u001f\u0016V\u0006\u0018\u001c\u0019");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[0] = PropertyUtils.H("~");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = ConditionalActionConfiguration.H("\u0016\u0012\u0001\u0019\u001f\u0007");
                break;
        }
        objArr[1] = PropertyUtils.H("\"y*?3l5n\u0011Gb{3s/~\u0005\u0013\rx-|3`\fc#b*Z-w/r\n\u007f9s#u");
        switch (a) {
            case 0:
            default:
                objArr[2] = ConditionalActionConfiguration.H("\u0006\u0006\f\f\u0004\u0010");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = PropertyUtils.H("%h\u0007~-{)i5M\u0006M#y7s/Y;\u007f\"o");
                break;
            case 2:
                objArr[2] = ConditionalActionConfiguration.H("\u001e��%\r\r\u0003\u0019\u001a\u0019\u0015\u0011");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* compiled from: li */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/AcceptWordInlaysAction$wa.class */
    private static class wa extends EditorActionHandler {
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m12enum(int a) {
            String H = LanguageFileExtensionDetails.H("B\u001cs\fd\u0001a\u0016.\u0005!Q\u0001\fM\u0001w p\u0004|]d\u0018E;b\u0007f\u001avI\u000ea}D,\u000egL<\u0007=[Rln\u001bg\r)\n`\u0016.\u0001!\ty\u000fm��");
            Object[] objArr = new Object[3];
            switch (a) {
                case 0:
                case 2:
                case 3:
                default:
                    objArr[0] = GeneratorConfig.H("J]\u0015\u001e\u0005\u000e");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = LanguageFileExtensionDetails.H("Jv\bd\u0018");
                    break;
            }
            objArr[1] = GeneratorConfig.H("\u001f\u0007\u0013P\b\u000b\u0017\u000b\u0016��\\DP>5\u0007\u0010G?\r\u001b\u001e\u001d\u000b>38��;\u0017\u0003\u000e��1\u0015\u0006\u0007\u000e\u001e\u0004X3\u0014\b\u00023\u000b\u0007\f\u001b \f\u0018\u0005\u000b\u0016;NW\u0018\u0006\u000f\u000e");
            switch (a) {
                case 0:
                case 1:
                default:
                    objArr[2] = LanguageFileExtensionDetails.H("%p+z\u0018k\bj\u0006H\f6jv\bd\u0018");
                    break;
                case 2:
                    do {
                    } while (0 != 0);
                    objArr[2] = GeneratorConfig.H("\r\u0006\u001a\n\u0017��\u0001;\u000b0@T\u0011\u000b\u0004\u0018");
                    break;
                case 3:
                    objArr[2] = LanguageFileExtensionDetails.H("\u0006a&<Lt\u000fu\t");
                    break;
            }
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        private wa() {
        }

        public void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
            if (editor == null) {
                m12enum(3);
            }
            EditorManagerService.getInstance().acceptWordTip(editor);
        }

        public boolean isEnabledForCaret(@NotNull Editor editor, @NotNull Caret caret, DataContext dataContext) {
            if (editor == null) {
                m12enum(0);
            }
            if (caret == null) {
                m12enum(1);
            }
            return AcceptWordInlaysAction.isSupported(editor);
        }

        public boolean executeInCommand(@NotNull Editor editor, DataContext dataContext) {
            if (editor == null) {
                m12enum(2);
            }
            return false;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static boolean isSupported(@NotNull Editor editor) {
        if (editor == null) {
            m11enum(2);
        }
        Project project = editor.getProject();
        AICodeRequestSettings.settings().isShowIdeCodeTips();
        return project != null && editor.getCaretModel().getCaretCount() == 1 && EditorManagerService.getInstance().hasTipInlays(editor) && TemplateManager.getInstance(project).getActiveTemplate(editor) == null;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean Re(Document a, int a2, int a3) {
        int lineStartOffset = a.getLineStartOffset(a2);
        if (lineStartOffset != a3 && !AICodeStringUtil.isSpacesOrTabs(a.getText(TextRange.create(lineStartOffset, a3)), false)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m11enum(0);
        }
        if (Rf(a)) {
            a.getPresentation().setEnabled(false);
        } else {
            super.update(a);
        }
    }

    public AcceptWordInlaysAction() {
        super(new wa());
        setInjectedContext(true);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean Rf(@NotNull AnActionEvent a) {
        Project project;
        Editor editor;
        int indentLine;
        if (a == null) {
            m11enum(1);
        }
        if ((a.getInputEvent() instanceof KeyEvent) && a.getInputEvent().getKeyChar() == '\t' && (project = a.getProject()) != null && (editor = getEditor(a.getDataContext())) != null) {
            Document document = editor.getDocument();
            int i = CodeStyle.getIndentOptions(project, document).INDENT_SIZE;
            int offset = editor.getCaretModel().getOffset();
            int lineNumber = document.getLineNumber(offset);
            if (!Re(document, lineNumber, offset) && (indentLine = IndentLineUtil.indentLine(project, editor, lineNumber, i, offset)) >= offset) {
                TextRange create = TextRange.create(offset, indentLine);
                EditorManagerService editorManagerService = EditorManagerService.getInstance();
                if (editorManagerService.countTipInlays(editor, create, true, false, false, false) <= 0 && editorManagerService.countTipInlays(editor, create, false, true, false, false) <= 0) {
                    if (editorManagerService.countTipInlays(editor, create, false, false, true, false) > 0 && editorManagerService.countTipInlays(editor, TextRange.create(offset, document.getLineEndOffset(lineNumber)), true, true, false, true) <= 0) {
                        return false;
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
