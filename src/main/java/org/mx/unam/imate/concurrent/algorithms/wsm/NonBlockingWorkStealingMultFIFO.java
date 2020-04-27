package org.mx.unam.imate.concurrent.algorithms.wsm;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class NonBlockingWorkStealingMultFIFO implements WorkStealingStruct {

    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

    private static final int TOP = -3;
    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private final AtomicInteger Tail;
    private final AtomicIntegerArray Tasks;

    private final int[] tail;
    private final int[] head;

    /**
     * En esta primera versión, el tamaño del arreglo es igual al tamaño de las
     * tareas.
     *
     * @param size El tamaño del arreglo de tareas.
     * @param numThreads
     */
    public NonBlockingWorkStealingMultFIFO(int size, int numThreads) {
        this.tail = new int[numThreads + 1];
        this.head = new int[numThreads + 1];
        this.Tail = new AtomicInteger(0);
        this.Head = new AtomicInteger(1);
        int array[] = new int[size + 1]; // Si no, se rompe
        for (int i = 0; i < numThreads; i++) {
            tail[i] = 0;
            head[i] = 1;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = BOTTOM;
        }
        this.Tasks = new AtomicIntegerArray(array); // Inicializar las tareas a bottom.
    }

    @Override
    public boolean isEmpty() {
        return Head.get() > Tail.get();
    }

    @Override
    public boolean put(int task, int label) {
        tail[label] = tail[label] + 1;
        Tasks.set(tail[label], task); // Equivalent to Tasks[tail].write(task)
        Tail.set(tail[label]);
        return true;
    }

    @Override
    public int take(int label) {
        head[label] = Math.max(head[label], Head.get());
        int x = TOP;
        while (head[label] <= tail[label]) {
            x = Tasks.get(head[label]);
            if (x != TOP) {
                Tasks.set(head[label], TOP);
                head[label]++;
                break;
            }
            head[label]++;
        }
        Head.set(head[label]);
        if (x != TOP) {
            return x;
        } else {
            return EMPTY;
        }
    }

    @Override
    public int steal(int label) {
        head[label] = Math.max(head[label], Head.get());
        tail[label] = Tail.get();
        while (true) {
            int x = TOP;
            while (head[label] <= tail[label]) {
                x = Tasks.get(head[label]);
                if (x != BOTTOM && x != TOP) {
                    Tasks.set(head[label], TOP);
                    head[label]++;
                    Head.set(head[label]);
                    return x;
                }
                head[label]++;
            }
            int ntail = Tail.get();
            unsafe.loadFence();
            if (tail[label] == ntail && x == TOP) {
                Head.set(head[label]);
                return EMPTY;
            } else {
                tail[label] = ntail;
                if (x == BOTTOM) {
                    head[label]--;
                }
            }
        }

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
        return Head.get() > Tail.get();
    }
}
