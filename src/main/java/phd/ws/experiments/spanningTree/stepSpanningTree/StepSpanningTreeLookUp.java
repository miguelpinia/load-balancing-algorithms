/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.ws.experiments.spanningTree.stepSpanningTree;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import phd.ws.WorkStealingStruct;
import phd.utils.Report;
import phd.ds.Graph;

/**
 *
 * @author miguel
 */
public class StepSpanningTreeLookUp {

    public static AbstractStepSpanningTree getStepSpanningTree(StepSpanningTreeType type, Graph graph,
            int root, AtomicIntegerArray color, AtomicIntegerArray parent, int label, int numThreads,
            WorkStealingStruct struct, WorkStealingStruct[] structs, Report report, boolean specialExecution,
            AtomicIntegerArray visited, AtomicInteger counter, boolean stealTime, CyclicBarrier barrier) {
        return switch (type) {
            case COUNTER -> new CounterStepSpanningTree(graph, root, color, parent, label, numThreads,
                    struct, structs, report, specialExecution, visited, counter, stealTime, barrier);
            case DOUBLE_COLLECT -> new DoubleCollectStepSpanningTree(graph, root, color, parent, label, numThreads,
                    struct, structs, report, specialExecution, stealTime, barrier);
            default -> null;
        };
    }

}
