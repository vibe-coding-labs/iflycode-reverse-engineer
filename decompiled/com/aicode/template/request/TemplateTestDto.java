package com.aicode.template.request;

import java.util.List;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/request/TemplateTestDto.class */
public class TemplateTestDto {
    private String testFrame;
    private String mockFrame;
    private String testContent;
    private Integer testCaseNumber;
    private List<String> branchList;

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

    public String getTestContent() {
        return this.testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    public Integer getTestCaseNumber() {
        return this.testCaseNumber;
    }

    public void setTestCaseNumber(Integer testCaseNumber) {
        this.testCaseNumber = testCaseNumber;
    }

    public List<String> getBranchList() {
        return this.branchList;
    }

    public void setBranchList(List<String> branchList) {
        this.branchList = branchList;
    }
}
