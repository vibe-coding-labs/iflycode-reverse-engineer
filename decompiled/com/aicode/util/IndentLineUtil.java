package com.aicode.util;

import com.aicode.diff.FileService;
import com.aicode.ui.ActionButton;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.actions.EditorActionUtil;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/* compiled from: cb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/IndentLineUtil.class */
public class IndentLineUtil {
    public static /* synthetic */ String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (5 << 4) ^ (2 << 1);
        int i2 = (4 << 4) ^ 5;
        int i3 = (3 << 3) ^ 2;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i4 = length2 - 1;
        int i5 = i4;
        int i6 = length;
        while (i4 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (i2 ^ (str.charAt(i7) ^ stringBuffer.charAt(i6)));
            if (i8 < 0) {
                break;
            }
            char charAt = (char) (i3 ^ (str.charAt(i8) ^ stringBuffer.charAt(i6)));
            i5 = i8 - 1;
            i6--;
            cArr[i8] = charAt;
            if (i6 < 0) {
                i6 = length;
            }
        }
        return new String(cArr);
    }

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m409enum(int a) {
        throw new IllegalArgumentException(String.format(FileService.H("\u001d<553)2:?k?0x\n\u001c/*\u0002 +8fol67(2,//o8(\u000bM\u007f\"3gq5qh6w\u0010\u001a+>\u007f#;2D\u0014 w6?1#"), ActionButton.H("\u0010\u000b\u0001\u0006\u0002\u0005"), FileService.H("|b\u0015E>$6(0#p81>\u0011@\u0011$;(:2(\u001f+2\r>4#"), ActionButton.H("\r\u00100+\u001b\u001b$\u001b\u0003\u0012")));
    }

    public static /* synthetic */ int indentLine(Project project, @NotNull Editor editor, int lineNumber, int indent, int caretOffset) {
        if (editor == null) {
            m409enum(0);
        }
        return indentLine(project, editor, lineNumber, indent, caretOffset, EditorActionUtil.shouldUseSmartTabs(project, editor));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static /* synthetic */ int k(CharSequence a, int a2, int a3, int a4) {
        int i = 0;
        int i2 = a2;
        int a5 = i2;
        while (i2 < a3) {
            if (a.charAt(a5) != '\t') {
                i++;
            } else {
                i = ((i / a4) + 1) * a4;
            }
            a5++;
            i2 = a5;
        }
        return i;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ int indentLine(Project project, @NotNull Editor editor, int lineNumber, int indent, int caretOffset, boolean z) {
        boolean z2;
        if (editor == null) {
            m409enum(1);
        }
        EditorSettings settings = editor.getSettings();
        int tabSize = settings.getTabSize(project);
        Document document = editor.getDocument();
        CharSequence immutableCharSequence = document.getImmutableCharSequence();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        if (lineNumber < document.getLineCount()) {
            i2 = document.getLineStartOffset(lineNumber);
            i3 = document.getLineEndOffset(lineNumber);
            i = i2;
            boolean z3 = true;
            while (i <= i3 && i != i3) {
                char charAt = immutableCharSequence.charAt(i);
                if (charAt != '\t') {
                    if (z3) {
                        z3 = false;
                        i4 = i;
                    }
                    if (charAt != ' ') {
                        z2 = z3;
                        break;
                    }
                }
                i++;
            }
            z2 = z3;
            if (z2) {
                i4 = i3;
            }
        }
        int i5 = caretOffset;
        if (caretOffset >= i2 && caretOffset < i3 && i == i3) {
            i = caretOffset;
            i4 = Math.min(caretOffset, i4);
        }
        int k = k(immutableCharSequence, i2, i, tabSize);
        int k2 = k(immutableCharSequence, i2, i4, tabSize);
        int i6 = k + indent;
        int lineNumber2 = i6;
        if (i6 < 0) {
            lineNumber2 = 0;
        }
        int i7 = k2 + indent;
        int i8 = i7;
        if (i7 < 0) {
            i8 = 0;
        }
        if (!z) {
            i8 = lineNumber2;
        }
        StringBuilder sb = new StringBuilder(lineNumber2);
        int i9 = 0;
        int shouldUseSmartTabs = 0;
        while (i9 < lineNumber2) {
            if (tabSize > 0 && settings.isUseTabCharacter(project) && shouldUseSmartTabs + tabSize <= i8) {
                sb.append('\t');
                shouldUseSmartTabs += tabSize;
                i9 = shouldUseSmartTabs;
            } else {
                shouldUseSmartTabs++;
                sb.append(' ');
                i9 = shouldUseSmartTabs;
            }
        }
        int shouldUseSmartTabs2 = i2 + sb.length();
        if (caretOffset < i) {
            if (caretOffset >= i2 && caretOffset > shouldUseSmartTabs2) {
                i5 = shouldUseSmartTabs2;
            }
            return i5;
        }
        return caretOffset + ((sb.length() - i) - i2);
    }
}
