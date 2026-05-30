package com.aicode.view;

import java.io.IOException;
import org.cef.callback.CefCallback;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefResponse;

/* compiled from: h */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/view/ResourceHandlerState.class */
public interface ResourceHandlerState {
    Boolean readResponse(byte[] bArr, int i, IntRef intRef, CefCallback cefCallback) throws IOException;

    void close() throws IOException;

    void getResponseHeaders(CefResponse cefResponse, IntRef intRef, StringRef stringRef);
}
