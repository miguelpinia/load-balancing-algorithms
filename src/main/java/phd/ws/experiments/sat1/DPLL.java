/*
 */
package phd.ws.experiments.sat1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 *
 * @author miguel
 */
public class DPLL {

    private static boolean logEnabled = true;

    private static class DPLLState {

        Map<String, Boolean> model;
        List<List<String>> clauses;

        public DPLLState(Map<String, Boolean> model, List<List<String>> clauses) {
            this.model = model;
            this.clauses = clauses;
        }
    }

    private static List<List<String>> oneLiteralRule(List<List<String>> clauses,
            Map<String, Boolean> model) {
        Queue<List<String>> queue = new LinkedList<>();
        for (List<String> clause : clauses) {
            if (clause.size() == 1) {
                queue.add(clause);
            }
        }
        while (!queue.isEmpty()) {
            List<String> c = queue.remove();
            String lit = c.get(0);
            clauses.remove(c);
            if (lit.startsWith("-")) {
                model.put(negateLiteral(lit), false);
            } else {
                model.put(lit, true);

            }
            clauses = clauses.stream().
                    filter(clause -> !clause.contains(lit)).
                    collect(Collectors.toList());
            clauses.forEach(clause -> {
                if (clause.contains(lit)) {
                    clause.remove(lit);
                }
            });
            clauses = clauses.stream().
                    distinct().
                    collect(Collectors.toList());
            clauses.forEach(clause -> {
                if (clause.size() == 1) {
                    queue.add(clause);
                }
            });
        }
        return clauses;
    }

    private static boolean containsEmptyClause(List<List<String>> clauses) {
        return clauses.stream().anyMatch(c -> c.isEmpty());
    }

    private static String chooseLiteral(Set<String> vars) {
        Optional<String> val = vars.stream().findFirst();
        if (val.isPresent()) {
            String v = val.get();
            vars.remove(v);
            return v;
        }
        return null;
    }

    private static List<List<String>> simplify(List<List<String>> clauses, String literal, boolean value) {
        List<List<String>> simplified = new ArrayList<>();
        for (List<String> clause : clauses) {
            if (!clause.contains(literal) && !clause.contains(negateLiteral(literal))) {
                simplified.add(clause);
            } else {
                List<String> simpClause = new ArrayList<>();
                for (String l : clause) {
                    if (!l.equals(negateLiteral(literal))) {
                        simpClause.add(l);
                    }
                }
                if (!simpClause.isEmpty()) {
                    simplified.add(clause);
                }
            }
        }
        return simplified;
    }

    private static String negateLiteral(String literal) {
        if (literal.startsWith("-")) {
            return literal.substring(1);
        }
        return "-" + literal;
    }

    private static String cnf(List<List<String>> clauses) {
        return clauses.stream().
                map(list -> list.stream().collect(Collectors.joining(" v ", "(", ")"))).
                collect(Collectors.joining(" ^ ", "{", "}"));
    }

    public static boolean dpll(Map<String, Boolean> model, List<List<String>> clauses, int numVars, Set<String> vars) {
        Stack<DPLLState> stack = new Stack<>();
        stack.push(new DPLLState(model, clauses));
        while (!stack.isEmpty()) {
            System.out.println(stack.size());
            DPLLState currentState = stack.pop();
            model = currentState.model;
            clauses = currentState.clauses;

            if (clauses.isEmpty()) {
                return true;
            }

            if (containsEmptyClause(clauses)) {
                continue;
            }
            clauses = oneLiteralRule(clauses, model);
//            System.out.println(cnf(clauses));

            String literal = chooseLiteral(vars);
            if (literal == null) {
                continue;
            }
            List<List<String>> reducedClauses = simplify(clauses, literal, true);
            System.out.println("Literal: " + literal);
            Map<String, Boolean> trueModel = new HashMap<>(model);
            trueModel.put(literal, true);

            stack.push(new DPLLState(trueModel, reducedClauses));

            List<List<String>> reducedClausesNegated = simplify(clauses, literal, false);
            Map<String, Boolean> falseModel = new HashMap<>(model);
            falseModel.put(literal, false);
            stack.push(new DPLLState(falseModel, reducedClausesNegated));
            System.gc();
        }
        return false;
    }

    public static Result solve(Formula formula) {
        log(0, "Formula CNF: " + formula.toString());
        long executionTime = System.currentTimeMillis();
        int orderSize = formula.getVarFactory().getVariables().size();
        Result r = resolve(formula);
        r.setTimeTaken(System.currentTimeMillis() - executionTime);
        System.out.println("Solvable? " + r.isSatisfied());
        System.out.println("Assignment:");
        r.getResult().entrySet().iterator().forEachRemaining(e -> {
            System.out.println(e.getKey() + ": " + e.getValue());
        });
        return r;
    }

