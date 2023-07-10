package phd.ws.experiments.sat.concurrent;

import phd.ws.gen.WSType;

/**
 *
 * @author miguel
 */
public class ParallelConfig {

    private WSType type;
    private int processorNum;
    private int nodeCapacity;
    private int step;
    private boolean measurement;

    public ParallelConfig(WSType type, int procNum, int size) {
        this.type = type;
        processorNum = procNum;
        nodeCapacity = size;
    }

    public ParallelConfig(WSType type, int processorNum, int nodeCapacity, int step, boolean measurement) {
        this.type = type;
        this.processorNum = processorNum;
        this.nodeCapacity = nodeCapacity;
        this.step = step;
        this.measurement = measurement;
    }

    public WSType getType() {
        return type;
    }

    public void setType(WSType type) {
        this.type = type;
    }

    public int getProcessorNum() {
        return processorNum;
    }

    public void setProcessorNum(int processorNum) {
        this.processorNum = processorNum;
    }

    public int getNodeCapacity() {
        return nodeCapacity;
    }

    public void setNodeCapacity(int nodeCapacity) {
        this.nodeCapacity = nodeCapacity;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean getMeasurement() {
        return measurement;
    }

    public void setMeasurement(boolean measurement) {
        this.measurement = measurement;
    }

}
