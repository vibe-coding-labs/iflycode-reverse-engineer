package com.aicode.action;

import com.aicode.PluginStartupActivity;
import com.aicode.action.click.PluginAnAction;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.Maps;
import com.aicode.util.PositionUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: am */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/UserInfoAction.class */
public class UserInfoAction extends PluginAnAction {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m36enum(int a) {
        String H = Maps.H(")&\u0005+\u00037\u000b-D>@a@\u001c,1\u0001\u0007\u001d8\u0002r\u001f2]r9\r\u0006+\u0016xTj;SO<\u0001{K!nY\u0011~\u0018<\u001b N<��'D:\ns\u001a=��<");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = PositionUtil.H("$");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = Maps.H("6\u0002-\u0002$");
                break;
        }
        objArr[1] = PositionUtil.H("Z&Cqt\fQ-^/\u001c\"~\u0019V Fw`6V1{,_&s!]0^/");
        switch (a) {
            case 0:
            default:
                objArr[2] = Maps.H("(\u000b \u0007=\u0001\u0003\u0001*\t<\u0006%\t4");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = PositionUtil.H("G2M8E$");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m36enum(1);
        }
        a.getPresentation().setText((StringUtils.isBlank(PluginStartupActivity.getApiKey()) || StringUtils.isBlank(AICodeSettingsState.getInstance().userName)) ? PositionUtil.H("贛厺Ｒ杲療弜") : "账号：" + AICodeSettingsState.getInstance().userName);
    }

    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m36enum(0);
        }
    }
}
