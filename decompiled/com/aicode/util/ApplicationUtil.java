package com.aicode.util;

import com.aicode.language.AICodeLanguageInfo;
import com.aicode.service.editor.CancelRequestTip;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.openapi.wm.IdeFrame;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.jetbrains.annotations.Nullable;

/* compiled from: gb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/ApplicationUtil.class */
public final class ApplicationUtil {
    public static final /* synthetic */ List<String> supportLanguage = Arrays.asList(CancelRequestTip.H("\u001a\u0011L["), AICodeLanguageInfo.H("3\u000b*\u0007{K"));

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ String f666enum;

    @Nonnull
    public static /* synthetic */ Iterable<Project> findValidProjects() {
        return (Iterable) Arrays.stream(ProjectManager.getInstance().getOpenProjects()).filter(project -> {
            return (project == null || project.isDisposed() || project.isDefault()) ? false : true;
        }).collect(Collectors.toList());
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Nullable
    public static /* synthetic */ Project findCurrentProject() {
        IdeFrame lastFocusedFrame = IdeFocusManager.getGlobalInstance().getLastFocusedFrame();
        Project project = lastFocusedFrame != null ? lastFocusedFrame.getProject() : null;
        if (!P(project)) {
            Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
            int length = openProjects.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                Project project2 = openProjects[i2];
                if (P(project2)) {
                    return project2;
                }
                i2++;
                i = i2;
            }
            return null;
        }
        return project;
    }

    public static /* synthetic */ Boolean isSupportLanguage(Editor a) {
        return true;
    }
}
