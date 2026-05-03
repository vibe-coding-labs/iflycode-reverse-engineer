/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 */
package com.aicode.agent.dto;

import com.google.gson.annotations.SerializedName;

public class CodeModel {
    @SerializedName(value="modelId")
    private String modelId;
    @SerializedName(value="modelCode")
    private String modelCode;
    @SerializedName(value="modelName")
    private String modelName;
    private boolean checked;
    private String originalModelName;
    private boolean tokenExhausted;

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelCode() {
        return this.modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public boolean isTokenExhausted() {
        return this.tokenExhausted;
    }

    public void setTokenExhausted(boolean tokenExhausted) {
        this.tokenExhausted = tokenExhausted;
    }

    public String getOriginalModelName() {
        return this.originalModelName;
    }

    public void setOriginalModelName(String originalModelName) {
        this.originalModelName = originalModelName;
    }

    public String toString() {
        return this.modelName;
    }
}
