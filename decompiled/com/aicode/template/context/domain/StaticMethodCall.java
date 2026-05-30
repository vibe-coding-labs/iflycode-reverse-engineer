package com.aicode.template.context.domain;

import java.util.List;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/StaticMethodCall.class */
public class StaticMethodCall extends MethodCall {
    private String ownerClass;

    public StaticMethodCall(String ownerClass, String variableName, Method method, List<MethodCallArgument> methodCallArguments) {
        super(method, variableName, methodCallArguments);
        this.ownerClass = ownerClass;
    }

    public String getOwnerClass() {
        return this.ownerClass;
    }

    @Override // com.aicode.template.context.domain.MethodCall
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StaticMethodCall)) {
            return false;
        }
        StaticMethodCall that = (StaticMethodCall) o;
        if (this.ownerClass != null && this.ownerClass.equals(that.getOwnerClass())) {
            if (that.getMethod() != null && getMethod() != null && !that.getMethod().getMethodId().equals(getMethod().getMethodId())) {
                return false;
            }
            if (getMethod() != null) {
                if (!getMethod().getMethodId().equals(that.getMethod().getMethodId())) {
                    return false;
                }
                return true;
            }
            if (that.getMethod() != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override // com.aicode.template.context.domain.MethodCall
    public int hashCode() {
        int result = getMethod() != null ? getMethod().hashCode() : 0;
        return (31 * result) + (getMethodCallArguments() != null ? getMethodCallArguments().hashCode() : 0);
    }
}
