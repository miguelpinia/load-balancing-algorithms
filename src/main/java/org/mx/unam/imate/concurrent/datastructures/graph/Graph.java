package org.mx.unam.imate.concurrent.datastructures.graph;

import java.util.List;


/**
 * This implementation use a All vertices are represented as integers with
 * values between 0 and the total of vertices less one.
 *
 * For undirected graphs, we implemented the graph using adjancency lists, which
 * works better for sparese graphs.
 *
 * In the case of directed graphs, add a list to store the childs of each
 * vertex. This allow us know the vertices that reach to some vertex.
 *
 * @author miguel
 */
public class Graph {

    private final Vertex[] vertices;

    private final boolean directed;
    private final Integer root;
    private int numEdges;

    private final GraphType type;

    public Graph(Edge[] edges, boolean directed, Integer root, Integer numVertices, GraphType type) {
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

    /**
     * Just create the graph without information about the edges, only
     * the number of vertex that contain.
     */
    public Graph(Integer root, Integer numVertices, GraphType type, boolean directed) {
        this.directed = directed;
        this.root = root;
        this.numEdges = 0;
        this.type = type;
        this.vertices = new Vertex[numVertices];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(directed, i);
        }
    }

    private void _addEdge(Edge edge) {
        int src = edge.getSrc();
        int dest = edge.getDest();
        if (dest == -1) {
            return;
        }
        vertices[src].addNeighbour(dest);
        numEdges++;
        if (directed) {
            vertices[dest].addChild(src);
        } else {
            vertices[dest].addNeighbour(src);
            numEdges++;
        }

    }

    public void addEdge(Integer src, Integer dest) {
        if (dest == -1) {
            return;
        }
        vertices[src].addNeighbour(dest);
        numEdges++;
        if (directed) {
            vertices[dest].addChild(src);
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
        return vertices.length;
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

}
