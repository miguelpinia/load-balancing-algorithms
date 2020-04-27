package org.mx.unam.imate.concurrent.algorithms.idempotent;

import java.util.concurrent.atomic.AtomicReference;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.Triplet;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import org.mx.unam.imate.concurrent.datastructures.TaskArrayWithSize;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingDeque implements WorkStealingStruct {

    private static final int EMPTY = -1;
    private static final int MAX_SIZE = 0xFFFFFF;
    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

    private TaskArrayWithSize tasks;
    private final AtomicReference<Triplet> anchor;

    public IdempotentWorkStealingDeque(int size) {
        this.tasks = new TaskArrayWithSize(size);
        this.anchor = new AtomicReference<>(new Triplet(0, 0, 0));
    }

    @Override
    public void put(int task) {
        Triplet oldReference = anchor.get();
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        if (s == tasks.getSize()) {
            expand();
            put(task);
        }
        tasks.getArray()[(h + s) % tasks.getSize()] = task;
        unsafe.storeFence();
        anchor.set(new Triplet(h, s + 1, g + 1));
    }

    @Override
    public boolean isEmpty() {
        return anchor.get().getSize() <= 0;
    }

    @Override
    public int take() {
        Triplet oldReference = anchor.get();
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        if (s == 0) {
            return EMPTY;
        }
        int task = tasks.getArray()[(h + s - 1) % tasks.getSize()];
        anchor.set(new Triplet(h, s - 1, g));
        return task;
    }

    @Override
    public int steal() {
        while (true) {
            Triplet oldReference = anchor.get();
            int h = oldReference.getHead();
            int s = oldReference.getSize();
            int g = oldReference.getTag();

            if (s == 0) {
                return EMPTY;
            }
            TaskArrayWithSize a = tasks;
            unsafe.loadFence();
            int task = a.getArray()[h % a.getSize()];
            unsafe.loadFence();
            int h2 = h + 1 % MAX_SIZE;
            Triplet newReference = new Triplet(h2, s - 1, g);
            if (anchor.compareAndSet(oldReference, newReference)) {
                return task;
            }

        }
    }

    public void expand() {
        Triplet oldReference = anchor.get();
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        TaskArrayWithSize a = new TaskArrayWithSize(2 * s);
        unsafe.storeFence();
        for (int i = 0; i < s; i++) {
            a.getArray()[(h + i) % a.getSize()] = tasks.getArray()[(h + i) % tasks.getSize()];
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

    @Override
    public boolean isEmpty(int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
