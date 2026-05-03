/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.jvm.functions.Function0
 *  org.jetbrains.annotations.NotNull
 */
package com.aicode.inline.controller;

import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.diff.FileInfo;
import com.aicode.inline.InlineChatInputPanel;
import com.aicode.inline.controller.SessionController;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class EphemeralChatSessionController
extends SessionController {
    @NotNull
    private final AtomicBoolean byte;
    @NotNull
    private final InlineChatInputPanel enum;

    @Override
    public void unlockSession() {
        EphemeralChatSessionController a;
        EphemeralChatSessionController ephemeralChatSessionController = a;
        ephemeralChatSessionController.byte.set(false);
        ephemeralChatSessionController.enum.getButtonPanel().showSendButton((Function0<Boolean>)((Function0)() -> {
            EphemeralChatSessionController a;
            return !a.enum.getInputComponent().getText().isEmpty();
        }));
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray = new Object[3];
        objectArray[0] = FileExtensionLanguageDetails.H("J_ct`yWnstFs|kxNdywl");
        objectArray[1] = FileInfo.H("T\u0004o\\{\u0017t\u0019{\u001b8\u0000n\u0016z\u0019{wR\u001ct\u000ea\u0000j$D\f8-q\u0000d\u001f~\u0006|\u0019_0P\u0000N\u0010o\u001co\u0006n7r\u0019j\u0005q\u0012{\fr");
        objectArray[2] = FileExtensionLanguageDetails.H("0wk~f>");
        throw new IllegalArgumentException(String.format(FileInfo.H("t.9B\u0006g\u001dn^q\u0019m^W'o\u000e]\u0002r4\u0011\u0003{\br\u0002c<D\f7O$\u001b&Rt\u0012=Pov\u0014\u0007=\u0018i\u001crIn\u001biW|\u0012>\u0010b\u0005l"), objectArray));
    }

    public void dispose() {
    }

    @Override
    public void lockSession() {
        EphemeralChatSessionController a;
        EphemeralChatSessionController ephemeralChatSessionController = a;
        ephemeralChatSessionController.byte.set(true);
        ephemeralChatSessionController.enum.getButtonPanel().showStopButton();
    }

    public EphemeralChatSessionController(@NotNull InlineChatInputPanel inlineChatInputPanel) {
        EphemeralChatSessionController ephemeralChatSessionController = inlineChatInputPanel2;
        InlineChatInputPanel inlineChatInputPanel2 = inlineChatInputPanel;
        EphemeralChatSessionController a = ephemeralChatSessionController;
        if (inlineChatInputPanel2 == null) {
            EphemeralChatSessionController.enum(0);
        }
        a.enum = inlineChatInputPanel2;
        EphemeralChatSessionController ephemeralChatSessionController2 = a;
        a.byte = new AtomicBoolean(false);
    }
}
