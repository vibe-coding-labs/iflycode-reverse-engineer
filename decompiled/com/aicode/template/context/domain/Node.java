package com.aicode.template.context.domain;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/domain/Node.class */
public class Node<T> {
    private final T data;
    private final Node<T> parent;
    private final int depth;
    private Object needData;

    public Node(T data, Node<T> parent, int depth) {
        this.data = data;
        this.parent = parent;
        this.depth = depth;
    }

    public Object getNeedData() {
        return this.needData;
    }

    public void setNeedData(Object needData) {
        this.needData = needData;
    }

    public T getData() {
        return this.data;
    }

    public Node<T> getParent() {
        return this.parent;
    }

    public int getDepth() {
        return this.depth;
    }

    public boolean hasSameAncestor() {
        return hasSameAncestor(this.data);
    }

    public boolean hasSameAncestor(T data) {
        if (this.parent == null || this.parent.data == null) {
            return false;
        }
        return this.parent.data.equals(data) || this.parent.hasSameAncestor(data);
    }
}
