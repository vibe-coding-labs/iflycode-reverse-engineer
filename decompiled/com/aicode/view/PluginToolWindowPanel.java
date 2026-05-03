/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.Disposable
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.ui.SimpleToolWindowPanel
 *  org.jetbrains.annotations.NotNull
 */
package com.aicode.view;

import com.aicode.diff.GenericUtils;
import com.aicode.inline.controller.ChatInputController;
import com.aicode.view.WebViewWindowPanel;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PluginToolWindowPanel
extends SimpleToolWindowPanel {
    private final Disposable byte;
    private final Project enum;

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray;
        String string = GenericUtils.H("Yb'=22=/a/wbF.\u0014=7\u0005$53w(1\u0010\u000b44-4(rFL,px?4z|\"ol\u0015N:*+$s55&s9=p->7?");
        Object[] objectArray2 = new Object[3];
        switch (a) {
            default: {
                objectArray = objectArray2;
                objectArray2[0] = ChatInputController.H("\u000e\u000b\u001a\b\u000b\u0019\u0002");
                break;
            }
            case 1: {
                objectArray = objectArray2;
                while (false) {
                }
                objectArray2[0] = GenericUtils.H("'>*5=/\u001e; +7#\")76");
                break;
            }
        }
        objectArray[1] = ChatInputController.H(",\u0017\u0019W\u0015\u0012\u0014/(\u001b]\u000f\u001c\u0016\bW$\f\u0019 \"\u0018.\u0016\u001a\u001e)\u0012\u0019\u0016\u0011\u000e%\u0003\u0000\u001f\u001a");
        objectArray[2] = GenericUtils.H("d9-\"/m");
        throw new IllegalArgumentException(String.format(string, objectArray));
    }

    /*
     * WARNING - void declaration
     */
    public PluginToolWindowPanel(@NotNull Project project, @NotNull Disposable disposable) {
        void a;
        PluginToolWindowPanel pluginToolWindowPanel = pluginToolWindowPanel2;
        PluginToolWindowPanel pluginToolWindowPanel2 = project;
        PluginToolWindowPanel project2 = pluginToolWindowPanel;
        if (pluginToolWindowPanel2 == null) {
            PluginToolWindowPanel.enum(0);
        }
        if (a == null) {
            PluginToolWindowPanel.enum(1);
        }
        PluginToolWindowPanel pluginToolWindowPanel3 = project2;
        super(true);
        pluginToolWindowPanel3.enum = pluginToolWindowPanel2;
        pluginToolWindowPanel3.byte = a;
        PluginToolWindowPanel pluginToolWindowPanel4 = project2;
        project2.setContent(new WebViewWindowPanel((Project)pluginToolWindowPanel2));
    }
}
