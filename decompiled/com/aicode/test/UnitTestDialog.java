package com.aicode.test;

import com.aicode.action.batch.BatchUnitTestDialog;
import com.aicode.action.batch.ExcludeMethodConfigurable;
import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.action.batch.ResultTree;
import com.aicode.diff.GenericUtils;
import com.aicode.enums.DuplicateFileNameSwitchEnum;
import com.aicode.enums.GenaratebyTemplateSwitchEnum;
import com.aicode.enums.UnitTestBaseEnum;
import com.aicode.enums.UnitTestMockEnum;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.UnitTestSettingsState;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.PluginComponentPanelBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.IconManager;
import com.intellij.ui.TitledSeparator;
import com.intellij.ui.TreeUIHelper;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.FormBuilder;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: hc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/UnitTestDialog.class */
public class UnitTestDialog extends DialogWrapper {

    /* renamed from: char, reason: not valid java name */
    private JBCheckBox f625char;

    /* renamed from: int, reason: not valid java name */
    private JRadioButton f626int;

    /* renamed from: new, reason: not valid java name */
    private String f627new;

    /* renamed from: long, reason: not valid java name */
    private ComboBox f628long;

    /* renamed from: super, reason: not valid java name */
    private ComboBox f629super;

    /* renamed from: for, reason: not valid java name */
    private JRadioButton f630for;

    /* renamed from: if, reason: not valid java name */
    private JLabel f631if;

    /* renamed from: case, reason: not valid java name */
    private ExcludeMethodConfigurable f632case;

    /* renamed from: final, reason: not valid java name */
    private static final Logger f633final = Logger.getInstance(UnitTestDialog.class);

    /* renamed from: try, reason: not valid java name */
    private String f634try;

    /* renamed from: float, reason: not valid java name */
    private JPanel f635float;

    /* renamed from: byte, reason: not valid java name */
    private final Project f636byte;

    /* renamed from: enum, reason: not valid java name */
    private JPanel f637enum;

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m357enum(int a) {
        throw new IllegalStateException(String.format(GenericUtils.H("\u0005\u0003->Ve\u000f\u0007x=:#*%\u0006Jv(kh1j\u001f\u000f,#w15&y#4-&)6p->7?"), AICodeStringUtil.H("\u0005\u0002p9GDBEXR3cHUO\u001fiYesuOZV`FF@@C"), GenericUtils.H("1+40-6\u001a;$*$5 ")));
    }

