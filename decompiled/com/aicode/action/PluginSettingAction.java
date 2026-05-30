package com.aicode.action;

import com.aicode.PluginStartupActivity;
import com.aicode.action.click.PluginAnAction;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.service.CommonService;
import com.aicode.ui.FontKt;
import com.aicode.util.JComponentKt;
import com.aicode.util.MessageBundle;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: jh */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/PluginSettingAction.class */
public class PluginSettingAction extends PluginAnAction {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m28enum(int a) {
        String H = FontKt.H("Xw1?9--+~$76y\u0005Wj\u00135.+.~ -,#4 \u0010\u001d ndz0x~-?eu?XO'h.*-6x*61r,<e,+6*");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = JComponentKt.H("\u0002");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = FontKt.H(" 4;42");
                break;
        }
        objArr[1] = JComponentKt.H("��-\tP8 \f7\u001a=Q$��6\r$\u0003B\u001b#\u001c?\u0017+0&\u00116\r'\b\u0003\u0007-\u0016.\t");
        switch (a) {
            case 0:
            default:
                objArr[2] = FontKt.H(">=61+7\u00157<?*03?\"");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = JComponentKt.H("7\u0014=\u001e5\u0002");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        AnActionEvent anActionEvent;
        if (a == null) {
            m28enum(1);
        }
        if (!StringUtils.isNotBlank(PluginStartupActivity.getApiKey())) {
            anActionEvent = a;
            a.getPresentation().setVisible(false);
            a.getPresentation().setEnabled(false);
        } else {
            anActionEvent = a;
            a.getPresentation().setVisible(true);
            a.getPresentation().setEnabled(true);
        }
        anActionEvent.getPresentation().setText(MessageBundle.get(JComponentKt.H("\u0002+\u0007$\t\be\u001c\u001d9\n0\u0010\u0001\u0004046\u000f)\nf\u001d(\u001f,\u0017'\b")));
    }

    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m28enum(0);
        }
        CommonService.openPage(a.getProject(), PageEnum.SETTING_PAGE);
    }
}
