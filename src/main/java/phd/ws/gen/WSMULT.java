package phd.ws.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author miguel
 * @param <T>
 */
public class WSMULT<T> implements WSStruct<T> {

    private final T BOTTOM;
    private final List<NodeTasks<T>> tasks;
    private final AtomicInteger HEAD;
    private final int[] heads;
    private final AtomicInteger size;
    private int nodeCounter;
    private int tail;
    private final int nodeCapacity;
    private int capacity;
    private List<T> snapshot;

    public WSMULT(int nodeCapacity, int numThreads, T BOTTOM) {
        nodeCounter = 0;
        tail = -1;
        heads = new int[numThreads];
        HEAD = new AtomicInteger(0);
        Arrays.fill(heads, 0);

        tasks = new ArrayList<>(capacity);
        this.nodeCapacity = nodeCapacity;
        tasks.add(new NodeTasks<>(nodeCapacity));
        nodeCounter++;
        capacity = nodeCounter * nodeCapacity;
        this.BOTTOM = BOTTOM;
        size = new AtomicInteger(0);
    }

    public WSMULT(int nodeCapacity, int numThreads, T BOTTOM, boolean snapshot) {
        this(nodeCapacity, numThreads, BOTTOM);
        if (snapshot) {
            this.snapshot = new ArrayList<>(nodeCapacity);
        }
    }

    @Override
    public boolean isEmpty(int threadId) {
        heads[threadId] = Math.max(heads[threadId], HEAD.get());
        return heads[threadId] > tail;
    }

    public void expand() {
        tasks.add(new NodeTasks<>(nodeCapacity));
        nodeCounter++;
        capacity = nodeCounter * nodeCapacity;
    }

    @Override
    public boolean put(T task, int threadId) {
        if (tail == (capacity - 1)) {
            expand();
        }
        if (tail <= capacity - 3) {
            tasks.get(nodeCounter - 1).set((tail + 1) % nodeCapacity, BOTTOM);
            tasks.get(nodeCounter - 1).set((tail + 2) % nodeCapacity, BOTTOM);
        }
        tail++;
        size.getAndIncrement();
        tasks.get(nodeCounter - 1).set(tail % nodeCapacity, task);
        if (snapshot != null) {
            snapshot.add(task);
        }
        return true;
    }

    @Override
    public T take(int ownerId) {
        heads[ownerId] = Math.max(heads[ownerId], HEAD.get());
        int h = heads[ownerId];
        if (h <= tail) {
            int node = h / nodeCapacity;
            int position = h % nodeCapacity;
            T x = tasks.get(node).get(position);
            heads[ownerId] = h + 1;
            HEAD.set(h + 1);
            size.getAndDecrement();
            return x;
        }
        return null;
    }

    @Override
    public T get(int position) {
        int node = position / nodeCapacity;
        int pos = position % nodeCapacity;
        T x = tasks.get(node).get(pos);
        return x;
    }

    @Override
    public List<T> getSnapshot() {
        return snapshot;
    }

    @Override
    public T steal(int threadId) {
        heads[threadId] = Math.max(heads[threadId], HEAD.get());
        int h = heads[threadId];
        if (h <= tail) {
            int node = h / nodeCapacity;
            int position = h % nodeCapacity;
            if (node < tasks.size()) {
                T x = tasks.get(node).get(position);
                if (!x.equals(BOTTOM)) {
                    heads[threadId] = h + 1;
                    HEAD.set(h + 1);
                    size.getAndDecrement();
                    return x;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size.get();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void put(T task) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T take() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T steal() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
