package org.mx.unam.imate.concurrent.datastructures;

import java.util.LinkedList;

/**
 *
 * @author miguel
 */
public class Stack {

    private final LinkedList<Integer> stack;

    public Stack() {
        stack = new LinkedList<>();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void push(int task) {
        stack.add(task);
    }

    public int pop() {
        if (stack.isEmpty()) {
            return -1;
        }
        return stack.removeLast();
    }

    public boolean inStack(int val) {
        return stack.contains(val);
    }

    public int peek() {
        if (stack.isEmpty()) {
            return -1;
        }
        return stack.peekLast();
    }

    @Override
    public String toString() {
        return "Stack{" + stack + '}';
    }

}
