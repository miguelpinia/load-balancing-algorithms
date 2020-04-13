package org.mx.unam.imate.concurrent.algorithms.output;

import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.chaselev.ChaseLevWorkStealing;
import org.mx.unam.imate.concurrent.algorithms.cilk.DequeCilk;
import org.mx.unam.imate.concurrent.algorithms.idempotent.deque.IdempotentWorkStealingDeque;
import org.mx.unam.imate.concurrent.algorithms.idempotent.fifo.IdempotentWorkStealingFIFO;
import org.mx.unam.imate.concurrent.algorithms.idempotent.lifo.IdempotentWorkStealingLIFO;
import org.mx.unam.imate.concurrent.algorithms.ours.fifo.v1.FIFOWorkStealingV1;
import org.mx.unam.imate.concurrent.algorithms.simple.SIMPLEWorkStealing;

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
        }
        return null;
    }

}
