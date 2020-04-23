package org.mx.unam.imate.concurrent.algorithms.experiments;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
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
public class Bateria {

    private final GraphType graphType;
    private final Integer vertexSize;
    private final StepSpanningTreeType stepType;
    private final Integer iterations;

    public Bateria(GraphType graphType, int vertexSize, StepSpanningTreeType stepType, int iterations) {
        this.graphType = graphType;
        this.vertexSize = vertexSize;
        this.stepType = stepType;
        this.iterations = iterations;
    }

    public void prueba() {
        int processorsNum = Runtime.getRuntime().availableProcessors();
        List<Result> cilkList = new ArrayList<>();
        List<Result> chaseLevList = new ArrayList<>();
        List<Result> idemDequeList = new ArrayList<>();
        List<Result> idemFifoList = new ArrayList<>();
        List<Result> idemLifoList = new ArrayList<>();
        List<Result> nbwsList = new ArrayList<>();
        List<Result> wfwsList = new ArrayList<>();
        List<Result> wsncList = new ArrayList<>();
        List<Result> bnbwsList = new ArrayList<>();
        List<Result> bwfwsList = new ArrayList<>();
        List<Result> bwsncList = new ArrayList<>();
        List<Result> newAlgList = new ArrayList<>();
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (int i = 0; i < processorsNum; i++) {

            System.out.println("Número de HILOS: " + (i + 1));
            // CILK
            Parameters cilk = new Parameters(graphType, AlgorithmsType.CILK, vertexSize, (i + 1), 128, false, 10, stepType);
            SpanningTree stcilk = new SpanningTree(cilk);
            Result resultcilk = stcilk.statistics(stcilk.experiment());
            cilkList.add(resultcilk);
            // CHASE-LEV
            Parameters chaseLev = new Parameters(graphType, AlgorithmsType.CHASELEV, vertexSize, (i + 1), 0, false, 10, stepType);
            SpanningTree stchaselev = new SpanningTree(chaseLev);
            Result resultchaselev = stchaselev.statistics(stchaselev.experiment());
            chaseLevList.add(resultchaselev);
            // IDEMPOTENT DEQUE
            Parameters idemDeque = new Parameters(graphType, AlgorithmsType.IDEMPOTENT_DEQUE, vertexSize, (i + 1), 128, false, 10, stepType);
            SpanningTree stidemDeque = new SpanningTree(idemDeque);
            Result resultidemDeque = stidemDeque.statistics(stidemDeque.experiment());
            idemDequeList.add(resultidemDeque);
            // IDEMPOTENT FIFO
            Parameters idemFifo = new Parameters(graphType, AlgorithmsType.IDEMPOTENT_FIFO, vertexSize, (i + 1), 128, false, 10, stepType);
            SpanningTree stidemFifo = new SpanningTree(idemFifo);
            Result resultidemFifo = stidemFifo.statistics(stidemFifo.experiment());
            idemFifoList.add(resultidemFifo);
            // IDEMPOTENT LIFO
            Parameters idemLifo = new Parameters(graphType, AlgorithmsType.IDEMPOTENT_LIFO, vertexSize, (i + 1), 128, false, 10, stepType);
            SpanningTree stidemLifo = new SpanningTree(idemLifo);
            Result resultidemLifo = stidemLifo.statistics(stidemLifo.experiment());
            idemLifoList.add(resultidemLifo);
            // NBWSMULT
            Parameters nbwsmult = new Parameters(graphType, AlgorithmsType.NBWSMULT_FIFO, vertexSize, (i + 1), 1000000, false, 10, stepType);
            SpanningTree stnbwsmult = new SpanningTree(nbwsmult);
            Result resultnbwsmult = stnbwsmult.statistics(stnbwsmult.experiment());
            nbwsList.add(resultnbwsmult);
            // WFWSMULT
            Parameters wfwsmult = new Parameters(graphType, AlgorithmsType.WFWSMULT_FIFO, vertexSize, (i + 1), 1000000, false, 10, stepType);
            SpanningTree stwfwsmult = new SpanningTree(wfwsmult);
            Result resultwfwsmult = stwfwsmult.statistics(stwfwsmult.experiment());
            wfwsList.add(resultwfwsmult);
            // WSNCMULT
            Parameters wsncmult = new Parameters(graphType, AlgorithmsType.WSNCMULT_FIFO, vertexSize, (i + 1), 1000000, false, 10, stepType);
            SpanningTree stwsncmult = new SpanningTree(wsncmult);
            Result resultwsncmult = stwsncmult.statistics(stwsncmult.experiment());
            wsncList.add(resultwsncmult);
            // BNBWSMULT
            Parameters bnbwsmult = new Parameters(graphType, AlgorithmsType.B_NBWSMULT_FIFO, vertexSize, (i + 1), 1000000, false, 10, stepType);
            SpanningTree stbnbwsmult = new SpanningTree(bnbwsmult);
            Result resultbnbwsmult = stbnbwsmult.statistics(stbnbwsmult.experiment());
            bnbwsList.add(resultbnbwsmult);
            // BWFWSMULT
            Parameters bwfwsmult = new Parameters(graphType, AlgorithmsType.B_WFWSMULT_FIFO, vertexSize, (i + 1), 1000000, false, 10, stepType);
            SpanningTree stbwfwsmult = new SpanningTree(bwfwsmult);
            Result resultbwfwsmult = stbwfwsmult.statistics(stbwfwsmult.experiment());
            bwfwsList.add(resultbwfwsmult);
            // BWSNCMULT
            Parameters bwsncmult = new Parameters(graphType, AlgorithmsType.B_WSNCMULT_FIFO, vertexSize, (i + 1), 1000000, false, 10, stepType);
            SpanningTree stbwsncmult = new SpanningTree(bwsncmult);
            Result resultbwsncmult = stbwsncmult.statistics(stbwsncmult.experiment());
            bwsncList.add(resultbwsncmult);
            // NewAlgorithm
            Parameters newAlgorithm = new Parameters(graphType, AlgorithmsType.NEW_ALGORITHM, vertexSize, (i + 1), 1000000, false, 10, stepType);
            SpanningTree stnewAlgorithm = new SpanningTree(newAlgorithm);
            Result resultnewAlgorithm = stnewAlgorithm.statistics(stnewAlgorithm.experiment());
            newAlgList.add(resultnewAlgorithm);
        }

        long chaseLevMedian = chaseLevList.get(0).getMedian();
        double chaseLevAverage = chaseLevList.get(0).getAverage();
        XYSeries cilkSeries = new XYSeries("Cilk");
        XYSeries chaseLevSeries = new XYSeries("Chase-Lev");
        XYSeries idemDequeSeries = new XYSeries("Idempotent Deque");
        XYSeries idemFifoSeries = new XYSeries("Idempotent FIFO");
        XYSeries idemLifoSeries = new XYSeries("Idempotent LIFO");
        XYSeries nbwsSeries = new XYSeries("Non-blocking");
        XYSeries wfwsSeries = new XYSeries("Wait-Free");
        XYSeries wsncSeries = new XYSeries("Non-Concurrent");
        XYSeries bnbwsSeries = new XYSeries("Bounded Non-Blocking");
        XYSeries bwfwsSeries = new XYSeries("Bounded Wait-Free");
        XYSeries bwsncSeries = new XYSeries("Bounded Non-Concurrent");
        XYSeries newAlgSeries = new XYSeries("New Algorithm");

        for (int i = 0; i < processorsNum; i++) {
            cilkSeries.add((i + 1), medianNormalized(chaseLevMedian, i, cilkList));
            chaseLevSeries.add((i + 1), medianNormalized(chaseLevMedian, i, chaseLevList));
            idemDequeSeries.add((i + 1), medianNormalized(chaseLevMedian, i, idemDequeList));
            idemFifoSeries.add((i + 1), medianNormalized(chaseLevMedian, i, idemFifoList));
            idemLifoSeries.add((i + 1), medianNormalized(chaseLevMedian, i, idemLifoList));
            nbwsSeries.add((i + 1), medianNormalized(chaseLevMedian, i, nbwsList));
            wfwsSeries.add((i + 1), medianNormalized(chaseLevMedian, i, wfwsList));
            wsncSeries.add((i + 1), medianNormalized(chaseLevMedian, i, wsncList));
            bnbwsSeries.add((i + 1), medianNormalized(chaseLevMedian, i, bnbwsList));
            bwfwsSeries.add((i + 1), medianNormalized(chaseLevMedian, i, bwfwsList));
            bwsncSeries.add((i + 1), medianNormalized(chaseLevMedian, i, bwsncList));
            newAlgSeries.add((i + 1), medianNormalized(chaseLevMedian, i, newAlgList));
        }

        dataset.addSeries(cilkSeries);
        dataset.addSeries(chaseLevSeries);
        dataset.addSeries(idemDequeSeries);
        dataset.addSeries(idemFifoSeries);
        dataset.addSeries(idemLifoSeries);
        dataset.addSeries(nbwsSeries);
        dataset.addSeries(wfwsSeries);
        dataset.addSeries(wsncSeries);
        dataset.addSeries(bnbwsSeries);
        dataset.addSeries(bwfwsSeries);
        dataset.addSeries(bwsncSeries);
        dataset.addSeries(newAlgSeries);

        JFreeChart xylineChart = ChartFactory
                .createXYLineChart("Tiempos de ejecución",
                        "Procesadores",
                        "SpeedUp",
                        dataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);
        XYPlot plot = xylineChart.getXYPlot();
        XYLineAndShapeRenderer xyline = new XYLineAndShapeRenderer(true, true);
        XYItemLabelGenerator xy = new StandardXYItemLabelGenerator();
        xyline.setDefaultItemLabelGenerator(xy);
        xyline.setDefaultItemLabelsVisible(true);
        xyline.setDefaultLinesVisible(true);
        plot.setRenderer(xyline);
        plot.setDomainCrosshairVisible(true);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        int width = 1280;
        /* Width of the image */
        int height = 960;
        /* Height of the image */
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        File XYChart = new File(String.format("/tmp/ejecución-%s.jpeg",
                dateFormat.format(Calendar.getInstance().getTime())));
        try {
            ChartUtils.saveChartAsJPEG(XYChart, xylineChart, width, height);
        } catch (IOException ex) {
            Logger.getLogger(Bateria.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private double medianNormalized(long chaseLevMedian, int processorNum, List<Result> results) {
        long median = results.get(processorNum).getMedian();
        return (double) chaseLevMedian / (double) median;
    }

    private double averageNormalized(long chaseLevAverage, int processorNum, List<Result> results) {
        double average = results.get(processorNum).getAverage();
        return chaseLevAverage / average;
    }

}
