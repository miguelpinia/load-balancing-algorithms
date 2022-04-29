package org.mx.unam.imate.concurrent.algorithms.idempotent;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicReference;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.Triplet;
import org.mx.unam.imate.concurrent.datastructures.TaskArrayWithSize;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingDeque implements WorkStealingStruct {

    private static final int EMPTY = -1;
    private static final int MAX_SIZE = 0xFFFFFF;

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
            return;
        }
        tasks.set((h + s) % tasks.getSize(), task);
        VarHandle.releaseFence();
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
        if (s == 0) return EMPTY;
        int task = tasks.get((h + s - 1) % tasks.getSize());
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
            VarHandle.acquireFence();
            TaskArrayWithSize a = tasks;
            int task = a.get(h % a.getSize());
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
        TaskArrayWithSize a = new TaskArrayWithSize(2 * s);
        VarHandle.releaseFence();
        for (int i = 0; i < s; i++) {
            a.set((h + i) % a.getSize(), tasks.get((h + i) % tasks.getSize()));
            VarHandle.releaseFence();
        }
        tasks = a;
        VarHandle.fullFence();
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
