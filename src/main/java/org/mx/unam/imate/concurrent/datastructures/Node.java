package org.mx.unam.imate.concurrent.datastructures;

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
}
