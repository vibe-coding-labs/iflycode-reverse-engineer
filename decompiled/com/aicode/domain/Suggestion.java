package com.aicode.domain;

import com.aicode.service.CodeInlayList;
import org.jetbrains.annotations.NotNull;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/domain/Suggestion.class */
public final class Suggestion {
    private final int score;
    private final String type;

    @NotNull
    private final String hash;

    @NotNull
    private final CodeInlayList inlays;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 1:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 2:
            case 3:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            default:
                i2 = 3;
                break;
            case 2:
            case 3:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            default:
                objArr[0] = "hash";
                break;
            case 1:
                objArr[0] = "inlays";
                break;
            case 2:
            case 3:
                objArr[0] = "com/aicode/domain/Suggestion";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            default:
                objArr[1] = "com/aicode/domain/Suggestion";
                break;
            case 2:
                objArr[1] = "getHash";
                break;
            case 3:
                objArr[1] = "getInlays";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            default:
                objArr[2] = "<init>";
                break;
            case 2:
            case 3:
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 1:
            default:
                throw new IllegalArgumentException(format);
            case 2:
            case 3:
                throw new IllegalStateException(format);
        }
    }

    public Suggestion(int score, String type, @NotNull String hash, @NotNull CodeInlayList inlays) {
        if (hash == null) {
            $$$reportNull$$$0(0);
        }
        if (inlays == null) {
            $$$reportNull$$$0(1);
        }
        this.score = score;
        this.type = type;
        this.hash = hash;
        this.inlays = inlays;
    }

    public int getScore() {
        return this.score;
    }

    public String getType() {
        return this.type;
    }

    @NotNull
    public String getHash() {
        String str = this.hash;
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return str;
    }

    @NotNull
    public CodeInlayList getInlays() {
        CodeInlayList codeInlayList = this.inlays;
        if (codeInlayList == null) {
            $$$reportNull$$$0(3);
        }
        return codeInlayList;
    }

    public String toString() {
        return "Suggestion{score=" + this.score + ", type='" + this.type + "', hash='" + this.hash + "', inlays=" + this.inlays + "}";
    }
}
