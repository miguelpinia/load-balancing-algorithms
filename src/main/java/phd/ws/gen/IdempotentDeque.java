package phd.ws.gen;

import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicStampedReference;
import phd.utils.AnchorDeque;

/**
 *
 * @author miguel
 * @param <T>
 */
public class IdempotentDeque<T> implements WSStruct<T> {

    private static final int MAX_SIZE = 0xFFFFFF;
    private TaskArray<T> tasks;
    private final AtomicStampedReference<AnchorDeque> anchor;
    private List<T> snapshot;

    public IdempotentDeque(int size) {
        tasks = new TaskArray<>(size);
        anchor = new AtomicStampedReference<>(new AnchorDeque(0, 0), 0);
    }

    public IdempotentDeque(int size, boolean snapshot) {
        this(size);
        if (snapshot) {
            this.snapshot = new ArrayList<>(size);
        }
    }

    @Override
    public boolean isEmpty() {
        return anchor.getReference().getSize() <= 0;
    }

    @Override
    public int size() {
        return anchor.getReference().getSize();
    }

    public void expand() {
        AnchorDeque oldTriplet = anchor.getReference();
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
        int[] stampHolder = new int[1];
        AnchorDeque oldTriplet = anchor.get(stampHolder);
        int h = oldTriplet.getHead();
        int s = oldTriplet.getSize();
        if (s == tasks.size()) {
            expand();
            put(task);
            return;
        }
        tasks.set((h + s) % tasks.size(), task);
        VarHandle.releaseFence();
        anchor.set(new AnchorDeque(h, s + 1), stampHolder[0] + 1);
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
        int[] stampHolder = new int[1];
        AnchorDeque oldReference = anchor.get(stampHolder);
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        if (s == 0) {
            return null;
        }
        T task = tasks.get((h + s - 1) % tasks.size());
        anchor.set(new AnchorDeque(h, s - 1), stampHolder[0]);
        return task;
    }

    @Override
    public T steal() {
        int[] stampHolder = new int[1];
        while (true) {
            AnchorDeque oldReference = anchor.get(stampHolder);
            int h = oldReference.getHead();
            int s = oldReference.getSize();
            int g = stampHolder[0];
            if (s == 0) {
                return null;
            }
            VarHandle.acquireFence();
            TaskArray<T> a = tasks;
            T task = a.get(h % a.size());
            int h2 = h + 1 % MAX_SIZE;
            AnchorDeque newReference = new AnchorDeque(h2, s - 1);
            if (anchor.compareAndSet(oldReference, newReference, g, g)) {
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
