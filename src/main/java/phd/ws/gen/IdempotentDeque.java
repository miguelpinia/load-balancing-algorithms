package phd.ws.gen;

import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import phd.utils.Triplet;

/**
 *
 * @author miguel
 */
public class IdempotentDeque<T> implements WSStruct<T> {

    private static final int MAX_SIZE = 0xFFFFFF;
    private TaskArray<T> tasks;
    private final AtomicReference<Triplet> anchor;
    private List<T> snapshot;

    public IdempotentDeque(int size) {
        tasks = new TaskArray<>(size);
        anchor = new AtomicReference<>(new Triplet(0, 0, 0));
    }

    public IdempotentDeque(int size, boolean snapshot) {
        this(size);
        if (snapshot) {
            this.snapshot = new ArrayList<>(size);
        }
    }

    @Override
    public boolean isEmpty() {
        return anchor.get().getSize() <= 0;
    }

    @Override
    public int size() {
        return anchor.get().getSize();
    }

    public void expand() {
        Triplet oldTriplet = anchor.get();
        int h = oldTriplet.getHead();
        int s = oldTriplet.getSize();
        TaskArray<T> a = new TaskArray<>(2 * s);
        VarHandle.releaseFence();
        for (int i = 0; i < s; i++) {
            a.set((h + i) % a.size(), tasks.get((h + i) % tasks.size()));
            VarHandle.releaseFence();
        }
        tasks = a;
        VarHandle.fullFence();
    }

    @Override
    public void put(T task) {
        Triplet oldTriplet = anchor.get();
        int h = oldTriplet.getHead();
        int s = oldTriplet.getSize();
        int g = oldTriplet.getTag();
        if (s == tasks.size()) {
            expand();
            put(task);
            return;
        }
        tasks.set((h + s) % tasks.size(), task);
        VarHandle.releaseFence();
        anchor.set(new Triplet(h, s + 1, g + 1));
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
        Triplet oldReference = anchor.get();
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        int g = oldReference.getTag();
        if (s == 0) {
            return null;
        }
        T task = tasks.get((h + s - 1) % tasks.size());
        anchor.set(new Triplet(h, s - 1, g));
        return task;
    }

    @Override
    public T steal() {
        while (true) {
            Triplet oldReference = anchor.get();
            int h = oldReference.getHead();
            int s = oldReference.getSize();
            int g = oldReference.getTag();
            if (s == 0) {
                return null;
            }
            VarHandle.acquireFence();
            TaskArray<T> a = tasks;
            T task = a.get(h % a.size());
            int h2 = h + 1 % MAX_SIZE;
            Triplet newReference = new Triplet(h2, s - 1, g);
            if (anchor.compareAndSet(oldReference, newReference)) {
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
