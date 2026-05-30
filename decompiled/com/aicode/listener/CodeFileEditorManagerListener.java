package com.aicode.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.CommonService;
import com.aicode.agent.service.RecentFilesManager;
import com.aicode.inline.InlineChatService;
import com.aicode.inline.InlineChatStreamHandleService;
import com.aicode.util.AICodeUtils;
import com.aicode.util.ApplicationUtil;
import com.aicode.util.EditorKt;
import com.aicode.util.HandleCacheUtil;
import com.aicode.util.IndentLineUtil;
import com.aicode.util.Maps;
import com.aicode.util.PropertyUtils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Alarm;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: fe */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/CodeFileEditorManagerListener.class */
public class CodeFileEditorManagerListener implements FileEditorManagerListener {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f502enum = Logger.getInstance(CodeFileEditorManagerListener.class);

    /* renamed from: byte, reason: not valid java name */
    private static final Map<Editor, DocumentListener> f501byte = new ConcurrentHashMap();

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m240enum(int a) {
        String H = HandleCacheUtil.H("V:,aK\u001cd!!8o-\u0003<B<}\u0018u3L_q?~2|+~0eh\u000fRw|+;b{'..zdhD\u0003\u007f'6'~: =ntn*{$");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 7:
            default:
                objArr[0] = Maps.H("\u001c<\u0001:\u000f5");
                break;
            case 1:
            case 4:
            case 5:
            case 6:
            case 8:
                do {
                } while (0 != 0);
                objArr[0] = HandleCacheUtil.H("?x<t*j8F6{-");
                break;
            case 2:
                objArr[0] = Maps.H("=\u000b:��'\u001e#");
                break;
            case 3:
                objArr[0] = HandleCacheUtil.H("-n%u:d<");
                break;
            case 9:
                objArr[0] = Maps.H("6\u0002-\u0002$");
                break;
        }
        objArr[1] = HandleCacheUtil.H("\u001fc>&7i<O\u001bdq`:b:o;r:\u00074k?n\u0012m7g\u0018d6c'[;m=w.t<L6x e1r:");
        switch (a) {
            case 0:
            case 1:
            case 2:
            default:
                objArr[2] = Maps.H("\t:\b=<?\u00016\n7'1\u00023");
                break;
            case 3:
                do {
                } while (0 != 0);
                objArr[2] = HandleCacheUtil.H("a<n<r$h\u001a`\u0012x-P*d\rn%u:d<");
                break;
            case 4:
                objArr[2] = Maps.H("\u0017=\u00017+(\u0016! 7\f&\u0019-\u0002$");
                break;
            case 5:
                objArr[2] = HandleCacheUtil.H("Z\u000fb0R&r;m:e L6d<");
                break;
            case 6:
                objArr[2] = Maps.H("\u0017=\u00017 7\u0010:\t=\u0001'8!\u001f$");
                break;
            case 7:
            case 8:
                objArr[2] = HandleCacheUtil.H("w'l:H8o,r,");
                break;
            case 9:
                objArr[2] = Maps.H("\u0017=\u00036\u0007,\u001a \n\u001b\u00072\u001a/\t4");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public void addListener(Editor a) {
        DocumentListener Uc = Uc();
        a.getDocument().addDocumentListener(Uc);
        f501byte.put(a, Uc);
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public void cancelAllAndAddRequest(@NotNull Runnable request, Alarm alarm, Object LOCK, int a) {
        Object obj;
        if (request == null) {
            m240enum(3);
        }
        synchronized (LOCK) {
            if (!alarm.isDisposed()) {
                alarm.cancelAllRequests();
                try {
                    alarm.addRequest(request, a);
                    obj = LOCK;
                } catch (Throwable unused) {
                    obj = LOCK;
                }
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void fileOpenedSync(@NotNull FileEditorManager source, @NotNull VirtualFile virtualFile, @NotNull Pair<FileEditor[], FileEditorProvider[]> pair) {
        Editor editor;
        if (source == null) {
            m240enum(0);
        }
        if (virtualFile == null) {
            m240enum(1);
        }
        if (pair == null) {
            m240enum(2);
        }
        String path = virtualFile.getPath();
        if (StringUtils.isBlank(path) || !new File(path).exists() || FileTypeManager.getInstance().getFileTypeByFile(virtualFile).isBinary() || (editor = AICodeUtils.getEditor(source, virtualFile)) == null) {
            return;
        }
        addListener(editor);
        RecentFilesManager.fileOpened(source.getProject(), path);
        if (StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
            return;
        }
        sendOpenDocument(virtualFile, path, editor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void VB(Document a, VirtualFile a2, String a3, Editor a4) {
        ApplicationManager.getApplication().runReadAction(() -> {
            try {
                MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.ACTION_OPEN_DOCUMENT.getType());
                messageDto.setPath(a3);
                messageDto.setContent(a.getText());
                if (a4 != null && a4.getCaretModel() != null) {
                    int offset = a4.getCaretModel().getOffset();
                    int lineNumber = a.getLineNumber(offset);
                    int lineStartOffset = offset - a.getLineStartOffset(lineNumber);
                    CodeInfoDto.RangeDTO rangeDTO = new CodeInfoDto.RangeDTO();
                    rangeDTO.setLine(Integer.valueOf(lineNumber));
                    rangeDTO.setCharacter(Integer.valueOf(lineStartOffset));
                    CodeInfoDto.RangeDTO rangeDTO2 = new CodeInfoDto.RangeDTO();
                    rangeDTO2.setLine(Integer.valueOf(lineNumber));
                    rangeDTO2.setCharacter(Integer.valueOf(lineStartOffset));
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(rangeDTO);
                    arrayList.add(rangeDTO2);
                    messageDto.setRange(arrayList);
                }
                PluginWebsocketClient.sendWsMessageWithOutApm(messageDto, ProjectUtil.guessProjectForFile(a2));
            } catch (Throwable th) {
            }
        });
    }

    private DocumentListener Uc() {
        return new DocumentListener() { // from class: com.aicode.listener.CodeFileEditorManagerListener.01
            /* renamed from: enum, reason: not valid java name */
            private static /* synthetic */ void m241enum(int a) {
                throw new IllegalArgumentException(String.format(IndentLineUtil.H("4X\u0019T\u0012E2wSJ\u0019[_`\u0011o\no\u0006@\u0002\u0011\u0005K\u001aV:m\u000fA\u0006\u000b\\\u0001\u000e\u0005_O\u000e\u0017sz]\t\u001a\u0016\u0003D\fTTE\u0010THU[AD��G\u0018"), PropertyUtils.H("4d }0"), IndentLineUtil.H("J\u0010Mpa\u0017B\u001cH\u000b\u001e\u0019C\u001bC2f\u001eV[h\u0014@\u0018d\u0016L\rr2`\u0007C\u001b{\u000f_\u001eG\u0011Y3I\u001bC[\u000fO\u0007\u000fE"), PropertyUtils.H("e(s9v\"~$Dn0|\"v ")));
            }

            /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
            public void beforeDocumentChange(DocumentEvent a) {
                VirtualFile file = FileDocumentManager.getInstance().getFile(a.getDocument());
                if (file == null || InlineChatStreamHandleService.HANDING_DATA) {
                    return;
                }
                ApplicationManager.getApplication().invokeLater(() -> {
                    InlineChatService.cleanLastData(EditorKt.getInfoByVirtualFile(file));
                }, ModalityState.defaultModalityState());
            }

            /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
            public void documentChanged(@NotNull DocumentEvent a) {
                Document document;
                VirtualFile file;
                List<Object> list;
                if (a == null) {
                    m241enum(0);
                }
                if (StringUtils.isBlank(PluginStartupActivity.getApiKey()) || (file = FileDocumentManager.getInstance().getFile((document = a.getDocument()))) == null) {
                    return;
                }
                String path = file.getPath();
                if (!StringUtils.isBlank(path)) {
                    if (!new File(path).exists()) {
                        CodeFileEditorManagerListener.f502enum.debug("document not exist:" + path);
                        return;
                    }
                    Project findCurrentProject = ApplicationUtil.findCurrentProject();
                    if (PluginDocumentListener.projectListConcurrentHashMap.containsKey(findCurrentProject) && (list = PluginDocumentListener.projectListConcurrentHashMap.get(findCurrentProject)) != null && list.size() >= 2) {
                        ApplicationManager.getApplication().invokeLater(() -> {
                            Editor editorFromAbsolutePath = AICodeUtils.getEditorFromAbsolutePath(findCurrentProject, path);
                            if (editorFromAbsolutePath != null) {
                                Alarm alarm = (Alarm) list.get(0);
                                Object obj = list.get(1);
                                if (CommonService.isSupportJava(editorFromAbsolutePath)) {
                                    CodeFileEditorManagerListener.this.cancelAllAndAddRequest(() -> {
                                        CodeFileEditorManagerListener.VB(document, file, path, editorFromAbsolutePath);
                                    }, alarm, obj, 3000);
                                } else {
                                    CodeFileEditorManagerListener.this.cancelAllAndAddRequest(() -> {
                                        CodeFileEditorManagerListener.VB(document, file, path, editorFromAbsolutePath);
                                    }, alarm, obj, 50);
                                }
                            }
                        });
                    }
                }
            }
        };
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void syncDocumentList(@NotNull VirtualFile virtualFile) {
        if (virtualFile == null) {
            m240enum(5);
        }
        if (virtualFile != null) {
            wA(virtualFile);
        }
    }

    public static void sendOpenDocument(@NotNull VirtualFile virtualFile, String path, Editor editor) {
        if (virtualFile == null) {
            m240enum(4);
        }
        MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.ACTION_OPEN_DOCUMENT.getType());
        messageDto.setPath(path);
        messageDto.setContent(editor.getDocument().getText());
        PluginWebsocketClient.sendWsMessageWithOutApm(messageDto, ProjectUtil.guessProjectForFile(virtualFile));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void selectionChanged(@NotNull FileEditorManagerEvent a) {
        Editor editor;
        if (a == null) {
            m240enum(9);
        }
        VirtualFile newFile = a.getNewFile();
        if (newFile != null) {
            String path = newFile.getPath();
            if (!StringUtils.isBlank(path) && new File(path).exists() && !FileTypeManager.getInstance().getFileTypeByFile(newFile).isBinary() && (editor = AICodeUtils.getEditor(FileEditorManager.getInstance(a.getManager().getProject()), newFile)) != null) {
                RecentFilesManager.fileOpened(a.getManager().getProject(), path);
                if (!StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
                    syncDocumentList(newFile);
                    sendOpenDocument(newFile, path, editor);
                }
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static void wA(@NotNull VirtualFile virtualFile) {
        if (virtualFile == null) {
            m240enum(6);
        }
        Project guessProjectForFile = ProjectUtil.guessProjectForFile(virtualFile);
        if (guessProjectForFile == null || guessProjectForFile.isDisposed()) {
            return;
        }
        MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.ACTION_SYNC_DOCUMENT_LIST.getType());
        messageDto.setData(AICodeUtils.getOpenFilePathList(guessProjectForFile));
        PluginWebsocketClient.sendWsMessageWithOutApm(messageDto, ProjectUtil.guessProjectForFile(virtualFile));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile a) {
        DocumentListener documentListener;
        if (source == null) {
            m240enum(7);
        }
        if (a == null) {
            m240enum(8);
        }
        Editor editor = AICodeUtils.getEditor(source, a);
        if (editor != null) {
            if (CollUtil.isNotEmpty(f501byte) && (documentListener = f501byte.get(editor)) != null) {
                editor.getDocument().removeDocumentListener(documentListener);
                f501byte.remove(editor);
            }
            wA(a);
        }
    }
}
