package com.aicode.service.editor;

import com.aicode.exception.RequestCancelException;
import com.aicode.service.EditorRequestService;
import com.aicode.util.JComponentKt;
import com.aicode.util.PluginInfoUtils;
import com.aicode.util.PsiUtils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.DocumentEx;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.util.containers.ContainerUtil;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

/* compiled from: ac */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/EditorUtil.class */
public final class EditorUtil {

    /* renamed from: enum, reason: not valid java name */
    public static final Key<List<EditorRequestService>> f589enum = Key.create(JComponentKt.H("\u001f,��/\u0002u\u0018*\r)\u0012*\u0011\u0010\u00014\u0016=\rfG"));

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m287enum(int a) {
        String H = RequestCancelException.H("u!\u0018mO Z'\u0003\"H2\u0018\u001f|:@\u001d\ntXsE3J>H'J<QdX=gT\u0015=^\u007f��1\u0010|Pdi\u0016V6\u00181R.\u00141ZxJ6P7");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 1:
            case 4:
            case 6:
            default:
                objArr[0] = JComponentKt.H("'��0\u000b.\u0015");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = RequestCancelException.H("P<\\-I&R/");
                break;
            case 3:
                objArr[0] = JComponentKt.H("\u000f)\b,,-\n-\u001a/\u0013");
                break;
            case 5:
                objArr[0] = RequestCancelException.H("!Z)Q&O/");
                break;
        }
        objArr[1] = JComponentKt.H(";\u0011.J?\u0011aK\r*g\u001d \u0011.\u0017 ��q\u001d\u001d6,\u00117L\u0005\u0002 \u001b-\u0016\f\u000b(\u000b");
        switch (a) {
            case 0:
            default:
                objArr[2] = RequestCancelException.H("\nV\u0004W<H)Q7z<M7S)");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = JComponentKt.H("\u0010,\u000b\u001b)\u0006#\u0012,\u000b\u0007��0\u000b.\u0015");
                break;
            case 2:
                objArr[2] = RequestCancelException.H("s\u0016A\u0016W<P/[7W\tk\u0007L$Q<\\.]<Q\u000bP\"Q+");
                break;
            case 3:
                objArr[2] = JComponentKt.H("/\u0016*\u0011;\u000b\t>;\u001b\u0015\u0011%�� \u0017\u000e\u00017\u00185\u000f");
                break;
            case 4:
            case 5:
                objArr[2] = RequestCancelException.H("e\u0007A\u0007\\6I5F\u0001Z)Q&O/");
                break;
            case 6:
                objArr[2] = JComponentKt.H("\u001f\u001c+\u001d\u001a,\u0017/\u0014\u001b\n3\u0011<\f5\u0014");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static boolean isSelectedEditor(@NotNull Editor editor) {
        Editor editor2;
        if (editor == null) {
            m287enum(1);
        }
        Project project = editor.getProject();
        if (project == null || project.isDisposed()) {
            return false;
        }
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        if (fileEditorManager != null) {
            if (PsiUtils.instanceOf(fileEditorManager, RequestCancelException.H("]6Nj\u0016v`\u0016Y>Q5\u000b-N<M%t\n\u000b$Q3X\u001f\\6V*pK]>M6\u001a\u0015[9@\u0007P:A=f>T<Y8@0w4S("))) {
                Editor editor3 = null;
                try {
                    editor3 = (Editor) Class.forName(JComponentKt.H(" \n3VkJ\u001d*$\u0002,\tv\u00113��0\u0019\t6v\u0018,\u000f%#!\n+\u0016\rw \u00020\ng)&\u0005=;-\u0006<\u0001\u001b\u0002)��$\u0004=\f\n\b.\u0014")).getMethod(RequestCancelException.H("4W!v'X6V&q\u0017a7@+`&W-L6"), Boolean.TYPE).invoke(fileEditorManager, true);
                    editor2 = editor3;
                } catch (Exception unused) {
                    editor2 = editor3;
                }
                return editor2 != null && editor3.equals(editor);
            }
            TextEditor selectedEditor = fileEditorManager.getSelectedEditor();
            return (selectedEditor instanceof TextEditor) && editor.equals(selectedEditor.getEditor());
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static long getDocumentModificationStamp(@NotNull Document document) {
        if (document == null) {
            m287enum(2);
        }
        return document instanceof DocumentEx ? ((DocumentEx) document).getModificationSequence() : document.getModificationStamp();
    }

    public static void addEditorRequest(@NotNull Editor editor, @NotNull EditorRequestService request) {
        if (editor == null) {
            m287enum(4);
        }
        if (request == null) {
            m287enum(5);
        }
        com.intellij.openapi.editor.ex.util.EditorUtil.disposeWithEditor(editor, request.getDisposable());
        if (!f589enum.isIn(editor)) {
            f589enum.set(editor, ContainerUtil.createLockFreeCopyOnWriteList());
        }
        ((List) f589enum.getRequired(editor)).add(request);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static boolean isFocusedEditor(@NotNull Editor editor) {
        if (editor == null) {
            m287enum(0);
        }
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            return true;
        }
        return PluginInfoUtils.isRemoteIDE() || editor.getContentComponent().isFocusOwner();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static int whitespacePrefixLength(@NotNull String lineContent) {
        if (lineContent == null) {
            m287enum(3);
        }
        int length = lineContent.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char charAt = lineContent.charAt(i2);
            if (charAt != ' ' && charAt != '\t') {
                return i2;
            }
            i2++;
            i = i2;
        }
        return i2;
    }

    @TestOnly
    public static List<EditorRequestService> getEditorRequests(@NotNull Editor editor) {
        if (editor == null) {
            m287enum(6);
        }
        return (List) f589enum.get(editor, List.of());
    }
}
