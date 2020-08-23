package org.mx.unam.imate.concurrent.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 *
 * @author miguel
 */
public class GraphUtils {

    public static boolean inArray(int val, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == val) {
                return true;
            }
        }
        return false;
    }

    public static int[] initializeParent(int numVertices) {
        int parents[] = new int[numVertices];
        Arrays.fill(parents, -1);
        return parents;
    }

    public static Graph buildFromParents(int[] parents) {
        Edge[] edges = new Edge[parents.length];
        for (int i = 0; i < parents.length; i++) {
            edges[i] = new Edge(i, parents[i]);
        }
        Graph graph = new Graph(edges, parents.length, GraphType.RANDOM, false);
        return graph;
    }

    public static Graph buildFromParents(AtomicIntegerArray parents) {
        Edge[] edges = new Edge[parents.length()];
        for (int i = 0; i < parents.length(); i++) {
            edges[i] = new Edge(i, parents.get(i));
        }
        Graph graph = new Graph(edges, parents.length(), GraphType.RANDOM, false);
        return graph;
    }

    public static int MOD(int a, int b) {
        return ((a % b) + b) % b;
    }

    public static Graph torus2D(int shape) {
        int numEdges = shape * shape * 4;
        int numVertices = shape * shape;
        Edge[] edges = new Edge[numEdges];
        int neighbor;
        int i, j, currentIdx, pos;
        for (int k = 0; k < numEdges; k++) {
            j = (k / 4) % shape;
            i = k / (shape * 4);
            currentIdx = (i * shape) + j;
            pos = k % 4;
            switch (pos) {
                case 0:
                    neighbor = MOD((i - 1), shape) * shape + j;
                    break;
                case 1:
                    neighbor = i * shape + MOD((j + 1), shape);
                    break;
                case 2:
                    neighbor = MOD((i + 1), shape) * shape + j;
                    break;
                case 3:
                    neighbor = i * shape + MOD((j - 1), shape);
                    break;
                default:
                    neighbor = 0;
            }
            edges[k] = new Edge(currentIdx, neighbor);
        }
        Graph newGraph = new Graph(edges, numVertices, GraphType.TORUS_2D, false);
        return newGraph;
    }

    public static Graph directedTorus2D(int shape) {
        int numEdges = shape * shape * 2;
        int numVertices = shape * shape;
        Edge[] edges = new Edge[numEdges];
        int neighbor;
        int i, j, currentIdx, pos;
        for (int k = 0; k < numEdges; k++) {
            j = (k / 2) % shape;
            i = k / (shape * 2);
            currentIdx = (i * shape) + j;
            pos = k % 2;
            switch (pos) {
                case 0:
                    neighbor = i * shape + MOD((j + 1), shape);
                    break;
                case 1:
                    neighbor = MOD((i + 1), shape) * shape + j;
                    break;
                default:
                    neighbor = 0;
            }
            edges[k] = new Edge(currentIdx, neighbor);
        }
        Graph newGraph = new Graph(edges, numVertices, GraphType.TORUS_2D, true);
        return newGraph;
    }

    public static Graph torus2D60(int shape) {
        Random rand = new Random(System.currentTimeMillis());
        int numEdges = shape * shape * 4;
        int numVertices = shape * shape;
        Edge[] edges = new Edge[numEdges];
        int randomNumber, i, j, currentIdx, pos;
        int current = 0;
        int neighbor;
        for (int k = 0; k < numEdges; k++) {
            j = (k / 4) % shape;
            i = k / (shape * 4);
            currentIdx = (i * shape) + j;
            pos = k % 4;
            randomNumber = rand.nextInt(100);

            switch (pos) {
                case 0:
                    neighbor = MOD((i - 1), shape) * shape + j;
                    edges[current++] = new Edge(currentIdx, neighbor);
                    break;
                case 1:
                    if (randomNumber < 60) {
                        neighbor = i * shape + MOD((j + 1), shape);
                        edges[current++] = new Edge(currentIdx, neighbor);
                    }
                    break;
                case 2:
                    if (randomNumber < 60) {
                        neighbor = MOD((i + 1), shape) * shape + j;

                        edges[current++] = new Edge(currentIdx, neighbor);
                    }
                    break;
                case 3:
                    if (randomNumber < 60) {
                        neighbor = i * shape + MOD((j - 1), shape);
                        edges[current++] = new Edge(currentIdx, neighbor);
                    }
                    break;
                default:
                    neighbor = 0;
            }
        }
        edges = Arrays.copyOf(edges, current);
        Graph newGraph = new Graph(edges, numVertices, GraphType.TORUS_2D_60, false);
        return newGraph;
    }

    public static Graph directedTorus2D60(int shape) {
        Random rand = new Random(System.currentTimeMillis());
        int numEdges = shape * shape * 2;
        int numVertices = shape * shape;
        Edge[] edges = new Edge[numEdges];
        int randomNumber, i, j, currentIdx, pos;
        int current = 0;
        int neighbor;
        for (int k = 0; k < numEdges; k++) {
            j = (k / 2) % shape;
            i = k / (shape * 2);
            currentIdx = (i * shape) + j;
            pos = k % 2;
            randomNumber = rand.nextInt(100);
            switch (pos) {
                case 0:
                    neighbor = i * shape + MOD((j + 1), shape);
                    edges[current++] = new Edge(currentIdx, neighbor);
                    break;
                case 1:
                    if (randomNumber < 60) {
                        neighbor = MOD((i + 1), shape) * shape + j;
                        edges[current++] = new Edge(currentIdx, neighbor);
                    }
                    break;
            }
        }
        edges = Arrays.copyOf(edges, current);
        Graph newGraph = new Graph(edges, numVertices, GraphType.TORUS_2D_60, false);
        return newGraph;
    }

    public static Graph torus3D(int shape) {
        int numEdges = shape * shape * shape * 6;
        int numVertices = shape * shape * shape;
        Edge[] edges = new Edge[numEdges];
        int i;
        int j;
        int k;
        int currentIdx;
        int pos;
        int neighbor;
        for (int m = 0; m < numEdges; ++m) {
            k = (m / 6) % shape;
            j = (m / (shape * 6)) % shape;
            i = (m / (shape * shape * 6)) % shape;
            currentIdx = (i * shape * shape) + (j * shape) + k;
            pos = m % 6;
            switch (pos) {
                case 0:
                    neighbor = (i * shape * shape) + (j * shape) + MOD((k - 1), shape);
                    break;
                case 1:
                    neighbor = (i * shape * shape) + (j * shape) + MOD((k + 1), shape);
                    break;
                case 2:
                    neighbor = (i * shape * shape) + (MOD((j - 1), shape) * shape) + k;
                    break;
                case 3:
                    neighbor = (i * shape * shape) + (MOD((j + 1), shape) * shape) + k;
                    break;
                case 4:
                    neighbor = (MOD((i - 1), shape) * shape * shape) + (j * shape) + k;
                    break;
                case 5:
                    neighbor = (MOD((i + 1), shape) * shape * shape) + (j * shape) + k;
                    break;
                default:
                    neighbor = 0;
            }
            edges[m] = new Edge(currentIdx, neighbor);
        }
        Graph graph = new Graph(edges, numVertices, GraphType.TORUS_3D, false);
        return graph;
    }

    public static Graph directedTorus3D(int shape) {
        int numEdges = shape * shape * shape * 3;
        int numVertices = shape * shape * shape;
        Edge[] edges = new Edge[numEdges];
        int i;
        int j;
        int k;
        int currentIdx;
        int pos;
        int neighbor;
        for (int m = 0; m < numEdges; ++m) {
            k = (m / 3) % shape;
            j = (m / (shape * 3)) % shape;
            i = (m / (shape * shape * 3)) % shape;
            currentIdx = (i * shape * shape) + (j * shape) + k;
            pos = m % 3;
            switch (pos) {
                case 0:
                    neighbor = (i * shape * shape) + (j * shape) + MOD((k + 1), shape);
                    break;
                case 1:
                    neighbor = (i * shape * shape) + (MOD((j + 1), shape) * shape) + k;
                    break;
                case 2:
                    neighbor = (MOD((i + 1), shape) * shape * shape) + (j * shape) + k;
                    break;
                default:
                    neighbor = 0;
            }
            edges[m] = new Edge(currentIdx, neighbor);
        }
        Graph graph = new Graph(edges, numVertices, GraphType.TORUS_3D, true);
        return graph;
    }

    public static Graph torus3D40(int shape) {
        Random random = new Random(System.currentTimeMillis());
        int numEdges = shape * shape * shape * 6;
        int numVertices = shape * shape * shape;
        Edge[] edges = new Edge[numEdges];
        int randomNumber;
        int i;
        int j;
        int k;
        int current = 0;
        int currentIdx;
        int pos;
        int neighbor;
        for (int m = 0; m < (shape * shape * shape * 6); ++m) {
            k = (m / 6) % shape;
            j = (m / (shape * 6)) % shape;
            i = (m / (shape * shape * 6)) % shape;
            currentIdx = (i * shape * shape) + (j * shape) + k;
            pos = m % 6;
            randomNumber = random.nextInt(100);
            switch (pos) {
                case 0:
                    neighbor = (i * shape * shape) + (j * shape) + MOD((k - 1), shape);
                    edges[current++] = new Edge(currentIdx, neighbor);
                    break;
                case 1:
                    if (randomNumber < 40) {
                        neighbor = (i * shape * shape) + (j * shape) + MOD((k + 1), shape);
                        edges[current++] = new Edge(currentIdx, neighbor);
                    }
                    break;
                case 2:
                    if (randomNumber < 40) {
                        neighbor = (i * shape * shape) + (MOD((j - 1), shape) * shape) + k;
                        edges[current++] = new Edge(currentIdx, neighbor);
                    }
                    break;
                case 3:
                    if (randomNumber < 40) {
                        neighbor = (i * shape * shape) + (MOD((j + 1), shape) * shape) + k;
                        edges[current++] = new Edge(currentIdx, neighbor);
                    }
                    break;
                case 4:
                    if (randomNumber < 40) {
                        neighbor = (MOD((i - 1), shape) * shape * shape) + (j * shape) + k;
                        edges[current++] = new Edge(currentIdx, neighbor);
                    }
                    break;
                case 5:
                    if (randomNumber < 40) {
                        neighbor = (MOD((i + 1), shape) * shape * shape) + (j * shape) + k;
                        edges[current++] = new Edge(currentIdx, neighbor);
                    }
                    break;
            }

        }
        edges = Arrays.copyOf(edges, current);
        Graph graph = new Graph(edges, numVertices, GraphType.TORUS_3D_40, false);
        return graph;
    }

    public static Graph directedTorus3D40(int shape) {
        Random random = new Random(System.currentTimeMillis());
        int numEdges = shape * shape * shape * 3;
        int numVertices = shape * shape * shape;
        Edge[] edges = new Edge[numEdges];
        int randomNumber;
        int i;
        int j;
        int k;
        int current = 0;
        int currentIdx;
        int pos;
        int neighbor;
        for (int m = 0; m < numEdges; ++m) {
            k = (m / 3) % shape;
            j = (m / (shape * 3)) % shape;
            i = (m / (shape * shape * 3)) % shape;
            currentIdx = (i * shape * shape) + (j * shape) + k;
            pos = m % 3;
            randomNumber = random.nextInt(100);
            switch (pos) {
                case 0:
                    neighbor = (i * shape * shape) + (j * shape) + MOD((k + 1), shape);
                    edges[current++] = new Edge(currentIdx, neighbor);
                    break;
                case 1:
                    if (randomNumber < 40) {
                        neighbor = (i * shape * shape) + (MOD((j + 1), shape) * shape) + k;
                        edges[current++] = new Edge(currentIdx, neighbor);
                    }
                    break;
                case 2:
                    if (randomNumber < 40) {
                        neighbor = (MOD((i + 1), shape) * shape * shape) + (j * shape) + k;
                        edges[current++] = new Edge(currentIdx, neighbor);
                    }
                    break;
            }
        }
        edges = Arrays.copyOf(edges, current);
        Graph graph = new Graph(edges, numVertices, GraphType.TORUS_3D_40, false);
        return graph;
    }

    private static Graph myKGraph(int numberVertices, int vertexDegree, GraphType type) {
        Random random = new Random(System.currentTimeMillis());
        int numEdges = numberVertices * vertexDegree;
        int numVertices = numberVertices;
        Edge[] edges = new Edge[numEdges];
        int randomVertex;
        int i;
        int j;
        int current;
        boolean inPrevious;
        for (int k = 0; k < (numberVertices * vertexDegree); k++) {
            i = k / (numberVertices * vertexDegree);
            j = (k / vertexDegree) % numberVertices;
            current = (i * numberVertices) + j;
            do {
                inPrevious = false;
                randomVertex = random.nextInt(numberVertices);
                for (int idx = 0; idx < k % vertexDegree; idx++) {
                    if (edges[k] != null && randomVertex == edges[k].getDest()) {
                        inPrevious = true;
                        break;
                    }
                }
            } while (randomVertex == current || inPrevious);
            edges[k] = new Edge(current, randomVertex);
        }
        Graph graph = new Graph(edges, numVertices, type, false);
        return graph;
    }

    public static Graph random(int numberVertices, int vertexDegree) {
        Random random = new Random(System.currentTimeMillis());
        int numEdges = numberVertices * vertexDegree;
        int numVertices = numberVertices;
        Edge[] edges = new Edge[numEdges];
        int randomVertex;
        int i;
        int j;
        int current;
        boolean inPrevious;
        for (int k = 0; k < (numberVertices * vertexDegree); k++) {
            i = k / (numberVertices * vertexDegree);
            j = (k / vertexDegree) % numberVertices;
            current = (i * numberVertices) + j;
            do {
                inPrevious = false;
                randomVertex = random.nextInt(numberVertices);
                for (int idx = 0; idx < k % vertexDegree; idx++) {
                    if (edges[k] != null && randomVertex == edges[k].getDest()) {
                        inPrevious = true;
                        break;
                    }
                }
            } while (randomVertex == current || inPrevious);
            edges[k] = new Edge(current, randomVertex);
        }
        Graph graph = new Graph(edges, numVertices, GraphType.RANDOM, false);
        return graph;
    }

    public static Graph kgraph(int numVertices, int k) {
        return myKGraph(numVertices, k, GraphType.KGRAPH);
    }

    private static Graph myDirectedGraph(int numVertices, int degree) {
        Random random = new Random(System.currentTimeMillis());
        DirectedVertex[] vertices = new DirectedVertex[numVertices];
        int randomVertex;
        int intentos;
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new DirectedVertex(degree);
        }
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < degree; j++) {
                randomVertex = random.nextInt(numVertices);
                intentos = 0;
                do {
                    DirectedVertex v = vertices[randomVertex];
                    if (v.canAddEntrada() && !v.inEntrada(i)) {
                        vertices[i].addSalida(randomVertex);
                        v.addEntrada(i);
                        break;
                    }
                    randomVertex = (randomVertex + 1) % numVertices;
                    intentos++;

                } while (intentos < numVertices);
            }
        }
        Edge[] edges = new Edge[numVertices * degree];
        int edgeIdx = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < degree; j++) {
                edges[edgeIdx++] = new Edge(i, vertices[i].getSalida()[j]);
            }
        }
        Graph graph = new Graph(edges, numVertices, GraphType.KGRAPH, true);
        return graph;
    }

    public static Graph directedKGraph(int numVertices, int k) {
        return myDirectedGraph(numVertices, k);
    }

    public static Graph directedRandom(int numVertices, int degree) {
        Random random = new Random(System.currentTimeMillis());
        List<Edge> edges = new ArrayList<>();
        int randomVertex;
        for (int i = 0; i < numVertices; i++) {
            edges.add(new Edge(i, ((i + 1) % numVertices)));
            int tryDegree = random.nextInt(degree - 1);
            while (tryDegree > 0) {
                randomVertex = random.nextInt(numVertices);
                if (randomVertex == i) {
                    continue;
                }
                edges.add(new Edge(i, randomVertex));
                tryDegree--;
            }
        }
        Edge[] nEdges = new Edge[edges.size()];
        for (int i = 0; i < nEdges.length; i++) {
            nEdges[i] = edges.get(i);
        }
        Graph graph = new Graph(nEdges, numVertices, GraphType.RANDOM, true);
        return graph;
    }

    public static Graph graphType(int shape, GraphType type, boolean directed) {
        switch (type) {
            case TORUS_2D:
                return directed ? directedTorus2D(shape) : torus2D(shape);
            case TORUS_2D_60:
                return directed ? directedTorus2D60(shape) : torus2D60(shape);
            case TORUS_3D:
                return directed ? directedTorus3D(shape) : torus3D(shape);
            case TORUS_3D_40:
                return directed ? directedTorus3D40(shape) : torus3D40(shape);
            case RANDOM:
                return directed ? directedRandom(shape, 6) : random(shape, 6);
            case KGRAPH:
                return directed ? directedKGraph(shape, 3) : kgraph(shape, 3);
            default:
                return null;
        }
    }

    public static int[] stubSpanning(Graph graph, int steps) {
        Random random = new Random(System.currentTimeMillis());
        int[] stubSpanning = new int[steps];
        int randomVal = random.nextInt(graph.getNumVertices());
        if (graph.getType() == GraphType.KGRAPH) {
            for (int i = 0; i < steps; i++) {
                stubSpanning[i] = MOD(randomVal + i, graph.getNumVertices());
            }
            return stubSpanning;
        }
        int i = 0;
        Node ptr;
        Stack stack = new Stack();
        stack.push(randomVal);
        int idx, tmpVal;
        while (i < steps) {
            idx = stack.pop();
            ptr = graph.getVertices()[idx];
            while (ptr != null) {
                tmpVal = ptr.getVal();
                if (!stack.inStack(tmpVal) && !inArray(tmpVal, stubSpanning)) {
                    stack.push(tmpVal);
                }
                ptr = ptr.getNext();
            }
            if (!inArray(idx, stubSpanning)) {
                stubSpanning[i++] = idx;
            }
        }
        return stubSpanning;
    }

    static class DirectedVertex {

        int[] entrada;
        int[] salida;
        int sizeEntrada;
        int sizeSalida;
        int degree;

        public DirectedVertex(int degree) {
            this.degree = degree;
            entrada = new int[degree];
            salida = new int[degree];
            sizeEntrada = 0;
            sizeSalida = 0;
        }

        public boolean addEntrada(int vertexEntrada) {
            if (sizeEntrada > degree) {
                return false;
            }
            entrada[sizeEntrada++] = vertexEntrada;
            return true;
        }

        public boolean addSalida(int vertexSalida) {
            if (sizeSalida > degree) {
                return false;
            }
            salida[sizeSalida++] = vertexSalida;
            return true;
        }

        public int[] getEntrada() {
            return entrada;
        }

        public int[] getSalida() {
            return salida;
        }

        public boolean inEntrada(int vertex) {
            for (int i = 0; i < entrada.length; i++) {
                if (entrada[i] == vertex) {
                    return true;
                }
            }
            return false;
        }

        public boolean inSalida(int vertex) {
            for (int i = 0; i < salida.length; i++) {
                if (salida[i] == vertex) {
                    return true;
                }
            }
            return false;
        }

        public boolean canAddEntrada() {
            return sizeEntrada < degree;
        }

        public boolean canAddSalida() {
            return sizeSalida < degree;
        }

    }

}
