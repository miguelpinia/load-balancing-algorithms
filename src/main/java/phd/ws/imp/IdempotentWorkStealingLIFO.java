/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.ws.imp;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import phd.utils.Pair;
import phd.ws.WorkStealingStruct;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingLIFO implements WorkStealingStruct {

    private static final int EMPTY = -1;

    private int[] tasks;
    private final AtomicReference<Pair> anchor;
    private int capacity;
    private int puts = 0;
    private int takes = 0;
    private AtomicInteger steals = new AtomicInteger(0);

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
//        puts++;
    }

    @Override
    public int take() {
        Pair a = anchor.get();
        int t = a.getT();
        int g = a.getG();
        if (t == 0) {
//            takes++;
            return EMPTY;
        }
        int task = tasks[t - 1];
        anchor.set(new Pair(t - 1, g));
//        takes++;
        return task;
    }

    @Override
    public int steal() {
        while (true) {
            Pair oldReference = anchor.get();
            int t = oldReference.getT();
            int g = oldReference.getG();
            if (t == 0) {
//                steals.incrementAndGet();
                return EMPTY;
            }
            VarHandle.acquireFence();
            int[] tmp = tasks;
            int task = tmp[t - 1];
            VarHandle.fullFence();
            if (anchor.compareAndSet(oldReference, new Pair(t - 1, g))) {
//                steals.incrementAndGet();
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

    @Override
    public int getPuts() {
        return puts;
    }

    @Override
    public int getTakes() {
        return takes;
    }

    @Override
    public int getSteals() {
        return steals.get();
    }

}
