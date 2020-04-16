package org.mx.unam.imate.concurrent.algorithms.idempotent.fifo;

import java.util.concurrent.atomic.AtomicInteger;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import org.mx.unam.imate.concurrent.datastructures.TaskArrayWithSize;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingFIFO implements WorkStealingStruct {

    private static final int EMPTY = -1;
    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

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
        unsafe.storeFence();
        tasks.getArray()[t % tasks.getSize()] = task;
        tail.set(t + 1);
    }

    @Override
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

    @Override
    public int steal() {
        int h = head.get();
        unsafe.loadFence();
        int t = tail.get();
        if (h == t) {
            return EMPTY;
        }
        TaskArrayWithSize a = tasks;
        int task = a.getArray()[h % a.getSize()];
        unsafe.loadFence();
        if (!head.compareAndSet(h, h + 1)) {
            steal();
        }
        return task;
    }

    public void expand() {
        int size = tasks.getSize();
        TaskArrayWithSize a = new TaskArrayWithSize(2 * size);
        unsafe.storeFence();
        int h = head.get();
        int t = tail.get();
        for (int i = h; i < t; i++) {
            a.getArray()[i % a.getSize()] = tasks.getArray()[i % tasks.getSize()];
            unsafe.storeFence();
        }
        tasks = a;
        unsafe.storeFence();
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
