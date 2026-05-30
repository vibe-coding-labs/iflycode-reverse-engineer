package com.aicode.util;

import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

/* compiled from: pb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/FileSizeUtil.class */
public final class FileSizeUtil {
    public static final /* synthetic */ Key<Boolean> KEY_TOO_LARGE = Key.create(JComponentKt.H("\"\t%\u007fR*G&\u000f)\u0006\u0016\u000b*/9\fuQ"));

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ boolean isSupported(@Nullable VirtualFile file) {
        return (file == null || file.getLength() > 1048576 || KEY_TOO_LARGE.isIn(file)) ? false : true;
    }
}
