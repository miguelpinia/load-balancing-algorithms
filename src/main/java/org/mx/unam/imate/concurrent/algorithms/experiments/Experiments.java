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
        switch (type) {
            case WS_NC_MULT:
            case B_WS_NC_MULT:
            case NBWSMULT_FIFO:
            case B_NBWSMULT_FIFO:
            case WS_NC_MULT_LA:
            case B_WS_NC_MULT_LA:
            case NEW_B_WS_NC_MULT:
            case NEW_B_WS_NC_MULT_LA:
                return true;
            default:
                return false;
        }
    }

    public JSONArray putSteals(List<AlgorithmsType> types, JSONObject options) {
        int operations = options.getInt("operations");
        int size = options.getInt("size");
        JSONArray results = new JSONArray();
        types.forEach((type) -> {
            JSONObject result = new JSONObject();
            WorkStealingStruct alg = WorkStealingStructLookUp
                    .getWorkStealingStruct(type, size, 1);
            long putTime;
            long stealTime_;
            long time;
            System.out.println("Beginning test " + type);
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
            }
            long total = putTime + stealTime_;
            System.out.println("Ending test");
            result.put("Alg", type);
            result.put("put_time", putTime);
            result.put("steal_time", stealTime_);
            result.put("total_time", total);
            results.put(result);
        });

        return results;
    }

    public JSONArray putTakes(List<AlgorithmsType> types, JSONObject options) {
        int operations = options.getInt("operations");
        int size = options.getInt("size");
        JSONArray results = new JSONArray();
        types.forEach((type) -> {
            JSONObject result = new JSONObject();
            WorkStealingStruct alg = WorkStealingStructLookUp
                    .getWorkStealingStruct(type, size, 1);
            long putTime;
            long takestime;
            long time;
            System.out.println("Beginning test " + type);
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
            } else {
                time = System.nanoTime();
                for (int i = 0; i < operations; i++) {
                    alg.put(i);
                }
                putTime = System.nanoTime() - time;
                time = System.nanoTime();
                for (int i = 0; i < operations; i++) {
                    alg.take();
                }
                takestime = System.nanoTime() - time;
            }
            long total = putTime + takestime;
            System.out.println("Ending test");
            result.put("Alg", type);
            result.put("put_time", putTime);
            result.put("take_time", takestime);
            result.put("total_time", total);
            results.put(result);
        });
        return results;
    }

    public JSONObject putTakesSteals(List<AlgorithmsType> types, JSONObject options) {
        int numWorkers = options.getInt("workers");
        int numStealers = options.getInt("stealers");
        int operations = options.getInt("operations");
        int size = options.getInt("size");
        JSONObject output = new JSONObject();
        output.put("workers", numWorkers);
        output.put("stealers", numStealers);
        output.put("operations", operations);
        output.put("size", size);
        JSONArray results = new JSONArray();
        System.out.println("Types: " + types);
        types.forEach(type -> {
            JSONObject result = new JSONObject();
            int totalThreads = numWorkers + numStealers;
            WorkStealingStruct[] workers = new WorkStealingStruct[numWorkers];
            int thread;
            long putTime;
            long takeTime = 0;
            long stealTime = 0;
            System.out.println("begining test " + type);
            for (int i = 0; i < workers.length; i++) {
                workers[i] = WorkStealingStructLookUp
                        .getWorkStealingStruct(type, size, totalThreads);
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
            }
            long total = putTime + takeTime + stealTime;
            System.out.println("Ending test");
            result.put("Alg", type.name());
            result.put("put_time", putTime);
            result.put("take_time", takeTime);
            result.put("steal_time", stealTime);
            result.put("total_time", total);
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
