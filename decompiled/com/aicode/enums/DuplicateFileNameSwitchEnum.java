package com.aicode.enums;

import com.aicode.util.PropertyUtils;

/* compiled from: mf */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/DuplicateFileNameSwitchEnum.class */
public enum DuplicateFileNameSwitchEnum {
    ENABLED(PropertyUtils.H("U\"z5l*|")),
    DISABLED(PropertyUtils.H("\u0003Y?z5l*|"));


    /* renamed from: byte, reason: not valid java name */
    private final String f233byte;

    DuplicateFileNameSwitchEnum(String a) {
        this.f233byte = a;
    }

    public String getType() {
        return this.f233byte;
    }
}
