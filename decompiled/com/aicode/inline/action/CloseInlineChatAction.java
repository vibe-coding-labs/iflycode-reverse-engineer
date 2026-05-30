package com.aicode.inline.action;

import com.aicode.action.click.PluginAnAction;
import com.aicode.exception.RequestCancelException;
import com.aicode.inline.InlineChatService;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.util.EditorKt;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

/* compiled from: qg */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/action/CloseInlineChatAction.class */
public class CloseInlineChatAction extends PluginAnAction {

    /* renamed from: enum, reason: not valid java name */
    private final Editor f367enum;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m183enum(int a) {
        String H = InlineChatStatusServiceKt.H("\u0007\u0011*\u001dkF'\u0018k\b3\u000ba$\t\r2-sO\u0007n4��5\u00036\u001b9\r\u0013daF7Fa\u000b Ch\u001bEk3E$\u0019/\rI\"$\u001a|\u001b$D)\u0017*\u000f");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = RequestCancelException.H("Z<M7S)");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = InlineChatStatusServiceKt.H("\u0006");
                break;
        }
        objArr[1] = RequestCancelException.H("]6OjU:{\u0010[=\u00123V3V6Q|s\u0016M7_9\n\u0001|\u0018A0l,T6P<|0U'~;P*S5");
        switch (a) {
            case 0:
            default:
                objArr[2] = InlineChatStatusServiceKt.H("}\r)\u000b2]");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = RequestCancelException.H("#[+W6Q\bQ!Y7V.Y?");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m183enum(1);
        }
        InlineChatService.Companion.closeInlineChat(this.f367enum);
        EditorKt.removeEditor(this.f367enum);
    }

    public Editor getEditor() {
        return this.f367enum;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CloseInlineChatAction(@NotNull Editor a) {
        super(InlineChatStatusServiceKt.H("'+\r5\u0006"), RequestCancelException.H("\u001cI-\u001cm"), AllIcons.Actions.Close);
        if (a == null) {
            m183enum(0);
        }
        this.f367enum = a;
    }
}
