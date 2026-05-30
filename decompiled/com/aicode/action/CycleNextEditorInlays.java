package com.aicode.action;

import Q.AbstractC0001sa;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.service.EditorManagerService;
import com.aicode.util.Application;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

/* compiled from: sd */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/CycleNextEditorInlays.class */
public class CycleNextEditorInlays extends AbstractC0001sa {
    public static final String ID = Application.H("ENJepr!okrEO@ht{Hlu{jc");

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m22enum(int a) {
        throw new IllegalArgumentException(String.format(Application.H("Eubs#(ov#f{e)JAczC;!O��i{qaajqc\\\r\"#k<4xC\u0006!t'/g7byae\tDay,md\"wo\u007f|"), OpenTelemetryUtil.H("e q(}$"), Application.H("ylm#nleAI`)yx`~JH+DpixrAijelNgyc}Hlu{jc"), OpenTelemetryUtil.H("3G/v(a,A'l5}8")));
    }

    @Override // Q.AbstractC0001sa
    public /* bridge */ /* synthetic */ void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        super.actionPerformed(anActionEvent);
    }

    @Override // Q.AbstractC0001sa
    public /* bridge */ /* synthetic */ void update(@NotNull AnActionEvent anActionEvent) {
        super.update(anActionEvent);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // Q.AbstractC0001sa
    public boolean doCycleAction(@NotNull Editor a) {
        if (a == null) {
            m22enum(0);
        }
        EditorManagerService editorManagerService = EditorManagerService.getInstance();
        if (editorManagerService.hasNextInlaySet(a)) {
            editorManagerService.showNextInlaySet(a);
            return true;
        }
        return false;
    }
}
