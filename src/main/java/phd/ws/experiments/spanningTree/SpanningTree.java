package phd.ws.experiments.spanningTree;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

import phd.ws.WorkStealingStruct;
import phd.ws.experiments.spanningTree.stepSpanningTree.AbstractStepSpanningTree;
import phd.ws.experiments.spanningTree.stepSpanningTree.StepSpanningTreeLookUp;
import phd.utils.Parameters;
import phd.utils.Report;
import phd.ds.Graph;
import phd.ds.GraphUtils;

/**
 *
 * @author miguel
 */
public class SpanningTree {

    /**
     * Report the time of perform the spanning tree algorithm.
     *
     * @param graph Graph over which spanning-tree algorithm will be performed.
     * @param roots Initial roots for the distributed spanning-tree algorithm.
     * @param params Object with the params needed to execute the spanning tree.
     * @return The execution time of perform the spanning tree algorithm.
     */
    public long spanningTreeSimplified(Graph graph, int[] roots, Parameters params) {
        long executionTime = System.nanoTime();
        Thread[] threads = new Thread[params.getNumThreads()];
        AtomicIntegerArray colors = new AtomicIntegerArray(graph.getNumberVertices());
        AtomicIntegerArray parents = new AtomicIntegerArray(GraphUtils.initializeParents(graph.getNumberVertices()));
        WorkStealingStruct[] structs = new WorkStealingStruct[params.getNumThreads()];
        AtomicIntegerArray visited = new AtomicIntegerArray(graph.getNumberVertices());
        AtomicInteger counter = new AtomicInteger(0);
        Runnable barrierAction = () -> {
        };
        CyclicBarrier barrier = new CyclicBarrier(params.getNumThreads(), barrierAction);
        for (int i = 0; i < params.getNumThreads(); i++) {
            structs[i] = WorkStealingStructLookUp.getWorkStealingStruct(params.getAlgType(), params.getStructSize(), params.getNumThreads());
        }
        for (int i = 0; i < params.getNumThreads(); i++) {
            AbstractStepSpanningTree step = StepSpanningTreeLookUp.getStepSpanningTree(params.getStepSpanningTreeType(),
                    graph, roots[i], colors, parents, (i + 1), params.getNumThreads(), structs[i], structs,
                    new Report(), params.isSpecialExecution(), visited, counter, params.isStealTime(), barrier);
            threads[i] = new Thread(step);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(SpanningTree.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        executionTime = System.nanoTime() - executionTime;
        return executionTime;
    }

    public Graph spanningTree(Graph graph, int[] roots, Report report, Parameters params) {
        long executionTime = System.nanoTime();
        Thread[] threads = new Thread[params.getNumThreads()];
        AtomicIntegerArray colors = new AtomicIntegerArray(graph.getNumberVertices());
        AtomicIntegerArray parents = new AtomicIntegerArray(GraphUtils.initializeParents(graph.getNumberVertices()));
        WorkStealingStruct[] structs = new WorkStealingStruct[params.getNumThreads()];
        int[] processors = new int[params.getNumThreads()];
        AtomicIntegerArray visited = new AtomicIntegerArray(graph.getNumberVertices());
        AtomicInteger counter = new AtomicInteger(0);
        Runnable barrierAction = () -> {
        };
        CyclicBarrier barrier = new CyclicBarrier(params.getNumThreads(), barrierAction);
        // Building the work-stealing algorithm
        for (int i = 0; i < params.getNumThreads(); i++) {
            structs[i] = WorkStealingStructLookUp
                    .getWorkStealingStruct(params.getAlgType(), params.getStructSize(), params.getNumThreads());
        }
        // Building the spanning tree and 
        for (int i = 0; i < params.getNumThreads(); i++) {
            AbstractStepSpanningTree step = StepSpanningTreeLookUp.getStepSpanningTree(params.getStepSpanningTreeType(),
                    graph, roots[i], colors, parents, (i + 1), params.getNumThreads(), structs[i], structs,
                    report, params.isSpecialExecution(), visited, counter, params.isStealTime(), barrier);

            threads[i] = new Thread(step);
        }
        if (!params.isAllTime()) {
            executionTime = System.nanoTime();
        }
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
        Graph tree = GraphUtils.buildFromParents(parents, roots[0], graph.isDirected());
        return tree;
    }

}
