package com.aicode.template.context.service;

import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.Type;
import com.aicode.template.context.resolved.ResolveVarible;
import com.aicode.template.request.dto.CaseResult;
import java.util.Map;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/service/TestBuilder.class */
public interface TestBuilder {
    public static final String RESULT_VARIABLE_NAME = "expectedResult";

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/service/TestBuilder$ParamRole.class */
    public enum ParamRole {
        Mock,
        Input,
        Output
    }

    String renderMethodParams(Method method, Map<String, String> map, Map<String, String> map2) throws Exception;

    String renderMethodParamsWithCase(Method method, Map<String, String> map, Map<String, String> map2, CaseResult caseResult) throws Exception;

    String buildPrameterizedTestComponentsString(Method method, Map<String, String> map, Map<String, String> map2, Map<String, String> map3) throws Exception;

    String renderReturnParam(Method method, Method method2, String str, Map<String, String> map, Map<String, String> map2) throws Exception;

    String renderReturnParamAndMockito(Method method, Method method2, Type type, String str, Map<String, String> map, Map<String, String> map2) throws Exception;

    String renderReturnParamToMock(Method method, Type type, String str, Map<String, String> map, Map<String, String> map2) throws Exception;

    String renderReturnParamWithData(Method method, Type type, String str, Map<String, String> map, Map<String, String> map2, CaseResult caseResult) throws Exception;

    String renderVariableWithData(Method method, ResolveVarible resolveVarible, Map<String, String> map, Map<String, String> map2, CaseResult caseResult) throws Exception;

    String resetVariable(Method method, CaseResult caseResult) throws Exception;

    String renderCaseBranches(Method method, CaseResult caseResult) throws Exception;

    String renderMockReturnParamWithData(Method method, Type type, String str, Map<String, String> map, Map<String, String> map2, CaseResult caseResult) throws Exception;

    String renderInitType(Type type, String str, Map<String, String> map, Map<String, String> map2) throws Exception;

    String renderInitTypeValue(Type type, String str, Map<String, String> map, Map<String, String> map2) throws Exception;

    String renderMethodCaseBody(Method method, Type type, Map<String, String> map, Map<String, String> map2) throws Exception;

    String renderJavaMethodAssert(Method method, CaseResult caseResult, Map<String, String> map, Map<String, String> map2, String str) throws Exception;

    String renderDocComment();
}
