package com.aicode.util;

import cn.hutool.core.io.IoUtil;
import com.aicode.agent.service.CodeCompleteService;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.LoggerRt;
import com.intellij.util.lang.UrlClassLoader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: zb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/FileUtils.class */
public class FileUtils {

    /* renamed from: byte, reason: not valid java name */
    private static final /* synthetic */ Logger f677byte = LoggerFactory.getLogger(FileUtils.class);

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ String f678enum = CodeCompleteService.H("dGeKkE<\u001alKlZdGgIq_h\\&\u001f");
    public static /* synthetic */ String AGENT_DIR = AICodeUtils.H("idhrf");
    public static /* synthetic */ String BIN_DIR = CodeCompleteService.H("\u007f\u001b9");
    public static /* synthetic */ String WASM_DIR = AICodeUtils.H("\u007fb~qa");
    public static /* synthetic */ String FILE_TEMPLATES = CodeCompleteService.H("lIi@TGjUlYi\u0017$");
    public static /* synthetic */ String NODE_EXE_FILENAME = AICodeUtils.H("r{bm{#va");
    public static /* synthetic */ String AGENT_EXE_SUFFIX = CodeCompleteService.H("\u0016x\n2");
    public static /* synthetic */ String X86_64_WINDOWS7_NODE = AICodeUtils.H("9w\u001fx0<\\zcj^[lf1Wmbxw");
    public static /* synthetic */ String X86_64_WINDOWS_NODE = CodeCompleteService.H("}\u0007,/c\u001bUWlKdMpV_Vr\u00162");
    public static /* synthetic */ String X86_64_LINUX_NODE = AICodeUtils.H("_>>\\;>[V]u`~Wmbxw");
    public static /* synthetic */ String X86_64_DARWIN_NODE = CodeCompleteService.H("G\"F\n\u0019>\u007faDrUnK_Vr\u00162");
    public static /* synthetic */ String X86_64_DARWIN_ARM_NODE = AICodeUtils.H("{5w\u0010\u001f\u0013Ylb\u007f}mTkzgkWmbxw");
    public static /* synthetic */ String CONFIG_JSON = CodeCompleteService.H("fJnDnB.Rn\u001d9");

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m403enum(int a) {
        throw new IllegalArgumentException(String.format(AICodeUtils.H("B\u007f{ggao{:rm~,BNavB4#b vi~cmk\u007f`x$fji3;z`(&~ojZ\u0007k}py*jU@;wc(mxp~"), CodeCompleteService.H("iSh"), AICodeUtils.H("vie,l(,FCc'vych\u0015rryc]wdpa"), CodeCompleteService.H("LnT`Mty~MsV\u007fNh")));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String getFileExtension(String a) {
        Matcher matcher = Pattern.compile(CodeCompleteService.H("~^\r[\t#")).matcher(a);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    public static /* synthetic */ String getPath(String a) {
        return (String) Optional.ofNullable(FileUtils.class.getResource(a)).map((v0) -> {
            return v0.getPath();
        }).orElse("");
    }

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    public static /* synthetic */ void copyFile(String a, String a2) {
        File file = new File(a);
        File file2 = new File(a2);
        Charset forName = Charset.forName(detectCharset(file2));
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), forName));
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), forName));
                boolean z = true;
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            if (!z) {
                                bufferedWriter.newLine();
                            }
                            bufferedWriter.write(readLine);
                            z = false;
                        } else {
                            bufferedWriter.close();
                            bufferedReader.close();
                            return;
                        }
                    } catch (Throwable th) {
                        try {
                            bufferedWriter.close();
                            throw th;
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                            throw th;
                        }
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
        } catch (IOException e) {
            f677byte.info(CodeCompleteService.H("fdVvJn\u0003rS9V\u007fzV\u0005fIiL\""), e);
        }
    }

    public static /* synthetic */ File getFileOfPluginPath(String a) {
        return new File(PathManager.getPluginsPath() + File.separator + a);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String getResource(String a) {
        try {
            InputStream inputStream = (InputStream) Objects.requireNonNull(getResourceAsStream(a));
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
            throw new RuntimeException(AICodeUtils.H("]ml##L\u0007rg#\u007foe^\u0014ipugv\u007f\u007fw"), e);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ void copyFile(File a, String a2) {
        InputStream resourceAsStream = getResourceAsStream(a2);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(a, false);
            IoUtil.copy(resourceAsStream, fileOutputStream, 8192);
            resourceAsStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ File getAndCreateFileOfPluginPath(String a) {
        File fileOfPluginPath = getFileOfPluginPath(a);
        if (!fileOfPluginPath.exists()) {
            try {
                fileOfPluginPath.createNewFile();
                return fileOfPluginPath;
            } catch (IOException unused) {
            }
        }
        return fileOfPluginPath;
    }

    @Nullable
    public static /* synthetic */ InputStream getResourceAsStream(String a) {
        return FileUtils.class.getResourceAsStream(a);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ void deleteFileOfPluginPath(String a) {
        File fileOfPluginPath = getFileOfPluginPath(a);
        try {
            if (!fileOfPluginPath.exists()) {
                return;
            }
            fileOfPluginPath.delete();
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ void copyDir(String a, String a2) {
        File file = new File(a);
        if (file.exists()) {
            try {
                cn.hutool.core.io.FileUtil.copyFilesFromDir(file, new File(a2), true);
                f677byte.info(CodeCompleteService.H("aiR`JxvNA=FoPlZ~\u000fyVe_|FcnXIlG:"));
            } catch (Exception e) {
                f677byte.info(AICodeUtils.H("Sgaoh%o]H&kl}s$^]ipe|l\u007fe<"), e);
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ URL internProtocol(@NotNull URL url) {
        URL url2 = url;
        if (url2 == null) {
            m403enum(0);
        }
        String protocol = url2.getProtocol();
        boolean z = false;
        if (CodeCompleteService.H("EoL`").equals(protocol) || AICodeUtils.H("g}`").equals(protocol)) {
            protocol = protocol.intern();
            z = true;
        }
        String host = url2.getHost();
        String str = host;
        if (host != null && str.isEmpty()) {
            str = "";
            z = true;
        }
        if (z) {
            try {
                url2 = new URL(protocol, str, url2.getPort(), url2.getFile());
            } catch (MalformedURLException e) {
                LoggerRt.getInstance(UrlClassLoader.class).error(e);
                return null;
            }
        }
        return url2;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ Path getLocalPath() {
        String str = System.getenv(f678enum);
        if (str != null) {
            Path path = Paths.get(str, new String[0]);
            if (!Files.exists(path, new LinkOption[0])) {
                f677byte.error("path doesn't exist: " + str);
                return null;
            }
            return path;
        }
        return null;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String getTypeName(String a) {
        String[] split = a.split(AICodeUtils.H("@<"));
        if (split.length <= 0) {
            return null;
        }
        return split[split.length - 1];
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String getContent(String a) {
        try {
            FileInputStream fileInputStream = new FileInputStream(a);
            try {
                String a2 = new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);
                fileInputStream.close();
                return a2;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static /* synthetic */ void copyFileContent(String a, String a2) {
        File file = new File(a2);
        try {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName(detectCharset(file))));
                try {
                    bufferedWriter.write(a);
                    bufferedWriter.close();
                } catch (Throwable th) {
                    try {
                        bufferedWriter.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                }
            } catch (IOException e) {
            }
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    public static /* synthetic */ String detectCharset(File a) {
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream;
        try {
            fileInputStream = new FileInputStream(a);
            try {
                bufferedInputStream = new BufferedInputStream(fileInputStream);
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            CharsetDetector charsetDetector = new CharsetDetector();
            charsetDetector.setText(bufferedInputStream);
            CharsetMatch detect = charsetDetector.detect();
            if (detect == null) {
                bufferedInputStream.close();
                fileInputStream.close();
                return AICodeUtils.H("]WK1*");
            }
            String name = detect.getName();
            bufferedInputStream.close();
            fileInputStream.close();
            return name;
        } catch (Throwable th3) {
            try {
                bufferedInputStream.close();
                throw th3;
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
                throw th3;
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static /* synthetic */ void copyFileContent(String a, String a2, String a3) {
        File file = new File(a3);
        try {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName(detectCharset(new File(a)))));
                try {
                    bufferedWriter.write(a2);
                    bufferedWriter.close();
                } catch (Throwable th) {
                    try {
                        bufferedWriter.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                }
            } catch (IOException e) {
            }
        } catch (Exception unused) {
        }
    }
}
