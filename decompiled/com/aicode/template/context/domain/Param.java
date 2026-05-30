package com.aicode.template.context.domain;

import com.aicode.template.TypeDictionary;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiType;
import java.util.ArrayList;
import java.util.Optional;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/Param.class */
public class Param {
    final Type type;
    private String name;
    private final ArrayList<Field> assignedToFields;

    public Param(PsiParameter psiParameter, Optional<PsiType> substitutedType, TypeDictionary typeDictionary, int maxRecursionDepth, ArrayList<Field> assignedToFields, boolean shouldResolveAllMethods) {
        this(resolveType(psiParameter, substitutedType, shouldResolveAllMethods, typeDictionary, maxRecursionDepth), psiParameter.getName(), assignedToFields);
    }

    public Param(Type type, String name, ArrayList<Field> assignedToFields) {
        this.type = type;
        this.name = name;
        this.assignedToFields = assignedToFields;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Param)) {
            return false;
        }
        Param param = (Param) o;
        return this.type != null ? this.type.equals(param.type) : param.type == null;
    }

    public int hashCode() {
        if (this.type != null) {
            return this.type.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "Param{name='" + this.name + ", type=" + this.type + "', assignedToFields=" + this.assignedToFields + "}";
    }

    private static Type resolveType(PsiParameter psiParameter, Optional<PsiType> substitutedType, boolean shouldResolveAllMethods, TypeDictionary typeDictionary, int maxRecursionDepth) {
        return typeDictionary.getType(substitutedType.orElse(psiParameter.getType()), maxRecursionDepth, shouldResolveAllMethods, null);
    }

    public Type getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Field> getAssignedToFields() {
        return this.assignedToFields;
    }
}
