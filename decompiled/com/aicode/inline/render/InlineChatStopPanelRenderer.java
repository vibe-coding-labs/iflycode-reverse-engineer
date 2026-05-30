package com.aicode.inline.render;

import cn.hutool.core.util.StrUtil;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.Maps;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorCustomElementRenderer;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.AnimatedIcon;
import com.intellij.ui.JBColor;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: vj */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatStopPanelRenderer.class */
public class InlineChatStopPanelRenderer extends JPanel implements EditorCustomElementRenderer, Disposable {

    /* renamed from: case, reason: not valid java name */
    private JComponent f467case;

    /* renamed from: final, reason: not valid java name */
    @Nullable
    private Inlay<?> f468final;

    /* renamed from: try, reason: not valid java name */
    private final Function0<Unit> f469try;

    /* renamed from: float, reason: not valid java name */
    private int f470float;

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private final Editor f471byte;

    /* renamed from: enum, reason: not valid java name */
    private int f472enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m217enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
                H = Maps.H("\t\u0006\b&��4\u0006 O5\u000b*b>':\u0014\u0012��%>N\u0005(\u001c3\u001c(%\b\u0012|Hv\u0017\u007fM>5OA+Av\u0016y\t-��;D6\u001c;D:\ns\u001a=��<");
                i = a;
                break;
            case 4:
                do {
                } while (0 != 0);
                H = Maps.H("\u0012 \u001a= '\u001d!q��\u0005(\u0007<��xH\"}J\u0017x\u0002&\u0016-D6\u001c;D*\u0016;\u0011*\u0001s\u001a=��<");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
                i2 = 3;
                break;
            case 4:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = Maps.H("\u00149\u0001*\n,7&\u0017(�� \u0015*��5");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = Maps.H("\n7\u001d<\u0003\"");
                i3 = a;
                break;
            case 2:
                objArr[0] = Maps.H(",\u000b5\u001f<\u001a-\u0002$");
                i3 = a;
                break;
            case 3:
                objArr[0] = Maps.H(".\u0019>");
                i3 = a;
                break;
            case 4:
                objArr[0] = Maps.H("\u00077/Q\b<\u00033\u0011,}\u0007\u001b%\u0007<\u0014b#\b\u000e8\n!K\u0011\u0003=:\u0001\u0001\u001b\u00072\u0011\n\u00107\u0003\u001f\u00056\u0016#6=\u00017\u0011:\t\"");
                i3 = a;
                break;
            case 5:
            case 6:
            case 7:
                objArr[0] = Maps.H(":\u001a$\r)");
                i3 = a;
                break;
            case 8:
                objArr[0] = Maps.H("7");
                i3 = a;
                break;
            case 9:
                objArr[0] = Maps.H("\u00109\u0001(\u0001,=6\u0013!\u0003>");
                i3 = a;
                break;
            case 10:
                objArr[0] = Maps.H("\u0007*\u001c,2;\u0010*\u00061\u0001<\t#");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
                objArr[1] = Maps.H("\u00077/Q\b<\u00033\u0011,}\u0007\u001b%\u0007<\u0014b#\b\u000e8\n!K\u0011\u0003=:\u0001\u0001\u001b\u00072\u0011\n\u00107\u0003\u001f\u00056\u0016#6=\u00017\u0011:\t\"");
                i4 = a;
                break;
            case 4:
                do {
                } while (0 != 0);
                objArr[1] = Maps.H("(\u0001,*7\u001d<\u0003\"");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            default:
                objArr[2] = Maps.H("S:\u001a!\u0018n");
                break;
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[2] = Maps.H("&\n\u0019\u001f#'0\u0006?\u0010:\t>");
                break;
            case 4:
                break;
            case 5:
                objArr[2] = Maps.H(":\u00054\u0010\u0018\r<\u0007'-6?:\f-��#");
                break;
            case 6:
                objArr[2] = Maps.H("\u00068\b;;*\r?\u001b;-6?:\f-��#");
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = Maps.H("#\u0015!\u0002$");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
                throw new IllegalArgumentException(format);
            case 4:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void inAllChildren(@NotNull JComponent component, @NotNull Function1<? super JComponent, Unit> function1) {
        if (component == null) {
            m217enum(2);
        }
        if (function1 == null) {
            m217enum(3);
        }
        function1.invoke(component);
        JComponent[] components = component.getComponents();
        int length = components.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            JComponent jComponent = components[i2];
            if (jComponent instanceof JComponent) {
                inAllChildren(jComponent, function1);
            }
            i2++;
            i = i2;
        }
    }

    public int calcHeightInPixels(@NotNull Inlay a) {
        if (a == null) {
            m217enum(6);
        }
        return this.f471byte.getLineHeight();
    }

    /* compiled from: vj */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatStopPanelRenderer$P.class */
    class P extends MouseAdapter {

        /* renamed from: float, reason: not valid java name */
        public final /* synthetic */ Color f475float;

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ Function0 f477enum;

        public void mouseClicked(MouseEvent mouseEvent) {
            this.f477enum.invoke();
        }

        public P(Function0 function0, Color color) {
            this.f477enum = function0;
            this.f475float = color;
        }

        public void mouseExited(MouseEvent mouseEvent) {
            InlineChatStopPanelRenderer.this.f467case.setForeground(this.f475float);
        }

        public void mouseEntered(MouseEvent mouseEvent) {
            InlineChatStopPanelRenderer.this.f467case.setForeground(JBColor.BLUE);
        }
    }

    public int getOffset() {
        return this.f470float;
    }

    public void setOffset(int a) {
        this.f470float = a;
    }

    public final void redraw() {
        ApplicationManager.getApplication().invokeLater(() -> {
            Ca(this);
        });
    }

    /* compiled from: vj */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatStopPanelRenderer$N.class */
    class N extends ComponentAdapter {

        /* renamed from: byte, reason: not valid java name */
        public final /* synthetic */ InlineChatStopPanelRenderer f473byte;

        public N(InlineChatStopPanelRenderer inlineChatStopPanelRenderer) {
            this.f473byte = inlineChatStopPanelRenderer;
        }

        public void componentResized(ComponentEvent componentEvent) {
            this.f473byte.redraw();
        }
    }

    public void setLineBreBlock(int a) {
        this.f472enum = a;
    }

    private static void Ca(InlineChatStopPanelRenderer a) {
        if (a.getSize().height != a.getMinimumSize().height) {
            a.setSize(new Dimension(500, a.getMinimumSize().height));
            Inlay<?> inlay = a.getInlay();
            if (inlay != null) {
                inlay.update();
            }
            a.revalidate();
            a.repaint();
        }
    }

    @Nullable
    public Inlay<?> getInlay() {
        return this.f468final;
    }

    public InlineChatStopPanelRenderer(int lineBreBlock, @NotNull Disposable parentDisposable, @NotNull Editor editor, Function0<Unit> function0) {
        if (parentDisposable == null) {
            m217enum(0);
        }
        if (editor == null) {
            m217enum(1);
        }
        this.f470float = 0;
        this.f471byte = editor;
        this.f469try = function0;
        setLayout(new BoxLayout(this, 0));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(0));
        setBackground(JBColor.WHITE);
        setVisible(true);
        this.f472enum = lineBreBlock;
        add(Box.createHorizontalStrut(lineBreBlock * 8));
        add(new JLabel(new AnimatedIcon.Default(), 0));
        JLabel jLabel = new JLabel(Maps.H("\u001a\tQx+;\u000b6畷扄乘iC#"));
        add(jLabel);
        jLabel.setForeground(JBColor.GRAY);
        jLabel.setBackground(JBColor.GRAY);
        jLabel.setPreferredSize(new Dimension(100, 30));
        this.f467case = new JLabel(BasicActionsBundle.message(Maps.H(":\u000b5\r6\u0016a\u00070\u0012;\u0013b\t:\f6\u0004z\u0001,Gw"), StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(Maps.H("\u0010$#3\u000b6J\u0011\u0003=:\u0001\u0001\u001b\u00072\u0011w-6\u001f&\n=0'\\u; ��#)7\u0001 Pm")), KeymapUtil.getFirstMouseShortcutText(Maps.H("\u0010$#3\u000b6J\u0011\u0003=:\u0001\u0001\u001b\u00072\u0011w-6\u001f&\n=0'\\u; ��#)7\u0001 Pm")))));
        this.f467case.setForeground(JBColor.GRAY);
        this.f467case.setPreferredSize(new Dimension(100, 30));
        add(this.f467case);
        inAllChildren(this, a -> {
            a.addComponentListener(new N(this));
            return Unit.INSTANCE;
        });
        this.f467case.addMouseListener(new P(function0, this.f467case.getForeground()));
        Disposer.register(parentDisposable, this);
    }

    @NotNull
    public final Editor getEditor() {
        Editor editor = this.f471byte;
        if (editor == null) {
            m217enum(4);
        }
        return editor;
    }

    public int calcWidthInPixels(@NotNull Inlay a) {
        if (a == null) {
            m217enum(5);
        }
        return 500;
    }

    public void dispose() {
        if (this.f468final != null) {
            this.f468final.dispose();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void paint(@NotNull Inlay inlay, @NotNull Graphics g, @NotNull Rectangle targetRegion, @NotNull TextAttributes a) {
        if (inlay == null) {
            m217enum(7);
        }
        if (g == null) {
            m217enum(8);
        }
        if (targetRegion == null) {
            m217enum(9);
        }
        if (a == null) {
            m217enum(10);
        }
        Rectangle bounds = inlay.getBounds();
        if (bounds != null && !Intrinsics.areEqual(getBounds(), bounds)) {
            setBounds(bounds);
            revalidate();
            repaint();
        }
    }

    public void createInlay(int a) {
        setOffset(a);
        this.f468final = (Inlay) Objects.requireNonNull(this.f471byte.getInlayModel().addBlockElement(a, false, true, Integer.MAX_VALUE, this));
    }

    public void setInlay(@Nullable Inlay<?> inlay) {
        this.f468final = inlay;
    }

    public int getLineBreBlock() {
        return this.f472enum;
    }
}
