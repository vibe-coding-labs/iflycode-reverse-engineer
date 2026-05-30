package com.aicode.util;

import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.RequestResultList;
import com.aicode.test.dto.ChangeInfoDto;
import com.aicode.test.dto.UnitTestMethodDto;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.diff.impl.patch.IdeaTextPatchBuilder;
import com.intellij.openapi.diff.impl.patch.UnifiedDiffWriter;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.openapi.vcs.changes.BinaryContentRevision;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.changes.ContentRevision;
import com.intellij.openapi.vcs.changes.CurrentContentRevision;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.util.PsiTreeUtil;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: va */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/UnitTestCollectUtil.class */
public class UnitTestCollectUtil {
    public static final /* synthetic */ String UNIT_TEST_METHOD_FLAG = BasicActionsBundle.message(RequestResultList.H("KrAf[G<dMnA.FRvo\u0007{X}KeFQLlW\u000fpo_pJv^"), new Object[0]);

    /* renamed from: byte, reason: not valid java name */
    private static final /* synthetic */ Pattern f747byte = Pattern.compile(RequestResultList.H("]oDYp@\f,FL6\u0003\\V\u000b;MP-\u001d[\u0019\u001aXGZ2\u00025\u0015MG<\t\\V\n:)nn\u0005Xj"));

    /* renamed from: enum, reason: not valid java name */
    private static final /* synthetic */ Pattern f748enum = Pattern.compile(RequestResultList.H("s|\u0001"));

