package phd.ds;

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

    public int get(int position) {
        return array[position];
    }

    public void set(int position, int value) {
        array[position] = value;
    }
}
