package com.aicode.action.batch;

import com.aicode.PluginStartupActivity;
import com.aicode.action.batch.node.FileNode;
import com.aicode.action.click.PluginAnAction;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.agent.service.UserService;
import com.aicode.icons.Icons;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.template.TemplateGenerator;
import com.aicode.ui.ActionButton;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.PsiUtils;
import com.aicode.util.StringUtils;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: zi */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/BatchUTGeneratorAction.class */
public class BatchUTGeneratorAction extends PluginAnAction {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f10enum = Logger.getInstance(BatchUTGeneratorAction.class);

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m37enum(int a) {
        throw new IllegalArgumentException(String.format(InlineChatStatusServiceKt.H("\u0006\u0010!\u0016kF$\u001bi\n3\u000bk.\u000e\nrm\u001f#%L,\u00189\u000f-��\t=\u000eyHo>Of\f+H\u007f\fgI/Y*\u0017)\u000bI\"$\u001a|\u001b$D)\u0017*\u000f"), CancelRequestTip.H("\f"), InlineChatStatusServiceKt.H("eL\u0007`(\u0005?\u0016/\u000bo\u0004\u001e,\u00156\u0001e/\t2��%G\u0018\u001e=\u000f4,\u0013%?\u0011\f>*\u001a3\u000b��\u00073\u000b)\r"), CancelRequestTip.H("\u001f\u001a\u0015\u0010\u001d\f")));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private Module cE(Project a, VirtualFile[] a2, List<FileNode> list) {
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        int length = a2.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            Module findModuleForFile = ModuleUtilCore.findModuleForFile(a2[i2], a);
            if (findModuleForFile != null) {
                if (PsiUtils.instanceOf(ModuleType.get(findModuleForFile), InlineChatStatusServiceKt.H("*\u00031W\"��4��\u00114\u00153A%=\r(\u0002=\u0001t\u0012&\b)\u0015\"L\u0010\u001e\u001f-\u0006\u00018\f-\u0001\u0013\u001b6\u0006"))) {
                    hashSet.add(findModuleForFile);
                } else {
                    hashSet2.add(findModuleForFile);
                }
            }
            i2++;
            i = i2;
        }
        if (hashSet.size() == 1) {
            return (Module) hashSet.iterator().next();
        }
        if (hashSet.size() > 1) {
            Hf(a, BasicActionsBundle.message(CancelRequestTip.H("\u0013\b\t\n\u00056\u007f217 \tO\u001f\u0004\b\u0015X\u0002��\u0016\u0004^\u0006\u000e\u0005\u0005$\"\u0002I\u001d\u001f\t\u0018\u0007\u000eD\u000f\u0015\u0015.3"), new Object[0]));
            return null;
        }
        if (hashSet2.isEmpty()) {
            Hf(a, CancelRequestTip.H("杇菚厽剛頓的橆地侠怮"));
            return null;
        }
        Hf(a, BasicActionsBundle.message(InlineChatStatusServiceKt.H("#\n\u0013>\u0015>A(,\u001c%\u000bc\u001d4\u0016=B(\u001c4\u0016t\u0012\f?8\u000f;\u001co\u00015\u0010)\u0011"), new Object[0]));
        return null;
    }

    private void EF(Project a, VirtualFile[] a2, Module a3, List<FileNode> list) {
        String testPath = BatchUnitTestTemplateService.getTestPath(a, a2[0]);
        ProgressManager.getInstance().run(new ta(a, CancelRequestTip.H("匾洡旭亷覢柒乯"), false, a, a2, list, new ArrayList(), new GeneratorConfig(), testPath, a3, new StringBuilder()));
    }

    private void Hf(Project a, String a2) {
        NotificationGroupManager.getInstance().getNotificationGroup(InlineChatStatusServiceKt.H("\u001e��/$\n9W/\u000b3\u000b%\u0006")).createNotification(BasicActionsBundle.message(CancelRequestTip.H("71,\u0014\u0011D\u000b\b\u0002\u0019\u0012��K5\u0014\u0002\u001f\u0019\u0004\u0004&\u0013\u000e\u001f\u001e*\u001f\u0004\u001e\u001aD8)\u001a\u0016"), new Object[0]), MessageType.WARNING).setTitle(BasicActionsBundle.message(InlineChatStatusServiceKt.H("\u0002\u000f7\t)A+$\u000b)\u0007(F\u001f\u001b \u00183\u000b\u0006\u0001.\u0016\u0006\"\f\u001c3\f1J3\u0007>\u0017"), new Object[0])).setContent(a2).notify(a);
    }

