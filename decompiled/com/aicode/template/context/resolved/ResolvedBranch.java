package com.aicode.template.context.resolved;

import com.aicode.util.StringUtils;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiStatement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/resolved/ResolvedBranch.class */
public class ResolvedBranch {
    private ResolvedBranch parent;
    private ResolvedBranch prev;
    private ResolvedBranch next;
    private String conditionText;
    private String methodName;
    private Boolean result;
    private Boolean isOut;
    private TextRange textRange;
    private Integer level;
    private PsiStatement branch;
    private String ExceptionCanonicalText;
    private Boolean virtual = false;
    private List<ResolvedMethodCall> methodCalls = new ArrayList();
    private List<ResolvedBranch> childrenCases = new ArrayList();
    private List<String> elseBranches = new ArrayList();

    public String getConditionText() {
        return this.conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Boolean getResult() {
        return this.result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Boolean getOut() {
        return this.isOut;
    }

    public void setOut(Boolean out) {
        this.isOut = out;
    }

    public PsiStatement getBranch() {
        return this.branch;
    }

    public void setBranch(PsiStatement branch) {
        this.branch = branch;
    }

    public TextRange getTextRange() {
        return this.textRange;
    }

    public void setTextRange(TextRange textRange) {
        this.textRange = textRange;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<ResolvedMethodCall> getMethodCalls() {
        return this.methodCalls;
    }

    public void setMethodCalls(List<ResolvedMethodCall> methodCalls) {
        this.methodCalls = methodCalls;
    }

    public List<ResolvedBranch> getChildrenCases() {
        return this.childrenCases;
    }

    public void setChildrenCases(List<ResolvedBranch> childrenCases) {
        this.childrenCases = childrenCases;
    }

    public String getExceptionCanonicalText() {
        return this.ExceptionCanonicalText;
    }

    public void setExceptionCanonicalText(String exceptionCanonicalText) {
        this.ExceptionCanonicalText = exceptionCanonicalText;
    }

    public List<String> getElseBranches() {
        return this.elseBranches;
    }

    public void setElseBranches(List<String> elseBranches) {
        this.elseBranches = elseBranches;
    }

    public ResolvedBranch getParent() {
        return this.parent;
    }

    public void setParent(ResolvedBranch parent) {
        this.parent = parent;
    }

    public ResolvedBranch getPrev() {
        return this.prev;
    }

    public void setPrev(ResolvedBranch prev) {
        this.prev = prev;
    }

    public Boolean getVirtual() {
        return this.virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public ResolvedBranch getNext() {
        return this.next;
    }

    public void setNext(ResolvedBranch next) {
        this.next = next;
    }

    public List<String> resolveAllBranches() {
        List<String> branches = new ArrayList<>();
        if (StringUtils.isNotBlank(this.conditionText) && !this.virtual.booleanValue()) {
            branches.add(this.conditionText);
        }
        if (CollectionUtils.isNotEmpty(this.childrenCases)) {
            for (ResolvedBranch childrenCase : this.childrenCases) {
                branches.addAll(childrenCase.resolveAllBranches());
            }
        }
        return branches;
    }

    public Integer getBranchesSize() {
        Integer size = 1;
        if (CollectionUtils.isNotEmpty(this.childrenCases)) {
            for (ResolvedBranch childrenCase : this.childrenCases) {
                size = Integer.valueOf(size.intValue() + childrenCase.getBranchesSize().intValue());
            }
        }
        return size;
    }
}
