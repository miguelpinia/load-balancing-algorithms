package org.mx.unam.imate.concurrent.algorithms.idempotent.fifo;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mx.unam.imate.concurrent.datastructures.TaskArrayWithSize;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingFIFO {

    private static final int EMPTY = -1;
    private static final Unsafe unsafe = createUnsafe();

    private TaskArrayWithSize tasks;
    private final AtomicInteger head;
    private final AtomicInteger tail;

    private static Unsafe createUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(Unsafe.class);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(IdempotentWorkStealingFIFO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IdempotentWorkStealingFIFO(int size) {
        this.head = new AtomicInteger(0);
        this.tail = new AtomicInteger(0);
        this.tasks = new TaskArrayWithSize(size);
    }

    public boolean isEmpty() {
        return head.get() == tail.get();
    }

    public void put(int task) {
        int h = head.get();
        int t = tail.get();
        if (t == h + tasks.getSize()) {
            expand();
            put(task);
        }
        unsafe.storeFence();
        tasks.getArray()[t % tasks.getSize()] = task;
        tail.set(t + 1);
    }

    public int take() {
        int h = head.get();
        int t = tail.get();
        if (h == t) {
            return EMPTY;
        }
        int task = tasks.getArray()[h % tasks.getSize()];
        head.set(h + 1);
        return task;
    }

    public int steal() {
        unsafe.loadFence();
        int h = head.get();
        unsafe.loadFence();
        int t = tail.get();
        if (h == t) {
            return EMPTY;
        }
        TaskArrayWithSize a = tasks;
        unsafe.loadFence();
        int task = a.getArray()[h % a.getSize()];
        if (!head.compareAndSet(h, h + 1)) {
            steal();
        }
        return task;
    }

    public void expand() {
        int size = tasks.getSize();
        TaskArrayWithSize a = new TaskArrayWithSize(2 * size);
        unsafe.storeFence();
        int h = head.get();
        int t = tail.get();
        for (int i = h; i < t; i++) {
            a.getArray()[i % a.getSize()] = tasks.getArray()[i % tasks.getSize()];
        }
        unsafe.storeFence();
        tasks = a;
        unsafe.storeFence();
    }

}
