package phd.ws.experiments.sat.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;
import phd.main.Constants;
import phd.utils.WSUtils;

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
        String title = String.format("experiment-brute-sat-measurements-%d-%s.json", step, time);
        WSUtils.saveJsonObjectToFile(result, title);
    }

    public void statisticalEvaluation(JSONObject properties) {
        JSONObject result = Statistics.evaluationSATST(properties);
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss");
        int structSize = properties.getInt(Constants.STRUCT_SIZE);
        String time = format.format(new Date());
        String title = String.format("experiment-brute-sat-st-%s-%d.json", time, structSize);
        WSUtils.saveJsonObjectToFile(result, title);
    }

}
