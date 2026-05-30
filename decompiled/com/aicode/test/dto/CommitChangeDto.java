package com.aicode.test.dto;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/CommitChangeDto.class */
public class CommitChangeDto {
    private String methodId;
    private Integer commitChangeUnitTestTotal;

    public CommitChangeDto(String methodId, Integer commitChangeUnitTestTotal) {
        this.methodId = methodId;
        this.commitChangeUnitTestTotal = commitChangeUnitTestTotal;
    }

    public String getMethodId() {
        return this.methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public Integer getCommitChangeUnitTestTotal() {
        return this.commitChangeUnitTestTotal;
    }

    public void setCommitChangeUnitTestTotal(Integer commitChangeUnitTestTotal) {
        this.commitChangeUnitTestTotal = commitChangeUnitTestTotal;
    }

    public String toString() {
        return "CommitChangeDto{methodId='" + this.methodId + "', commitChangeUnitTestTotal=" + this.commitChangeUnitTestTotal + "}";
    }
}
