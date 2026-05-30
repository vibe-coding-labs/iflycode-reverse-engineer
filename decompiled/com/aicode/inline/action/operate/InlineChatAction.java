package com.aicode.inline.action.operate;

import com.aicode.action.click.PluginAnAction;
import com.aicode.exception.RequestTimeoutException;
import com.aicode.inline.InlineChatInlay;
import com.aicode.service.editor.CancelRequestTip;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAware;
import org.jetbrains.annotations.NotNull;

/* compiled from: pf */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/action/operate/InlineChatAction.class */
public class InlineChatAction extends PluginAnAction implements DumbAware {
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m189enum(int a) {
        throw new IllegalArgumentException(String.format(CancelRequestTip.H("*\u0019\r\u001fGO\u0005\u001fT\u0012\u000e\u0013V6+\n\u0004>\u0014\rF\n=,\u0018\u000b\u0005\r\u0019\b\u0018JFD4`L\u0003\u0003EU\u0003k`\u0014G\u001d\u0005\u001e\u0019K\u0005\u0005\u001eA\u0003\u000fJ\u001f\u0004\u0005\u0005"), RequestTimeoutException.H("\u001f"), CancelRequestTip.H("\u0015\n\b_\u0011\b\u0002EN(b\u0003\u0004\u0004\u0001\u0003\bE\u000b\u0002\u0015.(\u0002C\n\u0015\u0015\u0002$1\u0002H9\u001e\u0001\u0004\u0005\u000e)\u0002��\u0015+\t\u0005\u0018\u0006\u0007"), RequestTimeoutException.H("\u0014\"\u0014\u001d:8'$\u0012:\u0012(\u0016>\u001e")));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m189enum(0);
        }
        try {
            InlineChatInlay.disposeInlay();
            handle((Editor) a.getData(CommonDataKeys.EDITOR));
        } catch (Throwable th) {
        }
    }

    public void handle(Editor editor) {
    }
}
