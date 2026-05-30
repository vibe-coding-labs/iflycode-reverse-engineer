package com.aicode.template.context.resolved;

import com.aicode.util.PsiUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;
import java.util.List;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/resolved/ResolvedMethodCall.class */
public class ResolvedMethodCall {
    private final PsiMethod psiMethod;
    private final PsiClass psiClass;
    private final List<MethodCallArg> methodCallArguments;
    private final String methodId;
    private final PsiType returnType;
    private final String returnParamName;
    private final Integer lineNumber;

    public ResolvedMethodCall(PsiMethod psiMethod, List<MethodCallArg> methodCallArguments, PsiType returnType1, Integer lineNumber, String returnParam) {
        String formatMethodId;
        this.psiMethod = psiMethod;
        this.methodCallArguments = methodCallArguments;
        this.psiClass = psiMethod.getContainingClass();
        formatMethodId = PsiUtils.formatMethodId(psiMethod.getContainingClass(), psiMethod.getName(), psiMethod.getParameterList().getParameters());
        this.methodId = formatMethodId;
        this.lineNumber = lineNumber;
        this.returnType = returnType1 == null ? psiMethod.getReturnType() : returnType1;
        this.returnParamName = returnParam;
    }

    public ResolvedMethodCall(PsiMethod psiMethod, List<MethodCallArg> methodCallArguments, String methodId, PsiClass psiClass, PsiType returnType, Integer lineNumber, String returnParam) {
        this.psiMethod = psiMethod;
        this.methodCallArguments = methodCallArguments;
        this.psiClass = psiClass;
        this.methodId = methodId;
        this.returnType = returnType;
        this.lineNumber = lineNumber;
        this.returnParamName = returnParam;
    }

    public PsiMethod getPsiMethod() {
        return this.psiMethod;
    }

    public List<MethodCallArg> getMethodCallArguments() {
        return this.methodCallArguments;
    }

    public String getMethodId() {
        return this.methodId;
    }

    public PsiClass getPsiClass() {
        return this.psiClass;
    }

    public PsiType getReturnType() {
        return this.returnType;
    }

    public String getReturnParamName() {
        return this.returnParamName;
    }

    public String toString() {
        return "ResolvedMethodCall{methodId='" + this.methodId + "'}";
    }
}
