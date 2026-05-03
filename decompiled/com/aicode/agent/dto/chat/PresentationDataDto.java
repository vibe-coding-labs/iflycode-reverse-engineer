/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto.chat;

import com.aicode.agent.dto.chat.CodeInfoDto;

public class PresentationDataDto {
    private int line;
    private int character;
    private String type;
    private CodeInfoDto codeInfoDto;

    public int getLine() {
        return this.line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getCharacter() {
        return this.character;
    }

    public void setCharacter(int character) {
        this.character = character;
    }

    public CodeInfoDto getCodeInfoDto() {
        return this.codeInfoDto;
    }

    public void setCodeInfoDto(CodeInfoDto codeInfoDto) {
        this.codeInfoDto = codeInfoDto;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
