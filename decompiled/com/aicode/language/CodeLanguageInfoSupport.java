package com.aicode.language;

import com.aicode.inline.controller.ChatInputController;
import com.aicode.service.LanguageInfoSupport;
import com.aicode.service.editor.CancelRequestTip;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.fileTypes.PlainTextLikeFileType;
import com.intellij.openapi.fileTypes.impl.AbstractFileType;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: if */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/language/CodeLanguageInfoSupport.class */
public class CodeLanguageInfoSupport implements LanguageInfoSupport {
    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m225enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 3:
            case 4:
            default:
                H = CancelRequestTip.H("$\u0017\u0004\u0016\f\u0004D^g!\u0004\u0019@ /\u000e<\u0006\u0010\t\u0006J\u0013\u0002\u0003\u0010\b��\u0017\u0006\u0013Ajh\u0019MB\r\rKr$_T\u0007T\u0019\u0001\u0018\u001fV\u0018\u001f\u0004A\u0003\u000fJ\u001f\u0004\u0005\u0005");
                i = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                H = ChatInputController.H("9;\u001f\b,\u001b\u001a\u0016P\u0011\u0017\n6=\u001dUT\u000eVQ7h\u000f\u001b\u0014\u001fG\u0005\u0017��E\u001b\u0006\u001b\u0007\f\u0017U\f\u001b\u0016\u001a");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 3:
            case 4:
            default:
                i2 = 3;
                break;
            case 2:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[0] = CancelRequestTip.H("\u0017\u0018\u0005\f");
                i3 = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = ChatInputController.H("\u0019\u0016\u0018_\u001d\u000b\r\u0019\u001e\u0015S\u001e\u001f05\f\u0014\u0016\u0018W7+,\u0007\"\u0006\u0005��\u001e\u0019\u0013�� \r\t\u001d-\f\u0005\u0012\u0001\b\u0002");
                i3 = a;
                break;
            case 3:
                objArr[0] = CancelRequestTip.H("\r��\u0004\r\u0004\u0010\u000e\f");
                i3 = a;
                break;
            case 4:
                objArr[0] = ChatInputController.H("\u0014\u0017\u0015\u0010,\u000f\u0017\u0013");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 3:
            case 4:
            default:
                objArr[1] = CancelRequestTip.H("\u0006\u0005\u0007L\u0002\u0018\u0012\n\u0001\u0006L\r��#*\u001f\u000b\u0005\u0007D(83\u0014=\u0015\u001a\u0013\u0001\n\f\u0013?\u001e\u0016\u000e2\u001f\u001a\u0001\u001e\u001b\u001d");
                i4 = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = ChatInputController.H("\u0001\u0002\u0016\u0010'\f\u0010\u001b>\u001f\u0017\u0012\u0017\u000f\u001d\u0013");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = CancelRequestTip.H("\u0004\u0002\u00053\u0001\"2\u001b\u0010\u00118\n\u0005\u0011\u0003\u0011\u0017\u0004,\u000b\u001a\u0001\u0018\u0007\u000e");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = ChatInputController.H("\u0001\u0002\u0016\u0010'\f\u0010\u001b>\u001f\u0017\u0012\u0017\u000f\u001d\u0013");
                break;
            case 2:
                break;
            case 3:
            case 4:
                objArr[2] = CancelRequestTip.H("\u0002$\u0016\u0004\u0005\u001b0\u0011��\u000e\b\u0002\u0013\u0014 \r��\u0003\u0004%\u0014\u0011\u001d");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 3:
            case 4:
            default:
                throw new IllegalArgumentException(format);
            case 2:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.LanguageInfoSupport
    @Nullable
    public AICodeLanguageInfo findVSCodeLanguageMapping(@NotNull PsiFile a) {
        if (a == null) {
            m225enum(0);
        }
        Language Kc = Kc(a);
        if (a.getFileType() instanceof AbstractFileType) {
            return new AICodeLanguageInfo(Kc, a.getFileType().getName());
        }
        if (Kc == Language.ANY || Kb(Kc, a.getName())) {
            CharSequence extension = FileUtilRt.getExtension(a.getName(), (String) null);
            return new AICodeLanguageInfo(Kc, extension != null ? extension.toString() : ChatInputController.H("\u001b\u000b\n\u0017\u001c\r\u0010\b\b"));
        }
        return new AICodeLanguageInfo(Kc, LanguageMap.getId(Kc));
    }

    @NotNull
    private Language Kc(@NotNull PsiFile a) {
        if (a == null) {
            m225enum(1);
        }
        Language language = a.getLanguage();
        Language language2 = language;
        if (language == Language.ANY && (a.getFileType() instanceof LanguageFileType)) {
            language2 = a.getFileType().getLanguage();
        }
        Language language3 = language2;
        if (language3 == null) {
            m225enum(2);
        }
        return language3;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean Kb(@NotNull Language language, @NotNull String fileName) {
        if (language == null) {
            m225enum(3);
        }
        if (fileName == null) {
            m225enum(4);
        }
        if (language == PlainTextLanguage.INSTANCE && !(FileTypeManager.getInstance().getFileTypeByFileName(fileName) instanceof PlainTextLikeFileType)) {
            return true;
        }
        return false;
    }
}
