package phd.ws.experiments.sat.concurrent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import phd.utils.WorkStealingUtils;
import phd.ws.experiments.sat.Formula;
import phd.ws.experiments.sat.Utils;
import phd.ws.gen.WSLookUp;
import phd.ws.gen.WSStruct;
import phd.ws.gen.WSType;

/**
 *
 * @author miguel
 */
public class BruteSolver {

    private static final AtomicBoolean isSatisfied = new AtomicBoolean(false);
    private static final Range BOTTOM = new Range(-1, -1);

    private static boolean isWSClassic(WSType type) {
        switch (type) {
            case CHASELEV:
            case CILK:
            case IDEMPOTENT_FIFO:
            case IDEMPOTENT_LIFO:
            case IDEMPOTENT_DEQUE:
                return true;
            default:
                return false;
        }
    }

    private static void classicEvaluateStealMeasurement(WSStruct<Range> struct, Formula formula, Counting c) {
        Range range = struct.steal();
        if (range == null) {
            return;
        }
//        range.increment();
        c.inc();
        Formula f;
        for (long i = range.getStart(); i < range.getEnd(); i++) {
            f = new Formula(formula);
            f.assignment(i);
            if (f.isSatisfied()) {
                isSatisfied.set(true);
                return;
            }
        }
    }

    private static void evaluateStealMeasurement(WSStruct<Range> struct, Formula formula, int id, Counting c) {
        Range range = struct.steal(id);
        if (range == null) {
            return;
        }
        c.inc();
//        range.increment();
        Formula f;
        for (long i = range.getStart(); i < range.getEnd(); i++) {
            f = new Formula(formula);
            f.assignment(i);
            if (f.isSatisfied()) {
                isSatisfied.set(true);
                return;
            }
        }
    }

    private static void classicEvaluate(WSStruct<Range> struct, Formula formula, boolean measurement) {
        Range range = struct.steal();
        if (range == null) {
            return;
        }
        if (measurement) {
            range.increment();
        }
        Formula f;
        for (long i = range.getStart(); i < range.getEnd(); i++) {
            f = new Formula(formula);
            f.assignment(i);

            if (f.isSatisfied()) {
                System.out.println("Satisfied " + f.isSatisfied());
                isSatisfied.set(true);
                return;
            }
        }
    }

    private static void evaluate(WSStruct<Range> struct, Formula formula, int id, boolean measurement) {
        Range range = struct.steal(id);
        if (range == null) {
            return;
        }
        if (measurement) {
            range.increment();
        }
        Formula f;
        for (long i = range.getStart(); i < range.getEnd(); i++) {
            f = new Formula(formula);
            f.assignment(i);
            if (f.isSatisfied()) {
                System.out.println("Satisfied? " + f.isSatisfied());
                isSatisfied.set(true);
                return;
            }
        }
    }

