package phd.ws.experiments.sat1;

import com.royvanrijn.boolish.DPLLSolver;

/**
 *
 * @author miguel
 */
public class Main {

    public static void main(String[] args) throws Exception {
        DPLLSolver solver = new DPLLSolver();
        solver.solve("data/uf20-0532.cnf");
//        String[][] formula = {{"-p", "q", "-r", "s"}, {"-q", "-r", "s"}, {"r"}, {"-p", "-s"}, {"-p", "r"}};
//        DataDimacs data = DIMACSParser.getInfo("data/uf20-0532.cnf");
//        List<List<String>> clauses = Arrays.asList(data.getClauses()).stream().map(s -> Arrays.asList(s)).collect(Collectors.toList());
//        Map<String, Boolean> model = new HashMap<>();
//        boolean result = DPLL.dpll(model, clauses, data.getNumVariables(), data.getVariables());
//        System.out.println("Result: " + result);

//        VariableFactory varFactory = new VariableFactory();
//        LiteralFactory literalFactory = new LiteralFactory(varFactory);
//        Formula f = new Formula(test, literalFactory);
        // expected output:
        //  (¬p v q v ¬r v s) ^ (¬q v ¬r v s) ^ (r) ^ (¬p v ¬s) ^ (¬p v r)
//        System.out.println(f);
//        System.out.println(f.getLiteralsLexi());
//        DPLL.disableLog();
//        DPLL.enableLog();
//        DPLL.solve(f);
    }

}
