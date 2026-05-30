package com.aicode.action;

import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.agent.service.GitReviewService;
import com.aicode.content.util.file.LanguageFileExtensionDetails;
import com.aicode.service.EditorManagerService;
import com.aicode.settings.AICodeRequestSettings;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.IndentLineUtil;
import com.aicode.util.NewFileUtils;
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

/* compiled from: dk */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/AcceptLineCodeInlaysAction.class */
public class AcceptLineCodeInlaysAction extends EditorAction implements DumbAware, CodeAction {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m9enum(int a) {
        String H = LanguageFileExtensionDetails.H("U\u000bn\u0011b\u0007`\u0017nEN>#.M\u0001q&e\u0011xY\\ {\u0005c\u0006q\rQn(GwN%\u0007Od+\u0010\"DrLt\u0001`\n\u0001\"l\u001a4\u001blDa\u0017b\u000f");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[0] = NewFileUtils.H("\u0014");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = LanguageFileExtensionDetails.H("l��f\u0016a\u0011");
                break;
        }
        objArr[1] = NewFileUtils.H("C\u001dFVl6B\u001cN\u001d\u0004\u0018d!I\u001dL_n\u001eT��M\u001bC4C\u001ay\u0007C\u0010h\u001dL\u0013R\na\u0011O��L\u001f");
        switch (a) {
            case 0:
            default:
                objArr[2] = LanguageFileExtensionDetails.H("|\u0014k\u0003z\u0006");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = NewFileUtils.H("T\u001cF:C\u0010H\rC>D\nB\u001dJ\u000bD7M\fM\u0005");
                break;
            case 2:
                objArr[2] = LanguageFileExtensionDetails.H("%p=a\ty\u000b}\u0016k\u0007");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean Rf(@NotNull AnActionEvent a) {
        Project project;
        Editor editor;
        int indentLine;
        if (a == null) {
            m9enum(1);
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

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static boolean isSupported(@NotNull Editor editor) {
        if (editor == null) {
            m9enum(2);
        }
        Project project = editor.getProject();
        AICodeRequestSettings.settings().isShowIdeCodeTips();
        return project != null && editor.getCaretModel().getCaretCount() == 1 && EditorManagerService.getInstance().hasTipInlays(editor) && TemplateManager.getInstance(project).getActiveTemplate(editor) == null;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m9enum(0);
        }
        if (Rf(a)) {
            a.getPresentation().setEnabled(false);
        } else {
            super.update(a);
        }
    }

    /* compiled from: dk */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/AcceptLineCodeInlaysAction$va.class */
    private static class va extends EditorActionHandler {
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m10enum(int a) {
            String H = GitReviewService.H("\u0006\u000e&\u000f9\n>\u001fH5\"\u0004j1\u000f\u0015\u0013\u0012>\u001c,[1\u001b\u001f7'\u0014<\u00167^zC$KE1!\\u\u0018cS8P'\u0004sOr\u0007*\na\u0018/Q?\u001f%\u001e");
            Object[] objArr = new Object[3];
            switch (a) {
                case 0:
                case 2:
                case 3:
                default:
                    objArr[0] = MethodGeneratorConfig.H("?0(;6%");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = GitReviewService.H("\u00120\u0018,\u0006");
                    break;
            }
            objArr[1] = MethodGeneratorConfig.H(":84~>-)/*\u001dY<0.=>1X8865.%\u0013\u0014\u001d?\u0017720\u0012#/&0\u0006:4-)!3w\u001a%*8iW, 4\"\"\u0017;:%#<%");
            switch (a) {
                case 0:
                case 1:
                default:
                    objArr[2] = GitReviewService.H("\u001994nZ0\u0005 \u001a\u0007\u0015820\u0018,\u0006");
                    break;
                case 2:
                    do {
                    } while (0 != 0);
                    objArr[2] = MethodGeneratorConfig.H("?,u}780\u0012?\u001c59,.73");
                    break;
                case 3:
                    objArr[2] = GitReviewService.H("\u001a.?2\u00142\u001f=\u0017");
                    break;
            }
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        public void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
            if (editor == null) {
                m10enum(3);
            }
            EditorManagerService.getInstance().acceptTipForLine(editor);
        }

        public boolean executeInCommand(@NotNull Editor editor, DataContext dataContext) {
            if (editor == null) {
                m10enum(2);
            }
            return false;
        }

        public boolean isEnabledForCaret(@NotNull Editor editor, @NotNull Caret caret, DataContext dataContext) {
            if (editor == null) {
                m10enum(0);
            }
            if (caret == null) {
                m10enum(1);
            }
            return AcceptLineCodeInlaysAction.isSupported(editor);
        }

        private va() {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean Re(Document a, int a2, int a3) {
        int lineStartOffset = a.getLineStartOffset(a2);
        if (lineStartOffset != a3 && !AICodeStringUtil.isSpacesOrTabs(a.getText(TextRange.create(lineStartOffset, a3)), false)) {
            return true;
        }
        return false;
    }

    public AcceptLineCodeInlaysAction() {
        super(new va());
        setInjectedContext(true);
    }
}
