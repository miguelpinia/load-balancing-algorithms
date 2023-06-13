/*
 */
package phd.ws.experiments.sat.concurrent;

/**
 *
 * @author miguel
 */
public class Result {

    private long time;
    private boolean satisfied;

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

}
