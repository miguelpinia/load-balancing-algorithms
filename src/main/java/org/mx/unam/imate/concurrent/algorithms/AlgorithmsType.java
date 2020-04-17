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
    OURS_V1,
    OURS_V2,
    NBWSMULT_FIFO,
    WFWSMULT_FIFO,
    WSNCMULT_FIFO,
    B_NBWSMULT_FIFO,
    B_WFWSMULT_FIFO,
    B_WSNCMULT_FIFO

}
