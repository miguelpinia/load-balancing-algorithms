package org.mx.unam.imate.concurrent.algorithms.output;

import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.chaselev.ChaseLevWorkStealing;
import org.mx.unam.imate.concurrent.algorithms.cilk.DequeCilk;
import org.mx.unam.imate.concurrent.algorithms.idempotent.IdempotentWorkStealingDeque;
import org.mx.unam.imate.concurrent.algorithms.idempotent.IdempotentWorkStealingFIFO;
import org.mx.unam.imate.concurrent.algorithms.idempotent.IdempotentWorkStealingLIFO;
import org.mx.unam.imate.concurrent.algorithms.ours.fifo.FIFOWorkStealingV1;
import org.mx.unam.imate.concurrent.algorithms.ours.fifo.FIFOWorkStealingV2;
import org.mx.unam.imate.concurrent.algorithms.simple.SIMPLEWorkStealing;
import org.mx.unam.imate.concurrent.algorithms.wsm.BoundedNonBlockingWorkStealingMultFIFO;
import org.mx.unam.imate.concurrent.algorithms.wsm.BoundedWaitFreeWorkStealingMultFIFO;
import org.mx.unam.imate.concurrent.algorithms.wsm.BoundedWorkStealingNonConcurrentMultFIFO;
import org.mx.unam.imate.concurrent.algorithms.wsm.NewAlgorithm;
import org.mx.unam.imate.concurrent.algorithms.wsm.NonBlockingWorkStealingMultFIFO;
import org.mx.unam.imate.concurrent.algorithms.wsm.WaitFreeWorkStealingMultFIFO;
import org.mx.unam.imate.concurrent.algorithms.wsm.WorkStealingNonConcurrentMultFIFO;

/**
 *
 * @author miguel
 */
public class WorkStealingStructLookUp {

    public static WorkStealingStruct getWorkStealingStruct(AlgorithmsType type, int size, int numThreads) {
        switch (type) {
            case SIMPLE:
                return new SIMPLEWorkStealing();
            case CILK:
                return new DequeCilk(size);
            case CHASELEV:
                return new ChaseLevWorkStealing();
            case IDEMPOTENT_DEQUE:
                return new IdempotentWorkStealingDeque(size);
            case IDEMPOTENT_FIFO:
                return new IdempotentWorkStealingFIFO(size);
            case IDEMPOTENT_LIFO:
                return new IdempotentWorkStealingLIFO(size);
            case OURS_V1:
                return new FIFOWorkStealingV1(size, numThreads);
            case OURS_V2:
                return new FIFOWorkStealingV2(size, numThreads);
            case NEW_ALGORITHM:
                return new NewAlgorithm(size, numThreads);
            case NBWSMULT_FIFO:
                return new NonBlockingWorkStealingMultFIFO(size, numThreads);
            case WFWSMULT_FIFO:
                return new WaitFreeWorkStealingMultFIFO(size, numThreads);
            case WSNCMULT_FIFO:
                return new WorkStealingNonConcurrentMultFIFO(size, numThreads);
            case B_NBWSMULT_FIFO:
                return new BoundedNonBlockingWorkStealingMultFIFO(size, numThreads);
            case B_WFWSMULT_FIFO:
                return new BoundedWaitFreeWorkStealingMultFIFO(size, numThreads);
            case B_WSNCMULT_FIFO:
                return new BoundedWorkStealingNonConcurrentMultFIFO(size, numThreads);
        }
        return null;
    }

}
