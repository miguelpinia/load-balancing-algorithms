package org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree;

import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.chaselev.ChaseLevWorkStealing;
import org.mx.unam.imate.concurrent.algorithms.cilk.DequeCilk;
import org.mx.unam.imate.concurrent.algorithms.idempotent.IdempotentWorkStealingDeque;
import org.mx.unam.imate.concurrent.algorithms.idempotent.IdempotentWorkStealingFIFO;
import org.mx.unam.imate.concurrent.algorithms.idempotent.IdempotentWorkStealingLIFO;
import org.mx.unam.imate.concurrent.algorithms.simple.SIMPLEWorkStealing;
import org.mx.unam.imate.concurrent.algorithms.wsm.BoundedNewAlgorithm;
import org.mx.unam.imate.concurrent.algorithms.wsm.BoundedNonBlockingWorkStealingMultFIFO;
import org.mx.unam.imate.concurrent.algorithms.wsm.NewAlgorithm;
import org.mx.unam.imate.concurrent.algorithms.wsm.NonBlockingWorkStealingMultFIFO;

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
                return new ChaseLevWorkStealing(size);
            case IDEMPOTENT_DEQUE:
                return new IdempotentWorkStealingDeque(size);
            case IDEMPOTENT_FIFO:
                return new IdempotentWorkStealingFIFO(size);
            case IDEMPOTENT_LIFO:
                return new IdempotentWorkStealingLIFO(size);
            case NEW_ALGORITHM:
                return new NewAlgorithm(size, numThreads);
            case B_NEW_ALGORITHM:
                return new BoundedNewAlgorithm(size, numThreads);
            case NBWSMULT_FIFO:
                return new NonBlockingWorkStealingMultFIFO(size, numThreads);
            case B_NBWSMULT_FIFO:
                return new BoundedNonBlockingWorkStealingMultFIFO(size, numThreads);
        }
        return null;
    }

}
