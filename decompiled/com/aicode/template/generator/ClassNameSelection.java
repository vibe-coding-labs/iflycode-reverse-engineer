package com.aicode.template.generator;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/ClassNameSelection.class */
public class ClassNameSelection {
    private final String className;
    private final UserDecision userDecision;

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/ClassNameSelection$UserDecision.class */
    public enum UserDecision {
        New,
        Goto,
        Abort
    }

    public ClassNameSelection(String className, UserDecision userDecision) {
        this.className = className;
        this.userDecision = userDecision;
    }

    public String getClassName() {
        return this.className;
    }

    public UserDecision getUserDecision() {
        return this.userDecision;
    }
}
