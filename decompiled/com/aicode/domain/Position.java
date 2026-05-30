package com.aicode.domain;

import com.google.gson.annotations.SerializedName;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/domain/Position.class */
public class Position {

    @SerializedName("line")
    int line;

    @SerializedName("character")
    int character;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 0:
            default:
                objArr[0] = "lineInfo";
                break;
            case 1:
                objArr[0] = "text";
                break;
        }
        objArr[1] = "com/aicode/domain/Position";
        switch (i) {
            case 0:
            default:
                objArr[2] = "<init>";
                break;
            case 1:
                objArr[2] = "toOffset";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @NotNull
    public static Position of(int line, int character) {
        return new Position(line, character);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Position(@NotNull LineInfo lineInfo) {
        this(lineInfo.getLineNumber(), lineInfo.getColumnOffset());
        if (lineInfo == null) {
            $$$reportNull$$$0(0);
        }
    }

    public Position(int line, int character) {
        this.line = line;
        this.character = character;
    }

    public int toOffset(@NotNull String text) {
        if (text == null) {
            $$$reportNull$$$0(1);
        }
        return StringUtil.lineColToOffset(text, this.line, this.character);
    }

    public int getLine() {
        return this.line;
    }

    public int getCharacter() {
        return this.character;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setCharacter(int character) {
        this.character = character;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Position;
    }

    public String toString() {
        int line = getLine();
        return "Position(line=" + line + ", character=" + getCharacter() + ")";
    }

    public static Position getCursorPosition(Editor editor) {
        Application application = ApplicationManager.getApplication();
        return (Position) application.runReadAction(() -> {
            CaretModel caretModel = editor.getCaretModel();
            int offset = caretModel.getOffset();
            int lineNumber = editor.getDocument().getLineNumber(offset);
            int column = offset - editor.getDocument().getLineStartOffset(lineNumber);
            return new Position(lineNumber, column);
        });
    }
}
