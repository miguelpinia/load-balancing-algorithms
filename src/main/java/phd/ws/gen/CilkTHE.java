/*
 */
package phd.ws.gen;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author miguel
 */
public class CilkTHE<T> implements WSStruct<T> {

    private final AtomicInteger H;
    private final AtomicInteger T;
    private AtomicReferenceArray<T> tasks;
    private final ReentrantLock lock;

    public CilkTHE(int initialSize) {
        lock = new ReentrantLock(true);
        tasks = new AtomicReferenceArray<>(initialSize);
        H = new AtomicInteger(0);
        T = new AtomicInteger(0);
    }

    @Override
    public boolean isEmpty() {
        int tail = T.get();
        int head = H.get();
        return head >= tail;
    }

    @Override
    public int size() {
        return T.get() - H.get();
    }

    public void expand() {
        AtomicReferenceArray<T> newData = new AtomicReferenceArray<>(2 * tasks.length());
        for (int i = 0; i < tasks.length(); i++) {
            newData.set(i, tasks.get(i));
        }
        tasks = newData;
    }

    @Override
    public void put(T task) {
        int tail = T.get();
        if (tail >= tasks.length()) {
            expand();
            put(task);
            return;
        }
        tasks.set(tail % tasks.length(), task);
        T.set(tail + 1);
    }

    @Override
    public T take() {
        int t = T.get() - 1;
        T.set(t);
        VarHandle.fullFence();
        int h = H.get();
        if (t > h) {
            return tasks.get(t % tasks.length());
        }
        if (t < h) {
            lock.lock();
            try {
                if (H.get() >= (t + 1)) {
                    T.set(t + 1);
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                    return null;
                }
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
        return tasks.get(t % tasks.length());
    }

    @Override
    public T steal() {
        lock.lock();
        int h = H.get();
        H.set(h + 1);
        VarHandle.fullFence();
        T ret;
        if (h + 1 <= T.get()) {
            ret = tasks.get(h % tasks.length());
        } else {
            H.set(h);
            ret = null;
        }
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
        return ret;
    }

    @Override
    public boolean isEmpty(int threadId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean put(T task, int threadId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T take(int ownerId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T steal(int threadId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
