package com.aicode.service;

import com.aicode.domain.LineInfo;
import com.aicode.enums.TipType;
import com.aicode.inline.controller.SessionController;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.language.AICodeLanguageInfo;
import com.aicode.util.NewFileUtils;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/* compiled from: f */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/EditorRequestService.class */
public interface EditorRequestService extends RequestCancellable {
    @NotNull
    AICodeLanguageInfo getFileLanguage();

    @NotNull
    LineInfo getLineInfo();

    long getDocumentModificationSequence();

    String getFileNameSuffix();

    boolean equalsRequest(@NotNull EditorRequestService editorRequestService);

    int getRequestId();

    Disposable getDisposable();

    void setSessionController(SessionController sessionController);

    SessionController getSessionController();

    @NotNull
    TipType getCompletionType();

    void setOffset(int i);

    @NotNull
    Project getProject();

    int getOffset();

    boolean isUseTabIndents();

    long getRequestTimestamp();

    int getTabWidth();

    boolean isSelected();

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m265enum(int a) {
        throw new IllegalStateException(String.format(NewFileUtils.H("z&N\u0007r\u001bp\"\u000b\u0014Z\u0019S\u0006OY\u0018\u001c\u0014MnoF\fO\u001a\u0018\u0004H\u0001\r\rN\r^\u000bNRU\u001cO\u001d"), InlineChatStatusServiceKt.H("+#\u0004n\u00055\u001a(\u0006?P\t:?\u001e0\u001f8W\b\f2\n3\u000b);<\u001d?\f*($\u0016=\u0007.\r"), NewFileUtils.H("]\ri\f^\u000bN\u000bV\u001ec\u001aN\nF\u001cE\rp��^\u000fJ\t")));
    }

    @NotNull
    String getDocumentContent();

    String getFileName();

    @NotNull
    default String getCurrentDocumentPrefix() {
        String substring = getDocumentContent().substring(0, getOffset());
        if (substring == null) {
            m265enum(0);
        }
        return substring;
    }
}
