/*
 */
package phd.ws.experiments.sat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author miguel
 */
public class Utils {

    public static List<List<String>> parseDimacs(String inputFile) {
        try {
            return Files.lines(Paths.get(inputFile)).
                    map(line -> line.trim().replace("\\s+", " ").trim()).
                    filter(line -> line.endsWith(" 0")).
                    map(line -> Arrays.stream(line.substring(0, line.length() - 2).
                    trim().split("\\s+")).collect(Collectors.toList())).
                    collect(Collectors.toList());
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean validate(Formula formula, VariableFactory varFactory) {
        boolean result = true;
        for (Clause clause : formula.getClauses()) {
            result &= clause.getLiterals().
                    stream().
                    anyMatch(lit -> lit.isNegated() != varFactory.getVariable(lit.getVariableStr()).getValue());
        }
        return result;
    }

    public static String withAssigment(Formula formula, VariableFactory varFactory) {
        return formula.getClauses().
                stream().
                map(cl -> cl.getLiterals().stream().
                map(lit -> lit.getLiteralStr() + ":" + (lit.isNegated() != varFactory.getVariable(lit.getVariableStr()).getValue())).
                collect(Collectors.joining(" v ", "(", ")"))).
                collect(Collectors.joining(" ^ ", "{", "}"));
    }

}
