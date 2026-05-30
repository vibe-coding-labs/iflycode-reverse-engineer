package com.aicode.language;

import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.exception.RequestCancelException;
import com.aicode.service.LanguageInfoSupport;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/* compiled from: re */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/language/LanguageInfoManager.class */
public final class LanguageInfoManager {

    /* renamed from: enum, reason: not valid java name */
    private static final Key<AICodeLanguageInfo> f487enum = Key.create(RequestCancelException.H("\u0012j\u0007\u0002nW{Q;V8J9_:l,\tg"));

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m228enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 3:
            default:
                H = MethodGeneratorConfig.H("\u0014)=!>8/;u=</q\u001fT{\f8 76t#<3.8>'8#\u007fZV)sr3=u\\\u0004{~)t8. )q1)<q=?t/:5;");
                i = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                H = RequestCancelException.H("\u007f\u0016Y%j6\\;\u0016<Q'p\u0010[x\u0012#\u0010|o[]\"L,\u00109Y%\u0014!F0A!QxJ6P7");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 3:
            default:
                i2 = 3;
                break;
            case 1:
            case 2:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 3:
            default:
                objArr[0] = MethodGeneratorConfig.H("'&52");
                i3 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = RequestCancelException.H("2K.\u001f6_2[7}PS9Y7K8{\u001e\u001f\u001b^6W\"W6Q\u001aM\"[\u001e^6E$Y)");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 3:
            default:
                objArr[1] = MethodGeneratorConfig.H(">.\"z::>>;\u0018\\65<;.4\u001e\u0012z\u0017;:2.2:4\u0016(.>\u0012;: (<%");
                i4 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = RequestCancelException.H("1V6T\u001bW?S&B#Q\u001e^(T*R<");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = MethodGeneratorConfig.H("=3:1\u0017236*'/4\u0012;$1&70");
                break;
            case 1:
            case 2:
                break;
            case 3:
                do {
                } while (0 != 0);
                objArr[2] = RequestCancelException.H("R:M r2S4F\"_0");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 3:
            default:
                throw new IllegalArgumentException(format);
            case 1:
            case 2:
                throw new IllegalStateException(format);
        }
    }

    @NotNull
    public static AICodeLanguageInfo findFallback(@NotNull VirtualFile file) {
        if (file == null) {
            m228enum(3);
        }
        Language language = Language.ANY;
        LanguageFileType fileType = file.getFileType();
        if (fileType instanceof LanguageFileType) {
            language = fileType.getLanguage();
        }
        Language language2 = language;
        return new AICodeLanguageInfo(language2, LanguageMap.getId(language2));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    public static AICodeLanguageInfo findLanguageMapping(@NotNull PsiFile file) {
        if (file == null) {
            m228enum(0);
        }
        AICodeLanguageInfo aICodeLanguageInfo = (AICodeLanguageInfo) f487enum.get(file);
        if (aICodeLanguageInfo != null) {
            if (aICodeLanguageInfo == null) {
                m228enum(1);
            }
            return aICodeLanguageInfo;
        }
        AICodeLanguageInfo aICodeLanguageInfo2 = (AICodeLanguageInfo) LanguageInfoSupport.EP.getExtensionList().stream().map(a -> {
            return a.findVSCodeLanguageMapping(file);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).findFirst().orElseThrow(() -> {
            return new IllegalStateException(MethodGeneratorConfig.H("\u001d>31+=+:}5&9>71=) ;<4=}>9:x\u0019\u00182.;36\u0010 ?%2=:q:\u0005\u0007?:!54;Y\u0007:24 u5<)q;#.>70?"));
        });
        f487enum.set(file, aICodeLanguageInfo2);
        if (aICodeLanguageInfo2 == null) {
            m228enum(2);
        }
        return aICodeLanguageInfo2;
    }
}
