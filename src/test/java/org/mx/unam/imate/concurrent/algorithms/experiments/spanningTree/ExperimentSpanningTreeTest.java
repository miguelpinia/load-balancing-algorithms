/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 *
 * @author miguel
 */
public class ExperimentSpanningTreeTest {

    /**
     * Test of experiment method, of class ExperimentSpanningTree.
     */
    @Test
    public void testExperiment() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.CHASELEV, 1000, 8, 0, true, 10);
        System.out.println("\n\nexperimento de Chase-Lev con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentChaseLev1() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.CHASELEV, 1000, 1, 0, true, 10);

        System.out.println("\n\nexperimento de Chase-Lev con 1 hilo y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentOURS() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.OURS_V1, 1000, 8, 1000000, true, 10);

        System.out.println("\n\nexperimento de FIFOWorkStealing con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentNBWS() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.NBWSMULT_FIFO, 1000, 8, 1000000, true, 10);

        System.out.println("\n\nexperimento de Non-Blocking Work Stealing con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentNewAlgorithm() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.NEW_ALGORITHM, 1000, 8, 1000000, true, 10);
        System.out.println("\n\nexperimento de New Algorithm con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentWFWS() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.WFWSMULT_FIFO, 1000, 8, 1000000, true, 10);

        System.out.println("\n\nexperimento de Wait-Free WorkStealing con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentWSNC() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.WSNCMULT_FIFO, 1000, 8, 1000000, true, 10);

        System.out.println("\n\nexperimento de WorkStealing Non-Concurrent con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentBNBWS() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.B_NBWSMULT_FIFO, 1000, 8, 1000000, true, 10);

        System.out.println("\n\nexperimento de Bounded Non-Blocking Work Stealing con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentBWFWS() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.B_WFWSMULT_FIFO, 1000, 8, 1000000, true, 10);

        System.out.println("\n\nexperimento de Bounded Wait-Free WorkStealing con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentBWSNC() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.B_WSNCMULT_FIFO, 1000, 8, 1000000, true, 10);

        System.out.println("\n\nexperimento de Bounded WorkStealing Non-Concurrent con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentLIFO() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_LIFO, 1000, 8, 128, true, 10);
        System.out.println("\n\nexperimento de IdempotentLIFO con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentFIFO() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_FIFO, 1000, 8, 128, true, 10);
        System.out.println("\n\nexperimento de IdempotentFIFO con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }

    @Test
    public void testExperimentDeque() {
        Parameters params = new Parameters(GraphType.TORUS_2D, AlgorithmsType.IDEMPOTENT_DEQUE, 1000, 8, 128, true, 10);
        System.out.println("\n\nexperimento de IdempotentDeque con 8 hilos y 10 repeticiones");
        ExperimentSpanningTree instance = new ExperimentSpanningTree(params);
        List<Report> reports = instance.experiment();
        instance.statistics(reports);
    }
}
