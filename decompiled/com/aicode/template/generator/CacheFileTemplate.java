package com.aicode.template.generator;

import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.agent.dto.MessageDto;
import com.aicode.template.fileloader.FileTemplateContext;
import com.intellij.psi.PsiDirectory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/CacheFileTemplate.class */
public class CacheFileTemplate {
    private Map<String, Object> paramMaps;
    private FileTemplateContext context;
    private PsiDirectory targetDirectory;
    private GeneratorFileConfig generatorFileConfig;
    private MethodGeneratorConfig methodGeneratorConfig;
    private List<MessageDto> messageDtos = new ArrayList();

    public void setParamMaps(Map<String, Object> paramMaps) {
        this.paramMaps = paramMaps;
    }

    public void setContext(FileTemplateContext context) {
        this.context = context;
    }

    public void setTargetDirectory(PsiDirectory targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public void setGeneratorFileConfig(GeneratorFileConfig generatorFileConfig) {
        this.generatorFileConfig = generatorFileConfig;
    }

    public void setMethodGeneratorConfig(MethodGeneratorConfig methodGeneratorConfig) {
        this.methodGeneratorConfig = methodGeneratorConfig;
    }

    public void setMessageDtos(List<MessageDto> messageDtos) {
        this.messageDtos = messageDtos;
    }

    public Map<String, Object> getParamMaps() {
        return this.paramMaps;
    }

    public FileTemplateContext getContext() {
        return this.context;
    }

    public PsiDirectory getTargetDirectory() {
        return this.targetDirectory;
    }

    public GeneratorFileConfig getGeneratorFileConfig() {
        return this.generatorFileConfig;
    }

    public MethodGeneratorConfig getMethodGeneratorConfig() {
        return this.methodGeneratorConfig;
    }

    public List<MessageDto> getMessageDtos() {
        return this.messageDtos;
    }
}
