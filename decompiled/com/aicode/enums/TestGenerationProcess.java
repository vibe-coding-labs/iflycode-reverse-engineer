package com.aicode.enums;

import com.aicode.agent.service.CodeCompleteService;
import com.aicode.util.StringUtils;

/* compiled from: bm */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/TestGenerationProcess.class */
public enum TestGenerationProcess {
    GENERATION(CodeCompleteService.H("甧戍匧洜"), ""),
    GENERATION_BUILD(CodeCompleteService.H("生戲卒浮 \u0013=罤讆"), CodeCompleteService.H("弥始缮诌云硖")),
    GENERATION_BUILD_EXECUTE(CodeCompleteService.H("畯扅卺流��.\u0005编诳'\u000e 扟衑匧洜"), CodeCompleteService.H("开姩缑说并扟衑匧洜"));


    /* renamed from: float, reason: not valid java name */
    private String f275float;

    /* renamed from: byte, reason: not valid java name */
    private String f276byte;

    public String getName() {
        return this.f276byte;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static TestGenerationProcess loadByName(String a) {
        TestGenerationProcess[] values = values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            TestGenerationProcess testGenerationProcess = values[i2];
            if (StringUtils.equals(testGenerationProcess.getName(), a)) {
                return testGenerationProcess;
            }
            i2++;
            i = i2;
        }
        return GENERATION;
    }

    TestGenerationProcess(String a, String a2) {
        this.f276byte = a;
        this.f275float = a2;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public String getDescription() {
        return StringUtils.isEmpty(this.f275float) ? this.f275float : "，" + this.f275float;
    }
}
