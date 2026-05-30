package com.aicode.template.request;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.ResponseDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.enums.PluginSceneEnum;
import com.aicode.enums.UnitTestMockEnum;
import com.aicode.template.TypeDictionary;
import com.aicode.template.builder.MethodFactory;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.Param;
import com.aicode.template.context.domain.Type;
import com.aicode.template.context.resolved.ResolvedBranch;
import com.aicode.template.context.service.impl.JavaTestBuilderImpl;
import com.aicode.template.generator.GeneratorTemplateConfig;
import com.aicode.template.request.dto.CaseBranch;
import com.aicode.template.request.dto.CaseParam;
import com.aicode.template.request.dto.CaseResult;
import com.aicode.template.request.dto.ToMockMethod;
import com.aicode.test.dto.UnitTestDto;
import com.aicode.util.ClassNameUtils;
import com.aicode.util.JavaPsiUtils;
import com.aicode.util.PsiUtils;
import com.aicode.util.TypeUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/request/TemplateRequestService.class */
public class TemplateRequestService {
    private static final Logger LOG = Logger.getInstance(TemplateRequestService.class);
    public static final Cache<String, FileRequestDto> classModelRenders = CacheUtil.newLRUCache(1000);
    public static final int MAX_TOKEN_CHAR_LENGTH = 22000;
    public static final int MAX_REQUEST_LIMIT = 3;
    public static final long RETRY_WAIT_TIME = 0;
    private static final int CLASS_CACHE_LIMIT = 1000;

    public static synchronized void handleAgentAction(CommandEnum commandEnum, JsonObject asJsonObject, MessageDto messageDto, String id, Project project) {
        if (commandEnum == null) {
            return;
        }
        if (commandEnum == CommandEnum.CODE_TEST_MAKE_CASE_JAVA) {
            FileRequestDto requestDto = (FileRequestDto) classModelRenders.get(convertKey(messageDto.getTaskId(), messageDto.getPath()));
            if (requestDto != null) {
                List<MethodRequestResult> results = requestDto.getMethodRequestResults();
                Optional<MethodRequestResult> result = results.stream().filter(m -> {
                    return Objects.equals(m.getRequestId(), messageDto.getId());
                }).findFirst();
                try {
                    if (result.isPresent()) {
                        try {
                            JsonArray jsonArray = asJsonObject.get("data").getAsJsonArray();
                            if (jsonArray != null && jsonArray.size() > 0) {
                                Method method = result.get().getMethod();
                                analysisString(jsonArray.get(0).getAsString(), method);
                            }
                            MethodRequestResult methodRequestResult = result.get();
                            methodRequestResult.setReturn(true);
                            methodRequestResult.setEndTime(new Date());
                        } catch (Exception e) {
                            LOG.warn("getSourceType Exception" + e.getMessage());
                            MethodRequestResult methodRequestResult2 = result.get();
                            methodRequestResult2.setReturn(true);
                            methodRequestResult2.setEndTime(new Date());
                        }
                    }
                } catch (Throwable th) {
                    MethodRequestResult methodRequestResult3 = result.get();
                    methodRequestResult3.setReturn(true);
                    methodRequestResult3.setEndTime(new Date());
                    throw th;
                }
            } else {
                LOG.warn("信息已清除");
            }
        }
        PluginWebsocketClient.AGENT_REQUEST.remove(id);
    }

    public static synchronized void handleRequestErrorTestCase(ResponseDto responseDto, CommandEnum commandEnum, MessageDto messageDto) {
        if (commandEnum != null && commandEnum == CommandEnum.CODE_TEST_MAKE_CASE_JAVA) {
            FileRequestDto requestDto = (FileRequestDto) classModelRenders.get(convertKey(messageDto.getTaskId(), messageDto.getPath()));
            if (requestDto != null) {
                List<MethodRequestResult> results = requestDto.getMethodRequestResults();
                Optional<MethodRequestResult> result = results.stream().filter(m -> {
                    return Objects.equals(m.getRequestId(), messageDto.getId());
                }).findFirst();
                result.ifPresent(methodRequestResult -> {
                    methodRequestResult.setRequestCount(Integer.valueOf(methodRequestResult.getRequestCount().intValue() + 1));
                    if (methodRequestResult.getRequestCount().intValue() <= 3) {
                    }
                    methodRequestResult.setReturn(true);
                    methodRequestResult.setEndTime(new Date());
                });
                return;
            }
            LOG.warn("处理异常信息已清除");
        }
    }

