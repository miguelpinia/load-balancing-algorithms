/*
 */
package phd.ws.experiments.sat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author miguel
 */
public class DPLL {

    public static List<List<String>> parseDimacs(String inputFile) {
        try {
            return Files.lines(Paths.get(inputFile)).
                    map(line -> line.trim().replace("\\s+", " ").trim()).
                    filter(line -> line.endsWith(" 0")).
                    map(line -> Arrays.stream(line.substring(0, line.length() - 2).
                    trim().split("\\s+")).collect(Collectors.toList())).
                    collect(Collectors.toList());
        } catch (IOException ex) {
            Logger.getLogger(DPLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static boolean validate(Formula formula, VariableFactory varFactory) {
        boolean result = true;
        for (Clause clause : formula.getClauses()) {
            result &= clause.getLiterals().
                    stream().
                    anyMatch(lit -> lit.isNegated() != varFactory.getVariable(lit.getVariableStr()).getValue());
        }
        return result;
    }

    private static String withAssigment(Formula formula, VariableFactory varFactory) {
        return formula.getClauses().
                stream().
                map(cl -> cl.getLiterals().stream().
                map(lit -> lit.getLiteralStr() + ":" + (lit.isNegated() != varFactory.getVariable(lit.getVariableStr()).getValue())).
                collect(Collectors.joining(" v ", "(", ")"))).
                collect(Collectors.joining(" ^ ", "{", "}"));
    }

    public static boolean solveDPLL(Formula formula) {
        Stack<Formula> stack = new Stack<>();
        Formula original = new Formula(formula);
        stack.push(formula);
        while (!stack.isEmpty()) {
            formula = stack.pop();
            if (formula.isSatisfied()) {
                System.out.println("Assigment:\n " + formula.getVarFactory());
                System.out.println("Validation: " + validate(original, formula.getLiteralFactory().getVarFactory()));
                System.out.println("Formula: " + withAssigment(original, formula.getLiteralFactory().getVarFactory()));
                return true;
            }
            if (formula.containsEmptyClause()) {
                continue;
            }

            formula.oneLiteralRuleSimplification();
            Literal option = formula.chooseLiteral();
            if (option == null && stack.isEmpty()) {
                return false;
            }
            if (option == null) {
                continue;
            }
            Formula fLeft = new Formula(formula);
            fLeft.setLiteralValue(option, false);
            stack.push(fLeft);
            Formula fRight = new Formula(formula);
            fRight.setLiteralValue(option, true);
            stack.push(fRight);
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<List<String>> formula = parseDimacs("data/uf20-0306.cnf");
        Formula f = new Formula(formula);
        System.out.println(f.toString());
        System.out.println("Is Satisfiable? " + solveDPLL(f));
    }

}
