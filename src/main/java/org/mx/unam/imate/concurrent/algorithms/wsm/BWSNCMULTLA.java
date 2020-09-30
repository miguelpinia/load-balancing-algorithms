package org.mx.unam.imate.concurrent.algorithms.wsm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class BWSNCMULTLA implements WorkStealingStruct {

    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

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
     * @param arrayLength El tamaño del arreglo de tareas.
     * @param numThreads
     */
    public BWSNCMULTLA(int arrayLength, int numThreads) {
        this.tail = 0;
        this.head = new int[numThreads];
        this.Head = new AtomicInteger(1);
        this.nodes = 0;
        this.arrayLength = arrayLength;
        for (int i = 0; i < numThreads; i++) {
            head[i] = 1;
        }

        tasks = new ArrayList<>();
        B = new ArrayList<>();
        tasks.add(new NodeArrayInt(arrayLength, BOTTOM));
        B.add(new NodeArrayBool(arrayLength));
        nodes++;
        length = nodes * arrayLength;
    }

    @Override
    public boolean isEmpty(int label) {
        return head[label] > tail;
    }

    public void expand() {
        tasks.add(new NodeArrayInt(arrayLength, BOTTOM));
        B.add(new NodeArrayBool(arrayLength));
        nodes = nodes + 1;
        length = nodes * arrayLength;
        unsafe.storeFence();
    }

    @Override
    public boolean put(int task, int label) {
        if (tail == length) {
            expand();
            return put(task, label);
        }
        tail = tail + 1;
        tasks.get(nodes - 1).setItem(tail % arrayLength, task);
        return true;
    }

    @Override
    public int take(int label) {
        head[label] = Math.max(head[label], Head.get());
        if (head[label] <= tail) {
            int h = head[label];
            int node = h / arrayLength;
            int position = h % arrayLength;
            int x = tasks.get(node).get(position);
            Head.set(h + 1);
            head[label] = h + 1;
            return x;
        } else {
            return EMPTY;
        }
    }

    @Override
    public int steal(int label) {
        int h = 0;
        while (true) {
            h = Math.max(head[label], Head.get());
            int node = h / arrayLength;
            int position = h % arrayLength;
            int x = tasks.get(node).get(position);
            if (x != BOTTOM) {
                head[label] = h + 1;
                if (B.get(node).getAtomicBoolean(position).getAndSet(false)) {
                    Head.set(h + 1);
                    return x;
                }
            } else {
                return EMPTY;
            }
        }
    }

    private class NodeArrayInt {

        private final int length;
        private final AtomicIntegerArray items;

        public NodeArrayInt(int length, int defaultValue) {
            this.length = length;
            int[] defaultArray = new int[length]; // ¿Crear arreglo y asignarlo en constructor de AtomicIntegerArray?
            for (int i = 0; i < length; i++) {
                defaultArray[i] = defaultValue;
            }
            items = new AtomicIntegerArray(defaultArray);
        }

        public boolean setItem(int idx, int value) {
            if (idx <= 0 || idx >= length) {
                return false;
            }
            items.set(idx, value);
            return true;
        }

        public int get(int idx) {
            return items.get(idx);
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
            if (idx <= 0 || idx >= length) {
                return false;
            }
            B[idx].set(value);
            return true;
        }

        public AtomicBoolean getAtomicBoolean(int idx) {
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