    private static /* synthetic */ void lambda$handleRequestErrorTestCase$2(MessageDto messageDto, MethodRequestResult methodRequestResult) {
        try {
            Thread.sleep(0L);
            messageDto.setId(IdUtil.fastSimpleUUID());
            methodRequestResult.setRequestId(messageDto.getId());
            PluginWebsocketClient.sendWsMessage(messageDto, messageDto.getProject());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void analysisString(String xmlData, Method method) {
        if (StringUtils.isBlank(xmlData)) {
            return;
        }
        try {
            addCase(xmlData, method, method.getCaseResults());
        } catch (Exception e) {
            LOG.warn("读取模型数据异常", e);
            LOG.warn(JSONUtil.toJsonStr(method.getCaseResults()));
        }
    }

    private static void addCase(String xmlData, Method method, List<CaseResult> unitTestCases) {
        Pattern pattern = Pattern.compile("<test-case>(.*?)</test-case>", 32);
        Matcher matcher = pattern.matcher(xmlData);
        while (matcher.find()) {
            String testCase = matcher.group(1).replaceAll("case_mock_all", "case-mock-all").replaceAll("case_mock", "case-mock").replaceAll("mock_", "mock-");
            if (countMatches(testCase, "<method-name>") == 1 && countMatches(testCase, "<case-mock-all>") == 1 && countMatches(testCase, "<content>") == 1 && countMatches(testCase, "<case-input>") == 1 && countMatches(testCase, "<result>") == 1 && countMatches(testCase, "<type>") == 1) {
                String methodName = convertMethodName(extractTagValue(testCase, "method-name"));
                String content = extractTagValue(testCase, "content");
                String case_input = extractTagValue(testCase, "case-input");
                String result = extractTagValue(testCase, "result");
                extractTagValue(testCase, "type");
                String case_mock_all = extractTagValue(testCase, "case-mock-all");
                String branches = extractTagValue(testCase, "branchs");
                String exception = extractTagValue(testCase, "exception");
                CaseParam output = convertOutput(result, method);
                Map<String, CaseParam> input = convertInput(case_input, method);
                List<ToMockMethod> mockMethods = new ArrayList<>();
                List<CaseBranch> mockBranches = new ArrayList<>();
                if (StringUtils.isNotEmpty(case_mock_all)) {
                    addMock(case_mock_all, mockMethods);
                }
                if (StringUtils.isNotEmpty(branches)) {
                    addBranches(branches, method, mockBranches);
                }
                CaseBranch caseBranch = new CaseBranch();
                caseBranch.setResult(true);
                caseBranch.setEndOffset(Integer.valueOf(method.getEndOffset().intValue() - 1));
                caseBranch.setStartOffset(Integer.valueOf(method.getStartOffset().intValue() - 1));
                caseBranch.setConditionText("");
                caseBranch.setOut(true);
                mockBranches.add(caseBranch);
                CaseResult caseResult = new CaseResult(getMethodName(methodName, unitTestCases), input, mockMethods, output, resolveMessage(content));
                convertException(exception, caseResult, method);
                caseResult.setBranches(mockBranches);
                unitTestCases.add(caseResult);
            }
        }
    }

    public static void addCase(UnitTestDto.DataDTO.FunctionDataDTO functionDataDTO, Method method) {
        for (UnitTestDto.DataDTO.FunctionDataDTO.CodeList caseCode : functionDataDTO.getCodeList()) {
            String methodName = caseCode.getCaseMethodName();
            String content = caseCode.getCaseDescription();
            String case_input = caseCode.getCase_input();
            String result = caseCode.getResult();
            caseCode.getType();
            String case_mock_all = caseCode.getCase_mock_all();
            String branches = caseCode.getCaseBranches();
            String exception = caseCode.getException();
            CaseParam output = convertOutput(result, method);
            Map<String, CaseParam> input = convertInput(case_input, method);
            List<ToMockMethod> mockMethods = new ArrayList<>();
            List<CaseBranch> mockBranches = new ArrayList<>();
            if (StringUtils.isNotEmpty(case_mock_all)) {
                addMock(case_mock_all, mockMethods);
            }
            if (StringUtils.isNotEmpty(branches)) {
                addBranches(branches, method, mockBranches);
            }
            CaseBranch caseBranch = new CaseBranch();
            caseBranch.setResult(true);
            caseBranch.setEndOffset(Integer.valueOf(method.getEndOffset().intValue() - 1));
            caseBranch.setStartOffset(Integer.valueOf(method.getStartOffset().intValue() - 1));
            caseBranch.setConditionText("");
            caseBranch.setOut(true);
            mockBranches.add(caseBranch);
            CaseResult caseResult = new CaseResult(getMethodName(methodName, method.getCaseResults()), input, mockMethods, output, resolveMessage(content));
            convertException(exception, caseResult, method);
            caseResult.setBranches(mockBranches);
            method.getCaseResults().add(caseResult);
        }
    }

    private static String getMethodName(String methodName, List<CaseResult> caseResults) {
        long count = caseResults.stream().filter(caseResult -> {
            return caseResult.getCaseMethodName().equals(methodName);
        }).count();
        if (count > 0) {
            return getMethodName(methodName + count, caseResults);
        }
        return count == 0 ? methodName : methodName + count;
    }

    private static void convertException(String exception, CaseResult caseResult, Method method) {
        try {
            if (StringUtils.isEmpty(exception) || "null".equalsIgnoreCase(exception) || "无".equalsIgnoreCase(exception)) {
                return;
            }
            Set<String> exceptions = method.getExceptions();
            CaseParam caseParam = convertJsonObject(exception, "exception");
            if (caseParam != null && (caseParam.getData() instanceof String)) {
                String exception2 = (String) caseParam.getData();
                String message = null;
                for (String str : exceptions) {
                    String substring = str.substring(str.lastIndexOf(".") + 1);
                    if (exception2.contains(substring)) {
                        caseResult.setException(str);
                        caseResult.setExceptionMessage(exception2);
                        return;
                    }
                }
                if (exception2.contains(":")) {
                    String[] exMessage = exception2.split(":");
                    exception2 = StringUtils.trim(exMessage[0]);
                    if (exMessage.length > 1) {
                        message = StringUtils.trim(exMessage[1]);
                    }
                }
                try {
                    String exceptionClass = ClassNameUtils.isExceptionClass(exception2);
                    if (StringUtils.isNotBlank(exceptionClass)) {
                        caseResult.setException(exceptionClass);
                    }
                    if (StringUtils.isNotBlank(message)) {
                        caseResult.setExceptionMessage(resolveMessage(message));
                    }
                } catch (Exception e) {
                    LOG.info("未获取到exception:" + exception2);
                }
            }
        } catch (Exception e2) {
            LOG.info("输入错误exception", e2);
        }
    }

    private static String resolveMessage(String message) {
        if (StringUtils.isEmpty(message)) {
            return message;
        }
        return message.replaceAll("\"", "'");
    }

    private static CaseParam convertOutput(String result, Method method) {
        CaseParam caseParam = convertJsonObject(result, "expectedResult");
        return caseParam;
    }

    private static CaseParam convertJsonObject(String jsonResult, String paramTypeName) {
        String resultValue = jsonResult;
        CaseParam caseParam = null;
        try {
            if (StringUtils.isNotEmpty(jsonResult) && JSONUtil.isTypeJSONObject(jsonResult)) {
                JSONObject resultJson = JSONUtil.parseObj(jsonResult);
                if (resultJson.containsKey("iflycodeName")) {
                    resultValue = resultJson.getStr("iflycodeName", (String) null);
                } else if (resultJson.containsKey("array")) {
                    resultValue = resultJson.getStr("array");
                }
            }
            caseParam = DataUtils.tryConvertCaseParam(paramTypeName, resultValue);
        } catch (Exception e) {
            LOG.info("读取用例数据异常" + jsonResult, e);
        }
        return caseParam;
    }

    private static Map<String, CaseParam> convertInput(String case_input, Method method) {
        Map<String, CaseParam> input = new HashMap<>();
        try {
            if (JSONUtil.isTypeJSONObject(case_input)) {
                String replace = StringUtils.replace(case_input, "\\", "\\\\");
                JSONObject jsonObject = JSONUtil.parseObj(replace);
                for (Param methodParam : method.getMethodParams()) {
                    if (jsonObject.containsKey(methodParam.getName())) {
                        if (TypeUtils.isInArray(methodParam.getType())) {
                            input.put(methodParam.getName(), convertJsonObject(jsonObject.getStr(methodParam.getName()), "ARRAY"));
                        } else {
                            input.put(methodParam.getName(), convertJsonObject(jsonObject.getStr(methodParam.getName()), methodParam.getType().isArray() ? "ARRAY" : methodParam.getType().getName()));
                        }
                    } else if (!TypeUtils.isBasicType(methodParam.getType())) {
                        input.put(methodParam.getName(), convertJsonObject(case_input, methodParam.getType().isArray() ? "ARRAY" : methodParam.getType().getName()));
                    }
                }
            } else if (JSONUtil.isTypeJSONArray(case_input)) {
                for (Param methodParam2 : method.getMethodParams()) {
                    if (TypeUtils.isArrayType(methodParam2.getType())) {
                        input.put(methodParam2.getName(), DataUtils.tryConvertCaseParam(methodParam2.getName(), case_input));
                    }
                }
            } else {
                CaseParam caseParam = convertJsonObject(case_input, "input");
                if (caseParam != null && caseParam.getData() == null) {
                    method.getMethodParams().forEach(param -> {
                        input.put(param.getName(), caseParam);
                    });
                }
            }
        } catch (Exception e) {
            LOG.info("读取用例输入异常", e);
        }
        return input;
    }

    private static void addMock(String xmlData, List<ToMockMethod> mockMethods) {
        Pattern pattern = Pattern.compile("<case-mock>(.*?)</case-mock>", 32);
        Matcher matcher = pattern.matcher(xmlData);
        while (matcher.find()) {
            String testCase = matcher.group(1);
            try {
                if (countMatches(testCase, "<mock-method>") == 1 && countMatches(testCase, "<mock-class>") == 1 && countMatches(testCase, "<mock-return>") == 1) {
                    String methodName = extractTagValue(testCase, "mock-method");
                    String mockClass = extractTagValue(testCase, "mock-class");
                    String mockReturn = extractTagValue(testCase, "mock-return");
                    ToMockMethod mockMethod = new ToMockMethod();
                    mockMethod.setMethodName(convertMethodName(methodName));
                    mockMethod.setClassName(convertMethodName(mockClass));
                    mockMethod.setReturnValue(convertJsonObject(mockReturn, methodName + "Response"));
                    mockMethods.add(mockMethod);
                }
            } catch (Exception e) {
                LOG.info("读取用例Mock信息异常", e);
            }
        }
    }

    private static void addBranches(String xmlData, Method method, List<CaseBranch> branches) {
        Pattern pattern = Pattern.compile("<branch>(.*?)</branch>", 32);
        Matcher matcher = pattern.matcher(xmlData);
        List<String> modelBranches = new ArrayList<>();
        List<String> allBranches = (List) method.getCaseBranchSet().resolveAllBranches().stream().distinct().collect(Collectors.toList());
        while (matcher.find()) {
            String testCase = matcher.group(1);
            try {
                if (StringUtils.isNotBlank(testCase)) {
                    String testCase2 = URLUtil.encode(StringEscapeUtils.unescapeHtml4(testCase));
                    String branch = null;
                    if (testCase2.contains("==")) {
                        String newBranch = StrUtil.trimEnd(testCase2.substring(0, testCase2.lastIndexOf("==")));
                        Stream<String> stream = allBranches.stream();
                        Objects.requireNonNull(newBranch);
                        branch = stream.filter((v1) -> {
                            return r1.equals(v1);
                        }).findFirst().orElse(null);
                        if (StringUtils.isEmpty(branch)) {
                            Stream<String> stream2 = allBranches.stream();
                            Objects.requireNonNull(newBranch);
                            branch = stream2.filter(newBranch::startsWith).findFirst().orElse(null);
                        }
                    }
                    if (StringUtils.isEmpty(branch)) {
                        Stream<String> stream3 = allBranches.stream();
                        Objects.requireNonNull(testCase2);
                        branch = stream3.filter(testCase2::startsWith).findFirst().orElse(null);
                    }
                    if (StringUtils.isNotBlank(branch)) {
                        Boolean result = Boolean.valueOf(StringUtils.containsIgnoreCase(testCase2.replace(branch, ""), "true"));
                        modelBranches.add(branch + "__" + result);
                    }
                }
            } catch (Exception e) {
                LOG.info("读取用例Mock信息异常", e);
            }
        }
        if (method.getCaseBranchSet() != null) {
            recursionBranches(method.getCaseBranchSet(), modelBranches, branches);
        }
    }

    private static void recursionBranches(ResolvedBranch resolvedBranch, List<String> modelBranches, List<CaseBranch> mockBranches) {
        try {
            if (Objects.nonNull(resolvedBranch)) {
                boolean needCheckChildren = checkBranchInModelData(resolvedBranch, modelBranches, mockBranches);
                if (needCheckChildren && CollectionUtils.isNotEmpty(resolvedBranch.getChildrenCases())) {
                    checkChildren(resolvedBranch.getChildrenCases(), modelBranches, mockBranches);
                }
                resolveAllBranches(resolvedBranch, mockBranches);
            }
        } catch (Exception e) {
            LOG.warn("补充用例分支信息异常", e);
        }
    }

    private static void resolveAllBranches(ResolvedBranch resolvedBranch, List<CaseBranch> mockBranches) {
        if (Objects.nonNull(resolvedBranch)) {
            CaseBranch caseBranch = mockBranches.stream().filter(item -> {
                return item.getConditionText().equals(resolvedBranch.getConditionText());
            }).findFirst().orElse(null);
            if (StringUtils.isNotBlank(resolvedBranch.getConditionText())) {
                if (Objects.isNull(caseBranch)) {
                    resolveCaseBranch(resolvedBranch, resolvedBranch.getResult(), mockBranches);
                }
                if ((!resolvedBranch.getResult().booleanValue() || !resolvedBranch.getOut().booleanValue()) && resolvedBranch.getResult().booleanValue()) {
                    for (ResolvedBranch childrenCase : resolvedBranch.getChildrenCases()) {
                        resolveAllBranches(childrenCase, mockBranches);
                    }
                    return;
                }
                return;
            }
            if (StringUtils.isBlank(resolvedBranch.getConditionText())) {
                for (ResolvedBranch childrenCase2 : resolvedBranch.getChildrenCases()) {
                    resolveAllBranches(childrenCase2, mockBranches);
                }
            }
        }
    }

    private static void checkChildren(List<ResolvedBranch> childList, List<String> modelBranches, List<CaseBranch> mockBranches) {
        List<ResolvedBranch> nextList = new ArrayList<>();
        for (ResolvedBranch childrenCase : childList) {
            boolean checked = checkBranchInModelData(childrenCase, modelBranches, mockBranches);
            if (checked) {
                nextList.add(childrenCase);
            }
        }
        if (CollectionUtils.isNotEmpty(nextList)) {
            for (ResolvedBranch resolvedBranch : nextList) {
                checkChildren(resolvedBranch.getChildrenCases(), modelBranches, mockBranches);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean matchIfBranch(String modelBranch, String conditionText) {
        if (StringUtils.isNotBlank(conditionText)) {
            return StringUtils.equals(modelBranch.split("__")[0], conditionText);
        }
        return false;
    }

    private static boolean checkBranchInModelData(ResolvedBranch resolvedBranch, List<String> modelBranches, List<CaseBranch> mockBranches) {
        if (Objects.nonNull(resolvedBranch)) {
            Optional<String> optional = modelBranches.stream().filter(modelBranch -> {
                return matchIfBranch(modelBranch, resolvedBranch.getConditionText());
            }).findFirst();
            if (optional.isPresent()) {
                Boolean result = Boolean.valueOf(optional.get().split("__")[1]);
                modelBranches.removeIf(r -> {
                    return StringUtils.equals((CharSequence) optional.get(), r);
                });
                resolveCaseBranch(resolvedBranch, result, mockBranches);
                if (Boolean.TRUE.equals(result)) {
                    setParent(resolvedBranch, true, mockBranches);
                    setPrev(resolvedBranch, false, mockBranches);
                    setAfter(resolvedBranch, false, mockBranches);
                }
                return result.booleanValue();
            }
            return true;
        }
        return false;
    }

    public static void setParent(ResolvedBranch branch, Boolean result, List<CaseBranch> mockBranches) {
        if (Objects.nonNull(branch) && result.booleanValue() && Objects.nonNull(branch.getParent())) {
            resolveCaseBranch(branch.getParent(), true, mockBranches);
            setParent(branch.getParent(), true, mockBranches);
        }
    }

    public static void setPrev(ResolvedBranch branch, Boolean result, List<CaseBranch> mockBranches) {
        if (Objects.nonNull(branch) && !result.booleanValue() && Objects.nonNull(branch.getPrev())) {
            resolveCaseBranch(branch.getPrev(), false, mockBranches);
            setPrev(branch.getPrev(), false, mockBranches);
        }
    }

    public static void setAfter(ResolvedBranch branch, Boolean result, List<CaseBranch> mockBranches) {
        if (Objects.nonNull(branch) && !result.booleanValue() && Objects.nonNull(branch.getNext())) {
            resolveCaseBranch(branch.getNext(), false, mockBranches);
            setAfter(branch.getNext(), false, mockBranches);
        }
    }

    private static void resolveCaseBranch(ResolvedBranch resolvedBranch, Boolean result, List<CaseBranch> mockBranches) {
        int start = resolvedBranch.getTextRange().getStartOffset();
        int end = resolvedBranch.getTextRange().getEndOffset();
        CaseBranch caseBranch = mockBranches.stream().filter(item -> {
            return item.getConditionText().equals(resolvedBranch.getConditionText());
        }).findFirst().orElse(null);
        if (!result.booleanValue() && Objects.nonNull(caseBranch)) {
            return;
        }
        if (result.booleanValue() && Objects.nonNull(caseBranch) && !caseBranch.getResult().booleanValue()) {
            caseBranch.setResult(true);
            return;
        }
        CaseBranch caseBranch2 = new CaseBranch();
        caseBranch2.setOut(resolvedBranch.getOut());
        caseBranch2.setMethodName(resolvedBranch.getMethodName());
        caseBranch2.setResult(result);
        caseBranch2.setConditionText(resolvedBranch.getVirtual().booleanValue() ? "" : resolvedBranch.getConditionText());
        caseBranch2.setEndOffset(Integer.valueOf(end));
        caseBranch2.setStartOffset(Integer.valueOf(start));
        mockBranches.add(caseBranch2);
    }

    private static String convertMethodName(String methodName) {
        if (StringUtils.isEmpty(methodName)) {
            return methodName;
        }
        if (methodName.indexOf(".") > 0) {
            methodName = methodName.substring(methodName.lastIndexOf(".") + 1);
        }
        return convertBaseMethodName(methodName);
    }

    private static String convertBaseMethodName(String originalString) {
        if (StringUtils.isEmpty(originalString)) {
            return "";
        }
        return originalString.replaceAll("[^a-zA-Z]", "");
    }

    public static String extractTagValue(String str, String tag) {
        String openingTag = "<" + tag + ">";
        String closingTag = "</" + tag + ">";
        int start = str.indexOf(openingTag);
        int end = str.indexOf(closingTag);
        if (start < 0 || end < 0 || start >= end) {
            return null;
        }
        String tagValue = str.substring(start + openingTag.length(), end).trim();
        if ("case-input".equals(tag) || "mock-return".equals(tag) || "result".equals(tag)) {
            if (tagValue.contains("...,")) {
                tagValue = tagValue.replace("...,", "");
            }
            if (tagValue.contains("//")) {
                Pattern pattern = Pattern.compile("//.*?\n");
                Matcher matcher = pattern.matcher(tagValue);
                tagValue = matcher.replaceFirst("").replace("//", "");
            }
        }
        return StringUtils.replace(tagValue, "\n", "");
    }

    private static String caseHandle(String testCase, String label) {
        Pattern pattern = Pattern.compile("<" + label + ">(.*?)</" + label + ">", 32);
        Matcher matcher = pattern.matcher(testCase);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String happyCase = matcher.group(1);
            Pattern casePattern = Pattern.compile("<test-case>(.*?)</test-case>", 32);
            Matcher caseMatcher = casePattern.matcher(happyCase);
            sb.append("<").append(label).append(">\n");
            while (caseMatcher.find()) {
                String happyTestCase = caseMatcher.group(1);
                if (countMatches(happyTestCase, "<method-name>") == 1 && countMatches(happyTestCase, "<case-mock-all>") == 1 && countMatches(happyTestCase, "<content>") == 1 && countMatches(happyTestCase, "<case-input>") == 1 && countMatches(happyTestCase, "<result>") == 1 && countMatches(happyTestCase, "<type>") == 1) {
                    sb.append("    <test-case>").append(happyTestCase).append("</test-case>\n");
                }
            }
            sb.append("</").append(label).append(">");
        }
        return sb.toString();
    }

    private static String caseMocks(String mockCase, String label) {
        Pattern pattern = Pattern.compile("<" + label + ">(.*?)</" + label + ">", 32);
        Matcher matcher = pattern.matcher(mockCase);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String happyCase = matcher.group(1);
            Pattern casePattern = Pattern.compile("<case-mock>(.*?)</case-mock>", 32);
            Matcher caseMatcher = casePattern.matcher(happyCase);
            sb.append("<").append(label).append(">\n");
            while (caseMatcher.find()) {
                String happyTestCase = caseMatcher.group(1);
                if (countMatches(happyTestCase, "<mock-method>") == 1 && countMatches(happyTestCase, "<mock-class>") == 1 && countMatches(happyTestCase, "<mock-return>") == 1) {
                    sb.append("    <case-mock>").append(happyTestCase).append("</case-mock>\n");
                }
            }
            sb.append("</").append(label).append(">");
        }
        return sb.toString();
    }

    public static int countMatches(String str, String sub) {
        if (str.isEmpty() || sub.isEmpty()) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while (true) {
            int idx2 = str.indexOf(sub, idx);
            if (idx2 != -1) {
                count++;
                idx = idx2 + sub.length();
            } else {
                return count;
            }
        }
    }

    public static MethodRequestResult requestAI(PsiClass srcClass, Type testType, PsiMethod method, TypeDictionary typeDictionary, GeneratorTemplateConfig templateConfig, String filePath, Project project, List<MessageDto> messageDtos, Set<Method> selectedMethods, FileRequestDto fileRequestDto, Module srcModule, Map<String, String> defaultCacheMap) {
        String methodId;
        MessageDto messageDto;
        if (srcClass == null) {
            return null;
        }
        methodId = PsiUtils.formatMethodId(method.getContainingClass(), method.getName(), method.getParameterList().getParameters());
        Optional<Method> optional = testType.getMethods().stream().filter(m -> {
            return m.getMethodId().equals(methodId);
        }).findFirst();
        if (optional.isPresent()) {
            Method testMethod = optional.get();
            if (shouldBeTested(method, srcClass, templateConfig) && typeDictionary.isRelevant(method, srcClass)) {
                selectedMethods.add(testMethod);
                TemplateTestPromptDto templateTestPromptDto = new TemplateTestPromptDto();
                Set<String> hasAppendClass = new HashSet<>();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("【被测代码】：\n");
                stringBuilder.append(method.getText()).append("\n");
                stringBuilder.append("【结构信息】\n");
                List<String> needMethods = (List) testMethod.getMethodCalls().stream().filter(methodCall -> {
                    return methodCall.getMethod().getMethodId().startsWith(testType.getCanonicalName());
                }).map(methodCall2 -> {
                    return methodCall2.getMethod().getMethodId();
                }).collect(Collectors.toList());
                getBodyContent(stringBuilder, method, srcClass, needMethods, true, 15399);
                hasAppendClass.add(testType.getCanonicalName());
                Boolean canAppend = Boolean.valueOf(calculateString2MaxToken(stringBuilder));
                if (!method.getParameterList().isEmpty() && canAppend.booleanValue()) {
                    PsiParameter[] psiParameters = method.getParameterList().getParameters();
                    for (PsiParameter psiParameter : psiParameters) {
                        if (canAppend.booleanValue()) {
                            Type paramType = typeDictionary.getType(psiParameter.getType(), 1, false);
                            appendTypeBody(paramType, stringBuilder, PsiUtil.resolveClassInType(psiParameter.getType()), canAppend.booleanValue(), hasAppendClass, project, srcModule, typeDictionary, defaultCacheMap);
                            canAppend = Boolean.valueOf(calculateString2MaxToken(stringBuilder));
                        }
                    }
                }
                if (method.getReturnType() != null && !method.getReturnType().equals(PsiUtils.getField("com.intellij.psi.PsiType", "VOID")) && canAppend.booleanValue()) {
                    Type returnType = typeDictionary.getType(method.getReturnType(), 1, false);
                    PsiClass psiClass = PsiUtil.resolveClassInType(method.getReturnType());
                    appendTypeBody(returnType, stringBuilder, psiClass, canAppend.booleanValue(), hasAppendClass, project, srcModule, typeDictionary, defaultCacheMap);
                }
                if (templateConfig.isMethodUt()) {
                    Optional<UnitTestDto.DataDTO.FunctionDataDTO> function = templateConfig.getUnitTestDto().getFunctionData().stream().filter(functionDataDTO -> {
                        PsiMethod tempMethod = JavaPsiFacade.getElementFactory(project).createMethodFromText(functionDataDTO.getCode(), srcClass);
                        return StringUtils.equals(functionDataDTO.getFunctionName(), method.getName()) && StrUtil.equalsAnyIgnoreCase(methodId, new CharSequence[]{PsiUtils.formatMethodId(srcClass, tempMethod.getName(), tempMethod.getParameterList().getParameters())});
                    }).findFirst();
                    UnitTestDto.DataDTO.FunctionDataDTO functionDataDTO2 = function.get();
                    String funcId = functionDataDTO2.getId();
                    messageDto = new MessageDto(funcId, CommandEnum.CODE_TEST_MAKE_CASE_JAVA.getType());
                    fileRequestDto.setRequestId(funcId);
                    messageDto.setTaskId(funcId);
                    messageDto.setLang(srcClass.getLanguage().getID());
                    templateTestPromptDto.setStream(true);
                    classModelRenders.put(convertKey(funcId, filePath), fileRequestDto);
                } else {
                    messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.CODE_TEST_MAKE_CASE_JAVA.getType());
                    templateTestPromptDto.setStream(false);
                }
                messageDto.setPath(filePath);
                TemplateTestDto testDto = new TemplateTestDto();
                calculateString2MaxToken(stringBuilder);
                testDto.setTestContent(stringBuilder.toString());
                testDto.setTestFrame(templateConfig.getTestFramework().getName());
                testDto.setMockFrame(UnitTestMockEnum.OFF.equals(templateConfig.getMockFramework()) ? "" : templateConfig.getMockFramework().getName());
                List<String> branchList = (List) testMethod.getCaseBranchSet().resolveAllBranches().stream().distinct().collect(Collectors.toList());
                testDto.setBranchList(branchList);
                Integer testCaseNumber = testMethod.getCaseBranchSet().getBranchesSize();
                if (testCaseNumber.intValue() == 0 && testMethod.hasParams()) {
                    testCaseNumber = Integer.valueOf(testMethod.getMethodParams().size());
                }
                Integer testCaseNumber2 = Integer.valueOf(testCaseNumber.intValue() + 2);
                boolean saasScene = PluginSceneEnum.saasScene();
                if (saasScene) {
                    if (testCaseNumber2.intValue() > 4) {
                        testCaseNumber2 = 4;
                    }
                } else if (testCaseNumber2.intValue() > 6) {
                    testCaseNumber2 = 6;
                }
                testDto.setTestCaseNumber(testCaseNumber2);
                templateTestPromptDto.setContent(method.getText());
                templateTestPromptDto.setUnitTest(testDto);
                messageDto.setData(templateTestPromptDto);
                messageDto.setText(new StringBuffer());
                MethodRequestResult result = new MethodRequestResult();
                result.setRequestId(messageDto.getId());
                result.setReturn(false);
                result.setMethodId(methodId);
                result.setMethod(testMethod);
                messageDtos.add(messageDto);
                return result;
            }
            return null;
        }
        return null;
    }

    private static void appendTypeBody(Type paramType, StringBuilder stringBuilder, PsiClass psiClass, boolean canAppend, Set<String> hasAppendClass, Project project, Module srcModule, TypeDictionary typeDictionary, Map<String, String> DEFAULT_TYPE_TO_BOCOM) {
        PsiClass psiParamsClass;
        PsiClass psiParamsClass2;
        int idx = hasAppendClass.size();
        if (paramType != null) {
            Type resolvedType = paramType;
            boolean appendMethods = false;
            try {
                resolvedType = JavaPsiUtils.resolveChildTypeIfNeeded(paramType, true, 3, srcModule, typeDictionary, DEFAULT_TYPE_TO_BOCOM);
                if (resolvedType != paramType) {
                    JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(srcModule.getProject());
                    GlobalSearchScope scope = GlobalSearchScope.allScope(srcModule.getProject());
                    PsiClass classes = javaPsiFacade.findClass(resolvedType.getCanonicalName(), scope);
                    if (classes != null) {
                        psiClass = classes;
                        appendMethods = true;
                    }
                }
            } catch (Exception e) {
                LOG.warn("单测实现失败", e);
            }
            if (!TypeUtils.isNoImportType(resolvedType) && !TypeUtils.isArrayType(resolvedType) && psiClass != null && !hasAppendClass.contains(resolvedType.getCanonicalName())) {
                stringBuilder.append(String.format("import文件%d信息：\n", Integer.valueOf(idx)));
                getBodyContent(stringBuilder, null, psiClass, null, appendMethods, 22000);
                canAppend = calculateString2MaxToken(stringBuilder);
                hasAppendClass.add(resolvedType.getCanonicalName());
            }
            for (Type composedType : resolvedType.getComposedTypes()) {
                if (canAppend && !TypeUtils.isNoImportType(composedType) && !hasAppendClass.contains(composedType.getCanonicalName())) {
                    String canonicalName = ClassNameUtils.resolveGenericTypeName(composedType.getCanonicalName());
                    psiParamsClass2 = PsiUtils.h(canonicalName, null, project);
                    if (psiParamsClass2 != null) {
                        appendTypeBody(composedType, stringBuilder, psiParamsClass2, canAppend, hasAppendClass, project, srcModule, typeDictionary, DEFAULT_TYPE_TO_BOCOM);
                        canAppend = calculateString2MaxToken(stringBuilder);
                    }
                }
                for (Type composedTypeChildType : composedType.getComposedTypes()) {
                    String canonicalName2 = ClassNameUtils.resolveGenericTypeName(composedTypeChildType.getCanonicalName());
                    psiParamsClass = PsiUtils.h(canonicalName2, null, project);
                    if (psiParamsClass != null) {
                        appendTypeBody(composedTypeChildType, stringBuilder, psiParamsClass, canAppend, hasAppendClass, project, srcModule, typeDictionary, DEFAULT_TYPE_TO_BOCOM);
                        canAppend = calculateString2MaxToken(stringBuilder);
                    }
                }
            }
        }
    }

    private static boolean calculateString2MaxToken(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 22000) {
            stringBuilder.delete(22000, stringBuilder.length());
            int lastIndex = stringBuilder.lastIndexOf("\n");
            stringBuilder.delete(lastIndex, stringBuilder.length());
            stringBuilder.append("}");
            return false;
        }
        return true;
    }

    public static List<MessageDto> requestAI(String requestId, PsiClass srcClass, Type testType, TypeDictionary typeDictionary, GeneratorTemplateConfig templateConfig, String filePath, Project project, boolean requestAI, Module srcModule, Set<Method> selectedMethods) {
        String methodId;
        List<MessageDto> messageDtos = new ArrayList<>();
        FileRequestDto fileRequestDto = new FileRequestDto();
        fileRequestDto.setFilePath(filePath);
        if (StringUtils.isNotBlank(requestId)) {
            fileRequestDto.setRequestId(requestId);
            fileRequestDto.setMethodRequestResults(new ArrayList());
            templateConfig.setMethodUt(false);
            classModelRenders.put(convertKey(requestId, filePath), fileRequestDto);
        } else {
            templateConfig.setMethodUt(true);
        }
        fileRequestDto.setMethodRequestResults(new ArrayList());
        Map<String, String> defaultMap = JavaTestBuilderImpl.DEFAULT_TYPE_TO_BOCOM;
        if (requestAI) {
            for (PsiMethod psiMethod : srcClass.getMethods()) {
                MethodRequestResult methodRequestResult = requestAI(srcClass, testType, psiMethod, typeDictionary, templateConfig, filePath, project, messageDtos, selectedMethods, fileRequestDto, srcModule, defaultMap);
                Optional.ofNullable(methodRequestResult).ifPresent(r -> {
                    fileRequestDto.getMethodRequestResults().add(r);
                });
            }
        } else {
            for (PsiMethod method : srcClass.getMethods()) {
                methodId = PsiUtils.formatMethodId(method.getContainingClass(), method.getName(), method.getParameterList().getParameters());
                Optional<Method> optional = testType.getMethods().stream().filter(m -> {
                    return m.getMethodId().equals(methodId);
                }).findFirst();
                if (optional.isPresent()) {
                    Method testMethod = optional.get();
                    if (shouldBeTested(method, srcClass, templateConfig) && typeDictionary.isRelevant(method, srcClass)) {
                        selectedMethods.add(testMethod);
                        MethodRequestResult result = new MethodRequestResult();
                        result.setRequestId(IdUtil.fastSimpleUUID());
                        result.setReturn(true);
                        result.setMethodId(methodId);
                        result.setMethod(testMethod);
                        fileRequestDto.getMethodRequestResults().add(result);
                    }
                }
            }
        }
        return messageDtos;
    }

    public static String convertKey(String requestId, String filePath) {
        return requestId + ":" + HexUtil.encodeHexStr(filePath);
    }

    public static Boolean containFile(String requestId, String filePath) {
        Iterator it = classModelRenders.iterator();
        while (it.hasNext()) {
            FileRequestDto classModelRender = (FileRequestDto) it.next();
            if (!StringUtils.equals(classModelRender.getRequestId(), requestId) && StringUtils.equals(convertKey(requestId, filePath), convertKey(requestId, classModelRender.getFilePath()))) {
                return true;
            }
        }
        return false;
    }

    private static void getBodyContent(StringBuilder stringBuilder, PsiMethod existMethod, PsiClass srcClass, List<String> needMethods, boolean appendMethods, int MAX_TOKEN_LIMIT) {
        String methodId;
        stringBuilder.append("包名：").append(ClassNameUtils.extractPackageName(srcClass.getQualifiedName())).append("\n");
        stringBuilder.append("类结构：").append("\n");
        PsiDocComment psiDocComment = srcClass.getDocComment();
        if (psiDocComment != null) {
            stringBuilder.append(psiDocComment.getText()).append("\n");
        }
        PsiAnnotation[] annotations = srcClass.getAnnotations();
        for (PsiAnnotation psiAnnotation : annotations) {
            stringBuilder.append(psiAnnotation.getText()).append("\n");
        }
        if (srcClass.hasModifierProperty("public")) {
            stringBuilder.append("public ");
        } else if (srcClass.hasModifierProperty("protected")) {
            stringBuilder.append("protected ");
        }
        stringBuilder.append(" class ").append(srcClass.getName());
        PsiClass superClass = srcClass.getSuperClass();
        if (superClass != null && !"java.lang.Object".equalsIgnoreCase(superClass.getQualifiedName())) {
            stringBuilder.append(" extends ").append(superClass.getName());
        }
        PsiClass[] interfaces = srcClass.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            if (i == 0) {
                stringBuilder.append(" implements ");
            }
            stringBuilder.append(interfaces[i].getName());
        }
        stringBuilder.append("{\n");
        for (PsiField field : appendMethods ? srcClass.getFields() : srcClass.getAllFields()) {
            stringBuilder.append(field.getText()).append("\n");
        }
        boolean isLimit = false;
        if (appendMethods) {
            for (PsiMethod method : srcClass.getAllMethods()) {
                PsiClass containingClass = method.getContainingClass();
                boolean isInterfaceMethod = containingClass == null ? false : containingClass.isInterface();
                if (!method.hasModifierProperty("native") && !isInterfaceMethod && !TypeUtils.isIgnore(method.getName())) {
                    if (!method.isConstructor()) {
                        String methodBody = method.getText();
                        if (existMethod != null && method == existMethod) {
                            appendMethodText(stringBuilder, methodBody, method.getName());
                        } else {
                            int maxLength = stringBuilder.length();
                            methodId = PsiUtils.formatMethodId(method.getContainingClass(), method.getName(), method.getParameterList().getParameters());
                            if (!isLimit && needMethods != null && needMethods.contains(methodId) && maxLength + methodBody.length() < MAX_TOKEN_LIMIT) {
                                stringBuilder.append(methodBody).append("\n");
                            } else if (methodBody != null) {
                                appendMethodText(stringBuilder, methodBody, method.getName());
                            }
                        }
                    }
                    isLimit = stringBuilder.length() >= MAX_TOKEN_LIMIT;
                }
            }
        }
        stringBuilder.append("\n").append("}\n");
    }

