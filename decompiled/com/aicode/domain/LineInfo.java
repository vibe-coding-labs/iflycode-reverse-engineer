package com.aicode.domain;

import com.aicode.service.editor.EditorUtil;
import com.aicode.util.AICodeStringUtil;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/domain/LineInfo.class */
public final class LineInfo {
    private final int lineCount;
    private final int lineNumber;
    private final int lineStartOffset;
    private final int columnOffset;

    @NotNull
    private final String line;
    private final int nextLineIndent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 5:
            case 6:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            case 5:
            case 6:
            default:
                i2 = 3;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            case 5:
            default:
                objArr[0] = "document";
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
                objArr[0] = "com/aicode/domain/LineInfo";
                break;
            case 6:
                objArr[0] = "line";
                break;
        }
        switch (i) {
            case 0:
            case 5:
            case 6:
            default:
                objArr[1] = "com/aicode/domain/LineInfo";
                break;
            case 1:
                objArr[1] = "create";
                break;
            case 2:
                objArr[1] = "getLinePrefix";
                break;
            case 3:
                objArr[1] = "getLineSuffix";
                break;
            case 4:
                objArr[1] = "getWhitespaceBeforeCursor";
                break;
            case 7:
                objArr[1] = "getLine";
                break;
        }
        switch (i) {
            case 0:
            default:
                objArr[2] = "create";
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
                break;
            case 5:
                objArr[2] = "calculateNextLineIndent";
                break;
            case 6:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 5:
            case 6:
            default:
                throw new IllegalArgumentException(format);
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
                throw new IllegalStateException(format);
        }
    }

    @NotNull
    public static LineInfo create(@NotNull Document document, int offset) {
        if (document == null) {
            $$$reportNull$$$0(0);
        }
        try {
            int line = document.getLineNumber(offset);
            TextRange lineRange = TextRange.create(document.getLineStartOffset(line), document.getLineEndOffset(line));
            return new LineInfo(document.getLineCount(), line, lineRange.getStartOffset(), offset - lineRange.getStartOffset(), document.getText(lineRange), calculateNextLineIndent(document, offset));
        } catch (Throwable th) {
            if (0 == 0) {
                $$$reportNull$$$0(1);
            }
            return null;
        }
    }

    @NotNull
    public String getLinePrefix() {
        String substring = this.line.substring(0, this.columnOffset);
        if (substring == null) {
            $$$reportNull$$$0(2);
        }
        return substring;
    }

    @NotNull
    public String getLineSuffix() {
        String substring = this.line.substring(this.columnOffset);
        if (substring == null) {
            $$$reportNull$$$0(3);
        }
        return substring;
    }

    public boolean isBlankLine() {
        return this.line.isBlank();
    }

    @NotNull
    public String getWhitespaceBeforeCursor() {
        String trailingWhitespace = AICodeStringUtil.trailingWhitespace(getLinePrefix());
        if (trailingWhitespace == null) {
            $$$reportNull$$$0(4);
        }
        return trailingWhitespace;
    }

    public int getLineEndOffset() {
        return getLineStartOffset() + this.line.length();
    }

    private static int calculateNextLineIndent(@NotNull Document document, int offset) {
        if (document == null) {
            $$$reportNull$$$0(5);
        }
        int maxLines = document.getLineCount();
        for (int line = document.getLineNumber(offset) + 1; line < maxLines; line++) {
            int start = document.getLineStartOffset(line);
            int end = document.getLineEndOffset(line);
            if (start != end) {
                String lineContent = document.getText(TextRange.create(start, end));
                if (!lineContent.isBlank()) {
                    return EditorUtil.whitespacePrefixLength(lineContent);
                }
            }
        }
        return -1;
    }

    public LineInfo(int lineCount, int lineNumber, int lineStartOffset, int columnOffset, @NotNull String line, int nextLineIndent) {
        if (line == null) {
            $$$reportNull$$$0(6);
        }
        this.lineCount = lineCount;
        this.lineNumber = lineNumber;
        this.lineStartOffset = lineStartOffset;
        this.columnOffset = columnOffset;
        this.line = line;
        this.nextLineIndent = nextLineIndent;
    }

    public int getLineCount() {
        return this.lineCount;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public int getLineStartOffset() {
        return this.lineStartOffset;
    }

    public int getColumnOffset() {
        return this.columnOffset;
    }

    @NotNull
    public String getLine() {
        String str = this.line;
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return str;
    }

    public int getNextLineIndent() {
        return this.nextLineIndent;
    }

    public String toString() {
        int var10000 = getLineCount();
        return "LineInfo(lineCount=" + var10000 + ", lineNumber=" + getLineNumber() + ", lineStartOffset=" + getLineStartOffset() + ", columnOffset=" + getColumnOffset() + ", line=" + getLine() + ", nextLineIndent=" + getNextLineIndent() + ")";
    }
}
