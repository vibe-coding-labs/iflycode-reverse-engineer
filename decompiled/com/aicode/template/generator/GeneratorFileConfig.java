package com.aicode.template.generator;

import com.aicode.enums.DuplicateRule;
import com.aicode.test.dto.UnitTestDto;
import com.intellij.openapi.module.Module;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPackage;
import java.util.ArrayList;
import java.util.List;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/GeneratorFileConfig.class */
public class GeneratorFileConfig {
    private String framework;
    private String mock;
    private DuplicateRule duplicateRule;
    private PsiDirectory targetDirectory;
    private String testFileName;
    UnitTestDto.DataDTO.FunctionDataDTO functionDataDTO;
    private PsiPackage psiPackage = null;
    private PsiClass srcClass = null;
    private Module testModule = null;
    private Module srcModule = null;
    private PsiFile psiFile = null;
    private List<PsiMethod> testMethods = new ArrayList();
    private Boolean requestAi = true;

    public void setPsiPackage(PsiPackage psiPackage) {
        this.psiPackage = psiPackage;
    }

    public void setSrcClass(PsiClass srcClass) {
        this.srcClass = srcClass;
    }

    public void setTestModule(Module testModule) {
        this.testModule = testModule;
    }

    public void setSrcModule(Module srcModule) {
        this.srcModule = srcModule;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public void setMock(String mock) {
        this.mock = mock;
    }

    public void setDuplicateRule(DuplicateRule duplicateRule) {
        this.duplicateRule = duplicateRule;
    }

    public void setPsiFile(PsiFile psiFile) {
        this.psiFile = psiFile;
    }

    public void setTestMethods(List<PsiMethod> testMethods) {
        this.testMethods = testMethods;
    }

    public void setTargetDirectory(PsiDirectory targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public void setRequestAi(Boolean requestAi) {
        this.requestAi = requestAi;
    }

    public void setTestFileName(String testFileName) {
        this.testFileName = testFileName;
    }

    public void setFunctionDataDTO(UnitTestDto.DataDTO.FunctionDataDTO functionDataDTO) {
        this.functionDataDTO = functionDataDTO;
    }

    public PsiPackage getPsiPackage() {
        return this.psiPackage;
    }

    public PsiClass getSrcClass() {
        return this.srcClass;
    }

    public Module getTestModule() {
        return this.testModule;
    }

    public Module getSrcModule() {
        return this.srcModule;
    }

    public String getFramework() {
        return this.framework;
    }

    public String getMock() {
        return this.mock;
    }

    public DuplicateRule getDuplicateRule() {
        return this.duplicateRule;
    }

    public PsiFile getPsiFile() {
        return this.psiFile;
    }

    public List<PsiMethod> getTestMethods() {
        return this.testMethods;
    }

    public PsiDirectory getTargetDirectory() {
        return this.targetDirectory;
    }

    public Boolean getRequestAi() {
        return this.requestAi;
    }

    public String getTestFileName() {
        return this.testFileName;
    }

    public UnitTestDto.DataDTO.FunctionDataDTO getFunctionDataDTO() {
        return this.functionDataDTO;
    }
}
