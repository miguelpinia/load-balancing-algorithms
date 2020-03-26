package org.mx.unam.imate.concurrent.algorithms;

import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 *
 * @author miguel
 */
public interface SpanningTree {

    Graph spanningTree(int shape, int numThreads, boolean displayInfo, boolean randomRoots, GraphType type);

}
