package com.aicode.enums;

import com.aicode.inline.controller.ChatInputController;
import com.aicode.util.JComponentKt;

/* compiled from: wi */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/UnitTestMockEnum.class */
public enum UnitTestMockEnum {
    AUTO(JComponentKt.H("9\u000bf["), ChatInputController.H("臃劍")),
    OFF(ChatInputController.H("��OC"), JComponentKt.H("兡闙")),
    POWER_MOCK(JComponentKt.H("3-\u0013 \u0011\u0015\u0011q_"), ChatInputController.H("\u001b\n\u001b^\"1\u000e\u0010\u0010\u0003\u0015\u0015@\u001d\u0004\u0017\f\u0012\f\u001f\u0016\u0016\u0015_����[@")),
    MOCKITO(ChatInputController.H("8\u0011\u0011\b\u0006]J"), JComponentKt.H("\u0006=%J4\u0010\"\fyB S-\t&\b+\u0010*N;\u0011`Q"));


    /* renamed from: float, reason: not valid java name */
    private final String f284float;

    /* renamed from: byte, reason: not valid java name */
    private final String f285byte;

    public String getDependency() {
        return this.f285byte;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static UnitTestMockEnum findByName(String a) {
        UnitTestMockEnum[] values = values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            UnitTestMockEnum unitTestMockEnum = values[i2];
            if (unitTestMockEnum.getName().equalsIgnoreCase(a)) {
                return unitTestMockEnum;
            }
            i2++;
            i = i2;
        }
        return OFF;
    }

    public static UnitTestMockEnum findByDependency() {
        return OFF;
    }

    UnitTestMockEnum(String a, String a2) {
        this.f284float = a;
        this.f285byte = a2;
    }

    public String getName() {
        return this.f284float;
    }
}