    private static void appendMethodText(StringBuilder stringBuilder, String methodBody, String methodName) {
        int methodNameIndex = methodBody.indexOf(methodName + "(");
        int first = methodNameIndex == -1 ? -1 : methodBody.indexOf("{", methodNameIndex);
        if (first > -1) {
            stringBuilder.append(methodBody.substring(0, first - 1));
            stringBuilder.append(";").append("\n");
        } else {
            stringBuilder.append(methodBody).append("\n");
        }
    }

    public static boolean shouldBeTested(PsiMethod method, PsiClass srcClass, GeneratorTemplateConfig templateConfig) {
        if (!templateConfig.getTestMethods().isEmpty()) {
            return CollectionUtil.contains(templateConfig.getTestMethods(), method);
        }
        boolean isTestable = MethodFactory.isTestable(method, srcClass);
        boolean isInherited = MethodFactory.isInherited(method, srcClass);
        boolean shouldBuTested = isTestable && !isInherited;
        if (method.hasModifierProperty("private")) {
            return shouldBuTested && templateConfig.isTestPrivate();
        }
        return shouldBuTested;
    }

    public static synchronized boolean isModelReturned(String requestId, String filePath) {
        FileRequestDto requestDto = (FileRequestDto) classModelRenders.get(convertKey(requestId, filePath));
        return isModelReturned(requestId, requestDto);
    }

