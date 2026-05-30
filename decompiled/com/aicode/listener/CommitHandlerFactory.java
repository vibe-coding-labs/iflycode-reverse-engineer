package com.aicode.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.test.dto.ChangeInfoDto;
import com.aicode.test.dto.CommitChangeDto;
import com.aicode.test.dto.UnitTestCollectDto;
import com.aicode.test.dto.UnitTestMethodDto;
import com.aicode.ui.FontKt;
import com.aicode.util.HandleCacheUtil;
import com.aicode.util.JComponentKt;
import com.aicode.util.StringUtils;
import com.aicode.util.UnitTestCollectUtil;
import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsRoot;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.vfs.VirtualFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: sg */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/CommitHandlerFactory.class */
public class CommitHandlerFactory extends CheckinHandlerFactory {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m244enum(int a) {
        String H = CancelRequestTip.H("6\u0005\u0017\u0005\f\u0004\u0004\u001eA\u0007\u0019\u0004\nj\t(\u001f%\u001c\u0005\u0005I\u001d\f\u0002\u0011!)\u0011��\u0018JGE\u001bOA\u000e\u0010Vg1K@\u0014G\u001d\u0005\u0018\u001fV\u0018\u0012\tA\u0003\u000fJ\u001f\u0004\u0005\u0005");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = FontKt.H("5#0?*");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = CancelRequestTip.H("\b\u0019\u001b\u0010\u0014\u0015\"\u0005\u0004\u0005\u0014\u0011\u001d");
                break;
        }
        objArr[1] = FontKt.H("=-.p\u001e\n5%= |#24&++<\u0003B\u0015%9%*+\u0010%+=\"7 \b8&61(?");
        objArr[2] = CancelRequestTip.H("\b\u0004\u0013\u001c\t\u0004)\u000b\u0004\u0015\u001d\f\u001b");
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* compiled from: sg */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/CommitHandlerFactory$o.class */
    private static class o extends CheckinHandler {

        /* renamed from: final, reason: not valid java name */
        private final Project f507final;

        /* renamed from: byte, reason: not valid java name */
        private final CheckinProjectPanel f510byte;

        /* renamed from: enum, reason: not valid java name */
        private final Map<VirtualFile, List<Change>> f511enum = new ConcurrentHashMap();

        /* renamed from: float, reason: not valid java name */
        private static final Map<Project, String> f509float = new ConcurrentHashMap();

        /* renamed from: try, reason: not valid java name */
        private static final Logger f508try = LoggerFactory.getLogger(o.class);

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        private List<UnitTestMethodDto> Fc(Change a, List<Integer> list, List<Integer> list2) {
            o oVar;
            ArrayList arrayList = new ArrayList();
            VirtualFile virtualFile = a.getVirtualFile();
            if (virtualFile != null) {
                if (HandleCacheUtil.H("\u000b_\\\u0014").equalsIgnoreCase(FileTypeManager.getInstance().getFileTypeByFile(virtualFile).getName())) {
                    FileStatus fileStatus = a.getFileStatus();
                    boolean equals = FileStatus.ADDED.equals(fileStatus);
                    boolean equals2 = FileStatus.MODIFIED.equals(fileStatus);
                    if (!equals && !equals2) {
                        return arrayList;
                    }
                    String diffContent = UnitTestCollectUtil.diffContent(List.of(a), this.f507final);
                    if (!StringUtils.isBlank(diffContent)) {
                        Document document = (Document) ApplicationManager.getApplication().runReadAction(() -> {
                            return FileDocumentManager.getInstance().getDocument(virtualFile);
                        });
                        if (document != null) {
                            int lineCount = document.getLineCount();
                            List<ChangeInfoDto> changeByDiff = UnitTestCollectUtil.getChangeByDiff(diffContent, lineCount);
                            if (!changeByDiff.isEmpty()) {
                                list.add(Integer.valueOf(lineCount));
                                if (equals) {
                                    oVar = this;
                                    list2.add(Integer.valueOf(lineCount));
                                } else {
                                    int count = (int) Stream.of((Object[]) diffContent.split(JComponentKt.H("c"))).filter(a2 -> {
                                        return !a2.startsWith(HandleCacheUtil.H("5!~")) && a2.startsWith(JComponentKt.H("`"));
                                    }).count();
                                    oVar = this;
                                    list2.add(Integer.valueOf(count));
                                }
                                return UnitTestCollectUtil.getChangeMethods(UnitTestCollectUtil.getAllMethods(oVar.f507final, document), changeByDiff, equals);
                            }
                            f508try.info("current file has no change info list:  " + virtualFile.getPath());
                            return arrayList;
                        }
                        return arrayList;
                    }
                    f508try.info("current file has no diff:  " + virtualFile.getPath());
                    return arrayList;
                }
                return arrayList;
            }
            return arrayList;
        }

