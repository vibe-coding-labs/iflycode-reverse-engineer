/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.intellij.openapi.project.Project
 */
package com.aicode.agent.service;

import com.aicode.util.StringUtils;
import com.google.gson.JsonArray;
import com.intellij.openapi.project.Project;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class RecentFilesManager {
    public static final Map<Project, Deque<String>> recentFilesMap = new ConcurrentHashMap<Project, Deque<String>>();
    private static final int enum = 20;

    /*
     * WARNING - void declaration
     */
    public static void fileOpened(Project project, String string) {
        void a;
        Object a2;
        Project project2 = project;
        Object object = a2 = recentFilesMap.containsKey(project2) ? recentFilesMap.get(project2) : new ArrayDeque();
        a2.remove(a);
        object.addFirst(a);
        if (object.size() > 20) {
            a2.removeLast();
        }
        recentFilesMap.put(project2, (Deque<String>)a2);
    }

    public static JsonArray getRecentFileDirs(Project project) {
        Object a;
        String string;
        Iterator<String> iterator;
        Project project2 = project;
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        Iterator<String> iterator2 = iterator = RecentFilesManager.getRecentFiles(project2).iterator();
        while (iterator2.hasNext()) {
            string = iterator.next();
            a = new File(string);
            if (!((File)a).exists()) {
                iterator2 = iterator;
                continue;
            }
            String string2 = ((File)a).getParent();
            if (StringUtils.isBlank((CharSequence)string2)) {
                iterator2 = iterator;
                continue;
            }
            linkedHashSet.add(string2);
            iterator2 = iterator;
        }
        string = new JsonArray();
        a = linkedHashSet.iterator();
        int n = 0;
        Object object = a;
        while (object.hasNext()) {
            if (n >= 5) break;
            ++n;
            string.add((String)a.next());
            object = a;
        }
        return string;
    }

    public static Deque<String> getRecentFiles(Project a) {
        if (recentFilesMap.containsKey(a)) {
            return recentFilesMap.get(a);
        }
        return new ArrayDeque<String>();
    }

    public RecentFilesManager() {
        RecentFilesManager a;
    }
}
