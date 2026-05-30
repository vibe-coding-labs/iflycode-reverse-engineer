package com.aicode.action.batch;

import com.aicode.action.batch.node.AbstractNode;
import com.aicode.diff.GenericUtils;
import com.aicode.inline.ide.IdeAction;
import com.intellij.ui.ColoredTreeCellRenderer;
import javax.swing.JTree;
import org.jetbrains.annotations.NotNull;

/* compiled from: af */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/TreeCellRenderer.class */
public class TreeCellRenderer extends ColoredTreeCellRenderer {
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m48enum(int a) {
        throw new IllegalArgumentException(String.format(GenericUtils.H("\u000388\"446$8v;.w\u001f\f%!\u0013+:t0\u0012\u000b6->>'>\u0007]t~)uz=\u0002Lv(vu!z>.78s5+8s9=p->7?"), IdeAction.H("\u001bV\u001dV"), GenericUtils.H("\u0001\u0005)c2204\u0011\u0018|:9&3=\nC1:,3:u\u0007)!)\u0010>( \u0001>64&9>!"), IdeAction.H("G\u0013^\u0012B5z\u0015A'J\u0002I=A\u0016W\nV\u001dA")));
    }

    public void customizeCellRenderer(@NotNull JTree tree, Object value, boolean z, boolean z2, boolean z3, int i, boolean z4) {
        if (tree == null) {
            m48enum(0);
        }
        if (!(value instanceof AbstractNode)) {
            return;
        }
        ((AbstractNode) value).render(this);
    }
}
