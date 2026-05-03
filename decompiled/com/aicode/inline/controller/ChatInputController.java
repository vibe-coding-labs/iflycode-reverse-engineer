/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.ui.components.JBTextArea
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.aicode.inline.controller;

import com.aicode.action.batch.GeneratorConfig;
import com.aicode.content.util.file.LanguageFileExtensionDetails;
import com.intellij.ui.components.JBTextArea;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class ChatInputController {
    @NotNull
    private final JBTextArea float;
    @NotNull
    private final Function1<String, Unit> byte;
    @Nullable
    private String enum;

    /*
     * WARNING - void declaration
     */
    public ChatInputController(@NotNull JBTextArea jBTextArea, @NotNull Function1<String, Unit> function1) {
        void textArea;
        Function1<String, Unit> function12 = function13;
        Function1<String, Unit> function13 = function1;
        Function1<String, Unit> a = function12;
        if (textArea == null) {
            ChatInputController.enum(0);
        }
        if (function13 == null) {
            ChatInputController.enum(1);
        }
        a.float = textArea;
        a.byte = function13;
    }

    public void stop() {
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray;
        String string = LanguageFileExtensionDetails.H("\u000fQd\u001bb\u0007`\u00174\u001f}\r/\"B\u000ex/p\u0004~_>BQ/e\u0000u\tfY\u000ea}D0\u0012sX1\n\rk|Bc\u0016g\r2\u0011`\u0016,\u0003iAk\u001d~\u0013");
        Object[] objectArray2 = new Object[3];
        switch (a) {
            default: {
                objectArray = objectArray2;
                objectArray2[0] = GeneratorConfig.H("\u0013\u0014\u001f\u0005/\n\u001c\u000e");
                break;
            }
            case 1: {
                objectArray = objectArray2;
                while (false) {
                }
                objectArray2[0] = LanguageFileExtensionDetails.H("c\u000f_\u0014g\u0005{\u000b");
                break;
            }
        }
        objectArray[1] = GeneratorConfig.H("\u0004\u001e\n^\u000f\u0011\u001a\u0000AVg7\r\u0019\u0003\u0012\u001aF!;\u000b\u0007\t\u0002\u0012\u0004\u001a\u001bg\u001d\f\u0013\u0011:\u0011\u0019\f\u001b'\u001d\t\u0005\u0015\u001e\u0002\u0014\u001c\u001d");
        objectArray[2] = LanguageFileExtensionDetails.H("0\bk\u0001fA");
        throw new IllegalArgumentException(String.format(string, objectArray));
    }

    public void submit() {
        ChatInputController chatInputController = this;
        if (!StringsKt.isBlank((CharSequence)chatInputController.float.getText())) {
            ChatInputController chatInputController2 = chatInputController;
            String a = StringsKt.trim((CharSequence)chatInputController2.float.getText()).toString();
            chatInputController2.float.setText("");
            chatInputController2.updateInput(null);
            chatInputController2.byte.invoke((Object)a);
        }
    }

    public static String H(Object object) {
        int a;
        Object object2 = object;
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String string = new StringBuffer(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName()).toString();
        object2 = (String)object2;
        int n = ((String)object2).length();
        int n2 = n - 1;
        char[] cArray = new char[n];
        int n3 = 2 << 3 ^ (2 ^ 5);
        int cfr_ignored_0 = (2 ^ 5) << 4 ^ 1;
        int n4 = 3 << 3 ^ 3;
        int n5 = a = string.length() - 1;
        int n6 = n2;
        String string2 = string;
        while (n6 >= 0) {
            int n7 = n2--;
            cArray[n7] = (char)(n4 ^ (((String)object2).charAt(n7) ^ string2.charAt(a)));
            if (n2 < 0) break;
            int n8 = n2--;
            char c = cArray[n8] = (char)(n3 ^ (((String)object2).charAt(n8) ^ string2.charAt(a)));
            if (--a < 0) {
                a = n5;
            }
            n6 = n2;
        }
        return new String(cArray);
    }

    public final void updateInput(@Nullable String string) {
        ChatInputController chatInputController = string2;
        String string2 = string;
        ChatInputController a = chatInputController;
        if (!Intrinsics.areEqual((Object)string2, (Object)a.enum)) {
            a.enum = string2;
        }
    }
}
