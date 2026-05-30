package com.aicode.action;

import com.aicode.action.batch.GeneratorConfig;
import com.aicode.exception.RequestTimeoutException;
import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.service.EditorManagerService;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.settings.AICodeRequestSettings;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.IndentLineUtil;
import com.intellij.application.options.CodeStyle;
import com.intellij.codeInsight.lookup.LookupManager;
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

/* compiled from: xd */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/AcceptInlaysAction.class */
public class AcceptInlaysAction extends EditorAction implements DumbAware, CodeAction {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m7enum(int a) {
        String H = CancelRequestTip.H(" \u0013M_\b��\t\u0013P\u0016\u0002\u001fK+$\u0005^d0)\u000bG\u0017\u0006\u0013��\u0019\u0011\u0004\u0015?mMO\u001bOE\n\u001b]R\u0004k`\u0014G\u001d\u0005\u001e\u0019K\u0005\u0005\u001eA\u0003\u000fJ\u001f\u0004\u0005\u0005");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[0] = GeneratorConfig.H("\u0016");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = CancelRequestTip.H("\u000f\u000e\u0018\u0005\u0006\u001b");
                break;
        }
        objArr[1] = GeneratorConfig.H(")3\u0005Q\t\u0017\r\u0017\u001f\bP\b! \f\u001c\t^+\u001f\u0011\u0001\b\u001a\u00032\u0004\u001f\u0006\u001a#\u0017\u0010\u001b\n\u001d");
        switch (a) {
            case 0:
            default:
                objArr[2] = CancelRequestTip.H("\u001f\u001a\u0015\u0010\u001d\f");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = GeneratorConfig.H("\f��.\u0016\u0004\u0013��\u0001\u001c%/%\n\u0011\u001e\u001b\u00061\u0012\u0017\u000b\u0007");
                break;
            case 2:
                objArr[2] = CancelRequestTip.H("\u0002\u00199\u0014\u0011\u001a\u0005\u0003\u0005\f\r");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* compiled from: xd */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/AcceptInlaysAction$pa.class */
    private static class pa extends EditorActionHandler {
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m8enum(int a) {
            String H = RequestTimeoutException.H("\u0010\u00027\u0004v_:\u0001v\u0011.\u0012|=\u0014\u0014/4nV\u0018u&\u0016$\u0016=\u00141\u00013@[x(]y\u00172Ui\u001ehB\u0007u;\u00022\u0014|\u00135\u000f{\u0018t\u0010+\u00118\u0019");
            Object[] objArr = new Object[3];
            switch (a) {
                case 0:
                case 2:
                case 3:
                default:
                    objArr[0] = ConditionalActionConfiguration.H("\u0016\u0012\u0001\u0019\u001f\u0007");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = RequestTimeoutException.H("S$\u00161\u0001");
                    break;
            }
            objArr[1] = ConditionalActionConfiguration.H("\u0014\u001c\u001b\u001cW5:\u0011\u001f\u001bT\u0019\u001e\u0019\u0001\u0006\u0002{\u0010\u0010\u0015\u0014\u0004\b0\n\r\u000f\u0012/\u0018\u001d\u000f��\u0003\u001aU3\u0007\u0003\u001a@u\u0003\u0004\u001d��\u000b5\u0012\u0018\f\u0001\u0015\u0007");
            switch (a) {
                case 0:
                case 1:
                default:
                    objArr[2] = RequestTimeoutException.H("<%2/\u0001>\u0011?\u001f\u001d\u0015cs$\u00161\u0001");
                    break;
                case 2:
                    do {
                    } while (0 != 0);
                    objArr[2] = ConditionalActionConfiguration.H("\u0016\u000e\\_\u0018\u001c\u00190\u0016>\u001c\u001b\u0005\f\u001e\u0011");
                    break;
                case 3:
                    objArr[2] = RequestTimeoutException.H("\u001f4?iU&\u0011 \u0010");
                    break;
            }
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        private pa() {
        }

        public void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
            if (editor == null) {
                m8enum(3);
            }
            EditorManagerService.getInstance().acceptTip(editor);
        }

        public boolean isEnabledForCaret(@NotNull Editor editor, @NotNull Caret caret, DataContext dataContext) {
            if (editor == null) {
                m8enum(0);
            }
            if (caret == null) {
                m8enum(1);
            }
            return AcceptInlaysAction.isSupported(editor);
        }

        public boolean executeInCommand(@NotNull Editor editor, DataContext dataContext) {
            if (editor == null) {
                m8enum(2);
            }
            return false;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static boolean isSupported(@NotNull Editor editor) {
        if (editor == null) {
            m7enum(2);
        }
        Project project = editor.getProject();
        return project != null && editor.getCaretModel().getCaretCount() == 1 && (AICodeRequestSettings.settings().isShowIdeCodeTips() || LookupManager.getActiveLookup(editor) == null) && EditorManagerService.getInstance().hasTipInlays(editor) && TemplateManager.getInstance(project).getActiveTemplate(editor) == null;
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
            m7enum(0);
        }
        if (Rf(a)) {
            a.getPresentation().setEnabled(false);
        } else {
            super.update(a);
        }
    }

    public AcceptInlaysAction() {
        super(new pa());
        setInjectedContext(true);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean Rf(@NotNull AnActionEvent a) {
        Project project;
        Editor editor;
        int indentLine;
        if (a == null) {
            m7enum(1);
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
