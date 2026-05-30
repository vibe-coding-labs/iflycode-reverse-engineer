package com.aicode.util;

import com.aicode.content.util.file.LanguageFileExtensionDetails;
import com.aicode.inline.ide.IdeAction;
import com.intellij.openapi.editor.Editor;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections.CollectionUtils;

/* compiled from: kb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/CodeCheckUtil.class */
public class CodeCheckUtil {

    /* renamed from: final, reason: not valid java name */
    private static final /* synthetic */ String f668final = IdeAction.H("pFG\nB\u0010\\\u001bC\u0006Z\u0012D\u001cWA\u001d\u000bH\u001cZ\fD\u0003LJ\u0013+p��O\u0005Y\r]=g\u001eJ\u000eW\f^\u0002K+w\u0016I��@\u0007_J\u001e\u0005C\u0015^\u001fK\u000bA\u0010\\\u0002V[\u0011\u001dK\u000b[\u0002P@\n\rA\u0016D\u001d^\u0001A\u0012A��QF\u0003\u001aH\u001bY\u0010M\u001aHJ\u001a$~��V\u001a@\u0001@:~\u0004@\u0001E\u0005^\u0007C1u\u0012Y\u001aT��AY\u001c\u001d^��B\u0011T\u0003@\u0017G\u0002\\R\u0003��O\u0015B\u0002AA\u0001\u001fS\u001fA\u0004R\n\\\u001aY\u0006JW\u001b\u001eZ\u001dP\fN\u001aDJ\u001b5k\u000bZ\u000bW\tE*g\u001eE��J\u000e^\u000fK+}\bP\u001fF\u0003ZJ\u0013\u001cN\nQ\u0013I\u0007D\u0002O\u001bID\r��T\u0004Z\u0002PT\u001d\u0004T\u0006K\u001dG\u0001R\u001eP\rHM\f\u0003F\u001bA\u0016Z\bQW\u0007&p\u0011R\u001dQ\u000eP a\u001eZ\u001bV��A\u0015L/h\r@\u0013M\u0014QE\n��V\u0005E\u001f[\u001dA��@\fZZ\u0016\u0013G\u0001R\u0013DM\u001c\u0017T\u000bZ\u0006U\u0019Z\u0006W��SW\u0013\u000bF\u001f[\u0010A\u0003CP\u0013=p\u001cR\u0018J\u0006@5t\rE\u000eP��N\u0004V(|\u0017Y\u0006\u0001:O");

    /* renamed from: byte, reason: not valid java name */
    private static final /* synthetic */ String f671byte = LanguageFileExtensionDetails.H("S乥(鿍\\\u000f&\u001ctuNI$]QJ");

    /* renamed from: try, reason: not valid java name */
    private static final /* synthetic */ String f669try = IdeAction.H("~Dx\tEP\u0011C\u0013\u001fv\u0018\u0011\u001aNa+\u001eS2~3ye,Y\u000bｵ‸⁴Ｎば\f～\u200c\u2067\u0012D\u0012;\u0006");

    /* renamed from: float, reason: not valid java name */
    private static final /* synthetic */ String f670float = LanguageFileExtensionDetails.H("8\u007fJ");

