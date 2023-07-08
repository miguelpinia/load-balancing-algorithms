package phd.ws.gen;

import java.util.List;

/**
 *
 * @author miguel
 */
public interface WSStruct<T> {

    /**
     * Returns true if the work-stealing data-structure is empty.
     *
     * @return True if the structure is empty. False otherwise.
     */
    boolean isEmpty();

    /**
     * Returns true if the work-stealing data-structure is empty from the view
     * of the thread <em>threadId</em>. Returns false otherwise.
     *
     * @param threadId Name of the processor calling this method. The threadId
     * can be the id of the data-structure owner.
     * @return True if the structure is empty. False otherwise.
     */
    boolean isEmpty(int threadId);

    /**
     * Add the task to the data-structure.
     *
     * @param task Task to the add to the implemented data-structure.
     */
    void put(T task);

    /**
     * Add the task to the data-structure.
     *
     * @param task
     * @param threadId
     * @return
     */
    boolean put(T task, int threadId);

    /**
     * Allows the owner thread takes a taks from the data-structure.
     *
     * @return A task.
     */
    T take();

    /**
     * Allows the owner thread (ownerId) takes a task from the data-structure.
     *
     * @param ownerId
     * @return A task
     */
    T take(int ownerId);

    /**
     * Allows other threads steal a task from this data-structure.
     *
     * @return
     */
    T steal();

    /**
     * Allows the thread threadId steal a task from this data-structure.
     *
     * @param threadId
     * @return
     */
    T steal(int threadId);

    int size();

    T get(int position);

    List<T> getSnapshot();
}
