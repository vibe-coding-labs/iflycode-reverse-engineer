package com.aicode.template.context.service;

import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.Param;
import com.aicode.template.context.domain.Type;
import com.aicode.template.request.dto.CaseResult;
import java.util.List;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/service/LangTestBuilder.class */
public interface LangTestBuilder {
    public static final String PARAMS_SEPARATOR = ", ";

    String renderJavaCallParams(List<Param> list, CaseResult caseResult);

    String renderJavaCallParam(Type type, String str, CaseResult caseResult);

    String renderJavaVariable(Type type, String str, CaseResult caseResult);

    String renderJavaMethodCaseBody(Type type, Method method);

    String renderJavaMethodAssert(Method method, CaseResult caseResult, String str);
}
