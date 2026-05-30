package com.aicode.inline.ide;

import org.jetbrains.annotations.NotNull;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/ide/PredicateFactory.class */
public interface PredicateFactory {
    @NotNull
    ConditionalEditorActionPredicate predicate(@NotNull ActionScope actionScope);
}
