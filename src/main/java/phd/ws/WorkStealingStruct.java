package phd.ws;

/**
 *
 * @author miguel
 */
public interface WorkStealingStruct {

    /**
     * Indica si la estructura de datos está vacía.
     *
     * @return Verdadero si está vacía, falso en otro caso.
     */
    boolean isEmpty();

    /**
     * Indica si la estructura de datos está vacía.
     *
     * @param label El procesador (owner) que pregunta si la estructura está
     * vacía.
     * @return Verdadero si está vacía, falso en otro caso.
     */
    boolean isEmpty(int label);

    /**
     * Agrega un elemento a la estructura que sólo el dueño de la estructura
     * puede añadir.Por simplicidad, agregamos un entero que representa una
     * tarea. Este modelo puede ser abstraído para contener objetos más
     * complejos.
     *
     * @param task la tarea a agregar.
     */
    void put(int task);

    /**
     * Variante en la que se almacena la información del procesador que está
     * guardando la tarea.
     *
     * @param task La tarea.
     * @param label El nombre del procesador.
     *
     * @return Verdadero si pudo agregar la tarea
     */
    boolean put(int task, int label);

    /**
     * La semántica de esta operación permite que sólo el dueño(worker) de la
     * estructura pueda tomar un elemento de ella.
     *
     * @return Una tarea.
     */
    int take();

    /**
     * Variante en la que se toma en cuenta el nombre del procesador que está
     * tomando la tarea.
     *
     * @param label El nombre del procesador.
     * @return Una tarea.
     */
    int take(int label);

    /**
     * La semántica de esta operación permite que otros procesos/procesadores
     * tomen tareas de esta estructura. Dependiendo de la implementación, la
     * semántica puede ser relajada(una tarea puede ser tomada al menos una vez)
     * o estricta (una tarea puede ser tomada exactamente una vez).
     *
     * @return Una tarea.
     */
    int steal();

    /**
     * Variante en la que se toma en cuenta el nombre del procesador que está
     * robando la tarea.
     *
     * @param label El nombre del procesador.
     * @return Una tarea.
     */
    int steal(int label);

    int getPuts();

    int getTakes();

    int getSteals();

}
