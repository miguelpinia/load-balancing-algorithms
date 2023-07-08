/*
 */
package phd.ws.experiments.sat.concurrent;

import phd.ws.gen.WSType;

/**
 *
 * @author miguel
 */
public class CoVResult {

    private int numberThread;
    private double mean;
    private WSType type;

    public CoVResult() {
        numberThread = 0;
        mean = 0;
        type = WSType.WS_MULT;
    }

    public CoVResult(int numThread, double mean, WSType type) {
        this.numberThread = numThread;
        this.mean = mean;
        this.type = type;
    }

    public int getNumberThread() {
        return numberThread;
    }

    public void setNumberThread(int numberThread) {
        this.numberThread = numberThread;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public WSType getType() {
        return type;
    }

    public void setType(WSType type) {
        this.type = type;
    }

}
