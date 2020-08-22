package org.mx.unam.imate.concurrent.datastructures.graph;

import java.util.List;

import org.mx.unam.imate.concurrent.datastructures.Edge;
import org.mx.unam.imate.concurrent.datastructures.GraphType;

/**
 * This implementation use a All vertices are represented as integers
 * with values between 0 and the total of vertices less one.
 *
 * For undirected graphs, we implemented the graph using adjancency lists, which
 * works better for sparese graphs.
 *
 * In the case of directed graphs, add a list to store the childs of
 * each vertex. This allow us know the vertices that reach to some
 * vertex.
 *
 * @author miguel
 */
public class Graph {

    private final Vertex[] vertices;

    private final boolean directed;
    private final Integer root;
    // reemplazar con un get
    private final Integer numVertices;
    private Integer numEdges;

    private final GraphType type;

    public Graph(Edge[] edges, boolean directed, Integer root, Integer numVertices, GraphType type) {
        this.numVertices = numVertices;
        this.directed = directed;
        this.root = root;
        this.numEdges = 0;
        this.type = type;
        this.vertices = new Vertex[numVertices];
        for (int i = 0; i < numVertices; i++) {
            vertices[i] = new Vertex(directed, i);
        }
        for (Edge edge : edges) {
            _addEdge(edge);
        }
    }

    private void _addEdge(Edge edge) {
        int src = edge.getSrc();
        int dest = edge.getDest();
        vertices[src].addNeighbour(dest);
        numEdges++;
        if (directed) {
            boolean isRoot = dest == root;
            vertices[dest].addChild(isRoot ? -1 : src);
        } else {
            vertices[dest].addNeighbour(src);
            numEdges++;
        }

    }

    public void addEdge(Edge edge) {
        _addEdge(edge);
    }

    public void addEdges(Edge[] edges) {
        for (Edge edge : edges) {
            _addEdge(edge);
        }
    }

    public void deleteEdge(Edge edge) {
        Integer src = edge.getSrc();
        Integer dst = edge.getDest();
        vertices[src].deleteNeighbour(dst);
        vertices[dst].deleteNeighbour(src);
    }

    public boolean hasEdge(Edge edge) {
        int src = edge.getSrc();
        int dst = edge.getDest();
        return vertices[src].getNeighbours().contains(dst);
    }

    public List<Integer> getNeighbours(int vertex) {
        return vertices[vertex].getNeighbours();
    }

    public List<Integer> getChilds(int vertex) {
        return vertices[vertex].getChilds();
    }

    public int getRoot() {
        return root;
    }

    public int getNumberVertices() {
        return numVertices;
    }

    public int getNumberEdges() {
        return numEdges;
    }

    public GraphType getType() {
        return type;
    }

    public boolean isDirected() {
        return directed;
    }

    public int getNumEdges() {
        return numEdges;
    }

}
