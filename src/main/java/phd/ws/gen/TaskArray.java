/*
 */
package phd.ws.gen;

/**
 *
 * @author miguel
 */
public class TaskArray<T> {

    private final int size;
    private final T[] array;

    public TaskArray(int size) {
        this.size = size;
        array = (T[]) new Object[size];
    }

    public int size() {
        return size;
    }

    public T get(int idx) {
        return array[idx];
    }

    public void set(int idx, T value) {
        array[idx] = value;
    }
}
