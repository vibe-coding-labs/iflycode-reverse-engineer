package com.aicode.template.generator;

import com.aicode.enums.DuplicateRule;
import com.aicode.template.fileloader.TemplateDescriptor;
import com.aicode.template.generator.ClassNameSelection;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.codeStyle.JavaCodeStyleSettings;
import com.intellij.refactoring.util.RefactoringMessageUtil;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/GeneratedClassNameResolver.class */
public class GeneratedClassNameResolver {
    private static final Logger LOG = Logger.getInstance(GeneratedClassNameResolver.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 6:
            case 7:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 3:
            case 4:
            case 8:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 6:
            case 7:
            default:
                i2 = 3;
                break;
            case 3:
            case 4:
            case 8:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            case 7:
            default:
                objArr[0] = "project";
                break;
            case 1:
            case 5:
                objArr[0] = "targetDirectory";
                break;
            case 2:
            case 6:
                objArr[0] = "targetTestSubjectClass";
                break;
            case 3:
            case 4:
            case 8:
                objArr[0] = "com/aicode/template/generator/GeneratedClassNameResolver";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 6:
            case 7:
            default:
                objArr[1] = "com/aicode/template/generator/GeneratedClassNameResolver";
                break;
            case 3:
            case 4:
                objArr[1] = "resolveClassName";
                break;
            case 8:
                objArr[1] = "getUserDecision";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            default:
                objArr[2] = "resolveClassName";
                break;
            case 3:
            case 4:
            case 8:
                break;
            case 5:
            case 6:
                objArr[2] = "getClassName";
                break;
            case 7:
                objArr[2] = "getUserDecision";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 6:
            case 7:
            default:
                throw new IllegalArgumentException(format);
            case 3:
            case 4:
            case 8:
                throw new IllegalStateException(format);
        }
    }

    @NotNull
    public ClassNameSelection resolveClassName(@NotNull Project project, @NotNull PsiDirectory targetDirectory, @NotNull PsiClass targetTestSubjectClass, TemplateDescriptor templateDescriptor, DuplicateRule duplicateRule, String testFileName) {
        String className;
        ClassNameSelection classNameSelection;
        ClassNameSelection classNameSelection2;
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (targetDirectory == null) {
            $$$reportNull$$$0(1);
        }
        if (targetTestSubjectClass == null) {
            $$$reportNull$$$0(2);
        }
        if (StringUtils.isBlank(testFileName)) {
            className = getClassName(targetDirectory, targetTestSubjectClass, templateDescriptor, 0, duplicateRule);
        } else {
            className = testFileName;
        }
        String extension = "." + FileUtilRt.getExtension(templateDescriptor.getFilename());
        String fileCreateErrorMessage = RefactoringMessageUtil.checkCanCreateFile(targetDirectory, className + "." + FileUtilRt.getExtension(templateDescriptor.getFilename()));
        if (fileCreateErrorMessage != null) {
            VirtualFile file = getVirtualFile(targetDirectory, className + extension);
            if (file != null) {
                try {
                    if (DuplicateRule.OVERWRITE.equals(duplicateRule)) {
                        file.delete(this);
                        classNameSelection2 = new ClassNameSelection(className, ClassNameSelection.UserDecision.New);
                    } else if (DuplicateRule.COEXIST.equals(duplicateRule)) {
                        String className2 = getClassName(targetDirectory, targetTestSubjectClass, templateDescriptor, 1, duplicateRule);
                        classNameSelection2 = new ClassNameSelection(className2, ClassNameSelection.UserDecision.New);
                    } else {
                        classNameSelection2 = new ClassNameSelection(className, ClassNameSelection.UserDecision.Abort);
                    }
                    ClassNameSelection classNameSelection3 = classNameSelection2;
                    if (classNameSelection3 == null) {
                        $$$reportNull$$$0(3);
                    }
                    return classNameSelection3;
                } catch (IOException e) {
                }
            }
            classNameSelection = new ClassNameSelection(className, ClassNameSelection.UserDecision.New);
        } else {
            classNameSelection = new ClassNameSelection(className, ClassNameSelection.UserDecision.New);
        }
        ClassNameSelection classNameSelection4 = classNameSelection;
        if (classNameSelection4 == null) {
            $$$reportNull$$$0(4);
        }
        return classNameSelection4;
    }

    private String getClassName(@NotNull PsiDirectory targetDirectory, @NotNull PsiClass targetTestSubjectClass, TemplateDescriptor templateDescriptor, int index, DuplicateRule duplicateRule) {
        if (targetDirectory == null) {
            $$$reportNull$$$0(5);
        }
        if (targetTestSubjectClass == null) {
            $$$reportNull$$$0(6);
        }
        String className = composeTestClassName(targetTestSubjectClass, index);
        try {
            String extension = "." + FileUtilRt.getExtension(templateDescriptor.getFilename());
            String fileCreateErrorMessage = RefactoringMessageUtil.checkCanCreateFile(targetDirectory, className + "." + FileUtilRt.getExtension(templateDescriptor.getFilename()));
            if (fileCreateErrorMessage != null) {
                if (DuplicateRule.OVERWRITE.equals(duplicateRule)) {
                    VirtualFile file = getVirtualFile(targetDirectory, className + extension);
                    if (file != null) {
                        file.delete(this);
                        return className;
                    }
                } else if (DuplicateRule.COEXIST.equals(duplicateRule)) {
                    return getClassName(targetDirectory, targetTestSubjectClass, templateDescriptor, index + 1, duplicateRule);
                }
            }
        } catch (Exception e) {
            LOG.warn("生成单测文件名称异常", e);
        }
        return className;
    }

    private VirtualFile getVirtualFile(PsiDirectory targetDirectory, String fileName) {
        for (VirtualFile child : targetDirectory.getVirtualFile().getChildren()) {
            if (fileName.equals(child.getName())) {
                return child;
            }
        }
        return null;
    }

    @NotNull
    private ClassNameSelection getUserDecision(@NotNull Project project, final String className, String fileCreateErrorMessage, String dialogTitle) {
        ClassNameSelection classNameSelection;
        if (project == null) {
            $$$reportNull$$$0(7);
        }
        int selection = Messages.showDialog(project, fileCreateErrorMessage + "\n请选择操作?", dialogTitle, new String[]{"使用其他名称", "&打开已有文件", "&取消"}, 0, Messages.getQuestionIcon());
        if (selection == 2 || selection == -1) {
            classNameSelection = new ClassNameSelection(null, ClassNameSelection.UserDecision.Abort);
        } else if (selection == 0) {
            String resolvedClassName = Messages.showInputDialog(project, "请填写需要使用的测试类名:", "测试类", Messages.getQuestionIcon(), className, new InputValidatorEx() { // from class: com.aicode.template.generator.GeneratedClassNameResolver.1
                @Nullable
                public String getErrorText(String inputString) {
                    if (checkInput(inputString)) {
                        return null;
                    }
                    return "类已存在 - " + className + ". 请使用其他类名";
                }

                public boolean checkInput(String inputString) {
                    return !className.equalsIgnoreCase(inputString);
                }

                public boolean canClose(String inputString) {
                    return checkInput(inputString);
                }
            });
            classNameSelection = new ClassNameSelection(resolvedClassName, ClassNameSelection.UserDecision.New);
        } else {
            classNameSelection = new ClassNameSelection(className, ClassNameSelection.UserDecision.Goto);
        }
        ClassNameSelection classNameSelection2 = classNameSelection;
        if (classNameSelection2 == null) {
            $$$reportNull$$$0(8);
        }
        return classNameSelection2;
    }

    private String composeTestClassName(PsiClass targetClass, int prex) {
        JavaCodeStyleSettings customSettings = JavaCodeStyleSettings.getInstance(targetClass.getContainingFile());
        return customSettings.TEST_NAME_PREFIX + targetClass.getName() + customSettings.TEST_NAME_SUFFIX + (prex == 0 ? "" : Integer.valueOf(prex));
    }
}
