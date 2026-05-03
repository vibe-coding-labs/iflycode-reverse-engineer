/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.intellij.openapi.application.ApplicationManager
 *  io.opentelemetry.api.GlobalOpenTelemetry
 *  io.opentelemetry.api.OpenTelemetry
 *  io.opentelemetry.api.trace.Span
 *  okhttp3.OkHttpClient
 *  okhttp3.Request
 *  okhttp3.Request$Builder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.apm;

import com.aicode.apm.OpenTelemetryConfig;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.apm.enums.TracerEnum;
import com.aicode.diff.GenericUtils;
import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.StringUtils;
import com.google.gson.JsonObject;
import com.intellij.openapi.application.ApplicationManager;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class OpenTelemetryService {
    private static final Logger enum = LoggerFactory.getLogger(OpenTelemetryService.class);
    public Span parentSpan = null;

    /*
     * Loose catch block
     * WARNING - void declaration
     */
    private boolean kd(String string) {
        boolean bl;
        block12: {
            void a;
            OpenTelemetryService a22222;
            block10: {
                boolean bl2;
                block11: {
                    OpenTelemetryService openTelemetryService = this;
                    a22222 = new OkHttpClient();
                    Request request = new Request.Builder().url((String)a).build();
                    a22222 = a22222.newCall(request).execute();
                    if (!StringUtils.isNotBlank((CharSequence)a22222.message())) break block10;
                    bl2 = true;
                    if (a22222 == null) break block11;
                    a22222.close();
                }
                return bl2;
            }
            bl = true;
            if (a22222 == null) break block12;
            {
                catch (Throwable throwable) {
                    try {
                        Throwable throwable2;
                        block13: {
                            if (a22222 != null) {
                                try {
                                    a22222.close();
                                    throwable2 = throwable;
                                    break block13;
                                }
                                catch (Throwable throwable3) {
                                    throwable.addSuppressed(throwable3);
                                }
                            }
                            throwable2 = throwable;
                        }
                        throw throwable2;
                    }
                    catch (IOException a22222) {
                        enum.warn("APM\u8fde\u63a5\u5931\u8d25 " + (String)a);
                        return false;
                    }
                }
            }
            a22222.close();
        }
        return bl;
    }

    public OpenTelemetryService() {
        OpenTelemetryService a;
    }

    public static OpenTelemetryService getInstance() {
        return (OpenTelemetryService)ApplicationManager.getApplication().getService(OpenTelemetryService.class);
    }

    /*
     * WARNING - void declaration
     */
    public synchronized void handApmConfig(JsonObject jsonObject) {
        JsonObject jsonObject2;
        block10: {
            Boolean bl;
            Object a;
            OpenTelemetryService openTelemetryService;
            block9: {
                void a2;
                openTelemetryService = this;
                AICodeSettingsState aICodeSettingsState = AICodeSettingsState.getInstance();
                a = aICodeSettingsState.apmUrl;
                bl = aICodeSettingsState.apmEnable;
                if (StringUtils.isBlank((CharSequence)a) || bl == null) {
                    a = BasicActionsBundle.message(GenericUtils.H("33\u0014\u0010\"+u<\u0001\u00185\u007f=>4(0>?-"), new Object[0]);
                    bl = Boolean.parseBoolean(BasicActionsBundle.message(ConditionalActionConfiguration.H("=0\u000e\u0007\u0014\u0010p4\u0006\u0012\u001fX\b\t\u001d\u0005\u0019\u0017"), new Object[0]));
                }
                if (a2 != null && a2.size() > 0) {
                    jsonObject2 = a2.get(GenericUtils.H(";6%8")).getAsJsonObject();
                    a = jsonObject2.get(ConditionalActionConfiguration.H("\u0013\u0007\u001e4\u001a\r\u0011$\b\u0013")).getAsString();
                    bl = jsonObject2.get(GenericUtils.H("0(=\u00156>5=<")).getAsBoolean();
                    AICodeSettingsState aICodeSettingsState2 = aICodeSettingsState;
                    aICodeSettingsState2.apmEnable = bl;
                    aICodeSettingsState2.apmUrl = a;
                }
                if (!StringUtils.isBlank((CharSequence)a) && ((String)a).trim().startsWith(ConditionalActionConfiguration.H("\u001c\u0005\u000e\u000f"))) break block9;
                GlobalOpenTelemetry.resetForTest();
                return;
            }
            if (bl.booleanValue()) {
                bl = openTelemetryService.kd((String)a);
                enum.info("\u662f\u5426\u5f00\u542fAPM===>" + bl + ",agent push opentelemetry url is " + (String)a);
            }
            jsonObject2 = null;
            if (!bl.booleanValue()) break block10;
            jsonObject2 = OpenTelemetryConfig.init((String)a);
            GlobalOpenTelemetry.resetForTest();
            GlobalOpenTelemetry.set((OpenTelemetry)jsonObject2);
            if (openTelemetryService.parentSpan == null) {
                openTelemetryService.parentSpan = OpenTelemetryUtil.buildWithTracer(TracerEnum.IDEA_RUN, OpenTelemetryService.class.getName());
                return;
            }
        }
        try {
            if (jsonObject2 != null) {
                jsonObject2.shutdown();
            }
            GlobalOpenTelemetry.resetForTest();
            return;
        }
        catch (Throwable throwable) {
            enum.warn(GenericUtils.H("\u0019\u0000\u001dx\u66ab\u65e7\u5f53\u5e61"), throwable);
        }
    }
}
