package org.mx.unam.imate.concurrent.algorithms.ours.fifo.v4;

import org.mx.unam.imate.concurrent.datastructures.Node;

/**
 *
 * @author miguel
 */
public class FIFOWorkStealingV4 {

    private static final int EMPTY = -1;

    private Node head;
    private Node tail;

    public FIFOWorkStealingV4() {
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return tail == null;
    }

    public boolean put(int task) {
        if (tail != null) {
            tail.setNext(new Node(task, null));
            tail = tail.getNext();
        } else {
            tail = new Node(task, null);
            head = tail;
        }
        return true;
    }

    public int take() {
        if (tail != null) {
            int task = head.getVal();
            head = head.getNext();
            if (head == null) {
                tail = head;
            }
            return task;
        }
        return EMPTY;
    }

    public int steal() {
        if (tail != null) {
            int task = head.getVal();
            head = head.getNext();
            if (head == null) {
                tail = head;
            }
            return task;
        }
        return EMPTY;
    }

}
