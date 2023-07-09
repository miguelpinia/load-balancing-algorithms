package com.royvanrijn.boolish;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DPLLSolver {

    public static void main(String[] args) throws Exception {
        new DPLLSolver().solve("data/uf20-0532.cnf");
    }

    public void solve(final String inputFile) throws Exception {

        // Read a DIMACS file and turn into a list of clauses:
        final List<Clause> clauses = Files.lines(Paths.get(inputFile))
                .map(line -> line.trim().replaceAll("\\s+", " ").trim())
                .filter(line -> line.endsWith(" 0"))
                .map(Clause::new).collect(Collectors.toList());

        List<Integer> literals = new ArrayList<>();

        processDPLL(clauses, literals);

        // If we get here, we are unable to assign SAT, so we are UNSAT:
        System.out.println("Got to the end, UNSAT");
    }

    private void processDPLL(List<Clause> clauses, List<Integer> literals) {

        // Check if we have no more clauses that are unsatisfied:
        if (!clauses.stream().filter(clause -> !clause.clauseSatisfied).findAny().isPresent()) {
            // We are done, SAT!
            exitWithSAT(literals);
        }

        List<Integer> newLiterals = new ArrayList<>();

        // Step 1a: Find unit clauses:
        List<Integer> unitPropagation = findUnitClauses(clauses);

        // Step 1b: Process the unit variables (new units can be created while processing):
        while (unitPropagation.size() > 0) {

            // Remove this unit from all clauses:
            for (Integer unit : unitPropagation) {
                // Detect conflicts: We can not have both unit and -unit in the set, if there is we've hit a conflict.
                if (unitPropagation.contains(-unit)) {
                    // Undo everything we've done and return.
                    for (Integer literal : newLiterals) {
                        System.out.println("Undo unit: " + literal);
                        undoStep(clauses, literal);
                    }
                    return;
                }
                newLiterals.add(unit);
                System.out.println("Applying unit: " + unit);
                applyStep(clauses, unit);
            }
            unitPropagation.removeAll(newLiterals);

            // Look if we've created new unit clauses:
            unitPropagation.addAll(findUnitClauses(clauses));
        }

        // Get all the unassignedLiterals from the alive clauses:
        Set<Integer> unassignedLiterals = clauses.stream()
                .filter(clause -> !clause.clauseSatisfied)
                .flatMap(clause -> clause.unassignedLiterals.stream()).collect(Collectors.toSet());

        for (Integer decisionLiteral : unassignedLiterals) {
            System.out.println("Gambling/deciding on: " + decisionLiteral);
            applyStep(clauses, decisionLiteral);

            // Go deeper with a fresh list:
            List<Integer> deeperAssignedLiterals = new ArrayList();
            deeperAssignedLiterals.addAll(literals);
            deeperAssignedLiterals.addAll(newLiterals);
            deeperAssignedLiterals.add(decisionLiteral);

            processDPLL(clauses, deeperAssignedLiterals);

            System.out.println("Undo gambling: " + decisionLiteral);
            undoStep(clauses, decisionLiteral);
        }

        // Undo all we've done this step:
        for (Integer literal : newLiterals) {
            System.out.println("Undo units: " + literal);
            undoStep(clauses, literal);
        }
    }

    /**
     * * When applying a step, keep all the dead literals (variables) so we can
     * undo. * * @param clauses * @param literal
     */
    private void applyStep(final List<Clause> clauses, final Integer literal) {
        for (Clause clause : clauses) {
            if (!clause.clauseSatisfied) {
                if (clause.unassignedLiterals.contains(literal)) {
                    clause.clauseSatisfied = true;
                } else if (clause.unassignedLiterals.contains(-literal)) {
                    clause.unassignedLiterals.remove((Integer) (-literal));
                    clause.deadLiterals.add(-literal);
                }
            }
        }
    }

    /**
     * * Because we are backtracking we need to be able to undo all the steps
     * in a clause, so we keep all the information * * @param clauses * @param
     * literal
     */
    private void undoStep(final List<Clause> clauses, final Integer literal) {
        for (Clause clause : clauses) {
            if (clause.clauseSatisfied && clause.unassignedLiterals.contains(literal)) {
                clause.clauseSatisfied = false;
            }
            if (clause.deadLiterals.contains(-literal)) {
                clause.deadLiterals.remove((Integer) (-literal));
                clause.unassignedLiterals.add(-literal);
            }
        }
    }

    private List<Integer> findUnitClauses(final List<Clause> clauses) {
        List<Integer> unitPropagation = new ArrayList<>();
        // Check if there are unit clauses:
        for (Clause clause : clauses) {
            if (clause.isUnitClause()) {
                unitPropagation.add(clause.unassignedLiterals.get(0));
            }
        }
        return unitPropagation;
    }

    private void exitWithSAT(final List<Integer> literals) {
        // We are done:
        System.out.println("SAT");

        // Sort the output as absolute values.
        Collections.sort(literals, Comparator.comparingInt(Math::abs));

        // TODO: We might not list all the input variables here, some are not needed and can be + or -.
        // And print:
        System.out.println(literals.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(" ")) + " 0");

        // Bye bye.
        System.exit(1);
    }

    public class Clause {

        private List<Integer> unassignedLiterals = new ArrayList<>();
        private List<Integer> deadLiterals = new ArrayList<>();
        private boolean clauseSatisfied = false;

        private Clause(String inputLine) {
            unassignedLiterals.addAll(
                    Arrays.stream(inputLine
                            .substring(0, inputLine.length() - 2)
                            .trim().split("\\s+"))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList()));
        }

        boolean isUnitClause() {
            return !clauseSatisfied && unassignedLiterals.size() == 1;
        }
    }
}
