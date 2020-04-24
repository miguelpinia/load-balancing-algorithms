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
     * Test of medianCompare method, of class TestBattery.
     */
    @Test
    public void testPrueba() {
        System.out.println("prueba");
        TestBattery instance = new TestBattery(GraphType.TORUS_2D, 1000, StepSpanningTreeType.DOUBLE_COLLECT, 10);
        instance.medianCompare();
        // TODO review the generated test code and remove the default call to fail.
    }

//    @Test
//    public void testTorus2D60() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.TORUS_2D_60, 1000, StepSpanningTreeType.DOUBLE_COLLECT, 10);
//        instance.medianCompare();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testTorus3D() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.TORUS_3D, 100, StepSpanningTreeType.DOUBLE_COLLECT, 10);
//        instance.medianCompare();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testTorus3D40() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.TORUS_3D_40, 100, StepSpanningTreeType.DOUBLE_COLLECT, 10);
//        instance.medianCompare();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testRandom() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.RANDOM, 1000000, StepSpanningTreeType.DOUBLE_COLLECT, 10);
//        instance.medianCompare();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    /**
//     * Test of medianCompare method, of class TestBattery.
//     */
//    @Test
//    public void testPruebaCounter() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.TORUS_2D, 1000, StepSpanningTreeType.COUNTER, 10);
//        instance.medianCompare();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testTorus2D60Counter() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.TORUS_2D_60, 1000, StepSpanningTreeType.COUNTER, 10);
//        instance.medianCompare();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testTorus3DCounter() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.TORUS_3D, 100, StepSpanningTreeType.COUNTER, 10);
//        instance.medianCompare();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testTorus3D40Counter() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.TORUS_3D_40, 100, StepSpanningTreeType.COUNTER, 10);
//        instance.medianCompare();
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    @Test
//    public void testRandomCounter() {
//        System.out.println("prueba");
//        TestBattery instance = new TestBattery(GraphType.RANDOM, 1000000, StepSpanningTreeType.COUNTER, 10);
//        instance.medianCompare();
//        // TODO review the generated test code and remove the default call to fail.
//    }
}
