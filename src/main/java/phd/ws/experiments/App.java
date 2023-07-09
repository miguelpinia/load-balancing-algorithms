package phd.ws.experiments;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import phd.main.Constants;
import phd.ws.AlgorithmsType;
import phd.ws.experiments.spanningTree.BenchmarkSpanningTree;
import phd.ws.experiments.zero.BenchmarkZeroCost;

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
    private final BenchmarkZeroCost benchmarkZero;
    private final BenchmarkSpanningTree benchmarkSpanning;

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
        this.benchmarkZero = new BenchmarkZeroCost();
        this.benchmarkSpanning = new BenchmarkSpanningTree();
    }

    private JSONObject getOptionalValueJSONObj(JSONObject object, String key) {
        return object.has(key) ? object.getJSONObject(key) : new JSONObject();
    }

    private List<AlgorithmsType> processJSONArray(JSONArray array) {
        List<AlgorithmsType> algs = new ArrayList<>();
        for (Object object : array) {
            algs.add(AlgorithmsType.valueOf(object.toString()));
        }
        return algs;
    }

    public void executeExperiments() {
        if (putSteals) {
            benchmarkZero.benchmarkPutSteals(putStealsOptions, types);
        }
        if (putTakes) {
            benchmarkZero.benchmarkPutTakes(putTakesOptions, types);
        }
        if (putsTakesSteals) {
            benchmarkZero.benchmarkPutTakesSteals(ptsOptions, types);
        }
        if (statistics) {
            benchmarkSpanning.evaluationSpanningTree(statsOptions, types);
        }
        if (spanningTree) {
            var header = """
                      =====================================
                      =      comparing ws-algorithms      =
                      =====================================
                      """;
            System.out.println(header);
            benchmarkSpanning.compare(spanningTreeOptions, types);
        }
    }

}
