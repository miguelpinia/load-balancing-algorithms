package phd.ws.experiments.spanningTree;

import phd.ws.AlgorithmsType;

/**
 *
 * @author miguel
 */
public class CoVResult {

    private final int numberThread;
    private final double mean;
    private final AlgorithmsType algType;

    public CoVResult() {
        numberThread = 0;
        mean = 0;
        algType = AlgorithmsType.SIMPLE;
    }

    public CoVResult(int numberThread, double mean, AlgorithmsType algType) {
        this.numberThread = numberThread;
        this.mean = mean;
        this.algType = algType;
    }

    public int getNumberThread() {
        return numberThread;
    }

    public double getMean() {
        return mean;
    }

    public AlgorithmsType getAlgType() {
        return algType;
    }

}
