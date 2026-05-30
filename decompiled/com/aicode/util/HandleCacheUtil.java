package com.aicode.util;

import com.aicode.diff.FileService;
import com.aicode.domain.LineInfo;
import com.aicode.generate.CodeTipUtil;
import com.aicode.inline.ide.IdeAction;
import com.aicode.language.CommonLanguageSupport;
import com.aicode.service.CodeInlayList;
import com.aicode.service.CodeTip;
import com.aicode.service.EditorRequestService;
import com.aicode.service.TipCache;
import com.aicode.settings.AICodeRequestSettings;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: nb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/HandleCacheUtil.class */
public final class HandleCacheUtil {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum */
    private static /* synthetic */ void m405enum(int a) {
        String H = FileService.H("\u00132991+qyp$78r��\u0010#!\t!*s-47765/)*m-^N#e\u007f\"3gx<zc\u0001@=7!4y%;2D\u0014 w6?1#");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = IdeAction.H("f\u001bD\u0016M\u0015Y");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = FileService.H("49)5*");
                break;
            case 2:
                objArr[0] = IdeAction.H("\u000fA\bH");
                break;
        }
        objArr[1] = FileService.H("n\u0016\u0006\u007f#6.:#8`!2\u001b\f\u007f\n3.='1\u0005\u0005\u0015-2\r>4#");
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[2] = IdeAction.H("A\u000eJ;x\u001bv\u0002K\u000eH");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = FileService.H("\t#\u00143,0/\u0018/\n\u0013\u0016\">,47");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public static /* synthetic */ String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        int length = stringBuffer.length() - 1;
        int i = ((2 ^ 5) << 3) ^ 2;
        int i2 = ((3 ^ 5) << 4) ^ 5;
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
            char charAt = (char) (i2 ^ (str.charAt(i7) ^ stringBuffer.charAt(i5)));
            i4 = i7 - 1;
            i5--;
            cArr[i7] = charAt;
            if (i5 < 0) {
                i5 = length;
            }
        }
        return new String(cArr);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static /* synthetic */ boolean D(@NotNull LineInfo line) {
        if (line == null) {
            m405enum(2);
        }
        String lineSuffix = line.getLineSuffix();
        return lineSuffix.isEmpty() || CommonLanguageSupport.isValidMiddleOfTheLinePosition(lineSuffix);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Nullable
    public static /* synthetic */ List<CodeInlayList> handleCache(@NotNull EditorRequestService request, @NotNull TipCache cache) {
        if (request == null) {
            m405enum(0);
        }
        if (cache == null) {
            m405enum(1);
        }
        if (AICodeRequestSettings.settings().internalDisableHttpCache || !D(request.getLineInfo())) {
            return null;
        }
        String currentDocumentPrefix = request.getCurrentDocumentPrefix();
        String TrimEndSpaceTab = CodeTipUtil.TrimEndSpaceTab(currentDocumentPrefix);
        List<CodeTip> latest = cache.getLatest(currentDocumentPrefix);
        if (latest != null) {
            boolean isLatestPrefix = cache.isLatestPrefix(TrimEndSpaceTab);
            List<CodeInlayList> list = (List) latest.stream().map(a -> {
                return CodeTipUtil.createEditorCodeTip(request, a, isLatestPrefix);
            }).filter((v0) -> {
                return Objects.nonNull(v0);
            }).collect(Collectors.toList());
            if (list.isEmpty()) {
                return null;
            }
            return list;
        }
        return null;
    }
}
