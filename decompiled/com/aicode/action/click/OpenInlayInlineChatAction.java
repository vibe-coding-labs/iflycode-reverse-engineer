package com.aicode.action.click;

import cn.hutool.core.util.StrUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.content.util.EditorUtils;
import com.aicode.inline.InlineChatService;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.settings.AICodeSettingsState;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: be */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/click/OpenInlayInlineChatAction.class */
public class OpenInlayInlineChatAction extends PluginAnAction {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m55enum(int a) {
        String H = EditorUtils.H("Q9x1w$~?8%2t\u001c'M7b\u0003h*VAm'm%\u007f,~4HA:clc:.{f3>\u001eNh`\u007f<t(\u0012\u0007\u007f?'>\u007far2q*");
        Object[] objArr = new Object[3];
        objArr[0] = CancelRequestTip.H("\f");
        objArr[1] = EditorUtils.H("\u0004l59,t%U\u0005xi~'f e?\u0015\u0002q/|/5\u000em#x\u0004^\u0007z9['k5\\\fS#f([\"h.r(");
        switch (a) {
            case 0:
            default:
                objArr[2] = CancelRequestTip.H("\u001f\u001a\u0015\u0010\u001d\f");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = EditorUtils.H("(d([\u0006~\u001bb.|.n*x\"");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void update(@NotNull AnActionEvent a) {
        boolean contains;
        if (a == null) {
            m55enum(0);
        }
        contains = AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.INLINE_CHAT.getPermission());
        if (!contains) {
            a.getPresentation().setEnabledAndVisible(false);
            return;
        }
        a.getPresentation().setText("内联聊天(Beta)  " + StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(EditorUtils.H("\u007f\u001b~.['k5\\\fS#f([\"h.r(")), KeymapUtil.getFirstMouseShortcutText(CancelRequestTip.H("?��\b\u0003\"\u0005\u0006\u0003\u001f\u00147\u001c\u0001\u0014$\u0006\u0004\u0019\u000e\u000f"))));
        Project project = a.getProject();
        Editor editor = (Editor) a.getData(PlatformDataKeys.EDITOR);
        if (!StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
            if (!AICodeSettingsState.getInstance().openInlineChat) {
                a.getPresentation().setEnabled(false);
                return;
            } else if (editor == null || project == null) {
                a.getPresentation().setEnabled(false);
                return;
            } else {
                a.getPresentation().setEnabled(true);
                return;
            }
        }
        a.getPresentation().setEnabled(false);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void actionPerformed(@NotNull AnActionEvent a) {
        Editor selectedTextEditor;
        if (a == null) {
            m55enum(1);
        }
        Project project = a.getProject();
        if (project != null && (selectedTextEditor = FileEditorManager.getInstance(project).getSelectedTextEditor()) != null) {
            InlineChatService.Companion.openInlineChat(selectedTextEditor);
        }
    }

    public OpenInlayInlineChatAction(@Nullable String text, @Nullable String a) {
        super(text, a, null);
    }

    public boolean isDumbAware() {
        return true;
    }
}
