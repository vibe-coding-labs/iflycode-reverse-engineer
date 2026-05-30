package com.aicode.template.context.domain.annotion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/annotion/DiClassAnnotationEnum.class */
public enum DiClassAnnotationEnum {
    SINGLETON("javax.inject.Singleton"),
    SERVICE("org.springframework.stereotype.Service"),
    COMPONENT("org.springframework.stereotype.Component"),
    REPOSITORY("org.springframework.stereotype.Repository"),
    CONTROLLER("org.springframework.stereotype.Controller"),
    REST_CONTROLLER("org.springframework.web.bind.annotation.RestController"),
    CONFIGURATION("org.springframework.context.annotation.Configuration");

    private final String canonicalName;
    private static final List<String> annStrList = (List) Arrays.stream(values()).map((v0) -> {
        return v0.getCanonicalName();
    }).collect(Collectors.toList());

    public static boolean isDiClassAnnotation(String annName) {
        return annStrList.contains(annName);
    }

    DiClassAnnotationEnum(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }
}
