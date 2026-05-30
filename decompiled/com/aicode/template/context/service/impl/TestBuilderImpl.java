package com.aicode.template.context.service.impl;

import cn.hutool.json.JSONArray;
import com.aicode.message.BasicActionsBundle;
import com.aicode.template.FileTemplateConfig;
import com.aicode.template.TypeDictionary;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.MethodCall;
import com.aicode.template.context.domain.Type;
import com.aicode.template.context.resolved.ResolveComponents;
import com.aicode.template.context.resolved.ResolveVarible;
import com.aicode.template.context.service.TestBuilder;
import com.aicode.template.request.DataUtils;
import com.aicode.template.request.dto.CaseParam;
import com.aicode.template.request.dto.CaseResult;
import com.aicode.util.StringUtils;
import com.aicode.util.TypeUtils;
import com.intellij.openapi.module.Module;
import com.intellij.util.lang.JavaVersion;
import java.util.Map;
import java.util.Optional;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/service/impl/TestBuilderImpl.class */
public class TestBuilderImpl implements TestBuilder {
    private final LangTestBuilderFactory langTestBuilderFactory;

    public TestBuilderImpl(Module srcModule, TypeDictionary typeDictionary, FileTemplateConfig fileTemplateConfig, JavaVersion javaVersion) {
        this.langTestBuilderFactory = new LangTestBuilderFactory(srcModule, fileTemplateConfig, typeDictionary, javaVersion);
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String buildPrameterizedTestComponentsString(Method method, Map<String, String> replacementTypesForReturn, Map<String, String> replacementTypes, Map<String, String> defaultTypeValues) throws Exception {
        return null;
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderMethodParams(Method method, Map<String, String> typesOverrides, Map<String, String> defaultTypeValues) throws Exception {
        return this.langTestBuilderFactory.createTestBuilder(method, TestBuilder.ParamRole.Input, defaultTypeValues, typesOverrides, 1).renderJavaCallParams(method.getMethodParams(), method.getCaseResult());
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderMethodParamsWithCase(Method method, Map<String, String> typesOverrides, Map<String, String> defaultTypeValues, CaseResult caseResult) throws Exception {
        try {
            return this.langTestBuilderFactory.createTestBuilder(method, TestBuilder.ParamRole.Input, defaultTypeValues, typesOverrides, 1).renderJavaCallParams(method.getMethodParams(), caseResult);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderReturnParam(Method testedMethod, Method calledMethod, String defaultName, Map<String, String> typesOverrides, Map<String, String> defaultTypeValues) throws Exception {
        TestBuilder.ParamRole paramRole = TestBuilder.ParamRole.Output;
        int renderType = 2;
        String calledMethodId = calledMethod.getMethodId();
        if (!testedMethod.getMethodId().equals(calledMethod.getMethodId())) {
            Optional<MethodCall> optional = testedMethod.getMethodCalls().stream().filter(methodCall -> {
                return methodCall.getMethod().getMethodId().equals(calledMethodId);
            }).findFirst();
            if (optional.isPresent()) {
                defaultName = optional.get().getVariableName();
                Optional<ResolveVarible> optional1 = testedMethod.getResolveComponents().getRendered(calledMethod.getReturnType(), 2, defaultName);
                if (optional1.isPresent()) {
                    renderType = optional1.get().getVaribleType();
                }
            }
            paramRole = TestBuilder.ParamRole.Mock;
        }
        return this.langTestBuilderFactory.createTestBuilder(testedMethod, paramRole, defaultTypeValues, typesOverrides, Integer.valueOf(renderType)).renderJavaCallParam(calledMethod.getReturnType(), defaultName, testedMethod.getCaseResult());
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderReturnParamAndMockito(Method testedMethod, Method calledMethod, Type testedClass, String defaultName, Map<String, String> typesOverrides, Map<String, String> defaultTypeValues) throws Exception {
        TestBuilder.ParamRole paramRole = TestBuilder.ParamRole.Output;
        int renderType = 0;
        String calledMethodId = calledMethod.getMethodId();
        if (!testedMethod.getMethodId().equals(calledMethod.getMethodId())) {
            Optional<MethodCall> optional = testedMethod.getMethodCalls().stream().filter(methodCall -> {
                return methodCall.getMethod().getMethodId().equals(calledMethodId);
            }).findFirst();
            if (optional.isPresent()) {
                defaultName = optional.get().getVariableName();
                Optional<ResolveVarible> optional1 = testedMethod.getResolveComponents().getRendered(calledMethod.getReturnType(), 0, defaultName);
                if (optional1.isPresent()) {
                    renderType = optional1.get().getVaribleType();
                }
            }
            paramRole = TestBuilder.ParamRole.Mock;
        }
        String fieldName = StringUtils.deCapitalizeFirstLetter(calledMethod.getReturnType().getName());
        if (!TypeUtils.isBasicType(calledMethod.getReturnType()) && !TypeUtils.isDateType(calledMethod.getReturnType()) && TypeUtils.isMockable(calledMethod.getReturnType()) && testedClass.getFields().stream().anyMatch(item -> {
            return item.getName().equals(fieldName) && item.isNotInBuilder() && item.getType().equals(calledMethod.getReturnType());
        })) {
            return StringUtils.deCapitalizeFirstLetter(calledMethod.getReturnType().getName());
        }
        return this.langTestBuilderFactory.createTestBuilder(testedMethod, paramRole, defaultTypeValues, typesOverrides, Integer.valueOf(renderType)).renderJavaCallParam(calledMethod.getReturnType(), defaultName, testedMethod.getCaseResult());
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderReturnParamToMock(Method testedMethod, Type type, String defaultName, Map<String, String> typesOverrides, Map<String, String> defaultTypeValues) throws Exception {
        return this.langTestBuilderFactory.createTestBuilder(testedMethod, TestBuilder.ParamRole.Output, defaultTypeValues, typesOverrides, 0).renderJavaCallParam(type, defaultName, testedMethod.getCaseResult());
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderReturnParamWithData(Method testedMethod, Type type, String defaultName, Map<String, String> typesOverrides, Map<String, String> defaultTypeValues, CaseResult caseResult) throws Exception {
        return this.langTestBuilderFactory.createTestBuilder(testedMethod, TestBuilder.ParamRole.Output, defaultTypeValues, typesOverrides, 2).renderJavaCallParam(type, defaultName, caseResult);
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderVariableWithData(Method testedMethod, ResolveVarible resolveVarible, Map<String, String> replacementTypes, Map<String, String> defaultTypeValues, CaseResult caseResult) throws Exception {
        CaseParam output;
        if (resolveVarible == null) {
            return "";
        }
        if (resolveVarible.getVaribleType() != 1 && caseResult != null && (output = caseResult.getOutput()) != null) {
            Object outputData = output.getData();
            if (outputData == null) {
                return "";
            }
            String outputType = output.getType();
            if ("ARRAY".equals(outputType)) {
                JSONArray data = (JSONArray) outputData;
                if (data.isEmpty()) {
                    return "";
                }
            }
        }
        return this.langTestBuilderFactory.createTestBuilder(testedMethod, resolveVarible.getVaribleType() == 1 ? TestBuilder.ParamRole.Input : TestBuilder.ParamRole.Output, defaultTypeValues, replacementTypes, Integer.valueOf(resolveVarible.getVaribleType())).renderJavaVariable(resolveVarible.getResolveType(), resolveVarible.getName(), caseResult);
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String resetVariable(Method method, CaseResult caseResult) throws Exception {
        if (method != null) {
            method.getResolveComponents().getHasRenders().clear();
            method.getResolveComponents().getInput().clear();
            method.getResolveComponents().getOutput().clear();
            method.getResolveComponents().getMockData().clear();
            ResolveComponents.reset(method, caseResult);
            return "";
        }
        return "";
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderCaseBranches(Method testedMethod, CaseResult caseResult) throws Exception {
        if (DataUtils.isEmptyData(caseResult)) {
            return "";
        }
        return caseResult.toCommitBranchText();
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderMockReturnParamWithData(Method testedMethod, Type type, String defaultName, Map<String, String> typesOverrides, Map<String, String> defaultTypeValues, CaseResult caseResult) throws Exception {
        return this.langTestBuilderFactory.createTestBuilder(testedMethod, TestBuilder.ParamRole.Output, defaultTypeValues, typesOverrides, 0).renderJavaCallParam(type, defaultName, caseResult);
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderInitType(Type type, String defaultName, Map<String, String> typesOverrides, Map<String, String> defaultTypeValues) throws Exception {
        return this.langTestBuilderFactory.createTestBuilder(null, TestBuilder.ParamRole.Input, defaultTypeValues, typesOverrides, -1).renderJavaCallParam(type, defaultName, null);
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderInitTypeValue(Type type, String defaultName, Map<String, String> typesOverrides, Map<String, String> defaultTypeValues) throws Exception {
        return this.langTestBuilderFactory.createTestBuilder(null, TestBuilder.ParamRole.Input, defaultTypeValues, typesOverrides, -1).renderJavaCallParam(type, defaultName, null);
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderMethodCaseBody(Method testedMethod, Type testedClass, Map<String, String> replacementTypes, Map<String, String> defaultTypeValues) throws Exception {
        return this.langTestBuilderFactory.createTestBuilder(testedMethod, TestBuilder.ParamRole.Output, defaultTypeValues, replacementTypes, 2).renderJavaMethodCaseBody(testedClass, testedMethod);
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderJavaMethodAssert(Method method, CaseResult caseResult, Map<String, String> replacementTypes, Map<String, String> defaultTypeValues, String template) throws Exception {
        return this.langTestBuilderFactory.createTestBuilder(method, TestBuilder.ParamRole.Output, defaultTypeValues, replacementTypes, 2).renderJavaMethodAssert(method, caseResult, template);
    }

    @Override // com.aicode.template.context.service.TestBuilder
    public String renderDocComment() {
        String pluginTitle = BasicActionsBundle.message("aicode.plugin.title", new Object[0]);
        return "/** 由" + pluginTitle + "创建 */";
    }
}
