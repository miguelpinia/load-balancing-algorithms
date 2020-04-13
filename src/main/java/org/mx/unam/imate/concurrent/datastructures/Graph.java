package org.mx.unam.imate.concurrent.datastructures;

import java.util.Arrays;

/**
 *
 * @author miguel
 */
public class Graph {

    public static final String CICLO = "CICLO_DETECTADO";
    public static final String DISCONEXA = "COMPONENTES_DISCONEXAS";
    public static final String ES_ARBOL = "ES_ARBOL";

    private Node[] vertices;
    private int numVertices;
    private int numConnectedVertices;
    private int root;

    public Graph() {
    }

    public Graph(Edge[] edges, int numVertices, int root) {
        this(edges, numVertices);
        this.root = root;
    }

    public Graph(Edge[] edges, int numVertices) {
        vertices = new Node[numVertices];
        this.numVertices = numVertices;
        this.numConnectedVertices = 0;
        for (Edge edge : edges) {
            int src = edge.getSrc();
            int dest = edge.getDest();
            Node srcNode;
            Node destNode;
            if (dest == -1) {
                srcNode = null;
                destNode = new Node(src, null);
                root = src;
            } else {
                srcNode = new Node(dest, vertices[src]);
                destNode = new Node(src, vertices[dest]);
            }
            if (srcNode != null && !inList(srcNode, vertices[src])) {
                vertices[src] = srcNode;
            }
            if (dest != -1 && !inList(destNode, vertices[dest])) {
                vertices[dest] = destNode;
            }
        }
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] != null) {
                numConnectedVertices++;
            }
        }
    }

    public void setRoot(int root) {
        this.root = root;
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

    public void printGraph() {
        String repr = "";
        for (int i = 0; i < vertices.length; i++) {
            Node node = vertices[i];
            while (node != null) {
                System.out.println(String.format("(%d, %d) ", i, node.getVal()));
                node = node.getNext();
            }
            System.out.println();
        }
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

    boolean isCyclicUtil(int root, boolean visited[]) {
        int[] parents = new int[visited.length];
        Arrays.fill(parents, -1);
        Queue queue = new Queue();
        visited[root] = true;
        queue.enqueue(root);
        int u;
        Node tmp;
        while (!queue.isEmpty()) {
            u = queue.dequeue();
            tmp = vertices[u];
            while (tmp != null) {
                int v = tmp.getVal();
                if (!visited[v]) {
                    visited[v] = true;
                    queue.enqueue(v);
                    parents[v] = u;
                } else if (parents[u] != v) {
                    return true;
                }
                tmp = tmp.getNext();
            }
        }
        return false;
    }

    public boolean isTree() {
        boolean visited[] = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }
        if (isCyclicUtil(root, visited)) {
            return false;
        }
        for (int u = 0; u < numVertices; u++) {
            if (u != root && !visited[u]) {
                return false;
            }
        }
        return true;
    }

    public String isTreeResponse() {
        boolean visited[] = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }
        if (isCyclicUtil(root, visited)) {
            return CICLO;
        }
        for (int u = 0; u < numVertices; u++) {
            if (!visited[u]) {
                System.out.println("Vértice: " + u);
                return DISCONEXA;
            }
        }
        return ES_ARBOL;
    }

}
