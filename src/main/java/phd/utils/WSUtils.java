package phd.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import phd.ds.Graph;

/**
 *
 * @author miguel
 */
public class WSUtils {

    public static void report(Graph graph, AtomicIntegerArray parents, int roots[]) {
        report(graph, parents, null, roots);
    }

    public static void report(Graph graph, AtomicIntegerArray parents, AtomicIntegerArray color, int roots[]) {
        try (PrintWriter pw = new PrintWriter("/tmp/report.txt", StandardCharsets.UTF_8.displayName())) {
            int numberVertices = graph.getNumberVertices();
            pw.println("=========Graph=========");
            Integer next;
            Iterator<Integer> it;
            for (int i = 0; i < numberVertices; i++) {
                it = graph.getNeighbours(i).iterator();
                while (it.hasNext()) {
                    next = it.next();
                    pw.println(String.format("(%d, %d) ", i, next));
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
        } catch (IOException ex) {
            Logger.getLogger(WSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void saveJsonObjectToFile(JSONObject jo, String path) {
        try (PrintWriter saveFile = new PrintWriter(new File(path), StandardCharsets.UTF_8.displayName())) {
            saveFile.println(jo.toString(2));
            saveFile.flush();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(WSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveJsonArrayToFile(JSONArray ja, String path) {
        try (PrintWriter saveFile = new PrintWriter(path, StandardCharsets.UTF_8.displayName())) {
            saveFile.println(ja.toString(2));
            saveFile.flush();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(WSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