    private static Integer parallelSolverMeasurement(Formula formula, ParallelConfig config) {
        WSLookUp<Range> lookup = new WSLookUp<>();
        WSStruct<Range> struct = lookup.lookupMultMeasurements(config.getType(), config.getNodeCapacity(), config.getProcessorNum(), BOTTOM);
        int vars = formula.getVarFactory().getSize();
        int processors = config.getProcessorNum();
        double searchSpaceSize = Math.pow(2, vars) - 1;
        int step = config.getStep();
        int newTasks = (int) (searchSpaceSize / step) + 1;
        int start = 0;
        int end = start + (step * newTasks);
        for (int i = start; i < end; i += step) {
            Range r = new Range(i, i + step);
            struct.put(r, 0);
        }
        CyclicBarrier barrier = new CyclicBarrier(processors);
        Thread[] workers = new Thread[processors];
        List<Counting> counts = new ArrayList<>();
        for (int i = 0; i < processors; i++) {
            counts.add(new Counting());
        }
        workers[0] = new Thread(() -> {
            try {
                barrier.await();
                while (!isSatisfied.get() && !struct.isEmpty(0)) {
                    Range range = struct.take(0);
                    if (range == null) {
                        return;
                    }
                    counts.get(0).inc();
//                    range.increment();
                    Formula f;
                    for (long i = range.getStart(); i < range.getEnd(); i++) {
                        f = new Formula(formula);
                        f.assignment(i);
                        if (f.isSatisfied()) {
                            isSatisfied.set(true);
                            return;
                        }
                    }
                }
            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(BruteSolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        for (int i = 1; i < processors; i++) {
            final int j = i;
            workers[i] = new Thread(() -> {
                try {
                    barrier.await();
                    while (!isSatisfied.get() && !struct.isEmpty(j)) {
                        evaluateStealMeasurement(struct, formula, j, counts.get(j));
                    }
                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(BruteSolver.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        for (Thread worker : workers) {
            worker.start();
        }
        try {
            for (Thread worker : workers) {
                worker.join();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(BruteSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
//        int repeatedWork = ;
        return counts.stream().mapToInt(c -> c.getCount()).sum();//struct.getSnapshot().stream().map(r -> r.getCount()).toList();
    }

    private static Integer parallelSolverClassicMeasurement(Formula formula, ParallelConfig config) {
        WSLookUp<Range> lookup = new WSLookUp<>();
        WSStruct<Range> struct = lookup.lookupClassicMeasurement(config.getType(), config.getNodeCapacity());
        int vars = formula.getVarFactory().getSize();
        int processors = config.getProcessorNum();
        double searchSpaceSize = Math.pow(2, vars) - 1;
        int step = config.getStep();
        int newTasks = (int) (searchSpaceSize / step) + 1;
        int start = 0;
        int end = start + (step * newTasks);
        for (int i = start; i < end; i += step) {
            Range r = new Range(i, i + step);
            struct.put(r);
        }
        CyclicBarrier barrier = new CyclicBarrier(processors);
        Thread[] workers = new Thread[processors];
        List<Counting> counts = new ArrayList<>();
        for (int i = 0; i < processors; i++) {
            counts.add(new Counting());
        }
        workers[0] = new Thread(() -> {
            try {
                barrier.await();
                while (!isSatisfied.get() && !struct.isEmpty()) {
                    Range range = struct.take();
                    if (range == null) {
                        return;
                    }
                    counts.get(0).inc();
//                    range.increment();
                    Formula f;
                    for (long i = range.getStart(); i < range.getEnd(); i++) {
                        f = new Formula(formula);
                        f.assignment(i);
                        if (f.isSatisfied()) {
                            isSatisfied.set(true);
                            return;
                        }
                    }
                }
            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(BruteSolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        for (int i = 1; i < processors; i++) {
            final int j = i;
            workers[i] = new Thread(() -> {
                try {
                    barrier.await();
                    while (!isSatisfied.get() && !struct.isEmpty()) {
                        classicEvaluateStealMeasurement(struct, formula, counts.get(j));
                    }
                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(BruteSolver.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        for (Thread worker : workers) {
            worker.start();
        }
        try {
            for (Thread worker : workers) {
                worker.join();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(BruteSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return counts.stream().mapToInt(c -> c.getCount()).sum();//struct.getSnapshot().stream().map(r -> r.getCount()).toList();
    }

    private static boolean parallelSolverClassic(Formula formula, ParallelConfig config) {
        WSLookUp<Range> lookup = new WSLookUp<>();
        WSStruct<Range> struct = lookup.lookupClassic(config.getType(), config.getNodeCapacity());
        int processors = config.getProcessorNum();
        int vars = formula.getVarFactory().getSize();
        double searchSpaceSize = Math.pow(2, vars) - 1;
        int step = config.getStep();
        int newTasks = (int) (searchSpaceSize / step) + 1;
        int start = 0;
        int end = start + (step * newTasks);
        final boolean measurement = config.getMeasurement();
        for (int i = start; i < end; i += step) {
            Range r = new Range(i, i + step);
            struct.put(r);
        }
        CyclicBarrier barrier = new CyclicBarrier(config.getProcessorNum());
        Thread[] workers = new Thread[processors];
        for (int i = 0; i < processors; i++) {
            workers[i] = new Thread(() -> {
                try {
                    barrier.await();
                    while (!isSatisfied.get() && !struct.isEmpty()) {
                        classicEvaluate(struct, formula, measurement);
                    }
                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(BruteSolver.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        for (Thread worker : workers) {
            worker.start();
        }
        try {
            for (Thread worker : workers) {
                worker.join();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(BruteSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isSatisfied.get();
    }

    private static boolean parallelSolver(Formula formula, ParallelConfig config) {
        WSLookUp<Range> lookup = new WSLookUp<>();
        WSStruct<Range> struct = lookup.lookupMult(config.getType(), config.getNodeCapacity(), config.getProcessorNum(), BOTTOM);
        int processors = config.getProcessorNum();
        final AtomicInteger generatedTasks = new AtomicInteger(0);
        int vars = formula.getVarFactory().getSize();
        double searchSpaceSize = Math.pow(2, vars) - 1;
        int step = config.getStep();
        int newTasks = (int) (searchSpaceSize / step) + 1;
        int start = 0;
        int end = start + (step * newTasks);
        final boolean measurement = config.getMeasurement();
        for (int i = start; i < end; i += step) {
            Range r = new Range(i, i + step);
            struct.put(r, 0);
        }
        CyclicBarrier barrier = new CyclicBarrier(config.getProcessorNum(), () -> {
        });
        Thread[] workers = new Thread[processors];
        for (int i = 0; i < processors; i++) {
            final int j = i;
            workers[i] = new Thread(() -> {
                try {
                    barrier.await();
                    while (!isSatisfied.get() && !struct.isEmpty(j)) {
                        evaluate(struct, formula, j, measurement);
                    }
                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(BruteSolver.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        for (Thread worker : workers) {
            worker.start();
        }
        try {
            for (Thread worker : workers) {
                worker.join();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(BruteSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isSatisfied.get();
    }

    public static void parallelDPLLMeasurements(Configuration config, Result result) {
        List<List<String>> formula = Utils.parseDimacs(config.getDatafile());
        int processors = config.getProcessors();
        int size = config.getStructSize();
        int step = config.getStep();
        Formula f = new Formula(formula);
        WSType type = config.getType();
        int vars = f.getVarFactory().getSize();
        double searchSpaceSize = Math.pow(2, vars) - 1;
        int newTasks = (int) (searchSpaceSize / step) + 1;
//        boolean satisfied;
        System.out.println("WS algorithm: " + type);
        System.out.println(f.toString());
        long time = System.nanoTime();
        ParallelConfig configuration = new ParallelConfig(type, processors, size);
        configuration.setStep(step);
        int repeatedWork;
//        List<Integer> measurements;
        if (isWSClassic(type)) {
            repeatedWork = parallelSolverClassicMeasurement(f, configuration);
        } else {
            repeatedWork = parallelSolverMeasurement(f, configuration);
        }
        time = System.nanoTime() - time;
        System.out.println("Finishing ");
        result.setSatisfied(false);
        result.setTime(time);
        result.setSearchSpace(newTasks);
//        result.setMeasurements(measurements);
        result.setRepeatedWork(repeatedWork);
    }

    public static void parallelDPLL(Configuration config, Result result) {
        List<List<String>> formula = Utils.parseDimacs(config.getDatafile());
        int processors = config.getProcessors();
        int size = config.getStructSize();
        int step = config.getStep();
        Formula f = new Formula(formula);
        WSType type = config.getType();
        boolean satisfied;
        System.out.println("WS algorithm: " + type);
        System.out.println(f.toString());
        long time = System.nanoTime();
        ParallelConfig configuration = new ParallelConfig(type, processors, size);
        configuration.setStep(step);
        if (isWSClassic(type)) {
            satisfied = parallelSolverClassic(f, configuration);
        } else {
            satisfied = parallelSolver(f, configuration);
        }
        time = System.nanoTime() - time;
        System.out.println("Is Satifiable? " + satisfied);
        result.setSatisfied(satisfied);
        result.setTime(time);
    }

    public static JSONObject readProperties(String configFile) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(configFile)), StandardCharsets.UTF_8.displayName());
            JSONObject obj = new JSONObject(json);
            return obj;
        } catch (IOException ex) {
            Logger.getLogger(Solver.class.getName()).log(Level.SEVERE, "Error: Can't read properties file", ex);
        }
        return null;
    }

    public static JSONObject experimentMeasurements(String configFile) {
        JSONObject json = readProperties(configFile);
        String dataFile = json.getString("datafile");
        int structSize = json.getInt("structSize");
        List<WSType> algs = json.getJSONArray("algorithms").toList().stream().map(s -> WSType.valueOf(s.toString())).collect(Collectors.toList());
        int iterations = json.getInt("iterations");
        int step = json.getInt("step");
        int processors = Runtime.getRuntime().availableProcessors();
        JSONObject results = new JSONObject();
        results.put("iterations", iterations);
        for (WSType alg : algs) {
            JSONObject o = new JSONObject();
            for (int i = 0; i < processors; i++) {
                JSONArray array = new JSONArray();
                double space = 0;
                for (int j = 0; j < iterations; j++) {
                    Result r = new Result(0, false);
                    Configuration c = new Configuration(dataFile, alg, structSize, (i + 1), step);
                    isSatisfied.set(false);
                    parallelDPLLMeasurements(c, r);
                    array.put(r.getRepeatedWork());
                    System.out.println("Algorithm: " + alg + ", time (ns): " + r.getTime() + ", iter: " + (j + 1) + ", processors in usage: " + (i + 1));
                    space = r.getSearchSpace();
                }
                o.put(Integer.toString(i + 1), array);
                o.put("space", space);
            }
            results.put(alg.toString(), o);
        }
        return results;
    }

    public static JSONObject experiment(String configFile) {
        JSONObject json = readProperties(configFile);
        String dataFile = json.getString("datafile");
        int structSize = json.getInt("structSize");
        List<WSType> algs = json.getJSONArray("algorithms").toList().stream().map(s -> WSType.valueOf(s.toString())).collect(Collectors.toList());
        int iterations = json.getInt("iterations");
        int processors = Runtime.getRuntime().availableProcessors();
        JSONObject results = new JSONObject();
        for (WSType alg : algs) {
            JSONObject o = new JSONObject();
            for (int i = 0; i < processors; i++) {
                JSONArray array = new JSONArray();
                for (int j = 0; j < iterations; j++) {
                    Result r = new Result(0, false);
                    Configuration c = new Configuration(dataFile, alg, structSize, (i + 1));
                    isSatisfied.set(false);
                    parallelDPLL(c, r);
                    array.put(r.getTime());
                    System.out.println("Algorithm: " + alg + ", time (ns): " + r.getTime() + ", iter: " + (j + 1) + ", processors in usage: " + (i + 1));
                }
                o.put(Integer.toString(i + 1), array);
            }
            results.put(alg.toString(), o);
        }
        return results;
    }

    public static void resetSatisfied() {
        isSatisfied.set(false);
    }

//    public static void meanCoVSat(String configFile) {
//        JSONObject config = readProperties(configFile);
//
//    }
    public static void main(String[] args) {
        String configFile = "config_sat.json";
        JSONObject result = experiment(configFile);
        WorkStealingUtils.saveJsonObjectToFile(result, "experiment-sat-brute-result.json");
        System.out.println("Finish Evaluation");
    }

}

class Counting {

    private int count;

    public Counting() {
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public void inc() {
        count++;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
