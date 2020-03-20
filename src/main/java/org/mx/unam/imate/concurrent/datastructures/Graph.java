package org.mx.unam.imate.concurrent.datastructures;

/**
 *
 * @author miguel
 */
public class Graph {

    private Node[] vertices;
    private int numVertices;
    private int numConnectedVertices;

    public Graph() {
    }

    public Graph(Edge[] edges, int numVertices) {
        vertices = new Node[numVertices];
        this.numVertices = numVertices;
        this.numConnectedVertices = 0;
        for (Edge edge : edges) {
            int src = edge.getSrc();
            int dest = edge.getDest();
            Node srcNode = new Node(dest, vertices[src]);
            Node destNode = new Node(src, vertices[dest]);
            if (!inList(srcNode, vertices[src])) {
                vertices[src] = srcNode;
            }
            if (!inList(destNode, vertices[dest])) {
                vertices[dest] = destNode;
            }
        }
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] != null) {
                numConnectedVertices++;
            }
        }
    }

    public int getNumVertices() {
        return numVertices;
    }

    public Node[] getVertices() {
        return vertices;
    }

    public int getNumConnectedVertices() {
        return numConnectedVertices;
    }

    void printRow(int i) {
        Node node = vertices[i];
        // TO-DO (MAPA 19-03-2020): Cambiar println por string templates y hacer un sólo println al final.
        while (node != null) {
            System.out.print(String.format("(%d, %d) ", i, node.getVal()));
            node = node.getNext();
        }
        System.out.println();
    }

    private boolean inList(Node node, Node head) {
        while (head != null) {
            if (head.getVal() == node.getVal()) {
                return true;
            }
            head = head.getNext();
        }
        return false;
    }

}
