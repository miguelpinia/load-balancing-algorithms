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
    WS_NC_MULT,
    WS_NC_MULT_LA,
    B_NBWSMULT_FIFO,
    B_WS_NC_MULT,
    B_WS_NC_MULT_LA
}
