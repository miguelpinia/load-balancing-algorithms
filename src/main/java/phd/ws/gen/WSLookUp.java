/*
 */
package phd.ws.gen;

/**
 *
 * @author miguel
 */
public class WSLookUp<T> {

    public WSStruct<T> lookupClassic(WSType type, int size) {
        switch (type) {
            case CILK:
                return new CilkTHE<>(size);
            case CHASELEV:
                return new ChaseLev<>(size);
            case IDEMPOTENT_FIFO:
                return new IdempotentFIFO<>(size);
            case IDEMPOTENT_LIFO:
                return new IdempotentLIFO<>(size);
            case IDEMPOTENT_DEQUE:
                return new IdempotentDeque<>(size);
            default:
                return null;
        }
    }

    public WSStruct<T> lookupMult(WSType type, int nodeCapacity, int numThreads, T bottom) {
        switch (type) {
            case WS_MULT:
                return new WSMULT<>(nodeCapacity, numThreads, bottom);
            case B_WS_MULT:
                return new BWSMULT<>(nodeCapacity, numThreads, bottom);
            default:
                return null;
        }
    }

}
