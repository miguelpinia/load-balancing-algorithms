package org.mx.unam.imate.concurrent.datastructures.graph;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 *
 * @author miguel
 */
public class GraphUtilsTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
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
        boolean result1 = org.mx.unam.imate.concurrent.datastructures.GraphUtils.inArray(val1, array);
        boolean result2 = org.mx.unam.imate.concurrent.datastructures.GraphUtils.inArray(val2, array);
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
    }

    /**
     * Test of stubSpanning method, of class GraphUtils.
     */
    @DisplayName("Test if the stub generated is correct")
    @Test
    public void testStubSpanning() {
    }

}
