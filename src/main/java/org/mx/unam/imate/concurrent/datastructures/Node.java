package org.mx.unam.imate.concurrent.datastructures;

import java.util.Objects;

/**
 *
 * @author miguel
 */
public class Node {

    private final int val;
    private Node next;

    public Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.val;
        hash = 67 * hash + Objects.hashCode(this.next);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.val != other.val) {
            return false;
        }
        return Objects.equals(this.next, other.next);
    }

    @Override
    public String toString() {
        return "Node{" + "val=" + val + ", next=" + next + '}';
    }

}
