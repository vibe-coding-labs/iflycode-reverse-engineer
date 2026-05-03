/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 */
package com.aicode.agent.dto;

import com.aicode.agent.dto.CodeModel;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FunctionModelInfo {
    @SerializedName(value="permissionCode")
    private String permissionCode;
    @SerializedName(value="permissionName")
    private String permissionName;
    @SerializedName(value="language")
    private String language;
    @SerializedName(value="codeModelList")
    private List<CodeModel> codeModelList;

    public String getPermissionCode() {
        return this.permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<CodeModel> getCodeModelList() {
        return this.codeModelList;
    }

    public void setCodeModelList(List<CodeModel> codeModelList) {
        this.codeModelList = codeModelList;
    }
}
