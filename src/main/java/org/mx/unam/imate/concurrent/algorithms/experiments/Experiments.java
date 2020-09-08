package org.mx.unam.imate.concurrent.algorithms.experiments;

import java.util.List;

import org.mx.unam.imate.concurrent.algorithms.AlgorithmsType;
import org.mx.unam.imate.concurrent.algorithms.WorkStealingStruct;
import org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree.WorkStealingStructLookUp;

/**
 *
 * @author miguel
 */
public class Experiments {

    public void putSteals(List<AlgorithmsType> types) {
        types.forEach((type) -> {
            WorkStealingStruct alg = WorkStealingStructLookUp.getWorkStealingStruct(type, 1000000, 1);
            long putTime;
            long stealTime_;
            StringBuilder sb = new StringBuilder();
            String salida = "";
            long time;
            if (type == AlgorithmsType.WS_NC_MULT || type == AlgorithmsType.B_WS_NC_MULT
                    || type == AlgorithmsType.NBWSMULT_FIFO || type == AlgorithmsType.B_NBWSMULT_FIFO) {
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.put(i, 0);
                }
                putTime = System.nanoTime() - time;
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.steal(0);
                }
                stealTime_ = System.nanoTime() - time;
                long total = putTime + stealTime_;
                sb.append(String.format("Alg: %s%nSteal time: %d ns/ %.2f ms%n"
                        + "Total time: %d ns/ %.2f ms%n",
                        type, stealTime_, (float) (stealTime_ / 1000000f),
                        total, (float) (total / 1000000f)));
            } else {
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.put(i);
                }
                putTime = System.nanoTime() - time;
                time = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    alg.steal();
                }
                stealTime_ = System.nanoTime() - time;
                long total = putTime + stealTime_;
                sb.append(String.format("Alg: %s%nSteal time: %d ns/ %.2f ms%n"
                        + "Total time: %d ns/ %.2f ms%n",
                        type, stealTime_, (float) (stealTime_ / 1000000f),
                        total, (float) (total / 1000000f)));
            }
            System.out.println(salida);
        });
    }

}
