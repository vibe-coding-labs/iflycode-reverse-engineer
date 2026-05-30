package com.aicode.domain;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/domain/CommandCache.class */
public class CommandCache {
    private boolean startSelected;
    private int startSelectedStartOffset;
    private boolean endSelected;
    private int endSelectedStartOffset;

    public void setStartSelected(boolean startSelected) {
        this.startSelected = startSelected;
    }

    public void setStartSelectedStartOffset(int startSelectedStartOffset) {
        this.startSelectedStartOffset = startSelectedStartOffset;
    }

    public void setEndSelected(boolean endSelected) {
        this.endSelected = endSelected;
    }

    public void setEndSelectedStartOffset(int endSelectedStartOffset) {
        this.endSelectedStartOffset = endSelectedStartOffset;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CommandCache)) {
            return false;
        }
        CommandCache other = (CommandCache) o;
        return other.canEqual(this) && isStartSelected() == other.isStartSelected() && getStartSelectedStartOffset() == other.getStartSelectedStartOffset() && isEndSelected() == other.isEndSelected() && getEndSelectedStartOffset() == other.getEndSelectedStartOffset();
    }

    protected boolean canEqual(Object other) {
        return other instanceof CommandCache;
    }

    public int hashCode() {
        int result = (1 * 59) + (isStartSelected() ? 79 : 97);
        return (((((result * 59) + getStartSelectedStartOffset()) * 59) + (isEndSelected() ? 79 : 97)) * 59) + getEndSelectedStartOffset();
    }

    public String toString() {
        return "CommandCache(startSelected=" + isStartSelected() + ", startSelectedStartOffset=" + getStartSelectedStartOffset() + ", endSelected=" + isEndSelected() + ", endSelectedStartOffset=" + getEndSelectedStartOffset() + ")";
    }

    public boolean isStartSelected() {
        return this.startSelected;
    }

    public int getStartSelectedStartOffset() {
        return this.startSelectedStartOffset;
    }

    public boolean isEndSelected() {
        return this.endSelected;
    }

    public int getEndSelectedStartOffset() {
        return this.endSelectedStartOffset;
    }
}
