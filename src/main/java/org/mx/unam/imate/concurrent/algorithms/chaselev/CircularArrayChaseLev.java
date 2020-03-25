package org.mx.unam.imate.concurrent.algorithms.chaselev;

/**
 *
 * @author miguel
 */
public class CircularArrayChaseLev {

    private int size;
    private int[] tasks;

    public CircularArrayChaseLev() {
        this(7);
    }

    public CircularArrayChaseLev(int logarithmicSize) {
        this.size = logarithmicSize;
        this.tasks = new int[1 << size];
    }

    public int size() {
        return 1 << this.size;
    }

    public int get(int i) {
        return tasks[i % size()];
    }

    public void put(int i, int task) {
        tasks[i % size()] = task;
    }

    public CircularArrayChaseLev grow(int b, int t) {
        CircularArrayChaseLev a = new CircularArrayChaseLev(this.size + 1);
        for (int i = t; i < b; i++) {
            a.put(i, this.get(i));
        }
        return a;
    }

}
