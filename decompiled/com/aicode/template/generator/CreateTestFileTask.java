package com.aicode.template.generator;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.IdUtil;
import com.aicode.action.batch.BatchUnitTestTemplateService;
import com.aicode.action.batch.GeneratorConfig;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.service.CommonService;
import com.aicode.enums.DuplicateRule;
import com.aicode.enums.TestGenerationProcess;
import com.aicode.enums.UnitTestMockEnum;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.settings.BatchUnitTestSettingsState;
import com.aicode.template.CodeRefactorUtil;
import com.aicode.template.FileTemplateConfig;
import com.aicode.template.TestTemplateContextBuilder;
import com.aicode.template.VelocityInitializer;
import com.aicode.template.builder.MethodReferencesBuilder;
import com.aicode.template.builder.MockBuilderFactory;
import com.aicode.template.context.domain.Type;
import com.aicode.template.fileloader.FileTemplateContext;
import com.aicode.template.fileloader.TemplateDescriptor;
import com.aicode.template.fileloader.TemplateRegistry;
import com.aicode.template.fileloader.UnitTemplateManager;
import com.aicode.template.generator.ClassNameSelection;
import com.aicode.template.request.FileRequestDto;
import com.aicode.template.request.TemplateRequestService;
import com.aicode.test.UnitTestService;
import com.aicode.test.dto.MethodUnitTestData;
import com.aicode.util.ClassNameUtils;
import com.aicode.util.JavaPsiUtils;
import com.aicode.util.StringUtils;
import com.aicode.util.UnitTestCollectUtil;
import com.intellij.codeInsight.FileModificationService;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.SourceFolder;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.GlobalSearchScopesCore;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testIntegration.createTest.CreateTestAction;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.apache.velocity.app.Velocity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;
import org.jetbrains.jps.model.java.JavaResourceRootProperties;
import org.jetbrains.jps.model.java.JavaSourceRootProperties;
import org.jetbrains.jps.model.java.JavaSourceRootType;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/CreateTestFileTask.class */
public class CreateTestFileTask extends Task.Backgroundable {
    private static final Logger LOG = Logger.getInstance(CreateTestFileTask.class.getName());
    private final List<VirtualFile> javaFiles;
    private final Project project;
    private final GeneratorConfig generatorConfig;
    private final TestTemplateContextBuilder testTemplateContextBuilder;
    private final CodeRefactorUtil codeRefactorUtil;
    private final GeneratedClassNameResolver generatedClassNameResolver;
    private final TargetDirectoryLocator targetDirectoryLocator;
    private final Map<String, CacheFileTemplate> cacheFileTemplateMap;
    private final AtomicInteger generatingNumber;
    private final AtomicInteger failedNumber;
    private final AtomicInteger allMethodSize;
    private final AtomicBoolean isCanceled;
    private final List<String> generatorFileAnalyzers;
    private final String requestId;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 0:
            default:
                objArr[0] = "title";
                break;
            case 1:
                objArr[0] = "progressIndicator";
                break;
            case 2:
                objArr[0] = "e";
                break;
            case 3:
                objArr[0] = "project";
                break;
            case 4:
                objArr[0] = "productionModule";
                break;
            case 5:
                objArr[0] = "mainModule";
                break;
            case 6:
                objArr[0] = "module";
                break;
        }
        objArr[1] = "com/aicode/template/generator/CreateTestFileTask";
        switch (i) {
            case 0:
            default:
                objArr[2] = "<init>";
                break;
            case 1:
                objArr[2] = "run";
                break;
            case 2:
                objArr[2] = "onThrowable";
                break;
            case 3:
            case 4:
                objArr[2] = "suggestModuleForTestsReflective";
                break;
            case 5:
                objArr[2] = "computeTestRoots";
                break;
            case 6:
                objArr[2] = "suitableTestSourceFolders";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CreateTestFileTask(@Nullable Project project, @NotNull String title, List<VirtualFile> javaFiles, GeneratorConfig generatorConfig) {
        super(project, title);
        if (title == null) {
            $$$reportNull$$$0(0);
        }
        this.isCanceled = new AtomicBoolean(false);
        this.javaFiles = javaFiles;
        this.generatorConfig = generatorConfig;
        this.project = getProject();
        GeneratorTemplateConfig templateConfig = new GeneratorTemplateConfig();
        if (Boolean.TRUE.equals(generatorConfig.getEnabledGenerateByTemplate())) {
            generatorConfig.setRequestAi(false);
            templateConfig.setRequestAi(false);
        } else {
            generatorConfig.setRequestAi(true);
            templateConfig.setRequestAi(true);
        }
        templateConfig.setTestFramework(generatorConfig.getTestFramework());
        templateConfig.setMockFramework(generatorConfig.getMockFramework());
        templateConfig.setTestPrivate(generatorConfig.isTestPrivate());
        this.testTemplateContextBuilder = new TestTemplateContextBuilder(new MockBuilderFactory(), new MethodReferencesBuilder(), templateConfig);
        this.codeRefactorUtil = new CodeRefactorUtil();
        this.generatedClassNameResolver = new GeneratedClassNameResolver();
        this.targetDirectoryLocator = new TargetDirectoryLocator();
        this.cacheFileTemplateMap = new HashMap();
        this.generatingNumber = new AtomicInteger(0);
        this.failedNumber = new AtomicInteger(0);
        this.allMethodSize = new AtomicInteger(0);
        this.generatorFileAnalyzers = new ArrayList();
        this.requestId = IdUtil.fastSimpleUUID();
    }

    public CreateTestFileTask(@Nullable Project project, GeneratorConfig generatorConfig) {
        super(project, BasicActionsBundle.message("config.batch.unit.test.title", new Object[0]));
        this.isCanceled = new AtomicBoolean(false);
        this.javaFiles = convertToFile(generatorConfig);
        this.generatorConfig = generatorConfig;
        this.project = getProject();
        GeneratorTemplateConfig templateConfig = new GeneratorTemplateConfig();
        if (Boolean.TRUE.equals(generatorConfig.getEnabledGenerateByTemplate())) {
            generatorConfig.setRequestAi(false);
            templateConfig.setRequestAi(false);
        } else {
            generatorConfig.setRequestAi(true);
            templateConfig.setRequestAi(true);
        }
        templateConfig.setTestFramework(generatorConfig.getTestFramework());
        templateConfig.setMockFramework(generatorConfig.getMockFramework());
        templateConfig.setTestPrivate(generatorConfig.isTestPrivate());
        this.testTemplateContextBuilder = new TestTemplateContextBuilder(new MockBuilderFactory(), new MethodReferencesBuilder(), templateConfig);
        this.codeRefactorUtil = new CodeRefactorUtil();
        this.generatedClassNameResolver = new GeneratedClassNameResolver();
        this.targetDirectoryLocator = new TargetDirectoryLocator();
        this.cacheFileTemplateMap = new HashMap();
        this.generatingNumber = new AtomicInteger(0);
        this.failedNumber = new AtomicInteger(0);
        this.allMethodSize = new AtomicInteger(0);
        this.generatorFileAnalyzers = new ArrayList();
        this.requestId = IdUtil.fastSimpleUUID();
    }

    /* JADX WARN: Incorrect condition in loop: B:37:0x0210 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run(@NotNull ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(1);
        }
        clearCache();
        progressIndicator.setIndeterminate(false);
        FileTemplateConfig fileTemplateConfig = new FileTemplateConfig(this.generatorConfig.isTestPrivate(), this.generatorConfig.isTestPrivate(), true);
        String framework = this.generatorConfig.getTestFramework().getName();
        String mock = UnitTestMockEnum.OFF.equals(this.generatorConfig.getMockFramework()) ? "" : this.generatorConfig.getMockFramework().getName();
        final ProjectRootManager projectRootManager = ProjectRootManager.getInstance(this.project);
        PsiManager psiManager = PsiManager.getInstance(this.project);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("validate test directory");
        if (StringUtils.isNotBlank(this.generatorConfig.getTestModuleDirectory())) {
            boolean validateSuccess = false;
            try {
                try {
                    String testDirectory = this.generatorConfig.getTestModuleDirectory();
                    String directory = testDirectory.indexOf("src") > 0 ? testDirectory.substring(0, testDirectory.indexOf("src") + 3) : testDirectory;
                    VirtualFile directoryFile = LocalFileSystem.getInstance().findFileByIoFile(new File(directory));
                    Module baseModule = ModuleUtil.findModuleForFile(directoryFile, this.project);
                    this.targetDirectoryLocator.checkAndCreateTestDirectory(this.project, baseModule, this.generatorConfig.getTestModuleDirectory());
                    validateSuccess = ((Boolean) ApplicationManager.getApplication().runReadAction(new Computable<Boolean>() { // from class: com.aicode.template.generator.CreateTestFileTask.1
                        /* renamed from: compute, reason: merged with bridge method [inline-methods] */
                        public Boolean m338compute() {
                            Module srcModule = projectRootManager.getFileIndex().getModuleForFile(CreateTestFileTask.this.javaFiles.get(0));
                            if (srcModule == null) {
                                CreateTestFileTask.LOG.warn("获取module失败:" + CreateTestFileTask.this.javaFiles.get(0).getCanonicalPath());
                                return false;
                            }
                            Module testModule = CreateTestFileTask.this.suggestModuleForTestsReflective(CreateTestFileTask.this.project, srcModule, CreateTestFileTask.this.generatorConfig.getTestModuleDirectory());
                            if (testModule == null) {
                                CreateTestFileTask.LOG.warn("获取测试module失败:" + CreateTestFileTask.this.generatorConfig.getTestModuleDirectory());
                                return false;
                            }
                            return true;
                        }
                    })).booleanValue();
                    if (!validateSuccess) {
                        stopGenerateAndClearCache(this.javaFiles);
                        progressIndicator.stop();
                        ApplicationManager.getApplication().invokeLater(() -> {
                            CommonService.messageBus(this.project, BasicActionsBundle.message("config.batch.unit.test.create.error", new Object[0]), MessageType.ERROR);
                        });
                    }
                } catch (Exception e) {
                    LOG.warn("更新测试目录异常", e);
                    if (!validateSuccess) {
                        stopGenerateAndClearCache(this.javaFiles);
                        progressIndicator.stop();
                        ApplicationManager.getApplication().invokeLater(() -> {
                            CommonService.messageBus(this.project, BasicActionsBundle.message("config.batch.unit.test.create.error", new Object[0]), MessageType.ERROR);
                        });
                    }
                }
                if (!validateSuccess) {
                    return;
                }
            } catch (Throwable th) {
                if (!validateSuccess) {
                    stopGenerateAndClearCache(this.javaFiles);
                    progressIndicator.stop();
                    ApplicationManager.getApplication().invokeLater(() -> {
                        CommonService.messageBus(this.project, BasicActionsBundle.message("config.batch.unit.test.create.error", new Object[0]), MessageType.ERROR);
                    });
                }
                throw th;
            }
        }
        stopWatch.stop();
        stopWatch.start("build request params");
        buildRequestParams(progressIndicator, psiManager, projectRootManager, framework, mock, fileTemplateConfig);
        CompletableFuture<String> completableFuture = doSendRequestAi(progressIndicator, this.project);
        stopWatch.stop();
        stopWatch.start("wait Ai");
        int sleepTimes = TemplateRequestService.calculateRequestAiInterval(this.allMethodSize.get());
        if (!this.javaFiles.isEmpty()) {
            AtomicInteger guessTimes = new AtomicInteger(TemplateRequestService.calculateGeneratorTimes(this.allMethodSize.get(), sleepTimes));
            LOG.debug("guessTimes:" + guessTimes.get());
            AtomicInteger doTimes = new AtomicInteger(0);
            AtomicReference<FileRequestDto> fileResponse = new AtomicReference<>(null);
            boolean checkRequestIsOverAndWriteFile = checkRequestIsOverAndWriteFile(completableFuture, fileResponse, sleepTimes, guessTimes, doTimes, fileTemplateConfig);
            while (!futureIsDone) {
                int times = doTimes.incrementAndGet();
                int limitTimes = guessTimes.get() - times;
                if (limitTimes > 0) {
                    String message = BasicActionsBundle.message("config.batch.unit.test.generate.wait.message", this.javaFiles.size() + "个文件", Integer.valueOf(limitTimes), Integer.valueOf(times));
                    progressIndicator.setText(message);
                } else {
                    setProgress(progressIndicator, GeneratorProcess.GENERATING);
                    progressIndicator.setText(GeneratorProcess.GENERATING.getMessage());
                    progressIndicator.setIndeterminate(true);
                }
                if (this.isCanceled.get() || progressIndicator.isCanceled()) {
                    progressIndicator.stop();
                    this.isCanceled.set(true);
                    stopGenerateAndClearCache(this.javaFiles);
                    return;
                } else {
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    checkRequestIsOverAndWriteFile = checkRequestIsOverAndWriteFile(completableFuture, fileResponse, sleepTimes, guessTimes, doTimes, fileTemplateConfig);
                }
            }
            setProgress(progressIndicator, GeneratorProcess.GENERATED);
            LOG.debug("generatingNumber：" + this.generatingNumber.get() + "，guessTimes:" + guessTimes.get() + ",sleepTimes:" + AICodeSettingsState.getInstance().getUnitRequestInterval());
        }
        this.generatorConfig.setGeneratorFilePathList(new ArrayList(this.generatorFileAnalyzers));
        stopGenerateAndClearCache(this.javaFiles);
        stopWatch.stop();
        LOG.debug(stopWatch.prettyPrint(TimeUnit.SECONDS));
    }

    public void onFinished() {
        ApplicationManager.getApplication().invokeLater(() -> {
            LOG.debug("执行路径：" + this.generatorConfig.getExecPath());
            int skipSize = this.javaFiles.size() - this.generatingNumber.get();
            if (!this.isCanceled.get()) {
                this.isCanceled.set(true);
                String message = BasicActionsBundle.message("config.batch.unit.test.notice", this.generatorConfig.getTestGenerationProcess().getDescription(), Integer.valueOf(this.generatingNumber.get()), Integer.valueOf(skipSize), Integer.valueOf(this.failedNumber.get()));
                CommonService.messageBus(this.project, message, MessageType.INFO);
                if (!Objects.equals(TestGenerationProcess.GENERATION, this.generatorConfig.getTestGenerationProcess())) {
                    BatchUnitTestTemplateService.doCompile(this.project, true, this.generatorConfig);
                }
            }
        });
    }

    public void onThrowable(@NotNull Throwable e) {
        if (e == null) {
            $$$reportNull$$$0(2);
        }
        LOG.warn("[批量单测文件生成失败] fail : " + e.getMessage());
        LOG.warn("[批量单测文件生成失败] fail : " + e.getCause());
        LOG.warn("[批量单测文件生成失败] fail : " + e.getStackTrace());
        stopGenerateAndClearCache(this.javaFiles);
    }

    private boolean checkRequestIsOverAndWriteFile(CompletableFuture completableFuture, AtomicReference<FileRequestDto> fileResponse, int sleepTimes, AtomicInteger guessTimes, AtomicInteger doTimes, FileTemplateConfig fileTemplateConfig) {
        boolean futureIsDone = completableFuture.isDone();
        if (futureIsDone) {
            futureIsDone = TemplateRequestService.isAllReturned(this.requestId);
        }
        if (fileResponse.get() == null) {
            FileRequestDto requestDto = TemplateRequestService.getReturnedFile(this.requestId);
            if (requestDto != null) {
                fileResponse.set(requestDto);
                int sleepTimes2 = AICodeSettingsState.getInstance().getUnitRequestInterval();
                AICodeSettingsState.getInstance().setUnitRequestInterval(requestDto.getDiff(sleepTimes2));
                int newGuessTimes = TemplateRequestService.calculateGeneratorTimes(this.allMethodSize.addAndGet(-requestDto.getMethodRequestResults().size()), requestDto.getDiff(sleepTimes2));
                LOG.debug("reset sleepTimes:" + sleepTimes2 + ",newGuessTimes:" + newGuessTimes + ",oldGuessTimes:" + guessTimes.get());
                guessTimes.set(newGuessTimes + doTimes.get());
                LOG.debug("guessTimes:" + guessTimes.get());
                futureIsDone = false;
                ApplicationManager.getApplication().invokeLater(() -> {
                    doWrite(fileTemplateConfig, (FileRequestDto) fileResponse.get());
                    fileResponse.set(null);
                });
            }
        } else {
            futureIsDone = false;
        }
        return futureIsDone;
    }

    private void buildRequestParams(ProgressIndicator progressIndicator, final PsiManager psiManager, final ProjectRootManager projectRootManager, String framework, String mock, FileTemplateConfig fileTemplateConfig) {
        double base = GeneratorProcess.REQUEST_AI.getProcess();
        setProgress(progressIndicator, GeneratorProcess.REQUEST_AI);
        double times = 1.0d;
        for (final VirtualFile javaFile : this.javaFiles) {
            try {
            } catch (Exception e) {
                LOG.warn("生成上下文异常", e);
            }
            if (this.isCanceled.get() || progressIndicator.isCanceled()) {
                this.isCanceled.set(true);
                stopGenerateAndClearCache(this.javaFiles);
                return;
            }
            final GeneratorFileConfig generatorFileConfig = new GeneratorFileConfig();
            ApplicationManager.getApplication().runReadAction(new Computable<Integer>() { // from class: com.aicode.template.generator.CreateTestFileTask.2
                /* renamed from: compute, reason: merged with bridge method [inline-methods] */
                public Integer m339compute() {
                    PsiClass srcClass;
                    JavaPsiFacade facade = JavaPsiFacade.getInstance(CreateTestFileTask.this.project);
                    PsiFile psiFile = psiManager.findFile(javaFile);
                    if (psiFile instanceof PsiJavaFile) {
                        PsiFile psiFile2 = (PsiJavaFile) psiFile;
                        if (psiFile2.getClasses().length > 0) {
                            srcClass = psiFile2.getClasses()[0];
                        } else {
                            srcClass = null;
                        }
                        String packageName = psiFile2.getPackageName();
                        if (StringUtils.contains(CreateTestFileTask.this.generatorConfig.getTestModuleDirectory(), "com")) {
                            String name = ClassNameUtils.getPackageName(CreateTestFileTask.this.generatorConfig.getTestModuleDirectory());
                            if (StringUtils.isNotBlank(name)) {
                                packageName = name;
                            }
                        }
                        PsiPackage srcPackage = facade.findPackage(packageName);
                        if (srcPackage == null) {
                            srcPackage = facade.findPackage(psiFile2.getPackageName());
                            BatchUnitTestSettingsState.getInstance().testModuleDirectory = "";
                        }
                        Module srcModule = projectRootManager.getFileIndex().getModuleForFile(javaFile);
                        if (srcClass == null || srcModule == null) {
                            return null;
                        }
                        Module testModule = CreateTestFileTask.this.suggestModuleForTestsReflective(CreateTestFileTask.this.project, srcModule, CreateTestFileTask.this.generatorConfig.getTestModuleDirectory());
                        if (testModule == null) {
                            return null;
                        }
                        List<VirtualFile> testRootUrls = CreateTestFileTask.computeTestRoots(testModule);
                        if (testRootUrls.isEmpty() && CreateTestFileTask.computeSuitableTestRootUrls(testModule).isEmpty()) {
                            testModule = srcModule;
                        }
                        if (!testModule.equals(CreateTestFileTask.this.generatorConfig.getTestModule())) {
                            CreateTestFileTask.this.generatorConfig.setTestModule(testModule);
                            CreateTestFileTask.this.generatorConfig.setModule(testModule);
                        }
                        generatorFileConfig.setPsiFile(psiFile2);
                        generatorFileConfig.setSrcClass(srcClass);
                        generatorFileConfig.setPsiPackage(srcPackage);
                        generatorFileConfig.setSrcModule(srcModule);
                        generatorFileConfig.setTestModule(testModule);
                        generatorFileConfig.setTestFileName(CreateTestFileTask.this.generatorConfig.getTestFileName());
                    }
                    return 0;
                }
            });
            generatorFileConfig.setMock(mock);
            generatorFileConfig.setFramework(framework);
            generatorFileConfig.setDuplicateRule(this.generatorConfig.getDuplicateRule());
            requestAndBuildParams(this.project, generatorFileConfig, fileTemplateConfig, progressIndicator);
            progressIndicator.setFraction(base + (base * (times / this.javaFiles.size())));
            times += 1.0d;
        }
    }

    private CompletableFuture<String> doSendRequestAi(ProgressIndicator progressIndicator, Project project) {
        List<MessageDto> messageDtos = (List) this.cacheFileTemplateMap.entrySet().stream().map((v0) -> {
            return v0.getValue();
        }).map((v0) -> {
            return v0.getMessageDtos();
        }).flatMap((v0) -> {
            return v0.stream();
        }).collect(Collectors.toList());
        this.allMethodSize.set(messageDtos.size());
        return CompletableFuture.supplyAsync(() -> {
            try {
                int sleepTimes = AICodeSettingsState.getInstance().getUnitRequestInterval();
                LOG.debug("sleepTimes:" + sleepTimes);
                for (int i = 0; i < messageDtos.size(); i++) {
                    MessageDto item = (MessageDto) messageDtos.get(i);
                    if (this.isCanceled.get() || progressIndicator.isCanceled()) {
                        return "FAILED";
                    }
                    item.setTaskId(this.requestId);
                    PluginWebsocketClient.sendWsMessage(item, project);
                    if (i > 0) {
                        try {
                            TimeUnit.SECONDS.sleep(sleepTimes);
                        } catch (InterruptedException e) {
                            LOG.warn("Thread was interrupted, Failed to complete operation");
                        }
                    }
                }
                return "SUCCESS";
            } catch (Exception e2) {
                LOG.warn("[请求AI模型] fail : " + e2.getMessage());
                LOG.warn("[请求AI模型] fail : " + e2.getCause());
                LOG.warn("[请求AI模型] fail : " + e2.getStackTrace());
                return "FAILED";
            }
        });
    }

    private void doWrite(FileTemplateConfig fileTemplateConfig, FileRequestDto requestDto) {
        if (requestDto != null) {
            String filePath = requestDto.getFilePath();
            CacheFileTemplate cacheFileTemplate = this.cacheFileTemplateMap.get(filePath);
            if (cacheFileTemplate != null) {
                writeTestClass(this.project, cacheFileTemplate, fileTemplateConfig, filePath);
            }
            TemplateRequestService.remove(this.requestId, filePath);
        }
    }

    public Boolean isCanceled() {
        return Boolean.valueOf(this.isCanceled.get());
    }

    private void stopGenerateAndClearCache(List<VirtualFile> javaFiles) {
        try {
            for (VirtualFile javaFile : javaFiles) {
                TemplateRequestService.remove(this.requestId, javaFile.getCanonicalPath(), false);
            }
        } catch (Exception e) {
        }
        this.cacheFileTemplateMap.clear();
        this.testTemplateContextBuilder.clearCache();
    }

    private void clearCache() {
        TemplateRequestService.clearCache();
        this.cacheFileTemplateMap.clear();
        this.testTemplateContextBuilder.clearCache();
    }

    private void requestAndBuildParams(Project project, GeneratorFileConfig generatorFileConfig, FileTemplateConfig fileTemplateConfig, ProgressIndicator progressIndicator) {
        if (generatorFileConfig != null && (generatorFileConfig.getPsiFile() instanceof PsiJavaFile) && generatorFileConfig.getPsiPackage() != null) {
            PsiPackage finalSrcPackage = generatorFileConfig.getPsiPackage();
            Module finalTestModule = generatorFileConfig.getTestModule();
            Module finalSrcModule = generatorFileConfig.getSrcModule();
            PsiClass finalSrcClass = generatorFileConfig.getSrcClass();
            PsiDirectory targetDirectory = this.targetDirectoryLocator.getOrCreateDirectory(project, finalSrcPackage, finalTestModule, this.generatorConfig.getTestModuleDirectory());
            if (targetDirectory == null) {
                LOG.warn("生成目标测试目录失败：" + finalSrcPackage.getQualifiedName());
                return;
            }
            String packageName = finalSrcPackage.getQualifiedName();
            TemplateDescriptor templateDescriptor = new TemplateRegistry().getEnabledTemplateDescriptor(generatorFileConfig.getFramework(), generatorFileConfig.getMock());
            ClassNameSelection classNameSelection = (ClassNameSelection) WriteCommandAction.runWriteCommandAction(project, () -> {
                return this.generatedClassNameResolver.resolveClassName(project, targetDirectory, finalSrcClass, templateDescriptor, generatorFileConfig.getDuplicateRule(), generatorFileConfig.getTestFileName());
            });
            Boolean isGenerating = TemplateRequestService.containFile(this.requestId, generatorFileConfig.getPsiFile().getVirtualFile().getCanonicalPath());
            if (isGenerating.booleanValue() || classNameSelection.getUserDecision().equals(ClassNameSelection.UserDecision.Abort)) {
                LOG.info("classNameSelection is Abort:" + classNameSelection.getClassName());
                setExecPath(packageName, classNameSelection.getClassName());
                this.generatorFileAnalyzers.add(targetDirectory.getVirtualFile().getCanonicalPath() + "/" + classNameSelection.getClassName() + ".java");
                return;
            }
            setExecPath(packageName, classNameSelection.getClassName());
            AICodeSettingsState.getInstance().generateUnitTestFile = classNameSelection.getClassName() + "." + FileUtilRt.getExtension(templateDescriptor.getFilename());
            FileTemplateContext context = new FileTemplateContext(new FileTemplateDescriptor(templateDescriptor.getFilename()), project, classNameSelection.getClassName(), finalSrcPackage, finalSrcModule, finalTestModule, targetDirectory, finalSrcClass, fileTemplateConfig, this.generatorConfig.getExcludeMethodList(), this.generatorConfig.getRequestAi());
            context.setFilePath(generatorFileConfig.getPsiFile().getVirtualFile().getCanonicalPath());
            Map<String, Object> paramMaps = getTestClassPropsMap(context, targetDirectory);
            if (paramMaps == null) {
                LOG.warn("生成模板上下文信息失败");
                return;
            }
            CacheFileTemplate cacheFileTemplate = new CacheFileTemplate();
            cacheFileTemplate.setContext(context);
            if (context.getRequestAi().booleanValue() && paramMaps.containsKey("messages")) {
                List<MessageDto> messageDtos = (List) paramMaps.get("messages");
                cacheFileTemplate.setMessageDtos(messageDtos);
            }
            cacheFileTemplate.setParamMaps(paramMaps);
            cacheFileTemplate.setTargetDirectory(targetDirectory);
            cacheFileTemplate.setGeneratorFileConfig(generatorFileConfig);
            this.cacheFileTemplateMap.put(context.getFilePath(), cacheFileTemplate);
        }
    }

    private void writeTestClass(final Project project, CacheFileTemplate cacheFileTemplate, FileTemplateConfig fileTemplateConfig, String filePath) {
        try {
            final GeneratorFileConfig generatorFileConfig = cacheFileTemplate.getGeneratorFileConfig();
            if (generatorFileConfig == null) {
                this.failedNumber.incrementAndGet();
                return;
            }
            if ((generatorFileConfig.getPsiFile() instanceof PsiJavaFile) && generatorFileConfig.getPsiPackage() != null) {
                PsiPackage finalSrcPackage = generatorFileConfig.getPsiPackage();
                Module finalTestModule = generatorFileConfig.getTestModule();
                Module finalSrcModule = generatorFileConfig.getSrcModule();
                final PsiClass finalSrcClass = generatorFileConfig.getSrcClass();
                final PsiDirectory targetDirectory = this.targetDirectoryLocator.getOrCreateDirectory(project, finalSrcPackage, finalTestModule, this.generatorConfig.getTestModuleDirectory());
                if (targetDirectory == null) {
                    LOG.warn("生成目标测试目录失败：" + finalSrcPackage.getQualifiedName());
                    this.failedNumber.incrementAndGet();
                    return;
                }
                final TemplateDescriptor templateDescriptor = new TemplateRegistry().getEnabledTemplateDescriptor(generatorFileConfig.getFramework(), generatorFileConfig.getMock());
                ClassNameSelection classNameSelection = (ClassNameSelection) WriteCommandAction.runWriteCommandAction(project, new Computable<ClassNameSelection>() { // from class: com.aicode.template.generator.CreateTestFileTask.3
                    /* renamed from: compute, reason: merged with bridge method [inline-methods] */
                    public ClassNameSelection m340compute() {
                        ClassNameSelection classNameSelection2 = CreateTestFileTask.this.generatedClassNameResolver.resolveClassName(project, targetDirectory, finalSrcClass, templateDescriptor, generatorFileConfig.getDuplicateRule(), generatorFileConfig.getTestFileName());
                        return classNameSelection2;
                    }
                });
                String packageName = finalSrcPackage.getQualifiedName();
                Boolean isGenerating = TemplateRequestService.containFile(this.requestId, generatorFileConfig.getPsiFile().getVirtualFile().getCanonicalPath());
                if (isGenerating.booleanValue() || classNameSelection.getUserDecision().equals(ClassNameSelection.UserDecision.Abort)) {
                    LOG.info("classNameSelection is Abort:" + classNameSelection.getClassName());
                    this.failedNumber.incrementAndGet();
                    if (!DuplicateRule.OVERWRITE.equals(this.generatorConfig.getDuplicateRule())) {
                        setExecPath(packageName, classNameSelection.getClassName());
                        return;
                    }
                    return;
                }
                setExecPath(packageName, classNameSelection.getClassName());
                AICodeSettingsState.getInstance().generateUnitTestFile = classNameSelection.getClassName() + "." + FileUtilRt.getExtension(templateDescriptor.getFilename());
                FileTemplateContext context = new FileTemplateContext(new FileTemplateDescriptor(templateDescriptor.getFilename()), project, classNameSelection.getClassName(), finalSrcPackage, finalSrcModule, finalTestModule, targetDirectory, finalSrcClass, fileTemplateConfig, this.generatorConfig.getExcludeMethodList(), this.generatorConfig.getRequestAi());
                context.setFilePath(generatorFileConfig.getPsiFile().getVirtualFile().getCanonicalPath());
                if (cacheFileTemplate.getParamMaps() != null && cacheFileTemplate.getParamMaps().containsKey("TESTED_CLASS")) {
                    Boolean hasTestMethods = (Boolean) cacheFileTemplate.getParamMaps().get("HAS_TEST_METHODS");
                    if (hasTestMethods.booleanValue()) {
                        this.testTemplateContextBuilder.resolveMethodCallByCaseResult((Type) cacheFileTemplate.getParamMaps().get("TESTED_CLASS"));
                        FileTemplateManager fileTemplateManager = UnitTemplateManager.getInstance(targetDirectory.getProject());
                        String templateName = context.getFileTemplateDescriptor().getFileName();
                        writeTestFile(fileTemplateManager, templateName, cacheFileTemplate.getContext(), cacheFileTemplate.getParamMaps(), targetDirectory, filePath);
                    } else {
                        LOG.warn("当前类没有适合生成单测的方法" + context.getTargetClass());
                    }
                } else {
                    LOG.warn("未解析成功或不支持类型" + context.getTargetClass());
                    this.failedNumber.incrementAndGet();
                }
            }
        } catch (Exception e) {
            LOG.warn("生成文件失败", e);
        }
    }

    private Map<String, Object> getTestClassPropsMap(final FileTemplateContext context, final PsiDirectory targetDirectory) {
        FileTemplateManager fileTemplateManager = UnitTemplateManager.getInstance(targetDirectory.getProject());
        try {
            Integer integer = (Integer) ApplicationManager.getApplication().runReadAction(new Computable<Integer>() { // from class: com.aicode.template.generator.CreateTestFileTask.4
                /* renamed from: compute, reason: merged with bridge method [inline-methods] */
                public Integer m341compute() {
                    PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(targetDirectory);
                    if (aPackage != null) {
                        GlobalSearchScope scope = GlobalSearchScopesCore.directoryScope(targetDirectory, false);
                        try {
                            PsiElement[] findClassByShortName = aPackage.findClassByShortName(context.getTargetClass(), scope);
                            if (findClassByShortName.length > 0) {
                                if (!FileModificationService.getInstance().preparePsiElementForWrite(findClassByShortName[0])) {
                                    return null;
                                }
                                return null;
                            }
                        } catch (IndexNotReadyException e) {
                            return null;
                        }
                    }
                    return 0;
                }
            });
            if (integer == null) {
                return null;
            }
            return this.testTemplateContextBuilder.build(this.requestId, context, fileTemplateManager.getDefaultProperties());
        } catch (Exception e) {
            LOG.warn("error generating test class", e);
            return null;
        }
    }

    private void writeTestFile(FileTemplateManager fileTemplateManager, String templateName, FileTemplateContext context, Map<String, Object> templateCtxtParams, PsiDirectory targetDirectory, String filePath) {
        FileTemplate codeTemplate = fileTemplateManager.getInternalTemplate(templateName);
        codeTemplate.setReformatCode(true);
        Velocity.setProperty("velocimacro.max.depth", 200);
        VelocityInitializer.verifyRuntimeSetup();
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> {
            long startGeneration = System.currentTimeMillis();
            PsiFile psiFile = (PsiFile) WriteCommandAction.runWriteCommandAction(this.project, () -> {
                try {
                    PsiElement psiElement = FileTemplateUtil.createFromTemplate(codeTemplate, context.getTargetClass(), templateCtxtParams, targetDirectory, (ClassLoader) null);
                    PsiFile resolveEmbeddedClass = resolveEmbeddedClass(psiElement);
                    LOG.info("Done generating PsiElement from template " + codeTemplate.getName() + " in " + (System.currentTimeMillis() - startGeneration) + " millis");
                    return resolveEmbeddedClass instanceof PsiFile ? resolveEmbeddedClass : resolveEmbeddedClass.getContainingFile();
                } catch (Throwable e) {
                    LOG.warn("模板生成失败", e);
                    return null;
                }
            });
            if (psiFile == null) {
                AICodeSettingsState.getInstance().generateUnitTestFile = "";
                this.failedNumber.incrementAndGet();
                return;
            }
            batchUnitTestDataCollect(psiFile, filePath);
            AICodeSettingsState.getInstance().generateUnitTestFile = "";
            this.generatorFileAnalyzers.add(psiFile.getVirtualFile().getCanonicalPath());
            this.generatingNumber.incrementAndGet();
            JavaCodeStyleManager codeStyleManager = JavaCodeStyleManager.getInstance(this.project);
            long startReformating = System.currentTimeMillis();
            WriteCommandAction.runWriteCommandAction(this.project, () -> {
                try {
                    codeStyleManager.optimizeImports(psiFile);
                    codeStyleManager.shortenClassReferences(psiFile);
                    this.codeRefactorUtil.uncommentImports(psiFile, context.getProject());
                    TextRange textRange = psiFile.getTextRange();
                    CodeStyleManager.getInstance(this.project).reformatText(psiFile, textRange.getStartOffset(), textRange.getEndOffset());
                    LOG.info("Done reformatting generated PsiClass in " + (System.currentTimeMillis() - startReformating) + " millis");
                } catch (Throwable e) {
                    LOG.info("reformatText error", e);
                }
            });
        });
    }

    private PsiElement resolveEmbeddedClass(PsiElement psiElement) {
        PsiElement resolveEmbeddedClass = resolveEmbeddedClassRecursive(psiElement, 2);
        if (resolveEmbeddedClass == null) {
            return psiElement;
        }
        return resolveEmbeddedClass;
    }

    @Nullable
    private PsiElement resolveEmbeddedClassRecursive(PsiElement psiElement, int recursionLevel) {
        if ((psiElement instanceof PsiClass) || (psiElement != null && psiElement.getClass().getCanonicalName().equals("org.jetbrains.kotlin.psi.KtClass"))) {
            return psiElement;
        }
        if (recursionLevel <= 0) {
            return null;
        }
        PsiElement[] psiElementChildren = psiElement.getChildren();
        for (PsiElement psiElementChild : psiElementChildren) {
            PsiElement resolvedPsiClass = resolveEmbeddedClassRecursive(psiElementChild, recursionLevel - 1);
            if (resolvedPsiClass != null) {
                return resolvedPsiClass;
            }
        }
        return null;
    }

    private Module suggestModuleForTestsReflective(@NotNull Project project, @NotNull Module productionModule, String moduleDirectory) {
        if (project == null) {
            $$$reportNull$$$0(3);
        }
        if (productionModule == null) {
            $$$reportNull$$$0(4);
        }
        try {
            if (StringUtils.isNotBlank(moduleDirectory)) {
                Module baseModule = ModuleUtil.findModuleForFile(LocalFileSystem.getInstance().findFileByIoFile(new File(moduleDirectory)), project);
                return baseModule;
            }
            Method suggestModuleForTests = null;
            for (Method method : CreateTestAction.class.getDeclaredMethods()) {
                Class<?>[] parameters = method.getParameterTypes();
                if (method.getReturnType().isAssignableFrom(Module.class) && parameters != null && parameters.length == 2 && parameters[0].isAssignableFrom(Project.class) && parameters[1].isAssignableFrom(Module.class)) {
                    suggestModuleForTests = method;
                }
            }
            if (suggestModuleForTests != null) {
                suggestModuleForTests.setAccessible(true);
                Object module = suggestModuleForTests.invoke(null, project, productionModule);
                if (module != null) {
                    return (Module) module;
                }
                return null;
            }
            return null;
        } catch (Exception e) {
            LOG.info("suggestModuleForTests Method mot found. expected to exist on idea 15 - 2017. falling back to older implementation", e);
            return productionModule;
        }
    }

    public static List<VirtualFile> computeTestRoots(@NotNull Module mainModule) {
        if (mainModule == null) {
            $$$reportNull$$$0(5);
        }
        ArrayList<VirtualFile> virtualFiles = new ArrayList<>();
        List<SourceFolder> sourceFolders = suitableTestSourceFolders(mainModule);
        if (!sourceFolders.isEmpty()) {
            for (SourceFolder sourceFolder : sourceFolders) {
                if (sourceFolder.getFile() != null) {
                    virtualFiles.add(sourceFolder.getFile());
                }
            }
        } else {
            HashSet<Module> modules = new HashSet<>();
            ModuleUtilCore.collectModulesDependsOn(mainModule, modules);
            Iterator<Module> it = modules.iterator();
            while (it.hasNext()) {
                Module module = it.next();
                List<SourceFolder> folders = suitableTestSourceFolders(module);
                for (SourceFolder sourceFolder2 : folders) {
                    if (sourceFolder2.getFile() != null) {
                        virtualFiles.add(sourceFolder2.getFile());
                    }
                }
            }
        }
        return virtualFiles;
    }

    public static List<String> computeSuitableTestRootUrls(Module module) {
        ArrayList<String> rootUrls = new ArrayList<>();
        for (SourceFolder sourceFolder : suitableTestSourceFolders(module)) {
            rootUrls.add(sourceFolder.getUrl());
        }
        return rootUrls;
    }

    private static List<SourceFolder> suitableTestSourceFolders(@NotNull Module module) {
        if (module == null) {
            $$$reportNull$$$0(6);
        }
        ArrayList<SourceFolder> sourceFolders = new ArrayList<>();
        for (ContentEntry contentEntry : ModuleRootManager.getInstance(module).getContentEntries()) {
            List<SourceFolder> testSourceFolders = contentEntry.getSourceFolders(JavaSourceRootType.TEST_SOURCE);
            for (SourceFolder sourceFolder : testSourceFolders) {
                if (!isForGeneratedSources(sourceFolder)) {
                    sourceFolders.add(sourceFolder);
                }
            }
        }
        return sourceFolders;
    }

    private static boolean isForGeneratedSources(SourceFolder sourceFolder) {
        JavaSourceRootProperties properties = sourceFolder.getJpsElement().getProperties(JavaModuleSourceRootTypes.SOURCES);
        JavaResourceRootProperties resourceProperties = sourceFolder.getJpsElement().getProperties(JavaModuleSourceRootTypes.RESOURCES);
        return (properties != null && properties.isForGeneratedSources()) || (resourceProperties != null && resourceProperties.isForGeneratedSources());
    }

    private List<VirtualFile> convertToFile(GeneratorConfig generatorConfig) {
        List<VirtualFile> virtualFiles = new ArrayList<>();
        List<String> absolutePaths = generatorConfig.getFileAbsolutePathList();
        Integer limit = generatorConfig.getTestUnitLimit();
        int count = 0;
        for (String absolutePath : absolutePaths) {
            if (count >= limit.intValue()) {
                break;
            }
            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(absolutePath);
            if (virtualFile != null) {
                virtualFiles.add(virtualFile);
                count++;
            }
        }
        return virtualFiles;
    }

    private void setExecPath(String packageName, String className) {
        if (this.javaFiles.size() == 1) {
            this.generatorConfig.setSingleFile(true);
            this.generatorConfig.setExecPath(packageName + "." + className);
        } else if (this.javaFiles.size() > 1) {
            String newPackageName = isTopPackage(this.generatorConfig.getExecPath(), packageName);
            this.generatorConfig.setExecPath(newPackageName);
        }
    }

    private String isTopPackage(String firstPackage, String secondPackage) {
        if (StringUtils.isEmpty(firstPackage)) {
            return secondPackage;
        }
        if (StringUtils.isEmpty(secondPackage)) {
            return firstPackage;
        }
        int first = firstPackage.split("\\.").length;
        int second = secondPackage.split("\\.").length;
        return first <= second ? firstPackage : secondPackage;
    }

    private void setProgress(ProgressIndicator progressIndicator, GeneratorProcess process) {
        if (!progressIndicator.isIndeterminate()) {
            progressIndicator.setText2(process.getMessage());
            progressIndicator.setFraction(process.getProcess());
        }
    }

    private void batchUnitTestDataCollect(PsiElement element, String filePath) {
        List<MethodUnitTestData> list = new ArrayList<>();
        PsiJavaFile psiJavaFile = (PsiJavaFile) element;
        Application application = ApplicationManager.getApplication();
        application.runReadAction(() -> {
            Collection<PsiMethod> psiMethods = PsiTreeUtil.findChildrenOfType(psiJavaFile, PsiMethod.class);
            for (PsiMethod psiMethod : psiMethods) {
                int lineCount = JavaPsiUtils.getLineCount(psiMethod);
                String methodId = UnitTestCollectUtil.getTestMethodId(psiMethod);
                if (StringUtils.isNotBlank(methodId)) {
                    MethodUnitTestData methodUnitTestData = new MethodUnitTestData(methodId, Integer.valueOf(lineCount));
                    list.add(methodUnitTestData);
                }
            }
        });
        LOG.info("test collection generate batch " + list.size());
        UnitTestService.testCollectionGenerate(this.project, list, filePath);
    }
}
