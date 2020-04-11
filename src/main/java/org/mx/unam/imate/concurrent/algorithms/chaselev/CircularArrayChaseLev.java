package org.mx.unam.imate.concurrent.algorithms.chaselev;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 *
 * @author miguel
 */
public class CircularArrayChaseLev {

    private int size;
    private AtomicIntegerArray tasks;

    public CircularArrayChaseLev() {
        this(7);
    }

    public CircularArrayChaseLev(int logarithmicSize) {
        this.size = logarithmicSize;
        this.tasks = new AtomicIntegerArray(1 << size);
    }

    public int size() {
        return 1 << this.size;
    }

    public int get(int i) {
        return tasks.get(i % size());
    }

    public void put(int i, int task) {
        tasks.set(i % size(), task);
    }

    public CircularArrayChaseLev grow(int b, int t) {
        CircularArrayChaseLev a = new CircularArrayChaseLev(this.size + 1);
        for (int i = t; i < b; i++) {
            a.put(i, this.get(i));
        }
        return a;
    }

}
