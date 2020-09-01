package org.mx.unam.imate.concurrent.datastructures;

import java.util.LinkedList;

/**
 *
 * @author miguel
 */
public class Queue {

    private final LinkedList<Integer> queue;

    public Queue() {
        queue = new LinkedList<>();
    }

    public void enqueue(int task) {
        queue.add(task);
    }

    public int dequeue() {
        if (queue.isEmpty()) {
            return -1;
        }
        return queue.remove();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

}
