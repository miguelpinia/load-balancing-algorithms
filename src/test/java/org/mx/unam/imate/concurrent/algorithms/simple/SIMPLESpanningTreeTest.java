package org.mx.unam.imate.concurrent.algorithms.simple;

import org.junit.jupiter.api.Test;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 *
 * @author miguel
 */
public class SIMPLESpanningTreeTest {

    /**
     * Test of spanningTree method, of class SIMPLESpanningTree.
     */
    @Test
    public void testSpanningTree2DTorusSequentialStub() {
        System.out.println("spanningTree2DTorusSequentialStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomStub = false;
        SIMPLESpanningTree instance = new SIMPLESpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomStub, GraphType.TORUS_2D);
    }

    /**
     * Test of spanningTree method, of class SIMPLESpanningTree.
     */
    @Test
    public void testSpanningTree2DTorusRandomStub() {
        System.out.println("spanningTree2DTorusRandomStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        SIMPLESpanningTree instance = new SIMPLESpanningTree();
        boolean randomStub = true;
        instance.spanningTree(shape, numThreads, displayInfo, randomStub, GraphType.TORUS_2D);
    }

    /**
     * Test of spanningTree2D method, of class SIMPLESpanningTree.
     */
    @Test
    public void testSpanningTree2D60TorusSequentialStub() {
        System.out.println("spanningTree2D60TorusSequentialStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomStub = false;
        SIMPLESpanningTree instance = new SIMPLESpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomStub, GraphType.TORUS_2D_60);
    }

    /**
     * Test of spanningTree method, of class SIMPLESpanningTree.
     */
    @Test
    public void testSpanningTree2D60TorusRandomStub() {
        System.out.println("spanningTree2D60TorusRandomStub");
        int shape = 1000;
        int numThreads = 8;
        boolean displayInfo = false;
        SIMPLESpanningTree instance = new SIMPLESpanningTree();
        boolean randomStub = true;
        instance.spanningTree(shape, numThreads, displayInfo, randomStub, GraphType.TORUS_2D_60);
    }

    /**
     * Test of spanningTree method, of class SIMPLESpanningTree.
     */
    @Test
    public void testSpanningTree3DTorusSequentialStub() {
        System.out.println("spanningTree3DTorusSequentialStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomStub = false;
        SIMPLESpanningTree instance = new SIMPLESpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomStub, GraphType.TORUS_3D);
    }

    /**
     * Test of spanningTree method, of class SIMPLESpanningTree.
     */
    @Test
    public void testSpanningTree3DTorusRandomStub() {
        System.out.println("spanningTree3DTorusRandomStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        SIMPLESpanningTree instance = new SIMPLESpanningTree();
        boolean randomStub = true;
        instance.spanningTree(shape, numThreads, displayInfo, randomStub, GraphType.TORUS_3D);
    }

    /**
     * Test of spanningTree method, of class SIMPLESpanningTree.
     */
    @Test
    public void testSpanningTree3D40TorusSequentialStub() {
        System.out.println("spanningTree3D40TorusSequentialStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomStub = false;
        SIMPLESpanningTree instance = new SIMPLESpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomStub, GraphType.TORUS_3D_40);
    }

    /**
     * Test of spanningTree method, of class SIMPLESpanningTree.
     */
    @Test
    public void testSpanningTree3D40TorusRandomStub() {
        System.out.println("spanningTree3D40TorusRandomStub");
        int shape = 100;
        int numThreads = 8;
        boolean displayInfo = false;
        SIMPLESpanningTree instance = new SIMPLESpanningTree();
        boolean randomStub = true;
        instance.spanningTree(shape, numThreads, displayInfo, randomStub, GraphType.TORUS_3D_40);
    }

    /**
     * Test of spanningTree method, of class SIMPLESpanningTree.
     */
    @Test
    public void testSpanningTreeRandomSequentialStub() {
        System.out.println("spanningTreeRandomSequentialStub");
        int shape = 200000;
        int numThreads = 8;
        boolean displayInfo = false;
        boolean randomStub = false;
        SIMPLESpanningTree instance = new SIMPLESpanningTree();
        instance.spanningTree(shape, numThreads, displayInfo, randomStub, GraphType.RANDOM);
    }

    /**
     * Test of spanningTree method, of class SIMPLESpanningTree.
     */
    @Test
    public void testSpanningTreeRandomRandomStub() {
        System.out.println("spanningTreeRandomRandomStub");
        int shape = 200000;
        int numThreads = 8;
        boolean displayInfo = false;
        SIMPLESpanningTree instance = new SIMPLESpanningTree();
        boolean randomStub = true;
        instance.spanningTree(shape, numThreads, displayInfo, randomStub, GraphType.RANDOM);
    }
}
