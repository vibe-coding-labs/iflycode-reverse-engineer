/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

public class WebRequestDto<T> {
    private String type;
    private T value;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
