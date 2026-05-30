package com.aicode.service.editor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.CodeTipRequestDto;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.ResponseStreamDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.apm.enums.SpanAttrEnum;
import com.aicode.apm.enums.TracerEnum;
import com.aicode.domain.Position;
import com.aicode.enums.AICodeStatus;
import com.aicode.enums.CodeTipRequestType;
import com.aicode.enums.FileExtensionEnum;
import com.aicode.enums.TipType;
import com.aicode.enums.TipTypeEnum;
import com.aicode.generate.CodeTipUtil;
import com.aicode.generate.SimpleCodeTipCache;
import com.aicode.inline.controller.ChatInputController;
import com.aicode.request.AgentCodeTip;
import com.aicode.request.CodeGenerateEditorRequest;
import com.aicode.service.CodeInlayList;
import com.aicode.service.EditorRequestService;
import com.aicode.service.RequestTipService;
import com.aicode.service.TipCache;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.status.AICodeStatusService;
import com.aicode.ui.ActionButton;
import com.aicode.util.CodeCheckUtil;
import com.aicode.util.FileSizeUtil;
import com.aicode.util.HandleCacheUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.intellij.lang.Language;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiBinaryFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import io.opentelemetry.api.trace.Span;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: zc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/RequestTipServiceImpl.class */
public class RequestTipServiceImpl implements RequestTipService, Disposable {

    /* renamed from: final, reason: not valid java name */
    private static final Logger f599final;

    /* renamed from: try, reason: not valid java name */
    public String f600try;
    public static final Map<String, CodeTipRequestDto> CODE_TIP_MAP;

    /* renamed from: float, reason: not valid java name */
    public static final /* synthetic */ boolean f601float;
    public static final Map<Project, String> LATEST_RESPONSE_DATA;

    /* renamed from: byte, reason: not valid java name */
    public String f602byte;
    public static final Object object;
    public static final Map<Project, Map<String, Long>> LAST_REQUEST;
    public final TipCache cache = new SimpleCodeTipCache(32);

