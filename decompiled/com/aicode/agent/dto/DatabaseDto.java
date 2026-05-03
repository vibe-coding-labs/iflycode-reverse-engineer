/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

import com.aicode.agent.dto.ConnectConfigDto;
import java.util.List;

public class DatabaseDto {
    private String id;
    private ConnectConfigDto formData;
    private List<String> databases;
    private Boolean status;
    private String errMsg;
    private Long createTime;
    private Long updateTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConnectConfigDto getFormData() {
        return this.formData;
    }

    public void setFormData(ConnectConfigDto formData) {
        this.formData = formData;
    }

    public List<String> getDatabases() {
        return this.databases;
    }

    public void setDatabases(List<String> databases) {
        this.databases = databases;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
