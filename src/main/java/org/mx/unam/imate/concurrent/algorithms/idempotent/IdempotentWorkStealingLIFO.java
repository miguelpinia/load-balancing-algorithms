/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.idempotent;

import java.util.concurrent.atomic.AtomicReference;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.Pair;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingLIFO implements WorkStealingStruct {

    private static final int EMPTY = -1;
    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

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
        }
        tasks[t] = task;
        unsafe.storeFence();
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
            int[] tmp = tasks;
            unsafe.loadFence();
            int task = tmp[t - 1];
            if (anchor.compareAndSet(oldReference, new Pair(t - 1, g))) {
                return task;
            }
        }

    }

    public void expand() {
        int[] newTasks = new int[2 * capacity];
        for (int i = 0; i < capacity; i++) { // Comparar con System.arrayCopy
            newTasks[i] = tasks[i];
        }
        tasks = newTasks;
        unsafe.storeFence();
        capacity = 2 * capacity;
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
