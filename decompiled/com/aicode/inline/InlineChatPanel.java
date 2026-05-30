package com.aicode.inline;

import com.aicode.agent.service.CodeCompleteService;
import com.aicode.diff.FileService;
import com.aicode.inline.InlineChatService;
import com.aicode.ui.ActionButton;
import com.aicode.ui.Style;
import com.aicode.util.EditorKt;
import com.aicode.util.IndentLineUtil;
import com.aicode.util.Maps;
import com.aicode.util.NewFileUtils;
import com.aicode.util.PropertyUtils;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorCustomElementRenderer;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.ui.RoundedLineBorder;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: pj */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/InlineChatPanel.class */
public class InlineChatPanel extends JPanel implements EditorCustomElementRenderer, KeyStrokeHandler, Disposable {

    /* renamed from: for, reason: not valid java name */
    private static final Logger f345for = LoggerFactory.getLogger(InlineChatPanel.class);

    /* renamed from: if, reason: not valid java name */
    @Nullable
    private Container f346if;

    /* renamed from: case, reason: not valid java name */
    @NotNull
    private final InlineChatInputPanel f347case;

    /* renamed from: final, reason: not valid java name */
    @Nullable
    private JComponent f348final;

    /* renamed from: try, reason: not valid java name */
    @NotNull
    private final JPanel f349try;

    /* renamed from: float, reason: not valid java name */
    @NotNull
    private final InlineChatTopPanel f350float;

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private final Editor f351byte;