    /* renamed from: float, reason: not valid java name */
    private static final /* synthetic */ Logger f746float = Logger.getInstance(UnitTestCollectUtil.class.getName());

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m443enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = RequestResultList.H("Xd5\u001cofvD=BeFH}u\u0003\"F.\u0017D%v\\k^8D~W7WeFTan\u0012sZtF");
                i = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                H = RequestResultList.H("TUzZz@u]0D~Qz(O\\t|hCt\n*\tSrwMiJr\u0012\u00077b\u0004'Zf\u0012\u0012v5\fk\nu_bW7KoF\u0001qe\u0012sZtF");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            default:
                i2 = 2;
                break;
            case 1:
                i2 = 3;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = RequestResultList.H("KrB/SIq~Gb\u001auF^i4|vCl~tPcfo^MvcFH[qF");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = RequestResultList.H("cZ|A\u007fO");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = RequestResultList.H("DrQL[OvNGpM}X");
                i4 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = RequestResultList.H("KrB/SIq~Gb\u001auF^i4|vCl~tPcfo^MvcFH[qF");
                i4 = a;
                break;
        }
        switch (i4) {
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = RequestResultList.H("qYZC\u007fBe\\O@u|o~|]\u007fO");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            default:
                throw new IllegalStateException(format);
            case 1:
                throw new IllegalArgumentException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isTestOfMethod(PsiMethod a) {
        if (a == null) {
            return true;
        }
        try {
            com.intellij.openapi.application.Application application = ApplicationManager.getApplication();
            Objects.requireNonNull(a);
            PsiAnnotation[] psiAnnotationArr = (PsiAnnotation[]) application.runReadAction(a::getAnnotations);
            return ((Boolean) application.executeOnPooledThread(() -> {
                boolean z;
                boolean contains;
                int length = psiAnnotationArr.length;
                int i = 0;
                int i2 = 0;
                while (true) {
                    if (i >= length) {
                        z = false;
                        break;
                    }
                    PsiAnnotation psiAnnotation = psiAnnotationArr[i2];
                    Objects.requireNonNull(psiAnnotation);
                    String str = (String) application.runReadAction(psiAnnotation::getQualifiedName);
                    if (StringUtils.isNotBlank(str)) {
                        contains = TypeUtils.f741final.contains(str);
                        if (contains) {
                            z = true;
                            break;
                        }
                    }
                    i2++;
                    i = i2;
                }
                return Boolean.valueOf(z);
            }).get()).booleanValue();
        } catch (Throwable th) {
            f746float.info(RequestResultList.H("获古泵觌天贏"), th);
            return false;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String diffContent(List<Change> list, Project a) {
        StringWriter stringWriter = new StringWriter();
        String basePath = a.getBasePath();
        if (basePath != null) {
            try {
                List list2 = (List) list.stream().filter(a2 -> {
                    return !m449extends(a2);
                }).collect(Collectors.toList());
                if (!list2.isEmpty()) {
                    Path path = Paths.get(basePath, new String[0]);
                    UnifiedDiffWriter.write(a, path, (List) ApplicationManager.getApplication().executeOnPooledThread(() -> {
                        return IdeaTextPatchBuilder.buildPatch(a, list2.subList(0, Math.min(list2.size(), 500)), path, false, true);
                    }).get(), stringWriter, RequestResultList.H(" "), (CommitContext) null, Collections.emptyList());
                    String[] split = stringWriter.toString().split(RequestResultList.H(" "));
                    StringBuilder sb = new StringBuilder();
                    int length = split.length;
                    int i = 0;
                    int i2 = 0;
                    while (i < length) {
                        String str = split[i2];
                        if (!str.startsWith(RequestResultList.H("I\\yJ`\u0010")) && !str.startsWith(RequestResultList.H("i\\yJ`\u0010")) && !str.startsWith(RequestResultList.H("8&\u0014%\u0017%\u0017,\u001e*\u0018=\u000f\u001c.=\u000f \u0012%\u0017")) && !str.contains(RequestResultList.H("{o\u0012Y`lEqD}\npW7@nV\u0001|f\u0012{FtO"))) {
                            sb.append(str).append(RequestResultList.H(" "));
                        }
                        i2++;
                        i = i2;
                    }
                    return sb.toString();
                }
                return "";
            } catch (Exception e) {
                throw new RuntimeException("Error calculating diff: " + e.getMessage(), e);
            }
        }
        throw new RuntimeException(RequestResultList.H("WGoXRfo\tzKkO1SvQh\u0012H` \\hCt\u0004"));
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static /* synthetic */ List<UnitTestMethodDto> getChangeMethods(List<UnitTestMethodDto> list, List<ChangeInfoDto> list2, boolean z) {
        boolean z2;
        boolean z3;
        Iterator<UnitTestMethodDto> it = list.iterator();
        while (it.hasNext()) {
            UnitTestMethodDto next = it.next();
            List a = next.getMethodRange();
            ArrayList arrayList = new ArrayList();
            for (ChangeInfoDto changeInfoDto : list2) {
                Object changeLine = changeInfoDto.getChangeLine();
                String content = changeInfoDto.getContent();
                if (a.contains(changeLine)) {
                    arrayList.add(content);
                }
            }
            next.setChangeContent(arrayList);
            if (arrayList.isEmpty()) {
                z2 = false;
                z3 = true;
            } else {
                z2 = true;
                z3 = true;
            }
            next.setHasChange(Boolean.valueOf(z2));
            it = it;
        }
        ArrayList arrayList2 = new ArrayList();
        com.intellij.openapi.application.Application application = ApplicationManager.getApplication();
        try {
            for (UnitTestMethodDto unitTestMethodDto : list) {
                UnitTestMethodDto unitTestMethodDto2 = (UnitTestMethodDto) application.executeOnPooledThread(() -> {
                    PsiMethod psiMethod = unitTestMethodDto.getPsiMethod();
                    Boolean hasChange = unitTestMethodDto.getHasChange();
                    if (((Boolean) application.runReadAction(() -> {
                        return Boolean.valueOf(m455this(psiMethod));
                    })).booleanValue() || !hasChange.booleanValue()) {
                        return null;
                    }
                    int count = (int) unitTestMethodDto.getChangeContent().stream().filter(a2 -> {
                        return a2.startsWith(RequestResultList.H("\u0001"));
                    }).count();
                    String str = (String) application.runReadAction(() -> {
                        return getTestMethodId(psiMethod);
                    });
                    int a3 = z ? unitTestMethodDto.getMethodLine().intValue() : count;
                    boolean isTestOfMethod = isTestOfMethod(psiMethod);
                    Objects.requireNonNull(psiMethod);
                    f746float.info("方法名为" + ((String) application.runReadAction(psiMethod::getName)) + "，是单测方法：" + isTestOfMethod + "，变更的方法行数为" + unitTestMethodDto.getMethodLine() + "行，净增行数为" + a3 + "行，方法的标识为" + str);
                    return new UnitTestMethodDto(psiMethod, unitTestMethodDto.getMethodLine(), Boolean.valueOf(isTestOfMethod), str, Integer.valueOf(a3));
                }).get();
                if (unitTestMethodDto2 != null) {
                    arrayList2.add(unitTestMethodDto2);
                }
            }
        } catch (Exception e) {
            f746float.info("遍历单测方法信息异常：" + e.getMessage());
        }
        return arrayList2;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* renamed from: this, reason: not valid java name */
    private static /* synthetic */ boolean m455this(PsiMethod a) {
        return a.isConstructor() || PropertyUtils.isPropertyGetter(a) || PropertyUtils.isPropertySetter(a) || a.hasModifierProperty(RequestResultList.H("@qsFoN{^")) || a.hasModifierProperty(RequestResultList.H("nSiFnO")) || PropertyUtils.isMainMethod(a);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String getTestMethodId(PsiMethod a) {
        PsiDocComment firstChild = a.getFirstChild();
        if (firstChild instanceof PsiDocComment) {
            String[] split = firstChild.getText().split(RequestResultList.H(" "));
            int length = split.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                String str = split[i2];
                if (!str.contains(UNIT_TEST_METHOD_FLAG)) {
                    i2++;
                    i = i2;
                } else {
                    return str.substring(str.indexOf(UNIT_TEST_METHOD_FLAG) + UNIT_TEST_METHOD_FLAG.length()).trim();
                }
            }
            return null;
        }
        return null;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: volatile, reason: not valid java name */
    private static /* synthetic */ boolean m452volatile(ContentRevision contentRevision) {
        VirtualFile virtualFile;
        ContentRevision contentRevision2 = contentRevision;
        if (!(contentRevision2 instanceof CurrentContentRevision)) {
            contentRevision2 = null;
        }
        CurrentContentRevision currentContentRevision = (CurrentContentRevision) contentRevision2;
        if (currentContentRevision == null || (virtualFile = currentContentRevision.getVirtualFile()) == null) {
            return false;
        }
        return m440throw(contentRevision2) || FileUtilRt.isTooLarge(virtualFile.getLength());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    /* renamed from: return, reason: not valid java name */
    private static /* synthetic */ List<Integer> m453return(Document a, PsiMethod a2) {
        ArrayList arrayList = new ArrayList();
        PsiMethod navigationElement = a2.getNavigationElement();
        int startOffset = navigationElement.getTextRange().getStartOffset();
        int endOffset = navigationElement.getTextRange().getEndOffset();
        int lineNumber = a.getLineNumber(startOffset) + 1;
        int lineNumber2 = a.getLineNumber(endOffset) + 1;
        int i = lineNumber;
        int i2 = i;
        while (i <= lineNumber2) {
            int i3 = i2;
            i2++;
            arrayList.add(Integer.valueOf(i3));
            i = i2;
        }
        if (arrayList == null) {
            m443enum(0);
        }
        return arrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: private, reason: not valid java name */
    private static /* synthetic */ List<ChangeInfoDto> m450private(Map<String, String> map, int a) {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ArrayList arrayList4 = new ArrayList();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String[] split = entry.getValue().split(RequestResultList.H(" "));
            Matcher matcher = f748enum.matcher(key);
            int i = 0;
            ArrayList arrayList5 = new ArrayList();
            while (matcher.find()) {
                i++;
                if (i == 3) {
                    int parseInt = Integer.parseInt(matcher.group());
                    if (matcher.find()) {
                        int parseInt2 = Integer.parseInt(matcher.group()) - 6;
                        if (parseInt2 != 0) {
                            int i2 = 0;
                            int i3 = 0;
                            while (i2 < parseInt2 + 1) {
                                int i4 = i3;
                                i3++;
                                arrayList5.add(Integer.valueOf(parseInt + 3 + i4));
                                i2 = i3;
                            }
                            arrayList = arrayList5;
                        } else {
                            arrayList = arrayList5;
                            arrayList.add(Integer.valueOf(parseInt + 3));
                        }
                        if (CollectionUtils.isNotEmpty(arrayList)) {
                            if (((Integer) arrayList5.get(arrayList5.size() - 1)).intValue() + 3 >= a) {
                                arrayList2 = new ArrayList(Arrays.asList(split).subList(3, split.length - 2));
                                arrayList3 = arrayList5;
                            } else {
                                arrayList2 = new ArrayList(Arrays.asList(split).subList(3, split.length - 3));
                                arrayList3 = arrayList5;
                            }
                            if (arrayList3.size() > arrayList2.size()) {
                                int size = arrayList5.size() - arrayList2.size();
                                int i5 = 0;
                                int i6 = 0;
                                while (i5 < arrayList2.size()) {
                                    ChangeInfoDto changeInfoDto = new ChangeInfoDto((Integer) arrayList5.get(size + i6), (String) arrayList2.get(i6));
                                    i6++;
                                    arrayList4.add(changeInfoDto);
                                    i5 = i6;
                                }
                            } else {
                                int size2 = arrayList2.size() - arrayList5.size();
                                int i7 = 0;
                                int i8 = 0;
                                while (i7 < arrayList5.size()) {
                                    ChangeInfoDto changeInfoDto2 = new ChangeInfoDto((Integer) arrayList5.get(i8), (String) arrayList2.get(size2 + i8));
                                    i8++;
                                    arrayList4.add(changeInfoDto2);
                                    i7 = i8;
                                }
                            }
                        }
                    }
                }
            }
        }
        return arrayList4;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* renamed from: extends, reason: not valid java name */
    private static /* synthetic */ boolean m449extends(@NotNull Change change) {
        if (change == null) {
            m443enum(1);
        }
        return m452volatile(change.getBeforeRevision()) || m452volatile(change.getAfterRevision());
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: throw, reason: not valid java name */
    private static /* synthetic */ boolean m440throw(ContentRevision a) {
        if (a == null) {
            return false;
        }
        if (a instanceof BinaryContentRevision) {
            return true;
        }
        return a.getFile().getFileType().isBinary();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ List<ChangeInfoDto> getChangeByDiff(String a, int a2) {
        Matcher matcher = f747byte.matcher(a);
        ArrayList arrayList = new ArrayList();
        Matcher matcher2 = matcher;
        while (matcher2.find()) {
            matcher2 = matcher;
            arrayList.add(matcher2.group());
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(arrayList.size());
        int i = 0;
        int i2 = 0;
        while (i < arrayList.size()) {
            if (i2 != arrayList.size() - 1) {
                String str = (String) arrayList.get(i2);
                linkedHashMap.put(str, a.substring(a.indexOf(str) + str.length() + 1, a.indexOf((String) arrayList.get(i2 + 1))));
            } else {
                String str2 = (String) arrayList.get(i2);
                linkedHashMap.put(str2, a.substring(a.indexOf(str2) + str2.length() + 1));
            }
            i2++;
            i = i2;
        }
        return m450private(linkedHashMap, a2);
    }

    public static /* synthetic */ List<UnitTestMethodDto> getAllMethods(Project a, Document a2) {
        ArrayList arrayList = new ArrayList();
        ApplicationManager.getApplication().runReadAction(() -> {
            for (PsiMethod psiMethod : PsiTreeUtil.findChildrenOfType(PsiDocumentManager.getInstance(a).getPsiFile(a2), PsiMethod.class)) {
                if (psiMethod != null) {
                    List<Integer> m453return = m453return(a2, psiMethod);
                    arrayList.add(new UnitTestMethodDto(psiMethod, m453return, Integer.valueOf(m453return.size()), false));
                }
            }
        });
        return arrayList;
    }
}
