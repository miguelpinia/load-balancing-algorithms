package org.mx.unam.imate.concurrent.datastructures;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author miguel
 */
public class DequeCilk {

    private int[] tasks;
    private int head;
    private int tail;
    private final ReentrantLock lock;

    public DequeCilk(int initialSize) {
        this.lock = new ReentrantLock();
        tasks = new int[initialSize];
        head = tail = 0;
    }

    public boolean isEmpty() {
        return head >= tail;
    }

    private void expand() {
        int[] newData = new int[2 * tasks.length];
        System.arraycopy(tasks, 0, newData, 0, tasks.length);
        tasks = newData;
    }

    public void push(int task) {
        if (tail == tasks.length) {
            expand();
            push(task);
        }
        tasks[tail++] = task;
    }

    public int pop() {
        tail--;
        if (head > tail) {
            tail++;
            lock.lock();
            try {
                tail--;
                if (head > tail) {
                    tail++;
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                    return -1;
                }
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
        return tasks[tail];
    }

    public int steal() {
        lock.lock();
        try {
            head++;
            if (head > tail) {
                head--;
                lock.unlock();
                return -1;
            }
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return tasks[head];
    }

}
