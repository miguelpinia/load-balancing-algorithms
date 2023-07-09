package phd.ws.experiments.sat;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author miguel
 */
public class VariableFactory {

    private final Map<String, Variable> factory;

    public VariableFactory() {
        factory = new HashMap<>();
    }

    public VariableFactory(VariableFactory factory) {
        this.factory = new HashMap<>();
        factory.factory.values().forEach(v -> {
            Variable newVar = new Variable(v);
            this.factory.put(newVar.getVar(), newVar);
        });
    }

    public Variable getVariable(String varRep) {
        return factory.get(varRep);
    }

    public void setVariable(String varRep, boolean value) {
        Variable v = factory.get(varRep);
        if (v == null) {
            factory.put(varRep, new Variable(varRep, value));
        } else {
            v.updateValue(value);
            factory.replace(varRep, v);
        }
    }

    public void setVariable(String varRep, Variable v) {
        factory.put(varRep, v);
    }

    public int getSize() {
        return factory.size();
    }

    public List<Variable> getVariablesSorted() {
        return factory.values().stream().sorted(Comparator.comparing(Variable::getVar)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return factory.values().
                stream().
                map(var -> var.toString()).
                collect(Collectors.joining(", ", "{", "}"));
    }
}
