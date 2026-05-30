package com.aicode.template.fileloader;

import com.intellij.ide.fileTemplates.impl.DefaultTemplate;
import com.intellij.util.containers.MultiMap;
import java.net.URL;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/fileloader/FileTemplateLoadResult.class */
public class FileTemplateLoadResult {
    private MultiMap<String, DefaultTemplate> result;
    private URL defaultTemplateDescription;
    private URL defaultIncludeDescription;

    public FileTemplateLoadResult(MultiMap<String, DefaultTemplate> result) {
        this.result = result;
    }

    public FileTemplateLoadResult() {
    }

    public FileTemplateLoadResult(MultiMap<String, DefaultTemplate> result, URL defaultTemplateDescription, URL defaultIncludeDescription) {
        this.result = result;
        this.defaultTemplateDescription = defaultTemplateDescription;
        this.defaultIncludeDescription = defaultIncludeDescription;
    }

    public MultiMap<String, DefaultTemplate> getResult() {
        return this.result;
    }

    public void setResult(MultiMap<String, DefaultTemplate> result) {
        this.result = result;
    }

    public URL getDefaultTemplateDescription() {
        return this.defaultTemplateDescription;
    }

    public void setDefaultTemplateDescription(URL defaultTemplateDescription) {
        this.defaultTemplateDescription = defaultTemplateDescription;
    }

    public URL getDefaultIncludeDescription() {
        return this.defaultIncludeDescription;
    }

    public void setDefaultIncludeDescription(URL defaultIncludeDescription) {
        this.defaultIncludeDescription = defaultIncludeDescription;
    }
}
