package org.mx.unam.imate.concurrent.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author miguel
 */
public class NodeTest {

    /**
     * Test of getVal method, of class Node.
     */
    @Test
    public void testGetVal() {
        System.out.println("getVal");
        Node instance = new Node(10, null);
        int expResult = 10;
        int result = instance.getVal();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNext method, of class Node.
     */
    @Test
    public void testGetNext() {
        System.out.println("getNext");
        Node instance = new Node(0, null);
        Node expResult = null;
        Node result = instance.getNext();
        assertEquals(expResult, result);
    }

}