    public static synchronized boolean isAllReturned(String requestId) {
        if (classModelRenders.isEmpty()) {
            return true;
        }
        Iterator it = classModelRenders.iterator();
        while (it.hasNext()) {
            FileRequestDto classModelRender = (FileRequestDto) it.next();
            if (!isModelReturned(requestId, classModelRender)) {
                return false;
            }
        }
        return true;
    }

    public static synchronized FileRequestDto getReturnedFile(String requestId) {
        if (classModelRenders.isEmpty()) {
            return null;
        }
        Iterator it = classModelRenders.iterator();
        while (it.hasNext()) {
            FileRequestDto requestDto = (FileRequestDto) it.next();
            if (requestDto != null && StringUtils.equals(requestId, requestDto.getRequestId())) {
                if (requestDto.getMethodRequestResults().isEmpty() || requestDto.getMethodRequestResults().stream().allMatch((v0) -> {
                    return v0.isReturn();
                })) {
                    return requestDto;
                }
                return null;
            }
        }
        return null;
    }

    public static boolean remove(String requestId, String filePath) {
        return remove(requestId, filePath, true);
    }

    public static boolean remove(String requestId, String filePath, boolean checkReturn) {
        if (classModelRenders.containsKey(convertKey(requestId, filePath))) {
            if (checkReturn && !isModelReturned(requestId, filePath)) {
                return false;
            }
            classModelRenders.remove(convertKey(requestId, filePath));
            return true;
        }
        return true;
    }

    public static synchronized boolean isModelReturned(String requestId, FileRequestDto requestDto) {
        if (requestDto == null || requestDto.getMethodRequestResults() == null || !StringUtils.equals(requestId, requestDto.getRequestId())) {
            return false;
        }
        return requestDto.getMethodRequestResults().isEmpty() || requestDto.getMethodRequestResults().stream().allMatch((v0) -> {
            return v0.isReturn();
        });
    }

    public static int calculateRequestAiInterval(int methodSize) {
        return Math.min(15, 10 + (methodSize - 4));
    }

    public static int calculateGeneratorTimes(int methodSize, int inerrval) {
        int aiInterval = calculateRequestAiInterval(methodSize);
        int skip = aiInterval / inerrval;
        if (methodSize <= 1) {
            return 30;
        }
        return (methodSize + 1) * skip * inerrval;
    }

    public static void clearCache() {
        if (classModelRenders != null) {
            classModelRenders.clear();
        }
    }
}
