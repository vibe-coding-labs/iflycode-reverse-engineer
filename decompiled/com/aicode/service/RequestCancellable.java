package com.aicode.service;

/* compiled from: v */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/RequestCancellable.class */
public interface RequestCancellable {

    /* renamed from: enum, reason: not valid java name */
    public static final RequestCancellable f550enum = new f();

    void cancel();

    boolean isCancelled();

    /* compiled from: v */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/RequestCancellable$f.class */
    class f implements RequestCancellable {
        @Override // com.aicode.service.RequestCancellable
        public void cancel() {
        }

        @Override // com.aicode.service.RequestCancellable
        public boolean isCancelled() {
            return false;
        }
    }
}
