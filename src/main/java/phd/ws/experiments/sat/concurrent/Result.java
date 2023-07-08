/*
 */
package phd.ws.experiments.sat.concurrent;

import java.util.List;

/**
 *
 * @author miguel
 */
public class Result {

    private long time;
    private boolean satisfied;
    private List<Integer> measurements;
    private int repeatedWork;
    private double searchSpace;

    public Result(long time, boolean satisfied) {
        this.time = time;
        this.satisfied = satisfied;
    }

    public long getTime() {
        return time;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }

    public void setMeasurements(List<Integer> measurements) {
        this.measurements = measurements;
    }

    public List<Integer> getMeasurements() {
        return measurements;
    }

    public int getRepeatedWork() {
        return repeatedWork;
    }

    public void setRepeatedWork(int repeatedWork) {
        this.repeatedWork = repeatedWork;
    }

    public double getSearchSpace() {
        return searchSpace;
    }

    public void setSearchSpace(double searchSpace) {
        this.searchSpace = searchSpace;
    }

}
