/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.project.Project
 *  org.cef.browser.CefBrowser
 *  org.cef.browser.CefFrame
 *  org.cef.callback.CefSchemeHandlerFactory
 *  org.cef.handler.CefResourceHandler
 *  org.cef.network.CefRequest
 *  org.jetbrains.annotations.NotNull
 */
package com.aicode.view;

import com.aicode.content.util.file.LanguageFileExtensionDetails;
import com.aicode.diff.FileInfo;
import com.aicode.view.CustomResourceHandler;
import com.intellij.openapi.project.Project;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefSchemeHandlerFactory;
import org.cef.handler.CefResourceHandler;
import org.cef.network.CefRequest;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class CustomSchemeHandlerFactory
implements CefSchemeHandlerFactory {
    private Project enum;

    public CustomSchemeHandlerFactory(@NotNull Project project) {
        CustomSchemeHandlerFactory customSchemeHandlerFactory = customSchemeHandlerFactory2;
        CustomSchemeHandlerFactory customSchemeHandlerFactory2 = project;
        CustomSchemeHandlerFactory a = customSchemeHandlerFactory;
        if (customSchemeHandlerFactory2 == null) {
            CustomSchemeHandlerFactory.enum(0);
        }
        if (customSchemeHandlerFactory2 == null) {
            throw new RuntimeException();
        }
        a.enum = customSchemeHandlerFactory2;
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray = new Object[3];
        objectArray[0] = LanguageFileExtensionDetails.H("\t}\rx\u001az\u0000");
        objectArray[1] = FileInfo.H("\u0015p%\u000e\u0019x\u0010u\u001arYi\u0017r$\u00159f\u0006h\u0010{$}\u0016r\u0004e\u0015U\u0014w\u0014t\u001d@\u0015~\u001do\u0010r");
        objectArray[2] = LanguageFileExtensionDetails.H("3\u000b|\u0016mJ");
        throw new IllegalArgumentException(String.format(FileInfo.H(",v\u0015n\u0013r\u0002q\u0015:7CNG&n\u001bH\u0001q\u001a?8@\np\u001e\u007f\nr\u0004?Y2 \u001dZ|\u0013<ZeY;\r7\u0004u.@Z}\u0017eOd\u0011=\u0007u\u000eg"), objectArray));
    }

    public CefResourceHandler create(CefBrowser cefBrowser, CefFrame cefFrame, String string, CefRequest cefRequest) {
        CustomSchemeHandlerFactory customSchemeHandlerFactory = customSchemeHandlerFactory2;
        CustomSchemeHandlerFactory customSchemeHandlerFactory2 = cefRequest;
        CustomSchemeHandlerFactory a = customSchemeHandlerFactory;
        return new CustomResourceHandler(a.enum);
    }
}
