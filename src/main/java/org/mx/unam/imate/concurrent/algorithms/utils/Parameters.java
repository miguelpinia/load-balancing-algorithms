package org.mx.unam.imate.concurrent.algorithms.utils;

import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

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
     * para realizar un reporte de la ejecución.
     *
     * @param type El tipo de gráfica.
     * @param algType El tipo de algoritmo.
     * @param shape El shape de la gráfica.
     * @param numThreads El número de hilos.
     * @param structSize El tamaño de la estructura.
     * @param report Si se va a realizar un reporte.
     */
    public Parameters(GraphType type, AlgorithmsType algType, int shape, int numThreads, int structSize, boolean report) {
        this.type = type;
        this.shape = shape;
        this.report = report;
        this.numThreads = numThreads;
        this.algType = algType;
        this.structSize = structSize;
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

    public boolean isSpecialExecution() {
        return (algType == AlgorithmsType.OURS_V1 || algType == AlgorithmsType.OURS_V2);
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

}
