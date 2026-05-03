/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

public class CodeCheckListDto {
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
        private Boolean status;
        private String message;
        private Object data;

        public Boolean getStatus() {
            return this.status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return this.data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
