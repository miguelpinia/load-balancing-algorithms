package org.mx.unam.imate.concurrent.algorithms.idempotent.fifo;

import java.util.concurrent.atomic.AtomicInteger;

import org.mx.unam.imate.concurrent.datastructures.TaskArrayWithSize;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingFIFO {

    private static final int EMPTY = -1;

    private TaskArrayWithSize tasks;
    private final AtomicInteger head;
    private final AtomicInteger tail;

    public IdempotentWorkStealingFIFO(int size) {
        this.head = new AtomicInteger(0);
        this.tail = new AtomicInteger(0);
        this.tasks = new TaskArrayWithSize(size);
    }

    public void put(int task) {
        int h = head.get();
        int t = tail.get();
        if (t == h + tasks.getSize()) {
            expand();
            put(task);
        }
        tasks.getArray()[t % tasks.getSize()] = task;
        tail.set(t + 1);
    }

    public int take() {
        int h = head.get();
        int t = tail.get();
        if (h == t) {
            return EMPTY;
        }
        int task = tasks.getArray()[h % tasks.getSize()];
        head.set(h + 1);
        return task;
    }

    public int steal() {
        int h = head.get();
        int t = tail.get();
        if (h == t) {
            return EMPTY;
        }
        TaskArrayWithSize a = tasks;
        int task = a.getArray()[h % a.getSize()];
        if (!head.compareAndSet(h, h + 1)) {
            steal();
        }
        return task;
    }

    public void expand() {
        int size = tasks.getSize();
        TaskArrayWithSize a = new TaskArrayWithSize(2 * size);
        int h = head.get();
        int t = tail.get();
        for (int i = h; i < t; i++) {
            a.getArray()[i % a.getSize()] = tasks.getArray()[i % a.getSize()];
        }
        tasks = a;
    }

}
