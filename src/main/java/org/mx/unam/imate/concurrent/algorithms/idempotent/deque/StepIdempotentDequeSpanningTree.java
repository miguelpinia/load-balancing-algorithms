package org.mx.unam.imate.concurrent.algorithms.idempotent.deque;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.mx.unam.imate.concurrent.algorithms.StepSpanningTree;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.Node;

/**
 *
 * @author miguel
 */
public class StepIdempotentDequeSpanningTree implements StepSpanningTree {

    private final Graph graph;
    private final int root;
    private final int[] color;
    private final int[] parent;
    private final int label;
    private final int numThreads;
    private final AtomicInteger counter;
    private final IdempotentWorkStealingDeque deque;
    private final IdempotentWorkStealingDeque[] deques;
    private final Random random;

    public StepIdempotentDequeSpanningTree(Graph graph, int root, int[] color, int[] parent,
            int label, IdempotentWorkStealingDeque deque, IdempotentWorkStealingDeque[] deques,
            int numThreads, AtomicInteger counter) {
        this.graph = graph;
        this.root = root;
        this.color = color;
        this.parent = parent;
        this.label = label;
        this.numThreads = numThreads;
        this.counter = counter;
        this.deque = deque;
        this.deques = deques;
        this.random = new Random(System.currentTimeMillis());
    }

    int pickRandomThread(int numThreads, int self) {
        int val = random.nextInt(numThreads) + 1;
        while (val == self) {
            val = random.nextInt(numThreads) + 1;
        }
        return val - 1;
    }

    @Override
    public void graph_traversal_step(Graph graph, int root, int[] color, int[] parent, int label) {
        color[root] = label;
        counter.incrementAndGet();
        deque.put(root);
        int v, w;
        int stolenItem;
        int thread;
        int iterations = graph.getNumConnectedVertices();
        do {
            while (!deque.isEmpty()) {
                v = deque.take();
                if (v != -1) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    Node ptr = graph.getVertices()[v];
                    while (ptr != null) {
                        w = ptr.getVal();
                        if (color[w] == 0) {
                            color[w] = label;
                            parent[w] = v;
                            deque.put(w);
                            counter.incrementAndGet();
                        }
                        ptr = ptr.getNext();
                    }
                }
            }
            thread = pickRandomThread(numThreads, label);
            stolenItem = deques[thread].steal();
            if (stolenItem != -1) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                deque.put(stolenItem);
            }
        } while (counter.get() < iterations);
    }

    @Override
    public void run() {
        graph_traversal_step(graph, root, color, parent, label);
    }

}
