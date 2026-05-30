package com.aicode.inline;

import com.aicode.inline.action.CloseInlineChatAction;
import com.aicode.inline.listener.InlineChatInputBorderFocusListener;
import com.aicode.ui.ActionButton;
import com.aicode.ui.Style;
import com.intellij.openapi.editor.Editor;
import com.intellij.util.ui.JBUI;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;

/* compiled from: qk */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/InlineChatTopPanel.class */
public class InlineChatTopPanel extends JPanel {

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private final GridBagConstraints f365byte;

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final GridBagConstraints f366enum;

    public InlineChatTopPanel(InlineChatInputPanel a, Editor a2) {
        super(new GridBagLayout());
        this.f366enum = new GridBagConstraints();
        this.f366enum.gridx = 0;
        this.f366enum.gridy = 0;
        this.f366enum.fill = 2;
        this.f366enum.anchor = 10;
        this.f366enum.weightx = 1.0d;
        this.f365byte = new GridBagConstraints();
        this.f365byte.gridx = 1;
        this.f365byte.gridy = 0;
        this.f365byte.fill = 0;
        this.f365byte.anchor = 10;
        this.f365byte.insets = JBUI.insetsLeft(5);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setBackground(Style.Colors.InlineChat.INSTANCE.getBackground());
        add(a, this.f366enum);
        add(ActionButton.button(new CloseInlineChatAction(a2)), this.f365byte);
        a.getInputComponent().addFocusListener(new InlineChatInputBorderFocusListener(a, null, null, 6));
    }
}
