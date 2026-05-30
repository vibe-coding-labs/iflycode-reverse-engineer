package com.aicode.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ha */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/Maps.class */
public final class Maps {
    public static /* synthetic */ String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).insert(0, stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = ((2 ^ 5) << 3) ^ 5;
        int i2 = (5 << 3) ^ (3 ^ 5);
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i3 = length2 - 1;
        int i4 = i3;
        int i5 = length;
        while (i3 >= 0) {
            int i6 = i4;
            int i7 = i4 - 1;
            cArr[i6] = (char) (i ^ (str.charAt(i6) ^ stringBuffer.charAt(i5)));
            if (i7 < 0) {
                break;
            }
            char charAt = (char) (1 ^ (str.charAt(i7) ^ stringBuffer.charAt(i5)));
            i4 = i7 - 1;
            i5--;
            cArr[i7] = charAt;
            if (i5 < 0) {
                i5 = length;
            }
        }
        return new String(cArr);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @SafeVarargs
    public static /* synthetic */ <K, V> Map<K, V> merge(Map<K, ? extends V>... mapArr) {
        if (mapArr != null) {
            if (mapArr.length == 0) {
                return Collections.emptyMap();
            }
            if (mapArr.length == 1) {
                return Map.copyOf(mapArr[0]);
            }
            HashMap hashMap = null;
            int length = mapArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                Map<K, ? extends V> map = mapArr[i2];
                if (!map.isEmpty()) {
                    if (hashMap == null) {
                        hashMap = new HashMap();
                    }
                    hashMap.putAll(map);
                }
                i2++;
                i = i2;
            }
            return hashMap == null ? Collections.emptyMap() : Collections.unmodifiableMap(hashMap);
        }
        throw new RuntimeException();
    }
}
