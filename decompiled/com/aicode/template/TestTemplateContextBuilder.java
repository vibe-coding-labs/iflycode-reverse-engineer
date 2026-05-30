package com.aicode.template;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.aicode.template.builder.MethodReferencesBuilder;
import com.aicode.template.builder.MockBuilderFactory;
import com.aicode.template.context.domain.Type;
import com.aicode.template.fileloader.FileTemplateContext;
import com.aicode.template.generator.GeneratorTemplateConfig;
import com.aicode.test.dto.UnitTestDto;
import com.aicode.util.StringUtils;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.impl.scopes.ModuleWithDependenciesScope;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.psi.PsiClass;
import com.intellij.util.lang.JavaVersion;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/TestTemplateContextBuilder.class */
public class TestTemplateContextBuilder {
    private final MockBuilderFactory mockBuilderFactory;
    private final MethodReferencesBuilder methodReferencesBuilder;
    private final Cache<String, Type> typeCache = CacheUtil.newLRUCache(80000);
    private final Cache<String, TypeDictionary> typeDictionaryCache = CacheUtil.newLRUCache(1000);
    private final GeneratorTemplateConfig templateConfig;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "com/aicode/template/TestTemplateContextBuilder";
        switch (i) {
            case 0:
            case 1:
            default:
                objArr[1] = "resolveClasspathJars";
                break;
            case 2:
                objArr[1] = "initTemplateContext";
                break;
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    public TestTemplateContextBuilder(MockBuilderFactory mockBuilderFactory, MethodReferencesBuilder methodReferencesBuilder, GeneratorTemplateConfig templateConfig) {
        this.mockBuilderFactory = mockBuilderFactory;
        this.methodReferencesBuilder = methodReferencesBuilder;
        this.templateConfig = templateConfig;
    }

    public Map<String, Object> build(String requestId, FileTemplateContext context, Properties defaultProperties) {
        System.currentTimeMillis();
        HashMap<String, Object> ctxtParams = initTemplateContext(defaultProperties);
        populateDateFields(ctxtParams, Calendar.getInstance());
        ctxtParams.put("CLASS_NAME", context.getTargetClass());
        ctxtParams.put("TAB", "    ");
        ctxtParams.put("PACKAGE_NAME", context.getTargetPackage().getQualifiedName());
        int maxRecursionDepth = context.getFileTemplateConfig().getMaxRecursionDepth();
        ctxtParams.put("MAX_RECURSION_DEPTH", Integer.valueOf(maxRecursionDepth));
        ctxtParams.put("StringUtils", new StringUtils());
        PsiClass srcClass = context.getSrcClass();
        UnitTestDto.DataDTO dataDTO = this.templateConfig.getUnitTestDto();
        if (StringUtils.isBlank(requestId)) {
            dataDTO.setLanguage(srcClass.getLanguage().getID());
        }
        return ctxtParams;
    }

    public void resolveMethodCallByCaseResult(Type type) {
        this.methodReferencesBuilder.resolveMethodCallByCaseResult(type);
    }

    public void clearCache() {
        this.typeCache.clear();
        this.typeDictionaryCache.clear();
    }

    @NotNull
    private List<String> resolveClasspathJars(FileTemplateContext context) {
        ModuleWithDependenciesScope moduleWithDependenciesAndLibrariesScope = context.getTestModule().getModuleWithDependenciesAndLibrariesScope(true);
        if (moduleWithDependenciesAndLibrariesScope instanceof ModuleWithDependenciesScope) {
            ModuleWithDependenciesScope moduleWithDependenciesScope = moduleWithDependenciesAndLibrariesScope;
            List<String> list = (List) moduleWithDependenciesScope.getRoots().stream().map((v0) -> {
                return v0.getName();
            }).filter(name -> {
                return name.endsWith(".jar");
            }).collect(Collectors.toList());
            if (list == null) {
                $$$reportNull$$$0(0);
            }
            return list;
        }
        List<String> of = List.of();
        if (of == null) {
            $$$reportNull$$$0(1);
        }
        return of;
    }

    @Nullable
    private JavaVersion getJavaVersion(Module testModule) {
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(testModule);
        Sdk sdk = moduleRootManager.getSdk();
        if (sdk != null && sdk.getSdkType().getName().toLowerCase().contains("java")) {
            return JavaVersion.tryParse(sdk.getVersionString());
        }
        return null;
    }

    void populateDateFields(Map<String, Object> ctxtParams, Calendar calendar) {
        ctxtParams.put("MONTH_NAME_EN", new SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.getTime()));
        ctxtParams.put("DAY_NUMERIC", Integer.valueOf(calendar.get(5)));
        ctxtParams.put("HOUR_NUMERIC", Integer.valueOf(calendar.get(11)));
        ctxtParams.put("MINUTE_NUMERIC", Integer.valueOf(calendar.get(12)));
        ctxtParams.put("SECOND_NUMERIC", Integer.valueOf(calendar.get(13)));
    }

    @NotNull
    private HashMap<String, Object> initTemplateContext(Properties defaultProperties) {
        HashMap<String, Object> templateCtxtParams = new HashMap<>();
        for (Map.Entry<Object, Object> entry : defaultProperties.entrySet()) {
            templateCtxtParams.put((String) entry.getKey(), entry.getValue());
        }
        if (templateCtxtParams == null) {
            $$$reportNull$$$0(2);
        }
        return templateCtxtParams;
    }
}
