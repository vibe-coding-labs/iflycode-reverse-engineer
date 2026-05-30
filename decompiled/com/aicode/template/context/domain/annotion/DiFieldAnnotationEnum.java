package com.aicode.template.context.domain.annotion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/annotion/DiFieldAnnotationEnum.class */
public enum DiFieldAnnotationEnum {
    INJECT("javax.inject.Inject"),
    NAMED("javax.inject.Named"),
    QUALIFIER("javax.inject.Qualifier"),
    QUALIFIER_SPRING("org.springframework.beans.factory.annotation.Qualifier"),
    AUTOWIRED("org.springframework.beans.factory.annotation.Autowired"),
    RESOURCE("javax.annotation.Resource");

    private final String canonicalName;
    private static final List<String> annStrList = (List) Arrays.stream(values()).map((v0) -> {
        return v0.getCanonicalName();
    }).collect(Collectors.toList());

    public static boolean isDiFieldAnnotation(String annName) {
        return annStrList.contains(annName);
    }

    DiFieldAnnotationEnum(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }
}
