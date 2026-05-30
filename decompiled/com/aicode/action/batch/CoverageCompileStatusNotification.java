package com.aicode.action.batch;

import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.diff.FileService;
import com.aicode.diff.GenericUtils;
import com.aicode.enums.TestGenerationProcess;
import com.aicode.template.TemplateGenerator;
import com.aicode.template.generator.ProcessErrorFileAnalyzer;
import com.intellij.compiler.CompilerMessageImpl;
import com.intellij.compiler.CompilerWorkspaceConfiguration;
import com.intellij.coverage.CoverageExecutor;
import com.intellij.coverage.CoverageRunner;
import com.intellij.coverage.DefaultJavaCoverageRunner;
import com.intellij.coverage.IDEACoverageRunner;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.ShortenCommandLine;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.execution.configurations.coverage.CoverageEnabledConfiguration;
import com.intellij.execution.configurations.coverage.JavaCoverageEnabledConfiguration;
import com.intellij.execution.junit.JUnitConfiguration;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ExecutionUtil;
import com.intellij.execution.testframework.TestSearchScope;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.compiler.CompilationStatusListener;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileStatusNotification;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.compiler.CompilerMessage;
import com.intellij.openapi.compiler.CompilerMessageCategory;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.classFilter.ClassFilter;
import com.intellij.util.ui.UIUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: qe */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/CoverageCompileStatusNotification.class */
public class CoverageCompileStatusNotification implements CompileStatusNotification, CompilationStatusListener {

    /* renamed from: char, reason: not valid java name */
    private final GeneratorConfig f56char;

    /* renamed from: int, reason: not valid java name */
    private final Project f57int;

    /* renamed from: new, reason: not valid java name */
    private final Boolean f58new;

    /* renamed from: long, reason: not valid java name */
    private final ToolWindow f59long;

    /* renamed from: for, reason: not valid java name */
    private final Boolean f61for;

    /* renamed from: if, reason: not valid java name */
    private final CompilerManager f62if;

    /* renamed from: final, reason: not valid java name */
    private final Integer f64final;

    /* renamed from: float, reason: not valid java name */
    private final Boolean f66float;

    /* renamed from: byte, reason: not valid java name */
    private final List<VirtualFile> f67byte;

    /* renamed from: try, reason: not valid java name */
    private static final String f65try = GenericUtils.H("\u00157#dd");

    /* renamed from: else, reason: not valid java name */
    private static final String f55else = GenericUtils.H("\u00155,$#-ms");

    /* renamed from: super, reason: not valid java name */
    private static final String f60super = GenericUtils.H("\u0014-<mr");

