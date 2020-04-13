/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.simple;

import java.util.concurrent.atomic.AtomicIntegerArray;

import org.mx.unam.imate.concurrent.algorithms.Report;
import org.mx.unam.imate.concurrent.algorithms.StepSpanningTree;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.Node;
import org.mx.unam.imate.concurrent.datastructures.Stack;

/**
 *
 * @author miguel
 */
public class StepSIMPLESpanningTree implements StepSpanningTree {

    private final Graph graph;
    private final int root;
    private final AtomicIntegerArray color;
    private final AtomicIntegerArray parent;
    private final int label;
    private Report report;

    public StepSIMPLESpanningTree(Graph graph, AtomicIntegerArray color, AtomicIntegerArray parent, int root, int label, Report report) {
        this.graph = graph;
        this.root = root;
        this.color = color;
        this.parent = parent;
        this.label = label;
        this.report = report;
    }

    @Override
    public void run() {
        graph_traversal_step(graph, color, parent, root, label, report);
    }

    @Override
    public void graph_traversal_step(Graph graph, AtomicIntegerArray colors, AtomicIntegerArray parent, int root, int label, Report report) {
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
