package org.mx.unam.imate.concurrent.algorithms.experiments.zero;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.json.JSONObject;
import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.WorkStealingStructLookUp;
import org.mx.unam.imate.concurrent.main.Constants;

/**
 *
 * @author miguel
 */
public class ZeroCost {

    public ZeroCost() {
    }

    private static boolean isOurWS(AlgorithmsType type) {
        return switch (type) {
            case WS_NC_MULT, B_WS_NC_MULT, NBWSMULT_FIFO, B_NBWSMULT_FIFO,
                 WS_NC_MULT_LA, B_WS_NC_MULT_LA, NEW_B_WS_NC_MULT,
                 NEW_B_WS_NC_MULT_LA, WS_NC_MULT_OPT, WS_NC_MULT_LA_OPT,
                 B_WS_NC_MULT_OPT, B_WS_NC_MULT_LA_OPT ->
                true;
            default ->
                false;
        };
    }

    public static CoVResultZero meanCoVZeroPutsSteals(ZeroCostParameters params) {
        System.out.println(String.format("Alg: %s, numOperations: %d", params.getAlgorithm().toString(), params.getNumOperations()));
        DescriptiveStatistics ds = new DescriptiveStatistics(params.getK());
        DescriptiveStatistics dsPuts = new DescriptiveStatistics(params.getK());
        DescriptiveStatistics dsSteals = new DescriptiveStatistics(params.getK());
        double smallS = Double.MAX_VALUE;
        double smallX = Double.MAX_VALUE;
        double smallCoV = Double.MAX_VALUE;
        double smallPuts = Double.MAX_VALUE;
        double smallSteals = Double.MAX_VALUE;
        for (int i = 0; i < params.getIterations(); i++) {
            ExpResult result = zeroCostPutsSteals(params.getAlgorithm(), params.getStructSize(), params.getNumOperations());
            ds.addValue(result.getTotalTimes());
            dsPuts.addValue(result.getPutsTimes());
            dsSteals.addValue(result.getStealsTimes());
            if (i > params.getK()) {
                double s = ds.getStandardDeviation();
                double x = ds.getMean();
                double cov = s / x;
                double meanPuts = dsPuts.getMean();
                double meanSteals = dsSteals.getMean();
                System.out.println(String.format("DS: %.2f, MEAN: %.2f, CoV: %.4f, iter: %d, meanPuts: %.2f, meanSteals: %.2f", s, x, cov, i, meanPuts, meanSteals));
                if (cov < 0.05) {
                    return new CoVResultZero(1, x, params.getAlgorithm(), meanPuts, 0, meanSteals);
                }
                if (smallCoV > cov) {
                    smallCoV = cov;
                    smallX = x;
                    smallS = s;
                    smallPuts = meanPuts;
                    smallSteals = meanSteals;
                }
            }
        }
        System.out.println(String.format("(Last Iteration) DS: %.2f, MEAN: %.2f, CoV: %.4f, meanPuts: %.2f, meanSteals: %.2f", smallS, smallX, smallCoV, smallPuts, smallSteals));
        return new CoVResultZero(1, smallX, params.getAlgorithm(), smallPuts, 0, smallSteals);
    }

    public static CoVResultZero meanCoVZeroPutsTakes(ZeroCostParameters params) {
        System.out.println(String.format("Alg: %s, numOperations: %d", params.getAlgorithm().toString(), params.getNumOperations()));
        DescriptiveStatistics ds = new DescriptiveStatistics(params.getK());
        DescriptiveStatistics dsPuts = new DescriptiveStatistics(params.getK());
        DescriptiveStatistics dsTakes = new DescriptiveStatistics(params.getK());
        double smallS = Double.MAX_VALUE;
        double smallX = Double.MAX_VALUE;
        double smallCoV = Double.MAX_VALUE;
        double smallPuts = Double.MAX_VALUE;
        double smallTakes = Double.MAX_VALUE;
        for (int i = 0; i < params.getIterations(); i++) {
            ExpResult result = zeroCostPutsTakes(params.getAlgorithm(), params.getStructSize(), params.getNumOperations());
            ds.addValue(result.getTotalTimes());
            dsPuts.addValue(result.getPutsTimes());
            dsTakes.addValue(result.getTakesTimes());
            if (i > params.getK()) {
                double s = ds.getStandardDeviation();
                double x = ds.getMean();
                double cov = s / x;
                double meanPuts = dsPuts.getMean();
                double meanTakes = dsTakes.getMean();
                System.out.println(String.format("DS: %.2f, MEAN: %.2f, CoV: %.4f, iter: %d, meanPuts: %.2f, meanTakes: %.2f", s, x, cov, i, meanPuts, meanTakes));
                if (cov < 0.05) {
                    return new CoVResultZero(1, x, params.getAlgorithm(), meanPuts, meanTakes, 0);
                }
                if (smallCoV > cov) {
                    smallCoV = cov;
                    smallX = x;
                    smallS = s;
                    smallPuts = meanPuts;
                    smallTakes = meanTakes;
                }
            }
        }
        System.out.println(String.format("(Last Iteration) DS: %.2f, MEAN: %.2f, CoV: %.4f, meanPuts: %.2f, meanTakes: %.2f", smallS, smallX, smallCoV, smallPuts, smallTakes));
        return new CoVResultZero(1, smallX, params.getAlgorithm(), smallPuts, smallTakes, 0);
    }

