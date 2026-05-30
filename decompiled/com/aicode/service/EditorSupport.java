package com.aicode.service;

import com.aicode.inline.controller.ChatInputController;
import com.aicode.util.AICodeStringUtil;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.annotations.NotNull;

/* compiled from: k */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/EditorSupport.class */
public interface EditorSupport {
    public static final ExtensionPointName<EditorSupport> EP = ExtensionPointName.create(ChatInputController.H("\u0003\u0007I\b\b\u000f\u001dZ\u0015\u0005\u0016\bBIZ\u001d\u001f\u001e\n\u001d\u000b&\u000b\u0002\u0013��[Q"));

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m266enum(int a) {
        throw new IllegalArgumentException(String.format(AICodeStringUtil.H("f^B[\u000b\bGV\u0001LDR\u0007lbHYh\u0013\u0001W\u0010]GHPSPUOY��\n\u0003\u0015J-iJ\u0007\u0004Y\u0012\u0012T\fWDhd\u001dXWG\u0018QB\fTDP["), ChatInputController.H("\u001c\u0011\u000b\u001a\u0015\u0004"), AICodeStringUtil.H("HO@\t\u0007\u0004niHB\u000eYYEQEYT4UY_L\\J`R\\J^NC"), ChatInputController.H("\u001d\u0016, !\u0016\u0001\u0015(\b\u000f\u001d \f\u0019\u0010<\u0007\u000e\t\u001a\u0010\u001a\u001f\u0012")));
    }

    boolean isCodeTipsEnabled(@NotNull Editor editor);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    static boolean isEditorCodeTipsSupported(@NotNull Editor editor) {
        if (editor == null) {
            m266enum(0);
        }
        if (EP.hasAnyExtensions() && EP.findFirstSafe(a -> {
            return !a.isCodeTipsEnabled(editor);
        }) != null) {
            return false;
        }
        return true;
    }
}
