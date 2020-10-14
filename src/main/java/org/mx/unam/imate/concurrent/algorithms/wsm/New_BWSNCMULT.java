package org.mx.unam.imate.concurrent.algorithms.wsm;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class New_BWSNCMULT implements WorkStealingStruct {

    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private final AtomicInteger Tail;
    private AtomicReferenceArray<Item> tasks;

    private final int[] head;
    private int tail;

    public New_BWSNCMULT(int size, int numThreads) {
        tail = 0;
        head = new int[numThreads];
        Head = new AtomicInteger(1);
        Tail = new AtomicInteger(0);
        Item[] array = new Item[size];
        for (int i = 0; i < size; i++) {
            array[i] = new Item(true, BOTTOM);
        }
        tasks = new AtomicReferenceArray<>(array);
    }

    @Override
    public boolean isEmpty(int label) {
        return head[label] > tail;
    }

    @Override
    public boolean put(int task, int label) {
        if (tail == tasks.length() - 1) {
            expand();
            put(task, label);
        }
        tail = tail + 1;
        try {
            tasks.get(tail).setValue(task); // Equivalent to Tasks[tail].write(task)
        } catch (NullPointerException ex) {
            System.out.println("Error!!!!! " + tasks.get(tail) + ", tail: " + tail);
        }

        return true;
    }

    @Override
    public int take(int label) {
        head[label] = Math.max(head[label], Head.get());
        if (head[label] <= tail) {
            int x = tasks.get(head[label]).getValue();
            Head.set(head[label] + 1);
            head[label]++;
            return x;
        } else {
            return EMPTY;
        }
    }

    @Override
    public int steal(int label) {
        while (true) {
            head[label] = Math.max(head[label], Head.get());
            if (head[label] < tasks.length()) {
                int x = tasks.get(head[label]).getValue();
                if (x != BOTTOM) {
                    int h = head[label];
                    head[label]++;
                    if (tasks.get(h).getSwap().getAndSet(false)) {
                        Head.set(head[label]);
                        return x;
                    }
                } else {
                    return EMPTY;
                }
            }
        }
    }

    public void expand() {
        int size = tasks.length();
        Item[] array = new Item[size * 2];
        for (int i = 0; i < size * 2; i++) {
            array[i] = new Item(true, BOTTOM);
        }
        AtomicReferenceArray<Item> a = new AtomicReferenceArray<>(array);
        unsafe.storeFence();

        for (int i = 0; i < size; i++) {
            a.get(i).getSwap().set(tasks.get(i).getSwap().get());
            a.get(i).setValue(tasks.get(i).getValue());
            unsafe.storeFence();
        }
        tasks = a;
        unsafe.storeFence();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int take() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void put(int task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int steal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class Item {

        private final AtomicBoolean swap;
        private Integer value;

        public Item(boolean swap, int value) {
            this.swap = new AtomicBoolean(swap);
            this.value = value;
        }

        public AtomicBoolean getSwap() {
            return swap;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

    }

}
