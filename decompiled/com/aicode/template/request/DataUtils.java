package com.aicode.template.request;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.aicode.template.context.domain.Type;
import com.aicode.template.request.dto.CaseParam;
import com.aicode.template.request.dto.CaseResult;
import com.aicode.template.request.dto.TypeEnum;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/request/DataUtils.class */
public class DataUtils {
    private static Set<String> NUMBER_TYPE = new HashSet();
    private static Set<String> BOOLEAN_TYPE = new HashSet();
    private static Set<String> DATE_TYPE = new HashSet();
    public static final List<String> noSupportValues;
    public static final CaseResult Empty;

    static {
        NUMBER_TYPE.add("java.lang.Long");
        NUMBER_TYPE.add("java.lang.Integer");
        NUMBER_TYPE.add("java.lang.Float");
        NUMBER_TYPE.add("java.lang.Double");
        NUMBER_TYPE.add("java.lang.Short");
        NUMBER_TYPE.add("java.lang.Byte");
        NUMBER_TYPE.add("long");
        NUMBER_TYPE.add("int");
        NUMBER_TYPE.add("float");
        NUMBER_TYPE.add("double");
        NUMBER_TYPE.add("short");
        NUMBER_TYPE.add("byte");
        BOOLEAN_TYPE.add("java.lang.Boolean");
        BOOLEAN_TYPE.add("boolean");
        DATE_TYPE.add("java.util.Date");
        DATE_TYPE.add("java.time.LocalDate");
        DATE_TYPE.add("java.time.LocalDateTime");
        DATE_TYPE.add("java.time.LocalTime");
        noSupportValues = List.of("无返回值", "无", "null", "void");
        Empty = new CaseResult("EMPTY", null, null, null, null);
    }

    public static boolean isNumberType(Type type) {
        return NUMBER_TYPE.contains(type.getCanonicalName());
    }

    public static boolean isBooleanType(Type type) {
        return isBooleanType(type.getCanonicalName());
    }

    public static boolean isDateType(Type type) {
        return isDateType(type.getCanonicalName());
    }

    public static boolean isBooleanType(String canonicalName) {
        return BOOLEAN_TYPE.contains(canonicalName);
    }

    public static boolean isDateType(String canonicalName) {
        return DATE_TYPE.contains(canonicalName);
    }

    public static boolean canSetNullValue(String canonicalName) {
        Stream<String> filter = NUMBER_TYPE.stream().filter(m -> {
            return m.length() <= 6;
        });
        Objects.requireNonNull(canonicalName);
        return filter.noneMatch((v1) -> {
            return r1.contains(v1);
        }) && !"boolean".equalsIgnoreCase(canonicalName);
    }

    public static String convertToBoolean(String value, String foramtString, String canonicalName) {
        if ("false".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)) {
            return String.format(foramtString, value);
        }
        if (value.equalsIgnoreCase("null") && "java.lang.Boolean".equalsIgnoreCase(canonicalName)) {
            return value;
        }
        return String.format(foramtString, "true");
    }

    public static Object convertData(Object data, TypeEnum type) {
        Object result;
        if (data == null) {
            return null;
        }
        switch (type) {
            case BOOLEAN:
                result = Boolean.valueOf(data.toString());
                break;
            case LIST:
            case ARRAY:
                result = JSONUtil.parseArray(data);
                break;
            case CLASS:
            case HASHMAP:
                result = JSONUtil.parseObj(data);
                break;
            case STRING:
            case NUMBER:
            default:
                result = String.valueOf(data);
                break;
        }
        return result;
    }

    public static boolean isNull(Object data) {
        if (data == null) {
            return true;
        }
        if (data instanceof String) {
            String value = (String) data;
            return StringUtils.isBlank(value) || noSupportValues.contains(value);
        }
        return false;
    }

    public static CaseParam tryConvertCaseParam(String name, String jsonStr) {
        Object data;
        String type = TypeEnum.STRING.name();
        if (type.equalsIgnoreCase(name)) {
            data = (StringUtils.isBlank(jsonStr) || noSupportValues.contains(jsonStr)) ? null : jsonStr;
        } else if (JSONUtil.isTypeJSONObject(jsonStr)) {
            data = JSONUtil.parseObj(jsonStr);
            type = TypeEnum.CLASS.name();
        } else if (JSONUtil.isTypeJSONArray(jsonStr)) {
            data = JSONUtil.parseArray(jsonStr);
            type = TypeEnum.ARRAY.name();
        } else {
            data = (StringUtils.isBlank(jsonStr) || noSupportValues.contains(jsonStr)) ? null : jsonStr;
        }
        return new CaseParam(name, type, name, data);
    }

    public static boolean checkNumberData(String data) {
        if (StringUtils.isNotEmpty(data)) {
            return Pattern.matches("[-+]?(\\d+(.\\d*)?|.\\d+)([eE][-+]?\\d+)?", data);
        }
        return false;
    }

    public static List<CaseResult> parseToCaseResult(JSONArray caseDataArray) {
        List<CaseResult> caseResults = new ArrayList<>();
        Iterator it = caseDataArray.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            try {
                caseResults.add((CaseResult) JSONUtil.parseObj(o).toBean(CaseResult.class));
            } catch (Exception e) {
            }
        }
        return caseResults;
    }

    public static boolean isEmptyData(CaseResult result) {
        if (result == null) {
            return true;
        }
        return result.getCaseMethodName().equalsIgnoreCase("EMPTY") && result.getInput() == null && result.getMockMethods() == null && result.getOutput() == null && result.getMessage() == null;
    }
}
