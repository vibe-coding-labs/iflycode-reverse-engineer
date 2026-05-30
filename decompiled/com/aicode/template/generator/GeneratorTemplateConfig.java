package com.aicode.template.generator;

import com.aicode.enums.DuplicateRule;
import com.aicode.enums.UnitTestBaseEnum;
import com.aicode.enums.UnitTestMockEnum;
import com.aicode.test.dto.UnitTestDto;
import com.intellij.openapi.module.Module;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPackage;
import java.util.HashSet;
import java.util.Set;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/GeneratorTemplateConfig.class */
public class GeneratorTemplateConfig {
    UnitTestBaseEnum testFramework;
    UnitTestMockEnum mockFramework;
    boolean testPrivate;
    private DuplicateRule duplicateRule;
    private String targetDirectory;
    UnitTestDto.DataDTO unitTestDto;
    private String testClassAbsolutePath;
    private boolean methodUt;
    private PsiPackage psiPackage = null;
    private PsiClass srcClass = null;
    private Module testModule = null;
    private Module srcModule = null;
    Boolean requestAi = true;
    private PsiFile psiFile = null;
    private Set<PsiMethod> testMethods = new HashSet();

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

    public void setTestFramework(UnitTestBaseEnum testFramework) {
        this.testFramework = testFramework;
    }

    public void setMockFramework(UnitTestMockEnum mockFramework) {
        this.mockFramework = mockFramework;
    }

    public void setTestPrivate(boolean testPrivate) {
        this.testPrivate = testPrivate;
    }

    public void setRequestAi(Boolean requestAi) {
        this.requestAi = requestAi;
    }

    public void setDuplicateRule(DuplicateRule duplicateRule) {
        this.duplicateRule = duplicateRule;
    }

    public void setPsiFile(PsiFile psiFile) {
        this.psiFile = psiFile;
    }

    public void setTestMethods(Set<PsiMethod> testMethods) {
        this.testMethods = testMethods;
    }

    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public void setUnitTestDto(UnitTestDto.DataDTO unitTestDto) {
        this.unitTestDto = unitTestDto;
    }

    public void setTestClassAbsolutePath(String testClassAbsolutePath) {
        this.testClassAbsolutePath = testClassAbsolutePath;
    }

    public void setMethodUt(boolean methodUt) {
        this.methodUt = methodUt;
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

    public UnitTestBaseEnum getTestFramework() {
        return this.testFramework;
    }

    public UnitTestMockEnum getMockFramework() {
        return this.mockFramework;
    }

    public boolean isTestPrivate() {
        return this.testPrivate;
    }

    public Boolean getRequestAi() {
        return this.requestAi;
    }

    public DuplicateRule getDuplicateRule() {
        return this.duplicateRule;
    }

    public PsiFile getPsiFile() {
        return this.psiFile;
    }

    public Set<PsiMethod> getTestMethods() {
        return this.testMethods;
    }

    public String getTargetDirectory() {
        return this.targetDirectory;
    }

    public UnitTestDto.DataDTO getUnitTestDto() {
        return this.unitTestDto;
    }

    public String getTestClassAbsolutePath() {
        return this.testClassAbsolutePath;
    }

    public boolean isMethodUt() {
        return this.methodUt;
    }
}
