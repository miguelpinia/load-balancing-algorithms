package org.mx.unam.imate.concurrent.algorithms.experiments;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.SpanningTree;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.StepSpanningTreeType;
import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.algorithms.utils.Result;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 * Indicar la gráfica, realizar el experimento de uno hasta el total de
 * procesadores. Generar la gráfica.
 *
 * @author miguel
 */
public class TestBattery {

    private final GraphType graphType;
    private final Integer vertexSize;
    private final StepSpanningTreeType stepType;
    private final Integer iterations;

    public TestBattery(GraphType graphType, int vertexSize, StepSpanningTreeType stepType, int iterations) {
        this.graphType = graphType;
        this.vertexSize = vertexSize;
        this.stepType = stepType;
        this.iterations = iterations;
    }

    public void medianCompare() {
        int processorsNum = Runtime.getRuntime().availableProcessors();
        Map<AlgorithmsType, List<Result>> lists = buildLists();
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (int i = 0; i < processorsNum; i++) {
            System.out.println("Número de HILOS: " + (i + 1) + ", " + stepType);
            lists.get(AlgorithmsType.CILK).add(getResult(new Parameters(graphType, AlgorithmsType.CILK, vertexSize, (i + 1), 128, false, iterations, stepType)));
            lists.get(AlgorithmsType.CHASELEV).add(getResult(new Parameters(graphType, AlgorithmsType.CHASELEV, vertexSize, (i + 1), 0, false, iterations, stepType)));
            lists.get(AlgorithmsType.IDEMPOTENT_DEQUE).add(getResult(new Parameters(graphType, AlgorithmsType.IDEMPOTENT_DEQUE, vertexSize, (i + 1), 128, false, iterations, stepType)));
            lists.get(AlgorithmsType.IDEMPOTENT_FIFO).add(getResult(new Parameters(graphType, AlgorithmsType.IDEMPOTENT_FIFO, vertexSize, (i + 1), 128, false, iterations, stepType)));
            lists.get(AlgorithmsType.IDEMPOTENT_LIFO).add(getResult(new Parameters(graphType, AlgorithmsType.IDEMPOTENT_LIFO, vertexSize, (i + 1), 128, false, iterations, stepType)));
            lists.get(AlgorithmsType.NBWSMULT_FIFO).add(getResult(new Parameters(graphType, AlgorithmsType.NBWSMULT_FIFO, vertexSize, (i + 1), 0, false, iterations, stepType)));
            lists.get(AlgorithmsType.B_NBWSMULT_FIFO).add(getResult(new Parameters(graphType, AlgorithmsType.B_NBWSMULT_FIFO, vertexSize, (i + 1), 0, false, iterations, stepType)));
            lists.get(AlgorithmsType.NEW_ALGORITHM).add(getResult(new Parameters(graphType, AlgorithmsType.NEW_ALGORITHM, vertexSize, (i + 1), 0, false, iterations, stepType)));
        }

        long chaseLevMedian = lists.get(AlgorithmsType.CHASELEV).get(0).getMedian();
        double chaseLevAverage = lists.get(AlgorithmsType.CHASELEV).get(0).getAverage();
        lists.entrySet().forEach((entry) -> {
            dataset.addSeries(getMedianSeries(entry.getValue(), chaseLevMedian, processorsNum, entry.getKey().toString()));
        });

        generateSpeedUpChart("SpeedUps comparisons (Medians) " + graphType + ", " + stepType, "Procesadores", "SpeedUp", "medianas", processorsNum, dataset);
    }

    private double medianNormalized(long chaseLevMedian, int processorNum, List<Result> results) {
        long median = results.get(processorNum).getMedian();
        return (double) chaseLevMedian / (double) median;
    }

    private double averageNormalized(long chaseLevAverage, int processorNum, List<Result> results) {
        double average = results.get(processorNum).getAverage();
        return chaseLevAverage / average;
    }

    private Result getResult(Parameters params) {
        SpanningTree st = new SpanningTree(params);
        return st.statistics(st.experiment());
    }

    private Map<AlgorithmsType, List<Result>> buildLists() {
        Map<AlgorithmsType, List<Result>> lists = new HashMap<>();
        lists.put(AlgorithmsType.CILK, new ArrayList<>());
        lists.put(AlgorithmsType.CHASELEV, new ArrayList<>());
        lists.put(AlgorithmsType.IDEMPOTENT_DEQUE, new ArrayList<>());
        lists.put(AlgorithmsType.IDEMPOTENT_FIFO, new ArrayList<>());
        lists.put(AlgorithmsType.IDEMPOTENT_LIFO, new ArrayList<>());
        lists.put(AlgorithmsType.NBWSMULT_FIFO, new ArrayList<>());
        lists.put(AlgorithmsType.B_NBWSMULT_FIFO, new ArrayList<>());
        lists.put(AlgorithmsType.NEW_ALGORITHM, new ArrayList<>());
//        lists.put(AlgorithmsType.B_NEW_ALGORITHM, new ArrayList<>());
        return lists;
    }

    private XYSeries getMedianSeries(List<Result> listResult, long chaseLevMedian, int processorsNum, String name) {
        XYSeries series = new XYSeries(name);
        for (int i = 0; i < processorsNum; i++) {
            series.add((i + 1), medianNormalized(chaseLevMedian, i, listResult));
        }
        return series;
    }

    private void generateSpeedUpChart(String title, String xAxisLabel, String yAxisLabel,
            String prefixName, int processorsNum, XYSeriesCollection dataset) {
        JFreeChart xylineChart = ChartFactory
                .createXYLineChart(title,
                        xAxisLabel,
                        yAxisLabel,
                        dataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);
        XYPlot plot = xylineChart.getXYPlot();
        XYLineAndShapeRenderer xyline = new XYLineAndShapeRenderer(true, true);
        XYItemLabelGenerator xy = new StandardXYItemLabelGenerator();
        xyline.setDefaultItemLabelGenerator(xy);
        xyline.setDefaultLinesVisible(true);
        BasicStroke stroke = new BasicStroke(3f);
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            xyline.setSeriesStroke(i, stroke);
        }

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setRange(0.00, processorsNum + 1);
        domain.setTickUnit(new NumberTickUnit(1));
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(0.0, 5);
        range.setTickUnit(new NumberTickUnit(0.2));

        plot.setRenderer(xyline);
        plot.setDomainCrosshairVisible(true);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        int width = 1280;
        int height = 960;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        File XYChart = new File(String.format("/tmp/%s-%s.jpeg", prefixName,
                dateFormat.format(Calendar.getInstance().getTime())));
        try {
            ChartUtils.saveChartAsJPEG(XYChart, xylineChart, width, height);
        } catch (IOException ex) {
            Logger.getLogger(TestBattery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
