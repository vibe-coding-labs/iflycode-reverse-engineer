package com.aicode.listener;

import com.aicode.agent.service.GitReviewService;
import com.aicode.enums.LanguageEnum;
import com.aicode.util.NewFileUtils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: ck */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/FileWatchedAdapter.class */
public class FileWatchedAdapter implements FileDocumentManagerListener {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f512enum = LoggerFactory.getLogger(FileWatchedAdapter.class);

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m246enum(int a) {
        throw new IllegalArgumentException(String.format(NewFileUtils.H("c\u0002@��P\nT\u001c\u000b\u001fO��\u000b9r\u0001\u0014|}6KUR\u0011Y\u0018t.[\u0018HH\nZUS\u000b\u0016LX*.\u0004]\\]S\u0019I\u001c\u000b\u0017S\u001a\u000b\u001bERU\u001cO\u001d"), GitReviewService.H("%\u0015)\u0004<\u000f'\u0006"), NewFileUtils.H("H\u0016tdN\u0014Y\u0007I\u001a\t\u0018B\n^\u001da8XWi\u0014R\tm\t_\u001aT\u000bO8D\u0013K\u001dF\u0003"), GitReviewService.H("\"\u001e#\u0011&\n\u0014\u0004\"\u000f;\b/\u000e\u0019\u0010'\u0003'\u0015")));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private String mb(String a) {
        String[] split = a.split(GitReviewService.H("\u001aS"));
        if (split.length <= 0) {
            return null;
        }
        return split[split.length - 1];
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void beforeDocumentSaving(@NotNull Document a) {
        if (a == null) {
            m246enum(0);
        }
        try {
            VirtualFile file = FileDocumentManager.getInstance().getFile(a);
            if (file == null || LanguageEnum.isVaildLanguage(mb(file.getName()))) {
                ProjectUtil.guessProjectForFile(file);
            }
        } catch (Exception e) {
            f512enum.info(e.getMessage());
        }
    }
}
