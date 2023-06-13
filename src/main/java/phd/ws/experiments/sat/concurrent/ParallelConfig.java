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

    public ParallelConfig(WSType type, int procNum, int size) {
        this.type = type;
        processorNum = procNum;
        nodeCapacity = size;
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

}
