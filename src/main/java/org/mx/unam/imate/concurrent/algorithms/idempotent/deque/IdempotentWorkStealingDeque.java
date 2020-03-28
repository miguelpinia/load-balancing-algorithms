package org.mx.unam.imate.concurrent.algorithms.idempotent.deque;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;
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
    private static final int MAX_SIZE = 0xFFFF;
    private static final Unsafe unsafe = createUnsafe();

    private TaskArrayWithSize tasks;
    private final AtomicReference<Triplet> anchor;

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
        unsafe.storeFence();
        anchor.set(new Triplet(h, s + 1, g + 1));
    }

    public boolean isEmpty() {
        return anchor.get().getSize() == 0;
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
        unsafe.loadFence();
        Triplet oldReference = anchor.get();
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        if (s == 0) {
            return EMPTY;
        }
        TaskArrayWithSize a = tasks;
        unsafe.loadFence();
        int task = a.getArray()[h % a.getSize()];
        int h2 = h + 1 % MAX_SIZE;
        Triplet newReference = new Triplet(h2, s - 1, g);
        if (!anchor.compareAndSet(oldReference, newReference)) {
            steal();
        }
        return task;
    }

    public void expand() {
        Triplet oldReference = anchor.get();
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        TaskArrayWithSize a = new TaskArrayWithSize(2 * s);
        unsafe.storeFence();
        for (int i = 0; i < s; i++) {
            a.getArray()[(h + i) % a.getSize()] = tasks.getArray()[(h + i) % tasks.getSize()];
        }
        unsafe.storeFence();
        tasks = a;
        unsafe.storeFence();
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
