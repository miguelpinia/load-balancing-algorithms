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
        StepSpanningTreeType ssttype = StepSpanningTreeType.COUNTER;
        Parameters params = new Parameters(type, algType, shape, numThreads, structSize, report, 1, ssttype, directed, true);
        Graph graph = GraphUtils.graphType(shape, type, directed);
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
        System.out.println("Test the execution of spanning tree for the torus 2D without use work-stealing");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.SIMPLE, 1000, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLEDirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for the directed torus 2D without use work-stealing");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.SIMPLE, 1000, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 2D60 without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLETorus2D60() {
        System.out.println("Test the execution of spanning tree for the torus 2D60 without use work-stealing");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.SIMPLE, 1000, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D60 without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLEDirectedTorus2D60() {
        System.out.println("Test the execution of spanning tree for the directed torus 2D60 without use work-stealing");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.SIMPLE, 1000, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLETorus3D() {
        System.out.println("Test the execution of spanning tree for the torus 3D without use work-stealing");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.SIMPLE, 100, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLEDirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for the directed torus 3D without use work-stealing");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.SIMPLE, 100, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D40 without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLETorus3D40() {
        System.out.println("Test the execution of spanning tree for the torus 3D40 without use work-stealing");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.SIMPLE, 100, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D40 without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLEDirectedTorus3D40() {
        System.out.println("Test the execution of spanning tree for the directed torus 3D40 without use work-stealing");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.SIMPLE, 100, 8, 0, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the random graph without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLERandom() {
        System.out.println("Test the execution of spanning tree for the random graph without use work-stealing");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.SIMPLE, 1000000, 8, 0, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed random graph without use work-stealing")
    @Test
    public void testSpanningTreeSIMPLEDirectedRandom() {
        System.out.println("Test the execution of spanning tree for the directed random graph without use work-stealing");
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
        System.out.println("Test the execution of spanning tree for the torus 2D using Cilk Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.CILK, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkDirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for the directed torus 2D using Cilk Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.CILK, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 2D60 using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkTorus2D60() {
        System.out.println("Test the execution of spanning tree for the torus 2D60 using Cilk Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.CILK, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D60 using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkDirectedTorus2D60() {
        System.out.println("Test the execution of spanning tree for the directed torus 2D60 using Cilk Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.CILK, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkTorus3D() {
        System.out.println("Test the execution of spanning tree for the torus 3D using Cilk Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.CILK, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkDirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for the directed torus 3D using Cilk Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.CILK, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D40 using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkTorus3D40() {
        System.out.println("Test the execution of spanning tree for the torus 3D40 using Cilk Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.CILK, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D40 using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkDirectedTorus3D40() {
        System.out.println("Test the execution of spanning tree for the directed torus 3D40 using Cilk Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.CILK, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the random graph using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkRandom() {
        System.out.println("Test the execution of spanning tree for the random graph using Cilk Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.CILK, 1000000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed random graph using Cilk Work-Stealing algorithm")
    @Test
    public void testSpanningTreeCilkDirectedRandom() {
        System.out.println("Test the execution of spanning tree for the directed random graph using Cilk Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.CILK, 1000000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }
    ////////////////////////////////////
    // Pruebas de algoritmo Chase-Lev //
    ////////////////////////////////////

    @DisplayName("Test the execution of spanning tree for the torus 2D using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevTorus2D() {
        System.out.println("Test the execution of spanning tree for the torus 2D using Chase-Lev Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.CHASELEV, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevDirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for the directed torus 2D using Chase-Lev Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.CHASELEV, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 2D60 using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevTorus2D60() {
        System.out.println("Test the execution of spanning tree for the torus 2D60 using Chase-Lev Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.CHASELEV, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 2D60 using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevDirectedTorus2D60() {
        System.out.println("Test the execution of spanning tree for the directed torus 2D60 using Chase-Lev Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.CHASELEV, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevTorus3D() {
        System.out.println("Test the execution of spanning tree for the torus 3D using Chase-Lev Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.CHASELEV, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevDirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for the directed torus 3D using Chase-Lev Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.CHASELEV, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the torus 3D40 using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevTorus3D40() {
        System.out.println("Test the execution of spanning tree for the torus 3D40 using Chase-Lev Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.CHASELEV, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed torus 3D40 using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevDirectedTorus3D40() {
        System.out.println("Test the execution of spanning tree for the directed torus 3D40 using Chase-Lev Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.CHASELEV, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the random graph using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevRandom() {
        System.out.println("Test the execution of spanning tree for the random graph using Chase-Lev Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.CHASELEV, 1000000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for the directed random graph using Chase-Lev Work-Stealing algorithm")
    @Test
    public void testSpanningTreeChaseLevDirectedRandom() {
        System.out.println("Test the execution of spanning tree for the directed random graph using Chase-Lev Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.CHASELEV, 1000000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }
    //////////////////////////////////////////////////////
    // Pruebas de algoritmo idempotent usando una Deque //
    //////////////////////////////////////////////////////

    @DisplayName("Test the execution of spanning tree for torus 2D using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeTorus2D() {
        System.out.println("Test the execution of spanning tree for torus 2D using IdempotentDeque Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_DEQUE, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeDirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for directed torus 2D using IdempotentDeque Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_DEQUE, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeTorus2D60() {
        System.out.println("Test the execution of spanning tree for torus 2D60 using IdempotentDeque Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_DEQUE, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeDirectedTorus2D60() {
        System.out.println("Test the execution of spanning tree for directed torus 2D60 using IdempotentDeque Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_DEQUE, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeTorus3D() {
        System.out.println("Test the execution of spanning tree for torus 3D using IdempotentDeque Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_DEQUE, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeDirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for directed torus 3D using IdempotentDeque Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_DEQUE, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeTorus3D40() {
        System.out.println("Test the execution of spanning tree for torus 3D40 using IdempotentDeque Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_DEQUE, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeDirectedTorus3D40() {
        System.out.println("Test the execution of spanning tree for directed torus 3D40 using IdempotentDeque Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_DEQUE, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for random graph using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeRandom() {
        System.out.println("Test the execution of spanning tree for random graph using IdempotentDeque Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_DEQUE, 1000000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed random graph using IdempotentDeque Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentDequeDirectedRandom() {
        System.out.println("Test the execution of spanning tree for directed random graph using IdempotentDeque Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_DEQUE, 1000000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }
    ////////////////////////////////////////////////////
    // Pruebas de algoritmo idempotent usando un FIFO //
    ////////////////////////////////////////////////////

    @DisplayName("Test the execution of spanning tree for torus 2D using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoTorus2D() {
        System.out.println("Test the execution of spanning tree for torus 2D using IdempotentFIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_FIFO, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoDirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for directed torus 2D using IdempotentFIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_FIFO, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoTorus2D60() {
        System.out.println("Test the execution of spanning tree for torus 2D60 using IdempotentFIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_FIFO, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoDirectedTorus2D60() {
        System.out.println("Test the execution of spanning tree for directed torus 2D60 using IdempotentFIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_FIFO, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoTorus3D() {
        System.out.println("Test the execution of spanning tree for torus 3D using IdempotentFIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_FIFO, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoDirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for directed torus 3D using IdempotentFIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_FIFO, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoTorus3D40() {
        System.out.println("Test the execution of spanning tree for torus 3D40 using IdempotentFIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_FIFO, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoDirectedTorus3D40() {
        System.out.println("Test the execution of spanning tree for directed torus 3D40 using IdempotentFIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_FIFO, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for random graph using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoRandom() {
        System.out.println("Test the execution of spanning tree for random graph using IdempotentFIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_FIFO, 1000000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed random graph using IdempotentFIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentFifoDirectedRandom() {
        System.out.println("Test the execution of spanning tree for directed random graph using IdempotentFIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_FIFO, 1000000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }
    ////////////////////////////////////////////////////
    // Pruebas de algoritmo idempotent usando un Lifo //
    ////////////////////////////////////////////////////

    @DisplayName("Test the execution of spanning tree for torus 2D using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoTorus2D() {
        System.out.println("Test the execution of spanning tree for torus 2D using IdempotentLIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_LIFO, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoDirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for directed torus 2D using IdempotentLIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_LIFO, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoTorus2D60() {
        System.out.println("Test the execution of spanning tree for torus 2D60 using IdempotentLIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_LIFO, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoDirectedTorus2D60() {
        System.out.println("Test the execution of spanning tree for directed torus 2D60 using IdempotentLIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.IDEMPOTENT_LIFO, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoTorus3D() {
        System.out.println("Test the execution of spanning tree for torus 3D using IdempotentLIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_LIFO, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoDirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for directed torus 3D using IdempotentLIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.IDEMPOTENT_LIFO, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoTorus3D40() {
        System.out.println("Test the execution of spanning tree for torus 3D40 using IdempotentLIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_LIFO, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoDirectedTorus3D40() {
        System.out.println("Test the execution of spanning tree for directed torus 3D40 using IdempotentLIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.IDEMPOTENT_LIFO, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for random graph using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoRandom() {
        System.out.println("Test the execution of spanning tree for random graph using IdempotentLIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_LIFO, 1000000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed random graph using IdempotentLIFO Work-Stealing algorithm")
    @Test
    public void testSpanningTreeIdempotentLifoDirectedRandom() {
        System.out.println("Test the execution of spanning tree for directed random graph using IdempotentLIFO Work-Stealing algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.IDEMPOTENT_LIFO, 1000000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }
    ////////////////////////////////////////////////////////////
    // Pruebas de algoritmo bounded non-blocking workstealing //
    ////////////////////////////////////////////////////////////

    @DisplayName("Test the execution of spanning tree for torus 2D using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGTorus2D() {
        System.out.println("Test the execution of spanning tree for torus 2D using WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.WS_NC_MULT, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGDirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for directed torus 2D using WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.WS_NC_MULT, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGTorus2D60() {
        System.out.println("Test the execution of spanning tree for torus 2D60 using WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.WS_NC_MULT, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGDirectedTorus2D60() {
        System.out.println("Test the execution of spanning tree for directed torus 2D60 using WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.WS_NC_MULT, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGTorus3D() {
        System.out.println("Test the execution of spanning tree for torus 3D using WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.WS_NC_MULT, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGDirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for directed torus 3D using WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.WS_NC_MULT, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGTorus3D40() {
        System.out.println("Test the execution of spanning tree for torus 3D40 using WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.WS_NC_MULT, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGDirectedTorus3D40() {
        System.out.println("Test the execution of spanning tree for directed torus 3D40 using WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.WS_NC_MULT, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for random graph using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGRandom() {
        System.out.println("Test the execution of spanning tree for random graph using WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.WS_NC_MULT, 1000000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed random graph using WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWALGDirectedRandom() {
        System.out.println("Test the execution of spanning tree for directed random graph using WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.WS_NC_MULT, 1000000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }
    ////////////////////////////////////////////////////////////
    // Pruebas de algoritmo bounded non-blocking workstealing //
    ////////////////////////////////////////////////////////////

    @DisplayName("Test the execution of spanning tree for torus 2D using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGTorus2D() {
        System.out.println("Test the execution of spanning tree for torus 2D using B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.B_WS_NC_MULT, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGDirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for directed torus 2D using B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.B_WS_NC_MULT, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGTorus2D60() {
        System.out.println("Test the execution of spanning tree for torus 2D60 using B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.B_WS_NC_MULT, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGDirectedTorus2D60() {
        System.out.println("Test the execution of spanning tree for directed torus 2D60 using B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.B_WS_NC_MULT, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGTorus3D() {
        System.out.println("Test the execution of spanning tree for torus 3D using B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.B_WS_NC_MULT, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGDirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for directed torus 3D using B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.B_WS_NC_MULT, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGTorus3D40() {
        System.out.println("Test the execution of spanning tree for torus 3D40 using B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.B_WS_NC_MULT, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGDirectedTorus3D40() {
        System.out.println("Test the execution of spanning tree for directed torus 3D40 using B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.B_WS_NC_MULT, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for random graph using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGRandom() {
        System.out.println("Test the execution of spanning tree for random graph using B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.B_WS_NC_MULT, 1000000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed random graph using B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeBNEWALGDirectedRandom() {
        System.out.println("Test the execution of spanning tree for directed random graph using B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.B_WS_NC_MULT, 1000000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    //////////////////////////////////////
    // Pruebas de algoritmo WSNCMULT_LA //
    //////////////////////////////////////
    @DisplayName("Test the execution of spanning tree for torus 2D using WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeWSNCMULTLATorus2D() {
        System.out.println("Test the execution of spanning tree for torus 2D using WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.WS_NC_MULT_LA, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeWSNCMULTLADirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for directed torus 2D using WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.WS_NC_MULT_LA, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeWSNCMULTLATorus2D60() {
        System.out.println("Test the execution of spanning tree for torus 2D60 using WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.WS_NC_MULT_LA, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

//    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using WS_NC_MULT_LA algorithm")
//    @Test
//    public void testSpanningTreeWSNCMULTLADirectedTorus2D60() {
//        System.out.println("Test the execution of spanning tree for directed torus 2D60 using WS_NC_MULT_LA algorithm");
//        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.WS_NC_MULT_LA, 1000, 8, 4096, false, true);
//        String result = GraphUtils.detectType(tree);
//        Assertions.assertEquals(GraphUtils.IS_TREE, result);
//    }
    @DisplayName("Test the execution of spanning tree for torus 3D using WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeWSNCMULTLATorus3D() {
        System.out.println("Test the execution of spanning tree for torus 3D using WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.WS_NC_MULT_LA, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeWSNCMULTLADirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for directed torus 3D using WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.WS_NC_MULT_LA, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeWSNCMULTLATorus3D40() {
        System.out.println("Test the execution of spanning tree for torus 3D40 using WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.WS_NC_MULT_LA, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

//    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using WS_NC_MULT_LA algorithm")
//    @Test
//    public void testSpanningTreeWSNCMULTLADirectedTorus3D40() {
//        System.out.println("Test the execution of spanning tree for directed torus 3D40 using WS_NC_MULT_LA algorithm");
//        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.WS_NC_MULT_LA, 100, 8, 4096, false, true);
//        String result = GraphUtils.detectType(tree);
//        Assertions.assertEquals(GraphUtils.IS_TREE, result);
//    }
    @DisplayName("Test the execution of spanning tree for random graph using WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeWSNCMULTLARandom() {
        System.out.println("Test the execution of spanning tree for random graph using WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.WS_NC_MULT_LA, 1000000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

//    @DisplayName("Test the execution of spanning tree for directed random graph using WS_NC_MULT_LA algorithm")
//    @Test
//    public void testSpanningTreeWSNCMULTLADirectedRandom() {
//        System.out.println("Test the execution of spanning tree for directed random graph using WS_NC_MULT_LA algorithm");
//        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.WS_NC_MULT_LA, 1000000, 8, 4096, false, true);
//        String result = GraphUtils.detectType(tree);
//        Assertions.assertEquals(GraphUtils.IS_TREE, result);
//    }
    ////////////////////////////////////////////////////////////////
    // Test for the bounded version of WS_NC with list of arrays. //
    ////////////////////////////////////////////////////////////////
    @DisplayName("Test the execution of spanning tree for torus 2D using B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeBWSNCMULTLATorus2D() {
        System.out.println("Test the execution of spanning tree for torus 2D using B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.B_WS_NC_MULT_LA, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeBWSNCMULTLADirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for directed torus 2D using B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.B_WS_NC_MULT_LA, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeBWSNCMULTLATorus2D60() {
        System.out.println("Test the execution of spanning tree for torus 2D60 using B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.B_WS_NC_MULT_LA, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    // @DisplayName("Test the execution of spanning tree for directed torus 2D60 using B_WS_NC_MULT_LA algorithm")
    // @Test
    // public void testSpanningTreeBWSNCMULTLADirectedTorus2D60() {
    //     System.out.println("Test the execution of spanning tree for directed torus 2D60 using B_WS_NC_MULT_LA algorithm");
    //     Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.B_WS_NC_MULT_LA, 1000, 8, 4096, false, true);
    //     String result = GraphUtils.detectType(tree);
    //     Assertions.assertEquals(GraphUtils.IS_TREE, result);
    // }
    @DisplayName("Test the execution of spanning tree for torus 3D using B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeBWSNCMULTLATorus3D() {
        System.out.println("Test the execution of spanning tree for torus 3D using B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.B_WS_NC_MULT_LA, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeBWSNCMULTLADirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for directed torus 3D using B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.B_WS_NC_MULT_LA, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeBWSNCMULTLATorus3D40() {
        System.out.println("Test the execution of spanning tree for torus 3D40 using B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.B_WS_NC_MULT_LA, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

//    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using B_WS_NC_MULT_LA algorithm")
//    @Test
//    public void testSpanningTreeBWSNCMULTLADirectedTorus3D40() {
//        System.out.println("Test the execution of spanning tree for directed torus 3D40 using B_WS_NC_MULT_LA algorithm");
//        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.B_WS_NC_MULT_LA, 100, 8, 4096, false, true);
//        String result = GraphUtils.detectType(tree);
//        Assertions.assertEquals(GraphUtils.IS_TREE, result);
//    }
    @DisplayName("Test the execution of spanning tree for random graph using B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeBWSNCMULTLARandom() {
        System.out.println("Test the execution of spanning tree for random graph using B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.B_WS_NC_MULT_LA, 1000000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    // @DisplayName("Test the execution of spanning tree for directed random graph using B_WS_NC_MULT_LA algorithm")
    // @Test
    // public void testSpanningTreeBWSNCMULTLADirectedRandom() {
    //     System.out.println("Test the execution of spanning tree for directed random graph using B_WS_NC_MULT_LA algorithm");
    //     Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.B_WS_NC_MULT_LA, 1000000, 8, 4096, false, true);
    //     String result = GraphUtils.detectType(tree);
    //     Assertions.assertEquals(GraphUtils.IS_TREE, result);
    // }
    ////////////////////////////////////////////////////////////////
    // Test for the new bounded version of WS_NC using one array. //
    ////////////////////////////////////////////////////////////////
    @DisplayName("Test the execution of spanning tree for torus 2D using NEW_B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTTorus2D() {
        System.out.println("Test the execution of spanning tree for torus 2D using NEW_B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.NEW_B_WS_NC_MULT, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using NEW_B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTDirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for directed torus 2D using NEW_B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.NEW_B_WS_NC_MULT, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using NEW_B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTTorus2D60() {
        System.out.println("Test the execution of spanning tree for torus 2D60 using NEW_B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.NEW_B_WS_NC_MULT, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using NEW_B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTDirectedTorus2D60() {
        System.out.println("Test the execution of spanning tree for directed torus 2D60 using NEW_B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.NEW_B_WS_NC_MULT, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D using NEW_B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTTorus3D() {
        System.out.println("Test the execution of spanning tree for torus 3D using NEW_B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.NEW_B_WS_NC_MULT, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using NEW_B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTDirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for directed torus 3D using NEW_B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.NEW_B_WS_NC_MULT, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using NEW_B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTTorus3D40() {
        System.out.println("Test the execution of spanning tree for torus 3D40 using NEW_B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.NEW_B_WS_NC_MULT, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using NEW_B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTDirectedTorus3D40() {
        System.out.println("Test the execution of spanning tree for directed torus 3D40 using NEW_B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.NEW_B_WS_NC_MULT, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for random graph using NEW_B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTRandom() {
        System.out.println("Test the execution of spanning tree for random graph using NEW_B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.NEW_B_WS_NC_MULT, 1000000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed random graph using NEW_B_WS_NC_MULT algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTDirectedRandom() {
        System.out.println("Test the execution of spanning tree for directed random graph using NEW_B_WS_NC_MULT algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.NEW_B_WS_NC_MULT, 1000000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Test for the new bounded version of WS_NC using one array list of arrays. //
    ///////////////////////////////////////////////////////////////////////////////

    @DisplayName("Test the execution of spanning tree for torus 2D using NEW_B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTLATorus2D() {
        System.out.println("Test the execution of spanning tree for torus 2D using NEW_B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.NEW_B_WS_NC_MULT_LA, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 2D using NEW_B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTLADirectedTorus2D() {
        System.out.println("Test the execution of spanning tree for directed torus 2D using NEW_B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D, AlgorithmsType.NEW_B_WS_NC_MULT_LA, 1000, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 2D60 using NEW_B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTLATorus2D60() {
        System.out.println("Test the execution of spanning tree for torus 2D60 using NEW_B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.NEW_B_WS_NC_MULT_LA, 1000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

//    @DisplayName("Test the execution of spanning tree for directed torus 2D60 using NEW_B_WS_NC_MULT_LA algorithm")
//    @Test
//    public void testSpanningTreeNEWBWSNCMULTLADirectedTorus2D60() {
//        System.out.println("Test the execution of spanning tree for directed torus 2D60 using NEW_B_WS_NC_MULT_LA algorithm");
//        Graph tree = generateTree(GraphType.TORUS_2D_60, AlgorithmsType.NEW_B_WS_NC_MULT_LA, 1000, 8, 4096, false, true);
//        String result = GraphUtils.detectType(tree);
//        Assertions.assertEquals(GraphUtils.IS_TREE, result);
//    }
    @DisplayName("Test the execution of spanning tree for torus 3D using NEW_B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTLATorus3D() {
        System.out.println("Test the execution of spanning tree for torus 3D using NEW_B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.NEW_B_WS_NC_MULT_LA, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for directed torus 3D using NEW_B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTLADirectedTorus3D() {
        System.out.println("Test the execution of spanning tree for directed torus 3D using NEW_B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D, AlgorithmsType.NEW_B_WS_NC_MULT_LA, 100, 8, 4096, false, true);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    @DisplayName("Test the execution of spanning tree for torus 3D40 using NEW_B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTLATorus3D40() {
        System.out.println("Test the execution of spanning tree for torus 3D40 using NEW_B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.NEW_B_WS_NC_MULT_LA, 100, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

//    @DisplayName("Test the execution of spanning tree for directed torus 3D40 using NEW_B_WS_NC_MULT_LA algorithm")
//    @Test
//    public void testSpanningTreeNEWBWSNCMULTLADirectedTorus3D40() {
//        System.out.println("Test the execution of spanning tree for directed torus 3D40 using NEW_B_WS_NC_MULT_LA algorithm");
//        Graph tree = generateTree(GraphType.TORUS_3D_40, AlgorithmsType.NEW_B_WS_NC_MULT_LA, 100, 8, 4096, false, true);
//        String result = GraphUtils.detectType(tree);
//        Assertions.assertEquals(GraphUtils.IS_TREE, result);
//    }
    @DisplayName("Test the execution of spanning tree for random graph using NEW_B_WS_NC_MULT_LA algorithm")
    @Test
    public void testSpanningTreeNEWBWSNCMULTLARandom() {
        System.out.println("Test the execution of spanning tree for random graph using NEW_B_WS_NC_MULT_LA algorithm");
        Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.NEW_B_WS_NC_MULT_LA, 1000000, 8, 4096, false, false);
        String result = GraphUtils.detectType(tree);
        Assertions.assertEquals(GraphUtils.IS_TREE, result);
    }

    // @DisplayName("Test the execution of spanning tree for directed random graph using NEW_B_WS_NC_MULT_LA algorithm")
    // @Test
    // public void testSpanningTreeNEWBWSNCMULTLADirectedRandom() {
    //     System.out.println("Test the execution of spanning tree for directed random graph using NEW_B_WS_NC_MULT_LA algorithm");
    //     Graph tree = generateTree(GraphType.RANDOM, AlgorithmsType.NEW_B_WS_NC_MULT_LA, 1000000, 8, 4096, false, true);
    //     String result = GraphUtils.detectType(tree);
    //     Assertions.assertEquals(GraphUtils.IS_TREE, result);
    // }
}
