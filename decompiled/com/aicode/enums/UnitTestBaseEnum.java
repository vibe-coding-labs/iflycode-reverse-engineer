package com.aicode.enums;

import com.aicode.language.AICodeLanguageInfo;
import com.aicode.util.Application;

/* compiled from: de */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/UnitTestBaseEnum.class */
public enum UnitTestBaseEnum {
    AUTO(Application.H("tcya"), ""),
    JUNIT_FOUR(Application.H("Dx{\u007fy:"), AICodeLanguageInfo.H("\u001d6\u001c-\u0001y\u0018+\u0001}Q")),
    JUNIT_FIVE(AICodeLanguageInfo.H("\t\u00070\u0006`\u0010"), Application.H("\u007f<*\u001b\\{c`~:}AGlrvb.}WOhv>zpvLRk\u007f8w}g")),
    SPRINGBOOTTEST(Application.H("RraykagIayAs~z"), AICodeLanguageInfo.H("6\u001ac\u001b\f>6\u001c-\u00158\u001d\u001f\"*\t6\u001a5A\n6$\u000ew\u001c*\r\u001bp'\u001a1\u001a\"\u000eb\t\u001a\b4\u001e-\u0015\u0006\u001a,\u0006\n\ngQ"));


    /* renamed from: byte, reason: not valid java name */
    private final String f282byte;

    /* renamed from: enum, reason: not valid java name */
    private final String f283enum;

    public String getDependency() {
        return this.f282byte;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static UnitTestBaseEnum findByName(String a) {
        UnitTestBaseEnum[] values = values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            UnitTestBaseEnum unitTestBaseEnum = values[i2];
            if (unitTestBaseEnum.getName().equalsIgnoreCase(a)) {
                return unitTestBaseEnum;
            }
            i2++;
            i = i2;
        }
        return JUNIT_FOUR;
    }

    UnitTestBaseEnum(String a, String a2) {
        this.f283enum = a;
        this.f282byte = a2;
    }

    public String getName() {
        return this.f283enum;
    }
}
