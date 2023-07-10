package phd.ws.experiments.spanningTree;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.json.JSONObject;
import phd.ds.Graph;
import phd.ds.GraphType;
import phd.ds.GraphUtils;
import phd.main.Constants;
import phd.utils.Parameters;
import phd.utils.Report;
import phd.utils.Result;
import phd.utils.SimpleReport;
import phd.ws.AlgorithmsType;

/**
 *
 * @author miguel
 */
public class ExperimentsSpanningTree {

    private static final int ITERATIONS = 75;
    private static final int K = 5;

    public static Parameters buildParamsFromJSON(JSONObject json) {
        int vertexSize = json.getInt(Constants.VERTEX_SIZE);
        int structSize = json.getInt(Constants.STRUCT_SIZE);
        GraphType graphType = GraphType.valueOf(json.getString(Constants.GRAPH_TYPE));
        boolean directed = json.getBoolean(Constants.DIRECTED);
        Parameters params = new Parameters(graphType, vertexSize, structSize, directed);
        return params;
    }

    /**
     * Perform at most <strong>n iterations</strong> of the spanning tree
     * algorithm with graph and params provided. It must test if the coefficient
     * of variation (CoV) of last k iterations reach the steady-state, i.e., CoV
     * falls below a preset threshold, in this case, 0.02.
     *
     * @param graph
     * @param params
     * @param iterations
     * @param k
     * @return
     */
    public static CoVResult meanCoVSpanningTree(Graph graph, Parameters params, int iterations, int k) {
        final int[] roots = GraphUtils.stubSpanning(graph, params.getNumThreads());
        SpanningTree st = new SpanningTree();
        DescriptiveStatistics ds = new DescriptiveStatistics(k);
        System.out.println(String.format("Alg: %s, numThreads: %d", params.getAlgType().toString(), params.getNumThreads()));
        double smallS = Double.MAX_VALUE;
        double smallX = Double.MAX_VALUE;
        double smallCoV = Double.MAX_VALUE;
        for (int i = 0; i < iterations; i++) {
            long executionTime = st.spanningTreeSimplified(graph, roots, params);
            ds.addValue(executionTime);
            if (i > k) {
                double s = ds.getStandardDeviation();
                double x = ds.getMean();
                double cov = s / x;
                System.out.println(String.format("DS: %.2f, MEAN: %.2f, CoV: %.4f, iter: %d", s, x, cov, i));
                if (cov < 0.05) {
                    return new CoVResult(params.getNumThreads(), x, params.getAlgType());
                }
                if (smallCoV > cov) {
                    smallCoV = cov;
                    smallX = x;
                    smallS = s;
                }
            }
        }
        System.out.println(String.format("(Last Iteration) DS: %.2f, MEAN: %.2f, CoV: %.4f", smallS, smallX, smallCoV));
        return new CoVResult(params.getNumThreads(), smallX, params.getAlgType());
    }

    public static Map<String, List<CoVResult>> fullExperiment(Graph graph, JSONObject props, List<AlgorithmsType> algorithms) {
        Map<String, List<CoVResult>> evaluation = new HashMap<>();
        Parameters params = buildParamsFromJSON(props);
        int processorsNum = Runtime.getRuntime().availableProcessors();
        algorithms.forEach((algorithm) -> {
            List<CoVResult> results = new ArrayList<>();
            for (int i = 0; i < processorsNum; i++) {
                params.setAlgType(algorithm);
                params.setNumThreads((i + 1));
                CoVResult result = meanCoVSpanningTree(graph, params, ITERATIONS, K);
                results.add(result);
            }
            evaluation.put(algorithm.toString(), results);
        });
        return evaluation;
    }

    public static List<Report> experiment(Graph graph, Parameters params) {
        List<Report> reports = new ArrayList<>();
        final int[] roots = GraphUtils.stubSpanning(graph, params.getNumThreads());
        SpanningTree st = new SpanningTree();
        for (int i = 0; i < params.getNumIterExps(); i++) {
            Report report = new Report();
            report.setAlgType(params.getAlgType());
            report.setGraphType(params.getType());
            st.spanningTree(graph, roots, report, params);
            reports.add(report);
        }
        return reports;
    }

