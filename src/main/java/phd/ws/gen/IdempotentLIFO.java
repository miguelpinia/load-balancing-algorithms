package phd.ws.gen;

import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import phd.utils.Pair;

/**
 *
 * @author miguel
 */
public class IdempotentLIFO<T> implements WSStruct<T> {

    private T[] tasks;
    private final AtomicReference<Pair> anchor;
    private int capacity;
    private List<T> snapshot;

    public IdempotentLIFO(int size) {
        anchor = new AtomicReference<>(new Pair(0, 0));
        capacity = size;
        tasks = (T[]) new Object[size];
    }

    public IdempotentLIFO(int size, boolean snapshot) {
        this(size);
        if (snapshot) {
            this.snapshot = new ArrayList<>(size);
        }
    }

    @Override
    public boolean isEmpty() {
        return anchor.get().getT() == 0;
    }

    @Override
    public int size() {
        return anchor.get().getT();
    }

    public void expand() {
        T[] newTasks = (T[]) new Object[2 * capacity];
        for (int i = 0; i < capacity; i++) {
            newTasks[i] = tasks[i];
            VarHandle.releaseFence();
        }
        tasks = newTasks;
        VarHandle.fullFence();
        capacity = 2 * capacity;
    }

    @Override
    public void put(T task) {
        Pair a = anchor.get();
        int t = a.getT();
        int g = a.getG();
        if (t == capacity) {
            expand();
            put(task);
            return;
        }
        tasks[t] = task;
        VarHandle.fullFence();
        anchor.set(new Pair(t + 1, g + 1));
        if (snapshot != null) {
            snapshot.add(task);
        }
    }

    @Override
    public T get(int position) {
        // this could not work
        return tasks[position];
    }

    @Override
    public List<T> getSnapshot() {
        return snapshot;
    }

    @Override
    public T take() {
        Pair a = anchor.get();
        int t = a.getT();
        int g = a.getG();
        if (t == 0) {
            return null;
        }
        T task = tasks[t - 1];
        anchor.set(new Pair(t - 1, g));
        return task;
    }

    @Override
    public T steal() {
        while (true) {
            Pair oldReference = anchor.get();
            int t = oldReference.getT();
            int g = oldReference.getG();
            if (t == 0) {
                return null;
            }
            VarHandle.acquireFence();
            T[] tmp = tasks;
            T task = tmp[t - 1];
            VarHandle.fullFence();
            if (anchor.compareAndSet(oldReference, new Pair(t - 1, g))) {
                return task;
            }
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
