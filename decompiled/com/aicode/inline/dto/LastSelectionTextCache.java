/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.inline.dto;

import com.aicode.agent.dto.chat.CodeInfoDto;
import java.util.List;

public class LastSelectionTextCache {
    private int careOffsetStart;
    private int selectionStart;
    private int selectionEnd;
    private int realStartOffset;
    private int realEndOffset;
    private String text;
    private List<CodeInfoDto.RangeDTO> range;

    public LastSelectionTextCache(int selectionStart, int selectionEnd, String selectedText, List<CodeInfoDto.RangeDTO> range) {
        this.selectionStart = selectionStart;
        this.selectionEnd = selectionEnd;
        this.text = selectedText;
        this.range = range;
    }

    public int getCareOffsetStart() {
        return this.careOffsetStart;
    }

    public int getSelectionStart() {
        return this.selectionStart;
    }

    public int getSelectionEnd() {
        return this.selectionEnd;
    }

    public int getRealStartOffset() {
        return this.realStartOffset;
    }

    public int getRealEndOffset() {
        return this.realEndOffset;
    }

    public String getText() {
        return this.text;
    }

    public List<CodeInfoDto.RangeDTO> getRange() {
        return this.range;
    }

    public void setCareOffsetStart(int careOffsetStart) {
        this.careOffsetStart = careOffsetStart;
    }

    public void setSelectionStart(int selectionStart) {
        this.selectionStart = selectionStart;
    }

    public void setSelectionEnd(int selectionEnd) {
        this.selectionEnd = selectionEnd;
    }

    public void setRealStartOffset(int realStartOffset) {
        this.realStartOffset = realStartOffset;
    }

    public void setRealEndOffset(int realEndOffset) {
        this.realEndOffset = realEndOffset;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRange(List<CodeInfoDto.RangeDTO> range) {
        this.range = range;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LastSelectionTextCache)) {
            return false;
        }
        LastSelectionTextCache other = (LastSelectionTextCache)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getCareOffsetStart() != other.getCareOffsetStart()) {
            return false;
        }
        if (this.getSelectionStart() != other.getSelectionStart()) {
            return false;
        }
        if (this.getSelectionEnd() != other.getSelectionEnd()) {
            return false;
        }
        if (this.getRealStartOffset() != other.getRealStartOffset()) {
            return false;
        }
        if (this.getRealEndOffset() != other.getRealEndOffset()) {
            return false;
        }
        String this$text = this.getText();
        String other$text = other.getText();
        if (this$text == null ? other$text != null : !this$text.equals(other$text)) {
            return false;
        }
        List<CodeInfoDto.RangeDTO> this$range = this.getRange();
        List<CodeInfoDto.RangeDTO> other$range = other.getRange();
        return !(this$range == null ? other$range != null : !((Object)this$range).equals(other$range));
    }

    protected boolean canEqual(Object other) {
        return other instanceof LastSelectionTextCache;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getCareOffsetStart();
        result = result * 59 + this.getSelectionStart();
        result = result * 59 + this.getSelectionEnd();
        result = result * 59 + this.getRealStartOffset();
        result = result * 59 + this.getRealEndOffset();
        String $text = this.getText();
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
        List<CodeInfoDto.RangeDTO> $range = this.getRange();
        result = result * 59 + ($range == null ? 43 : ((Object)$range).hashCode());
        return result;
    }

    public String toString() {
        return "LastSelectionTextCache(careOffsetStart=" + this.getCareOffsetStart() + ", selectionStart=" + this.getSelectionStart() + ", selectionEnd=" + this.getSelectionEnd() + ", realStartOffset=" + this.getRealStartOffset() + ", realEndOffset=" + this.getRealEndOffset() + ", text=" + this.getText() + ", range=" + this.getRange() + ")";
    }
}
