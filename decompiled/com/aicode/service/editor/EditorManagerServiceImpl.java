package com.aicode.service.editor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.CodeTipRequestDto;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.ResponseStreamDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.apm.enums.SpanAttrEnum;
import com.aicode.content.util.EditorUtils;
import com.aicode.domain.LineInfo;
import com.aicode.enums.AICodeStatus;
import com.aicode.enums.CodeCollectEnum;
import com.aicode.enums.CodeTipRequestType;
import com.aicode.enums.CodeTipType;
import com.aicode.enums.OperateActionEnum;
import com.aicode.enums.TipType;
import com.aicode.exception.RequestTimeoutException;
import com.aicode.listener.AutoCodeGenerateListener;
import com.aicode.message.BasicActionsBundle;
import com.aicode.request.AgentCodeTip;
import com.aicode.request.RequestId;
import com.aicode.service.CodeEditorInlay;
import com.aicode.service.CodeInlayList;
import com.aicode.service.EditorManagerService;
import com.aicode.service.EditorRequestService;
import com.aicode.service.EditorSupport;
import com.aicode.service.RejectTipMessage;
import com.aicode.service.RequestTipService;
import com.aicode.service.RequestsCancelledService;
import com.aicode.service.TipReceivedMessage;
import com.aicode.service.TipRenderer;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.status.AICodeStatusService;
import com.aicode.ui.ActionButton;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.CodeCheckUtil;
import com.aicode.util.PluginInfoUtils;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.intellij.injected.editor.EditorWindow;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.InlayModel;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.editor.impl.ImaginaryEditor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.KeyWithDefaultValue;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.concurrency.annotations.RequiresBackgroundThread;
import com.intellij.util.concurrency.annotations.RequiresEdt;
import io.opentelemetry.api.trace.Span;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: ec */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/EditorManagerServiceImpl.class */
public class EditorManagerServiceImpl implements EditorManagerService {

    /* renamed from: new, reason: not valid java name */
    private static final Key<Boolean> f568new;

    /* renamed from: long, reason: not valid java name */
    private static final Logger f569long;
    public static final Key<RequestResultList> CACHE_KEY_LAST_REQUEST;

    /* renamed from: super, reason: not valid java name */
    private static final Set<String> f570super;
    public static final AtomicInteger docChangeCount;

    /* renamed from: for, reason: not valid java name */
    public static Boolean f571for;

    /* renamed from: if, reason: not valid java name */
    public static final /* synthetic */ boolean f572if;

    /* renamed from: final, reason: not valid java name */
    public static OperateActionEnum f574final;

    /* renamed from: try, reason: not valid java name */
    private static final Key<Boolean> f575try;
    public static final Map<String, String> keyMap;
    public static final Key<RequestResultList> KEY_LAST_REQUEST;
    public static final int DELAY_MILLIS = 50;

    /* renamed from: byte, reason: not valid java name */
    public static String f577byte;

    /* renamed from: enum, reason: not valid java name */
    public static final Key<Boolean> f578enum;
    public static final String ACCEPT_CODE_FOR_LINE = EditorUtils.H("\u001bW\u000f!z先邓金绾!z%~Y5b'6f\u001a⇲>e逓蠔釘绷");
    public static final String ACCEPT_CODE_FOR_WORD = CancelRequestTip.H("3 #mm儁邁醳织HH\u0018\u0018$\u0013\u001a\u0004MFJ⇸MM遠讽釽纉");
    public final CancelRequestTip requestAlarm = new CancelRequestTip(this);

    /* renamed from: float, reason: not valid java name */
    private Integer f576float = 0;

