package com.aicode.diff;

import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/* compiled from: wd */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/diff/FileService.class */
public class FileService {
    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (2 << 3) ^ (3 ^ 5);
        int i2 = (4 << 3) ^ 3;
        int i3 = ((3 ^ 5) << 3) ^ 1;
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

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static String addWriteSpace(String a, String a2) {
        boolean z;
        int i;
        String[] split = a.split(ConditionalActionConfiguration.H("w"));
        int length = split.length;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            String str = split[i3];
            if (!StringUtils.isBlank(str)) {
                if (str.startsWith(GenericUtils.H("{"))) {
                    z = false;
                }
            } else {
                i3++;
                i2 = i3;
            }
        }
        z = true;
        if (!z) {
            return a;
        }
        int i4 = 0;
        String str2 = a;
        String[] split2 = a2.split(ConditionalActionConfiguration.H("w"));
        int length2 = split2.length;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            if (i5 >= length2) {
                break;
            }
            String str3 = split2[i6];
            if (StringUtils.isBlank(str3)) {
                i6++;
                i5 = i6;
            } else if (str3.startsWith(GenericUtils.H("{"))) {
                char[] charArray = str3.toCharArray();
                int length3 = charArray.length;
                int i7 = 0;
                int i8 = 0;
                while (i7 < length3 && Character.isWhitespace(charArray[i8])) {
                    i8++;
                    i7 = i8;
                    i4++;
                }
            } else {
                i = 0;
            }
        }
        i = i4;
        if (i > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(ConditionalActionConfiguration.H("]").repeat(i4));
            str2 = sb + str2.replaceAll(GenericUtils.H("Q"), "\n" + sb);
        }
        return str2;
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static void replaceContentInFile(String a, String a2) {
        try {
            FileWriter fileWriter = new FileWriter(a2, StandardCharsets.UTF_8);
            try {
                fileWriter.write(a);
                fileWriter.flush();
                fileWriter.close();
            } catch (Throwable th) {
                try {
                    fileWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        } catch (Throwable th3) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 5, instructions: 5 */
    public static void replaceContentInFile(String a, String a2, String a3, String str, int a4, int a5) {
        String str2;
        String a6 = str;
        try {
            int a7 = a4 + 1;
            int a8 = a5 + 1;
            if (a3.contains(ConditionalActionConfiguration.H("uw"))) {
                a3 = a3.replaceAll(GenericUtils.H("^Q"), System.lineSeparator());
                str2 = a6;
            } else if (a3.contains(ConditionalActionConfiguration.H("rp"))) {
                a3 = a3.replaceAll(GenericUtils.H("YV"), System.lineSeparator());
                str2 = a6;
            } else if (a3.contains(ConditionalActionConfiguration.H("w"))) {
                a3 = a3.replaceAll(GenericUtils.H("Q"), System.lineSeparator());
                str2 = a6;
            } else {
                if (a3.contains(ConditionalActionConfiguration.H("p"))) {
                    a3 = a3.replaceAll(GenericUtils.H("V"), System.lineSeparator());
                }
                str2 = a6;
            }
            if (str2.contains(ConditionalActionConfiguration.H("uw"))) {
                a6 = a6.replaceAll(GenericUtils.H("^Q"), System.lineSeparator());
            } else if (a6.contains(ConditionalActionConfiguration.H("rp"))) {
                a6 = a6.replaceAll(GenericUtils.H("YV"), System.lineSeparator());
            } else if (a6.contains(ConditionalActionConfiguration.H("w"))) {
                a6 = a6.replaceAll(GenericUtils.H("Q"), System.lineSeparator());
            } else if (a6.contains(ConditionalActionConfiguration.H("p"))) {
                a6 = a6.replaceAll(GenericUtils.H("V"), System.lineSeparator());
            }
            try {
                FileReader fileReader = new FileReader(a, StandardCharsets.UTF_8);
                try {
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    try {
                        FileWriter fileWriter = new FileWriter(a2, StandardCharsets.UTF_8);
                        try {
                            StringBuilder sb = new StringBuilder();
                            StringBuilder sb2 = new StringBuilder();
                            int i = 0;
                            BufferedReader bufferedReader2 = bufferedReader;
                            while (true) {
                                String readLine = bufferedReader2.readLine();
                                if (readLine == null && i >= a8) {
                                    fileWriter.write(sb.toString());
                                    fileWriter.close();
                                    bufferedReader.close();
                                    fileReader.close();
                                    return;
                                }
                                i++;
                                String str3 = readLine == null ? "" : readLine;
                                if (i >= a7 && i < a8) {
                                    bufferedReader2 = bufferedReader;
                                    sb2.append(str3).append(System.lineSeparator());
                                } else if (i == a8) {
                                    sb2.append(str3).append(System.lineSeparator());
                                    if (org.apache.commons.lang3.StringUtils.isNoneBlank(new CharSequence[]{a3})) {
                                        a6 = sb2.toString().replace(a3, a6);
                                    }
                                    sb.append(a6);
                                    bufferedReader2 = bufferedReader;
                                } else {
                                    sb.append(str3).append(System.lineSeparator());
                                    bufferedReader2 = bufferedReader;
                                }
                            }
                        } catch (Throwable th) {
                            try {
                                fileWriter.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                                throw th;
                            }
                        }
                    } catch (Throwable th3) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                            throw th3;
                        }
                    }
                } catch (Throwable th5) {
                    try {
                        fileReader.close();
                    } catch (Throwable th6) {
                        th5.addSuppressed(th6);
                        throw th5;
                    }
                }
            } catch (Throwable th7) {
            }
        } catch (Exception e) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void deleteFile(File a) {
        try {
            if (!a.exists()) {
                return;
            }
            Files.deleteIfExists(a.toPath());
        } catch (Exception unused) {
        }
    }
}
