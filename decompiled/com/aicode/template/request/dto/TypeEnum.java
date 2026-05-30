package com.aicode.template.request.dto;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/request/dto/TypeEnum.class */
public enum TypeEnum {
    BOOLEAN,
    STRING,
    NUMBER,
    ARRAY,
    LIST,
    HASHMAP,
    CLASS,
    STREAM,
    DATE;

    public static TypeEnum parse(String name) {
        for (TypeEnum value : values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return STRING;
    }
}
