package com.aicode.util;

import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.content.util.EditorUtils;
import com.aicode.exception.RequestTimeoutException;
import com.aicode.service.editor.RequestResultList;
import com.aicode.ui.FontKt;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.panel.ComponentPanel;
import com.intellij.openapi.ui.panel.GridBagPanelBuilder;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.text.HtmlBuilder;
import com.intellij.openapi.util.text.HtmlChunk;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.BrowserHyperlinkListener;
import com.intellij.ui.ContextHelpLabel;
import com.intellij.ui.EditorTextComponent;
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.panels.NonOpaquePanel;
import com.intellij.ui.scale.JBUIScale;
import com.intellij.util.SystemProperties;
import com.intellij.util.ui.JBEmptyBorder;
import com.intellij.util.ui.JBInsets;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UI;
import com.intellij.util.ui.UIUtil;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.HyperlinkListener;
import javax.swing.plaf.LabelUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: aa */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/PluginComponentPanelBuilder.class */
public class PluginComponentPanelBuilder implements GridBagPanelBuilder {
    public static final /* synthetic */ int MAX_COMMENT_WIDTH = 70;

    /* renamed from: true, reason: not valid java name */
    private /* synthetic */ Runnable f692true;

    /* renamed from: char, reason: not valid java name */
    private /* synthetic */ String f695char;

    /* renamed from: int, reason: not valid java name */
    private /* synthetic */ String f696int;

    /* renamed from: new, reason: not valid java name */
    private /* synthetic */ JComponent f697new;

    /* renamed from: long, reason: not valid java name */
    private /* synthetic */ String f698long;

    /* renamed from: super, reason: not valid java name */
    private /* synthetic */ boolean f699super;

    /* renamed from: for, reason: not valid java name */
    private /* synthetic */ boolean f700for;

    /* renamed from: if, reason: not valid java name */
    private final /* synthetic */ JComponent f701if;

    /* renamed from: float, reason: not valid java name */
    private /* synthetic */ String f705float;

    /* renamed from: enum, reason: not valid java name */
    private /* synthetic */ Icon f707enum;

    /* renamed from: case, reason: not valid java name */
    private /* synthetic */ HyperlinkListener f702case = BrowserHyperlinkListener.INSTANCE;

    /* renamed from: byte, reason: not valid java name */
    private /* synthetic */ boolean f706byte = true;

    /* renamed from: final, reason: not valid java name */
    private /* synthetic */ boolean f703final = true;

    /* renamed from: try, reason: not valid java name */
    private /* synthetic */ UI.Anchor f704try = UI.Anchor.Center;

    /* renamed from: else, reason: not valid java name */
    private /* synthetic */ boolean f694else = true;

