package com.aicode.enums;

import com.aicode.inline.controller.ChatInputController;
import com.aicode.util.AICodeUtils;

/* compiled from: se */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/PyUnitTestMockEnum.class */
public enum PyUnitTestMockEnum {
    AUTO(ChatInputController.H("\u0002\u001a]J"), AICodeUtils.H("臨劤")),
    OFF(AICodeUtils.H("{dj"), ChatInputController.H("党闈")),
    UNITTESTMOCK(ChatInputController.H("\r\u0015\u001e\n\u0006\u001c\u0006\n\\\u000e��JN"), AICodeUtils.H("{bkp~OWu!w{ag")),
    PYTESTMOCK(AICodeUtils.H("r}~OWu\"w{ag"), ChatInputController.H("\u0007\u0007\u0006\u001c\u0006\n_\u000e��JN"));


    /* renamed from: byte, reason: not valid java name */
    private final String f264byte;

    /* renamed from: enum, reason: not valid java name */
    private final String f265enum;

    public String getDependency() {
        return this.f264byte;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static PyUnitTestMockEnum findByName(String a) {
        PyUnitTestMockEnum[] values = values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            PyUnitTestMockEnum pyUnitTestMockEnum = values[i2];
            if (pyUnitTestMockEnum.getName().equalsIgnoreCase(a)) {
                return pyUnitTestMockEnum;
            }
            i2++;
            i = i2;
        }
        return UNITTESTMOCK;
    }

    PyUnitTestMockEnum(String a, String a2) {
        this.f265enum = a;
        this.f264byte = a2;
    }

    public String getName() {
        return this.f265enum;
    }
}
