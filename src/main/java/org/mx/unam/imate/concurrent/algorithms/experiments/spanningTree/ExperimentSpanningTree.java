/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.output.GeneralSpanningTree;
import org.mx.unam.imate.concurrent.algorithms.output.WorkStealingStructLookUp;
import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.GraphUtils;

/**
 *
 * @author miguel
 */
public class ExperimentSpanningTree {

    private final Parameters params;

    public ExperimentSpanningTree(Parameters parameters) {
        params = parameters;
    }

    public List<Report> experiment() {
        List<Report> reports = new ArrayList<>();
        for (int i = 0; i < params.getNumIterExps(); i++) {
            Graph graph = GraphUtils.graphType(params.getShape(), params.getType());
            final int[] roots = GraphUtils.stubSpanning(graph, params.getNumThreads());
            Report report = new Report();
            long executionTime = System.nanoTime();
            spanningTree(graph, roots, report);
            executionTime = System.nanoTime() - executionTime;
            report.setExecutionTime(executionTime);
            reports.add(report);
        }
        return reports;
    }

    private Graph spanningTree(Graph graph, int[] roots, Report report) {
        Thread[] threads = new Thread[params.getNumThreads()];
        AtomicIntegerArray colors = new AtomicIntegerArray(graph.getNumVertices());
        AtomicIntegerArray parents = new AtomicIntegerArray(GraphUtils.initializeParent(graph.getNumVertices()));
        WorkStealingStruct[] structs = new WorkStealingStruct[params.getNumThreads()];
        int[] processors = new int[params.getNumThreads()];

        for (int i = 0; i < params.getNumThreads(); i++) {
            System.out.println("Generando estructura");
            structs[i] = WorkStealingStructLookUp
                    .getWorkStealingStruct(params.getAlgType(), params.getStructSize(), params.getNumThreads());
        }

        for (int i = 0; i < params.getNumThreads(); i++) {
            System.out.println("Construyendo hilos");
            threads[i] = new Thread(new ExperimentStepSpanningTree(graph, roots[i], colors,
                    parents, (i + 1), params.getNumThreads(), structs[i],
                    structs, report, params.isSpecialExecution()));
        }

        for (Thread thread : threads) {
            System.out.println("Iniciando ejecución");
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
                System.out.println("Esperando que se una los hilos");
            } catch (InterruptedException ex) {
                Logger.getLogger(GeneralSpanningTree.class.getName())
                        .log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        for (int i = 0; i < graph.getNumVertices(); i++) {
            if (colors.get(i) != 0) {
                processors[colors.get(i) - 1]++;
            }
        }
        report.setProcessors(processors);
        for (int i = 1; i < roots.length; i++) {
            parents.set(roots[i], roots[i - 1]);
        }
        Graph tree = GraphUtils.buildFromParents(parents);
        return tree;
    }

}
