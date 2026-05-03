/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.enums;

import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.NewFileUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class PluginSceneEnum
extends Enum<PluginSceneEnum> {
    private final String float;
    public static final /* enum */ PluginSceneEnum PLUGIN_SAAS = new PluginSceneEnum(NewFileUtils.H("I4K\fy\u0007\u0014G"), InlineChatStatusServiceKt.H("5\u0002<\u000b\u720d\u674c"));
    public static final /* enum */ PluginSceneEnum PLUGIN_PRIVATE = new PluginSceneEnum(InlineChatStatusServiceKt.H("$.\u00011\u0005\f9\u001dh\u0010"), NewFileUtils.H("Ik\u0013[3H\u0011E_W\u79b4\u6733\u537e\u7238\u670e"));
    public static final /* enum */ PluginSceneEnum PLUGIN_INNER = new PluginSceneEnum(NewFileUtils.H("N3L\u000bd\u001a^\r]K"), InlineChatStatusServiceKt.H("\u0007\u000b\u0004? \"\f\be/\u96a5\u56bf\u51fd\u720d\u674c"));
    private static final /* synthetic */ PluginSceneEnum[] byte;
    private final String enum;

    public String getScene() {
        PluginSceneEnum a;
        return a.float;
    }

    public static boolean saasScene() {
        return PLUGIN_SAAS.getScene().equals(BasicActionsBundle.message(NewFileUtils.H("\u0010T\f@\u0019JSM\u0003h(D\u0011\u0005\nC\u0017E\u001c"), new Object[0]));
    }

    public static PluginSceneEnum[] values() {
        return (PluginSceneEnum[])byte.clone();
    }

    static {
        PluginSceneEnum[] pluginSceneEnumArray = new PluginSceneEnum[3];
        pluginSceneEnumArray[0] = PLUGIN_SAAS;
        pluginSceneEnumArray[1] = PLUGIN_PRIVATE;
        pluginSceneEnumArray[2] = PLUGIN_INNER;
        byte = pluginSceneEnumArray;
    }

    public String getDescription() {
        PluginSceneEnum a;
        return a.enum;
    }

    /*
     * WARNING - void declaration
     */
    private PluginSceneEnum(String string2, String string3) {
        Object a;
        void a2;
        PluginSceneEnum a3;
        PluginSceneEnum pluginSceneEnum = object;
        Object object = string3;
        PluginSceneEnum pluginSceneEnum2 = a3 = pluginSceneEnum;
        pluginSceneEnum2.float = a2;
        pluginSceneEnum2.enum = a;
    }

    public static PluginSceneEnum valueOf(String a) {
        return Enum.valueOf(PluginSceneEnum.class, a);
    }
}
