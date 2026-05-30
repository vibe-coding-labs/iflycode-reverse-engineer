package com.aicode.util;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import java.net.URI;
import java.net.URISyntaxException;
import org.jetbrains.annotations.NotNull;

/* compiled from: za */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/VirtualFileUtils.class */
public class VirtualFileUtils {

    /* renamed from: enum, reason: not valid java name */
    private static final /* synthetic */ Logger f749enum = Logger.getInstance(VirtualFileUtils.class);

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m460enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 2:
            case 3:
            case 6:
            default:
                H = PropertyUtils.H("\fh(ma>-`kz.dmZ\b~3^y7; &`9}#|x>\u0006\u0003l9# vn1 fg`<\u0017\u0013&i=mg~\u0018Tvc.< l=j");
                i = a;
                break;
            case 1:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
                do {
                } while (0 != 0);
                H = PositionUtil.H("2Le\u000e{0B2\b5L-U\"T`?\u0019\u001b`C`T<z\r\b6Z1\u00102J+L;\\bG,]-");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 2:
            case 3:
            case 6:
            default:
                i2 = 3;
                break;
            case 1:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = PropertyUtils.H("f\u001eR\"t*p\bp=c");
                i3 = a;
                break;
            case 1:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
                do {
                } while (0 != 0);
                objArr[0] = PositionUtil.H("J6PbQ)y\u0005Q \u001f5M eV~1G1E!C\u0019P%W\u0017]0]2");
                i3 = a;
                break;
            case 2:
                objArr[0] = PropertyUtils.H("\u0011I:d\u0018e=m4k");
                i3 = a;
                break;
            case 3:
                objArr[0] = PositionUtil.H("Y8E)");
                i3 = a;
                break;
            case 6:
                objArr[0] = PropertyUtils.H("l#j");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 2:
            case 3:
            case 6:
            default:
                objArr[1] = PositionUtil.H("J6PbQ)y\u0005Q \u001f5M eV~1G1E!C\u0019P%W\u0017]0]2");
                i4 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = PropertyUtils.H("(k>k");
                i4 = a;
                break;
            case 4:
            case 5:
                objArr[1] = PositionUtil.H("0]0Z,A1y8E)");
                i4 = a;
                break;
            case 7:
            case 8:
            case 9:
                objArr[1] = PropertyUtils.H("x4@\u0005E0h3y*L#o");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = PositionUtil.H("O+^,");
                break;
            case 1:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = PropertyUtils.H("Z8R+|#c'A\"i\u001bn+\u007f8~");
                break;
            case 3:
                objArr[2] = PositionUtil.H("0]0Z,A1y8E)");
                break;
            case 6:
                objArr[2] = PropertyUtils.H("x4@\u0005E0h3y*L#o");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 2:
            case 3:
            case 6:
            default:
                throw new IllegalArgumentException(format);
            case 1:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    /* renamed from: null, reason: not valid java name */
    public static /* synthetic */ String m458null(@NotNull String url) {
        if (url == null) {
            m460enum(6);
        }
        if (SystemInfo.isWindows) {
            if (url.startsWith(PositionUtil.H("9[.Lc\u001fo\u001fo"))) {
                String str = "file:///" + url.substring(PropertyUtils.H("g\"p+#~)").length()).replace(PositionUtil.H("o"), PropertyUtils.H("<dE")).replace(PositionUtil.H("d"), PropertyUtils.H("<c2"));
                if (str == null) {
                    m460enum(7);
                }
                return str;
            }
            if (url.startsWith(PositionUtil.H("$@5Uz\u001fo")) && !url.startsWith(PropertyUtils.H("0h'yt6~)"))) {
                String str2 = "file:///" + url.substring(PositionUtil.H("$@5Uz\u001fo").length());
                if (str2 == null) {
                    m460enum(8);
                }
                return str2;
            }
        }
        if (url == null) {
            m460enum(9);
        }
        return url;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    /* renamed from: void, reason: not valid java name */
    private static /* synthetic */ String m456void(@NotNull String path) {
        if (path == null) {
            m460enum(3);
        }
        if (!SystemInfo.isWindows || !path.startsWith(PropertyUtils.H("~)"))) {
            if (path == null) {
                m460enum(5);
            }
            return path;
        }
        String replace = path.replace('/', '\\').replace(PositionUtil.H("l"), PropertyUtils.H("<c2"));
        if (replace == null) {
            m460enum(4);
        }
        return replace;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    public static /* synthetic */ String from(@NotNull VirtualFile virtualFile) {
        String str;
        if (virtualFile == null) {
            m460enum(0);
        }
        try {
            str = new URI(virtualFile.getFileSystem().getProtocol(), "", (m459const(virtualFile.getFileSystem()) ? PropertyUtils.H(")") : "") + m456void(virtualFile.getPath()), null).toString();
        } catch (URISyntaxException e) {
            String m458null = m458null(virtualFile.getUrl());
            f749enum.warn("Unable to parse as compliant URI, using fallback: " + m458null);
            str = m458null;
        }
        if (str == null) {
            m460enum(1);
        }
        return str;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* renamed from: const, reason: not valid java name */
    private static /* synthetic */ boolean m459const(@NotNull VirtualFileSystem fileSystem) {
        if (fileSystem == null) {
            m460enum(2);
        }
        return SystemInfo.isWindows && (fileSystem instanceof LocalFileSystem) && !PsiUtils.instanceOf(fileSystem, PositionUtil.H("&_-\\kd\u000eP)B7BvF)X#Q0sDC#Cn\\1'\rM5Ekd%B/y&_&a;\\+M5"));
    }
}
