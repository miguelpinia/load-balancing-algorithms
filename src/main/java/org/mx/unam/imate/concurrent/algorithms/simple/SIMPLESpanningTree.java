package org.mx.unam.imate.concurrent.algorithms.simple;

import org.mx.unam.imate.concurrent.algorithms.SpanningTree;
import org.mx.unam.imate.concurrent.datastructures.Graph;

/**
 *
 * @author miguel
 */
public class SIMPLESpanningTree implements SpanningTree {

    @Override
    public Graph spanningTree() {
        return null;
//        Thread[] threads = new Thread[numThreads];
//        Random r = new Random(System.currentTimeMillis());
//        Graph graph = GraphUtils.graphType(shape, type);
//        AtomicIntegerArray color = new AtomicIntegerArray(graph.getNumVertices());
//        AtomicIntegerArray parent = new AtomicIntegerArray(GraphUtils.initializeParent(graph.getNumVertices()));
//        int roots[] = GraphUtils.stubSpanning(graph, numThreads);
//        graph.setRoot(roots[0]);
//        if (displayInfo) {
//            System.out.print("[");
//            for (int i = 0; i < roots.length; i++) {
//                System.out.print(roots[i] + " ");
//            }
//            System.out.println("]");
//        }
//        int[] processors = new int[numThreads];
//        for (int i = 0; i < numThreads; i++) {
//            threads[i] = new Thread(new StepSIMPLESpanningTree(graph,
//                    randomRoots ? r.nextInt(graph.getNumVertices()) : roots[i],
//                    color,
//                    parent, (i + 1)), String.format("Hilo %d", i));
//            threads[i].start();
//        }
//        for (int i = 0; i < numThreads; i++) {
//            try {
//                threads[i].join();
//            } catch (InterruptedException ex) {
//                Logger.getLogger(SIMPLESpanningTree.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        for (int i = 0; i < graph.getNumVertices(); i++) {
//            if (color.get(i) != 0) {
//                processors[color.get(i) - 1]++;
//            }
//            if (displayInfo) {
//                String val = String.format("Vértice: %d, padre: %d, color: %d", i, parent.get(i), color.get(i));
//                System.out.println(val);
//            }
//        }
//        graph = null;
//        for (int i = 0; i < numThreads; i++) {
//            System.out.println(String.format("C%d: %d", (i + 1), processors[i]));
//        }
//        for (int i = 1; i < roots.length; i++) {
//            if (parent.get(roots[i]) == -1) {
//                parent.set(roots[i], roots[i - 1]);;
//            }
//        }
//        Graph tree = GraphUtils.buildFromParents(parent);
//        return tree;
    }

}
