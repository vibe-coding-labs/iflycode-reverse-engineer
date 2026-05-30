package com.aicode.template.context.domain;

import com.aicode.template.TypeDictionary;
import com.aicode.template.context.domain.annotion.DiFieldAnnotationEnum;
import com.aicode.template.context.domain.annotion.SpringFieldAnnotationEnum;
import com.aicode.util.ClassNameUtils;
import com.aicode.util.PropertyUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import java.util.Arrays;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/Field.class */
public class Field {
    private final Type type;
    private final boolean overridden;
    private final boolean isFinal;
    private final boolean isStatic;
    private final String ownerClassCanonicalName;
    private final boolean isAnnotatedByDI;
    private final boolean hasSetter;
    private final boolean getProperty;
    private final boolean setProperty;
    private final boolean isInitializedInline;
    private final boolean isAnnotatedBySpringValue;
    private String name;
    private boolean isNotInBuilder;

    public Field(PsiField psiField, PsiClass srcClass, TypeDictionary typeDictionary, int maxRecursionDepth) {
        this.name = psiField.getName();
        this.type = buildType(psiField.getType(), typeDictionary, maxRecursionDepth);
        this.isAnnotatedByDI = buildAnnotatedByDI(psiField, srcClass, typeDictionary);
        this.isAnnotatedBySpringValue = buildAnnotatedBySpringConfig(psiField, srcClass, typeDictionary);
        this.hasSetter = buildHasSetter(srcClass, psiField.getName(), typeDictionary);
        this.getProperty = buildGetProperty(srcClass, psiField.getName(), typeDictionary);
        this.setProperty = buildSetProperty(srcClass, psiField.getName(), typeDictionary);
        String canonicalText = srcClass.getQualifiedName() == null ? srcClass.getName() : srcClass.getQualifiedName();
        if (canonicalText != null) {
            this.ownerClassCanonicalName = ClassNameUtils.stripArrayVarargsDesignator(canonicalText);
        } else {
            this.ownerClassCanonicalName = "";
        }
        this.overridden = isOverriddenInChild(psiField, srcClass);
        this.isFinal = psiField.getModifierList() != null && psiField.getModifierList().hasExplicitModifier("final");
        this.isStatic = psiField.getModifierList() != null && psiField.getModifierList().hasExplicitModifier("static");
        this.isInitializedInline = typeDictionary != null && typeDictionary.isTestSubject(srcClass) && psiField.hasInitializer();
    }

    public Field(String fieldName, Type fieldType, boolean isFinal, boolean isStatic, boolean isNotInBuilder) {
        this.name = fieldName;
        this.type = fieldType;
        this.isAnnotatedByDI = true;
        this.isAnnotatedBySpringValue = false;
        this.hasSetter = false;
        this.getProperty = false;
        this.setProperty = false;
        this.ownerClassCanonicalName = fieldType.getCanonicalName();
        this.overridden = false;
        this.isFinal = isFinal;
        this.isStatic = isStatic;
        this.isNotInBuilder = isNotInBuilder;
        this.isInitializedInline = false;
    }

    private static Type buildType(PsiType type, TypeDictionary typeDictionary, int maxRecursionDepth) {
        if (typeDictionary == null) {
            return new Type(type, null, null, 0, false);
        }
        return typeDictionary.getType(type, maxRecursionDepth, true);
    }

    private boolean buildHasSetter(PsiClass psiClass, String fieldName, TypeDictionary typeDictionary) {
        return null != typeDictionary && typeDictionary.isTestSubject(psiClass) && null != psiClass.getMethods() && psiClass.getMethods().length > 0 && Arrays.stream(psiClass.getMethods()).anyMatch(psiMethod -> {
            return PropertyUtils.isPropertySetter(psiMethod, fieldName);
        });
    }

    private boolean buildGetProperty(PsiClass psiClass, String fieldName, TypeDictionary typeDictionary) {
        return null != typeDictionary && psiClass.getMethods().length > 0 && Arrays.stream(psiClass.getMethods()).anyMatch(psiMethod -> {
            return PropertyUtils.hasGetter(psiMethod, fieldName);
        });
    }

    private boolean buildSetProperty(PsiClass psiClass, String fieldName, TypeDictionary typeDictionary) {
        return null != typeDictionary && psiClass.getMethods().length > 0 && Arrays.stream(psiClass.getMethods()).anyMatch(psiMethod -> {
            return PropertyUtils.hasSetter(psiMethod, fieldName);
        });
    }

    private boolean buildAnnotatedByDI(PsiField psiField, PsiClass srcClass, TypeDictionary typeDictionary) {
        return null != typeDictionary && typeDictionary.isTestSubject(srcClass) && null != psiField.getAnnotations() && psiField.getAnnotations().length > 0 && Arrays.stream(psiField.getAnnotations()).anyMatch(ann -> {
            return DiFieldAnnotationEnum.isDiFieldAnnotation(ann.getQualifiedName());
        });
    }

    private boolean buildAnnotatedBySpringConfig(PsiField psiField, PsiClass srcClass, TypeDictionary typeDictionary) {
        return null != typeDictionary && typeDictionary.isTestSubject(srcClass) && null != psiField.getAnnotations() && psiField.getAnnotations().length > 0 && Arrays.stream(psiField.getAnnotations()).anyMatch(ann -> {
            return SpringFieldAnnotationEnum.isSpringConfigFieldAnnotation(ann.getQualifiedName());
        });
    }

    public static boolean isOverriddenInChild(PsiField psiField, PsiClass srcClass) {
        String srcQualifiedName = srcClass.getQualifiedName();
        String fieldClsQualifiedName = psiField.getContainingClass() == null ? null : psiField.getContainingClass().getQualifiedName();
        return (srcQualifiedName == null || fieldClsQualifiedName == null || srcQualifiedName.equals(fieldClsQualifiedName) || srcClass.findFieldByName(psiField.getName(), false) == null) ? false : true;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Field)) {
            return false;
        }
        Field field = (Field) o;
        if (this.type.equals(field.type) && this.ownerClassCanonicalName.equals(field.ownerClassCanonicalName)) {
            return this.name.equals(field.name);
        }
        return false;
    }

    public int hashCode() {
        int result = this.type.hashCode();
        return (31 * ((31 * result) + this.ownerClassCanonicalName.hashCode())) + this.name.hashCode();
    }

    public String toString() {
        return "Field{type=" + this.type + ", overridden=" + this.overridden + ", isFinal=" + this.isFinal + ", isStatic=" + this.isStatic + ", ownerClassCanonicalName='" + this.ownerClassCanonicalName + "', name='" + this.name + "'}";
    }

    public Type getType() {
        return this.type;
    }

    public boolean isOverridden() {
        return this.overridden;
    }

    public boolean isFinal() {
        return this.isFinal;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public String getOwnerClassCanonicalName() {
        return this.ownerClassCanonicalName;
    }

    public boolean isAnnotatedByDI() {
        return this.isAnnotatedByDI;
    }

    public boolean isHasSetter() {
        return this.hasSetter;
    }

    public boolean isGetProperty() {
        return this.getProperty;
    }

    public boolean isSetProperty() {
        return this.setProperty;
    }

    public boolean isInitializedInline() {
        return this.isInitializedInline;
    }

    public boolean isAnnotatedBySpringValue() {
        return this.isAnnotatedBySpringValue;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNotInBuilder() {
        return this.isNotInBuilder;
    }

    public void setNotInBuilder(boolean notInBuilder) {
        this.isNotInBuilder = notInBuilder;
    }
}
