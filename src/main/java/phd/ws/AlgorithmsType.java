package phd.ws;

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
    B_NBWSMULT_FIFO,
    WS_NC_MULT,
    WS_NC_MULT_LA,
    B_WS_NC_MULT,
    B_WS_NC_MULT_LA,
    NEW_B_WS_NC_MULT,
    NEW_B_WS_NC_MULT_LA,
    WS_NC_MULT_OPT,
    WS_NC_MULT_LA_OPT,
    B_WS_NC_MULT_OPT,
    B_WS_NC_MULT_LA_OPT
}
