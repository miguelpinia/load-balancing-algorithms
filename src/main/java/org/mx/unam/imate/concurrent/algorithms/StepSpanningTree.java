package org.mx.unam.imate.concurrent.algorithms;

import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.datastructures.graph.Graph;

/**
 * @author miguel
 */
public interface StepSpanningTree extends Runnable {

    void graphTraversalStep(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parent, int root, int label, Report report);

    @Override
    void run();

}
