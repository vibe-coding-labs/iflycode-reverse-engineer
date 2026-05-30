package com.aicode.enums;

import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.RequestResultList;
import com.aicode.util.Maps;

/* compiled from: bn */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/GitRepoStatusEnum.class */
public enum GitRepoStatusEnum {
    AUTHORIZED_EXPIRED(-1, true, true, BasicActionsBundle.message(Maps.H("\u000f;1\u0001\u0011,N7\u001b&\u0003$\u0017*#\u001dA2\u0001<\u0004?Oh\u0018;C4\u0010$\u0006!\r0[=Vs"), new Object[0])),
    SSH_PROTOCOL(-2, true, false, BasicActionsBundle.message(Maps.H("(\u001d+\u001d*!V\u0004=\u001b?��5Yf\u0007p\u001d#\u0007 ��0\u00078[=Vs"), new Object[0])),
    TOKEN_INVALID(-3, true, false, BasicActionsBundle.message(Maps.H("\u0014 \u00033\u0011,Z#\u001c!3\u0014\n7\u0013-B$Rj\u00070C8\u0006\"\u000e?\u00010[=Vs"), new Object[0])),
    UNAUTHORIZED(-4, true, true, BasicActionsBundle.message(Maps.H(")\u00053Re\u0007p\u001d=\u001d3\u0006=F \u001c=Sf"), new Object[0]) + BasicActionsBundle.message(RequestResultList.H("F|c]qB.Yo\\pYTg}M/Gp["), new Object[0]));


    /* renamed from: final, reason: not valid java name */
    private int f245final;

    /* renamed from: try, reason: not valid java name */
    private boolean f246try;

    /* renamed from: byte, reason: not valid java name */
    private String f248byte;

    /* renamed from: enum, reason: not valid java name */
    private boolean f249enum;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static GitRepoStatusEnum getGitRepoStatusEnum(int a) {
        GitRepoStatusEnum[] values = values();
        int length = values.length;
        int i = 0;
        int a2 = 0;
        while (i < length) {
            GitRepoStatusEnum gitRepoStatusEnum = values[a2];
            if (gitRepoStatusEnum.f245final != a) {
                a2++;
                i = a2;
            } else {
                return gitRepoStatusEnum;
            }
        }
        return null;
    }

    public boolean isNeedAuthorize() {
        return this.f246try;
    }

    GitRepoStatusEnum(int a, boolean z, boolean z2, String a2) {
        this.f245final = a;
        this.f249enum = z;
        this.f246try = z2;
        this.f248byte = a2;
    }

    public boolean isNeedSkipWeb() {
        return this.f249enum;
    }

    public int getCode() {
        return this.f245final;
    }

    public String getMessage() {
        return this.f248byte;
    }
}
