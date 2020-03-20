package org.mx.unam.imate.concurrent.algorithms.chaselev;

import java.util.concurrent.atomic.AtomicInteger;

import org.mx.unam.imate.concurrent.datastructures.CircularArrayChaseLev;

/**
 *
 * @author miguel
 */
public class ChaseLevWorkStealing {

    private static final int LOG_INITIAL_SIZE = 7;

    public final static int EMPTY = -1;
    public final static int ABORT = -2;

    private final AtomicInteger top;
    private final AtomicInteger bottom;
    private volatile CircularArrayChaseLev activeArray;

    public ChaseLevWorkStealing(int size) {
        this.top = new AtomicInteger(0);
        this.bottom = new AtomicInteger(0);
        activeArray = new CircularArrayChaseLev(LOG_INITIAL_SIZE);
    }

    public boolean isEmpty() {
        int b = bottom.get();
        int t = top.get();
        return (b - t) <= 0;
    }

    public void pushBottom(int task) {
        int b = bottom.get();
        int t = top.get();
        CircularArrayChaseLev a = this.activeArray;
        long size = b - t;
        if (size >= (a.size() - 1)) {
            a = a.grow(b, t);
            this.activeArray = a;
        }
        a.put(b, task);
        bottom.set(b + 1);
    }

    public int steal() {
        int t = top.get();
        int b = bottom.get();
        CircularArrayChaseLev a = this.activeArray;
        long size = b - t;
        if (size <= 0) {
            return EMPTY;
        }
        int task = a.get(t);
        if (!top.compareAndSet(t, t + 1)) {
            return ABORT;
        }
        return task;
    }

    // En trabajo a futuro, hay que actualizar esta versión para que pueda
    // decrecer el tamaño del arreglo circular que usa, como lo mencionan en
    // la sección 3 del artículo de este algoritmo.
    public int popBottom() {
        int b = bottom.get();
        CircularArrayChaseLev a = this.activeArray;
        b = b - 1;
        bottom.set(b);
        int t = top.get();
        long size = b - t;
        if (size < 0) {
            bottom.set(t);
            return EMPTY;
        }
        int task = a.get(b);
        if (size > 0) {
            return task;
        }
        if (!top.compareAndSet(t, t + 1)) {
            task = EMPTY;
        }
        bottom.set(t + 1);
        return task;
    }

}
