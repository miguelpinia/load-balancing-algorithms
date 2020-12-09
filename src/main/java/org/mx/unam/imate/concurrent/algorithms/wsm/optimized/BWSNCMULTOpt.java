/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.wsm.optimized;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class BWSNCMULTOpt implements WorkStealingStruct {
    
    private static final Unsafe unsafe = WorkStealingUtils.createUnsafe();

    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private int[] Tasks;
    private AtomicBoolean[] B;

    private final int[] head;
    private int tail;

    /**
     * En esta primera versión, el tamaño del arreglo es igual al tamaño de las
     * tareas.
     *
     * @param size El tamaño del arreglo de tareas.
     * @param numThreads
     */
    public BWSNCMULTOpt(int size, int numThreads) {
        this.tail = -1;
        this.head = new int[numThreads];
        this.Head = new AtomicInteger(0);
        this.B = new AtomicBoolean[size];
        Arrays.fill(head, 0);
        this.Tasks = new int[size]; // Inicializar las tareas a bottom.
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean put(int task, int label) {
        if (tail == Tasks.length - 1) {
            expand();
        }
        if (tail <= Tasks.length - 3) {
            Tasks[tail + 1] = BOTTOM; 
            Tasks[tail + 2] = BOTTOM;
            B[tail + 1] = new AtomicBoolean(true);
            B[tail + 2] = new AtomicBoolean(true);
        }
        tail++;
        Tasks[tail] = task; // Equivalent to Tasks[tail].write(task)
        return true;
    }

    @Override
    public int take(int label) {
        head[label] = Math.max(head[label], Head.get());
        if (head[label] <= tail) {
            int x = Tasks[head[label]];
            head[label]++;
            Head.set(head[label]);
            return x;
        }
        return EMPTY;
    }

    @Override
    public int steal(int label) {
        while (true) {
            head[label] = Math.max(head[label], Head.get());
            if (head[label] <= tail) {
                int x = Tasks[head[label]];
                if (x != BOTTOM) {
                    int h = head[label];
                    head[label]++;
                    if (B[h].getAndSet(false)) {
                        Head.set(head[label]);
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

    public void expand() {
        int size = Tasks.length;
        int array[] = new int[size * 2];
        Arrays.fill(array, BOTTOM);
        AtomicBoolean[] b = new AtomicBoolean[size * 2];
        unsafe.storeFence();
        for (int i = 0; i < b.length; i++) {
            b[i] = new AtomicBoolean(true);
            unsafe.storeFence();
        }
        for (int i = 0; i < size; i++) {
            array[i] = Tasks[i];
            b[i] = B[i];
            unsafe.storeFence();
        }
        Tasks = array;
        B = b;
        unsafe.storeFence();
    }

    @Override
    public void put(int task) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int take() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int steal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty(int label) {
        return head[label] > tail;
    }
    
}
