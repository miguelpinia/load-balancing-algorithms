package org.mx.unam.imate.concurrent.datastructures.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent the vertex of a graph. Use a <b>List</b> to store its neighbours,
 * where each element it's a number between 0 and the size of the graph. If the
 * graph is directed, add another <b>List</b> to store the vertices where from
 * which it has edges.
 *
 * @author miguelpinia
 */
public class Vertex {

    private final boolean directed;
    private final Integer value;
    private final List<Integer> neighbours;
    private final List<Integer> childs;

    public Vertex(boolean directed, Integer value) {
        this.directed = directed;
        this.value = value;
        this.neighbours = new ArrayList<>();
        this.childs = directed ? new ArrayList<>() : null;
    }

    public boolean isDirected() {
        return directed;
    }

    public Integer getValue() {
        return value;
    }

    public List<Integer> getNeighbours() {
        return neighbours;
    }

    public List<Integer> getChilds() {
        return childs;
    }

    public void addNeighbour(int neighbour) {
        if (!neighbours.contains(neighbour)) {
            neighbours.add(neighbour);
        }
    }

    public void deleteNeighbour(int neighbour) {
        if (neighbours.contains(neighbour)) {
            neighbours.remove(neighbour);
        }
    }

    public void addChild(int child) {
        if (directed && !childs.contains(child)) {
            childs.add(child);
        }
    }

    public void deleteChild(int child) {
        if (directed && childs.contains(child)) {
            childs.remove(child);
        }
    }

}
