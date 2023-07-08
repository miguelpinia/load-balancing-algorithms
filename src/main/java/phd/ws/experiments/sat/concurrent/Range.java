package phd.ws.experiments.sat.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author miguel
 */
public class Range {

    private long start;
    private long end;
    private AtomicInteger count;

    public Range(long start, long end) {
        this.start = start;
        this.end = end;
        count = new AtomicInteger(0);
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public void increment() {
        count.getAndIncrement();
    }

    public int getCount() {
        return count.get();
    }

    @Override
    public String toString() {
        return "Range{" + "start=" + start + ", end=" + end + ", count=" + count + '}';
    }

}
