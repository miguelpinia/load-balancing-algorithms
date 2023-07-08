/*
 */
package phd.ws.experiments.sat.concurrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.json.JSONArray;
import org.json.JSONObject;
import phd.ws.gen.WSType;

/**
 *
 * @author miguel
 */
public class Statistics {

    private static final int ITERATIONS = 75;
    private static final int K = 5;

    public static CoVResult meanCoVSAT(Configuration config, int iterations, int k) {
        DescriptiveStatistics ds = new DescriptiveStatistics();
        double smallS = Double.MAX_VALUE;
        double smallX = Double.MAX_VALUE;
        double smallCoV = Double.MAX_VALUE;
        long executionTime;
        for (int i = 0; i < iterations; i++) {
            Result result = new Result(0, false);
            BruteSolver.resetSatisfied();
            BruteSolver.parallelDPLL(config, result);
            executionTime = result.getTime();
            ds.addValue(executionTime);
            if (i > k) {
                double s = ds.getStandardDeviation();
                double x = ds.getMean();
                double cov = s / x;
                System.out.println(String.format("DS: %.2f, MEAN: %.2f, CoV: %.4f, iter: %d", s, x, cov, i));
                if (cov < 0.05) {
                    return new CoVResult(config.getProcessors(), x, config.getType());
                }
                if (smallCoV > cov) {
                    smallCoV = cov;
                    smallX = x;
                    smallS = s;
                }
            }
        }
        System.out.println(String.format("(Last Iteration) DS: %.2f, MEAN: %.2f, CoV: %.4f", smallS, smallX, smallCoV));
        return new CoVResult(config.getProcessors(), smallX, config.getType());
    }

    public static Map<String, List<CoVResult>> fullExperiment(String fileName) {
        Map<String, List<CoVResult>> evaluation = new HashMap<>();
        JSONObject json = BruteSolver.readProperties(fileName);
        String dataFile = json.getString("datafile");
        int structSize = json.getInt("structSize");
        List<WSType> algs = json.getJSONArray("algorithms").toList().stream().map(s -> WSType.valueOf(s.toString())).collect(Collectors.toList());
        int step = json.getInt("step");
        int processors = Runtime.getRuntime().availableProcessors();
        algs.forEach(alg -> {
            List<CoVResult> results = new ArrayList<>();
            for (int i = 0; i < processors; i++) {
                Configuration config = new Configuration(dataFile, alg, structSize, (i + 1), step);
                CoVResult result = meanCoVSAT(config, ITERATIONS, K);
                results.add(result);
            }
            evaluation.put(alg.toString(), results);
        });
        return evaluation;
    }

    public static JSONObject evaluationSATST(String fileName) {
//        JSONObject json = BruteSolver.readProperties(fileName);
        Map<String, List<CoVResult>> covs = fullExperiment(fileName);
        JSONObject results = analysisCoV(covs);
//        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
//        String time = format.format(new Date());
//        String title = String.format("brute-solver-%d-%s-stats.json", json.getInt("structSize"), time);
//        WorkStealingUtils.saveJsonObjectToFile(results, title);
        return results;
    }

    private static JSONObject analysisCoV(Map<String, List<CoVResult>> covs) {
        JSONObject data = new JSONObject();
        covs.forEach((algName, listCovs) -> {
            JSONArray vals = new JSONArray();
            for (CoVResult cov : listCovs) {
                JSONArray array = new JSONArray();
                array.put(cov.getNumberThread());
                array.put(cov.getMean());
                vals.put(array);
            }
            data.append(algName, vals);
        });
        return data;
    }

}
