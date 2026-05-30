package com.aicode.util;

import com.aicode.content.util.EditorUtils;
import com.aicode.inline.controller.ChatInputController;
import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.language.AICodeLanguageInfo;
import com.aicode.service.editor.RequestResultList;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: jb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/HighlighterUtil.class */
public class HighlighterUtil {
    public static final /* synthetic */ Key<List<RangeHighlighter>> HIGH_LIGHTER = Key.create(ConditionalActionConfiguration.H("<\u001d\u0016\u001b\u001a\u001d\u0016\u0001\u0018FT"));

    /* compiled from: jb */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/HighlighterUtil$EditorBranchRange.class */
    public static class EditorBranchRange {

        /* renamed from: final, reason: not valid java name */
        private /* synthetic */ boolean f686final;

        /* renamed from: try, reason: not valid java name */
        private /* synthetic */ boolean f687try;

        /* renamed from: float, reason: not valid java name */
        private /* synthetic */ int f688float;

        /* renamed from: byte, reason: not valid java name */
        private /* synthetic */ boolean f689byte;

        /* renamed from: enum, reason: not valid java name */
        private /* synthetic */ int f690enum;

        public /* synthetic */ EditorBranchRange(int a, int a2, boolean z, boolean z2, boolean z3) {
            this.f690enum = a;
            this.f688float = a2;
            this.f687try = z;
            this.f686final = z2;
            this.f689byte = z3;
        }

        public /* synthetic */ boolean isRoot() {
            return this.f689byte;
        }

        public /* synthetic */ boolean isOut() {
            return this.f686final;
        }

