package com.aicode.ui;

import com.aicode.diff.FileService;
import com.aicode.inline.action.SendMessageAction;
import com.aicode.inline.action.StopAction;
import com.aicode.util.IndentLineUtil;
import java.awt.CardLayout;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.swing.JPanel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

/* compiled from: xb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/ui/SendStopActionButtonPanel.class */
public final class SendStopActionButtonPanel extends JPanel {

    /* renamed from: case, reason: not valid java name */
    @NotNull
    private final com.intellij.openapi.actionSystem.impl.ActionButton f643case;

    /* renamed from: final, reason: not valid java name */
    @NotNull
    private final StopAction f644final;

    /* renamed from: try, reason: not valid java name */
    @NotNull
    private static final String f645try = Arrays.toString(IndentLineUtil.H("倪歋").getBytes(StandardCharsets.UTF_8));

    /* renamed from: float, reason: not valid java name */
    @NotNull
    private static final String f646float = Arrays.toString(FileService.H("厌過").getBytes(StandardCharsets.UTF_8));

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private final com.intellij.openapi.actionSystem.impl.ActionButton f647byte;

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final SendMessageAction f648enum;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m371enum(int a) {
        String H = IndentLineUtil.H("?S\u0018UY\u000e\u0001DSJ[\u0019iV1O��e\u000bM%6\u001eP\u0007K\u0007P/a\u000b\u0006I\u0014��\u000bUE\u0012\u000b}tA\u0015\u001d\u0011\u0003D\u0006^TE%a[F\u0011\u000b\u0011U\u001aE");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 2:
            default:
                objArr[0] = FileService.H("\u0015%,<9=)3\u007f");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = IndentLineUtil.H(",`\u0015G��B\u0010NF\u001b");
                break;
        }
        objArr[1] = FileService.H("41!n2\u0019\u0001=$ x-#q\u001f:#\u00172095\u0016&#7#1\u000f\u0014\u0007$-1\u001d5(8#");
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[2] = IndentLineUtil.H("HB\u0011I\u0002\u0017");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = FileService.H("-$0:2\u0016>&\u001d8 22!");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public void showSendButton(@NotNull Function0<Boolean> function0) {
        if (function0 == null) {
            m371enum(2);
        }
        getLayout().show(this, f646float);
        setEnabled(((Boolean) function0.invoke()).booleanValue());
        this.f643case.setEnabled(((Boolean) function0.invoke()).booleanValue());
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SendStopActionButtonPanel(@NotNull Function0<Unit> function0, @NotNull Function0<Unit> function02) {
        super(new CardLayout());
        if (function0 == null) {
            m371enum(0);
        }
        if (function02 == null) {
            m371enum(1);
        }
        this.f648enum = new SendMessageAction(function0);
        com.intellij.openapi.actionSystem.impl.ActionButton button = ActionButton.button(this.f648enum);
        button.setEnabled(false);
        this.f643case = button;
        this.f644final = new StopAction(function02);
        this.f647byte = ActionButton.button(this.f644final);
        setOpaque(false);
        add(this.f643case, f646float);
        add(this.f647byte, f645try);
    }

    public void showStopButton() {
        getLayout().show(this, f645try);
        setEnabled(true);
        this.f647byte.setEnabled(true);
    }
}
