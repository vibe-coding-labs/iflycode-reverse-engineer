package com.aicode.template.context.domain;

import com.aicode.template.context.resolved.ResolveComponents;
import com.aicode.template.context.resolved.ResolvedBranch;
import com.aicode.template.request.DataUtils;
import com.aicode.template.request.dto.CaseResult;
import com.aicode.util.ClassNameUtils;
import com.intellij.openapi.util.TextRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/Method.class */
public class Method {
    private final String methodId;
    private final String name;
    private final Type returnType;
    private final String ownerClassCanonicalType;
    private final List<Param> methodParams;
    private final List<String> callParams;
    private Set<String> exceptions;
    private String methodExceptionTypes;
    private final boolean isPrivate;
    private final boolean isProtected;
    private final boolean isDefault;
    private final boolean isPublic;
    private final boolean isAbstract;
    private final boolean isNative;
    private final boolean isStatic;
    private final boolean isSetter;
    private final boolean isGetter;
    private final boolean constructor;
    private final boolean overridden;
    private final boolean inherited;
    private final boolean isInInterface;
    private final boolean isSynthetic;
    private final String propertyName;
    private final boolean accessible;
    private final boolean primaryConstructor;
    private final boolean testable;
    private final Integer startOffset;
    private final Integer endOffset;
    private final Set<MethodCall> directMethodCalls = new HashSet();
    private final Set<MethodCall> methodCalls = new HashSet();
    private final ResolveComponents resolveComponents = new ResolveComponents();
    private final Set<StaticMethodCall> staticMethodCalls = new HashSet();
    private final Set<Method> methodReferences = new HashSet();
    private final Set<MethodCall> calledFamilyMembers = new HashSet();
    private ResolvedBranch caseBranchSet = null;
    private final Set<Reference> internalReferences = new HashSet();
    private final Set<Field> indirectlyAffectedFields = new HashSet();
    private final List<CaseResult> caseResults = new ArrayList();
    private final CaseResult caseResult = DataUtils.Empty;
    private final Set<String> reflectionMethods = new HashSet();

    public boolean hasReturn() {
        return (this.returnType == null || "void".equalsIgnoreCase(this.returnType.getName())) ? false : true;
    }

    public boolean hasParams() {
        return !this.methodParams.isEmpty();
    }

    public String toString() {
        return "Method{methodId='" + this.methodId + "'}";
    }

    public boolean methodEquals(Method to) {
        if (getMethodId().equals(to.getMethodId())) {
            if (hasReturn()) {
                return getReturnType().typeEquals(to.getReturnType());
            }
            return true;
        }
        if (getOwnerClassCanonicalType().equals(to.getOwnerClassCanonicalType()) && getMethodParams().size() == to.getMethodParams().size()) {
            String theOwnerClassCanonicalType = ClassNameUtils.extractClassNameFormMethodId(getMethodId()) + "#" + this.name;
            String toOwnerClassCanonicalType = ClassNameUtils.extractClassNameFormMethodId(to.getMethodId()) + "#" + to.getName();
            return theOwnerClassCanonicalType.equalsIgnoreCase(toOwnerClassCanonicalType) && getMethodParams().stream().allMatch(param -> {
                return to.getMethodParams().stream().anyMatch(param1 -> {
                    return param1.getName().equals(param.getName());
                });
            });
        }
        return false;
    }

    public Method(String methodId, String name, Type returnType, String ownerClassCanonicalType, List<Param> methodParams, String methodExceptionTypes, boolean isPrivate, boolean isProtected, boolean isDefault, boolean isPublic, boolean isAbstract, boolean isNative, boolean isStatic, boolean isSetter, boolean isGetter, boolean constructor, boolean overridden, boolean inherited, boolean isInInterface, boolean isSynthetic, String propertyName, boolean accessible, boolean primaryConstructor, boolean testable, List<String> callParams, TextRange textRange) {
        this.methodId = methodId;
        this.name = name;
        this.returnType = returnType;
        this.ownerClassCanonicalType = ownerClassCanonicalType;
        this.methodParams = methodParams;
        this.methodExceptionTypes = methodExceptionTypes;
        this.isPrivate = isPrivate;
        this.isProtected = isProtected;
        this.isDefault = isDefault;
        this.isPublic = isPublic;
        this.isAbstract = isAbstract;
        this.isNative = isNative;
        this.isStatic = isStatic;
        this.isSetter = isSetter;
        this.isGetter = isGetter;
        this.constructor = constructor;
        this.overridden = overridden;
        this.inherited = inherited;
        this.isInInterface = isInInterface;
        this.isSynthetic = isSynthetic;
        this.propertyName = propertyName;
        this.callParams = callParams;
        this.accessible = accessible;
        this.primaryConstructor = primaryConstructor;
        this.testable = testable;
        if (textRange != null) {
            this.startOffset = Integer.valueOf(textRange.getStartOffset());
            this.endOffset = Integer.valueOf(textRange.getEndOffset());
        } else {
            this.startOffset = 0;
            this.endOffset = 0;
        }
    }

