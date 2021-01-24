package org.mx.unam.imate.concurrent.datastructures.graph;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private final TIntList neighbours;
    private final TIntList childs;

    public Vertex(boolean directed, Integer value) {
        this.directed = directed;
        this.value = value;
        this.neighbours = new TIntArrayList();
        this.childs = directed ? new TIntArrayList() : null;
    }

    public boolean isDirected() {
        return directed;
    }

    public Integer getValue() {
        return value;
    }

    public List<Integer> getNeighbours() {
        List<Integer> result = Arrays.stream(neighbours.toArray())
            .boxed()
            .collect(Collectors.toList());
        return result;
    }

    public List<Integer> getChilds() {
        List<Integer> result = Arrays.stream(childs.toArray())
            .boxed()
            .collect(Collectors.toList());
        return result;
    }

    public void addNeighbour(int neighbour) {
        if (!neighbours.contains(neighbour)) {
            neighbours.add(neighbour);
        }
    }

    public void deleteNeighbour(int neighbour) {
        if (neighbours.contains(neighbour)) {
            neighbours.remove(Integer.valueOf(neighbour));
        }
    }

    public void addChild(int child) {
        if (directed && !childs.contains(child)) {
            childs.add(child);
        }
    }

    public void deleteChild(int child) {
        if (directed && childs.contains(child)) {
            childs.remove(Integer.valueOf(child));
        }
    }

}
