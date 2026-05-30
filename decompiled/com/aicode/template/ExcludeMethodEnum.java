package com.aicode.template;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/ExcludeMethodEnum.class */
public enum ExcludeMethodEnum {
    ABSTRACT("abstract", false),
    NATIVE("native", false),
    GETTER("getter", true),
    SETTER("setter", true),
    MAIN("main", true),
    EQUALS("equals", true),
    TOSTRING("toString", true),
    HASHCODE("hashCode", true);

    private String name;
    private boolean canWrite;

    ExcludeMethodEnum(String name, boolean canWrite) {
        this.name = name;
        this.canWrite = canWrite;
    }

    public String getName() {
        return this.name;
    }

    public boolean isCanWrite() {
        return this.canWrite;
    }
}
