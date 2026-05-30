package com.aicode.listener;

import com.aicode.diff.GenericUtils;
import com.aicode.util.ApplicationUtil;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.util.Alarm;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

/* compiled from: nc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/PluginDocumentListener.class */
public class PluginDocumentListener implements ProjectComponent {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f520enum = Logger.getInstance(PluginDocumentListener.class);
    public static Map<Project, List<Object>> projectListConcurrentHashMap = new ConcurrentHashMap();

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void projectOpened() {
        Project findCurrentProject = ApplicationUtil.findCurrentProject();
        projectListConcurrentHashMap.put(findCurrentProject, Arrays.asList(findCurrentProject != null ? new Alarm(Alarm.ThreadToUse.POOLED_THREAD, findCurrentProject) : new Alarm(Alarm.ThreadToUse.POOLED_THREAD), new Object()));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void projectClosed() {
        for (Project project : projectListConcurrentHashMap.keySet()) {
            if (project.isDisposed()) {
                projectListConcurrentHashMap.remove(project);
            }
        }
    }

    @NotNull
    public String getComponentName() {
        return GenericUtils.H("\u000b?3)0?\u001c?0.55,>4\u0019$+>=6)");
    }
}
