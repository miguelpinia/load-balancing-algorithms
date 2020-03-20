package org.mx.unam.imate.concurrent.datastructures;

/**
 *
 * @author miguel
 */
public class Stack {

    private Node top;

    public Stack() {
        top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(int task) {
        Node newNode = new Node(task, top);
        top = newNode;
    }

    public int pop() {
        if (top == null) {
            return -1;
        }
        Node tmp = top;
        int value = tmp.getVal();
        top = top.getNext();
        return value;
    }

    public boolean inStack(int val) {
        Node tmp = top;
        while (tmp != null) {
            if (tmp.getVal() == val) {
                return true;
            }
            tmp = tmp.getNext();
        }
        return false;
    }
}
