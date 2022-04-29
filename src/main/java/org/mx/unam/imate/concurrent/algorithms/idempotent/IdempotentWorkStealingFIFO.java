package org.mx.unam.imate.concurrent.algorithms.idempotent;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.datastructures.TaskArrayWithSize;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingFIFO implements WorkStealingStruct {

    private static final int EMPTY = -1;

    private TaskArrayWithSize tasks;
    private final AtomicInteger head;
    private final AtomicInteger tail;

    public IdempotentWorkStealingFIFO(int size) {
        this.head = new AtomicInteger(0);
        this.tail = new AtomicInteger(0);
        this.tasks = new TaskArrayWithSize(size);
    }

    @Override
    public boolean isEmpty() {
        return head.get() == tail.get();
    }

    @Override
    public void put(int task) {
        int h = head.get();
        int t = tail.get();
        if (t == h + tasks.getSize()) {
            expand();
            put(task);
        }
        VarHandle.releaseFence();
        tasks.set(t % tasks.getSize(), task);
        tail.set(t + 1);
    }

    @Override
    public int take() {
        int h = head.get();
        int t = tail.get();
        if (h == t) {
            return EMPTY;
        }
        int task = tasks.get(h % tasks.getSize());
        head.set(h + 1);
        return task;
    }

    @Override
    public int steal() {
        while (true) {
            int h = head.get();
            VarHandle.acquireFence();
            int t = tail.get();
            if (h == t) {
                return EMPTY;
            }
            TaskArrayWithSize a = tasks;
            int task = a.get(h % a.getSize());
            VarHandle.acquireFence();
            if (head.compareAndSet(h, h + 1)) {
                return task;
            }

        }
    }

    public void expand() {
        int size = tasks.getSize();
        TaskArrayWithSize a = new TaskArrayWithSize(2 * size);
        VarHandle.releaseFence();
        int h = head.get();
        int t = tail.get();
        for (int i = h; i < t; i++) {
            a.set(i % a.getSize(), tasks.get(i % tasks.getSize()));
            VarHandle.releaseFence();
        }
        tasks = a;
        VarHandle.releaseFence();
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

    @Override
    public boolean isEmpty(int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
