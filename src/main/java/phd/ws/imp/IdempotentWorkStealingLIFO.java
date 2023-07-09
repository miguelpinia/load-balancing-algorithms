/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.ws.imp;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicReference;

import phd.ws.WorkStealingStruct;
import phd.utils.Pair;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingLIFO implements WorkStealingStruct {

    private static final int EMPTY = -1;

    private int[] tasks;
    private final AtomicReference<Pair> anchor;
    private int capacity;

    public IdempotentWorkStealingLIFO(int size) {
        anchor = new AtomicReference<>(new Pair(0, 0));
        capacity = size;
        tasks = new int[size];
    }

    @Override
    public boolean isEmpty() {
        return anchor.get().getT() == 0;
    }

    @Override
    public void put(int task) {
        Pair a = anchor.get();
        int t = a.getT();
        int g = a.getG();
        if (t == capacity) {
            expand();
            put(task);
            return;
        }
        tasks[t] = task;
        VarHandle.fullFence();
        anchor.set(new Pair(t + 1, g + 1));
    }

    @Override
    public int take() {
        Pair a = anchor.get();
        int t = a.getT();
        int g = a.getG();
        if (t == 0) {
            return EMPTY;
        }
        int task = tasks[t - 1];
        anchor.set(new Pair(t - 1, g));
        return task;
    }

    @Override
    public int steal() {
        while (true) {
            Pair oldReference = anchor.get();
            int t = oldReference.getT();
            int g = oldReference.getG();
            if (t == 0) {
                return EMPTY;
            }
            VarHandle.acquireFence();
            int[] tmp = tasks;
            int task = tmp[t - 1];
            VarHandle.fullFence();
            if (anchor.compareAndSet(oldReference, new Pair(t - 1, g))) {
                return task;
            }
        }

    }

    public void expand() {
        int[] newTasks = new int[2 * capacity];
        for (int i = 0; i < capacity; i++) { // Comparar con System.arrayCopy
            newTasks[i] = tasks[i];
            VarHandle.releaseFence();
        }
        tasks = newTasks;
        VarHandle.fullFence();
        capacity = 2 * capacity;
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
