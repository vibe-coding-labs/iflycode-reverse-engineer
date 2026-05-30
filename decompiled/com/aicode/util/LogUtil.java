package com.aicode.util;

import com.aicode.agent.enums.CommandEnum;
import com.aicode.diff.GenericUtils;
import com.aicode.ui.ActionButton;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.diagnostic.Logger;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: ua */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/LogUtil.class */
public class LogUtil {
    public static final /* synthetic */ String AGENT_SEND = ActionButton.H("\u001b\u0017\u0003\u0013");
    public static final /* synthetic */ String WEB_RECEIVE = GenericUtils.H("(2:\u0002:4'#~e");

    /* renamed from: enum, reason: not valid java name */
    private static final /* synthetic */ Logger f691enum = Logger.getInstance(LogUtil.class);
    public static /* synthetic */ AtomicBoolean PRINT_STACK_TRACE = new AtomicBoolean(false);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ void info(String a, String a2) {
        if (PRINT_STACK_TRACE.get()) {
            if (ActionButton.H("\u0011\u00196\u001c\u0010\f\r\u001b\u001b\u0012").equals(a)) {
                f691enum.info("receive web ===========> " + a2);
            }
            if (GenericUtils.H("#=75").equals(a)) {
                JsonObject asJsonObject = JsonParser.parseString(a2).getAsJsonObject();
                String a3 = asJsonObject.get(ActionButton.H("-\u001a\u0002\u0005\u0013\u0003\u0013")).getAsString();
                String asString = asJsonObject.get(GenericUtils.H("05")).getAsString();
                if (!CommandEnum.USER_VERSION.getType().equals(a3)) {
                    if (!a3.equals(CommandEnum.LOG_TEST_COLLECTION_GENERATE.getType()) && !a3.equals(CommandEnum.LOG_TEST_COLLECTION_COMMIT.getType())) {
                        f691enum.info("request agent ===========> " + a3 + " , " + asString);
                    } else {
                        f691enum.info("request ws message ===========>\n" + asJsonObject);
                    }
                }
            }
        }
    }
}
