package org.mx.unam.imate.concurrent.algorithms.cilk;

import java.util.concurrent.locks.ReentrantLock;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class DequeCilk implements WorkStealingStruct {

    private static final int EMPTY = -1;
    private volatile int H;
    private volatile int T;
    private int[] tasks;
    private final ReentrantLock lock;
    private static final Unsafe UNSAFE = WorkStealingUtils.createUnsafe();

    public DequeCilk(int initialSize) {
        this.lock = new ReentrantLock();
        tasks = new int[initialSize];
        H = 0;
        T = 0;
    }

    @Override
    public boolean isEmpty() {
        int tail = T;
        int head = H;
        return head >= tail;
    }

    private void expand() {
        int[] newData = new int[2 * tasks.length];
        for (int i = 0; i < tasks.length; i++) { // Comparar contra System.arrayCopy
            newData[i] = tasks[i];
        }
        tasks = newData;
    }

    @Override
    public void put(int task) {
        int tail = T;
        if (tail == tasks.length) {
            expand();
            put(task);
        }
        tasks[tail] = task;
        T = tail + 1;
    }

    @Override
    public int take() {
        int t = T - 1;
        T = t;
        UNSAFE.loadFence();
        int h = H;
        if (t > h) {
            return tasks[t];
        }
        if (t < h) {
            lock.lock();
            try {
                if (H >= (t + 1)) {
                    T = t + 1;
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                    return EMPTY;
                }
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }

        }
        return tasks[t];
    }

    @Override
    public int steal() {
        lock.lock();
        int ret = EMPTY;
        try {
            int h = H;
            H = h + 1;
            UNSAFE.storeFence();
            if (h + 1 <= T) {
                ret = tasks[h];
            } else {
                H = h;
                ret = EMPTY;
            }
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return ret;
    }

    @Override
    public boolean put(int task, int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int take(int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int steal(int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
