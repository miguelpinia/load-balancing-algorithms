/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.simple;

import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.Node;
import org.mx.unam.imate.concurrent.datastructures.Stack;

/**
 *
 * @author miguel
 */
public class StepSIMPLESpanningTree implements Runnable {

    private final Graph graph;
    private final int root;
    private final AtomicIntegerArray color;
    private final AtomicIntegerArray parent;
    private final int label;

    public StepSIMPLESpanningTree(Graph graph, int root, AtomicIntegerArray color, AtomicIntegerArray parent, int label) {
        this.graph = graph;
        this.root = root;
        this.color = color;
        this.parent = parent;
        this.label = label;
    }

    @Override
    public void run() {
        graph_traversal_step(graph, root, color, parent, label);
    }

    public void graph_traversal_step(Graph graph, int root, AtomicIntegerArray color,
            AtomicIntegerArray parent, int label) {
        Stack stack = new Stack();
        color.set(root, label);
        stack.push(root);
        int v, w;
        while (!stack.isEmpty()) {
            v = stack.pop();
            Node ptr = graph.getVertices()[v];
            while (ptr != null) {
                w = ptr.getVal();
                if (color.get(w) == 0) {
                    color.set(w, label);
                    parent.set(w, v);
                    stack.push(w);
                }
                ptr = ptr.getNext();
            }
        }
    }

}
