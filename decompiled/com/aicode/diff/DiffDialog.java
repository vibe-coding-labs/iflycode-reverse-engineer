package com.aicode.diff;

import com.aicode.agent.service.CodeCompleteService;
import com.aicode.enums.CodeCollectEnum;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.EditorManagerServiceImpl;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.StringUtils;
import com.intellij.diff.DiffManager;
import com.intellij.diff.DiffRequestPanel;
import com.intellij.diff.requests.SimpleDiffRequest;
import com.intellij.diff.util.DiffUserDataKeysEx;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import java.awt.BorderLayout;
import java.awt.Window;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jetbrains.annotations.Nullable;

/* compiled from: me */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/diff/DiffDialog.class */
public class DiffDialog extends DialogWrapper {

    /* renamed from: byte, reason: not valid java name */
    private final Project f208byte;

    /* renamed from: enum, reason: not valid java name */
    private final SimpleDiffRequest f209enum;

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m119enum(int a) {
        throw new IllegalStateException(String.format(CodeCompleteService.H("iB\r3fxIl\nbO{\n(l\r��s\u0004*Y/eXVt\rfOq\u0003tNz\\~L'WiMh"), AICodeStringUtil.H("MJC\n\u0007\u0004ohEO\u0001AGCj(eCOD`FF@@C"), CodeCompleteService.H("fQcJzLMAsPsOw")));
    }

    @Nullable
    public JComponent createCenterPanel() {
        JPanel jPanel = new JPanel(new BorderLayout());
        DiffRequestPanel createRequestPanel = DiffManager.getInstance().createRequestPanel(this.f208byte, Disposer.newDisposable(), (Window) null);
        createRequestPanel.setRequest(this.f209enum);
        createRequestPanel.putContextHints(DiffUserDataKeysEx.BOTTOM_PANEL, jPanel);
        return createRequestPanel.getComponent();
    }

    public DiffDialog(Project a, SimpleDiffRequest a2) {
        super(a, true);
        this.f208byte = a;
        this.f209enum = a2;
        setTitle(BasicActionsBundle.message(AICodeStringUtil.H("@CMJJ@\"fB^@MJ\u0001CEIB"), new Object[0]));
        init();
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public void doOKAction() {
        if (Objects.isNull(this.f209enum)) {
            return;
        }
        VirtualFile virtualFile = (VirtualFile) this.f209enum.getUserData(DiffService.DIFF_FILEPATH_LEFT);
        VirtualFile virtualFile2 = (VirtualFile) this.f209enum.getUserData(DiffService.DIFF_FILEPATH_RIGHT);
        String str = (String) this.f209enum.getUserData(CloudDiffUtil.DIFF_SUGGEST_CODE);
        if (Objects.isNull(virtualFile) || Objects.isNull(virtualFile2)) {
            return;
        }
        try {
            String remove = StringUtils.remove(new String(virtualFile.contentsToByteArray(), StandardCharsets.UTF_8), '\r');
            WriteCommandAction.runWriteCommandAction(this.f208byte, () -> {
                DocumentImpl document = FileDocumentManager.getInstance().getDocument(virtualFile2);
                if ((document instanceof DocumentImpl) && !document.acceptsSlashR()) {
                    document.setText(remove);
                }
            });
            EditorManagerServiceImpl.acceptCount(this.f208byte, virtualFile2.getPath(), str, CodeCollectEnum.COMPARE);
            close(0);
        } catch (IOException e) {
            EditorManagerServiceImpl.acceptCount(this.f208byte, virtualFile2.getPath(), str, CodeCollectEnum.COMPARE);
            close(0);
        } catch (Throwable th) {
            EditorManagerServiceImpl.acceptCount(this.f208byte, virtualFile2.getPath(), str, CodeCollectEnum.COMPARE);
            close(0);
            throw th;
        }
    }

    public Action[] createActions() {
        Action oKAction = getOKAction();
        oKAction.putValue(CodeCompleteService.H("lfR\u007f"), BasicActionsBundle.message(AICodeStringUtil.H("OLMJ\u0002\b\"fB^GJ@\u000bhnGL\u0007PA_KMLA"), new Object[0]));
        Action cancelAction = getCancelAction();
        cancelAction.putValue(CodeCompleteService.H("lfR\u007f"), AICodeStringUtil.H("叹涬"));
        Action[] actionArr = {cancelAction, oKAction};
        if (actionArr == null) {
            m119enum(0);
        }
        return actionArr;
    }
}
