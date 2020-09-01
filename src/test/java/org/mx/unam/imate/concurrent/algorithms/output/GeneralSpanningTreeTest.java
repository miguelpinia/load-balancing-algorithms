package org.mx.unam.imate.concurrent.algorithms.output;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.SpanningTree;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.stepSpanningTree.StepSpanningTreeType;
import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.datastructures.graph.Graph;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphType;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphUtils;

/**
 *
 * @author miguel
 */
public class GeneralSpanningTreeTest {

    private static Graph generateTree(GraphType type, AlgorithmsType algType, int shape,
            int numThreads, int structSize, boolean report, boolean directed) {
        StepSpanningTreeType ssttype = StepSpanningTreeType.DOUBLE_COLLECT;
        Parameters params = new Parameters(type, algType, shape, numThreads, structSize, report, 1, ssttype, directed, true);
        Graph graph = GraphUtils.graphType(shape, type, report);
        int[] roots = GraphUtils.stubSpanning(graph, params.getNumThreads());
        Report r = new Report();
        SpanningTree st = new SpanningTree();
        Graph tree = st.spanningTree(graph, roots, r, params);
        return tree;
    }

    ////////////////////////////////////////////////////
    // Pruebas de algoritmo SIMPLE con work stealing. //
    ////////////////////////////////////////////////////
    @DisplayName("Test the execution of spanning tree for the torus 2D without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLETorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.SIMPLE, 1000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLEDirectedTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.SIMPLE, 1000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 2D60 without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLETorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.SIMPLE, 1000, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D60 without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLEDirectedTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.SIMPLE, 1000, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLETorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.SIMPLE, 100, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLEDirectedTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.SIMPLE, 100, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D40 without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLETorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.SIMPLE, 100, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D40 without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLEDirectedTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.SIMPLE, 100, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the random graph without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLERandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.SIMPLE, 1000000, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed random graph without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLEDirectedRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.SIMPLE, 1000000, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    ///////////////////////////////////
    // Pruebas de algoritmo THE Cilk //
    ///////////////////////////////////
    @DisplayName("Test the execution of spanning tree for the torus 2D using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.CILK, 1000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkDirectedTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.CILK, 1000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 2D60 using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.CILK, 1000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D60 using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkDirectedTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.CILK, 1000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.CILK, 100, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkDirectedTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.CILK, 100, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D40 using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.CILK, 100, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D40 using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkDirectedTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.CILK, 100, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the random graph using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.CILK, 1000000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed random graph using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkDirectedRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.CILK, 1000000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    ////////////////////////////////////
    // Pruebas de algoritmo Chase-Lev //
    ////////////////////////////////////
    @DisplayName("Test the execution of spanning tree for the torus 2D using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.CHASELEV, 1000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevDirectedTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.CHASELEV, 1000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 2D60 using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.CHASELEV, 1000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D60 using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevDirectedTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.CHASELEV, 1000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.CHASELEV, 100, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevDirectedTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.CHASELEV, 100, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D40 using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.CHASELEV, 100, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D40 using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevDirectedTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.CHASELEV, 100, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the random graph using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.CHASELEV, 1000000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed random graph using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevDirectedRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.CHASELEV, 1000000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    //////////////////////////////////////////////////////
    // Pruebas de algoritmo idempotent usando una Deque //
    //////////////////////////////////////////////////////
    @DisplayName("Test the execution of spanning tree for torus 2D using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_DEQUE, 1000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeDirectedTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_DEQUE, 1000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_DEQUE, 1000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeDirectedTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_DEQUE, 1000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);

    }

    @DisplayName("Test the execution of spanning tree for torus 3D using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_DEQUE, 100, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeDirectedTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_DEQUE, 100, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_DEQUE, 100, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeDirectedTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_DEQUE, 100, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for random graph using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_DEQUE, 1000000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed random graph using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeDirectedRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_DEQUE, 1000000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    ////////////////////////////////////////////////////
    // Pruebas de algoritmo idempotent usando un FIFO //
    ////////////////////////////////////////////////////
    @DisplayName("Test the execution of spanning tree for torus 2D using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_FIFO, 1000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoDirectedTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_FIFO, 1000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_FIFO, 1000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoDirectedTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_FIFO, 1000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_FIFO, 100, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoDirectedTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_FIFO, 100, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_FIFO, 100, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoDirectedTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_FIFO, 100, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for random graph using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_FIFO, 1000000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed random graph using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoDirectedRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_FIFO, 1000000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    ////////////////////////////////////////////////////
    // Pruebas de algoritmo idempotent usando un Lifo //
    ////////////////////////////////////////////////////
    @DisplayName("Test the execution of spanning tree for torus 2D using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_LIFO, 1000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoDirectedTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_LIFO, 1000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_LIFO, 1000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoDirectedTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_LIFO, 1000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_LIFO, 100, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoDirectedTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_LIFO, 100, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_LIFO, 100, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoDirectedTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_LIFO, 100, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for random graph using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_LIFO, 1000000, 8, 128, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed random graph using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoDirectedRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_LIFO, 1000000, 8, 128, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    ////////////////////////////////////////////////////////////
    // Pruebas de algoritmo bounded non-blocking workstealing //
    ////////////////////////////////////////////////////////////
    @DisplayName("Test the execution of spanning tree for torus 2D using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.WS_NC_MULT, 1000, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGDirectedTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.WS_NC_MULT, 1000, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.WS_NC_MULT, 1000, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGDirectedTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.WS_NC_MULT, 1000, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.WS_NC_MULT, 100, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGDirectedTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.WS_NC_MULT, 100, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.WS_NC_MULT, 100, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGDirectedTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.WS_NC_MULT, 100, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for random graph using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.WS_NC_MULT, 1000000, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed random graph using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGDirectedRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.WS_NC_MULT, 1000000, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    ////////////////////////////////////////////////////////////
    // Pruebas de algoritmo bounded non-blocking workstealing //
    ////////////////////////////////////////////////////////////
    @DisplayName("Test the execution of spanning tree for torus 2D using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.B_WS_NC_MULT, 1000, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGDirectedTorus2D() {
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.B_WS_NC_MULT, 1000, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.B_WS_NC_MULT, 1000, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGDirectedTorus2D60() {
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.B_WS_NC_MULT, 1000, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.B_WS_NC_MULT, 100, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGDirectedTorus3D() {
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.B_WS_NC_MULT, 100, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.B_WS_NC_MULT, 100, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGDirectedTorus3D40() {
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.B_WS_NC_MULT, 100, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for random graph using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.B_WS_NC_MULT, 1000000, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed random graph using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGDirectedRandom() {
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.B_WS_NC_MULT, 1000000, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }
}
