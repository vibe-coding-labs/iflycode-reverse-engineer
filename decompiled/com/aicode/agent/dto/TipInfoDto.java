/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

public class TipInfoDto {
    private String user;
    private String platform;
    private Boolean isShowOperateGuide;

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Boolean getShowOperateGuide() {
        return this.isShowOperateGuide;
    }

    public void setShowOperateGuide(Boolean showOperateGuide) {
        this.isShowOperateGuide = showOperateGuide;
    }
}
