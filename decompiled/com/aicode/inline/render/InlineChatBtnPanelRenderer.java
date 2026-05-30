package com.aicode.inline.render;

import cn.hutool.core.util.StrUtil;
import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.content.util.EditorUtils;
import com.aicode.icons.Icons;
import com.aicode.inline.enums.InlineChatOperateEnum;
import com.aicode.message.BasicActionsBundle;
import com.aicode.ui.ActionButton;
import com.aicode.util.Maps;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatBtnPanelRenderer.class */
public class InlineChatBtnPanelRenderer extends JPanel implements EditorCustomElementRenderer, Disposable {

    /* renamed from: int, reason: not valid java name */
    private final Function0<Unit> f424int;

    /* renamed from: new, reason: not valid java name */
    private JComponent f425new;

    /* renamed from: long, reason: not valid java name */
    private JComponent f426long;

    /* renamed from: super, reason: not valid java name */
    private JComponent f427super;

    /* renamed from: for, reason: not valid java name */
    private JComponent f428for;

    /* renamed from: if, reason: not valid java name */
    @Nullable
    private Inlay<?> f429if;

    /* renamed from: case, reason: not valid java name */
    private final Function0<Unit> f430case;

    /* renamed from: final, reason: not valid java name */
    @NotNull
    private final Editor f431final;

    /* renamed from: try, reason: not valid java name */
    private int f432try;

    /* renamed from: float, reason: not valid java name */
    private static final Logger f433float = LoggerFactory.getLogger(InlineChatBtnPanelRenderer.class);

    /* renamed from: byte, reason: not valid java name */
    private final Function0<Unit> f434byte;

