package org.mx.unam.imate.concurrent.algorithms.utils;

import java.util.concurrent.atomic.AtomicInteger;

import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphType;

/**
 * Esta clase llevará contadores de las operaciones que se realizan en los
 * distintos algoritmos.
 *
 * @author miguel
 */
public class Report implements Comparable<Report> {

    /**
     * Lleva el conteo de takes realizados en la ejecución de la prueba.
     */
    private final AtomicInteger takes;
    /**
     * Lleva el conteo de puts realizados en la ejecución de la prueba.
     */
    private final AtomicInteger puts;
    /**
     * Lleva el conteo de steals realizados en la ejecución de la prueba.
     */
    private final AtomicInteger steals;
    /**
     * El tiempo total en ms de la ejecución de la prueba.
     */
    private long executionTime;

    private int[] processors;

    private GraphType graphType;
    private AlgorithmsType algType;

    public Report() {
        takes = new AtomicInteger(0);
        puts = new AtomicInteger(0);
        steals = new AtomicInteger(0);
        executionTime = 0L;
    }

    public void takesIncrement() {
        takes.incrementAndGet();
    }

    public void putsIncrement() {
        puts.incrementAndGet();
    }

    public void stealsIncrement() {
        steals.incrementAndGet();
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public void setProcessors(int[] processors) {
        this.processors = processors;
    }

    public void setGraphType(GraphType graphType) {
        this.graphType = graphType;
    }

    public void setAlgType(AlgorithmsType algType) {
        this.algType = algType;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public int getTakes() {
        return takes.get();
    }

    public int getPuts() {
        return puts.get();
    }

    public int getSteals() {
        return steals.get();
    }

    public int[] getProcessors() {
        return processors;
    }

    public AlgorithmsType getAlgType() {
        return algType;
    }

    public GraphType getGraphType() {
        return graphType;
    }

    @Override
    public int compareTo(Report t) {
        if (this.executionTime > t.getExecutionTime()) {
            return 1;
        } else if (this.executionTime < t.getExecutionTime()) {
            return - 1;
        }
        return 0;
    }

}
