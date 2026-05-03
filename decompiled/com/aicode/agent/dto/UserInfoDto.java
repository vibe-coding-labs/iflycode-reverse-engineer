/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

import com.aicode.agent.dto.CodeModel;
import com.aicode.agent.dto.EnterpriseDto;
import com.aicode.agent.dto.SysUrlDto;
import java.util.List;

public class UserInfoDto {
    String clientId;
    String user;
    String token;
    List<CodeModel> codeModelDtoList;
    EnterpriseDto enterpriseDto;
    String tokenPath;
    SysUrlDto sysUrls;
    String packageCode;
    String packageName;
    boolean reLogin;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CodeModel> getCodeModelDtoList() {
        return this.codeModelDtoList;
    }

    public void setCodeModelDtoList(List<CodeModel> codeModelDtoList) {
        this.codeModelDtoList = codeModelDtoList;
    }

    public EnterpriseDto getEnterpriseDto() {
        return this.enterpriseDto;
    }

    public void setEnterpriseDto(EnterpriseDto enterpriseDto) {
        this.enterpriseDto = enterpriseDto;
    }

    public String getTokenPath() {
        return this.tokenPath;
    }

    public void setTokenPath(String tokenPath) {
        this.tokenPath = tokenPath;
    }

    public SysUrlDto getSysUrls() {
        return this.sysUrls;
    }

    public void setSysUrls(SysUrlDto sysUrls) {
        this.sysUrls = sysUrls;
    }

    public String getPackageCode() {
        return this.packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isReLogin() {
        return this.reLogin;
    }

    public void setReLogin(boolean reLogin) {
        this.reLogin = reLogin;
    }
}
