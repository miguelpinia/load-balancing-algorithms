package org.mx.unam.imate.concurrent.algorithms.utils;

import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphType;

/**
 *
 * @author miguel
 */
public class Result {

    private GraphType graphType;
    private AlgorithmsType algType;
    private long best;
    private long median;
    private double average;
    private double averageTakes;
    private double averagePuts;
    private double averageSteals;

    public Result() {
    }

    public Result(GraphType graphType, AlgorithmsType algType, long median, double average, double averageTakes, double averagePuts, double averageSteals, long best) {
        this.graphType = graphType;
        this.algType = algType;
        this.median = median;
        this.average = average;
        this.averageTakes = averageTakes;
        this.averagePuts = averagePuts;
        this.averageSteals = averageSteals;
        this.best = best;
    }

    public GraphType getGraphType() {
        return graphType;
    }

    public void setGraphType(GraphType graphType) {
        this.graphType = graphType;
    }

    public AlgorithmsType getAlgType() {
        return algType;
    }

    public void setAlgType(AlgorithmsType algType) {
        this.algType = algType;
    }

    public long getMedian() {
        return median;
    }

    public void setMedian(long median) {
        this.median = median;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getAverageTakes() {
        return averageTakes;
    }

    public long getBest() {
        return best;
    }

    public void setAverageTakes(double averageTakes) {
        this.averageTakes = averageTakes;
    }

    public double getAveragePuts() {
        return averagePuts;
    }

    public void setAveragePuts(double averagePuts) {
        this.averagePuts = averagePuts;
    }

    public double getAverageSteals() {
        return averageSteals;
    }

    public void setAverageSteals(double averageSteals) {
        this.averageSteals = averageSteals;
    }

    public void setBest(long best) {
        this.best = best;
    }
}
