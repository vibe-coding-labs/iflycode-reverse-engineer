package com.aicode.listener;

import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.content.util.OverlayUtils;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.service.EditorManagerService;
import com.aicode.service.editor.EditorUtil;
import com.aicode.util.JComponentKt;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.editor.event.SelectionEvent;
import com.intellij.openapi.editor.event.SelectionListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: bl */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/CodeEditorListener.class */
public class CodeEditorListener implements EditorFactoryListener {

    /* renamed from: enum, reason: not valid java name */
    private final CodeSelectionListener f500enum = new CodeSelectionListener();

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m237enum(int a) {
        throw new IllegalArgumentException(String.format(OverlayUtils.H("\u0006\u0018+\u0014 \u0005h_d\u000f.\u001e{6\u0012\u001e9.3\u0007!@*\u0016tJ\u0006#3\u000f>AjE\u001egl\u000e'Ly\u0002iO)W\t<2\u0018{\u00183\u0005m\u0002#K#\u00156\u001b"), MethodGeneratorConfig.H("17*7#"), OverlayUtils.H("eD\u0006i&\u0003/\u000e)\u0005B,%\u00125\t2\u00145E\u0019\u0018��,\u0004\b2\u00023\u0003\u0001\t5\u001f(\u000e?\u0005"), MethodGeneratorConfig.H("1562'#\u001c(1 ;<3")));
    }

    /* compiled from: bl */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/CodeEditorListener$CodeSelectionListener.class */
    private static class CodeSelectionListener implements SelectionListener {
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m238enum(int a) {
            throw new IllegalArgumentException(String.format(JComponentKt.H("\u0019\f.\u001a/\u0001'\u001b~\u001eg\\O\t\r\n< <\u0003\u0013y9\u000e2\u0007$\n;\f*^bF0Bb\u000b\u0006f`\u0010q\\+^$\u001a1\u0010i\u00011\fi\r'D7\n-\u000b"), InlineChatStatusServiceKt.H("\u001a"), JComponentKt.H("*��/K(\u0006=\u0017lK@%*\u0016<\u000b'\n\rv\n��$\u0003\f\u000b&\u001d7\f\t\n0\u0011'\n\u00054a 0\u001d=-,\u0003'\u0007=\u00061\u0016\u0005\u00061\u0010<\u0011$\u0015"), InlineChatStatusServiceKt.H("\u0017$-\u00018\n5\u0016#+.\u0002#\u000f?\u001b")));
        }

        private CodeSelectionListener() {
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public void selectionChanged(@NotNull SelectionEvent a) {
            if (a == null) {
                m238enum(0);
            }
            Editor editor = a.getEditor();
            Project project = editor.getProject();
            if (project == null || project.isDisposed() || !EditorUtil.isSelectedEditor(editor)) {
                return;
            }
            Document document = editor.getDocument();
            if (StringUtils.equals(document.getText(a.getNewRange()), document.getText(a.getOldRange()))) {
            }
        }
    }

    public void editorCreated(@NotNull EditorFactoryEvent a) {
        if (a == null) {
            m237enum(0);
        }
        Editor editor = a.getEditor();
        Project project = editor.getProject();
        if (project == null || project.isDisposed() || !EditorManagerService.getInstance().isAvailable(editor)) {
            return;
        }
        Disposable newDisposable = Disposer.newDisposable(MethodGeneratorConfig.H("8:\"-2\u001d/<>-\u001923:415,"));
        com.intellij.openapi.editor.ex.util.EditorUtil.disposeWithEditor(editor, newDisposable);
        editor.getSelectionModel().addSelectionListener(this.f500enum, newDisposable);
    }
}
