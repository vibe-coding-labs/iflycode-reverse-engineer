package com.aicode.inline.render;

import cn.hutool.core.util.StrUtil;
import com.aicode.agent.service.CodeCompleteService;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.inline.ide.IdeAction;
import com.aicode.language.AICodeLanguageInfo;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.StringUtils;
import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorCustomElementRenderer;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.util.Disposer;
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

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatErrorPanelRenderer.class */
public class InlineChatErrorPanelRenderer extends JPanel implements EditorCustomElementRenderer, Disposable {

    /* renamed from: for, reason: not valid java name */
    private int f453for;

    /* renamed from: if, reason: not valid java name */
    private JComponent f454if;

    /* renamed from: case, reason: not valid java name */
    private int f455case;

    /* renamed from: final, reason: not valid java name */
    @Nullable
    private Inlay<?> f456final;

    /* renamed from: try, reason: not valid java name */
    private final Function0<Unit> f457try;

    /* renamed from: float, reason: not valid java name */
    private JComponent f458float;

    /* renamed from: byte, reason: not valid java name */
    private final Function0<Unit> f459byte;

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final Editor f460enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m216enum(int a) {
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
                H = AICodeLanguageInfo.H("\u0005\u0007!\u0002.\u0017*\u0001o\u0018\u0006*b3\u0005\u0015*!\u001a24I(\b7\u00155\f\u000e.9ZcP<Yf\u0018\u001eij\rjP=_\"\u000b+\u001do\u00107\u001do\u001c!U1\u001b+\u001a");
                i = a;
                break;
            case 4:
                do {
                } while (0 != 0);
                H = IdeAction.H("\"g\u0004T0@#hX^\u001dG\rA\u001c\u0013\u007fbE\u0005\u0017\u000f\u0002Q\u0015Yx}��PD]\u000bQ\u001aV\u0016\u0013\u0001Q\u0014_");
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
                objArr[0] = AICodeLanguageInfo.H("?\u001f*\f!\n\u001c��<\u000e+\u0006>\f+\u0013");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = IdeAction.H("\u001dW\u0006P\u0017A");
                i3 = a;
                break;
            case 2:
                objArr[0] = AICodeLanguageInfo.H("\n \u00134\u001a1\u000b)\u0002");
                i3 = a;
                break;
            case 3:
                objArr[0] = IdeAction.H("B\r]");
                i3 = a;
                break;
            case 4:
                objArr[0] = AICodeLanguageInfo.H("\u001d\u00065m\u0012\"\u00191\u000b\nq1\u00074��+\u0011w\u001b\u001f%/\u001f6Z\u0006\u0010*\u001e\u0016,\f\u0016%\u0001\u000b\r=\u0011*9.\u0010=\u0005\u001d\u001b*\u0011:\u001c\"\u0004");
                i3 = a;
                break;
            case 5:
            case 6:
            case 7:
                objArr[0] = IdeAction.H("Z\u0001H\u0019J");
                i3 = a;
                break;
            case 8:
                objArr[0] = AICodeLanguageInfo.H("\u0011");
                i3 = a;
                break;
            case 9:
                objArr[0] = IdeAction.H("\u0010N\u001cB\nP*V\bM\u0017]");
                i3 = a;
                break;
            case 10:
                objArr[0] = AICodeLanguageInfo.H(",\f7\n\u0019\u001d;\f-\u0017*\u001a\"\u0005");
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
                objArr[1] = IdeAction.H("L\t@LI\rL��@,-\u000bG\u0007I\u0010P`v\u001d]\u001cV\u0017\u00011]6x\u0005E'G\u000eP#_*|\u001dt\u0005A\u000bI=A\u0016W\nV\u001dA");
                i4 = a;
                break;
            case 4:
                do {
                } while (0 != 0);
                objArr[1] = AICodeLanguageInfo.H("\u000e*\n\u0001\u00116\u001a(\u0004");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            default:
                objArr[2] = IdeAction.H("DZ\u0001M\f\r");
                break;
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[2] = AICodeLanguageInfo.H("��!?4\u0005\f\u0016-\u0019;\u001c\"\u0018");
                break;
            case 4:
                break;
            case 5:
                objArr[2] = IdeAction.H("N9\u007f\fs\rK\u001aM&J(Z\u0017A\u0014@");
                break;
            case 6:
                objArr[2] = AICodeLanguageInfo.H("-\u001e#\u001d\u0010\f&\u00190\u001d\u0006\u0010\u0014\u001c'\u000b+\u0005");
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = IdeAction.H("C\u000eM\u0016G");
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

    @NotNull
    public final Editor getEditor() {
        Editor editor = this.f460enum;
        if (editor == null) {
            m216enum(4);
        }
        return editor;
    }

    public int calcWidthInPixels(@NotNull Inlay a) {
        if (a == null) {
            m216enum(5);
        }
        return 500;
    }

    @Nullable
    public Inlay<?> getInlay() {
        return this.f456final;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatErrorPanelRenderer$n.class */
    public class n extends MouseAdapter {

        /* renamed from: float, reason: not valid java name */
        public final /* synthetic */ String f462float;

        /* renamed from: byte, reason: not valid java name */
        public final /* synthetic */ JLabel f463byte;

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ Color f464enum;

        public n(String str, JLabel jLabel, Color color) {
            this.f462float = str;
            this.f463byte = jLabel;
            this.f464enum = color;
        }

        public void mouseEntered(MouseEvent mouseEvent) {
            this.f463byte.setForeground(JBColor.BLUE);
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        public void mouseClicked(MouseEvent mouseEvent) {
            if (!this.f462float.contains(CodeCompleteService.H("叾涅"))) {
                if (this.f462float.contains(OpenTelemetryUtil.H("釂讞"))) {
                    InlineChatErrorPanelRenderer.this.f459byte.invoke();
                    return;
                }
                return;
            }
            InlineChatErrorPanelRenderer.this.f457try.invoke();
        }

        public void mouseExited(MouseEvent mouseEvent) {
            this.f463byte.setForeground(this.f464enum);
        }
    }

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatErrorPanelRenderer$y.class */
    class y extends ComponentAdapter {

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ InlineChatErrorPanelRenderer f466enum;

        public y(InlineChatErrorPanelRenderer inlineChatErrorPanelRenderer) {
            this.f466enum = inlineChatErrorPanelRenderer;
        }

        public void componentResized(ComponentEvent componentEvent) {
            this.f466enum.redraw();
        }
    }

    private static void Gb(InlineChatErrorPanelRenderer a) {
        if (a.getSize().height == a.getMinimumSize().height) {
            return;
        }
        a.setSize(new Dimension(500, a.getMinimumSize().height));
        Inlay<?> inlay = a.getInlay();
        if (inlay != null) {
            inlay.update();
        }
        a.revalidate();
        a.repaint();
    }

    public int getLineBreBlock() {
        return this.f453for;
    }

    public int getOffset() {
        return this.f455case;
    }

    public InlineChatErrorPanelRenderer(int lineBreBlock, @NotNull Disposable parentDisposable, @NotNull Editor editor, String content, Function0<Unit> function0, Function0<Unit> function02) {
        if (parentDisposable == null) {
            m216enum(0);
        }
        if (editor == null) {
            m216enum(1);
        }
        this.f455case = 0;
        this.f460enum = editor;
        this.f457try = function0;
        this.f459byte = function02;
        setLayout(new BoxLayout(this, 0));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(0));
        setBackground(JBColor.WHITE);
        setVisible(true);
        this.f453for = lineBreBlock;
        add(Box.createHorizontalStrut(lineBreBlock * 8));
        add(new JLabel(HighlightDisplayLevel.WARNING.getIcon(), 0));
        if (StringUtils.isNotBlank(content)) {
            JLabel jLabel = new JLabel(content + " | ");
            add(jLabel);
            jLabel.setForeground(JBColor.GRAY);
            jLabel.setBackground(JBColor.GRAY);
            jLabel.setPreferredSize(new Dimension(100, 30));
        }
        JBColor jBColor = JBColor.GRAY;
        this.f454if = aC(BasicActionsBundle.message(IdeAction.H("\rA\u0003M\bHvp\u0007E\u0010\u0001\u001c@\u001bV\u0001\u001d\u001bA��G"), StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(IdeAction.H("o1p5u\u000e\u000e-A\u0003M\bH\u001b{\u000ePJ}\u000bQ\u001d]9P\u001bM\u0017]")), KeymapUtil.getFirstMouseShortcutText(AICodeLanguageInfo.H("61\n \u001a![\u0007\u0011#\u00176\f\f\u00169\u001d8u&\u00066\f\u0002\u0011*\u0006{K")))), jBColor);
        add(this.f454if);
        JLabel jLabel2 = new JLabel(AICodeLanguageInfo.H("Oh\u0005"));
        add(jLabel2);
        jLabel2.setForeground(JBColor.GRAY);
        jLabel2.setBackground(JBColor.GRAY);
        this.f458float = aC(BasicActionsBundle.message(IdeAction.H("I\nC\u0006J\u0003\u0003;{\u000ePJL\u000fK\fA\u0014\u001d\u001bA��G"), StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(IdeAction.H("?|\fk\u001cVVz\u000bB\u0011]?R\u0003A\u0010\u0001&J\nD6v,L\u0005[=Q��T9P\u001bM\u0017]")), KeymapUtil.getFirstMouseShortcutText(AICodeLanguageInfo.H(";\u0002\b\u0015 \u0010a7(\u001b\u0011'*=,\u0014:Q\u0006\u00104��!\u001b\u001b\u0001wS\u0010\u0006+\u0005\u0002\u0011*\u0006{K")))), jBColor);
        add(this.f458float);
        inAllChildren(this, a -> {
            a.addComponentListener(new y(this));
            return Unit.INSTANCE;
        });
        Disposer.register(parentDisposable, this);
    }

