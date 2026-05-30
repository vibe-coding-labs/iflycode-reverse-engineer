package com.aicode.template.fileloader;

import com.aicode.util.StringUtils;
import com.intellij.openapi.diagnostic.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/fileloader/TemplateRegistry.class */
public class TemplateRegistry {
    private static final String TEMPLATE_FILE_SUFFIX = ".ft";
    public static final String JUNIT4_JAVA_TEMPLATE = "JUnit4.java";
    public static final String JUNIT5_JAVA_TEMPLATE = "JUnit5.java";
    public static final String JUNIT4_MOCKITO_JAVA_TEMPLATE = "JUnit4&Mockito.java";
    public static final String JUNIT4_POWERMOCK_JAVA_TEMPLATE = "JUnit4&Powermock.java";
    public static final String JUNIT5_MOCKITO_JAVA_TEMPLATE = "JUnit5&Mockito.java";
    public static final String TESTNG_MOCKITO_JAVA_TEMPLATE = "TestNG&Mockito.java";
    public static final String SPRINGBOOTTEST_MOCKITO_JAVA_TEMPLATE = "SpringBootTest&Mockito.java";
    private static final Logger LOG = Logger.getInstance(TemplateRegistry.class.getName());
    private static List<TemplateDescriptor> templateDescriptors = new ArrayList();

    static {
        templateDescriptors.add(new TemplateDescriptor("<html><i>JUnit4</i></html>", "<html><i>JUnit4</i></html><JUnit4>", "JUnit4.java", TemplateRole.Tester));
        templateDescriptors.add(new TemplateDescriptor("<html><i>JUnit5</i></html>", "<html><i>JUnit5</i></html><JUnit5>", "JUnit5.java", TemplateRole.Tester));
        templateDescriptors.add(new TemplateDescriptor("<html><i>JUnit4</i>& <i>Mockito</i></html>", "<html><i>JUnit4</i></html><JUnit4><html>& <i>Mockito</i></html><Mockito>", "JUnit4&Mockito.java", TemplateRole.Tester));
        templateDescriptors.add(new TemplateDescriptor("<html><i>JUnit4</i>& <i>Powermock</i></html>", "<html><i>JUnit4</i></html><JUnit4><html>& <i>Powermock</i></html><Powermock>", "JUnit4&Powermock.java", TemplateRole.Tester));
        templateDescriptors.add(new TemplateDescriptor("<html><i>JUnit5</i>& <i>Mockito</i></html>", "<html><i>JUnit5</i></html><JUnit5><html>& <i>Mockito</i></html><Mockito>", "JUnit5&Mockito.java", TemplateRole.Tester));
        templateDescriptors.add(new TemplateDescriptor("<html><i>TestNG </i>& <i>Mockito</i></html>", "<html><i>TestNG </i></html><TestNG><html>& <i>Mockito</i></html><Mockito>", "TestNG&Mockito.java", TemplateRole.Tester));
        templateDescriptors.add(new TemplateDescriptor("<html><i>SpringBootTest </i>& <i>Mockito</i></html>", "<html><i>SpringBootTest </i></html><SpringBootTest><html>& <i>Mockito</i></html><Mockito>", "SpringBootTest&Mockito.java", TemplateRole.Tester));
    }

    public List<TemplateDescriptor> getTemplateDescriptors() {
        return templateDescriptors;
    }

    public List<TemplateDescriptor> getEnabledTemplateDescriptors() {
        return (List) templateDescriptors.stream().filter((v0) -> {
            return v0.isEnabled();
        }).collect(Collectors.toList());
    }

    public TemplateDescriptor getEnabledTemplateDescriptor(String unitFramework, String mockFramework) {
        TemplateDescriptor exactMatchDescriptor = null;
        TemplateDescriptor partialMatchDescriptor = null;
        Iterator<TemplateDescriptor> it = getEnabledTemplateDescriptors().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            TemplateDescriptor descriptor = it.next();
            if (isMatch(descriptor.getFramework(), unitFramework) && descriptor.getMockFramework().equalsIgnoreCase(mockFramework)) {
                exactMatchDescriptor = descriptor;
                break;
            }
            if (isMatch(descriptor.getFramework(), unitFramework) || (StringUtils.isNotBlank(mockFramework) && descriptor.getMockFramework().equalsIgnoreCase(mockFramework))) {
                if (partialMatchDescriptor == null) {
                    partialMatchDescriptor = descriptor;
                }
            }
        }
        return exactMatchDescriptor != null ? exactMatchDescriptor : partialMatchDescriptor;
    }

    private boolean isMatch(String unitFramework, String baseFramework) {
        if (StringUtils.isEmpty(unitFramework) || StringUtils.isEmpty(baseFramework)) {
            return false;
        }
        String unit = unitFramework.indexOf(".") > 0 ? unitFramework.split("\\.")[0] : unitFramework;
        String base = baseFramework.indexOf(".") > 0 ? baseFramework.split("\\.")[0] : baseFramework;
        return StringUtils.equalsIgnoreCase(unit, base);
    }
}
