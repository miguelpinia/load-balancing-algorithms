/*
 */
package phd.ws.experiments.sat1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author miguel
 */
public class DIMACSParser {

    public static String[][] parse(String fileName) {
        BufferedReader br = null;
        try {
            File f = new File(fileName);
            br = new BufferedReader(new FileReader(f));
            String[][] data;
            List<String[]> lines = br.lines().
                    map(s -> s.trim().split(" ")).
                    collect(Collectors.toList());
            String[] conf = lines.stream().
                    filter(s -> s[0].equals("p")).
                    findFirst().orElse(null);
            int vars = Integer.parseInt(conf[2]);
            int clauses = Integer.parseInt(conf[3]);

            data = new String[clauses][];

            List<String[]> cls = lines.stream().
                    filter(l -> !l[0].equals("p") && !l[0].equals("c")).
                    collect(Collectors.toList());

            for (int i = 0; i < cls.size(); i++) {
                String[] cl = cls.get(i);
                String[] clause = new String[cl.length - 1];
                System.arraycopy(cl, 0, clause, 0, cl.length - 1);
                data[i] = clause;
            }

            return data;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIMACSParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIMACSParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(DIMACSParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new String[0][0];
    }

    public static DataDimacs getInfo(String fileName) {
        BufferedReader br = null;
        try {
            File f = new File(fileName);
            br = new BufferedReader(new FileReader(f));
            String[][] data;
            List<String[]> lines = br.lines().
                    map(s -> s.trim().split(" ")).
                    collect(Collectors.toList());
            String[] conf = lines.stream().
                    filter(s -> s[0].equals("p")).
                    findFirst().orElse(null);
            int vars = Integer.parseInt(conf[2]);
            int clauses = Integer.parseInt(conf[3]);
            Set<String> set = new HashSet<>();

            data = new String[clauses][];

            List<String[]> cls = lines.stream().
                    filter(l -> !l[0].equals("p") && !l[0].equals("c")).
                    collect(Collectors.toList());

            for (int i = 0; i < cls.size(); i++) {
                String[] cl = cls.get(i);
                String[] clause = new String[cl.length - 1];

                System.arraycopy(cl, 0, clause, 0, cl.length - 1);
                data[i] = clause;
                for (String literal : clause) {
                    set.add(literal);
                }
            }
            return new DataDimacs(vars, data, set);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DIMACSParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DIMACSParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(DIMACSParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
