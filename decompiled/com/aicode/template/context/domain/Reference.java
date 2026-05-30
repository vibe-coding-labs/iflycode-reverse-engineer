package com.aicode.template.context.domain;

import com.aicode.template.TypeDictionary;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiType;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/Reference.class */
public class Reference {
    private static final Logger LOG = Logger.getInstance(Reference.class.getName());
    private final String referenceName;
    private final Type referenceType;
    private final Type ownerType;
    private final String referenceId;

    public Reference(String referenceName, PsiType refType, PsiType psiOwnerType, TypeDictionary typeDictionary) {
        this.referenceName = referenceName;
        this.referenceType = new Type(refType, null, typeDictionary, 1, false);
        this.ownerType = new Type(psiOwnerType, null, typeDictionary, 1, false);
        this.referenceId = this.ownerType.getCanonicalName() + referenceName + this.referenceType.getCanonicalName();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reference)) {
            return false;
        }
        Reference reference = (Reference) o;
        return this.referenceId.equals(reference.referenceId);
    }

    public int hashCode() {
        return this.referenceId.hashCode();
    }

    public String getReferenceName() {
        return this.referenceName;
    }

    public Type getReferenceType() {
        return this.referenceType;
    }

    public Type getOwnerType() {
        return this.ownerType;
    }

    public String getReferenceId() {
        return this.referenceId;
    }

    public String toString() {
        return "Reference{referenceName='" + this.referenceName + "', referenceType=" + this.referenceType + ", ownerType=" + this.ownerType + "}";
    }
}
