/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

import com.aicode.agent.dto.chat.CodeInfoDto;

public class CodeCheckFixDto {
    private String type;
    private ValueDTO value;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ValueDTO getValue() {
        return this.value;
    }

    public void setValue(ValueDTO value) {
        this.value = value;
    }

    public static class ValueDTO {
        private String id;
        private CodeInfoDto codeInfo;
        private String errorType;
        private String errorMessage;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public CodeInfoDto getCodeInfo() {
            return this.codeInfo;
        }

        public void setCodeInfo(CodeInfoDto codeInfo) {
            this.codeInfo = codeInfo;
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
    }
}
