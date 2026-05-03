/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

public class ResponseStreamDto {
    String id;
    String code;
    String msg;
    ResponseData data;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponseData getData() {
        return this.data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    public class ResponseData {
        private boolean ended;
        private String text = "";
        private boolean showKeyMapTipFlag = false;

        public boolean isEnded() {
            return this.ended;
        }

        public void setEnded(boolean ended) {
            this.ended = ended;
        }

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isShowKeyMapTipFlag() {
            return this.showKeyMapTipFlag;
        }

        public void setShowKeyMapTipFlag(boolean showKeyMapTipFlag) {
            this.showKeyMapTipFlag = showKeyMapTipFlag;
        }
    }
}
