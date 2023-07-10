/*
 */
package phd.ws.experiments.sat1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author miguel
 */
public class Variable {

    private String symbol;
    private boolean value;
    private List<Clause> belongsTo;
    private boolean assigned;
    private VarStatus status;

    public Variable(String symbol, boolean value) {
        this.symbol = symbol;
        this.value = value;
        this.belongsTo = new ArrayList<>();
        this.assigned = false;
        this.status = VarStatus.NOASSIGNED;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean getValue() {
        return value;
    }

    public void flipValue() {
        value = !value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public List<Clause> getClauses() {
        return belongsTo;
    }

    public void addClause(Clause clause) {
        belongsTo.add(clause);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.symbol);
        hash = 37 * hash + (this.value ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.belongsTo);
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
        final Variable other = (Variable) obj;
        if (this.value != other.value) {
            return false;
        }
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        return Objects.equals(this.belongsTo, other.belongsTo);
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public boolean getAssigned() {
        return assigned;
    }

    public void setStatus(VarStatus status) {
        this.status = status;
    }

    public VarStatus getStatus() {
        return this.status;
    }

}
