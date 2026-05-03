/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.util.Pair
 */
package com.aicode.agent.service;

import com.aicode.agent.service.PluginAgentProcessService;
import com.intellij.openapi.util.Pair;
import java.io.IOException;

public interface PluginAgentProcessServiceEx
extends PluginAgentProcessService {
    public void startNotify();

    public Pair getAgentPort(Long var1, int var2) throws InterruptedException, IOException;

    public boolean isShutdown();

    public void shutdown();

    public void copySource() throws IOException;
}
