package phd.ws.imp;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicStampedReference;
import phd.ds.TaskArrayWithSize;
import phd.utils.AnchorDeque;
import phd.ws.WorkStealingStruct;

/**
 *
 * @author miguel
 */
public class IdempotentWorkStealingDeque implements WorkStealingStruct {

    private static final int EMPTY = -1;
    private static final int MAX_SIZE = 0xFFFFFF;

    private TaskArrayWithSize tasks;
    private final AtomicStampedReference<AnchorDeque> anchor;
    private int puts = 0;
    private int takes = 0;
    private int steals = 0;

    public IdempotentWorkStealingDeque(int size) {
        this.tasks = new TaskArrayWithSize(size);
        this.anchor = new AtomicStampedReference<>(new AnchorDeque(0, 0), 0);
    }

    @Override
    public void put(int task) {
        int[] stampHolder = new int[1];
        AnchorDeque oldReference = anchor.get(stampHolder);
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        if (oldReference.getSize() == tasks.getSize()) {
            expand();
            put(task);
            return;
        }
        tasks.set((h + s) % tasks.getSize(), task);
        VarHandle.releaseFence();
        anchor.set(new AnchorDeque(h, s + 1), stampHolder[0] + 1);
        puts++;
    }

    @Override
    public boolean isEmpty() {
        return anchor.getReference().getSize() <= 0;
    }

    @Override
    public int take() {
        int[] stampHolder = new int[1];
        AnchorDeque oldReference = anchor.get(stampHolder);
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        if (s == 0) {
            takes++;
            return EMPTY;
        }
        int task = tasks.get((h + s - 1) % tasks.getSize());
        anchor.set(new AnchorDeque(h, s - 1), stampHolder[0]);
        takes++;
        return task;
    }

    @Override
    public int steal() {
        int[] stampHolder = new int[1];
        while (true) {
            AnchorDeque oldReference = anchor.get(stampHolder);
            int g = stampHolder[0];
            int h = oldReference.getHead();
            int s = oldReference.getSize();
            if (s == 0) {
                steals++;
                return EMPTY;
            }
            VarHandle.acquireFence();
            TaskArrayWithSize a = tasks;
            int task = a.get(h % a.getSize());
            int h2 = h + 1 % MAX_SIZE;
            AnchorDeque newReference = new AnchorDeque(h2, s - 1);
            if (anchor.compareAndSet(oldReference, newReference, g, g)) {
                steals++;
                return task;
            }

        }
    }

    public void expand() {
        AnchorDeque oldReference = anchor.getReference();
        int h = oldReference.getHead();
        int s = oldReference.getSize();
        TaskArrayWithSize a = new TaskArrayWithSize(2 * s);
        VarHandle.releaseFence();
        for (int i = 0; i < s; i++) {
            a.set((h + i) % a.getSize(), tasks.get((h + i) % tasks.getSize()));
            VarHandle.releaseFence();
        }
        tasks = a;
        VarHandle.fullFence();
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
        return steals;
    }

}
