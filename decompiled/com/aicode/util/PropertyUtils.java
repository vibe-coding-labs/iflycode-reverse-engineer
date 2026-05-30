package com.aicode.util;

import com.aicode.agent.service.CodeCompleteService;
import com.aicode.template.AssertUtil;
import com.aicode.template.context.domain.Method;
import com.aicode.ui.FontKt;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.util.PropertyUtil;

/* compiled from: wa */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/PropertyUtils.class */
public class PropertyUtils {
    public static /* synthetic */ String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = ((2 ^ 5) << 4) ^ 5;
        int i2 = (1 << 3) ^ (3 ^ 5);
        int i3 = (4 << 3) ^ 2;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i4 = length2 - 1;
        int i5 = i4;
        int i6 = length;
        while (i4 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (i ^ (str.charAt(i7) ^ stringBuffer.charAt(i6)));
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

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isPropertyGetter(PsiMethod a) {
        return d(a.getContainingClass(), a) && PropertyUtil.isSimplePropertyGetter(a) && PropertyUtil.isSimpleGetter(a);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isSampleGetMethod(String a, Method a2) {
        String a3 = a2.getName();
        if (a2.isPublic() && ((a3.startsWith(FontKt.H("#6;")) || a3.startsWith(CodeCompleteService.H("A~"))) && a2.getMethodParams().isEmpty())) {
            if (inferFieldNameFromAccessor(a3) == null) {
                return false;
            }
            return a2.getMethodId().startsWith(a);
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isMainMethod(PsiMethod a) {
        if (!a.getName().equals(FontKt.H("5%:!"))) {
            return false;
        }
        if (!a.hasModifierProperty(CodeCompleteService.H("TtAjAn")) || !a.hasModifierProperty(FontKt.H(",790:,"))) {
            return false;
        }
        if (!PsiUtils.getField(CodeCompleteService.H("FoR4hJYmIlKm/TZe\u0016MWhw\u007fXh"), FontKt.H("\u000e\u000b\u001a\u000b")).equals(a.getReturnType())) {
            return false;
        }
        PsiParameterList parameterList = a.getParameterList();
        if (parameterList.getParametersCount() == 1 && parameterList.getParameters()[0].getType().equalsToText(CodeCompleteService.H("GiSa\fk`JN\"kiVhMasP"))) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static /* synthetic */ boolean C(PsiClass a, PsiMethod a2) {
        String name = a2.getName();
        if (FontKt.H("1>3��9��\t").equals(name) && a2.getParameterList().getParametersCount() == 2) {
            return true;
        }
        if ((CodeCompleteService.H("KxQDAq^WnZ").equals(name) || FontKt.H("6<7\u001b/&\u00033\"/\u001a+)\"',\u0018\u0011").equals(name)) && a2.getParameterList().getParametersCount() == 1) {
            return true;
        }
        if (a2.hasModifierProperty(CodeCompleteService.H("Pp]vfI")) && ((name.startsWith(FontKt.H("+\u0011\u001c")) || name.startsWith(CodeCompleteService.H("fY"))) && a2.getParameterList().getParametersCount() == 1)) {
            String inferFieldNameFromAccessor = inferFieldNameFromAccessor(name);
            if (inferFieldNameFromAccessor == null) {
                return false;
            }
            if (a.findFieldByName(inferFieldNameFromAccessor, false) != null) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean hasSetter(PsiMethod a, String a2) {
        return PropertyUtil.isSimplePropertySetter(a) && a2.equals(PropertyUtil.getPropertyName(a));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isPropertySetter(PsiMethod a, String a2) {
        return PropertyUtil.isSimplePropertySetter(a) && PropertyUtil.isSimpleSetter(a) && a2.equals(PropertyUtil.getPropertyName(a));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean hasGetter(PsiMethod a, String a2) {
        return PropertyUtil.isSimplePropertyGetter(a) && ("get" + AssertUtil.getName(a2)).equals(a.getName());
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isSampleSetMethod(String a, Method a2) {
        String a3 = a2.getName();
        if (!a2.isPublic() || ((!a3.startsWith(FontKt.H("76;")) && !a3.startsWith(CodeCompleteService.H("A~"))) || a2.getMethodParams().isEmpty())) {
            return false;
        }
        if (inferFieldNameFromAccessor(a3) == null) {
            return false;
        }
        return a2.getMethodId().startsWith(a);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static /* synthetic */ boolean d(PsiClass a, PsiMethod a2) {
        String name = a2.getName();
        if (!FontKt.H("%>3��9'.").equals(name) || a2.getParameterList().getParametersCount() != 1) {
            if ((CodeCompleteService.H("_xQDAq^WI}").equals(name) || FontKt.H("\"<7\u001b/&\u00033\"/\u001a+)\"',?6").equals(name)) && a2.getParameterList().isEmpty()) {
                return true;
            }
            if (a2.hasModifierProperty(CodeCompleteService.H("Pp]vAn")) && ((name.startsWith(FontKt.H("?6;")) || name.startsWith(CodeCompleteService.H("A~"))) && a2.getParameterList().getParametersCount() == 0)) {
                String inferFieldNameFromAccessor = inferFieldNameFromAccessor(name);
                if (inferFieldNameFromAccessor == null) {
                    return false;
                }
                if (a.findFieldByName(inferFieldNameFromAccessor, false) != null) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isPropertySetter(PsiMethod a) {
        return C(a.getContainingClass(), a) || (PropertyUtil.isSimplePropertySetter(a) && PropertyUtil.isSimpleSetter(a));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String inferFieldNameFromAccessor(String a) {
        if (a == null || a.length() < 4) {
            return null;
        }
        if (a.startsWith(FontKt.H("# -")) || a.startsWith(CodeCompleteService.H("u[o"))) {
            return Character.toLowerCase(a.charAt(3)) + a.substring(4);
        }
        if (a.startsWith(FontKt.H(",*"))) {
            return Character.toLowerCase(a.charAt(2)) + a.substring(3);
        }
        return null;
    }
}
