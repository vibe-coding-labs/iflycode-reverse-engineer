package com.aicode.template.context.domain;

import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/SyntheticParam.class */
public class SyntheticParam extends Param {

    @Nullable
    private final UsageContext usageContext;

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/SyntheticParam$UsageContext.class */
    public enum UsageContext {
        Property,
        Generic
    }

    public SyntheticParam(Type type, String name, UsageContext usageContext) {
        super(type, name, new ArrayList());
        this.usageContext = usageContext;
    }

    public SyntheticParam(Type type, String name) {
        super(type, name, new ArrayList());
        this.usageContext = null;
    }

    public UsageContext getUsageContext() {
        return this.usageContext;
    }
}
