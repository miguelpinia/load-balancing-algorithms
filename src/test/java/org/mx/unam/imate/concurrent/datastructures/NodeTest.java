package org.mx.unam.imate.concurrent.datastructures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author miguel
 */
public class NodeTest {

    /**
     * Test of getVal method, of class Node.
     */
    @DisplayName("Test to get the value of node")
    @Test
    public void testGetVal() {
        Node instance = new Node(10, null);
        int expResult = 10;
        int result = instance.getVal();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getNext method, of class Node.
     */
    @DisplayName("Test to get the next value")
    @Test
    public void testGetNext() {
        Node instance = new Node(0, null);
        Node expResult = null;
        Node result = instance.getNext();
        Assertions.assertEquals(expResult, result);
    }

}
