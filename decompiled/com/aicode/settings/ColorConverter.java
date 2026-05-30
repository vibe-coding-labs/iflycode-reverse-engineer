package com.aicode.settings;

import com.intellij.ui.ColorUtil;
import com.intellij.util.xmlb.Converter;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/settings/ColorConverter.class */
public class ColorConverter extends Converter<Color> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "value";
        objArr[1] = "com/aicode/settings/ColorConverter";
        switch (i) {
            case 0:
            default:
                objArr[2] = "fromString";
                break;
            case 1:
                objArr[2] = "toString";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Nullable
    /* renamed from: fromString, reason: merged with bridge method [inline-methods] */
    public Color m303fromString(@NotNull String value) {
        if (value == null) {
            $$$reportNull$$$0(0);
        }
        try {
            return ColorUtil.fromHex(value);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public String toString(@NotNull Color value) {
        if (value == null) {
            $$$reportNull$$$0(1);
        }
        return ColorUtil.toHtmlColor(value);
    }
}
