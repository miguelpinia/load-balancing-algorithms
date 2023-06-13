package phd.ws.gen;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author miguel
 */
public class IdempotentFIFO<T> implements WSStruct<T> {

    private TaskArray<T> tasks;
    private final AtomicInteger head;
    private final AtomicInteger tail;

    public IdempotentFIFO(int initialSize) {
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
        tasks = new TaskArray<>(initialSize);
    }

    @Override
    public boolean isEmpty() {
        return head.get() == tail.get();
    }

    @Override
    public int size() {
        return tail.get() - head.get();
    }

    public void expand() {
        int size = tasks.size();
        TaskArray<T> a = new TaskArray<>(2 * size);
        VarHandle.releaseFence();
        int h = head.get();
        int t = tail.get();
        for (int i = h; i < t; i++) {
            a.set(i % a.size(), tasks.get(i % tasks.size()));
            VarHandle.releaseFence();
        }
        tasks = a;
        VarHandle.releaseFence();
    }

    @Override
    public void put(T task) {
        int h = head.get();
        int t = tail.get();
        if (t == h + tasks.size()) {
            expand();
            put(task);
            return;
        }
        tasks.set(t % tasks.size(), task);
        VarHandle.releaseFence();
        tail.set(t + 1);
    }

    @Override
    public T take() {
        int h = head.get();
        int t = tail.get();
        if (h == t) {
            return null;
        }
        T task = tasks.get(h % tasks.size());
        head.set(h + 1);
        return task;
    }

    @Override
    public T steal() {
        while (true) {
            int h = head.get();
            VarHandle.acquireFence();
            int t = tail.get();
            if (h == t) {
                return null;
            }
            VarHandle.acquireFence();
            TaskArray<T> a = tasks;
            T task = a.get(h % a.size());
            VarHandle.acquireFence();
            if (head.compareAndSet(h, h + 1)) {
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
