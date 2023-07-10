/*
 */
package phd.ws.experiments.sat;

import java.util.Objects;

/**
 *
 * @author miguel
 */
public class Literal {

    private final Variable variable;
    private final String literal;
    private final boolean negated;

    public Literal(Variable variable, boolean negated) {
        this.variable = variable;
        this.literal = (negated ? "-" : "") + variable.getVar();
        this.negated = negated;
    }

    /**
     *
     * @param literal
     * @param varFactory
     */
    public Literal(Literal literal, VariableFactory varFactory) {
        this.negated = literal.negated;
        this.literal = literal.literal;
        Variable v = varFactory.getVariable(literal.getVariableStr());
        if (v == null) {
            v = new Variable(literal.variable);
            varFactory.setVariable(literal.getVariableStr(), v);
        }
        this.variable = v;
    }

    public Literal(String literal, VariableFactory factory) {
        boolean negation = literal.startsWith("-");
        String var = negation ? literal.substring(1) : literal;
        Variable v = factory.getVariable(var);
        if (v == null) {
            v = new Variable(var, false);
            factory.setVariable(var, v);
        }
        this.variable = v;
        this.literal = literal;
        this.negated = negation;
    }

    public void updateVarBoolValue(boolean value) {
        variable.updateValue(value);
    }

    public void updateVarBoolValueToClosed(boolean value) {
        variable.updateValueToClosed(value);
    }

    public String getLiteralStr() {
        return literal;
    }

    public String getVariableStr() {
        return variable.getVar();
    }

    public Variable getVariable() {
        return variable;
    }

    public boolean isSatisfied() {
        return negated != variable.getValue() && variable.getStatus() != Status.NOASSIGNED;
    }

    public boolean isNegated() {
        return negated;
    }

    public boolean isOpen() {
        return variable.getStatus() != Status.CLOSED;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.variable);
        hash = 97 * hash + Objects.hashCode(this.literal);
        hash = 97 * hash + (this.negated ? 1 : 0);
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
        final Literal other = (Literal) obj;
        if (this.negated != other.negated) {
            return false;
        }
        if (!Objects.equals(this.literal, other.literal)) {
            return false;
        }
        return Objects.equals(this.variable, other.variable);
    }

    @Override
    public String toString() {
        return literal + " = " + variable.toString();
    }

}
