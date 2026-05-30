package com.aicode.test.dto;

import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.dto.chat.CommentContext;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/RequestCaseCodeDto.class */
public class RequestCaseCodeDto {
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

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/RequestCaseCodeDto$ValueDTO.class */
    public static class ValueDTO {
        private String id;
        private String caseDescription;
        private String inputValue;
        private String outputValue;
        private String pid;
        private String type;
        private String language;
        private String testContent;
        private String unitTest;
        private String unitMock;
        private String methodContent;
        private String code;
        private String originCode;
        private String url;
        private String absolutePath;
        private String className;
        private String methodName;
        private String content;
        private CodeInfoDto codeInfo;
        private boolean needToken;
        private String append;
        private CommentContext context;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCaseDescription() {
            return this.caseDescription;
        }

        public void setCaseDescription(String caseDescription) {
            this.caseDescription = caseDescription;
        }

        public String getInputValue() {
            return this.inputValue;
        }

        public void setInputValue(String inputValue) {
            this.inputValue = inputValue;
        }

        public String getOutputValue() {
            return this.outputValue;
        }

        public void setOutputValue(String outputValue) {
            this.outputValue = outputValue;
        }

        public String getPid() {
            return this.pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLanguage() {
            return this.language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getTestContent() {
            return this.testContent;
        }

        public void setTestContent(String testContent) {
            this.testContent = testContent;
        }

        public String getUnitTest() {
            return this.unitTest;
        }

        public void setUnitTest(String unitTest) {
            this.unitTest = unitTest;
        }

        public String getUnitMock() {
            return this.unitMock;
        }

        public void setUnitMock(String unitMock) {
            this.unitMock = unitMock;
        }

        public String getMethodContent() {
            return this.methodContent;
        }

        public void setMethodContent(String methodContent) {
            this.methodContent = methodContent;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOriginCode() {
            return this.originCode;
        }

        public void setOriginCode(String originCode) {
            this.originCode = originCode;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAbsolutePath() {
            return this.absolutePath;
        }

        public void setAbsolutePath(String absolutePath) {
            this.absolutePath = absolutePath;
        }

        public String getClassName() {
            return this.className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMethodName() {
            return this.methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public CodeInfoDto getCodeInfo() {
            return this.codeInfo;
        }

        public void setCodeInfo(CodeInfoDto codeInfo) {
            this.codeInfo = codeInfo;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isNeedToken() {
            return this.needToken;
        }

        public void setNeedToken(boolean needToken) {
            this.needToken = needToken;
        }

        public String getAppend() {
            return this.append;
        }

        public void setAppend(String append) {
            this.append = append;
        }

        public CommentContext getContext() {
            return this.context;
        }

        public void setContext(CommentContext context) {
            this.context = context;
        }
    }
}
