package phd.ws.experiments.spanningTree.stepSpanningTree;

import java.util.Iterator;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import phd.ds.Graph;
import phd.utils.Report;
import phd.utils.SimpleReport;
import phd.ws.WorkStealingStruct;

/**
 *
 * @author miguel
 */
public class CounterStepSpanningTree extends AbstractStepSpanningTree {

    private final boolean specialExecution;
    private final AtomicInteger counter;
    private final AtomicIntegerArray visited;

    public CounterStepSpanningTree(Graph graph, int root, AtomicIntegerArray color,
            AtomicIntegerArray parent, int label, int numThreads, WorkStealingStruct struct,
            WorkStealingStruct[] structs, Report report, boolean specialExecution,
            AtomicIntegerArray visited, AtomicInteger counter, boolean stealTime, CyclicBarrier barrier) {
        super(graph, root, color, parent, label, numThreads, struct, report, stealTime, barrier, structs);
        this.specialExecution = specialExecution;
        this.visited = visited;
        this.counter = counter;
    }

    public CounterStepSpanningTree(Graph graph, int root, AtomicIntegerArray color,
            AtomicIntegerArray parent, int label, int numThreads, WorkStealingStruct struct,
            WorkStealingStruct[] structs, SimpleReport report, boolean specialExecution,
            AtomicIntegerArray visited, AtomicInteger counter, boolean stealTime, CyclicBarrier barrier) {
        super(graph, root, color, parent, label, numThreads, struct, report, stealTime, barrier, structs);
        this.specialExecution = specialExecution;
        this.visited = visited;
        this.counter = counter;
    }

    @Override
    public void graphTraversalStep(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, Report report) {
        if (specialExecution) {
            specialExecution(graph, colors, parents, root, label, report);
        } else {
            generalExecution(graph, colors, parents, root, label, report);
        }
    }

