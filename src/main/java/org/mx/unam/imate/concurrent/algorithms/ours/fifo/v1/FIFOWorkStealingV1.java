package org.mx.unam.imate.concurrent.algorithms.ours.fifo.v1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 *
 * @author miguel
 */
public class FIFOWorkStealingV1 {

    private static final int TOP = -3;
    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private final AtomicInteger Tail;
    private final AtomicIntegerArray Tasks;

    private int[] tail;
    private int[] head;

    /**
     * En esta primera versión, el tamaño del arreglo es igual al tamaño de las
     * tareas.
     *
     * @param size El tamaño del arreglo de tareas.
     */
    public FIFOWorkStealingV1(int size, int numThreads) {
        this.tail = new int[numThreads + 1];
        this.head = new int[numThreads + 1];
        this.Tail = new AtomicInteger(0);
        this.Head = new AtomicInteger(1);
        int array[] = new int[size];
        for (int i = 0; i < numThreads; i++) {
            tail[i] = 0;
            head[i] = 1;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = BOTTOM;
        }
        this.Tasks = new AtomicIntegerArray(array); // Inicializar las tareas a bottom.
    }

    public boolean isEmpty() {
        return Head.get() > Tail.get();
    }

    public boolean put(int task, int label) {
        label--;
        tail[label] = tail[label] + 1;
        Tasks.set(tail[label], task); // Equivalent to Tasks[tail].write(task)
        Tail.set(tail[label]);
        return true;
    }

    public int take(int label) {
        label--;
        if (head[label] > tail[label]) {
            return EMPTY;
        }
        int r = head[label];
        int x;
        while (r <= tail[label]) {
            x = Tasks.get(r);
            if (x != BOTTOM && x != TOP) {
                head[label] = r + 1;
                Tasks.set(r, TOP);
                Head.set(head[label]);
                return x;
            }
            r++;
        }
        head[label] = tail[label] + 1;
        Head.set(head[label]);
        return EMPTY;
    }

    public int steal(int label) {
        label--;
        head[label] = Math.max(head[label], Head.get());
        tail[label] = Tail.get();
        if (head[label] > tail[label]) {
            return EMPTY;
        }
        int r = head[label];
        int x;
        while (r <= tail[label]) {
            x = Tasks.get(r);
            if (x != BOTTOM && x != TOP) {
                head[label] = r + 1;
                Tasks.set(r, TOP);
                return x;
            }
            r++;
        }
        return EMPTY;
    }

}
