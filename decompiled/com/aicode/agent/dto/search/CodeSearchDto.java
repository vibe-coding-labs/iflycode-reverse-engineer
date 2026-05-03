/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto.search;

import java.math.BigDecimal;

public class CodeSearchDto {
    private String id;
    private String repoUrl;
    private String repoName;
    private String repoType;
    private String branch;
    private String filePath;
    private String fileName;
    private String language;
    private Integer isOpen;
    private Integer isPublic;
    private Integer startRow;
    private Integer endRow;
    private BigDecimal score;
    private String code;
    private Integer codeLength;
    private Double codeVector;
    private Long createTime;

    public CodeSearchDto() {
    }

    public CodeSearchDto(String id, String repoUrl, String repoName, String repoType, String branch, String filePath, String fileName, String language, Integer isOpen, Integer isPublic, Integer startRow, Integer endRow, BigDecimal score, String code, Integer codeLength, Double codeVector, Long createTime) {
        this.id = id;
        this.repoUrl = repoUrl;
        this.repoName = repoName;
        this.repoType = repoType;
        this.branch = branch;
        this.filePath = filePath;
        this.fileName = fileName;
        this.language = language;
        this.isOpen = isOpen;
        this.isPublic = isPublic;
        this.startRow = startRow;
        this.endRow = endRow;
        this.score = score;
        this.code = code;
        this.codeLength = codeLength;
        this.codeVector = codeVector;
        this.createTime = createTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepoUrl() {
        return this.repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getRepoName() {
        return this.repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoType() {
        return this.repoType;
    }

    public void setRepoType(String repoType) {
        this.repoType = repoType;
    }

    public String getBranch() {
        return this.branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getIsPublic() {
        return this.isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public Integer getStartRow() {
        return this.startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getEndRow() {
        return this.endRow;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    public BigDecimal getScore() {
        return this.score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCodeLength() {
        return this.codeLength;
    }

    public void setCodeLength(Integer codeLength) {
        this.codeLength = codeLength;
    }

    public Double getCodeVector() {
        return this.codeVector;
    }

    public void setCodeVector(Double codeVector) {
        this.codeVector = codeVector;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String toString() {
        return "CodeInfoDto{id='" + this.id + "', repoUrl='" + this.repoUrl + "', repoName='" + this.repoName + "', repoType='" + this.repoType + "', branch='" + this.branch + "', filePath='" + this.filePath + "', fileName='" + this.fileName + "', language='" + this.language + "', isOpen=" + this.isOpen + ", isPublic=" + this.isPublic + ", startRow=" + this.startRow + ", endRow=" + this.endRow + ", score=" + this.score + ", code='" + this.code + "', codeLength=" + this.codeLength + ", codeVector=" + this.codeVector + ", createTime=" + this.createTime + "}";
    }
}
