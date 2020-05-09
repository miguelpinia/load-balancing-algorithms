/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.output.GeneralSpanningTree;
import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.algorithms.utils.Report;
import org.mx.unam.imate.concurrent.algorithms.utils.Result;
import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.GraphUtils;

/**
 *
 * @author miguel
 */
public class SpanningTree {

    private final Parameters params;

    public SpanningTree(Parameters parameters) {
        params = parameters;
    }

    public List<Report> experiment(Graph graph) {
        List<Report> reports = new ArrayList<>();
        final int[] roots = GraphUtils.stubSpanning(graph, params.getNumThreads());
        for (int i = 0; i < params.getNumIterExps(); i++) {
            System.out.println("Iteración " + i + ", Algoritmo: " + params.getAlgType());
            Report report = new Report();
            report.setAlgType(params.getAlgType());
            report.setGraphType(params.getType());
            Graph tree = spanningTree(graph, roots, report);
            assert (tree.isTree());
            reports.add(report);
        }
        return reports;
    }

    private Graph spanningTree(Graph graph, int[] roots, Report report) {
        Thread[] threads = new Thread[params.getNumThreads()];
        AtomicIntegerArray colors = new AtomicIntegerArray(graph.getNumVertices());
        AtomicIntegerArray parents = new AtomicIntegerArray(GraphUtils.initializeParent(graph.getNumVertices()));
        WorkStealingStruct[] structs = new WorkStealingStruct[params.getNumThreads()];
        int[] processors = new int[params.getNumThreads()];
        AtomicIntegerArray visited = new AtomicIntegerArray(graph.getNumVertices());
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < params.getNumThreads(); i++) {
            structs[i] = WorkStealingStructLookUp
                    .getWorkStealingStruct(params.getAlgType(), params.getStructSize(), params.getNumThreads());
        }
        for (int i = 0; i < params.getNumThreads(); i++) {
            AbstractStepSpanningTree step = StepSpanningTreeLookUp.getStepSpanningTree(params.getStepSpanningTreeType(),
                    graph, roots[i], colors, parents, (i + 1), params.getNumThreads(), structs[i], structs,
                    report, params.isSpecialExecution(), visited, counter);

            threads[i] = new Thread(step);
        }
        long executionTime = System.nanoTime();
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(GeneralSpanningTree.class.getName())
                        .log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        executionTime = System.nanoTime() - executionTime;
        report.setExecutionTime(executionTime);
        for (int i = 0; i < graph.getNumVertices(); i++) {
            if (colors.get(i) != 0) {
                processors[colors.get(i) - 1]++;
            }
        }
        report.setProcessors(processors);
        for (int i = 1; i < roots.length; i++) {
            parents.set(roots[i], roots[i - 1]);
        }
        Graph tree = GraphUtils.buildFromParents(parents);
        return tree;
    }

    public Result statistics(List<Report> reports) {
        Collections.sort(reports);
        System.out.println("Gráfica:\t" + reports.get(0).getGraphType());
        System.out.println("Algoritmo:\t" + reports.get(0).getAlgType());
        reports.forEach((r) -> {
            System.out.println("Tiempo de ejecución " + r.getExecutionTime());
        });
        long best = reports.get(0).getExecutionTime();
        reports = removeWorstAndBest(reports);

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

        System.out.println("Gráfica:\t" + reports.get(0).getGraphType());
        System.out.println("Algoritmo:\t" + reports.get(0).getAlgType());
        System.out.println("Mejor tiempo:\t" + best + " ns");
        System.out.println("Mejor tiempo:\t" + best / 1000000 + " ms");
        System.out.println("Mediana de tiempo:\t" + median + " ns");
        System.out.println("Mediana de tiempo:\t" + median / 1000000 + " ms");
        System.out.println("Promedio de tiempo:\t" + average + " ns");
        System.out.println("Promedio de tiempo:\t" + average / 1000000 + " ms");
        System.out.println("Promedio de takes:\t" + averageTakes);
        System.out.println("Promedio de puts:\t" + averagePuts);
        System.out.println("Promedio de steals:\t" + averageSteals);
        return new Result(reports.get(0).getGraphType(),
                reports.get(0).getAlgType(), median, average,
                averageTakes, averagePuts, averageSteals, best);
    }

    private List<Report> removeWorstAndBest(List<Report> reports) {

        reports.remove(0);
        reports.remove(reports.size() - 1);
        return reports;
    }

    private long median(List<Long> values) {
        Collections.sort(values);
        int n = values.size();
        if (n % 2 != 0) {
            return values.get(n / 2);
        }
        return (values.get((n - 1) / 2) + values.get(n / 2)) / 2;
    }

}
