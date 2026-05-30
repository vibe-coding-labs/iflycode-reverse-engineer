package com.aicode.content.util.file;

import com.aicode.diff.FileInfo;
import com.aicode.util.Application;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/* compiled from: zb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/content/util/file/FileUtils.class */
public class FileUtils {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f204enum = Logger.getInstance(FileUtils.class);

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m117enum(int a) {
        throw new IllegalArgumentException(String.format(FileInfo.H("5o\u0018c\u0013r[(Xw\u001boU\\!i\nY��p\u0003&E=\u001cf\u0002c\u0006~\u0005>\u0012y\u000e3Rt\u0011>[d\u001by.\u0014\u001fn\u0004j^y!SOd\u0017;\u0019k\u0004m"), Application.H("lne{|b"), FileInfo.H("\rh\u0002)\u0013r\u0014qQ9Rw\u001du\u0003{\u0010c\u001a))]\u001e4\u0011w\u0012raa\u0006j\u0017N\u0003w\u0004r"), Application.H("h`rpR}cfxJf\u007fu")));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static String getResourceContent(String a) {
        try {
            InputStream inputStream = (InputStream) Objects.requireNonNull(FileUtils.class.getResourceAsStream(a));
            try {
                String str = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                if (inputStream != null) {
                    inputStream.close();
                }
                return str;
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            throw new RuntimeException(Application.H("Zkg,!C\u0005}e,}`gQ\u0016frzey}pu"), e);
        }
    }

    public static VirtualFile getEditorFile(@NotNull Editor editor) {
        if (editor == null) {
            m117enum(0);
        }
        return FileDocumentManager.getInstance().getFile(editor.getDocument());
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void tryCreateDirectory(String a) {
        try {
            if (FileUtil.exists(a) || FileUtil.createDirectory(Path.of(a, new String[0]).toFile())) {
            } else {
                throw new IOException("Failed to create directory: " + a);
            }
        } catch (IOException e) {
            throw new RuntimeException(Application.H("Okec`bn9I\u0005jxinqc\u0015R}elix`ai"), e);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String getFileExtension(String a) {
        Matcher matcher = Pattern.compile(FileInfo.H(")EYCC%")).matcher(a);
        if (!matcher.find()) {
            return "";
        }
        return matcher.group();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static boolean isUtf8File(String a) {
        try {
            BufferedReader newBufferedReader = Files.newBufferedReader(Paths.get(a, new String[0]));
            try {
                if (newBufferedReader.read() >= 0) {
                    newBufferedReader.transferTo(Writer.nullWriter());
                }
                if (newBufferedReader != null) {
                    newBufferedReader.close();
                }
                return true;
            } catch (Throwable th) {
                if (newBufferedReader != null) {
                    try {
                        newBufferedReader.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                }
                throw th;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static Optional<Map.Entry<String, String>> Rd(List<LanguageFileExtensionDetails> list, String a) {
        return list.stream().filter(a2 -> {
            return a.equalsIgnoreCase(a2.getName());
        }).findFirst().map(a3 -> {
            return Map.entry(a3.getName(), a3.getExtensions().get(0));
        });
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static File createFile(String a, String a2, String a3) {
        try {
            tryCreateDirectory(a);
            return Files.writeString(Path.of(a, a2), a3, new OpenOption[]{StandardOpenOption.CREATE}).toFile();
        } catch (IOException e) {
            throw new RuntimeException(FileInfo.H("QT51Q\u0016;\u0003q^t<B\u000er\u0017;\u0011w\u0004d"), e);
        }
    }
}
