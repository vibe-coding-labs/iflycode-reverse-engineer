package com.aicode.action.batch.node;

import com.aicode.agent.service.CodeCompleteService;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

/* compiled from: fd */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/node/CheckboxTreeCellRenderer.class */
public class CheckboxTreeCellRenderer implements TreeCellRenderer {

    /* renamed from: try, reason: not valid java name */
    private JLabel f108try;

    /* renamed from: byte, reason: not valid java name */
    private JLabel f110byte;

    /* renamed from: enum, reason: not valid java name */
    private JPanel f111enum = new JPanel();

    /* renamed from: float, reason: not valid java name */
    private JCheckBox f109float = new JCheckBox();

    /* compiled from: fd */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/node/CheckboxTreeCellRenderer$Ea.class */
    class Ea extends MouseAdapter {

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ JTree f115enum;

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        public void mouseClicked(MouseEvent a) {
            Rectangle pathBounds;
            TreePath pathForLocation = this.f115enum.getPathForLocation(a.getX(), a.getY());
            if (pathForLocation != null && (pathBounds = this.f115enum.getPathBounds(pathForLocation)) != null && a.getX() < pathBounds.x + 20) {
                DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) pathForLocation.getLastPathComponent();
                if (defaultMutableTreeNode.getUserObject() instanceof CheckedNode) {
                    CheckedNode checkedNode = (CheckedNode) defaultMutableTreeNode.getUserObject();
                    checkedNode.setChecked(!checkedNode.isChecked());
                    this.f115enum.repaint(pathBounds);
                }
            }
        }

        public Ea(JTree jTree) {
            this.f115enum = jTree;
        }
    }

    /* compiled from: fd */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/node/CheckboxTreeCellRenderer$CheckedNode.class */
    public static class CheckedNode {

        /* renamed from: float, reason: not valid java name */
        private String f112float;

        /* renamed from: byte, reason: not valid java name */
        private boolean f113byte;

        /* renamed from: enum, reason: not valid java name */
        private Icon f114enum;

        public void setText(String a) {
            this.f112float = a;
        }

        public String getText() {
            return this.f112float;
        }

        public String toString() {
            return this.f112float;
        }

        public boolean isChecked() {
            return this.f113byte;
        }

        public CheckedNode(boolean z, String a, Icon a2) {
            this.f113byte = z;
            this.f112float = a;
            this.f114enum = a2;
        }

        public Icon getIcon() {
            return this.f114enum;
        }

        public void setChecked(boolean z) {
            this.f113byte = z;
        }

        public void setIcon(Icon a) {
            this.f114enum = a;
        }
    }

    public Component getTreeCellRendererComponent(JTree jTree, Object a, boolean z, boolean z2, boolean z3, int i, boolean z4) {
        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) a;
        if (defaultMutableTreeNode.getUserObject() instanceof CheckedNode) {
            CheckedNode checkedNode = (CheckedNode) defaultMutableTreeNode.getUserObject();
            this.f109float.setSelected(ge(defaultMutableTreeNode));
            this.f110byte.setText(checkedNode.getText());
            this.f108try.setIcon(checkedNode.getIcon());
        }
        return this.f111enum;
    }

    public static void main(String[] strArr) {
        JFrame jFrame = new JFrame(FileExtensionLanguageDetails.H("Tfyg}gxj Qewe"));
        JTree jTree = new JTree(createTreeNodes());
        jTree.setCellRenderer(new CheckboxTreeCellRenderer());
        jTree.addMouseListener(new Ea(jTree));
        jFrame.add(new JScrollPane(jTree));
        jFrame.setSize(400, 300);
        jFrame.setDefaultCloseOperation(3);
        jFrame.setVisible(true);
    }

    public static DefaultMutableTreeNode createTreeNodes() {
        Icon icon = UIManager.getIcon(CodeCompleteService.H("{WwLZ@iu\tEoDh`oPt"));
        DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(new CheckedNode(false, FileExtensionLanguageDetails.H("Wx}t"), icon));
        DefaultMutableTreeNode defaultMutableTreeNode2 = new DefaultMutableTreeNode(new CheckedNode(true, CodeCompleteService.H("E@dEh\u001f+"), icon));
        DefaultMutableTreeNode defaultMutableTreeNode3 = new DefaultMutableTreeNode(new CheckedNode(true, FileExtensionLanguageDetails.H("F\u007f{la7#1"), icon));
        DefaultMutableTreeNode defaultMutableTreeNode4 = new DefaultMutableTreeNode(new CheckedNode(false, CodeCompleteService.H("E@dEh\u001f("), icon));
        DefaultMutableTreeNode defaultMutableTreeNode5 = new DefaultMutableTreeNode(new CheckedNode(false, FileExtensionLanguageDetails.H("F\u007f{la7 2"), icon));
        defaultMutableTreeNode2.add(defaultMutableTreeNode3);
        defaultMutableTreeNode4.add(defaultMutableTreeNode5);
        defaultMutableTreeNode.add(defaultMutableTreeNode2);
        defaultMutableTreeNode.add(defaultMutableTreeNode4);
        return defaultMutableTreeNode;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private boolean ge(DefaultMutableTreeNode a) {
        return (a.getUserObject() instanceof CheckedNode) && ((CheckedNode) a.getUserObject()).isChecked();
    }

    public CheckboxTreeCellRenderer() {
        this.f109float.setEnabled(true);
        this.f110byte = new JLabel();
        this.f111enum.setLayout(new FlowLayout(0, 0, 0));
        this.f111enum.setOpaque(false);
        this.f108try = new JLabel();
        this.f111enum.add(this.f109float);
        this.f111enum.add(this.f108try);
        this.f111enum.add(this.f110byte);
    }
}
