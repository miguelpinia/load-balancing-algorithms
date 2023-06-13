package phd.ws.experiments.sat.concurrent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import phd.utils.WorkStealingUtils;
import phd.ws.experiments.sat.Formula;
import phd.ws.experiments.sat.Literal;
import phd.ws.experiments.sat.Utils;
import phd.ws.gen.WSLookUp;
import phd.ws.gen.WSStruct;
import phd.ws.gen.WSType;

/**
 *
 * @author miguel
 */
public class Solver {

    private static final Formula BOTTOM = new Formula(new ArrayList<>());
    private static final AtomicBoolean isSatisfied = new AtomicBoolean(false);

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

    private static List<Formula> evaluate(Formula formula) {
        if (formula.isSatisfied()) {
            isSatisfied.getAndSet(true);
            return null;
        }
        if (formula.containsEmptyClause()) {
            return null;
        }
        formula.oneLiteralRuleSimplification();
        Literal option = formula.chooseLiteral();
        if (option == null) {
            return null;
        }
        List<Formula> l = new ArrayList<>();
        Formula left = new Formula(formula);
        left.setLiteralValue(option, false);
        l.add(left);
        Formula right = new Formula(formula);
        right.setLiteralValue(option, true);
        l.add(right);
        return l;
    }

    public static boolean parallelSolverClassic(Formula formula, ParallelConfig config) throws InterruptedException {
        WSLookUp<Formula> lookup = new WSLookUp<>();
        WSStruct<Formula> wsStruct = lookup.lookupClassic(config.getType(), config.getNodeCapacity());
        int processors = config.getProcessorNum();
        int generatedTasks = 0;
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        Formula tmp = formula;
        wsStruct.put(tmp);
        generatedTasks++;
        while (generatedTasks < processors) {
            Literal option = tmp.chooseLiteral();
            Formula left = new Formula(tmp);
            left.setLiteralValue(option, false);
            wsStruct.put(left);
            Formula right = new Formula(tmp);
            right.setLiteralValue(option, true);
            wsStruct.put(right);
            tmp = right;
            generatedTasks += 2;
        }
        List<Callable<List<Formula>>> jobs = new ArrayList<>();
        while (!wsStruct.isEmpty()) {
            if (isSatisfied.get()) {
                stop(executor);
                return true;
            }
            for (int i = 0; i < processors; i++) {
                jobs.add(() -> {
                    Formula f = wsStruct.steal();
                    if (f == null) {
                        return null;
                    }
                    return evaluate(f);
                });
            }
            executor.invokeAll(jobs).stream().map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new IllegalStateException(e);
                }
            }).forEach(list -> {
                if (list != null && !list.isEmpty()) {
                    list.iterator().forEachRemaining(f -> {
                        wsStruct.put(f);
                    });
                }
            });
        }
        stop(executor);
        return false;
    }

    /**
     * WS_MULT 59.330 B_WS_MULT 30.859 CILK 31.639 CHASELEV 41.220 IDEM_FIFO
     * 46.594 IDEM_LIFO 9.344 IDEM_DEQUE 25.790
     *
     * WS_MULT 02:59 IDEMPOTENT_LIFO 01:35
     *
     * @param formula
     * @param config
     * @return
     * @throws InterruptedException
     */
    public static boolean parallelSolver(Formula formula, ParallelConfig config) throws InterruptedException {
        WSLookUp<Formula> lookup = new WSLookUp<>();
        int processors = config.getProcessorNum();
        WSStruct<Formula> struct = lookup.lookupMult(config.getType(), config.getNodeCapacity(), processors, BOTTOM);
        int generatedTasks = 0;
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        Formula tmp = formula;
        struct.put(tmp, 0);
        generatedTasks++;
        while (generatedTasks < processors) {
            Literal option = tmp.chooseLiteral();
            Formula left = new Formula(tmp);
            left.setLiteralValue(option, false);
            struct.put(left, 0);
            Formula right = new Formula(tmp);
            right.setLiteralValue(option, true);
            struct.put(right, 0);
            tmp = right;
            generatedTasks += 2;
        }
        List<Callable<List<Formula>>> jobs = new ArrayList<>();
        while (!struct.isEmpty(0)) {
            if (isSatisfied.get()) {
                stop(executor);
                return true;
            }
            for (int i = 0; i < processors; i++) {
                final int j = i;
                jobs.add(() -> {
                    Formula f = struct.steal(j);
                    if (f == null) {
                        return null;
                    }
                    return evaluate(f);
                });
            }
            executor.invokeAll(jobs).stream().map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new IllegalStateException(e);
                }
            }).forEach(list -> {
                if (list != null && !list.isEmpty()) {
                    list.iterator().forEachRemaining(f -> {
                        struct.put(f, 0);
                    });
                }
            });
        }
        stop(executor);
        return false;
    }

    public static void stop(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
        } finally {
            if (!executor.isTerminated()) {
                System.out.println("Termination interrupted");
            }
            executor.shutdown();
        }
    }

    public static void parallelDPLL(Configuration configuration, Result result) throws InterruptedException {

        List<List<String>> formula = Utils.parseDimacs(configuration.getDatafile());
        int processors = configuration.getProcessors();
        int size = configuration.getStructSize();
        Formula f = new Formula(formula);

        WSType type = configuration.getType();
        boolean satisfied;
        System.out.println("WS algorithm: " + type);
        System.out.println(f.toString());
        long time = System.nanoTime();
        ParallelConfig config = new ParallelConfig(type, processors, size);
        if (isWSClassic(type)) {
            satisfied = parallelSolverClassic(f, config);
        } else {
            satisfied = parallelSolver(f, config);
        }
        time = System.nanoTime() - time;
        System.out.println("Is satisfiable? " + satisfied);
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

    public static JSONObject experiment(String configFile) throws InterruptedException {
        JSONObject json = readProperties(configFile);
        String dataFile = json.getString("datafile");
        int structSize = json.getInt("structSize");
        List<WSType> algs = json.getJSONArray("algorithms").toList().stream().map(s -> WSType.valueOf(s.toString())).collect(Collectors.toList());
        int iterations = json.getInt("iterations");
        int processors = Runtime.getRuntime().availableProcessors();
        JSONObject jsonResults = new JSONObject();
        for (WSType alg : algs) {
            JSONObject o = new JSONObject();
            for (int i = 0; i < processors; i++) {
                JSONArray a = new JSONArray();
                for (int j = 0; j < iterations; j++) {
                    Result r = new Result(0, false);
                    Configuration c = new Configuration(dataFile, alg, structSize, (i + 1));
                    isSatisfied.set(false);
                    parallelDPLL(c, r);
                    a.put(r.getTime());
                    System.out.println("Algorithm: " + alg + ", time (ns): " + r.getTime() + ", iter: " + (j + 1) + ", processors in usage: " + (i + 1));
                }
                o.put(Integer.toString(i + 1), a);
            }
            jsonResults.put(alg.toString(), o);
        }
        return jsonResults;
    }

    public static void main(String[] args) {
        try {
            String configFile = "config_sat.json";
            JSONObject result = experiment(configFile);
            WorkStealingUtils.saveJsonObjectToFile(result, "experiment-sat-result.json");
            System.out.println("Finish evaluation");
        } catch (InterruptedException ex) {
            Logger.getLogger(Solver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