    private static String visualizeAssigment(Map<String, Variable> assignment) {
        return assignment.entrySet().stream().
                map(e -> String.format("%s: %b", e.getKey(), e.getValue().getValue())).
                collect(Collectors.joining("\n"));
    }

    private static Result CDCL(Formula f) {
        int decLev = 0;
        return null;
    }

    public static Formula unitPropagation(Formula f) {
        Queue<Clause> queue = new LinkedList<>();

        Formula newFormula = Formula.copy(f);
        List<Clause> clauses = newFormula.getClauses();
        for (Clause clause : clauses) {
            if (clause.getLiterals().size() == 1) {
                queue.add(clause);
            }
        }
        Clause c;
        String rep;
        while (!queue.isEmpty()) {
            c = queue.remove();
            if (!c.getLiterals().isEmpty()) {
                rep = c.getLiterals().get(0).getRepr();
                if (rep.startsWith("-")) {
                    newFormula.setLiteral(rep.replace("-", ""), false);
                } else {
                    newFormula.setLiteral(rep, true);
                }
                for (Clause clause : newFormula.getClauses()) {
                    if (clause.getLiterals().size() == 1) {
                        queue.add(clause);
                    }
                }
            }
        }
        return newFormula;
    }

    private static Formula pureLiteralElimination(Formula formula) {
        Formula newFormula = Formula.copy(formula);
        List<Clause> clauses = newFormula.getClauses();
        for (Clause clause : clauses) {
            for (Literal literal : clause.getLiterals()) {
                String opposite = literal.getRepr().startsWith("-")
                        ? literal.getRepr().replaceFirst("-", "")
                        : String.format("-%s", literal.getRepr());
                if (!newFormula.containsLiteral(opposite)) {
                    if (opposite.startsWith("-")) {
                        newFormula.setLiteral(opposite.replace("-", ""), false);
                    } else {
                        newFormula.setLiteral(opposite, true);
                    }
                }
            }
        }
        return newFormula;
    }

    private static Result resolve(Formula formula) {
        int tabs = 0;
        Result r = new Result();
        Stack<Formula> stack = new Stack<>();
        stack.push(formula);
        Formula f;
        while (!stack.empty()) {
            f = stack.pop();
            System.out.println("Size stack: " + stack.size() + "; Formula: "
                    + f.toString() + "; " + visualizeAssigment(f.getAssignment()));
            Formula newFormula = unitPropagation(f);
            if (newFormula.getClauses().isEmpty()) {
                // return satisfiable, we found an assignment
                Map<String, Boolean> result = new HashMap<>();
                newFormula.getVarFactory().getVariables().entrySet().iterator().forEachRemaining(e -> {
                    result.put(e.getKey(), e.getValue().getValue());
                });
                r.setResultMap(result);
                r.setSatisfied(true);
                return r;
            }
//            if (newFormula.isOnlyEmptyClauses()) {
//                // return unsatisfiable, if remaining clauses are empty, this is
//                // because empty clauses are unsatisfiable
//                System.out.println("error here only emtpy" + newFormula);
//                r.setSatisfied(false);
//                return r;
//            }
            newFormula = pureLiteralElimination(newFormula);

            List<Literal> literals = f.getLiteralsLexi();
            Literal literal = literals.stream().
                    filter(l -> l.getVariable().getStatus() == VarStatus.NOASSIGNED).
                    findFirst().orElse(null);
            Variable variable = literal == null ? null : literal.getVariable();
            if (variable == null) {
                literal = literals.stream().
                        filter(l -> l.getVariable().getStatus() == VarStatus.FIRST).
                        findFirst().orElse(null);
                variable = literal == null ? null : literal.getVariable();
            }

            if (variable != null) {
                if (variable.getStatus() == VarStatus.NOASSIGNED) {
                    Formula right = Formula.copy(newFormula);
                    right.getVarFactory().set(variable.getSymbol(), true);
                    right.setLiteral(variable.getSymbol(), true);
                    stack.push(right);

                }
                Formula left = Formula.copy(newFormula);
                left.getVarFactory().set(variable.getSymbol(), false);
                left.setLiteral(variable.getSymbol(), false);
                stack.push(left);
            }
        }
        r.setSatisfied(false);
        return r;
    }

    public static void enableLog() {
        logEnabled = true;
    }

    public static void disableLog() {
        logEnabled = false;
    }

    private static void log(int tabs, String msg) {
        if (!logEnabled) {
            return;
        }
        for (int i = 0; i < tabs; i++) {
            System.out.print("\t");
        }
        System.out.println(msg.replaceAll("-", "¬"));
    }

}
