package com.aicode.inline.ide;

import com.aicode.inline.InlineChatInputPanel;
import com.aicode.inline.InlineChatPanel;
import com.aicode.inline.InlineChatService;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.util.JComponentKt;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import kotlin.NoWhenBranchMatchedException;
import org.jetbrains.annotations.NotNull;

/* compiled from: ui */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/ide/DefaultActionScopePredicateFactory.class */
public final class DefaultActionScopePredicateFactory implements PredicateFactory {

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final InlineChatService f412enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m201enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            default:
                H = CancelRequestTip.H("\u00047��\u0012\u001d\u0015\u0003\u0019K\r\u0005\u0018w\u0017)\b\u001f%\u0001\u0018\rA$5\u0004\u0017\f\u0004\u0014\u0005\u001fM@B\u0016BP\u001f\u0007Ag1K@\u0014G\u001d\u0005\u0018\u001fV\u0018\u0012\tA\u0003\u000fJ\u001f\u0004\u0005\u0005");
                i = a;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                do {
                } while (0 != 0);
                H = CancelRequestTip.H("!/; 8\u0003\r\r@\r\b\u0019\u000f\b\u0001EU\u0003OD1b\b\u0010\u0014\u0013P\u001e\u0004\u001fV\u0004\u0018\t\u0014\u0013\u0004J\u001f\u0004\u0005\u0005");
                i = a;
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
            case 4:
            case 5:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = CancelRequestTip.H("\u000e\u001e\u001c\u0002\u0005\u00135\u0015\u001c\u00152\u000f\u0018\u0007\u0018\n\f");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = CancelRequestTip.H("\u0019\u0012\u001e\u0019\f");
                i3 = a;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[0] = CancelRequestTip.H("\u000e\u0002\u0006D\u000b\u000348\u0003\u0002D\u0002\u001a\u0018\b\u000f1{\u001f\u0012\u0004N$\u0005\u000b\f\u0012\u000b\u0011$\u0013\u0004\b\u000e,\u0011\u0006\n\u0017\u0002 \u0002\u000e\u000f\u001f\u0015\u001c\t\u0004'\u000b\t\u0005\u001e\u001b\u0010");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            default:
                objArr[1] = CancelRequestTip.H("\u000e\u0002\u0006D\u000b\u000348\u0003\u0002D\u0002\u001a\u0018\b\u000f1{\u001f\u0012\u0004N$\u0005\u000b\f\u0012\u000b\u0011$\u0013\u0004\b\u000e,\u0011\u0006\n\u0017\u0002 \u0002\u000e\u000f\u001f\u0015\u001c\t\u0004'\u000b\t\u0005\u001e\u001b\u0010");
                i4 = a;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                do {
                } while (0 != 0);
                objArr[1] = CancelRequestTip.H("\r\u0013\u0004\u000e\u0003\u0012\u0010\u001d\f");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = CancelRequestTip.H("V\u0003\u001f\u0018\u001dW");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = CancelRequestTip.H("\r\u0013\u0004\u000e\u0003\u0012\u0010\u001d\f");
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            default:
                throw new IllegalArgumentException(format);
            case 2:
            case 3:
            case 4:
            case 5:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // com.aicode.inline.ide.PredicateFactory
    @NotNull
    public ConditionalEditorActionPredicate predicate(@NotNull ActionScope a) {
        if (a == null) {
            m201enum(1);
        }
        ConditionalEditorActionPredicate conditionalEditorActionPredicate = (a2, caret, dataContext) -> {
            return Sa(this, a2);
        };
        ConditionalEditorActionPredicate conditionalEditorActionPredicate2 = (a3, caret2, dataContext2) -> {
            return YA(this, a3);
        };
        ConditionalEditorActionPredicate conditionalEditorActionPredicate3 = (a4, caret3, dataContext3) -> {
            return mC(this, a4);
        };
        ConditionalEditorActionPredicate conditionalEditorActionPredicate4 = DefaultActionScopePredicateFactory::ha;
        switch (WhenMappings.enums[a.ordinal()]) {
            case 1:
                do {
                } while (0 != 0);
                if (conditionalEditorActionPredicate == null) {
                    m201enum(2);
                }
                return conditionalEditorActionPredicate;
            case 2:
                if (conditionalEditorActionPredicate2 == null) {
                    m201enum(3);
                }
                return conditionalEditorActionPredicate2;
            case 3:
                if (conditionalEditorActionPredicate3 == null) {
                    m201enum(4);
                }
                return conditionalEditorActionPredicate3;
            case 4:
                if (conditionalEditorActionPredicate4 == null) {
                    m201enum(5);
                }
                return conditionalEditorActionPredicate4;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public DefaultActionScopePredicateFactory(@NotNull InlineChatService a) {
        if (a == null) {
            m201enum(0);
        }
        this.f412enum = a;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static boolean YA(DefaultActionScopePredicateFactory a, Editor a2) {
        InlineChatPanel inlineChat;
        if (a2 != null && (inlineChat = a.f412enum.getInlineChat(a2)) != null) {
            return JComponentKt.isChildFocused(inlineChat);
        }
        return false;
    }

    private static boolean ha(Editor editor, Caret caret, DataContext dataContext) {
        return true;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static boolean mC(DefaultActionScopePredicateFactory a, Editor a2) {
        return (a2 == null || a.f412enum.getInlineChat(a2) == null) ? false : true;
    }

    /* compiled from: ui */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/ide/DefaultActionScopePredicateFactory$WhenMappings.class */
    public static class WhenMappings {
        public static final int[] enums;

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        static {
            int[] iArr;
            int[] iArr2;
            int[] iArr3;
            int[] iArr4;
            int[] iArr5 = new int[ActionScope.values().length];
            try {
                iArr5[ActionScope.INPUT_FOCUSED.ordinal()] = 1;
                iArr = iArr5;
            } catch (NoSuchFieldError unused) {
                iArr = iArr5;
            }
            try {
                iArr[ActionScope.INLINE_CHAT_FOCUSED.ordinal()] = 2;
                iArr2 = iArr5;
            } catch (NoSuchFieldError unused2) {
                iArr2 = iArr5;
            }
            try {
                iArr2[ActionScope.INLINE_CHAT_OPENED.ordinal()] = 3;
                iArr3 = iArr5;
            } catch (NoSuchFieldError unused3) {
                iArr3 = iArr5;
            }
            try {
                iArr3[ActionScope.ALWAYS.ordinal()] = 4;
                iArr4 = iArr5;
            } catch (NoSuchFieldError unused4) {
                iArr4 = iArr5;
            }
            enums = iArr4;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static boolean Sa(DefaultActionScopePredicateFactory a, Editor a2) {
        InlineChatInputPanel chatInputPanel;
        if (a2 != null) {
            InlineChatPanel inlineChat = a.f412enum.getInlineChat(a2);
            if (inlineChat != null && (chatInputPanel = inlineChat.getChatInputPanel()) != null) {
                return chatInputPanel.getInputComponent().hasFocus();
            }
            return false;
        }
        return false;
    }
}
