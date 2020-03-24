package org.mx.unam.imate.concurrent.algorithms.idempotent.deque;

import java.util.concurrent.atomic.AtomicReference;

import org.mx.unam.imate.concurrent.datastructures.TaskArrayWithSize;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingDeque {

    private static final int EMPTY = -1;
    private static final int MAX_SIZE = 0xFFFF;

    private TaskArrayWithSize tasks;
    private final AtomicReference<Triplet> anchor;

    public IdempotentWorkStealingDeque(int size) {
        this.tasks = new TaskArrayWithSize(size);
        this.anchor = new AtomicReference<>(new Triplet(0, 0, 0));
    }

    public void put(int task) {
        Triplet oldReference = anchor.get();
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        if (s == tasks.getSize()) {
            expand();
            put(task);
        }
        tasks.getArray()[(h + s) % tasks.getSize()] = task;
        anchor.set(new Triplet(h, s + 1, g + 1));
    }

    public int take() {
        Triplet oldReference = anchor.get();
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        if (s == 0) {
            return EMPTY;
        }
        int task = tasks.getArray()[(h + s - 1) % tasks.getSize()];
        anchor.set(new Triplet(h, s - 1, g));
        return task;
    }

    public int steal() {
        Triplet oldReference = anchor.get();
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        if (s == 0) {
            return EMPTY;
        }
        TaskArrayWithSize a = tasks;
        int task = a.getArray()[h % a.getSize()];
        int h2 = h + 1 % MAX_SIZE;
        Triplet newReference = new Triplet(h2, s - 1, g);
        if (!anchor.compareAndSet(oldReference, oldReference)) {
            steal(); // Ciclo infinito?
        }
        return task;
    }

    public void expand() {
        Triplet oldReference = anchor.get();
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        TaskArrayWithSize a = new TaskArrayWithSize(2 * s);
        for (int i = 0; i < s; i++) {
            a.getArray()[(h + i) % a.getSize()] = tasks.getArray()[(h + i) % tasks.getSize()];
        }
        tasks = a;
    }

    class Triplet {

        private final int head;
        private final int size;
        private final int tag;

        public Triplet(int head, int size, int tag) {
            this.head = head;
            this.size = size;
            this.tag = tag;
        }

        public int getHead() {
            return head;
        }

        public int getSize() {
            return size;
        }

        public int getTag() {
            return tag;
        }

    }

}
