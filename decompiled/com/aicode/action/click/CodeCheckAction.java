package com.aicode.action.click;

import com.aicode.PluginStartupActivity;
import com.aicode.action.ActionsUtil;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.service.CommonService;
import com.aicode.service.editor.RequestResultList;
import com.aicode.util.PositionUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsActions;
import com.intellij.openapi.vfs.VirtualFile;
import javax.swing.Icon;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: qm */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/click/CodeCheckAction.class */
public class CodeCheckAction extends PluginAnAction {

    /* renamed from: byte, reason: not valid java name */
    private String f118byte;
    public static String content;
    public static String path;

    /* renamed from: enum, reason: not valid java name */
    public static final /* synthetic */ boolean f119enum;

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m53enum(int a) {
        throw new IllegalArgumentException(String.format(PositionUtil.H("~=O-X ]7\u0012$\u001dp\u001f\u000f~/A\u000bJ#[g\u0002cm\u000e^&L-Ki8JGc\u0019&Yo\u001243HLoE-F1\u0013-]6\u0019+WbG,]-"), RequestResultList.H("JmLt\\"), PositionUtil.H("ap\u0002\u001c\"Q+V-z@U'M P!\u0018$q\u0004\\$\u0007\u001bZ!V��Z'Z\"s!]0^/"), RequestResultList.H("D|Y\\hyue@{@iD\u007fL")));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void handle(Project a, VirtualFile a2, Document a3) {
        path = a2.getPath();
        if (StringUtils.isBlank(path)) {
            return;
        }
        content = a3.getText();
        if (StringUtils.isBlank(content)) {
            return;
        }
        CommonService.openPage(a, PageEnum.CODE_CHECK);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(AnActionEvent a) {
        if (!StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
            a.getPresentation().setEnabled(true);
        } else {
            a.getPresentation().setEnabled(false);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m53enum(0);
        }
        try {
            Project project = a.getProject();
            if (!f119enum && project == null) {
                throw new AssertionError();
            }
            Editor editor = (Editor) a.getData(PlatformDataKeys.EDITOR);
            if (!f119enum && editor == null) {
                throw new AssertionError();
            }
            Document document = editor.getDocument();
            VirtualFile file = FileDocumentManager.getInstance().getFile(document);
            if (!f119enum && file == null) {
                throw new AssertionError();
            }
            handle(project, file, document);
        } catch (Throwable th) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        f119enum = !CodeCheckAction.class.desiredAssertionStatus();
    }

    public CodeCheckAction(@NlsActions.ActionText @Nullable String a) {
        this(a, null, null);
        this.f118byte = a;
    }

    public CodeCheckAction(@NlsActions.ActionText @Nullable String text, @NlsActions.ActionDescription @Nullable String description, @Nullable Icon icon) {
        super(text, description, null);
        ActionsUtil.registerOrReplaceAction(this);
    }
}