    /* renamed from: this, reason: not valid java name */
    private /* synthetic */ boolean f693this = true;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m415enum(int a) {
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
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 14:
            case 15:
            case 16:
            default:
                H = OpenTelemetryUtil.H("T#o9`,!\u007f\u0011\u0013b;4\u0010H-|\u0002z'NF~+~)|0z/}k#e|l5>WU%7!nw``<P\u00134>g8-+`aj5\u007f;");
                i = a;
                break;
            case 9:
            case 11:
            case 12:
            case 13:
                do {
                } while (0 != 0);
                H = EditorUtils.H("p%s3P0o4<*x2~\"yf\"/\r]aip3e9?*^\u001e&/\u007f5j6ylx8m6");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 14:
            case 15:
            case 16:
            default:
                i2 = 3;
                break;
            case 9:
            case 11:
            case 12:
            case 13:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = OpenTelemetryUtil.H(" l+`-P%k#");
                i3 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = EditorUtils.H("'x!{(o.");
                i3 = a;
                break;
            case 3:
                objArr[0] = OpenTelemetryUtil.H("m#|9");
                i3 = a;
                break;
            case 4:
                objArr[0] = EditorUtils.H("s-d8s#d(");
                i3 = a;
                break;
            case 5:
                objArr[0] = OpenTelemetryUtil.H("4b9q\u000es8|\u000fb$u.j%}#");
                i3 = a;
                break;
            case 6:
                objArr[0] = EditorUtils.H("9\u007f2|6~<b$n4");
                i3 = a;
                break;
            case 7:
                objArr[0] = OpenTelemetryUtil.H("a k*P%k#");
                i3 = a;
                break;
            case 8:
                objArr[0] = EditorUtils.H("v/b$n4");
                i3 = a;
                break;
            case 9:
            case 11:
            case 12:
            case 13:
                objArr[0] = OpenTelemetryUtil.H("(M\u000b!+e+~1kez?m, \u001by$V\u001cn\u0007`&t/c,M\u0013D1f)a\u000bp(h$v%");
                i3 = a;
                break;
            case 10:
            case 16:
                objArr[0] = EditorUtils.H("\"p)g#x(o.");
                i3 = a;
                break;
            case 14:
                objArr[0] = OpenTelemetryUtil.H("\u000bu2m ^<u1h)v%");
                i3 = a;
                break;
            case 15:
                objArr[0] = EditorUtils.H(">u,r!y8B(y.");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 14:
            case 15:
            case 16:
            default:
                objArr[1] = OpenTelemetryUtil.H("(M\u000b!+e+~1kez?m, \u001by$V\u001cn\u0007`&t/c,M\u0013D1f)a\u000bp(h$v%");
                i4 = a;
                break;
            case 9:
                do {
                } while (0 != 0);
                objArr[1] = EditorUtils.H(">h$~0r\u001cw#d6");
                i4 = a;
                break;
            case 11:
            case 12:
                objArr[1] = OpenTelemetryUtil.H("l$i0x=F${=e)c=L/w%g$");
                i4 = a;
                break;
            case 13:
                objArr[1] = EditorUtils.H("q;x'b(\\+\\\u0007c3n\u0002p)g#x(o.");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = OpenTelemetryUtil.H(";d=m\re\"v;");
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = EditorUtils.H("*s5w\u0007x!{(o.");
                break;
            case 3:
                objArr[2] = OpenTelemetryUtil.H(">J\u0013|\u0013g!`,k5M#|9");
                break;
            case 4:
                objArr[2] = EditorUtils.H("j/s4`\u0017\u007f$x(b\u0005f4T\u0018j4t*S-d8s#d(");
                break;
            case 5:
                objArr[2] = OpenTelemetryUtil.H("3f?l\u0014b9q\u000es8|\u000fb$u.j%}#");
                break;
            case 6:
                objArr[2] = EditorUtils.H("*s5w\u0010x#z9h*");
                break;
            case 7:
            case 8:
                objArr[2] = OpenTelemetryUtil.H(">J\u0013|\u0004g#a=l1H)}<");
                break;
            case 9:
            case 11:
            case 12:
            case 13:
                break;
            case 10:
                objArr[2] = EditorUtils.H("~){=j0T)i0w$q0^\"e(u)");
                break;
            case 14:
                objArr[2] = OpenTelemetryUtil.H("c6j*p%N&N\nq>|\u000fb$u.j%}#");
                break;
            case 15:
                objArr[2] = EditorUtils.H("$o#w9x\bh2t\ns9m/x*\\+\\\u0007c3n\u0002p)g#x(o.");
                break;
            case 16:
                objArr[2] = OpenTelemetryUtil.H("P\u0002`\u0013g!`,k5P%k#");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 14:
            case 15:
            case 16:
            default:
                throw new IllegalArgumentException(format);
            case 9:
            case 11:
            case 12:
            case 13:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public /* synthetic */ PluginComponentPanelBuilder moveLabelOnTop() {
        this.f699super = true;
        this.f693this = StringUtil.isEmpty(this.f696int) || StringUtil.isEmpty(this.f695char);
        return this;
    }

    public /* synthetic */ PluginComponentPanelBuilder withCommentIcon(@NotNull Icon a) {
        if (a == null) {
            m415enum(3);
        }
        this.f707enum = a;
        return this;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public /* synthetic */ PluginComponentPanelBuilder withTopRightComponent(@NotNull JComponent a) {
        if (a == null) {
            m415enum(5);
        }
        this.f697new = a;
        this.f693this = StringUtil.isEmpty(this.f696int) || StringUtil.isEmpty(this.f695char);
        return this;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public /* synthetic */ PluginComponentPanelBuilder withComment(@NlsContexts.DetailedDescription @NotNull String comment, boolean z) {
        if (comment == null) {
            m415enum(2);
        }
        this.f696int = comment;
        this.f703final = z;
        this.f693this = StringUtil.isEmpty(comment) || StringUtil.isEmpty(this.f695char);
        return this;
    }

    public /* synthetic */ PluginComponentPanelBuilder withLabel(@NlsContexts.Label @NotNull String a) {
        if (a == null) {
            m415enum(0);
        }
        this.f698long = a;
        return this;
    }

    public /* synthetic */ PluginComponentPanelBuilder withTooltipLink(@NotNull @NlsContexts.LinkLabel String linkText, @NotNull Runnable a) {
        if (linkText == null) {
            m415enum(7);
        }
        if (a == null) {
            m415enum(8);
        }
        this.f705float = linkText;
        this.f692true = a;
        return this;
    }

    public /* synthetic */ PluginComponentPanelBuilder resizeX(boolean z) {
        this.f694else = z;
        return this;
    }

    public /* synthetic */ PluginComponentPanelBuilder moveCommentRight() {
        this.f706byte = false;
        return this;
    }

    public /* synthetic */ PluginComponentPanelBuilder withCommentHyperlinkListener(@NotNull HyperlinkListener a) {
        if (a == null) {
            m415enum(4);
        }
        this.f702case = a;
        return this;
    }

    public /* synthetic */ PluginComponentPanelBuilder anchorLabelOn(UI.Anchor a) {
        this.f704try = a;
        return this;
    }

    public /* synthetic */ PluginComponentPanelBuilder resizeY(boolean z) {
        this.f700for = z;
        return this;
    }

    public /* synthetic */ boolean constrainsValid() {
        return this.f693this;
    }

    public /* synthetic */ PluginComponentPanelBuilder withComment(@NlsContexts.DetailedDescription @NotNull String a) {
        if (a == null) {
            m415enum(1);
        }
        return withComment(a, true);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public /* synthetic */ PluginComponentPanelBuilder withTooltip(@NlsContexts.Tooltip @NotNull String a) {
        if (a == null) {
            m415enum(6);
        }
        this.f695char = a;
        this.f693this = StringUtil.isEmpty(this.f696int) || StringUtil.isEmpty(a);
        return this;
    }

    @NotNull
    public /* synthetic */ JPanel createPanel() {
        NonOpaquePanel nonOpaquePanel = new NonOpaquePanel(new GridBagLayout());
        addToPanel(nonOpaquePanel, new GridBagConstraints(0, 0, 1, 1, 0.0d, 0.0d, 21, 2, (Insets) null, 0, 0), false);
        if (nonOpaquePanel == null) {
            m415enum(9);
        }
        return nonOpaquePanel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: aa */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/PluginComponentPanelBuilder$I.class */
    public final class I extends ComponentPanel {

        /* renamed from: try, reason: not valid java name */
        private final /* synthetic */ JLabel f708try;

        /* renamed from: float, reason: not valid java name */
        private final /* synthetic */ boolean f709float;

        /* renamed from: enum, reason: not valid java name */
        private final /* synthetic */ JLabel f711enum;

        public /* synthetic */ void setCommentText(String a) {
            if (StringUtil.equals(PluginComponentPanelBuilder.this.f696int, a)) {
                return;
            }
            PluginComponentPanelBuilder.this.f696int = a;
            J(a);
        }

        /* compiled from: aa */
        /* renamed from: com.aicode.util.PluginComponentPanelBuilder$I$a, reason: case insensitive filesystem */
        /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/PluginComponentPanelBuilder$I$a.class */
        class C0002a extends CommentLabel {
            /* renamed from: enum, reason: not valid java name */
            private static /* synthetic */ void m418enum(int a) {
                throw new IllegalStateException(String.format(RequestResultList.H("[gu\\_VvD _Ab}H~\b4P6\u000fE$lFn[8D\u007fV1Qc@%\u0010S/>\u0017yK"), IndentLineUtil.H("W\u0004':\u0017@\f_\u0019G\\Y��B5)%F\u0002O\u0003[6E\u0019[\u0010N\u0011E\u001aa+{\u001eH6^\u0016L\u0012L*#,_\u001e\\\u0019G\u001bO\u000bp\tY[\r\u001aaN\r_\u0015"), RequestResultList.H("IDa`GxgaZuP}Jh_\u001c\u000bN{5\fpU")));
            }

            public /* synthetic */ C0002a(String a) {
                super(a);
            }

            @NotNull
            public /* synthetic */ HyperlinkListener createHyperlinkListener() {
                HyperlinkListener hyperlinkListener = PluginComponentPanelBuilder.this.f702case;
                if (hyperlinkListener == null) {
                    m418enum(0);
                }
                return hyperlinkListener;
            }
        }

        /* compiled from: aa */
        /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/PluginComponentPanelBuilder$I$A.class */
        class A extends MouseAdapter {
            public /* synthetic */ void mouseExited(MouseEvent a) {
                PluginComponentPanelBuilder.this.f701if.dispatchEvent(I.this.e(a));
                a.consume();
            }

            public /* synthetic */ A() {
            }

            public /* synthetic */ void mouseEntered(MouseEvent a) {
                PluginComponentPanelBuilder.this.f701if.dispatchEvent(I.this.e(a));
                a.consume();
            }
        }

        public /* synthetic */ String getCommentText() {
            return PluginComponentPanelBuilder.this.f696int;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: synchronized, reason: not valid java name */
        private /* synthetic */ void m417synchronized(JPanel a, GridBagConstraints gridBagConstraints) {
            ContextHelpLabel create;
            JPanel jPanel;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.weightx = 0.0d;
            gridBagConstraints.anchor = 21;
            if (StringUtil.isNotEmpty(PluginComponentPanelBuilder.this.f698long)) {
                if (PluginComponentPanelBuilder.this.f699super || PluginComponentPanelBuilder.this.f697new != null) {
                    gridBagConstraints.insets = JBUI.insetsBottom(4);
                    gridBagConstraints.gridx = 1;
                    JPanel jPanel2 = new JPanel();
                    jPanel2.setLayout(new BoxLayout(jPanel2, 0));
                    if (PluginComponentPanelBuilder.this.f699super) {
                        jPanel2.add(this.f711enum);
                    }
                    if (PluginComponentPanelBuilder.this.f697new != null) {
                        jPanel2.add(new Box.Filler(JBUI.size(10, 0), JBUI.size(10, 0), JBUI.size(Integer.MAX_VALUE)));
                        jPanel2.add(PluginComponentPanelBuilder.this.f697new);
                    }
                    a.add(jPanel2, gridBagConstraints);
                    gridBagConstraints.gridy++;
                }
                if (!PluginComponentPanelBuilder.this.f699super) {
                    gridBagConstraints.gridx = 0;
                    switch (C0003i.f714enum[PluginComponentPanelBuilder.this.f704try.ordinal()]) {
                        case 1:
                            do {
                            } while (0 != 0);
                            gridBagConstraints.anchor = 19;
                            gridBagConstraints.insets = JBUI.insets(4, 0, 0, 8);
                            jPanel = a;
                            break;
                        case 2:
                            gridBagConstraints.anchor = 21;
                            gridBagConstraints.insets = JBUI.insetsRight(8);
                            jPanel = a;
                            break;
                        case 3:
                            gridBagConstraints.anchor = 20;
                            gridBagConstraints.insets = JBUI.insets(0, 0, 4, 8);
                        default:
                            jPanel = a;
                            break;
                    }
                    jPanel.add(this.f711enum, gridBagConstraints);
                }
            }
            gridBagConstraints.gridx += PluginComponentPanelBuilder.this.f699super ? 0 : 1;
            gridBagConstraints.weightx = 1.0d;
            gridBagConstraints.insets = new JBInsets(0, 0, 0, 0);
            gridBagConstraints.fill = PluginComponentPanelBuilder.this.f700for ? 1 : PluginComponentPanelBuilder.this.f694else ? 2 : 0;
            gridBagConstraints.weighty = PluginComponentPanelBuilder.this.f700for ? 1.0d : 0.0d;
            if (this.f709float) {
                a.add(PluginComponentPanelBuilder.this.f701if, gridBagConstraints);
            }
            if (StringUtil.isNotEmpty(PluginComponentPanelBuilder.this.f695char) || !PluginComponentPanelBuilder.this.f706byte) {
                JPanel jPanel3 = new JPanel();
                jPanel3.setLayout(new BoxLayout(jPanel3, 0));
                if (!this.f709float) {
                    jPanel3.add(PluginComponentPanelBuilder.this.f701if);
                }
                if (StringUtil.isNotEmpty(PluginComponentPanelBuilder.this.f695char)) {
                    if (!StringUtil.isNotEmpty(PluginComponentPanelBuilder.this.f705float) || PluginComponentPanelBuilder.this.f692true == null) {
                        create = ContextHelpLabel.create(PluginComponentPanelBuilder.this.f695char);
                    } else {
                        create = ContextHelpLabel.createWithLink((String) null, PluginComponentPanelBuilder.this.f695char, PluginComponentPanelBuilder.this.f705float, PluginComponentPanelBuilder.this.f692true);
                    }
                    ContextHelpLabel contextHelpLabel = create;
                    JBUI.Borders.emptyLeft(7).wrap(contextHelpLabel);
                    jPanel3.add(contextHelpLabel);
                    ComponentValidator.getInstance(PluginComponentPanelBuilder.this.f701if).ifPresent(componentValidator -> {
                        JLabel jLabel = new JLabel();
                        JBUI.Borders.emptyLeft(7).wrap(jLabel);
                        jLabel.setVisible(false);
                        jPanel3.add(jLabel);
                        jLabel.addMouseListener(new A());
                        PluginComponentPanelBuilder.this.f701if.addPropertyChangeListener(HandleCacheUtil.H("m;\u007f\"|<g3o*.0b<-wB\u0016"), a2 -> {
                            JPanel jPanel4;
                            if (a2.getNewValue() == null) {
                                jPanel4 = jPanel3;
                                jLabel.setVisible(false);
                                contextHelpLabel.setVisible(true);
                            } else if (!FontKt.H(".r}\u0010\u000b\u001b\u000e").equals(a2.getNewValue())) {
                                if (HandleCacheUtil.H("-3lC\u0001").equals(a2.getNewValue())) {
                                    jLabel.setIcon(AllIcons.General.BalloonError);
                                    jLabel.setVisible(true);
                                    contextHelpLabel.setVisible(false);
                                }
                                jPanel4 = jPanel3;
                            } else {
                                jPanel4 = jPanel3;
                                jLabel.setIcon(AllIcons.General.BalloonWarning);
                                jLabel.setVisible(true);
                                contextHelpLabel.setVisible(false);
                            }
                            jPanel4.revalidate();
                            jPanel3.repaint();
                        });
                    });
                    a.add(jPanel3, gridBagConstraints);
                } else if (!PluginComponentPanelBuilder.this.f706byte) {
                    if (!this.f709float) {
                        this.f708try.setBorder(PluginComponentPanelBuilder.this.l());
                        jPanel3.add(this.f708try);
                        a.add(jPanel3, gridBagConstraints);
                    } else {
                        GridBagConstraints gridBagConstraints2 = null;
                        gridBagConstraints.gridx++;
                        gridBagConstraints2.weightx = 0.0d;
                        gridBagConstraints.fill = gridBagConstraints;
                        ((GridBagConstraints) this).weighty = 0.0d;
                        a.add(gridBagConstraints.f708try, gridBagConstraints);
                    }
                }
            } else if (!this.f709float) {
                a.add(PluginComponentPanelBuilder.this.f701if, gridBagConstraints);
            }
            if (!this.f709float && !PluginComponentPanelBuilder.this.f694else) {
                GridBagConstraints gridBagConstraints3 = null;
                gridBagConstraints.gridx++;
                gridBagConstraints3.weightx = 1.0d;
                gridBagConstraints.fill = gridBagConstraints;
                a.add(new JPanel(), gridBagConstraints);
            }
            gridBagConstraints.fill = 2;
            gridBagConstraints.weighty = 0.0d;
            if (PluginComponentPanelBuilder.this.f706byte) {
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy++;
                gridBagConstraints.weightx = 0.0d;
                gridBagConstraints.anchor = 18;
                gridBagConstraints.insets = new JBInsets(0, 0, 0, 0);
                this.f708try.setBorder(PluginComponentPanelBuilder.this.l());
                a.add(this.f708try, gridBagConstraints);
                if (!PluginComponentPanelBuilder.this.f694else) {
                    GridBagConstraints gridBagConstraints4 = null;
                    gridBagConstraints.gridx++;
                    gridBagConstraints4.weightx = 1.0d;
                    gridBagConstraints.fill = gridBagConstraints;
                    a.add(new JPanel(), gridBagConstraints);
                }
            }
            PluginComponentPanelBuilder.this.f701if.putClientProperty(FontKt.H("(\u00077#\"6+1&+m!<;++$*')\u00013 6#"), this);
            gridBagConstraints.gridy++;
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        private /* synthetic */ I(boolean z) {
            I i;
            this.f709float = z;
            if (StringUtil.isNotEmpty(PluginComponentPanelBuilder.this.f698long)) {
                i = this;
                this.f711enum = new JLabel();
                LabeledComponent.TextWithMnemonic.fromTextWithMnemonic(PluginComponentPanelBuilder.this.f698long).setToLabel(this.f711enum);
                this.f711enum.setLabelFor(PluginComponentPanelBuilder.this.f701if);
            } else {
                i = this;
                i.f711enum = new JLabel("");
            }
            i.f708try = PluginComponentPanelBuilder.c(() -> {
                return new C0002a("");
            }, PluginComponentPanelBuilder.this.f696int, PluginComponentPanelBuilder.this.f706byte, 70, PluginComponentPanelBuilder.this.f703final);
            if (PluginComponentPanelBuilder.this.f707enum != null) {
                this.f708try.setIcon(PluginComponentPanelBuilder.this.f707enum);
            }
            this.f708try.setBorder(PluginComponentPanelBuilder.this.l());
        }

        private /* synthetic */ MouseEvent e(MouseEvent a) {
            Point point = a.getPoint();
            SwingUtilities.convertPoint(a.getComponent(), point, PluginComponentPanelBuilder.this.f701if);
            return new MouseEvent(PluginComponentPanelBuilder.this.f701if, a.getID(), a.getWhen(), a.getModifiers(), point.x, point.y, a.getXOnScreen(), a.getYOnScreen(), a.getClickCount(), a.isPopupTrigger(), a.getButton());
        }

        private /* synthetic */ void J(String a) {
            PluginComponentPanelBuilder.setCommentText(this.f708try, a, PluginComponentPanelBuilder.this.f706byte, 70);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private /* synthetic */ Border l() {
        if (!StringUtil.isNotEmpty(this.f696int)) {
            return JBUI.Borders.empty();
        }
        return new JBEmptyBorder(computeCommentInsets(this.f701if, this.f706byte));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: aa */
    /* renamed from: com.aicode.util.PluginComponentPanelBuilder$i, reason: case insensitive filesystem */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/PluginComponentPanelBuilder$i.class */
    public static /* synthetic */ class C0003i {

        /* renamed from: enum, reason: not valid java name */
        public static final /* synthetic */ int[] f714enum = new int[UI.Anchor.values().length];

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        static {
            try {
                f714enum[UI.Anchor.Top.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f714enum[UI.Anchor.Center.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f714enum[UI.Anchor.Bottom.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    public static /* synthetic */ Insets computeCommentInsets(@NotNull JComponent component, boolean z) {
        boolean z2;
        int i;
        int i2;
        if (component == null) {
            m415enum(10);
        }
        boolean z3 = false;
        boolean z4 = true;
        try {
            Class<?> cls = Class.forName(EditorUtils.H("%h1\r\u0011|=x*z$ujD\u001eo144vjB\u0005C9h6"));
            z3 = ((Boolean) cls.getMethod(OpenTelemetryUtil.H("i7Z%`%\u007f\rF\u0001u%d8@(f\u0015l%~2"), new Class[0]).invoke(null, new Object[0])).booleanValue();
            z4 = ((Boolean) cls.getMethod(EditorUtils.H("\u0011a\u001cs\"s?H-_[6\u0011u.t\u0005y(P(d6"), new Class[0]).invoke(null, new Object[0])).booleanValue();
            z2 = z;
        } catch (Exception e) {
            z2 = z;
        }
        if (!z2) {
            int i3 = 14;
            if (!(component instanceof JRadioButton) && !(component instanceof JCheckBox)) {
                if ((component instanceof JTextField) || (component instanceof EditorTextComponent) || (component instanceof JComboBox) || (component instanceof ComponentWithBrowseButton)) {
                    i3 = z3 ? 13 : 14;
                }
                i = i3;
            } else {
                i = z3 ? 8 : 13;
            }
            JBInsets insetsLeft = JBUI.insetsLeft(i);
            if (insetsLeft == null) {
                m415enum(12);
            }
            return insetsLeft;
        }
        int i4 = 8;
        int i5 = 2;
        int i6 = 0;
        if ((component instanceof JRadioButton) || (component instanceof JCheckBox)) {
            i6 = z4 ? 10 : z3 ? 8 : 9;
            if (!(component instanceof JCheckBox)) {
                i5 = z3 ? 26 : z4 ? 17 : 23;
                i2 = 8;
            } else {
                return new Insets(0, UIUtil.getCheckBoxTextHorizontalOffset((JCheckBox) component), JBUIScale.scale(i6), 0);
            }
        } else if ((component instanceof JTextField) || (component instanceof EditorTextComponent) || (component instanceof JComboBox) || (component instanceof ComponentWithBrowseButton)) {
            int i7 = z4 ? 3 : 4;
            i5 = z4 ? 2 : z3 ? 5 : 4;
            i6 = z4 ? 10 : z3 ? 8 : 9;
            i2 = i7;
        } else {
            if (component instanceof JButton) {
                i4 = z4 ? 2 : 4;
                i5 = z4 ? 2 : z3 ? 5 : 4;
                i6 = 0;
            }
            i2 = i4;
        }
        JBInsets insets = JBUI.insets(i2, i5, i6, 0);
        if (insets == null) {
            m415enum(11);
        }
        return insets;
    }

    @NotNull
    public static /* synthetic */ JLabel createCommentComponent(@NlsContexts.DetailedDescription @Nullable String commentText, boolean z, int maxLineLength, boolean z2) {
        JLabel c = c(() -> {
            return new CommentLabel("");
        }, commentText, z, maxLineLength, z2);
        if (c == null) {
            m415enum(13);
        }
        return c;
    }

    /* compiled from: aa */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/PluginComponentPanelBuilder$CommentLabel.class */
    public static class CommentLabel extends JBLabel {
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m416enum(int a) {
            throw new IllegalArgumentException(String.format(JComponentKt.H("\u0003\u0016?\u000b\u0011?#\u001fb\u0002&\u001d`& '-10\u000f,F8\u000f;\u000e3\u001d|K\u001did@2@a\b/Og\u0017v[\u0013f \u001e=\u001ci\u0001/\u0012i\r'D7\n-\u000b"), RequestTimeoutException.H("$\u0014!\f"), JComponentKt.H(".\u0004/K(\u0006#\t\n-v\n1\n,I\u0018\u0002<\b7\u0016KA\u00029,\u000b$\t57(\u0001'\b\u001a\u000b\t*)\u000e<L\n��-\u000b,\u00016(8\u001d$\u000b"), RequestTimeoutException.H("k\u001f>\u0018-F")));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private /* synthetic */ CommentLabel(@NlsContexts.Label @NotNull String a) {
            super(a);
            if (a == null) {
                m416enum(0);
            }
            setForeground(JBColor.namedColor(RequestTimeoutException.H("\u0014\u0018:\u001c<_2\u0014'\u000f?7&\u00100\u0004?\u00047\u001c"), new JBColor(Gray.x78, Gray.x8C)));
        }

        public /* synthetic */ void setUI(LabelUI a) {
            super.setUI(a);
            setFont(PluginComponentPanelBuilder.getCommentFont(getFont()));
        }
    }

    public /* synthetic */ void addToPanel(JPanel a, GridBagConstraints a2, boolean z) {
        if (constrainsValid()) {
            new I(z).m417synchronized(a, a2);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public /* synthetic */ int gridWidth() {
        if (this.f706byte) {
            return 2;
        }
        return this.f694else ? 4 : 3;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static /* synthetic */ JLabel c(@NotNull Supplier<? extends JBLabel> supplier, @NlsContexts.DetailedDescription @Nullable String commentText, boolean z, int maxLineLength, boolean z2) {
        if (supplier == null) {
            m415enum(14);
        }
        boolean booleanProperty = SystemProperties.getBooleanProperty(OpenTelemetryUtil.H("X\u0011e%!>mnn&N\nq>|bn&u8e\"\u007f2"), true);
        JBLabel allowAutoWrapping = supplier.get().setCopyable(booleanProperty).setAllowAutoWrapping(z2);
        allowAutoWrapping.setVerticalTextPosition(1);
        allowAutoWrapping.setFocusable(false);
        if (!booleanProperty) {
            allowAutoWrapping.setText(commentText);
            return allowAutoWrapping;
        }
        setCommentText(allowAutoWrapping, commentText, z, maxLineLength);
        return allowAutoWrapping;
    }

    public static /* synthetic */ Font getCommentFont(Font a) {
        return a.deriveFont(a.getSize2D());
    }

    public static /* synthetic */ JLabel createNonWrappingCommentComponent(@NlsContexts.DetailedDescription @NotNull String commentText) {
        if (commentText == null) {
            m415enum(15);
        }
        return new CommentLabel(commentText);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ void setCommentText(@NotNull JLabel component, @NlsContexts.DetailedDescription @Nullable String commentText, boolean z, int maxLineLength) {
        HtmlChunk.Element wrapWith;
        JLabel jLabel;
        if (component == null) {
            m415enum(16);
        }
        if (commentText == null) {
            return;
        }
        HtmlChunk raw = HtmlChunk.raw(commentText);
        if (maxLineLength <= 0 || commentText.length() <= maxLineLength || !z) {
            wrapWith = raw.wrapWith(HtmlChunk.div());
            jLabel = component;
        } else {
            jLabel = component;
            wrapWith = raw.wrapWith(HtmlChunk.div().attr(EditorUtils.H(";\u007f)u2"), component.getFontMetrics(component.getFont()).stringWidth(commentText.substring(0, maxLineLength))));
        }
        jLabel.setText(new HtmlBuilder().append(HtmlChunk.raw("")).append(wrapWith.wrapWith(OpenTelemetryUtil.H("f/w."))).wrapWith(EditorUtils.H("~9l6")).toString());
    }
}
