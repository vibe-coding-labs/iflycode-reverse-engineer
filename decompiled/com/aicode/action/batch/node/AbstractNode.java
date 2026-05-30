package com.aicode.action.batch.node;

import com.aicode.action.batch.TreeCellRenderer;
import com.aicode.agent.service.CodeCompleteService;
import com.intellij.util.ui.UIUtil;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/* compiled from: wf */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/node/AbstractNode.class */
public abstract class AbstractNode extends DefaultMutableTreeNode {
    public abstract void render(TreeCellRenderer treeCellRenderer);

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String spaceAndThinSpace() {
        return " " + (UIUtil.getLabelFont().canDisplay((char) 8201) ? String.valueOf((char) 8201) : CodeCompleteService.H(","));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public int getAllFileNodeChildCount() {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < getChildCount()) {
            TreeNode childAt = getChildAt(i3);
            i3++;
            i += childAt.getChildCount();
            i2 = i3;
        }
        return i;
    }
}
