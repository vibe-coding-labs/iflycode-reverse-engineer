/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.components.PersistentStateComponent
 *  com.intellij.openapi.components.State
 *  com.intellij.openapi.components.Storage
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.aicode.settings;

import com.aicode.settings.CodeGenerateRequestState;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name="ai.code.plugin.settings.AICodeRequestSettings", storages={@Storage(value="AICodeRequestSettings.xml")})
public class AICodeRequestSettings
implements PersistentStateComponent<CodeGenerateRequestState> {
    private CodeGenerateRequestState state;

    @NotNull
    public static CodeGenerateRequestState settings() {
        CodeGenerateRequestState state = ((AICodeRequestSettings)ApplicationManager.getApplication().getService(AICodeRequestSettings.class)).getState();
        assert (state != null);
        if (state == null) {
            throw new RuntimeException();
        }
        CodeGenerateRequestState codeGenerateRequestState = state;
        if (codeGenerateRequestState == null) {
            AICodeRequestSettings.$$$reportNull$$$0(0);
        }
        return codeGenerateRequestState;
    }

    @Nullable
    public synchronized CodeGenerateRequestState getState() {
        return this.state;
    }

    public synchronized void noStateLoaded() {
        this.state = new CodeGenerateRequestState();
    }

    public synchronized void loadState(@NotNull CodeGenerateRequestState state) {
        if (state == null) {
            AICodeRequestSettings.$$$reportNull$$$0(1);
        }
        this.state = state;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
            case 1: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 2;
                break;
            }
            case 1: {
                n2 = 3;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "com/aicode/settings/AICodeRequestSettings";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "state";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "settings";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[1] = "com/aicode/settings/AICodeRequestSettings";
                break;
            }
        }
        switch (n) {
            default: {
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "loadState";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
            case 1: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}
