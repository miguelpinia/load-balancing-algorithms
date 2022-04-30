package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.stepSpanningTree;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mx.unam.imate.concurrent.algorithms.StepSpanningTree;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.datastructures.graph.Graph;

/**
 *
 * @author miguel
 */
public abstract class AbstractStepSpanningTree implements StepSpanningTree {

    protected final Graph graph;
    protected final int root;
    protected final AtomicIntegerArray color;
    protected final AtomicIntegerArray parent;
    protected final int label;
    protected final int numThreads;
    protected final WorkStealingStruct struct;
    protected final WorkStealingStruct[] structs;
    protected final Report report;
    protected final Random random;
    protected final boolean stealTime;
    protected final CyclicBarrier barrier;

    public AbstractStepSpanningTree(Graph graph, int root, AtomicIntegerArray color,
            AtomicIntegerArray parent, int label, int numThreads, WorkStealingStruct struct,
            Report report, boolean stealTime, CyclicBarrier barrier, WorkStealingStruct... structs) {
        this.graph = graph;
        this.root = root;
        this.color = color;
        this.parent = parent;
        this.label = label;
        this.numThreads = numThreads;
        this.struct = struct;
        this.structs = structs;
        this.report = report;
        this.random = new Random(System.currentTimeMillis());
        this.stealTime = stealTime;
        this.barrier = barrier;
    }

    int pickRandomThread(int numThreads, int self) {
        int val = random.nextInt(numThreads) + 1;
        while (val == self) {
            val = random.nextInt(numThreads) + 1;
        }
        return val - 1;
    }

    @Override
    public abstract void graph_traversal_step(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, Report report);

    @Override
    public void run() {
        try {
            barrier.await();
            graph_traversal_step(graph, color, parent, root, label, report);
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(AbstractStepSpanningTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
