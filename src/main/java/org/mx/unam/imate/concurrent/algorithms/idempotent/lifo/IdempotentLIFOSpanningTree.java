/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.idempotent.lifo;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mx.unam.imate.concurrent.algorithms.SpanningTree;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.GraphType;
import org.mx.unam.imate.concurrent.datastructures.GraphUtils;

/**
 *
 * @author miguel
 */
public class IdempotentLIFOSpanningTree implements SpanningTree {

    @Override
    public void spanningTree(int shape, int numThreads, boolean displayInfo, boolean randomRoots, GraphType type) {
        Thread[] threads = new Thread[numThreads];
        Random r = new Random(System.currentTimeMillis());
        Graph graph = GraphUtils.graphType(shape, type);
        int[] color = new int[graph.getNumVertices()];
        int[] parent = new int[graph.getNumVertices()];
        int roots[] = GraphUtils.stubSpanning(graph, numThreads);
        AtomicInteger counter = new AtomicInteger(0);
        IdempotentWorkStealingLIFO[] queues = new IdempotentWorkStealingLIFO[numThreads];
        for (int i = 0; i < numThreads; i++) {
            queues[i] = new IdempotentWorkStealingLIFO(128);
        }
        if (displayInfo) {
            System.out.println(Arrays.toString(roots));
        }
        int[] processors = new int[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new StepIdempotentLIFOSpanningTree(graph,
                    randomRoots ? r.nextInt(graph.getNumVertices()) : roots[i],
                    color, parent, (i + 1),
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
                Logger.getLogger(IdempotentLIFOSpanningTree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < graph.getNumVertices(); i++) {
            if (color[i] != 0) {
                processors[color[i] - 1]++;
            }
            if (displayInfo) {
                String val = String.format("Vértice: %d, padre: %d, color: %d", i, parent[i], color[i]);
                System.out.println(val);
            }
        }
        for (int i = 0; i < numThreads; i++) {
            System.out.println(String.format("C%d: %d", (i + 1), processors[i]));
        }
    }
}