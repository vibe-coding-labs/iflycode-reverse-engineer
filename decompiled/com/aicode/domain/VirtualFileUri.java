package com.aicode.domain;

import com.aicode.util.PsiUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.VirtualFileSystem;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import org.jetbrains.annotations.NotNull;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/domain/VirtualFileUri.class */
public final class VirtualFileUri {
    static Logger LOG = Logger.getInstance(VirtualFileUri.class);

    @NotNull
    private final String uri;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 11:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 4:
            case 5:
            case 8:
            case 9:
            case 10:
            case 12:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 11:
            default:
                i2 = 3;
                break;
            case 4:
            case 5:
            case 8:
            case 9:
            case 10:
            case 12:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            default:
                objArr[0] = "file";
                break;
            case 1:
            case 6:
                objArr[0] = "fileSystem";
                break;
            case 2:
            case 3:
                objArr[0] = "path";
                break;
            case 4:
            case 5:
            case 8:
            case 9:
            case 10:
            case 12:
                objArr[0] = "com/aicode/domain/VirtualFileUri";
                break;
            case 7:
                objArr[0] = "url";
                break;
            case 11:
                objArr[0] = "uri";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 11:
            default:
                objArr[1] = "com/aicode/domain/VirtualFileUri";
                break;
            case 4:
            case 5:
                objArr[1] = "processPath";
                break;
            case 8:
            case 9:
            case 10:
                objArr[1] = "asPrefixedUri";
                break;
            case 12:
                objArr[1] = "getUri";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            default:
                objArr[2] = "from";
                break;
            case 3:
                objArr[2] = "processPath";
                break;
            case 4:
            case 5:
            case 8:
            case 9:
            case 10:
            case 12:
                break;
            case 6:
                objArr[2] = "isNeedsPathPrefix";
                break;
            case 7:
                objArr[2] = "asPrefixedUri";
                break;
            case 11:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 11:
            default:
                throw new IllegalArgumentException(format);
            case 4:
            case 5:
            case 8:
            case 9:
            case 10:
            case 12:
                throw new IllegalStateException(format);
        }
    }

    @NotNull
    public static VirtualFileUri from(@NotNull VirtualFile file) {
        String url;
        if (file == null) {
            $$$reportNull$$$0(0);
        }
        try {
            String prefix = isNeedsPathPrefix(file.getFileSystem()) ? "/" : "";
            url = new URI(file.getFileSystem().getProtocol(), "", prefix + prefix, (String) null).toString();
        } catch (URISyntaxException e) {
            url = asPrefixedUri(file.getUrl());
            LOG.warn("Unable to parse as compliant URI, using fallback: " + url);
        }
        return new VirtualFileUri(url);
    }

    @NotNull
    public static VirtualFileUri from(@NotNull VirtualFileSystem fileSystem, @NotNull String path) {
        if (fileSystem == null) {
            $$$reportNull$$$0(1);
        }
        if (path == null) {
            $$$reportNull$$$0(2);
        }
        String prefix = (!isNeedsPathPrefix(fileSystem) || path.startsWith("/")) ? "" : "/";
        return new VirtualFileUri(VirtualFileManager.constructUrl(fileSystem.getProtocol(), prefix + prefix));
    }

    @NotNull
    private static String processPath(@NotNull String path) {
        if (path == null) {
            $$$reportNull$$$0(3);
        }
        if (SystemInfo.isWindows && path.startsWith("//")) {
            String replace = path.replace('/', '\\').replace("$", "%24");
            if (replace == null) {
                $$$reportNull$$$0(4);
            }
            return replace;
        }
        if (path == null) {
            $$$reportNull$$$0(5);
        }
        return path;
    }

    private static boolean isNeedsPathPrefix(@NotNull VirtualFileSystem fileSystem) {
        if (fileSystem == null) {
            $$$reportNull$$$0(6);
        }
        return SystemInfo.isWindows && (fileSystem instanceof LocalFileSystem) && !PsiUtils.instanceOf(fileSystem, "com.intellij.openapi.vfs.ex.temp.TempFileSystem");
    }

    @NotNull
    static String asPrefixedUri(@NotNull String url) {
        if (url == null) {
            $$$reportNull$$$0(7);
        }
        if (SystemInfo.isWindows) {
            if (url.startsWith("file:////")) {
                String str = "file:///" + url.substring("file://".length()).replace("/", "%5C").replace("$", "%24");
                if (str == null) {
                    $$$reportNull$$$0(8);
                }
                return str;
            }
            if (url.startsWith("file://") && !url.startsWith("file:///")) {
                String str2 = "file:///" + url.substring("file://".length());
                if (str2 == null) {
                    $$$reportNull$$$0(9);
                }
                return str2;
            }
        }
        if (url == null) {
            $$$reportNull$$$0(10);
        }
        return url;
    }

    public VirtualFileUri(@NotNull String uri) {
        if (uri == null) {
            $$$reportNull$$$0(11);
        }
        this.uri = uri;
    }

    @NotNull
    public String getUri() {
        String str = this.uri;
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        return str;
    }

    public String toString() {
        return "VirtualFileUri(uri=" + getUri() + ")";
    }

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/domain/VirtualFileUri$TypeAdapter.class */
    public static final class TypeAdapter implements JsonSerializer<VirtualFileUri> {
        public JsonElement serialize(VirtualFileUri file, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(file.uri);
        }
    }
}
