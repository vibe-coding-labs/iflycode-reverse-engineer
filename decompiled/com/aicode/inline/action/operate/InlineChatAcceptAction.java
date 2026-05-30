package com.aicode.inline.action.operate;

import com.aicode.util.EditorKt;
import com.intellij.openapi.editor.Editor;

/* compiled from: cn */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/action/operate/InlineChatAcceptAction.class */
public class InlineChatAcceptAction extends InlineChatAction {
    @Override // com.aicode.inline.action.operate.InlineChatAction
    public void handle(Editor a) {
        EditorKt.removeEditor(a);
        EditorKt.closeButtonPanel(a);
    }
}