        public /* synthetic */ boolean isResult() {
            return this.f687try;
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public /* synthetic */ boolean equals(Object a) {
            if (a == this) {
                return true;
            }
            if (!(a instanceof EditorBranchRange)) {
                return false;
            }
            EditorBranchRange a2 = (EditorBranchRange) a;
            if (a2.canEqual(this) && getStartOffset() == a2.getStartOffset() && getEndOffset() == a2.getEndOffset() && isResult() == a2.isResult() && isOut() == a2.isOut() && isRoot() == a2.isRoot()) {
                return true;
            }
            return false;
        }

        public /* synthetic */ String toString() {
            return "HighlighterUtil.EditorBranchRange(startOffset=" + getStartOffset() + ", endOffset=" + getEndOffset() + ", result=" + isResult() + ", out=" + isOut() + ", root=" + isRoot() + ")";
        }

        public /* synthetic */ boolean canEqual(Object a) {
            return a instanceof EditorBranchRange;
        }

        public /* synthetic */ void setRoot(boolean z) {
            this.f689byte = z;
        }

        public /* synthetic */ void setOut(boolean z) {
            this.f686final = z;
        }

        public /* synthetic */ void setResult(boolean z) {
            this.f687try = z;
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public /* synthetic */ int hashCode() {
            return (((((((((1 * 59) + getStartOffset()) * 59) + getEndOffset()) * 59) + (isResult() ? 79 : 97)) * 59) + (isOut() ? 79 : 97)) * 59) + (isRoot() ? 79 : 97);
        }

        public /* synthetic */ void setStartOffset(int a) {
            this.f690enum = a;
        }

        public /* synthetic */ void setEndOffset(int a) {
            this.f688float = a;
        }

        public /* synthetic */ int getEndOffset() {
            return this.f688float;
        }

        public /* synthetic */ int getStartOffset() {
            return this.f690enum;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static /* synthetic */ void highlightText(final Editor a, List<EditorBranchRange> list) {
        List<EditorBranchRange> list2;
        int i;
        Color color = EditorColorsManager.getInstance().getGlobalScheme().getColor(EditorColors.CARET_ROW_COLOR);
        TextAttributes textAttributes = new TextAttributes(new Color(76, 169, 76), color, (Color) null, (EffectType) null, 0);
        TextAttributes textAttributes2 = new TextAttributes(Color.gray, color, (Color) null, (EffectType) null, 0);
        final MarkupModel markupModel = a.getMarkupModel();
        List list3 = (List) a.getUserData(HIGH_LIGHTER);
        List list4 = list3;
        if (CollectionUtils.isEmpty(list3)) {
            list4 = new ArrayList();
            list2 = list;
        } else {
            Objects.requireNonNull(markupModel);
            list4.forEach(markupModel::removeHighlighter);
            list2 = list;
        }
        EditorBranchRange orElse = list2.stream().filter(a2 -> {
            return a2.f689byte;
        }).findFirst().orElse(null);
        if (orElse == null) {
            return;
        }
        int orElse2 = list.stream().filter(a3 -> {
            return !a3.f689byte;
        }).mapToInt((v0) -> {
            return v0.getEndOffset();
        }).max().orElse(orElse.getEndOffset());
        int i2 = orElse2;
        EditorBranchRange orElse3 = list.stream().filter(a4 -> {
            return a4.getEndOffset() == orElse2 && !a4.f689byte;
        }).findFirst().orElse(null);
        if (orElse3 != null) {
            if (orElse3.f687try && orElse3.f686final) {
                if (orElse.getEndOffset() > i2) {
                    boolean z = false;
                    list.add(new EditorBranchRange(i2, orElse.f688float, z, false, z));
                    list.removeIf(a5 -> {
                        return a5.getEndOffset() > orElse2;
                    });
                    i = i2;
                    if (i == orElse.getEndOffset() && list.stream().noneMatch(a6 -> {
                        return !a6.f687try && a6.f686final;
                    })) {
                        list4.add(markupModel.addRangeHighlighter(orElse.getStartOffset(), i2, 4999, textAttributes, HighlighterTargetArea.EXACT_RANGE));
                    }
                    if (list.stream().anyMatch(a7 -> {
                        return !a7.f689byte && a7.f687try;
                    })) {
                        list4.add(markupModel.addRangeHighlighter(orElse.getStartOffset(), i2, 4999, textAttributes, HighlighterTargetArea.EXACT_RANGE));
                    }
                    for (EditorBranchRange editorBranchRange : list) {
                        if (!editorBranchRange.isResult()) {
                            list4.add(markupModel.addRangeHighlighter(editorBranchRange.getStartOffset(), editorBranchRange.getEndOffset(), 4999, textAttributes2, HighlighterTargetArea.EXACT_RANGE));
                        }
                    }
                    final Disposable newDisposable = Disposer.newDisposable(ConditionalActionConfiguration.H("\u001c\u001d\u0016\u00123\u001c\u0017\u0001\u0018,>=\u0019\f\u0014\u0006\u001e"));
                    final List list5 = list4;
                    EditorMouseListener editorMouseListener = new EditorMouseListener() { // from class: com.aicode.util.HighlighterUtil.01
                        /* renamed from: enum, reason: not valid java name */
                        private static /* synthetic */ void m407enum(int a8) {
                            throw new IllegalArgumentException(String.format(AICodeLanguageInfo.H("\u0004\u0006)\n\"\u001bjA\u007f\b1\u001dc2\b\u0018p{\u0017?/R=\u001d0\u0012+\u00127\u0017?\\eV-Ho\u0011>IZ=pJ0R+\u0002}K:Et^o\u001c!U1\u001b+\u001a"), RequestResultList.H("\u00072\u0013+\u0003"), AICodeLanguageInfo.H("\u0011\"\u0011m\u00127\f \u001a=F\n:7\u0003l:/\u0010fSsLs^*\f\u0011\u00016\u0002cG"), RequestResultList.H("m]h\\}i<\u000b'\u001d \u0013")));
                        }

                        public /* synthetic */ void mouseClicked(@NotNull EditorMouseEvent a8) {
                            if (a8 == null) {
                                m407enum(0);
                            }
                            super.mouseReleased(a8);
                            List list6 = list5;
                            MarkupModel markupModel2 = markupModel;
                            Objects.requireNonNull(markupModel2);
                            list6.forEach(markupModel2::removeHighlighter);
                            a.putUserData(HighlighterUtil.HIGH_LIGHTER, new ArrayList());
                            Disposer.dispose(newDisposable);
                        }
                    };
                    if (CollectionUtils.isNotEmpty(list)) {
                        a.getCaretModel().moveToOffset(list.get(0).getStartOffset() + 1);
                        a.getScrollingModel().scrollToCaret(ScrollType.CENTER);
                    }
                    a.addEditorMouseListener(editorMouseListener, newDisposable);
                    if (list4.isEmpty()) {
                        a.putUserData(HIGH_LIGHTER, list4);
                        return;
                    }
                    return;
                }
            } else {
                i2 = orElse.getEndOffset();
            }
        }
        i = i2;
        if (i == orElse.getEndOffset()) {
            list4.add(markupModel.addRangeHighlighter(orElse.getStartOffset(), i2, 4999, textAttributes, HighlighterTargetArea.EXACT_RANGE));
        }
        if (list.stream().anyMatch(a72 -> {
            return !a72.f689byte && a72.f687try;
        })) {
        }
        while (r0.hasNext()) {
        }
        final Disposable newDisposable2 = Disposer.newDisposable(ConditionalActionConfiguration.H("\u001c\u001d\u0016\u00123\u001c\u0017\u0001\u0018,>=\u0019\f\u0014\u0006\u001e"));
        final List list52 = list4;
        EditorMouseListener editorMouseListener2 = new EditorMouseListener() { // from class: com.aicode.util.HighlighterUtil.01
            /* renamed from: enum, reason: not valid java name */
            private static /* synthetic */ void m407enum(int a8) {
                throw new IllegalArgumentException(String.format(AICodeLanguageInfo.H("\u0004\u0006)\n\"\u001bjA\u007f\b1\u001dc2\b\u0018p{\u0017?/R=\u001d0\u0012+\u00127\u0017?\\eV-Ho\u0011>IZ=pJ0R+\u0002}K:Et^o\u001c!U1\u001b+\u001a"), RequestResultList.H("\u00072\u0013+\u0003"), AICodeLanguageInfo.H("\u0011\"\u0011m\u00127\f \u001a=F\n:7\u0003l:/\u0010fSsLs^*\f\u0011\u00016\u0002cG"), RequestResultList.H("m]h\\}i<\u000b'\u001d \u0013")));
            }

            public /* synthetic */ void mouseClicked(@NotNull EditorMouseEvent a8) {
                if (a8 == null) {
                    m407enum(0);
                }
                super.mouseReleased(a8);
                List list6 = list52;
                MarkupModel markupModel2 = markupModel;
                Objects.requireNonNull(markupModel2);
                list6.forEach(markupModel2::removeHighlighter);
                a.putUserData(HighlighterUtil.HIGH_LIGHTER, new ArrayList());
                Disposer.dispose(newDisposable2);
            }
        };
        if (CollectionUtils.isNotEmpty(list)) {
        }
        a.addEditorMouseListener(editorMouseListener2, newDisposable2);
        if (list4.isEmpty()) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ void highlightText(Editor a, int a2, int a3, boolean z) {
        TextAttributes textAttributes = new TextAttributes(z ? new Color(90, 235, 0) : new Color(238, 95, 91), EditorColorsManager.getInstance().getGlobalScheme().getColor(EditorColors.CARET_ROW_COLOR), (Color) null, (EffectType) null, 0);
        final MarkupModel markupModel = a.getMarkupModel();
        final RangeHighlighter addRangeHighlighter = markupModel.addRangeHighlighter(a2, a3, 4999, textAttributes, HighlighterTargetArea.EXACT_RANGE);
        final Disposable newDisposable = Disposer.newDisposable(InlineChatStatusServiceKt.H("\u0002)\u000245$\u000f2\u000b\u0018*\u0019\u001d(\u0010+\u0013"));
        a.addEditorMouseListener(new EditorMouseListener() { // from class: com.aicode.util.HighlighterUtil.02
            /* renamed from: enum, reason: not valid java name */
            private static /* synthetic */ void m408enum(int a4) {
                throw new IllegalArgumentException(String.format(EditorUtils.H("\u001ctu<w$~?<!x>6\r\u0013ir\u0013r0vao%/gV\u0005n$fo<elc:.ro>3)yeml/U\t'2u5?&2,-m-v"), ChatInputController.H("\u0010\u0014\u000b\u0014\u0002"), EditorUtils.H("\"{\"4!v'u%q`n4n09\u0005h=N\u0011n;r5z6\u0002x*te("), ChatInputController.H("JDPZ\u0017=\u0015\u001c\u0001\u0005\u001f\u0012")));
            }

            public /* synthetic */ void mouseClicked(@NotNull EditorMouseEvent a4) {
                if (a4 == null) {
                    m408enum(0);
                }
                super.mouseReleased(a4);
                markupModel.removeHighlighter(addRangeHighlighter);
                Disposer.dispose(newDisposable);
            }
        }, newDisposable);
    }
}
