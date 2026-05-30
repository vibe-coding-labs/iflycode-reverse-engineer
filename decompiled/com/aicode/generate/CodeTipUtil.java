package com.aicode.generate;

import Q.q;
import com.aicode.domain.LineInfo;
import com.aicode.enums.CodeTipType;
import com.aicode.language.CommonLanguageSupport;
import com.aicode.service.CodeEditorInlay;
import com.aicode.service.CodeInlayList;
import com.aicode.service.CodeTip;
import com.aicode.service.EditorRequestService;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.JComponentKt;
import com.aicode.util.Maps;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: oh */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/generate/CodeTipUtil.class */
public class CodeTipUtil {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f297enum = Logger.getInstance(CodeTipUtil.class);

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m158enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            default:
                H = JComponentKt.H("\f\u0019\"\u0016\"\f-\u0011h\b&\u001d\"d\u0005\u0002=!7\b%O.\u0019?\n5\u001b=\np\u0004Hl0Bh\u0001/O]-kF/Z\u0014*+\ne\r/\u0012i\r'D7\n-\u000b");
                i = a;
                break;
            case 3:
            case 4:
            case 6:
            case 10:
                do {
                } while (0 != 0);
                H = Maps.H("$\u0016\u001c;.)\u0019%D5Jg*\u0011\nr@*J}&I\u0005!\u00029t\u0006\u001a=H&\b%\u0011*\u0001s\u001a=��<");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            default:
                i2 = 3;
                break;
            case 3:
            case 4:
            case 6:
            case 10:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 5:
            case 7:
            case 8:
            case 11:
            default:
                objArr[0] = JComponentKt.H("\u001d'\u0015,\u001a2\u0013");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = Maps.H("5!6&\f1.>\t(\u00036��!\u0003>");
                i3 = a;
                break;
            case 2:
            case 18:
                objArr[0] = JComponentKt.H("%\u0006,\u0001\u0010\u0011'\b");
                i3 = a;
                break;
            case 3:
            case 4:
            case 6:
            case 10:
                objArr[0] = Maps.H("p-\u0013A3\f:\u000b<0F\u000f1\u001f(&\t\u0001,G\u0017\u00025\u0001\f\u0006#!<\u0005<");
                i3 = a;
                break;
            case 9:
            case 12:
            case 13:
                objArr[0] = JComponentKt.H("\b0\u0011$\u0014");
                i3 = a;
                break;
            case 14:
            case 16:
                objArr[0] = Maps.H(",\f=\u0019>\u0016\u001b��=��-\u0002$");
                i3 = a;
                break;
            case 15:
                objArr[0] = JComponentKt.H("\u000f)\b,\u001c\u0011\u0010+\u0016/��");
                i3 = a;
                break;
            case 17:
                objArr[0] = Maps.H("\u000b\u001a$\u00188\b%\r7\u0001\u001f\u001d&\t#");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            default:
                objArr[1] = JComponentKt.H("G��$l\u0004!\r&\u000b\u001dq\"\u00062\u001f\u000b>,\u001bj /\u0002,;+\u0014\f\u000b(\u000b");
                i4 = a;
                break;
            case 3:
            case 4:
                do {
                } while (0 != 0);
                objArr[1] = Maps.H("\n\u001a1\u001091:\u00109\u00045\u000e4\t=\u0001' -\u0014$");
                i4 = a;
                break;
            case 6:
                objArr[1] = JComponentKt.H("\u001b, \u0002(\u001f+:(\u0012$��%\u000b,\u0001668\u0011&\u0002");
                i4 = a;
                break;
            case 10:
                objArr[1] = Maps.H("7\u0003(5\u001c\u0010\f\f=\u0019>\u0016\u0011\u0001?\u00151\u001fb");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            default:
                objArr[2] = JComponentKt.H("��.\u001f\u0018+=;!\n4\t;,-��<+(\u0017");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = Maps.H("\n\u001a1\u001091:\u00109\u00045\u000e4\t=\u0001' -\u0014$");
                break;
            case 3:
            case 4:
            case 6:
            case 10:
                break;
            case 5:
                objArr[2] = JComponentKt.H("\u001b, \u0002(\u001f+:(\u0012$��%\u000b,\u0001668\u0011&\u0002");
                break;
            case 7:
                objArr[2] = Maps.H("=\u0002\u001f1\u0018\u0019(\u000b1!8\n=<&\u0012.\u0005(");
                break;
            case 8:
            case 9:
                objArr[2] = JComponentKt.H("��.\u001f\u0018+=;!\n4\t;&,\b8\u00062U");
                break;
            case 11:
            case 12:
                objArr[2] = Maps.H("\u0012?1\t\u0001,-0\u0004%\u000b*&=\u0018)\u0015#");
                break;
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = JComponentKt.H("'\u0017'\u001e\u0006\u0019\u001d,)\u0002,\n\u00101?*7\u0002)\n \u0001%(0\u0011$\u0014");
                break;
            case 17:
            case 18:
                objArr[2] = Maps.H("5\f\u001f<\u001b :9\r,\n \u0004)\u000f5");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            default:
                throw new IllegalArgumentException(format);
            case 3:
            case 4:
            case 6:
            case 10:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    private static List<CodeEditorInlay> Cf(@NotNull EditorRequestService request, @NotNull List<String> list) {
        ArrayList arrayList;
        if (request == null) {
            m158enum(8);
        }
        if (list == null) {
            m158enum(9);
        }
        ArrayList arrayList2 = new ArrayList();
        int offset = request.getOffset();
        if (list.size() <= 1 || !request.getLineInfo().isBlankLine() || !list.get(0).isEmpty()) {
            arrayList2.add(new q(CodeTipType.Inline, offset, list));
            if (list.size() > 1) {
                arrayList2.add(new q(CodeTipType.Block, offset, list.subList(1, list.size())));
            }
            arrayList = arrayList2;
        } else {
            arrayList = arrayList2;
            arrayList2.add(new q(CodeTipType.Block, offset, list.subList(1, list.size())));
        }
        if (arrayList == null) {
            m158enum(10);
        }
        return arrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String dropOverlappingTrailingLines(@NotNull String linesString, @NotNull String editorContent, int offset) {
        if (linesString == null) {
            m158enum(15);
        }
        if (editorContent == null) {
            m158enum(16);
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(AICodeStringUtil.splitLines(linesString)));
        return oe(arrayList, editorContent, offset) ? StringUtil.join(arrayList, Maps.H("D")) : linesString;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    private static TextRange gD(@NotNull EditorRequestService request, boolean z) {
        if (request == null) {
            m158enum(5);
        }
        LineInfo lineInfo = request.getLineInfo();
        TextRange create = TextRange.create(request.getOffset(), NE(request) ? lineInfo.getLineEndOffset() - AICodeStringUtil.trailingWhitespaceLength(lineInfo.getLineSuffix()) : request.getOffset());
        if (create == null) {
            m158enum(6);
        }
        return create;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean oe(@NotNull List<String> list, @NotNull String editorContent, int offset) {
        if (list == null) {
            m158enum(13);
        }
        if (editorContent == null) {
            m158enum(14);
        }
        if (offset < editorContent.length() && editorContent.charAt(offset) == '\n') {
            offset++;
        }
        if (offset >= editorContent.length()) {
            return false;
        }
        List lines = AICodeStringUtil.getNextLines(editorContent, offset, list.size());
        int findOverlappingLines = AICodeStringUtil.findOverlappingLines(list, lines);
        int i = 0;
        int offset2 = 0;
        while (i < findOverlappingLines) {
            offset2++;
            list.remove(list.size() - 1);
            i = offset2;
        }
        return findOverlappingLines >= 1;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static List<CodeEditorInlay> tD(@NotNull EditorRequestService request, @NotNull List<String> list) {
        boolean matches;
        if (request == null) {
            m158enum(11);
        }
        if (list == null) {
            m158enum(12);
        }
        ArrayList arrayList = new ArrayList();
        int offset = request.getOffset();
        if (list.size() <= 1 || !request.getLineInfo().isBlankLine() || !list.get(0).isEmpty()) {
            String lineSuffix = request.getLineInfo().getLineSuffix();
            String str = lineSuffix;
            matches = Pattern.compile(Maps.H("\u00174}1\"_\u0015>]")).matcher(lineSuffix).matches();
            if (matches) {
                str = str.replaceAll(JComponentKt.H("n"), "");
            }
            List<Pair<Integer, String>> matchSuffixSection = AICodeStringUtil.matchSuffixSection(str, list.get(0));
            if (matchSuffixSection != null && !matchSuffixSection.isEmpty()) {
                Iterator<Pair<Integer, String>> it = matchSuffixSection.iterator();
                while (it.hasNext()) {
                    Pair<Integer, String> next = it.next();
                    arrayList.add(new q(CodeTipType.Inline, offset + ((Integer) next.getFirst()).intValue(), List.of((String) next.second)));
                    it = it;
                }
            }
            if (list.size() > 1) {
                arrayList.add(new q(CodeTipType.Block, offset, list.subList(1, list.size())));
            }
            return arrayList;
        }
        arrayList.add(new q(CodeTipType.Block, offset, list.subList(1, list.size())));
        return arrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String TrimEndSpaceTab(String a) {
        int length = a.length() - 1;
        int i = length;
        while (length >= 0 && (Character.isSpaceChar(a.charAt(i)) || '\t' == a.charAt(i) || '\n' == a.charAt(i))) {
            i--;
            length = i;
        }
        return a.substring(0, i + 1);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean TD(@NotNull List<String> list, @NotNull LineInfo lineInfo) {
        List<String> list2;
        if (list == null) {
            m158enum(17);
        }
        if (lineInfo == null) {
            m158enum(18);
        }
        String whitespaceBeforeCursor = lineInfo.getWhitespaceBeforeCursor();
        if (list.isEmpty() || whitespaceBeforeCursor.isEmpty()) {
            return false;
        }
        boolean isBlankLine = lineInfo.isBlankLine();
        boolean z = false;
        String str = list.get(0);
        String str2 = str;
        if (!str.startsWith(whitespaceBeforeCursor)) {
            if (isBlankLine) {
                str2 = str;
                z = (str.isEmpty() || AICodeStringUtil.leadingWhitespace(str).startsWith(whitespaceBeforeCursor)) ? false : true;
            }
            list2 = list;
        } else {
            str2 = str;
            z = isBlankLine;
            list2 = list;
        }
        list2.set(0, str2);
        return z;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String trimStartSpaceTab(String a) {
        int i = 0;
        int i2 = 0;
        while (i < a.length() && (Character.isSpaceChar(a.charAt(i2)) || '\t' == a.charAt(i2) || '\n' == a.charAt(i2))) {
            i2++;
            i = i2;
        }
        return a.substring(i2, a.length());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static boolean NE(@NotNull EditorRequestService request) {
        if (request == null) {
            m158enum(7);
        }
        String lineSuffix = request.getLineInfo().getLineSuffix();
        return AICodeStringUtil.isSpacesOrTabs(lineSuffix, false) || CommonLanguageSupport.isValidMiddleOfTheLinePosition(lineSuffix);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    private static String Jf(@NotNull LineInfo lineInfo, List<String> list) {
        if (lineInfo == null) {
            m158enum(2);
        }
        String join = StringUtil.join(list, JComponentKt.H("f"));
        if (!lineInfo.isBlankLine()) {
            String whitespaceBeforeCursor = lineInfo.getWhitespaceBeforeCursor();
            if (join.startsWith(whitespaceBeforeCursor)) {
                String substring = join.substring(whitespaceBeforeCursor.length());
                if (substring == null) {
                    m158enum(3);
                }
                return substring;
            }
        }
        if (join == null) {
            m158enum(4);
        }
        return join;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Nullable
    public static CodeInlayList createEditorCodeTip(@NotNull EditorRequestService request, @NotNull CodeTip aICodeCompletion, boolean z) {
        if (request == null) {
            m158enum(0);
        }
        if (aICodeCompletion == null) {
            m158enum(1);
        }
        ArrayList arrayList = new ArrayList(aICodeCompletion.getTip());
        if (arrayList.isEmpty() || (arrayList.size() == 1 && (((String) arrayList.get(0)).isEmpty() || ((String) arrayList.get(0)).equals(Maps.H("G"))))) {
            f297enum.debug("ignoring empty completion: " + request);
            return null;
        }
        oe(arrayList, request.getDocumentContent(), request.getOffset());
        if (!arrayList.isEmpty()) {
            String Jf = Jf(request.getLineInfo(), arrayList);
            boolean z2 = z && TD(arrayList, request.getLineInfo());
            if (arrayList.isEmpty()) {
                return null;
            }
            return new DefaultInlayList(aICodeCompletion, gD(request, z2), Jf, tD(request, arrayList));
        }
        return null;
    }
}
