/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 */
package com.aicode.agent.dto.chat;

import com.google.gson.JsonArray;

public class CommentInfo {
    private String name;
    private String textContext;
    private int index;
    private JsonArray range;
    private JsonArray bodyRange;

    public String getName() {
        return this.name;
    }

    public String getTextContext() {
        return this.textContext;
    }

    public int getIndex() {
        return this.index;
    }

    public JsonArray getRange() {
        return this.range;
    }

    public JsonArray getBodyRange() {
        return this.bodyRange;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTextContext(String textContext) {
        this.textContext = textContext;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setRange(JsonArray range) {
        this.range = range;
    }

    public void setBodyRange(JsonArray bodyRange) {
        this.bodyRange = bodyRange;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CommentInfo)) {
            return false;
        }
        CommentInfo other = (CommentInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getIndex() != other.getIndex()) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$textContext = this.getTextContext();
        String other$textContext = other.getTextContext();
        if (this$textContext == null ? other$textContext != null : !this$textContext.equals(other$textContext)) {
            return false;
        }
        JsonArray this$range = this.getRange();
        JsonArray other$range = other.getRange();
        if (this$range == null ? other$range != null : !this$range.equals(other$range)) {
            return false;
        }
        JsonArray this$bodyRange = this.getBodyRange();
        JsonArray other$bodyRange = other.getBodyRange();
        return !(this$bodyRange == null ? other$bodyRange != null : !this$bodyRange.equals(other$bodyRange));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CommentInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getIndex();
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $textContext = this.getTextContext();
        result = result * 59 + ($textContext == null ? 43 : $textContext.hashCode());
        JsonArray $range = this.getRange();
        result = result * 59 + ($range == null ? 43 : $range.hashCode());
        JsonArray $bodyRange = this.getBodyRange();
        result = result * 59 + ($bodyRange == null ? 43 : $bodyRange.hashCode());
        return result;
    }

    public String toString() {
        return "CommentInfo(name=" + this.getName() + ", textContext=" + this.getTextContext() + ", index=" + this.getIndex() + ", range=" + this.getRange() + ", bodyRange=" + this.getBodyRange() + ")";
    }
}