    /* renamed from: enum, reason: not valid java name */
    @Nullable
    private Inlay<?> f352enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m172enum(int a) {
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
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            default:
                H = FileService.H("\u001d<xx='6>r&1>u\u0007\u001a)kC-&3m-.*+2( #m-_O,j} >jz>zc\u0001@4>#6e9\u000e\u0007p :m:31#");
                i = a;
                break;
            case 4:
            case 8:
                do {
                } while (0 != 0);
                H = NewFileUtils.H("k7\u000fFI L\u001e\u0002\u001dB\u0001H\u001dOY(,\bQ\\]W\u001dm8\u000f\u0013O\u0006\u000b\u000bG\u0004^\u000bNRU\u001cO\u001d");
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
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            default:
                i2 = 3;
                break;
            case 4:
            case 8:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = FileService.H(")*\"'+#%\u001a#20>5$1*");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = NewFileUtils.H("E\u0016R\u001dL\u0003");
                i3 = a;
                break;
            case 2:
                objArr[0] = FileService.H("\u0010?//\":#3;");
                i3 = a;
                break;
            case 3:
                objArr[0] = NewFileUtils.H("\u000fV\u001f");
                i3 = a;
                break;
            case 4:
            case 8:
                objArr[0] = FileService.H("%p`W\u000b6.2+=e6#8/\u001c\u0005v\u0002>.,9\u000408#+\u001d5(8#");
                i3 = a;
                break;
            case 5:
                objArr[0] = NewFileUtils.H("\u001bN��s\u0006I\u0006H\u0014");
                i3 = a;
                break;
            case 6:
            case 7:
            case 9:
                objArr[0] = FileService.H("$:*<6");
                i3 = a;
                break;
            case 10:
                objArr[0] = NewFileUtils.H("\u0016");
                i3 = a;
                break;
            case 11:
                objArr[0] = FileService.H("16\u0013\u001456\r(3/2!");
                i3 = a;
                break;
            case 12:
                objArr[0] = NewFileUtils.H("T\u0017S\rc\u0004_\u000bI\u0010N\u001dF\u0002");
                i3 = a;
                break;
            case 13:
                objArr[0] = FileService.H("!0# #3;");
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
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            default:
                objArr[1] = NewFileUtils.H("\u0013H\u0018\u000f\u0013B\u001ab;C[F\u0013V\u0001p)��4N\u001eB\u0017G3C\u0018T\"Z\u0007F\u001d");
                i4 = a;
                break;
            case 4:
                do {
                } while (0 != 0);
                objArr[1] = FileService.H("\u001456\u001a)=22=");
                i4 = a;
                break;
            case 8:
                objArr[1] = NewFileUtils.H("\u0013J\ts\u0006r%A\u0018c\u001aJ\rk\u001e[\fT\"Z\u0007F\u001d");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            default:
                objArr[2] = FileService.H("c$:/)q");
                break;
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[2] = NewFileUtils.H("\u001bE8N\u001ch\u0011I\u001e_\u001bF\u001f");
                break;
            case 4:
            case 8:
                break;
            case 5:
                objArr[2] = FileService.H("''(73)*");
                break;
            case 6:
                objArr[2] = NewFileUtils.H("/N\u0011C%B\u001dV\u0018b\u0017p\u001bC\fO\u0002");
                break;
            case 7:
                objArr[2] = FileService.H("\u0011\u00015(\u0018',0\t\u0007\u0019,\u000f$,#1<");
                break;
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[2] = NewFileUtils.H("\u0002Z��M\u0005");
                break;
            case 13:
                objArr[2] = FileService.H("\u0012\u0016$\u00010# #3;");
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
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            default:
                throw new IllegalArgumentException(format);
            case 4:
            case 8:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InlineChatPanel(@NotNull Disposable parentDisposable, @NotNull final Editor a) {
        super(new GridBagLayout());
        if (parentDisposable == null) {
            m172enum(0);
        }
        if (a == null) {
            m172enum(1);
        }
        this.f351byte = a;
        this.f347case = new InlineChatInputPanel(this, this.f351byte);
        this.f350float = new InlineChatTopPanel(this.f347case, this.f351byte);
        this.f349try = new JPanel(new BorderLayout());
        this.f349try.setBackground(Style.Colors.InlineChat.INSTANCE.getBackground());
        this.f349try.setVisible(false);
        this.f349try.addMouseListener(new r(this));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(0));
        Border createEmptyBorder = BorderFactory.createEmptyBorder(12, 12, 12, 12);
        RoundedLineBorder roundedLineBorder = new RoundedLineBorder(Style.Colors.InlineChat.INSTANCE.getBorder(), 8, 1);
        setBackground(Style.Colors.InlineChat.INSTANCE.getBackground());
        Unit unit = Unit.INSTANCE;
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(createEmptyBorder, roundedLineBorder), BorderFactory.createCompoundBorder(roundedLineBorder, BorderFactory.createMatteBorder(11, 11, 11, 11, Style.Colors.InlineChat.INSTANCE.getBackground()))));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0d;
        gridBagConstraints.fill = 2;
        add(this.f350float, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 1;
        add(this.f349try, gridBagConstraints);
        inAllChildren(this, a2 -> {
            a2.addComponentListener(new x(this));
            return unit;
        });
        this.f351byte.getCaretModel().addCaretListener(new CaretListener() { // from class: com.aicode.inline.InlineChatPanel.02
            /* renamed from: enum, reason: not valid java name */
            private static /* synthetic */ void m174enum(int a3) {
                throw new IllegalArgumentException(String.format(CodeCompleteService.H("h~\u00052HeLs��cJr\u0002Ggc\u0016\tpLN'PdWaOb]i}\n\u0003$^/\u0018rz\u0019\b{\f\"Z,Mp\u001b9\\7\u0011/\tnG'WiMh"), PropertyUtils.H("4d ~3"), CodeCompleteService.H("dFa KMbBl]2uWAaLb\u0006ENi\u0001#\u0019\u001a\u0016:]\\Ci\\p\u00056"), PropertyUtils.H(" u$d\u0006u,g%o.\u007f Zn0|\"u#")));
            }

            public void caretPositionChanged(@NotNull CaretEvent a3) {
                if (a3 == null) {
                    m174enum(0);
                }
                super.caretPositionChanged(a3);
                InlineChatService.Companion.removeFlag(a);
                InlineChatService.Companion.closeInlineChat(InlineChatPanel.this.f347case.getInlineChatPanel());
            }
        });
        this.f351byte.getDocument().addDocumentListener(new DocumentListener() { // from class: com.aicode.inline.InlineChatPanel.03
            /* renamed from: enum, reason: not valid java name */
            private static /* synthetic */ void m175enum(int a3) {
                throw new IllegalArgumentException(String.format(ActionButton.H(")��\u0005\r\u0003\u0011\u000b\u000bD\u0018@GH2!\u001a\u00199\u001d\u001e\u0003U\u0014\u001f]T%7\u001b\u0010\u001fWOW\u001cRD\u0011$xL��N_\u0006O<>\u0013\u000eO\u001b\u000b\nM\u0015@\u001f_^^D"), Maps.H("6\u0002-\u0002$"), ActionButton.H("1��\u0018B\u0016\u0001\u0011��\u0011\u0001Q+6\u0005\u001a\u000e\u001fZ&?'\t\u0014\n6\f\u001f\u0019'DQTG\u0016\u001b"), Maps.H("5JzD`W`\u0010\u001b\u00072\u001a/\t4")));
            }

            /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
            public void documentChanged(@NotNull DocumentEvent a3) {
                if (a3 == null) {
                    m175enum(0);
                }
                Inlay<?> inlay = InlineChatPanel.this.getInlay();
                if (inlay != null && inlay.getOffset() > InlineChatPanel.this.getEditor().getDocument().getTextLength()) {
                    InlineChatPanel.this.rB(InlineChatPanel.this.getEditor().getCaretModel().getPrimaryCaret().getOffset());
                }
            }
        });
        Disposer.register(parentDisposable, this);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.inline.KeyStrokeHandler
    public boolean execute(@NotNull KeyStroke a) {
        Clipboard systemClipboard;
        Transferable contents;
        if (a == null) {
            m172enum(5);
        }
        InlineChatInputComponent inputComponent = this.f347case.getInputComponent();
        if (8 != a.getKeyCode()) {
            if (10 == a.getKeyCode()) {
                getChatInputPanel().getChatInputController().submit();
                this.f347case.getButtonPanel().showStopButton();
            }
            if (27 == a.getKeyCode()) {
                InlineChatService.Companion.closeInlineChat(this.f347case.getInlineChatPanel());
                EditorKt.removeEditor(this.f351byte);
            }
            if (86 == a.getKeyCode() && (a.getModifiers() & 128) != 0) {
                Transferable contents2 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object) null);
                if (contents2 != null && contents2.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        String str = (String) contents2.getTransferData(DataFlavor.stringFlavor);
                        int selectionStart = inputComponent.getSelectionStart();
                        int selectionEnd = inputComponent.getSelectionEnd();
                        if (selectionStart != selectionEnd) {
                            inputComponent.getDocument().remove(selectionStart, selectionEnd - selectionStart);
                        }
                        inputComponent.insert(str, inputComponent.getCaretPosition());
                    } catch (UnsupportedFlavorException | IOException | BadLocationException e) {
                        throw new RuntimeException((Throwable) e);
                    }
                }
                return true;
            }
            if (67 == a.getKeyCode() && (a.getModifiers() & 128) != 0) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(inputComponent.getSelectedText()), (ClipboardOwner) null);
            }
            if (88 == a.getKeyCode() && (a.getModifiers() & 128) != 0 && (contents = (systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard()).getContents((Object) null)) != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    String selectedText = inputComponent.getSelectedText();
                    int selectionStart2 = inputComponent.getSelectionStart();
                    int selectionEnd2 = inputComponent.getSelectionEnd();
                    if (selectionStart2 != selectionEnd2) {
                        inputComponent.getDocument().remove(selectionStart2, selectionEnd2 - selectionStart2);
                        systemClipboard.setContents(new StringSelection(selectedText), (ClipboardOwner) null);
                    }
                } catch (BadLocationException e2) {
                    throw new RuntimeException((Throwable) e2);
                }
            }
            if (65 == a.getKeyCode() && (a.getModifiers() & 128) != 0) {
                inputComponent.selectAll();
            }
            return false;
        }
        return ub(inputComponent, inputComponent.getSelectionStart(), inputComponent.getSelectionEnd());
    }

    /* compiled from: pj */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/InlineChatPanel$x.class */
    class x extends ComponentAdapter {

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ InlineChatPanel f358enum;

        public void componentResized(ComponentEvent componentEvent) {
            this.f358enum.redraw();
        }

        public x(InlineChatPanel inlineChatPanel) {
            this.f358enum = inlineChatPanel;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void inAllChildren(@NotNull JComponent component, @NotNull Function1<? super JComponent, Unit> function1) {
        if (component == null) {
            m172enum(2);
        }
        if (function1 == null) {
            m172enum(3);
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

    public void setInlay(@Nullable Inlay<?> inlay) {
        this.f352enum = inlay;
    }

    public void setContainer(@Nullable Container a) {
        this.f346if = a;
    }

    @NotNull
    public final Editor getEditor() {
        Editor editor = this.f351byte;
        if (editor == null) {
            m172enum(4);
        }
        return editor;
    }

    private void rB(int a) {
        this.f352enum = (Inlay) Objects.requireNonNull(this.f351byte.getInlayModel().addBlockElement(a, false, true, 1, this));
    }

    @Nullable
    public Container getContainer() {
        return this.f346if;
    }

    private static void LB(InlineChatPanel a) {
        if (a.getSize().height == a.getMinimumSize().height) {
            return;
        }
        a.setSize(new Dimension(800, a.getMinimumSize().height));
        Inlay<?> inlay = a.getInlay();
        if (inlay != null) {
            inlay.update();
        }
        a.revalidate();
        a.repaint();
    }

    public final void redraw() {
        ApplicationManager.getApplication().invokeLater(() -> {
            LB(this);
        });
    }

    @Nullable
    public Inlay<?> getInlay() {
        return this.f352enum;
    }

    public int calcHeightInPixels(@NotNull Inlay a) {
        if (a == null) {
            m172enum(7);
        }
        return getSize().height;
    }

    /* compiled from: pj */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/InlineChatPanel$r.class */
    private static final class r extends MouseAdapter {

        /* renamed from: enum, reason: not valid java name */
        private final InlineChatPanel f356enum;

        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m176enum(int a) {
            throw new IllegalArgumentException(String.format(IndentLineUtil.H(":V\u0014Y\u0014C\u001b^^G\u0010R\u0014+=C��e\u0003E\u001f\f\u0004J\rAY\u000e'i\u0006\u000bQ\f��\u000bTD\u0019��|u\\\b\b\u0004\u0003D9a[J\u001b__B\u0013\tP\u0014\u0004["), FileService.H(".;+- {b-%"), IndentLineUtil.H("W\u0004\u0016\u000b\u0012E\u001aI\u0011OQH\u0011L]\u0005\u0016\u0003=E\u001a@\u001dI7C\u001eTd\n=i\u0018\u000f0F\u0010Y\u0007b\u0011P,r=C8H\u0007R!X\u0014Q\u0007N3I\u0005][\u000f\rE"), FileService.H("a&{n7o")));
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        public void mouseClicked(MouseEvent a) {
            if (a != null) {
                IdeFocusManager.getInstance(this.f356enum.getEditor().getProject()).requestFocus(this.f356enum.getInlineChatInputPanel().getInputComponent(), true);
            }
        }

        public r(@NotNull InlineChatPanel a) {
            if (a == null) {
                m176enum(0);
            }
            this.f356enum = a;
        }
    }

    public final void setContent(@NotNull JComponent a) {
        if (a == null) {
            m172enum(13);
        }
        a.setOpaque(true);
        a.setBackground(Style.Colors.InlineChat.INSTANCE.getBackground());
        this.f348final = a;
        Z();
    }

    public void dispose() {
        Inlay<?> inlay = this.f352enum;
        if (inlay != null) {
            inlay.dispose();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean ub(InlineChatInputComponent a, int a2, int a3) {
        Document document = a.getDocument();
        try {
            if (a2 != a3) {
                document.remove(a2, a3 - a2);
            } else {
                int length = document.getLength();
                if (length > 0) {
                    if (a.getCaretPosition() == length - 1) {
                        document.remove(length - 1, 1);
                    } else {
                        document.remove(a.getCaretPosition() - 1, 1);
                    }
                }
            }
            return true;
        } catch (BadLocationException e) {
            f345for.error(FileService.H(")8#=>:\u000e!4��\u00057?\u001c++2A\u0016\"00?nf&2"), e.getMessage());
            return false;
        }
    }

    @NotNull
    public InlineChatInputPanel getInlineChatInputPanel() {
        InlineChatInputPanel inlineChatInputPanel = this.f347case;
        if (inlineChatInputPanel == null) {
            m172enum(8);
        }
        return inlineChatInputPanel;
    }

    public InlineChatInputPanel getChatInputPanel() {
        return this.f347case;
    }

    public int calcWidthInPixels(@NotNull Inlay a) {
        if (a == null) {
            m172enum(6);
        }
        return getSize().width;
    }

    public void createInlay(int a) {
        this.f352enum = (Inlay) Objects.requireNonNull(this.f351byte.getInlayModel().addBlockElement(a, false, true, 1, this));
    }

    private void eB(InlineChatPanel a, JComponent a2) {
        if (!a.f349try.isVisible()) {
            a.f349try.setVisible(true);
        }
        a.f349try.removeAll();
        a.f349try.add(a2, NewFileUtils.H("a\u0015E\ri,"));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void paint(@NotNull Inlay inlay, @NotNull Graphics g, @NotNull Rectangle targetRegion, @NotNull TextAttributes a) {
        if (inlay == null) {
            m172enum(9);
        }
        if (g == null) {
            m172enum(10);
        }
        if (targetRegion == null) {
            m172enum(11);
        }
        if (a == null) {
            m172enum(12);
        }
        Rectangle bounds = inlay.getBounds();
        if (bounds != null && !Intrinsics.areEqual(getBounds(), bounds)) {
            setBounds(bounds);
            revalidate();
            repaint();
        }
    }

    public void setInlineContainer(Container a) {
        this.f346if = a;
    }

    private void Z() {
        JComponent jComponent = this.f348final;
        if (jComponent != null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                eB(this, jComponent);
            });
        }
    }
}
