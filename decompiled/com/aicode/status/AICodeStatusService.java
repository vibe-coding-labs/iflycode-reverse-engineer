/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.Disposable
 *  com.intellij.openapi.application.Application
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.project.ProjectManager
 *  com.intellij.openapi.util.Pair
 *  javax.annotation.concurrent.GuardedBy
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.aicode.status;

import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.content.util.file.LanguageFileExtensionDetails;
import com.aicode.enums.AICodeStatus;
import com.aicode.status.AICodeStatusListener;
import com.aicode.statusBar.StatusBarPopup;
import com.aicode.util.StringUtils;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.Pair;
import javax.annotation.concurrent.GuardedBy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class AICodeStatusService
implements AICodeStatusListener,
Disposable {
    @GuardedBy(value="lock")
    @NotNull
    private AICodeStatus float;
    private final Object byte;
    @GuardedBy(value="lock")
    @Nullable
    private String enum;

    @NotNull
    public static Pair<AICodeStatus, String> getCurrentStatus() {
        return ((AICodeStatusService)ApplicationManager.getApplication().getService(AICodeStatusService.class)).lC();
    }

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
                string = FileExtensionLanguageDetails.H("Bchhiskcn:|s4FO|`H`k\u007f!>=SRD^WT}=#3v0\u0013Nr&$`:#f'~t@U%y}t6fl;mdi{");
                n4 = a;
                break;
            }
            case 3: {
                string = LanguageFileExtensionDetails.H("S0!Wo9E(\u0003#j\u0016l\u0006aH\u0016-:\\rLy\ff\f3\u0010\\*%\u001aw\u000bc\tgDm\u001bi\u0004");
                n4 = a;
                break;
            }
        }
        switch (n4) {
            default: {
                n3 = 3;
                break;
            }
            case 3: {
                n3 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n3];
        switch (a) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = FileExtensionLanguageDetails.H("zobepd");
                n2 = a;
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = LanguageFileExtensionDetails.H("/F)\f/f\u0001k\r`G@*u\rt\u001f;8\\;|\u001aV\rq\tf\ne(l\u0016u\u0007f\r");
                n2 = a;
                break;
            }
        }
        switch (n2) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = FileExtensionLanguageDetails.H("PFV\fPf~kr`8@Uurt`;G\\D|eVrqvfueWliuxfr");
                n = a;
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = LanguageFileExtensionDetails.H("\u0018s\u000fZ\u0010b\u001ap\u001b");
                n = a;
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = FileExtensionLanguageDetails.H("i|uZG|Vbpzmjzwxjy");
                break;
            }
            case 2: {
                objectArray = objectArray;
                objectArray[2] = LanguageFileExtensionDetails.H("\\0D!Q\u0010r\u001eZ\u0010b\u001ap\u001b");
                break;
            }
            case 3: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (a) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                throw runtimeException;
            }
            case 3: 
        }
        runtimeException = new IllegalStateException(string2);
        throw runtimeException;
    }

    public static void notifyApplication(@NotNull AICodeStatus status) {
        if (status == null) {
            AICodeStatusService.enum(0);
        }
        AICodeStatusService.notifyApplication(status, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    private Pair<AICodeStatus, String> lC() {
        Pair pair;
        AICodeStatusService aICodeStatusService = this;
        Object a = aICodeStatusService.byte;
        synchronized (a) {
            AICodeStatusService aICodeStatusService2 = aICodeStatusService;
            Pair pair2 = Pair.create((Object)((Object)aICodeStatusService2.float), (Object)aICodeStatusService2.enum);
            // MONITOREXIT @DISABLED, blocks:[0, 1] lbl7 : MonitorExitStatement: MONITOREXIT : a
            pair = pair2;
        }
        if (pair == null) {
            AICodeStatusService.enum(3);
        }
        return pair;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void onAICodeStatus(@NotNull AICodeStatus aICodeStatus, @Nullable String string) {
        void status;
        AICodeStatusService aICodeStatusService = this;
        if (status == null) {
            AICodeStatusService.enum(2);
        }
        boolean bl = false;
        Object object = aICodeStatusService.byte;
        // MONITORENTER : object
        if (!aICodeStatusService.float.isDisablingClientRequests()) {
            void a;
            bl = aICodeStatusService.float != status || !StringUtils.equals((CharSequence)aICodeStatusService.enum, (CharSequence)a);
            AICodeStatusService aICodeStatusService2 = aICodeStatusService;
            aICodeStatusService2.float = status;
            aICodeStatusService2.enum = a;
        }
        // MONITOREXIT : object
        if (!bl) return;
        aICodeStatusService.bB();
    }

    /*
     * WARNING - void declaration
     */
    public static void notifyApplication(@NotNull AICodeStatus aICodeStatus, @Nullable String string) {
        void customMessage;
        AICodeStatus status;
        AICodeStatus aICodeStatus2 = aICodeStatus;
        if (aICodeStatus2 == null) {
            AICodeStatusService.enum(1);
        }
        if ((status = ApplicationManager.getApplication()).isDisposed()) {
            return;
        }
        ((AICodeStatusListener)status.getMessageBus().syncPublisher(TOPIC)).onAICodeStatus(aICodeStatus2, (String)customMessage);
    }

    public void dispose() {
    }

    public AICodeStatusService() {
        AICodeStatusService a;
        AICodeStatusService aICodeStatusService = a;
        a.byte = new Object();
        aICodeStatusService.float = AICodeStatus.Ready;
        ApplicationManager.getApplication().getMessageBus().connect((Disposable)a).subscribe(TOPIC, (Object)a);
    }

    private void bB() {
        AICodeStatusService aICodeStatusService = this;
        Runnable a = () -> {
            int a;
            AICodeStatusService aICodeStatusService = this;
            Project[] projectArray = ProjectManager.getInstance().getOpenProjects();
            int n = projectArray.length;
            int n2 = a = 0;
            while (n2 < n) {
                Project project = projectArray[a];
                if (!project.isDisposed()) {
                    StatusBarPopup.update(project, aICodeStatusService.enum);
                }
                n2 = ++a;
            }
        };
        Application application = ApplicationManager.getApplication();
        if (application.isDispatchThread()) {
            a.run();
            return;
        }
        application.invokeLater(a);
    }
}
