package com.aicode.action.batch;

import com.aicode.agent.service.GitReviewService;
import com.aicode.message.BasicActionsBundle;
import com.aicode.ui.ActionButton;
import com.intellij.debugger.settings.NodeRendererSettings;
import com.intellij.debugger.settings.RendererConfiguration;
import com.intellij.debugger.ui.tree.render.NodeRenderer;
import com.intellij.ide.util.ElementsChooser;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.ConfigurableUi;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.JBTable;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: gi */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/ExcludeMethodConfigurable.class */
public final class ExcludeMethodConfigurable extends JPanel implements ConfigurableUi<NodeRendererSettings>, Disposable {

    /* renamed from: float, reason: not valid java name */
    private final JBTable f70float;

    /* renamed from: byte, reason: not valid java name */
    private final ElementsChooser<NodeRenderer> f71byte;

    /* renamed from: enum, reason: not valid java name */
    private NodeRenderer f72enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m47enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = ActionButton.H("4 \n\u000b*\u000b :D\u0013\u0010\u001b\u0001\u001c\nT@\fl}\u001dT\u0002��\u0014\tH\u001c\t\bT\u001c\u0016\u001d\u0015\b\rY\u0003\u0002\b\u0012");
                i = a;
                break;
            case 1:
            case 2:
            case 3:
                do {
                } while (0 != 0);
                H = GitReviewService.H("\r\u0005mD\f?2\u0013g\u001a'\u0001q*\u000e\u001454\u001c>-Z \n>\u0016&\u00154\u001e\u0015|lU9Vb\u0016+Vf\u000b\u007fO%M(\u000b5\th\u001d.\u000ea\u0018/Q?\u001f%\u001e");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            default:
                i2 = 2;
                break;
            case 1:
            case 2:
            case 3:
                i2 = 3;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = ActionButton.H("=\u0016\u000eM\u0019\u0004\u0014\u001b\n��P\u0005\u001d8?\u000b\u0010Z\r\b\u0007\r\u001cJ::;\u0002\u0001\u000b\u0010*\u0018\u001c\u001a\t\u00187\u0001\u001d\u000f\t\u001d\u0016\u000b\f\u0015\b\u001b");
                i3 = a;
                break;
            case 1:
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[0] = GitReviewService.H("2\u001f>\u00058\u0004.\u0001");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = ActionButton.H("\u0013\u000b\u0007*\u000f\u0017\u0013\u0016\u0003\u0012\n\n");
                i4 = a;
                break;
            case 1:
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[1] = GitReviewService.H("\t/\u0016n\u001b��1.\u001e5D-\u0014?\u0019/\u0015H>*\u0004)\u0019m<5\u0015/\r5\u000f\u001b\b1\u0016)\u0019\u000b\u001c/\u001c(\u001d?\u00030\b%\u0017");
                i4 = a;
                break;
        }
        switch (i4) {
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = ActionButton.H("\u0018\u001d\u0007\b\u0007");
                break;
            case 2:
                objArr[2] = GitReviewService.H("(\t\f\u0015.\u00187\u0003,\u0016");
                break;
            case 3:
                objArr[2] = ActionButton.H("\u000b\b\u0004\u0001\n");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            default:
                throw new IllegalStateException(format);
            case 1:
            case 2:
            case 3:
                throw new IllegalArgumentException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private void Ef(RendererConfiguration a) {
        int elementCount = this.f71byte.getElementCount();
        ArrayList arrayList = new ArrayList(elementCount);
        int i = 0;
        int i2 = 0;
        while (i < elementCount) {
            ElementsChooser<NodeRenderer> elementsChooser = this.f71byte;
            int i3 = i2;
            i2++;
            arrayList.add((NodeRenderer) elementsChooser.getElementAt(i3));
            i = i2;
        }
        a.setRenderers(arrayList);
    }

    private void ff() {
        this.f71byte.getEmptyText().setText(BasicActionsBundle.message(GitReviewService.H("3\u0004\"\u0011\"\u0017n\u0019\u0006((\u0018d\u0004,\u00109X7\u001d\"\u001ex\b=\u001d*\b,\u0016o\u000e+\r.\u001c"), new Object[0]));
        this.f71byte.addElementsMarkListener((v0, v1) -> {
            v0.setEnabled(v1);
        });
        this.f71byte.addListSelectionListener(a -> {
            if (!a.getValueIsAdjusting()) {
                Ae(this.f71byte.getSelectedElements());
            }
        });
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private void vf(NodeRenderer a) {
        if (this.f72enum == a) {
            return;
        }
        this.f72enum = a;
    }

    @NotNull
    public JComponent getComponent() {
        if (this == null) {
            m47enum(0);
        }
        return this;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private ArrayList<String> wE(JBTable a) {
        DefaultTableModel model = a.getModel();
        ArrayList<String> arrayList = new ArrayList<>();
        int i = 0;
        int i2 = 0;
        while (i < model.getRowCount()) {
            int i3 = i2;
            i2++;
            arrayList.add(String.valueOf(model.getValueAt(i3, 0)));
            i = i2;
        }
        return arrayList;
    }

    public static Object[][] toArray(Map<?, ?> map) {
        return (Object[][]) map.entrySet().stream().map(a -> {
            return new Object[]{a.getKey(), a.getValue()};
        }).toArray(a2 -> {
            return new Object[a2];
        });
    }

    public boolean isModified(@NotNull NodeRendererSettings a) {
        if (a == null) {
            m47enum(2);
        }
        RendererConfiguration customRenderers = a.getCustomRenderers();
        this.f71byte.getElementCount();
        customRenderers.getRendererCount();
        return true;
    }

    public void reset(@NotNull NodeRendererSettings a) {
        if (a == null) {
            m47enum(3);
        }
        this.f71byte.removeAllElements();
        RendererConfiguration customRenderers = a.getCustomRenderers();
        ArrayList arrayList = new ArrayList(1);
        customRenderers.iterateRenderers(a2 -> {
            NodeRenderer clone = a2.clone();
            this.f71byte.addElement(clone, clone.isEnabled());
            if (arrayList.isEmpty()) {
                arrayList.add(clone);
            }
            return true;
        });
        this.f71byte.selectElements(arrayList);
        Ae(arrayList);
    }

    public void apply(@NotNull NodeRendererSettings a) {
        if (a == null) {
            m47enum(1);
        }
        Ef(a.getCustomRenderers());
        a.fireRenderersChanged();
    }

    public ExcludeMethodConfigurable() {
        super(new BorderLayout(5, 0));
        this.f72enum = null;
        this.f71byte = new ElementsChooser<>(true);
        ff();
        this.f70float = new JBTable(new DefaultTableModel(toArray(new HashMap()), new Object[]{GitReviewService.H("早沾吗科")}));
        this.f70float.getModel().addRow(new Object[]{ActionButton.H("\u0001\u0018\u0010\r\u001f\u0016\u0007\n")});
        this.f70float.getModel().addRow(new Object[]{GitReviewService.H("#\u0017$\u0002lD")});
        this.f70float.getModel().addRow(new Object[]{ActionButton.H("\u0004\u001c\u0019\u0003\u0001\f")});
        this.f70float.getModel().addRow(new Object[]{GitReviewService.H(">\u0013$\u001f\u007fS")});
        this.f70float.getModel().addRow(new Object[]{ActionButton.H("��\u0016\r\u0010")});
        this.f70float.getModel().addRow(new Object[]{GitReviewService.H("(\u0007%\nvR")});
        this.f70float.getModel().addRow(new Object[]{ActionButton.H("\u0014\u00150\r\u001f\u001e\n\u0019")});
        this.f70float.getModel().addRow(new Object[]{GitReviewService.H("\"\u0010>\u001e\u0013\u0004~D")});
        this.f70float.getEmptyText().setText(BasicActionsBundle.message(ActionButton.H("\r\n\u0011\u0002\u0017+x\u0006\u001f\u0001\f\u0001]\u001b\u001a\f\u000bl,\u000b\u0007\u001b[\u0002\u0005\u000b\u001e\u0013\u0018\u0011@\u0016\u0004\u0010\u000e\u001aW\u0019\u0012\u001c\n"), new Object[0]));
        add(ToolbarDecorator.createDecorator(this.f70float).setAddAction(anActionButton -> {
            this.f70float.getModel().addRow(new Object[]{""});
        }).setRemoveAction(anActionButton2 -> {
            int selectedRow = this.f70float.getSelectedRow();
            String str = (String) this.f70float.getModel().getValueAt(selectedRow, this.f70float.getSelectedColumn());
            if (!StringUtils.equals(ActionButton.H("\u0001\u0018\u0010\r\u001f\u0016\u0007\n"), str) && !StringUtils.equals(GitReviewService.H("/\u001b'\u00016\u001e"), str)) {
                this.f70float.getModel().removeRow(selectedRow);
                this.f70float.getSelectionModel().clearSelection();
            }
        }).disableUpAction().disableDownAction().createPanel());
    }

    public void dispose() {
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private void Ae(List<NodeRenderer> list) {
        if (list.size() == 1) {
            vf(list.get(0));
        } else {
            vf(null);
        }
    }

    public ArrayList<String> getBody() {
        return wE(this.f70float);
    }
}
