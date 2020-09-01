package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.stepSpanningTree.AbstractStepSpanningTree;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.stepSpanningTree.StepSpanningTreeLookUp;
import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.datastructures.graph.Graph;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphUtils;

/**
 *
 * @author miguel
 */
public class SpanningTree {

    public Graph spanningTree(Graph graph, int[] roots, Report report, Parameters params) {
        Thread[] threads = new Thread[params.getNumThreads()];
        AtomicIntegerArray colors = new AtomicIntegerArray(graph.getNumberVertices());
        AtomicIntegerArray parents = new AtomicIntegerArray(GraphUtils.initializeParents(graph.getNumberVertices()));
        WorkStealingStruct[] structs = new WorkStealingStruct[params.getNumThreads()];
        int[] processors = new int[params.getNumThreads()];
        AtomicIntegerArray visited = new AtomicIntegerArray(graph.getNumberVertices());
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < params.getNumThreads(); i++) {
            structs[i] = WorkStealingStructLookUp
                    .getWorkStealingStruct(params.getAlgType(), params.getStructSize(), params.getNumThreads());
        }
        for (int i = 0; i < params.getNumThreads(); i++) {
            AbstractStepSpanningTree step = StepSpanningTreeLookUp.getStepSpanningTree(params.getStepSpanningTreeType(),
                    graph, roots[i], colors, parents, (i + 1), params.getNumThreads(), structs[i], structs,
                    report, params.isSpecialExecution(), visited, counter, params.isStealTime());

            threads[i] = new Thread(step);
        }
        long executionTime = System.nanoTime();
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(SpanningTree.class.getName())
                        .log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        executionTime = System.nanoTime() - executionTime;
        report.setExecutionTime(executionTime);
        for (int i = 0; i < graph.getNumberVertices(); i++) {
            if (colors.get(i) != 0) {
                processors[colors.get(i) - 1]++;
            }
        }
        report.setProcessors(processors);
        for (int i = 1; i < roots.length; i++) {
            parents.set(roots[i], roots[i - 1]);
        }
        Graph tree = GraphUtils.buildFromParents(parents, graph.getRoot(), graph.isDirected());
        return tree;
    }

}
