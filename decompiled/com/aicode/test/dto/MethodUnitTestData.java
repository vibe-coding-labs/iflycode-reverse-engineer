package com.aicode.test.dto;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/MethodUnitTestData.class */
public class MethodUnitTestData {
    private String methodId;
    private Integer generateUnitTestTotal;

    public String getMethodId() {
        return this.methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public Integer getGenerateUnitTestTotal() {
        return this.generateUnitTestTotal;
    }

    public void setGenerateUnitTestTotal(Integer generateUnitTestTotal) {
        this.generateUnitTestTotal = generateUnitTestTotal;
    }

    public MethodUnitTestData(String methodId, Integer generateUnitTestTotal) {
        this.methodId = methodId;
        this.generateUnitTestTotal = generateUnitTestTotal;
    }
}
