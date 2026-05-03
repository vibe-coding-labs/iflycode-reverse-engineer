/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto.chat;

import com.aicode.agent.dto.chat.CommentInfo;
import java.util.List;

public class CommentContext {
    private String md5;
    private List<CommentInfo> methods;

    public String getMd5() {
        return this.md5;
    }

    public List<CommentInfo> getMethods() {
        return this.methods;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setMethods(List<CommentInfo> methods) {
        this.methods = methods;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CommentContext)) {
            return false;
        }
        CommentContext other = (CommentContext)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$md5 = this.getMd5();
        String other$md5 = other.getMd5();
        if (this$md5 == null ? other$md5 != null : !this$md5.equals(other$md5)) {
            return false;
        }
        List<CommentInfo> this$methods = this.getMethods();
        List<CommentInfo> other$methods = other.getMethods();
        return !(this$methods == null ? other$methods != null : !((Object)this$methods).equals(other$methods));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CommentContext;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $md5 = this.getMd5();
        result = result * 59 + ($md5 == null ? 43 : $md5.hashCode());
        List<CommentInfo> $methods = this.getMethods();
        result = result * 59 + ($methods == null ? 43 : ((Object)$methods).hashCode());
        return result;
    }

    public String toString() {
        return "CommentContext(md5=" + this.getMd5() + ", methods=" + this.getMethods() + ")";
    }
}
