package com.aicode.action;

import com.aicode.action.click.PluginAnAction;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.service.CommonService;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.AICodeStringUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

/* compiled from: yk */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/OpenWindowAction.class */
public class OpenWindowAction extends PluginAnAction {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m27enum(int a) {
        String H = InlineChatStatusServiceKt.H("GQ.\u0019,\u0001%\u001ag\u0004#\u001bm(HL=\">\u00020Y1\u00055\u0003+\u0006rF\u0015b\u007fX>Of\f\u0019zd\u0017hF?I*\u0017,\u000eI\"$\u001a|\u001b$D)\u0017*\u000f");
        Object[] objArr = new Object[3];
        objArr[0] = AICodeStringUtil.H("H");
        objArr[1] = InlineChatStatusServiceKt.H("eL\nm9\u0014.\u0007\"\u0006P;\"\u0010/\f\"F\b\u0012:\u0014>%%\n3\u000e��\u00073\u000b)\r");
        switch (a) {
            case 0:
            default:
                objArr[2] = AICodeStringUtil.H("TZCMRH");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = InlineChatStatusServiceKt.H("\u0003<\u000e��#%>9\u000b'\u000b5\u000f#\u0007");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public boolean isDumbAware() {
        return true;
    }

    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m27enum(0);
        }
        a.getPresentation().setEnabled(true);
    }

    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m27enum(1);
        }
        ApplicationManager.getApplication().invokeLater(() -> {
            ToolWindow toolWindow = ToolWindowManager.getInstance(a.getProject()).getToolWindow(BasicActionsBundle.message(AICodeStringUtil.H("J\u0014\u0002r|\u0016RDEIIz:dNOYCUfOK]fllRSBQ\u0004SI^Y"), new Object[0]));
            if (toolWindow != null) {
                toolWindow.show();
            }
            CommonService.openPage(getEventProject(a), PageEnum.CHAT_VIEW);
        });
    }

    public OpenWindowAction() {
        super(AICodeStringUtil.H("变赵寒诽\u001c\u0017b^U@\r|"), InlineChatStatusServiceKt.H("厸贻宲讳|Y\u0002\u00105\u000em2"), null);
    }
}
