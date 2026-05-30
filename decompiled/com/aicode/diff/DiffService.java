package com.aicode.diff;

import com.aicode.agent.service.InlineChatCommandService;
import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.FileUtils;
import com.intellij.diff.DiffContentFactory;
import com.intellij.diff.DiffDialogHints;
import com.intellij.diff.DiffManager;
import com.intellij.diff.chains.SimpleDiffRequestChain;
import com.intellij.diff.contents.DocumentContent;
import com.intellij.diff.editor.ChainDiffVirtualFile;
import com.intellij.diff.requests.SimpleDiffRequest;
import com.intellij.diff.util.DiffUserDataKeys;
import com.intellij.diff.util.Side;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWithProviderComposite;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import org.jetbrains.annotations.NotNull;

/* compiled from: xk */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/diff/DiffService.class */
public class DiffService {

    /* renamed from: float, reason: not valid java name */
    private static final String f211float = FileInfo.H("\u0014R\u0011mv3;SH!9}\u001brr\u001b\u000by��q\u0018a6r\u001ew7s");
    public static final String tempDirectoryName = ConditionalActionConfiguration.H("$\u001d\u0006\u0011\u001d\u001f-\u0005E@");

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f213enum = Logger.getInstance(DiffService.class);

    /* renamed from: try, reason: not valid java name */
    private static final String f210try = BasicActionsBundle.message(FileInfo.H("z\u0014w\u0012p-\u000f\u000e{\u001cg\u0004j\\o\u0011e\u0012r"), new Object[0]) + ": " + BasicActionsBundle.message(ConditionalActionConfiguration.H("\u0019\u0014BK\u001a\u001e_\u0015\u0017\u0005\u001a\u0019\u001a_\r\u0005E@"), new Object[0]);