    public static List<List<SimpleReport>> experimentMeasurements(Graph graph, Parameters params) {
        List<List<SimpleReport>> result = new ArrayList<>();
        int iterations = params.getNumIterExps();
        SpanningTree st = new SpanningTree();
        final int[] roots = GraphUtils.stubSpanning(graph, params.getNumThreads());
        for (int i = 0; i < iterations; i++) {
            List<SimpleReport> reports = new ArrayList<>();
            for (int j = 0; j < params.getNumThreads(); j++) {
                reports.add(new SimpleReport());
            }
            st.measurementsSpanningTree(graph, roots, params, reports);
            result.add(reports);
        }
        return result;
    }

    public static Result statistics(List<Report> reports, JSONObject results) {
        Collections.sort(reports);

        System.out.println(String.format("%n%nGraph:\t%s%nAlgorithm:\t%s%n",
                reports.get(0).getGraphType(),
                reports.get(0).getAlgType()));
        AlgorithmsType type = reports.get(0).getAlgType();
        JSONObject jsonReports = new JSONObject();
        for (int i = 0; i < reports.size(); i++) {
            Report r = reports.get(i);
            JSONObject or = new JSONObject();
            or.put("executionTime", r.getExecutionTime());
            or.put("takes", r.getTakes());
            or.put("puts", r.getPuts());
            or.put("steals", r.getSteals());
            or.put("maxStealTime", r.getMaxSteal());
            or.put("minStealTime", r.getMinSteal());
            or.put("avgStealTime", r.getAvgSteal());
            jsonReports.put(String.format("%d", i), or);

            System.out.println(String.format("Execution time: %d%nTakes: %d%nPuts: %d%nSteals: %d%nMax Steal Time: %d%nMin Steal Time: %d%nAvg Steal Time: %d%n",
                    r.getExecutionTime(), r.getTakes(), r.getPuts(), r.getSteals(), r.getMaxSteal(), r.getMinSteal(), r.getAvgSteal()));
        }
        results.put("data", jsonReports);
        long best = reports.get(0).getExecutionTime();

        // Delete worst and best
        reports.remove(0);
        reports.remove(reports.size() - 1);

        List<Long> values2Median = new ArrayList<>();
        List<Integer> takes = new ArrayList<>();
        List<Integer> puts = new ArrayList<>();
        List<Integer> steals = new ArrayList<>();

        reports.forEach((reporte) -> {
            values2Median.add(reporte.getExecutionTime());
            takes.add(reporte.getTakes());
            puts.add(reporte.getPuts());
            steals.add(reporte.getSteals());
        });

        Function<Double, Double> rounding = a -> BigDecimal.valueOf(a).setScale(4, RoundingMode.HALF_DOWN).doubleValue();

        long median = median(values2Median);
        double average = rounding.apply(values2Median.stream().mapToDouble(a -> a).average().getAsDouble());
        double averageTakes = rounding.apply(takes.stream().mapToDouble(a -> a).average().getAsDouble());
        double averagePuts = rounding.apply(puts.stream().mapToDouble(a -> a).average().getAsDouble());
        double averageSteals = rounding.apply(steals.stream().mapToDouble(a -> a).average().getAsDouble());

        JSONObject stats = new JSONObject();
        stats.put("bestTime", best);
        stats.put("medianTime", median);
        stats.put("averageTime", average);
        stats.put("averageTakes", averageTakes);
        stats.put("putsAverage", averagePuts);
        stats.put("averageSteals", averageSteals);
        results.put("statistics", stats);
//        System.out.println("Gráfica:\t" + reports.get(0).getGraphType());
//        System.out.println("Algoritmo:\t" + reports.get(0).getAlgType());
        System.out.println(String.format("Best time (ns):\t\t%d", best));
        System.out.println(String.format("Best time (ms):\t\t%.2f", (double) best / 1000000));
        System.out.println(String.format("Median time (ns):\t%d", median));
        System.out.println(String.format("Median time (ms):\t%.2f", (double) median / 1000000));
        System.out.println(String.format("Time Average (ns):\t%.2f", average));
        System.out.println(String.format("Time Average (ms):\t%.2f", average / 1000000));
        System.out.println(String.format("Takes Average:\t\t%.2f", averageTakes));
        System.out.println(String.format("Puts Average:\t\t%.2f", averagePuts));
        System.out.println(String.format("Steals Average:\t\t%.2f", averageSteals));
        return new Result(reports.get(0).getGraphType(),
                reports.get(0).getAlgType(), median, average,
                averageTakes, averagePuts, averageSteals, best);
    }

    private static long median(List<Long> values) {
        Collections.sort(values);
        int n = values.size();
        if (n % 2 != 0) {
            return values.get(n / 2);
        }
        return (values.get((n - 1) / 2) + values.get(n / 2)) / 2;
    }

}
