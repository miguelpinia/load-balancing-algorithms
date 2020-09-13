package org.mx.unam.imate.concurrent.algorithms.experiments;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.WorkStealingStructLookUp;

/**
 *
 * @author miguel
 */
public class Experiments {

    private final Random random = new Random(System.nanoTime());

    private boolean isOurWS(AlgorithmsType type) {
        return type == AlgorithmsType.WS_NC_MULT || type == AlgorithmsType.B_WS_NC_MULT
                || type == AlgorithmsType.NBWSMULT_FIFO || type == AlgorithmsType.B_NBWSMULT_FIFO;
    }

    public JSONArray putSteals(List<AlgorithmsType> types, int operations) {
        JSONArray results = new JSONArray();
        types.forEach((type) -> {
            JSONObject result = new JSONObject();
            WorkStealingStruct alg = WorkStealingStructLookUp.getWorkStealingStruct(type, operations, 1);
            long putTime;
            long stealTime_;
            long time;
            if (isOurWS(type)) {
                time = System.nanoTime();
                for (int i = 0; i < operations; i++) {
                    alg.put(i, 0);
                }
                putTime = System.nanoTime() - time;
                time = System.nanoTime();
                for (int i = 0; i < operations; i++) {
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
                for (int i = 0; i < operations; i++) {
                    alg.put(i);
                }
                putTime = System.nanoTime() - time;
                time = System.nanoTime();
                for (int i = 0; i < operations; i++) {
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

        return results;
    }

    public JSONArray putTakes(List<AlgorithmsType> types, int operations) {
        JSONArray results = new JSONArray();
        types.forEach((type) -> {
            JSONObject result = new JSONObject();
            WorkStealingStruct alg = WorkStealingStructLookUp.getWorkStealingStruct(type, operations, 1);
            long putTime;
            long takestime;
            long time;
            if (isOurWS(type)) {
                time = System.nanoTime();
                for (int i = 0; i < operations; i++) {
                    alg.put(i, 0);
                }
                putTime = System.nanoTime() - time;
                time = System.nanoTime();
                for (int i = 0; i < operations; i++) {
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
                for (int i = 0; i < operations; i++) {
                    alg.put(i);
                }
                putTime = System.nanoTime() - time;
                time = System.nanoTime();
                for (int i = 0; i < operations; i++) {
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
        return results;
    }

    public JSONObject putTakesSteals(List<AlgorithmsType> types, int numWorkers, int numStealers, int operations) {
        JSONObject output = new JSONObject();
        output.put("workers", numWorkers);
        output.put("stealers", numStealers);
        JSONArray results = new JSONArray();
        types.forEach(type -> {
            JSONObject result = new JSONObject();
            int totalThreads = numWorkers + numStealers;
            WorkStealingStruct[] workers = new WorkStealingStruct[numWorkers];
            int thread;
            long putTime;
            long takeTime = 0;
            long stealTime = 0;
            for (int i = 0; i < workers.length; i++) {
                workers[i] = WorkStealingStructLookUp.getWorkStealingStruct(type, operations, totalThreads);
            }
            if (isOurWS(type)) {
                // Hacemos puts
                putTime = System.nanoTime();
                for (int i = 0; i < workers.length; i++) {
                    for (int j = 0; j < 1000000; j++) {
                        workers[i].put(j, i);
                    }
                }
                putTime = System.nanoTime() - putTime;
                // Hacemos takes y steals
                while (!isEmptyWorkers(true, workers)) {
                    long aux = System.nanoTime();
                    for (int i = 0; i < workers.length; i++) {
                        workers[i].take(i);
                    }
                    aux = System.nanoTime() - aux;
                    takeTime += aux;
                    aux = System.nanoTime();
                    for (int i = 0; i < numStealers; i++) {
                        thread = pickRandomThread(numWorkers);
                        workers[thread].steal(numWorkers + i);
                    }
                    aux = System.nanoTime() - aux;
                    stealTime += aux;
                }
                result.put("algorithm", type.name());
                result.put("putTime", putTime);
                result.put("takeTime", takeTime);
                result.put("stealTime", stealTime);
                result.put("total", putTime + takeTime + stealTime);
            } else {
                putTime = System.nanoTime();
                for (WorkStealingStruct worker : workers) {
                    for (int i = 0; i < operations; i++) {
                        worker.put(i);
                    }
                }
                putTime = System.nanoTime() - putTime;
                while (!isEmptyWorkers(false, workers)) {
                    long aux = System.nanoTime();
                    for (WorkStealingStruct worker : workers) {
                        worker.take();
                    }
                    aux = System.nanoTime() - aux;
                    takeTime += aux;
                    aux = System.nanoTime();
                    for (int i = 0; i < numStealers; i++) {
                        thread = pickRandomThread(numWorkers);
                        workers[thread].steal();
                    }
                    aux = System.nanoTime() - aux;
                    stealTime += aux;
                }
                result.put("algorithm", type.name());
                result.put("putTime", putTime);
                result.put("takeTime", takeTime);
                result.put("stealTime", stealTime);
                result.put("total", putTime + takeTime + stealTime);
            }
            results.put(result);
        });
        output.put("results", results);
        return output;
    }

    int pickRandomThread(int numThreads) {
        return random.nextInt(numThreads);
    }

    private boolean isEmptyWorkers(boolean special, WorkStealingStruct... workers) {
        if (workers.length < 1) {
            return true;
        }
        if (special) {
            for (int i = 0; i < workers.length; i++) {
                if (!workers[i].isEmpty(i)) {
                    return false;
                }
            }
            return true;
        }
        return Arrays.asList(workers).stream().allMatch(worker -> worker.isEmpty());
    }

}
