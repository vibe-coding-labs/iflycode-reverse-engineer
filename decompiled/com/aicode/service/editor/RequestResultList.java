/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.aicode.service.editor;

import com.aicode.action.batch.GeneratorConfig;
import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.service.CodeInlayList;
import com.aicode.service.EditorRequestService;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class RequestResultList {
    public final Object case;
    public final EditorRequestService final;
    public boolean try;
    public int float;
    public final ObjectLinkedOpenHashSet<CodeInlayList> byte;
    public int enum;

    public void setIndex(int n) {
        int a = n;
        RequestResultList a2 = this;
        a2.float = a;
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray;
        Object[] objectArray2;
        String string = GeneratorConfig.H("/\n\u001e\u001a\u0010\u000e\f\u0000H\u0018\u0001\n\u0005s \u0017\u001b7\u0017\u0018\u0013I\u0014\u0013\u000b\u000eHV-*\u001cX]I\rON\u0017\u001eNZ\u001awj\u001dX\u0015\u001b\r\u001cG\u001f\u0010\u001dg3\u0007T\u0016\u001b\u0013\u0005");
        Object[] objectArray3 = new Object[3];
        switch (a) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = ConditionalActionConfiguration.H("\u000f\u0016\u0007\u001d\b\u0003\u0001");
                break;
            }
            case 1: 
            case 2: {
                objectArray2 = objectArray3;
                while (false) {
                }
                objectArray3[0] = GeneratorConfig.H("\u000b\u001a\u0014\u000f\u0006\u001a");
                break;
            }
        }
        objectArray2[1] = ConditionalActionConfiguration.H("U 'W\u001c\u0005\n\u0007\t\u001dR\u001d\u000e\u001b\u001a&)\u001dR\u000b\u000f\u0001\u0019\u001e\u0006F>4%\u0001\u0014\u001d\u001f;\t\u000b\b\u001f\u0002$\u0004\u0003\u0001");
        switch (a) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = GeneratorConfig.H("^\u001d\u0016\u0007\u000bW");
                break;
            }
            case 1: {
                objectArray = objectArray2;
                while (false) {
                }
                objectArray2[2] = ConditionalActionConfiguration.H("\r\u001c\u0019:\u0018\u0004\f\t\u0006");
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[2] = GeneratorConfig.H("\u001f\u000b\n)\u00138\u0011\r\").\u001b\u001b\u0005\u001a\r");
                break;
            }
        }
        throw new IllegalArgumentException(String.format(string, objectArray));
    }

    public void setHasOnDemandCodeTips(boolean bl) {
        boolean a = bl;
        RequestResultList a2 = this;
        a2.try = a;
    }

    public RequestResultList(@NotNull EditorRequestService editorRequestService) {
        RequestResultList requestResultList = editorRequestService2;
        EditorRequestService editorRequestService2 = editorRequestService;
        RequestResultList a = requestResultList;
        if (editorRequestService2 == null) {
            RequestResultList.enum(0);
        }
        RequestResultList requestResultList2 = a;
        RequestResultList requestResultList3 = a;
        a.case = new Object();
        RequestResultList requestResultList4 = a;
        requestResultList3.byte = new ObjectLinkedOpenHashSet();
        requestResultList2.float = 0;
        requestResultList2.enum = -1;
        a.final = editorRequestService2;
    }

    public void setMaxShownIndex(int n) {
        int a = n;
        RequestResultList a2 = this;
        a2.enum = a;
    }

    public Object getInlayLock() {
        RequestResultList a;
        return a.case;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean hasPrev() {
        RequestResultList requestResultList = this;
        Object a = requestResultList.case;
        synchronized (a) {
            Object object;
            boolean bl;
            if (requestResultList.byte.size() > 1) {
                bl = true;
                object = a;
            } else {
                bl = false;
                object = a;
            }
            // ** MonitorExit[v1] (shouldn't be in output)
            return bl;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean hasNext() {
        RequestResultList requestResultList = this;
        Object a = requestResultList.case;
        synchronized (a) {
            Object object;
            boolean bl;
            if (requestResultList.byte.size() > 1) {
                bl = true;
                object = a;
            } else {
                bl = false;
                object = a;
            }
            // ** MonitorExit[v1] (shouldn't be in output)
            return bl;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public CodeInlayList getPrevCodeTip() {
        RequestResultList requestResultList = this;
        Object a = requestResultList.case;
        synchronized (a) {
            int n;
            block5: {
                n = requestResultList.byte.size();
                if (n > 1) break block5;
                requestResultList.float = 0;
                return null;
            }
            RequestResultList requestResultList2 = requestResultList;
            --requestResultList2.float;
            if (requestResultList2.float < 0) {
                requestResultList.float = n - 1;
            }
            RequestResultList requestResultList3 = requestResultList;
            return RequestResultList.TB(requestResultList3.byte, requestResultList3.float);
        }
    }

    public static String H(Object object) {
        int a;
        Object object2 = object;
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String string = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        object2 = (String)object2;
        int n = ((String)object2).length();
        int n2 = n - 1;
        char[] cArray = new char[n];
        int n3 = (2 ^ 5) << 4 ^ 2 << 1;
        int cfr_ignored_0 = (2 ^ 5) << 4 ^ (2 ^ 5);
        int n4 = 4 << 4 ^ 3 << 1;
        int n5 = a = string.length() - 1;
        int n6 = n2;
        String string2 = string;
        while (n6 >= 0) {
            int n7 = n2--;
            cArray[n7] = (char)(n4 ^ (((String)object2).charAt(n7) ^ string2.charAt(a)));
            if (n2 < 0) break;
            int n8 = n2--;
            char c = cArray[n8] = (char)(n3 ^ (((String)object2).charAt(n8) ^ string2.charAt(a)));
            if (--a < 0) {
                a = n5;
            }
            n6 = n2;
        }
        return new String(cArray);
    }

    public int getIndex() {
        RequestResultList a;
        return a.float;
    }

    public EditorRequestService getRequest() {
        RequestResultList a;
        return a.final;
    }

    public boolean canEqual(Object object) {
        Object a = object;
        RequestResultList a2 = this;
        return a instanceof RequestResultList;
    }

    public String toString() {
        RequestResultList a;
        return "EditorRequestResultList(request=" + a.getRequest() + ", inlayLock=" + a.getInlayLock() + ", inlayLists=" + a.getInlayLists() + ", index=" + a.getIndex() + ", maxShownIndex=" + a.getMaxShownIndex() + ", hasOnDemandCodeTips=" + a.isHasOnDemandCodeTips() + ")";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public CodeInlayList getCurrentCodeTip() {
        RequestResultList requestResultList = this;
        Object a = requestResultList.case;
        synchronized (a) {
            RequestResultList requestResultList2 = requestResultList;
            return RequestResultList.TB(requestResultList2.byte, requestResultList2.float);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public void addInlays(@NotNull CodeInlayList codeInlayList) {
        void a;
        RequestResultList requestResultList = object;
        if (a == null) {
            RequestResultList.enum(1);
        }
        Object object = requestResultList.case;
        synchronized (object) {
            requestResultList.byte.add((Object)a);
            requestResultList.enum = Math.max(0, requestResultList.enum);
            return;
        }
    }

    public int getMaxShownIndex() {
        RequestResultList a;
        return a.enum;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setHasOnDemandCodeTips() {
        RequestResultList requestResultList = this;
        Object a = requestResultList.case;
        synchronized (a) {
            requestResultList.try = true;
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean hasOnDemandCodeTips() {
        Object object;
        boolean bl;
        RequestResultList requestResultList = this;
        Object a = requestResultList.case;
        // MONITORENTER : a
        if (!requestResultList.try) {
            if (requestResultList.byte.size() <= 1) {
                bl = false;
                object = a;
                // MONITOREXIT : object
                return bl;
            }
        }
        bl = true;
        object = a;
        return bl;
    }

    public boolean isHasOnDemandCodeTips() {
        RequestResultList a;
        return a.try;
    }

    @Nullable
    private static CodeInlayList TB(@NotNull ObjectSortedSet<CodeInlayList> objectSortedSet, int n) {
        int inlays = n;
        ObjectSortedSet<CodeInlayList> index = objectSortedSet;
        if (index == null) {
            RequestResultList.enum(2);
        }
        return index.stream().skip(inlays).findFirst().orElse(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resetInlays() {
        RequestResultList requestResultList = this;
        Object a = requestResultList.case;
        synchronized (a) {
            requestResultList.byte.clear();
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public CodeInlayList getNextCodeTip() {
        RequestResultList requestResultList = this;
        Object a = requestResultList.case;
        synchronized (a) {
            int n;
            block5: {
                n = requestResultList.byte.size();
                if (n > 1) break block5;
                requestResultList.float = 0;
                return null;
            }
            RequestResultList requestResultList2 = requestResultList;
            ++requestResultList2.float;
            if (requestResultList2.float >= n) {
                requestResultList.float = 0;
            }
            RequestResultList requestResultList3 = requestResultList;
            requestResultList.enum = Math.max(requestResultList3.enum, requestResultList.float);
            return RequestResultList.TB(requestResultList3.byte, requestResultList.float);
        }
    }

    public ObjectLinkedOpenHashSet<CodeInlayList> getInlayLists() {
        RequestResultList a;
        return a.byte;
    }
}
