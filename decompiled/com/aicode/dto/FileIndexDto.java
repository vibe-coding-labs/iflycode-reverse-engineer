package com.aicode.dto;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/dto/FileIndexDto.class */
public class FileIndexDto {
    private String title;
    private String filePath;
    private String fileName;
    private int selectStartLine;
    private int selectEndLine;

    public String getFileIndexName() {
        return this.fileName + ":" + this.selectStartLine + "-" + this.selectEndLine;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSelectStartLine() {
        return this.selectStartLine;
    }

    public void setSelectStartLine(int selectStartLine) {
        this.selectStartLine = selectStartLine;
    }

    public int getSelectEndLine() {
        return this.selectEndLine;
    }

    public void setSelectEndLine(int selectEndLine) {
        this.selectEndLine = selectEndLine;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
