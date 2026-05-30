package com.aicode.template.context.domain;

import com.aicode.template.TypeDictionary;
import com.aicode.template.builder.MethodFactory;
import com.aicode.template.context.domain.annotion.DiClassAnnotationEnum;
import com.aicode.template.context.domain.annotion.SpringFieldAnnotationEnum;
import com.aicode.util.ClassNameUtils;
import com.aicode.util.JavaPsiUtils;
import com.aicode.util.NewFileUtils;
import com.aicode.util.PropertyUtils;
import com.aicode.util.PsiUtils;
import com.aicode.util.StringUtils;
import com.aicode.util.TypeUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/Type.class */
public class Type {
    private static final Logger LOG = Logger.getInstance(Type.class.getName());
    private final String canonicalName;
    private final String name;
    private final boolean isPrimitive;
    private final String packageName;
    private final List<Type> composedTypes;
    private final boolean array;
    private final int arrayDimensions;
    private final boolean varargs;
    private final boolean isEnum;
    private final List<String> enumValues;
    private final boolean isInterface;
    private final boolean isAbstract;
    private final boolean isStatic;
    private final boolean isFinal;
    private final Type parentContainerClass;
    private final String superClass;
    private boolean dependenciesResolved;
    private boolean hasDefaultConstructor;
    private final List<Method> methods;
    private final Set<String> staticClassNames;
    private final Set<Field> fields;
    private final List<Type> implementedInterfaces;
    private final boolean isAnnotatedByDI;
    private boolean resolved;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 1:
            case 2:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 0:
            default:
                i2 = 2;
                break;
            case 1:
            case 2:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            default:
                objArr[0] = "com/aicode/template/context/domain/Type";
                break;
            case 1:
            case 2:
                objArr[0] = "psiClass";
                break;
        }
        switch (i) {
            case 0:
            default:
                objArr[1] = "resolveType";
                break;
            case 1:
            case 2:
                objArr[1] = "com/aicode/template/context/domain/Type";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "resolveFields";
                break;
            case 2:
                objArr[2] = "resolveImplementedInterfaces";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            default:
                throw new IllegalStateException(format);
            case 1:
            case 2:
                throw new IllegalArgumentException(format);
        }
    }

    public boolean typeEquals(Type to) {
        if (to == null) {
            return false;
        }
        if (equals(to)) {
            return true;
        }
        return ClassNameUtils.extractContainerType(to.getCanonicalName()).equalsIgnoreCase(ClassNameUtils.extractContainerType(this.canonicalName));
    }

    public Type(PsiType psiType, @Nullable Object typePsiElement, @Nullable TypeDictionary typeDictionary, int maxRecursionDepth, boolean shouldResolveAllMethods) {
        boolean endsWith;
        boolean endsWith2;
        this.dependenciesResolved = false;
        this.hasDefaultConstructor = false;
        this.staticClassNames = new HashSet();
        this.implementedInterfaces = new ArrayList();
        this.resolved = false;
        String canonicalText = ClassNameUtils.resolveCanonicalName(psiType, typePsiElement);
        endsWith = canonicalText.endsWith(NewFileUtils.H("l8"));
        this.array = endsWith;
        this.arrayDimensions = ClassNameUtils.arrayDimensions(canonicalText);
        endsWith2 = canonicalText.endsWith(NewFileUtils.H("U\u0013A"));
        this.varargs = endsWith2;
        String cName = StringUtils.trim(ClassNameUtils.stripArrayVarargsDesignator(canonicalText, this.array));
        if (cName.length() == 1) {
            PsiClass psiClass = PsiUtil.resolveClassInType(psiType);
            if (psiClass != null && psiClass.getSuperClass() != null && psiClass.getSuperClass().getQualifiedName() != null) {
                cName = psiClass.getSuperClass().getQualifiedName();
            }
            if (cName.length() == 1) {
                cName = "java.lang.Object";
            }
        }
        this.name = ClassNameUtils.extractClassName(ClassNameUtils.stripArrayVarargsDesignator(psiType.getPresentableText()));
        this.packageName = ClassNameUtils.extractPackageName(cName);
        this.isPrimitive = psiType instanceof PsiPrimitiveType;
        this.composedTypes = TypeUtils.isStringType(cName) ? new ArrayList<>() : resolveTypes(psiType, typeDictionary, maxRecursionDepth);
        this.canonicalName = StringUtils.trim(resetComposedType(cName));
        PsiClass psiClass2 = PsiUtil.resolveClassInType(psiType);
        this.isEnum = JavaPsiUtils.resolveIfEnum(psiClass2);
        this.isInterface = psiClass2 != null && psiClass2.isInterface();
        this.isAnnotatedByDI = psiClass2 != null && buildAnnotatedByDi(psiClass2, typeDictionary);
        this.isAbstract = (psiClass2 == null || psiClass2.getModifierList() == null || !psiClass2.getModifierList().hasModifierProperty("abstract")) ? false : true;
        this.isStatic = hasModifier(psiClass2, "static") || (psiClass2 != null && "org.jetbrains.plugins.scala.lang.psi.impl.toplevel.typedef.ScObjectImpl".equals(psiClass2.getClass().getCanonicalName()));
        if (psiClass2 != null && psiClass2.getSuperClass() != null && typeDictionary != null) {
            this.superClass = getCanonicalName(psiClass2.getSuperClass().getQualifiedName(), psiClass2.getSuperClass(), false);
        } else {
            this.superClass = "";
        }
        this.parentContainerClass = (psiClass2 == null || psiClass2.getParent() == null || !(psiClass2.getParent() instanceof PsiClass) || typeDictionary == null) ? null : typeDictionary.getType((PsiType) resolveType(psiClass2.getParent()), maxRecursionDepth, false);
        this.fields = new LinkedHashSet();
        this.enumValues = JavaPsiUtils.resolveJavaEnumValues(psiClass2);
        this.methods = new ArrayList();
        this.isFinal = isFinalType(psiClass2);
    }

    public Type(PsiClass psiClass, TypeDictionary typeDictionary, int maxRecursionDepth, boolean shouldResolveAllMethods) {
        boolean endsWith;
        boolean endsWith2;
        this.dependenciesResolved = false;
        this.hasDefaultConstructor = false;
        this.staticClassNames = new HashSet();
        this.implementedInterfaces = new ArrayList();
        this.resolved = false;
        String canonicalText = ClassNameUtils.resolveCanonicalName(psiClass, null);
        endsWith = canonicalText.endsWith(NewFileUtils.H("l8"));
        this.array = endsWith;
        this.arrayDimensions = ClassNameUtils.arrayDimensions(canonicalText);
        endsWith2 = canonicalText.endsWith(NewFileUtils.H("U\u0013A"));
        this.varargs = endsWith2;
        this.canonicalName = getCanonicalName(canonicalText, psiClass, this.array);
        this.name = psiClass.getQualifiedName() == null ? null : ClassNameUtils.extractClassName(ClassNameUtils.stripArrayVarargsDesignator(psiClass.getQualifiedName()));
        this.packageName = ClassNameUtils.extractPackageName(this.canonicalName);
        this.isPrimitive = false;
        this.composedTypes = new ArrayList();
        this.isEnum = psiClass.isEnum();
        this.isInterface = psiClass.isInterface();
        this.isAnnotatedByDI = buildAnnotatedByDi(psiClass, typeDictionary);
        this.isAbstract = psiClass.getModifierList() != null && psiClass.getModifierList().hasModifierProperty("abstract");
        this.isStatic = psiClass.getModifierList() != null && psiClass.getModifierList().hasExplicitModifier("static");
        this.parentContainerClass = (psiClass.getParent() == null || !(psiClass.getParent() instanceof PsiClass) || typeDictionary == null) ? null : typeDictionary.getType((PsiType) resolveType(psiClass.getParent()), maxRecursionDepth, false);
        if (psiClass.getSuperClass() != null && typeDictionary != null) {
            this.superClass = getCanonicalName(psiClass.getSuperClass().getQualifiedName(), psiClass.getSuperClass(), false);
        } else {
            this.superClass = "";
        }
        this.fields = new LinkedHashSet();
        this.enumValues = JavaPsiUtils.resolveJavaEnumValues(psiClass);
        this.methods = new ArrayList();
        this.isFinal = isFinalType(psiClass);
    }

    public Type(String canonicalName, String name, String packageName, boolean isPrimitive, boolean isInterface, boolean isAbstract, boolean array, int arrayDimensions, boolean varargs, List<Type> composedTypes) {
        this.dependenciesResolved = false;
        this.hasDefaultConstructor = false;
        this.staticClassNames = new HashSet();
        this.implementedInterfaces = new ArrayList();
        this.resolved = false;
        this.canonicalName = canonicalName;
        this.name = name;
        this.isPrimitive = isPrimitive;
        this.packageName = packageName;
        this.isInterface = isInterface;
        this.isAbstract = isAbstract;
        this.array = array;
        this.arrayDimensions = arrayDimensions;
        this.varargs = varargs;
        this.composedTypes = composedTypes;
        this.enumValues = new ArrayList();
        this.isEnum = false;
        this.methods = new ArrayList();
        this.fields = new LinkedHashSet();
        this.parentContainerClass = null;
        this.superClass = "";
        this.isStatic = false;
        this.isFinal = false;
        this.isAnnotatedByDI = false;
    }

    private String resetComposedType(String canonicalName) {
        if (this.composedTypes != null && !this.composedTypes.isEmpty() && canonicalName.indexOf("<") > 0) {
            String name = ClassNameUtils.extractContainerType(canonicalName);
            if (this.composedTypes.size() == 1) {
                name = name + "<" + this.composedTypes.get(0).getCanonicalName() + ">";
            } else if (canonicalName.split("<").length == 2) {
                String comTypes = canonicalName.substring(canonicalName.indexOf("<") + 1, canonicalName.lastIndexOf(">"));
                List<String> newNames = new ArrayList<>();
                String[] comTypeNames = comTypes.split(",");
                for (String n : comTypeNames) {
                    if (!n.contains(".")) {
                        Optional<Type> t = this.composedTypes.stream().filter(c -> {
                            return c.getName().equals(n);
                        }).findFirst();
                        newNames.add(t.isPresent() ? t.get().getCanonicalName() : n);
                    } else {
                        newNames.add(n);
                    }
                }
                return name + "<" + String.join(",", newNames) + ">";
            }
            return name;
        }
        return canonicalName;
    }

    private String getCanonicalName(String canonicalText, PsiClass psiClass, boolean array) {
        String cName = StringUtils.trim(ClassNameUtils.stripArrayVarargsDesignator(canonicalText, array));
        if (cName.length() == 1) {
            if (psiClass.getSuperClass() != null && psiClass.getSuperClass().getQualifiedName() != null) {
                cName = psiClass.getSuperClass().getQualifiedName();
            }
            if (cName.length() == 1) {
                cName = "java.lang.Object";
            }
        }
        return StringUtils.trim(resetComposedType(cName));
    }

    @NotNull
    public static PsiClassType resolveType(PsiClass psiClass) {
        PsiClassType createType = JavaPsiFacade.getInstance(psiClass.getProject()).getElementFactory().createType(psiClass);
        if (createType == null) {
            $$$reportNull$$$0(0);
        }
        return createType;
    }

    public void resolveDependencies(@Nullable TypeDictionary typeDictionary, int maxRecursionDepth, PsiType psiType, boolean shouldResolveAllMethods) {
        PsiClass psiClass = PsiUtil.resolveClassInType(psiType);
        if (psiClass != null && maxRecursionDepth > 0 && typeDictionary != null) {
            if (psiClass.getConstructors().length == 0) {
                this.hasDefaultConstructor = true;
            }
            for (PsiMethod psiMethod : psiClass.getAllMethods()) {
                try {
                    if (isPropertyRelated(psiMethod) || psiMethod.isConstructor() || typeDictionary.isRelevant(psiMethod, psiClass)) {
                        String methodId = PsiUtils.formatMethodId(psiClass, psiMethod.getName(), psiMethod.getParameterList().getParameters());
                        Method method = this.methods.stream().filter(m -> {
                            return m.getMethodId().equals(methodId);
                        }).findFirst().orElse(null);
                        if (method == null) {
                            method = MethodFactory.createMethod(psiMethod, psiClass, null, maxRecursionDepth - 1, typeDictionary, psiType, null);
                            this.methods.add(method);
                        }
                        if (typeDictionary.shouldCheckMethodCall(psiMethod) && (typeDictionary.isTestSubject(psiClass) || typeDictionary.isRelevant(psiMethod, psiClass))) {
                            MethodFactory.resolveInternalReferences(typeDictionary, psiMethod, method, psiClass, this.staticClassNames, maxRecursionDepth);
                        }
                    }
                } catch (ProcessCanceledException e) {
                    LOG.info("手动取消PSI解析");
                } catch (Throwable e2) {
                    LOG.info(e2.getMessage());
                }
            }
            if (psiClass.getQualifiedName() != null && !TypeUtils.isLanguageBaseClass(psiClass.getQualifiedName()) && !TypeUtils.isBasicType(psiClass.getQualifiedName())) {
                resolveFields(psiClass, typeDictionary, maxRecursionDepth - 1);
            }
            resolveImplementedInterfaces(psiClass, typeDictionary, shouldResolveAllMethods, maxRecursionDepth - 1);
            this.dependenciesResolved = true;
        }
    }

    private boolean isPropertyRelated(PsiMethod psiMethod) {
        return (PropertyUtils.isPropertySetter(psiMethod) || PropertyUtils.isPropertyGetter(psiMethod)) && !isGroovyLangProperty(psiMethod);
    }

    private void resolveFields(@NotNull PsiClass psiClass, TypeDictionary typeDictionary, int maxRecursionDepth) {
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        for (PsiField psiField : psiClass.getAllFields()) {
            if (!"groovy.lang.MetaClass".equals(psiField.getType().getCanonicalText()) && !isTestOfField(psiField)) {
                String canonicalName = psiField.getType().getCanonicalText();
                boolean addField = StringUtils.isNotEmpty(canonicalName) && this.fields.stream().noneMatch(field -> {
                    return field.getType() != null && field.getType().getCanonicalName().equalsIgnoreCase(canonicalName);
                });
                boolean ownField = typeDictionary.isTestSubject(psiClass) && Objects.equals(psiClass.getQualifiedName(), canonicalName);
                if (addField && !ownField) {
                    Field field2 = new Field(psiField, psiClass, typeDictionary, maxRecursionDepth);
                    if (field2.getType() != null) {
                        long matchCount = this.fields.stream().filter(field1 -> {
                            return field1.getName().equalsIgnoreCase(field2.getName());
                        }).count();
                        if (matchCount > 0) {
                            field2.setName(field2.getName() + matchCount);
                        }
                        this.fields.add(field2);
                    }
                }
            }
        }
    }

    private boolean isTestOfField(PsiField psiField) {
        if (psiField == null) {
            return true;
        }
        return Arrays.stream(psiField.getAnnotations()).anyMatch(ann -> {
            return TypeUtils.isTestAnnotation(ann.getQualifiedName());
        });
    }

    private void resolveImplementedInterfaces(@NotNull PsiClass psiClass, TypeDictionary typeDictionary, boolean shouldResolveAllMethods, int maxRecursionDepth) {
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        for (PsiType psiType : psiClass.getImplementsListTypes()) {
            this.implementedInterfaces.add(new Type(psiType, null, typeDictionary, maxRecursionDepth, shouldResolveAllMethods));
        }
    }

    private boolean isFinalType(PsiClass aClass) {
        return hasModifier(aClass, "final");
    }

    private boolean isGroovyLangProperty(PsiMethod method) {
        PsiParameter[] parameters = method.getParameterList().getParameters();
        if (parameters.length == 0) {
            return false;
        }
        PsiParameter psiParameter = parameters[0];
        return "groovy.lang.MetaClass".equals(psiParameter.getType().getCanonicalText()) && "metaClass".equals(psiParameter.getName());
    }

    private List<Type> resolveTypes(PsiType psiType, TypeDictionary typeDictionary, int maxRecursionDepth) {
        PsiClass psiParamsClass;
        PsiClass psiParamsClass2;
        List<Type> types = new ArrayList<>();
        if (typeDictionary != null && (psiType instanceof PsiClassType)) {
            PsiClassType psiClassType = (PsiClassType) psiType;
            PsiType[] parameters = psiClassType.getParameters();
            if (parameters.length > 0) {
                for (PsiType psiType2 : parameters) {
                    types.add(typeDictionary.getType(psiType2, maxRecursionDepth, false, null));
                }
            }
            PsiClass psiClass = PsiUtil.resolveClassInType(psiType);
            if (psiClass != null) {
                PsiClass superClass = psiClass.getSuperClass();
                if (superClass != null) {
                    for (PsiClassType psiClassType2 : psiClass.getSuperTypes()) {
                        String canonicalParamsText = ClassNameUtils.resolveCanonicalName(psiClassType2, null);
                        Pattern r = Pattern.compile("<(.*?)>");
                        Matcher m = r.matcher(canonicalParamsText);
                        if (m.find()) {
                            String matchedText = m.group(1);
                            String[] importGrant = matchedText.split(",");
                            for (String string : importGrant) {
                                if (StringUtil.isNotEmpty(string) && string.length() > 1) {
                                    try {
                                        Project project = psiClass.getProject();
                                        psiParamsClass2 = PsiUtils.h(canonicalParamsText, null, project);
                                        if (psiParamsClass2 != null) {
                                            types.add(typeDictionary.getType(psiParamsClass2, maxRecursionDepth, false));
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            PsiClass psiClass2 = PsiUtil.resolveClassInType(psiType);
            if (psiClass2 != null) {
                PsiType[] superParameters = psiClass2.getSuperTypes();
                for (PsiType psiTypeArg : superParameters) {
                    String canonicalParamsText2 = ClassNameUtils.resolveCanonicalName(psiTypeArg, null);
                    Project project2 = psiType.getResolveScope() == null ? null : psiType.getResolveScope().getProject();
                    psiParamsClass = PsiUtils.h(canonicalParamsText2, null, project2);
                    if (typeDictionary != null) {
                        if (psiParamsClass == null) {
                            types.add(typeDictionary.getType(psiTypeArg, maxRecursionDepth, false, null));
                        } else {
                            types.add(typeDictionary.getType(psiParamsClass, maxRecursionDepth, false));
                        }
                    } else {
                        types.add(new Type(psiTypeArg, null, null, 0, false));
                    }
                }
            }
        }
        return types;
    }

    private boolean hasModifier(PsiClass psiClass, String aStatic) {
        return (psiClass == null || psiClass.getModifierList() == null || !psiClass.getModifierList().hasExplicitModifier(aStatic)) ? false : true;
    }

    public List<Method> findConstructors() {
        List<Method> constructors = new ArrayList<>();
        String constructorName = this.name;
        for (Method method : this.methods) {
            if (method.isConstructor() && !"java.lang.Object".equals(method.getOwnerClassCanonicalType()) && constructorName.equals(method.getName())) {
                constructors.add(method);
            }
        }
        constructors.sort((o1, o2) -> {
            return o2.getMethodParams().size() - o1.getMethodParams().size();
        });
        return constructors;
    }

    private boolean buildAnnotatedByDi(PsiClass psiClass, TypeDictionary typeDictionary) {
        return null != typeDictionary && typeDictionary.isTestSubject(psiClass) && null != psiClass.getAnnotations() && psiClass.getAnnotations().length > 0 && Arrays.stream(psiClass.getAnnotations()).anyMatch(ann -> {
            return DiClassAnnotationEnum.isDiClassAnnotation(ann.getQualifiedName());
        });
    }

    private boolean buildAnnotatedBySpringConfig(PsiClass psiClass, TypeDictionary typeDictionary) {
        return null != typeDictionary && typeDictionary.isTestSubject(psiClass) && null != psiClass.getAnnotations() && psiClass.getAnnotations().length > 0 && Arrays.stream(psiClass.getAnnotations()).anyMatch(ann -> {
            return SpringFieldAnnotationEnum.isSpringConfigFieldAnnotation(ann.getQualifiedName());
        });
    }

    public boolean hasConstructor() {
        return getMethods().stream().anyMatch(method -> {
            return method.isConstructor() && !"java.lang.Object".equals(method.getOwnerClassCanonicalType());
        });
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPrimitive() {
        return this.isPrimitive;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public List<Type> getComposedTypes() {
        return this.composedTypes;
    }

    public boolean isArray() {
        return this.array;
    }

    public boolean isCollection() {
        return this.canonicalName.startsWith("java.util.Collection") || this.canonicalName.startsWith("java.util.List");
    }

    public int getArrayDimensions() {
        return this.arrayDimensions;
    }

    public boolean isVarargs() {
        return this.varargs;
    }

    public boolean isEnum() {
        return this.isEnum;
    }

    public List<String> getEnumValues() {
        return this.enumValues;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public boolean isAbstract() {
        return this.isAbstract;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public boolean isFinal() {
        return this.isFinal;
    }

    public Type getParentContainerClass() {
        return this.parentContainerClass;
    }

    public String getSuperClass() {
        return this.superClass;
    }

    public boolean isDependenciesResolved() {
        return this.dependenciesResolved;
    }

    public boolean isHasDefaultConstructor() {
        return this.hasDefaultConstructor;
    }

    public List<Method> getMethods() {
        return this.methods;
    }

    public List<Field> getFields() {
        if (this.fields == null) {
            return new ArrayList();
        }
        return new ArrayList(this.fields);
    }

    public List<Type> getImplementedInterfaces() {
        return this.implementedInterfaces;
    }

    public boolean isAnnotatedByDI() {
        return this.isAnnotatedByDI;
    }

    public Set<String> getStaticClassNames() {
        return this.staticClassNames;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public boolean isResolved() {
        return this.resolved;
    }

    public String renderArray() {
        return "[]".repeat(this.arrayDimensions);
    }
}
