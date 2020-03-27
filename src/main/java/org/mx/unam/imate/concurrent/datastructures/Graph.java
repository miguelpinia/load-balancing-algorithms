package org.mx.unam.imate.concurrent.datastructures;

/**
 *
 * @author miguel
 */
public class Graph {

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

    boolean isCyclicUtil(int v, boolean visited[], int parent) {
        int[] parents = new int[visited.length];
        Stack stack = new Stack();
        visited[v] = true;
        stack.push(v);
        int u;
        Node tmp;
        while (!stack.isEmpty()) {
            u = stack.pop();
            tmp = vertices[u];
            while (tmp != null) {
                if (!visited[tmp.getVal()]) {
                    visited[tmp.getVal()] = true;
                    stack.push(tmp.getVal());
                    parents[tmp.getVal()] = u;
                } else if (parents[u] != tmp.getVal()) {
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
        if (isCyclicUtil(root, visited, -1)) {
            return false;
        }
        for (int u = 0; u < numVertices; u++) {
            if (!visited[u]) {
                return false;
            }
        }
        return true;
    }

}
