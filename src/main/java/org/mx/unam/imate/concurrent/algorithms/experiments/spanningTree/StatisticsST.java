package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.algorithms.utils.Result;
import org.mx.unam.imate.concurrent.datastructures.graph.Graph;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphUtils;

/**
 *
 * @author miguel
 */
public class StatisticsST {

    public static List<Report> experiment(Graph graph, Parameters params) {
        List<Report> reports = new ArrayList<>();
        final int[] roots = GraphUtils.stubSpanning(graph, params.getNumThreads());
        SpanningTree st = new SpanningTree();
        for (int i = 0; i < params.getNumIterExps(); i++) {
            Report report = new Report();
            report.setAlgType(params.getAlgType());
            report.setGraphType(params.getType());
            Graph tree = st.spanningTree(graph, roots, report, params);
            assert (GraphUtils.isTree(tree));
            reports.add(report);
        }
        return reports;
    }

    public static Result statistics(List<Report> reports) {
        Collections.sort(reports);

        System.out.println(String.format("%n%nGraph:\t%s%nAlgorithm:\t%s%n",
                reports.get(0).getGraphType(),
                reports.get(0).getAlgType()));
        reports.forEach((r) -> {
            System.out.println(String.format("Execution time: %d%nTakes: %d%nPuts: %d%nSteals: %d%nMax Steal Time: %d%nMin Steal Time: %d%nAvg Steal Time: %d%n",
                    r.getExecutionTime(), r.getTakes(), r.getPuts(), r.getSteals(), r.getMaxSteal(), r.getMinSteal(), r.getAvgSteal()));
        });
        long best = reports.get(0).getExecutionTime();

        // Delete worst and best
        reports.remove(0);
        reports.remove(reports.size() - 1);

        List<Long> values2Median = new ArrayList<>();
        List<Integer> takes = new ArrayList<>();
        List<Integer> puts = new ArrayList<>();
        List<Integer> steals = new ArrayList<>();

        reports.forEach((reporte) -> {
            values2Median.add(reporte.getExecutionTime());
            takes.add(reporte.getTakes());
            puts.add(reporte.getPuts());
            steals.add(reporte.getSteals());
        });

        long median = median(values2Median);
        double average = values2Median.stream().mapToDouble(a -> a).average().getAsDouble();
        double averageTakes = takes.stream().mapToDouble(a -> a).average().getAsDouble();
        double averagePuts = puts.stream().mapToDouble(a -> a).average().getAsDouble();
        double averageSteals = steals.stream().mapToDouble(a -> a).average().getAsDouble();

//        System.out.println("Gráfica:\t" + reports.get(0).getGraphType());
//        System.out.println("Algoritmo:\t" + reports.get(0).getAlgType());
        System.out.println("Best time:\t" + best + " ns");
        System.out.println("Best time:\t" + best / 1000000 + " ms");
        System.out.println("Median time:\t" + median + " ns");
        System.out.println("Median time:\t" + median / 1000000 + " ms");
        System.out.println("Time Average:\t" + average + " ns");
        System.out.println("Time Average:\t" + average / 1000000 + " ms");
        System.out.println("Takes Average:\t" + averageTakes);
        System.out.println("Puts Average:\t" + averagePuts);
        System.out.println("Steals Average:\t" + averageSteals);

        return new Result(reports.get(0).getGraphType(),
                reports.get(0).getAlgType(), median, average,
                averageTakes, averagePuts, averageSteals, best);
    }

    private static long median(List<Long> values) {
        Collections.sort(values);
        int n = values.size();
        if (n % 2 != 0) {
            return values.get(n / 2);
        }
        return (values.get((n - 1) / 2) + values.get(n / 2)) / 2;
    }

}
