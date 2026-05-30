package com.aicode.action.batch;

import com.aicode.action.batch.node.FileNode;
import com.aicode.content.util.EditorUtils;
import com.aicode.enums.BatchTestUnitLimt;
import com.aicode.enums.DuplicateFileNameSwitchEnum;
import com.aicode.enums.DuplicateRule;
import com.aicode.enums.GenaratebyTemplateSwitchEnum;
import com.aicode.enums.TestGenerationProcess;
import com.aicode.enums.UnitTestBaseEnum;
import com.aicode.enums.UnitTestMockEnum;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.BatchUnitTestSettingsState;
import com.aicode.util.JComponentKt;
import com.aicode.util.PluginComponentPanelBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.SourceFolder;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.IconManager;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.TitledSeparator;
import com.intellij.ui.TreeUIHelper;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Objects;
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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: pi */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/BatchUnitTestDialog.class */
public class BatchUnitTestDialog extends DialogWrapper {

    /* renamed from: native, reason: not valid java name */
    private JPanel f20native;

    /* renamed from: assert, reason: not valid java name */
    private String f21assert;

    /* renamed from: while, reason: not valid java name */
    private ComboBox f22while;

    /* renamed from: throw, reason: not valid java name */
    private List<String> f23throw;

    /* renamed from: null, reason: not valid java name */
    private ComboBox f24null;

    /* renamed from: void, reason: not valid java name */
    private final Module f25void;

    /* renamed from: goto, reason: not valid java name */
    private ExcludeMethodConfigurable f26goto;

    /* renamed from: short, reason: not valid java name */
    private JBTextField f27short;

    /* renamed from: catch, reason: not valid java name */
    private ComboBox f28catch;

    /* renamed from: const, reason: not valid java name */
    private final Project f29const;

    /* renamed from: false, reason: not valid java name */
    private JRadioButton f30false;

    /* renamed from: do, reason: not valid java name */
    private JRadioButton f31do;

    /* renamed from: break, reason: not valid java name */
    private JPanel f32break;

    /* renamed from: class, reason: not valid java name */
    private JPanel f33class;

    /* renamed from: true, reason: not valid java name */
    private final String f34true;

    /* renamed from: this, reason: not valid java name */
    private TextFieldWithBrowseButton f35this;

    /* renamed from: else, reason: not valid java name */
    private JRadioButton f36else;

    /* renamed from: char, reason: not valid java name */
    private JPanel f37char;

    /* renamed from: int, reason: not valid java name */
    private static final Logger f38int = Logger.getInstance(BatchUnitTestDialog.class);

    /* renamed from: new, reason: not valid java name */
    private JRadioButton f39new;

    /* renamed from: long, reason: not valid java name */
    private final String f40long;

    /* renamed from: super, reason: not valid java name */
    private String f41super;

    /* renamed from: for, reason: not valid java name */
    private String f42for;

    /* renamed from: if, reason: not valid java name */
    private ComboBox f43if;

    /* renamed from: case, reason: not valid java name */
    private JBCheckBox f44case;

    /* renamed from: final, reason: not valid java name */
    private List<FileNode> f45final;

    /* renamed from: try, reason: not valid java name */
    private final String f46try;

    /* renamed from: float, reason: not valid java name */
    private JRadioButton f47float;

    /* renamed from: byte, reason: not valid java name */
    private JBCheckBox f48byte;

    /* renamed from: enum, reason: not valid java name */
    private JLabel f49enum;

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m41enum(int a) {
        throw new IllegalStateException(String.format(EditorUtils.H("[\u000e2r\u007f\u001f~%'1u?s/B]854dt|J\te9 5h(\u0017\u001e\u007f5g;qdr2x#"), JComponentKt.H("\b7\u0013`\b-\u0001\u0016;'K$��,\u0017\u00170f\r>\r;\u0016G\f$\u0017.\u0003\u0015\b*\u0011\u001f\b:\u001b\u0006\r8\u0013.��"), EditorUtils.H("?E\t{5w\b|0u(z<")));
    }

    @NotNull
    public Action[] createActions() {
        Action oKAction = getOKAction();
        oKAction.putValue(JComponentKt.H("\f\u00052\u001c"), EditorUtils.H("甀扔卉億浟讚"));
        Action cancelAction = getCancelAction();
        cancelAction.putValue(JComponentKt.H("\f\u00052\u001c"), EditorUtils.H("参淇"));
        Action[] actionArr = {oKAction, cancelAction};
        if (actionArr == null) {
            m41enum(0);
        }
        return actionArr;
    }