    /* renamed from: case, reason: not valid java name */
    private Integer f573case = 0;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m283enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            default:
                H = EditorUtils.H("W?p9w$i(<!n(}Fx\u0002c\u0002o-k|l&s;S��f,of5lgh6\"gz\u0005\b8hrzh+i50%y9\u001a\u0003{em-s(");
                i = a;
                break;
            case 18:
                do {
                } while (0 != 0);
                H = CancelRequestTip.H("%+\u0005\u001e+\u0010\u000f\u000fA\f\u0013\u0002?8\u0005AS\u0005\\W\u001eM\n\u0012\u0012\u0015m#\u0006\u001dT\u0006\r\u001c\u0014\u0013\u0004J\u001f\u0004\u0005\u0005");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            default:
                i2 = 3;
                break;
            case 18:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 1:
            case 3:
            case 4:
            case 6:
            case 9:
            case 11:
            case 13:
            case 16:
            case 17:
            case 19:
            case 21:
            case 25:
            case 29:
            case 33:
            case 37:
            case 39:
            case 40:
            case 43:
            case 44:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 59:
            case 61:
            case 64:
            case 67:
            case 71:
            default:
                objArr[0] = EditorUtils.H("{!j,p6");
                i3 = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = CancelRequestTip.H("\u0007\r\t\u0013\u0002\u00028\u0010\u001f\u000e\f");
                i3 = a;
                break;
            case 5:
            case 60:
                objArr[0] = EditorUtils.H("\u0011l*i=|0");
                i3 = a;
                break;
            case 7:
            case 22:
            case 24:
            case 28:
            case 32:
            case 36:
            case 42:
            case 58:
            case 62:
            case 66:
            case 70:
                objArr[0] = CancelRequestTip.H("\u0013\u000f\u001b\u0004\u0014\u001a\u001d");
                i3 = a;
                break;
            case 8:
            case 14:
            case 15:
            case 63:
                objArr[0] = EditorUtils.H("\"\u007f/s\u0004T\r\u007f<O1l0");
                i3 = a;
                break;
            case 10:
            case 26:
            case 30:
                objArr[0] = CancelRequestTip.H("\r��\u0007\u0004\u0007\u001b\u0004\"\u0005\u0004\u0005\u0014\u0011\u001d");
                i3 = a;
                break;
            case 12:
            case 47:
                objArr[0] = EditorUtils.H("9s<O\u0004m1W!o!");
                i3 = a;
                break;
            case 18:
                objArr[0] = CancelRequestTip.H("\u0013\u001f\u0006D\u0017\u001f*&\u0001��E\u0019��\u0017\u0015\n\u0002\u0004Y\u00133>\u0015\u000e\u0004Y7\u0016\u0004\u0019\b\u0015,��#,\u000e\f\u0006'\r\u001a\u0017\b\t\u000f8\u001c\u0019\u0005");
                i3 = a;
                break;
            case 20:
                objArr[0] = EditorUtils.H("y.~?s#N3{4v=l0");
                i3 = a;
                break;
            case 23:
            case 27:
            case 31:
            case 35:
            case 65:
            case 69:
                objArr[0] = CancelRequestTip.H("\u0003\u0004\u001d\u0010\u0010\u001a");
                i3 = a;
                break;
            case 34:
            case 38:
            case 68:
            case 72:
                objArr[0] = EditorUtils.H("\u0002q+w=g0");
                i3 = a;
                break;
            case 41:
                objArr[0] = CancelRequestTip.H("\b\u001d\u001e\n\u0002");
                i3 = a;
                break;
            case 45:
                objArr[0] = EditorUtils.H("$T\r\u007f<P=k7");
                i3 = a;
                break;
            case 46:
                objArr[0] = CancelRequestTip.H("\u0005\u000e\t\u001f\u001c\u0014\u0007\u001d");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            default:
                objArr[1] = EditorUtils.H(">i[Bv%y.c934d(H\fq,2#v ` dbD>I\u000fy?L;k?}$b\u0018s?L\b} J5o(");
                i4 = a;
                break;
            case 18:
                do {
                } while (0 != 0);
                objArr[1] = CancelRequestTip.H("\u000f\u0004\u0015#\u0004\u001d\u0010\u0010\u001a");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = EditorUtils.H("\"e\fL��w)b:s!");
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = CancelRequestTip.H("\n\u0006\u0001\u001a\u001c<\b\u0011#\u0004\u001d\u0010\u0010\u001a");
                break;
            case 3:
                objArr[2] = EditorUtils.H("x*e\u000e[\u0002v G9k%");
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = CancelRequestTip.H("\t\u0002\u0002\u000f\u001a\u0005%��\u0019");
                break;
            case 9:
            case 10:
                objArr[2] = EditorUtils.H("/\u007f>J\u000em W1o7");
                break;
            case 11:
            case 12:
                objArr[2] = CancelRequestTip.H("\f\u0010\u001d\u001c\u0007\u0013\"\u0002\u000b\u001f\u0016\f\r");
                break;
            case 13:
                objArr[2] = EditorUtils.H("7i\b}\"b,N\bq+G=~(");
                break;
            case 14:
                objArr[2] = CancelRequestTip.H("\u0013\u0011\u001c:\u0004\u0010\u001f\u000f\u0002\u0005 \r");
                break;
            case 15:
                objArr[2] = EditorUtils.H("9\u007f5B.e=U\u000fm G9k%");
                break;
            case 16:
                objArr[2] = CancelRequestTip.H("\u0002,#\n\f\u0018 \u0001\u00183\u0004\u001b\u001f\u0014\u0002\u001d\u001a");
                break;
            case 17:
                objArr[2] = EditorUtils.H("*_\u0015W+o9f7");
                break;
            case 18:
                break;
            case 19:
            case 20:
                objArr[2] = CancelRequestTip.H("\u0016\u0014\u00048(*\u0006\u0010\u0011<\u0001\u00113\u000f\u001b\u0004\u0014\u001a\u001d");
                break;
            case 21:
            case 22:
                objArr[2] = EditorUtils.H("\ts<t?v*[\bS$r(y\u000ez W1o7");
                break;
            case 23:
            case 24:
            case 25:
            case 26:
                objArr[2] = CancelRequestTip.H("\u001d\u001a\u001b\r\u0013\u0015#\u0004\u001d\u0010\u0010\u001a");
                break;
            case 27:
            case 28:
            case 29:
            case 30:
                objArr[2] = EditorUtils.H("e;r,N\u0004W+o9f7");
                break;
            case 31:
            case 32:
            case 33:
            case 34:
                objArr[2] = CancelRequestTip.H("\r\u0006=\u001a\u001b\r\u0013\u0015#\u0004\u001d\u0010\u0010\u001a");
                break;
            case 35:
            case 36:
            case 37:
            case 38:
                objArr[2] = EditorUtils.H("~.E;r,N\u0004W+o9f7");
                break;
            case 39:
                objArr[2] = CancelRequestTip.H("\u001d\u00078\u001a\u000e\u0002\u000f\u0019\u0002\u0018\u0007\u000e");
                break;
            case 40:
            case 41:
                objArr[2] = EditorUtils.H("m3q;F?U\u0002{6p1q#");
                break;
            case 42:
            case 43:
                objArr[2] = CancelRequestTip.H("$\u001a(\u0017��\u0001\u001e\u00043\u000f\u001b\u0004\u0014\u001a\u001d");
                break;
            case 44:
            case 45:
                objArr[2] = EditorUtils.H(",^\u0005W+o9f7");
                break;
            case 46:
            case 47:
                objArr[2] = CancelRequestTip.H("\b\u0012\t8\u0019\u0005\u001d\u0017\t\u001c\u00043\u000f\u001b\u0004\u0014\u001a\u001d");
                break;
            case 48:
                objArr[2] = EditorUtils.H("h)u\u0015e8q*j,n$t\u000er$N\u000el\u0016w9k!");
                break;
            case 49:
                objArr[2] = CancelRequestTip.H("\u001f\u001b\t\t\u000b\u0002.\u0007\u0001$\u0007\f#\u001b\u0006\u001c\"\u000e\u0007\u001a\u001d\u0014\u001d\f");
                break;
            case 50:
                objArr[2] = EditorUtils.H("$r\u001bq\nr$]\"r)V\u0004Q#O1q!");
                break;
            case 51:
                objArr[2] = CancelRequestTip.H("%\b\u001a:\u0011\u0010\u001c(\u000f\u0006\u000b\b\"\f\u001d");
                break;
            case 52:
                objArr[2] = EditorUtils.H("%`)U,\u007f7y$c>s\u000fr$z\u000bz0");
                break;
            case 53:
                objArr[2] = CancelRequestTip.H(">%\u0006\u001e:\u0011\u0010\u001c(\u000f\u0006\u000b\b\"\f\u001d");
                break;
            case 54:
                objArr[2] = EditorUtils.H("e%n-U,\u007f7y$c>s\u000fr$z\u000bz0");
                break;
            case 55:
                objArr[2] = CancelRequestTip.H(",.\n\f\u0004��<\u0001\u0011'\u0005\u0018=\u0018\u0007\f");
                break;
            case 56:
                objArr[2] = EditorUtils.H("I\b_ h.d*s.~\u000fs,V'q7O1q!");
                break;
            case 57:
            case 58:
                objArr[2] = CancelRequestTip.H("\u0007(9\n\u0001;\u001a,\r\f��\u0004\u000e%\u0018\u0019\u001a");
                break;
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
                objArr[2] = EditorUtils.H(" s(s=N6q7g\fv4");
                break;
            case 64:
                objArr[2] = CancelRequestTip.H("\u000e\u0012( $\u001d\b��\u001d\u0007\u0006%\u0004\u000b\u0006&\u001e\u001b\r");
                break;
            case 65:
            case 66:
            case 67:
            case 68:
                objArr[2] = EditorUtils.H("e5P.~ d._#V��g6T7m ");
                break;
            case 69:
            case 70:
            case 71:
            case 72:
                objArr[2] = CancelRequestTip.H("\t\b2\u0011\u0005,9\f \u001a\u0018\t\u0011\u0012'\u0005\u0018=\u0018\u0007\f");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            default:
                throw new IllegalArgumentException(format);
            case 18:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        f572if = !EditorManagerServiceImpl.class.desiredAssertionStatus();
        f569long = LoggerFactory.getLogger(EditorManagerServiceImpl.class);
        KEY_LAST_REQUEST = Key.create(EditorUtils.H("w$b5a;4$t\"b\"H3{4v=l0"));
        CACHE_KEY_LAST_REQUEST = Key.create(CancelRequestTip.H("\u0015\u001d\u000b\u0007\\]I\u0002\f\u0001\u0019\u0002\u00188\b\u001c\u0005\u0015IN"));
        f575try = KeyWithDefaultValue.create(EditorUtils.H(";l=u%uef?U\u0002{6p1q#"), false);
        f568new = Key.create(CancelRequestTip.H("\b��\u0017\u001b\f\r\u0016]\u0003\u000e\u001c\u0007\u001f>\u001f\u001a\u001d\u0002\u0002\u0004_^"));
        f578enum = Key.create(EditorUtils.H("`3f1~$>/y.i��h U=k+"));
        f570super = Set.of(CancelRequestTip.H("\"\u0019\u0011,#\rI8\u001d\u001e\r\u0018l\u0002\n\u0018\u0004\f\u0019\u000fJ\u000f\u0014P$[X"));
        f574final = null;
        f571for = false;
        f577byte = "";
        docChangeCount = new AtomicInteger(0);
        keyMap = Map.of(EditorUtils.H("吒乒箲奰"), CancelRequestTip.H("↫"), EditorUtils.H("吒乓箲奰"), CancelRequestTip.H("↩"), EditorUtils.H("吒嶾箲奰"), CancelRequestTip.H("↪"), EditorUtils.H("吒厫箲奰"), CancelRequestTip.H("↨"));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.EditorManagerService
    @RequiresEdt
    public boolean isAvailable(@NotNull Editor a) {
        if (a == null) {
            m283enum(0);
        }
        Boolean bool = (Boolean) f568new.get(a);
        Boolean bool2 = bool;
        if (bool == null) {
            bool2 = Boolean.valueOf(((a instanceof EditorWindow) || (a instanceof ImaginaryEditor) || ((a instanceof EditorEx) && ((EditorEx) a).isEmbeddedIntoDialogWrapper()) || a.isViewer() || a.isOneLineMode() || !PluginInfoUtils.isSupportedIDE(a.getProject()) || !RequestTipService.getInstance().isAvailable(a)) ? false : true);
            f568new.set(a, bool2);
        }
        return bool2.booleanValue() && !a.isDisposed();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.EditorManagerService
    public boolean hasCacheData(@NotNull Editor editor, char c) {
        if (editor == null) {
            m283enum(3);
        }
        RequestResultList requestResultList = (RequestResultList) KEY_LAST_REQUEST.get(editor);
        if (requestResultList != null) {
            List<CodeInlayList> fetchCachedTips = RequestTipService.getInstance().fetchCachedTips(requestResultList.getRequest());
            if (fetchCachedTips == null || fetchCachedTips.isEmpty()) {
                return false;
            }
            CodeInlayList codeInlayList = fetchCachedTips.get(0);
            if (!codeInlayList.isEmpty()) {
                CodeEditorInlay next = codeInlayList.iterator().next();
                return !next.getLines().isEmpty() && next.getLines().get(0).startsWith(String.valueOf(c));
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.service.EditorManagerService
    public int countTipInlays(@NotNull Editor editor, @NotNull TextRange searchRange, boolean z, boolean z2, boolean z3, boolean z4) {
        if (editor == null) {
            m283enum(1);
        }
        if (searchRange == null) {
            m283enum(2);
        }
        if (isAvailable(editor)) {
            int startOffset = searchRange.getStartOffset();
            int endOffset = searchRange.getEndOffset();
            InlayModel inlayModel = editor.getInlayModel();
            int i = 0;
            if (z) {
                i = (int) (0 + inlayModel.getInlineElementsInRange(startOffset, endOffset).stream().filter(a -> {
                    if (!(a.getRenderer() instanceof TipRenderer)) {
                        return false;
                    }
                    if (z4) {
                        return true;
                    }
                    List<String> contentLines = ((TipRenderer) a.getRenderer()).getContentLines();
                    if (contentLines.isEmpty()) {
                        return false;
                    }
                    return searchRange.getEndOffset() >= a.getOffset() + AICodeStringUtil.leadingWhitespaceLength(contentLines.get(0));
                }).count());
            }
            if (z3) {
                i = (int) (i + inlayModel.getBlockElementsInRange(startOffset, endOffset).stream().filter(a2 -> {
                    return a2.getRenderer() instanceof TipRenderer;
                }).count());
            }
            if (z2) {
                i = (int) (i + inlayModel.getAfterLineEndElementsInRange(startOffset, endOffset).stream().filter(a3 -> {
                    return a3.getRenderer() instanceof TipRenderer;
                }).count());
            }
            return i;
        }
        return 0;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.EditorManagerService
    @RequiresEdt
    public boolean acceptTip(@NotNull Editor a) {
        RequestResultList requestResultList;
        CodeInlayList currentCodeTip;
        if (a == null) {
            m283enum(4);
        }
        if (a.isDisposed()) {
            f569long.warn(EditorUtils.H("?D\u0012b\"szd2h$q/om^\bm5l+z "));
            return false;
        }
        Project project = a.getProject();
        if (project != null && !project.isDisposed()) {
            if (!Cb(a) && (requestResultList = (RequestResultList) CACHE_KEY_LAST_REQUEST.get(a)) != null && (currentCodeTip = requestResultList.getCurrentCodeTip()) != null) {
                String str = RequestTipServiceImpl.LATEST_RESPONSE_DATA.get(project);
                if (str != null && currentCodeTip.getData() != null && !currentCodeTip.getData().isEnded()) {
                    if (RequestTipServiceImpl.CODE_TIP_MAP.containsKey(str)) {
                        CodeTipRequestDto codeTipRequestDto = RequestTipServiceImpl.CODE_TIP_MAP.get(str);
                        Span parentSpan = codeTipRequestDto.getParentSpan();
                        parentSpan.setAttribute(SpanAttrEnum.COMPLETE_RESULT.getText(), codeTipRequestDto.getLastReplacementText());
                        parentSpan.end();
                        RequestTipServiceImpl.CODE_TIP_MAP.remove(str);
                    }
                    RequestTipServiceImpl.LAST_REQUEST.get(project).remove(str);
                    AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
                }
                disposeTips(a, OperateActionEnum.Applied);
                AutoCodeGenerateListener.ignoreApply.set(true);
                if (f571for.booleanValue() && !f577byte.isEmpty()) {
                    currentCodeTip.setReplacementText(f577byte);
                    acceptTip(project, a, requestResultList.getRequest(), currentCodeTip);
                    f571for = false;
                    f577byte = "";
                } else {
                    acceptTip(project, a, requestResultList.getRequest(), currentCodeTip);
                }
                CACHE_KEY_LAST_REQUEST.set(a, (Object) null);
                return true;
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean LC(String a, String a2, int a3) {
        int i = 0;
        int i2 = 0;
        while (i < a2.length()) {
            if (a.charAt(a3 + i2) == a2.charAt(i2)) {
                i2++;
                i = i2;
            } else {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ec */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/EditorManagerServiceImpl$F.class */
    public class F implements Flow.Subscriber<List<CodeInlayList>> {

        /* renamed from: super, reason: not valid java name */
        public final /* synthetic */ EditorRequestService f580super;

        /* renamed from: for, reason: not valid java name */
        public final /* synthetic */ EditorManagerServiceImpl f581for;

        /* renamed from: if, reason: not valid java name */
        public static final /* synthetic */ boolean f582if;

        /* renamed from: case, reason: not valid java name */
        public final /* synthetic */ long f583case;

        /* renamed from: final, reason: not valid java name */
        public final /* synthetic */ Editor f584final;

        /* renamed from: try, reason: not valid java name */
        private volatile Flow.Subscription f585try;

        /* renamed from: float, reason: not valid java name */
        public final /* synthetic */ Consumer f586float;

        /* renamed from: byte, reason: not valid java name */
        private volatile boolean f587byte;

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ AtomicBoolean f588enum;

        /*  JADX ERROR: IndexOutOfBoundsException in pass: SSATransform
            java.lang.IndexOutOfBoundsException: bitIndex < 0: -1
            	at java.base/java.util.BitSet.get(BitSet.java:626)
            	at jadx.core.dex.visitors.ssa.LiveVarAnalysis.fillBasicBlockInfo(LiveVarAnalysis.java:65)
            	at jadx.core.dex.visitors.ssa.LiveVarAnalysis.runAnalysis(LiveVarAnalysis.java:36)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:58)
            	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:44)
            */
        public F(com.aicode.service.editor.EditorManagerServiceImpl r16, com.aicode.service.EditorRequestService r17, com.intellij.openapi.editor.Editor r18, java.util.concurrent.atomic.AtomicBoolean r19, java.util.function.Consumer r20, long r21) {
            /*  JADX ERROR: IndexOutOfBoundsException in pass: SSATransform
                java.lang.IndexOutOfBoundsException: bitIndex < 0: -1
                	at java.base/java.util.BitSet.get(BitSet.java:626)
                	at jadx.core.dex.visitors.ssa.LiveVarAnalysis.fillBasicBlockInfo(LiveVarAnalysis.java:65)
                	at jadx.core.dex.visitors.ssa.LiveVarAnalysis.runAnalysis(LiveVarAnalysis.java:36)
                	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:58)
                */
            /*  JADX ERROR: Method code generation error
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.nodes.IContainer.get(jadx.api.plugins.input.data.attributes.IJadxAttrType)" because "cont" is null
                	at jadx.core.codegen.RegionGen.declareVars(RegionGen.java:70)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:65)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:281)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                */
            /*
                r15 = this;
                r0 = r15
                r1 = r20
                r15 = r1
                r20 = r0
                r0 = r21
                r1 = r20
                r2 = r1; r1 = r0; r0 = r-1; r-1 = r2; 
                r3 = r2; r2 = r1; r1 = r0; r0 = r3; 
                r4 = r15
                r5 = r19
                r6 = r20
                r7 = r6; r6 = r5; r5 = r7; 
                r8 = r18
                r9 = r17
                r10 = r20
                r11 = r10; r10 = r9; r9 = r11; 
                r12 = r16
                r11.f581for = r12
                r9.f580super = r10
                r7.f584final = r8
                r5.f588enum = r6
                r3.f586float = r4
                r1.f583case = r2
                r0.<init>()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aicode.service.editor.EditorManagerServiceImpl.F.<init>(com.aicode.service.editor.EditorManagerServiceImpl, com.aicode.service.EditorRequestService, com.intellij.openapi.editor.Editor, java.util.concurrent.atomic.AtomicBoolean, java.util.function.Consumer, long):void");
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        static {
            f582if = !EditorManagerServiceImpl.class.desiredAssertionStatus();
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onError(Throwable th) {
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        @Override // java.util.concurrent.Flow.Subscriber
        /* renamed from: KB, reason: merged with bridge method [inline-methods] */
        public void onNext(List<CodeInlayList> list) {
            RequestResultList requestResultList;
            EditorManagerServiceImpl.f569long.info(RequestTimeoutException.H("ぶ\u0005(4\u0014g\u0005,\u000f,\u00113\u00035Q\u001534\u0018<D*\fpB\u0007ぃ"));
            if (!this.f581for.mc(this.f580super, this.f584final)) {
                EditorManagerServiceImpl.f569long.debug(ActionButton.H("っ\n=,\u0001^\u0010\u0006\u001a\u001d\u0004\u0017\u0016\u001dD\u001d)<\u000f\u0013\u001dK\u0004\u0019I^>が"));
                return;
            }
            if (this.f588enum.compareAndSet(true, false) && (requestResultList = (RequestResultList) EditorManagerServiceImpl.KEY_LAST_REQUEST.get(this.f584final)) != null) {
                requestResultList.resetInlays();
            }
            if (this.f581for.Xc(this.f584final, list)) {
                EditorManagerServiceImpl.f569long.debug(RequestTimeoutException.H("あ\u0010?\u0015\"F\u0005(=\u0001+\u00037\u0007|\u001c2\u0013p\u0018\u001219��e\u00028\u0011}U\u0017ぃ"));
                return;
            }
            this.f585try.request(1L);
            ((TipReceivedMessage) ApplicationManager.getApplication().getMessageBus().syncPublisher(TipReceivedMessage.TOPIC)).inlaysReceived(this.f580super, list);
            if (this.f587byte || this.f586float == null || list.isEmpty()) {
                return;
            }
            CodeInlayList codeInlayList = list.get(0);
            if (codeInlayList.getData() == null || codeInlayList.getData().isEnded()) {
                this.f587byte = true;
            }
            if (!f582if && (codeInlayList == null || codeInlayList.isEmpty())) {
                throw new AssertionError();
            }
            Application application = ApplicationManager.getApplication();
            Consumer consumer = this.f586float;
            long j = this.f583case;
            Editor editor = this.f584final;
            application.invokeLater(() -> {
                consumer.accept(codeInlayList);
                long a = System.currentTimeMillis() - j;
                EditorManagerServiceImpl.f569long.info("【code complete inlay finished】，" + RequestTipServiceImpl.LATEST_RESPONSE_DATA.get(editor.getProject()) + "代码补全总耗时【" + a + "】毫秒");
            });
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onComplete() {
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onSubscribe(Flow.Subscription a) {
            this.f585try = a;
            a.request(1L);
            Objects.requireNonNull(this.f585try);
            Disposable disposable = this.f580super.getDisposable();
            Flow.Subscription subscription = this.f585try;
            Objects.requireNonNull(subscription);
            Objects.requireNonNull(subscription);
            Disposer.tryRegister(disposable, subscription::cancel);
        }
    }

    private Flow.Subscriber<List<CodeInlayList>> da(Editor a, EditorRequestService a2, Consumer<CodeInlayList> consumer, long a3) {
        return new F(this, a2, a, new AtomicBoolean(false), consumer, a3);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean mB(@NotNull CodeInlayList inlays, @NotNull EditorRequestService request, @NotNull Editor editor, boolean z, @NotNull OperateActionEnum a) {
        TipInlayRenderer tipInlayRenderer;
        ArrayList arrayList;
        ArrayList arrayList2;
        Inlay<TipRenderer> inlay;
        if (inlays == null) {
            m283enum(31);
        }
        if (request == null) {
            m283enum(32);
        }
        if (editor == null) {
            m283enum(33);
        }
        if (a == null) {
            m283enum(34);
        }
        if (z) {
            try {
                disposeTips(editor, a);
            } catch (Exception e) {
                return false;
            }
        }
        if (inlays.getData() != null) {
            disposeTips(editor, OperateActionEnum.Applied);
        }
        if (editor.getCaretModel().getOffset() == request.getOffset()) {
            InlayModel inlayModel = editor.getInlayModel();
            int i = 0;
            Iterator<CodeEditorInlay> it = inlays.iterator();
            while (it.hasNext()) {
                CodeEditorInlay next = it.next();
                if (next.isEmptyTip()) {
                    it = it;
                } else {
                    if (i == 0 && inlays.getReplacementRange().getLength() > 0) {
                        f574final = null;
                        List<String> replaceLeadingTabs = TipInlayRenderer.replaceLeadingTabs(next.getLines(), request);
                        String str = replaceLeadingTabs.get(0);
                        if (countTrailingSpaces(HA(editor)) > 0) {
                            replaceLeadingTabs.set(0, str.trim());
                            inlays.setRemoveBlank(true);
                        }
                        tipInlayRenderer = new TipInlayRenderer(editor, request, next.getType(), replaceLeadingTabs);
                    } else {
                        ArrayList arrayList3 = new ArrayList(next.getLines());
                        int countTrailingSpaces = countTrailingSpaces(HA(editor));
                        boolean isEmpty = StringUtils.isEmpty(MC(editor));
                        String str2 = (String) arrayList3.get(0);
                        String la = la();
                        String zc = zc();
                        boolean z2 = true;
                        if (inlays.getData() != null) {
                            z2 = inlays.getData().isShowKeyMapTipFlag();
                        }
                        int size = inlays.getInlays().size();
                        if (i == 0 && z2) {
                            if (size != 1 || !isEmpty) {
                                if (size > 1 && isEmpty) {
                                    str2 = str2 + String.format(EditorUtils.H("`.i/7"), "") + zc;
                                }
                                arrayList2 = arrayList3;
                            } else {
                                str2 = str2 + String.format(CancelRequestTip.H("MDXv5"), "") + la;
                                arrayList2 = arrayList3;
                            }
                            arrayList2.set(0, str2);
                        }
                        if (i == 0 && countTrailingSpaces > 0) {
                            String replaceAll = str2.replaceAll(CancelRequestTip.H("755m"), "");
                            inlays.setRemoveBlank(true);
                            if (StringUtils.isEmpty(replaceAll)) {
                                it = it;
                                i++;
                            } else {
                                arrayList3.set(0, replaceAll);
                            }
                        }
                        if (i > 0 && CodeTipType.Inline.equals(next.getType()) && z2) {
                            if (size != 1 || !isEmpty) {
                                if (size > 1 && StringUtils.isNotBlank(str2) && isEmpty) {
                                    str2 = str2 + String.format(CancelRequestTip.H("MDXv5"), "") + zc;
                                }
                                arrayList = arrayList3;
                            } else {
                                str2 = str2 + String.format(EditorUtils.H("`.i/7"), "") + la;
                                arrayList = arrayList3;
                            }
                            arrayList.set(0, str2);
                        }
                        tipInlayRenderer = new TipInlayRenderer(editor, request, next.getType(), arrayList3);
                    }
                    Inlay<TipRenderer> inlay2 = null;
                    switch (next.getType()) {
                        case Inline:
                            do {
                            } while (0 != 0);
                            inlay = inlayModel.addInlineElement(next.getEditorOffset(), true, Integer.MAX_VALUE - i, tipInlayRenderer);
                            inlay2 = inlay;
                            break;
                        case AfterLineEnd:
                            inlay = inlayModel.addAfterLineEndElement(next.getEditorOffset(), true, tipInlayRenderer);
                            inlay2 = inlay;
                            break;
                        case Block:
                            inlay = inlayModel.addBlockElement(next.getEditorOffset(), true, false, Integer.MAX_VALUE - i, tipInlayRenderer);
                            inlay2 = inlay;
                            break;
                        default:
                            inlay = null;
                            break;
                    }
                    if (inlay != null) {
                        tipInlayRenderer.setInlay(inlay2);
                    }
                    i++;
                    it = it;
                }
            }
            try {
                String TA = TA(inlays);
                ResponseStreamDto.ResponseData pb = pb(inlays);
                MessageDto messageDto = (MessageDto) PluginWebsocketClient.AGENT_REQUEST.get(TA);
                if (pb != null) {
                    if (messageDto != null && !messageDto.getIsDisplay().get()) {
                        if (messageDto != null) {
                            messageDto.getIsDisplay().set(true);
                        }
                        PluginWebsocketClient.sendWsMessage(CommandEnum.LOG_DISPLAY, TA, editor.getProject());
                        f569long.info(EditorUtils.H("掾遌灊晟戎勚栄徏戏勛"));
                    }
                } else {
                    PluginWebsocketClient.sendWsMessage(CommandEnum.LOG_DISPLAY, TA, editor.getProject());
                    f569long.info(CancelRequestTip.H("揁遨瀄晊扸勷桮徾扖務"));
                }
            } catch (Exception e2) {
                f569long.error(EditorUtils.H("揣逗瀽昄扱劁桂忔镁诰～"), e2);
            }
            return true;
        }
        Aa(inlays, request, editor);
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.service.EditorManagerService
    public void cancelTipRequests(@NotNull Editor a) {
        if (a == null) {
            m283enum(16);
        }
        this.requestAlarm.lA();
        List list = (List) EditorUtil.f589enum.get(a);
        if (list == null || list.isEmpty()) {
            return;
        }
        f569long.debug("cancel requests: " + list.size());
        int size = list.size();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            EditorRequestService editorRequestService = (EditorRequestService) it.next();
            it = it;
            it.remove();
            editorRequestService.cancel();
        }
        ((RequestsCancelledService) ApplicationManager.getApplication().getMessageBus().syncPublisher(RequestsCancelledService.TOPIC)).requestsCancelled(size);
    }

    public void dispose() {
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void ma(boolean z, @NotNull Editor editor, @NotNull EditorRequestService contentRequest, CodeTipRequestType requestType, @Nullable Consumer<CodeInlayList> consumer) {
        int i;
        EditorManagerServiceImpl editorManagerServiceImpl;
        if (editor == null) {
            m283enum(19);
        }
        if (contentRequest == null) {
            m283enum(20);
        }
        f569long.info(CancelRequestTip.H("\u0002\u0019\u0012\u0017R\u0019\u0004\u0017G\u0013\u0004<8\f\u001a��T\u000b\u0007\u0004��\u000b\u0002"));
        if (!z) {
            try {
                i = AICodeSettingsState.getInstance().triggerTime.intValue();
                editorManagerServiceImpl = this;
            } catch (Exception unused) {
                i = 200;
                editorManagerServiceImpl = this;
            }
            editorManagerServiceImpl.requestAlarm.cancelAllAndAddRequest(() -> {
                if (contentRequest.isCancelled()) {
                    return;
                }
                Ka(editor, contentRequest, requestType, consumer);
            }, i);
            return;
        }
        this.requestAlarm.cancelAllAndAddRequest(() -> {
            if (!contentRequest.isCancelled()) {
                Ka(editor, contentRequest, requestType, consumer);
            }
        }, 50);
    }

    private void Db(@NotNull CodeInlayList inlays, @NotNull EditorRequestService request, @NotNull Editor editor, boolean z, @NotNull OperateActionEnum a) {
        if (inlays == null) {
            m283enum(27);
        }
        if (request == null) {
            m283enum(28);
        }
        if (editor == null) {
            m283enum(29);
        }
        if (a == null) {
            m283enum(30);
        }
        if (!editor.isDisposed()) {
            lc(inlays, request, editor, z, a);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.service.EditorManagerService
    @RequiresEdt
    @NotNull
    public List<TipRenderer> getInlays(@NotNull Editor editor, int startOffset, int a) {
        if (editor == null) {
            m283enum(17);
        }
        InlayModel inlayModel = editor.getInlayModel();
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(inlayModel.getInlineElementsInRange(startOffset, a));
        arrayList.addAll(inlayModel.getAfterLineEndElementsInRange(startOffset, a));
        arrayList.addAll(inlayModel.getBlockElementsInRange(startOffset, a));
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Inlay inlay = (Inlay) it.next();
            if (inlay.getRenderer() instanceof TipRenderer) {
                arrayList2.add((TipRenderer) inlay.getRenderer());
            }
        }
        if (arrayList2 == null) {
            m283enum(18);
        }
        return arrayList2;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public int countTrailingSpaces(String a) {
        int i = 0;
        int length = a.length() - 1;
        int i2 = length;
        while (length >= 0 && Character.isWhitespace(a.charAt(i2))) {
            i2--;
            length = i2;
            i++;
        }
        return i;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean fC(@NotNull CodeInlayList inlays, @NotNull EditorRequestService request, @NotNull Editor editor, boolean z, @NotNull OperateActionEnum a) {
        if (inlays == null) {
            m283enum(23);
        }
        if (request == null) {
            m283enum(24);
        }
        if (editor == null) {
            m283enum(25);
        }
        if (a == null) {
            m283enum(26);
        }
        if (!mc(request, editor)) {
            f569long.debug(EditorUtils.H("\u0017w=h4|ip:smu5��\ts<t?v*:6q86.[\u000f} o4z "));
            return false;
        }
        if (editor.isDisposed()) {
            return false;
        }
        return mB(inlays, request, editor, z, a);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.service.EditorManagerService
    @RequiresEdt
    public void disposeTips(@NotNull Editor editor, @NotNull OperateActionEnum a) {
        if (editor == null) {
            m283enum(9);
        }
        if (a == null) {
            m283enum(10);
        }
        if (isAvailable(editor) && !Cb(editor)) {
            if (f571for.booleanValue() && a == OperateActionEnum.CaretChange) {
                f571for = false;
                f577byte = "";
            }
            RequestResultList requestResultList = (RequestResultList) KEY_LAST_REQUEST.get(editor);
            if (!a.equals(OperateActionEnum.Applied)) {
                Project project = editor.getProject();
                String str = RequestTipServiceImpl.LATEST_RESPONSE_DATA.get(project);
                if (StringUtils.isNotBlank(str)) {
                    if (a == OperateActionEnum.EscReject) {
                        if (RequestTipServiceImpl.CODE_TIP_MAP.containsKey(str)) {
                            CodeTipRequestDto codeTipRequestDto = RequestTipServiceImpl.CODE_TIP_MAP.get(str);
                            Span parentSpan = codeTipRequestDto.getParentSpan();
                            parentSpan.setAttribute(SpanAttrEnum.COMPLETE_RESULT.getText(), codeTipRequestDto.getLastReplacementText());
                            parentSpan.end();
                            RequestTipServiceImpl.CODE_TIP_MAP.remove(str);
                        }
                        RequestTipServiceImpl.LAST_REQUEST.get(project).remove(str);
                        PluginWebsocketClient.sendWsMessage(CommandEnum.LOG_REJECT_ESC, str, project);
                        CACHE_KEY_LAST_REQUEST.set(editor, (Object) null);
                    } else {
                        String fastSimpleUUID = IdUtil.fastSimpleUUID();
                        MessageDto messageDto = new MessageDto(fastSimpleUUID, CommandEnum.LOG_REJECT.getType());
                        messageDto.setData(str);
                        Span buildWithCommand = OpenTelemetryUtil.buildWithCommand(messageDto.getCommand(), PluginWebsocketClient.class.getName());
                        buildWithCommand.setAttribute(SpanAttrEnum.COMMAND_ID.getText(), fastSimpleUUID);
                        buildWithCommand.setAttribute(SpanAttrEnum.COMPLETE_REJECT.getText(), str);
                        PluginWebsocketClient.sendWsMessageForCode(buildWithCommand, messageDto, project);
                    }
                    RequestTipServiceImpl.LATEST_RESPONSE_DATA.remove(project);
                }
            }
            if (a.isUserAction() && a.isResetLastRequest()) {
                KEY_LAST_REQUEST.set(editor, (Object) null);
            }
            if (requestResultList == null || requestResultList.getRequest().getOffset() != editor.getCaretModel().getOffset()) {
                cancelTipRequests(editor);
            }
            Pb(editor, () -> {
                ZB(getInlays(editor, 0, editor.getDocument().getTextLength()));
            });
        }
    }

    private String TA(@NotNull CodeInlayList a) {
        if (a == null) {
            m283enum(14);
        }
        String str = null;
        if (a instanceof AgentCodeTipList) {
            str = ((AgentCodeTipList) a).getRequestId();
        }
        return str;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private String zc() {
        String Na = Na(StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(CancelRequestTip.H(",$$\b\u0005\u0004c,\u0019\u0019\u0018\r!\u0006\u0012\u001f\u001e\u0014")), KeymapUtil.getFirstMouseShortcutText(EditorUtils.H("@\u0013F1~$>*f=V\u0018W+o9f7"))));
        String str = StrUtil.isNotBlank(Na) ? Na + "  全部采纳" : "";
        String Na2 = Na(StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(CancelRequestTip.H("\u0016\u001e\"\u000e\u0012\u0013\\\u0013\u001d\u001d\u000b\u001e-\b#(*\u0006\u0010\u0011!\u0006\u0012\u001f\u001e\u0014")), KeymapUtil.getFirstMouseShortcutText(EditorUtils.H("W\u0004B5D\u001e8,q*i'V(~.U\"^\u0004W+o9f7"))));
        return StrUtil.trim(str + "    " + (StrUtil.isNotBlank(Na2) ? Na2 + "  逐行采纳" : ""));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean PA(@NotNull Editor editor, RequestResultList lastRequest, Document a) {
        String substring;
        if (editor == null) {
            m283enum(13);
        }
        if (!AutoCodeGenerateListener.commandNameCtrlZ.get()) {
            CodeInlayList codeInlayList = (CodeInlayList) lastRequest.getInlayLists().last();
            int offset = lastRequest.getRequest().getOffset();
            int offset2 = editor.getCaretModel().getOffset();
            f569long.info("【xgz】lastLineSuffix: " + lastRequest.getRequest().getLineInfo().getLineSuffix());
            if (codeInlayList == null || codeInlayList.isEmpty() || offset > offset2) {
                return false;
            }
            String text = a.getText(TextRange.create(offset, offset2));
            String replacementText = codeInlayList.getReplacementText();
            if (codeInlayList.isRemoveBlank()) {
                replacementText = replacementText.replaceFirst(CancelRequestTip.H("\n\b6n"), "");
            }
            if (!replacementText.startsWith(text)) {
                return false;
            }
            substring = editor.getDocument().getText().substring(CodeCheckUtil.getCaretOffset(editor), CodeCheckUtil.getLineEndOffset(editor));
            if (AICodeStringUtil.notMatchSuffixIndex(substring, replacementText.substring(offset2 - offset)) <= 0) {
                int lineNumber = a.getLineNumber(offset2);
                ArrayList newArrayList = Lists.newArrayList(replacementText.substring(text.length()).split(EditorUtils.H("N")));
                AgentCodeTipList agentCodeTipList = new AgentCodeTipList(codeInlayList, (AgentCodeTip) codeInlayList.getAICodeTip(), lastRequest.getRequest());
                agentCodeTipList.setReplacementRange(new TextRange(codeInlayList.getReplacementRange().getStartOffset() + 1, codeInlayList.getReplacementRange().getEndOffset() + 1));
                if (offset2 - offset <= replacementText.length()) {
                    int lastIndexOf = text.lastIndexOf(CancelRequestTip.H(">O"));
                    int lastIndexOf2 = replacementText.lastIndexOf(EditorUtils.H("dN"));
                    boolean z = AutoCodeGenerateListener.isImitationDealFlag.get();
                    boolean z2 = false;
                    if (CollectionUtils.isNotEmpty(newArrayList)) {
                        String str = (String) newArrayList.get(newArrayList.size() - 1);
                        z2 = lastIndexOf == lastIndexOf2 && StringUtils.isNotEmpty(str) && str.contains(CancelRequestTip.H("8"));
                    }
                    boolean z3 = ((lastIndexOf == lastIndexOf2 && z2) || lastIndexOf2 > lastIndexOf) && lastIndexOf2 > -1 && lastIndexOf > -1 && z;
                    int i = lineNumber + 1;
                    int lineEndOffset = a.getLineEndOffset(i);
                    String substring2 = a.getText().substring(lineEndOffset - 1, lineEndOffset);
                    if (StringUtils.isNotEmpty(substring2) && substring2.endsWith(EditorUtils.H("9")) && z3) {
                        WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> {
                            try {
                                a.deleteString(a.getLineStartOffset(i), a.getLineEndOffset(i) + a.getLineSeparatorLength(i));
                                AutoCodeGenerateListener.isImitationDealFlag.set(false);
                            } catch (Exception e) {
                                f569long.error(CancelRequestTip.H("剁阩$\r\f\u0015膞勀畷扛6彥幟"), e);
                            }
                        });
                    }
                    agentCodeTipList.setReplacementText(replacementText.substring(offset2 - offset));
                    String[] split = agentCodeTipList.getReplacementText().split(CancelRequestTip.H("O"));
                    int i2 = 0;
                    Iterator<CodeEditorInlay> it = codeInlayList.iterator();
                    while (it.hasNext()) {
                        CodeEditorInlay next = it.next();
                        int i3 = i2;
                        next.setEditorOffset(offset2);
                        if (i3 != 0) {
                            String[] strArr = new String[split.length - 1];
                            System.arraycopy(split, 1, strArr, 0, split.length - 1);
                            ArrayList arrayList = new ArrayList(List.of((Object[]) strArr));
                            if (CodeTipType.Inline.equals(next.getType())) {
                                next.setLines(new ArrayList());
                            } else {
                                next.setLines(arrayList);
                            }
                        } else {
                            next.setLines(Collections.singletonList(split[0]));
                        }
                        i2++;
                        f569long.info(String.format(EditorUtils.H("たn*{かL\u001ae9H4i?c\ry8b\u0016\u001f\u0012Ce>x:7"), Integer.valueOf(i2), next.getLines()));
                        it = it;
                    }
                    Db(agentCodeTipList, lastRequest.getRequest(), editor, true, OperateActionEnum.Applied);
                    AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private ResponseStreamDto.ResponseData pb(@NotNull CodeInlayList a) {
        if (a == null) {
            m283enum(15);
        }
        ResponseStreamDto.ResponseData responseData = null;
        if (a instanceof AgentCodeTipList) {
            responseData = ((AgentCodeTipList) a).getData();
        }
        return responseData;
    }

    @RequiresBackgroundThread
    private void Ka(@NotNull Editor editor, @NotNull EditorRequestService request, CodeTipRequestType requestType, @Nullable Consumer<CodeInlayList> consumer) {
        if (editor == null) {
            m283enum(21);
        }
        if (request == null) {
            m283enum(22);
        }
        RequestTipService.getInstance().fetchTips(request, da(editor, request, consumer, System.currentTimeMillis()), editor, "", requestType);
    }

    private String HA(Editor a) {
        int offset = a.getCaretModel().getOffset();
        return a.getDocument().getText().substring(a.getDocument().getLineStartOffset(a.getDocument().getLineNumber(offset)), offset);
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    @Override // com.aicode.service.EditorManagerService
    @RequiresEdt
    public void editorChanged(@NotNull Editor editor, int offset, @NotNull CodeTipRequestType requestType, boolean z) {
        if (editor == null) {
            m283enum(11);
        }
        if (requestType == null) {
            m283enum(12);
        }
        if (editor.getProject() == null) {
            return;
        }
        Document document = editor.getDocument();
        int textLength = document.getTextLength();
        int offset2 = editor.getCaretModel().getOffset();
        if (textLength != this.f576float.intValue() && this.f573case.intValue() != offset2) {
            docChangeCount.incrementAndGet();
            this.f576float = Integer.valueOf(textLength);
            this.f573case = Integer.valueOf(offset2);
            f569long.info("当前docChangeCount统计结果:" + docChangeCount.get());
        }
        RequestResultList requestResultList = (RequestResultList) CACHE_KEY_LAST_REQUEST.get(editor);
        f569long.info(CancelRequestTip.H("\u0015\u0014\u0002\u001f\u0019\u0004\u0004/\r\u0002\u0002��\u000eJ��\f\f\b\u000e\u0007"));
        if (zB(document, offset, requestType, requestResultList) || fb(document)) {
            return;
        }
        cancelTipRequests(editor);
        if (!SC() && !f571for.booleanValue()) {
            if (QB(editor)) {
                disposeTips(editor, OperateActionEnum.Typing);
                CACHE_KEY_LAST_REQUEST.set(editor, (Object) null);
                return;
            }
            if (!requestType.isForced()) {
                if (document.getText().length() >= 8) {
                    f569long.info(CancelRequestTip.H("\u001b\u0015��\u0012\u0014M\u000e\u001f\u0014\u000eK\u0015\u0019*7��\t\u0011��J\b\u001aC��\u0014\u0014\u000f"));
                    if (requestResultList != null) {
                        try {
                            if (requestType.isUnforced() && CollectionUtils.isNotEmpty(requestResultList.getInlayLists()) && PA(editor, requestResultList, document)) {
                                if (AutoCodeGenerateListener.isImitationBuryingPoint.get()) {
                                    String TA = TA((CodeInlayList) requestResultList.getInlayLists().last());
                                    if (StringUtil.isNotEmpty(TA)) {
                                        PluginWebsocketClient.sendWsMessage(CommandEnum.LOG_IMITATIVE_WRITE, TA, editor.getProject());
                                    }
                                    AutoCodeGenerateListener.isImitationBuryingPoint.set(false);
                                    return;
                                }
                                return;
                            }
                        } catch (Throwable th) {
                            f569long.info(EditorUtils.H("7w(d*b$U\u000f> q*p6"));
                        }
                    }
                    if (AutoCodeGenerateListener.commandNameCtrlZ.get()) {
                        AutoCodeGenerateListener.commandNameCtrlZ.set(false);
                        disposeTips(editor, OperateActionEnum.Typing);
                        return;
                    }
                    EditorRequestService createRequest = RequestTipService.getInstance().createRequest(editor, offset, TipType.GhostText);
                    if (createRequest != null) {
                        EditorUtil.addEditorRequest(editor, createRequest);
                        RequestResultList requestResultList2 = new RequestResultList(createRequest);
                        KEY_LAST_REQUEST.set(editor, requestResultList2);
                        disposeTips(editor, OperateActionEnum.Typing);
                        if (EditorSupport.isEditorCodeTipsSupported(editor)) {
                            ma(z, editor, createRequest, requestType, a -> {
                                if (!fC(a, createRequest, editor, false, OperateActionEnum.Typing)) {
                                    return;
                                }
                                CACHE_KEY_LAST_REQUEST.set(editor, requestResultList2);
                            });
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            f569long.info(EditorUtils.H("g;w?uzC\u0014r(!9j3j-u?smX\u0018>#l*|!"));
            disposeTips(editor, OperateActionEnum.Typing);
            ((RejectTipMessage) ApplicationManager.getApplication().getMessageBus().syncPublisher(RejectTipMessage.TOPIC)).automaticCodeTipsRejected(null);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void lc(@NotNull CodeInlayList inlays, @NotNull EditorRequestService request, @NotNull Editor editor, boolean z, @NotNull OperateActionEnum a) {
        TipInlayRenderer tipInlayRenderer;
        Inlay<TipRenderer> inlay;
        if (inlays == null) {
            m283enum(35);
        }
        if (request == null) {
            m283enum(36);
        }
        if (editor == null) {
            m283enum(37);
        }
        if (a == null) {
            m283enum(38);
        }
        if (z) {
            disposeTips(editor, a);
        }
        InlayModel inlayModel = editor.getInlayModel();
        int i = 0;
        Iterator<CodeEditorInlay> it = inlays.iterator();
        while (it.hasNext()) {
            CodeEditorInlay next = it.next();
            if (next.isEmptyTip()) {
                it = it;
            } else {
                if (i != 0 || inlays.getReplacementRange().getLength() <= 0) {
                    ArrayList arrayList = new ArrayList(next.getLines());
                    int countTrailingSpaces = countTrailingSpaces(HA(editor));
                    String str = (String) arrayList.get(0);
                    if (i == 0 && countTrailingSpaces > 0) {
                        arrayList.set(0, str.trim());
                    }
                    tipInlayRenderer = new TipInlayRenderer(editor, request, next.getType(), arrayList);
                } else {
                    f574final = null;
                    tipInlayRenderer = new TipInlayRenderer(editor, request, next.getType(), TipInlayRenderer.replaceLeadingTabs(next.getLines(), request));
                }
                Inlay<TipRenderer> inlay2 = null;
                switch (next.getType()) {
                    case Inline:
                        do {
                        } while (0 != 0);
                        inlay = Za(request, editor, inlays, tipInlayRenderer, i);
                        inlay2 = inlay;
                        break;
                    case AfterLineEnd:
                        inlay = inlayModel.addAfterLineEndElement(next.getEditorOffset(), false, tipInlayRenderer);
                        inlay2 = inlay;
                        break;
                    case Block:
                        inlay = inlayModel.addBlockElement(next.getEditorOffset(), true, false, Integer.MAX_VALUE - i, tipInlayRenderer);
                        inlay2 = inlay;
                        break;
                    default:
                        inlay = null;
                        break;
                }
                if (inlay != null) {
                    tipInlayRenderer.setInlay(inlay2);
                }
                i++;
                it = it;
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private String Na(String a) {
        Iterator<Map.Entry<String, String>> it = keyMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            a = a.replace(next.getKey(), next.getValue());
            it = it;
        }
        return a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static List<String> findCommonContinuousSubstrings(String a, String a2) {
        int indexOf;
        ArrayList arrayList = new ArrayList();
        int length = a.length();
        a2.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 1;
            int i4 = i3;
            while (i3 <= length) {
                String substring = a.substring(i2, i4);
                if (a2.contains(substring) && (indexOf = a2.indexOf(substring)) > -1 && LC(a2, substring, indexOf) && !arrayList.contains(substring)) {
                    arrayList.add(substring);
                }
                i4++;
                i3 = i4;
            }
            i2++;
            i = i2;
        }
        ListIterator listIterator = arrayList.listIterator();
        while (listIterator.hasNext()) {
            String str = (String) listIterator.next();
            if (arrayList.stream().anyMatch(a3 -> {
                return !a3.equals(str) && (a3.startsWith(str) || a3.endsWith(str));
            })) {
                listIterator.remove();
            }
        }
        return arrayList;
    }

    @Override // com.aicode.service.EditorManagerService
    @RequiresEdt
    public void acceptTip(@NotNull Project project, @NotNull Editor editor, @NotNull EditorRequestService request, @NotNull CodeInlayList a) {
        if (project == null) {
            m283enum(5);
        }
        if (editor == null) {
            m283enum(6);
        }
        if (request == null) {
            m283enum(7);
        }
        if (a == null) {
            m283enum(8);
        }
        a.getReplacementRange();
        String replacementText = a.getReplacementText();
        String TA = TA(a);
        String fastSimpleUUID = IdUtil.fastSimpleUUID();
        MessageDto messageDto = new MessageDto(fastSimpleUUID, CommandEnum.LOG_ACCEPT.getType());
        messageDto.setData(TA);
        Span buildWithCommand = OpenTelemetryUtil.buildWithCommand(messageDto.getCommand(), PluginWebsocketClient.class.getName());
        buildWithCommand.setAttribute(SpanAttrEnum.COMMAND_ID.getText(), fastSimpleUUID);
        buildWithCommand.setAttribute(SpanAttrEnum.COMPLETE_ACCEPT.getText(), TA);
        WriteCommandAction.runWriteCommandAction(project, "Apply " + BasicActionsBundle.message(CancelRequestTip.H("\u0011��\u001d\u0018\u001dI\u0006\b\u0002\")\fG1\u0010\u0001\u001c\n\u0017&\u0004\u0013\u000e\u000e\u000f3\u0006\u001f\u0005 ~\u0019\b\f��"), new Object[0]) + " Suggestion", BasicActionsBundle.message(EditorUtils.H("\u0002`&h6<(},y)dte\u001f\u007f9n(D=n(\u007f%Q?U\u0014nkw=g0"), new Object[0]), () -> {
            String substring;
            String str;
            try {
                if (project.isDisposed()) {
                    return;
                }
                Document document = editor.getDocument();
                try {
                    f578enum.set(document, true);
                    f578enum.set(document, (Object) null);
                    int offset = editor.getCaretModel().getOffset();
                    int offset2 = request.getOffset();
                    if (a.isRemoveBlank()) {
                        str = replacementText.trim().substring(offset - offset2);
                        substring = str;
                    } else {
                        substring = replacementText.substring(offset - offset2);
                        str = substring;
                    }
                    try {
                    } catch (Throwable th) {
                        f569long.warn("[acceptTip]: 去除多余括号\n" + th);
                    }
                    if (StringUtils.isEmpty(str)) {
                        return;
                    }
                    String MC = MC(editor);
                    if (StringUtils.isNotEmpty(MC)) {
                        document.deleteString(offset, offset + MC.length());
                    }
                    document.insertString(offset, substring);
                    editor.getCaretModel().moveToOffset(offset + substring.length());
                    acceptCount(editor, offset, offset + substring.length(), CodeCollectEnum.GENERATE);
                    FileDocumentManager.getInstance().saveDocument(document);
                    if (StringUtil.isNotEmpty(TA)) {
                        f569long.info("accept code log " + TA);
                        PluginWebsocketClient.sendWsMessageForCode(buildWithCommand, messageDto, project);
                        RequestTipServiceImpl.LATEST_RESPONSE_DATA.remove(project);
                    }
                } catch (Throwable th2) {
                    f578enum.set(document, (Object) null);
                    throw th2;
                }
            } catch (Exception e) {
                buildWithCommand.recordException(e);
                buildWithCommand.end();
            }
        }, new PsiFile[0]);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private String la() {
        String Na = Na(StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(CancelRequestTip.H(",$$\b\u0005\u0004c,\u0019\u0019\u0018\r!\u0006\u0004\t\u001c\u0016")), KeymapUtil.getFirstMouseShortcutText(EditorUtils.H("@\u0013F1~$>*f=V\u0018W+o9f7"))));
        String str = StrUtil.isNotBlank(Na) ? Na + "  全部采纳" : "";
        String Na2 = Na(StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(CancelRequestTip.H("7?1\u001d\t\bI\u0006\u0011\u0011!4>\u0006\u0006\u0010!\u0006\u0004\t\u001c\u0016")), KeymapUtil.getFirstMouseShortcutText(EditorUtils.H("a2U\"e?+?j1|2A\"H\u0005W+o9f7"))));
        return StrUtil.trim(str + "    " + (StrUtil.isNotBlank(Na2) ? Na2 + "  逐词采纳" : ""));
    }

    private String MC(Editor a) {
        int offset = a.getCaretModel().getOffset();
        return a.getDocument().getText().substring(offset, a.getDocument().getLineEndOffset(a.getDocument().getLineNumber(offset)));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static Stack<Integer> findMatchingRightParentheses(String a) {
        Stack stack = new Stack();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int length = a.length() - 1;
        int i = length;
        while (length >= 0) {
            char charAt = a.charAt(i);
            if (charAt == ')' || charAt == '}' || charAt == ']') {
                stack.push(Integer.valueOf(i));
            } else if (charAt != '\"') {
                if (charAt == '\'') {
                    arrayList2.add(Integer.valueOf(i));
                } else if ((charAt == '(' || charAt == '{' || charAt == '[') && !stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                arrayList.add(Integer.valueOf(i));
            }
            i--;
            length = i;
        }
        if (CollectionUtils.isNotEmpty(arrayList) && arrayList.size() % 2 != 0) {
            stack.push((Integer) arrayList.get(arrayList.size() - 1));
        }
        if (CollectionUtils.isNotEmpty(arrayList2) && arrayList2.size() % 2 != 0) {
            stack.push((Integer) arrayList2.get(arrayList2.size() - 1));
        }
        Stack<Integer> stack2 = new Stack<>();
        while (!stack.isEmpty()) {
            stack2.push((Integer) stack.pop());
        }
        return stack2;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.service.EditorManagerService
    public boolean hasPreviousInlaySet(@NotNull Editor a) {
        if (a == null) {
            m283enum(52);
        }
        if (a != null) {
            RequestResultList requestResultList = (RequestResultList) KEY_LAST_REQUEST.get(a);
            return requestResultList != null && requestResultList.hasPrev();
        }
        throw new RuntimeException();
    }

    private boolean Cb(@NotNull Editor a) {
        if (a == null) {
            m283enum(39);
        }
        return ((Boolean) f575try.get(a)).booleanValue();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void ZB(List<TipRenderer> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Iterator<TipRenderer> it = list.iterator();
        while (it.hasNext()) {
            Inlay<TipRenderer> inlay = it.next().getInlay();
            if (inlay != null) {
                Disposer.dispose(inlay);
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.EditorManagerService
    public void showNextInlaySet(@NotNull Editor a) {
        if (a == null) {
            m283enum(53);
        }
        RequestResultList requestResultList = (RequestResultList) KEY_LAST_REQUEST.get(a);
        if (requestResultList != null) {
            CodeInlayList nextCodeTip = requestResultList.getNextCodeTip();
            if (nextCodeTip == null) {
                if (mc(requestResultList.getRequest(), a)) {
                    requestResultList.setHasOnDemandCodeTips();
                    oc(a, requestResultList);
                    return;
                }
                return;
            }
            fC(nextCodeTip, requestResultList.getRequest(), a, true, OperateActionEnum.Cycling);
            return;
        }
        f569long.debug(EditorUtils.H(",c?s?N\u000f6?d+p;i50>x,L��w)b:s!"));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private boolean QB(@NotNull Editor a) {
        if (a == null) {
            m283enum(48);
        }
        return a.getCaretModel().getCaretCount() > 1 || a.getSelectionModel().hasSelection();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean Xc(@NotNull Editor editor, @NotNull List<CodeInlayList> list) {
        if (editor == null) {
            m283enum(44);
        }
        if (list == null) {
            m283enum(45);
        }
        RequestResultList requestResultList = (RequestResultList) KEY_LAST_REQUEST.get(editor);
        if (requestResultList != null) {
            requestResultList.resetInlays();
            Iterator<CodeInlayList> it = list.iterator();
            while (it.hasNext()) {
                CodeInlayList next = it.next();
                it = it;
                requestResultList.addInlays(next);
            }
        }
        return requestResultList == null;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.service.EditorManagerService
    public boolean hasNextInlaySet(@NotNull Editor a) {
        if (a == null) {
            m283enum(51);
        }
        if (a != null) {
            RequestResultList requestResultList = (RequestResultList) KEY_LAST_REQUEST.get(a);
            if (requestResultList == null) {
                return false;
            }
            return requestResultList.hasNext();
        }
        throw new RuntimeException();
    }

    @Override // com.aicode.service.EditorManagerService
    public void showPreviousInlaySet(@NotNull Editor a) {
        CodeInlayList prevCodeTip;
        if (a == null) {
            m283enum(54);
        }
        RequestResultList requestResultList = (RequestResultList) KEY_LAST_REQUEST.get(a);
        if (requestResultList == null || (prevCodeTip = requestResultList.getPrevCodeTip()) == null) {
            return;
        }
        fC(prevCodeTip, requestResultList.getRequest(), a, true, OperateActionEnum.Cycling);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean lb(@NotNull Editor editor, int a) {
        if (editor == null) {
            m283enum(49);
        }
        Project project = editor.getProject();
        if (project == null) {
            return false;
        }
        Document document = editor.getDocument();
        if (PsiDocumentManager.getInstance(project).getPsiFile(document) != null) {
            LineInfo.create(document, a);
            String substring = document.getText().substring(a);
            if (!substring.isBlank() && !((String[]) substring.lines().toArray(a2 -> {
                return new String[a2];
            }))[0].matches(CancelRequestTip.H(":<60@RT~eaOJ;L"))) {
                return true;
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private boolean SC() {
        String currentCommandName = CommandProcessor.getInstance().getCurrentCommandName();
        return currentCommandName != null && f570super.contains(currentCommandName);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private Inlay<TipRenderer> Za(EditorRequestService a, Editor a2, CodeInlayList a3, TipInlayRenderer a4, int a5) {
        boolean matches;
        TipInlayRenderer a6;
        Inlay<TipRenderer> addInlineElement;
        CodeEditorInlay codeEditorInlay = a3.getInlays().get(a5);
        String str = codeEditorInlay.getLines().get(0);
        Document document = a2.getDocument();
        String substring = document.getText().substring(a2.getCaretModel().getOffset(), document.getLineEndOffset(document.getLineNumber(a2.getCaretModel().getOffset())));
        a.getLineInfo().getLineSuffix();
        matches = Pattern.compile(CancelRequestTip.H("7(]4\u001bt\u0002\u001eA")).matcher(substring).matches();
        if (matches) {
            substring = substring.replaceAll(EditorUtils.H("d"), "");
        }
        List<Pair<Integer, String>> matchSuffixSection = AICodeStringUtil.matchSuffixSection(substring, str);
        InlayModel inlayModel = a2.getInlayModel();
        if (matchSuffixSection != null && matchSuffixSection.size() != 0) {
            int i = 0;
            int i2 = 0;
            while (i < matchSuffixSection.size()) {
                Pair<Integer, String> pair = matchSuffixSection.get(i2);
                Integer num = (Integer) pair.getFirst();
                if (!StringUtils.isEmpty((CharSequence) pair.second) && (addInlineElement = inlayModel.addInlineElement(codeEditorInlay.getEditorOffset() + num.intValue(), true, (Integer.MAX_VALUE - a5) - num.intValue(), (a6 = new TipInlayRenderer(a2, a, codeEditorInlay.getType(), List.of((String) pair.second))))) != null) {
                    a6.setInlay(addInlineElement);
                }
                i2++;
                i = i2;
            }
            return null;
        }
        if (!substring.equals(str)) {
            return inlayModel.addInlineElement(codeEditorInlay.getEditorOffset(), true, Integer.MAX_VALUE - a5, a4);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v51 */
    /* JADX WARN: Type inference failed for: r0v52 */
    /* JADX WARN: Type inference failed for: r0v82 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.EditorManagerService
    @RequiresEdt
    public boolean acceptTipForLine(@NotNull Editor a) {
        if (a == null) {
            m283enum(55);
        }
        if (!a.isDisposed()) {
            Project project = a.getProject();
            if (project != null && !project.isDisposed()) {
                if (Cb(a)) {
                    f569long.warn(EditorUtils.H("\u0006s':22(d?z4!3N\u0017w4rzr6s-ukf?U\u0002{6p1q#"));
                    return false;
                }
                RequestResultList requestResultList = (RequestResultList) CACHE_KEY_LAST_REQUEST.get(a);
                if (requestResultList == null) {
                    return false;
                }
                if (((CodeInlayList) requestResultList.getInlayLists().last()).getData() != null) {
                    Nc(a, requestResultList, a.getDocument());
                }
                CodeInlayList currentCodeTip = requestResultList.getCurrentCodeTip();
                if (currentCodeTip != null && !currentCodeTip.getInlays().isEmpty()) {
                    String replacementText = currentCodeTip.getReplacementText();
                    CodeEditorInlay codeEditorInlay = currentCodeTip.getInlays().get(0);
                    List<String> lines = codeEditorInlay.getLines();
                    if (!CollectionUtils.isEmpty(lines)) {
                        disposeTips(a, OperateActionEnum.Applied);
                        AutoCodeGenerateListener.ignoreApply.set(true);
                        String str = lines.get(0);
                        boolean isRemoveBlank = ((CodeInlayList) requestResultList.getInlayLists().last()).isRemoveBlank();
                        int offset = a.getCaretModel().getOffset();
                        int offset2 = requestResultList.getRequest().getOffset();
                        String text = a.getDocument().getText(TextRange.create(offset2, offset));
                        String substring = replacementText.substring(offset - offset2);
                        if (offset == offset2) {
                            int indexOf = substring.indexOf(CancelRequestTip.H("k"));
                            str = indexOf > -1 ? substring.substring(0, indexOf) : substring;
                        }
                        String HA = HA(a);
                        EditorManagerServiceImpl editorManagerServiceImpl = (!StringUtils.isNotBlank(HA) || countTrailingSpaces(HA) <= 0 || countLeadingSpaces(str) <= 0) ? 0 : 1;
                        if (isRemoveBlank && CodeTipType.Inline.equals(codeEditorInlay.getType()) && StringUtils.isNotBlank(str) && substring.startsWith(str) && editorManagerServiceImpl != null) {
                            str = str.replaceFirst(EditorUtils.H("]\u0004lo"), "");
                        }
                        if (isRemoveBlank) {
                            replacementText = replacementText.trim();
                        }
                        List<String> arrayList = new ArrayList();
                        if (StringUtils.isNotBlank(substring)) {
                            arrayList = Ba(substring, str);
                        }
                        String str2 = "";
                        if (CollectionUtils.isNotEmpty(arrayList)) {
                            str2 = arrayList.get(0);
                        }
                        vc(project, a, str, str2, replacementText.equals(text + str));
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        f569long.warn(CancelRequestTip.H("\u0011\u0014\u0019$?\u001fM\u0015\u00180'\n\u000f\u000fV,!\u001e\u001d\u0005\u0019\u0004\u0005"));
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean OC(@NotNull Editor editor, int a) {
        if (editor == null) {
            m283enum(50);
        }
        Project project = editor.getProject();
        if (project != null) {
            Document document = editor.getDocument();
            if (PsiDocumentManager.getInstance(project).getPsiFile(document) != null) {
                try {
                    LineInfo.create(document, a);
                    String substring = document.getText().substring(a);
                    if (substring.isBlank()) {
                        return false;
                    }
                    return !((String[]) substring.lines().toArray(a2 -> {
                        return new String[a2];
                    }))[0].isBlank();
                } catch (Exception e) {
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean zB(@NotNull Document document, int requestOffset, @NotNull CodeTipRequestType requestType, @Nullable RequestResultList a) {
        if (document == null) {
            m283enum(46);
        }
        if (requestType == null) {
            m283enum(47);
        }
        if (a != null && !requestType.isForcedOrManual()) {
            if (a.getRequest().getOffset() == requestOffset && a.getRequest().getDocumentModificationSequence() == EditorUtil.getDocumentModificationStamp(document)) {
                return true;
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void Pb(@NotNull Editor editor, @NotNull Runnable a) {
        if (editor == null) {
            m283enum(40);
        }
        if (a == null) {
            m283enum(41);
        }
        if (!f572if && ((Boolean) f575try.get(editor)).booleanValue()) {
            throw new AssertionError();
        }
        try {
            f575try.set(editor, true);
            a.run();
            f575try.set(editor, (Object) null);
        } catch (Throwable th) {
            f575try.set(editor, (Object) null);
            throw th;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean fb(Document a) {
        String[] strArr = AICodeSettingsState.getInstance().codeCompleteDisableLang;
        VirtualFile file = FileDocumentManager.getInstance().getFile(a);
        if (file != null) {
            String extension = file.getExtension();
            int length = strArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                if (!strArr[i2].toLowerCase().equals(extension)) {
                    i2++;
                    i = i2;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean mc(@NotNull EditorRequestService request, @NotNull Editor a) {
        if (request == null) {
            m283enum(42);
        }
        if (a == null) {
            m283enum(43);
        }
        if (request.getRequestId() == RequestId.currentRequestId()) {
            RequestResultList requestResultList = (RequestResultList) KEY_LAST_REQUEST.get(a);
            return requestResultList != null && requestResultList.getRequest().equalsRequest(request);
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean qa(@NotNull Editor editor, RequestResultList lastRequest, Document document, boolean z) {
        String substring;
        if (editor == null) {
            m283enum(64);
        }
        CodeInlayList codeInlayList = (CodeInlayList) lastRequest.getInlayLists().last();
        int offset = lastRequest.getRequest().getOffset();
        int offset2 = editor.getCaretModel().getOffset();
        f569long.info("【xgz】lastLineSuffix: " + lastRequest.getRequest().getLineInfo().getLineSuffix());
        if (codeInlayList != null && !codeInlayList.isEmpty() && offset <= offset2) {
            String text = document.getText(TextRange.create(offset, offset2));
            String text2 = document.getText(TextRange.create(offset, offset2 + 1));
            boolean z2 = StringUtils.isNotEmpty(text) && text.endsWith(EditorUtils.H("?"));
            boolean z3 = StringUtils.isNotEmpty(text2) && CancelRequestTip.H("\u0018").equals(text2);
            if (z2 && z3) {
                return false;
            }
            String replacementText = codeInlayList.getReplacementText();
            if (z) {
                replacementText = replacementText.trim();
            }
            if (replacementText.startsWith(text)) {
                AgentCodeTipList agentCodeTipList = new AgentCodeTipList(codeInlayList, (AgentCodeTip) codeInlayList.getAICodeTip(), lastRequest.getRequest());
                agentCodeTipList.setReplacementRange(new TextRange(codeInlayList.getReplacementRange().getStartOffset() + 1, codeInlayList.getReplacementRange().getEndOffset() + 1));
                if (offset2 - offset > replacementText.length()) {
                    return false;
                }
                agentCodeTipList.setReplacementText(replacementText.substring(offset2 - offset));
                text.contains(EditorUtils.H("dN"));
                String replacementText2 = agentCodeTipList.getReplacementText();
                if (!StringUtils.isBlank(replacementText2)) {
                    substring = editor.getDocument().getText().substring(CodeCheckUtil.getCaretOffset(editor), CodeCheckUtil.getLineEndOffset(editor));
                    if (replacementText2.equals(substring)) {
                        return false;
                    }
                    String[] split = replacementText2.split(CancelRequestTip.H("o"));
                    int i = 0;
                    Iterator<CodeEditorInlay> it = codeInlayList.iterator();
                    while (it.hasNext()) {
                        CodeEditorInlay next = it.next();
                        next.setEditorOffset(offset2);
                        int size = codeInlayList.getInlays().size() - 1;
                        if (i == 0) {
                            next.setLines(Collections.singletonList(split[0]));
                        } else {
                            String str = split[i >= split.length ? split.length - 1 : i];
                            String[] strArr = new String[split.length - 1];
                            System.arraycopy(split, 1, strArr, 0, split.length - 1);
                            ArrayList arrayList = new ArrayList(List.of((Object[]) strArr));
                            arrayList.removeIf(a -> {
                                return StringUtils.isBlank(a);
                            });
                            if (CodeTipType.Inline.equals(next.getType())) {
                                next.setLines(new ArrayList());
                            } else {
                                next.setLines(arrayList);
                            }
                        }
                        i++;
                        f569long.info(String.format(EditorUtils.H("たn*{かL\u001ae9H4i?c\ry8b\u0016\u001f\u0012Ce>x:7"), Integer.valueOf(i), next.getLines()));
                        it = it;
                    }
                    if (!editor.isDisposed()) {
                        NC(agentCodeTipList, lastRequest.getRequest(), editor, true, OperateActionEnum.Applied);
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private void oc(@NotNull Editor editor, @NotNull RequestResultList a) {
        if (editor == null) {
            m283enum(57);
        }
        if (a == null) {
            m283enum(58);
        }
        EditorRequestService request = a.getRequest();
        ma(true, editor, request, CodeTipRequestType.Manual, codeInlayList -> {
            CodeInlayList nextCodeTip = a.getNextCodeTip();
            if (nextCodeTip == null) {
                return;
            }
            fC(nextCodeTip, request, editor, true, OperateActionEnum.Cycling);
        });
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void NC(@NotNull CodeInlayList inlays, @NotNull EditorRequestService request, @NotNull Editor editor, boolean z, @NotNull OperateActionEnum a) {
        TipInlayRenderer tipInlayRenderer;
        Inlay<TipRenderer> inlay;
        if (inlays == null) {
            m283enum(65);
        }
        if (request == null) {
            m283enum(66);
        }
        if (editor == null) {
            m283enum(67);
        }
        if (a == null) {
            m283enum(68);
        }
        if (z) {
            disposeTips(editor, a);
        }
        InlayModel inlayModel = editor.getInlayModel();
        int i = 0;
        Iterator<CodeEditorInlay> it = inlays.iterator();
        while (it.hasNext()) {
            CodeEditorInlay next = it.next();
            if (next.isEmptyTip()) {
                it = it;
            } else {
                if (i == 0 && inlays.getReplacementRange().getLength() > 0) {
                    f574final = null;
                    List<String> replaceLeadingTabs = TipInlayRenderer.replaceLeadingTabs(next.getLines(), request);
                    String str = replaceLeadingTabs.get(0);
                    if (inlays.getReplacementRange().getLength() > TipInlayRenderer.ya(str, 0, str.length(), true)) {
                        replaceLeadingTabs.set(0, str);
                    } else {
                        replaceLeadingTabs.set(0, str);
                    }
                    tipInlayRenderer = new TipInlayRenderer(editor, request, next.getType(), replaceLeadingTabs);
                } else {
                    ArrayList arrayList = new ArrayList(next.getLines());
                    int countTrailingSpaces = countTrailingSpaces(HA(editor));
                    String str2 = (String) arrayList.get(0);
                    if (i == 0 && countTrailingSpaces > 0) {
                        arrayList.set(0, str2.trim());
                    }
                    tipInlayRenderer = new TipInlayRenderer(editor, request, next.getType(), arrayList);
                }
                Inlay<TipRenderer> inlay2 = null;
                switch (next.getType()) {
                    case Inline:
                        do {
                        } while (0 != 0);
                        String str3 = next.getLines().get(0);
                        Document document = editor.getDocument();
                        String substring = document.getText().substring(editor.getCaretModel().getOffset(), document.getLineEndOffset(document.getLineNumber(editor.getCaretModel().getOffset())));
                        List<Pair<Integer, String>> matchSuffixSection = AICodeStringUtil.matchSuffixSection(substring, str3);
                        if (matchSuffixSection != null && matchSuffixSection.size() != 0) {
                            int i2 = 0;
                            int i3 = 0;
                            while (i2 < matchSuffixSection.size()) {
                                Pair<Integer, String> pair = matchSuffixSection.get(i3);
                                Integer num = (Integer) pair.getFirst();
                                if (!StringUtils.isEmpty((CharSequence) pair.second)) {
                                    tipInlayRenderer = new TipInlayRenderer(editor, request, next.getType(), List.of((String) pair.second));
                                    Inlay<TipRenderer> addInlineElement = inlayModel.addInlineElement(next.getEditorOffset() + num.intValue(), true, (Integer.MAX_VALUE - i) - num.intValue(), tipInlayRenderer);
                                    if (addInlineElement != null) {
                                        tipInlayRenderer.setInlay(addInlineElement);
                                    }
                                }
                                i3++;
                                i2 = i3;
                            }
                            inlay = null;
                            inlay2 = null;
                            break;
                        } else if (!substring.equals(str3)) {
                            inlay = inlayModel.addInlineElement(next.getEditorOffset(), true, Integer.MAX_VALUE - i, tipInlayRenderer);
                            inlay2 = inlay;
                            break;
                        }
                        break;
                    case AfterLineEnd:
                        inlay = inlayModel.addAfterLineEndElement(next.getEditorOffset(), false, tipInlayRenderer);
                        inlay2 = inlay;
                        break;
                    case Block:
                        inlay = inlayModel.addBlockElement(next.getEditorOffset(), true, false, Integer.MAX_VALUE - i, tipInlayRenderer);
                        inlay2 = inlay;
                        break;
                }
                inlay = null;
                if (inlay != null) {
                    tipInlayRenderer.setInlay(inlay2);
                }
                i++;
                it = it;
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.EditorManagerService
    public boolean acceptWordTip(@NotNull Editor a) {
        RequestResultList requestResultList;
        CodeInlayList currentCodeTip;
        if (a == null) {
            m283enum(59);
        }
        f569long.info(EditorUtils.H("迅儠逓讕釘绷"));
        if (a.isDisposed()) {
            f569long.warn(CancelRequestTip.H("\u0002\u0003\u000e\u0015\u000e\u0006T\u0011\u001c!6\n\u000f\u000fV\u0004\t# \u0002\u001e\u0011\u0010"));
            return false;
        }
        Project project = a.getProject();
        if (project == null || project.isDisposed()) {
            return false;
        }
        if (!Cb(a) && (requestResultList = (RequestResultList) CACHE_KEY_LAST_REQUEST.get(a)) != null && (currentCodeTip = requestResultList.getCurrentCodeTip()) != null) {
            disposeTips(a, OperateActionEnum.Applied);
            AutoCodeGenerateListener.ignoreApply.set(true);
            if (f571for.booleanValue() && !f577byte.isEmpty()) {
                currentCodeTip.setReplacementText(f577byte);
                f571for = false;
                f577byte = "";
            }
            acceptWordTip(project, a, requestResultList.getRequest(), currentCodeTip);
            return true;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean Nc(@NotNull Editor editor, RequestResultList lastRequest, Document a) {
        if (editor == null) {
            m283enum(56);
        }
        CodeInlayList codeInlayList = (CodeInlayList) lastRequest.getInlayLists().last();
        int offset = lastRequest.getRequest().getOffset();
        int offset2 = editor.getCaretModel().getOffset();
        f569long.info("【逐行采纳】lastLineSuffix: " + lastRequest.getRequest().getLineInfo().getLineSuffix());
        if (codeInlayList != null && !codeInlayList.isEmpty() && offset <= offset2) {
            String text = a.getText(TextRange.create(offset, offset2));
            String replacementText = codeInlayList.getReplacementText();
            if (codeInlayList.isRemoveBlank()) {
                replacementText = replacementText.replaceFirst(EditorUtils.H("]\u0004lo"), "");
            }
            if (replacementText.startsWith(text)) {
                AgentCodeTipList agentCodeTipList = new AgentCodeTipList(codeInlayList, (AgentCodeTip) codeInlayList.getAICodeTip(), lastRequest.getRequest());
                agentCodeTipList.setReplacementRange(new TextRange(codeInlayList.getReplacementRange().getStartOffset() + 1, codeInlayList.getReplacementRange().getEndOffset() + 1));
                if (offset2 - offset <= replacementText.length()) {
                    agentCodeTipList.setReplacementText(replacementText.substring(offset2 - offset));
                    String[] split = agentCodeTipList.getReplacementText().split(CancelRequestTip.H("m"));
                    int i = 0;
                    int i2 = 0;
                    Iterator<CodeEditorInlay> it = codeInlayList.iterator();
                    while (it.hasNext()) {
                        CodeEditorInlay next = it.next();
                        int i3 = i;
                        next.setEditorOffset(offset2);
                        if (i3 != 0) {
                            String[] strArr = new String[split.length - 1];
                            System.arraycopy(split, 1, strArr, 0, split.length - 1);
                            ArrayList arrayList = new ArrayList(List.of((Object[]) strArr));
                            if (!CodeTipType.Inline.equals(next.getType())) {
                                if (!CodeTipType.Block.equals(next.getType())) {
                                    next.setLines(arrayList);
                                } else {
                                    if (i2 < 1) {
                                        next.setLines(arrayList);
                                    } else {
                                        next.setLines(new ArrayList());
                                    }
                                    i2++;
                                }
                            } else {
                                next.setLines(new ArrayList());
                            }
                        } else {
                            next.setLines(Collections.singletonList(split[0]));
                        }
                        i++;
                        f569long.info(String.format(EditorUtils.H("〄遟衚醊纲かL\u001ae9H4i?c\ry8b\u0016\u001f\u0012Ce>x:7"), Integer.valueOf(i), next.getLines()));
                        it = it;
                    }
                    if (!editor.isDisposed()) {
                        bA(agentCodeTipList, lastRequest.getRequest(), editor, true, OperateActionEnum.Applied);
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void bA(@NotNull CodeInlayList inlays, @NotNull EditorRequestService request, @NotNull Editor editor, boolean z, @NotNull OperateActionEnum a) {
        TipInlayRenderer tipInlayRenderer;
        Inlay<TipRenderer> inlay;
        if (inlays == null) {
            m283enum(69);
        }
        if (request == null) {
            m283enum(70);
        }
        if (editor == null) {
            m283enum(71);
        }
        if (a == null) {
            m283enum(72);
        }
        if (z) {
            disposeTips(editor, a);
        }
        InlayModel inlayModel = editor.getInlayModel();
        int i = 0;
        Iterator<CodeEditorInlay> it = inlays.iterator();
        while (it.hasNext()) {
            CodeEditorInlay next = it.next();
            if (next.isEmptyTip()) {
                it = it;
            } else {
                if (i == 0 && inlays.getReplacementRange().getLength() > 0) {
                    f574final = null;
                    tipInlayRenderer = new TipInlayRenderer(editor, request, next.getType(), TipInlayRenderer.replaceLeadingTabs(next.getLines(), request));
                } else {
                    ArrayList arrayList = new ArrayList(next.getLines());
                    int countTrailingSpaces = countTrailingSpaces(HA(editor));
                    String str = (String) arrayList.get(0);
                    if (i == 0 && countTrailingSpaces > 0) {
                        arrayList.set(0, str.trim());
                    }
                    tipInlayRenderer = new TipInlayRenderer(editor, request, next.getType(), arrayList);
                }
                Inlay<TipRenderer> inlay2 = null;
                switch (next.getType()) {
                    case Inline:
                        do {
                        } while (0 != 0);
                        Inlay<TipRenderer> addInlineElement = inlayModel.addInlineElement(next.getEditorOffset(), true, Integer.MAX_VALUE - i, tipInlayRenderer);
                        inlay = null;
                        tipInlayRenderer.setInlay(addInlineElement);
                        inlay2 = null;
                        break;
                    case AfterLineEnd:
                        inlay = inlayModel.addAfterLineEndElement(next.getEditorOffset(), false, tipInlayRenderer);
                        inlay2 = inlay;
                        break;
                    case Block:
                        inlay = inlayModel.addBlockElement(next.getEditorOffset(), true, false, Integer.MAX_VALUE - i, tipInlayRenderer);
                        inlay2 = inlay;
                        break;
                    default:
                        inlay = null;
                        break;
                }
                if (inlay != null) {
                    tipInlayRenderer.setInlay(inlay2);
                }
                i++;
                it = it;
            }
        }
    }

    private void vc(Project a, Editor a2, String a3, String a4, boolean z) {
        Document document = a2.getDocument();
        String TA = TA((CodeInlayList) Objects.requireNonNull(((RequestResultList) CACHE_KEY_LAST_REQUEST.get(a2)).getCurrentCodeTip()));
        WriteCommandAction.runWriteCommandAction(a, "Apply " + BasicActionsBundle.message(EditorUtils.H("\u0002`&h6<(},y)dte\u001f\u007f9n(D=n(\u007f%Q?U\u0014nkw=g0"), new Object[0]) + " Line Suggestion", CancelRequestTip.H("��$.\u0006\r\u0011Z\u0006\u0007\u0006\u001b\u0004\u0002"), () -> {
            int countLeadingSpaces;
            try {
                RequestResultList requestResultList = (RequestResultList) CACHE_KEY_LAST_REQUEST.get(a2);
                int offset = a2.getCaretModel().getOffset();
                String MC = MC(a2);
                if (StringUtils.isNotEmpty(MC)) {
                    document.deleteString(offset, offset + MC.length());
                }
                String str = "";
                if (StringUtils.isNotEmpty(a4) && (countLeadingSpaces = countLeadingSpaces(a4)) > 0) {
                    str = String.format("%-" + countLeadingSpaces + "s", "");
                }
                String str2 = z ? a3 : a3 + "\n" + str;
                document.insertString(a2.getCaretModel().getOffset(), str2);
                a2.getCaretModel().moveToOffset(a2.getCaretModel().getOffset() + str2.length());
                acceptCount(a2, offset, offset + str2.length(), CodeCollectEnum.GENERATE);
                Nc(a2, requestResultList, document);
                if (StringUtil.isNotEmpty(TA)) {
                    f569long.info("accept line code log " + TA);
                    PluginWebsocketClient.sendWsMessage(CommandEnum.LOG_ACCEPT_LINE, TA, a);
                }
                if (z) {
                    CodeInlayList codeInlayList = (CodeInlayList) requestResultList.getInlayLists().last();
                    if (codeInlayList.getData() == null || (codeInlayList.getData() != null && codeInlayList.getData().isEnded())) {
                        CACHE_KEY_LAST_REQUEST.set(a2, (Object) null);
                        RequestTipServiceImpl.LATEST_RESPONSE_DATA.remove(a);
                    }
                }
            } catch (Exception e) {
                f569long.error(EditorUtils.H("逎蠉釄绫弝幼"), e.getMessage());
            }
        }, new PsiFile[0]);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static int countLeadingSpaces(String a) {
        Matcher matcher = Pattern.compile(CancelRequestTip.H("?=\u0004]")).matcher(a);
        if (matcher.find()) {
            return matcher.group().length();
        }
        return 0;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private List<String> Ba(String a, String a2) {
        ArrayList newArrayList = Lists.newArrayList(a.split(CancelRequestTip.H("o")));
        Iterator it = newArrayList.iterator();
        while (it.hasNext()) {
            if (!((String) it.next()).contains(a2)) {
                it = it;
                it.remove();
            } else {
                it.remove();
                return newArrayList;
            }
        }
        return newArrayList;
    }

    @Override // com.aicode.service.EditorManagerService
    public void acceptWordTip(@NotNull Project project, @NotNull Editor editor, @NotNull EditorRequestService request, @NotNull CodeInlayList a) {
        if (project == null) {
            m283enum(60);
        }
        if (editor == null) {
            m283enum(61);
        }
        if (request == null) {
            m283enum(62);
        }
        if (a == null) {
            m283enum(63);
        }
        String replacementText = a.getReplacementText();
        String TA = TA(a);
        WriteCommandAction.runWriteCommandAction(project, "Apply " + BasicActionsBundle.message(EditorUtils.H("\u0002`&h6<(},y)dte\u001f\u007f9n(D=n(\u007f%Q?U\u0014nkw=g0"), new Object[0]) + " Suggestion", CancelRequestTip.H("2\u0002\b\u0019\u0012\u0005N>?\u0019\u0004\u0017\u0011"), () -> {
            String substring;
            String str;
            Document document;
            String a2;
            try {
                if (project.isDisposed()) {
                    return;
                }
                Document document2 = editor.getDocument();
                try {
                    f578enum.set(document2, true);
                    f578enum.set(document2, (Object) null);
                    int offset = editor.getCaretModel().getOffset();
                    int offset2 = request.getOffset();
                    boolean isRemoveBlank = a.isRemoveBlank();
                    if (!isRemoveBlank) {
                        substring = replacementText.substring(offset - offset2);
                        str = substring;
                    } else {
                        substring = replacementText.trim().substring(offset - offset2);
                        str = substring;
                    }
                    String extractPrefix = CodeCheckUtil.extractPrefix(substring);
                    try {
                        f569long.info(EditorUtils.H("弲剓亦砂bd9"), str);
                        a2 = editor.getDocument().getText().substring(CodeCheckUtil.getCaretOffset(editor), CodeCheckUtil.getLineEndOffset(editor));
                        document2.deleteString(offset, offset + AICodeStringUtil.notMatchSuffixIndex(a2, str.substring(extractPrefix.length())));
                        document = document2;
                    } catch (Throwable th) {
                        f569long.warn("[acceptTip]: 去除多余括号\n" + th);
                        document = document2;
                    }
                    document.insertString(offset, extractPrefix);
                    editor.getCaretModel().moveToOffset(offset + extractPrefix.length());
                    acceptCount(editor, offset, offset + extractPrefix.length(), CodeCollectEnum.GENERATE);
                    FileDocumentManager.getInstance().saveDocument(document2);
                    if (!qa(editor, (RequestResultList) CACHE_KEY_LAST_REQUEST.get(editor), document2, isRemoveBlank)) {
                        RequestTipServiceImpl.LATEST_RESPONSE_DATA.remove(project);
                        CACHE_KEY_LAST_REQUEST.set(editor, (Object) null);
                    }
                    if (!StringUtil.isNotEmpty(TA)) {
                        return;
                    }
                    f569long.info("accept code log " + TA);
                    PluginWebsocketClient.sendWsMessage(CommandEnum.LOG_ACCEPT_WORD, TA, project);
                } catch (Throwable th2) {
                    f578enum.set(document2, (Object) null);
                    throw th2;
                }
            } catch (Exception e) {
                f569long.error(CancelRequestTip.H("��\u0015\u0015\u0017\u0002\u0019M\u0010\b\u0013\u0005m9��\u0019T\u0011\u001a\u001a\u0001\u001c\\F"), e);
            }
        }, new PsiFile[0]);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void acceptCount(Project a, int a2, int a3, String a4, Document a5, CodeCollectEnum a6) {
        try {
            FileDocumentManager.getInstance().saveDocument(a5);
            acceptCount(a, a4, a5.getText(new TextRange(a5.getLineStartOffset(a5.getLineNumber(a2)), a5.getLineEndOffset(a5.getLineNumber(a3)))), a6);
        } catch (Throwable th) {
            f569long.warn(CancelRequestTip.H("\u0006\u0002\u0002\u0011\u0004\u00043(2\u0005\u001fQ\u0014\u0018\u0018\u001f\u0002"), th);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void Aa(CodeInlayList a, EditorRequestService a2, Editor a3) {
        Inlay<TipRenderer> inlay;
        CodeTipType codeTipType;
        ArrayList arrayList;
        String text = a3.getDocument().getText(TextRange.create(a2.getOffset(), a3.getCaretModel().getOffset()));
        String replacementText = a.getReplacementText();
        if (StringUtils.isNotBlank(text) && replacementText.startsWith(text)) {
            String[] split = replacementText.substring(a3.getCaretModel().getOffset() - a2.getOffset()).split(CancelRequestTip.H("o"));
            int i = 0;
            int length = split.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                TipInlayRenderer tipInlayRenderer = new TipInlayRenderer(a3, a2, i == 0 ? CodeTipType.Inline : CodeTipType.Block, Arrays.asList(split[i3]));
                Inlay<TipRenderer> inlay2 = null;
                if (i == 0) {
                    inlay = a3.getInlayModel().addInlineElement(a3.getCaretModel().getOffset(), true, Integer.MAX_VALUE - i, tipInlayRenderer);
                    inlay2 = inlay;
                } else {
                    if (i == 1) {
                        String[] strArr = new String[split.length - 1];
                        System.arraycopy(split, 1, strArr, 0, split.length - 1);
                        ArrayList arrayList2 = new ArrayList(List.of((Object[]) strArr));
                        if (i == 0) {
                            codeTipType = CodeTipType.Inline;
                            arrayList = arrayList2;
                        } else {
                            codeTipType = CodeTipType.Block;
                            arrayList = arrayList2;
                        }
                        tipInlayRenderer = new TipInlayRenderer(a3, a2, codeTipType, arrayList);
                        inlay2 = a3.getInlayModel().addBlockElement(a3.getCaretModel().getOffset(), true, false, Integer.MAX_VALUE - i, tipInlayRenderer);
                    }
                    inlay = inlay2;
                }
                if (inlay != null) {
                    tipInlayRenderer.setInlay(inlay2);
                }
                i++;
                i3++;
                i2 = i3;
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private void iB(long a) {
        try {
            Thread.sleep(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void acceptCount(Project a, String a2, String a3, CodeCollectEnum a4) {
        MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.LOG_ACCEPT_COUNT.getType());
        messageDto.setPath(a2);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(EditorUtils.H("w!o!"), a4.getType());
        jsonObject.addProperty(CancelRequestTip.H("\t\u0005\u0014\u0015"), a3);
        messageDto.setData(jsonObject);
        PluginWebsocketClient.sendWsMessage(messageDto, a);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private void qB(EditorRequestService a, Editor a2, int a3, CodeEditorInlay a4, List<String> list) {
        InlayModel inlayModel = a2.getInlayModel();
        for (String str : list) {
            char[] charArray = str.toCharArray();
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            int i = 0;
            int i2 = 0;
            while (i < charArray.length) {
                int i3 = i2;
                i2++;
                ApplicationManager.getApplication().invokeLater(() -> {
                    Character.toString(charArray[i3]);
                    String substring = str.substring(0, i3 + 1);
                    if (hashMap2.containsKey(EditorUtils.H("u/\u007f9U\u0013W+o9fu"))) {
                        ((Inlay) hashMap2.get(CancelRequestTip.H("()��\u001d\u001b\u0006!\u0006\u000f\u0002\u001eV"))).dispose();
                    }
                    TipInlayRenderer tipInlayRenderer = new TipInlayRenderer(a2, a, CodeTipType.Inline, Arrays.asList(substring));
                    Inlay<TipRenderer> addBlockElement = inlayModel.addBlockElement(a4.getEditorOffset(), true, false, Integer.MAX_VALUE - a3, tipInlayRenderer);
                    hashMap.put(EditorUtils.H("?_\u000fz q=mu"), tipInlayRenderer);
                    hashMap2.put(CancelRequestTip.H("()��\u001d\u001b\u0006!\u0006\u000f\u0002\u001eV"), addBlockElement);
                    if (addBlockElement != null) {
                        tipInlayRenderer.setInlay(addBlockElement);
                    }
                });
                i = i2;
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void acceptCount(Editor a, int a2, int a3, CodeCollectEnum a4) {
        try {
            acceptCount(a.getProject(), a2, a3, ((EditorImpl) a).getVirtualFile().getPath(), a.getDocument(), a4);
        } catch (Throwable th) {
            f569long.warn(EditorUtils.H(";f=\u007f1d\by8T\u0015> q*p6"), th);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private void MA(EditorRequestService a, Editor a2, int a3, CodeEditorInlay a4, String a5) {
        InlayModel inlayModel = a2.getInlayModel();
        char[] charArray = a5.toCharArray();
        int i = 0;
        int i2 = 0;
        while (i < charArray.length) {
            String ch = Character.toString(charArray[i2]);
            i2++;
            ApplicationManager.getApplication().invokeLater(() -> {
                TipInlayRenderer tipInlayRenderer = new TipInlayRenderer(a2, a, a4.getType(), Arrays.asList(ch));
                Inlay<TipRenderer> addInlineElement = inlayModel.addInlineElement(a4.getEditorOffset(), true, Integer.MAX_VALUE - a3, tipInlayRenderer);
                if (addInlineElement == null) {
                    return;
                }
                tipInlayRenderer.setInlay(addInlineElement);
            });
            i = i2;
        }
    }
}
