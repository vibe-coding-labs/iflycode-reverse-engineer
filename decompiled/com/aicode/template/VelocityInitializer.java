package com.aicode.template;

import com.aicode.util.ReflectUtil;
import com.intellij.openapi.diagnostic.Logger;
import java.lang.reflect.Field;
import org.apache.velocity.runtime.RuntimeInstance;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/VelocityInitializer.class */
public class VelocityInitializer {
    private static final Logger LOG = Logger.getInstance(VelocityInitializer.class);

    public static void verifyRuntimeSetup() {
    }

    @Nullable
    private static RuntimeInstance getRuntimeInstance() {
        try {
            Class<?> vwClass = Class.forName("com.intellij.ide.fileTemplates.VelocityWrapper");
            Field ri = vwClass.getDeclaredField("ri");
            return (RuntimeInstance) ReflectUtil.getField(ri, null);
        } catch (Throwable e) {
            LOG.info("RI not found on velocity runtime:" + e.getMessage());
            return null;
        }
    }
}
