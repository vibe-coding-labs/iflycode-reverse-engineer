package com.aicode.inline.action;

import com.aicode.action.click.PluginAnAction;
import com.aicode.util.Maps;
import com.aicode.util.PropertyUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.AnimatedIcon;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

/* compiled from: jl */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/action/StopAction.class */
public class StopAction extends PluginAnAction {

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final Function0<Unit> f370enum;

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m188enum(int a) {
        throw new IllegalArgumentException(String.format(Maps.H("$+\u0003-Bv\u0006 O5\u0002#H\u0014!<\u0010\u0016Z\u007f\f|\u0012?\u0007(\u00051\u001a7\u001ds\b6!IU&\brT>nY\u0011~\u0018<\u001b N<��'D:\ns\u001a=��<"), PropertyUtils.H("~"), Maps.H("7\u0001?@2Fp=\n\u0010f\u0007<\u001d$.\u0019M?\u0016=\u0001;��}<'\u000b(.0��!\u0003>"), PropertyUtils.H("{1q\n[/F3s-s?w)\u007f")));
    }

    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m188enum(0);
        }
        this.f370enum.invoke();
    }

    public StopAction(Function0<Unit> function0) {
        super(PropertyUtils.H("M$})s\"|"), "", new AnimatedIcon.Default());
        this.f370enum = function0;
    }
}
