package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.stepSpanningTree;

import java.util.Iterator;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.datastructures.graph.Graph;

/**
 *
 * @author miguel
 */
public class DoubleCollectStepSpanningTree extends AbstractStepSpanningTree {

    private final boolean specialExecution;

    public DoubleCollectStepSpanningTree(Graph graph, int root, AtomicIntegerArray color,
            AtomicIntegerArray parent, int label, int numThreads, WorkStealingStruct struct,
            WorkStealingStruct[] structs, Report report, boolean specialExecution, boolean stealTime, CyclicBarrier barrier) {
        super(graph, root, color, parent, label, numThreads, struct, report, stealTime, barrier, structs);
        this.specialExecution = specialExecution;
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
        color.set(root, label);
        struct.put(root);
        report.putsIncrement();
        int v, w, pos;
        int stolenItem = -1;
        int thread;
        boolean firstTime = true;
        boolean workToSteal = false;
        Iterator<Integer> it;
        long time;
        while (firstTime || workToSteal) {
            while (!struct.isEmpty()) {
                v = struct.take();
                report.takesIncrement();
                if (v >= 0) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    it = graph.getNeighbours(v).iterator();
                    while (it.hasNext()) {
                        w = it.next();
                        if (color.get(w) == 0) {
                            color.set(w, label);
                            parents.set(w, v);
                            struct.put(w);
                            report.putsIncrement();
                        }
                    }
                }
            }
            if (firstTime) {
                firstTime = false;
            }
            workToSteal = false;
            if (numThreads > 1) {
                thread = pickRandomThread(numThreads, label);
                for (int idx = 0; idx < numThreads * 2; idx++) {// Recorremos de forma circular a los hilos en búsqueda de algo que robar
                    pos = (idx + thread) % numThreads;// Hacemos un doble recorrido para simular una doble colecta (como en un snapshot).
                    if (pos != label) {
                        time = System.nanoTime();
                        stolenItem = structs[(idx + thread) % numThreads].steal();
                        time = System.nanoTime() - time;
                        report.stealsIncrement();
                        if (stealTime) {
                            report.setMinSteal(time);
                            report.setMaxSteal(time);
                            report.updateAvgSteal(time);
                        }
                    }
                    if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                        struct.put(stolenItem);
                        report.putsIncrement();
                        workToSteal = true;
                        break;
                    }
                }
            }
        }
    }

    private void specialExecution(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, Report report) {
        colors.set(root, label);
        struct.put(root, label - 1);
        report.putsIncrement();
        int v, w, pos;
        int stolenItem = -1;
        int thread;
        boolean firstTime = true;
        boolean workToSteal = false;
        Iterator<Integer> it;
        long time;
        while (firstTime || workToSteal) {
            while (!struct.isEmpty(label - 1)) {
                v = struct.take(label - 1);
                report.takesIncrement();
                if (v >= 0) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    it = graph.getNeighbours(v).iterator();
                    while (it.hasNext()) {
                        w = it.next();
                        if (colors.get(w) == 0) {
                            colors.set(w, label);
                            parents.set(w, v);
                            struct.put(w, label - 1);
                            report.putsIncrement();
                        }
                    }
                }
            }
            if (firstTime) {
                firstTime = false;
            }
            workToSteal = false;
            if (numThreads > 1) {
                thread = pickRandomThread(numThreads, label);
                for (int idx = 0; idx < numThreads * 2; idx++) {// Recorremos de forma circular a los hilos en búsqueda de algo que robar
                    pos = (idx + thread) % numThreads; // Hacemos un doble recorrido para simular una doble colecta (como en un snapshot).
                    if (pos != label) {
                        time = System.nanoTime();
                        stolenItem = structs[(idx + thread) % numThreads].steal(label - 1);
                        time = System.nanoTime() - time;
                        report.stealsIncrement();
                        if (stealTime) {
                            report.setMinSteal(time);
                            report.setMaxSteal(time);
                            report.updateAvgSteal(time);
                        }
                    }
                    if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                        struct.put(stolenItem, label - 1);
                        report.putsIncrement();
                        workToSteal = true;
                        break;
                    }
                }
            }
        }
    }

}
