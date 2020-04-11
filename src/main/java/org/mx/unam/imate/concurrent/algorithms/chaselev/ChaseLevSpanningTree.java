package org.mx.unam.imate.concurrent.algorithms.chaselev;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mx.unam.imate.concurrent.algorithms.SpanningTree;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.GraphType;
import org.mx.unam.imate.concurrent.datastructures.GraphUtils;

/**
 *
 * @author miguel
 */
public class ChaseLevSpanningTree implements SpanningTree {

    @Override
    public Graph spanningTree(int shape, int numThreads, boolean displayInfo, boolean randomRoots, GraphType type) {
        Thread[] threads = new Thread[numThreads];
        Random r = new Random(System.currentTimeMillis());
        Graph graph = GraphUtils.graphType(shape, type);
        AtomicIntegerArray color = new AtomicIntegerArray(graph.getNumVertices());
        AtomicIntegerArray parents = new AtomicIntegerArray(GraphUtils.initializeParent(graph.getNumVertices()));
        int roots[] = GraphUtils.stubSpanning(graph, numThreads);
        graph.setRoot(roots[0]);
        AtomicInteger counter = new AtomicInteger(0);
        ChaseLevWorkStealing[] queues = new ChaseLevWorkStealing[numThreads];
        for (int i = 0; i < numThreads; i++) {
            queues[i] = new ChaseLevWorkStealing(7);
        }
        if (displayInfo) {
            System.out.print("[");
            for (int i = 0; i < roots.length; i++) {
                System.out.print(roots[i] + " ");
            }
            System.out.println("]");
        }
        int[] processors = new int[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new StepChaseLevSpanningTree(graph,
                    randomRoots ? r.nextInt(graph.getNumVertices()) : roots[i],
                    color, parents, (i + 1),
                    queues[i], queues,
                    numThreads, counter), String.format("Hilo %d", i));
        }
        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(ChaseLevSpanningTree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < graph.getNumVertices(); i++) {
            if (color.get(i) != 0) {
                processors[color.get(i) - 1]++;
            }
            if (displayInfo) {
                String val = String.format("Vértice: %d, padre: %d, color: %d", i, parents.get(i), color.get(i));
                System.out.println(val);
            }
        }
        for (int i = 0; i < numThreads; i++) {
            System.out.println(String.format("C%d: %d", (i + 1), processors[i]));
        }
        for (int i = 1; i < roots.length; i++) {
            parents.set(roots[i], roots[i - 1]);
        }
        Graph tree = GraphUtils.buildFromParents(parents);
        if (!tree.isTree()) {
            System.out.println(String.format("El error es: %s", tree.isTreeResponde()));
            WorkStealingUtils.report(graph, parents, roots);
        }
        graph = null;
        return tree;
    }

}
