package com.aicode.template.request;

import java.util.ArrayList;
import java.util.List;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/request/FileRequestDto.class */
public class FileRequestDto {
    private String requestId;
    private String filePath;
    private List<MethodRequestResult> methodRequestResults = new ArrayList();

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<MethodRequestResult> getMethodRequestResults() {
        return this.methodRequestResults;
    }

    public void setMethodRequestResults(List<MethodRequestResult> methodRequestResults) {
        this.methodRequestResults = methodRequestResults;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getDiff(int sleepTimes) {
        if (this.methodRequestResults.stream().allMatch((v0) -> {
            return v0.isReturn();
        }) && sleepTimes > 0) {
            double diff = this.methodRequestResults.stream().mapToLong((v0) -> {
                return v0.getDiff();
            }).average().orElse(sleepTimes);
            return ((int) (diff + sleepTimes)) / 2;
        }
        return sleepTimes;
    }
}
