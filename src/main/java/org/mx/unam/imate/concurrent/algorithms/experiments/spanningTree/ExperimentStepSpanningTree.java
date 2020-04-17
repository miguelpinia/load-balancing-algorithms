package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.StepSpanningTree;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.Node;

/**
 *
 * @author miguel
 */
public class ExperimentStepSpanningTree implements StepSpanningTree {

    private final Graph graph;
    private final int root;
    private final AtomicIntegerArray color;
    private final AtomicIntegerArray parent;
    private final int label;
    private final int numThreads;
    private final WorkStealingStruct struct;
    private final WorkStealingStruct[] structs;
    private final Report report;
    private final boolean specialExecution;
    private final Random random;
    private final AtomicInteger counter;
    private final AtomicIntegerArray visited;

    public ExperimentStepSpanningTree(Graph graph, int root, AtomicIntegerArray color,
            AtomicIntegerArray parent, int label, int numThreads, WorkStealingStruct struct,
            WorkStealingStruct[] structs, Report report, boolean specialExecution,
            AtomicIntegerArray visited, AtomicInteger counter) {
        this.graph = graph;
        this.root = root;
        this.color = color;
        this.parent = parent;
        this.label = label;
        this.numThreads = numThreads;
        this.struct = struct;
        this.structs = structs;
        this.report = report;
        this.specialExecution = specialExecution;
        this.visited = visited;
        this.counter = counter;
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
    public void graph_traversal_step(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, Report report) {
        if (specialExecution) {
            specialExecution(graph, colors, parents, root, label, report);
        } else {
            generalExecution(graph, colors, parents, root, label, report);
        }
    }

    @Override
    public void run() {
        graph_traversal_step(graph, color, parent, root, label, report);
    }

    private void generalExecution(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, Report report) {
        colors.set(root, label);
        struct.put(root);
        int x = visited.getAndSet(root, 1);
        if (x == 0) {
            counter.getAndIncrement();
        }
        report.putsIncrement();
        int v, w, pos;
        int stolenItem;
        int thread;
        do {
            while (!struct.isEmpty()) {
                v = struct.take();
                report.takesIncrement();
                if (v != -1) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    Node ptr = graph.getVertices()[v];
                    while (ptr != null) {
                        w = ptr.getVal();
                        if (colors.get(w) == 0) {
                            colors.set(w, label);
                            parents.set(w, v);
                            struct.put(w);
                            x = visited.getAndSet(w, 1);
                            if (x == 0) {
                                counter.getAndIncrement();
                            }
                            report.putsIncrement();
                        }
                        ptr = ptr.getNext();
                    }
                }
            }
            if (numThreads > 1) {
                thread = pickRandomThread(numThreads, label);
                stolenItem = structs[thread].steal();
                report.stealsIncrement();
                if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                    struct.put(stolenItem);
                    report.putsIncrement();
                }
            }
        } while (counter.get() < graph.getNumVertices());

    }

    private void specialExecution(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, Report report) {
        colors.set(root, label);
        struct.put(root, label);
        int x = visited.getAndSet(root, 1);
        if (x == 0) {
            counter.getAndIncrement();
        }
        report.putsIncrement();
        int v, w, pos;
        int stolenItem;
        int thread;
        do {
            while (!struct.isEmpty()) {
                v = struct.take(label);
                report.takesIncrement();
                if (v != -1) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    Node ptr = graph.getVertices()[v];
                    while (ptr != null) {
                        w = ptr.getVal();
                        if (colors.get(w) == 0) {
                            colors.set(w, label);
                            parents.set(w, v);
                            struct.put(w, label);
                            x = visited.getAndSet(w, 1);
                            if (x == 0) {
                                counter.getAndIncrement();
                            }
                            report.putsIncrement();
                        }
                        ptr = ptr.getNext();
                    }
                }
            }
            if (numThreads > 1) {
                thread = pickRandomThread(numThreads, label);
                stolenItem = structs[thread].steal(label);
                report.stealsIncrement();
                if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                    struct.put(stolenItem, label);
                    report.putsIncrement();
                }
            }
        } while (counter.get() < graph.getNumVertices());
    }
}
