package phd.ws.experiments.sat1;

/**
 *
 * @author miguel
 */
public class Literal {

    private boolean negated;
    private String repr;
    private Variable variable;

    public Literal(Variable variable, boolean negated) {
        this.variable = variable;
        this.negated = negated;
        if (negated) {
            repr = String.format("-%s", variable.getSymbol());
        } else {
            repr = variable.getSymbol();
        }
    }

    public Variable getVariable() {
        return variable;
    }

    public String getRepr() {
        return repr;
    }

    public boolean isSatisfied() {
        return negated != variable.getValue();
    }

}
