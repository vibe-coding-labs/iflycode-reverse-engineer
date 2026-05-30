package com.aicode.template.request;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/request/TemplateTestPromptDto.class */
public class TemplateTestPromptDto {
    private boolean stream;
    private String content;
    private TemplateTestDto unitTest;

    public boolean isStream() {
        return this.stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TemplateTestDto getUnitTest() {
        return this.unitTest;
    }

    public void setUnitTest(TemplateTestDto unitTest) {
        this.unitTest = unitTest;
    }
}
