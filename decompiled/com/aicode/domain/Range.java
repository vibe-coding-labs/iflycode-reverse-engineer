package com.aicode.domain;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/domain/Range.class */
public class Range {

    @SerializedName("start")
    @NotNull
    Position start;

    @SerializedName("end")
    @NotNull
    Position end;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 4:
            case 5:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            default:
                i2 = 3;
                break;
            case 4:
            case 5:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            case 2:
            case 6:
            default:
                objArr[0] = "start";
                break;
            case 1:
            case 3:
            case 7:
                objArr[0] = "end";
                break;
            case 4:
            case 5:
                objArr[0] = "com/aicode/domain/Range";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            default:
                objArr[1] = "com/aicode/domain/Range";
                break;
            case 4:
                objArr[1] = "getStart";
                break;
            case 5:
                objArr[1] = "getEnd";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            default:
                objArr[2] = "of";
                break;
            case 2:
            case 3:
                objArr[2] = "<init>";
                break;
            case 4:
            case 5:
                break;
            case 6:
                objArr[2] = "setStart";
                break;
            case 7:
                objArr[2] = "setEnd";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            default:
                throw new IllegalArgumentException(format);
            case 4:
            case 5:
                throw new IllegalStateException(format);
        }
    }

    @NotNull
    public static Range of(@NotNull Position start, @NotNull Position end) {
        if (start == null) {
            $$$reportNull$$$0(0);
        }
        if (end == null) {
            $$$reportNull$$$0(1);
        }
        return new Range(start, end);
    }

    public Range(@NotNull Position start, @NotNull Position end) {
        if (start == null) {
            $$$reportNull$$$0(2);
        }
        if (end == null) {
            $$$reportNull$$$0(3);
        }
        this.start = start;
        this.end = end;
    }

    @NotNull
    public Position getStart() {
        Position position = this.start;
        if (position == null) {
            $$$reportNull$$$0(4);
        }
        return position;
    }

    @NotNull
    public Position getEnd() {
        Position position = this.end;
        if (position == null) {
            $$$reportNull$$$0(5);
        }
        return position;
    }

    public void setStart(@NotNull Position start) {
        if (start == null) {
            $$$reportNull$$$0(6);
        }
        this.start = start;
    }

    public void setEnd(@NotNull Position end) {
        if (end == null) {
            $$$reportNull$$$0(7);
        }
        this.end = end;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Range;
    }

    public String toString() {
        Position start = getStart();
        return "Range(start=" + start + ", end=" + getEnd() + ")";
    }
}
