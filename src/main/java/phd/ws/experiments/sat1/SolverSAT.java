package phd.ws.experiments.sat1;

/**
 *
 * @author miguel
 */
public class SolverSAT {

//    private boolean containsEmptyClause(List<List<Literal>> clauses) {
//        return clauses.stream().anyMatch(clause -> clause.isEmpty());
//    }
//
//    private void oneLiteralRule(List<List<Literal>> clauses, Map<String, Literal> model) {
//        Queue<List<Literal>> queue = clauses.stream().
//                filter(clause -> clause.size() == 1).
//                collect(Collectors.toCollection(LinkedList::new));
//        while (!queue.isEmpty()) {
//            List<Literal> clause = queue.remove();
//            Literal literal = clause.get(0);
//            String litVal = literal.literal;
//            clauses.remove(clause);
//            if (litVal.startsWith("-")) {
//                literal.updateVarBoolValue(true);
//                model.put(litVal.substring(1), literal);
//            }
//        }
//    }
//
//    public boolean solve(List<List<Literal>> clauses, Map<String, Literal> model) {
//        Stack<DPLLState> stack = new Stack<>();
//        stack.push(new DPLLState(model, clauses));
//        while (!stack.isEmpty()) {
//            DPLLState state = stack.pop();
//            model = state.model;
//            clauses = state.clauses;
//
//            if (clauses.isEmpty()) {
//                return true;
//            }
//
//            if (containsEmptyClause(clauses)) {
//                continue;
//            }
//
//            oneLiteralRule(clauses, model);
//        }
//        return false;
//    }
    public static void main(String[] args) {

    }

}
