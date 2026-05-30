package com.aicode.test.dto;

import com.intellij.psi.PsiMethod;
import java.util.List;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/UnitTestMethodDto.class */
public class UnitTestMethodDto {
    private PsiMethod psiMethod;
    private List<Integer> methodRange;
    private Integer methodLine;
    private Boolean hasChange;
    private List<String> changeContent;
    private Boolean unitTestMethod;
    private String methodId;
    private Integer increment;

    public UnitTestMethodDto() {
    }

    public UnitTestMethodDto(PsiMethod psiMethod, List<Integer> methodRange, Integer methodLine, Boolean hasChange) {
        this.psiMethod = psiMethod;
        this.methodRange = methodRange;
        this.methodLine = methodLine;
        this.hasChange = hasChange;
    }

    public UnitTestMethodDto(PsiMethod psiMethod, Integer methodLine, Boolean unitTestMethod, String methodId, Integer increment) {
        this.psiMethod = psiMethod;
        this.methodLine = methodLine;
        this.unitTestMethod = unitTestMethod;
        this.methodId = methodId;
        this.increment = increment;
    }

    public PsiMethod getPsiMethod() {
        return this.psiMethod;
    }

    public void setPsiMethod(PsiMethod psiMethod) {
        this.psiMethod = psiMethod;
    }

    public List<Integer> getMethodRange() {
        return this.methodRange;
    }

    public void setMethodRange(List<Integer> methodRange) {
        this.methodRange = methodRange;
    }

    public Integer getMethodLine() {
        return this.methodLine;
    }

    public void setMethodLine(Integer methodLine) {
        this.methodLine = methodLine;
    }

    public Boolean getHasChange() {
        return this.hasChange;
    }

    public void setHasChange(Boolean hasChange) {
        this.hasChange = hasChange;
    }

    public List<String> getChangeContent() {
        return this.changeContent;
    }

    public void setChangeContent(List<String> changeContent) {
        this.changeContent = changeContent;
    }

    public Boolean getUnitTestMethod() {
        return this.unitTestMethod;
    }

    public void setUnitTestMethod(Boolean unitTestMethod) {
        this.unitTestMethod = unitTestMethod;
    }

    public String getMethodId() {
        return this.methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public Integer getIncrement() {
        return this.increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }
}
