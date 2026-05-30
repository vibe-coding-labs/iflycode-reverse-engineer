package com.aicode.action.batch;

import com.aicode.action.batch.node.FileNode;
import com.aicode.action.batch.node.TreeRootNode;
import com.intellij.ui.treeStructure.Tree;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/* compiled from: gh */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/ResultTree.class */
public class ResultTree extends Tree {
    public static final int DEFAULT_ROW_HEIGHT = 25;

    public static DefaultTreeModel createModel(String a, List<FileNode> list) {
        TreeRootNode treeRootNode = new TreeRootNode(a, list);
        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(treeRootNode);
        defaultTreeModel.setRoot(treeRootNode);
        return defaultTreeModel;
    }

    public ResultTree() {
        expandRow(0);
        setRootVisible(false);
        setShowsRootHandles(true);
        setCellRenderer(new TreeCellRenderer());
        setRowHeight(25);
        validate();
        repaint();
    }

    public ResultTree(TreeNode a) {
        super(a);
        setBorder(new EmptyBorder(10, 0, 0, 0));
        expandRow(0);
        setRootVisible(true);
        setShowsRootHandles(true);
        setCellRenderer(new TreeCellRenderer());
        setRowHeight(25);
        validate();
        repaint();
    }
}
