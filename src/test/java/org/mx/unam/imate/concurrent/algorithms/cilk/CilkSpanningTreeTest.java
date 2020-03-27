package org.mx.unam.imate.concurrent.algorithms.cilk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 *
 * @author miguel
 */
public class CilkSpanningTreeTest {

    /**
     * Test of spanningTree method, of class CilkSpanningTree.
     */
    @Test
    public void testSpanningTree2DTorusSequentialStub() {
        System.out.println("spanningTree2DTorusSequentialStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_2D;
        CilkSpanningTree instance = new CilkSpanningTree();
        Graph graph = instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
        Assertions.assertTrue(graph.isTree());
    }

//    /**
//     * Test of spanningTree method, of class CilkSpanningTree.
//     */
//    @Test
//    public void testSpanningTree2DTorusRandomStub() {
//        System.out.println("spanningTree2DTorusRandomStub");
//        int shape = 1000;
//        int numThreads = 8;
//        boolean displayInfo = false;
//        boolean randomRoots = true;
//        GraphType type = GraphType.TORUS_2D;
//        CilkSpanningTree instance = new CilkSpanningTree();
//        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
//        instance = null;
//    }
    /**
     * Test of spanningTree method, of class CilkSpanningTree.
     */
    @Test
    public void testSpanningTree2D60TorusSequentialStub() {
        System.out.println("spanningTree2D60TorusSequentialStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_2D_60;
        CilkSpanningTree instance = new CilkSpanningTree();
        Graph graph = instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
        Assertions.assertTrue(graph.isTree());
    }

//    /**
//     * Test of spanningTree method, of class CilkSpanningTree.
//     */
//    @Test
//    public void testSpanningTree2D60TorusRandomStub() {
//        System.out.println("spanningTree2D60TorusRandomStub");
//        int shape = 1000;
//        int numThreads = 8;
//        boolean displayInfo = false;
//        boolean randomRoots = true;
//        GraphType type = GraphType.TORUS_2D_60;
//        CilkSpanningTree instance = new CilkSpanningTree();
//        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
//        instance = null;
//    }
    /**
     * // * Test of spanningTree method, of class CilkSpanningTree. //
     */
    @Test
    public void testSpanningTree3DTorusSequentialStub() {
        System.out.println("spanningTree3DTorusSequentialStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_3D;
        CilkSpanningTree instance = new CilkSpanningTree();
        Graph graph = instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
        Assertions.assertTrue(graph.isTree());
    }

//    /**
//     * Test of spanningTree method, of class CilkSpanningTree.
//     */
//    @Test
//    public void testSpanningTree3DTorusRandomStub() {
//        System.out.println("spanningTree3DTorusRandomStub");
//        int shape = 100;
//        int numThreads = 8;
//        boolean displayInfo = false;
//        boolean randomRoots = true;
//        GraphType type = GraphType.TORUS_3D;
//        CilkSpanningTree instance = new CilkSpanningTree();
//        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
//        instance = null;
//    }
    /**
     * // * Test of spanningTree method, of class CilkSpanningTree. //
     */
    @Test
    public void testSpanningTree3D40TorusSequentialStub() {
        System.out.println("spanningTree3D40TorusSequentialStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_3D_40;
        CilkSpanningTree instance = new CilkSpanningTree();
        Graph graph = instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
        Assertions.assertTrue(graph.isTree());
    }

    /**
     * // * Test of spanningTree method, of class CilkSpanningTree. //
     */
    @Test
    public void testSpanningTreeRandomTorusSequentialStub() {
        System.out.println("spanningTreeRandomTorusSequentialStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.RANDOM;
        CilkSpanningTree instance = new CilkSpanningTree();
        Graph graph = instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
        Assertions.assertTrue(graph.isTree());
    }

}
