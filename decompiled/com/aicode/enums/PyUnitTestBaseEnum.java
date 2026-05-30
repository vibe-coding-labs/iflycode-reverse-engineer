package com.aicode.enums;

import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.util.Application;

/* compiled from: jn */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/PyUnitTestBaseEnum.class */
public enum PyUnitTestBaseEnum {
    AUTO(Application.H("tcya"), ""),
    PYTEST(Application.H("~tas~z"), ConditionalActionConfiguration.H("\u0004\b\u001d\tPR")),
    UNITTEST(ConditionalActionConfiguration.H("\u0006\u0018\u001d\u0005\u001d\tPR"), Application.H("PHgyas~z"));


    /* renamed from: byte, reason: not valid java name */
    private final String f261byte;

    /* renamed from: enum, reason: not valid java name */
    private final String f262enum;

    PyUnitTestBaseEnum(String a, String a2) {
        this.f261byte = a;
        this.f262enum = a2;
    }

    public String getDependency() {
        return this.f262enum;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static PyUnitTestBaseEnum findByName(String a) {
        PyUnitTestBaseEnum[] values = values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            PyUnitTestBaseEnum pyUnitTestBaseEnum = values[i2];
            if (!pyUnitTestBaseEnum.getName().equalsIgnoreCase(a)) {
                i2++;
                i = i2;
            } else {
                return pyUnitTestBaseEnum;
            }
        }
        return PYTEST;
    }

    public String getName() {
        return this.f261byte;
    }
}
