package org.mx.unam.imate.concurrent.algorithms.ours.fifo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;

/**
 *
 * @author miguel
 */
public class FIFOWorkStealingV2 implements WorkStealingStruct {

    private static final int TOP = -3;
    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private final AtomicInteger Tail;
    private final AtomicIntegerArray Tasks;

    private volatile int[] tail;
    private volatile int[] head;

    public FIFOWorkStealingV2(int size, int numThreads) {
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
        int x = BOTTOM;
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
        if (x == BOTTOM) {
            head[label] = tail[label];
        } else {
            head[label] = tail[label] + 1;
        }
        Head.set(head[label]);
        return EMPTY;
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

}
