package phd.ws.experiments.sat;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author miguel
 */
public class Formula {

    private List<Clause> clauses;
    private LiteralFactory litFactory;
    private VariableFactory varFactory;

    public Formula(List<List<String>> cls) {
        this.varFactory = new VariableFactory();
        this.litFactory = new LiteralFactory(varFactory);
        this.clauses = cls.stream().
                map(l -> l.stream().map(litFactory::getLiteral).collect(Collectors.toList())).
                map(c -> new Clause(c)).
                collect(Collectors.toList());
    }

    public Formula(Formula formula) {
        this.litFactory = new LiteralFactory(formula.litFactory);
        this.varFactory = this.litFactory.getVarFactory();
        this.clauses = formula.clauses.stream().
                map(c -> c.getLiterals().stream().map(l -> this.litFactory.getLiteral(l.getLiteralStr())).collect(Collectors.toList())).
                map(l -> new Clause(l)).
                collect(Collectors.toList());
    }

    public VariableFactory getVarFactory() {
        return varFactory;
    }

    public LiteralFactory getLiteralFactory() {
        return litFactory;
    }

    public List<Clause> getClauses() {
        return clauses;
    }

    @Override
    public String toString() {
        return clauses.stream().
                map(c -> c.toString()).
                collect(Collectors.joining(" ^ ", "{", "}"));
    }

    public String toStringWithValues() {
        return clauses.stream().map(c -> c.toStringWithVal()).collect(Collectors.joining(" ^ ", "{", "}"));
    }

    public boolean isEmpty() {
        return clauses.isEmpty();
    }

    public boolean containsEmptyClause() {
        return clauses.stream().anyMatch(c -> c.isEmpty());
    }

    public Literal chooseLiteral() {
        List<Literal> lits = litFactory.getLiterals();
        Optional<Literal> literal = lits.stream().filter(l -> l.getVariable().getStatus() == Status.NOASSIGNED).findFirst();
        if (literal.isPresent()) {
            return literal.get();
        }
        literal = lits.stream().filter(l -> l.getVariable().getStatus() == Status.ASSIGNED).findFirst();
        if (literal.isPresent()) {
            return literal.get();
        }
        return null;
    }

    public Literal chooseRandomLiteral() {
        List<Literal> lits = litFactory.getLiterals();
        Set<Literal> set = new HashSet<>(lits);
        Optional<Literal> literal = set.stream().filter(l -> l.getVariable().getStatus() == Status.NOASSIGNED).findAny();
        if (literal.isPresent()) {
            return literal.get();
        }
        literal = set.stream().filter(l -> l.getVariable().getStatus() == Status.ASSIGNED).findAny();
        if (literal.isPresent()) {
            return literal.get();
        }
        return null;
    }

    public void setLiteralValue(Literal literal, boolean value) {
        Literal lit = litFactory.getLiteral(literal.getLiteralStr());
        lit.updateVarBoolValue(value);
        clauses = clauses.stream().filter(c -> !c.isSatisfied()).collect(Collectors.toList());
        clauses.forEach(c -> {
            if (lit.isSatisfied()) {
                c.removeNegatedLiteral(lit);
            } else {
                c.removeLiteral(lit);
            }
        });
    }

    public boolean isSatisfied() {
        return clauses.isEmpty() || (!clauses.isEmpty() && clauses.stream().allMatch(c -> c.isSatisfied()));
    }

    public void oneLiteralRuleSimplification() {
        Queue<Clause> queue = new LinkedList<>();
        for (Clause clause : clauses) {
            if (clause.isUnitClause()) {
                queue.add(clause);
            }
        }
        while (!queue.isEmpty()) {
            Clause clause = queue.remove();
            Literal lit = clause.getFirst();
            if (lit == null) {
                continue;
            }
            lit.updateVarBoolValueToClosed(!lit.isNegated());
            // Remove all clauses contains literal
            clauses = clauses.stream().
                    filter(c -> !c.contains(lit)).
                    collect(Collectors.toList());
            // Delete from remaining clauses negated literal.
            clauses.forEach(c -> c.removeNegatedLiteral(lit));
            // Remove all duplicated clauses
            clauses = clauses.stream().
                    distinct().
                    collect(Collectors.toList());
            // Add to queue all discovered new unit clauses
            clauses.forEach(c -> {
                if (c.isUnitClause()) {
                    queue.add(c);
                }
            });
        }
    }

    public void assignment(long assignment) {
        List<Variable> vars = this.varFactory.getVariablesSorted();
        int numVariables = vars.size();
        String binary = Long.toBinaryString(assignment);
        boolean tmp;
        for (int i = numVariables - 1; i >= 0; i--) {
            if (i < binary.length()) {
                tmp = binary.charAt(binary.length() - (i + 1)) == '1';
            } else {
                tmp = false;
            }
            Variable v = vars.get(i);
            varFactory.setVariable(v.getVar(), tmp);
        }
    }

}
