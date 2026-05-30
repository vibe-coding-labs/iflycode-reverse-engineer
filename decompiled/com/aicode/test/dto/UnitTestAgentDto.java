package com.aicode.test.dto;

import com.aicode.agent.dto.chat.CodeInfoDto;
import java.util.List;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/UnitTestAgentDto.class */
public class UnitTestAgentDto {
    private String name;
    private List<CodeInfoDto.RangeDTO> range;
    private List<method> methods;
    private String structure;
    private String testFrame;
    private String mockFrame;
    private String msg;
    private String text;
    private String reason;
    private String error;
    private boolean ended;
    private List<MethodUnitTestData> methodUnitTestDataList;
    private String collectScheme;
    private String clientName;
    private String clientVersion;
    private String pluginVersion;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CodeInfoDto.RangeDTO> getRange() {
        return this.range;
    }

    public void setRange(List<CodeInfoDto.RangeDTO> range) {
        this.range = range;
    }

    public List<method> getMethods() {
        return this.methods;
    }

    public void setMethods(List<method> methods) {
        this.methods = methods;
    }

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/UnitTestAgentDto$method.class */
    public static class method {
        private String name;
        private List<CodeInfoDto.RangeDTO> range;
        private String code;
        private String inputValue;
        private String outputValue;
        private String testContent;
        private String unitTest;
        private String unitMock;
        private String methodContent;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CodeInfoDto.RangeDTO> getRange() {
            return this.range;
        }

        public void setRange(List<CodeInfoDto.RangeDTO> range) {
            this.range = range;
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
    }

    public String getStructure() {
        return this.structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getTestFrame() {
        return this.testFrame;
    }

    public void setTestFrame(String testFrame) {
        this.testFrame = testFrame;
    }

    public String getMockFrame() {
        return this.mockFrame;
    }

    public void setMockFrame(String mockFrame) {
        this.mockFrame = mockFrame;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isEnded() {
        return this.ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public List<MethodUnitTestData> getMethodUnitTestDataList() {
        return this.methodUnitTestDataList;
    }

    public void setMethodUnitTestDataList(List<MethodUnitTestData> methodUnitTestDataList) {
        this.methodUnitTestDataList = methodUnitTestDataList;
    }

    public String getCollectScheme() {
        return this.collectScheme;
    }

    public void setCollectScheme(String collectScheme) {
        this.collectScheme = collectScheme;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientVersion() {
        return this.clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getPluginVersion() {
        return this.pluginVersion;
    }

    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }
}
