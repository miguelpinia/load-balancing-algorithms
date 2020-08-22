package org.mx.unam.imate.concurrent.datastructures;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author miguel
 */
public class GraphUtilsTest {

    /**
     * Test of inArray method, of class SIMPLESpanningTree.
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
     * Test of torus2D method, of class GraphUtils.
     */
    @Test
    public void testTorus2D() {
        System.out.println("torus2D");
        int shape = 100;
        Graph result = GraphUtils.torus2D(shape);
        assertEquals(10000, result.getNumVertices());
    }

    /**
     * Test of torus2D60 method, of class GraphUtils.
     */
    @Test
    public void testTorus2D60() {
        System.out.println("torus2D60");
        int shape = 100;
        Graph result = GraphUtils.torus2D60(shape);
        Assertions.assertTrue(() -> {
            return result.getNumConnectedVertices() <= 10000;
        });
    }

    /**
     * Test of torus3D method, of class GraphUtils.
     */
    @Test
    public void testTorus3D() {
        System.out.println("torus3D");
        int shape = 100;
        Graph result = GraphUtils.torus3D(shape);
        assertEquals(1000000, result.getNumVertices());
    }

    /**
     * Test of torus3D40 method, of class GraphUtils.
     */
    @Test
    public void testTorus3D40() {
        System.out.println("torus3D40");
        int shape = 100;
        Graph result = GraphUtils.torus3D40(shape);
        Assertions.assertTrue(() -> {
            return result.getNumConnectedVertices() <= 1000000;
        });
    }

}
