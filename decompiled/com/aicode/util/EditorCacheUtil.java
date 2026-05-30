package com.aicode.util;

import com.aicode.agent.service.GitReviewService;
import com.aicode.content.util.EditorUtils;
import com.aicode.inline.dto.LastChatQuestionInfo;
import com.aicode.inline.dto.LastSelectionTextCache;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Key;

/* compiled from: bb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/EditorCacheUtil.class */
public class EditorCacheUtil {

    /* renamed from: enum, reason: not valid java name */
    private static final /* synthetic */ Key<LastChatQuestionInfo> f674enum = Key.create(GitReviewService.H("\u000e\b1k\u001f.\u001f#\u0013$T\u0014.9\u001f\"\u0004f\u001avO.\u0012-].\u001e+\u0005c\u00151\brD"));

    /* renamed from: byte, reason: not valid java name */
    private static final /* synthetic */ Key<Boolean> f673byte = Key.create(EditorUtils.H("e2jr{(|+9c\u0018\bs%n.uru)m3^\u000e<*x*ons)O\t)?{\"w!"));
    public static final /* synthetic */ Key<LastSelectionTextCache> LAST_SELECTION_TEXT_CACHE_KEY = Key.create(GitReviewService.H("9/\u0016c\u00179\b$\u00143C\u00028,\n(\u000eb\u001e/\u0016\u0018$5E.\u001e)\u00076P\"\u0010-\u00109\u001f%\u001fc\u00151\brD"));
    public static final /* synthetic */ Key<LastSelectionTextCache> ORIGINAL_SELECTION_TEXT_CACHE_KEY = Key.create(EditorUtils.H("y.}e}.t#r(scb4s3hov*1oX\b9/r sro\"m?S\u001f{&~et2\u007f*O\u0013)?{\"w!"));

    public static /* synthetic */ Boolean getEditCache(Editor a) {
        return (Boolean) a.getUserData(f673byte);
    }

    public static /* synthetic */ void setCache(Editor a, int a2, String a3, boolean z) {
        a.putUserData(f674enum, new LastChatQuestionInfo(a2, a3, z));
    }

    public static /* synthetic */ LastChatQuestionInfo getCache(Editor a) {
        return (LastChatQuestionInfo) a.getUserData(f674enum);
    }
}
