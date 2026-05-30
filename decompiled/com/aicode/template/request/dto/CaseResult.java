package com.aicode.template.request.dto;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.aicode.message.BasicActionsBundle;
import com.aicode.template.request.DataUtils;
import com.aicode.util.StringUtils;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/request/dto/CaseResult.class */
public class CaseResult {
    private String caseMethodName;
    private String type;
    private Map<String, CaseParam> input;
    private List<CaseBranch> branches;
    private List<ToMockMethod> mockMethods;
    private CaseParam output;
    private String message;
    private String exception;
    private String exceptionMessage;
    private String methodCommentId = IdUtil.fastSimpleUUID();

    public CaseResult() {
    }

    public CaseResult(String caseMethodName, Map<String, CaseParam> input, List<ToMockMethod> mockMethods, CaseParam output, String message) {
        this.caseMethodName = caseMethodName;
        this.input = input;
        this.mockMethods = mockMethods;
        this.output = output;
        this.message = message;
    }

    public CaseResult(String caseMethodName, Map<String, CaseParam> input, List<ToMockMethod> mockMethods, CaseParam output, String message, String exception) {
        this.caseMethodName = caseMethodName;
        this.input = input;
        this.mockMethods = mockMethods;
        this.output = output;
        this.message = message;
        this.exception = exception;
    }

    public String getCaseMethodName() {
        return this.caseMethodName;
    }

    public void setCaseMethodName(String caseMethodName) {
        this.caseMethodName = caseMethodName;
    }

    public Map<String, CaseParam> getInput() {
        return this.input;
    }

    public void setInput(Map<String, CaseParam> input) {
        this.input = input;
    }

    public List<ToMockMethod> getMockMethods() {
        return this.mockMethods;
    }

    public void setMockMethods(List<ToMockMethod> mockMethods) {
        this.mockMethods = mockMethods;
    }

    public CaseParam getOutput() {
        return this.output;
    }

    public void setOutput(CaseParam output) {
        this.output = output;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return this.exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getExceptionMessage() {
        return this.exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CaseBranch> getBranches() {
        return this.branches;
    }

    public void setBranches(List<CaseBranch> branches) {
        this.branches = branches;
    }

    public String toCommitBranchText() {
        if (CollectionUtils.isNotEmpty(this.branches)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("/**\n");
            if (this.branches.size() == 1 && StringUtils.isEmpty(this.branches.get(0).getConditionText())) {
                return "";
            }
            for (CaseBranch branch : this.branches) {
                if (StringUtils.isNotBlank(branch.getConditionText())) {
                    stringBuilder.append("* ").append(branch.getConditionText());
                    stringBuilder.append(" : ").append(branch.getResult()).append("\n");
                }
            }
            stringBuilder.append("*/\n");
            return stringBuilder.toString();
        }
        return "";
    }

    public String toComment() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/**\n");
        stringBuilder.append("*").append(BasicActionsBundle.message("config.unit.test.createFile.comment", new Object[0])).append(this.methodCommentId).append("\n");
        stringBuilder.append("* 以下代码由" + BasicActionsBundle.message("aicode.plugin.title", new Object[0]) + "生成 ").append("\n");
        if (!DataUtils.isEmptyData(this)) {
            stringBuilder.append("*").append(this.message).append("\n");
            stringBuilder.append("*").append("输入：");
            if (this.input == null || this.input.isEmpty()) {
                stringBuilder.append("无").append("\n");
            } else {
                stringBuilder.append("\n");
                this.input.forEach((key, value) -> {
                    if (value != null) {
                        stringBuilder.append("*\t").append(key).append(" = ").append(JSONUtil.toJsonStr(value.getData())).append("\n");
                    }
                });
            }
            stringBuilder.append("*").append("输出：");
            if (this.output == null || this.output.getData() == null) {
                stringBuilder.append("无").append("\n");
            } else {
                stringBuilder.append("\t").append(JSONUtil.toJsonStr(this.output.getData())).append("\n");
            }
        }
        stringBuilder.append("*/");
        return stringBuilder.toString();
    }

    public String toEndComment() {
        this.methodCommentId = IdUtil.fastSimpleUUID();
        return "//*************" + BasicActionsBundle.message("config.unit.test.createFile.comment", new Object[0]) + this.methodCommentId + "*************//";
    }

    private void appendMockMethod(StringBuilder stringBuilder, ToMockMethod mockMethod) {
        if (mockMethod == null) {
            return;
        }
        try {
            stringBuilder.append("*\t").append(mockMethod.getClassName()).append(".").append(mockMethod.getMethodName()).append(" = ");
            if (mockMethod.getReturnValue() == null) {
                stringBuilder.append("无");
            } else {
                stringBuilder.append(mockMethod.getReturnValue().getData());
            }
            stringBuilder.append("\n");
        } catch (Exception e) {
        }
    }
}
