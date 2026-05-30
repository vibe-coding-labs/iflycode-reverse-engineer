package com.aicode.util;

import com.aicode.diff.GenericUtils;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.ProperTextRange;
import com.intellij.openapi.util.TextRange;
import com.intellij.util.diff.Diff;
import com.intellij.util.diff.FilesTooBigForDiffException;
import com.intellij.util.text.TextRanges;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ub */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/AICodeStringUtil.class */
public final class AICodeStringUtil {
    public static /* synthetic */ String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (1 << 3) ^ 3;
        int i2 = (4 << 4) ^ 3;
        int i3 = (4 << 4) ^ (4 << 1);
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

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum */
    private static /* synthetic */ void m391enum(int a) {
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
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
                H = GenericUtils.H("\u001e%2(44<.s=wbc\u000b\f%+\u0019/>t0\u0007\u001e\r\u0016\u0018\u0018-4 zt~\u0016Jb%\"lz$vu\"y\u000e\u001e1>\u007f95&s9=p->7?");
                i = a;
                break;
            case 2:
            case 5:
            case 9:
                do {
                } while (0 != 0);
                H = PositionUtil.H("2Lr\u0019[\u0010s\u0003\u0013.]<Q&k_\r+��{Fe_7H?)\u0017G,\u00157U4L;\\bG,]-");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
                i2 = 3;
                break;
            case 2:
            case 5:
            case 9:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 1:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 16:
            default:
                objArr[0] = GenericUtils.H("7.#'");
                i3 = a;
                break;
            case 2:
            case 5:
            case 9:
                do {
                } while (0 != 0);
                objArr[0] = PositionUtil.H("['Tfn\u0016K7J;\u001a0F+WdH0k7Q c4K \\%|-X-");
                i3 = a;
                break;
            case 10:
                objArr[0] = GenericUtils.H("(>.:\u0007)99/\"54");
                i3 = a;
                break;
            case 11:
                objArr[0] = PositionUtil.H("2Y4Q\u0005W#M0_&");
                i3 = a;
                break;
            case 12:
                objArr[0] = GenericUtils.H("=4*?4!");
                i3 = a;
                break;
            case 13:
            case 20:
            case 22:
                objArr[0] = PositionUtil.H("S/T9^']0^/");
                i3 = a;
                break;
            case 14:
                objArr[0] = GenericUtils.H("'*/2");
                i3 = a;
                break;
            case 15:
                objArr[0] = PositionUtil.H("2\\/W0L7R$");
                i3 = a;
                break;
            case 17:
                objArr[0] = GenericUtils.H("2");
                i3 = a;
                break;
            case 18:
                objArr[0] = PositionUtil.H("#");
                i3 = a;
                break;
            case 19:
            case 21:
                objArr[0] = GenericUtils.H("\u0006\u000f+>0%\u0016;=>\u000b%%-2+");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
                objArr[1] = PositionUtil.H("['Tfn\u0016K7J;\u001a0F+WdH0k7Q c4K \\%|-X-");
                i4 = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = GenericUtils.H("%+\u0002\u0002.#10\r::/=#3*86");
                i4 = a;
                break;
            case 5:
                objArr[1] = PositionUtil.H("'l\u0018L1[\"g(P=W1Y8R$");
                i4 = a;
                break;
            case 9:
                objArr[1] = GenericUtils.H(",#(;#\u0017=1'\"54");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = PositionUtil.H("C0U F\u000e@7T2");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = GenericUtils.H("%+\u0002\u0002.#10\r::/=#3*86");
                break;
            case 2:
            case 5:
            case 9:
                break;
            case 3:
                objArr[2] = PositionUtil.H("Z,T,^+U,^\u0011A,P6@!Z,~'G>E)");
                break;
            case 4:
                objArr[2] = GenericUtils.H("5\u0006\n&#10\r::/=#3*86");
                break;
            case 6:
                objArr[2] = PositionUtil.H("2P$V+U,^\u0011A,P6@!Z,~'G>E)");
                break;
            case 7:
                objArr[2] = GenericUtils.H("?>\u0004\t+$#\u001b7>,5\")\u0002\b'\u0006:9=&;\f1$+\u001f:1");
                break;
            case 8:
                objArr[2] = PositionUtil.H("F1B)I\u0005W#M0_&");
                break;
            case 10:
            case 11:
                objArr[2] = GenericUtils.H(">9?=,\u001d'836*\":5?\u001c*%> ");
                break;
            case 12:
            case 13:
                objArr[2] = PositionUtil.H("j\u000bM9A t)_/{,E8H2");
                break;
            case 14:
            case 15:
                objArr[2] = GenericUtils.H("\u0005\u0002,.\u001c87?<5\b\"&-2+");
                break;
            case 16:
                objArr[2] = PositionUtil.H("R D\u000e\\1F\u000e@7T2");
                break;
            case 17:
            case 18:
                objArr[2] = GenericUtils.H("6;=>+\u001d\"?8;");
                break;
            case 19:
            case 20:
                objArr[2] = PositionUtil.H("V*}\u001a@\u000b@#V)A\u001aW!]0^/");
                break;
            case 21:
            case 22:
                objArr[2] = GenericUtils.H(">>-.\n6)7\u0004/452 \u0019-/>+");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
                throw new IllegalArgumentException(format);
            case 2:
            case 5:
            case 9:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isSpaceOrTab(char c, boolean z) {
        return c == ' ' || c == '\t' || (z && c == '\n');
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ int trailingWhitespaceLength(@NotNull String text) {
        int i;
        if (text == null) {
            m391enum(3);
        }
        if (!text.isEmpty()) {
            int length = text.length();
            int i2 = length;
            int i3 = i2;
            while (true) {
                if (i3 <= 0) {
                    i = length;
                    break;
                }
                char charAt = text.charAt(i2 - 1);
                if (charAt != ' ' && charAt != '\t') {
                    i = length;
                    break;
                }
                i2--;
                i3 = i2;
            }
            return i - i2;
        }
        return 0;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ List<String> getNextLines(@NotNull String text, int offset, int maxLines) {
        if (text == null) {
            m391enum(16);
        }
        LinkedList linkedList = new LinkedList();
        int i = 0;
        int i2 = offset;
        while (true) {
            if (i >= maxLines) {
                break;
            }
            int indexOf = text.indexOf(10, i2);
            if (indexOf != -1) {
                linkedList.add(text.substring(i2, indexOf));
                i++;
                i2 = indexOf + 1;
            } else if (text.length() > i2) {
                linkedList.add(text.substring(i2));
                return linkedList;
            }
        }
        return linkedList;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Nullable
    public static /* synthetic */ List<Pair<Integer, String>> createDiffInlays(@NotNull String completion, @NotNull String editor, boolean z) {
        if (completion == null) {
            m391enum(12);
        }
        if (editor == null) {
            m391enum(13);
        }
        String editor2 = u(editor, completion);
        String substring = completion.substring(editor2.length());
        String substring2 = editor.substring(editor2.length());
        int[] array = substring.chars().toArray();
        int[] array2 = substring2.chars().toArray();
        x(array2);
        int length = editor2.length();
        try {
            Diff.Change buildChanges = Diff.buildChanges(array, array2);
            if (buildChanges != null) {
                LinkedList linkedList = new LinkedList();
                Iterator it = buildChanges.toList().iterator();
                while (it.hasNext()) {
                    Diff.Change change = (Diff.Change) it.next();
                    if (change.inserted > 0) {
                        linkedList.add(Pair.create(Integer.valueOf(change.line0 + length), Q(array2, change.line1, change.inserted)));
                    }
                }
                return linkedList;
            }
            return null;
        } catch (FilesTooBigForDiffException e) {
            return null;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isSpacesOrTabs(CharSequence a, boolean z) {
        int i = 0;
        int i2 = 0;
        while (i < a.length()) {
            if (!isSpaceOrTab(a.charAt(i2), z)) {
                return false;
            }
            i2++;
            i = i2;
        }
        return true;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    public static /* synthetic */ String leadingWhitespace(@NotNull String text) {
        if (text == null) {
            m391enum(4);
        }
        if (!text.isEmpty()) {
            String substring = text.substring(0, leadingWhitespaceLength(text));
            if (substring == null) {
                m391enum(5);
            }
            return substring;
        }
        return "";
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean linesMatch(@NotNull Iterable<String> iterable, @NotNull Iterable<String> iterable2, boolean z) {
        if (iterable == null) {
            m391enum(17);
        }
        if (iterable2 == null) {
            m391enum(18);
        }
        Iterator<String> it = iterable.iterator();
        Iterator<String> it2 = iterable2.iterator();
        while (it.hasNext() && it2.hasNext()) {
            String next = it.next();
            String next2 = it2.next();
            if (!(z ? next.stripTrailing().equals(next2.stripTrailing()) : next.equals(next2))) {
                return false;
            }
        }
        return (it.hasNext() || it2.hasNext()) ? false : true;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ int findOverlappingLines(@NotNull List<String> list, @NotNull List<String> list2) {
        if (list == null) {
            m391enum(10);
        }
        if (list2 == null) {
            m391enum(11);
        }
        if (!list.isEmpty() && !list2.isEmpty()) {
            int size = list.size();
            int min = Math.min(size, list2.size());
            int i = 0;
            int i2 = 1;
            int i3 = 1;
            while (i2 <= min) {
                if (linesMatch(list2.subList(0, i3), list.subList(size - i3, size), true)) {
                    i = i3;
                } else if (i > 0) {
                    return i;
                }
                i3++;
                i2 = i3;
            }
            return i;
        }
        return 0;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    public static /* synthetic */ String stripLeading(@NotNull String text) {
        if (text == null) {
            m391enum(8);
        }
        if (!text.isEmpty()) {
            int leadingWhitespaceLength = leadingWhitespaceLength(text);
            String substring = leadingWhitespaceLength == 0 ? text : text.substring(leadingWhitespaceLength);
            if (substring == null) {
                m391enum(9);
            }
            return substring;
        }
        return "";
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static /* synthetic */ String u(@NotNull String data, @NotNull String reference) {
        if (data == null) {
            m391enum(14);
        }
        if (reference == null) {
            m391enum(15);
        }
        int min = Math.min(data.length(), reference.length());
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < min && data.charAt(i3) == reference.charAt(i3)) {
            i3++;
            i2 = i3;
            i++;
        }
        return data.substring(0, i);
    }

    public static /* synthetic */ String[] splitLines(@NotNull CharSequence text) {
        if (text == null) {
            m391enum(0);
        }
        return (String[]) text.toString().lines().toArray(a -> {
            return new String[a];
        });
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ int leadingWhitespaceLength(@NotNull String text) {
        char charAt;
        if (text == null) {
            m391enum(6);
        }
        int length = text.length();
        int i = 0;
        int i2 = 0;
        while (i < length && (charAt = text.charAt(i2)) != '\n') {
            if (!Character.isWhitespace(charAt)) {
                return i2;
            }
            i2++;
            i = i2;
        }
        return i2;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    public static /* synthetic */ String trailingWhitespace(@NotNull String text) {
        String str;
        if (text == null) {
            m391enum(1);
        }
        if (!text.isEmpty()) {
            int length = text.length();
            int i = length;
            while (length > 0) {
                char charAt = text.charAt(i - 1);
                if (charAt == '\n') {
                    break;
                }
                if (!Character.isWhitespace(charAt)) {
                    str = text;
                    break;
                }
                i--;
                length = i;
            }
            str = text;
            String substring = str.substring(i);
            if (substring == null) {
                m391enum(2);
            }
            return substring;
        }
        return "";
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ int leadingWhitespaceLengthWithTab(@NotNull String text, int tabSize) {
        if (text == null) {
            m391enum(7);
        }
        int length = text.length();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char charAt = text.charAt(i3);
            if (charAt == ' ') {
                i++;
            } else {
                if (charAt != '\t') {
                    break;
                }
                i += tabSize;
            }
            i3++;
            i2 = i3;
        }
        return i;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Nullable
    public static /* synthetic */ List<Pair<Integer, String>> matchSuffixSection(@NotNull String editorLineSuffix, @NotNull String completion) {
        int lastIndexOf;
        if (editorLineSuffix == null) {
            m391enum(19);
        }
        if (completion == null) {
            m391enum(20);
        }
        LinkedList linkedList = new LinkedList();
        char[] charArray = editorLineSuffix.toCharArray();
        int length = charArray.length - 1;
        int i = length;
        while (length >= 0) {
            String editorLineSuffix2 = Character.toString(charArray[i]);
            if (!org.apache.commons.lang3.StringUtils.isEmpty(editorLineSuffix2) && (lastIndexOf = completion.lastIndexOf(editorLineSuffix2)) != -1) {
                String str = completion;
                String substring = str.substring(lastIndexOf + 1);
                completion = str.substring(0, lastIndexOf);
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(substring)) {
                    linkedList.add(Pair.create(Integer.valueOf(i + 1), substring));
                }
            }
            i--;
            length = i;
        }
        linkedList.add(Pair.create(0, completion));
        Collections.reverse(linkedList);
        return linkedList;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ int notMatchSuffixIndex(@NotNull String editorLineSuffix, @NotNull String completion) {
        if (editorLineSuffix == null) {
            m391enum(21);
        }
        if (completion == null) {
            m391enum(22);
        }
        char[] charArray = editorLineSuffix.toCharArray();
        int length = charArray.length - 1;
        int i = length;
        while (length >= 0) {
            String editorLineSuffix2 = Character.toString(charArray[i]);
            if (!org.apache.commons.lang3.StringUtils.isEmpty(editorLineSuffix2)) {
                int lastIndexOf = completion.lastIndexOf(editorLineSuffix2);
                if (lastIndexOf == -1) {
                    return i + 1;
                }
                String str = completion;
                str.substring(lastIndexOf + 1);
                completion = str.substring(0, lastIndexOf);
            }
            i--;
            length = i;
        }
        return 0;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static /* synthetic */ TextRanges T(int[] a) {
        TextRanges textRanges = new TextRanges();
        int i = -1;
        int i2 = -1;
        int i3 = 0;
        int i4 = 0;
        while (i3 < a.length) {
            int i5 = a[i4];
            if (i5 == 34 && i == -1) {
                if (i2 != -1) {
                    textRanges.union(new ProperTextRange(i2, i4));
                    i2 = -1;
                } else {
                    i2 = i4;
                }
            } else if (i5 == 39 && i2 == -1) {
                if (i == -1) {
                    i = i4;
                } else {
                    textRanges.union(new ProperTextRange(i, i4));
                    i = -1;
                }
            }
            i4++;
            i3 = i4;
        }
        return textRanges;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static /* synthetic */ int z(int[] a, int a2, char c, char c2, TextRanges a3) {
        int i = 0;
        int i2 = a2;
        int i3 = i2;
        while (i2 < a.length) {
            if (!U(a3, i3)) {
                int i4 = a[i3];
                if (i4 == c2) {
                    i++;
                } else if (i4 != c) {
                    continue;
                } else if (i != 0) {
                    i--;
                } else {
                    return i3;
                }
            }
            i3++;
            i2 = i3;
        }
        return -1;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ int[] x(int[] a) {
        int z;
        TextRanges T = T(a);
        int i = 0;
        int i2 = 0;
        while (i < a.length) {
            int i3 = a[i2];
            if ((i3 == 40 || i3 == 41) && U(T, i2)) {
                a[i2] = 65536 + (i3 == 41 ? 1 : 0);
            } else if (i3 == 40 && (z = z(a, i2 + 1, ')', '(', T)) != -1) {
                a[i2] = 65536;
                a[z] = 65537;
            }
            i2++;
            i = i2;
        }
        return a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String Q(int[] a, int a2, int a3) {
        int[] iArr = new int[a3];
        int i = 0;
        int i2 = 0;
        while (i < a3) {
            int i3 = a[a2 + i2];
            switch (i3) {
                case 65536:
                    do {
                    } while (0 != 0);
                    iArr[i2] = 40;
                    break;
                case 65537:
                    iArr[i2] = 41;
                    break;
                case 65538:
                    iArr[i2] = 123;
                    break;
                case 65539:
                    iArr[i2] = 125;
                    break;
                case 65540:
                    iArr[i2] = 91;
                    break;
                case 65541:
                    iArr[i2] = 93;
                    break;
                default:
                    iArr[i2] = i3;
                    break;
            }
            i2++;
            i = i2;
        }
        return new String(iArr, 0, a3);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static /* synthetic */ boolean U(TextRanges a, int a2) {
        Iterator it = a.iterator();
        while (it.hasNext()) {
            TextRange textRange = (TextRange) it.next();
            if (!textRange.contains(a2)) {
                if (a2 > textRange.getEndOffset()) {
                    break;
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
