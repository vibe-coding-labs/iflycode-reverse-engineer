package com.aicode.action.click;

import com.aicode.exception.RequestCancelException;
import com.aicode.util.PropertyUtils;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.util.NlsActions;
import java.util.function.Supplier;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: th */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/click/PluginAnAction.class */
public abstract class PluginAnAction extends AnAction {
    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m56enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = RequestCancelException.H("q%U H'V+\u001e?P*_X|:I\u0014M3^uJ<\ryl\u0003I?Vc\u0011tKx\u001f7vW\u001a+>RAuH7K+\u001e7P,\u00141ZxJ6P7");
                i = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                H = PropertyUtils.H("L\u0015.b��l'pa{,jd4\u0016\u0005kjy%62&i?oCZ#oCF$b#s%<#o w");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            default:
                i2 = 3;
                break;
            case 1:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = RequestCancelException.H("=F6U>V;p&D/");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = PropertyUtils.H("-v&3 \u007f*qh>]D-m>o+=(p%x\b\u001b\u001cw\u0016S(x\u0017o\n\u007f9s#u");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = RequestCancelException.H("\u001cwlI\\3G,R4\u0017>\\,y\u0018Qws\u001b[6Nmh3K>V6u=~;P*S5");
                i4 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = PropertyUtils.H("u.h\rx\u0017]#u6D%w\"d\u001ft?\u007f-\u007f");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = RequestCancelException.H("\u00031J*He");
                break;
            case 1:
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            default:
                throw new IllegalArgumentException(format);
            case 1:
                throw new IllegalStateException(format);
        }
    }

    public PluginAnAction(@NlsActions.ActionText @Nullable String text, @NlsActions.ActionDescription @Nullable String description, @Nullable Icon a) {
        super(text, description, a);
    }

    @NotNull
    public ActionUpdateThread getActionUpdateThread() {
        ActionUpdateThread actionUpdateThread = ActionUpdateThread.EDT;
        if (actionUpdateThread == null) {
            m56enum(1);
        }
        return actionUpdateThread;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PluginAnAction(@NotNull Supplier<String> supplier, @Nullable Icon a) {
        super(supplier, a);
        if (supplier == null) {
            m56enum(0);
        }
    }

    public PluginAnAction() {
    }
}
