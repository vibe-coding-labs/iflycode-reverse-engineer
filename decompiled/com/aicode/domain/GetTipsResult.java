package com.aicode.domain;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/domain/GetTipsResult.class */
public class GetTipsResult {

    @SerializedName("tips")
    @NotNull
    List<Tip> tips;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 2:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 1:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            case 2:
            default:
                i2 = 3;
                break;
            case 1:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            default:
                objArr[0] = "completions";
                break;
            case 1:
                objArr[0] = "com/aicode/domain/GetTipsResult";
                break;
            case 2:
                objArr[0] = "tips";
                break;
        }
        switch (i) {
            case 0:
            case 2:
            default:
                objArr[1] = "com/aicode/domain/GetTipsResult";
                break;
            case 1:
                objArr[1] = "getTips";
                break;
        }
        switch (i) {
            case 0:
            default:
                objArr[2] = "<init>";
                break;
            case 1:
                break;
            case 2:
                objArr[2] = "setTips";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 2:
            default:
                throw new IllegalArgumentException(format);
            case 1:
                throw new IllegalStateException(format);
        }
    }

    public GetTipsResult(@NotNull List<Tip> completions) {
        if (completions == null) {
            $$$reportNull$$$0(0);
        }
        this.tips = completions;
    }

    @NotNull
    public List<Tip> getTips() {
        List<Tip> list = this.tips;
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        return list;
    }

    public void setTips(@NotNull List<Tip> tips) {
        if (tips == null) {
            $$$reportNull$$$0(2);
        }
        this.tips = tips;
    }

    protected boolean canEqual(Object other) {
        return other instanceof GetTipsResult;
    }

    public String toString() {
        return "GetTipsResult(tips=" + getTips() + ")";
    }

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/domain/GetTipsResult$Tip.class */
    public static final class Tip {

        @SerializedName("uuid")
        @NotNull
        private final String uuid;

        @SerializedName("text")
        @NotNull
        private final String text;

        @SerializedName("range")
        @NotNull
        private final Range range;

        @SerializedName("displayText")
        @NotNull
        private final String displayText;

        @SerializedName("position")
        @NotNull
        private final Position position;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str;
            int i2;
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                default:
                    i2 = 3;
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    i2 = 2;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 0:
                default:
                    objArr[0] = "uuid";
                    break;
                case 1:
                    objArr[0] = "text";
                    break;
                case 2:
                    objArr[0] = "range";
                    break;
                case 3:
                    objArr[0] = "displayText";
                    break;
                case 4:
                    objArr[0] = "position";
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    objArr[0] = "com/aicode/domain/GetTipsResult$Tip";
                    break;
            }
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                default:
                    objArr[1] = "com/aicode/domain/GetTipsResult$Tip";
                    break;
                case 5:
                    objArr[1] = "getUuid";
                    break;
                case 6:
                    objArr[1] = "getText";
                    break;
                case 7:
                    objArr[1] = "getRange";
                    break;
                case 8:
                    objArr[1] = "getDisplayText";
                    break;
                case 9:
                    objArr[1] = "getPosition";
                    break;
            }
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                default:
                    objArr[2] = "<init>";
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                default:
                    throw new IllegalArgumentException(format);
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    throw new IllegalStateException(format);
            }
        }

        public Tip(@NotNull String uuid, @NotNull String text, @NotNull Range range, @NotNull String displayText, @NotNull Position position) {
            if (uuid == null) {
                $$$reportNull$$$0(0);
            }
            if (text == null) {
                $$$reportNull$$$0(1);
            }
            if (range == null) {
                $$$reportNull$$$0(2);
            }
            if (displayText == null) {
                $$$reportNull$$$0(3);
            }
            if (position == null) {
                $$$reportNull$$$0(4);
            }
            this.uuid = uuid;
            this.text = text;
            this.range = range;
            this.displayText = displayText;
            this.position = position;
        }

        @NotNull
        public String getUuid() {
            String str = this.uuid;
            if (str == null) {
                $$$reportNull$$$0(5);
            }
            return str;
        }

        @NotNull
        public String getText() {
            String str = this.text;
            if (str == null) {
                $$$reportNull$$$0(6);
            }
            return str;
        }

        @NotNull
        public Range getRange() {
            Range range = this.range;
            if (range == null) {
                $$$reportNull$$$0(7);
            }
            return range;
        }

        @NotNull
        public String getDisplayText() {
            String str = this.displayText;
            if (str == null) {
                $$$reportNull$$$0(8);
            }
            return str;
        }

        @NotNull
        public Position getPosition() {
            Position position = this.position;
            if (position == null) {
                $$$reportNull$$$0(9);
            }
            return position;
        }

        public String toString() {
            String uuid = getUuid();
            return "GetTipsResult.Tip(uuid=" + uuid + ", text=" + getText() + ", range=" + getRange() + ", displayText=" + getDisplayText() + ", position=" + getPosition() + ")";
        }
    }
}
