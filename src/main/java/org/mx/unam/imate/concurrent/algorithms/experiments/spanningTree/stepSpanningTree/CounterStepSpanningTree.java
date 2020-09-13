package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.stepSpanningTree;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.datastructures.graph.Graph;

/**
 *
 * @author miguel
 */
public class CounterStepSpanningTree extends AbstractStepSpanningTree {

    private final boolean specialExecution;
    private final AtomicInteger counter;
    private final AtomicIntegerArray visited;

    public CounterStepSpanningTree(Graph graph, int root, AtomicIntegerArray color,
            AtomicIntegerArray parent, int label, int numThreads, WorkStealingStruct struct,
            WorkStealingStruct[] structs, Report report, boolean specialExecution,
            AtomicIntegerArray visited, AtomicInteger counter, boolean stealTime) {
        super(graph, root, color, parent, label, numThreads, struct, report, stealTime, structs);
        this.specialExecution = specialExecution;
        this.visited = visited;
        this.counter = counter;
    }

    @Override
    public void graph_traversal_step(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, Report report) {
        if (specialExecution) {
            specialExecution(graph, colors, parents, root, label, report);
        } else {
            generalExecution(graph, colors, parents, root, label, report);
        }
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
        Iterator<Integer> it;
        long time;
        do {
            while (!struct.isEmpty()) {
                v = struct.take();
                report.takesIncrement();
                if (v != -1) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    it = graph.getNeighbours(v).iterator();
                    while (it.hasNext()) {
                        w = it.next();
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
                    }
                }
            }
            if (numThreads > 1) {
                thread = pickRandomThread(numThreads, label);
                time = System.nanoTime();
                stolenItem = structs[thread].steal();
                time = System.nanoTime() - time;
                report.stealsIncrement();
                if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                    struct.put(stolenItem);
                    report.putsIncrement();
                }
                if (stealTime) {
                    report.setMinSteal(time);
                    report.setMaxSteal(time);
                    report.updateAvgSteal(time);
                }
            }
        } while (counter.get() < graph.getNumberVertices());

    }

    private void specialExecution(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, Report report) {
        colors.set(root, label);
        struct.put(root, label - 1);
        int x = visited.getAndSet(root, 1);
        if (x == 0) {
            counter.getAndIncrement();
        }
        report.putsIncrement();
        int v, w, pos;
        int stolenItem;
        int thread;
        Iterator<Integer> it;
        long time;
        do {
            while (!struct.isEmpty(label - 1)) {
                v = struct.take(label - 1);
                report.takesIncrement();
                if (v != -1) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    it = graph.getNeighbours(v).iterator();
                    while (it.hasNext()) {
                        w = it.next();
                        if (colors.get(w) == 0) {
                            colors.set(w, label);
                            parents.set(w, v);
                            struct.put(w, label - 1);
                            x = visited.getAndSet(w, 1);
                            if (x == 0) {
                                counter.getAndIncrement();
                            }
                            report.putsIncrement();
                        }
                    }
                }
            }
            if (numThreads > 1) {
                thread = pickRandomThread(numThreads, label);
                time = System.nanoTime();
                stolenItem = structs[thread].steal(label - 1);
                time = System.nanoTime() - time;
                report.stealsIncrement();
                if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                    struct.put(stolenItem, label - 1);
                    report.putsIncrement();
                }
                if (stealTime) {
                    report.setMinSteal(time);
                    report.setMaxSteal(time);
                    report.updateAvgSteal(time);
                }
            }
        } while (counter.get() < graph.getNumberVertices());
    }

}
