package com.aicode.action.click;

import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.error.search.Presentation;
import com.aicode.inline.controller.ChatInputController;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.StringUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: uj */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/click/TerminalAction.class */
public class TerminalAction extends PluginAnAction {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m57enum(int a) {
        String H = ChatInputController.H("7\b\u0013\r\u000e\n\u0010\u0006X\u0012\u0016\u0007\u0019u:\u0017\u000f9\u000b\u001e\u0018X\f\u0011KT.*\u0006\u001b\u0017I]S\rUY\u001a\u0010Z^\u0004x\u007f\u0007X\u000e\u001a\r\u0006X\u001a\u0016\u0001R\u001c\u001cU\f\u001b\u0016\u001a");
        Object[] objArr = new Object[3];
        objArr[0] = MethodGeneratorConfig.H("17*7#");
        objArr[1] = ChatInputController.H("ZZ.`\u0013\u0017\u0006\u0006\u001e\u0013Q\u0013\u001a\u0001\u001f\u0015\u0015X56\u001d\u001b\b@*\u0017\n\u0019\u0010\u001b\u0013\u00128\u0016\u0016\u0007\u0015\u0018");
        switch (a) {
            case 0:
            default:
                objArr[2] = MethodGeneratorConfig.H("/>'2:4\u00044-<;3\"<3");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = ChatInputController.H("\f\u0005\u0006\u000f\u000e\u0013");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m57enum(1);
        }
        if (AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.CODE_DEBUG.getPermission())) {
            Editor editor = (Editor) a.getData(PlatformDataKeys.EDITOR);
            if (editor != null) {
                if (StringUtils.isBlank(editor.getSelectionModel().getSelectedText())) {
                    a.getPresentation().setVisible(false);
                    a.getPresentation().setEnabled(false);
                    return;
                } else {
                    a.getPresentation().setVisible(true);
                    a.getPresentation().setEnabled(true);
                    return;
                }
            }
            return;
        }
        a.getPresentation().setEnabledAndVisible(false);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m57enum(0);
        }
        String selectedText = ((Editor) a.getData(PlatformDataKeys.EDITOR)).getSelectionModel().getSelectedText();
        if (StringUtils.isBlank(selectedText)) {
            return;
        }
        Presentation.handleDebug(selectedText, selectedText, false, true);
    }

    public TerminalAction(@Nullable String text, @Nullable String description, Icon a) {
        super(text, description, a);
    }
}
