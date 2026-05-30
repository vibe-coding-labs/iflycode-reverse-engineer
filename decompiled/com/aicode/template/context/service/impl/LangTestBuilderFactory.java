package com.aicode.template.context.service.impl;

import com.aicode.template.FileTemplateConfig;
import com.aicode.template.TypeDictionary;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.service.LangTestBuilder;
import com.aicode.template.context.service.TestBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.util.lang.JavaVersion;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/service/impl/LangTestBuilderFactory.class */
public class LangTestBuilderFactory {
    private final FileTemplateConfig fileTemplateConfig;
    private final Module srcModule;
    private final TypeDictionary typeDictionary;

    @Nullable
    private final JavaVersion javaVersion;
    private Integer renderType = 0;

    public LangTestBuilderFactory(Module srcModule, FileTemplateConfig fileTemplateConfig, TypeDictionary typeDictionary, @Nullable JavaVersion javaVersion) {
        this.fileTemplateConfig = fileTemplateConfig;
        this.srcModule = srcModule;
        this.typeDictionary = typeDictionary;
        this.javaVersion = javaVersion;
    }

    public void setRenderType(Integer renderType) {
        this.renderType = renderType;
    }

    @NotNull
    public LangTestBuilder createTestBuilder(Method method, TestBuilder.ParamRole paramRole, Map<String, String> defaultTypeValues, Map<String, String> typesOverrides, Integer overRenderType) {
        Integer render = overRenderType == null ? this.renderType : overRenderType;
        return new JavaTestBuilderImpl(method, paramRole, this.fileTemplateConfig, this.srcModule, this.typeDictionary, this.javaVersion, defaultTypeValues, typesOverrides, render.intValue());
    }
}
