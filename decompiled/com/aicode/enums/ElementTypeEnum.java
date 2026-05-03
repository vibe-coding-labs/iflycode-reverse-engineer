/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.aicode.enums;

import com.aicode.language.AICodeLanguageInfo;
import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class ElementTypeEnum
extends Enum<ElementTypeEnum> {
    private String byte;
    private static final /* synthetic */ ElementTypeEnum[] enum;
    public static final /* enum */ ElementTypeEnum CLASS;
    public static final /* enum */ ElementTypeEnum METHOD;

    static {
        METHOD = new ElementTypeEnum(AICodeLanguageInfo.H(".\u0017*\u0007{A"));
        CLASS = new ElementTypeEnum(AICodeLanguageInfo.H("\u00112\u000egV"));
        ElementTypeEnum[] elementTypeEnumArray = new ElementTypeEnum[2];
        elementTypeEnumArray[0] = METHOD;
        elementTypeEnumArray[1] = CLASS;
        enum = elementTypeEnumArray;
    }

    public static ElementTypeEnum valueOf(String a) {
        return Enum.valueOf(ElementTypeEnum.class, a);
    }

    public String getType() {
        ElementTypeEnum a;
        return a.byte;
    }

    public static Optional<ElementTypeEnum> getByType(String a) {
        if (StringUtils.isBlank((CharSequence)a)) {
            return Optional.empty();
        }
        return Arrays.stream(ElementTypeEnum.values()).filter(elementTypeEnum -> {
            Object a = elementTypeEnum;
            String a2 = a;
            return ((ElementTypeEnum)((Object)((Object)a))).byte.equals(a2);
        }).findFirst();
    }

    private ElementTypeEnum(String string2) {
        Object a = string2;
        ElementTypeEnum a2 = this;
        a2.byte = a;
    }

    public static ElementTypeEnum[] values() {
        return (ElementTypeEnum[])enum.clone();
    }
}
