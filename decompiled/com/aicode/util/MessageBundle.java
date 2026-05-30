package com.aicode.util;

import com.aicode.ui.FontKt;
import com.intellij.DynamicBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/* compiled from: ja */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/MessageBundle.class */
public final class MessageBundle extends DynamicBundle {
    public static final /* synthetic */ MessageBundle INSTANCE = new MessageBundle();

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m413enum(int a) {
        throw new IllegalArgumentException(String.format(FontKt.H("\u001b4~p;/06t.76s\u000f\u001c!mK72/\u007f.#)&t`\u000e\u0003 nc}7\u007fv%6lw=[L1~40 ;{)=:r,<e,+6*"), HandleCacheUtil.H("=e&"), FontKt.H(";+5y+9/=*\u0010F7*0)|\u0002>4!/5+\u001b0,:6#"), HandleCacheUtil.H("1e+")));
    }

    public static /* synthetic */ String get(@PropertyKey(resourceBundle = "messages.aicode") @NotNull String key) {
        if (key == null) {
            m413enum(0);
        }
        return INSTANCE.getMessage(key, new Object[0]);
    }

    public static /* synthetic */ String get(@PropertyKey(resourceBundle = "messages.aicode") @NotNull String key, Object... params) {
        if (key == null) {
            m413enum(1);
        }
        return INSTANCE.getMessage(key, params);
    }

    private /* synthetic */ MessageBundle() {
        super(HandleCacheUtil.H("0e,T\u0019w*xz`7j9d:"));
    }
}
