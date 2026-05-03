/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.execution.ExecutionException
 *  com.intellij.execution.configurations.GeneralCommandLine
 *  com.intellij.execution.process.KillableProcessHandler
 *  com.intellij.execution.process.ProcessEvent
 *  com.intellij.execution.process.ProcessInfo
 *  com.intellij.execution.process.ProcessListener
 *  com.intellij.execution.process.impl.ProcessListUtil
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.util.Key
 *  com.intellij.openapi.util.SystemInfoRt
 *  com.intellij.util.io.BaseOutputReader$Options
 *  org.jetbrains.annotations.NotNull
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.agent;

import com.aicode.agent.PluginAgentCommandLine;
import com.aicode.agent.service.PluginAgentProcessServiceImpl;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.apm.enums.SpanAttrEnum;
import com.aicode.apm.enums.TracerEnum;
import com.aicode.content.util.EditorUtils;
import com.aicode.diff.FileService;
import com.aicode.inline.controller.ChatInputController;
import com.aicode.util.FileUtils;
import com.aicode.util.JComponentKt;
import com.aicode.util.StringUtils;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.KillableProcessHandler;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessInfo;
import com.intellij.execution.process.ProcessListener;
import com.intellij.execution.process.impl.ProcessListUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.SystemInfoRt;
import com.intellij.util.io.BaseOutputReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PluginAgentProcessHandler
extends KillableProcessHandler {
    private static final Logger enum = LoggerFactory.getLogger(PluginAgentProcessHandler.class);

    /*
     * Enabled aggressive block sorting
     */
    private static /* synthetic */ void enum(int a) {
        RuntimeException runtimeException;
        int n;
        Object[] objectArray;
        int n2;
        Object[] objectArray2;
        int n3;
        int n4;
        String string;
        switch (a) {
            default: {
                string = ChatInputController.H("\"\u001d^@*.\u0015\u0003B\b\u001f\u000e^27\u001a\"\u0014\u0005\u0010\u001e^\t\u0014\u0011\u000e*.\u0011\f\nTS]\u0001YD\u0007\u0002Hz X_\nU\u001e\n\b\u0003R\u0010\n\u001dR\u001c\u001cU\f\u001b\u0016\u001a");
                n4 = a;
                break;
            }
            case 1: {
                string = FileService.H("\u0018\u000409>\u0017:(t+:9-8\u0005Sf\"pi!`931$b>\u0016\u001fp0:9 53o:3/=");
                n4 = a;
                break;
            }
        }
        switch (n4) {
            default: {
                n3 = 3;
                break;
            }
            case 1: {
                n3 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n3];
        switch (a) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = ChatInputController.H("\u001d\n\u0004\u001f\u001f\u0017\u0011.\u0007\u0014\u0013");
                n2 = a;
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = FileService.H(";%2b\u0011\u000b5+0#p,\"2\u000f\u0007l\u0001295):\u0007%5,$)\u0019?!:>&\u000f<!0*&#");
                n2 = a;
                break;
            }
        }
        switch (n2) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = ChatInputController.H("\u0011\u0011\u0014Z\u0002\u0006$$\u0001\fW\u0015\u0013\u001d\u001c\nK8\b\u001d8:\u0018;\u001e\u0010\u001d\u000b+\u0005\u001d\u001d\u0000\u001a\u00016\u0018\u001b\u0006\u0002\u001f\u0004");
                n = a;
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[1] = FileService.H("\u00195#;('\b-;=)-\"");
                n = a;
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = ChatInputController.H("E\u001c\f\u0007\u000eH");
                break;
            }
            case 1: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (a) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                throw runtimeException;
            }
            case 1: 
        }
        runtimeException = new IllegalStateException(string2);
        throw runtimeException;
    }

    @NotNull
    public BaseOutputReader.Options readerOptions() {
        BaseOutputReader.Options options = BaseOutputReader.Options.forMostlySilentProcess();
        if (options == null) {
            PluginAgentProcessHandler.enum(1);
        }
        return options;
    }

    /*
     * WARNING - void declaration
     */
    public PluginAgentProcessHandler(@NotNull GeneralCommandLine generalCommandLine, PluginAgentProcessServiceImpl pluginAgentProcessServiceImpl) throws ExecutionException {
        void a;
        PluginAgentProcessHandler pluginAgentProcessHandler = pluginAgentProcessHandler2;
        PluginAgentProcessHandler pluginAgentProcessHandler2 = generalCommandLine;
        PluginAgentProcessHandler commandLine = pluginAgentProcessHandler;
        if (pluginAgentProcessHandler2 == null) {
            PluginAgentProcessHandler.enum(0);
        }
        PluginAgentProcessHandler pluginAgentProcessHandler3 = commandLine;
        super((GeneralCommandLine)pluginAgentProcessHandler2);
        PluginAgentProcessHandler pluginAgentProcessHandler4 = commandLine;
        pluginAgentProcessHandler3.addProcessListener(new ProcessListener(commandLine, (PluginAgentProcessServiceImpl)a){
            public final /* synthetic */ PluginAgentProcessHandler byte;
            public final /* synthetic */ PluginAgentProcessServiceImpl enum;

            public void processTerminated(@NotNull ProcessEvent processEvent) {
                01 v0 = this_;
                01 this_ = processEvent;
                01 a = v0;
                if (this_ == null) {
                    01.enum(1);
                }
                enum.debug(this_.getText());
            }

            public void startNotified(@NotNull ProcessEvent processEvent) {
                01 v0 = this_;
                01 this_ = processEvent;
                01 a = v0;
                if (this_ == null) {
                    01.enum(0);
                }
                enum.debug(this_.getText());
            }
            {
                void a;
                Object a2 = object;
                object = this;
                object.byte = a;
                object.enum = a2;
                object();
            }

            public void onTextAvailable(@NotNull ProcessEvent processEvent, @NotNull Key key) {
                String processEvent22;
                01 v0 = object;
                Object object = key;
                01 a = v0;
                if (processEvent22 == null) {
                    01.enum(2);
                }
                if (object == null) {
                    01.enum(3);
                }
                try {
                    processEvent22 = processEvent22.getText();
                    if (processEvent22.contains(EditorUtils.H("B4q?\u0012~1w0#"))) {
                        object = OpenTelemetryUtil.buildWithTracer(TracerEnum.AGENT_ERROR, a.getClass().getName());
                        object.setAttribute(SpanAttrEnum.AGENT_ERROR_REASON.getText(), processEvent22);
                        object.setAttribute(SpanAttrEnum.SYSTEM_USERNAME.getText(), System.getProperty(JComponentKt.H("\u0013>\u000e<F.\u0007$\n")));
                        object.end();
                    }
                    object = Pattern.compile(EditorUtils.H("d x+\u001dN\b\u00036jD&2"));
                    if (((Matcher)(object = ((Pattern)object).matcher(processEvent22))).find()) {
                        object = ((Matcher)object).group(0);
                        enum.info("process output port: " + (String)object);
                        if (a.enum != null) {
                            a.enum.setPort((String)object);
                            return;
                        }
                    }
                }
                catch (Throwable processEvent22) {
                    enum.warn(JComponentKt.H("\u0002'\u0010X.;\u00007\u0017+\rM$/\b=\u001e1C0\t?\u001fn\r2\u0014&\u001d"));
                }
            }

            private static /* synthetic */ void enum(int a) {
                Object[] objectArray;
                Object[] objectArray2;
                String string = EditorUtils.H("\\4`)0cM\f?\"i/4\u000fT.i\bG\u0005xof,o'j9W\u001dsz;bcl6\"f{%(\u0015Eaip3d8?*y9!82,-m.u");
                Object[] objectArray3 = new Object[3];
                switch (a) {
                    default: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = JComponentKt.H("lHrX,\u001c1!/\u001a/\u0013");
                        break;
                    }
                    case 3: {
                        objectArray2 = objectArray3;
                        while (false) {
                        }
                        objectArray3[0] = EditorUtils.H("s'`");
                        break;
                    }
                }
                objectArray2[1] = JComponentKt.H(";\u0011\u0011u?\u0011 \n+\ff\u000e8\u001c1\rK\u0012!\u001e%\r&/'\u0003'\u001b\u000e\ngMyIns(\u0001&\b<\reV");
                switch (a) {
                    default: {
                        objectArray = objectArray2;
                        objectArray2[2] = EditorUtils.H("?k%d9O5#e%q'}");
                        break;
                    }
                    case 1: {
                        objectArray = objectArray2;
                        while (false) {
                        }
                        objectArray2[2] = JComponentKt.H("\u001f,\u0017kKoII^;\u0002+\n8\u000b$\u0003");
                        break;
                    }
                    case 2: 
                    case 3: {
                        objectArray = objectArray2;
                        objectArray2[2] = EditorUtils.H(")y\u0018z<b\fw;>`\"z.|");
                        break;
                    }
                }
                throw new IllegalArgumentException(String.format(string, objectArray));
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static List<ProcessInfo> getAgents() {
        ArrayList<ProcessInfo> arrayList = new ArrayList<ProcessInfo>();
        try {
            Object object;
            ProcessInfo[] processInfoArray = (ProcessInfo[])ApplicationManager.getApplication().executeOnPooledThread(ProcessListUtil::getProcessList).get();
            Object object2 = null;
            ProcessInfo[] processInfoArray2 = System.getProperty(ChatInputController.H("\u001f\u000f\\\b\u001c\u0007\u0010\u0006\u000b\u0006"));
            if (SystemInfoRt.isLinux) {
                object = object2 = FileUtils.X86_64_LINUX_NODE;
            } else if (SystemInfoRt.isWindows) {
                object = processInfoArray2.startsWith(FileService.H("pm`")) ? (object2 = FileUtils.X86_64_WINDOWS7_NODE + FileUtils.AGENT_EXE_SUFFIX) : (object2 = FileUtils.X86_64_WINDOWS_NODE + FileUtils.AGENT_EXE_SUFFIX);
            } else {
                if (SystemInfoRt.isMac) {
                    object2 = PluginAgentCommandLine.isArm64() ? FileUtils.X86_64_DARWIN_ARM_NODE : FileUtils.X86_64_DARWIN_NODE;
                }
                object = object2;
            }
            if (StringUtils.isBlank((CharSequence)object)) {
                return arrayList;
            }
            processInfoArray2 = processInfoArray;
            int n = processInfoArray.length;
            for (int i = 0; i < n; ++i) {
                ProcessInfo processInfo = processInfoArray2[i];
                if (!StringUtils.contains((CharSequence)processInfo.getCommandLine(), (CharSequence)object2)) continue;
                arrayList.add(processInfo);
            }
            return arrayList;
        }
        catch (Exception exception) {
            enum.error(exception.getMessage());
        }
        return arrayList;
    }
}
