package com.aicode.enums;

import com.aicode.inline.ide.ConditionalActionConfiguration;

/* compiled from: zm */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/DuplicateRule.class */
public enum DuplicateRule {
    SKIP(ConditionalActionConfiguration.H("跐迡")),
    OVERWRITE(ConditionalActionConfiguration.H("覥盰")),
    COEXIST(ConditionalActionConfiguration.H("侴电亯耣"));


    /* renamed from: byte, reason: not valid java name */
    private String f235byte;

    public String getName() {
        return this.f235byte;
    }

    DuplicateRule(String a) {
        this.f235byte = a;
    }
}
