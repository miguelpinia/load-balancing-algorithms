package org.mx.unam.imate.concurrent.algorithms.idempotent.deque;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import org.mx.unam.imate.concurrent.datastructures.TaskArrayWithSize;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingDeque implements WorkStealingStruct {

    private static final int EMPTY = -1;
    private static final int MAX_SIZE = 0xFFFFFF;
    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

    private TaskArrayWithSize tasks;
    private final Triplet anchor;

    public IdempotentWorkStealingDeque(int size) {
        this.tasks = new TaskArrayWithSize(size);
        this.anchor = new Triplet(0, 0, 0);
    }

    private boolean casAnchor(Triplet oldValue, Triplet newVal) {
        boolean preCond = false;
        synchronized (anchor) {
            preCond = anchor.equals(oldValue);
            if (preCond) {
                anchor.set(newVal.getHead(), newVal.getSize(), newVal.getTag());
            }
        }
        return preCond;
    }

    @Override
    public void put(int task) {
        Triplet oldReference = anchor;
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        if (s == tasks.getSize()) {
            expand();
            put(task);
        }
        unsafe.storeFence();
        tasks.getArray()[(h + s) % tasks.getSize()] = task;
        anchor.set(h, s + 1, g + 1);
    }

    @Override
    public boolean isEmpty() {
        return anchor.getSize() <= 0;
    }

    @Override
    public int take() {
        Triplet oldReference = anchor;
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        if (s == 0) {
            return EMPTY;
        }
        int task = tasks.getArray()[(h + s - 1) % tasks.getSize()];
        anchor.set(h, s - 1, g);
        return task;
    }

    @Override
    public int steal() {
        Triplet oldReference = anchor;
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        unsafe.loadFence();
        if (s == 0) {
            return EMPTY;
        }
        TaskArrayWithSize a = tasks;
        int task = a.getArray()[h % a.getSize()];
        unsafe.loadFence();
        int h2 = h + 1 % MAX_SIZE;
        Triplet newReference = new Triplet(h2, s - 1, g);
        if (!casAnchor(oldReference, newReference)) {
            steal();
        }
        return task;
    }

    public void expand() {
        Triplet oldReference = anchor;
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        unsafe.storeFence();
        TaskArrayWithSize a = new TaskArrayWithSize(2 * s);
        unsafe.storeFence();
        for (int i = 0; i < s; i++) {
            a.getArray()[(h + i) % a.getSize()] = tasks.getArray()[(h + i) % tasks.getSize()];
        }
        unsafe.storeFence();
        tasks = a;
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

    class Triplet {

        private int head;
        private int size;
        private int tag;

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

        public void set(int head, int size, int tag) {
            this.head = head;
            this.size = size;
            this.tag = tag;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Triplet other = (Triplet) obj;
            if (this.head != other.head) {
                return false;
            }
            if (this.size != other.size) {
                return false;
            }
            return this.tag == other.tag;
        }

    }

}
