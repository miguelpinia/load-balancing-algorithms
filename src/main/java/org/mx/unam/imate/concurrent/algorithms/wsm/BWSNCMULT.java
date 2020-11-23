package org.mx.unam.imate.concurrent.algorithms.wsm;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class BWSNCMULT implements WorkStealingStruct {

    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private final AtomicInteger Tail;
    private AtomicIntegerArray Tasks;
    private AtomicBoolean[] B;

    private final int[] head;
    private int tail;

    /**
     * En esta primera versión, el tamaño del arreglo es igual al tamaño de las
     * tareas.
     *
     * @param size El tamaño del arreglo de tareas.
     * @param numThreads
     */
    public BWSNCMULT(int size, int numThreads) {
        this.tail = 0;
        this.head = new int[numThreads];
        this.Tail = new AtomicInteger(0);
        this.Head = new AtomicInteger(1);
        int array[] = new int[size];
        this.B = new AtomicBoolean[size];
        for (int i = 0; i < numThreads; i++) {
            head[i] = 1;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = BOTTOM;
            B[i] = new AtomicBoolean(true);
        }
        this.Tasks = new AtomicIntegerArray(array); // Inicializar las tareas a bottom.
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean put(int task, int label) {
        if (tail == Tasks.length() - 1) {
            expand();
        }
        tail++;
        Tasks.set(tail, task); // Equivalent to Tasks[tail].write(task)
        return true;
    }

    @Override
    public int take(int label) {
        head[label] = Math.max(head[label], Head.get());
        if (head[label] <= tail) {
            int x = Tasks.get(head[label]);
            head[label]++;
            Head.set(head[label]);
            return x;
        }
        return EMPTY;
    }

    @Override
    public int steal(int label) {
        while (true) {
            head[label] = Math.max(head[label], Head.get());
            if (head[label] < Tasks.length()) {
                int x = Tasks.get(head[label]);
                if (x != BOTTOM) {
                    int h = head[label];
                    head[label]++;
                    if (B[h].getAndSet(false)) {
                        Head.set(head[label]);
                        return x;
                    }
                } else {
                    return EMPTY;
                }
            }
        }
    }

    public void expand() {
        int size = Tasks.length();
        int array[] = new int[size * 2];
        Arrays.fill(array, BOTTOM);
        AtomicIntegerArray a = new AtomicIntegerArray(array);
        AtomicBoolean[] b = new AtomicBoolean[size * 2];
        unsafe.storeFence();
        for (int i = 0; i < b.length; i++) {
            b[i] = new AtomicBoolean(true);
        }
        unsafe.storeFence();
        for (int i = 0; i < size; i++) {
            a.set(i, Tasks.get(i));
            b[i] = B[i];
        }
        unsafe.storeFence();
        Tasks = a;
        B = b;
        unsafe.storeFence();
    }

    @Override
    public void put(int task) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int take() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int steal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty(int label) {
        return head[label] > tail;
    }

}
