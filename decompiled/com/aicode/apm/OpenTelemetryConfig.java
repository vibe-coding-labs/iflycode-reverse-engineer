/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.application.ApplicationInfo
 *  io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator
 *  io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
 *  io.opentelemetry.context.propagation.ContextPropagators
 *  io.opentelemetry.context.propagation.TextMapPropagator
 *  io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter
 *  io.opentelemetry.sdk.OpenTelemetrySdk
 *  io.opentelemetry.sdk.common.export.ProxyOptions
 *  io.opentelemetry.sdk.common.export.RetryPolicy
 *  io.opentelemetry.sdk.resources.Resource
 *  io.opentelemetry.sdk.trace.SdkTracerProvider
 *  io.opentelemetry.sdk.trace.SpanProcessor
 *  io.opentelemetry.sdk.trace.export.BatchSpanProcessor
 *  io.opentelemetry.sdk.trace.export.SpanExporter
 *  io.opentelemetry.sdk.trace.samplers.Sampler
 *  org.jetbrains.annotations.NotNull
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.apm;

import com.aicode.agent.service.GitReviewService;
import com.aicode.apm.enums.SpanAttrEnum;
import com.aicode.content.util.file.LanguageFileExtensionDetails;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.Application;
import com.aicode.util.JComponentKt;
import com.intellij.openapi.application.ApplicationInfo;
import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.common.export.ProxyOptions;
import io.opentelemetry.sdk.common.export.RetryPolicy;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class OpenTelemetryConfig {
    private static final long final = 5000L;
    private static final int try = 512;
    private static final Logger float = LoggerFactory.getLogger(OpenTelemetryConfig.class);
    private static final int byte = 30000;
    private static final int enum = 2048;

    public static OpenTelemetrySdk init(String string) {
        String string2 = string;
        try {
            String a = SdkTracerProvider.builder().setSampler(Sampler.traceIdRatioBased((double)1.0)).addSpanProcessor(OpenTelemetryConfig.td(string2)).setResource(OpenTelemetryConfig.AE()).build();
            TextMapPropagator[] textMapPropagatorArray = new TextMapPropagator[2];
            textMapPropagatorArray[0] = W3CTraceContextPropagator.getInstance();
            textMapPropagatorArray[1] = W3CBaggagePropagator.getInstance();
            TextMapPropagator textMapPropagator = TextMapPropagator.composite((TextMapPropagator[])textMapPropagatorArray);
            return OpenTelemetrySdk.builder().setTracerProvider((SdkTracerProvider)a).setPropagators(ContextPropagators.create((TextMapPropagator)textMapPropagator)).build();
        }
        catch (Throwable a) {
            float.info(LanguageFileExtensionDetails.H("l\u0006e\u0015%'}\u0005k<q\u0015w\u0012|\u0000Q7\\\u0006eC`\n`\bb\u000e"), a);
            return null;
        }
    }

    @NotNull
    private static Resource AE() {
        Resource resource = Resource.getDefault().toBuilder().put(LanguageFileExtensionDetails.H("P+}\u0014g\u0000cEg\u0005j\u000f"), GitReviewService.H("?\u000b1\u001f\u00043/\u0015g\u0018&\u001c,[3\u0014\u00109\b4")).put(SpanAttrEnum.SYSTEM_USERNAME.getText(), System.getProperty(LanguageFileExtensionDetails.H("\u0017}\u0006tEg\u0005j\u000f"))).put(SpanAttrEnum.IDEA_VERSION.getText(), ApplicationInfo.getInstance().getFullVersion()).put(SpanAttrEnum.PLUGIN_VERSION.getText(), BasicActionsBundle.message(GitReviewService.H("\n?\u000e2\u0002\u0002r;\u001c?\u0016+\u0017c\u0000&\n\u00167\u000e4"), new Object[0])).build();
        if (resource == null) {
            OpenTelemetryConfig.enum(2);
        }
        return resource;
    }

    @NotNull
    private static SpanProcessor td(String a) {
        BatchSpanProcessor batchSpanProcessor = BatchSpanProcessor.builder((SpanExporter)OpenTelemetryConfig.jE(a)).setExporterTimeout(30000L, TimeUnit.MILLISECONDS).setScheduleDelay(5000L, TimeUnit.MILLISECONDS).setMaxQueueSize(2048).setMaxExportBatchSize(512).build();
        if (batchSpanProcessor == null) {
            OpenTelemetryConfig.enum(0);
        }
        return batchSpanProcessor;
    }

    @NotNull
    private static RetryPolicy VD() {
        RetryPolicy retryPolicy = RetryPolicy.builder().setMaxAttempts(2).setInitialBackoff(Duration.ofSeconds(1L)).setMaxBackoff(Duration.ofSeconds(5L)).setBackoffMultiplier(1.5).build();
        if (retryPolicy == null) {
            OpenTelemetryConfig.enum(3);
        }
        return retryPolicy;
    }

    public OpenTelemetryConfig() {
        OpenTelemetryConfig a;
    }

    private static SSLContext IF(TrustManager[] trustManagerArray) {
        TrustManager[] trustManagerArray2 = trustManagerArray;
        try {
            Object a = SSLContext.getInstance(LanguageFileExtensionDetails.H("0K9"));
            TrustManager[] trustManagerArray3 = trustManagerArray2;
            a.init(null, trustManagerArray2, new SecureRandom());
            SSLContext.setDefault((SSLContext)a);
            return a;
        }
        catch (KeyManagementException | NoSuchAlgorithmException a) {
            throw new RuntimeException(a);
        }
    }

    private static TrustManager[] xE() {
        TrustManager[] trustManagerArray = new TrustManager[1];
        trustManagerArray[0] = new X509TrustManager(){

            @Override
            public void checkClientTrusted(X509Certificate[] x509CertificateArray, String object) {
                Object a = object;
                object = this;
            }
            {
                La a;
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509CertificateArray, String object) {
                Object a = object;
                object = this;
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        return trustManagerArray;
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray;
        String string = GitReviewService.H("\u00014%\u0005>>-\u0016h\u001e$\u000e!\u001d%Zu\u0018xH.F\n)8\u0004j\u001f-\rm\u0004&\f4\b$Q?\u001f%\u001e");
        Object[] objectArray2 = new Object[2];
        objectArray2[0] = LanguageFileExtensionDetails.H("f\u0007cLU0f\u0007h\u0004*\t}\r*'d\u001c|+|\u0018F#j\u0016|\u001aE\u0004g\u0002n\r");
        switch (a) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = GitReviewService.H("-\u00146*=\u0017-(3\u0015)\u0014\"\u0019&\u0000");
                break;
            }
            case 1: {
                objectArray = objectArray2;
                while (false) {
                }
                objectArray2[1] = LanguageFileExtensionDetails.H("\u000fh\u0014J\u001cx\tZ\u000bm\u0004p>n\fK\u001bv\u0004{\u0010b\u0018");
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = GitReviewService.H("\u0011&\f\u0013\u001f9\u001e$\u0018*\u0017");
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = LanguageFileExtensionDetails.H("~\u0011W\u001cj\u0016|\u001aV\u0004e\rd\u0013");
                break;
            }
        }
        throw new IllegalStateException(String.format(string, objectArray));
    }

    @NotNull
    private static OtlpHttpSpanExporter jE(String string) {
        String string2 = string;
        TrustManager[] a = OpenTelemetryConfig.xE();
        ProxyOptions proxyOptions = ProxyOptions.create((ProxySelector)new ProxySelector(){
            {
                ca a;
            }

            @Override
            public List<Proxy> select(URI uRI) {
                ArrayList<Proxy> arrayList;
                block4: {
                    Field field;
                    Object a = uRI;
                    ca a2 = this;
                    arrayList = new ArrayList<Proxy>();
                    Class<?> clazz = Class.forName(Application.H("mbY\u0019lhxjijdd+s`~~?w\u007fW\u000eGxz}Ejglnc15bbmg"));
                    Object object = clazz.getDeclaredMethod(JComponentKt.H("\u001e,\u001b\t\b:\u001b.\u0007;\u001b"), new Class[0]).invoke(null, new Object[0]);
                    Field field2 = field = clazz.getDeclaredField(Application.H("[^CZA^ST\u001b\u0017QOY["));
                    field2.setAccessible(true);
                    if (!((Boolean)field2.get(object)).booleanValue() || !JComponentKt.H("'\u001d,\u000e").equalsIgnoreCase(((URI)a).getScheme())) break block4;
                    Object object2 = a = clazz.getDeclaredField(Application.H("YXH\\\u001d\u0018KORV"));
                    ((Field)object2).setAccessible(true);
                    a = (String)((Field)object2).get(object);
                    Field field3 = field = clazz.getDeclaredField(JComponentKt.H("\u0019=\u000f>\u00100\u001f&\n*"));
                    field3.setAccessible(true);
                    int n = (Integer)field3.get(object);
                    a = new InetSocketAddress((String)a, n);
                    arrayList.add(new Proxy(Proxy.Type.HTTP, (SocketAddress)a));
                }
                try {
                    arrayList.add(Proxy.NO_PROXY);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (arrayList.isEmpty()) {
                    arrayList.add(Proxy.NO_PROXY);
                }
                return arrayList;
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void connectFailed(URI uRI, SocketAddress socketAddress, IOException iOException) {
                void a;
                IOException a2 = iOException;
                ca a3 = this;
                System.err.println("Connection to proxy failed for URI: " + (URI)a);
                float.warn("connectFailed" + (IOException)a2);
            }
        });
        OtlpHttpSpanExporter otlpHttpSpanExporter = OtlpHttpSpanExporter.builder().setEndpoint(string2).setCompression(GitReviewService.H(")\u000f\b*")).setRetryPolicy(OpenTelemetryConfig.VD()).setProxy(proxyOptions).setSslContext(OpenTelemetryConfig.IF(a), (X509TrustManager)a[0]).build();
        if (otlpHttpSpanExporter == null) {
            OpenTelemetryConfig.enum(1);
        }
        return otlpHttpSpanExporter;
    }
}
