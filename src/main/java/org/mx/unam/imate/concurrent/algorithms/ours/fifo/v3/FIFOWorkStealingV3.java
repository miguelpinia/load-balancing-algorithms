package org.mx.unam.imate.concurrent.algorithms.ours.fifo.v3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author miguel
 */
public class FIFOWorkStealingV3 {

    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger head;
    private final AtomicInteger tail;
    private int t;
    private final int[] tasks;

    public FIFOWorkStealingV3(int size) {
        this.tail = new AtomicInteger(0);
        this.t = 0;
        this.head = new AtomicInteger(0);
        this.tasks = new int[size];
    }

    public boolean isEmpty() {
        return t == head.get();
    }

    public boolean put(int task) {
        t = t + 1;
        tasks[t] = task;
        tail.set(t);
        return true;
    }

    public int take() {
        int l = tail.get();
        int h = head.get();
        for (int i = h; i < l; i++) {
            int x = tasks[i];
            tasks[i] = BOTTOM;
            if (x != BOTTOM) {
                head.set(i + 1);
                return x;
            }
        }
        return EMPTY;
    }

    public int steal() {
        int l = tail.get();
        int h = head.get();
        for (int i = h; i < l; i++) {
            int x = tasks[i];
            tasks[i] = BOTTOM;
            if (x != BOTTOM) {
                head.set(i + 1);
                return x;
            }
        }
        return EMPTY;
    }

}
