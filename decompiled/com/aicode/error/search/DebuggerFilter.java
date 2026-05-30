package com.aicode.error.search;

import com.aicode.PluginStartupActivity;
import com.aicode.exception.RequestTimeoutException;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.JComponentKt;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.filters.JvmExceptionOccurrenceFilter;
import com.intellij.execution.impl.InlayProvider;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorCustomElementRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ah */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/error/search/DebuggerFilter.class */
public class DebuggerFilter implements JvmExceptionOccurrenceFilter {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m155enum(int a) {
        String H = RequestTimeoutException.H("\u001b\t6\u0005=\u0014uNp\u0017(\u0014g&\u0014\u00143(nV*G \u0010&\u0014*\u0003\"\u0012/\\<\u001f\u0002wp\u001e1Ve\u0012|V!S=\u00044\u0012S<3\ty\u001a$@>\u0004+\n");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = JComponentKt.H(" \u001b#\u0003(\n ��0;%\u000e1\u0017\u0017\u001e,\u0002");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = RequestTimeoutException.H("\u001b-\u0001#\u0002\"\u0015");
                break;
        }
        objArr[1] = JComponentKt.H("aK\u0005a(\u0006-\u0007=\u001ad\b9\u001f&\u001dq\u000b\u000f-7��(I\u001c\u001b+\u001a9\u001f,\u001d\u0004\r5\u000b$\u0015");
        objArr[2] = RequestTimeoutException.H("3,\r5\u0001\u0007\t<\u0005\"\u0014");
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* compiled from: ah */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/error/search/DebuggerFilter$V.class */
    private static class V extends Filter.ResultItem implements InlayProvider {

        /* renamed from: byte, reason: not valid java name */
        private final int f292byte;

        /* renamed from: enum, reason: not valid java name */
        private final Project f293enum;

        public V(int a, int a2, Project a3) {
            super(a, a2, (HyperlinkInfo) null);
            this.f293enum = a3;
            this.f292byte = a;
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public EditorCustomElementRenderer createInlayRenderer(Editor a) {
            if (!StringUtils.isBlank(PluginStartupActivity.getApiKey()) && AICodeSettingsState.getInstance().enableCodeDebug) {
                return new Presentation(a, this.f293enum, this.f292byte);
            }
            return null;
        }
    }

    @Nullable
    public Filter.ResultItem applyFilter(@NotNull String exceptionClassName, @NotNull List<PsiClass> list, int a) {
        if (exceptionClassName == null) {
            m155enum(0);
        }
        if (list == null) {
            m155enum(1);
        }
        return new V(a, a + exceptionClassName.length(), list.get(0).getProject());
    }
}
