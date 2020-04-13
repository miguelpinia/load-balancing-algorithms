package org.mx.unam.imate.concurrent.algorithms.output;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.Report;
import org.mx.unam.imate.concurrent.algorithms.StepSpanningTree;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.Node;

/**
 *
 * @author miguel
 */
public class StepSpanningTreeImpl implements StepSpanningTree {

    private final Graph graph;
    private final int root;
    private final AtomicIntegerArray color;
    private final AtomicIntegerArray parent;
    private final int label;
    private final int numThreads;
    private final AtomicInteger counter;
    private final WorkStealingStruct struct;
    private final WorkStealingStruct[] structs;
    private final Report report;
    private final boolean specialExecution;
    private final Random random;

    public StepSpanningTreeImpl(Graph graph, int root, AtomicIntegerArray color,
            AtomicIntegerArray parent, int label, int numThreads, AtomicInteger counter,
            WorkStealingStruct struct, WorkStealingStruct[] structs, Report report, boolean specialExecution) {
        this.graph = graph;
        this.root = root;
        this.color = color;
        this.parent = parent;
        this.label = label;
        this.numThreads = numThreads;
        this.counter = counter;
        this.struct = struct;
        this.structs = structs;
        this.report = report;
        this.specialExecution = specialExecution;
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
        color.set(root, label);
        counter.incrementAndGet();
        struct.put(root);
        int v, w, pos;
        int stolenItem = -1;
        int thread;
        boolean firstTime = true;
        boolean workToSteal = false;
        while (firstTime || workToSteal) {
            while (!struct.isEmpty()) {
                v = struct.take();
                if (v != -1) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    Node ptr = graph.getVertices()[v];
                    while (ptr != null) {
                        w = ptr.getVal();
                        if (color.get(w) == 0) {
                            color.set(w, label);
                            parents.set(w, v);
                            struct.put(w);
                            counter.incrementAndGet();
                        }
                        ptr = ptr.getNext();
                    }
                }
            }
            if (firstTime) {
                firstTime = false;
            }
            workToSteal = false;
            thread = pickRandomThread(numThreads, label);
            for (int idx = 0; idx < numThreads * 2; idx++) {// Recorremos de forma circular a los hilos en búsqueda de algo que robar
                pos = (idx + thread) % numThreads;// Hacemos un doble recorrido para simular una doble colecta (como en un snapshot).
                if (pos != label) {
                    stolenItem = structs[(idx + thread) % numThreads].steal();
                }
                if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                    struct.put(stolenItem);
                    workToSteal = true;
                    break;
                }
            }
        }
    }

    private void specialExecution(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, Report report) {
        colors.set(root, label);
        counter.incrementAndGet();
        struct.put(root, label);
        int v, w, pos;
        int stolenItem = -1;
        int thread;
        boolean firstTime = true;
        boolean workToSteal = false;
        while (firstTime || workToSteal) {
            while (!struct.isEmpty()) {
                v = struct.take(label);
                if (v != -1) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    Node ptr = graph.getVertices()[v];
                    while (ptr != null) {
                        w = ptr.getVal();
                        if (colors.get(w) == 0) {
                            colors.set(w, label);
                            parents.set(w, v);
                            struct.put(w, label);
                            counter.incrementAndGet();
                        }
                        ptr = ptr.getNext();
                    }
                }
            }
            if (firstTime) {
                firstTime = false;
            }
            workToSteal = false;
            thread = pickRandomThread(numThreads, label);
            for (int idx = 0; idx < numThreads * 2; idx++) {// Recorremos de forma circular a los hilos en búsqueda de algo que robar
                pos = (idx + thread) % numThreads; // Hacemos un doble recorrido para simular una doble colecta (como en un snapshot).
                if (pos != label) {
                    stolenItem = structs[(idx + thread) % numThreads].steal(label);
                }
                if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                    struct.put(stolenItem, label);
                    workToSteal = true;
                    break;
                }
            }
        }
    }

}
