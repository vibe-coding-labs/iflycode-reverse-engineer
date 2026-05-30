package com.aicode.inline.action.operate;

import com.aicode.inline.dto.InlineChatInfo;
import com.aicode.util.EditorKt;
import com.intellij.openapi.editor.Editor;

/* compiled from: ce */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/action/operate/InlineChatRetryAction.class */
public class InlineChatRetryAction extends InlineChatAction {
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.inline.action.operate.InlineChatAction
    public void handle(Editor a) {
        InlineChatInfo infoByEditor = EditorKt.getInfoByEditor(a);
        if (infoByEditor == null || infoByEditor.getSessionController() == null || infoByEditor.getEditor() == null) {
            return;
        }
        infoByEditor.getSessionController().doRetry(infoByEditor.getEditor());
    }
}
