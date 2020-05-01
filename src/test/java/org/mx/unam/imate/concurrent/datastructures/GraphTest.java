package org.mx.unam.imate.concurrent.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author miguel
 */
public class GraphTest {

    /**
     * Test of getNumVertices method, of class Graph.
     */
    @Test
    public void testGetNumVertices() {
        System.out.println("getNumVertices");
        Graph instance = new Graph();
        int expResult = 0;
        int result = instance.getNumVertices();
        assertEquals(expResult, result);
    }

    /**
     * Test of printRow method, of class Graph.
     */
    @Test
    public void testPrintRow() {
        System.out.println("printRow");
        Edge[] edges = new Edge[4];
        edges[0] = new Edge(4, 3);
        edges[1] = new Edge(3, 2);
        edges[2] = new Edge(2, 1);
        edges[3] = new Edge(1, 0);
        Node node = new Node(0, null);
        Node node1 = new Node(1, node);
        Node node2 = new Node(2, node1);
        Node node3 = new Node(3, node2);
        Node node4 = new Node(4, node3);
        int i = 2;
        Graph instance = new Graph(edges, 5, GraphType.RANDOM, false);
        instance.printRow(2);
    }

}
