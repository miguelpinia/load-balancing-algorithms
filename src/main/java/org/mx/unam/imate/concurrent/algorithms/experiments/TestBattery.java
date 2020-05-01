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
import org.jfree.data.category.DefaultCategoryDataset;
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
    private final List<AlgorithmsType> types;

    public TestBattery(GraphType graphType, int vertexSize, StepSpanningTreeType stepType, int iterations, List<AlgorithmsType> types) {
        this.graphType = graphType;
        this.vertexSize = vertexSize;
        this.stepType = stepType;
        this.iterations = iterations;
        this.types = types;
    }

    public void compareAlgs() {
        int processorsNum = Runtime.getRuntime().availableProcessors();
        Map<AlgorithmsType, List<Result>> lists = buildLists();
        XYSeriesCollection medianDataset = new XYSeriesCollection();
        XYSeriesCollection bestDataset = new XYSeriesCollection();
        XYSeriesCollection averageDataset = new XYSeriesCollection();
        {
            System.out.println("Realizando ejecución de calentamiento :D");
            types.forEach((type) -> {
                SpanningTree st = new SpanningTree(new Parameters(graphType, type, vertexSize, 8, 128, false, 1, stepType));
                st.experiment();
            });
        }
        System.out.println("Iniciando ejecuciones");
        for (int i = 0; i < processorsNum; i++) {
            System.out.println("Número de HILOS: " + (i + 1) + ", " + stepType);
            for (AlgorithmsType type : types) {
                lists.get(type).add(getResult(new Parameters(graphType, type, vertexSize, (i + 1), 128, false, iterations, stepType)));
            }
        }

        long chaseLevMedian = lists.get(AlgorithmsType.CHASELEV).get(0).getMedian();
        long chaseLevBest = lists.get(AlgorithmsType.CHASELEV).get(0).getBest();
        double chaseLevAverage = lists.get(AlgorithmsType.CHASELEV).get(0).getAverage();
        lists.entrySet().forEach((entry) -> {
            medianDataset.addSeries(getMedianSeries(entry.getValue(), chaseLevMedian, processorsNum, entry.getKey().toString()));
            bestDataset.addSeries(getBestSeries(entry.getValue(), chaseLevBest, processorsNum, entry.getKey().toString()));
            averageDataset.addSeries(getAverageSeries(entry.getValue(), chaseLevAverage, processorsNum, entry.getKey().toString()));
        });

        generateSpeedUpChart("SpeedUps comparisons (Medians) " + graphType + ", " + stepType, "Procesadores", "SpeedUp", "Medians", processorsNum, medianDataset, 3.5);
        generateSpeedUpChart("SpeedUps comparisons (Best) " + graphType + ", " + stepType, "Procesadores", "SpeedUp", "Best", processorsNum, bestDataset, 3.5);
        generateSpeedUpChart("SpeedUps comparisons (Average) " + graphType + ", " + stepType, "Procesadores", "SpeedUp", "Average", processorsNum, averageDataset, 3.5);
    }

    private double medianNormalized(long chaseLevMedian, int processorNum, List<Result> results) {
        long median = results.get(processorNum).getMedian();
        return (double) chaseLevMedian / (double) median;
    }

    private double bestNormalized(long chaseLevBest, int processorNum, List<Result> results) {
        long best = results.get(processorNum).getBest();
        return (double) chaseLevBest / (double) best;
    }

    private double averageNormalized(double chaseLevAverage, int processorNum, List<Result> results) {
        double average = results.get(processorNum).getAverage();
        return chaseLevAverage / average;
    }

    private Result getResult(Parameters params) {
        SpanningTree st = new SpanningTree(params);
        return st.statistics(st.experiment());
    }

    private Map<AlgorithmsType, List<Result>> buildLists() {
        Map<AlgorithmsType, List<Result>> lists = new HashMap<>();
        for (AlgorithmsType type : types) {
            lists.put(type, new ArrayList<>());
        }
        return lists;
    }

    private XYSeries getMedianSeries(List<Result> listResult, long chaseLevMedian, int processorsNum, String name) {
        XYSeries series = new XYSeries(name);
        for (int i = 0; i < processorsNum; i++) {
            series.add((i + 1), medianNormalized(chaseLevMedian, i, listResult));
        }
        return series;
    }

    private XYSeries getBestSeries(List<Result> listResult, long chaseLevBest, int processorsNum, String name) {
        XYSeries series = new XYSeries(name);
        for (int i = 0; i < processorsNum; i++) {
            series.add((i + 1), bestNormalized(chaseLevBest, i, listResult));
        }
        return series;
    }

    private XYSeries getAverageSeries(List<Result> listResult, double chaseLevAverage, int processorsNum, String name) {
        XYSeries series = new XYSeries(name);
        for (int i = 0; i < processorsNum; i++) {
            series.add((i + 1), averageNormalized(chaseLevAverage, i, listResult));
        }
        return series;
    }

    private void getMedianBars(List<Result> listResult, long chaseLevMedian, int processorsNum, String name, DefaultCategoryDataset dataSet) {
        for (int i = 0; i < processorsNum; i++) {
            dataSet.addValue(medianNormalized(chaseLevMedian, i, listResult), Integer.toString(i), name);
        }
    }

    private void generateSpeedUpChart(String title, String xAxisLabel, String yAxisLabel,
            String prefixName, int processorsNum, XYSeriesCollection dataset, double rangeLimit) {
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
//        range.setRange(0.5, rangeLimit);
        range.setTickUnit(new NumberTickUnit(0.5));

        plot.setRenderer(xyline);
        plot.setDomainCrosshairVisible(true);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        int width = 1024;
        int height = 768;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        File XYChart = new File(String.format("%s-%s.jpeg", prefixName,
                dateFormat.format(Calendar.getInstance().getTime())));
        try {
            ChartUtils.saveChartAsJPEG(XYChart, xylineChart, width, height);
        } catch (IOException ex) {
            Logger.getLogger(TestBattery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