    public BatchUnitTestDialog(Project a, Module a2, String a3, String a4, String a5, List<FileNode> list, List<String> list2, String a6) {
        super(true);
        setTitle(BasicActionsBundle.message(EditorUtils.H("E\u0012s s&)>F\bu%..i5CBn$a=10u3x*"), new Object[0]));
        this.f45final = list;
        this.f23throw = list2;
        this.f29const = a;
        this.f25void = a2;
        this.f42for = a3;
        this.f34true = a4;
        this.f46try = a5;
        this.f40long = a6;
        init();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Nullable
    public JComponent createCenterPanel() {
        JBPanel jBPanel = new JBPanel();
        TreeUIHelper.getInstance().installTreeSpeedSearch(new ResultTree());
        this.f24null = new ComboBox();
        this.f24null.setPreferredSize(new Dimension(230, this.f24null.getPreferredSize().height));
        UnitTestBaseEnum[] values = UnitTestBaseEnum.values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            UnitTestBaseEnum unitTestBaseEnum = values[i2];
            if (!StringUtils.equals(JComponentKt.H("(\u001a4\t"), unitTestBaseEnum.getName())) {
                this.f24null.addItem(unitTestBaseEnum.getName());
            }
            i2++;
            i = i2;
        }
        this.f22while = new ComboBox();
        this.f22while.setPreferredSize(new Dimension(230, this.f24null.getPreferredSize().height));
        this.f22while.removeAllItems();
        this.f22while.addItem(UnitTestMockEnum.POWER_MOCK.getName());
        this.f22while.addItem(UnitTestMockEnum.MOCKITO.getName());
        this.f22while.addItem(UnitTestMockEnum.OFF.getDependency());
        this.f22while.addItemListener(a -> {
            if (a.getStateChange() == 1) {
                f38int.info("batch mock framework: " + ((String) a.getItem()));
            }
        });
        this.f24null.addItemListener(a2 -> {
            if (a2.getStateChange() == 1) {
                String str = (String) a2.getItem();
                if (!StringUtils.equals(UnitTestBaseEnum.JUNIT_FOUR.getName(), str)) {
                    this.f22while.removeAllItems();
                    this.f22while.addItem(UnitTestMockEnum.MOCKITO.getName());
                    this.f22while.addItem(UnitTestMockEnum.OFF.getDependency());
                } else {
                    this.f22while.removeAllItems();
                    this.f22while.addItem(UnitTestMockEnum.POWER_MOCK.getName());
                    this.f22while.addItem(UnitTestMockEnum.MOCKITO.getName());
                    this.f22while.addItem(UnitTestMockEnum.OFF.getDependency());
                }
                f38int.info("batch selected framework: " + str);
            }
        });
        this.f24null.setSelectedItem(BatchUnitTestSettingsState.getInstance().testFramework);
        this.f22while.setSelectedItem(BatchUnitTestSettingsState.getInstance().mockFramework);
        this.f33class = new JBPanel(new FlowLayout(0, 5, 10));
        this.f36else = new JBRadioButton();
        this.f36else.setText(BasicActionsBundle.message(EditorUtils.H("e~$t `rr*o#5(D\u0004{=)(u8onB\bm*s\"f(BRp$l>i=Z\t4.d,m3n.`*"), new Object[0]));
        this.f39new = new JBRadioButton();
        this.f39new.setText(BasicActionsBundle.message(JComponentKt.H("\u000b!+\u0005$\fn\u0004\"\u0011(\u0005a\u001c0\u0011=A9\u000e+\ng\u000b\u001a9%\u0006!\u0005,\u001bg\t7\u0014\u00194,\u0006'J:\u0004)\u0016"), new Object[0]));
        this.f30false = new JBRadioButton();
        this.f30false.setText(BasicActionsBundle.message(EditorUtils.H(")}'a5wey!)eYDg'n(>?~3RSy3j-n?F\bscf2k9Y\rw$<*p!d.g;"), new Object[0]));
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.f36else);
        buttonGroup.add(this.f39new);
        buttonGroup.add(this.f30false);
        this.f33class.add(this.f36else);
        this.f33class.add(this.f39new);
        this.f33class.add(this.f30false);
        this.f36else.addActionListener(actionEvent -> {
            setDuplicateFileNameSwitch(DuplicateRule.OVERWRITE);
        });
        this.f39new.addActionListener(actionEvent2 -> {
            setDuplicateFileNameSwitch(DuplicateRule.SKIP);
        });
        this.f30false.addActionListener(actionEvent3 -> {
            setDuplicateFileNameSwitch(DuplicateRule.COEXIST);
        });
        this.f20native = new JBPanel(new FlowLayout(0, 5, 5));
        this.f47float = new JRadioButton();
        this.f47float.setText(BasicActionsBundle.message(JComponentKt.H("��\"\u0005&\u000f$K)\f;\n6V<\u0001$\u001fv\n,\u001c\u001bg.\n,\u0001*\u001f=\np\u001a\u0005t9\u000e/\u0014%\u000e4\u0003"), new Object[0]));
        this.f47float.setAlignmentX(5.0f);
        this.f31do = new JRadioButton();
        this.f31do.setText(BasicActionsBundle.message(EditorUtils.H("d3~-r'sdP\u001eq!))~\"onR\u0018n24&b2B\u000ew9eue%\u0019\u0018\u007f,b%~0yiu&"), new Object[0]));
        this.f31do.setIconTextGap(5);
        ButtonGroup buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(this.f47float);
        buttonGroup2.add(this.f31do);
        this.f20native.add(this.f47float);
        this.f20native.add(this.f31do);
        this.f47float.addActionListener(actionEvent4 -> {
            setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum.ENABLED);
        });
        this.f31do.addActionListener(actionEvent5 -> {
            setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum.DISABLED);
        });
        this.f28catch = new ComboBox();
        this.f28catch.setPreferredSize(new Dimension(200, this.f28catch.getPreferredSize().height));
        this.f28catch.addItem(TestGenerationProcess.GENERATION.getName());
        this.f28catch.addItem(TestGenerationProcess.GENERATION_BUILD.getName());
        this.f28catch.addItem(TestGenerationProcess.GENERATION_BUILD_EXECUTE.getName());
        this.f28catch.setSelectedItem(BatchUnitTestSettingsState.getInstance().testGenerationProcess.getName());
        this.f28catch.addItemListener(a3 -> {
            if (a3.getStateChange() == 1) {
                String str = (String) a3.getItem();
                BatchUnitTestSettingsState.getInstance().testGenerationProcess = TestGenerationProcess.loadByName(str);
            }
        });
        this.f43if = new ComboBox();
        this.f43if.setPreferredSize(new Dimension(200, this.f43if.getPreferredSize().height));
        this.f43if.addItem(BatchTestUnitLimt.FIVE.getLimit());
        this.f43if.addItem(BatchTestUnitLimt.TEN.getLimit());
        this.f43if.addItem(BatchTestUnitLimt.TWENTY.getLimit());
        this.f43if.addItem(BatchTestUnitLimt.FIFTY.getLimit());
        this.f43if.setSelectedItem(BatchUnitTestSettingsState.getInstance().batchTestUnitLimt.getLimit());
        this.f43if.addItemListener(a4 -> {
            if (a4.getStateChange() == 1) {
                Integer num = (Integer) a4.getItem();
                BatchUnitTestSettingsState.getInstance().batchTestUnitLimt = BatchTestUnitLimt.loadLimt(num);
            }
        });
        this.f48byte = new JBCheckBox(BasicActionsBundle.message(JComponentKt.H("-*\r+\u0002'H!\u0004?\u000e'G+\u0016 \u001bc\u001f=\r=A\u001f; \u0019#\u0010=P$\n*\u0010\u0013>c\b-\n=\n.\u0012"), new Object[0]), BatchUnitTestSettingsState.getInstance().testPrivate);
        this.f44case = new JBCheckBox(BasicActionsBundle.message(EditorUtils.H(">i_\f{.)>q?x(\b\bs/nos9T\b8>a-brG\rn)<*p*h\"z;"), new Object[0]), BatchUnitTestSettingsState.getInstance().savePath);
        this.f27short = new JBTextField();
        this.f35this = new TextFieldWithBrowseButton();
        this.f26goto = new ExcludeMethodConfigurable();
        JBScrollPane jBScrollPane = new JBScrollPane(FormBuilder.createFormBuilder().addComponent(createUnitTestTreeView(this.f45final)).addComponent(new TitledSeparator(BasicActionsBundle.message(JComponentKt.H("=\u0017'\t$\fv\u001c(\u001b\f!g\u001a,\r,P=\n-\fR)(\u001b#\u0016(\u001b/\u0014"), new Object[0]))).addComponent(Dc(this.f24null, this.f22while)).addComponent(pD(this.f28catch, this.f43if)).addComponent(za()).addComponent(new TitledSeparator(BasicActionsBundle.message(EditorUtils.H("*h2v\"|n?gE\tzgr2y?54C\u000eih\u007f9d0R\u0018scm>s4X\b42w9~6}3{="), new Object[0]))).addComponent(RA(this.f48byte)).addComponent(this.f26goto).addComponent(new TitledSeparator(BasicActionsBundle.message(JComponentKt.H("\u0006$\u0003)��9V+\u000e9\b0P<\u0001\u0006=g\u001b'\u0017,P/\u00062\u001dR)(\u001b#\u0016(\u001b/\u0014"), new Object[0]))).addComponent(iF()).addComponent(iD(this.f27short)).addComponent(rD(this.f35this, this.f44case)).getPanel(), 20, 32);
        jBScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        jBScrollPane.setPreferredSize(new Dimension(740, 550));
        jBPanel.add(jBScrollPane);
        jBPanel.setVisible(true);
        return jBPanel;
    }

    private JComponent pD(ComboBox<Object> comboBox, ComboBox<Object> comboBox2) {
        JPanel jPanel = new JPanel(new FlowLayout(0, 5, 2));
        jPanel.add(new JBLabel(BasicActionsBundle.message(JComponentKt.H("\n+\fdM\tf/\n,\u001d'G1\f\u0010+l\u0010 \u0010,P\u001f;'\n-\u0018,\u0017\u0007 k\u0013?\u0004#\u00030\u0016e\u00195\u000e\u0004+"), new Object[0])));
        jPanel.add(comboBox);
        JBLabel jBLabel = new JBLabel(AllIcons.General.ContextHelp);
        jBLabel.setToolTipText(BasicActionsBundle.message(EditorUtils.H("e~$t `rr*o#5(D\u0004{=)(u8onA\u0018s#h s5H\u00128=r4d9D\u001f4)w%ojh\"l;"), new Object[0]));
        jPanel.add(jBLabel);
        if (this.f23throw.size() > 1) {
            JLabel jLabel = new JLabel(BasicActionsBundle.message(JComponentKt.H("\u0001mJ\b!*E:\u001f;\n,L\f1+\u0010k\u0017=\r\fp.\n1\u001c*\u001f\u001c'*\rc\u0007)\u000b*\u0011e\u00195\u000e\u0004+"), new Object[0]));
            jLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            jPanel.add(jLabel);
            jPanel.add(comboBox2);
            JBLabel jBLabel2 = new JBLabel(AllIcons.General.ContextHelp);
            jBLabel2.setToolTipText(BasicActionsBundle.message(EditorUtils.H(")}'a5wey!)eYDg'n(>?~3RSz#t$u=S\u0015y#.7n1^\u00184)w%ojh\"l;"), new Object[0]));
            jPanel.add(jBLabel2);
        }
        return jPanel;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public JPanel createUnitTestTreeView(List<FileNode> list) {
        JBLabel jBLabel;
        JBLabel jBLabel2;
        JBPanel jBPanel = new JBPanel(new BorderLayout());
        jBPanel.setPreferredSize(new Dimension(700, 200));
        ResultTree resultTree = new ResultTree();
        resultTree.setModel(ResultTree.createModel(this.f34true, list));
        if (!CollectionUtils.isEmpty(list) && list.get(0).getChildCount() > 0) {
            jBLabel = new JBLabel(BasicActionsBundle.message(JComponentKt.H("-*\r+\u0002'H!\u0004?\u000e'G+\u0016 \u001bc\u001f=\r=A\n:.\u0001&\u0017v\u001d\u0014?:\u001cq\u0017-\u0013\u001a;,V=\u0006=\u0003\u001fr5\u0011,\t2\u0005"), new Object[0]) + this.f23throw.size() + BasicActionsBundle.message(EditorUtils.H("$r(;ovdp(s?xen.4r\u001f\u001ew:src.w%E\t3%v t/\t\u0012c b>urC\u0005n-wgl1z!}7"), new Object[0]));
            jBLabel2 = jBLabel;
        } else {
            jBLabel = new JBLabel(BasicActionsBundle.message(EditorUtils.H("#2hW\u0003uge=d(snS\u0013t245b/SRe(l>d(\u0019\tw1f010u3x*"), new Object[0]));
            jBLabel2 = jBLabel;
        }
        jBLabel.setPreferredSize(new Dimension(0, 25));
        jBLabel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        jBPanel.add(jBLabel2, JComponentKt.H("% \u001d(\t"));
        jBPanel.add(ScrollPaneFactory.createScrollPane(resultTree), EditorUtils.H("\\!r3q="));
        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) resultTree.getModel().getRoot();
        if (defaultMutableTreeNode != null) {
            If(resultTree, new TreePath(defaultMutableTreeNode), true);
        }
        return jBPanel;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private JComponent rD(TextFieldWithBrowseButton a, JBCheckBox a2) {
        String str;
        String str2 = "";
        String str3 = BatchUnitTestSettingsState.getInstance().testModuleDirectory;
        String canonicalPath = FileUtil.toCanonicalPath(str3);
        if (!StringUtils.isNotBlank(str3) || !StringUtils.isNotBlank(this.f29const.getBasePath()) || !StringUtils.contains(canonicalPath, this.f29const.getBasePath())) {
            ContentEntry[] contentEntries = ModuleRootManager.getInstance(this.f25void).getContentEntries();
            int length = contentEntries.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                SourceFolder[] sourceFolders = contentEntries[i2].getSourceFolders();
                int length2 = sourceFolders.length;
                int i3 = 0;
                int i4 = 0;
                while (i3 < length2) {
                    SourceFolder sourceFolder = sourceFolders[i4];
                    if (sourceFolder.isTestSource() && !Objects.isNull(sourceFolder.getFile())) {
                        String path = sourceFolder.getFile().getPath();
                        str2 = path;
                        if (StringUtils.isNotBlank(path)) {
                            break;
                        }
                    }
                    i4++;
                    i3 = i4;
                }
                i2++;
                i = i2;
            }
            str = str2;
        } else {
            str = str3;
            str2 = str;
        }
        if (StringUtils.isBlank(str)) {
            str2 = this.f42for;
        }
        a.setText(str2);
        FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(true, true, false, false, false, false);
        fileChooserDescriptor.setRoots(new VirtualFile[]{ProjectUtil.guessProjectDir(this.f29const)});
        a.addBrowseFolderListener(new TextBrowseFolderListener(fileChooserDescriptor, this.f29const));
        JPanel panel = FormBuilder.createFormBuilder().addComponent(UI.PanelFactory.panel(a).resizeX(true).withLabel(BasicActionsBundle.message(EditorUtils.H("r%|/n;>)z4>n\u001f\u001f| srd.h4\b\tx5noj3C\tz(.?n.R\u000fn.`010u3x*"), new Object[0])).withComment(JComponentKt.H("弫刓洂论皱弬乕嬦址斸膯勋剖庑l姤杊靥觊讚膴蠴來敷")).createPanel()).addComponent(a2).getPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 0));
        return panel;
    }

    private JComponent RA(JBCheckBox a) {
        JBPanel jBPanel = new JBPanel();
        GroupLayout groupLayout = new GroupLayout(jBPanel);
        jBPanel.setLayout(groupLayout);
        JBLabel jBLabel = new JBLabel(BasicActionsBundle.message(JComponentKt.H(",\u0006*\u0004\u00108l\u0006$\u0017;\u0016V+'\u0006+W,\u001b\u001b:k\u00065\b,\u0013'��e\u0019\u0017,\u0001."), new Object[0]));
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup().addComponent(jBLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(a));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jBLabel).addComponent(a));
        return jBPanel;
    }

    private JPanel iD(JBTextField a) {
        a.setText(this.f40long);
        JBPanel jBPanel = new JBPanel(new FlowLayout(0, 5, 2));
        jBPanel.add(new JBLabel(BasicActionsBundle.message(EditorUtils.H(" n?H\u0018scc)b=C\t4'{%zjr&y*"), new Object[0])));
        a.setPreferredSize(new Dimension(JBUI.scale(280), a.getMinimumSize().height));
        JBPanel jBPanel2 = new JBPanel();
        jBPanel2.add(a);
        jBPanel2.add(new JBLabel(JComponentKt.H("C/\u0002\u001e/")));
        jBPanel2.setBorder(BorderFactory.createEmptyBorder(0, 55, 0, 0));
        jBPanel.add(jBPanel2);
        if (this.f23throw.size() != 1) {
            jBPanel.setVisible(false);
        }
        return jBPanel;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void changeGenerateByTemplateComponent(String a) {
        Icon icon;
        String message;
        BatchUnitTestDialog batchUnitTestDialog;
        if (this.f49enum == null) {
            return;
        }
        if (StringUtils.equals(JComponentKt.H("\u000b \u0014;"), a)) {
            icon = IconManager.getInstance().getIcon(EditorUtils.H("\u007f9w)NSe9a/r/\u0018\u001fo\"q,l724b("), BatchUnitTestDialog.class);
            message = BasicActionsBundle.message(JComponentKt.H(";\u0011'\t\u0007/{\u0011\u0019**\u0007o\u00122\u00134H9\u000e+\ng\u001c\n;5��3\u0014r\t7\u00046\u0011:A+��4\u001b"), new Object[0]);
            batchUnitTestDialog = this;
        } else if (!StringUtils.equals(EditorUtils.H("^\u0012G\u0016"), a)) {
            icon = AllIcons.General.BalloonError;
            message = BasicActionsBundle.message(EditorUtils.H("d3~-r'sdP\u001eq!))~\"onR\u0018n242b.Q\u0019d>.(s=C\u0019ioa(k1n&`*"), new Object[0]);
            batchUnitTestDialog = this;
            batchUnitTestDialog.setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum.ENABLED);
        } else {
            icon = AllIcons.General.BalloonWarning;
            message = BasicActionsBundle.message(JComponentKt.H(";\u0011'\t\u0007/{\u0011\u0019**\u0007o\u00122\u00134H9\u000e+\ng\u001c\n;5��3\u0014r\t7\u00046\u0011:A \u0011+\u0007"), new Object[0]);
            batchUnitTestDialog = this;
            batchUnitTestDialog.setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum.ENABLED);
        }
        Icon icon2 = icon;
        String str = message;
        SwingUtilities.invokeLater(() -> {
            this.f49enum.setIcon(icon2);
            this.f49enum.setText(str);
            this.f49enum.revalidate();
            this.f49enum.repaint();
        });
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum a) {
        BatchUnitTestDialog batchUnitTestDialog;
        if (a == null) {
            return;
        }
        if (DuplicateFileNameSwitchEnum.ENABLED.getType().equals(a.getType())) {
            this.f47float.setSelected(true);
            this.f31do.setSelected(false);
            this.f41super = BasicActionsBundle.message(JComponentKt.H("\u001a&\u0001>\u0017\fc+\u000e6\u0007!A+\u0016$\u001fv\n,\u001c\u001af2\u0016\u0016;;\u000e5\u0002r\u00189H9\u000e5\u000e%\u000e\u000b<u\u0015 \u000f(P;\f<\u0016"), new Object[0]);
            batchUnitTestDialog = this;
        } else {
            this.f31do.setSelected(true);
            this.f47float.setSelected(false);
            this.f41super = BasicActionsBundle.message(EditorUtils.H("\u007f(s 4a?(s=d4>>u))(E\u000fa=);u%~2G\txhx8)(B\u0011f!a/brV\u00054)w%ojh\"l;"), new Object[0]);
            batchUnitTestDialog = this;
        }
        batchUnitTestDialog.f37char.getComponents()[2].setText(this.f41super);
        this.f37char.validate();
        this.f37char.repaint();
    }

    private JPanel za() {
        JBLabel jBLabel = new JBLabel(BasicActionsBundle.message(JComponentKt.H("G\u0001&+\u0002?P-\b0\u0001\u0011q7\n,\u0017v\n\u001d-=A,\u001c*\b\r<6M>\u001f!\u00126\u0016e\u0019?\u0004!\u000e"), new Object[0]));
        jBLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        this.f20native.add(jBLabel);
        this.f49enum = new JBLabel();
        this.f20native.add(this.f49enum);
        JBLabel jBLabel2 = new JBLabel(AllIcons.General.ContextHelp);
        jBLabel2.setToolTipText(BasicActionsBundle.message(EditorUtils.H("*h2v\"|n?gE\tzgr2y?54C\u000eihi$u*B\u000eecs/f(B\u001f4)w%ojh\"l;"), new Object[0]));
        this.f20native.add(jBLabel2);
        this.f41super = BasicActionsBundle.message(JComponentKt.H("aK \u000e$\fv\u001c.\u001d'\n,Q��!9E,\u001b<\u001dj\u0005\u001c1'\u0016$\u0017=P\u001a'g\u001b:\u0014(\u0012\t: M,\u0002n\u000e&\t;C\"\u00155\u001f"), new Object[0]);
        this.f37char = new PluginComponentPanelBuilder(this.f20native).withLabel(BasicActionsBundle.message(EditorUtils.H(")}'a5wey!)eYDg'n(>?~3RSz#t$u=S\u00198/yus9Z\u001cv f,10u3x*"), new Object[0])).withComment(this.f41super).createPanel();
        setGenerateByTemplateSwitch(BatchUnitTestSettingsState.getInstance().enabledGenerateByTemplate);
        changeGenerateByTemplateComponent(this.f46try);
        this.f37char.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        return this.f37char;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public GeneratorConfig getSelectedValue(GeneratorConfig a) {
        UnitTestMockEnum findByName;
        GeneratorConfig generatorConfig;
        a.setDuplicateRule(getDuplicateFileNameSwitchEnum());
        a.setTestPrivate(this.f48byte.isSelected());
        a.setTestModuleDirectory(this.f35this.getText());
        String str = (String) this.f24null.getSelectedItem();
        String str2 = (String) this.f22while.getSelectedItem();
        UnitTestBaseEnum findByName2 = UnitTestBaseEnum.findByName(str);
        if (!StringUtils.equals(UnitTestMockEnum.OFF.getDependency(), str2)) {
            findByName = UnitTestMockEnum.findByName(str2);
            generatorConfig = a;
        } else {
            findByName = UnitTestMockEnum.findByDependency();
            generatorConfig = a;
        }
        generatorConfig.setTestFramework(findByName2);
        a.setMockFramework(findByName);
        a.setEnabledGenerateByTemplate(Boolean.valueOf(this.f47float.isSelected()));
        a.setTestGenerationProcess(TestGenerationProcess.loadByName((String) this.f28catch.getSelectedItem()));
        a.setTestUnitLimit((Integer) this.f43if.getSelectedItem());
        a.setExcludeMethodList(this.f26goto.getBody());
        BatchUnitTestSettingsState batchUnitTestSettingsState = BatchUnitTestSettingsState.getInstance();
        batchUnitTestSettingsState.testFramework = str;
        batchUnitTestSettingsState.mockFramework = str2;
        batchUnitTestSettingsState.testGenerationProcess = TestGenerationProcess.loadByName((String) this.f28catch.getSelectedItem());
        batchUnitTestSettingsState.batchTestUnitLimt = BatchTestUnitLimt.loadLimt((Integer) this.f43if.getSelectedItem());
        batchUnitTestSettingsState.enabledGenerateByTemplate = hB();
        batchUnitTestSettingsState.testPrivate = this.f48byte.isSelected();
        batchUnitTestSettingsState.savePath = this.f44case.isSelected();
        batchUnitTestSettingsState.duplicateRule = getDuplicateFileNameSwitchEnum();
        if (batchUnitTestSettingsState.savePath) {
            batchUnitTestSettingsState.testModuleDirectory = this.f35this.getText();
        }
        if (a.getFileAbsolutePathList().size() == 1) {
            a.setTestFileName(this.f27short.getText());
        }
        return a;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void doOKAction() {
        if (!StringUtils.isBlank(this.f35this.getText())) {
            super.doOKAction();
        } else {
            setErrorText(BasicActionsBundle.message(JComponentKt.H("=&\u00019\u0010?P\u001d ,\u0017c\u001f%\u00157K(\u001f-\u000f7��%,\u000b$C.=\u001b7\fk\u0017&\u001d6V"), new Object[0]), this.f35this.getTextField());
        }
    }

    private JPanel iF() {
        return setDuplicateFileNameSwitchComponent();
    }

    private JComponent Dc(ComboBox<Object> comboBox, ComboBox<Object> comboBox2) {
        JBPanel jBPanel = new JBPanel(new FlowLayout(0, 5, 2));
        jBPanel.add(new JLabel(BasicActionsBundle.message(JComponentKt.H(";\u0011!\u000f-\u0005W=#\u0010&\u000bv\u000b\u00167=A+\u001c+\nF(7\u0002 \u000e7\t1\u000ee\u0019\u0001:#\f"), new Object[0])));
        jBPanel.add(comboBox);
        JLabel jLabel = new JLabel(BasicActionsBundle.message(EditorUtils.H("(t.;oVDp(s?xen.O\t32\u007f2srJ\u0013u&.=u=Z\tm.`\"10u3x*"), new Object[0]));
        jLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jBPanel.add(jLabel);
        jBPanel.add(comboBox2);
        return jBPanel;
    }

    public JPanel setDuplicateFileNameSwitchComponent() {
        this.f21assert = BasicActionsBundle.message(EditorUtils.H("r%|/n;>)z4>n\u001f\u001f| srd.h4\b\u0019h6v(d=S\u00198.o>\u007f5D\u00184)w%ojh\"l;"), new Object[0]);
        this.f32break = new PluginComponentPanelBuilder(this.f33class).withLabel(BasicActionsBundle.message(JComponentKt.H("\f\u0005\"#\n'H+\u000e\u0016'%E4\t \u001bQ->\u000e1M<\u000b?\u0005-\u0001\u000e=&K'\u000e0\u001f-\u0004/\u0001g\u001b+\u00104\u001b"), new Object[0]) + "        ").withComment(this.f21assert).createPanel();
        setDuplicateFileNameSwitch(BatchUnitTestSettingsState.getInstance().duplicateRule);
        this.f32break.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        return this.f32break;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void If(Tree a, TreePath a2, boolean z) {
        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) a2.getLastPathComponent();
        int i = 0;
        int i2 = 0;
        while (i < defaultMutableTreeNode.getChildCount()) {
            TreePath pathByAddingChild = a2.pathByAddingChild(defaultMutableTreeNode.getChildAt(i2));
            i2++;
            If(a, pathByAddingChild, z);
            i = i2;
        }
        if (!z) {
            a.collapsePath(a2);
        } else {
            a.expandPath(a2);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void setDuplicateFileNameSwitch(DuplicateRule a) {
        BatchUnitTestDialog batchUnitTestDialog;
        if (DuplicateRule.SKIP != a) {
            if (DuplicateRule.OVERWRITE != a) {
                this.f30false.setSelected(true);
                this.f39new.setSelected(false);
                this.f36else.setSelected(false);
                this.f21assert = BasicActionsBundle.message(EditorUtils.H("r%|/n;>)z4>n\u001f\u001f| srd.h4\b\u0019h6v(d=S\u00198.o>\u007f5D\u00184)w%ojh\"l;"), new Object[0]);
                batchUnitTestDialog = this;
            } else {
                this.f36else.setSelected(true);
                this.f39new.setSelected(false);
                this.f30false.setSelected(false);
                this.f21assert = BasicActionsBundle.message(JComponentKt.H("\u001d\u0007 ?\u0016;T\"\u00071��'G8\u00051\ng\u001b\u000f?1M$\u00139\u0003\u000b',\u001f$I:\u0004\u0016)u\u0015 \u000f(P;\f<\u0016"), new Object[0]);
                batchUnitTestDialog = this;
            }
        } else {
            this.f39new.setSelected(true);
            this.f36else.setSelected(false);
            this.f30false.setSelected(false);
            this.f21assert = BasicActionsBundle.message(EditorUtils.H(">i\u007f,{.)>q?x(ss_\u0003fgs9c?5$S\rq/y s9\t\u0013`(r,u5C\t4)w%ojh\"l;"), new Object[0]);
            batchUnitTestDialog = this;
        }
        batchUnitTestDialog.f32break.getComponents()[2].setText(this.f21assert);
        this.f32break.validate();
        this.f32break.repaint();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public DuplicateRule getDuplicateFileNameSwitchEnum() {
        if (this.f36else.isSelected()) {
            return DuplicateRule.OVERWRITE;
        }
        if (this.f39new.isSelected()) {
            return DuplicateRule.SKIP;
        }
        return DuplicateRule.COEXIST;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private GenaratebyTemplateSwitchEnum hB() {
        if (!this.f47float.isSelected()) {
            return GenaratebyTemplateSwitchEnum.DISABLED;
        }
        return GenaratebyTemplateSwitchEnum.ENABLED;
    }
}
