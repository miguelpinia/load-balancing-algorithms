/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.experiments;

import org.junit.jupiter.api.Test;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.StepSpanningTreeType;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 *
 * @author miguel
 */
public class BateriaTest {

    /**
     * Test of prueba method, of class Bateria.
     */
    @Test
    public void testPrueba() {
        System.out.println("prueba");
        Bateria instance = new Bateria(GraphType.TORUS_2D, 1000, StepSpanningTreeType.DOUBLE_COLLECT, 10);
        instance.prueba();
        // TODO review the generated test code and remove the default call to fail.
    }

}
