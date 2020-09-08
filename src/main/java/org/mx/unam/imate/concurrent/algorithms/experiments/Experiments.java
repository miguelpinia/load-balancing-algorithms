package org.mx.unam.imate.concurrent.algorithms.experiments;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.WorkStealingStructLookUp;
import org.mx.unam.imate.concurrent.algorithms.utils.WorkStealingUtils;

/**
 *
 * @author miguel
 */
public class Experiments {

    public JSONArray putSteals(List<AlgorithmsType> types) {
        JSONArray results = new JSONArray();
        types.forEach((type) -> {
            JSONObject result = new JSONObject();
            WorkStealingStruct alg = WorkStealingStructLookUp.getWorkStealingStruct(type, 1000000, 1);
            long putTime;
            long stealTime_;
            long time;
            if (type == AlgorithmsType.WS_NC_MULT || type == AlgorithmsType.B_WS_NC_MULT
                    || type == AlgorithmsType.NBWSMULT_FIFO || type == AlgorithmsType.B_NBWSMULT_FIFO) {
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.put(i, 0);
                }
                putTime = System.nanoTime() - time;
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.steal(0);
                }
                stealTime_ = System.nanoTime() - time;
                long total = putTime + stealTime_;
                result.put("Alg", type);
                result.put("put_time_ns", putTime);
                result.put("take_time_ns", stealTime_);
                result.put("total_time_ns", total);
            } else {
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.put(i);
                }
                putTime = System.nanoTime() - time;
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.steal();
                }
                stealTime_ = System.nanoTime() - time;
                long total = putTime + stealTime_;
                result.put("Alg", type);
                result.put("put_time_ns", putTime);
                result.put("take_time_ns", stealTime_);
                result.put("total_time_ns", total);
            }
            results.put(result);
        });
        System.out.println(results.toString(2));
        WorkStealingUtils.saveJsonArrayToFile(results, "putsSteals.json");
        return results;
    }

    public JSONArray putTakes(List<AlgorithmsType> types) {
        JSONArray results = new JSONArray();
        types.forEach((type) -> {
            JSONObject result = new JSONObject();
            WorkStealingStruct alg = WorkStealingStructLookUp.getWorkStealingStruct(type, 1000000, 1);
            long putTime;
            long takestime;
            long time;
            if (type == AlgorithmsType.WS_NC_MULT || type == AlgorithmsType.B_WS_NC_MULT
                    || type == AlgorithmsType.NBWSMULT_FIFO || type == AlgorithmsType.B_NBWSMULT_FIFO) {
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.put(i, 0);
                }
                putTime = System.nanoTime() - time;
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.take(0);
                }
                takestime = System.nanoTime() - time;
                long total = putTime + takestime;
                result.put("Alg", type);
                result.put("put_time_ns", putTime);
                result.put("take_time_ns", takestime);
                result.put("total_time_ns", total);
                results.put(result);
            } else {
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.put(i);
                }
                putTime = System.nanoTime() - time;
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.steal();
                }
                takestime = System.nanoTime() - time;
                long total = putTime + takestime;
                result.put("Alg", type);
                result.put("put_time_ns", putTime);
                result.put("take_time_ns", takestime);
                result.put("total_time_ns", total);
                results.put(result);
            }
        });
        System.out.println(results.toString(2));
        WorkStealingUtils.saveJsonArrayToFile(results, "putsTakes.json");
        return results;
    }

}
