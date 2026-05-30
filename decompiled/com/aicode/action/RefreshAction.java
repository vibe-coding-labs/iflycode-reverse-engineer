package com.aicode.action;

import com.aicode.action.click.PluginAnAction;
import com.aicode.agent.service.ChatService;
import com.aicode.agent.service.RestartableAgentProcessService;
import com.aicode.diff.GenericUtils;
import com.aicode.util.AICodeUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.jetbrains.annotations.NotNull;

/* compiled from: tg */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/RefreshAction.class */
public class RefreshAction extends PluginAnAction {
    public static ConcurrentNavigableMap<String, Boolean> REFRESH_MAP = new ConcurrentSkipListMap();

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m32enum(int a) {
        String H = AICodeUtils.H("Mpg{oi/;.fiz,BNa\u007fK\u007fh-o~a~cvprmr.&*2h\u001d\\l$,t36y$qgt}\u000eNcv;wc(n{mc");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = GenericUtils.H("6");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = AICodeUtils.H("mvko{");
                break;
        }
        objArr[1] = GenericUtils.H("8?5k-:8*);y\u0016\u001c!4-$p\u0005<7*5 3\u001937\"4=");
        switch (a) {
            case 0:
            default:
                objArr[2] = AICodeUtils.H("sxdouj");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = GenericUtils.H("+<#0>6��6)>?1&>7");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m32enum(0);
        }
        a.getPresentation().setEnabledAndVisible(a.getProject() != null);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m32enum(1);
        }
        Project project = a.getProject();
        if (project == null) {
            return;
        }
        RestartableAgentProcessService.refreshTimes.set(1);
        ChatService.refreshAgent(project, true);
    }

    public RefreshAction() {
        super(GenericUtils.H("刿新"), null, AllIcons.Actions.Refresh);
    }
}
