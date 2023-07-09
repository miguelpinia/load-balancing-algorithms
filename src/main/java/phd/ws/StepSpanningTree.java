package phd.ws;

import java.util.concurrent.atomic.AtomicIntegerArray;

import phd.utils.Report;
import phd.ds.Graph;

/**
 * @author miguel
 */
public interface StepSpanningTree extends Runnable {

    void graphTraversalStep(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parent, int root, int label, Report report);

    @Override
    void run();

}
