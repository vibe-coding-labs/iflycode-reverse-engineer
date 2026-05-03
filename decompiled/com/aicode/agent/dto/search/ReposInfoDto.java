/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto.search;

public class ReposInfoDto {
    private String id;
    private String repoUrl;
    private String repoName;
    private String branch;
    private String repoType;

    public ReposInfoDto() {
    }

    public ReposInfoDto(String id, String repoUrl, String repoName, String branch, String repoType) {
        this.id = id;
        this.repoUrl = repoUrl;
        this.repoName = repoName;
        this.branch = branch;
        this.repoType = repoType;
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

    public String getBranch() {
        return this.branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRepoType() {
        return this.repoType;
    }

    public void setRepoType(String repoType) {
        this.repoType = repoType;
    }

    public String toString() {
        return "ReposInfoDto{id=" + this.id + ", repoUrl='" + this.repoUrl + "', repoName='" + this.repoName + "', branch='" + this.branch + "', repoType='" + this.repoType + "'}";
    }
}
