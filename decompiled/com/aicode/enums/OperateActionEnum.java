package com.aicode.enums;

/* compiled from: ci */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/OperateActionEnum.class */
public enum OperateActionEnum {
    UserOperate,
    IdeCompletion,
    CaretChange,
    SettingsChange,
    Cycling,
    TypingAsSuggested,
    Typing,
    EscReject,
    Applied;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public boolean isResetLastRequest() {
        return this == SettingsChange || this == Applied;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public boolean isUserAction() {
        return this == UserOperate;
    }
}
