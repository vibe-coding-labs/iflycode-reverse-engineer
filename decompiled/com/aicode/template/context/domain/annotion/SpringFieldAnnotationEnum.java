package com.aicode.template.context.domain.annotion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/annotion/SpringFieldAnnotationEnum.class */
public enum SpringFieldAnnotationEnum {
    VALUE("org.springframework.beans.factory.annotation.Value");

    private final String canonicalName;
    private static final List<String> annStrList = (List) Arrays.stream(values()).map((v0) -> {
        return v0.getCanonicalName();
    }).collect(Collectors.toList());

    public static boolean isSpringConfigFieldAnnotation(String annName) {
        return annStrList.contains(annName);
    }

    SpringFieldAnnotationEnum(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }
}
