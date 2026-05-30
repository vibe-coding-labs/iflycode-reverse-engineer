package com.aicode.toolwindow;

import com.aicode.PluginStartupActivity;
import com.aicode.action.RefreshAction;
import com.aicode.inline.InlineChatInlay;
import com.aicode.inline.action.OpenInlineChatAction;
import com.aicode.view.PluginToolWindowPanel;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentManager;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/toolwindow/ProjectToolWindowFactory.class */
public class ProjectToolWindowFactory implements ToolWindowFactory, DumbAware {
    public static final String UNIT_TEST_CONTENT_NAME = "";
    private static final Logger LOG = LoggerFactory.getLogger(ProjectToolWindowFactory.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 0:
            default:
                objArr[0] = "project";
                break;
            case 1:
                objArr[0] = "toolWindow";
                break;
        }
        objArr[1] = "com/aicode/toolwindow/ProjectToolWindowFactory";
        objArr[2] = "createToolWindowContent";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ProjectToolWindowFactory() {
        InlineChatInlay.INSTANCE.register();
        OpenInlineChatAction.Companion.register();
    }

    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (toolWindow == null) {
            $$$reportNull$$$0(1);
        }
        if (PluginStartupActivity.ACTIVITY_STARTED.get()) {
            toolWindow.show();
        } else {
            toolWindow.hide();
        }
        toolWindow.setTitleActions(List.of(new RefreshAction()));
        PluginToolWindowPanel unitTestToolWindowPanel = new PluginToolWindowPanel(project, toolWindow.getDisposable());
        ContentManager contentManager = toolWindow.getContentManager();
        contentManager.addContent(contentManager.getFactory().createContent(unitTestToolWindowPanel.getContent(), "", false));
    }
}
