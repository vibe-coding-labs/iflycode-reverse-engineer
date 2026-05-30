package com.aicode.util;

import cn.hutool.core.util.StrUtil;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/* compiled from: ab */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/ClassNameUtils.class */
public class ClassNameUtils {

    /* renamed from: enum */
    private static final /* synthetic */ Pattern f667enum = Pattern.compile(InlineChatStatusServiceKt.H("iXjKeW"));

    public static /* synthetic */ String stripArrayVarargsDesignator(String a) {
        return a.replace(InlineChatStatusServiceKt.H("��#"), "").replace(NewFileUtils.H("]\u0012@"), "");
    }

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum */
    private static /* synthetic */ void m396enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 3:
            default:
                H = NewFileUtils.H("!@H\bJ\u0010C\u000b\u0001\u0015E\n\u000b9.]O'O\u0004KUR\u0011\u0012S`:V\u0015]]\u001aJNH =I]\u0006\u0002\u0005\\hiW\u001dT\u0001\u0002\u001eR\u001b\u000b\u001bERU\u001cO\u001d");
                i = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                H = InlineChatStatusServiceKt.H("\r&iW\u0013\r0\u0015a\t!\u0015nL\u000fna\u0012gI(^6\u000b\u00157i\u0002*\u0014m\u001a\u0018,)\u000b/D*\u00147\u0012");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 3:
            default:
                i2 = 3;
                break;
            case 2:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = NewFileUtils.H("F\u0003u\bN\u0014");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = InlineChatStatusServiceKt.H("1\u001c5\f+\u0005\u0012\u001a");
                i3 = a;
                break;
            case 2:
                objArr[0] = NewFileUtils.H("^��P@a;L\u0012G\u0014\u0004\fo VGd\u0019C\u0003N!J\u0014E'O��O\u0002");
                i3 = a;
                break;
            case 3:
                objArr[0] = InlineChatStatusServiceKt.H("\u0010=(\u0011.��\u0010\u0004#\n");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 3:
            default:
                objArr[1] = NewFileUtils.H("^��P@a;L\u0012G\u0014\u0004\fo VGd\u0019C\u0003N!J\u0014E'O��O\u0002");
                i4 = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = InlineChatStatusServiceKt.H(">\u0006\u00121(\u000f1#\"\u0006\t95\u0017$\u0016\u0010\u0018+\u001b");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = NewFileUtils.H("_\u0010S\u0007C\u0013I,G\u0018S\u0001u\bN\u0014");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = InlineChatStatusServiceKt.H("\u000e60\u0013(\u000f/=7\u001f\u00150\u0007\r(\u0005\u000b\u0007\u000f5\u0011\u001c5\f+\u0005\u0012\u001a");
                break;
            case 2:
                break;
            case 3:
                objArr[2] = NewFileUtils.H(",B\u001cU\u0014A\u0004p\n_\u0011O\u0016u\bN\u0014");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 3:
            default:
                throw new IllegalArgumentException(format);
            case 2:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String extractTargetPropertyName(String a, boolean z, boolean z2) {
        if (!z2 || !a.startsWith(NewFileUtils.H("\u0016N\r"))) {
            if (!z2 || !a.startsWith(NewFileUtils.H("B\n"))) {
                if (!z || !a.startsWith(NewFileUtils.H("\u0002N\r"))) {
                    return null;
                }
                return removeFromCamelCaseName(a, InlineChatStatusServiceKt.H("\u0012>\n"));
            }
            return removeFromCamelCaseName(a, InlineChatStatusServiceKt.H("2\r"));
        }
        return removeFromCamelCaseName(a, InlineChatStatusServiceKt.H("\u0006>\n"));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static final /* synthetic */ String extractGenerics(String a) {
        Matcher matcher = f667enum.matcher(a);
        if (!matcher.find()) {
            return "";
        }
        return matcher.group(1);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String resolveGenericTypeName(String a) {
        if (!StrUtil.isNotEmpty(a) || a.indexOf(NewFileUtils.H("E")) <= 0) {
            return a;
        }
        return a.substring(0, a.indexOf(InlineChatStatusServiceKt.H("B")));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String extractClassName(@NotNull String fqName) {
        if (fqName == null) {
            m396enum(0);
        }
        String extractContainerType = extractContainerType(fqName);
        int lastIndexOf = extractContainerType.lastIndexOf(46);
        return stripArrayVarargsDesignator(lastIndexOf == -1 ? extractContainerType : extractContainerType.substring(lastIndexOf + 1));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String getPackageName(String a) {
        String a2 = com.intellij.openapi.util.io.FileUtil.toSystemDependentName(a, File.separatorChar);
        if (!StringUtils.contains(a2, InlineChatStatusServiceKt.H("\u00024\u0013"))) {
            return "";
        }
        return StringUtils.replace(a2.substring(a.lastIndexOf(NewFileUtils.H("\u0012D\u0014"))), File.separator, InlineChatStatusServiceKt.H("P"));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    public static /* synthetic */ String extractContainerType(String a) {
        int indexOf = a.indexOf(60);
        String stripArrayVarargsDesignator = stripArrayVarargsDesignator(indexOf == -1 ? a : a.substring(0, indexOf));
        if (stripArrayVarargsDesignator == null) {
            m396enum(2);
        }
        return stripArrayVarargsDesignator;
    }

    public static /* synthetic */ String extractClassNameFormMethodId(@NotNull String methodId) {
        if (methodId == null) {
            m396enum(1);
        }
        return methodId.substring(0, methodId.indexOf(NewFileUtils.H("[")));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String extractPackageName(String a) {
        if (a == null) {
            return null;
        }
        String extractContainerType = extractContainerType(a);
        int lastIndexOf = extractContainerType.lastIndexOf(46);
        return stripArrayVarargsDesignator(lastIndexOf == -1 ? "" : extractContainerType.substring(0, lastIndexOf));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String stripArrayVarargsDesignator(String a, boolean z) {
        String stripArrayVarargsDesignator = stripArrayVarargsDesignator(a);
        int indexOf = stripArrayVarargsDesignator.indexOf(InlineChatStatusServiceKt.H("��#"));
        if (z && indexOf > 0) {
            return stripArrayVarargsDesignator.substring(0, indexOf);
        }
        return stripArrayVarargsDesignator;
    }

    public static /* synthetic */ String extractMethodName(@NotNull String methodText) {
        if (methodText == null) {
            m396enum(3);
        }
        String str = methodText;
        if (methodText.indexOf(NewFileUtils.H("Q")) > 0) {
            int length = methodText.length() - 1;
            if (methodText.lastIndexOf(InlineChatStatusServiceKt.H("W")) > 0) {
                length = methodText.lastIndexOf(NewFileUtils.H("P")) - 1;
            }
            str = methodText.substring(0, length);
        }
        return (String) Arrays.stream(str.split(InlineChatStatusServiceKt.H("\u0007P"))).map(StringUtils::capitalizeFirstLetter).collect(Collectors.joining(NewFileUtils.H("&")));
    }

    public static /* synthetic */ int arrayDimensions(String a) {
        return a.split(InlineChatStatusServiceKt.H("=��#"), -1).length - 1;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String isExceptionClass(String a) {
        Project findCurrentProject = ApplicationUtil.findCurrentProject();
        if (findCurrentProject == null) {
            return "";
        }
        if (!NewFileUtils.H("d\u0013G\u001cY\rV)U\u0012T\u001eE\u001cy\u001aZ\u0013J\rI\u0006R\u0001").equals(a)) {
            JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(findCurrentProject);
            GlobalSearchScope allScope = GlobalSearchScope.allScope(findCurrentProject);
            PsiShortNamesCache psiShortNamesCache = PsiShortNamesCache.getInstance(findCurrentProject);
            return (String) ApplicationManager.getApplication().runReadAction(() -> {
                PsiClass findClass = javaPsiFacade.findClass(NewFileUtils.H("8N\u000bB_G\u0018u.\u0014<O\u0007M\u0007\\\r{ "), allScope);
                if (findClass == null) {
                    return "";
                }
                PsiClass[] classesByName = psiShortNamesCache.getClassesByName(a, allScope);
                int length = classesByName.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    PsiClass psiClass = classesByName[i2];
                    if (!psiClass.isInheritor(findClass, true)) {
                        i2++;
                        i = i2;
                    } else {
                        return psiClass.getQualifiedName();
                    }
                }
                return "";
            });
        }
        return InlineChatStatusServiceKt.H("+\u00052��(O\n #O����7\u001b<\u001f\n\u0002;\u000b0\r(\u0006\t\u001d$\u001a$\u00140\b4\u0010");
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String resolveCanonicalName(Object a, Object obj) {
        String str = null;
        if (0 != 0) {
            str = null;
        } else {
            if (a instanceof PsiType) {
                return ((PsiType) a).getCanonicalText();
            }
            if (a instanceof PsiClass) {
                return ((PsiClass) a).getQualifiedName();
            }
        }
        return str;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String removeFromCamelCaseName(String a, String a2) {
        String a3 = a.replaceFirst(a2, "");
        if (a3.length() == 0) {
            return null;
        }
        return a3.substring(0, 1).toLowerCase() + a3.substring(1, a3.length());
    }
}