    /* renamed from: enum, reason: not valid java name */
    public Language f603enum = null;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m294enum(int a) {
        String H = ActionButton.H("#\n\u0003\u000bBP\n\nE\u0019\u0007��U/ \u001b\u0007'ZY?i\u0014\u001f\u0002\u000b\u0019\u000b\u0010\u001b��HRJ&hH\u001d\u0017Kw;J[��I\u001a\u0018\u001b\u0006B\u0016\u000b\nh0\tV\u001f\u001e\u0001\u001b");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[0] = ChatInputController.H("\u001c\u0011\u000b\u001a\u0015\u0004");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = ActionButton.H("\u000b\u001d\u000f\b\b\u001b<;\u0003\u0018%\u0012\u001d\u0012");
                break;
            case 3:
            case 4:
                objArr[0] = ChatInputController.H("\f\u001c\u0004\u0017\u000b\t\u0002");
                break;
        }
        objArr[1] = ActionButton.H("\u0017\u001c\u0004��T:*\u000b\u001a\u0015E\u0007\u000b\u0016\b\u001b\u000b\u0010@0+\u0001\u0006\u001e\u0019}\u001a\u0001\u000f\u0006\f\u0004\u0019<\u001b\u0012+\u0001\f>;\u000f\u00138\u0006\u001d\u001b");
        switch (a) {
            case 0:
            default:
                objArr[2] = ChatInputController.H("\u0002\b6\u0004\u001f\u0010\u0019\u0003\f\u0016\u0013");
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = ActionButton.H("\u0011\u0010\u001d\u0005\n-��\t\u0007\u0004\u000e\u001e\u0003");
                break;
            case 3:
                objArr[2] = ChatInputController.H("4\u001f\u0002\u0004\u00038\u0016\u0011\u0016\u001c\u00116\u0007\n\u0005");
                break;
            case 4:
                objArr[2] = ActionButton.H("\u0018-&\u000f\u001e%\u0002\u001d\u0004");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    @Override // com.aicode.service.RequestTipService
    @Nullable
    public EditorRequestService createRequest(@NotNull Editor editor, int offset, @NotNull TipType a) {
        if (editor == null) {
            m294enum(1);
        }
        if (a == null) {
            m294enum(2);
        }
        return CodeGenerateEditorRequest.create(editor, offset, a);
    }

    @Override // com.aicode.service.RequestTipService
    @Nullable
    public List<CodeInlayList> fetchCachedTips(@NotNull EditorRequestService a) {
        if (a == null) {
            m294enum(3);
        }
        return HandleCacheUtil.handleCache(a, this.cache);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        f601float = !RequestTipServiceImpl.class.desiredAssertionStatus();
        LAST_REQUEST = new ConcurrentHashMap();
        LATEST_RESPONSE_DATA = new ConcurrentHashMap();
        CODE_TIP_MAP = new ConcurrentHashMap();
        object = new Object();
        f599final = LoggerFactory.getLogger(RequestTipServiceImpl.class);
    }

    @Override // com.aicode.service.RequestTipService
    public void fetchTips(@NotNull EditorRequestService request, Flow.Subscriber<List<CodeInlayList>> subscriber, Editor editor, String question, CodeTipRequestType a) {
        if (request == null) {
            m294enum(4);
        }
        AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
        if (!f601float && request.getCompletionType() != TipType.GhostText) {
            throw new AssertionError();
        }
        TracerEnum tracerEnum = TracerEnum.CODE_COMPLETE_PARENT;
        if (CodeTipRequestType.InlineChat.equals(a)) {
            tracerEnum = TracerEnum.CODE_COMPLETE_INLINE_CHAT_PARENT;
        }
        Span buildWithTracer = OpenTelemetryUtil.buildWithTracer(tracerEnum, getClass().getName());
        Xb(editor, buildWithTracer);
        String text = editor.getDocument().getText();
        AICodeSettingsState aICodeSettingsState = AICodeSettingsState.getInstance();
        String fastSimpleUUID = IdUtil.fastSimpleUUID();
        f599final.info("【code complete request agent id is " + fastSimpleUUID + "】");
        buildWithTracer.setAttribute(SpanAttrEnum.COMPLETE_FORCE.getText(), CodeTipRequestType.Manual.equals(a));
        buildWithTracer.setAttribute(SpanAttrEnum.COMPLETE_FILE_LINE.getText(), editor.getDocument().getLineCount() + "行");
        CodeTipRequestDto codeTipRequestDto = new CodeTipRequestDto(request, subscriber, buildWithTracer, Long.valueOf(System.currentTimeMillis()));
        Ac(fastSimpleUUID, text, aICodeSettingsState, editor, a, buildWithTracer, question, request.isSelected());
        CODE_TIP_MAP.put(fastSimpleUUID, codeTipRequestDto);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void Ac(String a, String a2, AICodeSettingsState a3, Editor a4, CodeTipRequestType a5, Span a6, String a7, boolean z) {
        String fileExtensionFromEditor = getFileExtensionFromEditor(a4);
        boolean z2 = (AICodeSettingsState.getInstance().openCodeEnhance && AICodeSettingsState.getInstance().enableCodeEnhance && (AICodeSettingsState.getInstance().languages.contains(FileExtensionEnum.getFileLanguage(fileExtensionFromEditor)) || AICodeSettingsState.getInstance().languages.contains(fileExtensionFromEditor.toUpperCase(Locale.ROOT)))) && a5 != CodeTipRequestType.InlineChat;
        AICodeStatusService.notifyApplication(AICodeStatus.CompletionInProgress, z2 ? ActionButton.H("壖彨畳扦乜ECY") : "");
        HashMap hashMap = new HashMap();
        hashMap.put(ChatInputController.H("\u0017\u0017\u0016\u001b\u00197\u0014\u0013\u0015\n\u000f\u0012\u001b5\u0010\u001d"), TipTypeEnum.getByName(a3.tipType).name());
        hashMap.put(ActionButton.H("\u0014\r\n\u0007\u001b,\u0006\u001e\u001f\u0016\f\b\u0005"), Boolean.valueOf(CodeTipRequestType.Manual.equals(a5)));
        hashMap.put(ChatInputController.H("\u0017\u0002\u001b0\u0011\u0015\u0012\u0002.\u0015\u001f74\u0017\u001d"), Boolean.valueOf(z2));
        ArrayList arrayList = new ArrayList();
        Fa(a4, arrayList);
        MessageDto messageDto = new MessageDto(a, CommandEnum.CODE_COMPLETE.getType());
        messageDto.setPath(this.f600try);
        messageDto.setContent(a2);
        messageDto.setRange(arrayList);
        messageDto.setData(hashMap);
        messageDto.setStream(AICodeSettingsState.getInstance().streamOutputConfig);
        messageDto.setRequestion(a7);
        ApplicationManager.getApplication().runReadAction(() -> {
            String substring;
            substring = a4.getDocument().getText().substring(CodeCheckUtil.getLineStartOffset(a4), CodeCheckUtil.getLineEndOffset(a4));
            if (StringUtils.isNotBlank(substring)) {
                messageDto.setStream(false);
            }
        });
        if (a5.isInlineChat()) {
            messageDto.setStream(true);
        }
        int i = EditorManagerServiceImpl.docChangeCount.get();
        f599final.info("获取docChangeCount统计结果:" + i);
        messageDto.setDocChangeCount(Integer.valueOf(i));
        EditorManagerServiceImpl.docChangeCount.set(0);
        f599final.info("触发请求后docChangeCount置空:" + EditorManagerServiceImpl.docChangeCount.get());
        Span buildWithParent = OpenTelemetryUtil.buildWithParent(a6, TracerEnum.CODE_COMPLETE, getClass().getName());
        buildWithParent.setAttribute(SpanAttrEnum.COMMAND_ID.getText(), a);
        if (PluginWebsocketClient.sendWsMessageForCode(buildWithParent, messageDto, a4.getProject()).booleanValue()) {
            f599final.info("【code complete start】===========>: " + a);
            HashMap hashMap2 = new HashMap(1);
            hashMap2.put(a, Long.valueOf(System.currentTimeMillis()));
            LAST_REQUEST.put(a4.getProject(), hashMap2);
            return;
        }
        CODE_TIP_MAP.remove(a);
        AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
        a6.end();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public String getFileExtensionFromEditor(Editor a) {
        VirtualFile file;
        if (a == null || (file = FileDocumentManager.getInstance().getFile(a.getDocument())) == null) {
            return null;
        }
        return file.getExtension();
    }

    public void dispose() {
        this.cache.clear();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.RequestTipService
    public boolean isAvailable(@NotNull Editor a) {
        if (a == null) {
            m294enum(0);
        }
        Project project = a.getProject();
        if (project == null) {
            return false;
        }
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(a.getDocument());
        if (psiFile != null) {
            this.f603enum = psiFile.getLanguage();
        }
        return (psiFile == null || (psiFile instanceof PsiBinaryFile) || psiFile.getFileType().isBinary() || !FileSizeUtil.isSupported(psiFile.getVirtualFile())) ? false : true;
    }

    @Override // com.aicode.service.RequestTipService
    public void fetchInlineChatContent(EditorRequestService a, Flow.Subscriber<List<CodeInlayList>> subscriber, Editor a2, String a3, CodeTipRequestType a4) {
        fetchTips(a, subscriber, a2, a3, a4);
    }

    @Override // com.aicode.service.RequestTipService
    public EditorRequestService createInlineChatRequest(Editor a, int a2, TipType a3) {
        return createRequest(a, a2, a3);
    }

    private void Xb(Editor a, Span a2) {
        ApplicationManager.getApplication().runReadAction(() -> {
            try {
                PsiFile psiFile = PsiDocumentManager.getInstance((Project) Objects.requireNonNull(a.getProject())).getPsiFile(a.getDocument());
                if (psiFile != null) {
                    this.f603enum = psiFile.getLanguage();
                    this.f602byte = this.f603enum.getID().toLowerCase();
                    VirtualFile file = FileDocumentManager.getInstance().getFile(a.getDocument());
                    if (file != null) {
                        a2.setAttribute(SpanAttrEnum.COMPLETE_FILE_SIZE.getText(), (file.getLength() / 1024) + "k");
                        String path = file.getPath();
                        if (StringUtils.isBlank(path)) {
                            return;
                        }
                        this.f600try = path;
                    }
                }
            } catch (Exception e) {
                a2.recordException(e);
            }
        });
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    @Override // com.aicode.service.RequestTipService
    public void dealStreamAgentTips(String a, ResponseStreamDto a2, Project a3, MessageDto a4) {
        synchronized (object) {
            ResponseStreamDto.ResponseData data = a2.getData();
            if (!CODE_TIP_MAP.containsKey(a)) {
                return;
            }
            CodeTipRequestDto codeTipRequestDto = CODE_TIP_MAP.get(a);
            codeTipRequestDto.setFirstAgentDuration(System.currentTimeMillis());
            if (data.isEnded()) {
                CODE_TIP_MAP.remove(a);
            }
            if (!LAST_REQUEST.containsKey(a3)) {
                return;
            }
            Map<String, Long> map = LAST_REQUEST.get(a3);
            Span parentSpan = codeTipRequestDto.getParentSpan();
            if (map.containsKey(a)) {
                if (data.isEnded()) {
                    map.remove(a);
                }
                String Rb = Rb(data, codeTipRequestDto);
                Long startTime = codeTipRequestDto.getStartTime();
                parentSpan.setAttribute(SpanAttrEnum.COMPLETE_DURATION.getText(), Long.valueOf(System.currentTimeMillis() - startTime.longValue()) + "毫秒");
                parentSpan.setAttribute(SpanAttrEnum.COMPLETE_IS_STREAM.getText(), true);
                parentSpan.setAttribute(SpanAttrEnum.COMPLETE_FIRST_DURATION.getText(), (codeTipRequestDto.getFirstAgentDuration() - startTime.longValue()) + "毫秒");
                if (data.isEnded()) {
                    AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
                    parentSpan.setAttribute(SpanAttrEnum.COMPLETE_RESULT.getText(), Rb);
                    parentSpan.end();
                }
                LATEST_RESPONSE_DATA.put(a3, a);
                int length = Rb.length();
                int currentLength = a4.getCurrentLength();
                int streamStep = a4.getStreamStep();
                String str = Rb;
                while (true) {
                    String substring = str.substring(0, Math.min(currentLength, length));
                    EditorRequestService request = codeTipRequestDto.getRequest();
                    Flow.Subscriber<List<CodeInlayList>> codeSubScriber = codeTipRequestDto.getCodeSubScriber();
                    List<String> asList = Arrays.asList(substring);
                    data.setShowKeyMapTipFlag(substring.contains(ActionButton.H("}")));
                    if (data.isEnded()) {
                        data.setShowKeyMapTipFlag(true);
                    }
                    ib(a, asList, request, codeSubScriber, data);
                    if (currentLength < length) {
                        streamStep++;
                        currentLength += streamStep;
                        str = Rb;
                        a4.setCurrentLength(currentLength);
                        a4.setStreamStep(streamStep);
                    } else {
                        f599final.info("【code complete deal finished】===========>: " + a);
                        return;
                    }
                }
            } else {
                f599final.info("code complete cancel " + a);
                parentSpan.end();
            }
        }
    }

    /* compiled from: zc */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/RequestTipServiceImpl$j.class */
    class j extends TypeToken<String[]> {
        public j() {
        }
    }

    private static void Fa(Editor a, List<CodeInfoDto.RangeDTO> list) {
        Position cursorPosition = Position.getCursorPosition(a);
        CodeInfoDto.RangeDTO rangeDTO = new CodeInfoDto.RangeDTO(Integer.valueOf(cursorPosition.getLine()), Integer.valueOf(cursorPosition.getCharacter()));
        CodeInfoDto.RangeDTO rangeDTO2 = new CodeInfoDto.RangeDTO(Integer.valueOf(cursorPosition.getLine()), Integer.valueOf(cursorPosition.getCharacter()));
        list.add(rangeDTO);
        list.add(rangeDTO2);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.RequestTipService
    public void dealAgentTips(String a, JsonObject a2, Project a3) {
        if (CODE_TIP_MAP.containsKey(a)) {
            CodeTipRequestDto codeTipRequestDto = CODE_TIP_MAP.get(a);
            CODE_TIP_MAP.remove(a);
            if (!LAST_REQUEST.containsKey(a3)) {
                return;
            }
            Map<String, Long> map = LAST_REQUEST.get(a3);
            Span parentSpan = codeTipRequestDto.getParentSpan();
            if (!map.containsKey(a)) {
                f599final.info("code complete cancel " + a);
                parentSpan.end();
                return;
            }
            map.remove(a);
            String[] strArr = new String[0];
            JsonObject a4 = a2.get(ActionButton.H("\u0015\n\u0019\u0016")).getAsJsonObject();
            String str = a;
            if (Objects.nonNull(a4)) {
                str = a4.get(ChatInputController.H("\u001d&>\u000b\u0017\u0014\u001f-\f")).getAsString();
                if (!a.equals(str)) {
                    f599final.info(ActionButton.H("吐久\u0013\u0005\u001d\n\n罛嬊｠\r\fｧ\u0016\n"), a, str);
                }
                strArr = (String[]) new Gson().fromJson(a4.getAsJsonArray(ChatInputController.H("\u0016\f\u00023#\u001b\u0006\u000e\u0004\n\u001b")), new j().getType());
            }
            parentSpan.setAttribute(SpanAttrEnum.COMPLETE_DURATION.getText(), Long.valueOf(System.currentTimeMillis() - codeTipRequestDto.getStartTime().longValue()) + "毫秒");
            parentSpan.setAttribute(SpanAttrEnum.COMPLETE_RESULT.getText(), a4.toString());
            parentSpan.end();
            if (strArr != null && strArr.length != 0) {
                List<String> list = (List) Arrays.stream(strArr).filter((v0) -> {
                    return Objects.nonNull(v0);
                }).collect(Collectors.toList());
                if (CollectionUtil.isEmpty(list)) {
                    AICodeStatusService.notifyApplication(AICodeStatus.Ready, ChatInputController.H("斋庞识"));
                    return;
                }
                LATEST_RESPONSE_DATA.put(a3, str);
                ib(str, list, codeTipRequestDto.getRequest(), codeTipRequestDto.getCodeSubScriber(), null);
                f599final.info("【code complete deal finished】===========>: " + a);
                return;
            }
            f599final.info("【code complete no result】===========>: " + a);
            AICodeStatusService.notifyApplication(AICodeStatus.Ready, ActionButton.H("斋店诙"));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ad  */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String Rb(ResponseStreamDto.ResponseData a, CodeTipRequestDto a2) {
        ResponseStreamDto.ResponseData responseData;
        String lastReplacementText;
        if (Objects.nonNull(a)) {
            if (a.isEnded()) {
                return a2.getLastReplacementText().substring(a2.getLastReplacementText().indexOf(ChatInputController.H("s")) + 1);
            }
            String text = a.getText();
            String str = text;
            if (!StringUtils.isBlank(text)) {
                if (str != null) {
                    if (str.endsWith(ActionButton.H("{\u000b\r\u0017"))) {
                        str = str.replaceAll(ChatInputController.H("O)\u0015\u0019"), "");
                        responseData = a;
                        responseData.setText(str);
                        if (StringUtils.isNotBlank(str)) {
                            a2.setLastReplacementText(a2.getLastReplacementText() + str);
                        }
                        lastReplacementText = a2.getLastReplacementText();
                        if (!lastReplacementText.contains(ActionButton.H("}"))) {
                            return lastReplacementText.substring(lastReplacementText.indexOf(ChatInputController.H("s")) + 1);
                        }
                        return "";
                    }
                    if (str.endsWith(ActionButton.H("\u000b\r\u0017"))) {
                        str = str.replaceAll(ChatInputController.H(")\u0015\u0019"), "");
                    }
                }
                responseData = a;
                responseData.setText(str);
                if (StringUtils.isNotBlank(str)) {
                }
                lastReplacementText = a2.getLastReplacementText();
                if (!lastReplacementText.contains(ActionButton.H("}"))) {
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void ib(String a, List<String> list, EditorRequestService a2, Flow.Subscriber<List<CodeInlayList>> subscriber, ResponseStreamDto.ResponseData a3) {
        SubmissionPublisher submissionPublisher = new SubmissionPublisher();
        try {
            submissionPublisher.subscribe(subscriber);
            ArrayList arrayList = new ArrayList();
            String[] split = a2.getCurrentDocumentPrefix().split(ActionButton.H("}"));
            boolean z = StringUtils.isBlank(split[split.length - 1]) && split[split.length - 1].length() > 0;
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (StringUtils.isBlank(next)) {
                    it = it;
                } else {
                    String replaceAll = next.replaceAll(ChatInputController.H("p"), ActionButton.H("QKMW"));
                    if (z) {
                        int i = 0;
                        int i2 = 0;
                        while (i < replaceAll.length() && Character.isWhitespace(replaceAll.charAt(i2))) {
                            i2++;
                            i = i2;
                        }
                        replaceAll = replaceAll.substring(i2);
                    }
                    AgentCodeTip FromString = AgentCodeTip.FromString(replaceAll.stripTrailing());
                    AgentCodeTip withCompletion = FromString.withCompletion(TipInlayRenderer.replaceLeadingTabs(FromString.getTip(), a2));
                    withCompletion.setRequestId(a);
                    withCompletion.setScene("");
                    withCompletion.setLanguage(this.f602byte);
                    AgentCodeTipList agentCodeTipList = new AgentCodeTipList(CodeTipUtil.createEditorCodeTip(a2, withCompletion, true), withCompletion, a2);
                    it = it;
                    agentCodeTipList.setRequestId(a);
                    agentCodeTipList.setScene("");
                    agentCodeTipList.setLanguage(this.f602byte);
                    agentCodeTipList.setData(a3);
                    arrayList.add(agentCodeTipList);
                }
            }
            if (!arrayList.isEmpty()) {
                submissionPublisher.submit(arrayList);
            }
            submissionPublisher.close();
            if (a3 == null || a3.isEnded()) {
                if (a3 != null && a3.isEnded()) {
                    AICodeStatusService.notifyApplication(AICodeStatus.Ready, arrayList.isEmpty() ? ChatInputController.H("斒序诗") : "");
                } else {
                    AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
                }
            }
        } catch (Throwable th) {
            AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
            try {
                submissionPublisher.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }
}