    private JComponent RA(JBCheckBox a) {
        JBPanel jBPanel = new JBPanel();
        GroupLayout groupLayout = new GroupLayout(jBPanel);
        jBPanel.setLayout(groupLayout);
        JBLabel jBLabel = new JBLabel(BasicActionsBundle.message(AICodeStringUtil.H("NIUVUPH\u000f|bEE\u000f_R^h9YCHD\u0012RtdM_MG\n[NXCA"), new Object[0]));
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup().addComponent(jBLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(a));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jBLabel).addComponent(a));
        return jBPanel;
    }

    @NotNull
    public Action[] createActions() {
        Action oKAction = getOKAction();
        oKAction.putValue(AICodeStringUtil.H("iMBA"), GenericUtils.H("畚扝"));
        Action cancelAction = getCancelAction();
        cancelAction.putValue(AICodeStringUtil.H("iMBA"), GenericUtils.H("厓淅"));
        Action[] actionArr = {oKAction, cancelAction};
        if (actionArr == null) {
            m357enum(0);
        }
        return actionArr;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public MethodGeneratorConfig getSelectedValue(MethodGeneratorConfig a) {
        UnitTestMockEnum findByName;
        MethodGeneratorConfig methodGeneratorConfig;
        a.setTestPrivate(this.f625char.isSelected());
        String str = (String) this.f628long.getSelectedItem();
        String str2 = (String) this.f629super.getSelectedItem();
        UnitTestBaseEnum findByName2 = UnitTestBaseEnum.findByName(str);
        if (StringUtils.equals(UnitTestMockEnum.OFF.getDependency(), str2)) {
            findByName = UnitTestMockEnum.findByDependency();
            methodGeneratorConfig = a;
        } else {
            findByName = UnitTestMockEnum.findByName(str2);
            methodGeneratorConfig = a;
        }
        methodGeneratorConfig.setTestFramework(findByName2);
        a.setMockFramework(findByName);
        a.setEnabledGenerateByTemplate(this.f626int.isSelected());
        a.setExcludeMethodList(this.f632case.getBody());
        UnitTestSettingsState unitTestSettingsState = UnitTestSettingsState.getInstance();
        unitTestSettingsState.testFramework = str;
        unitTestSettingsState.mockFramework = str2;
        unitTestSettingsState.enabledGenerateByTemplate = hB();
        unitTestSettingsState.testPrivate = this.f625char.isSelected();
        return a;
    }

    public UnitTestDialog(Project a) {
        super(true);
        this.f634try = AICodeStringUtil.H("nhca");
        setTitle(BasicActionsBundle.message(GenericUtils.H("<89935w$?0~,+2+$q#+>de"), new Object[0]));
        this.f636byte = a;
        init();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Nullable
    public JComponent createCenterPanel() {
        JBPanel jBPanel = new JBPanel();
        TreeUIHelper.getInstance().installTreeSpeedSearch(new ResultTree());
        this.f628long = new ComboBox();
        this.f628long.setPreferredSize(new Dimension(230, this.f628long.getPreferredSize().height));
        UnitTestBaseEnum[] values = UnitTestBaseEnum.values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            UnitTestBaseEnum unitTestBaseEnum = values[i2];
            if (!StringUtils.equals(AICodeStringUtil.H("FY[K"), unitTestBaseEnum.getName())) {
                this.f628long.addItem(unitTestBaseEnum.getName());
            }
            i2++;
            i = i2;
        }
        this.f629super = new ComboBox();
        this.f629super.setPreferredSize(new Dimension(230, this.f628long.getPreferredSize().height));
        this.f629super.removeAllItems();
        this.f629super.addItem(UnitTestMockEnum.POWER_MOCK.getName());
        this.f629super.addItem(UnitTestMockEnum.MOCKITO.getName());
        this.f629super.addItem(UnitTestMockEnum.OFF.getDependency());
        this.f628long.addItemListener(a -> {
            if (a.getStateChange() != 1) {
                return;
            }
            String str = (String) a.getItem();
            if (!StringUtils.equals(UnitTestBaseEnum.JUNIT_FOUR.getName(), str)) {
                this.f629super.removeAllItems();
                this.f629super.addItem(UnitTestMockEnum.MOCKITO.getName());
                this.f629super.addItem(UnitTestMockEnum.OFF.getDependency());
            } else {
                this.f629super.removeAllItems();
                this.f629super.addItem(UnitTestMockEnum.POWER_MOCK.getName());
                this.f629super.addItem(UnitTestMockEnum.MOCKITO.getName());
                this.f629super.addItem(UnitTestMockEnum.OFF.getDependency());
            }
            f633final.info("selected framework: " + str);
        });
        this.f629super.addItemListener(a2 -> {
            if (a2.getStateChange() != 1) {
                return;
            }
            f633final.info("mock framework: " + ((String) a2.getItem()));
        });
        this.f628long.setSelectedItem(UnitTestSettingsState.getInstance().testFramework);
        this.f629super.setSelectedItem(UnitTestSettingsState.getInstance().mockFramework);
        this.f635float = new JPanel(new FlowLayout(0, 5, 5));
        this.f635float.add(new JBLabel(BasicActionsBundle.message(GenericUtils.H(".-$\u0014\u00138y5>.11\u007f$7<)j86(#q%/=>\u0007\u001c'>v2;d'>)<\n\u000f#:v$:/67"), new Object[0])));
        this.f626int = new JRadioButton();
        this.f626int.setText(BasicActionsBundle.message(AICodeStringUtil.H("DBH��\u0004[\u0019OGOST\u0019\u0013\u0003tb\bYDYH\u0019{rCCIQHR\"eX\u0004]GI_KM[A"), new Object[0]));
        this.f626int.setAlignmentX(5.0f);
        this.f630for = new JRadioButton();
        this.f630for.setText(BasicActionsBundle.message(GenericUtils.H("\u0011\u001511>8t08%21{(*%'u#:1>}<\u0010\u00136)9$'d1\"j8\u0003\u0003'39$6u;;"), new Object[0]));
        this.f630for.setIconTextGap(5);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.f626int);
        buttonGroup.add(this.f630for);
        this.f635float.add(this.f626int);
        this.f635float.add(this.f630for);
        this.f626int.addActionListener(actionEvent -> {
            setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum.ENABLED);
        });
        this.f630for.addActionListener(actionEvent2 -> {
            setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum.DISABLED);
        });
        this.f625char = new JBCheckBox(BasicActionsBundle.message(AICodeStringUtil.H("OCIKO\u0001C^VYES\u001eIY\u000f\u00193bC^U\u0004LEuaLR^\u001eQRxoNN\u0007AKASIAP"), new Object[0]), UnitTestSettingsState.getInstance().testPrivate);
        this.f632case = new ExcludeMethodConfigurable();
        jBPanel.add(FormBuilder.createFormBuilder().addComponent(FormBuilder.createFormBuilder().addComponent(new TitledSeparator(BasicActionsBundle.message(GenericUtils.H("'#==>8l(2/\u0016\u0015}.696d'>78H\u001d2/9\"2/5 "), new Object[0]))).addComponent(Dc(this.f628long, this.f629super)).addComponent(za()).addComponent(new TitledSeparator(BasicActionsBundle.message(AICodeStringUtil.H("CHBJNJ\b\u0004\fHTE\bN^UCH\u0019xeR\u0003DR_[isH\bVUH_cc\u000fYLRE]FX@V"), new Object[0]))).addComponent(RA(this.f625char)).addComponent(this.f632case).getPanel()).getPanel(), GenericUtils.H("\u001b5=/? "));
        return jBPanel;
    }

    private JComponent Dc(ComboBox<Object> comboBox, ComboBox<Object> comboBox2) {
        JPanel jPanel = new JPanel(new FlowLayout(0, 5, 5));
        jPanel.add(new JLabel(BasicActionsBundle.message(AICodeStringUtil.H("_XC@RW\u0012U\u0007\u0019~~\bXOCH\u0019hr^R\u0015VNVabVE[I\n[NXCA"), new Object[0])));
        jPanel.add(comboBox);
        JLabel jLabel = new JLabel(BasicActionsBundle.message(GenericUtils.H("s-$52\"c +ls\u000bE->6#l>\u0007\u0019'u(\"!!\\\u001c-6::-=+:\u007f-\u001b\u000e98"), new Object[0]));
        jLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jPanel.add(jLabel);
        jPanel.add(comboBox2);
        return jPanel;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void changeGenerateByTemplateComponent(String a) {
        Icon icon;
        String message;
        UnitTestDialog unitTestDialog;
        if (this.f631if == null) {
            return;
        }
        if (StringUtils.equals(GenericUtils.H("\u0011\u0014\u000e\u000f"), a)) {
            icon = IconManager.getInstance().getIcon(AICodeStringUtil.H("DRLBu8^RZDID#tTIJGW\\\t_YC"), BatchUnitTestDialog.class);
            message = BasicActionsBundle.message(GenericUtils.H("!%==\u001d\u001ba%\u0003\u001e03u&('.|#:1>}(\u0010\u000f/4) h=-0,% u14./"), new Object[0]);
            unitTestDialog = this;
        } else if (!StringUtils.equals(AICodeStringUtil.H("ey|}"), a)) {
            icon = AllIcons.General.BalloonError;
            message = BasicActionsBundle.message(AICodeStringUtil.H("DCBADAH\u000f]CNN\u0015ER^\u0012CisUY\u000fYYEjr_U\u0015CHVxrR\u0004ZCPZUM[A"), new Object[0]);
            unitTestDialog = this;
            unitTestDialog.setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum.ENABLED);
        } else {
            icon = AllIcons.General.BalloonWarning;
            message = BasicActionsBundle.message(GenericUtils.H("!%==\u001d\u001ba%\u0003\u001e03u&('.|#:1>}(\u0010\u000f/4) h=-0,% u:%13"), new Object[0]);
            unitTestDialog = this;
        }
        Icon icon2 = icon;
        String str = message;
        SwingUtilities.invokeLater(() -> {
            this.f631if.setIcon(icon2);
            this.f631if.setText(str);
            this.f631if.revalidate();
            this.f631if.repaint();
        });
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private GenaratebyTemplateSwitchEnum hB() {
        if (!this.f626int.isSelected()) {
            return GenaratebyTemplateSwitchEnum.DISABLED;
        }
        return GenaratebyTemplateSwitchEnum.ENABLED;
    }

    private JPanel za() {
        JBLabel jBLabel = new JBLabel(BasicActionsBundle.message(GenericUtils.H("s-$52\"c +ls\u000bE->6#l>\u0007\u0019'u6(0<\u0017\b,y$+;&,\"\u007f-%0;:"), new Object[0]));
        jBLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        this.f635float.add(jBLabel);
        this.f631if = new JBLabel();
        this.f635float.add(this.f631if);
        JBLabel jBLabel2 = new JBLabel(AllIcons.General.ContextHelp);
        jBLabel2.setToolTipText(BasicActionsBundle.message(AICodeStringUtil.H("CHBJNJ\b\u0004\fHTE\bN^UCH\u0019xeR\u0003RONAye^\bHD]Cyt\u000fBLNT\u0001SIWP"), new Object[0]));
        this.f635float.add(jBLabel2);
        this.f627new = BasicActionsBundle.message(GenericUtils.H("4011<:w33.036e,#'u1(1>6w\u0006\u0005=\">#'d��\u0013}/  2&\u0013\u000e:y66t:<=!w8!/+"), new Object[0]);
        this.f637enum = new PluginComponentPanelBuilder(this.f635float).withLabel(BasicActionsBundle.message(AICodeStringUtil.H("IDNAEK\tOG\u0012\u000eT\u0019XHRD\u0012C\u0003\u001ei8AHOONVhr\u0003DB\u001eHRawMK]G\n[NXCA"), new Object[0])).withComment(this.f627new).createPanel();
        setGenerateByTemplateSwitch(UnitTestSettingsState.getInstance().enabledGenerateByTemplate);
        changeGenerateByTemplateComponent(this.f634try);
        return this.f637enum;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum a) {
        UnitTestDialog unitTestDialog;
        if (a == null) {
            return;
        }
        if (!DuplicateFileNameSwitchEnum.ENABLED.getType().equals(a.getType())) {
            this.f630for.setSelected(true);
            this.f626int.setSelected(false);
            this.f627new = BasicActionsBundle.message(AICodeStringUtil.H("FA\b\u000b@E\u000fHJTDD\u0002RCO\u0012CHR^R\u0015WYY\u0003\u001f|bC\u0003CS\u0012Cyz]JZDY\u0019mn\u000fBLNT\u0001SIWP"), new Object[0]);
            unitTestDialog = this;
        } else {
            this.f626int.setSelected(true);
            this.f630for.setSelected(false);
            this.f627new = BasicActionsBundle.message(GenericUtils.H(".<5$#\u0016W1:,3;u1\">+l>6(��R(\"\f\u000f!:/6h,#|#:/:?:\u0011\bo!:;2d!8&\""), new Object[0]);
            unitTestDialog = this;
        }
        unitTestDialog.f637enum.getComponents()[2].setText(this.f627new);
        this.f637enum.validate();
        this.f637enum.repaint();
    }
}
