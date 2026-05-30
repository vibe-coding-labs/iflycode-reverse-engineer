package com.aicode.content.util;

import com.aicode.dto.FileIndexDto;
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorKind;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.testFramework.LightVirtualFile;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: zn */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/content/util/EditorUtils.class */
public final class EditorUtils {
    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        int length = stringBuffer.length() - 1;
        int i = 5 << 3;
        int i2 = (4 << 3) ^ 5;
        int i3 = ((2 ^ 5) << 4) ^ 3;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i4 = length2 - 1;
        int i5 = i4;
        int i6 = length;
        while (i4 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (i ^ (str.charAt(i7) ^ stringBuffer.charAt(i6)));
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

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m115enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
                H = H("S;}4}.r37*y?}F^$h\th*k|f,o'j9)ct} yif?+;&\u0013\u001e9iiaj)o3!4I\t'>\u007faq1l7");
                i = a;
                break;
            case 10:
                do {
                } while (0 != 0);
                H = OverlayUtils.H("\u001c?\"\u0014\b\u001e0\u001d&F8\u00044\u001e%La\u001a(\u000e\u001e`!\u00142\u0018|\u001f(\u001ez\u0005\u0018$)\u0003/L*\u001c7\u001a");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
                i2 = 3;
                break;
            case 10:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 5:
            case 7:
            case 9:
            default:
                objArr[0] = H(",h.u!c/");
                i3 = a;
                break;
            case 4:
            case 6:
            case 8:
                do {
                } while (0 != 0);
                objArr[0] = OverlayUtils.H("0\f#\u0002");
                i3 = a;
                break;
            case 10:
                objArr[0] = H("?2k)<n?u%zk>iX\u0019r\"nnr(u+.\u001fB\u0014s3h\u0014k-l(");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
                objArr[1] = OverlayUtils.H("\u0012iFr\u00115\u0012.\b!FeD\u00034)\u000f5C)\u0005.\u0006u2\u00199(\u001e390��7\u0005");
                i4 = a;
                break;
            case 10:
                do {
                } while (0 != 0);
                objArr[1] = H(";y3G3J\u0018N2~$g��t4");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = OverlayUtils.H("9\u0005\u00181(\u0014\u0004\b-\u001d4\u0004");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = H("&b(O\"m?E\tb8_%v0o)");
                break;
            case 2:
                objArr[2] = OverlayUtils.H("oX !%\u000f\u0004\b5\u0005(\u0018\u000e\u0012\u0005$\u000f\u0014-\t'\u001d>\u0012");
                break;
            case 3:
            case 4:
                objArr[2] = H("m!-jW\u000er\u0001{(i\u0019x.u5T.b0\u007f\"k-o5");
                break;
            case 5:
            case 6:
                objArr[2] = OverlayUtils.H(")\"\u0012$\u001e(2(\u0004.\u0012\u0013$\u0013\u001f\f\t0\u00014\u0012");
                break;
            case 7:
            case 8:
                objArr[2] = H("4hE\be8Y.i(y)u\u000fH\u0019b.W$k,o?");
                break;
            case 9:
                objArr[2] = OverlayUtils.H("\u0005(\u001f=\u0013+\u000f\u0012\u001e\u001a80\u0018&\u00040��5\u0011");
                break;
            case 10:
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
                throw new IllegalArgumentException(format);
            case 10:
                throw new IllegalStateException(format);
        }
    }

    @NotNull
    public static FileIndexDto getFileIndexDto(Editor a, String a2) {
        SelectionModel selectionModel = a.getSelectionModel();
        int selectionStart = selectionModel.getSelectionStart();
        int selectionEnd = selectionModel.getSelectionEnd();
        Document document = a.getDocument();
        int lineNumber = document.getLineNumber(selectionStart) + 1;
        int lineNumber2 = document.getLineNumber(selectionEnd) + 1;
        VirtualFile file = FileEditorManager.getInstance(a.getProject()).getSelectedEditor().getFile();
        FileIndexDto fileIndexDto = new FileIndexDto();
        if (file != null) {
            fileIndexDto.setFileName(file.getName());
            fileIndexDto.setFilePath(file.getPath());
        }
        fileIndexDto.setTitle(a2);
        fileIndexDto.setSelectStartLine(lineNumber);
        fileIndexDto.setSelectEndLine(lineNumber2);
        if (fileIndexDto == null) {
            m115enum(10);
        }
        return fileIndexDto;
    }

    public static void replaceMainEditorSelection(@NotNull Project project, @NotNull String text) {
        if (project == null) {
            m115enum(3);
        }
        if (text == null) {
            m115enum(4);
        }
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> {
            application.runWriteAction(() -> {
                WriteCommandAction.runWriteCommandAction(project, () -> {
                    Editor selectedEditor = getSelectedEditor(project);
                    if (selectedEditor == null) {
                        return;
                    }
                    SelectionModel selectionModel = selectedEditor.getSelectionModel();
                    selectedEditor.getDocument().replaceString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd(), text);
                    selectedEditor.getContentComponent().requestFocus();
                    selectionModel.removeSelection();
                });
            });
        });
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static boolean hasSelection(@Nullable Editor editor) {
        return editor != null && editor.getSelectionModel().hasSelection();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static Editor getAndJumpToFileByIndex(Project a, FileIndexDto a2) {
        VirtualFile findFileByIoFile;
        if (a2 != null && a != null && (findFileByIoFile = LocalFileSystem.getInstance().findFileByIoFile(new File(a2.getFilePath()))) != null) {
            Editor openTextEditor = FileEditorManager.getInstance(a).openTextEditor(new OpenFileDescriptor(a, findFileByIoFile), true);
            if (openTextEditor == null) {
                return null;
            }
            int lineStartOffset = openTextEditor.getDocument().getLineStartOffset(a2.getSelectStartLine() - 1);
            int lineEndOffset = openTextEditor.getDocument().getLineEndOffset(a2.getSelectEndLine() - 1);
            openTextEditor.getSelectionModel().setSelection(lineStartOffset, lineEndOffset);
            openTextEditor.getCaretModel().moveToOffset(lineEndOffset);
            openTextEditor.getScrollingModel().scrollToCaret(ScrollType.CENTER_UP);
            return openTextEditor;
        }
        return null;
    }

    public static boolean isMainEditorTextSelected(@NotNull Project project) {
        if (project == null) {
            m115enum(2);
        }
        return hasSelection(getSelectedEditor(project));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Nullable
    public static Editor getSelectedEditor(@NotNull Project project) {
        if (project == null) {
            m115enum(1);
        }
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        if (fileEditorManager != null) {
            return fileEditorManager.getSelectedTextEditor();
        }
        return null;
    }

    public static void insertContentUnderMethod(@NotNull Project project, @NotNull String text, PsiMethod method) {
        if (project == null) {
            m115enum(7);
        }
        if (text == null) {
            m115enum(8);
        }
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> {
            application.runWriteAction(() -> {
                WriteCommandAction.runWriteCommandAction(project, () -> {
                    method.addAfter(PsiElementFactory.getInstance(project).createDocCommentFromText(text), method);
                });
            });
        });
    }

    public static void insertContentOnMethod(@NotNull Project project, @NotNull String text, PsiMethod method) {
        if (project == null) {
            m115enum(5);
        }
        if (text == null) {
            m115enum(6);
        }
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> {
            application.runWriteAction(() -> {
                WriteCommandAction.runWriteCommandAction(project, () -> {
                    method.addBefore(PsiElementFactory.getInstance(project).createDocCommentFromText(text), method);
                });
            });
        });
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static Editor createEditor(@NotNull Project project, String fileExtension, String code) {
        Document createDocument;
        if (project == null) {
            m115enum(0);
        }
        LightVirtualFile lightVirtualFile = new LightVirtualFile(String.format(OverlayUtils.H("I7F~\u0005"), PathManager.getTempPath(), "temp_" + DateTimeFormatter.ofPattern(H("e>x#k0c8R\tr)s(")).format(LocalDateTime.now()) + fileExtension), code);
        Document document = FileDocumentManager.getInstance().getDocument(lightVirtualFile);
        if (document != null) {
            createDocument = document;
        } else {
            createDocument = EditorFactory.getInstance().createDocument(code);
        }
        Document document2 = createDocument;
        disableHighlighting(project, document2);
        return EditorFactory.getInstance().createEditor(document2, project, lightVirtualFile, false, EditorKind.MAIN_EDITOR);
    }

    public static void disableHighlighting(@NotNull Project project, Document document) {
        if (project == null) {
            m115enum(9);
        }
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        if (psiFile == null) {
            return;
        }
        DaemonCodeAnalyzer.getInstance(project).setHighlightingEnabled(psiFile, false);
    }
}
