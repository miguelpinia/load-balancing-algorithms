package phd.ws.experiments.sat1;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author miguel
 */
public class LiteralFactory {

    private final Map<String, Literal> literals;
    private final VariableFactory varFactory;

    public LiteralFactory(VariableFactory varFactory) {
        this.literals = new HashMap<>();
        this.varFactory = varFactory;
    }

    private LiteralFactory(VariableFactory varFactory, Map<String, Literal> lits) {
        this.varFactory = varFactory;
        this.literals = lits;
    }

    public Literal create(String repr) {
        Literal literal = literals.get(repr);
        if (literal == null) {
            if (repr.startsWith("-")) {
                String symbol = repr.substring(1);
                Variable v = varFactory.create(symbol, false);
                literal = new Literal(v, true);
                literals.put(repr, literal);
            } else {
                Variable v = varFactory.create(repr, false);
                literal = new Literal(v, false);
                literals.put(repr, literal);
            }
        }
        return literal;
    }

    public Literal get(String repr) {
        return literals.get(repr);
    }

    public boolean contains(String repr) {
        return literals.containsKey(repr);
    }

    public VariableFactory getVarFactory() {
        return varFactory;
    }

    public static LiteralFactory copy(LiteralFactory factory) {
        VariableFactory varFact = VariableFactory.copy(factory.getVarFactory());
        Map<String, Literal> lits = new HashMap<>();
        factory.literals.
                entrySet().
                iterator().
                forEachRemaining(l -> lits.put(l.getKey(), l.getValue()));
        return new LiteralFactory(varFact, lits);
    }

    public Map<String, Literal> getLiterals() {
        return literals;
    }

    public void setVarStatus(String repr, VarStatus assignment) {
        literals.get(repr).getVariable().setStatus(assignment);
    }

    public VarStatus getVarStatus(String repr) {
        return literals.get(repr).getVariable().getStatus();
    }

}
