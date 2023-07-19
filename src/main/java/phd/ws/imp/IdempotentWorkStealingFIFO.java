package phd.ws.imp;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;
import phd.ds.TaskArrayWithSize;
import phd.ws.WorkStealingStruct;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingFIFO implements WorkStealingStruct {

    private static final int EMPTY = -1;

    private TaskArrayWithSize tasks;
    private final AtomicInteger head;
    private final AtomicInteger tail;
    private int puts = 0;
    private int takes = 0;
    private AtomicInteger steals = new AtomicInteger(0);

    public IdempotentWorkStealingFIFO(int size) {
        this.head = new AtomicInteger(0);
        this.tail = new AtomicInteger(0);
        this.tasks = new TaskArrayWithSize(size);
    }

    @Override
    public boolean isEmpty() {
        return head.get() == tail.get();
    }

    @Override
    public void put(int task) {
        int h = head.get();
        int t = tail.get();
        if (t == h + tasks.getSize()) {
            expand();
            put(task);
            return;
        }
        tasks.set(t % tasks.getSize(), task);
        VarHandle.releaseFence();
        tail.set(t + 1);
        puts++;
    }

    @Override
    public int take() {
        int h = head.get();
        int t = tail.get();
        if (h == t) {
            takes++;
            return EMPTY;
        }
        int task = tasks.get(h % tasks.getSize());
        head.set(h + 1);
        takes++;
        return task;
    }

    @Override
    public int steal() {
        while (true) {
            int h = head.get();
            VarHandle.acquireFence();
            int t = tail.get();
            if (h == t) {
                steals.incrementAndGet();
                return EMPTY;
            }
            VarHandle.acquireFence();
            TaskArrayWithSize a = tasks;
            int task = a.get(h % a.getSize());
            VarHandle.acquireFence();
            if (head.compareAndSet(h, h + 1)) {
                steals.incrementAndGet();
                return task;
            }

        }
    }

    public void expand() {
        int size = tasks.getSize();
        TaskArrayWithSize a = new TaskArrayWithSize(2 * size);
        VarHandle.releaseFence();
        int h = head.get();
        int t = tail.get();
        for (int i = h; i < t; i++) {
            a.set(i % a.getSize(), tasks.get(i % tasks.getSize()));
            VarHandle.releaseFence();
        }
        tasks = a;
        VarHandle.releaseFence();
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
