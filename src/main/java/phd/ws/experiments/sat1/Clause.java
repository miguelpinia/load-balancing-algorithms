package phd.ws.experiments.sat1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A Boolean variable is a variable with two possible values, <i>True</i>
 * and <i>False</i>. Let $V$ be a set of $n$ Boolean variables. A literal of a
 * Boolean variable <b>x</b> is either x or -x, i.e. positive or negative
 * literal of x. Let X be a set of literals defined as $X = \{x, -x | x \in
 * V\}$. <b>This class represent a clause</b>. A clause is a disjunction (OR) of
 * literals, $c = (x_{i1}, \vee \ldots \vee x_{ij} in X)$
 *
 * @author miguel
 */
public class Clause {

    private LiteralFactory litFactory;
    private List<Literal> literals;

    public Clause(LiteralFactory literalFactory) {
        litFactory = literalFactory;
        literals = new ArrayList<>();
    }

    public Clause(List<Literal> literals) {
        this.literals = literals;
    }

    public Clause(String[] literals, LiteralFactory litFactory) {
        this.litFactory = litFactory;
        this.literals = Arrays.stream(literals)
                .map(this.litFactory::create)
                .collect(Collectors.toList());
    }

    public void addLiteral(Literal literal) {
        literals.add(literal);
        literal.getVariable().addClause(this);
    }

    public void addLiteral(String literal) {
        Literal lit = litFactory.create(literal);
        literals.add(lit);
        lit.getVariable().addClause(this);
    }

    public void removeLiteral(String literal) {
        literals = literals.stream().
                filter(l -> !Objects.equals(l.getRepr(), literal)).
                collect(Collectors.toList());
    }

    public List<Literal> getLiterals() {
        return literals;
    }

    public boolean isSatisfied() {
        return literals.stream().anyMatch(x -> x.isSatisfied());
    }

    public boolean containsLiteral(String repr) {
        return literals.stream().
                anyMatch(x -> Objects.equals(x.getRepr(), repr));
    }

    private String toStringSep(String separator) {
        return literals.
                stream().
                map(x -> x.getRepr()).
                collect(Collectors.joining(separator, "{ ", " }"));
    }

    @Override
    public String toString() {
        return toStringSep(", ");
    }

    public String toStringDisjunctive() {
        return toStringSep(" v ");
    }

    public boolean isEmpty() {
        return literals.isEmpty();
    }
}
