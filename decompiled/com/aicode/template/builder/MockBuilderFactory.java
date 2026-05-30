package com.aicode.template.builder;

import com.aicode.template.TestSubjectInspector;
import com.aicode.template.fileloader.FileTemplateContext;
import com.aicode.util.StringUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.ResourceFileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/builder/MockBuilderFactory.class */
public class MockBuilderFactory {
    private static final String MOCKITO_CORE_JAR_NAME_PREFIX = "mockito-core-";
    private static final Logger logger = Logger.getInstance(MockBuilderFactory.class.getName());
    private static final Pattern MOCKITO_CORE_VERSION_REGEX = Pattern.compile("mockito-core-(.*)\\.jar");

    @NotNull
    public MockitoMockBuilder createMockitoMockBuilder(FileTemplateContext context, TestSubjectInspector testSubjectInspector, List<String> classpathJars) {
        return new MockitoMockBuilder(isMockInline(context), context.getFileTemplateConfig().isStubMockMethodCallsReturnValues(), testSubjectInspector, resolveMockitoVersion(classpathJars));
    }

    @NotNull
    public PowerMockBuilder createPowerMockBuilder(FileTemplateContext context, TestSubjectInspector testSubjectInspector, List<String> classpathJars) {
        return new PowerMockBuilder(true, context.getFileTemplateConfig().isStubMockMethodCallsReturnValues(), testSubjectInspector, resolveMockitoVersion(classpathJars), context.getFileTemplateConfig().isRenderInternalMethodCallStubs());
    }

    public static boolean isMockInline(FileTemplateContext context) {
        PsiFile mockMakerPsiFile;
        boolean found = false;
        VirtualFile mockMakerVFile = ResourceFileUtil.findResourceFileInDependents(context.getTestModule(), "mockito-extensions/org.mockito.plugins.MockMaker");
        logger.debug("found mockito MockMaker in test module classpath:" + mockMakerVFile);
        if (mockMakerVFile != null && (mockMakerPsiFile = PsiManager.getInstance(context.getProject()).getFileManager().getCachedPsiFile(mockMakerVFile)) != null) {
            String mockFileText = mockMakerPsiFile.getText();
            found = StringUtils.hasLine(mockFileText, "mock-maker-inline");
            logger.debug("mockito MockMaker content:" + mockFileText);
            logger.debug("is mock-maker-inline turned on:" + found);
        }
        return found;
    }

    @Nullable
    String resolveMockitoVersion(List<String> classpathJars) {
        if (classpathJars == null) {
            return null;
        }
        return (String) classpathJars.stream().map(f -> {
            Matcher matcher = MOCKITO_CORE_VERSION_REGEX.matcher(f);
            if (matcher.find()) {
                return matcher.group(1);
            }
            return "";
        }).filter(f2 -> {
            return !f2.isEmpty();
        }).findFirst().orElse(null);
    }
}
