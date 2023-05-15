package phd.ws.experiments.zero;

import java.math.BigDecimal;
import phd.ws.AlgorithmsType;

/**
 *
 * @author miguel
 */
public class CoVResultZero {

    private final int numberThread;
    private final BigDecimal mean;
    private final BigDecimal meanPuts;
    private final BigDecimal meanTakes;
    private final BigDecimal meanSteals;
    private final AlgorithmsType algType;

    public CoVResultZero(int numberThread, double mean, AlgorithmsType algType, double meanPuts, double meanTakes, double meanSteals) {
        this.numberThread = numberThread;
        this.mean = BigDecimal.valueOf(mean);
        this.algType = algType;
        this.meanPuts = BigDecimal.valueOf(meanPuts);
        this.meanSteals = BigDecimal.valueOf(meanSteals);
        this.meanTakes = BigDecimal.valueOf(meanTakes);
    }

    public CoVResultZero() {
        this(0, 0, AlgorithmsType.SIMPLE, 0, 0, 0);
    }

    public int getNumberThread() {
        return numberThread;
    }

    public BigDecimal getMean() {
        return mean;
    }

    public AlgorithmsType getAlgType() {
        return algType;
    }

    public BigDecimal getMeanPuts() {
        return meanPuts;
    }

    public BigDecimal getMeanTakes() {
        return meanTakes;
    }

    public BigDecimal getMeanSteals() {
        return meanSteals;
    }

}
