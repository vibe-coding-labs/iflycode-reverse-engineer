package com.aicode.util;

import cn.hutool.core.util.StrUtil;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.diff.FileInfo;

/* compiled from: oa */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/StringUtils.class */
public class StringUtils extends StrUtil {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String remove(String a, char c) {
        if (!isEmpty(a) && a.indexOf(c) != -1) {
            char[] charArray = a.toCharArray();
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (i2 < charArray.length) {
                if (charArray[i3] != c) {
                    int i4 = i;
                    i++;
                    charArray[i4] = charArray[i3];
                }
                i3++;
                i2 = i3;
            }
            return new String(charArray, 0, i);
        }
        return a;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String extractClassName(String a) {
        return a == null ? FileExtensionLanguageDetails.H("}tAqpj") : deCapitalizeFirstLetter(ClassNameUtils.extractClassName(a));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean hasLine(String a, String a2) {
        String[] split = a.split(FileInfo.H("4o"));
        int length = split.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            if (!split[i2].matches("\\s*" + a2 + "\\s*")) {
                i2++;
                i = i2;
            } else {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ String deCapitalizeFirstLetter(String a) {
        return a.substring(0, 1).toLowerCase() + a.substring(1);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String camelCaseToWords(String a) {
        StringBuilder sb = new StringBuilder();
        String[] split = a.split(FileInfo.H("Z$D0\\C\u0003M?:o\u0001G.G9O@63o\u0001a]G9U!,2]#AN\u000f\n5[)zZd5("));
        int length = split.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            StringBuilder append = sb.append(split[i2]);
            i2++;
            append.append(' ');
            i = i2;
        }
        return sb.toString().trim();
    }

    public static /* synthetic */ String capitalizeFirstLetter(String a) {
        return a.substring(0, 1).toUpperCase() + a.substring(1);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String removeSuffix(String a, String a2) {
        if (!a.endsWith(a2)) {
            return a;
        }
        return a.substring(0, a.length() - a2.length());
    }
}
