/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.simple;

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
    private final int[] color;
    private final int[] parent;
    private final int label;

    public StepSIMPLESpanningTree(Graph graph, int root, int[] color, int[] parent, int label) {
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

    @Override
    public void graph_traversal_step(Graph graph, int root, int[] color, int[] parent, int label) {
        Stack stack = new Stack();
        color[root] = label;
        stack.push(root);
        int v, w;
        while (!stack.isEmpty()) {
            v = stack.pop();
            Node ptr = graph.getVertices()[v];
            while (ptr != null) {
                w = ptr.getVal();
                if (color[w] == 0) {
                    color[w] = label;
                    parent[w] = v;
                    stack.push(w);
                }
                ptr = ptr.getNext();
            }
        }
    }

}
