package com.aicode.inline;

import com.aicode.inline.InlineChatService;
import com.aicode.ui.Font;
import com.aicode.util.ApplicationUtil;
import com.aicode.util.EditorKt;
import com.aicode.util.JComponentKt;
import com.aicode.util.Maps;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: dg */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/InlineChatInputComponent.class */
public class InlineChatInputComponent extends JBTextArea {

    /* renamed from: try, reason: not valid java name */
    @NotNull
    private final AbstractAction f324try;

    /* renamed from: float, reason: not valid java name */
    private int f325float = -1;

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private final AbstractAction f326byte = new AbstractAction() { // from class: com.aicode.inline.InlineChatInputComponent.02
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        private void FD(int a, InlineChatInputComponent a2, Ref.IntRef a3, int a4) {
            if (a > 0) {
                try {
                    a3.element = a2.getLineEndOffset(a - 1) - 1;
                } catch (BadLocationException e) {
                    throw new RuntimeException((Throwable) e);
                }
            }
            a2.setText(StringsKt.removeRange(a2.getText(), a3.element, a4).toString());
            a2.setCaretPosition(a3.element);
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public void actionPerformed(@Nullable ActionEvent actionEvent) {
            int caretPosition = InlineChatInputComponent.this.getCaretPosition();
            try {
                int lineOfOffset = InlineChatInputComponent.this.getLineOfOffset(caretPosition);
                Ref.IntRef intRef = new Ref.IntRef();
                try {
                    intRef.element = InlineChatInputComponent.this.getLineStartOffset(lineOfOffset);
                    Application application = ApplicationManager.getApplication();
                    InlineChatInputComponent inlineChatInputComponent = InlineChatInputComponent.this;
                    application.runWriteAction(() -> {
                        FD(lineOfOffset, inlineChatInputComponent, intRef, caretPosition);
                    });
                } catch (BadLocationException e) {
                    throw new RuntimeException((Throwable) e);
                }
            } catch (BadLocationException e2) {
                throw new RuntimeException((Throwable) e2);
            }
        }
    };

    /* renamed from: enum, reason: not valid java name */
    private JLabel f327enum;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void processKeyEvent(KeyEvent a) {
        KeyEvent keyEvent;
        super.processKeyEvent(a);
        if (a.getKeyCode() == 37 || a.getKeyCode() == 39) {
            int caretPosition = getCaretPosition();
            if ((a.getModifiersEx() & 64) != 0) {
                if (a.getKeyCode() != 37) {
                    if (a.getKeyCode() == 39 && caretPosition < getText().length()) {
                        moveCaretPosition(caretPosition + 1);
                    }
                } else if (caretPosition > 0) {
                    keyEvent = a;
                    moveCaretPosition(caretPosition - 1);
                    keyEvent.consume();
                    return;
                }
                keyEvent = a;
                keyEvent.consume();
                return;
            }
            if (37 == a.getKeyCode() && caretPosition > 0) {
                setCaretPosition(caretPosition - 1);
            }
            if (39 != a.getKeyCode() || caretPosition >= getDocument().getLength()) {
                return;
            }
            setCaretPosition(caretPosition + 1);
        }
    }

    public void updatePlaceholder() {
        this.f327enum.setVisible(getText().isEmpty());
    }

    public InlineChatInputComponent(final InlineChatInputPanel a) {
        this.f324try = new AbstractAction() { // from class: com.aicode.inline.InlineChatInputComponent.01
            public void actionPerformed(@Nullable ActionEvent actionEvent) {
                InlineChatService.Companion.closeInlineChat(a.getInlineChatPanel());
                Project findCurrentProject = ApplicationUtil.findCurrentProject();
                if (findCurrentProject != null) {
                    EditorKt.removeEditor(FileEditorManager.getInstance(findCurrentProject).getSelectedTextEditor());
                }
            }
        };
        setOpaque(false);
        setFocusable(true);
        setLineWrap(true);
        setWrapStyleWord(true);
        setFont(Font.INSTANCE.getMedium());
        setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
        getActionMap().put(Maps.H("*Qn\u001b1 =-'\u0016(Of"), this.f324try);
        getInputMap().put(KeyStroke.getKeyStroke(27, 0), JComponentKt.H("\u001d|Y6\u0006\r\n��\u0010;\u001fbQ"));
        this.f327enum = new JLabel(Maps.H("吐\u0001\u0012\u0003*+;\u0011,话闭"));
        this.f327enum.setForeground(JBColor.GRAY);
        this.f327enum.setBounds(5, 5, 100, 20);
        add(this.f327enum);
    }
}
