package org.mx.unam.imate.concurrent.algorithms.chaselev;

import java.util.concurrent.atomic.AtomicInteger;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class ChaseLevWorkStealing implements WorkStealingStruct {

    private static final int LOG_INITIAL_SIZE = 7;

    public final static int EMPTY = -1;
    public final static int ABORT = -2;
    private static final Unsafe UNSAFE = WorkStealingUtils.createUnsafe();

    private final AtomicInteger top;
    private final AtomicInteger bottom;
    private volatile CircularArrayChaseLev activeArray;

    public ChaseLevWorkStealing() {
        this.top = new AtomicInteger(0);
        this.bottom = new AtomicInteger(0);
        activeArray = new CircularArrayChaseLev(LOG_INITIAL_SIZE);
    }

    @Override
    public boolean isEmpty() {
        int b = bottom.get();
        int t = top.get();
        return (b - t) <= 0;
    }

    @Override
    public void put(int task) {
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

    @Override
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
    @Override
    public int take() {
        CircularArrayChaseLev a = this.activeArray;
        int b = bottom.get();
        b = b - 1;
        bottom.set(b);
        UNSAFE.loadFence();
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

    @Override
    public boolean put(int task, int label) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int take(int label) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int steal(int label) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
