package org.mx.unam.imate.concurrent.algorithms.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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
    private final AtomicLong maxSteal;
    private final AtomicLong minSteal;
    private final AtomicLong avgSteal;
    private final AtomicLong avgIter;

    public Report() {
        takes = new AtomicInteger(0);
        puts = new AtomicInteger(0);
        steals = new AtomicInteger(0);
        executionTime = 0L;
        maxSteal = new AtomicLong();
        minSteal = new AtomicLong(Long.MAX_VALUE);
        avgSteal = new AtomicLong();
        avgIter = new AtomicLong();
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

    public void setProcessors(int... processors) {
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
        return processors.clone();
    }

    public AlgorithmsType getAlgType() {
        return algType;
    }

    public GraphType getGraphType() {
        return graphType;
    }

    public long getMaxSteal() {
        return maxSteal.get();
    }

    public long getMinSteal() {
        return minSteal.get();
    }

    public long getAvgSteal() {
        return avgSteal.get();
    }

    public void setMaxSteal(long time) {
        long expectedValue = maxSteal.get();
        if (expectedValue < time) {
            maxSteal.compareAndSet(expectedValue, time);
        }
    }

    public void setMinSteal(long time) {
        long expectedValue = minSteal.get();
        if (expectedValue > time) {
            minSteal.compareAndSet(expectedValue, time);
        }
    }

    public synchronized void updateAvgSteal(long time) {
        if (avgIter.get() == 0) {
            avgIter.incrementAndGet();
            avgSteal.set(time);
        } else {
            long prevAvg = avgSteal.get();
            long n = avgIter.get();
            long avg = prevAvg + (time - prevAvg) / n;
            avgSteal.compareAndSet(prevAvg, avg);
            avgIter.incrementAndGet();
        }
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.takes);
        hash = 97 * hash + Objects.hashCode(this.puts);
        hash = 97 * hash + Objects.hashCode(this.steals);
        hash = 97 * hash + (int) (this.executionTime ^ (this.executionTime >>> 32));
        hash = 97 * hash + Arrays.hashCode(this.processors);
        hash = 97 * hash + Objects.hashCode(this.graphType);
        hash = 97 * hash + Objects.hashCode(this.algType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Report other = (Report) obj;
        if (this.executionTime != other.executionTime) {
            return false;
        }
        if (!Objects.equals(this.takes, other.takes)) {
            return false;
        }
        if (!Objects.equals(this.puts, other.puts)) {
            return false;
        }
        if (!Objects.equals(this.steals, other.steals)) {
            return false;
        }
        if (!Arrays.equals(this.processors, other.processors)) {
            return false;
        }
        if (this.graphType != other.graphType) {
            return false;
        }
        return this.algType == other.algType;
    }

}
