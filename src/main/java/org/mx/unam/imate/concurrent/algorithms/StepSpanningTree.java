/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms;

import org.mx.unam.imate.concurrent.datastructures.Graph;

/**
 *
 * @author miguel
 */
public interface StepSpanningTree extends Runnable {

    void graph_traversal_step(Graph graph, int root, int[] color, int[] parent, int label);

    @Override
    void run();

}
