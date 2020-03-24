package org.mx.unam.imate.concurrent.datastructures;

/**
 *
 * @author miguel
 */
public class TaskArrayWithSize {

    private final int size;
    private final int[] array;

    public TaskArrayWithSize(int size) {
        this.size = size;
        this.array = new int[size];
    }

    public int getSize() {
        return size;
    }

    public int[] getArray() {
        return array;
    }
}
