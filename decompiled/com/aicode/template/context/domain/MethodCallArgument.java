package com.aicode.template.context.domain;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/MethodCallArgument.class */
public class MethodCallArgument {
    private final String text;

    public MethodCallArgument(String text) {
        this.text = text;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MethodCallArgument)) {
            return false;
        }
        MethodCallArgument that = (MethodCallArgument) o;
        return this.text != null ? this.text.equals(that.text) : that.text == null;
    }

    public String getText() {
        return this.text;
    }

    public int hashCode() {
        if (this.text != null) {
            return this.text.hashCode();
        }
        return 0;
    }
}
