package org.mx.unam.imate.concurrent.algorithms.cilk;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.mx.unam.imate.concurrent.algorithms.StepSpanningTree;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.Node;

/**
 *
 * @author miguel
 */
public class StepCilkSpanningTree implements StepSpanningTree {

    private final Graph graph;
    private final int root;
    private final int[] color;
    private final int[] parent;
    private final int label;
    private final DequeCilk deque;
    private final DequeCilk[] deques;
    private final Random random;
    private final int numThreads;
    private final AtomicInteger counter;

    public StepCilkSpanningTree(Graph graph, int root, int[] color, int[] parent,
            int label, DequeCilk deque, DequeCilk[] deques, int numThreads, AtomicInteger counter) {
        this.graph = graph;
        this.root = root;
        this.color = color;
        this.parent = parent;
        this.label = label;
        this.deque = deque;
        this.deques = deques;
        this.numThreads = numThreads;
        this.counter = counter;
        random = new Random(System.currentTimeMillis());
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
        deque.push(root);
        int v, w;
        int stolenItem;
        int thread;
        int iterations = graph.getNumConnectedVertices();
        do {
            while (!deque.isEmpty()) {
                v = deque.pop();
                if (v != -1) {
                    Node ptr = graph.getVertices()[v];
                    while (ptr != null) {
                        w = ptr.getVal();
                        if (color[w] == 0) {
                            color[w] = label;
                            parent[w] = v;
                            deque.push(w);
                            counter.incrementAndGet();
                        }
                        ptr = ptr.getNext();
                    }
                }
            }
            thread = pickRandomThread(numThreads, label);
            stolenItem = deques[thread].steal();
            if (stolenItem != -1) {
                deque.push(stolenItem);
            }
        } while (counter.get() < iterations);
    }

    @Override
    public void run() {
        graph_traversal_step(graph, root, color, parent, label);
    }

}
