package com.aicode.template.generator;

import cn.hutool.core.date.StopWatch;
import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.service.CommonService;
import com.aicode.enums.UnitTestMockEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.template.CodeRefactorUtil;
import com.aicode.template.FileTemplateConfig;
import com.aicode.template.TestTemplateContextBuilder;
import com.aicode.template.VelocityInitializer;
import com.aicode.template.builder.MethodReferencesBuilder;
import com.aicode.template.builder.MockBuilderFactory;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.Type;
import com.aicode.template.fileloader.FileTemplateContext;
import com.aicode.template.fileloader.TemplateDescriptor;
import com.aicode.template.fileloader.TemplateRegistry;
import com.aicode.template.fileloader.UnitTemplateManager;
import com.aicode.template.request.FileRequestDto;
import com.aicode.template.request.MethodRequestResult;
import com.aicode.template.request.TemplateRequestService;
import com.aicode.test.UnitTestService;
import com.aicode.util.ClassNameUtils;
import com.aicode.util.StringUtils;
import com.google.gson.JsonObject;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.velocity.app.Velocity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/CreateTestMethodTask.class */
public class CreateTestMethodTask extends Task.Backgroundable {
    private MethodGeneratorConfig generatorConfig;
    private static final Logger LOG = Logger.getInstance(CreateTestMethodTask.class.getName());
    private static Project project = null;
    private static TestTemplateContextBuilder testTemplateContextBuilder = null;
    private static CodeRefactorUtil codeRefactorUtil = null;
    public static Map<String, CacheFileTemplate> cacheFileTemplateMap = new HashMap();
    public static final AtomicBoolean isCanceled = new AtomicBoolean(false);
    private static final AtomicBoolean isGeneratorFailed = new AtomicBoolean(false);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 0:
            default:
                objArr[0] = "progressIndicator";
                break;
            case 1:
                objArr[0] = "e";
                break;
        }
        objArr[1] = "com/aicode/template/generator/CreateTestMethodTask";
        switch (i) {
            case 0:
            default:
                objArr[2] = "run";
                break;
            case 1:
                objArr[2] = "onThrowable";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public CreateTestMethodTask(@Nullable Project project2, MethodGeneratorConfig generatorConfig) {
        super(project2, "收集上下文");
        this.generatorConfig = null;
        this.generatorConfig = generatorConfig;
        project = project2;
        GeneratorTemplateConfig templateConfig = new GeneratorTemplateConfig();
        templateConfig.setTestMethods(new HashSet(generatorConfig.getMethods()));
        templateConfig.setSrcClass(generatorConfig.getPsiClass());
        templateConfig.setTestFramework(generatorConfig.getTestFramework());
        templateConfig.setMockFramework(generatorConfig.getMockFramework());
        if (generatorConfig.isEnabledGenerateByTemplate()) {
            templateConfig.setRequestAi(false);
        } else {
            templateConfig.setRequestAi(true);
        }
        templateConfig.setUnitTestDto(generatorConfig.getUnitTestDto());
        testTemplateContextBuilder = new TestTemplateContextBuilder(new MockBuilderFactory(), new MethodReferencesBuilder(), templateConfig);
        codeRefactorUtil = new CodeRefactorUtil();
        isCanceled.set(false);
    }

    public void run(@NotNull ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(0);
        }
        try {
            FileTemplateConfig fileTemplateConfig = new FileTemplateConfig(true, true, true);
            String framework = this.generatorConfig.getTestFramework().getName();
            String mock = UnitTestMockEnum.OFF.equals(this.generatorConfig.getMockFramework()) ? "" : this.generatorConfig.getMockFramework().getName();
            ProjectRootManager projectRootManager = ProjectRootManager.getInstance(project);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start("init Type Caches");
            GeneratorFileConfig generatorFileConfig = new GeneratorFileConfig();
            generatorFileConfig.setMock(mock);
            generatorFileConfig.setFramework(framework);
            if (Boolean.TRUE.equals(Boolean.valueOf(this.generatorConfig.isEnabledGenerateByTemplate()))) {
                generatorFileConfig.setRequestAi(false);
            } else {
                generatorFileConfig.setRequestAi(true);
            }
            ApplicationManager.getApplication().runReadAction(() -> {
                JavaPsiFacade facade = JavaPsiFacade.getInstance(project);
                PsiClass srcClass = this.generatorConfig.getPsiClass();
                String packageName = ClassNameUtils.extractPackageName(srcClass.getQualifiedName());
                if (StringUtils.isBlank(packageName)) {
                    return null;
                }
                PsiPackage srcPackage = facade.findPackage(packageName);
                if (srcPackage == null) {
                    PsiClass parent = srcClass.getParent();
                    if (parent instanceof PsiClass) {
                        String parentPackageName = ClassNameUtils.extractPackageName(parent.getQualifiedName());
                        srcPackage = facade.findPackage(parentPackageName);
                    }
                }
                Module srcModule = projectRootManager.getFileIndex().getModuleForFile(this.generatorConfig.getPsiClass().getContainingFile().getVirtualFile());
                if (srcModule == null) {
                    return null;
                }
                generatorFileConfig.setPsiFile(srcClass.getContainingFile());
                generatorFileConfig.setSrcClass(srcClass);
                generatorFileConfig.setPsiPackage(srcPackage);
                generatorFileConfig.setSrcModule(srcModule);
                generatorFileConfig.setTestModule(srcModule);
                generatorFileConfig.setTestMethods(this.generatorConfig.getMethods());
                return 0;
            });
            requestAndBuildParams(project, generatorFileConfig, fileTemplateConfig);
            stopWatch.stop();
        } catch (Exception e) {
            LOG.info("生成上下文异常", e);
            isCanceled.set(true);
            UnitTestService.sendUnitTestErrInfo(project, WebViewDataTypeEnum.UNIT_TEST_FUNCTION_LIST, BasicActionsBundle.message("unit.test.method.generate.error.text", new Object[0]), this.generatorConfig.getUnitTestDto().getId());
        }
    }

    public void genCaseCode(GeneratorFileConfig generatorFileConfig, FileTemplateConfig fileTemplateConfig, FileRequestDto requestDto) {
        if (!generatorFileConfig.getTestMethods().isEmpty()) {
            resolveData(generatorFileConfig, fileTemplateConfig, requestDto);
            if (cacheFileTemplateMap == null || cacheFileTemplateMap.isEmpty()) {
                isCanceled.set(true);
            }
        }
    }

    private void resolveData(GeneratorFileConfig generatorFileConfig, FileTemplateConfig fileTemplateConfig, FileRequestDto requestDto) {
        List<MethodRequestResult> methodRequestResults = requestDto.getMethodRequestResults();
        if (CollectionUtils.isNotEmpty(methodRequestResults)) {
            List<Method> methodList = (List) methodRequestResults.stream().map((v0) -> {
                return v0.getMethod();
            }).collect(Collectors.toList());
            this.generatorConfig.setTemplateMethods(methodList);
            if (generatorFileConfig.getRequestAi().booleanValue() && methodRequestResults.stream().allMatch(methodRequestResult -> {
                return CollectionUtils.isEmpty(methodRequestResult.getMethod().getCaseResults());
            })) {
                LOG.warn("未生成模型数据" + requestDto.getFilePath());
                isGeneratorFailed.set(true);
                isCanceled.set(true);
                stopGenerateAndClearCache(methodRequestResults, requestDto.getFilePath());
                UnitTestService.sendUnitTestErrInfo(project, WebViewDataTypeEnum.UNIT_TEST_FUNCTION_CASE_CODE, BasicActionsBundle.message("unit.test.method.request.error.text", new Object[0]), this.generatorConfig.getUnitTestDto().getId());
                return;
            }
            CacheFileTemplate cacheFileTemplate = cacheFileTemplateMap.get(requestDto.getFilePath());
            this.generatorConfig.setFunctionDataDTO(generatorFileConfig.getFunctionDataDTO());
            if (cacheFileTemplate != null) {
                writeTestClass(project, cacheFileTemplate, fileTemplateConfig);
            }
            String responseId = generatorFileConfig.getFunctionDataDTO().getId();
            int size = methodRequestResults.size();
            String lastRequestId = methodRequestResults.get(size - 1).getRequestId();
            if (StringUtils.equals(responseId, lastRequestId)) {
                stopGenerateAndClearCache(methodRequestResults, requestDto.getFilePath());
            }
        }
    }

    public void onFinished() {
        ApplicationManager.getApplication().invokeLater(() -> {
            if (this.generatorConfig.getPsiFile() != null) {
                if (this.generatorConfig.isMethodUt()) {
                }
            } else if (!isCanceled.get()) {
                String message = isGeneratorFailed.get() ? "" : BasicActionsBundle.message("config.batch.unit.test.create.single.error.ignore", new Object[0]);
                CommonService.messageBus(project, BasicActionsBundle.message("config.batch.unit.test.create.single.error", message), MessageType.WARNING);
            }
        });
    }

    public void onThrowable(@NotNull Throwable e) {
        if (e == null) {
            $$$reportNull$$$0(1);
        }
        LOG.warn("[单测文件生成失败] fail : " + e.getMessage());
        LOG.warn("[单测文件生成失败] fail : " + e.getCause());
        LOG.warn("[单测文件生成失败] fail : " + e.getStackTrace());
        isCanceled.set(true);
        clearCache();
    }

    private void stopGenerateAndClearCache(List<MethodRequestResult> methodRequestResults, String filePath) {
        try {
            String canonicalPath = this.generatorConfig.getPsiClass().getContainingFile().getVirtualFile().getCanonicalPath();
            for (MethodRequestResult result : methodRequestResults) {
                TemplateRequestService.remove(result.getRequestId(), canonicalPath, false);
            }
            cacheFileTemplateMap.remove(filePath);
            testTemplateContextBuilder.clearCache();
        } catch (Exception e) {
            LOG.warn("清理缓存报错");
        }
    }

    private void clearCache() {
        try {
            TemplateRequestService.clearCache();
            cacheFileTemplateMap.clear();
            testTemplateContextBuilder.clearCache();
        } catch (Exception e) {
            LOG.warn("清理缓存报错");
        }
    }

    public Boolean isCanceled() {
        return Boolean.valueOf(isCanceled.get());
    }

    private void requestAndBuildParams(Project project2, GeneratorFileConfig generatorFileConfig, FileTemplateConfig fileTemplateConfig) {
        if (generatorFileConfig == null) {
            return;
        }
        PsiPackage finalSrcPackage = generatorFileConfig.getPsiPackage();
        Module finalTestModule = generatorFileConfig.getTestModule();
        Module finalSrcModule = generatorFileConfig.getSrcModule();
        PsiClass finalSrcClass = generatorFileConfig.getSrcClass();
        FileTemplateContext context = (FileTemplateContext) ApplicationManager.getApplication().runReadAction(() -> {
            VirtualFile projectFile = LocalFileSystem.getInstance().findFileByPath(project2.getBasePath());
            PsiDirectoryFactory directoryFactory = PsiDirectoryFactory.getInstance(project2);
            PsiDirectory targetDirectory = directoryFactory.createDirectory(projectFile);
            generatorFileConfig.setTargetDirectory(targetDirectory);
            String className = finalSrcClass.getName() + "Test";
            TemplateDescriptor templateDescriptor = new TemplateRegistry().getEnabledTemplateDescriptor(generatorFileConfig.getFramework(), generatorFileConfig.getMock());
            AICodeSettingsState.getInstance().generateUnitTestFile = finalSrcClass.getName() + "." + FileUtilRt.getExtension(templateDescriptor.getFilename());
            FileTemplateContext context1 = new FileTemplateContext(new FileTemplateDescriptor(templateDescriptor.getFilename()), project2, className, finalSrcPackage, finalSrcModule, finalTestModule, targetDirectory, finalSrcClass, fileTemplateConfig, this.generatorConfig.getMethods(), this.generatorConfig.getExcludeMethodList(), generatorFileConfig.getRequestAi());
            context1.setFilePath(generatorFileConfig.getSrcClass().getContainingFile().getVirtualFile().getCanonicalPath());
            return context1;
        });
        FileTemplateManager fileTemplateManager = UnitTemplateManager.getInstance(project2);
        Map<String, Object> paramMaps = testTemplateContextBuilder.build(null, context, fileTemplateManager.getDefaultProperties());
        CacheFileTemplate cacheFileTemplate = new CacheFileTemplate();
        cacheFileTemplate.setContext(context);
        cacheFileTemplate.setParamMaps(paramMaps);
        cacheFileTemplate.setGeneratorFileConfig(generatorFileConfig);
        cacheFileTemplate.setMethodGeneratorConfig(this.generatorConfig);
        cacheFileTemplateMap.put(context.getFilePath(), cacheFileTemplate);
        JsonObject receiveFunction = UnitTestService.receiveFunction(this.generatorConfig.getUnitTestDto());
        SocketMessageHandleListener.send2Web(project2, receiveFunction);
    }

    private void writeTestClass(Project project2, final CacheFileTemplate cacheFileTemplate, FileTemplateConfig fileTemplateConfig) {
        try {
            GeneratorFileConfig generatorFileConfig = cacheFileTemplate.getGeneratorFileConfig();
            if (generatorFileConfig == null) {
                return;
            }
            if ((generatorFileConfig.getPsiFile() instanceof PsiJavaFile) && generatorFileConfig.getPsiPackage() != null) {
                final PsiDirectory targetDirectory = generatorFileConfig.getTargetDirectory();
                final TemplateDescriptor templateDescriptor = new TemplateRegistry().getEnabledTemplateDescriptor(generatorFileConfig.getFramework(), generatorFileConfig.getMock());
                FileTemplateContext context = cacheFileTemplate.getContext();
                final String className = context.getTargetClass();
                AICodeSettingsState.getInstance().generateUnitTestFile = context.getTargetClass() + "." + FileUtilRt.getExtension(templateDescriptor.getFilename());
                if (cacheFileTemplate.getParamMaps() != null && cacheFileTemplate.getParamMaps().containsKey("TESTED_CLASS")) {
                    testTemplateContextBuilder.resolveMethodCallByCaseResult((Type) cacheFileTemplate.getParamMaps().get("TESTED_CLASS"));
                    final FileTemplateManager fileTemplateManager = UnitTemplateManager.getInstance(cacheFileTemplate.getContext().getProject());
                    final String templateName = context.getFileTemplateDescriptor().getFileName();
                } else {
                    LOG.warn("未解析成功或不支持类型" + context.getTargetClass());
                }
            }
        } catch (Exception e) {
            LOG.warn("生成文件失败", e);
            isCanceled.set(true);
            UnitTestService.sendUnitTestErrInfo(project2, WebViewDataTypeEnum.UNIT_TEST_FUNCTION_CASE_CODE, BasicActionsBundle.message("unit.test.method.generate.code.error.text", new Object[0]), this.generatorConfig.getUnitTestDto().getId());
        }
    }

    private PsiElement writeTestFile(FileTemplateManager fileTemplateManager, String templateName, FileTemplateContext context, Map<String, Object> templateCtxtParams, PsiDirectory targetDirectory) {
        try {
            try {
                FileTemplate codeTemplate = fileTemplateManager.getInternalTemplate(templateName);
                codeTemplate.setReformatCode(true);
                Velocity.setProperty("velocimacro.max.depth", 200);
                long startGeneration = new Date().getTime();
                VelocityInitializer.verifyRuntimeSetup();
                PsiFile resolveEmbeddedClass = resolveEmbeddedClass(TestFileTemplateUtil.createFromTemplate(codeTemplate, context, templateCtxtParams, targetDirectory, null));
                LOG.info("Done generating PsiElement from template " + codeTemplate.getName() + " in " + (new Date().getTime() - startGeneration) + " millis");
                long startReformating = new Date().getTime();
                PsiFile psiFile = resolveEmbeddedClass instanceof PsiFile ? resolveEmbeddedClass : resolveEmbeddedClass.getContainingFile();
                ApplicationManager.getApplication().invokeLater(() -> {
                    VirtualFile file = psiFile.getVirtualFile();
                    if (file != null) {
                        FileEditorManager.getInstance(project).openFile(file, true);
                    }
                    WriteCommandAction.runWriteCommandAction(project, () -> {
                        JavaCodeStyleManager codeStyleManager = JavaCodeStyleManager.getInstance(targetDirectory.getProject());
                        codeStyleManager.optimizeImports(psiFile);
                        codeStyleManager.shortenClassReferences(psiFile);
                        codeRefactorUtil.uncommentImports(psiFile, context.getProject());
                        PsiFile reformat = CodeStyleManager.getInstance(context.getProject()).reformat(psiFile);
                        if (reformat instanceof PsiFile) {
                            this.generatorConfig.setPsiFile(reformat);
                            UnitTestService.handleUnitTestBankData(project, this.generatorConfig);
                        }
                        LOG.info("Done reformatting generated PsiClass in " + (new Date().getTime() - startReformating) + " millis");
                    });
                });
                AICodeSettingsState.getInstance().generateUnitTestFile = "";
                return psiFile;
            } catch (Exception e) {
                LOG.warn("模板生成失败", e);
                AICodeSettingsState.getInstance().generateUnitTestFile = "";
                return null;
            }
        } catch (Throwable th) {
            AICodeSettingsState.getInstance().generateUnitTestFile = "";
            throw th;
        }
    }

    private static PsiElement resolveEmbeddedClass(PsiElement psiElement) {
        PsiElement resolveEmbeddedClass = resolveEmbeddedClassRecursive(psiElement, 2);
        if (resolveEmbeddedClass == null) {
            return psiElement;
        }
        return resolveEmbeddedClass;
    }

    @Nullable
    private static PsiElement resolveEmbeddedClassRecursive(PsiElement psiElement, int recursionLevel) {
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
}
