package com.aicode.template.builder;

import com.aicode.template.context.domain.Field;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.MethodCall;
import com.aicode.template.context.domain.MethodCallArgument;
import com.aicode.template.context.domain.Param;
import com.aicode.template.context.domain.StaticMethodCall;
import com.aicode.template.context.domain.Type;
import com.aicode.template.context.resolved.ResolveComponents;
import com.aicode.template.request.DataUtils;
import com.aicode.template.request.dto.CaseResult;
import com.aicode.util.StringUtils;
import com.aicode.util.TypeUtils;
import com.intellij.openapi.diagnostic.Logger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/builder/MethodReferencesBuilder.class */
public class MethodReferencesBuilder {
    private static final Logger logger = Logger.getInstance(MethodReferencesBuilder.class.getName());

    public void resolveMethodReferences(int maxMethodCallsDepth, List<Method> methods) {
        for (int i = 0; i < maxMethodCallsDepth; i++) {
            for (Method method : methods) {
                resolveMethodCalls(methods, method);
            }
        }
        for (Method method2 : methods) {
            resolveFieldsAffectedByCtor(method2.getReturnType(), maxMethodCallsDepth);
        }
        List<Type> methodParamTypes = (List) methods.stream().flatMap(method3 -> {
            return method3.getMethodParams().stream().map((v0) -> {
                return v0.getType();
            });
        }).collect(Collectors.toList());
        for (Type methodParamType : methodParamTypes) {
            resolveFieldsAffectedByCtor(methodParamType, maxMethodCallsDepth);
        }
        logger.debug("Resolved internal references in test template context");
    }

    public void resolveMethodCallFields(int maxRecursionDepth, List<Method> methods, List<Field> fields) {
        for (Method method : methods) {
            for (MethodCall methodCall : method.getMethodCalls()) {
                String methodOwnerClass = methodCall.getMethod().getOwnerClassCanonicalType();
                Field field = fields.stream().filter(field1 -> {
                    return field1.getType() != null && field1.getType().getCanonicalName().equalsIgnoreCase(methodOwnerClass);
                }).findFirst().orElse(null);
                if (field != null && field.getType() != null && field.getType().getMethods().stream().noneMatch(method1 -> {
                    return methodCall.getMethod().methodEquals(method1);
                })) {
                    field.getType().getMethods().add(methodCall.getMethod());
                }
                String ownerClass = methodCall.getMethod().getOwnerClassCanonicalType();
                method.getMethodCalls().stream().filter(item -> {
                    return item.getMethod().isStatic() && item.getMethod().getReturnType().getCanonicalName().equals(ownerClass);
                }).findFirst().ifPresent(item2 -> {
                    String fieldName = StringUtils.deCapitalizeFirstLetter(item2.getMethod().getReturnType().getName());
                    boolean isNotInBuilder = true;
                    if (item2.getMethod().getReturnType() != null) {
                        String returnTypename = item2.getMethod().getReturnType().getCanonicalName();
                        String returnOwner = item2.getMethod().getOwnerClassCanonicalType();
                        if (StringUtils.equals(returnOwner, returnTypename)) {
                            isNotInBuilder = false;
                        } else if (StringUtils.isNotBlank(item2.getMethod().getReturnType().getSuperClass())) {
                            String returnTypeSuperName = item2.getMethod().getReturnType().getSuperClass();
                            if (StringUtils.equals(returnOwner, returnTypeSuperName)) {
                                isNotInBuilder = false;
                            }
                        }
                    }
                    if (!TypeUtils.isBasicType(item2.getMethod().getReturnType()) && !TypeUtils.isDateType(item2.getMethod().getReturnType()) && TypeUtils.isMockable(item2.getMethod().getReturnType()) && fields.stream().noneMatch(f -> {
                        return f.getName().equals(fieldName) && f.getType().equals(item2.getMethod().getReturnType());
                    })) {
                        fields.add(new Field(fieldName, item2.getMethod().getReturnType(), item2.getMethod().getReturnType().isFinal(), item2.getMethod().isStatic(), isNotInBuilder));
                    }
                });
            }
        }
    }

    public void resolveMethodVariables(Type type) {
        List<Method> typeMethods = type.getMethods();
        List<Method> list = new ArrayList<>(typeMethods);
        List<Field> fields = type.getFields();
        for (Method method : list) {
            if (method.isPublic()) {
                ResolveComponents.reset(method, null);
                for (MethodCall methodCall : method.getMethodCalls()) {
                    for (Field field : fields) {
                        List<Method> fieldMethods = field.getType().getMethods();
                        Method fieldMethod = fieldMethods.stream().filter(fm -> {
                            return fm.methodEquals(methodCall.getMethod());
                        }).findFirst().orElse(null);
                        if (fieldMethod != null) {
                            fieldMethods.removeIf(fm2 -> {
                                return fm2.methodEquals(methodCall.getMethod());
                            });
                            fieldMethods.add(methodCall.getMethod());
                        }
                    }
                }
            }
        }
    }

