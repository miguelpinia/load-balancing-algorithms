package org.mx.unam.imate.concurrent.algorithms.ours.fifo.v2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author miguel
 */
public class FIFOWorkStealingV2 {

    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger head;
    private final AtomicInteger tail;
    private int t;
    private final int[] tasks;

    public FIFOWorkStealingV2(int size) {
        this.tail = new AtomicInteger(0);
        this.head = new AtomicInteger(0);
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
            head.set(i);
            if (x != BOTTOM) {
                return x;
            }
        }
        return EMPTY;
    }

    public int steal() {
        int x = tasks[head.get()];
        if (x != BOTTOM) {
            return x;
        }
        return EMPTY;
    }

}