    /* renamed from: enum, reason: not valid java name */
    private static final /* synthetic */ String f672enum = String.join(IdeAction.H("Q"), LanguageFileExtensionDetails.H("<,\u000b`\u000ez\u0010q\u000fl\u0016x\bv\u001b+Qa\u0004v\u0016f\bi�� _A<j\u0003o\u0015g\u0011W+t\u0006d\u001bf\u0012h\u0007A;|\u0005j\fm\u0013 Ro\u000f\u007f\u0012u\u0007a\rz\u0010h\u001a1]w\u0007a\u0017h\u001c*Fg\r|\bw\u0012k\rx\rj\u001d,Op\u0004q\u0015z\u0001p\u0004 VN2j\u001ap\fk\fP2n\fk\to\u0012m\u000f[9x\u0015p\u0018j\r3Pw\u0012j\u000e{\u0018i\f}\u000bh\u00108Oj\u0003\u007f\u000eh\r+Mu\u001fu\rn\u001e`\u0010p\u0015l\u0006=Wt\u0016w\u001cf\u0002p\b W_'a\u0016a\u001bc\t@+t\tj\u0006d\u0012e\u0007A1b\u001cu\ni\u0016 _v\u0002`\u001dy\u0005m\bh\u0003q\u0005.Aj\u0018n\u0016h\u001c>Qn\u0018l\u0007w\u000bk\u001et\u001cg\u0004'@i\nq\r|\u0016b\u001d=KL<{\u001ew\u001dd\u001cJ-t\u0016q\u001aj\r\u007f��E$g\fy\u0001~\u001d/Fj\u001ao\tu\u0017w\rj\ff\u00160Zy\u000bk\u001ey\b'P}\u0018a\u0016l\u0019s\u0016l\u001bj\u001f=_a\nu\u0017z\ri\u000f:_W<v\u001er\u0006l\f_8g\td\u001cj\u0002n\u001aB0}\u0015lMP\u0003"), IdeAction.H("9丩B龁6CLP\u001e9$\u0005N\u0011;\u0006"), LanguageFileExtensionDetails.H("2.4c\t:])_u:r]p\u0002\u000bgt\u001fX2Y5\u000f`3G？⁴„｢〚@Ｔ⁀\u200d^.^QJ"), IdeAction.H("t\u0015\u0006"));

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ int dffs(String a) {
        char c;
        char c2;
        Stack stack = new Stack();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Stack stack2 = new Stack();
        Stack stack3 = new Stack();
        int length = a.length() - 1;
        int i = length;
        while (length >= 0) {
            char charAt = a.charAt(i);
            if (charAt != '}') {
                if (charAt == '{' && !stack.isEmpty()) {
                    stack.pop();
                }
                c = charAt;
            } else {
                c = charAt;
                stack.push(Integer.valueOf(i));
            }
            if (c != ')') {
                if (charAt == '(' && !stack2.isEmpty()) {
                    stack2.pop();
                }
                c2 = charAt;
            } else {
                c2 = charAt;
                stack2.push(Integer.valueOf(i));
            }
            if (c2 == ']') {
                stack3.push(Integer.valueOf(i));
            } else if (charAt == '[') {
                if (!stack3.isEmpty()) {
                    stack3.pop();
                }
            } else if (charAt == '\"') {
                arrayList.add(Integer.valueOf(i));
            } else if (charAt == '\'') {
                arrayList2.add(Integer.valueOf(i));
            }
            i--;
            length = i;
        }
        if (CollectionUtils.isNotEmpty(arrayList) && arrayList.size() % 2 != 0) {
            stack.push((Integer) arrayList.get(arrayList.size() - 1));
        }
        if (CollectionUtils.isNotEmpty(arrayList2) && arrayList2.size() % 2 != 0) {
            stack.push((Integer) arrayList2.get(arrayList2.size() - 1));
        }
        Stack stack4 = new Stack();
        while (!stack.isEmpty()) {
            stack4.push((Integer) stack.pop());
        }
        while (!stack2.isEmpty()) {
            stack4.push((Integer) stack2.pop());
        }
        while (!stack3.isEmpty()) {
            stack4.push((Integer) stack3.pop());
        }
        return stack4.size();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String extractPrefix(String a) {
        ArrayList arrayList = new ArrayList();
        Matcher matcher = Pattern.compile(f672enum).matcher(a);
        while (matcher.find()) {
            matcher = matcher;
            arrayList.add(matcher.group());
        }
        String a2 = (String) arrayList.get(0);
        if (!a.startsWith(a2)) {
            return a.substring(0, a.indexOf(a2)) + a2;
        }
        return a2;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static /* synthetic */ boolean o(char c, char c2) {
        return (c == '(' && c2 == ')') || (c == '[' && c2 == ']');
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isBracketMatched(String a) {
        Stack stack = new Stack();
        char[] charArray = a.toCharArray();
        int length = charArray.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char c = charArray[i2];
            if (c != '{') {
                if (c != '}') {
                    continue;
                } else {
                    if (stack.isEmpty()) {
                        return false;
                    }
                    stack.pop();
                }
            } else {
                stack.push(Character.valueOf(c));
            }
            i2++;
            i = i2;
        }
        return stack.isEmpty();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isBracketMatched2(String a) {
        Stack stack = new Stack();
        char[] charArray = a.toCharArray();
        int length = charArray.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char c = charArray[i2];
            if (c == '{') {
                stack.push(Character.valueOf(c));
            } else if (c != '}') {
                continue;
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
            i2++;
            i = i2;
        }
        return stack.isEmpty();
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static /* synthetic */ boolean isValid(String a) {
        Stack stack = new Stack();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        AtomicInteger atomicInteger2 = new AtomicInteger(0);
        int i = 0;
        int i2 = 0;
        while (i < a.length()) {
            char charAt = a.charAt(i2);
            if (charAt != '(' && charAt != '[') {
                if (charAt == ')' || charAt == ']') {
                    if (!stack.isEmpty() && o(((Character) stack.pop()).charValue(), charAt)) {
                    }
                    return false;
                }
                if (charAt == '\'') {
                    atomicInteger.addAndGet(1);
                } else if (charAt == '\"') {
                    atomicInteger2.addAndGet(1);
                }
            } else {
                stack.push(Character.valueOf(charAt));
            }
            i2++;
            i = i2;
        }
        return stack.isEmpty() && atomicInteger.get() % 2 == 0 && atomicInteger2.get() % 2 == 0;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ void main(String[] strArr) {
        String H = LanguageFileExtensionDetails.H("fn\u0010v2o5\u0003p(\u0004w\u001aX\u0004+M\u0015iIP)\u001f\u0001k");
        ArrayList arrayList = new ArrayList();
        Matcher matcher = Pattern.compile(f672enum).matcher(H);
        while (matcher.find()) {
            matcher = matcher;
            arrayList.add(matcher.group());
        }
    }

    public static /* synthetic */ int getLineStartOffset(Editor a) {
        return a.getDocument().getLineStartOffset(getCurrentLineNumber(a));
    }

    public static /* synthetic */ int getLineEndOffset(Editor a) {
        return a.getDocument().getLineEndOffset(getCurrentLineNumber(a));
    }

    public static /* synthetic */ int getCaretOffset(Editor a) {
        return a.getCaretModel().getOffset();
    }

    public static /* synthetic */ int getCurrentLineNumber(Editor a) {
        return a.getDocument().getLineNumber(getCaretOffset(a));
    }
}
