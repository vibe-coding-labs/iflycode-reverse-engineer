package com.aicode.action;

import com.aicode.PluginStartupActivity;
import com.aicode.action.click.PluginAnAction;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.UserService;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.exception.RequestCancelException;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.StringUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

/* compiled from: vh */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/LogoutAction.class */
public class LogoutAction extends PluginAnAction {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m26enum(int a) {
        String H = OpenTelemetryUtil.H("M:e3c/b<omo6(\fL)z\u0004p-h`?jr%o#a4zl)o|lodKI+9(g}jy%f%��\nm253ml`?c'");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = RequestCancelException.H(">");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = OpenTelemetryUtil.H(")x/a?");
                break;
        }
        objArr[1] = RequestCancelException.H("]6[~_0G,A'?\u0016Q!L-Vpr6X7A'~;P*S5");
        switch (a) {
            case 0:
            default:
                objArr[2] = OpenTelemetryUtil.H("1v%I\u000bl\u0016p#n#|'j/");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = RequestCancelException.H("J(@\"H>");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m26enum(0);
        }
        if (StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
            ApplicationManager.getApplication().invokeLater(() -> {
                ToolWindow toolWindow = ToolWindowManager.getInstance(a.getProject()).getToolWindow(BasicActionsBundle.message(RequestCancelException.H("8L6J(Qyt\u0019Q=S\u007f{=M7J0Q\u0014F<J,\u007f-Q,Ovb\u0014O$"), new Object[0]));
                if (toolWindow == null || toolWindow.isVisible()) {
                    return;
                }
                toolWindow.show();
            });
            PluginWebsocketClient.sendWsMessage(CommandEnum.USER_LOGIN, a.getProject());
        } else {
            UserService.logout(a.getProject());
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m26enum(1);
        }
        a.getPresentation().setText(StringUtils.isBlank(PluginStartupActivity.getApiKey()) ? RequestCancelException.H("叹癏弆") : OpenTelemetryUtil.H("逎冰癴弞"));
    }

    public LogoutAction() {
        super(() -> {
            return StringUtils.isBlank(PluginStartupActivity.getApiKey()) ? OpenTelemetryUtil.H("叱癴弞") : RequestCancelException.H("逿冢癯弦");
        }, null);
    }
}
