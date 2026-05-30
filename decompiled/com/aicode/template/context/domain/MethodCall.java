package com.aicode.template.context.domain;

import java.util.List;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/MethodCall.class */
public class MethodCall {
    private final String variableName;
    private final Method method;
    private final List<MethodCallArgument> methodCallArguments;

    public MethodCall(Method method, String variableName, List<MethodCallArgument> methodCallArguments) {
        this.method = method;
        this.variableName = variableName;
        this.methodCallArguments = methodCallArguments;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MethodCall)) {
            return false;
        }
        MethodCall that = (MethodCall) o;
        if (that.getMethod() != null && this.method != null && !that.method.getMethodId().equals(this.method.getMethodId())) {
            return false;
        }
        if (this.method != null) {
            if (!this.method.equals(that.method)) {
                return false;
            }
        } else if (that.method != null) {
            return false;
        }
        if (this.variableName != null) {
            if (this.variableName.equals(that.variableName)) {
                return false;
            }
        } else if (that.variableName != null) {
            return false;
        }
        return this.methodCallArguments != null ? this.methodCallArguments.equals(that.methodCallArguments) : that.methodCallArguments == null;
    }

    public int hashCode() {
        int result = this.method != null ? this.method.hashCode() : 0;
        return (31 * result) + (this.methodCallArguments != null ? this.methodCallArguments.hashCode() : 0);
    }

    public Method getMethod() {
        return this.method;
    }

    public List<MethodCallArgument> getMethodCallArguments() {
        return this.methodCallArguments;
    }

    public String getVariableName() {
        return this.variableName;
    }
}
