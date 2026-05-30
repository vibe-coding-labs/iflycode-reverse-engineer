package com.aicode.template;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.aicode.template.builder.MethodFactory;
import com.aicode.template.context.domain.Type;
import com.aicode.template.context.resolved.ResolvedMethodCall;
import com.aicode.util.ClassNameUtils;
import com.aicode.util.PropertyUtils;
import com.aicode.util.PsiUtils;
import com.aicode.util.TypeUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiUtil;
import java.lang.invoke.SerializedLambda;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/TypeDictionary.class */
public class TypeDictionary {
    private static final Logger LOG = Logger.getInstance(TypeDictionary.class.getName());
    private static final int MAX_RELEVANT_METHOD_IDS_CACHE = 5000;
    private final Set<String> testSubjectTypesNames;
    private final Set<String> testSubjectMethodNames;
    private final Cache<String, Type> typeDictionary;
    private final PsiClass testSubjectClass;
    private final PsiPackage targetPackage;
    private final Set<PsiMethod> testedMethods;
    private final Set<String> methodCallsFromTestSubject;
    private final List<String> testSubjectMethodParamsType;
    private boolean throwSpecificExceptionTypes;
    private AtomicInteger newTypeCounter = new AtomicInteger();
    private AtomicInteger existingTypeHitsCounter = new AtomicInteger();
    private final Cache<String, Boolean> relevantMethodIdsCache = CacheUtil.newLRUCache(5000);
    private final long startTimestamp = System.currentTimeMillis();

    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda lambda) {
        String implMethodName = lambda.getImplMethodName();
        boolean z = -1;
        switch (implMethodName.hashCode()) {
            case 87286059:
                if (implMethodName.equals("lambda$isRelevant$4efe8f$1")) {
                    z = true;
                    break;
                }
                break;
            case 1633069425:
                if (implMethodName.equals("lambda$isRelevant$f685160b$1")) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                if (lambda.getImplMethodKind() == 7 && lambda.getFunctionalInterfaceClass().equals("cn/hutool/core/lang/func/Func0") && lambda.getFunctionalInterfaceMethodName().equals("call") && lambda.getFunctionalInterfaceMethodSignature().equals("()Ljava/lang/Object;") && lambda.getImplClass().equals("com/aicode/template/TypeDictionary") && lambda.getImplMethodSignature().equals("(Lcom/intellij/psi/PsiMethod;Ljava/lang/String;Lcom/intellij/psi/PsiClass;)Ljava/lang/Boolean;")) {
                    TypeDictionary typeDictionary = (TypeDictionary) lambda.getCapturedArg(0);
                    PsiMethod psiMethod = (PsiMethod) lambda.getCapturedArg(1);
                    String str = (String) lambda.getCapturedArg(2);
                    PsiClass psiClass = (PsiClass) lambda.getCapturedArg(3);
                    return () -> {
                        return Boolean.valueOf(computeIsRelevant(psiMethod, str, psiClass));
                    };
                }
                break;
            case true:
                if (lambda.getImplMethodKind() == 7 && lambda.getFunctionalInterfaceClass().equals("cn/hutool/core/lang/func/Func0") && lambda.getFunctionalInterfaceMethodName().equals("call") && lambda.getFunctionalInterfaceMethodSignature().equals("()Ljava/lang/Object;") && lambda.getImplClass().equals("com/aicode/template/TypeDictionary") && lambda.getImplMethodSignature().equals("(Lcom/intellij/psi/PsiMethod;Lcom/intellij/psi/PsiClass;)Ljava/lang/Boolean;")) {
                    TypeDictionary typeDictionary2 = (TypeDictionary) lambda.getCapturedArg(0);
                    PsiMethod psiMethod2 = (PsiMethod) lambda.getCapturedArg(1);
                    PsiClass psiClass2 = (PsiClass) lambda.getCapturedArg(2);
                    return () -> {
                        return Boolean.valueOf(computeIsRelevant(psiMethod2, null, psiClass2));
                    };
                }
                break;
        }
        throw new IllegalArgumentException("Invalid lambda deserialization");
    }

    private TypeDictionary(Set<String> testSubjectMethodNames, PsiClass srcClass, PsiPackage targetPackage, Set<String> methodCallsFromTestSubject, List<String> testSubjectMethodParamsType, Cache<String, Type> typeCache, Set<PsiMethod> testedMethods, boolean throwSpecificExceptionTypes) {
        this.testSubjectMethodNames = testSubjectMethodNames;
        this.testSubjectClass = srcClass;
        this.testSubjectTypesNames = resolveTypesNames(srcClass);
        this.targetPackage = targetPackage;
        this.methodCallsFromTestSubject = methodCallsFromTestSubject;
        this.testSubjectMethodParamsType = testSubjectMethodParamsType;
        this.typeDictionary = typeCache;
        this.throwSpecificExceptionTypes = throwSpecificExceptionTypes;
        this.testedMethods = testedMethods;
    }

    private Set<String> resolveTypesNames(PsiClass srcClass) {
        HashSet<String> typesNames = new HashSet<>();
        PsiClass psiClass = srcClass;
        while (true) {
            PsiClass clazz = psiClass;
            if (clazz == null || TypeUtils.isLanguageBaseClass(clazz.getQualifiedName())) {
                break;
            }
            typesNames.add(clazz.getQualifiedName());
            psiClass = clazz.getSuperClass();
        }
        return typesNames;
    }

    public static TypeDictionary create(PsiClass srcClass, PsiPackage targetPackage, Cache<String, Type> typeCache, boolean throwSpecificExceptionTypes, Set<String> excludeMethodList, Set<PsiMethod> testMethods) {
        String methodId;
        Set<String> methodCallsFromTestSubject = new HashSet<>();
        if (srcClass != null) {
            try {
                for (PsiMethod method : srcClass.getAllMethods()) {
                    if (isNotExistsMethod(method, excludeMethodList) && !TypeUtils.isIgnore(method.getName())) {
                        boolean shouldCheckMethodCall = CollectionUtils.isEmpty(testMethods) || testMethods.contains(method);
                        if (shouldCheckMethodCall) {
                            List<ResolvedMethodCall> methodCalls = MethodFactory.resolvedMethodCalls(method);
                            for (ResolvedMethodCall methodCall : methodCalls) {
                                if (methodCall != null && methodCall.getPsiMethod() != null) {
                                    methodId = PsiUtils.formatMethodId(r0.getContainingClass(), r0.getName(), methodCall.getPsiMethod().getParameterList().getParameters());
                                    if (!methodId.equals(methodCall.getMethodId())) {
                                        methodCallsFromTestSubject.add(methodId);
                                    }
                                }
                            }
                            methodCallsFromTestSubject.addAll((Collection) methodCalls.stream().map((v0) -> {
                                return v0.getMethodId();
                            }).collect(Collectors.toList()));
                        }
                    }
                }
            } catch (IndexNotReadyException e) {
            }
        }
        List<String> testSubjectMethodParamsType = srcClass == null ? List.of() : (List) Arrays.stream(srcClass.getAllMethods()).filter(method2 -> {
            return isNotExistsMethod(method2, excludeMethodList);
        }).flatMap(psiMethod1 -> {
            return Arrays.stream(psiMethod1.getParameterList().getParameters()).map(p -> {
                return p.getType().getCanonicalText();
            }).filter(item -> {
                return !TypeUtils.isBasicType(item);
            });
        }).collect(Collectors.toList());
        return new TypeDictionary(excludeMethodList, srcClass, targetPackage, methodCallsFromTestSubject, testSubjectMethodParamsType, typeCache, testMethods, throwSpecificExceptionTypes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isNotExistsMethod(PsiMethod method, Set<String> excludeMethodList) {
        if (excludeMethodList.isEmpty()) {
            return true;
        }
        Boolean existGetter = Boolean.valueOf(excludeMethodList.contains(ExcludeMethodEnum.GETTER.getName()));
        Boolean existSetter = Boolean.valueOf(excludeMethodList.contains(ExcludeMethodEnum.SETTER.getName()));
        Boolean existMain = Boolean.valueOf(excludeMethodList.contains(ExcludeMethodEnum.MAIN.getName()));
        if (existGetter.booleanValue() && PropertyUtils.isPropertyGetter(method)) {
            return false;
        }
        if (existSetter.booleanValue() && PropertyUtils.isPropertySetter(method)) {
            return false;
        }
        if (existMain.booleanValue() && PropertyUtils.isMainMethod(method)) {
            return false;
        }
        String methodName = method.getName();
        if (excludeMethodList.contains(methodName)) {
            return false;
        }
        return excludeMethodList.stream().noneMatch(exclude -> {
            return StringUtils.isNotEmpty(exclude) && methodName.matches(exclude);
        });
    }

    public boolean isRelevant(PsiMethod psiMethod, @Nullable PsiClass psiClass) {
        String formatMethodId;
        Cache<String, Boolean> cache = this.relevantMethodIdsCache;
        formatMethodId = PsiUtils.formatMethodId(psiMethod.getContainingClass(), psiMethod.getName(), psiMethod.getParameterList().getParameters());
        return ((Boolean) cache.get(formatMethodId, () -> {
            return Boolean.valueOf(computeIsRelevant(psiMethod, null, psiClass));
        })).booleanValue();
    }

    public boolean shouldCheckMethodCall(PsiMethod psiMethod) {
        boolean shouldCheckMethodCall = CollectionUtils.isEmpty(this.testedMethods) || this.testedMethods.contains(psiMethod);
        return shouldCheckMethodCall;
    }

    public boolean isRelevant(PsiMethod psiMethod, String methodId, @Nullable PsiClass psiClass) {
        return ((Boolean) this.relevantMethodIdsCache.get(methodId, () -> {
            return Boolean.valueOf(computeIsRelevant(psiMethod, methodId, psiClass));
        })).booleanValue();
    }

    private boolean computeIsRelevant(PsiMethod psiMethod, String methodId, @Nullable PsiClass psiClass) {
        PsiClass ownerClass = psiMethod.getContainingClass() == null ? psiClass : psiClass != null ? psiClass : psiMethod.getContainingClass();
        String _methodId = methodId == null ? PsiUtils.formatMethodId(psiMethod.getContainingClass(), psiMethod.getName(), psiMethod.getParameterList().getParameters()) : methodId;
        if (!isTestSubject(ownerClass) && !calledFromTestSubject(_methodId) && !isCtorOfUsedType(psiMethod)) {
            return false;
        }
        if (isTestSubject(ownerClass) && (isTestOfMethod(psiMethod) || !isNotExistsMethod(psiMethod, this.testSubjectMethodNames))) {
            return false;
        }
        if (ownerClass != null && TypeUtils.isLanguageBaseClass(ownerClass.getQualifiedName())) {
            return false;
        }
        return true;
    }

    private boolean isTestOfMethod(PsiMethod psiMethod) {
        if (psiMethod == null) {
            return true;
        }
        return Arrays.stream(psiMethod.getAnnotations()).anyMatch(ann -> {
            return TypeUtils.isTestAnnotation(ann.getQualifiedName());
        });
    }

    private boolean isCtorOfUsedType(PsiMethod psiMethod) {
        return psiMethod.isConstructor() && psiMethod.getContainingClass() != null && this.testSubjectMethodParamsType.contains(psiMethod.getContainingClass().getQualifiedName());
    }

    public boolean isUsedType(PsiMethod psiMethod) {
        return psiMethod.getContainingClass() != null && this.testSubjectMethodParamsType.contains(psiMethod.getContainingClass().getQualifiedName());
    }

    private boolean calledFromTestSubject(String methodId) {
        return this.methodCallsFromTestSubject.stream().anyMatch(resolvedMethodCall -> {
            return resolvedMethodCall.equals(methodId);
        });
    }

    public boolean isTestSubject(PsiClass psiClass) {
        return psiClass != null && this.testSubjectTypesNames.contains(psiClass.getQualifiedName());
    }

    @Nullable
    public Type getType(PsiType psiType, int maxRecursionDepth, boolean shouldResolveAllMethods) {
        return getTypeInternal(psiType, maxRecursionDepth, shouldResolveAllMethods, null);
    }

    @Nullable
    public boolean contain(PsiClass psiType) {
        String canonicalText = ClassNameUtils.resolveCanonicalName(psiType, null);
        return canonicalText != null && this.typeDictionary.containsKey(canonicalText);
    }

    @Nullable
    public Type getType(PsiClass psiClass, int maxRecursionDepth, boolean shouldResolveAllMethods) {
        return getTypeInternal(psiClass, maxRecursionDepth, shouldResolveAllMethods, null);
    }

    public Type getType(PsiType type, int maxRecursionDepth, boolean shouldResolveAllMethods, Object element) {
        return getTypeInternal(type, maxRecursionDepth, shouldResolveAllMethods, element);
    }

    @Nullable
    private Type getTypeInternal(Object element, int maxRecursionDepth, boolean shouldResolveAllMethods, Object typeElement) {
        Type type = null;
        String canonicalText = ClassNameUtils.resolveCanonicalName(element, typeElement);
        if (canonicalText != null) {
            type = (Type) this.typeDictionary.get(canonicalText);
            if (type == null) {
                LOG.debug(this.newTypeCounter.incrementAndGet() + ". Creating new Type for:" + canonicalText + " maxRecursionDepth:" + maxRecursionDepth);
                if (element instanceof PsiType) {
                    PsiType psiType = (PsiType) element;
                    type = new Type(psiType, typeElement, this, maxRecursionDepth, shouldResolveAllMethods);
                    this.typeDictionary.put((maxRecursionDepth <= 0 || !shouldResolveAllMethods) ? getCanonicalText(type) : canonicalText, type);
                } else if (element instanceof PsiClass) {
                    PsiClass psiClass = (PsiClass) element;
                    type = new Type(psiClass, this, maxRecursionDepth, shouldResolveAllMethods);
                    this.typeDictionary.put((maxRecursionDepth <= 0 || !shouldResolveAllMethods) ? getCanonicalText(type) : canonicalText, type);
                }
            }
            if (type != null && !type.isDependenciesResolved() && shouldResolveAllMethods && maxRecursionDepth > 0 && !type.isResolved() && (element instanceof PsiType)) {
                if (TypeUtils.isBasicType(type)) {
                    type.setResolved(true);
                } else {
                    PsiType psiType2 = (PsiType) element;
                    type.setResolved(true);
                    type.resolveDependencies(this, maxRecursionDepth, psiType2, shouldResolveAllMethods);
                }
            }
        }
        return type;
    }

    private String getCanonicalText(Type type) {
        String canonicalText = type.getCanonicalName();
        if (!type.isArray()) {
            return type.getCanonicalName() + (type.isArray() ? "[]" : "");
        }
        for (int i = 0; i < type.getArrayDimensions(); i++) {
            canonicalText = canonicalText + "[]";
        }
        return canonicalText;
    }

    public boolean isAccessible(PsiMethod psiMethod) {
        return PsiUtil.isAccessibleFromPackage(psiMethod, this.targetPackage) && (psiMethod.getContainingClass() == null || PsiUtil.isAccessibleFromPackage(psiMethod.getContainingClass(), this.targetPackage));
    }

    public void logStatistics() {
        LOG.debug(String.format("**** Statistics: took %dms. type hits/req:%d/%d type relevancy cache %d", Long.valueOf(this.startTimestamp - System.currentTimeMillis()), Integer.valueOf(this.newTypeCounter.get()), Integer.valueOf(this.newTypeCounter.get() + this.existingTypeHitsCounter.get()), Integer.valueOf(this.typeDictionary.size())));
    }

    public boolean isThrowSpecificExceptionTypes() {
        return this.throwSpecificExceptionTypes;
    }

    public void setThrowSpecificExceptionTypes(boolean throwSpecificExceptionTypes) {
        this.throwSpecificExceptionTypes = throwSpecificExceptionTypes;
    }

    public void resolveMethodReturnTypeAndParam(PsiMethod method, int maxRecursionDepth) {
        Type returnType;
        Type type;
        for (PsiParameter parameter : method.getParameterList().getParameters()) {
            if (!TypeUtils.isBasicType(parameter.getType().getCanonicalText()) && (type = getType(parameter.getType(), maxRecursionDepth, true, null)) != null && !type.getComposedTypes().isEmpty() && (parameter.getType() instanceof PsiClassType)) {
                PsiClassType psiClassType = parameter.getType();
                PsiType[] parameters = psiClassType.getParameters();
                for (PsiType psiTypeArg : parameters) {
                    if (!TypeUtils.isBasicType(psiTypeArg.getCanonicalText())) {
                        getType(psiTypeArg, maxRecursionDepth - 1, true, null);
                    }
                }
            }
        }
        if (method.getReturnType() != null && !TypeUtils.isBasicType(method.getReturnType().getCanonicalText()) && (returnType = getType(method.getReturnType(), maxRecursionDepth, true, null)) != null && !returnType.getComposedTypes().isEmpty()) {
            if (method.getReturnType() instanceof PsiClassType) {
                PsiClassType psiClassType2 = method.getReturnType();
                PsiType[] parameters2 = psiClassType2.getParameters();
                for (PsiType psiTypeArg2 : parameters2) {
                    if (!TypeUtils.isBasicType(psiTypeArg2.getCanonicalText())) {
                        getType(psiTypeArg2, maxRecursionDepth - 1, true, null);
                    }
                }
                return;
            }
            LOG.debug("resolveMethodReturnType:" + returnType.getCanonicalName());
        }
    }
}
