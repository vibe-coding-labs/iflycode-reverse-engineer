package com.aicode.template.request.dto;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/request/dto/CaseParam.class */
public class CaseParam {
    private String name;
    private String type;
    private String canonicalName;
    private Object data;

    public CaseParam() {
    }

    public CaseParam(String name, String type, String canonicalName, Object data) {
        this.name = name;
        this.type = type;
        this.canonicalName = canonicalName;
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public TypeEnum getResolveType() {
        TypeEnum typeEnum = TypeEnum.valueOf(this.type);
        return typeEnum == null ? TypeEnum.STRING : typeEnum;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
