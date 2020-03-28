package org.mx.unam.imate.concurrent.algorithms.idempotent.deque;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 *
 * @author miguel
 */
public class IdempotentDequeSpanningTreeTest {

    /**
     * Test of spanningTree method, of class IdempotentDequeSpanningTree.
     */
    @Test
    public void testSpanningTree2DTorusSequentialStub() {
        System.out.println("spanningTree2DTorusSequentialStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_2D;
        IdempotentDequeSpanningTree instance = new IdempotentDequeSpanningTree();
        Graph graph = instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
        Assertions.assertTrue(graph.isTree());
    }

    /**
     * Test of spanningTree method, of class IdempotentDequeSpanningTree.
     */
    @Test
    public void testSpanningTree2060DTorusSequentialStub() {
        System.out.println("spanningTree2D60TorusSequentialStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_2D_60;
        IdempotentDequeSpanningTree instance = new IdempotentDequeSpanningTree();
        Graph graph = instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
        Assertions.assertTrue(graph.isTree());
    }

    /**
     * Test of spanningTree method, of class IdempotentDequeSpanningTree.
     */
    @Test
    public void testSpanningTree3DTorusSequentialStub() {
        System.out.println("spanningTree3DTorusSequentialStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_3D;
        IdempotentDequeSpanningTree instance = new IdempotentDequeSpanningTree();
        Graph graph = instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
        Assertions.assertTrue(graph.isTree());
    }

    /**
     * Test of spanningTree method, of class IdempotentDequeSpanningTree.
     */
    @Test
    public void testSpanningTree3D40TorusSequentialStub() {
        System.out.println("spanningTree3D40TorusSequentialStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_3D_40;
        IdempotentDequeSpanningTree instance = new IdempotentDequeSpanningTree();
        Graph graph = instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
        Assertions.assertTrue(graph.isTree());
    }

    /**
     * Test of spanningTree method, of class IdempotentDequeSpanningTree.
     */
    @Test
    public void testSpanningTreeRandomTorusSequentialStub() {
        System.out.println("spanningTree2DTorusSequentialStub");
        int shape = 100000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.RANDOM;
        IdempotentDequeSpanningTree instance = new IdempotentDequeSpanningTree();
        Graph graph = instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
        Assertions.assertTrue(graph.isTree());
    }

}
