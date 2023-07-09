package phd.ws.experiments.zero;

import phd.ws.AlgorithmsType;

/**
 *
 * @author miguel
 */
public class ZeroCostParameters {

    private AlgorithmsType algorithm;
    private int iterations;
    private int k;
    private int structSize;
    private int numOperations;

    public ZeroCostParameters(AlgorithmsType algorithm, int iterations, int k, int structSize, int numOperations) {
        this.algorithm = algorithm;
        this.iterations = iterations;
        this.k = k;
        this.structSize = structSize;
        this.numOperations = numOperations;
    }

    public AlgorithmsType getAlgorithm() {
        return algorithm;
    }

    public int getIterations() {
        return iterations;
    }

    public int getK() {
        return k;
    }

    public int getStructSize() {
        return structSize;
    }

    public int getNumOperations() {
        return numOperations;
    }

    public void setAlgorithm(AlgorithmsType algorithm) {
        this.algorithm = algorithm;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setStructSize(int structSize) {
        this.structSize = structSize;
    }

    public void setNumOperations(int numOperations) {
        this.numOperations = numOperations;
    }

}
