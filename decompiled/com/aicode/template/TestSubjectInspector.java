package com.aicode.template;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.template.context.domain.Field;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.MethodCall;
import com.aicode.template.context.domain.MethodCallArgument;
import com.aicode.template.context.domain.Param;
import com.aicode.template.context.domain.Type;
import com.aicode.template.request.DataUtils;
import com.aicode.template.request.dto.CaseResult;
import com.aicode.util.TypeUtils;
import com.intellij.openapi.diagnostic.Logger;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/TestSubjectInspector.class */
public class TestSubjectInspector {
    private static final Logger LOG = Logger.getInstance(TestSubjectInspector.class.getName());
    private static final Set<String> SCALA_FUTURE_TYPES = Set.of("scala.concurrent.Future", "scala.concurrent.impl.Promise");
    private final boolean generateTestsForInternalMethods;
    private final Set<Method> selectedMethods;

    public TestSubjectInspector(boolean generateTestsForInternalMethods, Set<Method> selectedMethods) {
        this.generateTestsForInternalMethods = generateTestsForInternalMethods;
        this.selectedMethods = selectedMethods;
    }

    public boolean hasTestableInstanceMethod(List<Method> methods) {
        for (Method method : methods) {
            if (shouldBeTested(method) && !method.isStatic()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSetFields(List<Field> fields) {
        return fields != null && fields.stream().anyMatch((v0) -> {
            return v0.isAnnotatedBySpringValue();
        });
    }

    public boolean shouldBeTested(Method method) {
        if (CollectionUtil.isNotEmpty(this.selectedMethods)) {
            return CollectionUtil.contains(this.selectedMethods, method);
        }
        boolean shouldBuTested = method.isTestable() && !method.isInherited();
        if (method.isPrivate()) {
            return shouldBuTested && this.generateTestsForInternalMethods;
        }
        return shouldBuTested;
    }

    public boolean isMethodCalled(Method calledMethod, Method callerMethod, CaseResult caseResult) {
        Set<MethodCall> methodCalls = callerMethod.getMethodCalls();
        boolean isMethodCalled = false;
        if (calledMethod.isConstructor()) {
            return false;
        }
        Iterator<MethodCall> it = methodCalls.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MethodCall methodCall = it.next();
            if (methodCall.getMethod().methodEquals(calledMethod)) {
                if (calledMethod.hasReturn()) {
                    Type type = calledMethod.getReturnType();
                    Type parentContainerClass = type.getParentContainerClass();
                    if (parentContainerClass != null && !type.isStatic() && !type.isEnum()) {
                        return false;
                    }
                    if (!calledMethod.getMethodParams().isEmpty()) {
                        if (calledMethod.getMethodParams().stream().filter(param -> {
                            return ObjectUtil.isNotNull(param.getType());
                        }).anyMatch(param2 -> {
                            return param2.getType().getCanonicalName().contains("<lambda expression>");
                        })) {
                            return false;
                        }
                        if (!DataUtils.isEmptyData(caseResult) && calledMethod.isStatic()) {
                            Param callerParam = null;
                            for (MethodCallArgument methodCallArgument : methodCall.getMethodCallArguments()) {
                                callerParam = callerMethod.getMethodParams().stream().filter(param3 -> {
                                    return param3.getName().equalsIgnoreCase(methodCallArgument.getText());
                                }).findFirst().orElse(null);
                                if (callerParam != null) {
                                    break;
                                }
                            }
                            if (callerParam != null && caseResult.getInput() != null) {
                                Param finalCallerParam = callerParam;
                                boolean containerValue = caseResult.getInput().containsKey(finalCallerParam.getName());
                                if (containerValue && calledMethod.getMethodParams().stream().filter(param4 -> {
                                    return ObjectUtil.isNotNull(param4.getType());
                                }).anyMatch(param5 -> {
                                    return param5.getType().equals(finalCallerParam.getType());
                                })) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                isMethodCalled = true;
            }
        }
        return isMethodCalled;
    }

    public boolean isJavaFuture(Type type) {
        for (String javaFutureType : TypeUtils.JAVA_FUTURE_TYPES) {
            if (isSameGenericType(type, javaFutureType)) {
                return true;
            }
        }
        return isImplements(type, "java.util.concurrent.Future");
    }

    @Nullable
    public Method findOptimalConstructor(Type type) {
        Optional<Method> optPrimaryCtor = Optional.of(type.getMethods()).flatMap(methods -> {
            return methods.stream().filter((v0) -> {
                return v0.isPrimaryConstructor();
            }).findAny();
        });
        return optPrimaryCtor.orElse(findBiggestValidConstructor(type));
    }

    public Set<String> getJavaFutureTypes() {
        return TypeUtils.JAVA_FUTURE_TYPES;
    }

    public Set<String> getScalaFutureTypes() {
        return SCALA_FUTURE_TYPES;
    }

    public boolean isMethodOwnedByClass(Method method, Type testedClass) {
        List<Method> methods = testedClass.getMethods();
        return methods.stream().anyMatch(classMethod -> {
            return method.getMethodId().equals(classMethod.getMethodId());
        });
    }

    public boolean isNotInjectedInDiClass(Field field, Type testedClass) {
        return (testedClass == null || !testedClass.isAnnotatedByDI() || field.isAnnotatedByDI() || field.isHasSetter() || testedClass.hasConstructor()) ? false : true;
    }

    public boolean hasAccessibleCtor(Type testedClass) {
        List<Method> constructorList = testedClass.findConstructors();
        return constructorList.isEmpty() || constructorList.stream().anyMatch(method -> {
            return !method.isPrivate();
        });
    }

    @Nullable
    private static Method findBiggestValidConstructor(Type type) {
        return type.findConstructors().stream().filter((v0) -> {
            return v0.isAccessible();
        }).findFirst().orElse(null);
    }

    private static boolean isImplements(Type type, String classCanonicalName) {
        for (Type interfaceType : type.getImplementedInterfaces()) {
            if (isSameGenericType(interfaceType, classCanonicalName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSameGenericType(Type type, String classCanonicalName) {
        String replaceFirst;
        replaceFirst = type.getCanonicalName().replaceFirst(InlineChatStatusServiceKt.H("]uT"), "");
        return classCanonicalName.equals(replaceFirst);
    }

    private boolean hasInputParams(Set<String> paramNameKeys) {
        return paramNameKeys.size() > 1 || (paramNameKeys.size() == 1 && !paramNameKeys.contains("expectedResult"));
    }
}
