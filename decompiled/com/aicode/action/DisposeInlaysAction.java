package com.aicode.action;

import Q.ua;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.project.DumbAware;

/* compiled from: ki */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/DisposeInlaysAction.class */
public class DisposeInlaysAction extends EditorAction implements DumbAware, CodeAction {
    public DisposeInlaysAction() {
        super(new ua(null));
        setInjectedContext(true);
    }
}
