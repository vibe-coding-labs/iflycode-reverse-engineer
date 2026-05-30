package com.aicode.listener;

import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.enums.CodeTipRequestType;
import com.aicode.enums.OperateActionEnum;
import com.aicode.exception.RequestCancelException;
import com.aicode.service.EditorManagerService;
import com.aicode.service.editor.EditorUtil;
import com.aicode.settings.AICodeRequestSettings;
import com.intellij.codeInsight.lookup.Lookup;
import com.intellij.codeInsight.lookup.LookupEvent;
import com.intellij.codeInsight.lookup.LookupEx;
import com.intellij.codeInsight.lookup.LookupListener;
import com.intellij.codeInsight.lookup.LookupManagerListener;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.KeymapManager;
import javax.swing.KeyStroke;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ig */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/CodeLookupManagerListener.class */
public class CodeLookupManagerListener implements LookupManagerListener {

    /* renamed from: byte, reason: not valid java name */
    private final LookupListener f504byte = new LookupListener() { // from class: com.aicode.listener.CodeLookupManagerListener.01
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m243enum(int a) {
            String H = FileExtensionLanguageDetails.H("Deuu#9ME/{kd%WbQ{Szqg9efbc@Zuv|<&6t2%xt \tM'>`!ysvc.rjc2b!v>7=/");
            Object[] objArr = new Object[3];
            objArr[0] = RequestCancelException.H("=R&R/");
            objArr[1] = FileExtensionLanguageDetails.H("]`p |bzzcu-AVrgkrda(VjswLCQbncLuhdpknI~at!850ur");
            switch (a) {
                case 0:
                default:
                    objArr[2] = RequestCancelException.H("V6E+\u0007w(r\u0005jg6S=G7Y?");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[2] = FileExtensionLanguageDetails.H("pjxyu4\u00058-&-");
                    break;
                case 2:
                    objArr[2] = RequestCancelException.H("\br\u0005jg6S=G7Y?");
                    break;
                case 3:
                    objArr[2] = FileExtensionLanguageDetails.H("ixawpgQa*55.4'");
                    break;
                case 4:
                    objArr[2] = RequestCancelException.H("W&Q6\u0010|\u0015O\u0014bY\u0010W9J$Y?");
                    break;
            }
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        public void itemSelected(@NotNull LookupEvent a) {
            if (a == null) {
                m243enum(2);
            }
            super.itemSelected(a);
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public boolean beforeItemSelected(@NotNull LookupEvent a) {
            if (a == null) {
                m243enum(0);
            }
            String valueOf = String.valueOf(a.getCompletionChar());
            boolean shortcutForAction = CodeLookupManagerListener.getShortcutForAction(FileExtensionLanguageDetails.H("RHWiar }ug~y\r8<#(0"));
            Editor editor = a.getLookup().getEditor();
            if (shortcutForAction && valueOf.contains(RequestCancelException.H("[")) && EditorManagerService.getInstance().hasTipInlays(editor)) {
                if (!EditorManagerService.getInstance().acceptTip(editor)) {
                    return super.beforeItemSelected(a);
                }
                AutoCodeGenerateListener.ignoreLookupApply.set(true);
                LookupEx lookup = a.getLookup();
                if (lookup != null) {
                    lookup.hideLookup(true);
                }
                return false;
            }
            return super.beforeItemSelected(a);
        }

        public void currentItemChanged(@NotNull LookupEvent a) {
            if (a == null) {
                m243enum(4);
            }
            Editor editor = a.getLookup().getEditor();
            if (EditorManagerService.getInstance().hasTipInlays(editor)) {
                EditorManagerService.getInstance().editorChanged(editor, CodeTipRequestType.Automatic, true);
            }
            super.currentItemChanged(a);
        }

        public void lookupShown(@NotNull LookupEvent a) {
            if (a == null) {
                m243enum(1);
            }
            Editor editor = a.getLookup().getEditor();
            if (EditorManagerService.getInstance().hasTipInlays(editor)) {
                EditorManagerService.getInstance().editorChanged(editor, CodeTipRequestType.Automatic, true);
            }
            super.lookupShown(a);
        }

        public void lookupCanceled(@NotNull LookupEvent a) {
            if (a == null) {
                m243enum(3);
            }
            Editor editor = a.getLookup().getEditor();
            if (EditorManagerService.getInstance().hasTipInlays(editor)) {
                EditorManagerService.getInstance().editorChanged(editor, CodeTipRequestType.Automatic, true);
            }
            super.lookupCanceled(a);
        }
    };

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f505enum = Logger.getInstance(CodeLookupManagerListener.class);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static boolean getShortcutForAction(String a) {
        Keymap activeKeymap = KeymapManager.getInstance().getActiveKeymap();
        if (activeKeymap != null) {
            KeyboardShortcut[] shortcuts = activeKeymap.getShortcuts(a);
            if (shortcuts.length != 0) {
                int length = shortcuts.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    KeyboardShortcut keyboardShortcut = shortcuts[i2];
                    if (keyboardShortcut instanceof KeyboardShortcut) {
                        KeyboardShortcut keyboardShortcut2 = keyboardShortcut;
                        KeyStroke firstKeyStroke = keyboardShortcut2.getFirstKeyStroke();
                        KeyStroke secondKeyStroke = keyboardShortcut2.getSecondKeyStroke();
                        if (firstKeyStroke.getKeyCode() == 9 && secondKeyStroke == null) {
                            return true;
                        }
                    }
                    i2++;
                    i = i2;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void activeLookupChanged(@Nullable Lookup oldLookup, @Nullable Lookup a) {
        if (a != null) {
            a.addLookupListener(this.f504byte);
        }
        Lookup lookup = a != null ? a : oldLookup;
        if ((lookup != null ? lookup.getPsiFile() : null) == null) {
            EditorManagerService editorManagerService = EditorManagerService.getInstance();
            if (oldLookup != null && a == null) {
                if (oldLookup.getPsiFile() != null) {
                    Editor editor = oldLookup.getEditor();
                    if (!EditorUtil.isSelectedEditor(editor) || !editorManagerService.isAvailable(editor) || editor.getDocument().isInBulkUpdate()) {
                        return;
                    }
                    editorManagerService.editorChanged(editor, CodeTipRequestType.Forced, false);
                    return;
                }
                return;
            }
            if (a != null && oldLookup == null && !AICodeRequestSettings.settings().isShowIdeCodeTips()) {
                Editor editor2 = a.getEditor();
                if (!editorManagerService.isAvailable(editor2)) {
                    return;
                }
                editorManagerService.cancelTipRequests(editor2);
                editorManagerService.disposeTips(editor2, OperateActionEnum.IdeCompletion);
            }
        }
    }
}
