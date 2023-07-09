package phd.ws.gen;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author miguel
 * @param <T>
 */
public class NodePool<T> {

    private final Queue<NodeTasks<T>> pool;
    private final int nodeCapacity;

    public NodePool(int initialCapacity, int nodeCapacity) {
        pool = new LinkedList<>();
        this.nodeCapacity = nodeCapacity;
        for (int i = 0; i < initialCapacity; i++) {
            pool.add(new NodeTasks<>(nodeCapacity));
        }
    }

    public NodeTasks<T> get() {
        if (!pool.isEmpty()) {
            return pool.poll();
        }
        return new NodeTasks<>(nodeCapacity);
    }

    public void clear(NodeTasks<T> node) {
        node.clear();
        pool.add(node);
    }

}
