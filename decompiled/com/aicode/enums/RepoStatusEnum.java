package com.aicode.enums;

import com.aicode.util.Maps;

/* compiled from: yi */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/RepoStatusEnum.class */
public enum RepoStatusEnum {
    UNAUTHORIZED(-4, Maps.H("杣掷杀")),
    MISSING_TOKEN(-3, Maps.H("罩屹 \u001a\"Zm")),
    UNSUPPORTED_PROTOCOL(-2, Maps.H("乢敼捩盐区诧籄垈")),
    UNINITIALIZED(-1, Maps.H("忰剔姴匕")),
    PENDING(0, Maps.H("忌夻琅")),
    EXPIRED(6, Maps.H("嶻夎敋")),
    AUTHORIZED_1(1, Maps.H("嶡揠朗儃亟犉怂")),
    AUTHORIZED_2(2, Maps.H("嶡揠朗儃亟犉怂")),
    AUTHORIZED_3(3, Maps.H("嶡揠朗儃亟犉怂")),
    AUTHORIZED_4(4, Maps.H("嶡揠朗儃亟犉怂")),
    AUTHORIZED_5(5, Maps.H("嶡揠朗儃亟犉怂"));


    /* renamed from: float, reason: not valid java name */
    private final String f266float;

    /* renamed from: enum, reason: not valid java name */
    private final int f268enum;

    @Override // java.lang.Enum
    public String toString() {
        return this.f268enum + ": " + this.f266float;
    }

    public int getCode() {
        return this.f268enum;
    }

    public String getDescription() {
        return this.f266float;
    }

    RepoStatusEnum(int a, String a2) {
        this.f268enum = a;
        this.f266float = a2;
    }
}
