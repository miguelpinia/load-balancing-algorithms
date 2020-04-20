/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree;

import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.Node;

/**
 *
 * @author miguel
 */
public class DoubleCollectStepSpanningTree extends AbstractStepSpanningTree {

    private final boolean specialExecution;

    public DoubleCollectStepSpanningTree(Graph graph, int root, AtomicIntegerArray color,
            AtomicIntegerArray parent, int label, int numThreads, WorkStealingStruct struct,
            WorkStealingStruct[] structs, Report report, boolean specialExecution) {
        super(graph, root, color, parent, label, numThreads, struct, structs, report);
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
