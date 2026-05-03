/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.cef.callback.CefCallback
 *  org.cef.handler.CefLoadHandler$ErrorCode
 *  org.cef.misc.IntRef
 *  org.cef.misc.StringRef
 *  org.cef.network.CefResponse
 */
package com.aicode.view;

import com.aicode.agent.service.CodeCompleteService;
import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.view.ResourceHandlerState;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import org.cef.callback.CefCallback;
import org.cef.handler.CefLoadHandler;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefResponse;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class OpenedConnection
implements ResourceHandlerState {
    private final URLConnection float;
    private boolean byte;
    private InputStream enum;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void getResponseHeaders(CefResponse cefResponse, IntRef intRef, StringRef stringRef) {
        void a;
        Object a22 = stringRef;
        OpenedConnection a3 = this;
        try {
            void v2;
            void a4;
            Object object;
            a22 = a3.connection().getURL().toString();
            if (a22 == null) {
                object = a22;
            } else {
                Object object2 = a22;
                object = object2;
                ((String)object2).hashCode();
            }
            if (((String)object).contains(ConditionalActionConfiguration.H("A\t\u001d\u0018"))) {
                v2 = a4;
                a.setMimeType(CodeCompleteService.H("\\hQx\u0011xLi"));
            } else if (((String)a22).contains(ConditionalActionConfiguration.H("D\u0004\u0018"))) {
                v2 = a4;
                a.setMimeType(CodeCompleteService.H("xaY]#Gi^lZoLrOn"));
            } else if (((String)a22).contains(ConditionalActionConfiguration.H("A\u0019\u0018\f"))) {
                v2 = a4;
                a.setMimeType(CodeCompleteService.H("HDmJm\u0007~_k\u0015cRv"));
            } else {
                void v3 = a;
                if (((String)a22).contains(ConditionalActionConfiguration.H("S\u0007\u001e\u0003\u0007"))) {
                    v3.setMimeType(CodeCompleteService.H("|Mu]#VoRv"));
                    v2 = a4;
                } else {
                    v3.setMimeType(a3.connection().getContentType());
                    v2 = a4;
                }
            }
            v2.set(a3.if().available());
            a.setStatus(200);
            return;
        }
        catch (IOException a22) {
            void v4 = a;
            a.setError(CefLoadHandler.ErrorCode.ERR_FILE_NOT_FOUND);
            v4.setStatusText(a22.getLocalizedMessage());
            v4.setStatus(404);
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Boolean readResponse(byte[] byArray, int n, IntRef intRef, CefCallback cefCallback) throws IOException {
        boolean bl;
        int a = n;
        OpenedConnection a2 = this;
        int a3 = a2.if().available();
        if (a3 > 0) {
            void a4;
            void a5;
            a = Math.min(a3, a);
            a5.set(a2.if().read((byte[])a4, 0, a));
            bl = true;
        } else {
            a2.if().close();
            bl = false;
        }
        return bl;
    }

    @Override
    public void close() throws IOException {
        OpenedConnection a;
        a.if().close();
    }

    public OpenedConnection(URLConnection uRLConnection) {
        URLConnection a = uRLConnection;
        OpenedConnection a2 = this;
        a2.float = a;
    }

    private InputStream class() throws IOException {
        OpenedConnection openedConnection = this;
        if (!openedConnection.byte) {
            openedConnection.enum = openedConnection.connection().getInputStream();
            openedConnection.byte = true;
        }
        return openedConnection.enum;
    }

    public URLConnection connection() {
        OpenedConnection a;
        return a.float;
    }

    private InputStream if() throws IOException {
        OpenedConnection a;
        if (!a.byte) {
            return a.class();
        }
        return a.enum;
    }
}
