/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.idempotent.lifo;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingLIFO {

    private static final int EMPTY = -1;
    private static final Unsafe unsafe = createUnsafe();

    private int[] tasks;
    private final AtomicReference<Pair> anchor;
    private int capacity;

    private static Unsafe createUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(Unsafe.class);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(IdempotentWorkStealingLIFO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IdempotentWorkStealingLIFO(int size) {
        anchor = new AtomicReference<>(new Pair(0, 0));
        capacity = size;
        tasks = new int[size];
    }

    public boolean isEmpty() {
        return anchor.get().getT() == 0;
    }

    public void put(int task) {
        Pair a = anchor.get();
        int t = a.getT();
        int g = a.getG();
        if (t == capacity) {
            expand();
            put(task);
        }
        unsafe.storeFence();
        tasks[t] = task;
        anchor.set(new Pair(t + 1, g + 1));
    }

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

    public int steal() {
        Pair oldReference = anchor.get();
        int t = oldReference.getT();
        int g = oldReference.getG();
        unsafe.loadFence();
        if (t == 0) {
            return EMPTY;
        }
        int[] tmp = tasks;
        int task = tmp[t - 1];
        unsafe.loadFence();
        if (!anchor.compareAndSet(oldReference, new Pair(t - 1, g))) {
            steal();
        }
        return task;
    }

    public void expand() {
        int[] newTasks = new int[2 * capacity];
        for (int i = 0; i < capacity; i++) { // Comparar con System.arrayCopy
            newTasks[i] = tasks[i];
        }
        unsafe.storeFence();
        tasks = newTasks;
        unsafe.storeFence();
        capacity = 2 * capacity;
    }

    class Pair {

        private final int t;
        private final int g;

        public Pair(int t, int g) {
            this.t = t;
            this.g = g;
        }

        public int getT() {
            return t;
        }

        public int getG() {
            return g;
        }

    }

}
