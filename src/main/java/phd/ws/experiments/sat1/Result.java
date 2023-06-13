package phd.ws.experiments.sat1;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author miguel
 */
public class Result {

    private Map<String, Boolean> result;
    private long timeTaken;
    private boolean satisfied;

    public Result() {
        result = new HashMap<>();
        timeTaken = 0;
    }

    public void setTimeTaken(long time) {
        timeTaken = time;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void addVariableValue(Variable v) {
        result.put(v.getSymbol(), v.getValue());
    }

    public void addVariable(String symbol, boolean value) {
        result.put(symbol, value);
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }

    public void setResultMap(Map<String, Boolean> result) {
        this.result = result;
    }

    public Map<String, Boolean> getResult() {
        return result;
    }

}
