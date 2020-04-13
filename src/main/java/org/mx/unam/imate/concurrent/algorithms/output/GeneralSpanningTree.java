package org.mx.unam.imate.concurrent.algorithms.output;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.algorithms.SpanningTree;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.GraphUtils;

/**
 *
 * @author miguel
 */
public class GeneralSpanningTree implements SpanningTree {

    private final Parameters params;
    private Report report;

    public GeneralSpanningTree(Parameters parameters) {
        params = parameters;
        report = new Report();
    }

    @Override
    public Graph spanningTree() {
        Thread[] threads = new Thread[params.getNumThreads()];
        Graph graph = GraphUtils.graphType(params.getShape(), params.getType());
        AtomicIntegerArray colors = new AtomicIntegerArray(graph.getNumVertices());
        AtomicIntegerArray parents = new AtomicIntegerArray(GraphUtils.initializeParent(graph.getNumVertices()));
        final int[] roots = GraphUtils.stubSpanning(graph, params.getNumThreads());
        AtomicInteger counter = new AtomicInteger(0);
        WorkStealingStruct[] structs = new WorkStealingStruct[params.getNumThreads()];
        int[] processors = new int[params.getNumThreads()];

        for (int i = 0; i < params.getNumThreads(); i++) {
            structs[i] = WorkStealingStructLookUp
                    .getWorkStealingStruct(params.getAlgType(), params.getStructSize(), params.getNumThreads());
        }

        for (int i = 0; i < params.getNumThreads(); i++) {
            threads[i] = new Thread(new StepSpanningTreeImpl(graph, roots[i], colors,
                    parents, (i + 1), params.getNumThreads(), counter, structs[i],
                    structs, report, params.isSpecialExecution()));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(GeneralSpanningTree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (int i = 0; i < graph.getNumVertices(); i++) {
            if (colors.get(i) != 0) {
                processors[colors.get(i) - 1]++;
            }
        }
        for (int i = 0; i < params.getNumThreads(); i++) {
            System.out.println(String.format("C%d: %d", (i + 1), processors[i]));
        }
        for (int i = 1; i < roots.length; i++) {
            parents.set(roots[i], roots[i - 1]);
        }
        Graph tree = GraphUtils.buildFromParents(parents);

        graph = null;
        return tree;
    }

}
