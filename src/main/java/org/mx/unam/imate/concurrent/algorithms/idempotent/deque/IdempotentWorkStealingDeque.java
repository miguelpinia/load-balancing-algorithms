package org.mx.unam.imate.concurrent.algorithms.idempotent.deque;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mx.unam.imate.concurrent.datastructures.TaskArrayWithSize;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingDeque {

    private static final int EMPTY = -1;
    private static final int MAX_SIZE = 0xFFFFFF;
    private static final Unsafe unsafe = createUnsafe();

    private TaskArrayWithSize tasks;
    private Triplet anchor;

    private static Unsafe createUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(Unsafe.class);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(IdempotentWorkStealingDeque.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IdempotentWorkStealingDeque(int size) {
        this.tasks = new TaskArrayWithSize(size);
        this.anchor = new Triplet(0, 0, 0);
    }

    private boolean casAnchor(Triplet oldValue, Triplet newVal) {
        boolean preCond = false;
        synchronized (anchor) {
            preCond = anchor.equals(oldValue);
            if (preCond) {
                anchor = newVal;
            }
        }
        return preCond;
    }

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
        anchor = new Triplet(h, s + 1, g + 1);
    }

    public boolean isEmpty() {
        return anchor.getSize() <= 0;
    }

    public int take() {
        Triplet oldReference = anchor;
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        if (s == 0) {
            return EMPTY;
        }
        int task = tasks.getArray()[(h + s - 1) % tasks.getSize()];
        anchor = new Triplet(h, s - 1, g);
        return task;
    }

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
