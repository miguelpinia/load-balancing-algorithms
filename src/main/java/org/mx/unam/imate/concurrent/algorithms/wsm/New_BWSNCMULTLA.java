package org.mx.unam.imate.concurrent.algorithms.wsm;

import java.util.ArrayList;
import java.util.List;
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
public class New_BWSNCMULTLA implements WorkStealingStruct {

    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private final List<NodeArrayItem> tasks;
    private final int arrayLength;
    private int nodes;
    private int length;
    private final int[] head;
    private int tail;

    public New_BWSNCMULTLA(int size, int numThreads) {
        this.tail = -1;
        this.nodes = 0;
        this.arrayLength = size;
        this.Head = new AtomicInteger(0);
        this.head = new int[numThreads];
        for (int i = 0; i < numThreads; i++) {
            head[i] = 0;
        }
        tasks = new ArrayList<>();
        tasks.add(new NodeArrayItem(arrayLength, BOTTOM));
        nodes++;
        length = nodes * arrayLength;
    }

    public void expand() {
        tasks.add(new NodeArrayItem(arrayLength, BOTTOM));
        unsafe.storeFence();
        nodes++;
        length = nodes * arrayLength;
        unsafe.storeFence();
    }

    @Override
    public boolean isEmpty(int label) {
        return head[label] > tail;
    }

    @Override
    public boolean put(int task, int label) {
        if (tail == (length - 1)) {
            expand();
        }
        tail++;
        tasks.get(nodes - 1).setItem(tail % arrayLength, task);
        return true;
    }

    @Override
    public int take(int label) {
        head[label] = Math.max(head[label], Head.get());
        int h = head[label];
        if (h <= tail) {
            int node = h / arrayLength;
            int position = h % arrayLength;
            int x = tasks.get(node).getValue(position);
            Head.set(h + 1);
            head[label] = h + 1;
            return x;
        } else {
            return EMPTY;
        }
    }

    @Override
    public int steal(int label) {
        while (true) {
            int h = Math.max(head[label], Head.get());
            if (h < length) {
                int node = h / arrayLength;
                int position = h % arrayLength;
                int x = tasks.get(node).getValue(position);
                if (x != BOTTOM) {
                    head[label] = h + 1;
                    if (tasks.get(node).getSwap(position).getAndSet(false)) {
                        Head.set(h + 1);
                        return x;
                    }
                } else {
                    return EMPTY;
                }
            } else {
                return EMPTY;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void put(int task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int take() {
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

    private class NodeArrayItem {

        private final int length;
        private final AtomicReferenceArray<Item> tasks;

        public NodeArrayItem(int length, int defaultValue) {
            this.length = length;
            Item[] array = new Item[length];
            for (int i = 0; i < length; i++) {
                array[i] = new Item(true, defaultValue);
            }
            tasks = new AtomicReferenceArray<>(array);
        }

        public boolean setItem(int idx, int value) {
            if (idx < 0 || idx  >= length) {
                return false;
            }
            tasks.get(idx).setValue(value);
            return true;
        }

        public int getValue(int idx) {
            return tasks.get(idx).getValue();
        }

        public AtomicBoolean getSwap(int idx) {
            return tasks.get(idx).getSwap();
        }
    }

}
