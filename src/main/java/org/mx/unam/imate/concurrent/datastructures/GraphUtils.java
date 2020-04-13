package org.mx.unam.imate.concurrent.datastructures;

import java.util.Arrays;
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
        Graph graph = new Graph(edges, parents.length);
        return graph;
    }

    public static Graph buildFromParents(AtomicIntegerArray parents) {
        Edge[] edges = new Edge[parents.length()];
        for (int i = 0; i < parents.length(); i++) {
            edges[i] = new Edge(i, parents.get(i));
        }
        Graph graph = new Graph(edges, parents.length());
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
        Graph newGraph = new Graph(edges, numVertices);
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
        for (int k = 0; k < (shape * shape * 4); k++) {
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
        Graph newGraph = new Graph(edges, numVertices);
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
        Graph graph = new Graph(edges, numVertices);
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
        Graph graph = new Graph(edges, numVertices);
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
            inPrevious = false;
            edges[k] = new Edge(current, randomVertex);
        }
        Graph graph = new Graph(edges, numVertices);
        return graph;
    }

    public static Graph graphType(int shape, GraphType type) {
        switch (type) {
            case TORUS_2D:
                return GraphUtils.torus2D(shape);
            case TORUS_2D_60:
                return GraphUtils.torus2D60(shape);
            case TORUS_3D:
                return GraphUtils.torus3D(shape);
            case TORUS_3D_40:
                return GraphUtils.torus3D40(shape);
            case RANDOM:
                return GraphUtils.random(shape, 6);
        }
        return null;
    }

    public static int[] stubSpanning(Graph graph, int steps) {
        Random random = new Random(System.currentTimeMillis());
        int[] stubSpanning = new int[steps];
        int randomVal = random.nextInt(graph.getNumVertices());
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

}
