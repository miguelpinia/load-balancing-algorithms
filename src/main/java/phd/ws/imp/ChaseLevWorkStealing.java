package phd.ws.imp;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import phd.ws.WorkStealingStruct;

/**
 *
 * @author miguel
 */
public class ChaseLevWorkStealing implements WorkStealingStruct {

    private static final int EMPTY = -1;
    private final AtomicInteger H;
    private final AtomicInteger T;
    private AtomicIntegerArray tasks;

    public ChaseLevWorkStealing(int initialSize) {
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
        }
        tasks.set(tail % tasks.length(), task);
        T.set(tail + 1);
    }

    @Override
    public int take() {
        int t = T.get() - 1;
        T.set(t);
        VarHandle.fullFence();
        int h = H.get();
        if (t > h) {
            return tasks.get(t % tasks.length());
        }
        if (t < h) {
            T.set(h);
            return EMPTY;
        }
        T.set(h + 1);
        if (!H.compareAndSet(h, h + 1)) {
            return EMPTY;
        } else {
            return tasks.get(t % tasks.length());
        }
    }

    @Override
    public int steal() {
        while (true) {
            int h = H.get();
            int t = T.get();
            if (h >= t) {
                return EMPTY;
            }
            int task = tasks.get(h % tasks.length());
            if (!H.compareAndSet(h, h + 1)) {
                continue;
            }
            return task;
        }
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

}
