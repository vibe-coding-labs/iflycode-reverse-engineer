package com.aicode.action.batch.node;

import com.aicode.action.batch.TreeCellRenderer;
import com.aicode.diff.FileInfo;
import com.aicode.ui.FontKt;
import com.intellij.ui.IconManager;
import com.intellij.ui.SimpleTextAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/* compiled from: ad */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/node/FileNode.class */
public class FileNode extends AbstractNode {

    /* renamed from: byte, reason: not valid java name */
    private final String f116byte;

    /* renamed from: enum, reason: not valid java name */
    private static final Map<String, FileNode> f117enum = new HashMap();

    public static FileNode getFileNode(String a) {
        return f117enum.get(a);
    }

    public FileNode(String a) {
        this.f116byte = a;
        this.children = new Vector();
    }

    public static void clear() {
        f117enum.clear();
    }

    public String getFileName() {
        return this.f116byte;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.action.batch.node.AbstractNode
    public void render(TreeCellRenderer a) {
        TreeCellRenderer treeCellRenderer;
        if (this.f116byte.contains(FileInfo.H("3\u0015w\bv"))) {
            treeCellRenderer = a;
            treeCellRenderer.setIcon(IconManager.getInstance().getIcon(FontKt.H("}+\u0001\u0015--|!=* *}-5$ <|=3>"), FileNode.class));
        } else if (!this.f116byte.contains(FileInfo.H("\u0012w\u0017y"))) {
            treeCellRenderer = a;
            treeCellRenderer.setIcon(IconManager.getInstance().getIcon(FileInfo.H("[x\u0007f\u000b~\u001a22P\u0017hXn\u001ft>]\u0013xQe\bp"), FileNode.class));
        } else {
            treeCellRenderer = a;
            treeCellRenderer.setIcon(IconManager.getInstance().getIcon(FontKt.H("B;:+2;a\u0017\n<! `!!0+1+\u000b*<;|=3>"), FileNode.class));
        }
        treeCellRenderer.append(this.f116byte);
        a.append(spaceAndThinSpace() + spaceAndThinSpace(), SimpleTextAttributes.GRAYED_BOLD_ATTRIBUTES);
        a.setToolTipText("");
    }

    public void setFileNode(FileNode a) {
        f117enum.put(this.f116byte, a);
    }
}
