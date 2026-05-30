package com.aicode.action.batch.node;

import com.aicode.action.batch.TreeCellRenderer;
import java.util.Iterator;
import java.util.List;

/* compiled from: kj */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/node/TreeRootNode.class */
public class TreeRootNode extends AbstractNode {
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public TreeRootNode(String a, List<FileNode> list) {
        FileNode fileNode = new FileNode(a);
        fileNode.setUserObject(a);
        Iterator<FileNode> it = list.iterator();
        while (it.hasNext()) {
            FileNode next = it.next();
            it = it;
            fileNode.add(next);
        }
        add(fileNode);
    }

    @Override // com.aicode.action.batch.node.AbstractNode
    public void render(TreeCellRenderer treeCellRenderer) {
    }
}
