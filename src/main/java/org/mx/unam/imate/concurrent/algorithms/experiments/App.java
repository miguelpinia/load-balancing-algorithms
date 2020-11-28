package org.mx.unam.imate.concurrent.algorithms.experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.StatisticsST;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.stepSpanningTree.StepSpanningTreeType;
import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.algorithms.utils.Result;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;
import org.mx.unam.imate.concurrent.datastructures.graph.Graph;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphType;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphUtils;

/**
 * Indicar la gráfica, realizar el experimento de uno hasta el total de
 * procesadores. Generar la gráfica.
 *
 * @author miguel
 */
public class App {

    private static final String VERTEX_SIZE = "vertexSize";
    private static final String SPANNING_TREE_OPTIONS = "spanningTreeOptions";
    private static final String GRAPH_TYPE = "graphType";
    private static final String STEP_SPANNING_TYPE = "stepSpanningType";
    private static final String ITERATIONS = "iterations";
    private static final String DIRECTED = "directed";
    private static final String STEAL_TIME = "stealTime";
    private static final String STRUCT_SIZE = "structSize";
    private static final String PUT_STEALS = "putSteals";
    private static final String PUT_TAKES = "putTakes";
    private static final String PUTS_TAKES_STEALS = "putsTakesSteals";
    private static final String ALGORITHMS = "algorithms";

    private final JSONObject spanningTreeOptions;
    private final JSONObject putStealsOptions;
    private final JSONObject putTakesOptions;
    private final List<AlgorithmsType> types;
    private final boolean putSteals;
    private final boolean putTakes;
    private final boolean spanningTree;
    private final boolean putsTakesSteals;
    private final JSONObject ptsOptions;

    public App(JSONObject object) {
        this.spanningTree = object.has(SPANNING_TREE_OPTIONS);
        this.spanningTreeOptions = getOptionalValueJSONObj(object, SPANNING_TREE_OPTIONS);
        this.putSteals = object.has(PUT_STEALS);
        this.putStealsOptions = getOptionalValueJSONObj(object, PUT_STEALS);
        this.putTakes = object.has(PUT_TAKES);
        this.putTakesOptions = getOptionalValueJSONObj(object, PUT_TAKES);
        this.putsTakesSteals = object.has(PUTS_TAKES_STEALS);
        this.ptsOptions = getOptionalValueJSONObj(object, PUTS_TAKES_STEALS);
        this.types = processJSONArray(object.getJSONArray(ALGORITHMS));
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

    public void compareAlgs() {
        if (putSteals) {
            String header
                    = "=====================================\n"
                    + "= generating experiment puts-steals =\n"
                    + "=====================================\n";
            Experiments exp = new Experiments();
            System.out.println(header);
            JSONObject results = exp.putSteals(types, putStealsOptions);
            System.out.println(results.toString(2));
            WorkStealingUtils.saveJsonObjectToFile(results, "putsSteals.json");
        }
        if (putTakes) {
            String header
                    = "=====================================\n"
                    + "= generating experiment puts-takes  =\n"
                    + "=====================================\n";
            Experiments exp = new Experiments();
            System.out.println(header);
            JSONObject results = exp.putTakes(types, putTakesOptions);
            System.out.println(results.toString(2));
            WorkStealingUtils.saveJsonObjectToFile(results, "putsTakes.json");
        }
        if (putsTakesSteals) {
            String header
                    = "============================================\n"
                    + "= generating experiment puts-takes-steals  =\n"
                    + "============================================\n";
            Experiments exp = new Experiments();
            System.out.println(header);
            JSONObject results = exp.putTakesSteals(types, ptsOptions);
            System.out.println(results.toString(2));
            WorkStealingUtils.saveJsonObjectToFile(results, "putsTakesSteals.json");
        }
        if (spanningTree) {
            String header
                    = "=====================================\n"
                    + "=      comparing ws-algorithms      =\n"
                    + "=====================================\n";
            System.out.println(header);
            compare(spanningTreeOptions);
        }

    }

    private JSONObject compare(JSONObject stProps) {
        JSONObject results = new JSONObject();
        int vertexSize = stProps.getInt(VERTEX_SIZE);
        int structSize = stProps.getInt(STRUCT_SIZE);
        GraphType graphType = GraphType.valueOf(stProps.getString(GRAPH_TYPE));
        boolean directed = stProps.getBoolean(DIRECTED);
        StepSpanningTreeType stepType = StepSpanningTreeType.valueOf(stProps.getString(STEP_SPANNING_TYPE));
        boolean stealTime = stProps.getBoolean(STEAL_TIME);
        int iterations = stProps.getInt(ITERATIONS);
        int processorsNum = Runtime.getRuntime().availableProcessors();
        Map<AlgorithmsType, List<Result>> lists = buildLists();
        Graph graph = GraphUtils.graphType(vertexSize, graphType, directed);
        {
            System.out.println("Performing warm-up execution :D");
            types.forEach((type) -> {
                Parameters params = new Parameters(graphType, type,
                        vertexSize, 8, 128, false, 1, stepType, directed, stealTime);
                StatisticsST.experiment(graph, params);
            });
        }
        results.put("processors", processorsNum);
        results.put("algorithms", getAlgorithms(types));
        results.put("directed", directed);
        results.put("graphType", graphType.name());
        results.put("iterations", iterations);
        results.put("stealTime", stealTime);
        results.put("stepSpanningTree", stepType.name());
        results.put("vertexSize", vertexSize);
        results.put("structSize", structSize);
        System.out.println(String.format("Processors: %d", processorsNum));
        JSONObject execs = new JSONObject();
        for (int i = 0; i < processorsNum; i++) {
            System.out.println("Threads: " + (i + 1));
            JSONObject iter = new JSONObject();
            for (AlgorithmsType type : types) {
                JSONObject exec = new JSONObject();
                lists.get(type).add(getResult(new Parameters(graphType, type, vertexSize,
                        (i + 1), structSize, false, iterations, stepType, directed, stealTime), graph, exec));
                iter.put(type.name(), exec);
            }
            execs.put(String.format("thread-%d", i), iter);
        }
        results.put("executions", execs);
//        System.out.println(results.toString(2));
        WorkStealingUtils.saveJsonObjectToFile(results, "experiment-1.json");
        return results;
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