    /* renamed from: enum, reason: not valid java name */
    private final Function0<Unit> f435enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m214enum(int a) {
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
                H = EditorUtils.H("\\4p9{(o.}`U\u0013=\u0006Q+n\u000fh*zm@\ni!\u007f,s9CJ yna\u0003\u0017ti858hldL\u000fe9=(x86/dzx8m6");
                i = a;
                break;
            case 4:
                do {
                } while (0 != 0);
                H = Maps.H("5\u0007,\u000b;<\u0003?q��\u0005(\u0007<��xH\"}J\u0017x\u0002&\u0016-D6\u001c;D*\u0016;\u0011*\u0001s\u001a=��<");
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
                objArr[0] = EditorUtils.H("Q\u001bd(s2S%e=n)w/m?");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = Maps.H("\n7\u001d<\u0003\"");
                i3 = a;
                break;
            case 2:
                objArr[0] = EditorUtils.H("/y q5x(o.");
                i3 = a;
                break;
            case 3:
                objArr[0] = Maps.H(".\u0019>");
                i3 = a;
                break;
            case 4:
                objArr[0] = EditorUtils.H("\u0002r+0%s\"r\"sbY\u0005w)|,(.T\u0004c9oij\u0016~ s#U%~0c\u000ex\u001d|(r D(o>s?d(");
                i3 = a;
                break;
            case 5:
            case 6:
            case 7:
                objArr[0] = Maps.H(":\u001a$\r)");
                i3 = a;
                break;
            case 8:
                objArr[0] = EditorUtils.H("=");
                i3 = a;
                break;
            case 9:
                objArr[0] = Maps.H("\u00109\u0001(\u0001,=6\u0013!\u0003>");
                i3 = a;
                break;
            case 10:
                objArr[0] = EditorUtils.H("b(e2V8b?h8c9d)");
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
                objArr[1] = Maps.H("0\u000b5m\u001f��6\u000f8\u0010f*\u0011\u0019 \u00016~\u001f\u00052\u000b6\u0016w$??\u0006\n=,;\u0004-&,\u001d\u001f\u00056\u0016#6=\u00017\u0011:\t\"");
                i4 = a;
                break;
            case 4:
                do {
                } while (0 != 0);
                objArr[1] = EditorUtils.H("+s9D>\u007f9n(");
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
                objArr[2] = EditorUtils.H("$s\u0007{ U%h6r?d4");
                break;
            case 4:
                break;
            case 5:
                objArr[2] = Maps.H(":\u00054\u0010\u0018\r<\u0007'-6?:\f-��#");
                break;
            case 6:
                objArr[2] = EditorUtils.H("|%M\u0019^(t!\u007f8_#Q3n(m)");
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

    public void dispose() {
        if (this.f429if != null) {
            this.f429if.dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatBtnPanelRenderer$O.class */
    public class O extends MouseAdapter {

        /* renamed from: float, reason: not valid java name */
        public final /* synthetic */ Color f437float;

        /* renamed from: byte, reason: not valid java name */
        public final /* synthetic */ String f438byte;

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ JLabel f439enum;

        public O(String str, JLabel jLabel, Color color) {
            this.f438byte = str;
            this.f439enum = jLabel;
            this.f437float = color;
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public void mouseClicked(MouseEvent mouseEvent) {
            if (!this.f438byte.contains(MethodGeneratorConfig.H("醗续"))) {
                if (!this.f438byte.contains(ActionButton.H("抜纉"))) {
                    if (!this.f438byte.contains(MethodGeneratorConfig.H("醝讋"))) {
                        this.f439enum.setForeground(JBColor.GRAY);
                        InlineChatBtnPanelRenderer.this.f435enum.invoke();
                        return;
                    } else {
                        InlineChatBtnPanelRenderer.this.f434byte.invoke();
                        return;
                    }
                }
                InlineChatBtnPanelRenderer.this.f430case.invoke();
                return;
            }
            InlineChatBtnPanelRenderer.this.f424int.invoke();
        }

        public void mouseExited(MouseEvent mouseEvent) {
            this.f439enum.setForeground(this.f437float);
        }

        public void mouseEntered(MouseEvent mouseEvent) {
            this.f439enum.setForeground(JBColor.BLUE);
        }
    }

    @Nullable
    public Inlay<?> getInlay() {
        return this.f429if;
    }

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatBtnPanelRenderer$U.class */
    class U extends ComponentAdapter {

        /* renamed from: byte, reason: not valid java name */
        public final /* synthetic */ InlineChatBtnPanelRenderer f440byte;

        public void componentResized(ComponentEvent componentEvent) {
            this.f440byte.redraw();
        }

        public U(InlineChatBtnPanelRenderer inlineChatBtnPanelRenderer) {
            this.f440byte = inlineChatBtnPanelRenderer;
        }
    }

    private void Ha() {
        JLabel jLabel = new JLabel(EditorUtils.H("m}z"));
        jLabel.setForeground(JBColor.GRAY);
        jLabel.setPreferredSize(new Dimension(5, 30));
        add(jLabel);
    }

    private static void IB(InlineChatBtnPanelRenderer a) {
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

    public int calcWidthInPixels(@NotNull Inlay a) {
        if (a == null) {
            m214enum(5);
        }
        return 500;
    }

    public int getOffset() {
        return this.f432try;
    }

    private JComponent aC(String a, Color a2) {
        JLabel jLabel = new JLabel(a);
        jLabel.setForeground(JBColor.GRAY);
        jLabel.setPreferredSize(new Dimension(80, 30));
        jLabel.addMouseListener(new O(a, jLabel, a2));
        add(jLabel);
        return jLabel;
    }

    public int calcHeightInPixels(@NotNull Inlay a) {
        if (a == null) {
            m214enum(6);
        }
        return getSize().height;
    }

    public void setOffset(int a) {
        this.f432try = a;
    }

    public void setInlay(@Nullable Inlay<?> inlay) {
        this.f429if = inlay;
    }

    public InlineChatBtnPanelRenderer(InlineChatOperateEnum inlineChatOperateEnum, int lineBreBlock, @NotNull Disposable parentDisposable, @NotNull Editor editor, Function0<Unit> function0, Function0<Unit> function02, Function0<Unit> function03, Function0<Unit> function04) {
        if (parentDisposable == null) {
            m214enum(0);
        }
        if (editor == null) {
            m214enum(1);
        }
        this.f432try = 0;
        this.f431final = editor;
        this.f424int = function0;
        this.f430case = function02;
        this.f434byte = function03;
        this.f435enum = function04;
        setLayout(new BoxLayout(this, 0));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(0));
        setBackground(JBColor.WHITE);
        setVisible(true);
        add(Box.createHorizontalStrut(lineBreBlock * 8));
        yB();
        JBColor jBColor = JBColor.GRAY;
        this.f427super = aC(BasicActionsBundle.message(EditorUtils.H(" s*\u007f#zjB\u0012w93't/s=utb(y."), StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(EditorUtils.H("F\u0015^)G\u001d<��s*\u007f#z\u0007I\u001bbc\\%t)f9@9b$n4")), KeymapUtil.getFirstMouseShortcutText(Maps.H(",\u0018\u0010����=A\u001a\u000b5\r6\u0016\f\f9\u0007a|b\u000b1\u001f')7\u0001 Pm")))), jBColor);
        Ha();
        this.f426long = aC(BasicActionsBundle.message(Maps.H("\f7\b1\u001d*J;\u001b.I/\u001a1\u001b!\u0011z\u0001,Gw"), StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(Maps.H("\u0010\u001a,\u000b<\n},7\b1\u001d*'0\u0012;\u0013S\r \u001d*)7\u0001 Pm")), KeymapUtil.getFirstMouseShortcutText(EditorUtils.H("\u001dT\u0005L\u001cwgT(z$q!b\u0012w93\u0014r8d4@9b$n4")))), jBColor);
        Ha();
        this.f428for = aC(BasicActionsBundle.message(EditorUtils.H(" s*\u007f#zjB\u0012w934r&s.utb(y."), StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(EditorUtils.H("F\u0015^)G\u001d<��s*\u007f#z\u0007I\u001bbcO#})u9@9b$n4")), KeymapUtil.getFirstMouseShortcutText(Maps.H(",\u0018\u0010����=A\u001a\u000b5\r6\u0016\f\f9\u0007aod\u00021\f')7\u0001 Pm")))), jBColor);
        if (inlineChatOperateEnum == InlineChatOperateEnum.EDIT) {
            Ha();
            this.f425new = aC(BasicActionsBundle.message(Maps.H("0\n4\u001a!\u0001v\u0010'\\uF0\u00065\u000ez\u0001,Gw"), new Object[0]), jBColor);
        }
        inAllChildren(this, a -> {
            a.addComponentListener(new U(this));
            return Unit.INSTANCE;
        });
        Disposer.register(parentDisposable, this);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void inAllChildren(@NotNull JComponent component, @NotNull Function1<? super JComponent, Unit> function1) {
        if (component == null) {
            m214enum(2);
        }
        if (function1 == null) {
            m214enum(3);
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

    @NotNull
    public final Editor getEditor() {
        Editor editor = this.f431final;
        if (editor == null) {
            m214enum(4);
        }
        return editor;
    }

    private void yB() {
        JLabel jLabel = new JLabel(Icons.ToolWindowIcon);
        jLabel.setForeground(JBColor.GRAY);
        jLabel.setPreferredSize(new Dimension(30, 30));
        add(jLabel);
        JLabel jLabel2 = new JLabel(Maps.H("_"));
        jLabel2.setForeground(JBColor.GRAY);
        jLabel2.setPreferredSize(new Dimension(5, 30));
        add(jLabel2);
    }

    public final void redraw() {
        ApplicationManager.getApplication().invokeLater(() -> {
            IB(this);
        });
    }

    public void createInlay(int a) {
        setOffset(a);
        this.f429if = (Inlay) Objects.requireNonNull(this.f431final.getInlayModel().addBlockElement(a, false, true, Integer.MAX_VALUE, this));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void paint(@NotNull Inlay inlay, @NotNull Graphics g, @NotNull Rectangle targetRegion, @NotNull TextAttributes a) {
        if (inlay == null) {
            m214enum(7);
        }
        if (g == null) {
            m214enum(8);
        }
        if (targetRegion == null) {
            m214enum(9);
        }
        if (a == null) {
            m214enum(10);
        }
        Rectangle bounds = inlay.getBounds();
        if (bounds == null || Intrinsics.areEqual(getBounds(), bounds)) {
            return;
        }
        setBounds(bounds);
        revalidate();
        repaint();
    }
}
