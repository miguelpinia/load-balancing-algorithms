/*
 */
package phd.ws.experiments.sat1;

import java.util.Set;

/**
 *
 * @author miguel
 */
public class DataDimacs {

    private final String[][] clauses;
    private final int numVariables;
    private final Set<String> variables;

    public DataDimacs(int numVariables, String[][] clauses, Set<String> variables) {
        this.numVariables = numVariables;
        this.clauses = clauses;
        this.variables = variables;
    }

    public String[][] getClauses() {
        return clauses;
    }

    public int getNumVariables() {
        return numVariables;
    }

    public Set<String> getVariables() {
        return variables;
    }

}
