package phd.ws.experiments.spanningTree;

import phd.ws.AlgorithmsType;
import phd.ws.WorkStealingStruct;
import phd.ws.imp.ChaseLevWorkStealing;
import phd.ws.imp.DequeCilk;
import phd.ws.imp.IdempotentWorkStealingDeque;
import phd.ws.imp.IdempotentWorkStealingFIFO;
import phd.ws.imp.IdempotentWorkStealingLIFO;
import phd.ws.imp.SIMPLEWorkStealing;
import phd.ws.imp.BWSNCMULT;
import phd.ws.imp.BWSNCMULTLA;
import phd.ws.imp.BoundedNonBlockingWorkStealingMultFIFO;
import phd.ws.imp.New_BWSNCMULT;
import phd.ws.imp.New_BWSNCMULTLA;
import phd.ws.imp.NonBlockingWorkStealingMultFIFO;
import phd.ws.imp.WSNCMULT;
import phd.ws.imp.WSNCMULTLA;
import phd.ws.imp.BWSNCMULTLAOpt;
import phd.ws.imp.BWSNCMULTOpt;
import phd.ws.imp.WSNCMULTLAOpt;
import phd.ws.imp.WSNCMULTOpt;

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
