/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.unam.imate.concurrent.algorithms.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mx.unam.imate.concurrent.datastructures.Graph;
import org.mx.unam.imate.concurrent.datastructures.Node;
import sun.misc.Unsafe;

/**
 *
 * @author miguel
 */
public class WorkStealingUtils {

    public static Unsafe createUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(Unsafe.class);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(WorkStealingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void report(Graph graph, AtomicIntegerArray parents, int roots[]) {
        report(graph, parents, null, roots);
    }

    public static void report(Graph graph, AtomicIntegerArray parents, AtomicIntegerArray color, int roots[]) {
        try {
            PrintWriter pw = new PrintWriter(new File("/tmp/report.txt"));
            Node[] nodes = graph.getVertices();
            pw.println("=========Graph=========");
            for (int i = 0; i < nodes.length; i++) {
                Node n = nodes[i];
                while (n != null) {
                    pw.print(String.format("(%d, %d) ", i, n.getVal()));
                    n = n.getNext();
                }
                pw.println();
            }
            pw.println("=========Parents=========");
            for (int i = 0; i < parents.length(); i++) {
                pw.println(String.format("(%d, %d)", i, parents.get(i)));
            }
            if (color != null) {
                pw.println("=========Color=========");
                for (int i = 0; i < color.length(); i++) {
                    pw.println(String.format("(%d, %d)", i, color.get(i)));
                }
            }
            pw.println("=========roots=========");
            pw.println(Arrays.toString(roots));
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(WorkStealingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