    public void setLineBreBlock(int a) {
        this.f453for = a;
    }

    public void setOffset(int a) {
        this.f455case = a;
    }

    private JComponent aC(String a, Color a2) {
        JLabel jLabel = new JLabel(a);
        jLabel.setForeground(JBColor.GRAY);
        jLabel.setPreferredSize(new Dimension(100, 30));
        jLabel.addMouseListener(new n(a, jLabel, a2));
        add(jLabel);
        return jLabel;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void inAllChildren(@NotNull JComponent component, @NotNull Function1<? super JComponent, Unit> function1) {
        if (component == null) {
            m216enum(2);
        }
        if (function1 == null) {
            m216enum(3);
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

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void paint(@NotNull Inlay inlay, @NotNull Graphics g, @NotNull Rectangle targetRegion, @NotNull TextAttributes a) {
        if (inlay == null) {
            m216enum(7);
        }
        if (g == null) {
            m216enum(8);
        }
        if (targetRegion == null) {
            m216enum(9);
        }
        if (a == null) {
            m216enum(10);
        }
        Rectangle bounds = inlay.getBounds();
        if (bounds != null && !Intrinsics.areEqual(getBounds(), bounds)) {
            setBounds(bounds);
            revalidate();
            repaint();
        }
    }

    public final void redraw() {
        ApplicationManager.getApplication().invokeLater(() -> {
            Gb(this);
        });
    }

    public int calcHeightInPixels(@NotNull Inlay a) {
        if (a == null) {
            m216enum(6);
        }
        return this.f460enum.getLineHeight();
    }

    public void dispose() {
        if (this.f456final == null) {
            return;
        }
        this.f456final.dispose();
    }

    public void setInlay(@Nullable Inlay<?> inlay) {
        this.f456final = inlay;
    }

    public void createInlay(int a) {
        setOffset(a);
        this.f456final = (Inlay) Objects.requireNonNull(this.f460enum.getInlayModel().addBlockElement(a, false, true, Integer.MAX_VALUE, this));
    }
}
