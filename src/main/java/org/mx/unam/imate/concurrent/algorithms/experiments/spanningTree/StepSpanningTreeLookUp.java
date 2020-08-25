/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.datastructures.graph.Graph;

/**
 *
 * @author miguel
 */
public class StepSpanningTreeLookUp {

    public static AbstractStepSpanningTree getStepSpanningTree(StepSpanningTreeType type, Graph graph,
            int root, AtomicIntegerArray color, AtomicIntegerArray parent, int label, int numThreads,
            WorkStealingStruct struct, WorkStealingStruct[] structs, Report report, boolean specialExecution,
            AtomicIntegerArray visited, AtomicInteger counter) {
        switch (type) {
            case COUNTER:
                return new CounterStepSpanningTree(graph, root, color, parent, label, numThreads,
                        struct, structs, report, specialExecution, visited, counter);
            case DOUBLE_COLLECT:
                return new DoubleCollectStepSpanningTree(graph, root, color, parent, label, numThreads,
                        struct, structs, report, specialExecution);
            default:
                return null;
        }
    }

}
