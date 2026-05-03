/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

import com.aicode.agent.dto.chat.CodeInfoDto;

public class CodeCheckDto {
    private String codeFragment;
    private String errorType;
    private String errorMessage;
    private CodeInfoDto codeInfo;

    public String getCodeFragment() {
        return this.codeFragment;
    }

    public void setCodeFragment(String codeFragment) {
        this.codeFragment = codeFragment;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public CodeInfoDto getCodeInfo() {
        return this.codeInfo;
    }

    public void setCodeInfo(CodeInfoDto codeInfo) {
        this.codeInfo = codeInfo;
    }
}
