package com.aicode.inline.action.operate;

import com.aicode.inline.InlineChatService;
import com.intellij.openapi.editor.Editor;

/* compiled from: oe */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/action/operate/InlineChatUndoAction.class */
public class InlineChatUndoAction extends InlineChatAction {
    @Override // com.aicode.inline.action.operate.InlineChatAction
    public void handle(Editor a) {
        InlineChatService.handleUndoAction(a);
    }
}
