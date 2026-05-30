package com.aicode.language;

import com.aicode.service.LanguageInfoSupport;
import com.aicode.ui.FontKt;
import com.aicode.util.NewFileUtils;
import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: co */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/language/AICodeExtendedLanguageSupport.class */
public class AICodeExtendedLanguageSupport implements LanguageInfoSupport {

    /* renamed from: enum, reason: not valid java name */
    private static final Map<W, String> f481enum = Map.of(new W(FontKt.H("\u001e\u0004\u0013\u0003\n&,+3+)#"), NewFileUtils.H("\u0002\u0003Z")), FontKt.H("}5>:4=005*0&>ja"));

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m223enum(int a) {
        throw new IllegalArgumentException(String.format(NewFileUtils.H("L-F\u0006G\u001dE\r+?Y\u0016\u001a(e\u0016T<_\u0014GYZ\u0019p1B\u0018T\u0017[[\u001cL\\Z\t\u0014MY8<\u0015LMLS\u0019R\u0007\u001c��U\u001c\u000b\u001bERU\u001cO\u001d"), FontKt.H("$76#"), NewFileUtils.H("\u000bD\u0014\u000f\u0013C\u001bD\u001dOWn1A\u001aU\u0013N\u001e\u0014(f>F\u001fN<e;^\u0007Z\tZ @\u001d[\u001b[\u000fN*U\u0002K\u0006Q\u0005"), FontKt.H("*; ��.\u0011\u001d(?\"\u00179*\",\"87\u000385274!")));
    }

    /* compiled from: co */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/language/AICodeExtendedLanguageSupport$W.class */
    private static final class W {

        /* renamed from: byte, reason: not valid java name */
        private final String f482byte;

        /* renamed from: enum, reason: not valid java name */
        private final String f483enum;

        public String HB() {
            return this.f482byte;
        }

        public String toString() {
            return "ExtensionOverrideLanguageInfoSupport.Key(languageId=" + HB() + ", extension=" + PC() + ")";
        }

        public W(String a, String a2) {
            this.f482byte = a;
            this.f483enum = a2;
        }

        public String PC() {
            return this.f483enum;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.LanguageInfoSupport
    @Nullable
    public AICodeLanguageInfo findVSCodeLanguageMapping(@NotNull PsiFile a) {
        if (a == null) {
            m223enum(0);
        }
        VirtualFile virtualFile = a.getVirtualFile();
        if (virtualFile == null) {
            return null;
        }
        Language language = a.getLanguage();
        String str = f481enum.get(new W(language.getID(), virtualFile.getExtension()));
        if (str != null) {
            return new AICodeLanguageInfo(language, str);
        }
        return null;
    }
}
