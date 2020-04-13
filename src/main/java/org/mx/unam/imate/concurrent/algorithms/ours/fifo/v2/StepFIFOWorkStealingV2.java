package org.mx.unam.imate.concurrent.algorithms.ours.fifo.v2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.algorithms.StepSpanningTree;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.Node;

/**
 *
 * @author miguel
 */
public class StepFIFOWorkStealingV2 implements StepSpanningTree {

    private final Graph graph;
    private final int root;
    private final int[] color;
    private final int[] parent;
    private final int label;
    private final int numThreads;
    private final AtomicInteger counter;
    private final FIFOWorkStealingV2 fifo;
    private final FIFOWorkStealingV2[] fifos;
    private final Random random;

    public StepFIFOWorkStealingV2(Graph graph, int root, int[] color, int[] parent,
            int label, FIFOWorkStealingV2 fifo, FIFOWorkStealingV2[] fifos,
            int numThreads, AtomicInteger counter) {
        this.graph = graph;
        this.root = root;
        this.color = color;
        this.parent = parent;
        this.label = label;
        this.numThreads = numThreads;
        this.counter = counter;
        this.fifo = fifo;
        this.fifos = fifos;
        this.random = new Random(System.currentTimeMillis());
    }

    int pickRandomThread(int numThreads, int self) {
        int val = random.nextInt(numThreads) + 1;
        while (val == self) {
            val = random.nextInt(numThreads) + 1;
        }
        return val - 1;
    }

    public void graph_traversal_step(Graph graph, int root, int[] color, int[] parent, int label) {
        color[root] = label;
        counter.incrementAndGet();
        fifo.put(root);
        int v, w;
        int stolenItem;
        int thread;
        int iterations = graph.getNumConnectedVertices();
        do {
            while (!fifo.isEmpty()) {
                v = fifo.take();
                if (v != -1) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    Node ptr = graph.getVertices()[v];
                    while (ptr != null) {
                        w = ptr.getVal();
                        if (color[w] == 0) {
                            color[w] = label;
                            parent[w] = v;
                            fifo.put(w);
                            counter.incrementAndGet();
                        }
                        ptr = ptr.getNext();
                    }
                }
            }
            thread = pickRandomThread(numThreads, label);
            stolenItem = fifos[thread].steal();
            if (stolenItem != -1) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                fifo.put(stolenItem);
            }
        } while (counter.get() < iterations);
    }

    @Override
    public void run() {
        graph_traversal_step(graph, root, color, parent, label);
    }

    @Override
    public void graph_traversal_step(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parent, int root, int label, Report report) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
