package com.aicode.template.context.resolved;

import com.aicode.template.context.domain.Type;
import java.util.Objects;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/resolved/ResolveVarible.class */
public class ResolveVarible {
    private int varibleType;
    private String name;
    private Type resolveType;

    public boolean isSupport(int type) {
        return this.varibleType == type;
    }

    public ResolveVarible() {
    }

    public ResolveVarible(String name, Type type, int varibleType) {
        this.name = name;
        this.resolveType = type;
        this.varibleType = varibleType;
    }

    public int getVaribleType() {
        return this.varibleType;
    }

    public void setVaribleType(int varibleType) {
        this.varibleType = varibleType;
    }

    public Type getResolveType() {
        return this.resolveType;
    }

    public void setResolveType(Type resolveType) {
        this.resolveType = resolveType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResolveVarible that = (ResolveVarible) o;
        return this.varibleType == that.varibleType && Objects.equals(this.name, that.name) && Objects.equals(this.resolveType, that.resolveType);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.varibleType), this.name, this.resolveType);
    }
}