    /* renamed from: byte, reason: not valid java name */
    private static final String f212byte = System.getProperty(FileInfo.H("\"@\bvGi\u0002*\u0006v\bu\u0017e"));
    public static Key<VirtualFile> DIFF_FILEPATH_LEFT = Key.create(ConditionalActionConfiguration.H("*\u001d\u0017\u0018U<4gb!=881!2\"<.%)er"));
    public static Key<VirtualFile> DIFF_FILEPATH_RIGHT = Key.create(FileInfo.H("q59P\\_4R;K\u000eh2R9A9L-I1V6C"));

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m121enum(int a) {
        String H = ConditionalActionConfiguration.H("?\t\u0015\u0002\u001d\u0010]B\\\u001f\u001b\u0003^;<\u0018\r2\r\u0011_\u0016\t\u001d\u0006\u0010\u0016\u001b\u000f\u001bA\u0016~y\u0007V[\u0011\u001d^k8VX\u001cJ\u0006\u001b\u0007\u0005^\u0015\u0017\tX\u001f\u0016V\u0006\u0018\u001c\u0019");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 1:
            case 3:
            default:
                objArr[0] = FileInfo.H("t��t\u0012t\u001dc");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = ConditionalActionConfiguration.H("\u0016\u0012\u0001\u0019\u001f\u0007");
                break;
        }
        objArr[1] = FileInfo.H("w\u0012y\u001a=6U\u001d\u007f\u0018;\u0019}.GQS��f\u000bW\u0017i\u000ex\u001dr");
        switch (a) {
            case 0:
            default:
                objArr[2] = ConditionalActionConfiguration.H("$\b\u0018\u0001.\u0002\b\u0012'\u0017\u001e\u000f;\u0017\u000f2?+\u0002\u0014\u0010");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = FileInfo.H("\u0002t\u0017u<x\u0018q");
                break;
            case 2:
                objArr[2] = ConditionalActionConfiguration.H("��\u001a\u000e��=\u001f\u0012\u0012\u0016\u0018;\u0015\u0012\u0002,\u0004\u0016\u0013");
                break;
            case 3:
                objArr[2] = FileInfo.H("\u001ex\u0012gP\u00186P\u0014M\u0014q\n].`\u0012e\fa\t}=k\u001d\u007f\u001bs");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void openInlineChatDiff(@NotNull Editor editor, String content, Document document, int startLineOffset, int a) {
        if (editor == null) {
            m121enum(2);
        }
        try {
            Project project = editor.getProject();
            VirtualFile file = FileDocumentManager.getInstance().getFile(document);
            if (file == null) {
                f213enum.info(ConditionalActionConfiguration.H("\b\u000f\u001a\u001f\u0011\u000fX\u001a\u0017\u0003M\u001e\u0011\u000f\u0007\u00035=S\u0010\u0018\u0018\u0011Q\u0011\u000fX\u000e;7\u0010\u0013\b\u001c\rL=3\u0017\u0004\u0016\u001b\u0015\n"));
                return;
            }
            String name = file.getName();
            String str = editor.getUserData(InlineChatCommandService.VERSION_KEY);
            String str2 = f212byte + File.separator + "PluginDiff" + File.separator + GenericUtils.getVersionedFileName(name, str);
            if (!new File(str2).exists()) {
                return;
            }
            VirtualFile refreshAndFindFileByPath = LocalFileSystem.getInstance().refreshAndFindFileByPath(str2);
            if (refreshAndFindFileByPath == null) {
                f213enum.info(FileInfo.H("\u0015~[2\u0015gR|\u001deTk\u0016d\nbT0_p\u001bw\u00184\u001fm\u0015/:Z\u0017x\tq\u00194,N\u001db\u0004e\u0003pRi\u0011v\u0016c"));
                return;
            }
            Document document2 = FileDocumentManager.getInstance().getDocument(refreshAndFindFileByPath);
            if (document2 == null) {
                return;
            }
            String content2 = FileService.addWriteSpace(content, document2.getText().substring(startLineOffset, a));
            Files.createDirectories(Paths.get(f212byte + File.separator + "PluginDiff", new String[0]), new FileAttribute[0]);
            String str3 = f212byte + File.separator + "PluginDiff" + File.separator + GenericUtils.getVersionedFileName(name, str + "_suggestion_" + GenericUtils.generateRandomInt(0, 100000));
            FileUtils.copyFile(str2, str3);
            VirtualFile refreshAndFindFileByPath2 = LocalFileSystem.getInstance().refreshAndFindFileByPath(str3);
            if (refreshAndFindFileByPath2 == null) {
                f213enum.info(ConditionalActionConfiguration.H("(\u0019\u0013\u0001\u0005\u001fN\u0013\u0014\n[\u000e\u0014��\u0003\u0018\t\u0014]\u0015\u001f84S\u0014\bT\u0007\u0014\u001f\u0013\u001b\t;?U\u0014\u0013\u001a\u001c\u0001<2��Q\u0017\u001b\u001d\n"));
                return;
            }
            replaceTextInFile(refreshAndFindFileByPath2, startLineOffset, a, content2);
            refreshAndFindFileByPath2.refresh(false, false);
            Document document3 = FileDocumentManager.getInstance().getDocument(refreshAndFindFileByPath2);
            FileType fileTypeByFileName = FileTypeManager.getInstance().getFileTypeByFileName(name);
            DocumentContent create = DiffContentFactory.getInstance().create(project, document3.getText(), fileTypeByFileName);
            DocumentContent create2 = DiffContentFactory.getInstance().create(project, document2.getText(), fileTypeByFileName);
            String str4 = refreshAndFindFileByPath2.getName() + "(建议代码)";
            String str5 = refreshAndFindFileByPath.getName() + "(原始代码)";
            int startLineOffset2 = document2.getLineNumber(startLineOffset);
            SimpleDiffRequest simpleDiffRequest = new SimpleDiffRequest(f210try, create, create2, str4, str5);
            simpleDiffRequest.putUserData(DiffUserDataKeys.SCROLL_TO_LINE, Pair.create(Side.RIGHT, Integer.valueOf(startLineOffset2)));
            simpleDiffRequest.putUserData(DiffUserDataKeys.PREFERRED_FOCUS_SIDE, Side.RIGHT);
            simpleDiffRequest.putUserData(CloudDiffUtil.DIFF_FILENAME, name);
            simpleDiffRequest.putUserData(CloudDiffUtil.DIFF_FILEPATH_LEFT, refreshAndFindFileByPath2.getPath());
            simpleDiffRequest.putUserData(CloudDiffUtil.DIFF_FILEPATH_RIGHT, refreshAndFindFileByPath.getPath());
            simpleDiffRequest.putUserData(CloudDiffUtil.DIFF_FILE_UNIQUE_ID, FileInfo.H("\u0014R\u0011mv3;SH!9}\u001brr\u001b\u000by��q\u0018a6r\u001ew7s"));
            DiffManager.getInstance().showDiff(project, simpleDiffRequest, DiffDialogHints.DEFAULT);
        } catch (Throwable th) {
        }
    }

    public static void replaceTextInVirtualFile(Project a, VirtualFile a2, int a3, int a4, String a5) {
        Document document = FileDocumentManager.getInstance().getDocument(a2);
        if (document == null) {
            throw new IllegalArgumentException(ConditionalActionConfiguration.H("0\u001e\u001d\u000e\u0015\u0018\u0001\u001eX\u0014\u001eH\u001f\u001b\bY\u001f\r\u0019\u0014%-\u001a\u0011��@\u000f\u0003&q\u0007\u001e.n\u0004\u0003��\u001c��\b\r\t\\/\u0018\u0006/+\u0015\u001d7\u001d\u0014\u0018"));
        }
        WriteCommandAction.runWriteCommandAction(a, () -> {
            try {
                document.replaceString(a3, a4, a5);
            } catch (Throwable th) {
                f213enum.info(ConditionalActionConfiguration.H("\t\u001bCZ8?\u0011%\u001e\u0006\u000f7 \u001d\u0011\u000f\u001b\u001f\n\u00022\u0018\u0012\u001eX\u00189<\u0016\u000e"), th);
            }
        });
        FileDocumentManager.getInstance().saveDocument(document);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void openDiffViewForAICode(@NotNull Project project, String content, Editor a) {
        if (project == null) {
            m121enum(0);
        }
        try {
            SelectionModel selectionModel = a.getSelectionModel();
            openDiff(project, content, a.getDocument(), selectionModel.getSelectionStart(), selectionModel.getSelectionEnd());
        } catch (Throwable th) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void openDiff(@NotNull Project project, String content, Document document, int startLineOffset, int endLineOffset) {
        if (project == null) {
            m121enum(1);
        }
        try {
            VirtualFile file = FileDocumentManager.getInstance().getFile(document);
            if (file == null) {
                f213enum.info(FileInfo.H("\u0011z\u0016\u007f\u001bi_q\u001bc\u0015*\u0016d\u0006n\u001cx]r\\0:\u0016\u0010b]g\u0018x-B\nr\r \tk\u0011n\u0015t\u0010c"));
                return;
            }
            YC(document, file);
            String name = file.getName();
            String path = file.getPath();
            String addWriteSpace = FileService.addWriteSpace(content, document.getText().substring(startLineOffset, endLineOffset));
            VirtualFile refreshAndFindFileByPath = LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
            if (refreshAndFindFileByPath != null) {
                Files.createDirectories(Paths.get(f212byte + File.separator + "PluginDiff", new String[0]), new FileAttribute[0]);
                String str = f212byte + File.separator + "PluginDiff" + File.separator + GenericUtils.getVersionedFileName(name, String.valueOf(GenericUtils.generateRandomInt(0, 10000)));
                FileUtils.copyFile(path, str);
                VirtualFile refreshAndFindFileByPath2 = LocalFileSystem.getInstance().refreshAndFindFileByPath(new File(str).getCanonicalPath());
                if (refreshAndFindFileByPath2 == null) {
                    f213enum.info(FileInfo.H("|T2\u0014|\u0006;\u001ft��=\t\u007f\fc@=\u00136\u0014r\u0011q]vL|,S\u001e~\u001e`\u0018phE\u0011t\u001cm\bj\u0006;\u0014t\u0018c"));
                    return;
                }
                replaceTextInVirtualFile(project, refreshAndFindFileByPath2, startLineOffset, endLineOffset, addWriteSpace);
                SimpleDiffRequest simpleDiffRequest = new SimpleDiffRequest(f210try, DiffContentFactory.getInstance().create(project, refreshAndFindFileByPath2), DiffContentFactory.getInstance().create(project, refreshAndFindFileByPath), refreshAndFindFileByPath2.getName() + "(建议代码)", refreshAndFindFileByPath.getName() + "(原始代码)");
                simpleDiffRequest.putUserData(DIFF_FILEPATH_LEFT, refreshAndFindFileByPath2);
                simpleDiffRequest.putUserData(DIFF_FILEPATH_RIGHT, refreshAndFindFileByPath);
                simpleDiffRequest.putUserData(CloudDiffUtil.DIFF_SUGGEST_CODE, content);
                new DiffDialog(project, simpleDiffRequest).show();
                return;
            }
            f213enum.info(ConditionalActionConfiguration.H("PW\u0017\u0012\u001b\u0005[\u0019\u001e\n\u0013@0.��\u0004\u001a\u0012[\u0018''\u001d]\r\u0013K\u001d\u0011\u001d\u001b\u0018\f\u0018\u0016W\t\u0007\u001b\b\u001e\u00137(T\u0003\u0012\u0019\u0013\n"));
        } catch (Throwable th) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void closeDiffViewIfAlreadyOpened(@NotNull Project a) {
        if (a == null) {
            m121enum(3);
        }
        EditorWithProviderComposite[] editors = FileEditorManagerEx.getInstanceEx(a).getCurrentWindow().getEditors();
        int length = editors.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            EditorWithProviderComposite editorWithProviderComposite = editors[i2];
            try {
                if (ConditionalActionConfiguration.H("<\u0014\u001f\u001a").equals(editorWithProviderComposite.getFile().getName()) && (editorWithProviderComposite.getFile() instanceof ChainDiffVirtualFile)) {
                    if (FileInfo.H("\u0014R\u0011mv3;SH!9}\u001brr\u001b\u000by��q\u0018a6r\u001ew7s").equals(((SimpleDiffRequestChain.DiffRequestProducerWrapper) editorWithProviderComposite.getFile().createProcessor(a).getRequestChain().getRequests().get(0)).getRequest().getUserData(CloudDiffUtil.DIFF_FILE_UNIQUE_ID))) {
                        editorWithProviderComposite.dispose();
                        return;
                    }
                    continue;
                }
            } catch (Exception e) {
            }
            i2++;
            i = i2;
        }
    }

    public static void replaceTextInFile(VirtualFile a, int a2, int a3, String a4) {
        Document document = FileDocumentManager.getInstance().getDocument(a);
        if (document == null) {
            throw new IllegalArgumentException(FileInfo.H("<~\u0017h\u001bz[(Zz\u0001;\u0016~��=\u001e`\u001f~Y=\u001dz\u0017;\u001b{\u000f4A4:\u0016\u0002i\u0012b\u0014p-E^A��r\u0019q\u0013w>x\u0012r"));
        }
        StringBuilder sb = new StringBuilder(document.getText());
        sb.replace(a2, a3, a4);
        FileUtils.copyFileContent(sb.toString(), a.getPath());
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static Document getDocument(Editor a) {
        VirtualFile file = FileDocumentManager.getInstance().getFile(a.getDocument());
        if (file != null) {
            String str = f212byte + File.separator + "PluginDiff" + File.separator + GenericUtils.getVersionedFileName(file.getName(), a.getUserData(InlineChatCommandService.VERSION_KEY));
            if (new File(str).exists()) {
                VirtualFile refreshAndFindFileByPath = LocalFileSystem.getInstance().refreshAndFindFileByPath(str);
                if (refreshAndFindFileByPath != null) {
                    return FileDocumentManager.getInstance().getDocument(refreshAndFindFileByPath);
                }
                f213enum.info(FileInfo.H("\u0015~[2\u0015gR|\u001deTk\u0016d\nbT0_p\u001bw\u00184\u001fm\u0015/:Z\u0017x\tq\u00194,N\u001db\u0004e\u0003pRi\u0011v\u0016c"));
                return null;
            }
            return null;
        }
        f213enum.info(ConditionalActionConfiguration.H(":=\u001a\u001f\u0014\n[\u0019+?X\u000b\u0006\u0018\u001f\u001b\u0015\u001d^\u001d\u0011\u0011\u001f_\u001a\u0004I\u001f<0\u0017\u0014\n\u001e\fM\u0014\u001a\u001b\b\u001e\u0013\u0007\u0018"));
        return null;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void copyFile(String a, String a2, String a3, String a4) {
        try {
            Files.createDirectories(Paths.get(f212byte + File.separator + "PluginDiff", new String[0]), new FileAttribute[0]);
            FileUtils.copyFileContent(a, a2, f212byte + File.separator + "PluginDiff" + File.separator + GenericUtils.getVersionedFileName(a3, a4));
        } catch (Exception unused) {
        }
    }

    private static void YC(Document a, VirtualFile a2) {
        FileDocumentManager.getInstance().saveDocument(a);
        a2.refresh(false, false, (Runnable) null);
    }
}
