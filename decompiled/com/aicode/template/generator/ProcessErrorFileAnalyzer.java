package com.aicode.template.generator;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatementBase;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/ProcessErrorFileAnalyzer.class */
public class ProcessErrorFileAnalyzer {
    private static final Logger LOG = Logger.getInstance(ProcessErrorFileAnalyzer.class);
    private final Project project;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 0:
            default:
                objArr[0] = "virtualFile";
                break;
            case 1:
                objArr[0] = "lines";
                break;
        }
        objArr[1] = "com/aicode/template/generator/ProcessErrorFileAnalyzer";
        objArr[2] = "processFile";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ProcessErrorFileAnalyzer(Project project) {
        this.project = project;
    }

    public boolean processFile(@NotNull final VirtualFile virtualFile, @NotNull final Set<Integer> lines) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (lines == null) {
            $$$reportNull$$$0(1);
        }
        final Set<Integer> commentLines = new HashSet<>();
        ((Boolean) ApplicationManager.getApplication().runReadAction(new Computable<Boolean>() { // from class: com.aicode.template.generator.ProcessErrorFileAnalyzer.1
            /* renamed from: compute, reason: merged with bridge method [inline-methods] */
            public Boolean m347compute() {
                PsiManager psiManager = PsiManager.getInstance(ProcessErrorFileAnalyzer.this.project);
                PsiFile psiFile = psiManager.findFile(virtualFile);
                if (psiFile == null || !psiFile.isValid()) {
                    return false;
                }
                Document document = PsiDocumentManager.getInstance(ProcessErrorFileAnalyzer.this.project).getDocument(psiFile);
                for (PsiImportList psiImportList : PsiTreeUtil.collectElementsOfType(psiFile, new Class[]{PsiImportList.class})) {
                    for (int i = 0; i < psiImportList.getAllImportStatements().length; i++) {
                        PsiImportStatementBase importStatement = psiImportList.getAllImportStatements()[i];
                        TextRange textRange = importStatement.getTextRange();
                        int startOffset = textRange.getStartOffset();
                        int endOffset = textRange.getEndOffset();
                        int startLineNumber = document.getLineNumber(startOffset) + 1;
                        int endLineNumber = document.getLineNumber(endOffset) + 1;
                        for (Integer line : lines) {
                            if (startLineNumber <= line.intValue() && endLineNumber >= line.intValue()) {
                                commentLines.add(Integer.valueOf(startLineNumber - 1));
                                commentLines.add(Integer.valueOf(endLineNumber - 1));
                            }
                        }
                    }
                }
                ProcessErrorFileAnalyzer.this.collectErrorLine(commentLines, lines, document, psiFile, PsiField.class);
                ProcessErrorFileAnalyzer.this.collectErrorLine(commentLines, lines, document, psiFile, PsiAnnotation.class);
                ProcessErrorFileAnalyzer.this.collectErrorLine(commentLines, lines, document, psiFile, PsiMethod.class);
                return true;
            }
        })).booleanValue();
        WriteCommandAction.runWriteCommandAction(this.project, () -> {
            try {
                PsiManager psiManager = PsiManager.getInstance(this.project);
                PsiFile psiFile = psiManager.findFile(virtualFile);
                Document document = PsiDocumentManager.getInstance(this.project).getDocument(psiFile);
                Iterator it = commentLines.iterator();
                while (it.hasNext()) {
                    Integer commentLine = (Integer) it.next();
                    int start = document.getLineStartOffset(commentLine.intValue());
                    document.insertString(start, "// ");
                }
                PsiDocumentManager.getInstance(this.project).commitDocument(document);
            } catch (Exception e) {
                LOG.warn("注释异常方法失败", e);
            }
        });
        return true;
    }

    private <T extends PsiElement> void collectErrorLine(Set<Integer> commentLines, Set<Integer> lines, Document document, PsiFile psiFile, Class<T> elementClass) {
        for (PsiElement element : PsiTreeUtil.collectElementsOfType(psiFile, new Class[]{elementClass})) {
            TextRange textRange = element.getTextRange();
            int startOffset = textRange.getStartOffset();
            int endOffset = textRange.getEndOffset();
            int startLineNumber = document.getLineNumber(startOffset) + 1;
            int endLineNumber = document.getLineNumber(endOffset) + 1;
            for (Integer line : lines) {
                if (startLineNumber <= line.intValue() && endLineNumber >= line.intValue()) {
                    for (int i = startLineNumber; i < endLineNumber + 1; i++) {
                        commentLines.add(Integer.valueOf(i - 1));
                    }
                }
            }
        }
    }
}
