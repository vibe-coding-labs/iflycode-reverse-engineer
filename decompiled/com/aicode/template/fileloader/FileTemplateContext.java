package com.aicode.template.fileloader;

import com.aicode.template.FileTemplateConfig;
import com.aicode.util.PsiUtils;
import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPackage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/fileloader/FileTemplateContext.class */
public class FileTemplateContext {
    private FileTemplateDescriptor fileTemplateDescriptor;
    private final Project project;
    private final String targetClass;
    private final PsiPackage targetPackage;
    private final Module srcModule;
    private final Module testModule;
    private final PsiDirectory targetDirectory;
    private final PsiClass srcClass;
    private final FileTemplateConfig fileTemplateConfig;
    private final List<String> excludeMethodList;
    private final Set<PsiMethod> selectedMethods;
    private final Boolean requestAi;
    private String filePath;

    public FileTemplateContext(FileTemplateDescriptor fileTemplateDescriptor, Project project, String targetClass, PsiPackage targetPackage, Module srcModule, Module testModule, PsiDirectory targetDirectory, PsiClass srcClass, FileTemplateConfig fileTemplateConfig, List<String> excludeMethodList, Boolean requestAi) {
        this.fileTemplateDescriptor = fileTemplateDescriptor;
        this.project = project;
        this.targetClass = targetClass;
        this.targetPackage = targetPackage;
        this.srcModule = srcModule;
        this.testModule = testModule;
        this.targetDirectory = targetDirectory;
        this.srcClass = srcClass;
        this.requestAi = requestAi;
        this.fileTemplateConfig = fileTemplateConfig;
        this.excludeMethodList = excludeMethodList;
        this.selectedMethods = null;
    }

    public FileTemplateContext(FileTemplateDescriptor fileTemplateDescriptor, Project project, String targetClass, PsiPackage targetPackage, Module srcModule, Module testModule, PsiDirectory targetDirectory, PsiClass srcClass, FileTemplateConfig fileTemplateConfig, List<PsiMethod> selectMethods, List<String> excludeMethodList, Boolean requestAi) {
        this.fileTemplateDescriptor = fileTemplateDescriptor;
        this.project = project;
        this.targetClass = targetClass;
        this.targetPackage = targetPackage;
        this.srcModule = srcModule;
        this.testModule = testModule;
        this.targetDirectory = targetDirectory;
        this.srcClass = srcClass;
        this.requestAi = requestAi;
        this.fileTemplateConfig = fileTemplateConfig;
        this.excludeMethodList = excludeMethodList;
        this.selectedMethods = new HashSet(selectMethods);
    }

    public Project getProject() {
        return this.project;
    }

    public String getTargetClass() {
        return this.targetClass;
    }

    public PsiPackage getTargetPackage() {
        return this.targetPackage;
    }

    public Module getSrcModule() {
        return this.srcModule;
    }

    public PsiDirectory getTargetDirectory() {
        return this.targetDirectory;
    }

    public PsiClass getSrcClass() {
        return this.srcClass;
    }

    public FileTemplateDescriptor getFileTemplateDescriptor() {
        return this.fileTemplateDescriptor;
    }

    public Module getTestModule() {
        return this.testModule;
    }

    public FileTemplateConfig getFileTemplateConfig() {
        return this.fileTemplateConfig;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getExcludeMethodList() {
        return this.excludeMethodList;
    }

    public Boolean getRequestAi() {
        return this.requestAi;
    }

    public Set<String> getSelectedMethods() {
        if (this.selectedMethods == null) {
            return null;
        }
        return (Set) this.selectedMethods.stream().map(PsiUtils::formatMethodId).collect(Collectors.toSet());
    }
}
