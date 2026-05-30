package com.aicode.inline.action;

import com.aicode.action.click.PluginAnAction;
import com.aicode.diff.FileService;
import com.aicode.icons.Icons;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.intellij.openapi.actionSystem.AnActionEvent;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

/* compiled from: ff */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/action/SendMessageAction.class */
public class SendMessageAction extends PluginAnAction {

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final Function0<Unit> f369enum;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m187enum(int a) {
        String H = InlineChatStatusServiceKt.H("��\u0016!\u0016)\u0004/\u0010f\u0005\"\u001a&c\u0007\u0003? )\u0015-D7\u00034\u0002kF\u000f;?HaF?NE/+H~\ru[:L\"\u001f>\u001cI\"$\u001a|\u001b$D)\u0017*\u000f");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = FileService.H("&09;>7#1}");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = InlineChatStatusServiceKt.H("\u0006");
                break;
        }
        objArr[1] = FileService.H(";%3c>$|b\u0006\u0015{/1!<)\u0019A5%69->\u007f\u00113*0\u000b\u0015\u0011!!\"2\u0019)*%0#");
        switch (a) {
            case 0:
            default:
                objArr[2] = InlineChatStatusServiceKt.H("}\r)\u000b2]");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = FileService.H("%72\u0019\r<\u0010 %>%,!:)");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m187enum(1);
        }
        this.f369enum.invoke();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SendMessageAction(@NotNull Function0<Unit> function0) {
        super(FileService.H("\r)1)"), "", Icons.AirPlane);
        if (function0 == null) {
            m187enum(0);
        }
        this.f369enum = function0;
    }
}
