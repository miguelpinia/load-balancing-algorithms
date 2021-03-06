package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree;

import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.chaselev.ChaseLevWorkStealing;
import org.mx.unam.imate.concurrent.algorithms.cilk.DequeCilk;
import org.mx.unam.imate.concurrent.algorithms.idempotent.IdempotentWorkStealingDeque;
import org.mx.unam.imate.concurrent.algorithms.idempotent.IdempotentWorkStealingFIFO;
import org.mx.unam.imate.concurrent.algorithms.idempotent.IdempotentWorkStealingLIFO;
import org.mx.unam.imate.concurrent.algorithms.simple.SIMPLEWorkStealing;
import org.mx.unam.imate.concurrent.algorithms.wsm.BWSNCMULT;
import org.mx.unam.imate.concurrent.algorithms.wsm.BWSNCMULTLA;
import org.mx.unam.imate.concurrent.algorithms.wsm.BoundedNonBlockingWorkStealingMultFIFO;
import org.mx.unam.imate.concurrent.algorithms.wsm.New_BWSNCMULT;
import org.mx.unam.imate.concurrent.algorithms.wsm.New_BWSNCMULTLA;
import org.mx.unam.imate.concurrent.algorithms.wsm.NonBlockingWorkStealingMultFIFO;
import org.mx.unam.imate.concurrent.algorithms.wsm.WSNCMULT;
import org.mx.unam.imate.concurrent.algorithms.wsm.WSNCMULTLA;
import org.mx.unam.imate.concurrent.algorithms.wsm.optimized.BWSNCMULTLAOpt;
import org.mx.unam.imate.concurrent.algorithms.wsm.optimized.BWSNCMULTOpt;
import org.mx.unam.imate.concurrent.algorithms.wsm.optimized.WSNCMULTLAOpt;
import org.mx.unam.imate.concurrent.algorithms.wsm.optimized.WSNCMULTOpt;

/**
 *
 * @author miguel
 */
public class WorkStealingStructLookUp {

    /**
     * Factory for work-stealing algorithms. Supported types are: SIMPLE, CILK,
     * CHASELEV, IDEMPOTENT_DEQUE, IDEMPOTENT_FIFO, IDEMPOTENT_LIFO, WS_NC_MULT,
     * B_WS_NC_MULT, NBWSMULT_FIFO, B_NBWSMULT_FIFO. Currently, the size for the
     * datastructure is fixed.
     *
     * @param type The type of algorithm. Supported types are: SIMPLE, CILK,
     * CHASELEV, IDEMPOTENT_DEQUE, IDEMPOTENT_FIFO, IDEMPOTENT_LIFO, WS_NC_MULT,
     * B_WS_NC_MULT, NBWSMULT_FIFO, B_NBWSMULT_FIFO.
     * @param size The size for the data structure within of the algorithm.
     * @param numThreads Number of threads with which information is shared.
     * @return A new instance of the algorithm.
     */
    public static WorkStealingStruct getWorkStealingStruct(AlgorithmsType type, int size, int numThreads) {
        switch (type) {
            case SIMPLE:
                return new SIMPLEWorkStealing();
            case CILK:
                return new DequeCilk(size);
            case CHASELEV:
                return new ChaseLevWorkStealing(size);
            case IDEMPOTENT_DEQUE:
                return new IdempotentWorkStealingDeque(size);
            case IDEMPOTENT_FIFO:
                return new IdempotentWorkStealingFIFO(size);
            case IDEMPOTENT_LIFO:
                return new IdempotentWorkStealingLIFO(size);
            case WS_NC_MULT:
                return new WSNCMULT(size, numThreads);
            case B_WS_NC_MULT:
                return new BWSNCMULT(size, numThreads);
            case NBWSMULT_FIFO:
                return new NonBlockingWorkStealingMultFIFO(size, numThreads);
            case B_NBWSMULT_FIFO:
                return new BoundedNonBlockingWorkStealingMultFIFO(size, numThreads);
            case WS_NC_MULT_LA:
                return new WSNCMULTLA(size, numThreads);
            case B_WS_NC_MULT_LA:
                return new BWSNCMULTLA(size, numThreads);
            case NEW_B_WS_NC_MULT:
                return new New_BWSNCMULT(size, numThreads);
            case NEW_B_WS_NC_MULT_LA:
                return new New_BWSNCMULTLA(size, numThreads);
            case WS_NC_MULT_OPT:
                return new WSNCMULTOpt(size, numThreads);
            case WS_NC_MULT_LA_OPT:
                return new WSNCMULTLAOpt(size, numThreads);
            case B_WS_NC_MULT_OPT:
                return new BWSNCMULTOpt(size, numThreads);
            case B_WS_NC_MULT_LA_OPT:
                return new BWSNCMULTLAOpt(size, numThreads);
        }
        return null;
    }

}
