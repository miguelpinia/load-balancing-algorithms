package phd.ws.experiments.spanningTree;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import phd.ds.Graph;
import phd.ds.GraphType;
import phd.ds.GraphUtils;
import phd.main.Constants;
import phd.utils.Parameters;
import phd.utils.Result;
import phd.utils.SimpleReport;
import phd.utils.WSUtils;
import phd.ws.AlgorithmsType;
import phd.ws.experiments.spanningTree.stepSpanningTree.StepSpanningTreeType;

/**
 *
 * @author miguel
 */
public class BenchmarkSpanningTree {

    private void warmUp(JSONObject props, Graph graph, List<AlgorithmsType> types) {
        System.out.println("Performing warm-up execution.");
        types.forEach((type) -> {
            GraphType gType = GraphType.valueOf(props.getString(Constants.GRAPH_TYPE));
            int gSize = gType.equals(GraphType.RANDOM) ? 100 : 10;
            Parameters params = new Parameters(gType, type, gSize, 1, 32, false, 1,
                    StepSpanningTreeType.valueOf(props.getString(Constants.STEP_SPANNING_TYPE)),
                    props.getBoolean(Constants.DIRECTED),
                    props.getBoolean(Constants.STEAL_TIME));
            ExperimentsSpanningTree.experiment(graph, params);
        });
    }

