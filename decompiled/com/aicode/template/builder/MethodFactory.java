package com.aicode.template.builder;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.aicode.template.TypeDictionary;
import com.aicode.template.context.domain.Field;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.MethodCall;
import com.aicode.template.context.domain.MethodCallArgument;
import com.aicode.template.context.domain.Param;
import com.aicode.template.context.domain.Reference;
import com.aicode.template.context.domain.StaticMethodCall;
import com.aicode.template.context.domain.Type;
import com.aicode.template.context.resolved.MethodCallArg;
import com.aicode.template.context.resolved.ResolvedBranch;
import com.aicode.template.context.resolved.ResolvedMethodCall;
import com.aicode.template.context.resolved.ResolvedReference;
import com.aicode.template.request.dto.CaseResult;
import com.aicode.util.ClassNameUtils;
import com.aicode.util.JavaPsiUtils;
import com.aicode.util.PropertyUtils;
import com.aicode.util.PsiUtils;
import com.aicode.util.StringUtils;
import com.aicode.util.TypeUtils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiAssignmentExpression;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParenthesizedExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiReferenceList;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiTypeParameter;
import com.intellij.psi.SyntheticElement;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.search.LocalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.MethodSignatureUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.Query;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/builder/MethodFactory.class */
public class MethodFactory {
    private static final Logger LOG = Logger.getInstance(MethodFactory.class.getName());
    public static Cache<String, Method> methodIdCaches = CacheUtil.newLRUCache(100000, 600000);
    private static String jsonDirPath = "";

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "typeDictionary", "com/aicode/template/builder/MethodFactory", "resolveInternalReferences"));
    }

    private static List<CaseResult> getLocalData(PsiClass srcClass, String ownerClassCanonicalType, PsiType ownerClassPsiType, String methodName) {
        List<CaseResult> caseResults = new ArrayList<>();
        try {
            String ownerName = srcClass.getQualifiedName();
            if (ownerName == null) {
                ownerName = ownerClassPsiType != null ? ownerClassPsiType.getCanonicalText() : ownerClassCanonicalType;
            }
            if (ownerName != null) {
                String fileName = ClassNameUtils.extractClassName(ownerName) + "_" + methodName;
                String filePath = jsonDirPath + fileName + ".json";
                if (FileUtil.exist(filePath)) {
                    String json = FileUtil.readString(filePath, Charset.defaultCharset());
                    caseResults = JSONUtil.toList(json, CaseResult.class);
                }
            }
        } catch (Exception e) {
            LOG.warn("转换异常", e);
        }
        return caseResults;
    }

    public static Method createMethod(PsiMethod psiMethod, PsiClass srcClass, String methodId, int maxRecursionDepth, TypeDictionary typeDictionary, @Nullable PsiType ownerClassPsiType, Type returnType1) {
        String methodName = psiMethod.getName();
        String methodId2 = methodId == null ? PsiUtils.formatMethodId(srcClass, methodName, psiMethod.getParameterList().getParameters()) : methodId;
        Optional<PsiSubstitutor> methodSubstitutor = findMethodSubstitutor(psiMethod, srcClass, ownerClassPsiType);
        Type returnType = returnType1 == null ? resolveReturnType(psiMethod, maxRecursionDepth - 1, typeDictionary, methodSubstitutor) : returnType1;
        if (methodIdCaches.containsKey(methodId2)) {
            Method cacheMethod = (Method) methodIdCaches.get(methodId2);
            if (cacheMethod.hasReturn() && cacheMethod.getReturnType().equals(returnType)) {
                cacheMethod.getCaseResults().clear();
                return cacheMethod;
            }
        }
        boolean isPrivate = psiMethod.hasModifierProperty("private");
        boolean isProtected = psiMethod.hasModifierProperty("protected");
        boolean isDefault = isDefault(psiMethod);
        boolean isPublic = psiMethod.hasModifierProperty("public");
        boolean isAbstract = psiMethod.hasModifierProperty("abstract");
        boolean isNative = psiMethod.hasModifierProperty("native");
        boolean isStatic = psiMethod.hasModifierProperty("static");
        String ownerClassCanonicalType = resolveOwnerClassName(psiMethod);
        boolean isConstructor = psiMethod.isConstructor();
        boolean isPrimaryConstructor = typeDictionary.isUsedType(psiMethod);
        boolean isSetter = PropertyUtils.isPropertySetter(psiMethod);
        boolean isGetter = PropertyUtils.isPropertyGetter(psiMethod);
        String propertyName1 = ClassNameUtils.extractTargetPropertyName(methodName, isSetter, isGetter);
        boolean overriddenInChild = isOverriddenInChild(psiMethod, srcClass);
        boolean inherited = isInherited(psiMethod, srcClass);
        boolean isInterface = isInterface(psiMethod);
        boolean accessible = typeDictionary.isAccessible(psiMethod);
        boolean syntheticMethod = isSyntheticMethod(psiMethod);
        boolean testable = isTestable(psiMethod, srcClass);
        List<CaseResult> caseResults = getLocalData(srcClass, ownerClassCanonicalType, ownerClassPsiType, methodName);
        List<Param> methodParams = extractMethodParams(psiMethod, isPrimaryConstructor, maxRecursionDepth - 1, typeDictionary, methodSubstitutor);
        String throwsExceptions = extractMethodExceptionTypes(psiMethod, Boolean.valueOf(typeDictionary.isThrowSpecificExceptionTypes()));
        TextRange textRange = null;
        if (typeDictionary.isTestSubject(srcClass) && typeDictionary.shouldCheckMethodCall(psiMethod)) {
            textRange = (TextRange) ApplicationManager.getApplication().runReadAction(() -> {
                return psiMethod.getBody() == null ? psiMethod.getTextRange() : psiMethod.getBody().getTextRange();
            });
        }
        Method method = new Method(methodId2, methodName, returnType, ownerClassCanonicalType, methodParams, throwsExceptions, isPrivate, isProtected, isDefault, isPublic, isAbstract, isNative, isStatic, isSetter, isGetter, isConstructor, overriddenInChild, inherited, isInterface, syntheticMethod, propertyName1, accessible, isPrimaryConstructor, testable, null, textRange);
        method.getCaseResults().addAll(caseResults);
        methodIdCaches.put(methodId2, method);
        return method;
    }

    public static Method createMethod(ResolvedMethodCall methodCall, int maxRecursionDepth, TypeDictionary typeDictionary, @Nullable PsiType ownerClassPsiType) {
        PsiMethod psiMethod = methodCall.getPsiMethod();
        String methodId = methodCall.getMethodId();
        String methodName = psiMethod.getName();
        PsiClass srcClass = methodCall.getPsiClass();
        String methodId2 = methodId == null ? PsiUtils.formatMethodId(methodCall.getPsiClass(), methodName, psiMethod.getParameterList().getParameters()) : methodId;
        Optional<PsiSubstitutor> methodSubstitutor = findMethodSubstitutor(psiMethod, srcClass, ownerClassPsiType);
        Type returnType = methodCall.getReturnType() == null ? resolveReturnType(psiMethod, maxRecursionDepth, typeDictionary, methodSubstitutor) : typeDictionary.getType(methodCall.getReturnType(), maxRecursionDepth, true, null);
        if (methodIdCaches.containsKey(methodId2)) {
            Method cacheMethod = (Method) methodIdCaches.get(methodId2);
            if (cacheMethod.hasReturn() && cacheMethod.getReturnType().equals(returnType)) {
                cacheMethod.getCaseResults().clear();
                return cacheMethod;
            }
        }
        boolean isPrivate = psiMethod.hasModifierProperty("private");
        boolean isProtected = psiMethod.hasModifierProperty("protected");
        boolean isDefault = isDefault(psiMethod);
        boolean isPublic = psiMethod.hasModifierProperty("public");
        boolean isAbstract = psiMethod.hasModifierProperty("abstract");
        boolean isNative = psiMethod.hasModifierProperty("native");
        boolean isStatic = psiMethod.hasModifierProperty("static");
        String ownerClassCanonicalType = resolveOwnerClassName(psiMethod);
        boolean isConstructor = psiMethod.isConstructor();
        boolean isPrimaryConstructor = isConstructor && psiMethod.getClass().getSimpleName().contains("PrimaryConstructor");
        boolean isSetter = PropertyUtils.isPropertySetter(psiMethod);
        boolean isGetter = PropertyUtils.isPropertyGetter(psiMethod);
        String propertyName1 = ClassNameUtils.extractTargetPropertyName(methodName, isSetter, isGetter);
        boolean overriddenInChild = isOverriddenInChild(psiMethod, srcClass);
        boolean inherited = isInherited(psiMethod, srcClass);
        boolean isInterface = isInterface(psiMethod);
        boolean accessible = typeDictionary.isAccessible(psiMethod);
        boolean syntheticMethod = isSyntheticMethod(psiMethod);
        boolean testable = isTestable(psiMethod, srcClass);
        List<CaseResult> caseResults = getLocalData(srcClass, ownerClassCanonicalType, ownerClassPsiType, methodName);
        List<Param> methodParams = new ArrayList<>();
        for (PsiParameter psiParameter : psiMethod.getParameterList().getParameters()) {
            ArrayList<Field> assignedToFields = findMatchingFields(psiParameter, psiMethod);
            Optional<PsiType> substitutedType = methodCall.getMethodCallArguments().stream().filter(methodCallArg -> {
                return methodCallArg.getName().contains(psiParameter.getName());
            }).map((v0) -> {
                return v0.getType();
            }).findFirst();
            Param param = new Param(psiParameter, substitutedType, typeDictionary, maxRecursionDepth, assignedToFields, true);
            methodParams.add(param);
        }
        String throwsExceptions = extractMethodExceptionTypes(psiMethod, Boolean.valueOf(typeDictionary.isThrowSpecificExceptionTypes()));
        TextRange textRange = null;
        if (typeDictionary.isTestSubject(srcClass) && typeDictionary.shouldCheckMethodCall(psiMethod)) {
            textRange = (TextRange) ApplicationManager.getApplication().runReadAction(() -> {
                return psiMethod.getBody() == null ? psiMethod.getTextRange() : psiMethod.getBody().getTextRange();
            });
        }
        Method method = new Method(methodId2, methodName, returnType, ownerClassCanonicalType, methodParams, throwsExceptions, isPrivate, isProtected, isDefault, isPublic, isAbstract, isNative, isStatic, isSetter, isGetter, isConstructor, overriddenInChild, inherited, isInterface, syntheticMethod, propertyName1, accessible, isPrimaryConstructor, testable, CollectionUtils.isEmpty(methodCall.getMethodCallArguments()) ? null : (List) methodCall.getMethodCallArguments().stream().map((v0) -> {
            return v0.getText();
        }).collect(Collectors.toList()), textRange);
        method.getCaseResults().addAll(caseResults);
        methodIdCaches.put(methodId2, method);
        return method;
    }

    public static void resolveInternalReferences(@NotNull TypeDictionary typeDictionary, PsiMethod psiMethod, Method method, PsiClass psiClass, Set<String> staticCallClassNames, int maxRecursionDepth) {
        if (typeDictionary == null) {
            $$$reportNull$$$0(0);
        }
        if (Objects.isNull(method.getCaseBranchSet())) {
            ResolvedBranch resolvedBranch = JavaPsiUtils.findSpecialElementsInMethod(psiMethod);
            method.setCaseBranchSet(resolvedBranch);
        }
        if (CollectionUtils.isEmpty(method.getMethodCalls())) {
            method.getMethodCalls().addAll(resolveCalledMethods(psiMethod, psiClass, method, typeDictionary, method.getDirectMethodCalls(), method.getStaticMethodCalls(), staticCallClassNames, maxRecursionDepth));
        }
        if (CollectionUtils.isEmpty(method.getInternalReferences())) {
            method.getInternalReferences().addAll(resolveReferences(psiMethod, typeDictionary));
        }
        if (CollectionUtils.isEmpty(method.getMethodReferences())) {
            method.getMethodReferences().addAll(resolveMethodReferences(psiMethod, typeDictionary));
        }
        Set<String> exceptions = JavaPsiUtils.findThrowException(psiMethod);
        method.setExceptions(exceptions);
        method.resolveExceptions();
    }

    public static boolean hasInternalMethodCall(Method method, Type testedClass) {
        return method.getMethodCalls().stream().anyMatch(methodCall -> {
            return testedClass.getMethods().stream().anyMatch(classMethod -> {
                return classMethod.getMethodId().equals(methodCall.getMethod().getMethodId());
            });
        });
    }

    public static boolean hasInternalMethod(Method method, Type testedClass) {
        return testedClass.getMethods().stream().anyMatch(m -> {
            return m.getMethodId().equals(method.getMethodId());
        });
    }

    public static List<ResolvedMethodCall> resolvedMethodCalls(PsiMethod psiMethod) {
        return JavaPsiUtils.findResolvedMethodCalls(psiMethod);
    }

    private static Set<Reference> resolveReferences(PsiMethod psiMethod, TypeDictionary typeDictionary) {
        Set<Reference> references = new HashSet<>();
        for (ResolvedReference resolvedReference : JavaPsiUtils.findReferences(psiMethod)) {
            references.add(new Reference(resolvedReference.getReferenceName(), resolvedReference.getRefType(), resolvedReference.getPsiOwnerType(), typeDictionary));
        }
        return references;
    }

    private static Set<Method> resolveMethodReferences(PsiMethod psiMethod, TypeDictionary typeDictionary) {
        Set<Method> methodReferences = new HashSet<>();
        for (PsiMethod resolvedMethodReference : JavaPsiUtils.findMethodReferences(psiMethod)) {
            if (PropertyUtils.isPropertyGetter(resolvedMethodReference) || typeDictionary.isRelevant(resolvedMethodReference, null)) {
                PsiUtils.formatMethodId(resolvedMethodReference.getContainingClass(), resolvedMethodReference.getName(), psiMethod.getParameterList().getParameters());
                methodReferences.add(createMethod(resolvedMethodReference, resolvedMethodReference.getContainingClass(), null, 1, typeDictionary, null, null));
            }
        }
        return methodReferences;
    }

    private static Set<MethodCall> resolveCalledMethods(PsiMethod psiMethod, PsiClass psiClass, Method method, TypeDictionary typeDictionary, Set<MethodCall> directMethodCalls, Set<StaticMethodCall> staticMethodCalls, Set<String> staticClassNames, int maxRecursionDepth) {
        Set<MethodCall> allMethodCalls = new HashSet<>();
        if (maxRecursionDepth > 0) {
            List<ResolvedMethodCall> methodCalls = resolvedMethodCalls(psiMethod);
            String canonicalName = psiClass != null ? psiClass.getQualifiedName() : "";
            if (StringUtils.isBlank(canonicalName)) {
                return allMethodCalls;
            }
            for (ResolvedMethodCall resolvedMethodCall : methodCalls) {
                if (typeDictionary.isTestSubject(resolvedMethodCall.getPsiClass()) || typeDictionary.isRelevant(resolvedMethodCall.getPsiMethod(), resolvedMethodCall.getPsiClass())) {
                    String resolvedMethodCallOwnerClassName = resolvedMethodCall.getPsiClass() == null ? "" : resolvedMethodCall.getPsiClass().getQualifiedName();
                    if (resolvedMethodCallOwnerClassName != null) {
                        maxRecursionDepth = resolvedMethodCallOwnerClassName.equals(canonicalName) ? Math.max(maxRecursionDepth - 1, 1) : maxRecursionDepth;
                        Method calledMethod = createMethod(resolvedMethodCall, maxRecursionDepth, typeDictionary, null);
                        MethodCall methodCall = new MethodCall(calledMethod, resolvedMethodCall.getReturnParamName(), convertArgs(resolvedMethodCall.getMethodCallArguments()));
                        allMethodCalls.add(methodCall);
                        if (calledMethod.getMethodId().startsWith("java.lang.Class#getMethod(java.lang.String") && !resolvedMethodCall.getMethodCallArguments().isEmpty()) {
                            method.getReflectionMethods().add(resolvedMethodCall.getMethodCallArguments().get(0).getName());
                        }
                        if (calledMethod.isStatic()) {
                            Type staticType = typeDictionary.getType(resolvedMethodCall.getPsiClass(), 1, false);
                            if (calledMethod.isPublic() && checkStaticType(staticType, canonicalName) && !staticType.typeEquals(calledMethod.getReturnType()) && !TypeUtils.isNoMockStaticType(calledMethod.getMethodId(), calledMethod.getReturnType()) && calledMethod.hasReturn() && staticMethodCalls.stream().noneMatch(mc -> {
                                return mc.getMethod().getMethodId().equals(calledMethod.getMethodId());
                            })) {
                                staticClassNames.add(staticType.getCanonicalName());
                                StaticMethodCall staticMethodCall = new StaticMethodCall(staticType.getCanonicalName(), resolvedMethodCall.getReturnParamName(), calledMethod, convertArgs(resolvedMethodCall.getMethodCallArguments()));
                                staticMethodCalls.add(staticMethodCall);
                            }
                        }
                        if (typeDictionary.isRelevant(resolvedMethodCall.getPsiMethod(), resolvedMethodCall.getMethodId(), resolvedMethodCall.getPsiClass())) {
                            directMethodCalls.add(methodCall);
                        }
                    }
                }
            }
        }
        return allMethodCalls;
    }

    private static boolean checkStaticType(Type staticType, String ownerCanonicalName) {
        return (staticType == null || staticType.isEnum() || staticType.isInterface() || TypeUtils.isBasicType(staticType.getCanonicalName()) || staticType.getCanonicalName().equals(ownerCanonicalName)) ? false : true;
    }

    @Nullable
    private static Type resolveReturnType(PsiMethod psiMethod, int maxRecursionDepth, TypeDictionary typeDictionary, Optional<PsiSubstitutor> methodSubstitutor) {
        PsiType psiType = psiMethod.getReturnType();
        if (psiType == null) {
            return null;
        }
        Optional<U> map = methodSubstitutor.map(psiSubstitutor -> {
            return psiSubstitutor.substitute(psiType);
        });
        PsiClass genericType = extractGenericType(psiMethod, psiType.getCanonicalText());
        if (genericType != null) {
            return typeDictionary.getType(genericType, maxRecursionDepth, true);
        }
        return typeDictionary.getType((PsiType) map.orElse(psiType), maxRecursionDepth, true, null);
    }

    private static List<MethodCallArgument> convertArgs(List<MethodCallArg> methodCallArguments) {
        ArrayList<MethodCallArgument> methodCallArgs = new ArrayList<>();
        if (methodCallArguments != null) {
            for (MethodCallArg methodCallArgument : methodCallArguments) {
                methodCallArgs.add(new MethodCallArgument(methodCallArgument.getText()));
            }
        }
        return methodCallArgs;
    }

    private static PsiField resolveLeftHandExpressionAsField(PsiExpression expr, PsiParameter parameter) {
        PsiAssignmentExpression skipParentsOfType = PsiTreeUtil.skipParentsOfType(expr, new Class[]{PsiParenthesizedExpression.class});
        if (skipParentsOfType instanceof PsiAssignmentExpression) {
            PsiAssignmentExpression psiAssignmentExpression = skipParentsOfType;
            PsiReference reference = psiAssignmentExpression.getLExpression().getReference();
            PsiElement element = reference != null ? reference.resolve() : null;
            if (element instanceof PsiField) {
                return (PsiField) element;
            }
            return null;
        }
        return resolveUsedExpressionAsField(expr, parameter);
    }

    private static PsiField resolveUsedExpressionAsField(PsiExpression expression, PsiParameter parameter) {
        if (expression instanceof PsiReferenceExpression) {
            PsiReferenceExpression referenceExpression = (PsiReferenceExpression) expression;
            PsiParameter resolve = referenceExpression.resolve();
            if (resolve instanceof PsiParameter) {
                PsiParameter resolvedParameter = resolve;
                if (resolvedParameter.equals(parameter)) {
                    PsiReferenceExpression parent = referenceExpression.getParent();
                    if (parent instanceof PsiReferenceExpression) {
                        PsiReferenceExpression parentReference = parent;
                        PsiMethod resolve2 = parentReference.resolve();
                        if (resolve2 instanceof PsiMethod) {
                            Boolean isGetter = Boolean.valueOf(PropertyUtils.isPropertyGetter(resolve2));
                            if (isGetter.booleanValue()) {
                                PsiReferenceExpression fieldReferenceExpression = PsiTreeUtil.findChildOfAnyType(resolve2, new Class[]{PsiReferenceExpression.class});
                                PsiField resolve3 = fieldReferenceExpression.resolve();
                                if (resolve3 instanceof PsiField) {
                                    return resolve3;
                                }
                                return null;
                            }
                            return null;
                        }
                        return null;
                    }
                    return null;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    private static List<Param> extractMethodParams(PsiMethod psiMethod, boolean shouldResolveAllMethods, int maxRecursionDepth, TypeDictionary typeDictionary, Optional<PsiSubstitutor> methodSubstitutor) {
        ArrayList<Param> params = new ArrayList<>();
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        for (PsiParameter psiParameter : parameters) {
            Optional<U> map = methodSubstitutor.map(psiSubstitutor -> {
                return psiSubstitutor.substitute(psiParameter.getType());
            });
            ArrayList<Field> assignedToFields = findMatchingFields(psiParameter, psiMethod);
            Param param = new Param(psiParameter, map, typeDictionary, maxRecursionDepth, assignedToFields, shouldResolveAllMethods);
            params.add(param);
        }
        return params;
    }

    private static String extractMethodExceptionTypes(PsiMethod psiMethod, Boolean throwSpecificExceptionTypes) {
        if (throwSpecificExceptionTypes.booleanValue()) {
            PsiReferenceList throwsList = psiMethod.getThrowsList();
            PsiClassType[] referencedTypes = throwsList.getReferencedTypes();
            String throwsExceptions = (String) Arrays.stream(referencedTypes).map((v0) -> {
                return v0.resolve();
            }).filter((v0) -> {
                return Objects.nonNull(v0);
            }).map((v0) -> {
                return v0.getQualifiedName();
            }).filter((v0) -> {
                return Objects.nonNull(v0);
            }).distinct().collect(Collectors.joining(","));
            if (throwsExceptions.isEmpty()) {
                return null;
            }
            return throwsExceptions;
        }
        return "Exception";
    }

    private static ArrayList<Field> findMatchingFields(PsiParameter psiParameter, PsiMethod psiMethod) {
        ArrayList<Field> fields = new ArrayList<>();
        try {
            if (!psiMethod.hasModifierProperty("static")) {
                ApplicationManager.getApplication().invokeLater(() -> {
                    LocalSearchScope searchScope = new LocalSearchScope(psiMethod);
                    Query<PsiReference> search = ReferencesSearch.search(psiParameter, searchScope);
                    Collection<PsiReference> psiReferences = search.findAll();
                    for (PsiReference reference : psiReferences) {
                        PsiExpression element = reference.getElement();
                        PsiField psiField = null;
                        if ((element instanceof PsiExpression) && !PsiUtil.isOnAssignmentLeftHand(element)) {
                            psiField = resolveLeftHandExpressionAsField(element, psiParameter);
                        }
                        if (psiField != null && psiField.getContainingClass() != null) {
                            fields.add(new Field(psiField, psiField.getContainingClass(), null, 0));
                        }
                    }
                });
            }
        } catch (Throwable e) {
            LOG.warn(String.format("cant search for matching fields for parameter %s in method %s", psiParameter.getName(), psiMethod.getName()), e);
        }
        return fields;
    }

    private static boolean isInterface(PsiMethod psiMethod) {
        return psiMethod.hasModifierProperty("abstract");
    }

    private static boolean isSyntheticMethod(PsiMethod psiMethod) {
        return psiMethod instanceof SyntheticElement;
    }

    private static boolean hasGenericType(PsiMethod psiMethod) {
        return Stream.concat(Stream.of((Object[]) psiMethod.getParameterList().getParameters()).map((v0) -> {
            return v0.getType();
        }), Stream.of(psiMethod.getReturnType())).anyMatch(MethodFactory::mayContainTypeParameter);
    }

    private static boolean mayContainTypeParameter(PsiType psiType) {
        return psiType instanceof PsiClassReferenceType;
    }

    private static boolean isOverriddenInChild(PsiMethod method, @Nullable PsiClass srcClass) {
        if (srcClass == null) {
            return false;
        }
        String srcQualifiedName = srcClass.getQualifiedName();
        String methodClsQualifiedName = method.getContainingClass() == null ? null : method.getContainingClass().getQualifiedName();
        if (srcQualifiedName == null || methodClsQualifiedName == null || srcQualifiedName.equals(methodClsQualifiedName)) {
            return false;
        }
        PsiMethod childMethod = MethodSignatureUtil.findMethodBySuperMethod(srcClass, method, false);
        return childMethod != null;
    }

    public static boolean isInherited(PsiMethod method, @Nullable PsiClass srcClass) {
        if (srcClass == null) {
            return false;
        }
        String srcQualifiedName = srcClass.getQualifiedName();
        String methodClsQualifiedName = method.getContainingClass() == null ? null : method.getContainingClass().getQualifiedName();
        return (srcQualifiedName == null || methodClsQualifiedName == null || srcQualifiedName.equals(methodClsQualifiedName)) ? false : true;
    }

    private static boolean isDefault(PsiMethod psiMethod) {
        return psiMethod.hasModifierProperty("default") || psiMethod.hasModifierProperty("packageLocal");
    }

    @Nullable
    private static String resolveOwnerClassName(PsiMethod psiMethod) {
        if (psiMethod.getContainingClass() == null) {
            return null;
        }
        return psiMethod.getContainingClass().getQualifiedName();
    }

    public static boolean isTestable(PsiMethod psiMethod, @Nullable PsiClass srcClass) {
        boolean isNotBase = !TypeUtils.isLanguageBaseClass(resolveOwnerClassName(psiMethod));
        PropertyUtils.isPropertySetter(psiMethod);
        boolean isNotEnum = srcClass == null || !srcClass.isEnum();
        boolean isNotConstructor = !psiMethod.isConstructor() && isVisibleForTest(psiMethod, srcClass);
        boolean isNotOverridden = !isOverriddenInChild(psiMethod, srcClass);
        boolean isNotInterface = !isInterface(psiMethod);
        boolean isNotAbstract = !psiMethod.hasModifierProperty("abstract");
        boolean isNotNative = !psiMethod.hasModifierProperty("native");
        return isNotBase && isNotEnum && isNotConstructor && isNotOverridden && isNotInterface && isNotAbstract && isNotNative && 1 != 0;
    }

    private static boolean isVisibleForTest(PsiMethod psiMethod, PsiClass srcClass) {
        return ((isDefault(psiMethod) || psiMethod.hasModifierProperty("protected")) && !isInherited(psiMethod, srcClass)) || psiMethod.hasModifierProperty("public") || psiMethod.hasModifierProperty("private");
    }

    public static PsiClass extractGenericType(PsiMethod method, String canonicalText) {
        if (method.hasTypeParameters()) {
            PsiTypeParameter[] typeParameters = method.getTypeParameters();
            if (typeParameters.length == 1) {
                PsiTypeParameter typeParameter = typeParameters[0];
                String parameterName = typeParameter.getName();
                PsiClassType[] bounds = typeParameter.getExtendsListTypes();
                for (PsiClassType bound : bounds) {
                    PsiClass boundClass = bound.resolve();
                    if (boundClass != null && StringUtils.isNotBlank(parameterName) && parameterName.equals(canonicalText)) {
                        return boundClass;
                    }
                }
                return null;
            }
            return null;
        }
        return null;
    }

    private static Optional<PsiSubstitutor> findMethodSubstitutor(PsiMethod psiMethod, PsiClass srcClass, @Nullable PsiType ownerClassPsiType) {
        String ownerName = ownerClassPsiType != null ? ownerClassPsiType.getCanonicalText() : null;
        String srcName = srcClass != null ? srcClass.getQualifiedName() : null;
        if (isInherited(psiMethod, srcClass) && srcName != null && ownerName == null) {
            List<Pair<PsiMethod, PsiSubstitutor>> methodsSubstitutors = srcClass.findMethodsAndTheirSubstitutorsByName(psiMethod.getName(), true);
            return methodsSubstitutors.stream().filter(psiMethodPsiSubstitutorPair -> {
                return ((PsiMethod) psiMethodPsiSubstitutorPair.first).equals(psiMethod);
            }).map(psiMethodPsiSubstitutorPair2 -> {
                return (PsiSubstitutor) psiMethodPsiSubstitutorPair2.second;
            }).findFirst();
        }
        if (isInherited(psiMethod, srcClass) && isTestable(psiMethod, srcClass) && hasGenericType(psiMethod)) {
            List<Pair<PsiMethod, PsiSubstitutor>> methodsSubstitutors2 = srcClass.findMethodsAndTheirSubstitutorsByName(psiMethod.getName(), true);
            return methodsSubstitutors2.stream().filter(psiMethodPsiSubstitutorPair3 -> {
                return ((PsiMethod) psiMethodPsiSubstitutorPair3.first).equals(psiMethod);
            }).map(psiMethodPsiSubstitutorPair4 -> {
                return (PsiSubstitutor) psiMethodPsiSubstitutorPair4.second;
            }).findFirst();
        }
        if (ownerClassPsiType instanceof PsiClassType) {
            return Optional.of(((PsiClassType) ownerClassPsiType).resolveGenerics().getSubstitutor());
        }
        return Optional.empty();
    }
}
