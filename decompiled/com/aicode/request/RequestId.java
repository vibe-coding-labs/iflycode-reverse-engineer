/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.request;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class RequestId {
    private static final AtomicInteger enum = new AtomicInteger();

    public static int currentRequestId() {
        return enum.get();
    }

    public static int incrementAndGet() {
        return enum.incrementAndGet();
    }

    public RequestId() {
        RequestId a;
    }
}
