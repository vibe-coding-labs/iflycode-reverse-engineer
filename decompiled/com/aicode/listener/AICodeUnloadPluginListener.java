package com.aicode.listener;

import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.agent.service.CodeCompleteService;
import com.aicode.enums.OperateActionEnum;
import com.aicode.service.EditorManagerService;
import com.aicode.util.ApplicationUtil;
import com.aicode.util.MessageBundle;
import com.aicode.util.PluginInfoUtils;
import com.intellij.AbstractBundle;
import com.intellij.ide.plugins.DynamicPluginListener;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

/* compiled from: md */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/AICodeUnloadPluginListener.class */
public class AICodeUnloadPluginListener implements DynamicPluginListener {
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m232enum(int a) {
        throw new IllegalArgumentException(String.format(MethodGeneratorConfig.H("[f\u0012\u000e\u0010\u0016\u0019\r{3?,q\u001f/��.\u001a-:7u%:\"?\t\u000f,33otx.tz;\u001eVx il3n<*) q1)<q=?t/:5;"), CodeCompleteService.H("Y`W`@bz~ZoPnIhNv"), MethodGeneratorConfig.H("9;5y:<644;K\u00061%5*=8/|\u001b\u001d;\u001996\u0012',!0;\n8$8/&\u001d6) $!<%"), CodeCompleteService.H("ZxOcPby`K|@bwiUs@`")));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static void BC() {
        Iterator<Project> it = ApplicationUtil.findValidProjects().iterator();
        while (it.hasNext()) {
            Editor selectedTextEditor = FileEditorManager.getInstance(it.next()).getSelectedTextEditor();
            if (selectedTextEditor != null && !selectedTextEditor.isDisposed()) {
                EditorManagerService.getInstance().disposeTips(selectedTextEditor, OperateActionEnum.UserOperate);
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void beforePluginUnload(@NotNull IdeaPluginDescriptor pluginDescriptor, boolean z) {
        if (pluginDescriptor == null) {
            m232enum(0);
        }
        if (!PluginInfoUtils.isAICodePlugin(pluginDescriptor)) {
            return;
        }
        BC();
        try {
            Method declaredMethod = AbstractBundle.class.getDeclaredMethod(CodeCompleteService.H("FlGfkpMdAiFELk@h"), new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(MessageBundle.INSTANCE, new Object[0]);
        } catch (Exception unused) {
        }
    }
}
