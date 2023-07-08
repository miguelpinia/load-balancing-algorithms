package phd.ws.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author miguel
 */
public class BWSMULT<T> implements WSStruct<T> {

    private final T BOTTOM;

    private final AtomicInteger Head;
    private final List<NodeTasks<T>> tasks;
    private final List<NodeTasksBoolean> B;
    private final int[] heads;
    private final int nodeCapacity;
    private List<T> snapshot;
    private int nodeCounter;
    private int capacity;
    private int tail;

    public BWSMULT(int nodeCapacity, int numThreads, T BOTTOM) {
        tail = -1;
        nodeCounter = 0;
        this.nodeCapacity = nodeCapacity;
        this.Head = new AtomicInteger(0);
        this.heads = new int[numThreads];
        Arrays.fill(heads, 0);
        tasks = new ArrayList<>();
        B = new ArrayList<>();
        tasks.add(new NodeTasks<>(nodeCapacity));
        B.add(new NodeTasksBoolean(nodeCapacity));
        nodeCounter++;
        capacity = nodeCounter * nodeCapacity;
        this.BOTTOM = BOTTOM;
        snapshot = null;
    }

    public BWSMULT(int nodeCapacity, int numThreads, T BOTTOM, boolean snapshot) {
        this(nodeCapacity, numThreads, BOTTOM);
        if (snapshot) {
            this.snapshot = new ArrayList<>();
        } else {
            this.snapshot = null;
        }
    }

    @Override
    public boolean isEmpty(int threadId) {
        heads[threadId] = Math.max(heads[threadId], Head.get());
        return heads[threadId] > tail;
    }

    @Override
    public int size() {
        return tail - Head.get();
    }

    public void expand() {
        tasks.add(new NodeTasks<>(nodeCapacity));
        B.add(new NodeTasksBoolean(nodeCapacity));
        nodeCounter++;
        capacity = nodeCounter * nodeCapacity;
    }

    @Override
    public List<T> getSnapshot() {
        return snapshot;
    }

    @Override
    public boolean put(T task, int threadId) {
        if (tail == (capacity - 1)) {
            expand();
        }
        if (tail <= (capacity - 3)) {
            tasks.get(nodeCounter - 1).set((tail + 1) % nodeCapacity, BOTTOM);
            tasks.get(nodeCounter - 1).set((tail + 2) % nodeCapacity, BOTTOM);
            B.get(nodeCounter - 1).set((tail + 1) % nodeCapacity, true);
            B.get(nodeCounter - 1).set((tail + 2) % nodeCapacity, true);
        }
        tail++;
        tasks.get(nodeCounter - 1).set(tail % nodeCapacity, task);
        B.get(nodeCounter - 1).set(tail % nodeCapacity, true);
        if (snapshot != null) {
            snapshot.add(task);
        }
        return true;
    }

    @Override
    public T get(int position) {
        int node = position / nodeCapacity;
        int pos = position % nodeCapacity;
        T x = tasks.get(node).get(pos);
        return x;
    }

    @Override
    public T take(int ownerId) {
        heads[ownerId] = Math.max(heads[ownerId], Head.get());
        int h = heads[ownerId];
        if (h <= tail) {
            int node = h / nodeCapacity;
            int position = h % nodeCapacity;
            T x = tasks.get(node).get(position);
            Head.set(h + 1);
            heads[ownerId] = h + 1;
            return x;
        }
        return null;
    }

    @Override
    public T steal(int threadId) {
        int h, node, position;
        while (true) {
            h = Math.max(heads[threadId], Head.get());
            if (h <= tail) {
                node = h / nodeCapacity;
                position = h % nodeCapacity;
                if (node < tasks.size()) {
                    T x = tasks.get(node).get(position);
                    if (!x.equals(BOTTOM)) {
                        heads[threadId] = h + 1;
                        if (B.get(node).get(position).getAndSet(false)) {
                            Head.set(h + 1);
                            return x;
                        }
                    }
                }
                return null;
            } else {
                return null;
            }
        }
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

    class NodeTasksBoolean {

        private final int length;
        private final AtomicBoolean[] B;

        public NodeTasksBoolean(int length) {
            this.length = length;
            B = new AtomicBoolean[length];
        }

        public boolean set(int idx, boolean value) {
            if (idx < 0 || idx >= length) {
                return false;
            }
            B[idx] = new AtomicBoolean(value);
            return true;
        }

        public AtomicBoolean get(int idx) {
            return B[idx];
        }
    }

}
