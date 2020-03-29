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

    private volatile int tail;
    private volatile int head;

    /**
     * En esta primera versión, el tamaño del arreglo es igual al tamaño de las
     * tareas.
     *
     * @param size El tamaño del arreglo de tareas.
     */
    public FIFOWorkStealingV1(int size) {
        this.tail = 0;
        this.head = 1;
        this.Tail = new AtomicInteger(0);
        this.Head = new AtomicInteger(1);
        int array[] = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = BOTTOM;
        }
        this.Tasks = new AtomicIntegerArray(array); // Inicializar las tareas a bottom.
    }

    public boolean isEmpty() {
        return Tail.get() < Head.get();
    }

    public boolean put(int task) {
        tail = tail + 1;
        Tasks.set(tail, task); // Equivalent to Tasks[tail].write(task)
        Tail.set(tail);
        return true;
    }

    public int take() {
        if (head > tail) {
            return EMPTY;
        }
        int r = head;
        int x;
        while (r <= tail) {
            x = Tasks.get(r);
            if (x != BOTTOM && x != TOP) {
                head = r + 1;
                Tasks.set(r, TOP);
                Head.set(head);
                return x;
            }
            r++;
        }
        head = tail + 1;
        Head.set(head);
        return EMPTY;
    }

    public int steal() {
        head = Math.max(head, Head.get());
        tail = Tail.get();
        if (head > tail) {
            return EMPTY;
        }
        int r = head;
        int x;
        while (r <= tail) {
            x = Tasks.get(r);
            if (x != BOTTOM && x != TOP) {
                head = r + 1;
                Tasks.set(r, TOP);
                return x;
            }
            r++;
        }
        return EMPTY;
    }

}
