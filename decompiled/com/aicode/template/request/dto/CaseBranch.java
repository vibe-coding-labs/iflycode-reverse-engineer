package com.aicode.template.request.dto;

import com.aicode.message.BasicActionsBundle;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/request/dto/CaseBranch.class */
public class CaseBranch {
    private String methodName;
    private String conditionText;
    private Boolean result;
    private Boolean isOut;
    private Integer startOffset;
    private Integer endOffset;

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getConditionText() {
        return this.conditionText;
    }

    public String getConditionText(Boolean trim) {
        return StringUtils.isNotBlank(this.conditionText) ? trim.booleanValue() ? StringUtils.trim(this.conditionText) : this.conditionText : "";
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public Boolean getResult() {
        return this.result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Integer getStartOffset() {
        return this.startOffset;
    }

    public void setStartOffset(Integer startOffset) {
        this.startOffset = startOffset;
    }

    public Integer getEndOffset() {
        return this.endOffset;
    }

    public void setEndOffset(Integer endOffset) {
        this.endOffset = endOffset;
    }

    public Boolean getOut() {
        return this.isOut;
    }

    public void setOut(Boolean out) {
        this.isOut = out;
    }

    public String toCommitText() {
        return toCommitText(false);
    }

    public String toCommitText(boolean isInChild) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean appendCommit = StringUtils.isNotBlank(this.conditionText) && Objects.nonNull(this.result);
        if (!isInChild && appendCommit) {
            stringBuilder.append("/*\n").append("* ").append(BasicActionsBundle.message("config.batch.unit.test.branch.commit", new Object[0])).append("\n");
        }
        if (StringUtils.isNotBlank(this.conditionText) && Objects.nonNull(this.result)) {
            stringBuilder.append("* ").append(this.conditionText);
            stringBuilder.append("  ").append(this.result).append("\n");
        }
        stringBuilder.append(isInChild ? "" : appendCommit ? "*/" : "");
        return stringBuilder.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CaseBranch that = (CaseBranch) o;
        return Objects.equals(this.conditionText, that.conditionText) && Objects.equals(this.result, that.result) && Objects.equals(this.isOut, that.isOut);
    }

    public int hashCode() {
        return Objects.hash(this.conditionText, this.result, this.isOut);
    }
}
