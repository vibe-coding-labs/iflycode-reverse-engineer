package com.aicode.enums;

/* compiled from: gg */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/BatchTestUnitLimt.class */
public enum BatchTestUnitLimt {
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50);


    /* renamed from: byte, reason: not valid java name */
    private Integer f220byte;

    BatchTestUnitLimt(Integer a) {
        this.f220byte = a;
    }

    public Integer getLimit() {
        return this.f220byte;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static BatchTestUnitLimt loadLimt(Integer a) {
        BatchTestUnitLimt[] values = values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            BatchTestUnitLimt batchTestUnitLimt = values[i2];
            if (batchTestUnitLimt.getLimit() == a) {
                return batchTestUnitLimt;
            }
            i2++;
            i = i2;
        }
        return FIVE;
    }
}
