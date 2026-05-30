package com.aicode.enums;

import com.aicode.icons.Icons;
import com.aicode.ui.FontKt;
import com.aicode.util.Maps;
import com.aicode.util.MessageBundle;
import com.intellij.util.ui.PresentableEnum;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;

/* compiled from: sm */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/AICodeStatus.class */
public enum AICodeStatus implements PresentableEnum {
    Ready,
    NotSignedIn,
    CompletionInProgress,
    AgentBroken,
    IncompatibleClient,
    Unsupported,
    UnknownError;

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m124enum(int a) {
        throw new IllegalStateException(String.format(Maps.H("/\u001d\u001b<\"%\u001e\"\u000f~%\b \u001b&^K!K|\u0017x?\u001b\u0006=@2\u001a=T:\u0017:\u0011*\u0001s\u001a=��<"), FontKt.H("\u0001\u001b\u0005w%:,=*\u0001W&1#'0p\u0003\u0017\u000776+\n1#*/5"), Maps.H("?\n'=+\u0003>")));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public String getPresentableText() {
        switch (this) {
            case NotSignedIn:
                do {
                } while (0 != 0);
                return " " + MessageBundle.get(FontKt.H(">?):->i<!\u0017Q!'(=&;"));
            case Ready:
                return "";
            case CompletionInProgress:
                return MessageBundle.get(Maps.H("(\t?\f;\b\u007f\u0016=$\u001c\u0001+\r,\u001b."));
            case AgentBroken:
                return FontKt.H("557/7=r<<67>$>u��\u001f\u0002r:\f_4'7s*+");
            case IncompatibleClient:
                return Maps.H("\u0002\"\u00019\u001c6U<\u00108\u0002+\bq\u001000I\u00144\f\"\u001c'");
            case Unsupported:
                return FontKt.H("+\u000f\u001ee\u0010\u0016\u000bd6=:y66/&%'={3:'\u0010_\"\":4*1");
            case UnknownError:
                return Maps.H("\n\u0003:\n7\"\u0007D=\u000b7\u001a;");
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    public Icon getIcon() {
        switch (this) {
            case NotSignedIn:
                Icon icon = Icons.StatusBarIconNotSignedIn;
                do {
                } while (0 != 0);
                if (icon == null) {
                    throw new RuntimeException();
                }
                if (icon == null) {
                    m124enum(0);
                }
                return icon;
            case Ready:
                Icon icon2 = Icons.StatusBarIcon;
                if (icon2 != null) {
                    if (icon2 == null) {
                        m124enum(1);
                    }
                    return icon2;
                }
                throw new RuntimeException();
            case CompletionInProgress:
                Icon icon3 = Icons.StatusBarCompletionInProgress;
                if (icon3 != null) {
                    if (icon3 == null) {
                        m124enum(2);
                    }
                    return icon3;
                }
                throw new RuntimeException();
            default:
                Icon icon4 = Icons.StatusBarIconError;
                if (icon4 != null) {
                    if (icon4 == null) {
                        m124enum(3);
                    }
                    return icon4;
                }
                throw new RuntimeException();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public boolean isIconAlwaysShown() {
        return (this == Ready || this == NotSignedIn || this == CompletionInProgress) ? false : true;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public boolean isDisablingClientRequests() {
        return this == IncompatibleClient || this == AgentBroken;
    }
}