    public List<CaseResult> getCaseResults() {
        return this.caseResults;
    }

    public String getMethodId() {
        return this.methodId;
    }

    public String getMethodIdAndType() {
        return this.methodId + "#" + this.returnType.getCanonicalName();
    }

    public String getName() {
        return this.name;
    }

    public Type getReturnType() {
        return this.returnType;
    }

    public String getOwnerClassCanonicalType() {
        return this.ownerClassCanonicalType;
    }

    public List<Param> getMethodParams() {
        return this.methodParams;
    }

    public String getMethodExceptionTypes() {
        return this.methodExceptionTypes;
    }

    public void resolveExceptions() {
        Function<MethodCall, String> mapFunc = methodCall -> {
            return methodCall.getMethod().getMethodExceptionTypes();
        };
        List<String> exceptions = (List) this.directMethodCalls.stream().map(mapFunc).collect(Collectors.toList());
        exceptions.addAll((Collection) this.staticMethodCalls.stream().map(mapFunc).collect(Collectors.toList()));
        appendMethodExceptionTypes(exceptions);
    }

    public String appendMethodExceptionTypes(List<String> appendExceptions) {
        List<String> exceptions = new ArrayList<>();
        append(exceptions, this.methodExceptionTypes);
        for (String appendException : appendExceptions) {
            append(exceptions, appendException);
        }
        this.methodExceptionTypes = exceptions.size() > 5 ? "Exception" : (String) exceptions.stream().distinct().collect(Collectors.joining(","));
        return this.methodExceptionTypes;
    }

    private void append(List<String> exceptions, String appends) {
        if (appends == null || appends.length() == 1) {
            return;
        }
        exceptions.addAll(Arrays.asList(appends.split(",")));
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public boolean isProtected() {
        return this.isProtected;
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public boolean isAbstract() {
        return this.isAbstract;
    }

    public boolean isNative() {
        return this.isNative;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public boolean isSetter() {
        return this.isSetter;
    }

    public boolean isGetter() {
        return this.isGetter;
    }

    public boolean isConstructor() {
        return this.constructor;
    }

    public boolean isOverridden() {
        return this.overridden;
    }

    public boolean isInherited() {
        return this.inherited;
    }

    public boolean isInInterface() {
        return this.isInInterface;
    }

    public boolean isSynthetic() {
        return this.isSynthetic;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public boolean isAccessible() {
        return this.accessible;
    }

    public boolean isPrimaryConstructor() {
        return this.primaryConstructor;
    }

    public boolean isTestable() {
        return this.testable;
    }

    public Set<MethodCall> getDirectMethodCalls() {
        return this.directMethodCalls;
    }

    public Set<MethodCall> getMethodCalls() {
        return this.methodCalls;
    }

    public ResolveComponents getResolveComponents() {
        return this.resolveComponents;
    }

    public Set<StaticMethodCall> getStaticMethodCalls() {
        return this.staticMethodCalls;
    }

    public Set<MethodCall> getCalledFamilyMembers() {
        return this.calledFamilyMembers;
    }

    public Set<Method> getMethodReferences() {
        return this.methodReferences;
    }

    public Set<Reference> getInternalReferences() {
        return this.internalReferences;
    }

    public Set<Field> getIndirectlyAffectedFields() {
        return this.indirectlyAffectedFields;
    }

    public CaseResult getCaseResult() {
        return this.caseResult;
    }

    public ResolvedBranch getCaseBranchSet() {
        return this.caseBranchSet;
    }

    public void setCaseBranchSet(ResolvedBranch caseBranchSet) {
        this.caseBranchSet = caseBranchSet;
    }

    public Set<String> getReflectionMethods() {
        return this.reflectionMethods;
    }

    public Integer getStartOffset() {
        return this.startOffset;
    }

    public Integer getEndOffset() {
        return this.endOffset;
    }

    public Set<String> getExceptions() {
        return this.exceptions;
    }

    public void setExceptions(Set<String> exceptions) {
        this.exceptions = exceptions;
    }

    public List<String> getCallParams() {
        return this.callParams;
    }
}
