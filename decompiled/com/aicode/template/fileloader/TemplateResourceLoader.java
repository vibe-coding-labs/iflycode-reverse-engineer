package com.aicode.template.fileloader;

import com.intellij.ide.fileTemplates.FileTemplate;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.util.ExtProperties;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/fileloader/TemplateResourceLoader.class */
public class TemplateResourceLoader extends ResourceLoader {
    public void init(ExtendedProperties extProperties) {
    }

    public void init(ExtProperties extProperties) {
    }

    public Reader getResourceReader(String source, String encoding) throws ResourceNotFoundException {
        return new StringReader("");
    }

    public InputStream getResourceStream(String source) throws ResourceNotFoundException {
        UnitTemplateManager fileTemplateManager = UnitTemplateManager.getDefaultInstance();
        FileTemplate[] allPatterns = fileTemplateManager.getAllPatterns();
        Optional<FileTemplate> optTemplate = Stream.of((Object[]) allPatterns).filter(t -> {
            return source.equals(t.getName() + "." + t.getExtension());
        }).findAny();
        FileTemplate include = optTemplate.orElseThrow(() -> {
            return new ResourceNotFoundException("Template not found: " + source);
        });
        String text = include.getText();
        return new ByteArrayInputStream(text.getBytes());
    }

    public boolean isSourceModified(Resource resource) {
        return true;
    }

    public long getLastModified(Resource resource) {
        return 0L;
    }
}
