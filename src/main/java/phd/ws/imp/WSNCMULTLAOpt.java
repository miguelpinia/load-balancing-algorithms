package phd.ws.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import phd.ws.WorkStealingStruct;

/**
 *
 * @author miguel
 */
public class WSNCMULTLAOpt implements WorkStealingStruct {

    private static final int BOTTOM = -2;
    private static final int EMPTY = -1;

    private final AtomicInteger Head;
    private int tail;
    private final int[] head;

    private final int arrayLength;
    private int nodes;
    private int length;

    private final List<NodeArrayInt> tasks;
    private int puts = 0;
    private int takes = 0;
    private AtomicInteger steals = new AtomicInteger(0);

    public WSNCMULTLAOpt(int size, int numThreads) {
        this.nodes = 0;
        this.tail = -1;
        this.head = new int[numThreads];
        this.Head = new AtomicInteger(0);
        Arrays.fill(head, 0);

        // Inicializar valores de la estructura de datos
        tasks = new ArrayList<>();// ArrayList?
        arrayLength = size;
        tasks.add(new NodeArrayInt(size));
        nodes++;
        length = nodes * arrayLength;
    }

    @Override
    public boolean isEmpty(int label) {
        return head[label] > tail;
    }

    public void expand() {
        tasks.add(new NodeArrayInt(arrayLength));
        nodes++;
        length = nodes * arrayLength;
    }

    @Override
    public boolean put(int task, int label) {
        if (tail == (length - 1)) {
            expand();
        }
        if (tail <= length - 3) {
            tasks.get(nodes - 1).setItem((tail + 1) % arrayLength, BOTTOM);
            tasks.get(nodes - 1).setItem((tail + 2) % arrayLength, BOTTOM);
        }
        tail++;
        tasks.get(nodes - 1).setItem(tail % arrayLength, task);
        puts++;
        return true;
    }

    @Override
    public int take(int label) {
        head[label] = Math.max(head[label], Head.get());
        int h = head[label];
        if (h <= tail) {
            int node = h / arrayLength;
            int position = h % arrayLength;
            int x = tasks.get(node).get(position);
            head[label] = h + 1;
            Head.set(h + 1);
            takes++;
            return x;
        } else {
            takes++;
            return EMPTY;
        }
    }

    @Override
    public int steal(int label) {
        head[label] = Math.max(head[label], Head.get());
        int h = head[label];
        if (h <= tail) {
            int node = h / arrayLength;
            int position = h % arrayLength;
            if (node < tasks.size()) {
                int x = tasks.get(node).get(position);
                if (x != BOTTOM) {
                    head[label] = h + 1;
                    Head.set(h + 1);
                    steals.incrementAndGet();
                    return x;
                }
            }
        }
        steals.incrementAndGet();
        return EMPTY;
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

    private class NodeArrayInt {

        private final int length;
        private final int[] items;

        public NodeArrayInt(int length) {
            this.length = length;
            items = new int[length];
        }

        public boolean setItem(int idx, int value) {
            if (idx < 0 || idx >= length) {
                return false;
            }
            items[idx] = value;
            return true;
        }

        public int get(int idx) {
            return items[idx];
        }

        @Override
        public String toString() {
            return "NodeArrayInt{" + "length=" + length + ", items=" + Arrays.toString(items) + '}';
        }

    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void put(int task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int take() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int steal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