    public void resolveMethodCallByCaseResult(Type type) {
        Set<String> removedType = new HashSet<>();
        for (Method method : type.getMethods()) {
            for (StaticMethodCall staticMethodCall : method.getStaticMethodCalls()) {
                Method calledMethod = staticMethodCall.getMethod();
                if (calledMethod.isStatic() && !calledMethod.getMethodParams().isEmpty()) {
                    for (CaseResult caseResult : method.getCaseResults()) {
                        if (!DataUtils.isEmptyData(caseResult)) {
                            Param callerParam = null;
                            for (MethodCallArgument methodCallArgument : staticMethodCall.getMethodCallArguments()) {
                                callerParam = method.getMethodParams().stream().filter(param -> {
                                    return param.getName().equalsIgnoreCase(methodCallArgument.getText());
                                }).findFirst().orElse(null);
                                if (callerParam != null) {
                                    break;
                                }
                            }
                            if (callerParam != null && caseResult.getInput() != null) {
                                Param finalCallerParam = callerParam;
                                boolean containerValue = caseResult.getInput().containsKey(finalCallerParam.getName());
                                if (containerValue && calledMethod.getMethodParams().stream().anyMatch(param2 -> {
                                    return param2.getType().equals(finalCallerParam.getType());
                                })) {
                                    removedType.add(staticMethodCall.getOwnerClass());
                                }
                            }
                        }
                    }
                }
            }
            method.getStaticMethodCalls().removeIf(staticMethodCall2 -> {
                return removedType.contains(staticMethodCall2.getOwnerClass());
            });
        }
        Set<String> staticClassNames = type.getStaticClassNames();
        Objects.requireNonNull(removedType);
        staticClassNames.removeIf((v1) -> {
            return r1.contains(v1);
        });
    }

    private boolean isStaticCall(Method method) {
        return (method.isStatic() && method.getMethodParams().isEmpty()) ? false : true;
    }

    public boolean isMethodCalled(Method calledMethod, Method callerMethod) {
        Set<MethodCall> methodCalls = callerMethod.getMethodCalls();
        boolean isMethodCalled = false;
        Iterator<MethodCall> it = methodCalls.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MethodCall methodCall = it.next();
            if (methodCall.getMethod().getMethodId().equals(calledMethod.getMethodId())) {
                isMethodCalled = true;
                break;
            }
        }
        return isMethodCalled;
    }

    private boolean isMethodCalled(Type calledType, Method method) {
        return method.getMethodCalls().stream().anyMatch(methodCall1 -> {
            return methodCall1.getMethod().getMethodId().startsWith(calledType.getCanonicalName());
        });
    }

    private void resolveFieldsAffectedByCtor(Type type, int maxMethodCallsDepth) {
        if (maxMethodCallsDepth >= 1 && isValidObject(type)) {
            for (Method ctor : type.findConstructors()) {
                Set<Field> affectedFields = new HashSet<>();
                for (MethodCall methodCall : ctor.getMethodCalls()) {
                    for (Param param : methodCall.getMethod().getMethodParams()) {
                        Iterator<Field> it = param.getAssignedToFields().iterator();
                        while (it.hasNext()) {
                            Field assignedToField = it.next();
                            if (assignedToField.getOwnerClassCanonicalName().equals(ctor.getOwnerClassCanonicalType())) {
                                affectedFields.add(assignedToField);
                            }
                        }
                        int i = maxMethodCallsDepth;
                        maxMethodCallsDepth--;
                        resolveFieldsAffectedByCtor(param.getType(), i);
                    }
                }
                ctor.getIndirectlyAffectedFields().addAll(affectedFields);
            }
        }
    }

    private boolean isValidObject(Type type) {
        return (type == null || type.isPrimitive() || type.isArray() || type.isInterface() || type.isAbstract() || type.isVarargs()) ? false : true;
    }

    private void resolveMethodCalls(List<Method> methods, Method method) {
        MethodCall methodCallFound;
        Set<MethodCall> calledMethodsByMethodCalls = new HashSet<>();
        Set<MethodCall> methodsInMyFamilyTree = new HashSet<>();
        for (MethodCall methodCall : method.getMethodCalls()) {
            Method calledMethodFound = find(methods, methodCall.getMethod());
            if (calledMethodFound != null) {
                if (methodCall.getMethod().methodEquals(calledMethodFound) || calledMethodFound.getReturnType() == null) {
                    methodCallFound = methodCall;
                } else {
                    methodCallFound = new MethodCall(calledMethodFound, calledMethodFound.getReturnType().getName(), methodCall.getMethodCallArguments());
                }
                methodsInMyFamilyTree.add(methodCallFound);
                calledMethodsByMethodCalls.add(methodCallFound);
                if (method.getOwnerClassCanonicalType() != null && method.getOwnerClassCanonicalType().equals(methodCallFound.getMethod().getOwnerClassCanonicalType())) {
                    calledMethodsByMethodCalls.addAll(calledMethodFound.getMethodCalls());
                }
            }
        }
        method.getMethodCalls().removeAll(calledMethodsByMethodCalls);
        method.getMethodCalls().addAll(calledMethodsByMethodCalls);
        method.getCalledFamilyMembers().addAll(methodsInMyFamilyTree);
    }

    private Method find(List<Method> methods, Method toMethod) {
        for (Method method : methods) {
            if (method.methodEquals(toMethod)) {
                return method;
            }
            if (method.getReturnType() != null) {
                for (Method returnTypeMethod : method.getReturnType().getMethods()) {
                    if (returnTypeMethod.methodEquals(toMethod)) {
                        return returnTypeMethod;
                    }
                }
            }
        }
        return null;
    }
}