    private void generalExecution(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, Report report) {
        colors.set(root, label);
        struct.put(root);
        int x = visited.getAndSet(root, 1);
        if (x == 0) {
            counter.getAndIncrement();
        }
        report.putsIncrement();
        int v, w;
        int stolenItem;
        int thread;
        Iterator<Integer> it;
        long time;
        do {
            while (!struct.isEmpty()) {
                v = struct.take();
                report.incrementTakes();
                if (v >= 0) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    it = graph.getNeighbours(v).iterator();// Trabajo realizado
                    while (it.hasNext()) {
                        w = it.next();
                        if (colors.get(w) == 0) {
                            colors.set(w, label);
                            parents.set(w, v);
                            struct.put(w); // Trabajo por hacer por exploracion
                            x = visited.getAndSet(w, 1);
                            if (x == 0) {
                                counter.getAndIncrement();
                            }
                            report.putsIncrement();
                        }
                    }
                }
            }

            // Trabajo por hilo: puts + takes por exploracion
            // Trabajo repetido: puts - steals (si son exitosos)
            if (numThreads > 1) {
                thread = pickRandomThread(numThreads, label);
                time = System.nanoTime();
                stolenItem = structs[thread].steal();
                time = System.nanoTime() - time;
                if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                    report.stealsIncrement();
                    struct.put(stolenItem); // Crean tareas
                    report.putsIncrement();
                }
                if (stealTime) {
                    report.setMinSteal(time);
                    report.setMaxSteal(time);
                    report.updateAvgSteal(time);
                }
            }
        } while (counter.get() < graph.getNumberVertices());

    }

    private void specialExecution(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, Report report) {
        colors.set(root, label);
        struct.put(root, label - 1);
        int x = visited.getAndSet(root, 1);
        if (x == 0) {
            counter.getAndIncrement();
        }
        report.putsIncrement();
        int v, w;
        int stolenItem;
        int thread;
        Iterator<Integer> it;
        long time;
        do {
            while (!struct.isEmpty(label - 1)) {
                v = struct.take(label - 1);
                report.incrementTakes();
                if (v >= 0) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    it = graph.getNeighbours(v).iterator();
                    while (it.hasNext()) {
                        w = it.next();
                        if (colors.get(w) == 0) {
                            colors.set(w, label);
                            parents.set(w, v);
                            struct.put(w, label - 1);
                            x = visited.getAndSet(w, 1);
                            if (x == 0) {
                                counter.getAndIncrement();
                            }
                            report.putsIncrement();
                        }
                    }
                }
            }
            if (numThreads > 1) {
                thread = pickRandomThread(numThreads, label);
                time = System.nanoTime();
                stolenItem = structs[thread].steal(label - 1);
                time = System.nanoTime() - time;
                report.stealsIncrement();
                if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                    struct.put(stolenItem, label - 1);
                    report.putsIncrement();
                }
                if (stealTime) {
                    report.setMinSteal(time);
                    report.setMaxSteal(time);
                    report.updateAvgSteal(time);
                }
            }
        } while (counter.get() < graph.getNumberVertices());
    }

    @Override
    public void graphTraversalStep(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, SimpleReport report) {
        if (specialExecution) {
            specialExecutionMeasurement(graph, colors, parents, root, label, report);
        } else {
            generalExecutionMeasurement(graph, colors, parents, root, label, report);
        }
    }

    private void generalExecutionMeasurement(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, SimpleReport report) {
        int puts = 0;
        int takes = 0;
        int steals = 0;
        colors.set(root, label);
        struct.put(root);
        puts++;
        int x = visited.getAndSet(root, 1);
        if (x == 0) {
            counter.getAndIncrement();
        }
        int v, w;
        int stolenItem;
        int thread;
        Iterator<Integer> it;
        do {
            while (!struct.isEmpty()) {
                v = struct.take();
                if (v >= 0) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    takes++;
                    it = graph.getNeighbours(v).iterator();
                    while (it.hasNext()) {
                        w = it.next();
                        if (colors.get(w) == 0) {
                            colors.set(w, label);
                            parents.set(w, v);
                            struct.put(w);
                            puts++;
                            x = visited.getAndSet(w, 1);
                            if (x == 0) {
                                counter.getAndIncrement();
                            }
                        }
                    }
                }
            }
            if (numThreads > 1) {
                thread = pickRandomThread(numThreads, label);
                stolenItem = structs[thread].steal();
                if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                    struct.put(stolenItem);
                    puts++;
                    steals++;
                }
            }
        } while (counter.get() < graph.getNumberVertices());
        report.setPuts(puts++);
        report.setTakes(takes++);
        report.setSteals(puts++);
    }

    private void specialExecutionMeasurement(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parents, int root, int label, SimpleReport report) {
        int puts = 0;
        int takes = 0;
        int steals = 0;
        colors.set(root, label);
        struct.put(root, label - 1);
        int x = visited.getAndSet(root, 1);
        puts++;
        if (x == 0) {
            counter.getAndIncrement();
        }
        int v, w;
        int stolenItem;
        int thread;
        Iterator<Integer> it;
        do {
            while (!struct.isEmpty(label - 1)) {
                v = struct.take(label - 1);
                if (v >= 0) { // Ignoramos en caso de que esté vacía la cola por concurrencia
                    takes++;
                    it = graph.getNeighbours(v).iterator();
                    while (it.hasNext()) {
                        w = it.next();
                        if (colors.get(w) == 0) {
                            colors.set(w, label);
                            parents.set(w, v);
                            struct.put(w, label - 1);
                            puts++;
                            x = visited.getAndSet(w, 1);
                            if (x == 0) {
                                counter.getAndIncrement();
                            }
                        }
                    }
                }
            }
            if (numThreads > 1) {
                thread = pickRandomThread(numThreads, label);
                stolenItem = structs[thread].steal(label - 1);
                if (stolenItem >= 0) { // Ignoramos en caso de que esté vacía o intentemos robar algo que no nos corresponde.
                    steals++;
                    struct.put(stolenItem, label - 1);
                    puts++;
                }
            }
        } while (counter.get() < graph.getNumberVertices());
        report.setPuts(puts);
        report.setTakes(takes);
        report.setSteals(steals);
    }

}
