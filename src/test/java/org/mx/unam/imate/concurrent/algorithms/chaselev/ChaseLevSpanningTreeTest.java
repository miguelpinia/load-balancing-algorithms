package org.mx.unam.imate.concurrent.algorithms.chaselev;

import org.junit.jupiter.api.Test;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 *
 * @author miguel
 */
public class ChaseLevSpanningTreeTest {

    /**
     * Test of spanningTree method, of class ChaseLevSpanningTree.
     */
    @Test
    public void testSpanningTree2DSequentialStub() {
        System.out.println("spanningTree2DSequentialStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_2D;
        ChaseLevSpanningTree instance = new ChaseLevSpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
    }

    /**
     * Test of spanningTree method, of class ChaseLevSpanningTree.
     */
    @Test
    public void testSpanningTree2DRandomStub() {
        System.out.println("spanningTree2DRandomStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = true;
        GraphType type = GraphType.TORUS_2D;
        ChaseLevSpanningTree instance = new ChaseLevSpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
    }

    /**
     * Test of spanningTree method, of class ChaseLevSpanningTree.
     */
    @Test
    public void testSpanningTree2D60SequentialStub() {
        System.out.println("spanningTree2D60SequentialStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_2D_60;
        ChaseLevSpanningTree instance = new ChaseLevSpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
    }

    /**
     * Test of spanningTree method, of class ChaseLevSpanningTree.
     */
    @Test
    public void testSpanningTree2D60RandomStub() {
        System.out.println("spanningTree2D60RandomStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = true;
        GraphType type = GraphType.TORUS_2D_60;
        ChaseLevSpanningTree instance = new ChaseLevSpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
    }

    /**
     * Test of spanningTree method, of class ChaseLevSpanningTree.
     */
    @Test
    public void testSpanningTree3DSequentialStub() {
        System.out.println("spanningTree3DSequentialStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_3D;
        ChaseLevSpanningTree instance = new ChaseLevSpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
    }

    /**
     * Test of spanningTree method, of class ChaseLevSpanningTree.
     */
    @Test
    public void testSpanningTree3DRandomStub() {
        System.out.println("spanningTree3DRandomStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = true;
        GraphType type = GraphType.TORUS_3D;
        ChaseLevSpanningTree instance = new ChaseLevSpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
    }

    /**
     * Test of spanningTree method, of class ChaseLevSpanningTree.
     */
    @Test
    public void testSpanningTree3D40SequentialStub() {
        System.out.println("spanningTree3D40SequentialStub");
        int shape = 40;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.TORUS_3D_40;
        ChaseLevSpanningTree instance = new ChaseLevSpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
    }

    /**
     * Test of spanningTree method, of class ChaseLevSpanningTree.
     */
    @Test
    public void testSpanningTree3D40RandomStub() {
        System.out.println("spanningTree3D40RandomStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = true;
        GraphType type = GraphType.TORUS_3D_40;
        ChaseLevSpanningTree instance = new ChaseLevSpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
    }

    /**
     * Test of spanningTree method, of class ChaseLevSpanningTree.
     */
    @Test
    public void testSpanningTreeRandomSequentialStub() {
        System.out.println("spanningTreeRandomSequentialStub");
        int shape = 200000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = false;
        GraphType type = GraphType.RANDOM;
        ChaseLevSpanningTree instance = new ChaseLevSpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
    }

    /**
     * Test of spanningTree method, of class ChaseLevSpanningTree.
     */
    @Test
    public void testSpanningTreeRandomRandomStub() {
        System.out.println("spanningRandomRandomStub");
        int shape = 200000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomRoots = true;
        GraphType type = GraphType.RANDOM;
        ChaseLevSpanningTree instance = new ChaseLevSpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomRoots, type);
        instance = null;
    }
}
