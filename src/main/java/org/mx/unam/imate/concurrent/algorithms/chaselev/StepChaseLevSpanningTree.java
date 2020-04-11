package org.mx.unam.imate.concurrent.algorithms.chaselev;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.Node;

/**
 *
 * @author miguel
 */
public class StepChaseLevSpanningTree implements Runnable {

    private final Graph graph;
    private final int root;
    private final AtomicIntegerArray color;
    private final AtomicIntegerArray parent;
    private final int label;
    private final int numThreads;
    private final AtomicInteger counter;
    private final ChaseLevWorkStealing queue;
    private final ChaseLevWorkStealing[] queues;
    private final Random random;

    public StepChaseLevSpanningTree(Graph graph, int root, AtomicIntegerArray color, AtomicIntegerArray parent,
            int label, ChaseLevWorkStealing queue, ChaseLevWorkStealing[] queues,
            int numThreads, AtomicInteger counter) {
        this.graph = graph;
        this.root = root;
        this.color = color;
        this.parent = parent;
        this.label = label;
        this.numThreads = numThreads;
        this.counter = counter;
        this.queue = queue;
        this.queues = queues;
        this.random = new Random(System.currentTimeMillis());
    }

    int pickRandomThread(int numThreads, int self) {
        int val = random.nextInt(numThreads) + 1;
        while (val == self) {
            val = random.nextInt(numThreads) + 1;
        }
        return val - 1;
    }

    public void graph_traversal_step(Graph graph, int root, AtomicIntegerArray color, AtomicIntegerArray parent, int label) {
        color.set(root, label);
        counter.incrementAndGet();
        queue.put(root);
        int v, w;
        int stolenItem;
        int thread;
        int iterations = graph.getNumConnectedVertices();
        do {
            while (!queue.isEmpty()) {
                v = queue.take();
                if (v != -1) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    Node ptr = graph.getVertices()[v];
                    while (ptr != null) {
                        w = ptr.getVal();
                        if (color.get(w) == 0) {
                            color.set(w, label);
                            parent.set(w, v);
                            queue.put(w);
                            counter.incrementAndGet();
                        }
                        ptr = ptr.getNext();
                    }
                }
            }
            thread = pickRandomThread(numThreads, label);
            stolenItem = queues[thread].steal();
            if (stolenItem != -1 && stolenItem != -2) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                queue.put(stolenItem);
            }
        } while (counter.get() < iterations);
    }

    @Override
    public void run() {
        graph_traversal_step(graph, root, color, parent, label);
    }

}
