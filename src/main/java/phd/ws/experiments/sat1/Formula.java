package phd.ws.experiments.sat1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author miguel
 */
public class Formula {

    private List<Clause> clauses;
    private LiteralFactory litFactory;

    public Formula(String[][] formula, LiteralFactory litFactory) {
        this.litFactory = litFactory;
        this.clauses = Arrays.stream(formula).
                map(c -> new Clause(c, this.litFactory)).
                collect(Collectors.toList());
    }

    private Formula(List<Clause> clauses, LiteralFactory litFactory) {
        this.clauses = clauses;
        this.litFactory = litFactory;
    }

    public boolean containsLiteral(String literal) {
        return clauses.stream().anyMatch(c -> c.containsLiteral(literal));
    }

    public void removeClausesContainingLiteral(String literal) {
        clauses = clauses.stream().
                filter(c -> !c.containsLiteral(literal)).
                collect(Collectors.toList());
    }

    public void removeLiteralFromClauses(String literal) {
        clauses.stream().forEach(c -> {
            if (c.containsLiteral(literal)) {
                c.removeLiteral(literal);
            }
        });
    }

    public void setLiteral(String literal, boolean value) {
        litFactory.getVarFactory().set(literal, value);
        if (value) {
            removeClausesContainingLiteral(literal);
            removeLiteralFromClauses("-" + literal);
        } else {
            removeClausesContainingLiteral("-" + literal);
            removeLiteralFromClauses(literal);
        }
        removeDuplicateClauses();
        VarStatus status = litFactory.getVarStatus(literal);
        if (status == VarStatus.NOASSIGNED) {
            litFactory.setVarStatus(literal, VarStatus.FIRST);
        } else if (status == VarStatus.FIRST) {
            litFactory.setVarStatus(literal, VarStatus.CLOSED);
        }
    }

    private void removeDuplicateClauses() {
        clauses = clauses.stream().
                distinct().
                collect(Collectors.toList());
    }

    public List<Literal> getLiteralsLexi() {
        return litFactory.getLiterals().
                values().stream().
                sorted(Comparator.comparing(Literal::getRepr)).
                collect(Collectors.toList());
    }

    public List<Clause> getClauses() {
        return clauses;
    }

    public boolean isOnlyEmptyClauses() {
        return clauses.stream().allMatch(x -> x.isEmpty());
    }

    public Map<String, Variable> getAssignment() {
        return litFactory.getVarFactory().getVariables();
    }

    public VariableFactory getVarFactory() {
        return litFactory.getVarFactory();
    }

    @Override
    public String toString() {
        return clauses.stream().
                map(c -> c.toStringDisjunctive()).
                collect(Collectors.joining(" ^ ", "{", "}"));
    }

    public static Formula copy(Formula f) {
        LiteralFactory litFac = LiteralFactory.copy(f.litFactory);
        List<Clause> newClauses = new ArrayList<>();
        f.getClauses().iterator().forEachRemaining(c -> newClauses.add(c));
        return new Formula(newClauses, litFac);
    }
}
