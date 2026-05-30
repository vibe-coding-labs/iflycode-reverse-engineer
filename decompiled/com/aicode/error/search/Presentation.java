package com.aicode.error.search;

import com.aicode.agent.service.ChatService;
import com.aicode.icons.Icons;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.util.ApplicationUtil;
import com.aicode.util.FileUtil;
import com.aicode.util.HandleCacheUtil;
import com.intellij.codeInsight.hints.presentation.InputHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorCustomElementRenderer;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Icon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: dj */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/error/search/Presentation.class */
public class Presentation implements EditorCustomElementRenderer, InputHandler {

    /* renamed from: float, reason: not valid java name */
    private final Project f294float;

    /* renamed from: byte, reason: not valid java name */
    private final Editor f295byte;

    /* renamed from: enum, reason: not valid java name */
    private final int f296enum;
    public static List<String> suffixDir = Arrays.asList(File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator, File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator, File.separator + "src" + File.separator + "main" + File.separator, File.separator + "src" + File.separator + "test" + File.separator);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m157enum(int a) {
        String H = HandleCacheUtil.H("M!a,g0o* 9$f \u001fY'c\u0006\u007f9{h;ud(m:p>eh!|~uk{SJ2;.zeim*x 1 k/1,isd g8");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 2:
            default:
                objArr[0] = CancelRequestTip.H("\u0007\u0005\u0014\u0012\u000f/\u0007\u0014\u0007\u001d");
                break;
            case 1:
            case 3:
                do {
                } while (0 != 0);
                objArr[0] = HandleCacheUtil.H("#e<e ");
                break;
            case 4:
            case 5:
                objArr[0] = CancelRequestTip.H("\u0003\u001f\u001d\b\u0010");
                break;
            case 6:
                objArr[0] = HandleCacheUtil.H("3");
                break;
            case 7:
                objArr[0] = CancelRequestTip.H("\u001b");
                break;
            case 8:
                objArr[0] = HandleCacheUtil.H("\u007f1i:E/e<e1\u007f!n'");
                break;
        }
        objArr[1] = CancelRequestTip.H("\u000f\u0003G\u00055=\u0015\u0019\u0005\u0004X\u0012\u0013\u0013\u0005\u0018_\u0003��\u0004\u0002\u0013\u0005B;\u0019\u000f\u0019\u0004\u000f\u001e\u000b\u0005\u0018\u0006\u0007");
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[2] = HandleCacheUtil.H("|!q(t\r`:i>n0");
                break;
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[2] = CancelRequestTip.H("\u0007\u0005\u0014\u0012\u000f'\u001e\u0007\f\r");
                break;
            case 4:
                objArr[2] = HandleCacheUtil.H("*a3h\u0003x*p3X \\:r0g'");
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = CancelRequestTip.H("\u001a\u0010\u0018\u0007\u001d");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public void mouseExited() {
        this.f295byte.setCustomCursor(this, Cursor.getPredefinedCursor(2));
    }

