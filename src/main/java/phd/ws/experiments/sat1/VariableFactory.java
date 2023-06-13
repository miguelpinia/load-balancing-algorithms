/*
 */
package phd.ws.experiments.sat1;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author miguel
 */
public class VariableFactory implements Cloneable {

    private final Map<String, Variable> variables;

    public VariableFactory() {
        variables = new HashMap<>();
    }

    private VariableFactory(Map<String, Variable> vars) {
        variables = vars;
    }

    public Variable create(String symbol, boolean value) {
        Variable v = variables.get(symbol);
        if (v == null) {
            v = new Variable(symbol, value);
            variables.put(symbol, v);
        }
        return v;
    }

    public Variable get(String symbol) {
        return variables.get(symbol);
    }

    public void set(String symbol, boolean value) {
        Variable v = variables.get(symbol);
        v.setValue(value);
        v.setAssigned(true);
        if (v.getStatus() == VarStatus.NOASSIGNED) {
            v.setStatus(VarStatus.FIRST);
        } else if (v.getStatus() == VarStatus.FIRST) {
            v.setStatus(VarStatus.CLOSED);
        }
        variables.put(symbol, v);

    }

    public Map<String, Variable> getVariables() {
        return variables;
    }

    public static VariableFactory copy(VariableFactory factory) {
        Map<String, Variable> vars = new HashMap<>();
        factory.getVariables().
                entrySet().
                iterator().
                forEachRemaining(e -> vars.put(e.getKey(), e.getValue()));
        return new VariableFactory(vars);
    }
}
