package org.mx.unam.imate.concurrent.algorithms.wsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class WSNCMULTLA implements WorkStealingStruct {

    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

    private static final int TOP = -3;
    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private int tail;
    private final int[] head;

    private final int arrayLength;
    private int nodes;
    private int length;

    private final List<NodeArrayInt> tasks;

    public WSNCMULTLA(int size, int numThreads) {
        this.nodes = 0;
        this.tail = -1;
        this.head = new int[numThreads];
        this.Head = new AtomicInteger(0);
        for (int i = 0; i < numThreads; i++) {
            head[i] = 0;
        }

        // Inicializar valores de la estructura de datos
        tasks = new ArrayList<>();// ArrayList?
        arrayLength = size;
        tasks.add(new NodeArrayInt(size, BOTTOM));
        nodes++;
        length = nodes * arrayLength;
    }

    @Override
    public boolean isEmpty(int label) {
        return head[label] > tail;
    }

    public void expand() {
        tasks.add(new NodeArrayInt(arrayLength, BOTTOM));
        unsafe.storeFence();
        nodes++;
        length = nodes * arrayLength;
        unsafe.storeFence();
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
            int x = tasks.get(node).get(position);
            head[label] = h + 1;
            Head.set(h + 1);
            return x;
        } else {
            return EMPTY;
        }
    }

    @Override
    public int steal(int label) {
        head[label] = Math.max(head[label], Head.get());
        int h = head[label];
        if (h < length) {
            int node = h / arrayLength;
            int position = h % arrayLength;
            int x = tasks.get(node).get(position);
            if (x != BOTTOM) {
                head[label] = h + 1;
                Head.set(h + 1);
                return x;
            }
        }
        return EMPTY;
    }

    private class NodeArrayInt {

        private final int length;
        private final AtomicIntegerArray items;

        public NodeArrayInt(int length, int defaultValue) {
            this.length = length;
            int[] defaultArray = new int[length]; // ¿Crear arreglo y asignarlo en constructor de AtomicIntegerArray?
            Arrays.fill(defaultArray, defaultValue); // ¿O iniciar AtomicIntegerArray y con un ciclo iniciar valores?
            items = new AtomicIntegerArray(defaultArray);
        }

        public boolean setItem(int idx, int value) {
            if (idx < 0 || idx >= length) {
                return false;
            }
            items.set(idx, value);
            return true;
        }

        public int get(int idx) {
            return items.get(idx);
        }

        @Override
        public String toString() {
            return "NodeArrayInt{" + "length=" + length + ", items=" + items.toString() + '}';
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
