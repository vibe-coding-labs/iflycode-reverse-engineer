package com.aicode.template.context.resolved;

import com.intellij.psi.PsiType;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/resolved/MethodCallArg.class */
public class MethodCallArg {
    private final String text;
    private final String name;
    private final PsiType type;

    public MethodCallArg(String text, String name, PsiType type) {
        this.text = text;
        this.name = name;
        this.type = type;
    }

    public String getText() {
        return this.text;
    }

    public String getName() {
        return this.name;
    }

    public PsiType getType() {
        return this.type;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MethodCallArg)) {
            return false;
        }
        MethodCallArg that = (MethodCallArg) o;
        return this.text != null ? this.text.equals(that.text) : that.text == null;
    }

    public int hashCode() {
        if (this.text != null) {
            return this.text.hashCode();
        }
        return 0;
    }
}
