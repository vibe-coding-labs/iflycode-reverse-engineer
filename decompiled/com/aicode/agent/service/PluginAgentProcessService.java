/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.application.ApplicationManager
 *  org.jetbrains.annotations.NotNull
 */
package com.aicode.agent.service;

import com.aicode.action.batch.GeneratorConfig;
import com.aicode.util.Maps;
import com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public interface PluginAgentProcessService {
    public boolean isRunning();

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray = new Object[2];
        objectArray[0] = Maps.H("\u000b;\u0002|!\u0015\u00055\u0000=@2\u0012,?\u0019\\<\u000b \u00147\u0007=]\u001e\u001e;5\u0007\n\u0019\u0014*\u0019?8&\r=\u0001+\u0017\u000b\n!\u0002!\u000f5");
        objectArray[1] = GeneratorConfig.H("\u001f\u001c\u001b4\u0005\u0011\u0000\t\u0010\r\u001d");
        throw new IllegalStateException(String.format(GeneratorConfig.H("\":\n\u0007\u0004)\u0000\u0016N\u0015\u0000\u0007\u0017\u0006?m\\\u001cJW\u001b^\u0003\r\u000b\u001aX\u00007:N\n\u001c\u001b\b\u0019\fT\u0006\u000b\u0002\u0014"), objectArray));
    }

    @NotNull
    public static PluginAgentProcessService getInstance() {
        PluginAgentProcessService pluginAgentProcessService = (PluginAgentProcessService)ApplicationManager.getApplication().getService(PluginAgentProcessService.class);
        if (pluginAgentProcessService == null) {
            PluginAgentProcessService.enum(0);
        }
        return pluginAgentProcessService;
    }
}
