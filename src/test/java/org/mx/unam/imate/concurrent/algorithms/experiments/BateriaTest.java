/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.experiments;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import phd.ws.AlgorithmsType;

/**
 *
 * @author miguel
 */
public class BateriaTest {

    private static List<AlgorithmsType> algs;

    @BeforeAll
    public static void setUpClass() {
        algs = new ArrayList<>();
        algs.add(AlgorithmsType.B_WS_NC_MULT);
        algs.add(AlgorithmsType.WS_NC_MULT);
    }

//    /**
//     * Test of compareAlgs method, of class TestBattery.
//     */
    @DisplayName("Test the algorithms running in the test battery")
    @Test
    public void testPrueba() {
//        System.out.println("Toro 2D Double Collect");
//        TestBattery instance = new TestBattery(GraphType.TORUS_2D, 1000, StepSpanningTreeType.DOUBLE_COLLECT, 5, algs, false);
//        instance.compareAlgs();
        // TODO review the generated test code and remove the default call to fail.
    }
//
//    @Test
//    public void testTorus2D60() {
//        System.out.println("Toro 2D60 Double Collect");
//        TestBattery instance = new TestBattery(GraphType.TORUS_2D_60, 1000, StepSpanningTreeType.DOUBLE_COLLECT, 5);
//        instance.compareAlgs();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testTorus3D() {
//        System.out.println("Toro 3D Double Collect");
//        TestBattery instance = new TestBattery(GraphType.TORUS_3D, 100, StepSpanningTreeType.DOUBLE_COLLECT, 5);
//        instance.compareAlgs();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testTorus3D40() {
//        System.out.println("Toro 3D40 Double Collect");
//        TestBattery instance = new TestBattery(GraphType.TORUS_3D_40, 100, StepSpanningTreeType.DOUBLE_COLLECT, 5);
//        instance.compareAlgs();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testRandom() {
//        System.out.println("Random Double Collect");
//        TestBattery instance = new TestBattery(GraphType.RANDOM, 1000000, StepSpanningTreeType.DOUBLE_COLLECT, 5);
//        instance.compareAlgs();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//    @Test
//    public void testPruebaCounter() {
//        System.out.println("Toro 2D Counter");
//        TestBattery instance = new TestBattery(GraphType.TORUS_2D, 1000, StepSpanningTreeType.COUNTER, 10);
//        instance.compareAlgs();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testTorus2D60Counter() {
//        System.out.println("Toro 2D60 Counter");
//        TestBattery instance = new TestBattery(GraphType.TORUS_2D_60, 1000, StepSpanningTreeType.COUNTER, 10);
//        instance.compareAlgs();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testTorus3DCounter() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.TORUS_3D, 100, StepSpanningTreeType.COUNTER, 10);
//        instance.compareAlgs();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testTorus3D40Counter() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.TORUS_3D_40, 100, StepSpanningTreeType.COUNTER, 10);
//        instance.compareAlgs();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//    @Test
//    public void testRandomCounter() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.RANDOM, 1000000, StepSpanningTreeType.COUNTER, 10);
//        instance.compareAlgs();
//        // TODO review the generated test code and remove the default call to fail.
//    }
}
