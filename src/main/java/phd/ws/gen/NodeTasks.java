/*
 */
package phd.ws.gen;

import java.util.Arrays;

/**
 *
 * @author miguel
 * @param <T> Type to store in the array of tasks.
 */
public class NodeTasks<T> {

    private final int length;
    private final T[] tasks;
    private NodeTasks<T> next;

    public NodeTasks(int capacity) {
        length = capacity;
        tasks = (T[]) new Object[capacity];
        next = null;
    }

    public boolean set(int idx, T value) {
        if (idx < 0 || idx >= length) {
            return false;
        }
        tasks[idx] = value;
        return true;
    }

    public T get(int idx) {
        return tasks[idx];
    }

    public void setNext(NodeTasks<T> next) {
        this.next = next;
    }

    public NodeTasks<T> getNext() {
        return next;
    }

    public void clear() {
        Arrays.fill(tasks, null);
    }

}
