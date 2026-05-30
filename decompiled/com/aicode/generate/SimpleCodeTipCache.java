package com.aicode.generate;

import com.aicode.diff.GenericUtils;
import com.aicode.service.CodeTip;
import com.aicode.service.TipCache;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.NewFileUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.hash.EqualityPolicy;
import com.intellij.util.containers.hash.LinkedHashMap;
import com.intellij.util.io.DigestUtil;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: sj */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/generate/SimpleCodeTipCache.class */
public class SimpleCodeTipCache implements TipCache {

    /* renamed from: case, reason: not valid java name */
    @Nullable
    private String f303case;

    /* renamed from: final, reason: not valid java name */
    private final ReadWriteLock f304final = new ReentrantReadWriteLock();

    /* renamed from: try, reason: not valid java name */
    private static final Logger f305try = Logger.getInstance(SimpleCodeTipCache.class);

    /* renamed from: float, reason: not valid java name */
    private boolean f306float;

    /* renamed from: byte, reason: not valid java name */
    private final LinkedHashMap<Z, List<CodeTip>> f307byte;

    /* renamed from: enum, reason: not valid java name */
    @Nullable
    private String f308enum;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m161enum(int a) {
        String H = GenericUtils.H("\u0010+4.55=/d*8-b\n\u001d4l^\u0010\u00013w+24/77'>\u0007]~t!}s4\u0004Jz$hk\u0006]:*&)~8</s9=p->7?");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 2:
            case 3:
            case 6:
            case 8:
            case 10:
            default:
                objArr[0] = NewFileUtils.H("P��^\u000fJ\t");
                break;
            case 1:
            case 4:
            case 7:
            case 11:
                do {
                } while (0 != 0);
                objArr[0] = GenericUtils.H("(\",&+'");
                break;
            case 5:
                objArr[0] = NewFileUtils.H("R\u001dF\u001c");
                break;
            case 9:
                objArr[0] = GenericUtils.H(":#2\u001b8,\"86");
                break;
        }
        objArr[1] = NewFileUtils.H("]\u0003O_J\u0010n0E\u0016\u0005\u001fN\u0017\u007f:F\u0001[C^6B\rA\u001ae\u001bO\u001c\u007f\u0010P1Z\nK\u0014");
        switch (a) {
            case 0:
            default:
                objArr[2] = GenericUtils.H("<.\u00127'> /\b\"&-2+");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = NewFileUtils.H("\u000eF\u0005");
                break;
            case 2:
                objArr[2] = GenericUtils.H("<6/\u001417.('");
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = NewFileUtils.H("\bG\u0015");
                break;
            case 6:
            case 7:
                objArr[2] = GenericUtils.H("+&7:'>\u001417.('");
                break;
            case 8:
                objArr[2] = NewFileUtils.H("\u001aH\u000bj\u0015_\u001cX\rl\u001dX\u0002F\u0015");
                break;
            case 9:
            case 10:
                objArr[2] = GenericUtils.H("*7\";4&/\b\"&-2+");
                break;
            case 11:
                objArr[2] = NewFileUtils.H("[\u000bD\u0014P\u0006s\bP\u0019");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.TipCache
    public void updateLatest(@NotNull String prefix, @NotNull String prompt, boolean z) {
        if (prefix == null) {
            m161enum(6);
        }
        if (prompt == null) {
            m161enum(7);
        }
        Lock writeLock = this.f304final.writeLock();
        writeLock.lock();
        try {
            this.f303case = prefix;
            this.f308enum = mF(prompt);
            this.f306float = z;
            writeLock.unlock();
        } catch (Throwable th) {
            writeLock.unlock();
            throw th;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.TipCache
    @Nullable
    public List<CodeTip> get(@NotNull String prompt, boolean z) {
        if (prompt == null) {
            m161enum(1);
        }
        f305try.trace(NewFileUtils.H("q\u0014J\u001eK\u0015]\u0010c8\u0001\u0010K\u001bC\u001c~hF\u0005WLd+J\u0010^_@\u001bYYY\tD\u0014J\u001c"));
        Lock readLock = this.f304final.readLock();
        readLock.lock();
        try {
            List<CodeTip> list = (List) this.f307byte.get(new Z(mF(prompt), z));
            readLock.unlock();
            return list;
        } catch (Throwable th) {
            readLock.unlock();
            throw th;
        }
    }

    /* compiled from: sj */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/generate/SimpleCodeTipCache$Y.class */
    class Y extends LinkedHashMap<Z, List<CodeTip>> {

        /* renamed from: byte, reason: not valid java name */
        public final /* synthetic */ int f309byte;

        public /* bridge */ /* synthetic */ boolean removeEldestEntry(Map.Entry entry, Object obj, Object obj2) {
            return removeEldestEntry((Map.Entry<Z, List<CodeTip>>) entry, (Z) obj, (List<CodeTip>) obj2);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Y(int a, float a2, EqualityPolicy a3, boolean z, int i) {
            super(a, a2, a3, z);
            this.f309byte = i;
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        public boolean removeEldestEntry(Map.Entry<Z, List<CodeTip>> entry, Z z, List<CodeTip> list) {
            return size() > this.f309byte;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    @Override // com.aicode.service.TipCache
    public boolean isLatestPrefix(@NotNull String a) {
        boolean z;
        if (a == null) {
            m161enum(0);
        }
        Lock readLock = this.f304final.readLock();
        readLock.lock();
        try {
            if (this.f303case != null) {
                if (this.f303case.equals(a)) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            readLock.unlock();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.TipCache
    @Nullable
    public List<CodeTip> getLatest(@NotNull String a) {
        if (a == null) {
            m161enum(2);
        }
        Lock readLock = this.f304final.readLock();
        readLock.lock();
        try {
            List<CodeTip> qe = qe(a);
            readLock.unlock();
            return qe;
        } catch (Throwable th) {
            readLock.unlock();
            throw th;
        }
    }

    public SimpleCodeTipCache(int a) {
        this.f307byte = new Y(a, 0.6f, EqualityPolicy.CANONICAL, true, a);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.TipCache
    public void clear() {
        Lock writeLock = this.f304final.writeLock();
        writeLock.lock();
        try {
            this.f308enum = null;
            this.f303case = null;
            this.f306float = false;
            this.f307byte.clear();
        } finally {
            writeLock.unlock();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.service.TipCache
    public void add(@NotNull String prefix, @NotNull String prompt, boolean z, @NotNull CodeTip a) {
        if (prefix == null) {
            m161enum(3);
        }
        if (prompt == null) {
            m161enum(4);
        }
        if (a == null) {
            m161enum(5);
        }
        if (f305try.isTraceEnabled()) {
            f305try.trace("Caching new APIChoice for prompt: " + a);
        }
        Lock writeLock = this.f304final.writeLock();
        writeLock.lock();
        try {
            this.f303case = prefix;
            this.f308enum = mF(prompt);
            this.f306float = z;
            ((List) this.f307byte.computeIfAbsent(new Z(this.f308enum, this.f306float), a2 -> {
                return ContainerUtil.createLockFreeCopyOnWriteList();
            })).add(a.asCached());
            writeLock.unlock();
        } catch (Throwable th) {
            writeLock.unlock();
            throw th;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Nullable
    private List<CodeTip> qe(@NotNull String a) {
        if (a == null) {
            m161enum(8);
        }
        if (!StringUtils.isNotBlank(a)) {
            return null;
        }
        String TrimEndSpaceTab = CodeTipUtil.TrimEndSpaceTab(a);
        List list = (List) this.f307byte.get(new Z(mF(TrimEndSpaceTab), this.f306float));
        if (list == null || TrimEndSpaceTab.length() < this.f303case.length()) {
            return null;
        }
        String substring = TrimEndSpaceTab.substring(this.f303case.length());
        if (substring.isEmpty()) {
            return Collections.unmodifiableList(list);
        }
        List<CodeTip> list2 = (List) list.stream().map(a2 -> {
            return gE(a2, substring);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).collect(Collectors.toList());
        if (list2.isEmpty()) {
            return null;
        }
        return list2;
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    @Nullable
    private CodeTip gE(@NotNull CodeTip apiChoice, @NotNull String a) {
        String substring;
        boolean z;
        if (apiChoice == null) {
            m161enum(9);
        }
        if (a == null) {
            m161enum(10);
        }
        if (StringUtils.isBlank(a)) {
            return apiChoice;
        }
        boolean isEmpty = AICodeStringUtil.leadingWhitespace(a).isEmpty();
        List<String> tip = apiChoice.getTip();
        int size = tip.size();
        if (0 < size) {
            String str = tip.get(0);
            int lastIndexOf = a.lastIndexOf(10);
            String substring2 = a.substring(0, lastIndexOf == -1 ? a.length() : lastIndexOf);
            if (lastIndexOf == -1) {
                substring = a.substring(0);
                z = isEmpty;
            } else if (lastIndexOf != a.length()) {
                substring = a.substring(lastIndexOf + 1);
                z = isEmpty;
            } else {
                substring = "";
                z = isEmpty;
            }
            if (z && 0 == 0) {
                String stripLeading = AICodeStringUtil.stripLeading(str);
                if (!(lastIndexOf == -1 ? stripLeading.startsWith(substring2) : stripLeading.startsWith(substring))) {
                    return null;
                }
            } else {
                if (!(lastIndexOf == -1 ? str.startsWith(substring2.replaceAll(GenericUtils.H("\u000f\u0005��P"), "")) : str.startsWith(substring))) {
                    return null;
                }
            }
            if (lastIndexOf != -1) {
                if (!a.isBlank()) {
                    return null;
                }
                ArrayList arrayList = new ArrayList(size - 0);
                arrayList.add(str.substring(((isEmpty && 0 == 0) ? AICodeStringUtil.leadingWhitespace(str).length() : 0) + substring.length()));
                if (0 + 1 < size) {
                    arrayList.addAll(tip.subList(0 + 1, size));
                }
                return apiChoice.withCompletion(arrayList);
            }
            ArrayList arrayList2 = new ArrayList(size - 0);
            arrayList2.add(str.substring(((isEmpty && 0 == 0) ? AICodeStringUtil.leadingWhitespace(str).length() : 0) + substring2.replaceAll(NewFileUtils.H("w'xr"), "").length()));
            if (0 + 1 < size) {
                arrayList2.addAll(tip.subList(0 + 1, size));
            }
            return apiChoice.withCompletion(arrayList2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: sj */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/generate/SimpleCodeTipCache$Z.class */
    public static final class Z {

        /* renamed from: byte, reason: not valid java name */
        private final boolean f311byte;

        /* renamed from: enum, reason: not valid java name */
        private final String f312enum;

        public String toString() {
            return "SimpleCodeTipCache.CacheKey(promptHash=" + De() + ", isMultiline=" + Ie() + ")";
        }

        public Z(String a, boolean z) {
            this.f312enum = a;
            this.f311byte = z;
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public int hashCode() {
            int i = (1 * 59) + (Ie() ? 79 : 97);
            String De = De();
            return (i * 59) + (De == null ? 43 : De.hashCode());
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public boolean equals(Object a) {
            if (a == this) {
                return true;
            }
            if (!(a instanceof Z)) {
                return false;
            }
            Z a2 = (Z) a;
            if (Ie() == a2.Ie()) {
                String De = De();
                String De2 = a2.De();
                if (De == null) {
                    if (De2 != null) {
                        return false;
                    }
                } else if (!De.equals(De2)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        public boolean Ie() {
            return this.f311byte;
        }

        public String De() {
            return this.f312enum;
        }
    }

    private static String mF(@NotNull String prompt) {
        if (prompt == null) {
            m161enum(11);
        }
        return DigestUtil.sha256Hex(prompt.getBytes(StandardCharsets.UTF_8));
    }
}
