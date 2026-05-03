/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto.chat;

import java.util.List;

public class CodeInfoDto {
    private String content;
    private List<RangeDTO> range;
    private transient List<RangeDTO> bodyRange;
    private String fileName;
    private String path;
    private String language;
    private String allContent;

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<RangeDTO> getRange() {
        return this.range;
    }

    public void setRange(List<RangeDTO> range) {
        this.range = range;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAllContent() {
        return this.allContent;
    }

    public void setAllContent(String allContent) {
        this.allContent = allContent;
    }

    public static class RangeDTO {
        private Integer line;
        private Integer character;

        public RangeDTO() {
        }

        public RangeDTO(Integer line, Integer character) {
            this.line = line;
            this.character = character;
        }

        public Integer getLine() {
            return this.line;
        }

        public void setLine(Integer line) {
            this.line = line;
        }

        public Integer getCharacter() {
            return this.character;
        }

        public void setCharacter(Integer character) {
            this.character = character;
        }
    }
}
