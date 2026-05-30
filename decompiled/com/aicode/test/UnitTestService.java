package com.aicode.test;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.action.batch.BatchUnitTestTemplateService;
import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.ResponseDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.service.CodeCompleteService;
import com.aicode.agent.service.CommonService;
import com.aicode.content.util.EditorUtils;
import com.aicode.enums.CodeCollectEnum;
import com.aicode.enums.LanguageEnum;
import com.aicode.enums.UnitTestBaseEnum;
import com.aicode.enums.UnitTestMockEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.settings.UnitTestSettingsState;
import com.aicode.template.TemplateGenerator;
import com.aicode.template.context.domain.Method;
import com.aicode.template.generator.CacheFileTemplate;
import com.aicode.template.generator.CreateTestMethodTask;
import com.aicode.template.generator.GeneratorFileConfig;
import com.aicode.template.request.FileRequestDto;
import com.aicode.template.request.MethodRequestResult;
import com.aicode.template.request.TemplateRequestService;
import com.aicode.template.request.dto.CaseBranch;
import com.aicode.template.request.dto.CaseParam;
import com.aicode.template.request.dto.CaseResult;
import com.aicode.test.dto.MethodUnitTestData;
import com.aicode.test.dto.RequestCaseCodeDto;
import com.aicode.test.dto.UnitTestAgentDto;
import com.aicode.test.dto.UnitTestDto;
import com.aicode.util.HighlighterUtil;
import com.aicode.util.NewFileUtils;
import com.aicode.util.PsiUtils;
import com.aicode.util.TypeUtils;
import com.aicode.util.UnitTestCollectUtil;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.util.EditorHelper;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpressionStatement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiImportStatementBase;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: eb */
@Service
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/UnitTestService.class */
public final class UnitTestService {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f638enum = Logger.getInstance(UnitTestService.class);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m358enum(int a) {
        String H = CodeCompleteService.H("xS\r3WINk\u0005m]ipRM,\u001ai\u00168l\u001aDyMo\u001aqJt\u000fxLx\\~L'WiMh");
        Object[] objArr = new Object[2];
        objArr[0] = EditorUtils.H("eI\u00102's\"h8BSb(s/(\tN\u0012b\u0019d)q\r\u007f3f\"u(");
        switch (a) {
            case 0:
            default:
                objArr[1] = CodeCompleteService.H("k[o|jKc[cFbmmVf}HnW");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = EditorUtils.H("*d.F1~$\\\"e9");
                break;
            case 2:
                objArr[1] = CodeCompleteService.H("LxLm]ima_oDp");
                break;
            case 3:
                objArr[1] = EditorUtils.H("*e/W/I>z(l?k*U'v8s9");
                break;
        }
        throw new IllegalStateException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void handleAction(WebViewDataTypeEnum a, String a2, Project a3) {
        switch (a) {
            case UNIT_TEST_REQUEST_UT_INFO:
                do {
                } while (0 != 0);
                MessageDto messageDto = (MessageDto) a3.getUserData(WebViewWindowPanel.UNIT_TEST_MESSAGE_DATA);
                if (Objects.isNull(messageDto)) {
                    return;
                }
                PluginWebsocketClient.sendWsMessage(messageDto, a3);
                a3.putUserData(WebViewWindowPanel.UNIT_TEST_MESSAGE_DATA, (Object) null);
                return;
            case UNIT_TEST_REQUEST_METHOD_CASE:
                requestMethodCase(a2, a3);
                return;
            case UNIT_TEST_REQUEST_CASE_CODE:
                requestCaseCode(a2, a3);
                return;
            case UNIT_TEST_REQUEST_ALL_CODE_FILE:
                generateUnitTestFile(a3, a2);
                return;
            case UNIT_TEST_COPY_CASE_CODE:
                copyCaseCode(a2);
                return;
            case UNIT_TEST_PAGE_READY:
                Object userData = a3.getUserData(WebViewWindowPanel.UNIT_TEST_MESSAGE_DATA);
                if (userData instanceof MessageDto) {
                    PluginWebsocketClient.sendWsMessage((MessageDto) userData, a3);
                }
                a3.putUserData(WebViewWindowPanel.UNIT_TEST_MESSAGE_DATA, (Object) null);
                return;
            case UNIT_TEST_SAVE_CODE:
                openSaveTestFile(a3, a2);
                return;
            case UNIT_TESTING_MAPPING_FILE:
                mappingUnitTestFile(a3, a2);
                return;
            case UNIT_TEST_FUNCTION_CASE:
                UnitTestDto.DataDTO.FunctionDataDTO functionDataDTO = (UnitTestDto.DataDTO.FunctionDataDTO) new Gson().fromJson(((JsonObject) new Gson().fromJson(a2, JsonObject.class)).get(CodeCompleteService.H("vBjWb")), UnitTestDto.DataDTO.FunctionDataDTO.class);
                if (StringUtils.equalsIgnoreCase(LanguageEnum.JAVA.getDescription(), functionDataDTO.getLanguage())) {
                    hc(a3, functionDataDTO);
                    return;
                } else {
                    if (!StringUtils.equalsIgnoreCase(LanguageEnum.CPP_LANGUAGE_01.getDescription(), functionDataDTO.getLanguage()) && !StringUtils.equalsIgnoreCase(LanguageEnum.C_LANGUAGE_01.getDescription(), functionDataDTO.getLanguage()) && !StringUtils.equalsIgnoreCase(LanguageEnum.PYTHON_LANGUAGE_01.getDescription(), functionDataDTO.getLanguage())) {
                        return;
                    }
                    CppTestService.resolveFunctionCase(a3, functionDataDTO);
                    return;
                }
            case UNIT_TEST_FUNCTION_CASE_CODE:
                UnitTestDto.DataDTO.FunctionDataDTO functionDataDTO2 = (UnitTestDto.DataDTO.FunctionDataDTO) new Gson().fromJson(((JsonObject) new Gson().fromJson(a2, JsonObject.class)).get(EditorUtils.H("7q'c(")), UnitTestDto.DataDTO.FunctionDataDTO.class);
                if (!StringUtils.equalsIgnoreCase(LanguageEnum.JAVA.getDescription(), functionDataDTO2.getLanguage())) {
                    if (!StringUtils.equalsIgnoreCase(LanguageEnum.CPP_LANGUAGE_01.getDescription(), functionDataDTO2.getLanguage()) && !StringUtils.equalsIgnoreCase(LanguageEnum.C_LANGUAGE_01.getDescription(), functionDataDTO2.getLanguage()) && !StringUtils.equalsIgnoreCase(LanguageEnum.PYTHON_LANGUAGE_01.getDescription(), functionDataDTO2.getLanguage())) {
                        return;
                    }
                    CppTestService.getTestCode(a3, functionDataDTO2);
                    return;
                }
                aA(a3, functionDataDTO2);
                return;
            case UNIT_TEST_REGENERATE:
                EA(a3, a2);
                return;
            default:
                return;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void handleAgentAction(CommandEnum a, JsonObject a2, MessageDto a3, String a4, Project a5) {
        UnitTestService unitTestService = (UnitTestService) a5.getService(UnitTestService.class);
        switch (a) {
            case CODE_TEST_CASE:
                ConcurrentNavigableMap<String, MessageDto> concurrentNavigableMap = PluginWebsocketClient.AGENT_REQUEST;
                do {
                } while (0 != 0);
                concurrentNavigableMap.remove(a4);
                SocketMessageHandleListener.send2Web(a5, unitTestService.getTestCase(a2, a3));
                return;
            case CODE_TEST_ANALYSIS:
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                if (!StringUtils.equalsIgnoreCase(LanguageEnum.JAVA.getDescription(), a3.getLang())) {
                    CppTestService.cppTestAnalysis(a2, a3, a5);
                    return;
                } else {
                    javaUnitTestAnalysis(a2, a3, a5);
                    return;
                }
            case CODE_TEST_CODE:
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                SocketMessageHandleListener.send2Web(a5, unitTestService.getTestCode(a2, a3));
                return;
            case CODE_TEST_SAVE:
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                unitTestService.testSave(a5, a2.get(CodeCompleteService.H("GgVf")).getAsString(), a3);
                return;
            case TEST_MAKE_CASE:
            case TEST_MAKE_CODE:
            case CODE_TEST_MAKE_CASE_JAVA:
                if (StringUtils.equalsIgnoreCase(LanguageEnum.JAVA.getDescription(), a3.getLang()) || StringUtils.equalsIgnoreCase(LanguageEnum.C_LANGUAGE_01.getDescription(), a3.getLang()) || StringUtils.equalsIgnoreCase(LanguageEnum.CPP_LANGUAGE_01.getDescription(), a3.getLang()) || StringUtils.equalsIgnoreCase(LanguageEnum.PYTHON_LANGUAGE_01.getDescription(), a3.getLang())) {
                    JsonElement jsonElement = a2.get(EditorUtils.H("t*b,"));
                    if (jsonElement instanceof JsonObject) {
                        unitTestService.QC(a5, jsonElement.getAsJsonObject(), a3);
                        return;
                    }
                    return;
                }
                TemplateRequestService.handleAgentAction(a, a2, a3, a4, a5);
                return;
            default:
                return;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static String getTestPath(Project a, String a2, String a3, String a4) {
        if (StringUtils.contains(a2, CodeCompleteService.H("U|Mo"))) {
            String replace = StringUtils.replace(a2, EditorUtils.H("}*\u007f#"), CodeCompleteService.H("LxWu"));
            return replace.substring(0, replace.lastIndexOf(EditorUtils.H("c"))) + "Test.java";
        }
        if (StringUtils.isBlank(a3)) {
            a3 = a.getBasePath();
        }
        String str = a3 + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator;
        if (a4 != null && a4.endsWith(CodeCompleteService.H("lxWu"))) {
            return str + a4 + ".java";
        }
        return str + a4 + "Test.java";
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static void iA(PsiJavaFile a, PsiJavaFile a2, Project a3, Set<String> set, JavaCodeStyleManager a4, String str) {
        PsiClass[] classes = a.getClasses();
        int length = classes.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            PsiAnnotation[] annotations = classes[i2].getAnnotations();
            int length2 = annotations.length;
            int i3 = 0;
            int i4 = 0;
            while (i3 < length2) {
                PsiAnnotation psiAnnotation = annotations[i4];
                String text = psiAnnotation.getText();
                String qualifiedName = psiAnnotation.getQualifiedName();
                PsiModifierList modifierList = a2.getClasses()[0].getModifierList();
                if (modifierList != null) {
                    if (!Arrays.stream(modifierList.getAnnotations()).anyMatch(a5 -> {
                        return a5.getQualifiedName().equals(qualifiedName);
                    })) {
                        if (!Arrays.stream(modifierList.getAnnotations()).anyMatch(a6 -> {
                            return a6.getText().equals(text);
                        })) {
                            a4.shortenClassReferences(modifierList.addBefore(psiAnnotation, modifierList.getFirstChild()));
                        }
                    } else {
                        PsiAnnotation[] annotations2 = modifierList.getAnnotations();
                        boolean z = false;
                        int length3 = annotations2.length;
                        int i5 = 0;
                        int i6 = 0;
                        while (i5 < length3) {
                            PsiAnnotation psiAnnotation2 = annotations2[i6];
                            if (StringUtils.equals(EditorUtils.H("*/a<9u6u9q(t'8.2tbru!a(t02gB\u0018oh{/i3S\u001db$o5trp\ts=`(`\u0018u3D.e9"), psiAnnotation2.getQualifiedName())) {
                                z = true;
                                String[] split = psiAnnotation2.getText().replace(CodeCompleteService.H("zy~[k[m@F@x}iVt%S"), "").replace(EditorUtils.H("kd"), "").trim().split(CodeCompleteService.H("\u0004"));
                                int length4 = split.length;
                                int i7 = 0;
                                int i8 = 0;
                                while (i7 < length4) {
                                    String str2 = split[i8];
                                    if (StringUtils.isNotBlank(str2.trim())) {
                                        set.add(str2.trim());
                                    }
                                    i8++;
                                    i7 = i8;
                                }
                                psiAnnotation2.delete();
                            }
                            i6++;
                            i5 = i6;
                        }
                        if (StringUtils.equals(EditorUtils.H("*/a<9u6u9q(t'8.2tbru!a(t02gB\u0018oh{/i3S\u001db$o5trp\ts=`(`\u0018u3D.e9"), psiAnnotation.getQualifiedName())) {
                            String[] split2 = text.replace(CodeCompleteService.H("zy~[k[m@F@x}iVt%S"), "").replace(EditorUtils.H("kd"), "").trim().split(CodeCompleteService.H("\u0004"));
                            int length5 = split2.length;
                            int i9 = 0;
                            int i10 = 0;
                            while (i9 < length5) {
                                String str3 = split2[i10];
                                if (StringUtils.isNotBlank(str3.trim())) {
                                    set.add(str3.trim());
                                }
                                i10++;
                                i9 = i10;
                            }
                        }
                        if (z) {
                            a4.shortenClassReferences(modifierList.addBefore(JavaPsiFacade.getElementFactory(a3).createAnnotationFromText("@PrepareForTest({" + String.join(EditorUtils.H(":m"), set) + "})", (PsiElement) null), modifierList.getFirstChild()));
                        }
                    }
                }
                i4++;
                i3 = i4;
            }
            i2++;
            i = i2;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static String findCommonPrefix(String a, String a2) {
        int min = Math.min(a.length(), a2.length());
        int i = 1;
        int i2 = 1;
        while (i <= min) {
            String substring = a.substring(a.length() - i2);
            if (substring.equals(a2.substring(0, i2))) {
                return substring;
            }
            i2++;
            i = i2;
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: eb */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/UnitTestService$e.class */
    public class e extends ArrayList<CaseBranch> {

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ CaseBranch f640enum;

        public e(CaseBranch caseBranch) {
            this.f640enum = caseBranch;
            add(this.f640enum);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static UnitTestDto.DataDTO.FunctionDataDTO.CodeList aa(PsiMethod a, CaseResult a2, StringBuffer a3) {
        boolean contains;
        PsiMethod a4 = new UnitTestDto.DataDTO.FunctionDataDTO.CodeList();
        a4.setCaseDescription(a2.getMessage());
        a4.setCaseMethodName(a2.getCaseMethodName());
        a4.setCaseCode("    " + a.getText());
        a3.append("    " + a.getText() + "\n");
        HashMap hashMap = new HashMap();
        hashMap.put(EditorUtils.H("E\u0003f(b.`:H$c>z9"), CodeCompleteService.H("`gNc"));
        a4.setBranches(a2.getBranches());
        Map<String, CaseParam> input = a2.getInput();
        Iterator<Map.Entry<String, CaseParam>> it = input.entrySet().iterator();
        while (it.hasNext()) {
            CaseParam value = it.next().getValue();
            if (value != null) {
                contains = TypeUtils.f736long.contains(value.getCanonicalName());
                if (!contains) {
                    value.setData(EditorUtils.H("]$u&"));
                }
            }
        }
        a4.setInput(input);
        a4.setDependencies((List) a2.getMockMethods().stream().filter(a5 -> {
            return StringUtils.isNotBlank(a5.getMethodName());
        }).peek(a6 -> {
            boolean contains2;
            if (!Objects.nonNull(a6.getReturnValue())) {
                return;
            }
            contains2 = TypeUtils.f736long.contains(a6.getReturnValue().getCanonicalName());
            if (contains2) {
                return;
            }
            a6.getReturnValue().setData(CodeCompleteService.H("dcM`"));
        }).collect(Collectors.toList()));
        a4.setAsserts(hashMap);
        if (a4 == null) {
            m358enum(1);
        }
        return a4;
    }

    public static void testAnalysisErr(Project a, ResponseDto a2) {
        UnitTestDto.DataDTO dataDTO = new UnitTestDto.DataDTO();
        dataDTO.setReason(EditorUtils.H("$b9y?"));
        dataDTO.setMessage(a2.getMsg());
        SocketMessageHandleListener.send2Web(a, receiveFunction(dataDTO));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static void xb(Project a, PsiJavaFile a2, PsiJavaFile a3, Set<String> set, JavaCodeStyleManager a4, String a5, CodeStyleManager a6) {
        try {
            iA(a2, a3, a, set, a4, a5);
            Ob(a2, a3);
            mergeFields(a2, a3);
            SB(a2, a3);
            a4.removeRedundantImports(a3);
            a4.optimizeImports(a3);
            a6.reformat(a3);
        } catch (Throwable th) {
            f638enum.warn("合并异常: " + th.getMessage());
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean Ib(PsiJavaFile a, PsiImportStatementBase a2) {
        PsiImportStatementBase[] allImportStatements = a.getImportList().getAllImportStatements();
        int length = allImportStatements.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            if (!allImportStatements[i2].getImportReference().getCanonicalText().equals(a2.getImportReference().getCanonicalText())) {
                i2++;
                i = i2;
            } else {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void requestMethodCase(String a, Project a2) {
        RequestCaseCodeDto requestCaseCodeDto = (RequestCaseCodeDto) new Gson().fromJson(a, RequestCaseCodeDto.class);
        if (!Objects.isNull(requestCaseCodeDto) && !Objects.isNull(requestCaseCodeDto.getValue())) {
            RequestCaseCodeDto.ValueDTO value = requestCaseCodeDto.getValue();
            MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.CODE_TEST_CASE.getType());
            messageDto.setData(value.getCode());
            messageDto.setRequestCaseCodeDto(requestCaseCodeDto);
            PluginWebsocketClient.sendWsMessage(messageDto, a2);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static void Ob(PsiJavaFile a, PsiJavaFile a2) {
        PsiImportStatementBase[] allImportStatements = a.getImportList().getAllImportStatements();
        int length = allImportStatements.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            PsiImportStatementBase psiImportStatementBase = allImportStatements[i2];
            if (!Ib(a2, psiImportStatementBase)) {
                a2.getImportList().add(psiImportStatementBase);
            }
            i2++;
            i = i2;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean Ta(PsiJavaFile a, PsiField a2) {
        PsiField[] fields = a.getClasses()[0].getFields();
        int length = fields.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            if (!fields[i2].getName().equals(a2.getName())) {
                i2++;
                i = i2;
            } else {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void handleUnitTestBankData(Project a, MethodGeneratorConfig a2) {
        Project a3 = a2.getFunctionDataDTO();
        PsiJavaFile psiFile = a2.getPsiFile();
        String text = psiFile.getText();
        a3.setTestClassAbsolutePath(getTestPath(a, a2.getPath(), a2.getModulePath(), a2.getPsiFile().getName()));
        a3.setPath(a2.getPath());
        a3.setTestContent(text);
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList arrayList = new ArrayList();
        PsiClass[] classes = psiFile.getClasses();
        int length = classes.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            PsiMethod[] methods = classes[i2].getMethods();
            int length2 = methods.length;
            int i3 = 0;
            int i4 = 0;
            while (i3 < length2) {
                PsiMethod psiMethod = methods[i4];
                if (!StringUtils.equals(CodeCompleteService.H("{]ixx"), psiMethod.getName())) {
                    Method orElse = a2.getTemplateMethods().stream().filter(a4 -> {
                        return a4.getCaseResults().stream().anyMatch(a4 -> {
                            return a4.getCaseMethodName().equals(psiMethod.getName());
                        });
                    }).findFirst().orElse(null);
                    if (!Objects.nonNull(orElse)) {
                        String str = "test" + com.aicode.util.StringUtils.capitalizeFirstLetter(a3.getFunctionName());
                        Method orElse2 = a2.getTemplateMethods().stream().filter(a5 -> {
                            return com.aicode.util.StringUtils.equals(str, psiMethod.getName()) && com.aicode.util.StringUtils.equals(a5.getName(), a3.getFunctionName());
                        }).findFirst().orElse(null);
                        if (Objects.nonNull(orElse2)) {
                            ZA(orElse2, psiMethod, arrayList, stringBuffer);
                        }
                    } else if (!CollectionUtils.isNotEmpty(orElse.getCaseResults())) {
                        ZA(orElse, psiMethod, arrayList, stringBuffer);
                    } else {
                        CaseResult orElse3 = orElse.getCaseResults().stream().filter(a6 -> {
                            return a6.getCaseMethodName().equals(psiMethod.getName()) && orElse.getName().equals(a3.getFunctionName());
                        }).findFirst().orElse(null);
                        if (Objects.nonNull(orElse3)) {
                            arrayList.add(aa(psiMethod, orElse3, stringBuffer));
                        }
                    }
                }
                i4++;
                i3 = i4;
            }
            i2++;
            i = i2;
        }
        if (StringUtils.isNotBlank(stringBuffer.toString())) {
            a3.setCaseCode("```java\n" + stringBuffer + "```");
            a3.setCodeList(arrayList);
            sendUnitTestBankData(a, a3);
        }
        a3.setReason(EditorUtils.H("c?y="));
        a3.setCodeList(arrayList);
        sendUnitTestBankData(a, a3);
        ArrayList arrayList2 = new ArrayList();
        ApplicationManager.getApplication().runReadAction(() -> {
            for (PsiMethod psiMethod2 : PsiTreeUtil.findChildrenOfType(psiFile, PsiMethod.class)) {
                String testMethodId = UnitTestCollectUtil.getTestMethodId(psiMethod2);
                if (StringUtils.isNotBlank(testMethodId)) {
                    arrayList2.add(new MethodUnitTestData(testMethodId, Integer.valueOf(psiMethod2.getText().split(EditorUtils.H("G")).length)));
                }
            }
        });
        String path = a2.getPath();
        f638enum.info("test collection generate " + arrayList2.size());
        testCollectionGenerate(a, arrayList2, path);
    }

    public JsonObject getTestCode(JsonObject a, MessageDto a2) {
        JsonObject jsonObject = new JsonObject();
        RequestCaseCodeDto.ValueDTO value = a2.getRequestCaseCodeDto().getValue();
        RequestCaseCodeDto.ValueDTO valueDTO = (RequestCaseCodeDto.ValueDTO) new Gson().fromJson(a.get(EditorUtils.H("t*b,")), RequestCaseCodeDto.ValueDTO.class);
        jsonObject.addProperty(CodeCompleteService.H("\\tYi"), WebViewDataTypeEnum.UNIT_TEST_GET_CASE_CODE.getType());
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty(EditorUtils.H("\u007f)"), value.getId());
        jsonObject2.addProperty(CodeCompleteService.H("}@h"), value.getPid());
        jsonObject2.addProperty(EditorUtils.H("d2f("), value.getType());
        jsonObject2.addProperty(CodeCompleteService.H("KbMi"), valueDTO.getCode());
        jsonObject2.addProperty(EditorUtils.H("n(l9s/S$r("), valueDTO.getOriginCode());
        jsonObject.add(CodeCompleteService.H("pIa\\i"), jsonObject2);
        return jsonObject;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static String EC(String a) {
        Matcher matcher = Pattern.compile(EditorUtils.H("L/=i")).matcher(a);
        if (matcher.find()) {
            String a2 = matcher.group();
            return a.substring(0, a.length() - a2.length()) + (Integer.parseInt(a2) + 1);
        }
        return a + "1";
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void openSaveTestFile(Project a, String a2) {
        String a3;
        String str;
        String str2;
        UnitTestDto.DataDTO dataDTO = (UnitTestDto.DataDTO) new Gson().fromJson(((JsonObject) new Gson().fromJson(a2, JsonObject.class)).get(EditorUtils.H("7q'c(")), UnitTestDto.DataDTO.class);
        if (StringUtils.equalsIgnoreCase(LanguageEnum.JAVA.getDescription(), dataDTO.getLanguage())) {
            String systemDependentName = FileUtil.toSystemDependentName(dataDTO.getTestClassAbsolutePath(), File.separatorChar);
            String str3 = UnitTestSettingsState.getInstance().testClasPath;
            if (!StringUtils.isNotBlank(str3)) {
                str2 = systemDependentName;
                a3 = systemDependentName.substring(0, systemDependentName.lastIndexOf(File.separatorChar));
            } else {
                a3 = str3;
                str2 = systemDependentName;
            }
            str = str2.substring(systemDependentName.lastIndexOf(File.separator) + 1);
        } else if (StringUtils.equalsIgnoreCase(LanguageEnum.CPP_LANGUAGE_01.getDescription(), dataDTO.getLanguage())) {
            a3 = a.getBasePath();
            str = dataDTO.getClassName() + "Test." + LanguageEnum.CPP_LANGUAGE_01.getSuffix();
        } else if (StringUtils.equalsIgnoreCase(LanguageEnum.C_LANGUAGE_01.getDescription(), dataDTO.getLanguage())) {
            a3 = a.getBasePath();
            str = dataDTO.getClassName() + "Test." + LanguageEnum.C_LANGUAGE_01.getSuffix();
        } else if (StringUtils.equalsIgnoreCase(LanguageEnum.PYTHON_LANGUAGE_01.getDescription(), dataDTO.getLanguage())) {
            a3 = a.getBasePath();
            str = dataDTO.getClassName() + "Test." + LanguageEnum.PYTHON_LANGUAGE_01.getSuffix();
        } else {
            a3 = a.getBasePath();
            String path = dataDTO.getPath();
            str = "";
            if (StringUtils.isNotBlank(path) && path.contains(CodeCompleteService.H("\""))) {
                str = dataDTO.getClassName() + "Test." + path.substring(path.lastIndexOf(EditorUtils.H("c")) + 1);
            }
        }
        Application application = ApplicationManager.getApplication();
        String str4 = str;
        String str5 = a3;
        application.invokeLater(() -> {
            application.runReadAction(() -> {
                NewFileUtils.handleCreateFile(a, dataDTO, str4, str5, dataDTO.getLanguage(), CodeCollectEnum.UNITTEST);
            });
        });
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static JsonObject requestTestCaseErr(ResponseDto a, CommandEnum a2, MessageDto a3) {
        JsonObject jsonObject;
        JsonObject jsonObject2 = new JsonObject();
        JsonObject jsonObject3 = new JsonObject();
        switch (a2) {
            case CODE_TEST_CASE:
                do {
                } while (0 != 0);
                jsonObject2.addProperty(CodeCompleteService.H("JbN~"), WebViewDataTypeEnum.UNIT_TEST_GET_METHOD_CASE.getType());
                jsonObject = jsonObject3;
                jsonObject.addProperty(EditorUtils.H(";\u007f)"), a3.getPid());
                break;
            case CODE_TEST_CODE:
                RequestCaseCodeDto requestCaseCodeDto = a3.getRequestCaseCodeDto();
                jsonObject2.addProperty(CodeCompleteService.H("JbN~"), WebViewDataTypeEnum.UNIT_TEST_GET_CASE_CODE.getType());
                jsonObject3.addProperty(EditorUtils.H("\u007f)"), requestCaseCodeDto.getValue().getId());
                jsonObject3.addProperty(CodeCompleteService.H("kW\u007f"), requestCaseCodeDto.getValue().getPid());
                jsonObject3.addProperty(EditorUtils.H("d2f("), requestCaseCodeDto.getValue().getType());
            case CODE_TEST_ANALYSIS:
            case CODE_TEST_SAVE:
            case TEST_MAKE_CASE:
            default:
                jsonObject = jsonObject3;
                break;
            case TEST_MAKE_CODE:
                jsonObject2.addProperty(EditorUtils.H("d2f("), WebViewDataTypeEnum.UNIT_TEST_RECEIVE_FUNCTION_CASE_CODE.getType());
                jsonObject = jsonObject3;
                jsonObject3.addProperty(CodeCompleteService.H("W\u007f"), a3.getId());
                jsonObject3.addProperty(EditorUtils.H("h$q8y#"), CodeCompleteService.H("ILiQi"));
                jsonObject3.addProperty(EditorUtils.H("3\u007f2c*q("), a.getMsg());
                break;
            case CODE_TEST_MAKE_CASE_JAVA:
                jsonObject2.addProperty(CodeCompleteService.H("JbN~"), WebViewDataTypeEnum.UNIT_TEST_RECEIVE_FUNCTION_CASE.getType());
                jsonObject3.addProperty(EditorUtils.H("\u007f)"), a3.getId());
                jsonObject3.addProperty(CodeCompleteService.H("{I_hQu"), EditorUtils.H("$b9y?"));
                jsonObject3.addProperty(CodeCompleteService.H("]tZ~"), a.getCode());
                if (!StringUtils.equals(EditorUtils.H("~&y"), a.getCode())) {
                    jsonObject = jsonObject3;
                    jsonObject.addProperty(CodeCompleteService.H("al_MzY~"), a.getMsg());
                    break;
                } else {
                    jsonObject3.addProperty(CodeCompleteService.H("al_MzY~"), BasicActionsBundle.message(EditorUtils.H(".i5)(R\u0018n24,b(O\u0013rcr>v)E\bbcd(w1hod.n9"), new Object[0]));
                    jsonObject = jsonObject3;
                    break;
                }
        }
        jsonObject.addProperty(CodeCompleteService.H("Zh[Al_MzY~"), a.getMsg());
        jsonObject2.add(EditorUtils.H("7q'c("), jsonObject3);
        return jsonObject2;
    }

    public void testSave(Project a, String a2, MessageDto a3) {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> {
            application.runReadAction(() -> {
                PsiFile findFile;
                JsonObject jsonObject3;
                try {
                    LocalFileSystem.getInstance().refresh(false);
                    VirtualFile refreshAndFindFileByPath = LocalFileSystem.getInstance().refreshAndFindFileByPath(a2);
                    if (refreshAndFindFileByPath != null && (findFile = PsiManager.getInstance(a).findFile(refreshAndFindFileByPath)) != null) {
                        EditorHelper.openInEditor(findFile);
                        try {
                            JavaCodeStyleManager javaCodeStyleManager = JavaCodeStyleManager.getInstance(a);
                            javaCodeStyleManager.optimizeImports(findFile);
                            javaCodeStyleManager.shortenClassReferences(findFile);
                            jsonObject3 = jsonObject2;
                        } catch (Throwable th) {
                            f638enum.info("codeStyle error:" + th);
                            jsonObject3 = jsonObject2;
                        }
                        jsonObject3.addProperty(EditorUtils.H("\u007f)"), a3.getId());
                        jsonObject2.addProperty(CodeCompleteService.H("xL\u007fleJn"), EditorUtils.H("}"));
                        jsonObject.addProperty(CodeCompleteService.H("[s^n"), WebViewDataTypeEnum.UNIT_TEST_GET_ALL_CODE_FILE.getType());
                        jsonObject.add(EditorUtils.H("7q'c("), jsonObject2);
                        SocketMessageHandleListener.send2Web(a, jsonObject);
                        return;
                    }
                    jsonObject2.addProperty(CodeCompleteService.H("Go"), a3.getId());
                    jsonObject2.addProperty(EditorUtils.H(",\u007f2S$r("), CodeCompleteService.H(":"));
                    jsonObject.addProperty(EditorUtils.H("d2f("), WebViewDataTypeEnum.UNIT_TEST_GET_ALL_CODE_FILE.getType());
                    jsonObject.add(CodeCompleteService.H("zNf[n"), jsonObject2);
                    f638enum.info(EditorUtils.H("C4r09h\u0001\t= s/c|S\u0014sms:q9D[`$s.p?vav\"z("));
                    SocketMessageHandleListener.send2Web(a, jsonObject);
                } catch (Throwable th2) {
                    f638enum.info("codeStyle error:" + th2);
                }
            });
        });
    }

    public static void notice(Project a) {
        NotificationGroupManager.getInstance().getNotificationGroup(EditorUtils.H("\u001a\u007f.n>`pt.d\"u(")).createNotification(BasicActionsBundle.message(CodeCompleteService.H("zjR\\|\u0011{Q~p^L\"{\u007fSkJrni]eMidtWhU.[oQx"), new Object[0]), MessageType.ERROR).setTitle(BasicActionsBundle.message(EditorUtils.H("<u3(v\b\u001ct%u%brb\u0018\u007f9o)F?T\u0012y#F(j+jod.n9"), new Object[0])).setContent(BasicActionsBundle.message(CodeCompleteService.H("|q^FhZ4[rrJFb[uN1Qe\\~\u0007aGtPg_x\u000be]xF~"), new Object[0])).notify(a);
    }

    public static void generateUnitTestFile(Project a, String a2) {
        JsonElement jsonElement = ((JsonObject) new Gson().fromJson(a2, JsonObject.class)).get(EditorUtils.H("7q'c("));
        MessageDto messageDto = new MessageDto(jsonElement.getAsJsonObject().get(CodeCompleteService.H("@h")).getAsString(), CommandEnum.CODE_TEST_SAVE.getType());
        messageDto.setPath(jsonElement.getAsJsonObject().get(EditorUtils.H("w/r5i+n$@*b%")).getAsString());
        messageDto.setData(jsonElement.getAsJsonObject().get(CodeCompleteService.H("yNSecjMi")));
        PluginWebsocketClient.sendWsMessage(messageDto, a);
    }

    public static void mappingUnitTestFile(Project a, String a2) {
        JsonElement jsonElement = ((JsonObject) new Gson().fromJson(a2, JsonObject.class)).get(CodeCompleteService.H("vAi\\i"));
        String asString = jsonElement.getAsJsonObject().get(EditorUtils.H("w/r5i+n$@*b%")).getAsString();
        JsonArray asJsonArray = jsonElement.getAsJsonObject().getAsJsonArray(CodeCompleteService.H("h]DnCmL\u007f"));
        ApplicationManager.getApplication().invokeLater(() -> {
            try {
                VirtualFile findFileByPath = LocalFileSystem.getInstance().findFileByPath(asString);
                if (findFileByPath == null) {
                    f638enum.warn(EditorUtils.H("C4r09h\u0001\t= s/c|S\u0014sms:q9D[`$s.p?vav\"z("));
                    return;
                }
                Editor openTextEditor = FileEditorManager.getInstance(a).openTextEditor(new OpenFileDescriptor(a, findFileByPath), true);
                if (openTextEditor != null) {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = asJsonArray.iterator();
                    while (it.hasNext()) {
                        JsonElement jsonElement2 = (JsonElement) it.next();
                        Integer valueOf = Integer.valueOf(jsonElement2.getAsJsonObject().get(CodeCompleteService.H("lQa]~fjxHk_")).getAsInt());
                        Integer valueOf2 = Integer.valueOf(jsonElement2.getAsJsonObject().get(EditorUtils.H("?k:U'v8s9")).getAsInt());
                        Boolean valueOf3 = Boolean.valueOf(jsonElement2.getAsJsonObject().get(CodeCompleteService.H("[imNb_")).getAsBoolean());
                        Boolean valueOf4 = Boolean.valueOf(jsonElement2.getAsJsonObject().get(EditorUtils.H("(c\u0004c9")).getAsBoolean());
                        Boolean valueOf5 = Boolean.valueOf(StringUtils.isEmpty(jsonElement2.getAsJsonObject().get(CodeCompleteService.H("xUqAi[cFbJ^v_")).getAsString()));
                        it = it;
                        arrayList.add(new HighlighterUtil.EditorBranchRange(valueOf.intValue(), valueOf2.intValue(), valueOf3.booleanValue(), valueOf4.booleanValue(), valueOf5.booleanValue()));
                    }
                    HighlighterUtil.highlightText(openTextEditor, arrayList);
                }
            } catch (Exception e2) {
            }
        });
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void mergeFields(PsiJavaFile a, PsiJavaFile a2) {
        PsiClass psiClass = a2.getClasses()[0];
        PsiField[] fields = a.getClasses()[0].getFields();
        int length = fields.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            PsiField psiField = fields[i2];
            if (!Ta(a2, psiField)) {
                psiClass.addBefore(psiField, psiClass.getLastChild());
            }
            i2++;
            i = i2;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static void SB(PsiJavaFile a, PsiJavaFile a2) {
        PsiClass[] classes = a2.getClasses();
        PsiClass[] classes2 = a.getClasses();
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(a2.getProject());
        if (classes2.length > 0) {
            if (classes.length <= 0) {
                a2.add(elementFactory.createClass(classes2[0].getName()));
            }
            PsiClass psiClass = a2.getClasses()[0];
            PsiMethod[] methods = classes2[0].getMethods();
            int length = methods.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                PsiMethod psiMethod = methods[i2];
                String name = psiMethod.getName();
                if (StringUtils.equals(CodeCompleteService.H("\u007fzN[["), name)) {
                    Optional findFirst = Arrays.stream(psiClass.getMethods()).filter(a3 -> {
                        return StringUtils.equals(CodeCompleteService.H("\u007f\\hzz"), a3.getName());
                    }).findFirst();
                    if (findFirst.isPresent()) {
                        PsiMethod psiMethod2 = (PsiMethod) findFirst.get();
                        if (!StringUtils.equals(psiMethod2.getText(), psiMethod.getText())) {
                            PsiCodeBlock body = psiMethod2.getBody();
                            PsiCodeBlock body2 = psiMethod.getBody();
                            if (body != null && body2 != null) {
                                PsiStatement[] statements = body.getStatements();
                                PsiStatement[] statements2 = body2.getStatements();
                                int length2 = statements2.length;
                                int i3 = 0;
                                int i4 = 0;
                                while (i3 < length2) {
                                    PsiStatement psiStatement = statements2[i4];
                                    if ((psiStatement instanceof PsiExpressionStatement) && !Arrays.stream(statements).anyMatch(a4 -> {
                                        return StringUtils.equals(psiStatement.getText(), a4.getText());
                                    })) {
                                        body.addBefore(psiStatement, body.getLastChild());
                                    }
                                    i4++;
                                    i3 = i4;
                                }
                            }
                        }
                    }
                } else {
                    while (psiClass.findMethodsByName(name, false).length > 0) {
                        name = EC(name);
                    }
                    psiMethod.setName(name);
                    psiClass.addBefore(psiMethod, psiClass.getLastChild());
                }
                i2++;
                i = i2;
            }
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void copyCaseCode(String a) {
        RequestCaseCodeDto requestCaseCodeDto = (RequestCaseCodeDto) new Gson().fromJson(a, RequestCaseCodeDto.class);
        if (!Objects.isNull(requestCaseCodeDto) && !Objects.isNull(requestCaseCodeDto.getValue())) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(requestCaseCodeDto.getValue().getCode()), (ClipboardOwner) null);
        }
    }

    public static void sendUnitTestBankData(Project a, UnitTestDto.DataDTO.FunctionDataDTO a2) {
        JsonElement jsonTree = new Gson().toJsonTree(a2);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CodeCompleteService.H("Ld]m"), WebViewDataTypeEnum.UNIT_TEST_RECEIVE_FUNCTION_CASE_CODE.getType());
        jsonObject.add(EditorUtils.H("7q'c("), jsonTree);
        SocketMessageHandleListener.send2Web(a, jsonObject);
    }

    public static void javaUnitTestAnalysis(JsonObject a, MessageDto a2, Project a3) {
        UnitTestAgentDto unitTestAgentDto = (UnitTestAgentDto) new Gson().fromJson(a.get(EditorUtils.H("t*b,")), UnitTestAgentDto.class);
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> {
            application.runReadAction(() -> {
                VirtualFile refreshAndFindFileByPath = LocalFileSystem.getInstance().refreshAndFindFileByPath(a2.getPath());
                if (refreshAndFindFileByPath != null) {
                    PsiFile findFile = PsiManager.getInstance(a3).findFile(refreshAndFindFileByPath);
                    Module findModuleForFile = ModuleUtilCore.findModuleForFile(refreshAndFindFileByPath, a3);
                    if (findModuleForFile == null) {
                        notice(a3);
                        return;
                    }
                    String path = ModuleRootManager.getInstance(findModuleForFile).getContentRoots()[0].getPath();
                    Document document = PsiDocumentManager.getInstance(a3).getDocument(findFile);
                    MethodGeneratorConfig methodGeneratorConfig = new MethodGeneratorConfig();
                    methodGeneratorConfig.setTestFramework(UnitTestBaseEnum.findByName(unitTestAgentDto.getTestFrame()));
                    methodGeneratorConfig.setMockFramework(UnitTestMockEnum.findByName(unitTestAgentDto.getMockFrame()));
                    methodGeneratorConfig.setPsiFile(findFile);
                    String testPath = BatchUnitTestTemplateService.getTestPath(a3, refreshAndFindFileByPath);
                    PsiClass psiClass = null;
                    UnitTestDto.DataDTO dataDTO = new UnitTestDto.DataDTO();
                    dataDTO.setClassName(unitTestAgentDto.getName());
                    dataDTO.setId(IdUtil.fastSimpleUUID());
                    dataDTO.setPath(a2.getPath());
                    dataDTO.setTestFrame(unitTestAgentDto.getTestFrame());
                    dataDTO.setMockFrame(unitTestAgentDto.getMockFrame());
                    AICodeSettingsState aICodeSettingsState = AICodeSettingsState.getInstance();
                    if (aICodeSettingsState.modifyTestFrame && StringUtils.equals(EditorUtils.H("`9N\u001ed,u?P0s5D.e9"), a2.getPid()) && aICodeSettingsState.modifyTestFramenNum.intValue() == 0) {
                        dataDTO.setTestFrameAlert(true);
                        aICodeSettingsState.modifyTestFramenNum = Integer.valueOf(aICodeSettingsState.modifyTestFramenNum.intValue() + 1);
                    }
                    dataDTO.setOperationTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern(CodeCompleteService.H("Gb\u0017Rh-Kn\tDQ\u0006`E"))));
                    dataDTO.setStructure(unitTestAgentDto.getStructure());
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<UnitTestAgentDto.method> it = unitTestAgentDto.getMethods().iterator();
                    while (it.hasNext()) {
                        UnitTestAgentDto.method next = it.next();
                        List<CodeInfoDto.RangeDTO> range = next.getRange();
                        List<PsiMethod> psiMethodList = getPsiMethodList(findFile, document.getLineStartOffset(range.get(0).getLine().intValue()), document.getLineEndOffset(range.get(1).getLine().intValue()));
                        if (!CollectionUtils.isEmpty(psiMethodList)) {
                            PsiMethod psiMethod = psiMethodList.get(0);
                            arrayList.add(psiMethod);
                            psiClass = psiMethod.getContainingClass();
                            UnitTestDto.DataDTO.FunctionDataDTO functionDataDTO = new UnitTestDto.DataDTO.FunctionDataDTO();
                            functionDataDTO.setFunctionName(next.getName());
                            functionDataDTO.setId(IdUtil.fastSimpleUUID());
                            functionDataDTO.setCode(next.getCode());
                            functionDataDTO.setRange(range);
                            arrayList2.add(functionDataDTO);
                            it = it;
                        } else {
                            notice(a3);
                            return;
                        }
                    }
                    String testPath2 = getTestPath(a3, a2.getPath(), path, psiClass.getName());
                    PsiClass psiClass2 = psiClass;
                    dataDTO.setTestClassAbsolutePath(testPath2);
                    dataDTO.setFunctionData(arrayList2);
                    methodGeneratorConfig.setUnitTestDto(dataDTO);
                    PluginStartupActivity.handleExecutorService.execute(() -> {
                        startMethodGenerate(a3, methodGeneratorConfig, a2.getPath(), testPath, path, psiClass2, arrayList, true);
                    });
                }
            });
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0054  */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static List<UnitTestDto.DataDTO.FunctionDataDTO> IC(Project a, UnitTestAgentDto a2, UnitTestDto a3, UnitTestDto.DataDTO a4) {
        List<UnitTestAgentDto.method> list;
        Iterator<UnitTestAgentDto.method> it;
        List<UnitTestAgentDto.method> methods = a2.getMethods();
        ArrayList arrayList = new ArrayList();
        AICodeSettingsState aICodeSettingsState = AICodeSettingsState.getInstance();
        if (CollectionUtils.isNotEmpty(methods)) {
            if (methods.size() > 1) {
                list = methods;
                a3.setLevel(CodeCompleteService.H("oiA|Y"));
                it = list.iterator();
                while (it.hasNext()) {
                    UnitTestAgentDto.method next = it.next();
                    UnitTestDto.DataDTO.FunctionDataDTO functionDataDTO = new UnitTestDto.DataDTO.FunctionDataDTO();
                    functionDataDTO.setFunctionName(next.getName());
                    functionDataDTO.setId(IdUtil.fastSimpleUUID());
                    functionDataDTO.setMethodContent(next.getCode());
                    functionDataDTO.setUnitTest(aICodeSettingsState.testFramework);
                    functionDataDTO.setUnitMock(StringUtils.equals(UnitTestMockEnum.OFF.getName(), aICodeSettingsState.mockFramework) ? "" : aICodeSettingsState.mockFramework);
                    arrayList.add(functionDataDTO);
                    functionDataDTO.setTestTemplate("");
                    MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.CODE_TEST_CASE.getType());
                    messageDto.setData(next.getCode());
                    messageDto.setPid(a3.getId() + "," + a4.getId() + "," + functionDataDTO.getId());
                    it = it;
                    PluginWebsocketClient.sendWsMessage(messageDto, a);
                }
                if (arrayList == null) {
                    m358enum(0);
                }
                return arrayList;
            }
            a3.setLevel(EditorUtils.H("c+t\"d\"y#"));
        }
        list = methods;
        it = list.iterator();
        while (it.hasNext()) {
        }
        if (arrayList == null) {
        }
        return arrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void requestCaseCode(String a, Project a2) {
        RequestCaseCodeDto requestCaseCodeDto = (RequestCaseCodeDto) new Gson().fromJson(a, RequestCaseCodeDto.class);
        if (!Objects.isNull(requestCaseCodeDto) && !Objects.isNull(requestCaseCodeDto.getValue())) {
            RequestCaseCodeDto.ValueDTO value = requestCaseCodeDto.getValue();
            MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.CODE_TEST_CODE.getType());
            messageDto.setRequestCaseCodeDto(requestCaseCodeDto);
            messageDto.setPath(value.getAbsolutePath());
            value.setCode(value.getMethodContent());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(CodeCompleteService.H("ocKPuflDi"), value.getClassName());
            jsonObject.addProperty(EditorUtils.H("l?q6u%^*{("), value.getMethodName());
            jsonObject.addProperty(CodeCompleteService.H("xjYW@ZlDi"), value.getUnitTest());
            jsonObject.addProperty(EditorUtils.H("7j=q\u0007b*{("), value.getUnitMock());
            jsonObject.addProperty(CodeCompleteService.H("~L\u007flXJv\\dFb"), value.getCaseDescription());
            jsonObject.addProperty(EditorUtils.H("(~;c9"), value.getInputValue());
            jsonObject.addProperty(CodeCompleteService.H("Ls\\}\\x"), value.getOutputValue());
            messageDto.setData(jsonObject);
            PluginWebsocketClient.sendWsMessage(messageDto, a2);
        }
    }

    public static JsonObject receiveFunction(UnitTestDto.DataDTO a) {
        JsonElement jsonTree = new Gson().toJsonTree(a);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CodeCompleteService.H("W\u007fRb"), WebViewDataTypeEnum.UNIT_TEST_FUNCTION_LIST.getType());
        jsonObject.add(EditorUtils.H("7q'c("), jsonTree);
        return jsonObject;
    }

    public static void startMethodGenerate(Project a, MethodGeneratorConfig a2, String a3, String a4, String a5, PsiClass a6, List<PsiMethod> list, boolean z) {
        a2.setPath(a3);
        a2.setTestDirectoryPath(a4);
        a2.setModulePath(a5);
        a2.setPsiClass(a6);
        a2.setMethods(list);
        a2.setMethodUt(z);
        a2.setExcludeMethodList(new ArrayList());
        TemplateGenerator.createTestClass(a, a2);
    }

    private static void ZA(Method a, PsiMethod a2, ArrayList<UnitTestDto.DataDTO.FunctionDataDTO.CodeList> arrayList, StringBuffer a3) {
        CaseBranch caseBranch = new CaseBranch();
        caseBranch.setResult(true);
        caseBranch.setEndOffset(Integer.valueOf(a.getEndOffset().intValue() - 1));
        caseBranch.setStartOffset(Integer.valueOf(a.getStartOffset().intValue() - 1));
        caseBranch.setConditionText("");
        CaseResult caseResult = new CaseResult(a2.getName(), new HashMap(), new ArrayList(), new CaseParam(CodeCompleteService.H("hP"), "", "", null), "");
        caseResult.setBranches(new e(caseBranch));
        arrayList.add(aa(a2, caseResult, a3));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void saveUnitTestFile(Project a, UnitTestDto.DataDTO a2) {
        String str;
        String str2;
        JavaCodeStyleManager javaCodeStyleManager = JavaCodeStyleManager.getInstance(a);
        String testClasPath = a2.getTestClasPath();
        List<UnitTestDto.DataDTO.FunctionDataDTO> functionData = a2.getFunctionData();
        if (!testClasPath.contains(File.separator + "src" + File.separator + "test" + File.separator) && CollUtil.isNotEmpty(functionData)) {
            Matcher matcher = Pattern.compile(CodeCompleteService.H("|^yS|mJ\u0005.\n:\u0012\u0006")).matcher(functionData.get(0).getTestContent());
            String str3 = "";
            if (matcher.find()) {
                String group = matcher.group(0);
                str3 = group.substring(group.indexOf(EditorUtils.H(".{\"{*q(")) + CodeCompleteService.H("_DcKdNi").length(), group.indexOf(EditorUtils.H("v"))).trim();
            }
            String replace = str3.replace(CodeCompleteService.H("\""), File.separator);
            String findCommonPrefix = findCommonPrefix(testClasPath, replace);
            if (!StringUtils.isNotBlank(findCommonPrefix)) {
                str = replace;
                str2 = testClasPath;
            } else {
                str = replace.substring(replace.indexOf(findCommonPrefix) + findCommonPrefix.length());
                str2 = testClasPath;
            }
            testClasPath = str2 + File.separator + str;
        }
        String str4 = testClasPath;
        String testClassName = a2.getTestClassName();
        String str5 = str4 + File.separator + testClassName;
        WriteCommandAction.runWriteCommandAction(a, () -> {
            Path path = Paths.get(str5, new String[0]);
            Iterator<UnitTestDto.DataDTO.FunctionDataDTO> it = a2.getFunctionData().iterator();
            while (it.hasNext()) {
                PsiJavaFile createFileFromText = PsiFileFactory.getInstance(a).createFileFromText(testClassName, JavaFileType.INSTANCE, it.next().getTestContent());
                HashSet hashSet = new HashSet();
                if (createFileFromText.getClasses().length == 0) {
                    return;
                }
                if (createFileFromText.getClasses()[0].getMethods().length == 0) {
                    f638enum.warn(EditorUtils.H("名帨协洊旦斫斯沘"));
                    return;
                }
                CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(a);
                javaCodeStyleManager.removeRedundantImports(createFileFromText);
                javaCodeStyleManager.optimizeImports(createFileFromText);
                if (!Files.exists(path, new LinkOption[0])) {
                    String packageName = createFileFromText.getPackageName();
                    if (StringUtils.isNotBlank(packageName) && !StringUtils.equals(createFileFromText.getPackageName(), packageName)) {
                        createFileFromText.setPackageName(packageName);
                    }
                    PsiClass[] classes = createFileFromText.getClasses();
                    int length = classes.length;
                    int i = 0;
                    int i2 = 0;
                    while (i < length) {
                        PsiClass psiClass = classes[i2];
                        i2++;
                        psiClass.setName(psiClass.getName());
                        i = i2;
                    }
                    codeStyleManager.reformat(createFileFromText);
                    javaCodeStyleManager.optimizeImports(createFileFromText);
                    NewFileUtils.creatFile(a, createFileFromText.getText(), testClassName, str4, CodeCollectEnum.UNITTEST);
                } else {
                    VirtualFile findFileByPath = LocalFileSystem.getInstance().findFileByPath(str5);
                    if (findFileByPath == null) {
                        return;
                    }
                    PsiJavaFile findFile = PsiManager.getInstance(a).findFile(findFileByPath);
                    xb(a, createFileFromText, findFile, hashSet, javaCodeStyleManager, testClassName, codeStyleManager);
                    if (findFile != null) {
                        EditorHelper.openInEditor(findFile);
                    }
                }
            }
        });
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(EditorUtils.H("d2f("), WebViewDataTypeEnum.UNIT_TESTING_RESPONSE_SAVE.getType());
        SocketMessageHandleListener.send2Web(a, jsonObject);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: eb */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/UnitTestService$d.class */
    public class d extends TypeToken<List<String>> {
        public d() {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public JsonObject getTestCase(JsonObject a, MessageDto a2) {
        List list = (List) new Gson().fromJson(a.get(CodeCompleteService.H("[{]m")), new d().getType());
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            it = it;
            jsonObject2.addProperty(EditorUtils.H("&w-S*e("), str);
            jsonObject2.addProperty(CodeCompleteService.H("j@h"), a2.getPid());
        }
        jsonObject.addProperty(EditorUtils.H("d2f("), WebViewDataTypeEnum.UNIT_TEST_GET_METHOD_CASE.getType());
        jsonObject.add(CodeCompleteService.H("~^v\\i"), jsonObject2);
        return jsonObject;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    private static List<Integer> tc(PsiElement a) {
        ArrayList newArrayList = Lists.newArrayList();
        TextRange textRange = a.getTextRange();
        int startOffset = textRange.getStartOffset();
        int endOffset = textRange.getEndOffset();
        int i = startOffset;
        int i2 = i;
        while (i <= endOffset) {
            int i3 = i2;
            i2++;
            newArrayList.add(Integer.valueOf(i3));
            i = i2;
        }
        if (newArrayList == null) {
            m358enum(3);
        }
        return newArrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x014f  */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void QC(Project a, JsonObject a2, MessageDto a3) {
        UnitTestAgentDto unitTestAgentDto;
        UnitTestAgentDto unitTestAgentDto2 = (UnitTestAgentDto) new Gson().fromJson(a2, UnitTestAgentDto.class);
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        StringBuffer text = a3.getText();
        if (!StringUtils.equalsIgnoreCase(CommandEnum.TEST_MAKE_CODE.getType(), a3.getCommand())) {
            jsonObject.addProperty(EditorUtils.H("d2f("), WebViewDataTypeEnum.UNIT_TEST_RECEIVE_FUNCTION_CASE.getType());
            if (a2.has(CodeCompleteService.H("i]w^"))) {
                text.append(unitTestAgentDto2.getText());
                jsonObject2.addProperty(EditorUtils.H("d.n9"), text.toString());
                jsonObject2.addProperty(CodeCompleteService.H("fN"), a3.getId());
            }
        } else {
            jsonObject.addProperty(CodeCompleteService.H("iA\u007fO"), WebViewDataTypeEnum.UNIT_TEST_RECEIVE_FUNCTION_CASE_CODE.getType());
            if (a2.has(EditorUtils.H("d.n9"))) {
                text.append(unitTestAgentDto2.getText());
                jsonObject2.addProperty(CodeCompleteService.H("LkZi^WkO"), text.toString());
                if (!StringUtils.equalsIgnoreCase(LanguageEnum.JAVA.getDescription(), a3.getLang())) {
                    jsonObject2.addProperty(CodeCompleteService.H("fN"), a3.getPid());
                    unitTestAgentDto = unitTestAgentDto2;
                } else {
                    unitTestAgentDto = unitTestAgentDto2;
                    jsonObject2.addProperty(EditorUtils.H("\u007f)"), a3.getId());
                }
                if (StringUtils.isNotBlank(unitTestAgentDto.getError())) {
                    jsonObject.addProperty(EditorUtils.H("w;i1\u007f%e("), CodeCompleteService.H("ioJ`X"));
                    jsonObject.addProperty(EditorUtils.H("3\u007f2c*q("), unitTestAgentDto2.getError());
                    PluginWebsocketClient.AGENT_REQUEST.remove(a3.getId());
                }
                jsonObject2.addProperty(CodeCompleteService.H("[i|K`D"), unitTestAgentDto2.getReason());
                if (!unitTestAgentDto2.isEnded()) {
                    f638enum.debug("xmlCse >>> " + text.toString());
                    return;
                } else {
                    jsonObject.add(EditorUtils.H("7q'c("), jsonObject2);
                    SocketMessageHandleListener.send2Web(a, jsonObject);
                    return;
                }
            }
        }
        unitTestAgentDto = unitTestAgentDto2;
        if (StringUtils.isNotBlank(unitTestAgentDto.getError())) {
        }
        jsonObject2.addProperty(CodeCompleteService.H("[i|K`D"), unitTestAgentDto2.getReason());
        if (!unitTestAgentDto2.isEnded()) {
        }
    }

    private static boolean Qc(List<Integer> list, List<Integer> list2) {
        list.retainAll(list2);
        return CollectionUtils.isNotEmpty(list);
    }

    private static void EA(Project a, String a2) {
        UnitTestDto.DataDTO dataDTO = (UnitTestDto.DataDTO) new Gson().fromJson(((JsonObject) new Gson().fromJson(a2, JsonObject.class)).get(CodeCompleteService.H("zh@xM")), UnitTestDto.DataDTO.class);
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> {
            application.runReadAction(() -> {
                VirtualFile refreshAndFindFileByPath = LocalFileSystem.getInstance().refreshAndFindFileByPath(dataDTO.getPath());
                if (refreshAndFindFileByPath != null) {
                    PsiFile findFile = PsiManager.getInstance(a).findFile(refreshAndFindFileByPath);
                    Module findModuleForFile = ModuleUtilCore.findModuleForFile(refreshAndFindFileByPath, a);
                    if (findModuleForFile != null) {
                        String path = ModuleRootManager.getInstance(findModuleForFile).getContentRoots()[0].getPath();
                        Document document = PsiDocumentManager.getInstance(a).getDocument(findFile);
                        MethodGeneratorConfig methodGeneratorConfig = new MethodGeneratorConfig();
                        methodGeneratorConfig.setTestFramework(UnitTestBaseEnum.findByName(dataDTO.getTestFrame()));
                        methodGeneratorConfig.setMockFramework(UnitTestMockEnum.findByName(dataDTO.getMockFrame()));
                        if (dataDTO.isModifyTestFrame()) {
                            AICodeSettingsState aICodeSettingsState = AICodeSettingsState.getInstance();
                            aICodeSettingsState.testFramework = dataDTO.getTestFrame();
                            aICodeSettingsState.mockFramework = dataDTO.getMockFrame();
                            aICodeSettingsState.modifyTestFrame = dataDTO.isModifyTestFrame();
                        }
                        methodGeneratorConfig.setPsiFile(findFile);
                        String testPath = BatchUnitTestTemplateService.getTestPath(a, refreshAndFindFileByPath);
                        PsiClass psiClass = null;
                        dataDTO.setId(IdUtil.fastSimpleUUID());
                        dataDTO.setOperationTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern(CodeCompleteService.H("Gb\u0017Rh-Kn\tDU\u0002`E"))));
                        ArrayList arrayList = new ArrayList();
                        Iterator<UnitTestDto.DataDTO.FunctionDataDTO> it = dataDTO.getFunctionData().iterator();
                        while (it.hasNext()) {
                            UnitTestDto.DataDTO.FunctionDataDTO next = it.next();
                            List<CodeInfoDto.RangeDTO> range = next.getRange();
                            List<PsiMethod> psiMethodList = getPsiMethodList(findFile, document.getLineStartOffset(range.get(0).getLine().intValue()), document.getLineEndOffset(range.get(1).getLine().intValue()));
                            if (CollectionUtils.isEmpty(psiMethodList)) {
                                notice(a);
                                return;
                            }
                            PsiMethod psiMethod = psiMethodList.get(0);
                            arrayList.add(psiMethod);
                            psiClass = psiMethod.getContainingClass();
                            next.setId(IdUtil.fastSimpleUUID());
                            it = it;
                        }
                        methodGeneratorConfig.setUnitTestDto(dataDTO);
                        PsiClass psiClass2 = psiClass;
                        CommonService.openPage(a, PageEnum.UNIT_TEST);
                        PluginStartupActivity.handleExecutorService.execute(() -> {
                            startMethodGenerate(a, methodGeneratorConfig, dataDTO.getPath(), testPath, path, psiClass2, arrayList, true);
                        });
                        return;
                    }
                    notice(a);
                }
            });
        });
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static void hc(Project a, UnitTestDto.DataDTO.FunctionDataDTO a2) {
        List<MessageDto> list = (List) CreateTestMethodTask.cacheFileTemplateMap.get(a2.getPath()).getParamMaps().get(EditorUtils.H("h;i2q,s>"));
        if (!CollectionUtils.isEmpty(list)) {
            for (MessageDto messageDto : list) {
                if (StringUtils.equals(a2.getId(), messageDto.getId())) {
                    PluginWebsocketClient.sendWsMessage(messageDto, a);
                }
            }
            return;
        }
        f638enum.warn(CodeCompleteService.H("甶戜甌侊凕锓"));
        sendUnitTestErrInfo(a, WebViewDataTypeEnum.UNIT_TEST_RECEIVE_FUNCTION_CASE, BasicActionsBundle.message(EditorUtils.H("si5bct>t(skC\tu)~o`9I\u0019d,t>)?A\bscd(w1hod.n9"), new Object[0]), a2.getId());
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void handleJavaUnitTestByElement(Project a, Editor a2, PsiElement a3) {
        PsiFile psiFile = PsiDocumentManager.getInstance(a).getPsiFile(a2.getDocument());
        if (psiFile == null) {
            notice(a);
            return;
        }
        VirtualFile virtualFile = ((EditorImpl) a2).getVirtualFile();
        if (virtualFile.getPath() != null) {
            if (ModuleUtilCore.findModuleForFile(virtualFile, a) == null) {
                notice(a);
                return;
            } else if (a3 != null && PsiUtils.instanceOf(a3, CodeCompleteService.H("{RD\"VtLxbG\\z'\\Sl\u0007\\RmdiVoWy"))) {
                TextRange textRange = a3.getTextRange();
                RC(a, a2, textRange.getStartOffset(), textRange.getEndOffset(), psiFile);
                return;
            } else {
                notice(a);
                return;
            }
        }
        notice(a);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static void RC(Project a, Editor a2, int a3, int a4, PsiFile a5) {
        MessageDto messageDto;
        Document document = a2.getDocument();
        int lineNumber = document.getLineNumber(a3);
        int a6 = a3 - document.getLineStartOffset(lineNumber);
        int lineNumber2 = document.getLineNumber(a4);
        int lineStartOffset = a4 - document.getLineStartOffset(lineNumber2);
        CodeInfoDto.RangeDTO rangeDTO = new CodeInfoDto.RangeDTO();
        rangeDTO.setLine(Integer.valueOf(lineNumber));
        rangeDTO.setCharacter(Integer.valueOf(a6));
        CodeInfoDto.RangeDTO rangeDTO2 = new CodeInfoDto.RangeDTO();
        rangeDTO2.setLine(Integer.valueOf(lineNumber2));
        rangeDTO2.setCharacter(Integer.valueOf(lineStartOffset));
        ArrayList arrayList = new ArrayList();
        arrayList.add(rangeDTO);
        arrayList.add(rangeDTO2);
        MessageDto messageDto2 = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.CODE_TEST_ANALYSIS.getType());
        messageDto2.setPath(a5.getVirtualFile().getPath());
        messageDto2.setRange(arrayList);
        messageDto2.setPid(EditorUtils.H("`9N\u001ed,u?P0s5D.e9"));
        messageDto2.setLang(LanguageEnum.JAVA.getDescription());
        JsonObject jsonObject = new JsonObject();
        AICodeSettingsState aICodeSettingsState = AICodeSettingsState.getInstance();
        if (aICodeSettingsState.modifyTestFrame) {
            messageDto = messageDto2;
            jsonObject.addProperty(CodeCompleteService.H("tJy]JlZbO"), aICodeSettingsState.testFramework);
            jsonObject.addProperty(EditorUtils.H("7j=q\u0007b*{("), aICodeSettingsState.mockFramework);
        } else {
            jsonObject.addProperty(CodeCompleteService.H("tJy]JlZbO"), EditorUtils.H("Q\u001eB\u0002"));
            jsonObject.addProperty(CodeCompleteService.H("m@iBJlZbO"), EditorUtils.H("Q\u001eB\u0002"));
            messageDto = messageDto2;
        }
        messageDto.setData(jsonObject);
        a.putUserData(WebViewWindowPanel.UNIT_TEST_MESSAGE_DATA, messageDto2);
        CommonService.openPage(a, PageEnum.UNIT_TEST);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static List<PsiMethod> getPsiMethodList(PsiFile a, int a2, int a3) {
        PsiJavaFile psiJavaFile = (PsiJavaFile) a;
        Collection<PsiMethod> collection = (Collection) ApplicationManager.getApplication().runReadAction(() -> {
            return PsiTreeUtil.findChildrenOfType(psiJavaFile, PsiMethod.class);
        });
        if (CollectionUtils.isEmpty(collection)) {
            return new ArrayList();
        }
        List<Integer> w = w(a2, a3);
        ArrayList arrayList = new ArrayList();
        for (PsiMethod psiMethod : collection) {
            if (Qc(tc(psiMethod), w)) {
                arrayList.add(psiMethod);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    private static void aA(Project a, UnitTestDto.DataDTO.FunctionDataDTO a2) {
        MessageDto messageDto = (MessageDto) PluginWebsocketClient.AGENT_REQUEST.get(a2.getId());
        FileRequestDto fileRequestDto = (FileRequestDto) TemplateRequestService.classModelRenders.get(TemplateRequestService.convertKey(a2.getId(), messageDto.getPath()));
        if (fileRequestDto == null) {
            CreateTestMethodTask.isCanceled.set(true);
            sendUnitTestErrInfo(a, WebViewDataTypeEnum.UNIT_TEST_FUNCTION_CASE_CODE, BasicActionsBundle.message(CodeCompleteService.H("2wUV)QeKi6PLxWu\\3m_Xy[hN1@r]e[\"Ymu\\"), new Object[0]), a2.getId());
            f638enum.warn(EditorUtils.H("侠恿嶹渓阩"));
            return;
        }
        Optional<MethodRequestResult> findFirst = fileRequestDto.getMethodRequestResults().stream().filter(a3 -> {
            return Objects.equals(a3.getRequestId(), messageDto.getId());
        }).findFirst();
        if (findFirst.isPresent()) {
            try {
                try {
                    TemplateRequestService.addCase(a2, findFirst.get().getMethod());
                    MethodRequestResult methodRequestResult = findFirst.get();
                    methodRequestResult.setReturn(true);
                    methodRequestResult.setEndTime(new Date());
                } catch (Exception e2) {
                    f638enum.warn("getSourceType Exception" + e2.getMessage());
                    MethodRequestResult methodRequestResult2 = findFirst.get();
                    methodRequestResult2.setReturn(true);
                    methodRequestResult2.setEndTime(new Date());
                }
            } catch (Throwable th) {
                MethodRequestResult methodRequestResult3 = findFirst.get();
                methodRequestResult3.setReturn(true);
                methodRequestResult3.setEndTime(new Date());
                throw th;
            }
        }
        CacheFileTemplate cacheFileTemplate = CreateTestMethodTask.cacheFileTemplateMap.get(a2.getPath());
        GeneratorFileConfig generatorFileConfig = cacheFileTemplate.getGeneratorFileConfig();
        generatorFileConfig.setFunctionDataDTO(a2);
        new CreateTestMethodTask(a, cacheFileTemplate.getMethodGeneratorConfig()).genCaseCode(generatorFileConfig, cacheFileTemplate.getContext().getFileTemplateConfig(), fileRequestDto);
        PluginWebsocketClient.AGENT_REQUEST.remove(messageDto.getId());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    private static List<Integer> w(int a, int a2) {
        ArrayList arrayList = new ArrayList();
        int i = a;
        int a3 = i;
        while (i <= a2) {
            int i2 = a3;
            a3++;
            arrayList.add(Integer.valueOf(i2));
            i = a3;
        }
        if (arrayList == null) {
            m358enum(2);
        }
        return arrayList;
    }

    public static void sendUnitTestErrInfo(Project a, WebViewDataTypeEnum a2, String a3, String a4) {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject.addProperty(CodeCompleteService.H("^vSc"), a2.getType());
        jsonObject2.addProperty(EditorUtils.H("\u007f)"), a4);
        jsonObject2.addProperty(CodeCompleteService.H("PbK|Lh"), EditorUtils.H("$b9y?"));
        jsonObject2.addProperty(CodeCompleteService.H("MGtYnDc"), a3);
        jsonObject.add(EditorUtils.H("7q'c("), jsonObject2);
        SocketMessageHandleListener.send2Web(a, jsonObject);
    }

    public static void testCollectionGenerate(Project a, List<MethodUnitTestData> list, String a2) {
        if (!CollUtil.isNotEmpty(list)) {
            return;
        }
        UnitTestAgentDto unitTestAgentDto = new UnitTestAgentDto();
        unitTestAgentDto.setMethodUnitTestDataList(list);
        unitTestAgentDto.setCollectScheme(CodeCompleteService.H("BgSvEpX\u007fMuXgX"));
        unitTestAgentDto.setClientName(ApplicationInfo.getInstance().getVersionName());
        unitTestAgentDto.setClientVersion(ApplicationInfo.getInstance().getApiVersion());
        unitTestAgentDto.setPluginVersion(BasicActionsBundle.message(EditorUtils.H("\u001d\u007f.o?brP\u0017c*h4+(\u007f3c\"y#"), new Object[0]));
        MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.LOG_TEST_COLLECTION_GENERATE.getType());
        messageDto.setPath(a2);
        messageDto.setData(unitTestAgentDto);
        PluginWebsocketClient.sendWsMessage(messageDto, a);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void handleJavaUnitTest(Project a, Editor a2) {
        int selectionStart = a2.getSelectionModel().getSelectionStart();
        int selectionEnd = a2.getSelectionModel().getSelectionEnd();
        PsiFile psiFile = PsiDocumentManager.getInstance(a).getPsiFile(a2.getDocument());
        if (psiFile != null) {
            if (ModuleUtilCore.findModuleForFile(((EditorImpl) a2).getVirtualFile(), a) != null) {
                RC(a, a2, selectionStart, selectionEnd, psiFile);
                return;
            } else {
                notice(a);
                return;
            }
        }
        notice(a);
    }
}
