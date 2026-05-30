package com.aicode.test.dto;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/ChangeInfoDto.class */
public class ChangeInfoDto {
    private Integer changeLine;
    private String content;

    public ChangeInfoDto(Integer changeLine, String content) {
        this.changeLine = changeLine;
        this.content = content;
    }

    public Integer getChangeLine() {
        return this.changeLine;
    }

    public void setChangeLine(Integer changeLine) {
        this.changeLine = changeLine;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
