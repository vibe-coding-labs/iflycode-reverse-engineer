package com.aicode.template;

import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.action.batch.GeneratorConfig;
import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.service.CommonService;
import com.aicode.message.BasicActionsBundle;
import com.aicode.template.generator.CreateTestFileTask;
import com.aicode.template.generator.CreateTestMethodTask;
import com.aicode.test.UnitTestService;
import com.aicode.test.dto.UnitTestDto;
import com.aicode.util.PropertyUtils;
import com.aicode.util.StringUtils;
import com.aicode.util.TypeUtils;
import com.google.gson.JsonObject;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/TemplateGenerator.class */
public class TemplateGenerator {
    private static final Logger LOG = Logger.getInstance(TemplateGenerator.class.getName());
    public static final TemplateGenerator INSTANCE = new TemplateGenerator();
    public CreateTestFileTask batchCreateTestFileTask;
    private CreateTestMethodTask createTestMethodTaskTask;

    @Nullable
    public List<String> batchTestClass(Project project, List<VirtualFile> javaFiles, PsiManager psiManager, GeneratorConfig generatorConfig) {
        List<String> createFiles = new ArrayList<>();
        PluginStartupActivity.handleExecutorService.execute(() -> {
            ProgressManager.getInstance().run(new CreateTestFileTask(project, "生成测试文件", javaFiles, generatorConfig));
        });
        return createFiles;
    }

    @Nullable
    public static void batchTestClass(Project project, GeneratorConfig generatorConfig) {
        if ((INSTANCE.batchCreateTestFileTask != null && !INSTANCE.batchCreateTestFileTask.isCanceled().booleanValue()) || (INSTANCE.createTestMethodTaskTask != null && !INSTANCE.createTestMethodTaskTask.isCanceled().booleanValue())) {
            LOG.warn("上一个单元测试任务还未完成");
            CommonService.messageBus(project, BasicActionsBundle.message("config.batch.unit.test.task.error", new Object[0]), MessageType.WARNING);
        } else {
            CreateTestFileTask createTestFileTask = new CreateTestFileTask(project, generatorConfig);
            INSTANCE.batchCreateTestFileTask = createTestFileTask;
            ProgressManager.getInstance().run(createTestFileTask);
        }
    }

    private List<VirtualFile> convertToFile(List<String> absolutePaths) {
        List<VirtualFile> virtualFiles = new ArrayList<>();
        for (String absolutePath : absolutePaths) {
            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(absolutePath);
            if (virtualFile != null) {
                virtualFiles.add(virtualFile);
            }
        }
        return virtualFiles;
    }

