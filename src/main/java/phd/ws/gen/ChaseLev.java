package phd.ws.gen;

import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 *
 * @author miguel
 */
public class ChaseLev<T> implements WSStruct<T> {

    private final AtomicInteger H;
    private final AtomicInteger T;
    private AtomicReferenceArray<T> tasks;
    private List<T> snapshot;

    public ChaseLev(int initialSize) {
        tasks = new AtomicReferenceArray<>(initialSize);
        H = new AtomicInteger(0);
        T = new AtomicInteger(0);
    }

    public ChaseLev(int initialSize, boolean snapshot) {
        this(initialSize);
        if (snapshot) {
            this.snapshot = new ArrayList<>(initialSize);
        }
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

    private void expand() {
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
        if (snapshot != null) {
            snapshot.add(task);
        }
    }

    @Override
    public T get(int position) {
        return tasks.get(position);
    }

    @Override
    public List<T> getSnapshot() {
        return snapshot;
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
            T.set(h);
            return null;
        }
        T.set(h + 1);
        if (!H.compareAndSet(h, h + 1)) {
            return null;
        } else {
            return tasks.get(t % tasks.length());
        }
    }

    @Override
    public T steal() {
        while (true) {
            int h = H.get();
            int t = T.get();
            if (h >= t) {
                return null;
            }
            T task = tasks.get(h % tasks.length());
            if (!H.compareAndSet(h, h + 1)) {
                continue;
            }
            return task;
        }
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
