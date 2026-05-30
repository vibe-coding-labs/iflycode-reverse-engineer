package com.aicode.inline.action.operate;

import com.aicode.inline.InlineChatService;
import com.aicode.inline.controller.SessionController;
import com.aicode.inline.dto.InlineChatInfo;
import com.aicode.inline.enums.InlineChatStepEnum;
import com.aicode.util.EditorKt;
import com.intellij.openapi.editor.Editor;

/* compiled from: ak */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/action/operate/InlineChatStopAction.class */
public class InlineChatStopAction extends InlineChatAction {
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.inline.action.operate.InlineChatAction
    public void handle(Editor a) {
        InlineChatInfo infoByEditor = EditorKt.getInfoByEditor(a);
        if (infoByEditor != null && infoByEditor.getSessionController() != null && infoByEditor.getEditor() != null) {
            SessionController sessionController = infoByEditor.getSessionController();
            if (sessionController.getInlineChatStepEnum() != InlineChatStepEnum.LOADING && sessionController.getInlineChatStepEnum() != InlineChatStepEnum.CATEGORY) {
                return;
            }
        }
        InlineChatService.cleanRender(a);
    }
}
