/*
 */
package phd.ws.experiments.sat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author miguel
 */
public class LiteralFactory {

    private final Map<String, Literal> factory;
    private final VariableFactory varFactory;

    public LiteralFactory(VariableFactory varFactory) {
        this.varFactory = varFactory;
        this.factory = new HashMap<>();
    }

    public LiteralFactory(LiteralFactory litFactory) {
        this.varFactory = new VariableFactory(litFactory.varFactory);
        this.factory = new HashMap<>();
        litFactory.factory.values().forEach(l -> {
            Literal lit = new Literal(l, varFactory);
            this.factory.put(lit.getLiteralStr(), lit);
        });
    }

    public Map<String, Literal> getFactory() {
        return factory;
    }

    public VariableFactory getVarFactory() {
        return varFactory;
    }

    /**
     * Given a literal string representation, it returns the Literal associated
     * if exists else create a new Literal, adds it to the set of literals and
     * returns it.
     *
     * @param litStr
     * @return
     */
    public Literal getLiteral(String litStr) {
        Literal literal = factory.get(litStr);
        if (literal == null) {
            literal = new Literal(litStr, varFactory);
            factory.put(litStr, literal);
        }
        return literal;
    }

    public void updateValueLiteral(String litStr, boolean value) {
        Literal literal = factory.get(litStr);
        literal.updateVarBoolValue(value);
        factory.put(litStr, literal);
    }

    @Override
    public String toString() {
        return factory.values().
                stream().
                map(lit -> "  " + lit.toString()).
                collect(Collectors.joining("\n", "{\n", "\n}"));
    }

    public List<Literal> getLiterals() {
        return factory.values().stream().toList();
    }

}
