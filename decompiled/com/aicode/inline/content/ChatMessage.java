/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.inline.content;

public class ChatMessage {
    private String question;
    private boolean selected;

    public String getQuestion() {
        return this.question;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChatMessage)) {
            return false;
        }
        ChatMessage other = (ChatMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isSelected() != other.isSelected()) {
            return false;
        }
        String this$question = this.getQuestion();
        String other$question = other.getQuestion();
        return !(this$question == null ? other$question != null : !this$question.equals(other$question));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ChatMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isSelected() ? 79 : 97);
        String $question = this.getQuestion();
        result = result * 59 + ($question == null ? 43 : $question.hashCode());
        return result;
    }

    public String toString() {
        return "ChatMessage(question=" + this.getQuestion() + ", selected=" + this.isSelected() + ")";
    }
}