    /* renamed from: case, reason: not valid java name */
    private static final String f63case = GenericUtils.H("\u001b?)20+oe");

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f68enum = Logger.getInstance(CoverageCompileStatusNotification.class);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m45enum(int a) {
        String H = GenericUtils.H("\u0010+4.\u0018\u00187%{5)<\u007f\u0017\u0014='\u0015\u0010\u0001.j'>0+..1(\nP~t1m\u007f86xz${x$\u007f/?,#y?7$s9=p->7?");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = GenericUtils.H("+*?).8'");
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = GenericUtils.H("<84!1<6\u00187>7.#'");
                break;
        }
        objArr[1] = GenericUtils.H("\u001e6<t2/-03?}28\u0011\u0004-$x=#> #j\u000e\u0017\u0006<##-:\u0014?5/>98\u0004+#>*$\u0017>,952;17\"4=");
        switch (a) {
            case 0:
            default:
                objArr[2] = GenericUtils.H("7 65\u000f9-/4$");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = GenericUtils.H("52690#>7");
                break;
            case 2:
                objArr[2] = GenericUtils.H(">822#36-87>\u00152690#>7");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qe */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/CoverageCompileStatusNotification$aa.class */
    public class aa extends ProcessAdapter {

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ Project f69enum;

        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m46enum(int a) {
            throw new IllegalArgumentException(String.format(OpenTelemetryUtil.H("A6a7i%L\u0012.,c:1\u0015F#y\u0007q,^Ve0r%x4`5`v\bN}m5>nl\"0&iqfm1f%(\"a>/) !n1l("), FileService.H("b&'>6"), OpenTelemetryUtil.H("!k-\r\u0007g)c,tzi/y k.\u001d\u0014t%c,:\u0012{&w$N\fk\tz<x%k&[8c2u7[>|%h#l*1ho*$u"), FileService.H("0\"-&2+9\n)- |i165&")));
        }

        public void processTerminated(@NotNull ProcessEvent a) {
            if (a == null) {
                m46enum(0);
            }
            CoverageCompileStatusNotification.openWindow(this.f69enum, OpenTelemetryUtil.H("Cu-l "), false);
            CoverageCompileStatusNotification.openWindow(this.f69enum, FileService.H("\u001c\"cb\"#7'"), true);
        }

        public aa(Project project) {
            this.f69enum = project;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static void wC(Project a, String a2, boolean z, Module a3) {
        Project project;
        CoverageEnabledConfiguration coverageEnabledConfiguration;
        Xf(a, GenericUtils.H("\u00124 \u0019\u0019"), false);
        ConfigurationFactory configurationFactory = ConfigurationTypeUtil.findConfigurationType(GenericUtils.H("\u001a\u0014'\u001c\t")).getConfigurationFactories()[0];
        JUnitConfiguration createTemplateConfiguration = configurationFactory.createTemplateConfiguration(a);
        createTemplateConfiguration.setShortenCommandLine(ShortenCommandLine.MANIFEST);
        if (z) {
            project = a;
            LD(createTemplateConfiguration, a2, a3);
        } else {
            AD(createTemplateConfiguration, a2, a3);
            project = a;
        }
        RunnerAndConfigurationSettings createConfiguration = RunManager.getInstance(project).createConfiguration(createTemplateConfiguration, configurationFactory);
        CoverageRunner coverageRunner = CoverageRunner.getInstance(IDEACoverageRunner.class);
        CoverageEnabledConfiguration orCreate = CoverageEnabledConfiguration.getOrCreate(createTemplateConfiguration);
        try {
            orCreate.getClass().getMethod(GenericUtils.H("9:#\u0004*>4>\r2-\u0016/,#\u001a>.53(\u0012\u0018"), Boolean.TYPE).invoke(orCreate, true);
            coverageEnabledConfiguration = orCreate;
        } catch (Exception e) {
            coverageEnabledConfiguration = orCreate;
        }
        coverageEnabledConfiguration.setCoverageRunner(coverageRunner);
        orCreate.setCoverageEnabled(true);
        JavaCoverageEnabledConfiguration javaCoverageEnabledConfiguration = (JavaCoverageEnabledConfiguration) orCreate;
        if (StringUtils.isNotBlank(a2)) {
            javaCoverageEnabledConfiguration.setCoveragePatterns(new ClassFilter[]{new ClassFilter((z ? a2.substring(0, a2.lastIndexOf(GenericUtils.H("S"))) : a2) + ".*")});
        }
        CoverageExecutor coverageExecutor = new CoverageExecutor();
        DefaultJavaCoverageRunner defaultJavaCoverageRunner = new DefaultJavaCoverageRunner();
        ExecutionEnvironment build = ExecutionUtil.createEnvironment(coverageExecutor, createConfiguration).build();
        if (defaultJavaCoverageRunner == null) {
            return;
        }
        try {
            defaultJavaCoverageRunner.execute(build, a4 -> {
                ProcessHandler processHandler = a4.getProcessHandler();
                if (processHandler != null) {
                    processHandler.addProcessListener(new aa(a));
                }
            });
        } catch (ExecutionException e2) {
            f68enum.info(GenericUtils.H("\u0010?1w'1+?u\u001e8)'8>0<q=\"3&\u0007]"), e2);
        } catch (Throwable th) {
            f68enum.info(GenericUtils.H("\u0010?1w'1+?u\u001e8)'8>0<q=\"3&\u0007]"), th);
        }
    }

    public static final void openWindow(@NotNull Project project, String name, boolean z) {
        if (project == null) {
            m45enum(0);
        }
        Intrinsics.checkNotNullParameter(project, GenericUtils.H("  534\"="));
        setActivateViewOnRun(project, z);
        UIUtil.invokeLaterIfNeeded(() -> {
            Xf(project, name, z);
        });
    }

    private static void LD(JUnitConfiguration a, String a2, Module a3) {
        JUnitConfiguration.Data persistentData = a.getPersistentData();
        persistentData.setScope(TestSearchScope.SINGLE_MODULE);
        persistentData.TEST_OBJECT = GenericUtils.H("3\u0016\u0013\u0001\t");
        persistentData.MAIN_CLASS_NAME = a2;
        a.setModule(a3);
        a.setGeneratedName();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void setActivateViewOnRun(Project a, boolean z) {
        try {
            Class<?> cls = Class.forName(GenericUtils.H("46<v9+967.#\u001dQ624/-6'-y\u001c-<6)\u0001\u000f:\u0018#/(&\u0017\u0002\b\"\u000b\u001a*/=\""));
            cls.getMethod(GenericUtils.H(" >\u0014)<#:- =\u001c'15\u0013#-\u0019->"), Boolean.TYPE).invoke(cls.getMethod(GenericUtils.H(".\u001c\u0005\u0011>\u0017\u0018\"%;5"), Project.class).invoke(null, a), Boolean.valueOf(z));
        } catch (Exception e) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public CoverageCompileStatusNotification(ToolWindow a, CompilerManager a2, Boolean a3, Project a4, GeneratorConfig a5, Integer a6) {
        this.f59long = a;
        this.f62if = a2;
        this.f66float = a3;
        this.f58new = Boolean.valueOf(Objects.equals(TestGenerationProcess.GENERATION_BUILD_EXECUTE, a5.getTestGenerationProcess()));
        this.f57int = a4;
        this.f56char = a5;
        this.f64final = a6;
        this.f61for = Boolean.valueOf(a6.intValue() <= 0);
        CompilerWorkspaceConfiguration compilerWorkspaceConfiguration = CompilerWorkspaceConfiguration.getInstance(a4);
        if (!this.f61for.booleanValue()) {
            compilerWorkspaceConfiguration.AUTO_SHOW_ERRORS_IN_EDITOR = false;
            Be();
        }
        this.f67byte = new ArrayList();
        Iterator<String> it = a5.getGeneratorFilePathList().iterator();
        while (it.hasNext()) {
            VirtualFile findFileByPath = LocalFileSystem.getInstance().findFileByPath(it.next());
            if (findFileByPath != null) {
                this.f67byte.add(findFileByPath);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void Xf(Project a, String a2, boolean z) {
        ToolWindow toolWindow;
        Intrinsics.checkNotNullParameter(a, GenericUtils.H("!*?\u0004\u00033,"));
        if (!a.isDisposed() && (toolWindow = ToolWindowManager.Companion.getInstance(a).getToolWindow(a2)) != null) {
            if (z) {
                toolWindow.setAnchor(ToolWindowAnchor.BOTTOM, (Runnable) null);
                toolWindow.setSplitMode(true, (Runnable) null);
                toolWindow.show();
                return;
            }
            toolWindow.hide();
        }
    }

    private static void AD(JUnitConfiguration a, String a2, Module a3) {
        JUnitConfiguration.Data persistentData = a.getPersistentData();
        persistentData.setScope(TestSearchScope.SINGLE_MODULE);
        persistentData.TEST_OBJECT = GenericUtils.H("!93\u001c\u001e\u0015\u001f");
        persistentData.PACKAGE_NAME = a2;
        a.setModule(a3);
        a.setGeneratedName();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private void Be() {
        if (this.f59long != null) {
            this.f59long.setAvailable(this.f61for.booleanValue());
            if (!this.f61for.booleanValue()) {
                this.f59long.setAutoHide(true);
                this.f59long.hide();
            } else {
                this.f59long.show();
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void compilationFinished(boolean z, int i, int i2, @NotNull CompileContext a) {
        if (a == null) {
            m45enum(2);
        }
        if (z) {
            return;
        }
        Be();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void finished(boolean z, int errors, int warnings, @NotNull CompileContext a) {
        if (a == null) {
            m45enum(1);
        }
        if (!z) {
            if (errors == 0) {
                f68enum.info("Build completed successfully with " + warnings + " warnings");
                if (!this.f66float.booleanValue()) {
                    Application application = ApplicationManager.getApplication();
                    application.invokeLater(() -> {
                        application.runWriteAction(() -> {
                            WriteCommandAction.runWriteCommandAction(this.f57int, () -> {
                                TemplateGenerator.batchTestClass(this.f57int, this.f56char);
                            });
                        });
                    });
                    return;
                } else {
                    UIUtil.invokeLaterIfNeeded(() -> {
                        if (this.f58new.booleanValue()) {
                            Xf(this.f57int, GenericUtils.H("\u0013,=7("), false);
                            Xf(this.f57int, GenericUtils.H("\u001a>.51*5?"), false);
                        }
                    });
                    if (!this.f58new.booleanValue()) {
                        return;
                    }
                    wC(this.f57int, this.f56char.getExecPath(), this.f56char.isSingleFile(), this.f56char.getModule());
                    return;
                }
            }
            if (errors > 0) {
                CompilerMessage[] messages = a.getMessages(CompilerMessageCategory.ERROR);
                ApplicationManager.getApplication().invokeLater(() -> {
                    if (this.f61for.booleanValue()) {
                        Be();
                        return;
                    }
                    ProcessErrorFileAnalyzer processErrorFileAnalyzer = new ProcessErrorFileAnalyzer(this.f57int);
                    HashMap hashMap = new HashMap();
                    int length = messages.length;
                    int i = 0;
                    int i2 = 0;
                    while (i < length) {
                        CompilerMessage compilerMessage = messages[i2];
                        if ((compilerMessage instanceof CompilerMessageImpl) && Objects.nonNull(compilerMessage.getVirtualFile())) {
                            if (!hashMap.containsKey(compilerMessage.getVirtualFile())) {
                                hashMap.put(compilerMessage.getVirtualFile(), new HashSet());
                            }
                            ((Set) hashMap.get(compilerMessage.getVirtualFile())).add(Integer.valueOf(((CompilerMessageImpl) compilerMessage).getLine()));
                        }
                        i2++;
                        i = i2;
                    }
                    for (Map.Entry entry : hashMap.entrySet()) {
                        if (this.f67byte.contains(entry.getKey())) {
                            processErrorFileAnalyzer.processFile((VirtualFile) entry.getKey(), (Set) entry.getValue());
                        }
                    }
                    this.f62if.make(a.getCompileScope(), new CoverageCompileStatusNotification(this.f59long, this.f62if, this.f66float, this.f57int, this.f56char, Integer.valueOf(this.f64final.intValue() - 1)));
                });
                return;
            } else {
                f68enum.info("Build completed with " + errors + " errors and " + warnings + " warnings");
                return;
            }
        }
        f68enum.info(GenericUtils.H("\u0012%133x'>$e,<9!/7>"));
    }
}
