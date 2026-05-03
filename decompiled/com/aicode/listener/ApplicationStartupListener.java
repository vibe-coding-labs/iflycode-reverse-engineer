/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.ide.AppLifecycleListener
 *  com.intellij.openapi.diagnostic.Logger
 */
package com.aicode.listener;

import com.aicode.PluginStartupActivity;
import com.aicode.action.batch.GeneratorConfig;
import com.aicode.util.IndentLineUtil;
import com.intellij.ide.AppLifecycleListener;
import com.intellij.openapi.diagnostic.Logger;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class ApplicationStartupListener
implements AppLifecycleListener {
    private static final Logger enum = Logger.getInstance(ApplicationStartupListener.class);

    public void appWillBeClosed(boolean bl) {
        boolean a = bl;
        ApplicationStartupListener a2 = this;
        enum.info(GeneratorConfig.H("95\u0018\u000e\u0006\u0015\u001c\b\u0016\u001d\u000b\u001c6\u00079<\u000b\u001c\u001a0\u0010\u001c\u000b\f\u0010\r\t0g0\u0012\u0004/\u0007\u0013\u0005,\u001d&\u001f\u0001\u000b\u001c\u000b"));
        PluginStartupActivity.clear();
    }

    public ApplicationStartupListener() {
        ApplicationStartupListener a;
    }

    public void appClosing() {
        enum.info(IndentLineUtil.H("\u007f/A\u0003@\u001cI\u0015_ y\u0000b\u000fE\u001aC\u001bA#Y\u0019A3g\u0016^4\u0016\u000fA\u000fc\u0018D\fI\u0006P"));
        PluginStartupActivity.clear();
    }
}