    private JSONObject analysisCoV(Map<String, List<CoVResult>> covs) {
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

    private Map<AlgorithmsType, List<Result>> buildTypeLists(List<AlgorithmsType> types) {
        Map<AlgorithmsType, List<Result>> lists = new HashMap<>();
        types.forEach((type) -> {
            lists.put(type, new ArrayList<>());
        });
        return lists;
    }

    private JSONArray getAlgorithms(List<AlgorithmsType> types) {
        JSONArray array = new JSONArray();
        types.forEach((type) -> {
            array.put(type.name());
        });
        return array;
    }

    private Result getResult(Parameters params, Graph graph, JSONObject results) {
        return ExperimentsSpanningTree.statistics(ExperimentsSpanningTree.experiment(graph, params), results);
    }

    public JSONObject evaluationSpanningTree(JSONObject props, List<AlgorithmsType> types) {
        int vertexSize = props.getInt(Constants.VERTEX_SIZE);
        boolean directed = props.getBoolean(Constants.DIRECTED);
        int structSize = props.getInt(Constants.STRUCT_SIZE);
        GraphType graphType = GraphType.valueOf(props.getString(Constants.GRAPH_TYPE));
        Graph graph = GraphUtils.graphType(vertexSize, graphType, directed);
        // Warm up the virtual machine.
        warmUp(props, graph, types);
        Map<String, List<CoVResult>> covs = ExperimentsSpanningTree.fullExperiment(graph, props, types);
        JSONObject results = analysisCoV(covs);
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        String time = format.format(new Date());
        String title = String.format("graph/stats/stats-%s-%s-%d-%d-%s-%s.json", types.toString(), graphType.toString(), vertexSize, structSize, directed ? "directed" : "undirected", time);
        WSUtils.saveJsonObjectToFile(results, title);
        return results;
    }

    public JSONObject multiplicityMeasurement(JSONObject properties, List<AlgorithmsType> types) {
        JSONObject results = new JSONObject();
        int vertexSize = properties.getInt(Constants.VERTEX_SIZE);
        int structSize = properties.getInt(Constants.STRUCT_SIZE);
        GraphType graphType = GraphType.valueOf(properties.getString(Constants.GRAPH_TYPE));
        boolean directed = properties.getBoolean(Constants.DIRECTED);
        StepSpanningTreeType stepType = StepSpanningTreeType.valueOf(properties.getString(Constants.STEP_SPANNING_TYPE));
        int iterations = properties.getInt(Constants.ITERATIONS);
        int processors = Runtime.getRuntime().availableProcessors();
        Graph graph = GraphUtils.graphType(vertexSize, graphType, directed);
        results.put("vertexSize", vertexSize);
        results.put("structSize", structSize);
        results.put("graphType", graphType.name());
        results.put("directed", directed);
        System.out.println(String.format("Avalaible Processors: %d", processors));
        warmUp(properties, graph, types);
        System.out.println("begin evaluation");
        JSONObject execs = new JSONObject();
        types.forEach(type -> {
            JSONObject exec = new JSONObject();
            for (int i = 0; i < processors; i++) {
                System.out.println("Threads: " + (i + 1) + ", algorithm: " + type.name());

                JSONArray reportResults = new JSONArray();
                Parameters p = new Parameters(graphType, type, vertexSize,
                        (i + 1), structSize, false, iterations, stepType, directed, false,
                        false);
                List<List<SimpleReport>> reports = ExperimentsSpanningTree.experimentMeasurements(graph, p);
                for (List<SimpleReport> report : reports) {
                    JSONObject dataOps = new JSONObject();
                    JSONArray puts = new JSONArray();
                    JSONArray takes = new JSONArray();
                    JSONArray steals = new JSONArray();
                    for (SimpleReport simpleReport : report) {
                        puts.put(simpleReport.getPuts());
                        takes.put(simpleReport.getTakes());
                        steals.put(simpleReport.getSteals());
                    }
                    dataOps.put("puts", puts);
                    dataOps.put("takes", takes);
                    dataOps.put("steals", steals);
                    reportResults.put(dataOps);
                }
                exec.put(Integer.toString(i), reportResults);
            }
            execs.put(type.name(), exec);
        });
        results.put("executions", execs);
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        String time = format.format(new Date());
        String title = String.format("graph/multiplicity/st-%s-%s-%d-%d-%s-%s.json",
                types.toString(), graphType.name(),
                vertexSize, structSize, directed ? "directed" : "undirected", time);
        WSUtils.saveJsonObjectToFile(results, title);

        return results;
    }

    public JSONObject compare(JSONObject stProps, List<AlgorithmsType> types) {
        JSONObject results = new JSONObject();
        int vertexSize = stProps.getInt(Constants.VERTEX_SIZE);
        int structSize = stProps.getInt(Constants.STRUCT_SIZE);
        GraphType graphType = GraphType.valueOf(stProps.getString(Constants.GRAPH_TYPE));
        boolean directed = stProps.getBoolean(Constants.DIRECTED);
        StepSpanningTreeType stepType = StepSpanningTreeType.valueOf(stProps.getString(Constants.STEP_SPANNING_TYPE));
        boolean stealTime = stProps.getBoolean(Constants.STEAL_TIME);
        int iterations = stProps.getInt(Constants.ITERATIONS);
        int processorsNum = Runtime.getRuntime().availableProcessors();
        boolean allTime = stProps.getBoolean(Constants.ALL_TIME);
        Map<AlgorithmsType, List<Result>> lists = buildTypeLists(types);
        Graph graph = GraphUtils.graphType(vertexSize, graphType, directed);

        results.put("vertexSize", vertexSize);
        results.put("structSize", structSize);
        results.put("graphType", graphType.name());
        results.put("directed", directed);
        results.put("stepSpanningTree", stepType.name());
        results.put("stealTime", stealTime);
        results.put("iterations", iterations);
        results.put("processors", processorsNum);
        results.put("allTime", allTime);
        results.put("algorithms", getAlgorithms(types));

        warmUp(stProps, graph, types);

        System.out.println(String.format("Processors: %d", processorsNum));
        JSONObject execs = new JSONObject();
        for (int i = 0; i < processorsNum; i++) {
            System.out.println("Threads: " + (i + 1));
            JSONObject iter = new JSONObject();
            for (AlgorithmsType type : types) {
                JSONObject exec = new JSONObject();
                lists.get(type).add(getResult(new Parameters(graphType, type, vertexSize,
                        (i + 1), structSize, false, iterations, stepType, directed, stealTime,
                        allTime), graph, exec));
                iter.put(type.name(), exec);
            }
            execs.put(String.format("thread-%d", i), iter);
        }
        results.put("executions", execs);
//        System.out.println(results.toString(2));
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        String time = format.format(new Date());
        String title = String.format("st-%s-%s-%s-%b-%d-%d.json",
                types.toString(), time, graphType.name(),
                directed, structSize, vertexSize);
        WSUtils.saveJsonObjectToFile(results, title);
        WSUtils.saveJsonObjectToFile(results, "experiment-1.json");
        return results;
    }

}
