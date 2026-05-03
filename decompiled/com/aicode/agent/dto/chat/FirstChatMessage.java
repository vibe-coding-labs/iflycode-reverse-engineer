/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 */
package com.aicode.agent.dto.chat;

import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.dto.chat.SqlInfoDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FirstChatMessage {
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
        private String inputText;
        private String id;
        private String sessionId;
        private String type;
        private CodeInfoDto codeInfo;
        private SqlInfoDto sqlInfo;
        private JsonArray knowledge;
        private boolean errorType = false;
        private String errorMessage;
        private JsonArray intelligent;
        private JsonArray relatedFiles;
        private JsonObject data;
        private String language;
        private String code;

        public String getInputText() {
            return this.inputText;
        }

        public void setInputText(String inputText) {
            this.inputText = inputText;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSessionId() {
            return this.sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public CodeInfoDto getCodeInfo() {
            return this.codeInfo;
        }

        public void setCodeInfo(CodeInfoDto codeInfo) {
            this.codeInfo = codeInfo;
        }

        public boolean isErrorType() {
            return this.errorType;
        }

        public void setErrorType(boolean errorType) {
            this.errorType = errorType;
        }

        public String getErrorMessage() {
            return this.errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public SqlInfoDto getSqlInfo() {
            return this.sqlInfo;
        }

        public void setSqlInfo(SqlInfoDto sqlInfo) {
            this.sqlInfo = sqlInfo;
        }

        public JsonArray getKnowledge() {
            return this.knowledge;
        }

        public void setKnowledge(JsonArray knowledge) {
            this.knowledge = knowledge;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLanguage() {
            return this.language;
        }

        public JsonArray getIntelligent() {
            return this.intelligent;
        }

        public void setIntelligent(JsonArray intelligent) {
            this.intelligent = intelligent;
        }

        public JsonArray getRelatedFiles() {
            return this.relatedFiles;
        }

        public void setRelatedFiles(JsonArray relatedFiles) {
            this.relatedFiles = relatedFiles;
        }

        public JsonObject getData() {
            return this.data;
        }

        public void setData(JsonObject data) {
            this.data = data;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