    public int calcWidthInPixels(@NotNull Inlay a) {
        if (a == null) {
            m157enum(4);
        }
        return Icons.DebugIcon.getIconWidth();
    }

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    public static void handleDebug(String a, String a2, boolean z, boolean z2) {
        String str;
        String str2;
        Project findCurrentProject = ApplicationUtil.findCurrentProject();
        if (StringUtils.isBlank(a)) {
            ChatService.handleCodeDebug(findCurrentProject, "", a2, z2);
            return;
        }
        TreeMap treeMap = new TreeMap();
        TreeMap treeMap2 = new TreeMap();
        Yd(a, z, treeMap, treeMap2);
        if (MapUtils.isEmpty(treeMap) && MapUtils.isEmpty(treeMap2)) {
            ChatService.handleCodeDebug(findCurrentProject, "", a2, z2);
            return;
        }
        String a3 = null;
        int i = -1;
        if (!treeMap2.isEmpty()) {
            Iterator it = treeMap2.entrySet().iterator();
            loop0: while (true) {
                while (true) {
                    if (!it.hasNext()) {
                        str2 = a3;
                        break loop0;
                    }
                    Map.Entry entry = (Map.Entry) it.next();
                    if (new File((String) entry.getKey()).exists()) {
                        a3 = (String) entry.getKey();
                        try {
                            i = Integer.parseInt((String) entry.getValue());
                            str2 = a3;
                            break loop0;
                        } catch (NumberFormatException unused) {
                        }
                    }
                }
            }
            if (StringUtils.isNotBlank(str2) && i != -1) {
                ChatService.handleCodeDebug(findCurrentProject, a3, Integer.valueOf(i), "", a2, z2);
                return;
            }
        }
        List<String> sourceCodeDirectories = FileUtil.getSourceCodeDirectories(findCurrentProject);
        if (CollectionUtils.isEmpty(sourceCodeDirectories)) {
            ChatService.handleCodeDebug(findCurrentProject, "", a2, z2);
            return;
        }
        Iterator it2 = treeMap.entrySet().iterator();
        loop2: while (true) {
            while (true) {
                if (!it2.hasNext()) {
                    str = a3;
                    break loop2;
                }
                Map.Entry entry2 = (Map.Entry) it2.next();
                String We = We(((String) entry2.getKey()).replaceAll(HandleCacheUtil.H("Wz"), "\\" + File.separator), sourceCodeDirectories);
                if (We != null) {
                    a3 = We;
                    try {
                        i = Integer.parseInt((String) entry2.getValue());
                        str = a3;
                        break loop2;
                    } catch (NumberFormatException unused2) {
                    }
                }
            }
        }
        if (!StringUtils.isBlank(str) && i != -1) {
            ChatService.handleCodeDebug(findCurrentProject, a3, Integer.valueOf(i), "", a2, z2);
        } else {
            ChatService.handleCodeDebug(findCurrentProject, "", a2, z2);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void paint(@NotNull Inlay inlay, @NotNull Graphics g, @NotNull Rectangle r, @NotNull TextAttributes a) {
        Icon icon;
        Rectangle rectangle;
        if (inlay == null) {
            m157enum(5);
        }
        if (g == null) {
            m157enum(6);
        }
        if (r == null) {
            m157enum(7);
        }
        if (a == null) {
            m157enum(8);
        }
        if (EditorColorsManager.getInstance().getGlobalScheme().getColor(EditorColors.READONLY_FRAGMENT_BACKGROUND_COLOR) == null) {
            icon = Icons.DebugDarkIcon;
            rectangle = r;
        } else {
            icon = Icons.DebugIcon;
            rectangle = r;
        }
        Icon icon2 = icon;
        icon2.paintIcon(inlay.getEditor().getComponent(), g, ((rectangle.x + (r.width / 2)) - (icon.getIconWidth() / 2)) + 2, (r.y + (r.height / 2)) - (icon2.getIconHeight() / 2));
    }

    public void mouseMoved(@NotNull MouseEvent mouseEvent, @NotNull Point a) {
        if (mouseEvent == null) {
            m157enum(2);
        }
        if (a == null) {
            m157enum(3);
        }
        this.f295byte.setCustomCursor(this, Cursor.getPredefinedCursor(12));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void mouseClicked(@NotNull MouseEvent mouseEvent, @NotNull Point a) {
        if (mouseEvent == null) {
            m157enum(0);
        }
        if (a == null) {
            m157enum(1);
        }
        int lineNumber = this.f295byte.getDocument().getLineNumber(this.f296enum);
        int lineCount = this.f295byte.getDocument().getLineCount();
        int i = lineCount;
        if (lineCount - lineNumber > 20) {
            i = lineNumber + 20;
        }
        String text = this.f295byte.getDocument().getText(new TextRange(this.f295byte.getDocument().getLineEndOffset(lineNumber), this.f295byte.getDocument().getLineEndOffset(i - 1)));
        String text2 = this.f295byte.getDocument().getText(new TextRange(this.f296enum, this.f295byte.getDocument().getLineEndOffset(lineNumber)));
        if (!StringUtils.isBlank(text2)) {
            handleDebug(text, text2, true, true);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static void Yd(String a, boolean z, Map<String, String> map, Map<String, String> map2) {
        Pattern compile = Pattern.compile(CancelRequestTip.H("K^t|*%JH+YI=9AY,MM,#FDQC6\u000evt<I"));
        Pattern compile2 = Pattern.compile(HandleCacheUtil.H("Ha.t4}+fX?:g6{V1 }"));
        String[] split = a.split(CancelRequestTip.H("j"));
        int length = split.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            String str = split[i2];
            if (!StringUtils.isBlank(str)) {
                Matcher matcher = compile.matcher(str);
                if (matcher.find()) {
                    try {
                        map.put(matcher.group(1), matcher.group(4));
                    } catch (Exception e) {
                        return;
                    }
                } else if (z) {
                    continue;
                } else {
                    Matcher matcher2 = compile2.matcher(str);
                    if (matcher2.find()) {
                        try {
                            map2.put(matcher2.group(1), matcher2.group(2));
                        } catch (Exception unused) {
                            return;
                        }
                    } else {
                        continue;
                    }
                }
            }
            i2++;
            i = i2;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static String We(String a, List<String> list) {
        for (String str : list) {
            for (String a2 : suffixDir) {
                String a3 = str + a2 + a + ".java";
                if (new File(a3).exists()) {
                    return a3;
                }
            }
        }
        return null;
    }

    public static void handleDebug(Project a, String a2, String a3, String a4, int a5) {
        ChatService.handleCodeDebug(a, a2, Integer.valueOf(a5), a3, a4, false);
    }

    public Presentation(Editor a, Project a2, int a3) {
        this.f295byte = a;
        this.f294float = a2;
        this.f296enum = a3;
    }
}
