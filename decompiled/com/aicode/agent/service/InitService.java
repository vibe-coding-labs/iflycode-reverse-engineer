/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.collection.CollUtil
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.components.Service
 *  com.intellij.openapi.project.Project
 *  org.jetbrains.annotations.NotNull
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.agent.service;

import cn.hutool.core.collection.CollUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.enums.AICodeStatus;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.service.editor.RequestTipServiceImpl;
import com.aicode.status.AICodeStatusService;
import com.aicode.ui.ActionButton;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Service
public final class InitService {
    private static final Logger byte = LoggerFactory.getLogger(InitService.class);
    private final ScheduledExecutorService enum;

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray;
        String string = CancelRequestTip.H(" \u0013M_\b\u0000\r\u0017A\u0007\u0005\u0018P0dE\u00039\u0014\r\u001aV\u0002\u0013\u001f\f\n\u0002\u0015\u0004X\njh\u0019MM\u0002\u0016Pr$OD\u0005V\u001f\u0007\u001e\u0019G\t\u000e\u0015A\u0003\u000fJ\u001f\u0004\u0005\u0005");
        Object[] objectArray2 = new Object[3];
        objectArray2[0] = ActionButton.H("\u001d\u001a\u001d\b\u001d\u0007\n");
        objectArray2[1] = CancelRequestTip.H("\u0004\b\fNKC.\"\u000e\u000fB\f\u0017\u00159#N\u0012\u0013\u0004\u0004\u001b\u000e\bH.\u000f\b\u00152\u000f\u0018\u0007\u0018\n\f");
        switch (a) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = ActionButton.H("\u0017\u001d\u0000\u0003=\u001a\u001d\b\u001d\u0007\n");
                break;
            }
            case 1: {
                objectArray = objectArray2;
                while (false) {
                }
                objectArray2[2] = CancelRequestTip.H("\u0005\u0006\u0013\u001f\u0019$\b\f\u0011\r\u0004\u001e\u000f%\u0010\u001a\u0002");
                break;
            }
        }
        throw new IllegalArgumentException(String.format(string, objectArray));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void ld(Project project, String string) {
        Project project2 = project;
        Object a = RequestTipServiceImpl.LAST_REQUEST.get(project2);
        if (CollUtil.isEmpty(a)) {
            return;
        }
        a = a.entrySet().iterator();
        block0: while (true) {
            Object object = a;
            while (object.hasNext()) {
                void a2;
                Object object2 = (Map.Entry)a.next();
                String string2 = (String)object2.getKey();
                if ((object2 = (Long)object2.getValue()) == null) {
                    object = a;
                    continue;
                }
                long l = System.currentTimeMillis();
                long l2 = l - (Long)object2;
                if (l2 <= Long.parseLong((String)a2)) continue block0;
                byte.info("start complete at " + (Long)object2 + ",current time is " + l + ",duration " + l2 + " \u6beb\u79d2");
                byte.info(string2 + " Request Time Out! Clear Complete Result");
                RequestTipServiceImpl.LAST_REQUEST.put(project2, new HashMap());
                ApplicationManager.getApplication().invokeLater(() -> AICodeStatusService.notifyApplication(AICodeStatus.Ready, ""));
                continue block0;
            }
            break;
        }
    }

    public InitService() {
        InitService a;
        InitService initService = a;
        initService.enum = new ScheduledThreadPoolExecutor(2, PluginStartupActivity.namedThreadFactory);
    }

    public void initProject(@NotNull Project project) {
        InitService initService = initService2;
        InitService initService2 = project;
        InitService a = initService;
        if (initService2 == null) {
            InitService.enum(0);
        }
        a.md((Project)initService2);
    }

    private void md(@NotNull Project project) {
        InitService initService = object;
        Object object = project;
        InitService a = initService;
        if (object == null) {
            InitService.enum(1);
        }
        String string = BasicActionsBundle.message(ActionButton.H("N\\+=\u000b\u0010F\u0011\u001a\u0002\"$\u0001\n\u0016G\u0003\u0004\u0005\u0017L\u0017\u0011\n"), new Object[0]);
        object = () -> InitService.ld((Project)object, string);
        a.enum.scheduleAtFixedRate((Runnable)object, 0L, 500L, TimeUnit.MILLISECONDS);
    }
}