        public CheckinHandler.ReturnResult beforeCheckin() {
            if (StringUtils.isNotBlank(PluginStartupActivity.getApiKey())) {
                Collection selectedChanges = this.f510byte.getSelectedChanges();
                ApplicationManager.getApplication().executeOnPooledThread(() -> {
                    Iterator it = selectedChanges.iterator();
                    while (true) {
                        while (it.hasNext()) {
                            Change change = (Change) it.next();
                            VirtualFile virtualFile = change.getVirtualFile();
                            if (virtualFile != null) {
                                VcsRoot vcsRootObjectFor = ProjectLevelVcsManager.getInstance(this.f507final).getVcsRootObjectFor(virtualFile);
                                if (vcsRootObjectFor != null) {
                                    if (vcsRootObjectFor.getVcs() != null) {
                                        VirtualFile path = vcsRootObjectFor.getPath();
                                        List<Change> list = this.f511enum.get(path);
                                        List<Change> list2 = list;
                                        if (list == null) {
                                            list2 = new ArrayList();
                                        }
                                        list2.add(change);
                                        this.f511enum.put(path, list2);
                                    }
                                }
                            }
                        }
                        return;
                    }
                });
            }
            return super.beforeCheckin();
        }

        public o(CheckinProjectPanel a) {
            this.f510byte = a;
            this.f507final = a.getProject();
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        private void GC(List<UnitTestMethodDto> list, List<Integer> list2, List<Integer> list3, VirtualFile a) {
            String oC = oC(a);
            int sum = list2.stream().mapToInt((v0) -> {
                return v0.intValue();
            }).sum();
            if (sum != 0) {
                int sum2 = list3.stream().mapToInt((v0) -> {
                    return v0.intValue();
                }).sum();
                int i = 0;
                int i2 = 0;
                int i3 = 0;
                ArrayList arrayList = new ArrayList();
                Iterator<UnitTestMethodDto> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    UnitTestMethodDto next = it.next();
                    int intValue = next.getMethodLine().intValue();
                    if (next.getUnitTestMethod().booleanValue()) {
                        i += intValue;
                        i2 += next.getIncrement().intValue();
                        String methodId = next.getMethodId();
                        if (StringUtils.isNotBlank(methodId)) {
                            int i4 = i3 + intValue;
                            i3 = i4;
                            if (i4 > i) {
                                f508try.info("aiUnitTestTotal > unitTestTotal :" + i3 + "," + i);
                                break;
                            }
                            arrayList.add(new CommitChangeDto(methodId, Integer.valueOf(intValue)));
                        } else {
                            continue;
                        }
                    }
                }
                f508try.info("commitId " + oC + ", commitTotal " + sum + ", commitIncrementTotal " + sum2 + ", commitUnitTestTotal " + i + ", commitUnitTestIncrementTotal " + i2);
                if (i > sum) {
                    i = sum;
                }
                if (i2 > sum2) {
                    i2 = sum2;
                }
                UnitTestCollectDto unitTestCollectDto = new UnitTestCollectDto(oC, Integer.valueOf(sum), Integer.valueOf(sum2), Integer.valueOf(i), Integer.valueOf(i2), JComponentKt.H("+\u001b,\n:Fm\u0011 &\b!\u001d"), arrayList);
                unitTestCollectDto.setClientName(ApplicationInfo.getInstance().getVersionName());
                unitTestCollectDto.setClientVersion(ApplicationInfo.getInstance().getApiVersion());
                unitTestCollectDto.setPluginVersion(BasicActionsBundle.message(HandleCacheUtil.H(">~+L\u0018auv5d)c;9>y12we;"), new Object[0]));
                MessageDto messageDto = new MessageDto();
                messageDto.setCommand(CommandEnum.LOG_TEST_COLLECTION_COMMIT.getType());
                messageDto.setId(IdUtil.fastSimpleUUID());
                messageDto.setPath(a.getPath());
                messageDto.setData(unitTestCollectDto);
                PluginWebsocketClient.sendWsMessage(messageDto, this.f507final);
                return;
            }
            f508try.info(HandleCacheUtil.H("c-8`\u0006\u001ae9d2k#e<\u007fnN\u001di6b !:h\"a\u007fr:Q\u0013v{d 1-e8z!h\u0017.jk9"));
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public void checkinSuccessful() {
            ArrayList arrayList;
            if (StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
                f508try.info(HandleCacheUtil.H(".x1s~g9 3x/J\u0012({b61 e!7+s/-{i!"));
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            f508try.info("start data collection in " + currentTimeMillis);
            for (Map.Entry<VirtualFile, List<Change>> entry : this.f511enum.entrySet()) {
                VirtualFile key = entry.getKey();
                List<Change> value = entry.getValue();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                Iterator<Change> it = value.iterator();
                while (it.hasNext()) {
                    arrayList = arrayList4;
                    List<UnitTestMethodDto> Fc = Fc(it.next(), arrayList3, arrayList);
                    if (CollUtil.isEmpty(Fc)) {
                        it = it;
                    } else {
                        arrayList2.addAll(Fc);
                        it = it;
                    }
                }
                if (!CollUtil.isNotEmpty(arrayList2)) {
                    f508try.info(JComponentKt.H("\u0007m]7E,\u0006(\u000e,\f;��(\u000fe��-\t\u00120-_\"\f;\u0001&\u000b\u007f\n6\u0003/L0\f`V"));
                } else {
                    arrayList = arrayList4;
                    GC(arrayList2, arrayList3, arrayList, key);
                }
            }
            long currentTimeMillis2 = System.currentTimeMillis();
            Logger logger = f508try;
            long j = arrayList - currentTimeMillis;
            logger.info("end data collection in " + currentTimeMillis2 + ", duration " + logger + "ms");
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        private String oC(VirtualFile a) {
            try {
                return (String) ApplicationManager.getApplication().executeOnPooledThread(() -> {
                    Repository repositoryForRoot = VcsRepositoryManager.getInstance(this.f507final).getRepositoryForRoot(a);
                    if (repositoryForRoot == null) {
                        f508try.info(JComponentKt.H("\r)9K=\f(\u00110\f*\u0017'\n(G0E\u0006;!\u0007"));
                        return IdUtil.fastSimpleUUID();
                    }
                    String currentRevision = repositoryForRoot.getCurrentRevision();
                    String str = currentRevision;
                    if (StringUtils.isBlank(currentRevision)) {
                        f508try.info(HandleCacheUtil.H("\u001fk6k0e\u0007n&7!oc/kf9"));
                        return IdUtil.fastSimpleUUID();
                    }
                    String str2 = f509float.get(this.f507final);
                    if (StringUtils.isNotBlank(str2) && str2.contains(str)) {
                        f508try.info(JComponentKt.H("\t# \u0006&\u001dx\u0017'E7\u000bu\u0017}^/\f\u000b/9\u000e"));
                        str = str + "_" + System.currentTimeMillis();
                    }
                    f509float.put(this.f507final, str);
                    return str;
                }).get();
            } catch (Exception unused) {
                return IdUtil.fastSimpleUUID();
            }
        }
    }

    @NotNull
    public CheckinHandler createHandler(@NotNull CheckinProjectPanel panel, @NotNull CommitContext a) {
        if (panel == null) {
            m244enum(0);
        }
        if (a == null) {
            m244enum(1);
        }
        return new o(panel);
    }
}
