package com.aicode.template;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatementBase;
import com.intellij.psi.PsiImportStaticStatement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import java.lang.reflect.Method;
import java.util.Collection;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/CodeRefactorUtil.class */
public class CodeRefactorUtil {
    public static final String COMMENTED_IMPORT_TOKEN = "//import ";
    private static final Logger LOG;
    static final /* synthetic */ boolean $assertionsDisabled;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 1:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            default:
                i2 = 3;
                break;
            case 1:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            default:
                objArr[0] = "importStatement";
                break;
            case 1:
                objArr[0] = "com/aicode/template/CodeRefactorUtil";
                break;
        }
        switch (i) {
            case 0:
            default:
                objArr[1] = "com/aicode/template/CodeRefactorUtil";
                break;
            case 1:
                objArr[1] = "createImportStatementOnDemand";
                break;
        }
        switch (i) {
            case 0:
            default:
                objArr[2] = "createImportStatementOnDemand";
                break;
            case 1:
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            default:
                throw new IllegalArgumentException(format);
            case 1:
                throw new IllegalStateException(format);
        }
    }

    static {
        $assertionsDisabled = !CodeRefactorUtil.class.desiredAssertionStatus();
        LOG = Logger.getInstance(CodeRefactorUtil.class.getName());
    }

    public void uncommentImports(PsiFile psiFile, Project project) {
        PsiElement newImport;
        Collection<PsiComment> psiComments = PsiTreeUtil.findChildrenOfType(psiFile, PsiComment.class);
        for (PsiComment psiComment : psiComments) {
            String commentText = psiComment.getText();
            if (commentText != null && commentText.startsWith("//import ") && (newImport = extractImportStatement(psiFile, project, commentText.replace("//import ", "import "))) != null) {
                String prevSiblingText = psiComment.getPrevSibling() == null ? null : psiComment.getPrevSibling().getText();
                if (prevSiblingText != null && (prevSiblingText.equals("\n\n") || prevSiblingText.equals("\n"))) {
                    psiComment.getPrevSibling().delete();
                }
                psiComment.replace(newImport);
            }
        }
    }

    private PsiElement extractImportStatement(PsiFile psiFile, Project project, String unCommentedImport) {
        PsiElement newImport = null;
        String fileTypeName = psiFile.getFileType().getName();
        if ("JAVA".equalsIgnoreCase(fileTypeName)) {
            newImport = createImportStatementOnDemand(project, unCommentedImport, unCommentedImport.contains("static"));
        } else if ("Groovy".equalsIgnoreCase(fileTypeName)) {
            newImport = createGroovyImport(project, unCommentedImport);
        } else {
            LOG.info("Unsupported source file type " + fileTypeName);
        }
        return newImport;
    }

    private PsiElement createGroovyImport(Project project, String unCommentedImport) {
        PsiElement newImport = null;
        try {
            Class<?> aClass = Class.forName("org.jetbrains.plugins.groovy.lang.psi.GroovyPsiElementFactory");
            Object service = ServiceManager.getService(project, aClass);
            if (service != null && aClass.isAssignableFrom(service.getClass())) {
                Method method = aClass.getMethod("createImportStatementFromText", String.class);
                Object psiImport = method.invoke(service, unCommentedImport);
                if (psiImport instanceof PsiElement) {
                    newImport = (PsiElement) psiImport;
                }
            }
        } catch (Throwable e) {
            LOG.warn("当前加载groovy plugin插件失败", e);
        }
        return newImport;
    }

    @NotNull
    private PsiElement createImportStatementOnDemand(Project project, @NotNull String importStatement, boolean isStatic) throws IncorrectOperationException {
        if (importStatement == null) {
            $$$reportNull$$$0(0);
        }
        PsiJavaFile aFile = createDummyJavaFile(project, importStatement);
        PsiImportStatementBase statement = extractImport(aFile, isStatic);
        if (statement == null) {
            $$$reportNull$$$0(1);
        }
        return statement;
    }

    static PsiImportStatementBase extractImport(PsiJavaFile aFile, boolean isStatic) {
        PsiImportList importList = aFile.getImportList();
        if (!$assertionsDisabled && importList == null) {
            throw new AssertionError(aFile);
        }
        PsiImportStaticStatement[] importStaticStatements = isStatic ? importList.getImportStaticStatements() : importList.getImportStatements();
        if ($assertionsDisabled || importStaticStatements.length == 1) {
            return importStaticStatements[0];
        }
        throw new AssertionError(aFile.getText());
    }

    private PsiJavaFile createDummyJavaFile(Project project, @NonNls String text) {
        return PsiFileFactory.getInstance(project).createFileFromText("_Dummy_.java", JavaFileType.INSTANCE, text);
    }
}
