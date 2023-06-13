package phd.ws.experiments.sat;

import java.util.Objects;

/**
 *
 * @author miguel
 */
public class Variable {

    private String var;
    private boolean value;
    private Status status;

    public Variable(String var, boolean value) {
        this.var = var;
        this.value = value;
        this.status = Status.NOASSIGNED;
    }

    public Variable(Variable variable) {
        this.var = variable.var;
        this.value = variable.value;
        this.status = variable.status;
    }

    /**
     * Updates the boolean value of this variable only if the status is
     * NOASSIGNED or ASSIGNED. The update status is the following:
     *
     * NOASSIGNED => ASSIGNED.
     *
     * ASSIGNED => CLOSED.
     *
     * @param value
     */
    public void updateValue(boolean value) {
        if (status != Status.CLOSED) {
            this.value = value;
            status = status == Status.NOASSIGNED ? Status.ASSIGNED : Status.CLOSED;
        }
    }

    /**
     * Updates the boolean value of this variable only if the status is
     * NOASSIGNED or ASSIGNED and the new status will be CLOSED.
     *
     * @param value
     */
    public void updateValueToClosed(boolean value) {
        if (this.status == Status.NOASSIGNED || this.status == Status.ASSIGNED) {
            this.value = value;
            this.status = Status.CLOSED;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.var);
        hash = 41 * hash + (this.value ? 1 : 0);
        hash = 41 * hash + Objects.hashCode(this.status);
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
        if (!Objects.equals(this.var, other.var)) {
            return false;
        }
        return this.status == other.status;
    }

    /**
     * @return the var
     */
    public String getVar() {
        return var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * @return the value
     */
    public boolean getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(boolean value) {
        this.value = value;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Var = { " + var + ":" + value + ":" + status.toString() + " }";
    }

}
