package org.mx.unam.imate.concurrent.algorithms.wsm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;

/**
 *
 * @author miguel
 */
public class BWSNCMULTLA implements WorkStealingStruct {

    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private final List<NodeArrayInt> tasks;
    private final List<NodeArrayBool> B;
    private final int arrayLength;
    private int nodes;
    private int length;

    private final int[] head;
    private int tail;

    /**
     * En esta primera versión, el tamaño del arreglo es igual al tamaño de las
     * tareas.
     *
     * @param size El tamaño del arreglo de tareas.
     * @param numThreads
     */
    public BWSNCMULTLA(int size, int numThreads) {
        this.tail = -1;
        this.nodes = 0;
        this.arrayLength = size;
        this.Head = new AtomicInteger(0);
        this.head = new int[numThreads];
        for (int i = 0; i < numThreads; i++) {
            head[i] = 0;
        }
        tasks = new ArrayList<>();
        B = new ArrayList<>();
        tasks.add(new NodeArrayInt(size, BOTTOM));
        B.add(new NodeArrayBool(size));
        nodes++;
        length = nodes * size;
    }

    public void expand() {
        tasks.add(new NodeArrayInt(arrayLength, BOTTOM));
        B.add(new NodeArrayBool(arrayLength));
        nodes++;
        length = nodes * arrayLength;
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
                if (node < tasks.size()) {
                    int x = tasks.get(node).getValue(position);
                    if (x != BOTTOM) {
                        head[label] = h + 1;
                        if (B.get(node).getSwap(position).getAndSet(false)) {
                            Head.set(h + 1);
                            return x;
                        }
                    }
                }
                return EMPTY;
            } else {
                return EMPTY;
            }
        }
    }

    private class NodeArrayInt {

        private final int length;
        private final int[] items;

        public NodeArrayInt(int length, int defaultValue) {
            this.length = length;
            int[] defaultArray = new int[length]; // ¿Crear arreglo y asignarlo en constructor de AtomicIntegerArray?
            for (int i = 0; i < length; i++) {
                defaultArray[i] = defaultValue;
            }
            items = defaultArray;
        }

        public boolean setItem(int idx, int value) {
            if (idx < 0 || idx >= length) {
                return false;
            }
            items[idx] = value;
            return true;
        }

        public int getValue(int idx) {
            return items[idx];
        }

    }

    private class NodeArrayBool {

        private final int length;
        private final AtomicBoolean[] B;

        public NodeArrayBool(int length) {
            this.length = length;
            B = new AtomicBoolean[length];
            for (int i = 0; i < length; i++) {
                B[i] = new AtomicBoolean(true);
            }
        }

        public boolean setItem(int idx, boolean value) {
            if (idx < 0 || idx >= length) {
                return false;
            }
            B[idx].set(value);
            return true;
        }

        public AtomicBoolean getSwap(int idx) {
            return B[idx];
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

}
