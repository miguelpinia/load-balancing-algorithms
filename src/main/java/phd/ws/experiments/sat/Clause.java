package phd.ws.experiments.sat;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author miguel
 */
public class Clause {

    private List<Literal> literals;

    public Clause(List<Literal> literals) {
        this.literals = literals;
    }

    public boolean isSatisfied() {
        return !literals.isEmpty() && literals.stream().anyMatch(lit -> lit.isSatisfied());
    }

    public boolean contains(Literal literal) {
        if (literal == null) {
            return false;
        }
        for (int i = 0; i < literals.size(); i++) {
            if (literals.get(i).getLiteralStr().equals(literal.getLiteralStr())) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return literals.isEmpty();
    }

    public boolean isUnitClause() {
        return literals.size() == 1;
    }

    public void removeLiteral(Literal literal) {
        literals.remove(literal);
    }

    public void removeNegatedLiteral(Literal literal) {
        String negated = literal.isNegated()
                ? literal.getLiteralStr().substring(1)
                : "-" + literal.getLiteralStr();
        literals = literals.stream().
                filter(l -> !l.getLiteralStr().equals(negated))
                .toList();
    }

    public Literal getFirst() {
        return literals.isEmpty() ? null : literals.get(0);
    }

    public Literal getLiteral(String litStr) {
        Optional<Literal> optional = literals.stream().
                filter(l -> Objects.equals(l.getLiteralStr(), litStr)).
                findAny();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public List<Literal> getLiterals() {
        return literals;
    }

    @Override
    public String toString() {
        return literals.stream().
                map(lit -> lit.getLiteralStr()).
                collect(Collectors.joining(" v ", "(", ")"));
    }

    public String toStringWithVal() {
        return literals.stream().
                map(lit -> lit.getLiteralStr() + ":" + lit.getVariable().getValue()).
                collect(Collectors.joining(" v ", "(", ")"));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.literals);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Clause other = (Clause) obj;
        return Objects.equals(this.literals, other.literals);
    }

}
