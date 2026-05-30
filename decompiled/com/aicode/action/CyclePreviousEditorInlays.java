package com.aicode.action;

import Q.AbstractC0001sa;
import com.aicode.service.EditorManagerService;
import com.aicode.util.PropertyUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

/* compiled from: hi */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/CyclePreviousEditorInlays.class */
public class CyclePreviousEditorInlays extends AbstractC0001sa {
    public static final String ID = PropertyUtils.H("\u0007X\bs2dcy)d\u0007Y\u001ci+o\nz7m(u");

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m24enum(int a) {
        throw new IllegalArgumentException(String.format(PropertyUtils.H("��d1t&y#nl}c)Av\u0015c5X;u+0\u0002D\"f*u f9<j?$'qi\u0001\u0010cbe9%! o#sKR#on{&45y=j"), PropertyUtils.H("&p2x>t"), PropertyUtils.H("z(}]D9d(t1,*\u007f9s8n~E\u001eS*t\u001bn3w$u%t.X%o!k\nz7m(u"), PropertyUtils.H("c\u0004\u007f5x\"|\u0002w/e>h")));
    }

    @Override // Q.AbstractC0001sa
    public /* bridge */ /* synthetic */ void update(@NotNull AnActionEvent anActionEvent) {
        super.update(anActionEvent);
    }

    @Override // Q.AbstractC0001sa
    public /* bridge */ /* synthetic */ void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        super.actionPerformed(anActionEvent);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // Q.AbstractC0001sa
    public boolean doCycleAction(@NotNull Editor a) {
        if (a == null) {
            m24enum(0);
        }
        EditorManagerService editorManagerService = EditorManagerService.getInstance();
        if (editorManagerService.hasPreviousInlaySet(a)) {
            editorManagerService.showPreviousInlaySet(a);
            return true;
        }
        return false;
    }
}