    @Nullable
    public static void createTestClass(Project project, MethodGeneratorConfig generatorConfig) {
        if (INSTANCE.batchCreateTestFileTask != null && !INSTANCE.batchCreateTestFileTask.isCanceled().booleanValue()) {
            LOG.warn("上一个单元测试任务还未完成");
            UnitTestDto.DataDTO unitTestDto = generatorConfig.getUnitTestDto();
            unitTestDto.setReason("error");
            unitTestDto.setMessage(BasicActionsBundle.message("config.batch.unit.test.task.error", new Object[0]));
            JsonObject receiveFunction = UnitTestService.receiveFunction(unitTestDto);
            SocketMessageHandleListener.send2Web(project, receiveFunction);
            return;
        }
        Set<String> canNotWriteMethod = new HashSet<>();
        Set<String> checkedWriteMethod = new HashSet<>();
        Set<PsiMethod> ignoreMethods = new HashSet<>();
        ApplicationManager.getApplication().runReadAction(() -> {
            for (PsiMethod method : generatorConfig.getMethods()) {
                boolean isAbstract = method.hasModifierProperty("abstract");
                boolean isNative = method.hasModifierProperty("native");
                if (method.isConstructor()) {
                    canNotWriteMethod.add(method.getName());
                    ignoreMethods.add(method);
                }
                if (isAbstract || isNative) {
                    canNotWriteMethod.add(method.getName());
                    ignoreMethods.add(method);
                }
                if (generatorConfig.getExcludeMethodList().contains(ExcludeMethodEnum.SETTER.getName())) {
                    boolean isSetter = PropertyUtils.isPropertySetter(method);
                    if (isSetter) {
                        checkedWriteMethod.add(method.getName());
                        ignoreMethods.add(method);
                    }
                }
                if (generatorConfig.getExcludeMethodList().contains(ExcludeMethodEnum.GETTER.getName())) {
                    boolean isGetter = PropertyUtils.isPropertyGetter(method);
                    if (isGetter) {
                        checkedWriteMethod.add(method.getName());
                        ignoreMethods.add(method);
                    }
                }
                if (generatorConfig.getExcludeMethodList().contains(ExcludeMethodEnum.MAIN.getName())) {
                    boolean isMain = PropertyUtils.isMainMethod(method);
                    if (isMain) {
                        checkedWriteMethod.add(method.getName());
                        ignoreMethods.add(method);
                    }
                }
                if (generatorConfig.getExcludeMethodList().contains(method.getName())) {
                    checkedWriteMethod.add(method.getName());
                    ignoreMethods.add(method);
                }
                if (isTestOfMethod(method)) {
                    canNotWriteMethod.add(method.getName());
                    ignoreMethods.add(method);
                }
            }
        });
        ApplicationManager.getApplication().invokeLater(() -> {
            String message = "";
            if (CollectionUtils.isNotEmpty(canNotWriteMethod)) {
                message = BasicActionsBundle.message("unit.test.method.generate.skip.message", BasicActionsBundle.message("aicode.plugin.title", new Object[0]), "，是否跳过？");
            } else if (CollectionUtils.isNotEmpty(checkedWriteMethod)) {
                message = BasicActionsBundle.message("unit.test.method.generate.skip.message", BasicActionsBundle.message("aicode.plugin.title", new Object[0]), "");
            }
            if (StringUtils.isNotBlank(message)) {
                if (CollectionUtils.isNotEmpty(checkedWriteMethod)) {
                    canNotWriteMethod.addAll(checkedWriteMethod);
                } else {
                    canNotWriteMethod.addAll(checkedWriteMethod);
                }
                UnitTestDto.DataDTO dataDTO = new UnitTestDto.DataDTO();
                dataDTO.setId(IdUtil.fastSimpleUUID());
                dataDTO.setReason("error");
                dataDTO.setMessage(BasicActionsBundle.message("config.batch.unit.test.create.single.error.ignore", new Object[0]));
                JsonObject receiveFunction2 = UnitTestService.receiveFunction(dataDTO);
                SocketMessageHandleListener.send2Web(project, receiveFunction2);
                INSTANCE.createTestMethodTaskTask = null;
                return;
            }
            if (-1 > -1) {
                LOG.debug(String.valueOf(-1));
                if (-1 == 2) {
                    List<String> excludeMethodList = generatorConfig.getExcludeMethodList();
                    Objects.requireNonNull(canNotWriteMethod);
                    excludeMethodList.removeIf((v1) -> {
                        return r1.contains(v1);
                    });
                    return;
                } else if (-1 == 1) {
                    List<PsiMethod> methods = generatorConfig.getMethods();
                    Objects.requireNonNull(ignoreMethods);
                    methods.removeIf((v1) -> {
                        return r1.contains(v1);
                    });
                    return;
                } else {
                    List<PsiMethod> methods2 = generatorConfig.getMethods();
                    Objects.requireNonNull(ignoreMethods);
                    methods2.removeIf((v1) -> {
                        return r1.contains(v1);
                    });
                    return;
                }
            }
            CreateTestMethodTask createTestMethodTaskTask = new CreateTestMethodTask(project, generatorConfig);
            INSTANCE.createTestMethodTaskTask = createTestMethodTaskTask;
            ProgressManager.getInstance().run(createTestMethodTaskTask);
        });
    }

    private static boolean isTestOfMethod(PsiMethod psiMethod) {
        if (psiMethod == null) {
            return true;
        }
        return Arrays.stream(psiMethod.getAnnotations()).anyMatch(ann -> {
            boolean contains;
            contains = TypeUtils.f741final.contains(ann.getQualifiedName());
            return contains;
        });
    }
}
