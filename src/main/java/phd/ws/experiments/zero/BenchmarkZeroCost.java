package phd.ws.experiments.zero;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import phd.main.Constants;
import phd.utils.WSUtils;
import phd.ws.AlgorithmsType;
import phd.ws.experiments.Experiments;

/**
 *
 * @author miguel
 */
public class BenchmarkZeroCost {

    private JSONObject processCoVPutSteals(Map<String, CoVResultZero> covs) {
        JSONObject data = new JSONObject();
        covs.forEach((algName, cov) -> {
            JSONObject results = new JSONObject();
            results.put("totalMean", cov.getMean());
            results.put("putsMean", cov.getMeanPuts());
            results.put("stealsMean", cov.getMeanSteals());
            data.put(algName, results);
        });
        return data;
    }

    private JSONObject processCoVPutTakes(Map<String, CoVResultZero> covs) {
        JSONObject data = new JSONObject();
        covs.forEach((algName, cov) -> {
            JSONObject results = new JSONObject();
            results.put("totalMean", cov.getMean());
            results.put("putsMean", cov.getMeanPuts());
            results.put("takesMean", cov.getMeanTakes());
            data.put(algName, results);
        });
        return data;
    }

    private void generateJSONFile(JSONObject results, String fileName,
            int structSize, int numOperations, List<AlgorithmsType> types) {
        System.out.println(results.toString(2));
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        String time = format.format(new Date());
        String title = String.format("%s-%s-%s-%d-%d.json", fileName, types.toString(), time, numOperations, structSize);
        WSUtils.saveJsonObjectToFile(results, title);
        System.out.println(String.format("Writing to file: %s", title));
    }

    public void benchmarkPutSteals(JSONObject putStealsOptions, List<AlgorithmsType> types) {
        var header = """
                        =====================================
                        = generating experiment puts-steals =
                        =====================================
                        """;
        var footer = """
                     =====================================
                     = Finish benchmark for puts-steals  =
                     =====================================
                     """;
        System.out.println(header);
        int structSize = putStealsOptions.getInt(Constants.STRUCT_SIZE);
        int numOperations = putStealsOptions.getInt(Constants.NUM_OPERATIONS);
        System.out.println(String.format("The benchmark will be performed with a size %d and a total of %d operations", structSize, numOperations));
        Map<String, CoVResultZero> covs = ZeroCost.fullExperimentPutsSteals(types, putStealsOptions);
        JSONObject results = processCoVPutSteals(covs);
        generateJSONFile(results, "zerocost/puts-steals", structSize, numOperations, types);
        System.out.println(footer);

    }

    public void benchmarkPutTakes(JSONObject putTakesOptions, List<AlgorithmsType> types) {
        var header = """
                      =====================================
                      = generating experiment puts-takes  =
                      =====================================
                      """;
        var footer = """
                     =====================================
                     = Finish benchmark for puts-takes   =
                     =====================================
                     """;
        System.out.println(header);
        int structSize = putTakesOptions.getInt(Constants.STRUCT_SIZE);
        int numOperations = putTakesOptions.getInt(Constants.NUM_OPERATIONS);
        System.out.println(String.format("The benchmark will be performed with a size %d and a total of %d operations", structSize, numOperations));
        Map<String, CoVResultZero> covs = ZeroCost.fullExperimentPutsTakes(types, putTakesOptions);
        JSONObject results = processCoVPutTakes(covs);
        generateJSONFile(results, "zerocost/puts-takes", structSize, numOperations, types);
        System.out.println(footer);
    }

    public void benchmarkPutTakesSteals(JSONObject ptsOptions, List<AlgorithmsType> types) {
        String header = """
                      ============================================
                      = generating experiment puts-takes-steals  =
                      ============================================
                      """;
        var footer = """
                     ============================================
                     = Finish benchmark for puts-takes-steals   =
                     ============================================
                     """;
        System.out.println(header);
        int structSize = ptsOptions.getInt(Constants.STRUCT_SIZE);
        int numOperations = ptsOptions.getInt(Constants.NUM_OPERATIONS);
        System.out.println(String.format("The benchmark will be performed with a size %d and a total of %d operations", structSize, numOperations));
        Experiments exp = new Experiments();
        JSONObject results = exp.putTakesSteals(types, ptsOptions);
        generateJSONFile(results, "zerocost/puts-takes-steals", structSize, numOperations, types);
    }

}
