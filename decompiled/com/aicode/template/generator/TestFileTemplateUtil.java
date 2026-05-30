package com.aicode.template.generator;

import com.aicode.template.fileloader.FileTemplateContext;
import com.aicode.util.StringUtils;
import com.intellij.ide.fileTemplates.CreateFromTemplateHandler;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ClassLoaderUtil;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/TestFileTemplateUtil.class */
public class TestFileTemplateUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 1:
            case 4:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 2:
            case 3:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 4:
            default:
                i2 = 3;
                break;
            case 2:
            case 3:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            case 4:
            default:
                objArr[0] = "template";
                break;
            case 1:
                objArr[0] = "directory";
                break;
            case 2:
            case 3:
                objArr[0] = "com/aicode/template/generator/TestFileTemplateUtil";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 4:
            default:
                objArr[1] = "com/aicode/template/generator/TestFileTemplateUtil";
                break;
            case 2:
            case 3:
                objArr[1] = "createFromTemplate";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            default:
                objArr[2] = "createFromTemplate";
                break;
            case 2:
            case 3:
                break;
            case 4:
                objArr[2] = "checkAppendExtension";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 1:
            case 4:
            default:
                throw new IllegalArgumentException(format);
            case 2:
            case 3:
                throw new IllegalStateException(format);
        }
    }

    @NotNull
    public static PsiFile createFromTemplate(@NotNull FileTemplate template, FileTemplateContext context, @Nullable Map<String, Object> propsMap, @NotNull PsiDirectory directory, @Nullable ClassLoader classLoader) throws Exception {
        if (template == null) {
            $$$reportNull$$$0(0);
        }
        if (directory == null) {
            $$$reportNull$$$0(1);
        }
        Project project = directory.getProject();
        String fileName = context.getTargetClass();
        FileTemplateManager.getInstance(project).addRecentName(template.getName());
        if (propsMap == null) {
            Properties p = FileTemplateManager.getInstance(project).getDefaultProperties();
            propsMap = new HashMap();
            FileTemplateUtil.putAll(propsMap, p);
        }
        Properties p2 = new Properties();
        FileTemplateUtil.fillDefaultProperties(p2, directory);
        FileTemplateUtil.putAll(propsMap, p2);
        CreateFromTemplateHandler handler = FileTemplateUtil.findHandler(template);
        if (fileName != null && propsMap.get("NAME") == null) {
            propsMap.put("NAME", fileName);
        } else if (fileName == null && handler.isNameRequired()) {
            fileName = (String) propsMap.get("NAME");
            if (fileName == null) {
                throw new Exception("File name must be specified");
            }
        }
        String fileNameWithExt = fileName + (StringUtils.isEmpty(template.getExtension()) ? "" : "." + template.getExtension());
        propsMap.put("FILE_NAME", fileNameWithExt);
        propsMap.put("FILE_PATH", FileUtil.join(new String[]{directory.getVirtualFile().getPath(), fileNameWithExt}));
        try {
            Class<?> fileTemplateUtilClass = Class.forName("com.intellij.ide.fileTemplates.FileTemplateUtil");
            Method method = fileTemplateUtilClass.getDeclaredMethod("getDirPathRelativeToProjectBaseDir", PsiDirectory.class);
            method.setAccessible(true);
            String dirPath = (String) method.invoke(null, directory);
            if (dirPath != null) {
                propsMap.put("DIR_PATH", dirPath);
            }
            String[] dummyRefs = FileTemplateUtil.calculateAttributes(template.getText(), propsMap, true, directory.getProject());
            for (String dummyRef : dummyRefs) {
                propsMap.put(dummyRef, "");
            }
            String fileName2 = checkAppendExtension(fileName, template);
            propsMap.put("FILE_NAME", fileName2);
            handler.prepareProperties(propsMap);
            Map<String, Object> props_ = propsMap;
            String mergedText = (String) ClassLoaderUtil.computeWithClassLoader(classLoader != null ? classLoader : FileTemplateUtil.class.getClassLoader(), () -> {
                return template.getText(props_);
            });
            String templateText = StringUtil.convertLineSeparators(mergedText);
            PsiFileFactory fileFactory = PsiFileFactory.getInstance(project);
            PsiFile createFileFromText = fileFactory.createFileFromText(fileName2, getLanguageFileType(context.getSrcClass().getLanguage()), templateText);
            if (createFileFromText == null) {
                $$$reportNull$$$0(3);
            }
            return createFileFromText;
        } catch (Exception e) {
            e.printStackTrace();
            if (0 == 0) {
                $$$reportNull$$$0(2);
            }
            return null;
        }
    }

    protected static String checkAppendExtension(String fileName, @NotNull FileTemplate template) {
        if (template == null) {
            $$$reportNull$$$0(4);
        }
        String suggestedFileNameEnd = "." + template.getExtension();
        if (!fileName.endsWith(suggestedFileNameEnd)) {
            fileName = fileName + suggestedFileNameEnd;
        }
        return fileName;
    }

    public static FileType getLanguageFileType(Language language) {
        return JavaFileType.INSTANCE;
    }
}
