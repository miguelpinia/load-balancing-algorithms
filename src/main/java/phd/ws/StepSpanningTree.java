package phd.ws;

import java.util.concurrent.atomic.AtomicIntegerArray;
import phd.ds.Graph;
import phd.utils.Report;
import phd.utils.SimpleReport;

/**
 * @author miguel
 */
public interface StepSpanningTree extends Runnable {

    void graphTraversalStep(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parent, int root, int label, Report report);

    void graphTraversalStep(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parent, int root, int label, SimpleReport report);

    @Override
    void run();

}
