package org.mx.unam.imate.concurrent.algorithms.ours.fifo.v1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author miguel
 */
public class FIFOWorkStealingV1 {

    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger tail;
    private final int[] tasks;
    private int t;

    public FIFOWorkStealingV1(int size) {
        this.tail = new AtomicInteger(0);
        this.t = 0;
        this.tasks = new int[size];
    }

    public boolean isEmpty() {
        return t == 0;
    }

    public boolean put(int task) {
        t = t + 1;
        tasks[t] = task;
        tail.set(t);
        return true;
    }

    public int take() {
        int l = tail.get();
        for (int i = 0; i < l; i++) {
            int x = tasks[i];
            tasks[i] = BOTTOM;
            if (x != BOTTOM) {
                return x;
            }
        }
        return EMPTY;
    }

    public int steal() {
        int l = tail.get();
        for (int i = 0; i < l; i++) {
            int x = tasks[i];
            tasks[i] = BOTTOM;
            if (x != BOTTOM) {
                return x;
            }
        }
        return EMPTY;
    }

}
