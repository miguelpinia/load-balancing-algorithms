package org.mx.unam.imate.concurrent.datastructures.graph;

import phd.ds.Vertex;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author miguel
 */
public class VertexTest {

    private Vertex vertex;

    @BeforeEach
    public void setUp() {
        vertex = new Vertex(true, 100);
        vertex.addNeighbour(101);
        vertex.addNeighbour(102);
        vertex.addChild(98);
        vertex.addChild(99);
    }

    @AfterEach
    public void tearDown() {
        vertex = null;
    }

    /**
     * Test of isDirected method, of class Vertex.
     */
    @DisplayName("Test if the vertex is part of a directed graph")
    @Test
    public void testIsDirected() {
        boolean expResult = true;
        boolean result = vertex.isDirected();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class Vertex.
     */
    @DisplayName("Test if the returned value is correct")
    @Test
    public void testGetValue() {
        Integer expResult = 100;
        Integer result = vertex.getValue();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getNeighbours method, of class Vertex.
     */
    @DisplayName("Test if the neighbours list has 2 elements")
    @Test
    public void testGetNeighbours() {
        List<Integer> expResult = new ArrayList<>();
        expResult.add(101);
        expResult.add(102);
        List<Integer> result = vertex.getNeighbours();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getChilds method, of class Vertex.
     */
    @DisplayName("Test if the childs list has 2 elements")
    @Test
    public void testGetChilds() {
        List<Integer> expResult = new ArrayList<>();
        expResult.add(98);
        expResult.add(99);
        List<Integer> result = vertex.getChilds();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of addNeighbour method, of class Vertex.
     */
    @DisplayName("Test if the neighbour is added correctly")
    @Test
    public void testAddNeighbour() {
        Integer neighbour = 1;
        vertex.addNeighbour(neighbour);
        Assertions.assertTrue(vertex.getNeighbours().contains(neighbour));
    }

    /**
     * Test of addChild method, of class Vertex.
     */
    @DisplayName("Test if the child is added correctly")
    @Test
    public void testAddChild() {
        Integer child = 2;
        vertex.addChild(child);
        Assertions.assertTrue(vertex.getChilds().contains(child));
    }

    @DisplayName("Test if a neighbour is deleted correctly")
    @Test
    public void testDeleteNeighbour() {
        Integer neighbour = 101;
        vertex.deleteNeighbour(neighbour);
        Assertions.assertFalse(vertex.getNeighbours().contains(neighbour));
    }

    @DisplayName("Test if a child is deleted correctly")
    @Test
    public void testDeleteChild() {
        Integer child = 98;
        vertex.deleteChild(child);
        Assertions.assertFalse(vertex.getChilds().contains(child));
    }

}
