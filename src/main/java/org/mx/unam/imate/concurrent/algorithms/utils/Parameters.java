package org.mx.unam.imate.concurrent.algorithms.utils;

import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.stepSpanningTree.StepSpanningTreeType;
import org.mx.unam.imate.concurrent.datastructures.graph.GraphType;

/**
 * Clase que permite indicar los parámetros para la ejecución de un algoritmo de
 * balanceo de carga.
 *
 * @author miguel
 */
public class Parameters {

    private GraphType type;
    private int shape;
    private boolean report;
    private int numThreads;
    private AlgorithmsType algType;
    private int structSize;
    private int numIterExps;
    private StepSpanningTreeType stepSpanningTreeType;
    private boolean directed;
    private boolean stealTime;

    public Parameters() {
    }

    /**
     * Los parámetros necesarios para construir la ejecución de un árbol
     * generador distribuido son el tipo de gráfica <i>type</i>, el tipo de
     * algoritmo que se va a ejecutar <i>algType</i>, el número de vértices (en
     * este caso, se debe tomar en cuenta que si se genera un toro, debe darse
     * el shape de la malla que genera el toro o el número de vértices de la
     * gráfica aleatoria) <i>shape</i>, el número de hilos que van a ejecutar el
     * algoritmo <i>numThreads</i>, el tamaño de la estructura donde van a
     * almacenarse las tareas durante la ejecución del algoritmo
     * <i>structSize</i> y si se va a incluir un objeto que guarde información
     * para realizar un reporte de la ejecución.Por defecto el número de
     * iteraciones es uno y el algoritmo de stepSpanningTree toma en cuenta la
     * versión de doble colecta para terminar.
     *
     * @param type El tipo de gráfica.
     * @param algType El tipo de algoritmo.
     * @param shape El shape de la gráfica.
     * @param numThreads El número de hilos.
     * @param structSize El tamaño de la estructura.
     * @param report Si se va a realizar un reporte.
     * @param directed
     * @param stealTime
     */
    public Parameters(GraphType type, AlgorithmsType algType, int shape, int numThreads, int structSize, boolean report, boolean directed, boolean stealTime) {
        this.type = type;
        this.shape = shape;
        this.report = report;
        this.numThreads = numThreads;
        this.algType = algType;
        this.structSize = structSize;
        this.numIterExps = 1;
        this.stepSpanningTreeType = StepSpanningTreeType.DOUBLE_COLLECT;
        this.directed = directed;
        this.stealTime = stealTime;
    }

    /**
     * Los parámetros necesarios para construir la ejecución de un árbol
     * generador distribuido son el tipo de gráfica <i>type</i>, el tipo de
     * algoritmo que se va a ejecutar <i>algType</i>, el número de vértices (en
     * este caso, se debe tomar en cuenta que si se genera un toro, debe darse
     * el shape de la malla que genera el toro o el número de vértices de la
     * gráfica aleatoria) <i>shape</i>, el número de hilos que van a ejecutar el
     * algoritmo <i>numThreads</i>, el tamaño de la estructura donde van a
     * almacenarse las tareas durante la ejecución del algoritmo
     * <i>structSize</i> y si se va a incluir un objeto que guarde información
     * para realizar un reporte de la ejecución.El algoritmo de stepSpanningTree
     * toma en cuenta la versión de doble colecta para terminar.
     *
     * @param type El tipo de gráfica.
     * @param algType El tipo de algoritmo.
     * @param shape El shape de la gráfica.
     * @param numThreads El número de hilos.
     * @param structSize El tamaño de la estructura.
     * @param report Si se va a realizar un reporte.
     * @param numIterExps El número de iteraciones para un experimento.
     * @param directed
     * @param stealTime
     */
    public Parameters(GraphType type, AlgorithmsType algType, int shape, int numThreads, int structSize, boolean report, int numIterExps, boolean directed, boolean stealTime) {
        this(type, algType, shape, numThreads, structSize, report, directed, stealTime);
        this.numIterExps = numIterExps;
        this.stepSpanningTreeType = StepSpanningTreeType.DOUBLE_COLLECT;
    }

    /**
     * Los parámetros necesarios para construir la ejecución de un árbol
     * generador distribuido son el tipo de gráfica <i>type</i>, el tipo de
     * algoritmo que se va a ejecutar <i>algType</i>, el número de vértices (en
     * este caso, se debe tomar en cuenta que si se genera un toro, debe darse
     * el shape de la malla que genera el toro o el número de vértices de la
     * gráfica aleatoria) <i>shape</i>, el número de hilos que van a ejecutar el
     * algoritmo <i>numThreads</i>, el tamaño de la estructura donde van a
     * almacenarse las tareas durante la ejecución del algoritmo
     * <i>structSize</i> y si se va a incluir un objeto que guarde información
     * para realizar un reporte de la ejecución.
     *
     * @param type El tipo de gráfica.
     * @param algType El tipo de algoritmo.
     * @param shape El shape de la gráfica.
     * @param numThreads El número de hilos.
     * @param structSize El tamaño de la estructura.
     * @param report Si se va a realizar un reporte.
     * @param numIterExps El número de iteraciones para un experimento.
     * @param stepSpanningTreeType El tipo de stepSpanningTree.
     * @param directed
     * @param stealTime
     */
    public Parameters(GraphType type, AlgorithmsType algType, int shape, int numThreads, int structSize, boolean report, int numIterExps, StepSpanningTreeType stepSpanningTreeType, boolean directed, boolean stealTime) {
        this(type, algType, shape, numThreads, structSize, report, directed, stealTime);
        this.numIterExps = numIterExps;
        this.stepSpanningTreeType = stepSpanningTreeType;
    }

    public GraphType getType() {
        return type;
    }

    public int getShape() {
        return shape;
    }

    public boolean doReport() {
        return report;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public AlgorithmsType getAlgType() {
        return algType;
    }

    public int getStructSize() {
        return structSize;
    }

    public StepSpanningTreeType getStepSpanningTreeType() {
        return stepSpanningTreeType;
    }

    public boolean isSpecialExecution() {
        switch (algType) {
            case NBWSMULT_FIFO:
            case B_NBWSMULT_FIFO:
            case WS_NC_MULT:
            case B_WS_NC_MULT:
            case WS_NC_MULT_LA:
            case B_WS_NC_MULT_LA:
            case NEW_B_WS_NC_MULT:
            case NEW_B_WS_NC_MULT_LA:
                return true;
            default:
                return false;
        }
    }

    public int getNumIterExps() {
        return numIterExps;
    }

    public boolean isStealTime() {
        return stealTime;
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public void setNumIterExps(int numIterExps) {
        this.numIterExps = numIterExps;
    }

    public void setType(GraphType type) {
        this.type = type;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public void setNumThreads(int numThreads) {
        this.numThreads = numThreads;
    }

    public void setAlgType(AlgorithmsType algType) {
        this.algType = algType;
    }

    public void setStructSize(int structSize) {
        this.structSize = structSize;
    }

    public void setStepSpanningTreeType(StepSpanningTreeType stepSpanningTreeType) {
        this.stepSpanningTreeType = stepSpanningTreeType;
    }

    public void setStealTime(boolean stealTime) {
        this.stealTime = stealTime;
    }

    @Override
    public String toString() {
        return "Parameters{" + "type=" + type + ", shape=" + shape + ", report="
                + report + ", numThreads=" + numThreads + ", algType=" + algType
                + ", structSize=" + structSize + ", numIterExps=" + numIterExps
                + ", stepSpanningTreeType=" + stepSpanningTreeType + ", directed="
                + directed + ", stealTime=" + stealTime + '}';
    }

}
