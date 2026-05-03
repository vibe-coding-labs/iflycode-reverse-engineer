/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.project.Project
 *  org.cef.callback.CefCallback
 *  org.cef.handler.CefResourceHandler
 *  org.cef.misc.IntRef
 *  org.cef.misc.StringRef
 *  org.cef.network.CefRequest
 *  org.cef.network.CefResponse
 *  org.jetbrains.annotations.NotNull
 */
package com.aicode.view;

import com.aicode.action.batch.GeneratorConfig;
import com.aicode.content.util.EditorUtils;
import com.aicode.view.OpenedConnection;
import com.aicode.view.ResourceHandlerState;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import org.cef.callback.CefCallback;
import org.cef.handler.CefResourceHandler;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefRequest;
import org.cef.network.CefResponse;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class CustomResourceHandler
implements CefResourceHandler {
    private ResourceHandlerState float;
    private Project byte;
    private final Logger enum;

    /*
     * WARNING - void declaration
     */
    public boolean readResponse(byte[] byArray, int n, IntRef intRef, CefCallback cefCallback) {
        Object a6 = byArray;
        CustomResourceHandler a2 = this;
        try {
            void a3;
            void a4;
            void a5;
            boolean a6 = a2.for().readResponse((byte[])a6, (int)a5, (IntRef)a4, (CefCallback)a3);
            return a6;
        }
        catch (IOException a5) {
            a5.printStackTrace();
            boolean a6 = false;
            return false;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean processRequest(CefRequest cefRequest, CefCallback cefCallback) {
        CustomResourceHandler customResourceHandler = this;
        try {
            void a;
            Object object;
            void a2;
            String string = a2.getURL();
            Object[] objectArray = string.replace(GeneratorConfig.H("\u0002\b\u0011\u0003UVH\u0012\u0001\u001c\u001c@"), EditorUtils.H("`)}2\u007f(vu")).split(Pattern.quote(GeneratorConfig.H("P")));
            customResourceHandler.enum.info(Arrays.toString(objectArray));
            Object a3 = objectArray.length > 0 ? objectArray[0] : EditorUtils.H("g.t;R\u0005eft(s)gj~9l6");
            CustomResourceHandler customResourceHandler2 = customResourceHandler;
            customResourceHandler2.enum.info((String)a3);
            a3 = customResourceHandler2.getClass().getClassLoader().getResource((String)a3);
            Object[] objectArray2 = new Object[1];
            objectArray2[0] = a3;
            customResourceHandler2.enum.debug(GeneratorConfig.H("\t\u0014\u0019-\u000b\u0003"), objectArray2);
            customResourceHandler.enum.info(a3 != null ? ((URL)a3).toString() : null);
            Object object2 = a3 != null ? (objectArray.length > 1 ? (object = new URL((URL)a3 + "#/?" + (String)objectArray[1])) : (object = a3)) : (object = customResourceHandler.getClass().getClassLoader().getResource(EditorUtils.H("g.t;R\u0005eft(s)gj~9l6")));
            if (((URL)object2).getPath().endsWith(GeneratorConfig.H("\u0018\u001c\u0005\u0007\u0007\u001d\u000e@"))) {
                object = customResourceHandler.getClass().getClassLoader().getResource(EditorUtils.H("g.t;R\u0005eft(s)gj~9l6"));
            }
            OpenedConnection openedConnection = new OpenedConnection(((URL)Objects.requireNonNull(object)).openConnection());
            customResourceHandler.super(openedConnection);
            a.Continue();
            return true;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray = new Object[3];
        objectArray[0] = EditorUtils.H("<m+|(b.");
        objectArray[1] = GeneratorConfig.H("\r\u0010\u0004K\u0013\u000f\u00136+\u000bW\u000e\u0007\u0001\u0005Q+\f\u001c\u001c\u0011\u0003*&&\u0005\t\u0017\u0010\n1\u0006\u001f\n\u0014\u001c\u001d");
        objectArray[2] = EditorUtils.H("#-x$ud");
        throw new IllegalArgumentException(String.format(GeneratorConfig.H(")\f\u0003\u0007\u0002\u001c\u0000\f\u0005U\u0012\u0019B4 \u0017\b$P_$~\u000e\t\n\u000f\u0012\f\u0010\u0017\u0014P~j\u001d_X\u0001\u0002R[\u001bWJ\u001b^\u0003\r0!J\u0012\n\u0007O\u001b\u0002Q\u0000\r\u0015\u0003"), objectArray));
    }

    /*
     * WARNING - void declaration
     */
    public void getResponseHeaders(CefResponse cefResponse, IntRef intRef, StringRef stringRef) {
        void a;
        void a2;
        CustomResourceHandler a3 = stringRef;
        CustomResourceHandler a4 = this;
        a4.for().getResponseHeaders((CefResponse)a2, (IntRef)a, (StringRef)a3);
    }

    public CustomResourceHandler(@NotNull Project project) {
        CustomResourceHandler customResourceHandler = customResourceHandler2;
        CustomResourceHandler customResourceHandler2 = project;
        CustomResourceHandler a = customResourceHandler;
        if (customResourceHandler2 == null) {
            CustomResourceHandler.enum(0);
        }
        if (customResourceHandler2 == null) {
            throw new RuntimeException();
        }
        a.enum = Logger.getInstance(CustomResourceHandler.class);
        a.byte = customResourceHandler2;
    }

    public void cancel() {
        CustomResourceHandler customResourceHandler = this;
        try {
            customResourceHandler.for().close();
            customResourceHandler.super(null);
            return;
        }
        catch (IOException a) {
            a.printStackTrace();
            return;
        }
    }

    private ResourceHandlerState for() {
        CustomResourceHandler a;
        return a.float;
    }

    private void super(ResourceHandlerState resourceHandlerState) {
        ResourceHandlerState a = resourceHandlerState;
        CustomResourceHandler a2 = this;
        a2.float = a;
    }
}
