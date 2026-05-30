package com.aicode.listener;

import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.ChatService;
import com.aicode.agent.service.SqlService;
import com.aicode.inline.controller.ChatInputController;
import com.aicode.inline.controller.SessionController;
import com.aicode.inline.dto.InlineChatInfo;
import com.aicode.inline.enums.InlineChatStepEnum;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.util.EditorKt;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/* compiled from: mc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/PluginManagerListener.class */
public class PluginManagerListener implements ProjectManagerListener {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m254enum(int a) {
        String H = ChatInputController.H("\"\u001d\u0015\u000b\u0014\u0010\u001c\nE\u000fVGg\u000b5\u0018\u0016 \u0005\u0010\u0012R\t\u0014(7\u001b\u001f\r\u0010\u0004ZWY\u0001YE\u0006=w[\u0001JM\u0010O\u001f\u000b\n\u0001R\u0010\n\u001dR\u001c\u001cU\f\u001b\u0016\u001a");
        Object[] objArr = new Object[3];
        objArr[0] = CancelRequestTip.H("\u0011\u0018\u0005\u001b\u0014\n\u001d");
        objArr[1] = ChatInputController.H("\u0011\u0016\u0018u7\u001f\u0019\u0016\u0011\u0013U\u001c\u0015\u0001\n��\u0007>%Q\"\b\u001d\u0004\u0006\u001c3\u0018\u001b\u0013\u0019��\u001b>\u0017\n\u0001\u0007��\u001f\u0004");
        switch (a) {
            case 0:
            default:
                objArr[2] = CancelRequestTip.H("8:\u0002\u0007\u0012\u0014\u00043\r\u000e\u0019\u0003\u000f\u00064\u0013\u0007\u000e\u0018\u000f\"\u0010\u001f\f");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = ChatInputController.H("\u0005��\u0011\u000f\f\u0011\n:\u0019\r\u001d\u001f\u0012");
                break;
            case 2:
                objArr[2] = CancelRequestTip.H("\u0003\u0011\u0017\u0004#\u0004\r\b\u0018\u0013\"\t\u000b\u001e5\u0010\u001d\b");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    private static void kc(@NotNull Project project) {
        if (project == null) {
            m254enum(2);
        }
        try {
            ArrayList arrayList = new ArrayList();
            Iterator<Map.Entry<String, InlineChatInfo>> it = EditorKt.inlineChatCacheData.entrySet().iterator();
            loop0: while (true) {
                for (Iterator<Map.Entry<String, InlineChatInfo>> it2 = it; it2.hasNext(); it2 = it) {
                    InlineChatInfo value = it.next().getValue();
                    if (value != null) {
                        if (value.getEditor() != null && project.equals(value.getEditor().getProject())) {
                            arrayList.add(value);
                        }
                    }
                }
                break loop0;
            }
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                SessionController sessionController = ((InlineChatInfo) it3.next()).getSessionController();
                if (sessionController.getInlineChatStepEnum() == InlineChatStepEnum.SUCCESS) {
                    sessionController.handleOperation(sessionController.getEditor(), CommandEnum.DIALOG_ACCEPT);
                }
            }
        } catch (Throwable th) {
        }
    }

    public void projectClosed(@NotNull Project a) {
        if (a == null) {
            m254enum(1);
        }
        PluginWebsocketClient.closeWebsocket(a.getBasePath(), CancelRequestTip.H("��5(\u0002\r\b\u001f4\u001b\u000e\u0012\u0005\u0004"));
        ChatService.SESSION_ID.remove(a.getBasePath());
        SqlService.SQL_SESSION_ID.remove(a.getBasePath());
    }

    public void projectClosingBeforeSave(@NotNull Project a) {
        if (a == null) {
            m254enum(0);
        }
        kc(a);
    }
}
