package org.mx.unam.imate.concurrent.algorithms.wsm;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class BoundedWorkStealingNonConcurrentMultFIFO implements WorkStealingStruct {

    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

    private static final int ABORT = - 4;
    private static final int TOP = -3;
    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private final AtomicInteger Tail;
    private final AtomicReferenceArray<Pair> Tasks;

    private final int[] tail;
    private final int[] head;

    /**
     * En esta primera versión, el tamaño del arreglo es igual al tamaño de las
     * tareas.
     *
     * @param size El tamaño del arreglo de tareas.
     * @param numThreads
     */
    public BoundedWorkStealingNonConcurrentMultFIFO(int size, int numThreads) {
        this.tail = new int[numThreads + 1];
        this.head = new int[numThreads + 1];
        this.Tail = new AtomicInteger(0);
        this.Head = new AtomicInteger(1);
        Pair array[] = new Pair[size];
        for (int i = 0; i < numThreads; i++) {
            tail[i] = 0;
            head[i] = 1;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = new Pair();
        }
        this.Tasks = new AtomicReferenceArray<>(array); // Inicializar las tareas a bottom.
    }

    @Override
    public boolean isEmpty() {
        return Head.get() > Tail.get();
    }

    @Override
    public boolean put(int task, int label) {
        label--;
        tail[label]++;
        Tasks.set(tail[label], new Pair(task, State.TRUE)); // Equivalent to Tasks[tail].write(task)
        Tail.set(tail[label]);
        return true;
    }

    @Override
    public int take(int label) {
        label--;
        head[label] = Math.max(head[label], Head.get());
        if (head[label] <= tail[label]) {
            Pair p = Tasks.get(head[label]);
            if (p.getTask() >= 0 && p.getState() == State.TRUE) {
                p = Tasks.getAndSet(head[label], new Pair(p.getTask(), State.FALSE));
                if (p.getState() != State.FALSE) {
                    Head.set(head[label] + 1);
                }
            }
            head[label]++;
            return p.getTask();
        } else {
            return EMPTY;
        }
    }

    @Override
    public int steal(int label) {
        label--;
        head[label] = Math.max(head[label], Head.get());
        tail[label] = Tail.get();
        while (true) {
            Pair p = new Pair(BOTTOM, State.FALSE);
            while (head[label] <= tail[label]) {
                p = Tasks.get(head[label]);
                if (p.getTask() >= 0 && p.getState() == State.TRUE) {
                    p = Tasks.getAndSet(head[label], new Pair(p.getTask(), State.FALSE));
                    if (p.getState() != State.FALSE) {
                        head[label]++;
                        Head.set(head[label]);
                        return p.getTask();
                    }
                }
                head[label]++;
            }
            int ntail = Tail.get();
            unsafe.loadFence();
            if (tail[label] == ntail) {
                Head.set(head[label]);
                return EMPTY;
            } else {
                tail[label] = ntail;
                if (p.getTask() == BOTTOM && p.getState() == State.BOTTOM) {
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

    enum State {
        TRUE, FALSE, BOTTOM
    }

    class Pair {

        private final int task;
        private final State state;

        public Pair() {
            task = BOTTOM;
            state = State.BOTTOM;
        }

        public Pair(int task, State state) {
            this.task = task;
            this.state = state;
        }

        public int getTask() {
            return task;
        }

        public State getState() {
            return state;
        }
    }

}
