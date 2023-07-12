package phd.ws.experiments.sat.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;
import phd.main.Constants;
import phd.utils.WSUtils;
import phd.ws.gen.WSType;

/**
 *
 * @author miguel
 */
public class BenchmarkSAT {

    public void multiplicityCountBenchmark(JSONObject properties) {
        int step = properties.getInt("step");
        JSONObject result = BruteSolver.experimentMeasurements(properties);
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        String time = format.format(new Date());
        int structSize = properties.getInt(Constants.STRUCT_SIZE);
        List<WSType> algs = properties.getJSONArray("algorithms").toList().stream().map(s -> WSType.valueOf(s.toString())).collect(Collectors.toList());
        String title = String.format("sat/multiplicity/measurements-%s-%d-%d-%s.json", algs.toString(), step, structSize, time);
        WSUtils.saveJsonObjectToFile(result, title);
    }

    public void statisticalEvaluation(JSONObject properties) {
        System.out.println(properties);
        int step = properties.getInt("step");
        JSONObject result = Statistics.evaluationSATST(properties);
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        int structSize = properties.getInt(Constants.STRUCT_SIZE);
        List<WSType> algs = properties.getJSONArray("algorithms").toList().stream().map(s -> WSType.valueOf(s.toString())).collect(Collectors.toList());
        String time = format.format(new Date());
        String title = String.format("sat/stats/stats-%s-%d-%d-%s.json", algs.toString(), step, structSize, time);
        WSUtils.saveJsonObjectToFile(result, title);
    }

}