    private static ZeroCostParameters buildFromJSON(JSONObject props) {
        int structSize = props.getInt(Constants.STRUCT_SIZE);
        int numOperations = props.getInt(Constants.NUM_OPERATIONS);
        int iterations = props.getInt(Constants.ITERATIONS);
        int k = props.getInt(Constants.K);
        ZeroCostParameters params = new ZeroCostParameters(AlgorithmsType.SIMPLE, iterations, k, structSize, numOperations);
        return params;
    }

    public static Map<String, CoVResultZero> fullExperimentPutsTakes(List<AlgorithmsType> algorithms, JSONObject props) {
        Map<String, CoVResultZero> evaluation = new HashMap<>();
        ZeroCostParameters params = buildFromJSON(props);
        algorithms.forEach((algorithm) -> {
            params.setAlgorithm(algorithm);
            CoVResultZero resultPT = meanCoVZeroPutsTakes(params);
            evaluation.put(algorithm.toString(), resultPT);
        });
        return evaluation;
    }

    public static Map<String, CoVResultZero> fullExperimentPutsSteals(List<AlgorithmsType> algorithms, JSONObject props) {
        Map<String, CoVResultZero> evaluation = new HashMap<>();
        ZeroCostParameters params = buildFromJSON(props);
        algorithms.forEach((algorithm) -> {
            params.setAlgorithm(algorithm);
            CoVResultZero resultPS = meanCoVZeroPutsSteals(params);
            evaluation.put(algorithm.toString(), resultPS);
        });
        return evaluation;
    }

    private static ExpResult zeroCostPutsSteals(AlgorithmsType algorithm, int structSize, int operations) {
        WorkStealingStruct alg = WorkStealingStructLookUp.getWorkStealingStruct(algorithm, structSize, 1);
        long putsTime, stealsTime, totalTime, time;

        if (isOurWS(algorithm)) {
            time = System.nanoTime();
            for (int i = 0; i < operations; i++) {
                alg.put(i, 0);
            }
            putsTime = System.nanoTime() - time;
            time = System.nanoTime();
            for (int i = 0; i < operations; i++) {
                alg.steal(0);
            }
            stealsTime = System.nanoTime() - time;
        } else {
            time = System.nanoTime();
            for (int i = 0; i < operations; i++) {
                alg.put(i);
            }
            putsTime = System.nanoTime() - time;
            time = System.nanoTime();
            for (int i = 0; i < operations; i++) {
                alg.steal();
            }
            stealsTime = System.nanoTime() - time;
        }

        totalTime = putsTime + stealsTime;
        ExpResult result = new ExpResult(putsTime, 0, stealsTime, totalTime);
        return result;
    }

    private static ExpResult zeroCostPutsTakes(AlgorithmsType algorithm, int structSize, int operations) {
        WorkStealingStruct alg = WorkStealingStructLookUp.getWorkStealingStruct(algorithm, structSize, 1);
        long putsTime, takesTime, totalTime, time;

        if (isOurWS(algorithm)) {
            time = System.nanoTime();
            for (int i = 0; i < operations; i++) {
                alg.put(i, 0);
            }
            putsTime = System.nanoTime() - time;
            time = System.nanoTime();
            for (int i = 0; i < operations; i++) {
                alg.take(0);
            }
            takesTime = System.nanoTime() - time;
        } else {
            time = System.nanoTime();
            for (int i = 0; i < operations; i++) {
                alg.put(i);
            }
            putsTime = System.nanoTime() - time;
            time = System.nanoTime();
            for (int i = 0; i < operations; i++) {
                alg.take();
            }
            takesTime = System.nanoTime() - time;
        }
        totalTime = putsTime + takesTime;
        ExpResult result = new ExpResult(putsTime, takesTime, 0, totalTime);
        return result;
    }

    private static class ExpResult {

        private final long putsTimes;
        private final long takesTimes;
        private final long stealsTimes;
        private final long totalTimes;

        public ExpResult(long putsTimes, long takesTimes, long stealsTimes, long totalTimes) {
            this.putsTimes = putsTimes;
            this.takesTimes = takesTimes;
            this.stealsTimes = stealsTimes;
            this.totalTimes = totalTimes;
        }

        public long getPutsTimes() {
            return putsTimes;
        }

        public long getTakesTimes() {
            return takesTimes;
        }

        public long getStealsTimes() {
            return stealsTimes;
        }

        public long getTotalTimes() {
            return totalTimes;
        }

    }
}
