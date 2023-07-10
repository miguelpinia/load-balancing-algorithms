package phd.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import phd.ws.AlgorithmsType;
import phd.ds.GraphType;

/**
 * This class is used to keep counters for each operation performed by the
 * work-stealing algorithms.
 *
 * Esta clase llevará contadores de las operaciones que se realizan en los
 * distintos algoritmos.
 *
 * @author miguel
 */
public class Report implements Comparable<Report> {

    /**
     * Counter: How many takes are performed by experiment.
     *
     * Contador: Lleva el conteo de takes realizados en la ejecución de la
     * prueba.
     */
    private final AtomicInteger takes;

    /**
     * Counter: How many puts are performed by experiment.
     *
     * Contador: Lleva el conteo de puts realizados en la ejecución de la
     * prueba.
     */
    private final AtomicInteger puts;

    /**
     * Counter: How many steals are performed by experiment.
     *
     * Contador: Lleva el conteo de steals realizados en la ejecución de la
     * prueba.
     */
    private final AtomicInteger steals;

    /**
     * Total time performed by experiment.
     *
     * El tiempo total en ms de la ejecución de la prueba.
     */
    private long executionTime;

    private int[] processors;

    /**
     * Graph type used in the experiment.
     */
    private GraphType graphType;

    /**
     * Work-stealing algorithm used in the experiment.
     */
    private AlgorithmsType algType;

    /**
     * Max time performed by all steals.
     */
    private final AtomicLong maxSteal;

    /**
     * Min time performed by all steals.
     */
    private final AtomicLong minSteal;

    /**
     * Average time performed by all steals.
     */
    private final AtomicLong avgSteal;

    /**
     * Average time performed by each cycle.
     */
    private final AtomicLong avgIter;

    public Report() {
        takes = new AtomicInteger(0);
        puts = new AtomicInteger(0);
        steals = new AtomicInteger(0);
        executionTime = 0L;
        maxSteal = new AtomicLong(Long.MIN_VALUE);
        minSteal = new AtomicLong(Long.MAX_VALUE);
        avgSteal = new AtomicLong(0);
        avgIter = new AtomicLong(0);
    }

    /**
     * Increment by one the number of takes performed. The increment is
     * performed atomically using incrementAndGet.
     */
    public void incrementTakes() {
        takes.incrementAndGet();
    }

    /**
     * Increment by one the number of puts performed. The increment is performed
     * atomically using incrementAndGet.
     */
    public void putsIncrement() {
        puts.incrementAndGet();
    }

    /**
     * Increment by one the number of steals performed by the experiment. The
     * increment is performed atomically using incrementAndGet.
     */
    public void stealsIncrement() {
        steals.incrementAndGet();
    }

    /**
     * Set the execution time performed by the experiment.
     *
     * @param executionTime Execution time of the experiment.
     */
    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    /**
     * Set information about the processors.
     *
     * @param processors Array with information about the processors.
     */
    public void setProcessors(int... processors) {
        this.processors = processors;
    }

    /**
     * Set the graph type used in the experiment.
     *
     * @param graphType Graph type.
     */
    public void setGraphType(GraphType graphType) {
        this.graphType = graphType;
    }

    /**
     * Set the work-stealing algorithm type used in the experiment.
     *
     * @param algType Algorithm type.
     */
    public void setAlgType(AlgorithmsType algType) {
        this.algType = algType;
    }

    /**
     * Returns the execution time.
     *
     * @return The execution time.
     */
    public long getExecutionTime() {
        return executionTime;
    }

    /**
     * Returns the number of takes recorded.
     *
     * @return The number of takes.
     */
    public int getTakes() {
        return takes.get();
    }

    /**
     * Returns the number of puts recorded.
     *
     * @return The number of puts.
     */
    public int getPuts() {
        return puts.get();
    }

    /**
     * Returns the number of steals recorded.
     *
     * @return The number of steals.
     */
    public int getSteals() {
        return steals.get();
    }

    /**
     * Returns a copy of the information about the processors.
     *
     * @return
     */
    public int[] getProcessors() {
        return processors.clone();
    }

    /**
     * Returns the work-stealing algorithm type used in the experiment reported.
     *
     * @return The work-stealing algorithm..
     */
    public AlgorithmsType getAlgType() {
        return algType;
    }

    /**
     * Returns the graph type used in the experiment.
     *
     * @return The graph type.
     */
    public GraphType getGraphType() {
        return graphType;
    }

    /**
     * Returns the maximum time recorded of all steals.
     *
     * @return The maximum time of all steals.
     */
    public long getMaxSteal() {
        return maxSteal.get();
    }

    /**
     * Returns the minimum time recorded of all steals.
     *
     * @return The minimum time of all steals.
     */
    public long getMinSteal() {
        return minSteal.get();
    }

    /**
     * Returns the average time of all steals performed in the experiment.
     *
     * @return The average time of all steals.
     */
    public long getAvgSteal() {
        return avgSteal.get();
    }

    /**
     * Set the max value of all steals. This only will happen if the vale
     * <b>time</b> is greater than the last value recorded. If the last is true,
     * then, it will try set the maximum value using a compareAndSet primitive.
     *
     * @param time Time of the tentative maximum.
     */
    public void setMaxSteal(long time) {
        long expectedValue = maxSteal.get();
        if (expectedValue < time) {
            maxSteal.compareAndSet(expectedValue, time);
        }
    }

    /**
     * Set the min value of all steals. This only will happen if the value
     * <b>time</b> is smaller than the last value recorded. If the last is true,
     * then, it will try set the minimum value using the compareAndSet
     * primitive.
     *
     * @param time Time of the tentative minimum.
     */
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
