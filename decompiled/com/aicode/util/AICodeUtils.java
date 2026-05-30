package com.aicode.util;

import com.aicode.content.util.file.LanguageFileExtensionDetails;
import com.aicode.message.BasicActionsBundle;
import com.aicode.ui.ActionButton;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: qb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/AICodeUtils.class */
public class AICodeUtils {
    public static /* synthetic */ String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        int length = stringBuffer.length() - 1;
        int i = ((3 ^ 5) << 4) ^ (1 << 1);
        int i2 = ((3 ^ 5) << 4) ^ 1;
        int i3 = ((3 ^ 5) << 4) ^ ((3 << 2) ^ 3);
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i4 = length2 - 1;
        int i5 = i4;
        int i6 = length;
        while (i4 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (i2 ^ (str.charAt(i7) ^ stringBuffer.charAt(i6)));
            if (i8 < 0) {
                break;
            }
            char charAt = (char) (i3 ^ (str.charAt(i8) ^ stringBuffer.charAt(i6)));
            i5 = i8 - 1;
            i6--;
            cArr[i8] = charAt;
            if (i6 < 0) {
                i6 = length;
            }
        }
        return new String(cArr);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum */
    private static /* synthetic */ void m392enum(int a) {
        String H = LanguageFileExtensionDetails.H("C `\u0016J\u001ci\u0004nNp\f|\u0016mD)\u0012`\u0006RlD1P:/\fk\u001d%\u001aP,a\u000bgDb\u0014\u007f\u0012");
        Object[] objArr = new Object[2];
        objArr[0] = ActionButton.H("\u0011\u0002\u001a��T)9'6'w\u001b��\f\u0013K?\u001d\r\u001a\u000b\r'\u0019\u001e\u001e\u001b");
        switch (a) {
            case 0:
            default:
                objArr[1] = LanguageFileExtensionDetails.H("f\u001aP9`\u001cF\u0002j\u0012v\n");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = ActionButton.H("\u0013��\u000b)\u001b &\u001a\u000b'\u0014\u000b\u0004\u0017\u001c");
                break;
        }
        throw new IllegalStateException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ Editor getEditorFromAbsolutePath(Project a, String a2) {
        try {
            VirtualFile findFileByPath = LocalFileSystem.getInstance().findFileByPath(a2);
            if (findFileByPath != null) {
                TextEditor selectedEditor = FileEditorManager.getInstance(a).getSelectedEditor(findFileByPath);
                if (selectedEditor != null) {
                    return selectedEditor.getEditor();
                }
                Document document = FileDocumentManager.getInstance().getDocument(findFileByPath);
                if (document != null) {
                    return EditorFactory.getInstance().createEditor(document, a);
                }
                return null;
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    public static /* synthetic */ String getWasmsDirectoryPath() {
        return System.getProperty(ActionButton.H(";\u0006\n\u001a\\\u0005\u0018\u001f\r")) + File.separator + ".iflycode" + File.separator + FileUtils.WASM_DIR;
    }

    public static /* synthetic */ String getAgentDirectoryPath() {
        return System.getProperty(ActionButton.H(";\u0006\n\u001a\\\u0005\u0018\u001f\r")) + File.separator + ".iflycode" + File.separator + "bin" + File.separator + BasicActionsBundle.message(LanguageFileExtensionDetails.H("H-@!k\u0007*\bb\r[,:\u000fl\u0016\u007f\b|\u0010"), new Object[0]);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ PsiMethod getPsiMethodContent(PsiFile a, int a2, int a3) {
        PsiJavaFile psiJavaFile = (PsiJavaFile) a;
        Collection<PsiMethod> collection = (Collection) ApplicationManager.getApplication().runReadAction(() -> {
            return PsiTreeUtil.findChildrenOfType(psiJavaFile, PsiMethod.class);
        });
        if (!CollectionUtils.isEmpty(collection)) {
            List<Integer> w = w(a2, a3);
            for (PsiMethod psiMethod : collection) {
                if (p(y(psiMethod), w)) {
                    return psiMethod;
                }
            }
            return null;
        }
        return null;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Nullable
    public static /* synthetic */ VirtualFile getVirtualFile(Project a) {
        FileEditor selectedEditor = FileEditorManager.getInstance(a).getSelectedEditor();
        if (Objects.isNull(selectedEditor)) {
            return null;
        }
        VirtualFile file = selectedEditor.getFile();
        if (!Objects.isNull(file)) {
            return file;
        }
        return null;
    }

    private static /* synthetic */ boolean p(List<Integer> list, List<Integer> list2) {
        list.retainAll(list2);
        return CollectionUtils.isNotEmpty(list);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ Editor getEditor(FileEditorManager a, VirtualFile a2) {
        TextEditor[] editors = a.getEditors(a2);
        int length = editors.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            TextEditor textEditor = editors[i2];
            if (!(textEditor instanceof TextEditor)) {
                i2++;
                i = i2;
            } else {
                return textEditor.getEditor();
            }
        }
        return null;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    private static /* synthetic */ List<Integer> y(PsiMethod a) {
        ArrayList newArrayList = Lists.newArrayList();
        TextRange textRange = a.getTextRange();
        int startOffset = textRange.getStartOffset();
        int endOffset = textRange.getEndOffset();
        int i = startOffset;
        int i2 = i;
        while (i <= endOffset) {
            int i3 = i2;
            i2++;
            newArrayList.add(Integer.valueOf(i3));
            i = i2;
        }
        if (newArrayList == null) {
            m392enum(1);
        }
        return newArrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    private static /* synthetic */ List<Integer> w(int a, int a2) {
        ArrayList arrayList = new ArrayList();
        int i = a;
        int a3 = i;
        while (i <= a2) {
            int i2 = a3;
            a3++;
            arrayList.add(Integer.valueOf(i2));
            i = a3;
        }
        if (arrayList == null) {
            m392enum(0);
        }
        return arrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ List<String> getOpenFilePathList(Project a) {
        ArrayList arrayList = new ArrayList();
        VirtualFile[] openFiles = FileEditorManager.getInstance(a).getOpenFiles();
        int length = openFiles.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            VirtualFile virtualFile = openFiles[i2];
            i2++;
            arrayList.add(virtualFile.getPath());
            i = i2;
        }
        return arrayList;
    }
}
