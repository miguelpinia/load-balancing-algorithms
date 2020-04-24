package org.mx.unam.imate.concurrent.algorithms;

/**
 * Provee un enum para los algoritmos implementados y poder utilizarlos dentro
 * de clases delegadas.
 *
 * @author miguel
 */
public enum AlgorithmsType {
    SIMPLE,
    CHASELEV,
    CILK,
    IDEMPOTENT_DEQUE,
    IDEMPOTENT_FIFO,
    IDEMPOTENT_LIFO,
    NBWSMULT_FIFO,
    NEW_ALGORITHM,
    B_NBWSMULT_FIFO,
    B_NEW_ALGORITHM
}
