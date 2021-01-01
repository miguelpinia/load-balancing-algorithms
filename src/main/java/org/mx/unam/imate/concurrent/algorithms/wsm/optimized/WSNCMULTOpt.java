package org.mx.unam.imate.concurrent.algorithms.wsm.optimized;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;

/**
 *
 * @author miguel
 */
public class WSNCMULTOpt implements WorkStealingStruct {

    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private int[] Tasks;

    private int tail;
    private final int[] head;

    /**
     * En esta primera versión, el tamaño del arreglo es igual al tamaño de las
     * tareas.
     *
     * @param size El tamaño del arreglo de tareas.
     * @param numThreads
     */
    public WSNCMULTOpt(int size, int numThreads) {
        this.tail = -1;
        this.head = new int[numThreads];
        this.Head = new AtomicInteger(0);
        Arrays.fill(head, 0);
        this.Tasks = new int[size]; // Inicializar las tareas a bottom.
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean put(int task, int label) {
        if (tail == Tasks.length - 1) {
            expand();
        }
        if (tail <= Tasks.length - 3) {
            Tasks[tail + 1] = BOTTOM;
            Tasks[tail + 2] = BOTTOM;
        }
        tail++;
        Tasks[tail] = task; // Equivalent to Tasks[tail].write(task)
        return true;
    }

    @Override
    public int take(int label) {
        head[label] = Math.max(head[label], Head.get());
        if (head[label] <= tail) {
            int x = Tasks[head[label]];
            head[label]++;
            Head.set(head[label]);
            return x;
        }
        return EMPTY;
    }

    @Override
    public int steal(int label) {
        head[label] = Math.max(head[label], Head.get());
        if (head[label] <= tail) {
            int x = Tasks[head[label]];
            if (x != BOTTOM) {
                head[label]++;
                Head.set(head[label]);
                return x;
            }
        }
        return EMPTY;
    }

    @Override
    public void put(int task) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void expand() {
        int array[] = new int[2 * Tasks.length];
        System.arraycopy(Tasks, 0, array, 0, Tasks.length);
        Tasks = array;
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
