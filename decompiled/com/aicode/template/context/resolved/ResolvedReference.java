package com.aicode.template.context.resolved;

import com.intellij.psi.PsiType;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/resolved/ResolvedReference.class */
public class ResolvedReference {
    private final String referenceName;
    private final PsiType refType;
    private final PsiType psiOwnerType;

    public ResolvedReference(String referenceName, PsiType refType, PsiType psiOwnerType) {
        this.referenceName = referenceName;
        this.refType = refType;
        this.psiOwnerType = psiOwnerType;
    }

    public String getReferenceName() {
        return this.referenceName;
    }

    public PsiType getRefType() {
        return this.refType;
    }

    public PsiType getPsiOwnerType() {
        return this.psiOwnerType;
    }
}
