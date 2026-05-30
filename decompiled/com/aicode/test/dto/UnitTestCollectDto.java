package com.aicode.test.dto;

import java.util.List;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/UnitTestCollectDto.class */
public class UnitTestCollectDto {
    private String commitId;
    private Integer commitTotal;
    private Integer commitIncrementTotal;
    private Integer commitUnitTestTotal;
    private Integer commitUnitTestIncrementTotal;
    private String collectScheme;
    private String clientName;
    private String clientVersion;
    private String pluginVersion;
    private List<CommitChangeDto> methodUnitTestDataList;

    public UnitTestCollectDto(String commitId, Integer commitTotal, Integer commitIncrementTotal, Integer commitUnitTestTotal, Integer commitUnitTestIncrementTotal, String collectScheme, List<CommitChangeDto> methodUnitTestDataList) {
        this.commitId = commitId;
        this.commitTotal = commitTotal;
        this.commitIncrementTotal = commitIncrementTotal;
        this.commitUnitTestTotal = commitUnitTestTotal;
        this.commitUnitTestIncrementTotal = commitUnitTestIncrementTotal;
        this.collectScheme = collectScheme;
        this.methodUnitTestDataList = methodUnitTestDataList;
    }

    public String getCommitId() {
        return this.commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public Integer getCommitTotal() {
        return this.commitTotal;
    }

    public void setCommitTotal(Integer commitTotal) {
        this.commitTotal = commitTotal;
    }

    public Integer getCommitUnitTestTotal() {
        return this.commitUnitTestTotal;
    }

    public void setCommitUnitTestTotal(Integer commitUnitTestTotal) {
        this.commitUnitTestTotal = commitUnitTestTotal;
    }

    public List<CommitChangeDto> getMethodUnitTestDataList() {
        return this.methodUnitTestDataList;
    }

    public void setMethodUnitTestDataList(List<CommitChangeDto> methodUnitTestDataList) {
        this.methodUnitTestDataList = methodUnitTestDataList;
    }

    public Integer getCommitIncrementTotal() {
        return this.commitIncrementTotal;
    }

    public void setCommitIncrementTotal(Integer commitIncrementTotal) {
        this.commitIncrementTotal = commitIncrementTotal;
    }

    public Integer getCommitUnitTestIncrementTotal() {
        return this.commitUnitTestIncrementTotal;
    }

    public void setCommitUnitTestIncrementTotal(Integer commitUnitTestIncrementTotal) {
        this.commitUnitTestIncrementTotal = commitUnitTestIncrementTotal;
    }

    public String getCollectScheme() {
        return this.collectScheme;
    }

    public void setCollectScheme(String collectScheme) {
        this.collectScheme = collectScheme;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientVersion() {
        return this.clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getPluginVersion() {
        return this.pluginVersion;
    }

    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }
}
