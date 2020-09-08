package org.mx.unam.imate.concurrent.algorithms.experiments;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
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
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.util.ShapeUtils;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.StatisticsST;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.stepSpanningTree.StepSpanningTreeType;
import org.mx.unam.imate.concurrent.algorithms.utils.Parameters;
import org.mx.unam.imate.concurrent.algorithms.utils.Result;
import org.mx.unam.imate.concurrent.datastructures.graph.Graph;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphType;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphUtils;

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
    private final boolean directed;
    private final boolean stealTime;
    private final boolean putSteals;

    public TestBattery(GraphType graphType, int vertexSize, StepSpanningTreeType stepType,
            int iterations, List<AlgorithmsType> types, boolean directed, boolean stealTime,
            boolean putSteals) {
        this.graphType = graphType;
        this.vertexSize = vertexSize;
        this.stepType = stepType;
        this.iterations = iterations;
        this.types = types;
        this.directed = directed;
        this.stealTime = stealTime;
        this.putSteals = putSteals;
    }

    public void compareAlgs() {
        if (putSteals) {
            Experiments exp = new Experiments();
            System.out.println("=====================================");
            System.out.println("= generating experiment puts-steals =");
            System.out.println("=====================================");
            exp.putSteals(types);

        } else {
            int processorsNum = Runtime.getRuntime().availableProcessors();
            Map<AlgorithmsType, List<Result>> lists = buildLists();
            XYSeriesCollection medianDataset = new XYSeriesCollection();
            XYSeriesCollection bestDataset = new XYSeriesCollection();
            XYSeriesCollection averageDataset = new XYSeriesCollection();
            Graph graph = GraphUtils.graphType(vertexSize, graphType, directed);
            {
                System.out.println("Realizando ejecución de calentamiento :D");
                types.forEach((type) -> {
                    Parameters params = new Parameters(graphType, type,
                            vertexSize, 8, 128, false, 1, stepType, directed, stealTime);
                    StatisticsST.experiment(graph, params);
                });
            }
            System.out.println(String.format("Processors: %d", processorsNum));
            for (int i = 0; i < processorsNum; i++) {
                System.out.println("Threads: " + (i + 1));
                for (AlgorithmsType type : types) {
                    lists.get(type).add(getResult(new Parameters(graphType, type, vertexSize,
                            (i + 1), 128, false, iterations, stepType, directed, stealTime), graph));
                }
            }

            long chaseLevMedian = lists.get(AlgorithmsType.CHASELEV).get(0).getMedian();
            long chaseLevBest = lists.get(AlgorithmsType.CHASELEV).get(0).getBest();
            double chaseLevAverage = lists.get(AlgorithmsType.CHASELEV).get(0).getAverage();

            types.stream().map((type) -> {
                medianDataset.addSeries(getMedianSeries(lists.get(type), chaseLevMedian,
                        processorsNum, getAlgName(type)));
                return type;
            }).map((type) -> {
                bestDataset.addSeries(getBestSeries(lists.get(type), chaseLevBest,
                        processorsNum, getAlgName(type)));
                return type;
            }).forEachOrdered((type) -> {
                averageDataset.addSeries(getAverageSeries(lists.get(type), chaseLevAverage,
                        processorsNum, getAlgName(type)));
            });
//        lists.entrySet().forEach((entry) -> {
//            medianDataset.addSeries(getMedianSeries(entry.getValue(), chaseLevMedian, processorsNum, entry.getKey().toString()));
//            bestDataset.addSeries(getBestSeries(entry.getValue(), chaseLevBest, processorsNum, entry.getKey().toString()));
//            averageDataset.addSeries(getAverageSeries(entry.getValue(), chaseLevAverage, processorsNum, entry.getKey().toString()));
//        });

            generateSpeedUpChart("SpeedUp comparison (Median) " + graphType
                    + (directed ? " directed" : " undirected"), "Processors", "SpeedUp",
                    "Medians-" + graphType + "-" + stepType + "-" + vertexSize,
                    processorsNum, medianDataset, 3.5);
            generateSpeedUpChart("SpeedUp comparison (Best) " + graphType
                    + (directed ? " directed" : " undirected"), "Processors",
                    "SpeedUp", "Best-" + graphType + "-" + stepType + "-" + vertexSize,
                    processorsNum, bestDataset, 3.5);
            generateSpeedUpChart("SpeedUp comparison (Average) " + graphType
                    + (directed ? " directed" : " undirected"), "Processors", "SpeedUp",
                    "Average-" + graphType + "-" + stepType + "-" + vertexSize,
                    processorsNum, averageDataset, 3.5);

        }

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

    private Result getResult(Parameters params, Graph graph) {
        return StatisticsST.statistics(StatisticsST.experiment(graph, params));
    }

    private Map<AlgorithmsType, List<Result>> buildLists() {
        Map<AlgorithmsType, List<Result>> lists = new HashMap<>();
        types.forEach((type) -> {
            lists.put(type, new ArrayList<>());
        });
        return lists;
    }

    private XYSeries getMedianSeries(List<Result> listResult, long chaseLevMedian,
            int processorsNum, String name) {
        XYSeries series = new XYSeries(name);
        for (int i = 0; i < processorsNum; i++) {
            series.add((i + 1), medianNormalized(chaseLevMedian, i, listResult));
        }
        return series;
    }

    private XYSeries getBestSeries(List<Result> listResult, long chaseLevBest,
            int processorsNum, String name) {
        XYSeries series = new XYSeries(name);
        for (int i = 0; i < processorsNum; i++) {
            series.add((i + 1), bestNormalized(chaseLevBest, i, listResult));
        }
        return series;
    }

    private XYSeries getAverageSeries(List<Result> listResult, double chaseLevAverage,
            int processorsNum, String name) {
        XYSeries series = new XYSeries(name);
        for (int i = 0; i < processorsNum; i++) {
            series.add((i + 1), averageNormalized(chaseLevAverage, i, listResult));
        }
        return series;
    }

    private void getMedianBars(List<Result> listResult, long chaseLevMedian,
            int processorsNum, String name, DefaultCategoryDataset dataSet) {
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
        BasicStroke stroke = new BasicStroke(1f);
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            xyline.setSeriesStroke(i, stroke);
        }

        Shape diamond = ShapeUtils.createDiamond(5);
        Shape diagonalCross = ShapeUtils.createDiagonalCross(5, 1);
        Shape downTriangle = ShapeUtils.createDownTriangle(5);
        Shape regularCross = ShapeUtils.createRegularCross(5, 1);
        Shape upTriangle = ShapeUtils.createUpTriangle(5);
        xyline.setSeriesPaint(0, Color.BLUE);
        xyline.setSeriesPaint(1, Color.GREEN);
        xyline.setSeriesPaint(2, Color.ORANGE);
        xyline.setSeriesPaint(3, Color.RED);
        xyline.setSeriesPaint(4, Color.BLACK);
//        xyline.setSeriesPaint(0, Color.decode("#A9A9A9"));
//        xyline.setSeriesPaint(1, Color.decode("#808080"));
//        xyline.setSeriesPaint(2, Color.decode("#696969"));
//        xyline.setSeriesPaint(3, Color.decode("#708090"));
//        xyline.setSeriesPaint(4, Color.decode("#000000"));

        xyline.setSeriesShape(0, diamond);
        xyline.setSeriesShape(1, diagonalCross);
        xyline.setSeriesShape(2, regularCross);
        xyline.setSeriesShape(3, downTriangle);
        xyline.setSeriesShape(4, upTriangle);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setRange(0.8, processorsNum + 0.2);
        domain.setTickUnit(new NumberTickUnit(1));
        domain.setLabelFont(domain.getLabelFont().deriveFont(18f));
        domain.setTickLabelFont(domain.getTickLabelFont().deriveFont(16f));
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
//        range.setRange(0.5, rangeLimit);
        range.setTickUnit(new NumberTickUnit(0.5));
        range.setLabelFont(range.getLabelFont().deriveFont(18f));
        range.setTickLabelFont(range.getTickLabelFont().deriveFont(16f));
        LegendTitle legend = xylineChart.getLegend();
        legend.setPosition(RectangleEdge.BOTTOM);
        legend.setFrame(BlockBorder.NONE);
        Font labelFont = legend.getItemFont().deriveFont(20f);
        legend.setItemFont(labelFont);

        plot.setRenderer(xyline);
        plot.setDomainCrosshairVisible(true);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        int width = 1024;
        int height = 768;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        File XYChart = new File(String.format("results/%s-%s.jpeg", prefixName,
                dateFormat.format(Calendar.getInstance().getTime())));
        try {
            ChartUtils.saveChartAsJPEG(XYChart, xylineChart, width, height);
        } catch (IOException ex) {
            Logger.getLogger(TestBattery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getAlgName(AlgorithmsType type) {
        switch (type) {
            case SIMPLE:
                return "Simple";
            case CILK:
                return "THE Cilk";
            case CHASELEV:
                return "Chase-Lev";
            case WS_NC_MULT:
                return "WS-NC-MULT";
            case B_WS_NC_MULT:
                return "B-WS-NC-MULT";
            case IDEMPOTENT_FIFO:
                return "Idempotent FIFO";
            case IDEMPOTENT_DEQUE:
                return "Idempotent DEQUE";
            case IDEMPOTENT_LIFO:
                return "Idempotent LIFO";
            case NBWSMULT_FIFO:
                return "Non-blocking";
            case B_NBWSMULT_FIFO:
                return "Bounded Non-Blocking";
        }
        return null;
    }

}
