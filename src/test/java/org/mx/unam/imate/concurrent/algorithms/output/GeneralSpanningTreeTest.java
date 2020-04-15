/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.output;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 *
 * @author miguel
 */
public class GeneralSpanningTreeTest {

    private Graph generateTree(GraphType type, AlgorithmsType algType, int shape, int numThreads, int structSize, boolean report) {
        Parameters params = new Parameters(type, algType, shape, numThreads, structSize, report);
        GeneralSpanningTree instance = new GeneralSpanningTree(params);
        Graph tree = instance.spanningTree();
        return tree;
    }

    ////////////////////////////////////////////////////
    // Pruebas de algoritmo SIMPLE con work stealing. //
    ////////////////////////////////////////////////////
    @Test
    public void testSpanningTreeSIMPLETorus2D() {
        System.out.println("spanningTree SIMPLE Torus 2D");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.SIMPLE, 1000, 8, 0, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeSIMPLETorus2D60() {
        System.out.println("spanningTree SIMPLE Torus 2D 60");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.SIMPLE, 1000, 8, 0, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeSIMPLETorus3D() {
        System.out.println("spanningTree SIMPLE Torus 3D");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.SIMPLE, 100, 8, 0, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);

    }

    @Test
    public void testSpanningTreeSIMPLETorus3D40() {
        System.out.println("spanningTree SIMPLE Torus 3D 40%");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.SIMPLE, 100, 8, 0, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeSIMPLERandom() {
        System.out.println("spanningTree SIMPLE Random");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.SIMPLE, 1000000, 8, 0, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    ///////////////////////////////////
    // Pruebas de algoritmo THE Cilk //
    ///////////////////////////////////
    @Test
    public void testSpanningTreeCilkTorus2D() {
        System.out.println("spanningTree Cilk Torus 2D");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.CILK, 1000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeCilkTorus2D60() {
        System.out.println("spanningTree Cilk Torus 2D 60");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.CILK, 1000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeCilkTorus3D() {
        System.out.println("spanningTree Cilk Torus 3D");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.CILK, 100, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeCilkTorus3D40() {
        System.out.println("spanningTree Cilk Torus 3D 40%");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.CILK, 100, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeCilkRandom() {
        System.out.println("spanningTree Cilk Random");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.CILK, 1000000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    ////////////////////////////////////
    // Pruebas de algoritmo Chase-Lev //
    ////////////////////////////////////
    @Test
    public void testSpanningTreeChaseLevTorus2D() {
        System.out.println("spanningTree ChaseLev Torus 2D");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.CHASELEV, 1000, 8, 0, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeChaseLevTorus2D60() {
        System.out.println("spanningTree ChaseLev Torus 2D 60");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.CHASELEV, 1000, 8, 0, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeChaseLevTorus3D() {
        System.out.println("spanningTree ChaseLev Torus 3D");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.CHASELEV, 100, 8, 0, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeChaseLevTorus3D40() {
        System.out.println("spanningTree ChaseLev Torus 3D 40%");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.CHASELEV, 100, 8, 0, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeChaseLevRandom() {
        System.out.println("spanningTree ChaseLev Random");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.CHASELEV, 1000000, 8, 0, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    //////////////////////////////////////////////////////
    // Pruebas de algoritmo idempotent usando una Deque //
    //////////////////////////////////////////////////////
    @Test
    public void testSpanningTreeIdempotentDequeTorus2D() {
        System.out.println("spanningTree IdempotentDeque Torus 2D");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_DEQUE, 1000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentDequeTorus2D60() {
        System.out.println("spanningTree IdempotentDeque Torus 2D 60");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_DEQUE, 1000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentDequeTorus3D() {
        System.out.println("spanningTree IdempotentDeque Torus 3D");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_DEQUE, 100, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentDequeTorus3D40() {
        System.out.println("spanningTree IdempotentDeque Torus 3D 40%");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_DEQUE, 100, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentDequeRandom() {
        System.out.println("spanningTree IdempotentDeque Random");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_DEQUE, 1000000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    ////////////////////////////////////////////////////
    // Pruebas de algoritmo idempotent usando un FIFO //
    ////////////////////////////////////////////////////
    @Test
    public void testSpanningTreeIdempotentFifoTorus2D() {
        System.out.println("spanningTree IdempotentFifo Torus 2D");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_FIFO, 1000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentFifoTorus2D60() {
        System.out.println("spanningTree IdempotentFifo Torus 2D 60");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_FIFO, 1000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentFifoTorus3D() {
        System.out.println("spanningTree IdempotentFifo Torus 3D");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_FIFO, 100, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentFifoTorus3D40() {
        System.out.println("spanningTree IdempotentFifo Torus 3D 40%");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_FIFO, 100, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentFifoRandom() {
        System.out.println("spanningTree IdempotentFifo Random");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_FIFO, 1000000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    ////////////////////////////////////////////////////
    // Pruebas de algoritmo idempotent usando un Lifo //
    ////////////////////////////////////////////////////
    @Test
    public void testSpanningTreeIdempotentLifoTorus2D() {
        System.out.println("spanningTree IdempotentLifo Torus 2D");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_LIFO, 1000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentLifoTorus2D60() {
        System.out.println("spanningTree IdempotentLifo Torus 2D 60");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_LIFO, 1000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentLifoTorus3D() {
        System.out.println("spanningTree IdempotentLifo Torus 3D");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_LIFO, 100, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentLifoTorus3D40() {
        System.out.println("spanningTree IdempotentLifo Torus 3D 40%");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_LIFO, 100, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeIdempotentLifoRandom() {
        System.out.println("spanningTree IdempotentLifo Random");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_LIFO, 1000000, 8, 128, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    ///////////////////////////////////////////
    // Pruebas de algoritmo FIFOWorkStealing //
    ///////////////////////////////////////////
    @Test
    public void testSpanningTreeFifoWorkStealingV1Torus2D() {
        System.out.println("spanningTree FifoWorkStealingV1 Torus 2D");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.OURS_V1, 1000, 8, 1000 * 1000, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeFifoWorkStealingV1Torus2D60() {
        System.out.println("spanningTree FifoWorkStealingV1 Torus 2D 60");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.OURS_V1, 1000, 8, 1000 * 1000, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeFifoWorkStealingV1Torus3D() {
        System.out.println("spanningTree FifoWorkStealingV1 Torus 3D");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.OURS_V1, 100, 8, 100 * 100 * 100, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeFifoWorkStealingV1Torus3D40() {
        System.out.println("spanningTree FifoWorkStealingV1 Torus 3D 40%");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.OURS_V1, 100, 8, 100 * 100 * 100, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeFifoWorkStealingV1Random() {
        System.out.println("spanningTree FifoWorkStealingV1 Random");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.OURS_V1, 1000000, 8, 1000000, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    //////////////////////////////////////////////
    // Pruebas de algoritmo FIFOWorkStealing V2 //
    //////////////////////////////////////////////
    @Test
    public void testSpanningTreeFifoWorkStealingV2Torus2D() {
        System.out.println("spanningTree FifoWorkStealingV2 Torus 2D");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.OURS_V2, 1000, 8, 1000 * 1000, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeFifoWorkStealingV2Torus2D60() {
        System.out.println("spanningTree FifoWorkStealingV2 Torus 2D 60");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.OURS_V2, 1000, 8, 1000 * 1000, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeFifoWorkStealingV2Torus3D() {
        System.out.println("spanningTree FifoWorkStealingV2 Torus 3D");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.OURS_V2, 100, 8, 100 * 100 * 100, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeFifoWorkStealingV2Torus3D40() {
        System.out.println("spanningTree FifoWorkStealingV2 Torus 3D 40%");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.OURS_V2, 100, 8, 100 * 100 * 100, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

    @Test
    public void testSpanningTreeFifoWorkStealingV2Random() {
        System.out.println("spanningTree FifoWorkStealingV2 Random");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.OURS_V2, 1000000, 8, 1000000, false);
        String result = tree.isTreeResponse();
        System.out.println(String.format("El resultado del árbol generador es: %s", result));
        Assertions.assertEquals(Graph.ES_ARBOL, result);
    }

}
