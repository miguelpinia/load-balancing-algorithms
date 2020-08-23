package org.mx.unam.imate.concurrent.datastructures.graph;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mx.unam.imate.concurrent.datastructures.Edge;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 *
 * @author miguel
 */
public class GraphTest {

    private static Graph graph;
    private static Edge[] edges;
    private static boolean directed;

    @BeforeAll
    public static void setUpClass() {
        edges = new Edge[4];
        directed = false;
        edges[0] = new Edge(4, 0);
        edges[1] = new Edge(0, 1);
        edges[2] = new Edge(1, 2);
        edges[3] = new Edge(2, 3);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        graph = new Graph(edges, directed, 0, 5, GraphType.RANDOM);
    }

    @AfterEach
    public void tearDown() {
        graph = null;
    }

    /**
     * Test of addEdge method, of class Graph.
     */
    @DisplayName("Add directed edge")
    @Test
    public void testAddEdgeDirected() {
        graph.addEdge(new Edge(3, 4));
        List<Integer> l = new ArrayList<>();
        l.add(2);
        l.add(4);
        Assertions.assertArrayEquals(l.toArray(), graph.getNeighbours(3).toArray());
    }

    /**
     * Test of addEdge method, of class Graph.
     */
    @DisplayName("Add undirected edge")
    @Test
    public void testAddEdgeUndirected() {
        graph.addEdge(new Edge(3, 4));
        List<Integer> l0 = new ArrayList<>();
        l0.add(0);
        List<Integer> l1 = new ArrayList<>();
        l1.add(4);
        Assertions.assertEquals(l0.get(0), graph.getNeighbours(4).get(0));
        Assertions.assertEquals(l1.get(0), graph.getNeighbours(0).get(0));
    }

    /**
     * Test of addEdges method, of class Graph.
     */
    @DisplayName("Add edges")
    @Test
    public void testAddEdges() {
        boolean directed1 = true;
        graph = new Graph(new Edge[0], directed1, 0, 5, GraphType.RANDOM);
        graph.addEdges(edges);
        List<Integer> l0 = new ArrayList<>();
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        List<Integer> l4 = new ArrayList<>();
        l0.add(1);
        l1.add(2);
        l2.add(3);
        l4.add(0);

        Assertions.assertEquals(l0.get(0), graph.getNeighbours(0).get(0));
        Assertions.assertEquals(l1.get(0), graph.getNeighbours(1).get(0));
        Assertions.assertEquals(l2.get(0), graph.getNeighbours(2).get(0));
        Assertions.assertEquals(l4.get(0), graph.getNeighbours(4).get(0));
    }

    /**
     * Test of deleteEdge method, of class Graph.
     */
    @DisplayName("Delete directed edges")
    @Test
    public void testDeleteEdgeDirected() {
        boolean directed1 = true;
        graph = new Graph(edges, directed1, 0, 5, GraphType.RANDOM);
        graph.deleteEdge(new Edge(0, 1));
        Assertions.assertTrue(graph.getNeighbours(0).isEmpty());
    }

    /**
     * Test of deleteEdge method, of class Graph.
     */
    @DisplayName("Delete undirected edges")
    @Test
    public void testDeleteEdgeUndirected() {
        graph.deleteEdge(new Edge(0, 1));
        Assertions.assertEquals(1, graph.getNeighbours(0).size());
        Assertions.assertEquals(1, graph.getNeighbours(1).size());
    }

    /**
     * Test of hasEdge method, of class Graph.
     */
    @DisplayName("Has edge")
    @Test
    public void testHasEdge() {
        boolean directed1 = true;
        graph = new Graph(edges, directed1, 0, 5, GraphType.RANDOM);
        Assertions.assertTrue(graph.hasEdge(new Edge(0, 1)));
    }

    /**
     * Test of getNeighbours method, of class Graph.
     */
    @DisplayName("Has neighbours?")
    @Test
    public void testGetNeighbours() {
        Assertions.assertTrue(graph.getNeighbours(0).size() > 1);
    }

    /**
     * Test of getRoot method, of class Graph.
     */
    @DisplayName("Get root")
    @Test
    public void testGetRoot() {
        Assertions.assertEquals(0, graph.getRoot());
    }

    /**
     * Test of getSize method, of class Graph.
     */
    @DisplayName("Get the size and test if it is correct")
    @Test
    public void testGetSize() {
        Assertions.assertEquals(5, graph.getNumberVertices());
    }

    /**
     * Test of getType method, of class Graph.
     */
    @DisplayName("Get the graph type and test if it is correct")
    @Test
    public void testGetType() {
        Assertions.assertEquals(GraphType.RANDOM, graph.getType());
    }

    /**
     * Test of isDirected method, of class Graph.
     */
    @DisplayName("Test if the graph is directed")
    @Test
    public void testIsDirected() {
        Assertions.assertFalse(graph.isDirected());
    }

    /**
     * Test of getNumEdges method, of class Graph.
     */
    @DisplayName("Get the number of edges that contains the graph and test if it is correct")
    @Test
    public void testGetNumEdges() {
        Assertions.assertEquals(8, graph.getNumEdges());
    }

    /**
     * Test of getChilds method, of class Graph.
     */
    @DisplayName("Test if the childs are assigned correctly over a path 4 -> 0 -> 1 -> 2 -> 3")
    @Test
    public void testGetChilds() {
        boolean directed1 = true;
        Graph instance = new Graph(new Edge[0], directed1, 4, 5, GraphType.RANDOM);
        instance.addEdges(edges);
        List<Integer> l0 = new ArrayList<>();
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        List<Integer> l3 = new ArrayList<>();
        List<Integer> l4 = new ArrayList<>();
        l0.add(4);
        l1.add(0);
        l2.add(1);
        l3.add(2);
        List[] foo = {l0, l1, l2, l3, l4};
        for (int i = 0; i < 5; i++) {
            Assertions.assertArrayEquals(foo[i].toArray(), instance.getChilds(i).toArray());
        }
    }

}
