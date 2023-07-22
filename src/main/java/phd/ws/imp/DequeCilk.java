package phd.ws.imp;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.ReentrantLock;
import phd.ws.WorkStealingStruct;

/**
 *
 * @author miguel
 */
public class DequeCilk implements WorkStealingStruct {

    private static final int EMPTY = -1;
    private AtomicInteger H;
    private AtomicInteger T;
    private AtomicIntegerArray tasks;
    private final ReentrantLock lock;
    private int puts = 0;
    private int takes = 0;
    private AtomicInteger steals = new AtomicInteger(0);

    public DequeCilk(int initialSize) {
        lock = new ReentrantLock(true);
        tasks = new AtomicIntegerArray(initialSize);
        H = new AtomicInteger(0);
        T = new AtomicInteger(0);
    }

    @Override
    public boolean isEmpty() {
        long tail = T.get();
        long head = H.get();
        return head >= tail;
    }

    private void expand() {
        AtomicIntegerArray newData = new AtomicIntegerArray(2 * tasks.length());
        for (int i = 0; i < tasks.length(); i++) {
            newData.set(i, tasks.get(i));
        }
        tasks = newData;
    }

    @Override
    public void put(int task) {
        int tail = T.get();
        if (tail >= tasks.length()) {
            expand();
            put(task);
            return;
        }
        tasks.set(tail % tasks.length(), task);
        T.set(tail + 1);
//        puts++;
    }

    @Override
    public int take() {
        int t = T.get() - 1;
        T.set(t);
        VarHandle.fullFence();
        int h = H.get();
        if (t > h) {
//            takes++;
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
//                    takes++;
                    return EMPTY;
                }
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
//        takes++;
        return tasks.get(t % tasks.length());
    }

    @Override
    public int steal() {
        lock.lock();
        int h = H.get();
        H.set(h + 1);
        VarHandle.fullFence();
        int ret;
        if (h + 1 <= T.get()) {
            ret = tasks.get(h % tasks.length());
        } else {
            H.set(h);
            ret = EMPTY;
        }
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
//        steals.incrementAndGet();
        return ret;
    }

    @Override
    public boolean put(int task, int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int take(int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int steal(int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty(int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getPuts() {
        return puts;
    }

    @Override
    public int getTakes() {
        return takes;
    }

    @Override
    public int getSteals() {
        return steals.get();
    }

}
