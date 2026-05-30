package com.aicode.inline.render;

import cn.hutool.core.util.StrUtil;
import com.aicode.agent.service.GitReviewService;
import com.aicode.diff.FileService;
import com.aicode.message.BasicActionsBundle;
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

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatCategoryPanelRenderer.class */
public class InlineChatCategoryPanelRenderer extends JPanel implements EditorCustomElementRenderer, Disposable {

    /* renamed from: case, reason: not valid java name */
    private int f442case;

    /* renamed from: final, reason: not valid java name */
    private final Function0<Unit> f443final;

    /* renamed from: try, reason: not valid java name */
    private int f444try;

    /* renamed from: float, reason: not valid java name */
    @NotNull
    private final Editor f445float;

    /* renamed from: byte, reason: not valid java name */
    private JComponent f446byte;

    /* renamed from: enum, reason: not valid java name */
    @Nullable
    private Inlay<?> f447enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m215enum(int a) {
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
                H = FileService.H("9\u0018880*6>\u007f+;4R \u0017$$\f0;\u001e@ #769#\"!,ldt;}A\u001c6bz>zc.o\u000e\u0004'2\u007f#:3t$&q:3/=");
                i = a;
                break;
            case 4:
                do {
                } while (0 != 0);
                H = GitReviewService.H("\u00036$\u0004\u0018\u00181\nT\" \n\"\u001e%Zm��Xh2Z'\u00043\u000fa\u00149\u0019a\b3\u00194\b$Q?\u001f%\u001e");
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
                objArr[0] = FileService.H("\u0013\u0010&#19\u0011.'6,\"5$/4");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = GitReviewService.H("/\u00158\u001e&��");
                i3 = a;
                break;
            case 2:
                objArr[0] = FileService.H("$;+3>:#-%");
                i3 = a;
                break;
            case 3:
                objArr[0] = GitReviewService.H("\f<\u001c");
                i3 = a;
                break;
            case 4:
                objArr[0] = FileService.H(";%2b5/\u0011\u000f=.\u007f++;\u001b\u000e5m72:\"36q\u0005-=!4\u000408#+\u000e528(\f\u0003-\u0016>#0+\u0006#-514&#");
                i3 = a;
                break;
            case 5:
            case 6:
            case 7:
                objArr[0] = GitReviewService.H("\u0018?\u0006(\u000b");
                i3 = a;
                break;
            case 8:
                objArr[0] = FileService.H("6");
                i3 = a;
                break;
            case 9:
                objArr[0] = GitReviewService.H("5\u001b$\n$\u000e\u0018\u00146\u0003&\u001c");
                i3 = a;
                break;
            case 10:
                objArr[0] = FileService.H(" #'9\u00143 4*3!2&\"");
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
                objArr[1] = GitReviewService.H("/\u0018(Q1\u0002\u00043!\u001b\u007f\u0002/\u0016*\u0016._$\b3\u0002\u0011=j7$\u001d(\u0014-0\u001e,59+\u0005%\u001c.\b/= \u00143\u0001\u0013\u001f$\u00154\u0018,��");
                i4 = a;
                break;
            case 4:
                do {
                } while (0 != 0);
                objArr[1] = FileService.H(" 12\u00065=2,#");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            default:
                objArr[2] = GitReviewService.H("v\u0018?\u0003=L");
                break;
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[2] = FileService.H("/1\f9+\u0017.*=04&?");
                break;
            case 4:
                break;
            case 5:
                objArr[2] = GitReviewService.H("\u0018 \u00165:(\u001e\"\u0005\b\u0014\u001a\u0018)\u000f%\u0001");
                break;
            case 6:
                objArr[2] = FileService.H(">.\u000f\u0012\u001c#6*=3\u001d(\u00138,#/\"");
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = GitReviewService.H("\u00010\u0003'\u0006");
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

    public void setInlay(@Nullable Inlay<?> inlay) {
        this.f447enum = inlay;
    }

    public int getOffset() {
        return this.f444try;
    }

    public void createInlay(int a) {
        setOffset(a);
        this.f447enum = (Inlay) Objects.requireNonNull(this.f445float.getInlayModel().addBlockElement(a, false, true, Integer.MAX_VALUE, this));
    }

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatCategoryPanelRenderer$t.class */
    class t extends MouseAdapter {

        /* renamed from: float, reason: not valid java name */
        public final /* synthetic */ Function0 f448float;

        /* renamed from: byte, reason: not valid java name */
        public final /* synthetic */ Color f449byte;

        public void mouseEntered(MouseEvent mouseEvent) {
            InlineChatCategoryPanelRenderer.this.f446byte.setForeground(JBColor.BLUE);
        }

        public void mouseClicked(MouseEvent mouseEvent) {
            this.f448float.invoke();
        }

        public void mouseExited(MouseEvent mouseEvent) {
            InlineChatCategoryPanelRenderer.this.f446byte.setForeground(this.f449byte);
        }

        public t(Function0 function0, Color color) {
            this.f448float = function0;
            this.f449byte = color;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void paint(@NotNull Inlay inlay, @NotNull Graphics g, @NotNull Rectangle targetRegion, @NotNull TextAttributes a) {
        if (inlay == null) {
            m215enum(7);
        }
        if (g == null) {
            m215enum(8);
        }
        if (targetRegion == null) {
            m215enum(9);
        }
        if (a == null) {
            m215enum(10);
        }
        Rectangle bounds = inlay.getBounds();
        if (bounds != null && !Intrinsics.areEqual(getBounds(), bounds)) {
            setBounds(bounds);
            revalidate();
            repaint();
        }
    }

    @NotNull
    public final Editor getEditor() {
        Editor editor = this.f445float;
        if (editor == null) {
            m215enum(4);
        }
        return editor;
    }

    public void dispose() {
        if (this.f447enum != null) {
            this.f447enum.dispose();
        }
    }

    private static void Jb(InlineChatCategoryPanelRenderer a) {
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

    public void setOffset(int a) {
        this.f444try = a;
    }

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/render/InlineChatCategoryPanelRenderer$w.class */
    class w extends ComponentAdapter {

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ InlineChatCategoryPanelRenderer f452enum;

        public void componentResized(ComponentEvent componentEvent) {
            this.f452enum.redraw();
        }

        public w(InlineChatCategoryPanelRenderer inlineChatCategoryPanelRenderer) {
            this.f452enum = inlineChatCategoryPanelRenderer;
        }
    }

    public void setLineBreBlock(int a) {
        this.f442case = a;
    }

    public int calcHeightInPixels(@NotNull Inlay a) {
        if (a == null) {
            m215enum(6);
        }
        return this.f445float.getLineHeight();
    }

    @Nullable
    public Inlay<?> getInlay() {
        return this.f447enum;
    }

    public InlineChatCategoryPanelRenderer(int lineBreBlock, @NotNull Disposable parentDisposable, @NotNull Editor editor, Function0<Unit> function0) {
        if (parentDisposable == null) {
            m215enum(0);
        }
        if (editor == null) {
            m215enum(1);
        }
        this.f444try = 0;
        this.f445float = editor;
        this.f443final = function0;
        setLayout(new BoxLayout(this, 0));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(0));
        setBackground(JBColor.WHITE);
        setVisible(true);
        this.f442case = lineBreBlock;
        add(Box.createHorizontalStrut(lineBreBlock * 8));
        add(new JLabel(new AnimatedIcon.Default(), 0));
        JLabel jLabel = new JLabel(FileService.H("=��34\u0016(0#恞聒乹f?q"));
        add(jLabel);
        jLabel.setForeground(JBColor.GRAY);
        jLabel.setBackground(JBColor.GRAY);
        jLabel.setPreferredSize(new Dimension(100, 30));
        this.f446byte = new JLabel(BasicActionsBundle.message(GitReviewService.H("\u0018.\u0017(\u00143C\"\u00127\u00196@,\u0018)\u0014!X$\u000ebU"), StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(GitReviewService.H("5\u0006\u0006\u0011.\u0014o3&\u001f\u001f#$9\"\u00104U\b\u0014:\u0004/\u001f\u0015\u0005yW\u001e\u0002%\u0001\f\u0015$\u0002uO")), KeymapUtil.getFirstMouseShortcutText(FileService.H("\u0004\u001e\u0017)2!p\u0005-=!4\u000408#+c\u001d(1&\r\u0014\u0017.>9\u00063;6\u00022 /,?")))));
        this.f446byte.setForeground(JBColor.GRAY);
        this.f446byte.setPreferredSize(new Dimension(100, 30));
        add(this.f446byte);
        inAllChildren(this, a -> {
            a.addComponentListener(new w(this));
            return Unit.INSTANCE;
        });
        this.f446byte.addMouseListener(new t(function0, this.f446byte.getForeground()));
        Disposer.register(parentDisposable, this);
    }

    public final void redraw() {
        ApplicationManager.getApplication().invokeLater(() -> {
            Jb(this);
        });
    }

    public int calcWidthInPixels(@NotNull Inlay a) {
        if (a == null) {
            m215enum(5);
        }
        return 500;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void inAllChildren(@NotNull JComponent component, @NotNull Function1<? super JComponent, Unit> function1) {
        if (component == null) {
            m215enum(2);
        }
        if (function1 == null) {
            m215enum(3);
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

    public int getLineBreBlock() {
        return this.f442case;
    }
}