    public BatchUTGeneratorAction(@Nullable String text, @Nullable String a) {
        super(text, a, Icons.ToolWindowIcon);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: zi */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/BatchUTGeneratorAction$ta.class */
    public class ta extends Task.Backgroundable {

        /* renamed from: super, reason: not valid java name */
        public final /* synthetic */ String f11super;

        /* renamed from: for, reason: not valid java name */
        public final /* synthetic */ List f12for;

        /* renamed from: case, reason: not valid java name */
        public final /* synthetic */ List f14case;

        /* renamed from: final, reason: not valid java name */
        public final /* synthetic */ StringBuilder f15final;

        /* renamed from: try, reason: not valid java name */
        public final /* synthetic */ Module f16try;

        /* renamed from: float, reason: not valid java name */
        public final /* synthetic */ Project f17float;

        /* renamed from: byte, reason: not valid java name */
        public final /* synthetic */ VirtualFile[] f18byte;

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ GeneratorConfig f19enum;

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m39enum(int a) {
            String H = ActionButton.H("\"\u000b\u0007\u000f\u0018\n\f\fI\u0015@Gc\u0019.\u0015\u0001!\u0017\u0014\u0005S$/'.+9\u0010\u001b\u001dUC[��N@\u0015\u0013OK\u0007]L3z\u000f\r\u0006\u001bH\u001c\u0001��O\u0017@\u001f\u001b\u001a\f\u0016");
            Object[] objArr = new Object[3];
            switch (a) {
                case 0:
                default:
                    objArr[0] = AICodeStringUtil.H("EHI\u0005\u0004]CFP");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = ActionButton.H("\u001f");
                    break;
            }
            objArr[1] = AICodeStringUtil.H("JMQ\u0018JICDys3vlpDIH\u0002OGNRA\r~VSORd]ENNYE@^H^gN\u0018\u000eSY\r\u0013");
            switch (a) {
                case 0:
                default:
                    objArr[2] = ActionButton.H("\u001d\u0015\u0014");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[2] = AICodeStringUtil.H("EIxN_\u0003\u0010]UEG");
                    break;
            }
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        public void onThrowable(@NotNull Throwable a) {
            if (a == null) {
                m39enum(1);
            }
            BatchUTGeneratorAction.f10enum.warn("[批量单测文件分析失败] fail : " + a.getMessage());
            BatchUTGeneratorAction.f10enum.warn("[批量单测文件分析失败] fail : " + a.getCause());
            BatchUTGeneratorAction.f10enum.warn("[批量单测文件分析失败] fail : " + a.getStackTrace());
        }

        public void run(@NotNull ProgressIndicator a) {
            if (a == null) {
                m39enum(0);
            }
            Application application = ApplicationManager.getApplication();
            Project project = this.f17float;
            VirtualFile[] virtualFileArr = this.f18byte;
            List list = this.f12for;
            List list2 = this.f14case;
            GeneratorConfig generatorConfig = this.f19enum;
            String str = this.f11super;
            Module module = this.f16try;
            StringBuilder sb = this.f15final;
            application.runReadAction(() -> {
                BatchUnitTestTemplateService.handleClassFiles(project, List.of((Object[]) virtualFileArr), list, list2, generatorConfig, str, module, sb);
            });
        }

        public void onFinished() {
            BatchUnitTestDialog createUnitTestDialog = BatchUnitTestTemplateService.createUnitTestDialog(this.f17float, this.f16try, this.f11super, this.f12for, this.f14case, this.f15final);
            if (createUnitTestDialog.getExitCode() == 0) {
                GeneratorConfig selectedValue = createUnitTestDialog.getSelectedValue(this.f19enum);
                ExecutorService executorService = PluginStartupActivity.handleExecutorService;
                Project project = this.f17float;
                executorService.execute(() -> {
                    TemplateGenerator.batchTestClass(project, selectedValue);
                });
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ta(Project a, String a2, boolean z, Project project, VirtualFile[] virtualFileArr, List list, List list2, GeneratorConfig generatorConfig, String str, Module module, StringBuilder sb) {
            super(a, a2, z);
            this.f17float = project;
            this.f18byte = virtualFileArr;
            this.f12for = list;
            this.f14case = list2;
            this.f19enum = generatorConfig;
            this.f11super = str;
            this.f16try = module;
            this.f15final = sb;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void actionPerformed(AnActionEvent a) {
        ArrayList arrayList = new ArrayList();
        Project project = a.getProject();
        if (project != null) {
            if (StringUtils.contains(ApplicationInfo.getInstance().getVersionName(), CancelRequestTip.H("(%%!"))) {
                if (!StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
                    VirtualFile[] virtualFileArr = (VirtualFile[]) a.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
                    if (virtualFileArr == null || virtualFileArr.length == 0) {
                        Hf(project, BasicActionsBundle.message(CancelRequestTip.H("&\b\t\u0016\u0019\nC\t\n\u001e\t\rK\u0012\t\u0019\u0004C\u0019\u000e\u0018\u001eD'1\r\u0004\u0015\u0002L\u0007\u0006\u001b\u0002\u000fG\u001d\b\u0015\f\u0005"), new Object[0]));
                        return;
                    }
                    Module cE = cE(project, virtualFileArr, arrayList);
                    if (cE == null) {
                        return;
                    }
                    EF(project, virtualFileArr, cE, arrayList);
                    return;
                }
                UserService.showMessage(project);
                return;
            }
            Hf(project, BasicActionsBundle.message(InlineChatStatusServiceKt.H("?\u0016%\b)\u0002S:\u001d-\f\"c\u001d(\n9F.\u001a:\u0018r\u0014\"\u0011)\u001e\u000e)e\u00078\u001co\u00015\u0010)\u0011"), new Object[0]));
            return;
        }
        Hf(project, InlineChatStatusServiceKt.H("杶菎厗剔頾皌侧怌"));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m37enum(0);
        }
        if (!AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.BATCH_UNITTEST.getPermission())) {
            a.getPresentation().setEnabledAndVisible(false);
            return;
        }
        Presentation presentation = a.getPresentation();
        presentation.setIcon(Icons.getCurrentIcon());
        presentation.setEnabled(true);
    }
}
