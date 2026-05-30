package com.aicode.service.editor;

import com.aicode.content.util.EditorUtils;
import com.aicode.diff.FileInfo;
import com.aicode.enums.CodeTipRequestType;
import com.aicode.inline.InlineChatService;
import com.aicode.inline.ide.IdeAction;
import com.aicode.service.EditorManagerService;
import com.aicode.util.EditorKt;
import com.aicode.util.JComponentKt;
import com.intellij.ide.actions.SaveAllAction;
import com.intellij.ide.actions.UndoAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actions.BackspaceAction;
import com.intellij.openapi.editor.actions.DeleteAction;
import com.intellij.openapi.project.Project;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.annotations.NotNull;

/* compiled from: bc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/DocumentActionTracker.class */
public final class DocumentActionTracker {

    /* renamed from: float, reason: not valid java name */
    public static final /* synthetic */ boolean f564float;

    /* renamed from: byte, reason: not valid java name */
    private static final Logger f565byte;

    /* renamed from: enum, reason: not valid java name */
    private final AtomicInteger f566enum = new AtomicInteger(0);

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m281enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = JComponentKt.H("\u0001)&\u001b\f\u00114\u0012M&*\u001d0\u0011!Cf\u0016lA\u000b~3\r>\u001fo\u0007(\u0015i\u001d;\f<\u001d,D7\n-\u000b");
                i = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                H = IdeAction.H("(P\bQI\n\u0001PNC\fZ^u+A\f}Q\u0003\"%\u0015O\u001bC\u0012Q\u0013I\u001d\u0004C\n\r\u0012ko\u000f\u0002[FM\r\u0016\u000e\tZ-aX]\u0004TI@\u0004\n\u0001Q\u0014_");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            default:
                i2 = 2;
                break;
            case 1:
                i2 = 3;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = JComponentKt.H("\u0006 \u0004v\u001e(\u0004&\u000b'K+\u001b\u001f=&\n=Q \u0007*\u0011-\u0016W\u001a1\u001b8\u0006*\u00073 *\u001b7\u0017';0\u0005:\u0014$\u0015");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = IdeAction.H("��I\u001bM\u0017]");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = JComponentKt.H("\b;\f��\u00011\u00108\u0011\"\u0002");
                i4 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = IdeAction.H("M\u0017^\u000b\u000e'f\nJ\f\r\fQ\u0015Z\u0006G\u0001��\u001bQ\"t\u0006PQq\fK\u0010C\u0001A*T\u001bG\u0002O\u0007v\u0013K\fO\u001dA");
                i4 = a;
                break;
        }
        switch (i4) {
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = JComponentKt.H("*\u0016\u0004\u000b\n=;\u001c\u000e\u0004+\f��\u0004'\n,\u0019=\n\u0003\u0007-\u0016.\t");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            default:
                throw new IllegalStateException(format);
            case 1:
                throw new IllegalArgumentException(format);
        }
    }

    /* compiled from: bc */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/DocumentActionTracker$ActionListener.class */
    public static final class ActionListener implements AnActionListener {

        /* renamed from: enum, reason: not valid java name */
        private final AtomicReference<Editor> f567enum = new AtomicReference<>(null);

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m282enum(int a) {
            String H = EditorUtils.H("W?z3j9\\\u001d0-h.:\u0001R(i\bR\u0010mzb(b*u&b(szp)AN0$a|?22bnfR\u0011i5 5h(6/xfx8m6");
            Object[] objArr = new Object[3];
            switch (a) {
                case 0:
                case 2:
                default:
                    objArr[0] = FileInfo.H("\u0014\u007f\n~\u0006n");
                    break;
                case 1:
                case 3:
                    do {
                    } while (0 != 0);
                    objArr[0] = EditorUtils.H("#`(o.");
                    break;
            }
            objArr[1] = FileInfo.H("\u001dx\u00183\u000eo9\\\u001ct@u\u0017i\u0002t\u0016y`C\ri\u000e|\n>4v\u001db\u0004eQ\"\u001bP\fx��h&i\u0015~\u001ey%\u001a3x\u001ch��h2~\u0006h\u001by\fr");
            switch (a) {
                case 0:
                case 1:
                default:
                    objArr[2] = EditorUtils.H("#y!r4Z%y5i4i\fs?{)d d>");
                    break;
                case 2:
                case 3:
                    do {
                    } while (0 != 0);
                    objArr[2] = FileInfo.H("\u0015{\u0001y%\u007f\u0011o\u0001n\u0001V\u001be\u0013s\fz\fd");
                    break;
            }
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void beforeActionPerformed(@NotNull AnAction action, @NotNull AnActionEvent a) {
            ActionListener actionListener;
            if (action == null) {
                m282enum(0);
            }
            if (a == null) {
                m282enum(1);
            }
            Editor editor = (Editor) CommonDataKeys.EDITOR.getData(a.getDataContext());
            if (editor != null && EditorKt.containEditor(editor)) {
                if (action instanceof SaveAllAction) {
                    EditorKt.removeEditor(editor);
                    ApplicationManager.getApplication().invokeLater(() -> {
                        if (editor == null || editor.isDisposed()) {
                            return;
                        }
                        EditorKt.closeButtonPanel(editor);
                    });
                    actionListener = this;
                    actionListener.f567enum.set((editor == null && EditorManagerService.getInstance().isAvailable(editor)) ? editor : null);
                    if (DocumentActionTracker.oB(action)) {
                        return;
                    }
                    DocumentActionTracker.getInstance().Hb();
                    return;
                }
                if (action instanceof UndoAction) {
                    InlineChatService.handleUndoAction(editor);
                }
            }
            actionListener = this;
            actionListener.f567enum.set((editor == null && EditorManagerService.getInstance().isAvailable(editor)) ? editor : null);
            if (DocumentActionTracker.oB(action)) {
            }
        }

        public void afterActionPerformed(@NotNull AnAction action, @NotNull AnActionEvent a) {
            Editor editor;
            Project project;
            if (action == null) {
                m282enum(2);
            }
            if (a == null) {
                m282enum(3);
            }
            if (DocumentActionTracker.oB(action)) {
                DocumentActionTracker.getInstance().exitForcedCodeGenerateAction();
                if (DocumentActionTracker.getInstance().getExecutingForcedCodeGenerateAction() || (editor = this.f567enum.get()) == null || editor.isDisposed() || !EditorUtil.isSelectedEditor(editor) || (project = editor.getProject()) == null || project.isDisposed()) {
                    return;
                }
                EditorManagerService editorManagerService = EditorManagerService.getInstance();
                if (!editorManagerService.isAvailable(editor)) {
                    return;
                }
                DocumentActionTracker.f565byte.debug(FileInfo.H("8x\u001bcV8=\u0013\u001du\u0006r\u001diT^\u001ax2y\u0017u\rs\u000er\u001b7��l\u001av\u001de"));
                editorManagerService.editorChanged(editor, CodeTipRequestType.Forced, false);
            }
        }
    }

    public void exitForcedCodeGenerateAction() {
        int decrementAndGet = this.f566enum.decrementAndGet();
        if (!f564float && decrementAndGet < 0) {
            throw new AssertionError();
        }
    }

    private void Hb() {
        this.f566enum.incrementAndGet();
    }

    @NotNull
    public static DocumentActionTracker getInstance() {
        DocumentActionTracker documentActionTracker = (DocumentActionTracker) ApplicationManager.getApplication().getService(DocumentActionTracker.class);
        if (documentActionTracker == null) {
            m281enum(0);
        }
        return documentActionTracker;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static boolean oB(@NotNull AnAction action) {
        if (action == null) {
            m281enum(1);
        }
        return (action instanceof DeleteAction) || (action instanceof BackspaceAction);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        f564float = !DocumentActionTracker.class.desiredAssertionStatus();
        f565byte = Logger.getInstance(DocumentActionTracker.class);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public boolean getExecutingForcedCodeGenerateAction() {
        return this.f566enum.get() > 0;
    }
}
