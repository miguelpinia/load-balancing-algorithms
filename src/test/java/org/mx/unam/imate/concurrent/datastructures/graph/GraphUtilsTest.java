package org.mx.unam.imate.concurrent.datastructures.graph;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mx.unam.imate.concurrent.datastructures.Edge;

/**
 *
 * @author miguel
 */
public class GraphUtilsTest {

    private static Edge[] edgesCycle;
    private static Edge[] edgesTree;
    private static Edge[] edgesDisconnected;

    @BeforeAll
    public static void setUpClass() throws Exception {
        edgesCycle = new Edge[16];
        edgesTree = new Edge[14];
        edgesDisconnected = new Edge[4];
        edgesDisconnected[0] = new Edge(0, 1);
        edgesDisconnected[1] = new Edge(1, 2);
        edgesDisconnected[2] = new Edge(3, 4);
        edgesDisconnected[3] = new Edge(4, 5);

        edgesCycle[0] = new Edge(0, 1);
        edgesCycle[1] = new Edge(0, 2);
        edgesCycle[2] = new Edge(1, 3);
        edgesCycle[3] = new Edge(1, 4);
        edgesCycle[4] = new Edge(2, 5);
        edgesCycle[5] = new Edge(2, 6);
        edgesCycle[6] = new Edge(3, 7);
        edgesCycle[7] = new Edge(3, 8);
        edgesCycle[8] = new Edge(4, 9);
        edgesCycle[9] = new Edge(4, 10);
        edgesCycle[10] = new Edge(5, 11);
        edgesCycle[11] = new Edge(5, 12);
        edgesCycle[12] = new Edge(6, 13);
        edgesCycle[13] = new Edge(6, 14);
        edgesCycle[14] = new Edge(7, 8);
        edgesCycle[15] = new Edge(13, 14);
        edgesTree[0] = new Edge(0, 1);
        edgesTree[1] = new Edge(0, 2);
        edgesTree[2] = new Edge(1, 3);
        edgesTree[3] = new Edge(1, 4);
        edgesTree[4] = new Edge(2, 5);
        edgesTree[5] = new Edge(2, 6);
        edgesTree[6] = new Edge(3, 7);
        edgesTree[7] = new Edge(3, 8);
        edgesTree[8] = new Edge(4, 9);
        edgesTree[9] = new Edge(4, 10);
        edgesTree[10] = new Edge(5, 11);
        edgesTree[11] = new Edge(5, 12);
        edgesTree[12] = new Edge(6, 13);
        edgesTree[13] = new Edge(6, 14);
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of inArray method, of class GraphUtils.
     */
    @DisplayName("Test if values is in array using the corresponding function")
    @Test
    public void testInArray() {
        int val1 = 14;
        int val2 = 13;
        int[] array = {19, 20, 14, 100, 220, 300, 150};
        boolean expResult1 = true;
        boolean expResult2 = false;
        boolean result1 = GraphUtils.inArray(val1, array);
        boolean result2 = GraphUtils.inArray(val2, array);
        Assertions.assertEquals(expResult1, result1);
        Assertions.assertEquals(expResult2, result2);
    }

    /**
     * Test of initializeArray method, of class GraphUtils.
     */
    @DisplayName("Test if the array it is initialized correctly")
    @Test
    public void testInitializeArray() {
        int length = 10;
        int defaultValue = -1;
        int[] expResult = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        int[] result = GraphUtils.initializeArray(length, defaultValue);
        Assertions.assertArrayEquals(expResult, result);
    }

    /**
     * Test of buildFromParents method, of class GraphUtils.
     */
    @DisplayName("Test if can build from array")
    @Test
    public void testBuildFromParents_3args_1() {
        int[] parents = {1, 2, 3, -1, 0};
        int root = 3;
        boolean directed = true;
        Graph expResult = GraphUtils.buildFromParents(parents, root, directed);
        int p1 = expResult.getNeighbours(0).get(0);
        Assertions.assertEquals(1, p1);
        int p2 = expResult.getNeighbours(p1).get(0);
        Assertions.assertEquals(2, p2);
        int p3 = expResult.getNeighbours(p2).get(0);
        Assertions.assertEquals(3, p3);
        Assertions.assertTrue(expResult.getNeighbours(p3).isEmpty());
        int p4 = expResult.getNeighbours(4).get(0);
        Assertions.assertEquals(0, p4);
    }

    /**
     * Test of buildFromParents method, of class GraphUtils.
     */
    @DisplayName("Test if can build from AtomicIntegerArray")
    @Test
    public void testBuildFromParents_3args_2() {
        int[] parentsArray = {1, 2, 3, -1, 0};
        AtomicIntegerArray parents = new AtomicIntegerArray(parentsArray);
        int root = 3;
        boolean directed = true;
        Graph expResult = GraphUtils.buildFromParents(parents, root, directed);
        int p1 = expResult.getNeighbours(0).get(0);
        Assertions.assertEquals(1, p1);
        int p2 = expResult.getNeighbours(p1).get(0);
        Assertions.assertEquals(2, p2);
        int p3 = expResult.getNeighbours(p2).get(0);
        Assertions.assertEquals(3, p3);
        Assertions.assertTrue(expResult.getNeighbours(p3).isEmpty());
        int p4 = expResult.getNeighbours(4).get(0);
        Assertions.assertEquals(0, p4);
    }

    /**
     * Test of MOD method, of class GraphUtils.
     */
    @DisplayName("Test if the modulus operations works fine with negative integers")
    @Test
    public void testMOD() {
        int a = -12343;
        int b = 13;
        int expResult = 7;
        int result = GraphUtils.MOD(a, b);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of torus2D method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is a torus2D")
    @Test
    public void testTorus2D() {
        int shape = 10;
        Graph result = GraphUtils.torus2D(shape);
        Random random = new Random(System.currentTimeMillis());
        Assertions.assertEquals(GraphType.TORUS_2D, result.getType());
        Assertions.assertEquals(100, result.getNumberVertices());
        Assertions.assertFalse(result.isDirected());
        for (int k = 0; k < 10; k++) {
            int randomVertex = random.nextInt(shape * shape);
            int j = randomVertex % shape;
            int i = randomVertex / shape;
            Integer[] neighbours = {
                GraphUtils.MOD((i - 1), shape) * shape + j,
                i * shape + GraphUtils.MOD((j + 1), shape),
                GraphUtils.MOD((i + 1), shape) * shape + j,
                i * shape + GraphUtils.MOD((j - 1), shape)
            };
            List<Integer> ns = Arrays.asList(neighbours);
            boolean all = ns.stream().allMatch((neighbour) -> {
                return result.getNeighbours(randomVertex).contains(neighbour);
            });
            Assertions.assertTrue(all);
        }
    }

    /**
     * Test of directedTorus2D method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is a directed torus2D")
    @Test
    public void testDirectedTorus2D() {
        int shape = 10;
        Graph result = GraphUtils.directedTorus2D(shape);
        Random random = new Random(System.currentTimeMillis());
        Assertions.assertEquals(GraphType.TORUS_2D, result.getType());
        Assertions.assertTrue(result.isDirected());
        Assertions.assertEquals(100, result.getNumberVertices());
        for (int k = 0; k < 10; k++) {
            int randomVertex = random.nextInt(shape * shape);
            int j = randomVertex % shape;
            int i = randomVertex / shape;
            Integer[] neighbours = {
                i * shape + GraphUtils.MOD((j + 1), shape),
                GraphUtils.MOD((i + 1), shape) * shape + j
            };
            List<Integer> ns = Arrays.asList(neighbours);
            boolean all = ns.stream().allMatch((neighbour) -> {
                return result.getNeighbours(randomVertex).contains(neighbour);
            });
            Assertions.assertTrue(all);
        }
    }

    /**
     * Test of torus2D60 method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is a Torus2D60")
    @Test
    public void testTorus2D60() {
        int shape = 10;
        Graph result = GraphUtils.torus2D60(shape);
        Random random = new Random(System.currentTimeMillis());
        Assertions.assertEquals(GraphType.TORUS_2D_60, result.getType());
        Assertions.assertFalse(result.isDirected());
        Assertions.assertEquals(100, result.getNumberVertices());
        for (int k = 0; k < 10; k++) {
            int randomVertex = random.nextInt(shape * shape);
            int j = randomVertex % shape;
            int i = randomVertex / shape;
            Integer[] neighbours = {
                GraphUtils.MOD((i - 1), shape) * shape + j,
                i * shape + GraphUtils.MOD((j + 1), shape),
                GraphUtils.MOD((i + 1), shape) * shape + j,
                i * shape + GraphUtils.MOD((j - 1), shape)
            };
            List<Integer> ns = Arrays.asList(neighbours);
            if (result.getNeighbours(randomVertex).size() < 4) {
                boolean some = ns.stream().anyMatch((neighbour) -> {
                    return result.getNeighbours(randomVertex).contains(neighbour);
                });
                Assertions.assertTrue(some);
            } else {
                boolean some = ns.stream().allMatch((neighbour) -> {
                    return result.getNeighbours(randomVertex).contains(neighbour);
                });
                Assertions.assertTrue(some);
            }
        }
    }

    /**
     * Test of directedTorus2D60 method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is a directed torus 2D60")
    @Test
    public void testDirectedTorus2D60() {
        int shape = 10;
        Graph result = GraphUtils.directedTorus2D60(shape);
        Random random = new Random(System.currentTimeMillis());
        Assertions.assertEquals(GraphType.TORUS_2D_60, result.getType());
        Assertions.assertTrue(result.isDirected());
        Assertions.assertEquals(100, result.getNumberVertices());
        for (int k = 0; k < 10; k++) {
            int randomVertex = random.nextInt(shape * shape);
            int j = randomVertex % shape;
            int i = randomVertex / shape;
            Integer[] neighbours = {
                i * shape + GraphUtils.MOD((j + 1), shape),
                GraphUtils.MOD((i + 1), shape) * shape + j
            };
            List<Integer> ns = Arrays.asList(neighbours);
            if (result.getNeighbours(randomVertex).size() < 2) {
                boolean some = ns.stream().anyMatch((neighbour) -> {
                    return result.getNeighbours(randomVertex).contains(neighbour);
                });
                Assertions.assertTrue(some);
            } else {
                boolean some = ns.stream().allMatch((neighbour) -> {
                    return result.getNeighbours(randomVertex).contains(neighbour);
                });
                Assertions.assertTrue(some);
            }
        }
    }

    /**
     * Test of torus3D method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is a torus 3D")
    @Test
    public void testTorus3D() {
        int shape = 10;
        Graph result = GraphUtils.torus3D(shape);
        Random random = new Random(System.currentTimeMillis());
        Assertions.assertEquals(GraphType.TORUS_3D, result.getType());
        Assertions.assertFalse(result.isDirected());
        Assertions.assertEquals(1000, result.getNumberVertices());
        for (int m = 0; m < 10; m++) {
            int randomVertex = random.nextInt(shape * shape);
            int k = randomVertex % shape;
            int j = (randomVertex / shape) % shape;
            int i = (randomVertex / (shape * shape)) % shape;
            Integer[] neighbours = {
                (i * shape * shape) + (j * shape) + GraphUtils.MOD((k - 1), shape),
                (i * shape * shape) + (j * shape) + GraphUtils.MOD((k + 1), shape),
                (i * shape * shape) + (GraphUtils.MOD((j - 1), shape) * shape) + k,
                (i * shape * shape) + (GraphUtils.MOD((j + 1), shape) * shape) + k,
                (GraphUtils.MOD((i - 1), shape) * shape * shape) + (j * shape) + k,
                (GraphUtils.MOD((i + 1), shape) * shape * shape) + (j * shape) + k
            };
            List<Integer> ns = Arrays.asList(neighbours);
            boolean all = ns.stream().allMatch((neighbour) -> {
                return result.getNeighbours(randomVertex).contains(neighbour);
            });
            Assertions.assertTrue(all);
        }
    }

    /**
     * Test of directedTorus3D method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is a directed torus 3D")
    @Test
    public void testDirectedTorus3D() {
        int shape = 10;
        Graph result = GraphUtils.directedTorus3D(shape);
        Random random = new Random(System.currentTimeMillis());
        Assertions.assertEquals(GraphType.TORUS_3D, result.getType());
        Assertions.assertTrue(result.isDirected());
        Assertions.assertEquals(1000, result.getNumberVertices());
        for (int m = 0; m < 10; m++) {
            int randomVertex = random.nextInt(shape * shape);
            int k = randomVertex % shape;
            int j = (randomVertex / shape) % shape;
            int i = (randomVertex / (shape * shape)) % shape;
            Integer[] neighbours = {
                (i * shape * shape) + (j * shape) + GraphUtils.MOD((k + 1), shape),
                (i * shape * shape) + (GraphUtils.MOD((j + 1), shape) * shape) + k,
                (GraphUtils.MOD((i + 1), shape) * shape * shape) + (j * shape) + k
            };
            List<Integer> ns = Arrays.asList(neighbours);
            boolean all = ns.stream().allMatch((neighbour) -> {
                return result.getNeighbours(randomVertex).contains(neighbour);
            });
            Assertions.assertTrue(all);
        }
    }

    /**
     * Test of torus3D40 method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is a torus 3D40")
    @Test
    public void testTorus3D40() {
        int shape = 10;
        Graph result = GraphUtils.torus3D40(shape);
        Random random = new Random(System.currentTimeMillis());
        Assertions.assertEquals(GraphType.TORUS_3D_40, result.getType());
        Assertions.assertFalse(result.isDirected());
        Assertions.assertEquals(1000, result.getNumberVertices());
        for (int m = 0; m < 10; m++) {
            int randomVertex = random.nextInt(shape * shape);
            int k = randomVertex % shape;
            int j = (randomVertex / shape) % shape;
            int i = (randomVertex / (shape * shape)) % shape;
            Integer[] neighbours = {
                (i * shape * shape) + (j * shape) + GraphUtils.MOD((k - 1), shape),
                (i * shape * shape) + (j * shape) + GraphUtils.MOD((k + 1), shape),
                (i * shape * shape) + (GraphUtils.MOD((j - 1), shape) * shape) + k,
                (i * shape * shape) + (GraphUtils.MOD((j + 1), shape) * shape) + k,
                (GraphUtils.MOD((i - 1), shape) * shape * shape) + (j * shape) + k,
                (GraphUtils.MOD((i + 1), shape) * shape * shape) + (j * shape) + k
            };
            List<Integer> ns = Arrays.asList(neighbours);
            if (result.getNeighbours(randomVertex).size() < 6) {
                boolean some = ns.stream().anyMatch((neighbour) -> {
                    return result.getNeighbours(randomVertex).contains(neighbour);
                });
                Assertions.assertTrue(some);
            } else {
                boolean some = ns.stream().allMatch((neighbour) -> {
                    return result.getNeighbours(randomVertex).contains(neighbour);
                });
                Assertions.assertTrue(some);
            }
        }
    }

    /**
     * Test of directedTorus3D40 method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is a torus 3D40")
    @Test
    public void testDirectedTorus3D40() {
        int shape = 10;
        Graph result = GraphUtils.directedTorus3D40(shape);
        Random random = new Random(System.currentTimeMillis());
        Assertions.assertEquals(GraphType.TORUS_3D_40, result.getType());
        Assertions.assertTrue(result.isDirected());
        Assertions.assertEquals(1000, result.getNumberVertices());
        for (int m = 0; m < 10; m++) {
            int randomVertex = random.nextInt(shape * shape);
            int k = randomVertex % shape;
            int j = (randomVertex / shape) % shape;
            int i = (randomVertex / (shape * shape)) % shape;
            Integer[] neighbours = {
                (i * shape * shape) + (j * shape) + GraphUtils.MOD((k + 1), shape),
                (i * shape * shape) + (GraphUtils.MOD((j + 1), shape) * shape) + k,
                (GraphUtils.MOD((i + 1), shape) * shape * shape) + (j * shape) + k
            };
            List<Integer> ns = Arrays.asList(neighbours);
            if (result.getNeighbours(randomVertex).size() < 6) {
                boolean some = ns.stream().anyMatch((neighbour) -> {
                    return result.getNeighbours(randomVertex).contains(neighbour);
                });
                Assertions.assertTrue(some);
            } else {
                boolean some = ns.stream().allMatch((neighbour) -> {
                    return result.getNeighbours(randomVertex).contains(neighbour);
                });
                Assertions.assertTrue(some);
            }
        }
    }

    /**
     * Test of random method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is random")
    @Test
    public void testRandom() {
        int shape = 100;
        Graph result = GraphUtils.random(shape, 6);
        Assertions.assertEquals(100, result.getNumberVertices());
        Assertions.assertEquals(1200, result.getNumberEdges());
        Assertions.assertEquals(GraphType.RANDOM, result.getType());
    }

    /**
     * Test of directedRandom method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is directed random")
    @Test
    public void testDirectedRandom() {
        int shape = 100;
        Graph result = GraphUtils.directedRandom(shape, 6);
        Assertions.assertEquals(100, result.getNumberVertices());
        Assertions.assertTrue(result.getNumberEdges() <= 600);
        Assertions.assertEquals(GraphType.RANDOM, result.getType());
    }

    /**
     * Test of directedKGraph method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is a directed k-graph")
    @Test
    public void testDirectedKGraph() {
        // Disabled
//        int shape = 100;
//        Graph result = GraphUtils.directedKGraph(shape, 6);
//        Assertions.assertEquals(GraphType.KGRAPH, result.getType());
//        Assertions.assertTrue(result.isDirected());
//        for (int i = 0; i < shape; i++) {
//            List<Integer> childs = result.getChilds(i);
//            List<Integer> neighbours = result.getNeighbours(i);
//            int childSize = childs.size();
//            int neighboursSize = neighbours.size();
//            Assertions.assertEquals(6, childSize);
//            Assertions.assertEquals(6, neighboursSize);
//        }
    }

    /**
     * Test of kGraph method, of class GraphUtils.
     */
    @DisplayName("Test if the generated graph is a k-graph")
    @Test
    public void testKGraph() {
        // Disabled
//        int shape = 100;
//        Graph result = GraphUtils.kGraph(shape, 6);
//        Assertions.assertEquals(GraphType.KGRAPH, result.getType());
//        Assertions.assertFalse(result.isDirected());
//        for (int i = 0; i < shape; i++) {
//            List<Integer> childs = result.getChilds(i);
//            List<Integer> neighbours = result.getNeighbours(i);
//            int neighboursSize = neighbours.size();
//            System.out.println("Neighbours: " + neighboursSize);
//            Assertions.assertTrue(neighboursSize > 1 && neighboursSize <= 12);
//        }
    }

    /**
     * Test of graphType method, of class GraphUtils.
     */
    @DisplayName("Test if the graph generated has the correct type")
    @Test
    public void testGraphType() {
        GraphType random = GraphType.RANDOM;
        GraphType torus2D = GraphType.TORUS_2D;
        GraphType torus2D60 = GraphType.TORUS_2D_60;
        GraphType torus3D = GraphType.TORUS_3D;
        GraphType torus3D40 = GraphType.TORUS_3D_40;
        GraphType kGraph = GraphType.KGRAPH;
        boolean directed = true;
        boolean undirected = false;

        Graph randomGraph = GraphUtils.random(10, 6);
        Graph directedRandomGraph = GraphUtils.directedRandom(10, 6);
        Graph torus2DGraph = GraphUtils.torus2D(10);
        Graph directedTorus2DGraph = GraphUtils.directedTorus2D(10);
        Graph torus2D60Graph = GraphUtils.torus2D60(10);
        Graph directedTorus2D60Graph = GraphUtils.directedTorus2D60(10);
        Graph torus3DGraph = GraphUtils.torus3D(10);
        Graph directedTorus3DGraph = GraphUtils.directedTorus3D(10);
        Graph torus3D40Graph = GraphUtils.torus3D40(10);
        Graph directedTorus3D40Graph = GraphUtils.directedTorus3D40(10);
        Graph kGraphKGraph = GraphUtils.kGraph(10, 6);
//        Graph directedKGraphGraph = GraphUtils.directedKGraph(10, 6);

        Assertions.assertEquals(random, randomGraph.getType());
        Assertions.assertEquals(random, directedRandomGraph.getType());
        Assertions.assertEquals(torus2D, torus2DGraph.getType());
        Assertions.assertEquals(torus2D, directedTorus2DGraph.getType());
        Assertions.assertEquals(torus2D60, torus2D60Graph.getType());
        Assertions.assertEquals(torus2D60, directedTorus2D60Graph.getType());
        Assertions.assertEquals(torus3D, torus3DGraph.getType());
        Assertions.assertEquals(torus3D, directedTorus3DGraph.getType());
        Assertions.assertEquals(torus3D40, torus3D40Graph.getType());
        Assertions.assertEquals(torus3D40, directedTorus3D40Graph.getType());
//        Assertions.assertEquals(kGraph, kGraphKGraph.getType());
//        Assertions.assertEquals(kGraph, directedKGraphGraph.getType());

    }

    /**
     * Test of stubSpanning method, of class GraphUtils.
     */
    @DisplayName("Test if the stub generated is correct")
    @Test
    public void testStubSpanning() {
        Graph result = GraphUtils.random(1000, 6);
        int[] stub = GraphUtils.stubSpanning(result, 10);
        for (int i = 1; i < 10; i++) {
            Assertions.assertTrue(result.getNeighbours(stub[i - 1]).contains(stub[i]));
        }
    }

    /**
     * Test of hasCycle method, of class GraphUtils.
     */
    @DisplayName("Test if the graph has a cycle")
    @Test
    public void testHasCycle() {
        Graph graphCycle = new Graph(edgesCycle, false, 0, 15, GraphType.RANDOM);
        Graph graphTree = new Graph(edgesTree, false, 0, 15, GraphType.RANDOM);
        Assertions.assertTrue(GraphUtils.hasCycle(graphCycle));
        Assertions.assertFalse(GraphUtils.hasCycle(graphTree));
    }

    /**
     * Test of isTree method, of class GraphUtils.
     */
    @DisplayName("Test if the graph is a tree")
    @Test
    public void testIsTree() {
        Graph graphCycle = new Graph(edgesCycle, false, 0, 15, GraphType.RANDOM);
        Graph graphTree = new Graph(edgesTree, false, 0, 15, GraphType.RANDOM);
        Assertions.assertTrue(GraphUtils.isTree(graphTree));
        Assertions.assertFalse(GraphUtils.isTree(graphCycle));
    }

    @DisplayName("Prueba extra de árbol")
    @Test
    public void testTreeExp() {
        int foo[] = {4, 21, 1, 23, 3, 0, 5, 2, -1, 8, 5, 10, 7, 8, 13, 10, 15, 16, 13, 18, 24, 20, 21, 18, 4};
        Graph tree = GraphUtils.buildFromParents(foo, 8, false);
        System.out.println("Raíz: " + tree.getRoot() + "; " + foo[tree.getRoot()] + ", " + GraphUtils.detectType(tree));
        Assertions.assertTrue(GraphUtils.isTree(tree));
    }

    /**
     * Test of detectType method, of class GraphUtils.
     */
    @DisplayName("Test if the graph has the property of to be a cycle, a disconnected graph or a tree.")
    @Test
    public void testDetectType() {
        Graph graphCycle = new Graph(edgesCycle, false, 0, 15, GraphType.RANDOM);
        Graph graphTree = new Graph(edgesTree, false, 0, 15, GraphType.RANDOM);
        Graph graphDisconnected = new Graph(edgesDisconnected, false, 0, 6, GraphType.RANDOM);
        String tree = GraphUtils.detectType(graphTree);
        String cycle = GraphUtils.detectType(graphCycle);
        String disconnected = GraphUtils.detectType(graphDisconnected);
        Assertions.assertNotSame(GraphUtils.CYCLE, tree);
        Assertions.assertNotSame(GraphUtils.CYCLE, disconnected);
        Assertions.assertNotSame(GraphUtils.DISCONNECTED, cycle);
        Assertions.assertNotSame(GraphUtils.DISCONNECTED, tree);
        Assertions.assertNotSame(GraphUtils.IS_TREE, cycle);
        Assertions.assertNotSame(GraphUtils.IS_TREE, disconnected);
        Assertions.assertEquals(GraphUtils.CYCLE, cycle);
        Assertions.assertEquals(GraphUtils.IS_TREE, tree);
        Assertions.assertEquals(GraphUtils.DISCONNECTED, disconnected);
    }

}
