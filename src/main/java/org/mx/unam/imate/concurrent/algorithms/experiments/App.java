package org.mx.unam.imate.concurrent.algorithms.experiments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.CoVResult;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.StatisticsST;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.stepSpanningTree.StepSpanningTreeType;
import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.algorithms.utils.Result;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import org.mx.unam.imate.concurrent.datastructures.graph.Graph;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphType;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphUtils;
import org.mx.unam.imate.concurrent.main.Constants;

/**
 * Indicar la gráfica, realizar el experimento de uno hasta el total de
 * procesadores. Generar la gráfica.
 *
 * @author miguel
 */
public class App {

    private final JSONObject spanningTreeOptions;
    private final JSONObject putStealsOptions;
    private final JSONObject putTakesOptions;
    private final List<AlgorithmsType> types;
    private final boolean putSteals;
    private final boolean putTakes;
    private final boolean spanningTree;
    private final boolean putsTakesSteals;
    private final boolean statistics;
    private final JSONObject statsOptions;
    private final JSONObject ptsOptions;

    public App(JSONObject object) {
        this.spanningTree = object.has(Constants.SPANNING_TREE_OPTIONS);
        this.spanningTreeOptions = getOptionalValueJSONObj(object, Constants.SPANNING_TREE_OPTIONS);
        this.putSteals = object.has(Constants.PUT_STEALS);
        this.putStealsOptions = getOptionalValueJSONObj(object, Constants.PUT_STEALS);
        this.putTakes = object.has(Constants.PUT_TAKES);
        this.putTakesOptions = getOptionalValueJSONObj(object, Constants.PUT_TAKES);
        this.putsTakesSteals = object.has(Constants.PUTS_TAKES_STEALS);
        this.ptsOptions = getOptionalValueJSONObj(object, Constants.PUTS_TAKES_STEALS);
        this.types = processJSONArray(object.getJSONArray(Constants.ALGORITHMS));
        this.statistics = object.has(Constants.STATISTICS);
        this.statsOptions = getOptionalValueJSONObj(object, Constants.STATISTICS);
    }

    private boolean getOptionalValueBool(JSONObject object, String key) {
        return object.has(key) ? object.getBoolean(key) : false;
    }

    private JSONObject getOptionalValueJSONObj(JSONObject object, String key) {
        return object.has(key) ? object.getJSONObject(key) : new JSONObject();
    }

    private JSONArray getAlgorithms(List<AlgorithmsType> types) {
        JSONArray array = new JSONArray();
        types.forEach((type) -> {
            array.put(type.name());
        });
        return array;
    }

    private List<AlgorithmsType> processJSONArray(JSONArray array) {
        List<AlgorithmsType> algs = new ArrayList<>();
        for (Object object : array) {
            algs.add(AlgorithmsType.valueOf(object.toString()));
        }
        return algs;
    }

    public void benchmarkPutSteals() {
        String header
                = """
                      =====================================
                      = generating experiment puts-steals =
                      =====================================
                      """;
        Experiments exp = new Experiments();
        System.out.println(header);
        JSONObject results = exp.putSteals(types, ptsOptions);
        System.out.println(results.toString(2));
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        String time = format.format(new Date());
        WorkStealingUtils.saveJsonObjectToFile(results, String.format("%s-benchmark-puts-steals.json", time));
    }

    public void benchmarkPutTakes() {
        String header
                = """
                      =====================================
                      = generating experiment puts-takes  =
                      =====================================
                      """;
        Experiments exp = new Experiments();
        System.out.println(header);
        JSONObject results = exp.putTakes(types, putTakesOptions);
        System.out.println(results.toString(2));
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        String time = format.format(new Date());
        WorkStealingUtils.saveJsonObjectToFile(results, String.format("putsTakes-%s.json", time));
    }

    public void benchmarkPutTakesSteals() {
        String header = """
                      ============================================
                      = generating experiment puts-takes-steals  =
                      ============================================
                      """;
        Experiments exp = new Experiments();
        System.out.println(header);
        JSONObject results = exp.putTakesSteals(types, ptsOptions);
        System.out.println(results.toString(2));
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        String time = format.format(new Date());
        WorkStealingUtils.saveJsonObjectToFile(results, String.format("putsTakesSteals-%s.json", time));
    }

    public void compareAlgs() {
        if (putSteals) {
            benchmarkPutSteals();
        }
        if (putTakes) {
            benchmarkPutTakes();
        }
        if (putsTakesSteals) {
            benchmarkPutTakesSteals();
        }
        if (statistics) {
            System.out.println("Beggining statistics");
            evaluationSpanningTree(statsOptions);
        }
        if (spanningTree) {
            String header = """
                      =====================================
                      =      comparing ws-algorithms      =
                      =====================================
                      """;
            System.out.println(header);
            compare(spanningTreeOptions);
        }
    }

    private void warmUp(JSONObject props, Graph graph) {
        System.out.println("Performing warm-up execution :D");
        types.forEach((type) -> {
            Parameters params = new Parameters(GraphType.valueOf(props.getString(Constants.GRAPH_TYPE)),
                    type, props.getInt(Constants.VERTEX_SIZE),
                    1, 128, false, 1,
                    StepSpanningTreeType.valueOf(props.getString(Constants.STEP_SPANNING_TYPE)),
                    props.getBoolean(Constants.DIRECTED),
                    props.getBoolean(Constants.STEAL_TIME));
            StatisticsST.experiment(graph, params);
        });
    }

    private JSONObject compare(JSONObject stProps) {
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
        Map<AlgorithmsType, List<Result>> lists = buildLists();
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

        warmUp(stProps, graph);
        
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
        WorkStealingUtils.saveJsonObjectToFile(results, title);
        WorkStealingUtils.saveJsonObjectToFile(results, "experiment-1.json");
        return results;
    }
    
    public JSONObject evaluationSpanningTree(JSONObject props) {
        int vertexSize = props.getInt(Constants.VERTEX_SIZE);
        GraphType graphType = GraphType.valueOf(props.getString(Constants.GRAPH_TYPE));
        boolean directed = props.getBoolean(Constants.DIRECTED);
        int structSize = props.getInt(Constants.STRUCT_SIZE);
        Graph graph = GraphUtils.graphType(vertexSize, graphType, directed);
        warmUp(props, graph);
        Map<String, List<CoVResult>> covs = StatisticsST.fullExperiment(graph, props, types);
        JSONObject results = processCov(covs);
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        String time = format.format(new Date());
        String title = String.format("%s-%s-%d-%s-stats.json", graphType.toString(), directed ? "directed" : "undirected", structSize, time);
        WorkStealingUtils.saveJsonObjectToFile(results, title);
        return results;
    }
    
    private static JSONObject processCov(Map<String, List<CoVResult>> covs) {
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

    private Result getResult(Parameters params, Graph graph, JSONObject results) {
        return StatisticsST.statistics(StatisticsST.experiment(graph, params), results);
    }

    private Map<AlgorithmsType, List<Result>> buildLists() {
        Map<AlgorithmsType, List<Result>> lists = new HashMap<>();
        types.forEach((type) -> {
            lists.put(type, new ArrayList<>());
        });
        return lists;
    }

}
