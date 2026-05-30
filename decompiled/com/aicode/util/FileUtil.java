package com.aicode.util;

import com.aicode.agent.service.RecentFilesManager;
import com.aicode.inline.ide.IdeAction;
import com.google.gson.JsonArray;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: wb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/FileUtil.class */
public class FileUtil {

    /* renamed from: enum, reason: not valid java name */
    private static final /* synthetic */ ConcurrentHashMap<String, List<String>> f676enum = new ConcurrentHashMap<>();

    /* renamed from: byte, reason: not valid java name */
    private static final /* synthetic */ ConcurrentHashMap<String, Long> f675byte = new ConcurrentHashMap<>();

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ List<String> getSourceCodeDirectories(Project a) {
        long currentTimeMillis = System.currentTimeMillis();
        if (f675byte.containsKey(a.getBasePath()) && currentTimeMillis - f675byte.get(a.getBasePath()).longValue() <= 1800000 && f676enum.containsKey(a.getBasePath())) {
            return f676enum.get(a.getBasePath());
        }
        List<String> findSourceCodeDirectories = findSourceCodeDirectories(a.getBasePath());
        f675byte.put((String) Objects.requireNonNull(a.getBasePath()), Long.valueOf(currentTimeMillis));
        f676enum.put((String) Objects.requireNonNull(a.getBasePath()), findSourceCodeDirectories);
        return findSourceCodeDirectories;
    }

    public static /* synthetic */ String currentOpenFile(Project a) {
        String str = null;
        VirtualFile virtualFile = AICodeUtils.getVirtualFile(a);
        if (virtualFile != null && !FileTypeManager.getInstance().getFileTypeByFile(virtualFile).isBinary()) {
            str = virtualFile.getPath();
            RecentFilesManager.fileOpened(a, virtualFile.getPath());
        }
        return str;
    }

    public static /* synthetic */ List<String> findSourceCodeDirectories(String a) {
        ArrayList arrayList = new ArrayList();
        M(new File(a), arrayList);
        return arrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static /* synthetic */ void M(File a, List<String> list) {
        File[] listFiles = a.listFiles();
        if (listFiles == null) {
            return;
        }
        int length = listFiles.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            File a2 = listFiles[i2];
            if (a2.isDirectory() && a2.getName().equals(IdeAction.H("[\u0014N")) && r(a2)) {
                list.add(a2.getParent());
            } else if (a2.isDirectory()) {
                M(a2, list);
            }
            i2++;
            i = i2;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static /* synthetic */ boolean r(File a) {
        File[] listFiles;
        File[] listFiles2 = a.listFiles();
        if (listFiles2 != null) {
            int length = listFiles2.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                File a2 = listFiles2[i2];
                if (a2.isDirectory() && a2.getName().equals(NewFileUtils.H("O\u0011U��")) && (listFiles = a2.listFiles()) != null) {
                    int length2 = listFiles.length;
                    int i3 = 0;
                    int i4 = 0;
                    while (i3 < length2) {
                        File file = listFiles[i4];
                        if (!file.isDirectory() || !file.getName().equals(IdeAction.H("\tI\u0010L"))) {
                            i4++;
                            i3 = i4;
                        } else {
                            return true;
                        }
                    }
                }
                i2++;
                i = i2;
            }
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ JsonArray openFileList(Project a) {
        JsonArray jsonArray = new JsonArray();
        Iterator<String> it = RecentFilesManager.getRecentFiles(a).iterator();
        int i = 0;
        while (it.hasNext() && i < 10) {
            i++;
            jsonArray.add(it.next());
        }
        return jsonArray;
    }
}
